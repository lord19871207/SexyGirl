package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    public static int nullTo(Integer raw, int after){
        return raw == null ? after : raw;
    }

    public static boolean isNullOrEmpty(String raw) {
        return raw == null || "".equals(raw);
    }

    /**
     * 获取应用versionCode
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static final long GB_SD_CARD_SIZE = 1024 * 1024 * 1024;
    public static final long MB_SD_CARD_SIZE = 1024 * 1024;
    public static final long KB_SD_CARD_SIZE = 1024;
    public static final long B_SD_CARD_SIZE = 1;

    public static float getSDFreeSize(long unit) {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        long size = freeBlocks * blockSize;
        // 返回SD卡空闲大小
        return unit == GB_SD_CARD_SIZE ? size / GB_SD_CARD_SIZE
                : unit == MB_SD_CARD_SIZE ? size / MB_SD_CARD_SIZE
                : unit == KB_SD_CARD_SIZE ? size / KB_SD_CARD_SIZE
                : size / B_SD_CARD_SIZE;
    }

    public static void putStringArrayToList(ArrayList<String> list, String[] array){
        if (array != null) {
            for (String s : array) {
                list.add(s);
            }
        }
    }

    public static String proListToPath(List<String> pathList){
        StringBuilder path = new StringBuilder();
        for (String s : pathList){
            path.append(File.separator + s);
        }
        return path.toString();
    }
}
