package com.vgaw.sexygirl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
                    // 绅士
                    case R.id.nav_gentleman:
                        boolean isPicMask = PreferenceUtil.isPicMask();
                        item.setTitle(isPicMask ? "正常" : "绅士");
                        PreferenceUtil.setPicMask(!isPicMask);
                        changeFragment(oneFragment, OneFragment.TAG);
                        break;
                    // 本地
                    case R.id.nav_local:
                        changeFragment(localOneFragment, LocalOneFragment.TAG);
                        break;
                    // 关于
                    case R.id.nav_about:
                        Toast.makeText(MainActivity.this, "待开发", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        findViewById(R.id.tv_feedback).setOnClickListener(clickListener);
        findViewById(R.id.tv_quit).setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (v.getId()){
                case R.id.tv_feedback:
                    startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
                    break;
                case R.id.tv_quit:
                    finish();
                    break;
            }
        }
    };

    /**
     * 为了防止第一次弹出动画和更新对话框同时打开，所以将更新放在此处
     * 每次版本更新都要演示一次，防止第一次安装没有看到导致不知道需要演示的功能
     */
    private void proFirst(){
        if (PreferenceUtil.isVersionChanged(this)){
            drawerLayout.openDrawer(GravityCompat.START);
            drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    PreferenceUtil.setVersion(MainActivity.this);

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
            // 减少缓存使用
            if (currentFragment == oneFragment){
                manager.beginTransaction().remove(currentFragment).commit();
            }else {
                manager.beginTransaction().hide(currentFragment).commit();
            }
        }
        Fragment f = manager.findFragmentByTag(tag);
        if (f == null){
            manager.beginTransaction().add(R.id.container, fragment, tag).commit();
        }else {
            manager.beginTransaction().show(fragment).commit();
        }
        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        long temp = System.currentTimeMillis();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (localOneFragment != null && !localOneFragment.isHidden() && !localOneFragment.callBack()){
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
}
