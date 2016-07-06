package com.vgaw.sexygirl.Utils;

import java.io.File;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class ScanEngine {
    public static boolean shouldAskForScan(){
        return false;
    }

    public static String[] getCategory(){
        File rootDir = new File(FileUtil.getRootDirPath());
        if (!rootDir.exists()){
            return null;
        }
        return rootDir.list();
    }

    public static String[] getAlbum(String categoryName){
        File categoryFile = new File(FileUtil.getRootDirPath()
                + File.separator + categoryName);
        return categoryFile.list();
    }

    public static String[] getPic(String categoryName, String albumName){
        File albumFile = new File(FileUtil.getRootDirPath()
                + File.separator + categoryName
                + File.separator + albumName);
        return albumFile.list();
    }
}
