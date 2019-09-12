package com.harman.sky_ble_demo;

import android.content.Context;
import android.preference.PreferenceManager;



import java.util.ArrayList;

/**
 * Created by hkumar2 on 12/2/2016.
 */

public class StringUtility {
    private final static String TAG = StringUtility.class.getSimpleName();
    /**
     * method for split a string after every n characters
     * @param text string to split
     * @param n split string after every n character
     */
    public static ArrayList<String> splitEqually(String text, int n) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        ArrayList<String> ret = new ArrayList<String>((text.length() + n - 1) / n);
        String str = "";
        for (int start = 0; start < text.length(); start += n) {
              str = text.substring(start, Math.min(text.length(), start + n));
           if(str != null && !str.equalsIgnoreCase("00"))
            ret.add(str);
        }
        return ret;
    }

    public static String getString(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
            //sb.append("\t");
        }
      String str =  String.format("%-6s", sb.toString() ).replace(' ', '0');

        return str;
    }

    public static String getStringGA(String str) {

        String newString = str.toString().replaceAll("0", "");
        newString = newString.replaceAll("(.{1})(?!$)", "$1,");
        return newString;
    }
    /*---------------------Data Analytics OTA fail frequency-------- Start -----------------------*/
    public static void setAnalyticsOTAMac(String key, Context context, String value){
        if(context != null){
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).commit();
        }

    }
    /*---------------------Data Analytics OTA fail frequency-------- End -----------------------*/
    //Big Endian or Little Endian
    public static  String changeToLittleEndian(String value){
        if(value != null && value.length() == 4){
            String littleEndian = value.substring(2, 4);
            String bigEndian = value.substring(0, 2);
            return new String(littleEndian + bigEndian);
        }
        return value;
    }
}
