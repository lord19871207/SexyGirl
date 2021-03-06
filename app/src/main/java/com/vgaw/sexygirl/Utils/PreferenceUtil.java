package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.open.utils.Util;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class PreferenceUtil {
    private static SharedPreferences sp;

    public static void init(Context context){
        sp = context.getSharedPreferences("sexy_girl", Context.MODE_PRIVATE);
    }

    public static String getLastUGirl(){
        return sp.getString("last_ugirl", null);
    }

    public static void setLastUGirl(String info){
        sp.edit().putString("last_ugirl", info).commit();
    }

    public static String getLastTGirl(){
        return sp.getString("last_tgirl", null);
    }

    public static void setLastTGirl(String info){
        sp.edit().putString("last_tgirl", info).commit();
    }

    public static boolean isFirst(){
        return sp.getBoolean("isFirst", true);
    }

    public static void setFirst(){
        sp.edit().putBoolean("isFirst", false).commit();
    }

    public static boolean isVersionChanged(Context context){
        int nowVersion = Utils.getVersionCode(context);
        int lastVersion = sp.getInt("versionCode", -1);
        return nowVersion != lastVersion;
    }

    public static void setVersion(Context context){
        sp.edit().putInt("versionCode", Utils.getVersionCode(context)).commit();
    }

    public static boolean isPicMask(){
        return sp.getBoolean("isPicMask", false);
    }

    public static void setPicMask(boolean isPicMask){
        sp.edit().putBoolean("isPicMask", isPicMask).commit();
    }
}
