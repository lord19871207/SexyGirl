package com.vgaw.sexygirl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.ScanEngine;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.activity.LocalTwoActivity;
import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.holder.OneHolder;

import java.util.ArrayList;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class LocalOneFragment extends Fragment {
    public static final String TAG = "localonefragment";

    private TextView tv_path;
    private GridView gv;
    private EasyAdapter adapter;
    private ArrayList<String> dataList = new ArrayList<>();

    private ArrayList<String> pathList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tv_path = (TextView) getView().findViewById(R.id.tv_path);
        gv = (GridView) getView().findViewById(R.id.gv);
        gv.setNumColumns(2);
        adapter = new EasyAdapter(getContext(), dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new OneHolder(){
                    @Override
                    public void refreshView(Object item) {
                        iv_head_item.setImageResource(R.mipmap.dir);
                        tv_title_item.setText(String.valueOf(item));
                    }
                };
            }
        };
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (pathList.size() == 1){
                    Intent intent = new Intent(getContext(), LocalTwoActivity.class);
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(pathList.get(0));
                    list.add(dataList.get(position));
                    intent.putExtra("path", JSON.toJSONString(list));
                    startActivity(intent);
                }else {
                    backOrForward(false, dataList.get(position));
                }
            }
        });
        gv.setAdapter(adapter);
        tv_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathList.size() == 0){
                    return;
                }
                backOrForward(true, null);
            }
        });

        refresh();
    }

    /**
     * 返回值表示下次是否还能返回
     * @param isBack
     * @param path
     * @return
     */
    private void backOrForward(boolean isBack, String path){
        if (isBack){
            pathList.remove(pathList.size() - 1);
        }else {
            pathList.add(path);
        }
        tv_path.setText(getPath());
        refresh();
    }

    private String getPath(){
        StringBuilder path = new StringBuilder();
        for (String s : pathList){
            path.append(s + ">");
        }
        return path.toString();
    }

    private void refresh(){
        dataList.clear();
        Utils.putStringArrayToList(dataList, ScanEngine.get(pathList));
        adapter.notifyDataSetChanged();
    }

    public boolean callBack(){
        if (pathList.size() > 0){
            backOrForward(true, null);
            return false;
        }
        return true;
    }

}
