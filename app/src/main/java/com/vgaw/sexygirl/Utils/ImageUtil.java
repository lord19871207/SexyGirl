package com.vgaw.sexygirl.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.vgaw.sexygirl.R;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class ImageUtil {
    public static void init(Context context) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.pic_mask)
                .cacheInMemory(false)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiskCache(FileUtil.getCacheFile()))
                .build();
        ImageLoader.getInstance().init(config);
    }

    public static DisplayImageOptions getAblumOptions(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
                .cacheInMemory(false)
                .cacheOnDisk(true)
                //.preProcessor()
                //.postProcessor()
                //.decodingOptions()
                //.displayer(new SimpleBitmapDisplayer())
                .build();
        return options;
    }
}
