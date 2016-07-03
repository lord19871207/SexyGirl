package com.vgaw.sexygirl;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */

// 更过功能参考网址：http://bughd.com/doc/android-customize
public class BugHD {
    public static void checkUpdate(VersionCheckCallback callback) {
        FIR.checkForUpdateInFIR("5964f99ffa115354572c28f165cbdba1", callback);
    }

    public static void sendUDInfo(String info) {
        FIR.sendCrashManually(new Exception(info));
    }
}
