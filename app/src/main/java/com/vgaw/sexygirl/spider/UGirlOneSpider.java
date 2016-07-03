package com.vgaw.sexygirl.spider;

import android.util.Log;

import com.vgaw.sexygirl.Utils.PreferenceUtil;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.bean.UGrilOneBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by caojin on 2016/6/28.
 */
public class UGirlOneSpider extends DataSpider<UGrilOneBean> {
    private boolean isChecked = false;

    @Override
    protected void proPage(String response, DataSpiderListener listener) {
        BufferedReader reader = new BufferedReader(new StringReader(response));
        String temp = null;
        boolean hasMore = false;
        try {
            StringBuilder result = null;
            boolean isStarted = false;
            while ((temp = reader.readLine()) != null){
                if (isStarted){
                    result.append(temp);
                    // 采集完毕
                    if (temp.contains("</div>")){
                        isStarted = false;
                        UGrilOneBean bean = proStringToBean(result.toString());
                        if (bean != null){
                            dataList.add(bean);
                        }
                        result = null;
                    }
                    continue;
                }
                if (temp.contains("<div class=\"group\">")){
                    temp = reader.readLine();
                    if (temp != null && temp.contains("<div class=\"photo\">")){
                        isStarted = true;
                        result = new StringBuilder();
                        continue;
                    }
                }else if (temp.contains("fa-chevron-right")){
                    hasMore = true;
                }
            }
            if (!isChecked){
                isChecked = true;
                int count = checkUpdateOne();
                if (count == -1 && count != 0){
                    listener.onUpdated(count);
                }
                PreferenceUtil.setLastUGril(dataList.get(0).getNextUrl());
            }
            listener.onSuccess(hasMore);
        } catch (IOException e) {
            listener.onFailed();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    @Override
    protected String getUrl(int index) {
        return "http://www.lesmao.com/forum-UGirls-" + index + ".html";
    }

    protected int checkUpdateOne(){
        String last = PreferenceUtil.getLastUGril();
        for (int i = 0; i < dataList.size(); i++){
            if (dataList.get(i).getNextUrl().equals(last)){
                return i;
            }
        }
        return -1;
    }

    private UGrilOneBean proStringToBean(String raw){
        UGrilOneBean bean = new UGrilOneBean();
        ArrayList<String> list = Utils.findValueByKey(raw, "href", "src", "alt");
        if (list.size() == 3){
            bean.setNextUrl(list.get(0));
            bean.setPicUrl(list.get(1));
            bean.setPicName(list.get(2));
            return bean;
        }else {
            return null;
        }
    }
}
