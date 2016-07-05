package com.vgaw.sexygirl.spider;

import com.vgaw.sexygirl.Utils.PreferenceUtil;

/**
 * Created by caojin on 2016/7/5.
 */
public class TGirlOneSpider extends UGirlOneSpider {
    @Override
    protected String getUrl(int index) {
        return "http://www.lesmao.com/forum-39-" + index + ".html";
    }

    protected int checkUpdateOne(){
        String last = PreferenceUtil.getLastTGirl();
        PreferenceUtil.setLastTGirl(dataList.get(0).getNextUrl());
        for (int i = 0; i < dataList.size(); i++){
            if (dataList.get(i).getNextUrl().equals(last)){
                return i;
            }
        }
        return -1;
    }
}
