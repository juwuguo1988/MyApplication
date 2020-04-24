package com.example.hello.myapplication.lazy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.hello.myapplication.lazy.fragment.NormalFragment;

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return NormalFragment.newInstance("首页");
            case 1:
                return NormalFragment.newInstance("服药");
            case 2:
                return NormalFragment.newInstance("健康");
            case 3:
                return NormalFragment.newInstance("我的");
            default:
                return NormalFragment.newInstance("fragment 5");
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
