package com.walid.rxretrofit;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.walid.rxretrofit.bean.RetrofitParams;
import com.walid.rxretrofit.exception.ServerResultException;
import com.walid.rxretrofit.interfaces.ICodeVerify;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofit.interfaces.IHttpResult;
import com.walid.rxretrofit.utils.RxRetrogitLog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:58
 * Describe : http 管理类
 */
public class HttpManager {

    private Retrofit retrofit;
    private ICodeVerify codeVerify;

    private HttpManager() {
    }

    public static HttpManager getInstance() {
        return HttpManager.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        static HttpManager instance = new HttpManager();
    }

    public void create(String baseUrl, ICodeVerify codeVerify, RetrofitParams params) {
        this.codeVerify = codeVerify;
        Converter.Factory converterFactory = params.getConverterFactory();
        CallAdapter.Factory callAdapterFactory = params.getCallAdapterFactor();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory != null ? converterFactory : GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(callAdapterFactory != null ? callAdapterFactory : RxJava2CallAdapterFactory.create())
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

        // Log信息拦截器
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

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
        Observable<T> observableNew = observable.map(new Function<Result, T>() {
            @Override
            public T apply(Result result) throws Exception {
                if (result == null) {
                    throw new IllegalStateException("数据为空~");
                }
                RxRetrogitLog.d(result.toString());
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
