package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.os.Environment;

import com.vgaw.sexygirl.Category;

import java.io.File;

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

    public static boolean deleteFile(String path){
        File file = new File(path);
        if (file.exists() && file.isFile()){
            file.delete();
            return true;
        }
        return false;
    }

    public static File getPicFile(int category, String albumName, String picName){
        String categoryName = null;
        switch (category){
            case Category.CATEGORY_UGIRL:
                categoryName = "尤果网";
                break;
            case Category.CATEGORY_TGIRL:
                categoryName = "推女郎";
                break;
        }
        String dirPath = getRootDirPath()
                + File.separator + categoryName
                + File.separator + albumName;
        File dir = new File(dirPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
        return new File(dir, picName);
    }

    public static String getRootDirPath(){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
                + File.separator + "SexyGirl";
    }

    /**
     * 是否按文件夹分类
     * -》文件夹名称如何取（应用名/分类名/相册名/图片名）
     * -》问题1
     */

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
