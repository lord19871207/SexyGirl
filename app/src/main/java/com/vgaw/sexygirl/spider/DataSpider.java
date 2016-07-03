package com.vgaw.sexygirl.spider;

import com.loopj.android.http.TextHttpResponseHandler;
import com.vgaw.sexygirl.HttpUtil;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by caojin on 2016/6/28.
 */
public abstract class DataSpider<I> {
    protected ArrayList<I> dataList = new ArrayList();

    public ArrayList<I> getDataList(){
        return this.dataList;
    }

    public void nextPage(int index, final DataSpiderListener listener){
        HttpUtil.get(getUrl(index), null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                listener.onFailed();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                proPage(responseString, listener);
            }
        });
    }

    protected abstract void proPage(String response, DataSpiderListener listener);

    protected abstract String getUrl(int index);

    public interface DataSpiderListener{
        void onSuccess(boolean hasMore);
        void onFailed();
        void onUpdated(int count);
    }
}
