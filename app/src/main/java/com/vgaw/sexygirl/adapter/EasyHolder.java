package com.vgaw.sexygirl.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public abstract class EasyHolder {
    protected View view;

    public void init(Context mContext){
        view = LayoutInflater.from(mContext).inflate(getLayout(), null);
    }

    public abstract @LayoutRes int getLayout();

    public abstract View createView();

    public abstract void  refreshView(Object item);
}
