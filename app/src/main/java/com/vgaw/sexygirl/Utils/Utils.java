package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class Utils {
    // 可能出错，取值不对
    public static ArrayList<String> findValueByKey(String raw, String... key){
        ArrayList<String> resultList = new ArrayList<>(key.length);
        String[] array = raw.split("\"");
        int keySpeed = 0;
        for (int i = 0;i < array.length;i++){
            if (keySpeed < key.length && array[i].contains(key[keySpeed])){
                keySpeed++;
                if (i + 1 < array.length){
                    resultList.add(array[i + 1]);
                }
            }
        }
        return resultList;
    }

    public static void showToast(Context context, String info){
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }

    public static String nullToEmtpy(String raw) {
        return nullTo(raw, "");
    }

    public static String nullTo(String raw, String after) {
        return raw == null ? after : raw;
    }

    public static boolean isNullOrEmpty(String raw) {
        return raw == null || "".equals(raw);
    }
}
