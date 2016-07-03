package com.vgaw.sexygirl.holder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.ImageUtil;
import com.vgaw.sexygirl.adapter.EasyHolder;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */

// 290 * 435
public class TwoHolder extends EasyHolder{
    private ImageView iv_head_item;

    @Override
    public int getLayout() {
        return R.layout.item_two;
    }

    @Override
    public View createView() {
        iv_head_item = (ImageView) view.findViewById(R.id.iv_head_item);
        return view;
    }

    @Override
    public void refreshView(Object item) {
        String url = (String) item;
        ImageLoader.getInstance().displayImage(url, iv_head_item);
    }
}
