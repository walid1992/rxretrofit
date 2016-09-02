package com.walid.rxretrofit.interfaces;

/**
 * Author   : walid
 * Data     : 2016-08-18  16:00
 * Describe : rock
 */
public abstract class SimpleHttpCallback<T> implements IHttpCallback<T> {
    @Override
    public void onError(int code, String message) {

    }
}
