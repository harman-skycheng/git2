package com.harman.sky_ble_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.harman.sky_ble_demo.bluetoothutil.BLEConnection;
import com.harman.sky_ble_demo.bluetoothutil.IBluetoothConnection;

public class Main2Activity extends AppCompatActivity {
    TextView textview1, textview2, textview3;
    IBluetoothConnection bleConnection1, bleConnection2, bleConnection3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textview1 = findViewById(R.id.textview1);
        textview2 = findViewById(R.id.textview2);
        textview3 = findViewById(R.id.textview3);
        if (Constant.keys != null && !Constant.keys.isEmpty()) {
            if (!TextUtils.isEmpty(Constant.keys.get(0))) {
                bleConnection1 = new BLEConnection(this, Constant.keys.get(0));
                bleConnection1.addOnReceiveCommandListner(mListner1);
                bleConnection1.connect();
                bleConnection1.sendCommand(REQ_FIRMWARE_VER);


                bleConnection1.sendCommand(REQ_DFU_VERSION);
                bleConnection1.sendCommand(REQ_ANALYTICS_DATA);
                bleConnection1.sendCommand(REQ_DEV_INFO);

            }
            if (Constant.keys.size() >= 2) {
                if (!TextUtils.isEmpty(Constant.keys.get(1))) {
                    bleConnection2 = new BLEConnection(this, Constant.keys.get(1));
                    bleConnection2.addOnReceiveCommandListner(mListner2);
                    bleConnection2.connect();

                    bleConnection1.sendCommand(REQ_FIRMWARE_VER);


                    bleConnection1.sendCommand(REQ_DFU_VERSION);
                    bleConnection1.sendCommand(REQ_ANALYTICS_DATA);
                    bleConnection1.sendCommand(REQ_DEV_INFO);

                }
            }
            if (Constant.keys.size() >= 3) {
                if (!TextUtils.isEmpty(Constant.keys.get(2))) {
                    bleConnection3 = new BLEConnection(this, Constant.keys.get(2));
                    bleConnection3.addOnReceiveCommandListner(mListner3);
                    bleConnection3.connect();

                }
            }

        }

//        if (Constant.keys != null && !Constant.keys.isEmpty()) {
//            if (!TextUtils.isEmpty(Constant.keys.get(0))) {
//
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                bleConnection1.sendCommand(REQ_FIRMWARE_VER);
//            }
//            if (Constant.keys.size() >= 2) {
//                if (!TextUtils.isEmpty(Constant.keys.get(1))) {
//
//                    bleConnection2.sendCommand(REQ_FIRMWARE_VER);
//                }
//            }
//
//        }


    }

    private IBluetoothConnection.OnReceiveCommandListner mListner1 = new IBluetoothConnection.OnReceiveCommandListner() {
        @Override
        public void onReceiveCommand(final String s) {
            Log.d("textview1", s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textview1.setText(textview1.getText() + " , " + s);
                }
            });

        }
    };

    private IBluetoothConnection.OnReceiveCommandListner mListner2 = new IBluetoothConnection.OnReceiveCommandListner() {
        @Override
        public void onReceiveCommand(final String s) {
            Log.d("textview2", s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textview2.setText(s);
                }
            });

        }
    };

    private IBluetoothConnection.OnReceiveCommandListner mListner3 = new IBluetoothConnection.OnReceiveCommandListner() {
        @Override
        public void onReceiveCommand(final String s) {
            Log.d("textview3", s);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textview3.setText(s);
                }
            });

        }
    };


    public static byte[] REQ_ANALYTICS_DATA = new byte[]{
            (byte) 0xaa, //identifier
            (byte) 0x81, //cmd is and sub cmd id
            (byte) 0x01,
            (byte) 0x00
    };

    public static byte[] REQ_DEV_INFO = new byte[]{
            (byte) 0xaa, //identifier
            (byte) 0x11, // cmd Id and sub cmd id
            (byte) 0x00,
            (byte) 0x00

    };

    public static byte[] REQ_FIRMWARE_VER = new byte[]{
            (byte) 0xaa, //Identifier
            (byte) 0x41, // cmd Id and sub cmd id
            (byte) 0x00, //payload length
            (byte) 0x00
    };

    public static byte[] REQ_DFU_VERSION = new byte[]{
            (byte) 0xaa, //identifier
            (byte) 0x4a, // cmd Id and sub cmd id
            (byte) 0x01,
            (byte) 0x00
    };

    public static byte[] SED_INFO_ACK = new byte[]{
            (byte) 0xaa, //identifier
            (byte) 0x01, // cmd Id and sub cmd id
            (byte) 0x02,
            (byte) 0x12,
            (byte) 0x00
    };

    public static byte[] CLEAR_ANALYTICS_DATA = new byte[]{
            (byte) 0xaa, //identifier
            (byte) 0x01, // cmd Id and sub cmd id
            (byte) 0x02,
            (byte) 0x82,
            (byte) 0x00
    };

    public static byte[] CMD_REQ_DEVICE_SOFTWARE_VERSION = new byte[]{
            (byte) 0xaa,
            (byte) 0x44,
    };

    public static byte[] REQ_DFU_START = new byte[]{
            (byte) 0xaa,
            (byte) 0x43,
            (byte) 0x0c
    };

    public static byte[] REQ_TRIGGER_UPGRADE = new byte[]{
            (byte) 0xaa,
            (byte) 0x4d
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bleConnection1.removeOnReceiveCommandListner(mListner1);
        bleConnection1.disconnect();
        if (bleConnection2 != null) {
            bleConnection2.removeOnReceiveCommandListner(mListner2);

            bleConnection2.disconnect();
        }
        if (bleConnection3 != null) {
            bleConnection3.removeOnReceiveCommandListner(mListner3);

            bleConnection3.disconnect();
        }
        Constant.keys.clear();

    }
}
