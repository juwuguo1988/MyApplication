package com.example.hello.myapplication.utils.device;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * 设备管理工具类
 */
public class DeviceUtils {

    /**
     * 获取当前的手机号
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tManager.getLine1Number();
        return number;
    }

    /**
     * 获取当前设备号
     *
     * @param context
     * @return
     */
    public static String getPhoneIMEI(Context context) {
        //TODO 添加权限访问 android.permission.READ_PHONE_STATE
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (Exception e) {
            return versionCode;
        }
    }

    /**
     * 获取应用版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "Undefined";
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName;
            return versionName;
        } catch (Exception e) {
            return versionName;
        }
    }

    /**
     * 测试当前摄像头能否被使用
     */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null && canUse) {
            mCamera.release();
            mCamera = null;
        } else {
            canUse = false;
        }
        return canUse;
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    public static boolean checkGPSIsOpen(Context mContext) {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    private static Camera camera;

    /**
     * 打开相机
     *
     * @return
     */
    public static Camera openCamera() {
        camera = null;
        try {
            camera = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable
    }

    public static Camera getCamera() {
        return camera;
    }


    /**
     * 检查是否有闪光灯
     *
     * @return true：有，false：无
     */
    public static boolean hasFlash(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}
