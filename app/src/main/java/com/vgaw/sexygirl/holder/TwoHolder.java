package com.vgaw.sexygirl.holder;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.DensityUtil;
import com.vgaw.sexygirl.Utils.PreferenceUtil;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.bean.UGirlTwoBean;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */

// 290 * 435
public class TwoHolder extends EasyHolder{
    protected ImageView iv_head_item;
    protected ImageView iv_download;

    @Override
    public int getLayout() {
        return R.layout.item_two;
    }

    @Override
    public View createView() {
        iv_head_item = (ImageView) view.findViewById(R.id.iv_head_item);
        iv_download = (ImageView) view.findViewById(R.id.iv_download);
        return view;
    }

    @Override
    public void refreshView(Object item) {
        UGirlTwoBean bean = (UGirlTwoBean) item;
        if (PreferenceUtil.isPicMask()){
            iv_head_item.setImageResource(R.mipmap.pic_mask);
        }else {
            ImageLoader.getInstance().displayImage(bean.getUrl(), iv_head_item, new ImageSize(DensityUtil.getScreenWidth(context), DensityUtil.getScreenWidth(context)));
        }
        int status = bean.getStatus();
        if (status == UGirlTwoBean.STATUS_READY){
            animate();
            bean.setStatus(UGirlTwoBean.STATUS_DONE);
        }
    }

    private void animate(){
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0, 1, 0);
        PropertyValuesHolder scaleHolder = PropertyValuesHolder.ofFloat("scaleX", 1, (float) 1.5, 1);
        ObjectAnimator showAnimator = ObjectAnimator.ofPropertyValuesHolder(iv_download, alphaHolder, scaleHolder);
        showAnimator.setInterpolator(new LinearInterpolator());
        showAnimator.setDuration(1500);
        showAnimator.start();
    }
}
