package com.example.hello.myapplication.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juwuguo on 2020-03-11.
 */
public class UserGoodBean implements Parcelable {
    private String goodName;
    private String goodDosage;
    private String goodUnit;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodDosage() {
        return goodDosage;
    }

    public void setGoodDosage(String goodDosage) {
        this.goodDosage = goodDosage;
    }

    public String getGoodUnit() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit = goodUnit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.goodName);
        dest.writeString(this.goodDosage);
        dest.writeString(this.goodUnit);
    }

    public UserGoodBean() {
    }

    protected UserGoodBean(Parcel in) {
        this.goodName = in.readString();
        this.goodDosage = in.readString();
        this.goodUnit = in.readString();
    }

    public static final Creator<UserGoodBean> CREATOR = new Creator<UserGoodBean>() {
        @Override
        public UserGoodBean createFromParcel(Parcel source) {
            return new UserGoodBean(source);
        }

        @Override
        public UserGoodBean[] newArray(int size) {
            return new UserGoodBean[size];
        }
    };
}
