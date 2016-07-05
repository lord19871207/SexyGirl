package com.vgaw.sexygirl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.fragment.OneFragment;
import com.vgaw.sexygirl.holder.TwoHolder;
import com.vgaw.sexygirl.spider.DataSpider;
import com.vgaw.sexygirl.spider.TGirlTwoSpider;
import com.vgaw.sexygirl.spider.UGirlTwoSpider;
import com.vgaw.sexygirl.ui.loadmore.LoadMoreContainer;
import com.vgaw.sexygirl.ui.loadmore.LoadMoreHandler;
import com.vgaw.sexygirl.ui.loadmore.LoadMoreListViewContainer;

import java.util.ArrayList;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */

/**
 * intent传参：
 * 变量           类型          描述
 * url            String        首页网址
 * title          String        标题
 * category       int           分类
 *
 * intent返参：
 * 变量       类型          描述
 */
public class TwoActivity extends BaseActivity {
    private View v_back;

    private String url_prefix;
    private String url_suffix;

    private LoadMoreListViewContainer listViewContainer;
    private ListView lv;
    private EasyAdapter adapter;
    private ArrayList<String> dataList = null;
    private UGirlTwoSpider spider;
    private int index = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        v_back = findViewById(R.id.v_back);
        v_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initRefreshView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        proUrl(url);

        initCategory(intent.getIntExtra("category", OneFragment.CATEGORY_UGIRL));
        dataList = spider.getDataList();
        lv = (ListView) findViewById(R.id.lv);
        adapter = new EasyAdapter(this, dataList) {
            @Override
            public EasyHolder getHolder(int type) {
                return new TwoHolder();
            }
        };
        lv.setAdapter(adapter);

        getNext();
    }

    private void initCategory(int category){
        switch (category){
            // 推女郎
            case OneFragment.CATEGORY_TGIRL:
                spider = new TGirlTwoSpider() {
                    @Override
                    protected String getUrl(int index) {
                        return url_prefix + index + url_suffix;
                    }
                };
                break;
            // 尤果网
            case OneFragment.CATEGORY_UGIRL:
                spider = new UGirlTwoSpider() {
                    @Override
                    protected String getUrl(int index) {
                        return url_prefix + index + url_suffix;
                    }
                };
                break;
        }
    }

    private void initRefreshView() {
        listViewContainer = (LoadMoreListViewContainer) findViewById(R.id.load_more_list_view_container);
        listViewContainer.useDefaultFooter();
        listViewContainer.setUpView(findViewById(R.id.iv_up));
        listViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                getNext();
            }
        });
        listViewContainer.loadMoreFinish(false, true);
    }

    private void getNext(){
        spider.nextPage(index++, new DataSpider.DataSpiderListener() {
            @Override
            public void onSuccess(boolean hasMore) {
                adapter.notifyDataSetChanged();
                listViewContainer.loadMoreFinish(false, hasMore);
            }

            @Override
            public void onFailed() {
                listViewContainer.loadMoreFinish(false, false);
            }

            @Override
            public void onUpdated(int count) {

            }
        });
    }

    private void proUrl(String url){
        // http://www.lesmao.com/thread-13266-{$index[1-5]}-2.html
        String[] array = url.split("-");
        url_prefix = array[0] + "-" + array[1] + "-";
        url_suffix = "-" + array[3];
    }

}
