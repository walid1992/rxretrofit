package com.walid.rxretrofit.interfaces;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:59
 * Describe : IHttpResult
 */
public interface IHttpResult<T> {

    int getCode();

    String getMsg();

    T getData();

    boolean success();

}

