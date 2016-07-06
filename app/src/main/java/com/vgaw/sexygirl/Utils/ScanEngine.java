package com.vgaw.sexygirl.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class ScanEngine {
    public static boolean shouldAskForScan(){
        return false;
    }

    public static String[] get(List<String> pathList){
        File file = new File(FileUtil.getRootDirPath() + Utils.proListToPath(pathList));
        if (!file.exists()){
            return null;
        }
        return file.list();
    }
}
