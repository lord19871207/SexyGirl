package com.vgaw.sexygirl.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.fragment.LocalOneFragment;
import com.vgaw.sexygirl.fragment.OneFragment;

public class MainActivity extends BaseActivity {
    private OneFragment oneFragment;
    private LocalOneFragment localOneFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                }
                return false;
            }
        });
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
}
