package com.example.hello.myapplication.counter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.hello.myapplication.R;
import com.example.hello.myapplication.common.bean.UserGoodBean;

import java.util.List;

public class GoodDisplayAdapter extends BaseAdapter {

    private Context mContext;
    private List<UserGoodBean> mUserGoodBeans;
    private LayoutInflater mLayoutInflater;
    private boolean isHasDelete;


    public GoodDisplayAdapter(Context context, List<UserGoodBean> userGoodBeans, boolean isHasDelete) {
        this.mContext = context;
        this.mUserGoodBeans = userGoodBeans;
        this.isHasDelete = isHasDelete;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mUserGoodBeans.size();
    }

    @Override
    public UserGoodBean getItem(int position) {
        return mUserGoodBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        InquiryViewHolder inquiryViewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_good_display_layout, parent, false);
            inquiryViewHolder = new InquiryViewHolder();
            inquiryViewHolder.tv_good_name = convertView.findViewById(R.id.tv_good_name);
            inquiryViewHolder.tv_good_dose = convertView.findViewById(R.id.tv_good_dose);
            inquiryViewHolder.tv_good_unit = convertView.findViewById(R.id.tv_good_unit);
            inquiryViewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(inquiryViewHolder);
        } else {
            inquiryViewHolder = (InquiryViewHolder) convertView.getTag();
        }
        UserGoodBean userGoodBean = mUserGoodBeans.get(position);
        inquiryViewHolder.tv_good_name.setText(userGoodBean.getGoodName());
        inquiryViewHolder.tv_good_dose.setText(userGoodBean.getGoodDosage());
        inquiryViewHolder.tv_good_unit.setText(userGoodBean.getGoodUnit());

        if (isHasDelete) {
            inquiryViewHolder.btn_delete.setVisibility(View.VISIBLE);
        } else {
            inquiryViewHolder.btn_delete.setVisibility(View.GONE);
        }

        inquiryViewHolder.btn_delete.setOnClickListener(v -> {
            mUserGoodBeans.remove(userGoodBean);
            notifyDataSetChanged();
        });
        return convertView;
    }

    static class InquiryViewHolder {
        private TextView tv_good_name, tv_good_dose, tv_good_unit;
        private Button btn_delete;
    }

}
