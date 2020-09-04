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
        return App.httpManager.subscribe(observable, listener);
    }

    public static <T> HttpSubscriber<T> toSubscribeWithToast(Observable<HttpResult<T>> observable, final IHttpCallback<T> listener) {
        return App.httpManager.toSubscribeWithToast(observable, listener, App.instance);
    }

}
