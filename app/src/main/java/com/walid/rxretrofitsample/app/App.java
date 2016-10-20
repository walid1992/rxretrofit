package com.walid.rxretrofitsample.app;

import android.app.Application;

import com.google.gson.GsonBuilder;
import com.walid.rxretrofit.HttpManager;
import com.walid.rxretrofit.bean.RetrofitParams;
import com.walid.rxretrofitsample.network.ApiConstants;
import com.walid.rxretrofitsample.network.SeaCodeVerify;
import com.walid.rxretrofitsample.network.interceptor.ParamsInterceptor;
import com.walid.rxretrofit.utils.RxRetrogitLog;

import java.util.ArrayList;

import okhttp3.Interceptor;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author   : walid
 * Data     : 2016-09-23  13:43
 * Describe :
 */
public class App extends Application {

    public static Application instalce;

    @Override
    public void onCreate() {
        super.onCreate();
        instalce = this;
        RxRetrogitLog.DEBUG = true;
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new ParamsInterceptor());
        RetrofitParams params = new RetrofitParams();
        // data 转换器
        GsonBuilder builder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss");
        params.setConverterFactory(GsonConverterFactory.create(builder.create()));
        params.setConnectTimeoutSeconds(10);
        params.setReadTimeoutSeconds(10);
        params.setWriteTimeoutSeconds(10);
        params.setInterceptors(interceptors);
        HttpManager.getInstance().create(ApiConstants.URL, new SeaCodeVerify(), params);
    }

}
