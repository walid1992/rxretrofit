package com.walid.rxretrofit.interfaces;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:58
 * Describe : Code 校验
 */
public interface ICodeVerify {

    // 超时异常
    int SOCKET_TIMEOUT_EXCEPTION_CODE = -100;
    // 没有主机异常
    int UNKNOWN_HOST_EXCEPTION_CODE = -101;
    // 未知异常
    int UNKNOWN_EXCEPTION_CODE = -102;

    boolean checkValid(int code);

    String formatCodeMessage(int code, String message);

}
