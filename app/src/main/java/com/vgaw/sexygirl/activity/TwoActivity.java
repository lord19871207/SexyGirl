package com.vgaw.sexygirl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vgaw.sexygirl.Category;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.FileUtil;
import com.vgaw.sexygirl.adapter.EasyAdapter;
import com.vgaw.sexygirl.adapter.EasyHolder;
import com.vgaw.sexygirl.bean.BaseBean;
import com.vgaw.sexygirl.bean.UGirlTwoBean;
import com.vgaw.sexygirl.fragment.OneFragment;
import com.vgaw.sexygirl.holder.TwoHolder;
import com.vgaw.sexygirl.service.DownloadService;
import com.vgaw.sexygirl.spider.DataSpider;
import com.vgaw.sexygirl.spider.TGirlTwoSpider;
import com.vgaw.sexygirl.spider.UGirlTwoSpider;
import com.vgaw.sexygirl.ui.SharePop;
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
    private ArrayList<BaseBean> dataList = null;
    private UGirlTwoSpider spider;
    private int index = 1;
    private String albumName = null;
    private int category;

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
        albumName = intent.getStringExtra("title");
        String url = intent.getStringExtra("url");
        proUrl(url);

        category = intent.getIntExtra("category", Category.CATEGORY_UGIRL);
        initCategory(category);
        dataList = spider.getDataList();
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UGirlTwoBean bean = ((UGirlTwoBean)dataList.get(position));
                bean.setStatus(UGirlTwoBean.STATUS_READY);
                adapter.notifyDataSetChanged();
                DownloadService.getInstance().addDownloadTask(bean.getUrl(), FileUtil.getPicFile(category, albumName, getPicName(bean.getUrl())));
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                UGirlTwoBean bean = ((UGirlTwoBean)dataList.get(position));
                new SharePop(TwoActivity.this, mTencent)
                        .setData("SexyGirl", albumName, bean.getUrl(), bean.getUrl())
                        .show();
                return true;
            }
        });
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
            case Category.CATEGORY_TGIRL:
                spider = new TGirlTwoSpider() {
                    @Override
                    protected String getUrl(int index) {
                        return url_prefix + index + url_suffix;
                    }
                };
                break;
            // 尤果网
            case Category.CATEGORY_UGIRL:
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

    private String getPicName(String url){
        String[] array = url.split("/");
        return array[array.length - 1];
    }

}
