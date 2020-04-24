package com.example.hello.myapplication.lazy.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.hello.myapplication.R;

/**
 * Created by juwuguo on 2020-04-24.
 */
public class NormalFragment extends BaseLazyLoadFragment {
    private static final String TAG = "NormalFragment";
    private String mFragmentName;
    private TextView tv_fragment_name;
    private static final String ORIGIN_FRAGMENT_NAME = "ORIGIN_FRAGMENT_NAME";

    public static NormalFragment newInstance(String fragmentName) {
        Bundle args = new Bundle();
        args.putString(ORIGIN_FRAGMENT_NAME, fragmentName);
        NormalFragment fragment = new NormalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_normal_layout;
    }

    @Override
    protected void initView(View rootView) {
        mFragmentName = (String) getArguments().get(ORIGIN_FRAGMENT_NAME);
        tv_fragment_name = rootView.findViewById(R.id.tv_fragment_name);
    }

    @Override
    protected void onFragmentLoadStart() {
        super.onFragmentLoadStart();
        Log.d(TAG, mFragmentName + " fragment" + "开始一切操作");
        tv_fragment_name.setText(mFragmentName);

    }

    @Override
    protected void onFragmentLoadStop() {
        super.onFragmentLoadStop();
        Log.d(TAG, mFragmentName + " fragment" + "暂停一切操作");
        tv_fragment_name.setText(mFragmentName);
    }


}
