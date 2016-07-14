package com.vgaw.sexygirl.holder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.ImageUtil;
import com.vgaw.sexygirl.Utils.PreferenceUtil;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.bean.UGrilOneBean;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class OneHolder extends EasyHolder {
    protected ImageView iv_head_item;
    protected TextView tv_title_item;

    @Override
    public int getLayout() {
        return R.layout.item_one;
    }

    @Override
    public View createView() {
        iv_head_item = (ImageView) view.findViewById(R.id.iv_head_item);
        tv_title_item = (TextView) view.findViewById(R.id.tv_title_item);
        return view;
    }

    // 可能取值不正确
    @Override
    public void refreshView(Object item) {
        UGrilOneBean bean = (UGrilOneBean) item;
        tv_title_item.setText(bean.getPicName());
        if (PreferenceUtil.isPicMask()){
            iv_head_item.setImageResource(R.mipmap.pic_mask);
        }else {
            ImageLoader.getInstance().displayImage(bean.getPicUrl(), iv_head_item, ImageUtil.getAblumOptions());
        }
    }
}
