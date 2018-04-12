package com.walid.rxretrofit;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.walid.rxretrofit.bean.RetrofitParams;
import com.walid.rxretrofit.exception.ServerResultException;
import com.walid.rxretrofit.interfaces.ICodeVerify;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofit.interfaces.IHttpResult;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

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
    private RetrofitParams params;

    HttpManager(String baseUrl, ICodeVerify codeVerify, RetrofitParams params) {
        Converter.Factory converterFactory = params.getConverterFactory();
        CallAdapter.Factory callAdapterFactory = params.getCallAdapterFactor();
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory != null ? converterFactory : GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(callAdapterFactory != null ? callAdapterFactory : RxJava2CallAdapterFactory.create())
                .client(createClient(params))
                .build();
        this.codeVerify = codeVerify;
        this.params = params;
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
        if (params.isDebug()) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        ArrayList<Interceptor> interceptors = params.getInterceptors();
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        SSLSocketFactory sslSocketFactory = params.getSslSocketFactory();
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(params.getSslSocketFactory());
        }

        HostnameVerifier hostnameVerifier = params.getHostnameVerifier();
        if (hostnameVerifier != null) {
            builder.hostnameVerifier(params.getHostnameVerifier());
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

    private <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(Observable<Result> observable, HttpSubscriber<T> tHttpSubscriber) {
        Observable<IHttpResult<T>> observableNew = observable.map(new Function<Result, IHttpResult<T>>() {
            @Override
            public IHttpResult<T> apply(Result result) throws Exception {
                if (result == null) {
                    throw new IllegalStateException("数据为空~");
                }
                int code = result.getCode();
                if (!codeVerify.checkValid(result.getCode())) {
                    throw new ServerResultException(code, codeVerify.formatCodeMessage(code, result.getMsg()));
                }
                return result;
            }
        });
        observableNew.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tHttpSubscriber);
        return tHttpSubscriber;
    }

    public RetrofitParams getParams() {
        return params;
    }
}
