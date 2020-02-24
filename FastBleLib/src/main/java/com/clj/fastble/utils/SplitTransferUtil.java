package com.clj.fastble.utils;


import android.util.Log;

import com.clj.fastble.bluetooth.BleBluetooth;
import com.clj.fastble.callback.BleWriteCallback;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.exception.OtherException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SplitTransferUtil {

    private static final int DEFAULT_WRITE_DATA_SPLIT_COUNT = 13;

    public static void splitWrite(BleBluetooth bleBluetooth,
                                  String uuid_service,
                                  String uuid_write,
                                  byte[] data,
                                  BleWriteCallback callback) {
        splitWrite(bleBluetooth, uuid_service, uuid_write, data, DEFAULT_WRITE_DATA_SPLIT_COUNT, callback);
    }

    public static void splitWrite(BleBluetooth bleBluetooth,
                                  String uuid_service,
                                  String uuid_write,
                                  byte[] data,
                                  int subSize,
                                  BleWriteCallback callback) {
        if (data == null) {
            throw new IllegalArgumentException("data is Null!");
        }
        if (subSize < 1) {
            throw new IllegalArgumentException("split count should higher than 0!");
        }
        write(bleBluetooth, uuid_service, uuid_write, callback, splitByte(data, subSize));
    }

    public static void write(final BleBluetooth bleBluetooth,
                             final String uuid_service,
                             final String uuid_write,
                             final BleWriteCallback callback,
                             final Queue<byte[]> dataInfoQueue) {
        if (dataInfoQueue.peek() == null) {
            if (callback != null) {
                callback.onWriteSuccess();
            }
        } else {
            final byte[] data = dataInfoQueue.poll();
            StringBuffer stringBuffer = new StringBuffer();
            for (byte item : data) {
                stringBuffer.append(String.format("%x ", item));
            }
            Log.d("AppGlobal_MqttTag", stringBuffer.toString());

            bleBluetooth.newBleConnector()
                    .withUUIDString(uuid_service, uuid_write)
                    .writeCharacteristic(data,
                            new BleWriteCallback() {
                                @Override
                                public void onWriteSuccess() {
                                    BleLog.d(HexUtil.formatHexString(data, true) + " been written!");
                                    try {
                                        Thread.currentThread().sleep(25);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    write(bleBluetooth, uuid_service, uuid_write, callback, dataInfoQueue);
                                }

                                @Override
                                public void onWriteFailure(BleException exception) {
                                    if (callback != null) {
                                        callback.onWriteFailure(new OtherException("exception occur while writing: " + exception.getDescription()));
                                    }
                                }
                            },
                            uuid_write);
        }
    }

    private static Queue<byte[]> splitByte(byte[] data, int subSize) {
        if (subSize > 13) {
            BleLog.w("Be careful: split count beyond 20! Ensure MTU higher than 23!");
        }
        int pSize = data.length % subSize == 0 ? data.length / subSize : data.length / subSize + 1;
        Queue<byte[]> byteQueue = new LinkedList<>();
        if (data != null) {
            int index = 0;
            int postion = 0;
            int taskId = randomNumber();
            do {
                byte[] rawData = new byte[data.length - index];
                byte[] newData;
                byte[] request;
                System.arraycopy(data, index, rawData, 0, data.length - index);
                if (rawData.length <= subSize) {
                    newData = new byte[rawData.length];
                    System.arraycopy(rawData, 0, newData, 0, rawData.length);
                    index += rawData.length;
                } else {
                    newData = new byte[subSize];
                    System.arraycopy(data, index, newData, 0, subSize);
                    index += subSize;
                }
                request = createRequestByte(newData, pSize, postion, taskId);
                postion = postion + 1;
                byteQueue.offer(request);
            } while (index < data.length);
        }
        return byteQueue;
    }


    /**
     * 构造包头，包尾，以及gen
     */

    private static byte[] createRequestByte(byte[] subAryItem, int pSize, int position, int taskId) {
        int byteSize = subAryItem.length + 7;
        byte[] request = new byte[byteSize];
        request[0] = (byte) 0xAA;
        request[1] = (byte) (subAryItem.length + 5);
        request[2] = (byte) taskId;
        request[3] = (byte) pSize;
        request[4] = (byte) position;
        for (int j = 0; j < subAryItem.length; j++) {
            request[5 + j] = subAryItem[j];
        }
        request[byteSize - 2] = checkGen(subAryItem, request[0], request[1], request[2], request[3], request[4]);
        request[byteSize - 1] = (byte) 0x55;
        return request;
    }

    private static byte checkGen(byte[] subAryItem, byte header, byte length, byte taskId, byte pSize, byte number) {
        int checkNumber = header + length + taskId + pSize + number;
        for (byte item : subAryItem) {
            checkNumber += item;
        }
        return (byte) (checkNumber & 0xff);
    }

    private final static int randomNumber() {
        Random random = new Random();
        return (random.nextInt(255) + 1);
    }


}
