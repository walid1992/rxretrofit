package com.walid.rxretrofit.exception;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:58
 * Describe : server 返回异常
 */
public class ServerResultException extends RuntimeException {

    private int code;

    public ServerResultException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
