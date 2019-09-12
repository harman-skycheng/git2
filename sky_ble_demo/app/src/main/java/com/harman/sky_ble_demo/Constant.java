package com.harman.sky_ble_demo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by inaagarwal20 on 7/31/2016.
 * Constants Used by Engine module
 */
public class Constant {
    public static List<String> keys = new ArrayList<>();

    public static boolean mChangeStereoByUser = false;

    public static final long SCANNING_PERIOD = 10000;
    public static final long BLE_SCANNING_PERIOD = 20000;
    public static final long ENABLE_GET_HELP_TIME_PERIOD = 10000;
    public static final int PROGRESS_HALF_COMPLETE = 50;
    public static final int MILLISEC_TO_SEC = 1000;
    public static final int SEC_TO_MIN = 60;
    public static final String NONE_CHANNEL = "NONE_CHANNEL";
    public static final String LEFT_CHANNEL = "LEFT_CHANNEL";
    public static final String RIGHT_CHANNEL = "RIGHT_CHANNEL";
    public static boolean BLE_SUPPORT_ONLY = true;
    public static final int HARMAN_VENDOR_ID = 87;// ToDo: @Aishwarya Ideally it should be 87;
    public static final long RESEND_CMD_PERIOD = 2 * 1000;
    public static final String SINGLE_DEVICE = "SINGLE_DEVICE";
    public static final String STEREO = "STEREO";
    public static final String PARTY_MULTIPLE = "PARTY_MULTIPLE";
    public static final String PARTY = "PARTY";
    public static final String NONE_MODE = "NONE";
    public static final String STATE_DISCONNECTED = "STATE_DISCONNECTED";
    public static final String STATE_CONNECTING = "STATE_CONNECTING";
    public static final String STATE_CONNECTED = "STATE_CONNECTED";
    public static final String STATE_DEAFULT = "STATE_DEAFULT";
    public static final String DOWNLOAD_FIRMWARE_FAILED = "DOWNLOAD_FIRMWARE_FAILED";
    public static final String OTA_FAILED = "OTA_FAILED";
    public static final String OTA_UPGRADE_VERSION_NOT_MATCHED = "OTA_UPGRADE_VERSION_NOT_MATCHED";
    public static final String OTA_RESTART_FAILED = "OTA_CONNECTION_FAILED";
    public static final String OTA_FAIL_KEY = "FAIL";
    public static final String STATE_CONNECTION_ERROR = "STATE_CONNECTION_ERROR";
    public static final String STATE_SERVICE_ERROR = "STATE_SERVICE_ERROR";
    public static final int RET_DEV_INFO_REQUEST_MAX = 4;
    public static boolean OTA_GET_DATA_ANALYTICS = false;

    public static String KILL_APP_IN_OTA_PROCESS = "Kill_App_In_OTA_Process";
    public static String OTA_FAIL_MAC = "";
    public static boolean AFTER_OTA_CLEAN_ANALYTICS_DATA = false;
    public static float w = 292.34f;
    public static float h = 420.89f;
    public static float r = 146.17f;
    public static float factor = 0.9f;
    public static float brightness_max = 80;
    public static float brightness_min = 20;

    public static interface DFU_RES_STATUS_TYPE {
        public static final int DFU_READY = 1;
        public static final int DFU_DOWNLOADING = 2;
        public static final int DFU_UPGRADING = 3;
        public static final int DFU_ERROR = 0;

    }

    public static final String HarmanVendorId = "0057";
    public static final String DEVICE_ADDRESS = "Device_Address";

    public static interface ManufacturerData {
        String VID = "Vendor_ID";
        String PID = "Product_ID";
        String MID = "Model_ID";
        String ROLE = "Role";
        String CRC = "Crc";
        String NAME = "Device_Name";
        String CONNECTABLE = "Connect_Able";
        String BATTERY = "Device_Battery";
    }

    /* LED PATTERN CONSTANTS */
    public static final String CUSTOMIZED_THEME_ID = "08";

