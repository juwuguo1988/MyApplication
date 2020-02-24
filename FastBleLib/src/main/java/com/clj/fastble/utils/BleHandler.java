package com.clj.fastble.utils;

import com.clj.fastble.bluetooth.BleBluetooth;
import com.clj.fastble.bluetooth.BleConnector;
import com.clj.fastble.callback.BleWriteCallback;

/**
 * Created by hello on 18/8/3.
 */

public class BleHandler {

    private BleBluetooth bleBluetooth;
    private byte[] data;
    private String uuid_service;
    private String uuid_write;
    private int mOperatorType;
    private boolean mEffectiveData;
    private BleWriteCallback mCallback;

    public BleHandler(BleBluetooth bleBluetooth, byte[] data, String uuid_service, String uuid_write, int mOperatorType, BleWriteCallback callback, boolean effectiveData) {
        this.bleBluetooth = bleBluetooth;
        this.data = data;
        this.uuid_service = uuid_service;
        this.uuid_write = uuid_write;
        this.mOperatorType = mOperatorType;
        this.mCallback = callback;
        this.mEffectiveData = effectiveData;
    }

    public BleBluetooth getBleBluetooth() {
        return bleBluetooth;
    }

    public void setBleBluetooth(BleBluetooth bleBluetooth) {
        this.bleBluetooth = bleBluetooth;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getUuid_service() {
        return uuid_service;
    }

    public void setUuid_service(String uuid_service) {
        this.uuid_service = uuid_service;
    }

    public String getUuid_write() {
        return uuid_write;
    }

    public void setUuid_write(String uuid_write) {
        this.uuid_write = uuid_write;
    }

    public int getmOperatorType() {
        return mOperatorType;
    }

    public void setmOperatorType(int mOperatorType) {
        this.mOperatorType = mOperatorType;
    }

    public BleConnector getBleConnector() {
        return bleBluetooth.newBleConnector().withUUIDString(uuid_service, uuid_write);
    }

    public boolean ismEffectiveData() {
        return mEffectiveData;
    }

    public BleWriteCallback getmCallback() {
        return mCallback;
    }
}
