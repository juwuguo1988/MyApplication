package com.example.hello.myapplication.common.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.hello.myapplication.R;

import java.util.List;


public class TabLayout extends LinearLayout {

    private int mTabCount;
    private View mSelectedView;

    public interface OnTabClickListener {

        void onTabClick(int position);
    }

    public TabLayout(Context context) {
        this(context, null);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
    }

    public void initData(List<TabItem> tabItems, final OnTabClickListener onTabClickListener) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        if (tabItems == null && tabItems.isEmpty()) {
            throw new IllegalArgumentException("tabs can not be empty");
        } else {
            mTabCount = tabItems.size();
            for (int i = 0; i < tabItems.size(); i++) {
                final int position = i;
                View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null);
                TextView tvTabs = view.findViewById(R.id.tv_tab_item);
                ImageView ivTabs = view.findViewById(R.id.iv_tab_item);
                tvTabs.setText(tabItems.get(i).getTitleResId());
                ivTabs.setImageResource(tabItems.get(i).getImageResId());
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onTabClickListener != null) {
                            onTabClickListener.onTabClick(position);
                        }
                    }
                });
                addView(view, params);
            }
        }
    }

    public void setCurrentTab(int position) {
        if (position >= 0 && position < mTabCount) {
            View view = getChildAt(position);
            if (mSelectedView != view) {
                view.setSelected(true);
                if (mSelectedView != null) {
                    mSelectedView.setSelected(false);
                }
                mSelectedView = view;
            }
        }
    }

    public void setRedDotVisibility(int position, boolean isShow) {
        if (position >= 0 && position < mTabCount) {
            View view = getChildAt(position);
           // view.findViewById(R.id.v_red_dot).setVisibility(isShow ? VISIBLE : GONE);
        }
    }

    public static class TabItem {

        private int imageResId;
        private int titleResId;

        public TabItem(int imageResId, int titleResId) {
            this.imageResId = imageResId;
            this.titleResId = titleResId;
        }

        public int getImageResId() {
            return imageResId;
        }

        public void setImageResId(int imageResId) {
            this.imageResId = imageResId;
        }

        public int getTitleResId() {
            return titleResId;
        }

        public void setTitleResId(int titleResId) {
            this.titleResId = titleResId;
        }
    }
}