    public static final String DAConfig_Product = "http://storage.harman.com/JBLConnectPlus/DAConfig/DAConfig_Product.xml";
    public static final String DAConfig_Test = "http://storage.harman.com/Testing/JBLConnectPlus/DAConfig/DAConfig_Test.xml";
    public static final String AppUpdateConfig_Product = "http://storage.harman.com/JBLConnectPlus/AppUpdateConfig/AppUpdateConfig_Product.xml";
    public static final String AppUpdateConfig_Test = "http://storage.harman.com/Testing/JBLConnectPlus/AppUpdateConfig/AppUpdateConfig_Test.xml";

    public static final String ACTION_FORCE_UPGRADE = "ACTION_FORCE_UPGRADE";
    public static final String ACTION_APP_IS_FRONT = "Action_App_Is_Front";
    public static final String ACTION_APP_IS_BACK = "Action_APP_Is_Back";


    public static final String JBL_FLIP_4 = "1ed1";
    public static final String JBL_PULSE_3 = "1ed2";
    public static final String JBL_BOOMBOX = "1ee7";
    public static final String JBL_CHARGE_3 = "1ebc";
    public static final String JBL_XTREME_2 = "1efc";
    public static final String JBL_CHARGE_4 = "1f17";
    public static final String JBL_FLIP_5 = "1f31";
    public static final String JBL_Pulse4 = "1f56";

    /**
     * after add every single QCC pid here, don't forget to put it into the array of mQccProductList also
     **/
    public static final String JBL_FLIP_4_QCC = "1f24";
    public static final String JBL_PULSE_3_QCC = "1f28";
    public static final String JBL_BOOMBOX_QCC = "1f27";
    public static final String JBL_CHARGE_3_QCC = "1f25";
    public static final String JBL_XTREME_2_QCC = "1f26";
    public static final String JBL_CHARGE_4_QCC = "1f29";

    public static final String ACTION_DEVICE_UPDATED = "Harman_Device_Updated";
    public static final String ACTION_NEW_DEVICE_ACTIVE = "Harman_New_Device_Active";
    public static final String ACTION_DEVICE_ROLE_CHANGE = "Harman_Device_Role_Change";
    public static final String ACTION_DEVICE_CRC_CHANGE = "Harman_Device_Crc_Change";
    public static final String ACTION_DEVICE_NAME_CHANGE = "Harman_Device_Name_Change";
    public static final String ACTION_DEVICE_CONNECTABLE_CHANGE = "Harman_Device_Connectable_Change";
    public static final String ACTION_DEVICE_BATTERY_CHANGE = "Harman_Device_Battery_Change";
    public static final String JBL_FLIP5_UUID = "0000fddf-0000-1000-8000-00805f9b34fb";

    public static final int FLIP3_PID = 0x23;
    public static final int XSTREME_PID = 0x24;
    public static final int PULSE2_PID = 0x26;
    public static final int CHARGE3_PID = 0x1EBC;

    public static boolean RESTART_CHECK = false;
    public static String RESTART_MAC = null;

    public static String[] mQccProductList = {JBL_FLIP_4_QCC, JBL_PULSE_3_QCC, JBL_BOOMBOX_QCC, JBL_CHARGE_3_QCC, JBL_XTREME_2_QCC, JBL_CHARGE_4_QCC};
    private static String[] mVmicroProductList = {JBL_FLIP_5, JBL_Pulse4};
    public static final String ACTION_SWITCH_BRIGHTNESS = "ACTION_SWITCH_BRIGHTNESS";
    public static final String KEY_SWITCH_BRIGHTNESS = "KEY_SWITCH_BRIGHTNESS";
    public static final String KEY_SMOOTH = "KEY_SMOOTH";
    public static int MTU_VIMICRO = 35;

    public static boolean isVmicroProduct(String productId) {
        for (String pid : mVmicroProductList) {
            if (pid.equalsIgnoreCase(productId)) {
                return true;
            }
        }
        return false;
    }

    public static int numberOfTimes(String src, String check) {
        int srcLeng = src.length();
        String afterSrc = src.replaceAll(check, "");
        int afterLeng = afterSrc.length();
        int num = check.length();
        return (srcLeng - afterLeng) / num;
    }


}
