package com.vgaw.sexygirl.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.ScanEngine;
import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.holder.OneHolder;

import java.util.ArrayList;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class LocalTwoFragment extends Fragment {
    public static final String TAG = "localtwofragment";

    private ImageView v_back;
    private GridView gv;
    private EasyAdapter adapter;
    private ArrayList<String> dataList = new ArrayList<>();

    // [0]:categoryName;[1]:albumName
    private String[] nameArray = new String[2];
    private int limit = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_local_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v_back = (ImageView) getView().findViewById(R.id.v_back);
        gv = (GridView) getView().findViewById(R.id.gv);
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

            }
        });
        v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limit--;
            }
        });

        init();
    }

    private void init(){
        if (putStringArrayToList(dataList, ScanEngine.getCategory())){
            adapter.notifyDataSetChanged();
        }
    }

    private boolean putStringArrayToList(ArrayList<String> list, String[] array){
        if (array == null){
            return false;
        }
        for (String s : array){
            list.add(s);
        }
        return true;
    }
}
