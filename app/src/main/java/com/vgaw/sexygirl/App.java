package com.vgaw.sexygirl;

import android.app.Application;

import com.vgaw.sexygirl.Utils.ImageUtil;
import com.vgaw.sexygirl.Utils.PreferenceUtil;

import im.fir.sdk.FIR;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FIR.init(this);
        ImageUtil.init(this);
        PreferenceUtil.init(this);
    }
}
