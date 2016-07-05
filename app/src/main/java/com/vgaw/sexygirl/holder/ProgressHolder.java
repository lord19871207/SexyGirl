package com.vgaw.sexygirl.holder;

import android.view.View;
import android.widget.ProgressBar;

import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.adapter.EasyHolder;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class ProgressHolder extends EasyHolder {
    private ProgressBar pb;

    @Override
    public int getLayout() {
        return R.layout.item_progress;
    }

    @Override
    public View createView() {
        pb = (ProgressBar) view.findViewById(R.id.pb);
        pb.setMax(100);
        return view;
    }

    @Override
    public void refreshView(Object item) {
        int speed = (int) item;
        pb.setProgress(speed);
    }
}
