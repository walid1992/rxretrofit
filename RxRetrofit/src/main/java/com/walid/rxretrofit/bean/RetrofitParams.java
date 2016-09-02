package com.walid.rxretrofit.bean;

import java.util.ArrayList;

import okhttp3.Interceptor;

/**
 * Author   : walid
 * Data     : 2016-08-18  16:00
 * Describe : rock
 */
public class RetrofitParams {

    private int connectTimeoutSeconds;
    private int readTimeoutSeconds;
    private int writeTimeoutSeconds;
    private ArrayList<Interceptor> interceptors;

    public RetrofitParams() {
    }

    public int getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    public RetrofitParams setConnectTimeoutSeconds(int connectTimeoutSeconds) {
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        return this;
    }

    public int getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public RetrofitParams setReadTimeoutSeconds(int readTimeoutSeconds) {
        this.readTimeoutSeconds = readTimeoutSeconds;
        return this;
    }

    public int getWriteTimeoutSeconds() {
        return writeTimeoutSeconds;
    }

    public RetrofitParams setWriteTimeoutSeconds(int writeTimeoutSeconds) {
        this.writeTimeoutSeconds = writeTimeoutSeconds;
        return this;
    }

    public ArrayList<Interceptor> getInterceptors() {
        return interceptors;
    }

    public RetrofitParams setInterceptors(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

}
