package com.harman.sky_ble_demo;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    BluetoothAdapter bluetoothAdapter;
    private ListView listview;
    private BluetoothDeviceAdapter deviceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = findViewById(R.id.listview);
        Button button = findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edittext = findViewById(R.id.edittext);
                String s = edittext.getText().toString();
                String[] ss = s.split("%");
                for (String temp : ss) {
                    String key = (String) deviceAdapter.getItem(Integer.parseInt(temp));
                    if (!Constant.keys.contains(key)) {
                        Constant.keys.add(key);
                    }
                }


                startActivity(new Intent(MainActivity.this, Main2Activity.class));

            }
        });
        deviceAdapter = new BluetoothDeviceAdapter(this);
        listview.setAdapter(deviceAdapter);

        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        if (bluetoothAdapter == null) {
            finish();
        }
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            finish();
        }
//        insertDummyContactWrapper();
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2019);
        } else {
//            startScan();
            scheduleStartScan();
        }

    }

//    private void insertDummyContactWrapper() {
//        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
//        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
//            if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                showMessageOKCancel("You need to allow access to Contacts",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
//                                        123);
//                            }
//                        });
//                return;
//            }
//            requestPermissions(new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
//                    123);
//            return;
//        }else{
//            startScan();
//        }
//
//    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void startScan() {
        bluetoothAdapter.getBluetoothLeScanner().startScan(scanCallback);
        mScanning = true;
    }

    private void stoptScan() {
        mScanning = false;
        bluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
    }
    Handler mHandler = new Handler();
    private boolean mScanning = false;
    private static final long SCAN_PERIOD = 10000;

