package com.vgaw.sexygirl.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.FileUtil;
import com.vgaw.sexygirl.Utils.ScanEngine;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.holder.TwoHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caojin on 2016/7/6.
 */
public class LocalTwoActivity extends BaseActivity {
    private View v_back;
    private ListView lv;
    private EasyAdapter adapter;

    private ArrayList<String> dataList = new ArrayList<>();
    private List<String> pathList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_two);
        v_back = findViewById(R.id.v_back);
        v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv = (ListView) findViewById(R.id.lv);
        adapter = new EasyAdapter(this, dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new TwoHolder(){
                    @Override
                    public void refreshView(Object item) {
                        iv_head_item.setImageBitmap(BitmapFactory.decodeFile(FileUtil.getRootDirPath()
                                + Utils.proListToPath(pathList) + File.separator + String.valueOf(item)));
                    }
                };
            }
        };
        lv.setAdapter(adapter);

        refresh();
    }

    private void refresh(){
        pathList = JSON.parseArray(getIntent().getStringExtra("path"), String.class);
        String[] picArray = ScanEngine.get(pathList);
        Utils.putStringArrayToList(dataList, picArray);
        adapter.notifyDataSetChanged();
    }

}
