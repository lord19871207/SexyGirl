package com.vgaw.sexygirl.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.holder.ProgressHolder;
import com.vgaw.sexygirl.service.DownloadService;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class DownloadFragment extends ListFragment {
    public static final String TAG = "downloadfragment";
    private EasyAdapter adapter;
    private Handler handler;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DownloadService.getInstance().setOnProgressListener(new DownloadService.OnProgressListener() {
            @Override
            public void onProgressUpdate() {
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new EasyAdapter(getContext(), DownloadService.getInstance().getDataList()) {
            @Override
            public EasyHolder getHolder(int type) {
                return new ProgressHolder();
            }
        };
        setListAdapter(adapter);
        handler = new Handler();
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh(){
        handler.postDelayed(refreshRunnable, 1000);
    }

    private Runnable refreshRunnable = new Runnable() {
        @Override
        public void run() {

            adapter.notifyDataSetChanged();
            refresh();
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(refreshRunnable);
    }
}
