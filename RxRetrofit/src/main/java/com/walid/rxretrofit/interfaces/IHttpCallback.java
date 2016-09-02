package com.walid.rxretrofit.interfaces;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:59
 * Describe : rock
 */
public interface IHttpCallback<T> {
    void onNext(T t);

    void onError(int code, String message);
}
