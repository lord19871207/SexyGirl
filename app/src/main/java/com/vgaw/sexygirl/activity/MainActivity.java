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

import com.alibaba.fastjson.JSON;
import com.vgaw.sexygirl.BugHD;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.PreferenceUtil;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.bean.FOTABean;
import com.vgaw.sexygirl.fragment.LocalOneFragment;
import com.vgaw.sexygirl.fragment.OneFragment;
import com.vgaw.sexygirl.ui.loadmore.dialog.UpdateDialog;

import org.json.JSONException;
import org.json.JSONObject;

import im.fir.sdk.VersionCheckCallback;

public class MainActivity extends BaseActivity {
    private OneFragment oneFragment;
    private LocalOneFragment localOneFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUpdate();
        proNavigationView();

        oneFragment = new OneFragment();
        localOneFragment = new LocalOneFragment();

        changeFragment(oneFragment, OneFragment.TAG);
    }

    private void proNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    // 尤果网
                    case R.id.nav_ugirl:
                    // 在线
                    case R.id.nav_online:
                        changeFragment(oneFragment, OneFragment.TAG);
                        break;
                    // 本地
                    case R.id.nav_local:
                        changeFragment(localOneFragment, LocalOneFragment.TAG);
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
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (PreferenceUtil.isFirst()){
            final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerLayout.openDrawer(GravityCompat.START);
            drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    PreferenceUtil.setFirst();
                }
            }, 1000);
        }
    }

    private void changeFragment(Fragment fragment, String tag){
        FragmentManager manager = getSupportFragmentManager();
        Fragment f = manager.findFragmentByTag(tag);
        if (f == null){
            manager.beginTransaction().add(R.id.container, fragment, tag).commit();
        }else if (f.isHidden()){
            manager.beginTransaction().show(fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

            @Override
            public void onFail(Exception e) {
                super.onFail(e);
            }
        });
    }
}
