package com.example.hello.myapplication.common.bean;

/**
 * Created by hello on 18/1/23.
 */

public class ByteContent {
    private byte header;
    private byte length;
    private byte taskId;
    private byte storeid;
    private byte pSize;
    private byte number;
    public byte[] data;
    private byte check;
    private byte end;


    public byte getHeader() {
        return header;
    }

    public void setHeader(byte header) {
        this.header = header;
    }

    public byte getLength() {
        return length;
    }

    public void setLength(byte length) {
        this.length = length;
    }

    public byte getTaskId() {
        return taskId;
    }

    public void setTaskId(byte taskId) {
        this.taskId = taskId;
    }

    public byte getStoreid() {
        return storeid;
    }

    public void setStoreid(byte storeid) {
        this.storeid = storeid;
    }

    public byte getpSize() {
        return pSize;
    }

    public void setpSize(byte pSize) {
        this.pSize = pSize;
    }

    public byte getNumber() {
        return number;
    }

    public void setNumber(byte number) {
        this.number = number;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte getCheck() {
        return check;
    }

    public void setCheck(byte check) {
        this.check = check;
    }

    public byte getEnd() {
        return end;
    }

    public void setEnd(byte end) {
        this.end = end;
    }
}
