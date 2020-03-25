package com.example.hello.myapplication.common;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.clj.fastble.BleManager;

public class XZLApplication extends Application {
    private static Context mContext;
    private int mBtMaxConnectNum = 7;
    private int mBtOperatorTimeOut = 5000;
    public static int verticalScreenWidth;      //竖屏时屏幕的宽度
    public static int verticalScreenHeight;     //竖屏时屏幕的高度
    public static int horizontalScreenWidth;    //横屏时屏幕的宽度
    public static int horizontalScreenHeight;   //横屏时屏幕的高度

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化上下文
        mContext = getApplicationContext();
        initFastBleConfig();
        getScreenSize();
    }

    public static Context getContext() {
        return mContext;
    }


    private void initFastBleConfig() {
        BleManager.getInstance().init(this);
        BleManager.getInstance()
                .enableLog(true)//设置打印日志
                .setMaxConnectCount(mBtMaxConnectNum)//最大链接数
                .setOperateTimeout(mBtOperatorTimeOut);//操作超时时间
    }


    /**
     * 获取屏幕分辨率方法
     */
    private void getScreenSize() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        verticalScreenWidth = dm.widthPixels;
        verticalScreenHeight = dm.heightPixels;
        horizontalScreenWidth = verticalScreenHeight;
        horizontalScreenHeight = verticalScreenWidth;
    }

}
