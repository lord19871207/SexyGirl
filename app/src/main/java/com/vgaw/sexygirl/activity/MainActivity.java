package com.vgaw.sexygirl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.alibaba.fastjson.JSON;
import com.vgaw.sexygirl.BugHD;
import com.vgaw.sexygirl.Category;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.PreferenceUtil;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.bean.FOTABean;
import com.vgaw.sexygirl.fragment.DownloadFragment;
import com.vgaw.sexygirl.fragment.LocalOneFragment;
import com.vgaw.sexygirl.fragment.OneFragment;
import com.vgaw.sexygirl.ui.loadmore.dialog.UpdateDialog;

import im.fir.sdk.VersionCheckCallback;

public class MainActivity extends BaseActivity {
    private long currentTime = -1;
    private static final long DELAY = 1000;

    private OneFragment oneFragment;
    private LocalOneFragment localOneFragment;
    private DownloadFragment downloadFragment;
    private DrawerLayout drawerLayout;
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        proFirst();
        proNavigationView();

        oneFragment = new OneFragment();
        localOneFragment = new LocalOneFragment();
        downloadFragment = new DownloadFragment();

        changeFragment(oneFragment, OneFragment.TAG);
    }

    private void proNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_ugirl);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()){
                    // 推女郎
                    case R.id.nav_tuigirl:
                        changeFragment(oneFragment, OneFragment.TAG);
                        oneFragment.changeCategory(Category.CATEGORY_TGIRL);
                        break;
                    // 尤果网
                    case R.id.nav_ugirl:
                        changeFragment(oneFragment, OneFragment.TAG);
                        oneFragment.changeCategory(Category.CATEGORY_UGIRL);
                        break;
                    // 在线
                    case R.id.nav_online:
                        changeFragment(oneFragment, OneFragment.TAG);
                        break;
                    // 本地
                    case R.id.nav_local:
                        changeFragment(localOneFragment, LocalOneFragment.TAG);
                        break;
                    // 下载
                    case R.id.nav_download:
                        changeFragment(downloadFragment, DownloadFragment.TAG);
                        break;
                    // 反馈
                    case R.id.nav_feedback:
                        startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                        break;
                    // 退出
                    case R.id.nav_quit:
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 为了防止第一次弹出动画和更新对话框同时打开，所以将更新放在此处
     */
    private void proFirst(){
        if (PreferenceUtil.isFirst()){
            drawerLayout.openDrawer(GravityCompat.START);
            drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    PreferenceUtil.setFirst();

                    checkUpdate();
                }
            }, 1000);
        }else {
            checkUpdate();
        }
    }

    private void changeFragment(Fragment fragment, String tag){
        if (currentFragment == fragment){
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        if (currentFragment != null){
            manager.beginTransaction().hide(currentFragment).commit();
        }
        Fragment f = manager.findFragmentByTag(tag);
        if (f == null){
            manager.beginTransaction().add(R.id.container, fragment, tag).commit();
        }else if (f.isHidden()){
            manager.beginTransaction().show(fragment).commit();
        }
        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        long temp = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (localOneFragment != null && !localOneFragment.isHidden()){
            if (localOneFragment.callBack()){
                super.onBackPressed();
            }
        } else if (temp - currentTime > DELAY){
            Utils.showToast(this, "再次点击退出程序");
            currentTime = temp;
        } else {
            super.onBackPressed();
        }
    }

    private void checkUpdate(){
        BugHD.checkUpdate(new VersionCheckCallback() {
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                FOTABean bean = JSON.parseObject(s, FOTABean.class);
                if (Utils.getVersionCode(MainActivity.this) < bean.getVersion()){
                    new UpdateDialog(MainActivity.this, bean).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentFragment = null;
    }
}
