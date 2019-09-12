package com.harman.sky_ble_demo.bluetoothutil;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BLEConnection extends AbstractBluetoothConnection {
    public static final String TAG = "BLEConnection";

    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mWriteCharacteristic;
    private static Map<String, BLEConnection> mMap;
    BluetoothDevice mDevice = null;

    public BLEConnection(Context context, String address) {
        super(context, address);
    }

    @Override
    public void connect() {
        mDevice = getBluetoothDevice();
        if (mDevice != null) {
            mBluetoothGatt = mDevice.connectGatt(mContext, false, mBluetoothGattCallback, BluetoothDevice.TRANSPORT_LE);


        }

    }

    @Override
    public void disconnect() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }

    }

    @Override
    public void sendCommand(byte[] data) {
        if (commandQueue == null) {
            commandQueue = new ArrayList<>();
        }
        commandQueue.add(data);


    }

    public void sendCommandToDevice(byte[] data) {
        if (mWriteCharacteristic != null) {
            mWriteCharacteristic.setValue(data);

            boolean bool = mBluetoothGatt.writeCharacteristic(mWriteCharacteristic);
//            String command = HexHelper.encodeHexStr(characteristic.getValue());
        }

    }


    BluetoothGattCallback mBluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.d(TAG, "onConnectionStateChange status = " + status + ", newState = " + newState + " , address = " + mAddress);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.e(TAG, "onConnectionStateChange2 status = " + status + ", newState = " + newState + " , address = " + mAddress);
                if (mDevice != null) {
                    gatt.disconnect();
                    gatt.close();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBluetoothGatt = mDevice.connectGatt(mContext, false, mBluetoothGattCallback, BluetoothDevice.TRANSPORT_LE);


                }
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (null != gatt) {
                    BluetoothGattService bluetoothRxTxService = gatt.getService(UUID.fromString(Const.BLE_RX_TX_UUID));
                    if (bluetoothRxTxService == null) {
                        Log.e(TAG, "fail1" + " , address = " + mAddress);
                        return;

                    }
                    List<BluetoothGattCharacteristic> bluetoothGattCharacteristics = bluetoothRxTxService.getCharacteristics();
                    if (bluetoothGattCharacteristics == null || bluetoothGattCharacteristics.size() < 2) {
                        Log.e(TAG, "fail2" + " , address = " + mAddress);
                        return;
                    }


                    BluetoothGattCharacteristic readCharacteristic = bluetoothRxTxService.getCharacteristic(UUID.fromString(Const.RX_UUID));
                    if (readCharacteristic == null) {
                        Log.e(TAG, "fail3" + " , address = " + mAddress);
                        return;
                    }


                    gatt.setCharacteristicNotification(readCharacteristic, true);


                    BluetoothGattDescriptor descriptor = readCharacteristic.getDescriptor(UUID.fromString(Const.DESCRIPTOR));
                    if (null != descriptor) {
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                        gatt.writeDescriptor(descriptor);
                    } else {
                        Log.e(TAG, "fail4" + " , address = " + mAddress);
                        return;
                    }

                    mWriteCharacteristic = bluetoothRxTxService.getCharacteristic(UUID.fromString(Const.TX_UUID));
                    if (mWriteCharacteristic == null) {
                        Log.e(TAG, "fail5" + " , address = " + mAddress);
                        return;
                    } else {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        while (!bb) {
//                            boolean b = gatt.requestMtu(517);
//
//                            Log.d(TAG, "requestMtu b = " + b + " , address = " + mAddress);
//                            try {
//                                Thread.sleep(1500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }

                        boolean b = gatt.requestMtu(517);
//
                        Log.d(TAG, "requestMtu b = " + b + " , address = " + mAddress);


                    }


                }
            } else {
                Log.e(TAG, "onServicesDiscovered status = " + status + " , address = " + mAddress);

                return;
            }
        }

        private boolean bb = false;

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);

            String command = Util.encodeHexStr(characteristic.getValue(), Const.DIGITS_LOWER);
            if (status == BluetoothGatt.GATT_SUCCESS) {

                commandQueue.remove(0);
                startSendCommandQueue();

            } else {
                Log.e(TAG, "onCharacteristicWrite command = " + command + " ,status = " + status + " , address = " + mAddress);
            }


        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

            String command = Util.encodeHexStr(characteristic.getValue(), Const.DIGITS_LOWER);
            notifyChange(command);
            Log.d(TAG, "onCharacteristicChanged command = " + command + " , address = " + mAddress);
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.d(TAG, "onMtuChanged status = " + status + " ,mtu = " + mtu + " , address = " + mAddress + " , hasRequestMTU = " + hasRequestMTU);
            if (!hasRequestMTU) {
                bb = true;
                hasRequestMTU = true;
                startSendCommandQueue();
            }


        }
    };

    private boolean containCommand(byte[] data) {
        if (commandQueue == null || commandQueue.isEmpty()) {
            return false;
        }

        for (byte[] bytes : commandQueue) {
            if (isSameCommand(bytes, data)) {
                return true;
            }


        }
        return false;

    }

    private boolean isSameCommand(byte[] data, byte[] data2) {
        if (data == null || data.length == 0 || data2 == null || data2.length == 0) {
            return false;
        }
        if (data.length != data2.length) {
            return false;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] != data2[i]) {
                return false;
            }

        }

        return true;

    }

    private List<byte[]> commandQueue = new ArrayList<>();

    private void startSendCommandQueue() {
        if (commandQueue != null && commandQueue.size() > 0) {
            sendCommandToDevice(commandQueue.get(0));
            String command = Util.encodeHexStr(commandQueue.get(0), Const.DIGITS_LOWER);

            Log.d(TAG, "startSendCommandQueue command = " + command + " , address = " + mAddress);

        }

    }

    private boolean hasRequestMTU = false;


//    public static BLEConnection getInstance(Context context,String address){
//        if(mMap == null){
//            mMap = new HashMap<>();
//        }
//
//    }


}