//    private void scanLeDevice(final boolean enable) {
//        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    startScan();
//                }
//            }, SCAN_PERIOD);
//
//            mScanning = true;
//            stoptScan();
//        } else {
//            mScanning = false;
//            stoptScan();
//        }
//
//    }
    private Timer mTimer;
    private TimerTask mTimerTask;

    private void scheduleStartScan(){
//        if(mTimer == null){
//            mTimer = new Timer();
//        }
//        if(mTimerTask == null){
//            mTimerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    if(mScanning){
//                        stoptScan();
//
//                    }
//
                    startScan();
//                }
//            };
//        }
//        mTimer.schedule(mTimerTask,0,10000);
    }

    private void scheduleStopScan(){
//        if(mScanning){
           stoptScan();
//        }
//        if(mTimerTask != null){
//            mTimerTask.cancel();
//        }
//        if(mTimer != null){
//            mTimer.cancel();
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        scheduleStopScan();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 2019: {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    finish();
                } else {
//                    startScan();
                    scheduleStartScan();
                }
                return;
            }
        }
    }

    ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);

            BluetoothDevice bluetoothDevice = result.getDevice();
            if (!TextUtils.isEmpty(bluetoothDevice.getName())) {
                String s = HexHelper.encodeHexStr(result.getScanRecord().getBytes());


                Map<String, String> manufacturerDatas = getScanRecordData(s);


                String VID = manufacturerDatas.get(Constant.ManufacturerData.VID);

                if ((!TextUtils.isEmpty(VID)) && Integer.parseInt(VID, 16) == Constant.HARMAN_VENDOR_ID) {
                    deviceAdapter.addDevice(bluetoothDevice);
                    Log.d(TAG, "callbackType = " + callbackType + "result = " + result);
//                    Log.d(TAG, "manufacturerDatas = " + manufacturerDatas);
//                    Log.d(TAG, "s = " + s);
                    result.getScanRecord().getDeviceName();

                }
            }
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            Log.d(TAG, "results = " + results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.e(TAG, "errorCode = " + errorCode);
        }
    };

    public Map<String, String> getScanRecordData(String data) {
        Map<String, String> allData = new HashMap<>();
        if (null != data && data.length() == 124) {
            //Take pid to determine if 1:N product or TSW Product
            int start = 10;
            int end = start + 4;
            String VID = StringUtility.changeToLittleEndian(data.substring(start, end));
            if (VID.equalsIgnoreCase(Constant.HarmanVendorId)) { //1:N product
                allData.put(Constant.ManufacturerData.VID, VID);

                start = end;
                end = start + 4;
                String PID = StringUtility.changeToLittleEndian(data.substring(start, end));
                allData.put(Constant.ManufacturerData.PID, PID);

                start = end;
                end = start + 2;
                String MID = data.substring(start, end);
                allData.put(Constant.ManufacturerData.MID, MID);

                start = end;
                end = start + 2;
                String Role = data.substring(start, end);
                allData.put(Constant.ManufacturerData.ROLE, Role);

                start = end;
                end = start + 4;
                String CRC = StringUtility.changeToLittleEndian(data.substring(start, end));
                allData.put(Constant.ManufacturerData.CRC, CRC);

                start = end;
                end = start + 2;
                int NameSize = Integer.parseInt(data.substring(start, end), 16);
                int markStart = start;

                start = end + 2;
                end = start + ((NameSize - 1) * 2);
                if (end > data.length()) {
                    return allData;
                }
                String nameValue = data.substring(start, end);
                String deviceName = HexHelper.hexStringToString(nameValue);
                allData.put(Constant.ManufacturerData.NAME, deviceName);

                if (Constant.isVmicroProduct(PID)) {
                    start = markStart + 8;
                    end = start + 2;
                    NameSize = Integer.parseInt(data.substring(start, end), 16);

                    start = end + 2;
                    end = start + ((NameSize - 1) * 2);
                    nameValue = data.substring(start, end);
                    deviceName = HexHelper.hexStringToString(nameValue);
                    allData.put(Constant.ManufacturerData.NAME, deviceName);

                    String value = allData.get(Constant.ManufacturerData.ROLE);
                    int roleData = Integer.parseInt(value, 16);
                    byte[] roleBytes = {(byte) roleData};
                    String roleAndConnectable = HexHelper.bytes2BinStr(roleBytes);
                    allData.put(Constant.ManufacturerData.CONNECTABLE, roleAndConnectable.substring(0, 1));
                    allData.put(Constant.ManufacturerData.BATTERY, roleAndConnectable.substring(3, 6));
                    allData.put(Constant.ManufacturerData.ROLE, String.valueOf(Integer.parseInt(roleAndConnectable.substring(6, roleAndConnectable.length()))));
                } else {
                    String value = allData.get(Constant.ManufacturerData.ROLE);
                    int roleData = Integer.parseInt(value, 16);
                    byte[] roleBytes = {(byte) roleData};
                    String roleAndConnectable = HexHelper.bytes2BinStr(roleBytes);
                    allData.put(Constant.ManufacturerData.CONNECTABLE, roleAndConnectable.substring(0, 1));
                    allData.put(Constant.ManufacturerData.ROLE, String.valueOf(Integer.parseInt(roleAndConnectable.substring(3, roleAndConnectable.length()))));
                }
            } else {  //TWS product
                start = 42;
                end = start + 4;
                VID = StringUtility.changeToLittleEndian(data.substring(start, end));
                if (VID.equalsIgnoreCase(Constant.HarmanVendorId)) {
                    allData.put(Constant.ManufacturerData.VID, VID);
                    start = end + 4;
                    end = start + 4;
                    String PID = StringUtility.changeToLittleEndian(data.substring(start, end));
                    allData.put(Constant.ManufacturerData.PID, PID);
                    start = end;
                    end = start + 2;
                    String MID = data.substring(start, end);
                    allData.put(Constant.ManufacturerData.MID, MID);
                    start = end;
                    end = start + 4;
                    String CRC = StringUtility.changeToLittleEndian(data.substring(start, end));
                    allData.put(Constant.ManufacturerData.CRC, CRC);
                }
            }
        }
        return allData;
    }

}
