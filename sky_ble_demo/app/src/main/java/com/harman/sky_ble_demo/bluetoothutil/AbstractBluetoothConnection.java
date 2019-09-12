package com.harman.sky_ble_demo.bluetoothutil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBluetoothConnection implements IBluetoothConnection {
    private List<OnReceiveCommandListner> listners = null;
    protected Context mContext;
    protected String mAddress;

    public AbstractBluetoothConnection(Context ctx,String address){
        this.mContext = ctx;
        this.mAddress = address;

    }

    @Override
    public void addOnReceiveCommandListner(OnReceiveCommandListner listner) {
        if (listners == null) {
            listners = new ArrayList<>();
        }
        if (!listners.contains(listner)) {
            listners.add(listner);
        }


    }

    @Override
    public void removeOnReceiveCommandListner(OnReceiveCommandListner listner) {
        if (listners != null && listners.contains(listner)) {
            listners.remove(listner);
        }

    }

    @Override
    public void clearOnReceiveCommandListner() {
        if (listners != null && !listners.isEmpty()) {
            listners.clear();
            listners = null;
        }

    }

    protected void notifyChange(String s) {
        if (listners != null && !listners.isEmpty()) {
            for (OnReceiveCommandListner listner : listners) {
                if (listner != null) {
                    listner.onReceiveCommand(s);
                }

            }
        }
    }

    protected BluetoothDevice getBluetoothDevice() {
        BluetoothManager bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter.getRemoteDevice(mAddress);
    }
}
