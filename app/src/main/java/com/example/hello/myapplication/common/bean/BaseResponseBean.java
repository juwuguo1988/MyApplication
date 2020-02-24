package com.example.hello.myapplication.common.bean;

/**
 * Created by fengzhenye on 16/10/20.
 * 所有网络请求返回结果的bean的基类
 */
public class BaseResponseBean<T> {

    /**
     * status分为:
     * success  请求响应成功且业务逻辑成功，可解析data字段的具体内容并处理
     *
     * fail     请求响应成功但业务逻辑不符合，比如当用户直接登录时返回："用户未注册"，可解析data字段的具体内容解释出什么问题了
     * @see FailResponseBean
     *
     * error    请求响应成功，这时候可以解析message和code字段，从而知道出了什么错误
     * @see ErrorResponseBean
     */
    public String status;  //注意当status为success和fail时，都是在请求响应成功的回调中
    public T data;

    public boolean isSuccess() {
        return "success".equals(status);
    }
}
