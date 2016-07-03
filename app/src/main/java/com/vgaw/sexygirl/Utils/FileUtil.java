package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class FileUtil {
    private static String dirName = "sexygirl";
    private static File dir;

    public static void init(Context context){
        File file = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        dir = new File(file, dirName);
    }

    // 图片格式形如:http://i.lesmao.com/i/les/T/UGirls/183/183_001_t7i_h.jpg
    /*public static boolean savePic(String picName){
        File picFile = new File(dir, picName);
        if (picFile.exists()){
            return true;
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(picFile);

        } catch (IOException e) {
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                }
            }
        }
    }*/
}
