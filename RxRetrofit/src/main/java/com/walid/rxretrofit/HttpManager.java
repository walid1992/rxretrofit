package com.walid.rxretrofit;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.walid.rxretrofit.bean.RetrofitParams;
import com.walid.rxretrofit.exception.ServerResultException;
import com.walid.rxretrofit.interfaces.ICodeVerify;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofit.interfaces.IHttpResult;
import com.walid.rxretrofit.utils.Logger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;
import rx.schedulers.Schedulers;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:58
 * Describe : rock
 */
public class HttpManager {

    private Retrofit retrofit;
    private ICodeVerify codeVerify;

    private HttpManager() {
        RxJavaPlugins.getInstance().registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                Logger.e("RxJavaPlugins Error = " + e);
            }
        });
    }

    public static HttpManager getInstance() {
        return HttpManager.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        static HttpManager instance = new HttpManager();
    }

    public void create(String baseUrl, ICodeVerify codeVerify, RetrofitParams params) {
        this.codeVerify = codeVerify;
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createClient(params))
                .build();
    }

    private OkHttpClient createClient(RetrofitParams params) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置超时
        int connectTimeoutSeconds = params.getConnectTimeoutSeconds();
        if (connectTimeoutSeconds > 0) {
            builder.connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS);
        }

        int readTimeoutSeconds = params.getReadTimeoutSeconds();
        if (readTimeoutSeconds > 0) {
            builder.readTimeout(readTimeoutSeconds, TimeUnit.SECONDS);
        }

        int writeTimeoutSeconds = params.getWriteTimeoutSeconds();
        if (writeTimeoutSeconds > 0) {
            builder.writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS);
        }

        ArrayList<Interceptor> interceptors = params.getInterceptors();
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }

    public <ApiType> ApiType getApiService(Class<ApiType> type) {
        return retrofit.create(type);
    }

    public <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(Observable<Result> observable, Context context, IHttpCallback<T> listener) {
        return toSubscribe(observable, new HttpSubscriber<>(context, listener));
    }

    public <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(Observable<Result> observable, Context context, IHttpCallback<T> listener, boolean isShowToast) {
        return toSubscribe(observable, new HttpSubscriber<>(context, listener, isShowToast));
    }

    public <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(Observable<Result> observable, HttpSubscriber<T> httpSubscriber) {
        Observable<T> observableNew = observable.map(new Func1<Result, T>() {
            @Override
            public T call(Result result) {
                if (result == null) {
                    throw new IllegalStateException("数据为空~");
                }
                Logger.d(result.toString());
                int code = result.getCode();
                if (!codeVerify.checkValid(result.getCode())) {
                    throw new ServerResultException(code, codeVerify.formatCodeMessage(code, result.getMsg()));
                }
                return result.getData();
            }
        });
        observableNew.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(httpSubscriber);
        return httpSubscriber;
    }

}
