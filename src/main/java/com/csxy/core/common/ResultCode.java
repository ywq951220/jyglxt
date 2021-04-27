package com.csxy.core.common;

public enum ResultCode {
    SUCCESS(200),//成功
    FAIL(400),//失败
    UNAUTHORIZED(401),//未认证（签名错误）
    UNREALTIME(403),//未实名认证
    ILLEGAL_PARAM_EMPRY(10),//非法的参数值 参数值为空
    INTERNAL_SERVER_ERROR(500);//服务器内部错误


    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
