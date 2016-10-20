package com.walid.rxretrofitsample.network.interceptor;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author   : roy
 * Data     : 2016-08-18  15:59
 * Describe :
 */
public class ParamsInterceptor implements Interceptor {

    private final static AtomicLong sCIDGenerator = new AtomicLong(System.currentTimeMillis());

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//        builder.addHeader("Authorization", authorization);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }

}
