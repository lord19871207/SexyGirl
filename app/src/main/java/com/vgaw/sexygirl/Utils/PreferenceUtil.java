package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static String getLastUGril(){
        return sp.getString("last_ugirl", null);
    }

    public static void setLastUGril(String info){
        sp.edit().putString("last_ugirl", info).commit();
    }

    public static boolean isFirst(){
        return sp.getBoolean("isFirst", true);
    }

    public static void setFirst(){
        sp.edit().putBoolean("isFirst", false).commit();
    }
}
