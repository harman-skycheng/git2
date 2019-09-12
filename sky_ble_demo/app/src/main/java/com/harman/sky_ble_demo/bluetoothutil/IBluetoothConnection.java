package com.harman.sky_ble_demo.bluetoothutil;

import android.bluetooth.BluetoothDevice;

public interface IBluetoothConnection {
    void connect();

    void disconnect();

    void sendCommand(byte[] data);

    void addOnReceiveCommandListner(OnReceiveCommandListner listner);

    void removeOnReceiveCommandListner(OnReceiveCommandListner listner);

    void clearOnReceiveCommandListner();

    interface OnReceiveCommandListner {
        void onReceiveCommand(String s);
    }


}
