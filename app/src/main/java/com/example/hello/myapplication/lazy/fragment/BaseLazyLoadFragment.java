package com.example.hello.myapplication.lazy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by juwuguo on 2020-04-24.
 */
public abstract class BaseLazyLoadFragment extends Fragment {
    private View rootView;
    /**
     * 界面还没加载进来，就走了setUserVisibleHint()，事件分发了，
     * 如果子类事件分发事件里有UI更新，肯定会报错
     */
    private boolean isViewCreated;

    private boolean isVisibleStatus = false; //保存上次可见状态


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }
        initView(rootView);
        isViewCreated = true;
        /**
         *   解决第一次初始化加载问题
         *   由于首次加载的时候，setUserVisibleHint方法在onCreateView之前
         *   所以此时isViewCreated为false，导致事件分发不出去。
         *   在这必须补齐一次，让事件在第一次加载的时候，分发出去
         */
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
        return rootView;
    }


    protected abstract int getLayoutRes();

    protected abstract void initView(View rootView);


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isViewCreated) {//view 已经初始化完成后，再分发事件
            if (isVisibleToUser && !isVisibleStatus) { //当前可见，上次肯定不可见，分发true
                dispachUserVisibleHint(true);
            } else if (!isVisibleToUser && isVisibleStatus) { //当前不可见，上次肯定可见，分发false
                dispachUserVisibleHint(false);
            }
        }
    }


    private void dispachUserVisibleHint(boolean visibleStatus) {
        isVisibleStatus = visibleStatus;
        if (visibleStatus) {
            onFragmentLoadStart();
        } else {
            onFragmentLoadStop();
        }
    }


    protected void onFragmentLoadStart() {
    }


    protected void onFragmentLoadStop() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint() && !isVisibleStatus) {
            dispachUserVisibleHint(true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint() && isVisibleStatus) {
            dispachUserVisibleHint(false);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
        isViewCreated = false;
        isVisibleStatus = false;
    }


}
