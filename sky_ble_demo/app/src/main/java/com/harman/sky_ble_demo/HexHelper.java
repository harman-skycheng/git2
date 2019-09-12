package com.harman.sky_ble_demo;

import android.util.SparseArray;

/**
 * Created by inaagarwal20 on 9/7/2016.
 * Class to help converting byte response to readable String format and also to parse ScanRecord
 */
public class HexHelper {
    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private static final String[] BINARY_ARRAY = { "0000", "0001", "0010", "0011",
                                                   "0100", "0101", "0110", "0111",
                                                   "1000", "1001", "1010", "1011",
                                                   "1100", "1101", "1110", "1111" };

    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    private static final char[] DIGITS_UPPER = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    // The following data type values are assigned by Bluetooth SIG.
    // For more details refer to Bluetooth 4.1 specification, Volume 3, Part C, Section 18.
    private static final int DATA_TYPE_FLAGS = 0x01;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 0x02;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 0x03;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 0x04;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 0x05;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 0x06;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 0x07;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 0x08;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 0x09;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 0x0A;
    private static final int DATA_TYPE_SERVICE_DATA = 0x16;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 0xFF;
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    private static final String TAG = "HexHelper";



    public static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, bytes.length);
    }

    public static String bytesToHex(byte[] bytes, int len) {
        if(bytes == null){
            return null;
        }
        int tmpLen = bytes.length < len ? bytes.length : len;
        char[] hexChars = new char[tmpLen * 2];
        for (int j = 0; j < tmpLen; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    // Helper method to extract bytes from byte array.
    private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(scanRecord, start, bytes, 0, length);
        return bytes;
    }

    public static String getRole(byte unsingedByte){
        return getStringFromBytes(unsingedByte).substring(6, 8);
    }

    public static String getConnectableState(byte unsingedByte){
        return getStringFromBytes(unsingedByte).substring(0, 1);
    }

    private  static String getStringFromBytes(byte unsingedByte){
        int decimalRole = unsingedByte & 0xFF;
        int[] roleInteger = toBinary(decimalRole , 8);
        return buildString(roleInteger); /*roleInteger.toString();*/
    }

    private static int[] toBinary(int n, int base) {
        final int[] ret = new int[base];
        for (int i = 0; i < base; i++) {
            int j = (1 << i & n) !=0 ? 1: 0;
            ret[base - 1 - i] = j;
        }
        return ret;
    }

    private static String buildString(int[] roleInteger) {
        StringBuilder sb = new StringBuilder(roleInteger.length);
        for (int i : roleInteger) {
            sb.append(i);
        }
        return sb.toString();
    }

    /**
     * @see 将字节数组转换为十六进制字符串
     * @date 2014年5月5日 17:07:43
     * @author Herman.Xiong
     * @param data byte[]
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data) {
        return encodeHexStr(data, true);
    }

    /**
     * @see 将字节数组转换为十六进制字符串
     * @author Herman.Xiong
     * @date 2014年5月5日 17:08:01
     * @param data byte[]
     * @param toLowerCase true 传换成小写格式 ， false 传换成大写格式
     * @return 十六进制String
     */
    public static String encodeHexStr(byte[] data, boolean toLowerCase) {
        return encodeHexStr(data, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
    }

    /**
     * @see 将字节数组转换为十六进制字符串
     * @author Herman.Xiong
     * @date 2014年5月5日 17:08:15
     * @param data byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制String
     */
    protected static String encodeHexStr(byte[] data, char[] toDigits) {
        return new String(encodeHex(data, toDigits));
    }

    /**
     * @see 将字节数组转换为十六进制字符数组
     * @author Herman.Xiong
     * @date 2014年5月5日 17:07:31
     * @param data byte[]
     * @param toDigits 用于控制输出的char[]
     * @return 十六进制char[]
     */
    protected static char[] encodeHex(byte[] data, char[] toDigits) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }

    public static String bytes2BinStr(byte[] bArray){
        String outStr = "";
        int pos = 0;
        for(byte b:bArray){
            //High four
            pos = (b&0xF0)>>4;
            outStr+=BINARY_ARRAY[pos];
            //Low four
            pos=b&0x0F;
            outStr+=BINARY_ARRAY[pos];
        }
        return outStr;
    }

    public static void main(String[] args) {
        String a3 = "4a424c20466c69702034";
        System.out.println("--------Bright------->"+hexStringToString(a3));

        int Liang = Integer.parseInt("9a", 16);

        String abc = "9a";
        int u = Integer.parseInt(abc, 16);
        byte[] ab = {(byte) u, };

        System.out.println("-----Sun---->"+bytes2BinStr(ab));
        String Bright = bytes2BinStr(ab);

        System.out.println("---Connectable---->"+Bright.substring(0,1));
        System.out.println("---Battery---->"+Bright.substring(3,6));
        System.out.println("---Role---->"+String.valueOf(Integer.parseInt(Bright.substring(6, Bright.length()))));

    }

    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword);
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    public static byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new RuntimeException("未知的字符");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    protected static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("非法16进制字符 " + ch
                    + " 在索引 " + index);
        }
        return digit;
    }
}
