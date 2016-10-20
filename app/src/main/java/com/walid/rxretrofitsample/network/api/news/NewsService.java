package com.walid.rxretrofitsample.network.api.news;

import com.walid.rxretrofit.HttpManager;
import com.walid.rxretrofit.interfaces.IHttpCallback;
import com.walid.rxretrofitsample.network.SeaApiUtils;

/**
 * Author   : walid
 * Data     : 2016-08-29  14:47
 * Describe :
 */

public class NewsService {

    private static final INewsApi NEWS_API = HttpManager.getInstance().getApiService(INewsApi.class);

    public static void latest(final IHttpCallback<Object> listener) {
        SeaApiUtils.toSubscribe(NEWS_API.latest("ANDROID"), listener);
    }

}
