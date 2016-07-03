package com.vgaw.sexygirl.Utils;

import android.content.Context;

public final class DensityUtil {

    private static float density = -1f;
    private static float scaledDensity = -1f;
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    private DensityUtil() {
    }

    public static float getDensity(Context context) {
        if (density == -1) {
            density = context.getResources().getDisplayMetrics().density;
        }
        return density;
    }

    public static float getScaledDensity(Context context){
        if (scaledDensity == -1){
            scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        }
        return scaledDensity;
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * getDensity(context) + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / getDensity(context) + 0.5f);
    }

    public static int sp2px(Context context, float spValue){
        return (int) (spValue * getScaledDensity(context) + 0.5f);
    }

    public static int px2sp(Context context, float pxValue){
        return (int) (pxValue / getScaledDensity(context) + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        if (widthPixels == -1) {
            widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    public static int getScreenHeight(Context context) {
        if (heightPixels == -1) {
            heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }
}
