package com.walid.rxretrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.walid.rxretrofit.exception.ApiException;
import com.walid.rxretrofit.interfaces.ICodeVerify;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofit.interfaces.IHttpCancelListener;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:59
 * Describe : rock
 */
public class HttpSubscriber<T> extends Subscriber<T> implements IHttpCancelListener {

    private final String TAG = "HttpSubscriber";

    private Context context;
    private IHttpCallback<T> httpCallback;
    private boolean showError;

    public HttpSubscriber(Context context, IHttpCallback<T> httpCallback) {
        this(context, httpCallback, true);
    }

    public HttpSubscriber(Context context, IHttpCallback<T> httpCallback, boolean showError) {
        this.context = context;
        this.httpCallback = httpCallback;
        this.showError = showError;
    }

    // 订阅开始时调用
    @Override
    public void onStart() {
    }

    // 加载成功
    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    // 对错误进行统一处理
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            callbackErr(ICodeVerify.SOCKET_TIMEOUT_EXCEPTION_CODE, "网络请求超时~");
        } else if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            callbackErr(apiException.getCode(), apiException.getMessage());
        } else if (e instanceof UnknownHostException) {
            callbackErr(ICodeVerify.UNKNOWN_HOST_EXCEPTION_CODE, "连接不到服务器~");
        } else {
            callbackErr(ICodeVerify.UNKNOWN_EXCEPTION_CODE, e.getMessage());
        }
    }

    private void callbackErr(int code, String message) {
        if (showError) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
        if (httpCallback != null) {
            httpCallback.onError(code, message);
        }
    }

    // 将onNext方法中的返回结果交给Activity或Fragment自己处理
    @Override
    public void onNext(T t) {
        if (httpCallback == null) {
            return;
        }
        httpCallback.onNext(t);
    }

    // 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
    @Override
    public void onCancel() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

}