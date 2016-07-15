package com.vgaw.sexygirl.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
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
        lv.setOnItemLongClickListener(longClickListener);
        adapter = new EasyAdapter(this, dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new TwoHolder(){
                    @Override
                    public void refreshView(Object item) {
                        iv_head_item.setImageBitmap(BitmapFactory.decodeFile(getPicPath(String.valueOf(item))));
                    }
                };
            }
        };
        lv.setAdapter(adapter);

        refresh();
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (FileUtil.deleteFile(getPicPath(dataList.get(position)))){
                Utils.showToast(LocalTwoActivity.this, "删除成功");
                dataList.remove(position);
                adapter.notifyDataSetChanged();
            }else {
                Utils.showToast(LocalTwoActivity.this, "删除失败");
            }
            return true;
        }
    };

    private void refresh(){
        pathList = JSON.parseArray(getIntent().getStringExtra("path"), String.class);
        String[] picArray = ScanEngine.get(pathList);
        Utils.putStringArrayToList(dataList, picArray);
        adapter.notifyDataSetChanged();
    }

    private String getPicPath(String picName){
        return FileUtil.getRootDirPath()
                + Utils.proListToPath(pathList) + File.separator + picName;
    }

}
