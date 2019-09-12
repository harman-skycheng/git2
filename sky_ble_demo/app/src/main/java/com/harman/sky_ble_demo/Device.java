package com.harman.sky_ble_demo;

import android.bluetooth.BluetoothDevice;

public class Device {
    private BluetoothDevice bluetoothDevice;

    public Device(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    public BluetoothDevice getBluetoothDevice() {
        return bluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Device) {
            Device d = (Device) obj;
            return this.getBluetoothDevice().getAddress().equals(d.getBluetoothDevice().getAddress());

        } else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return getBluetoothDevice().getAddress().hashCode();
    }
}
