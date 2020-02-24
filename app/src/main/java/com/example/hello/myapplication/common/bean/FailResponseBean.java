package com.example.hello.myapplication.common.bean;

/**
 * Created by DysaniazzZ on 2016/11/9.
 */
public class FailResponseBean extends BaseResponseBean<FailResponseBean.FailResponseDetailBean> {

    public class FailResponseDetailBean {

        public String result;
        public String path;
        public int code;
        public String error;
        public String message;
        public long timestamp;
        public String status;
    }
}
