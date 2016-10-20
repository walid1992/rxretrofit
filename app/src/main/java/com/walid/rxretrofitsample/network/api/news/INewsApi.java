package com.walid.rxretrofitsample.network.api.news;


import com.walid.rxretrofitsample.network.HttpResult;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Author   : walid
 * Data     : 2016-08-29  14:47
 * Describe :
 */

interface INewsApi {

    // news
    @GET("/4/news/latest")
    Observable<HttpResult<Object>> latest(@Query("platform") String platform);

}
