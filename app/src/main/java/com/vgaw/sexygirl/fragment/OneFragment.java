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

import com.vgaw.sexygirl.Category;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.activity.TwoActivity;
import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.bean.UGrilOneBean;
import com.vgaw.sexygirl.holder.OneHolder;
import com.vgaw.sexygirl.spider.DataSpider;
import com.vgaw.sexygirl.spider.TGirlOneSpider;
import com.vgaw.sexygirl.spider.UGirlOneSpider;
import com.vgaw.sexygirl.ui.loadmore.LoadMoreContainer;
import com.vgaw.sexygirl.ui.loadmore.LoadMoreGridViewContainer;
import com.vgaw.sexygirl.ui.loadmore.LoadMoreHandler;

import java.util.ArrayList;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class OneFragment extends Fragment {
    public static final String TAG = "onefragment";

    private LoadMoreGridViewContainer gridViewContainer;
    private GridView gv;
    private EasyAdapter adapter;
    private ArrayList<UGrilOneBean> dataList = null;
    private DataSpider spider;
    private int index = 1;
    private int category = Category.CATEGORY_NONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRefreshView(getView());

        gv = (GridView) getView().findViewById(R.id.gv);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), TwoActivity.class);
                intent.putExtra("url", dataList.get(position).getNextUrl());
                intent.putExtra("title", dataList.get(position).getPicName());
                intent.putExtra("category", category);
                startActivity(intent);
            }
        });
        initCategory();
    }

    /**
     * 切换mask
     */
    public void updateMask(){
        adapter.notifyDataSetChanged();
    }

    public void changeCategoryAndRefresh(int category, boolean isRefresh){
        if (this.category != category){
            this.category = category;
            if (isRefresh){
                initCategory();
            }
        }
    }

    private void initCategory(){
        switch (category){
            // 尤果网
            case Category.CATEGORY_UGIRL:
                spider = new UGirlOneSpider();
                break;
            // 推女郎
            case Category.CATEGORY_TGIRL:
                spider = new TGirlOneSpider();
                break;
        }
        index = 1;
        if (dataList != null){
            dataList = null;
        }
        dataList = spider.getDataList();
        adapter = new EasyAdapter(getContext(), dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new OneHolder();
            }
        };
        gv.setAdapter(adapter);

        getNext();
    }

    private void initRefreshView(View view) {
        gridViewContainer = (LoadMoreGridViewContainer) view.findViewById(R.id.load_more_grid_view_container);
        gridViewContainer.useDefaultFooter();
        gridViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getNext();
            }
        });
        gridViewContainer.loadMoreFinish(false, true);
    }

    private void getNext() {
        spider.nextPage(index++, new DataSpider.DataSpiderListener() {
            @Override
            public void onSuccess(boolean hasMore) {
                adapter.notifyDataSetChanged();
                gridViewContainer.loadMoreFinish(false, hasMore);
            }

            @Override
            public void onFailed() {
                gridViewContainer.loadMoreFinish(false, false);
            }

            @Override
            public void onUpdated(int count) {
                Utils.showToast(getContext(), "新增" + count + "条更新");
            }
        });
    }
}
