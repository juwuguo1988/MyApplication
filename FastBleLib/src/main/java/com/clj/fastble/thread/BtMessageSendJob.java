package com.clj.fastble.thread;

import android.util.Log;

import com.clj.fastble.utils.BleHandler;

/**
 * Created by hello on 18/9/7.
 */

public class BtMessageSendJob implements Runnable {
    @Override
    public void run() {
        if (BtMessagePool.getQUEUE().peek() != null) {
            final BleHandler handler = BtMessagePool.getQUEUE().poll();
            if (handler.ismEffectiveData()) {
                handler.getBleConnector().writeCharacteristic2(handler.getData(), null, handler.getUuid_write());
                final byte[] data = handler.getData();
//                StringBuffer stringBuffer = new StringBuffer();
//                for (byte item : data) {
//                    stringBuffer.append(byteToHexString(item));
//                }
                Log.d("FastBleActivity", byteToHexString(data));
                if (BtMessagePool.getQUEUE().peek() == null) {
                    handler.getmCallback().onWriteSuccess();
                }
            }

        }
    }

    public static String byteToHexString(byte[] src) {
        StringBuffer stringBuffer = new StringBuffer();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuffer.append(0);
            }
            stringBuffer.append(hv + " ");
        }
        return stringBuffer.toString();
    }
}


