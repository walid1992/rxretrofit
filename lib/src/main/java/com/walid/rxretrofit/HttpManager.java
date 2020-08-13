package com.walid.rxretrofit;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.walid.rxretrofit.bean.RetrofitParams;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofit.interfaces.IHttpResult;
import com.walid.rxretrofit.obserable.DataCheckFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:58
 * Describe : http 管理类
 */
public class HttpManager {

    private Retrofit retrofit;
    private RetrofitParams params;
    private String baseUrl;

    HttpManager(String baseUrl, RetrofitParams params) {
        Converter.Factory converterFactory = params.getConverterFactory();
        CallAdapter.Factory callAdapterFactory = params.getCallAdapterFactor();
        // TODO 修复 Use JsonReader.setLenient(true) to accept malformed JSON at line 1column 1 path $ 异常
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory != null ? converterFactory : LenientGsonConverterFactory.create(new GsonBuilder().create()))
//                .addConverterFactory(converterFactory != null ? converterFactory : GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(callAdapterFactory != null ? callAdapterFactory : RxJava2CallAdapterFactory.create())
                .client(createClient(params))
                .build();
        this.params = params;
        this.baseUrl = baseUrl;
    }

    private OkHttpClient createClient(RetrofitParams params) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        // 设置协议
        builder.protocols(Collections.singletonList(Protocol.HTTP_1_1));

        // 设置连接池数量
//        builder.connectionPool(new ConnectionPool(10, 10, TimeUnit.MINUTES));

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

        ArrayList<Interceptor> networkInterceptors = params.getNetworkInterceptors();
        if (networkInterceptors != null && networkInterceptors.size() > 0) {
            for (Interceptor interceptor : networkInterceptors) {
                builder.addNetworkInterceptor(interceptor);
            }
        }

        ArrayList<Interceptor> interceptors = params.getInterceptors();
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        SSLSocketFactory sslSocketFactory = params.getSslSocketFactory();
        if (sslSocketFactory != null) {
            if (params.getX509TrustManager() != null) {
                builder.sslSocketFactory(params.getSslSocketFactory(), params.getX509TrustManager());
            } else {
                builder.sslSocketFactory(params.getSslSocketFactory());
            }
        }

        HostnameVerifier hostnameVerifier = params.getHostnameVerifier();
        if (hostnameVerifier != null) {
            builder.hostnameVerifier(params.getHostnameVerifier());
        }

        Dns dns = params.getDns();
        if (dns != null) {
            builder.dns(dns);
        }

        return builder.build();
    }

    public <ApiType> ApiType getApiService(Class<ApiType> type) {
        return retrofit.create(type);
    }

    public <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(Observable<Result> observable, Context context, final IHttpCallback<T> listener) {
        return toSubscribe(observable, new HttpSubscriber<T>(context) {
            @Override
            public void success(T t) {
                listener.onNext(t);
            }

            @Override
            public void error(int code, String message) {
                listener.onError(code, message);
            }
        });
    }

    public <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(Observable<Result> observable, Context context, final IHttpCallback<T> listener, boolean isShowToast) {
        return toSubscribe(observable, new HttpSubscriber<T>(context) {
            @Override
            public void success(T t) {
                listener.onNext(t);
            }

            @Override
            public void error(int code, String message) {
                listener.onError(code, message);
            }
        });
    }

    private <T, Result extends IHttpResult<T>> HttpSubscriber<T> toSubscribe(final Observable<Result> observable, HttpSubscriber<T> tHttpSubscriber) {
        observable
                .map(new DataCheckFunction<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tHttpSubscriber);
        return tHttpSubscriber;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public RetrofitParams getParams() {
        return params;
    }
}
