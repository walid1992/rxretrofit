package com.walid.rxretrofitsample.network;

import com.walid.rxretrofit.HttpSubscriber;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofitsample.app.App;

import io.reactivex.Observable;

/**
 * Author   : walid
 * Data     : 2016-08-18  16:09
 * Describe :
 */
public class ApiUtils {

    public static <T> HttpSubscriber<T> toSubscribe(Observable<HttpResult<T>> observable, final IHttpCallback<T> listener) {
        return toSubscribe(observable, listener, true);
    }

    public static <T> HttpSubscriber<T> toSubscribe(Observable<HttpResult<T>> observable, final IHttpCallback<T> listener, boolean showToast) {
        return App.httpManager.toSubscribe(observable, App.instance, listener, showToast);
    }

}
