package com.example.hello.myapplication.utils.ble;

import android.util.Log;

import com.example.hello.myapplication.common.XZLApplication;
import com.leto.btsdk.BtSdkMain;
import com.leto.btsdk.BtTaskType;
import com.leto.btsdk.IBtSdkMainListener;
import com.leto.btsdk.LtBleBusiData;
import com.leto.btsdk.Utils;


/**
 * 蓝牙sdk接口封装
 */
public class BtSdkManager implements IBtSdkMainListener {

    private BtSdkManager() {
        String hzkFileName = Utils.copyAssetFileToCacheAndGetPath(XZLApplication.getContext(), "GBK24cfx.BIN");
        BtSdkMain.getInstance().init(XZLApplication.getContext(), this, hzkFileName);
        BtSdkMain.getInstance().onResume();
    }

    private static class BtSdkHolder {
        private static final BtSdkManager instance = new BtSdkManager();
    }

    public static BtSdkManager getInstance() {
        return BtSdkHolder.instance;
    }

    public interface ConnectCallback {
        void connectRet(int retCode);
    }

    public interface SendDataCallback {
        void onReceiveData(LtBleBusiData data);

        void onFailedCallBack();

        void onTimeOutCallBack();
    }

    private ConnectCallback connectCallback;
    private SendDataCallback sendDataCallback;

    /**
     * 设置连接结果回调
     *
     * @param connectCallback 连接结果回调
     */
    public void setConnectCallback(ConnectCallback connectCallback) {
        this.connectCallback = connectCallback;
    }

    /**
     * 设置发送数据结果回调
     *
     * @param sendDataCallback callback
     */
    public void setSendDataCallback(SendDataCallback sendDataCallback) {
        this.sendDataCallback = sendDataCallback;
    }

    /**
     * 设备是否连接
     *
     * @return true-已连接, false-未连接
     */
    public boolean isDeviceConnected(String macAddress) {
        return BtSdkMain.getInstance().isDevConnected(macAddress);
    }

    /**
     * 连接药盒
     *
     * @param macAddress 药盒macAddress
     * @return BtSdkManager
     */
    public BtSdkManager connectDevice(String macAddress) {
        BtSdkMain.getInstance().connectDevice(macAddress);//"3C:A5:08:0A:40:D7"
        return this;
    }

    public BtSdkManager sendData(String jsonData, BtTaskType type, int storeId) {
        Log.d("BtBoxImpl", "BtBoxImpl send json to box >> " + jsonData);
        BtSdkMain.getInstance().sendData(jsonData.getBytes(), type, storeId);
        return this;
    }

    public BtSdkManager sendData(byte[] data, BtTaskType type, int storeId) {
        BtSdkMain.getInstance().sendData(data, type, storeId);
        return this;
    }

    @Override
    public void onConnect(int retCode) {
        if (null != connectCallback) {
            connectCallback.connectRet(retCode);
        }
    }

    @Override
    public void onRecvData(LtBleBusiData ltBleBusiData) {
        if (null != sendDataCallback) {
            sendDataCallback.onReceiveData(ltBleBusiData);
        }
    }

    @Override
    public void onSendProgress(int allPkgCnt, int sentPkgCnt) {
        Log.d("BtBoxImpl", "===allPkgCnt ==" + allPkgCnt + "===sentPkgCnt ==" + sentPkgCnt);
    }

    @Override
    public void onSendDataResult(int retCode, int dataLen, int taskId, int storeId) {
        //retCode 0成功 1失败 2超时
        Log.d("BtBoxImpl", "===onSendDataResult=======" + retCode);
        if (null != sendDataCallback) {
            if (retCode == 1) {
                sendDataCallback.onFailedCallBack();
            } else if (retCode == 2) {
                sendDataCallback.onTimeOutCallBack();
            }
        }
    }

    @Override
    public void onPrintLogToJava(String s) {

    }
}
