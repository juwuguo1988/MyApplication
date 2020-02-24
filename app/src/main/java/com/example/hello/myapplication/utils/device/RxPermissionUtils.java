package com.example.hello.myapplication.utils.device;

/**
 * Created by juwuguo on 19/6/14.
 */

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.hello.myapplication.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * 权限申请
 */

public class RxPermissionUtils {
    private static Context mContext;
    private static RxPermissions mRxPermissions;

    public static RxPermissionUtils getInstance(Context context) {
        mContext = context;
        mRxPermissions = new RxPermissions((Activity) context);
        return new RxPermissionUtils();
    }


    public void getPowerStatus(PowerClickCallBack powerClickCallBack, String... permissions) {
        mRxPermissions.request(permissions)
                .subscribe(isPowerOn -> {
                    if (isPowerOn) {
                        powerClickCallBack.onClick();
                    } else {
                        Toast.makeText(mContext, mContext.getString(R.string.exception_power_on_tip), Toast.LENGTH_LONG);
                    }
                });
    }


    public interface PowerClickCallBack {
        void onClick();
    }


}
