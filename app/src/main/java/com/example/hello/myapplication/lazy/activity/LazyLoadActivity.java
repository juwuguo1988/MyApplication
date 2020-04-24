package com.example.hello.myapplication.lazy.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.views.TabLayout;
import com.example.hello.myapplication.lazy.adapter.HomeFragmentAdapter;


import java.util.ArrayList;
import java.util.List;

public class LazyLoadActivity extends AppCompatActivity {
    private int[] mTabImages = new int[]{R.drawable.selector_tab_home_icon, R.drawable.selector_tab_medicine_icon,
            R.drawable.selector_tab_health_icon, R.drawable.selector_tab_user_icon};
    private int[] mTabTitles = new int[]{R.string.tab_home, R.string.tab_medicine, R.string.tab_health, R.string.tab_user};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_load_layout);
        initView();
    }


    private void initView() {
        ViewPager mVpHomePager = findViewById(R.id.vp_home_pager);
        TabLayout mTlHomeTabs = findViewById(R.id.tl_home_tabs);
        HomeFragmentAdapter fragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager());
        mVpHomePager.setAdapter(fragmentAdapter);
        mVpHomePager.setOffscreenPageLimit(4);
        List<TabLayout.TabItem> tabItems = new ArrayList<>();
        for (int i = 0; i < mTabTitles.length; i++) {
            tabItems.add(new TabLayout.TabItem(mTabImages[i], mTabTitles[i]));
        }
        mTlHomeTabs.initData(tabItems, new TabLayout.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                mVpHomePager.setCurrentItem(position);
            }
        });

        mTlHomeTabs.setCurrentTab(0);
        mVpHomePager.setCurrentItem(0);

        mVpHomePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTlHomeTabs.setCurrentTab(position);
                mVpHomePager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}

