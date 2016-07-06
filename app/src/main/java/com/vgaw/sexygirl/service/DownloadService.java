package com.vgaw.sexygirl.service;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.vgaw.sexygirl.Utils.HttpUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class DownloadService {
    private static DownloadService instance = new DownloadService();

    private LinkedHashMap<String, Integer> dataMap = new LinkedHashMap<>();

    private ArrayList<Integer> dataList = new ArrayList<>();

    private DownloadService(){}

    public static DownloadService getInstance(){
        return instance;
    }

    public void addDownloadTask(final String url, File path){
        if (dataMap.get(url) == null){
            dataMap.put(url, 0);
            HttpUtil.get(url, null, new FileAsyncHttpResponseHandler(path) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                    dataMap.remove(url);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                }

                @Override
                public void onProgress(long bytesWritten, long totalSize) {
                    super.onProgress(bytesWritten, totalSize);
                    int speed = (int) (bytesWritten / totalSize * 100);
                    if (speed == 100){
                        dataMap.remove(url);
                    }else {
                        dataMap.put(url, speed);
                    }
                    if (listener != null){
                        updateList();
                        listener.onProgressUpdate();
                    }
                }
            });
        }
    }

    private synchronized void updateList(){
        dataList.clear();
        dataList.addAll(dataMap.values());
    }

    public List<Integer> getDataList(){
        return this.dataList;
    }

    public interface OnProgressListener{
        void onProgressUpdate();
    }

    private OnProgressListener listener;

    public void setOnProgressListener(OnProgressListener listener){
        this.listener = listener;
    }
}
