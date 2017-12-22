package com.walid.rxretrofitsample.network.api.news;


import com.walid.rxretrofitsample.network.HttpResult;
import com.walid.rxretrofitsample.network.api.news.bean.InsuranceVo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Author   : walid
 * Data     : 2016-08-29  14:47
 * Describe :
 */

public interface IInsApi {

    // news
    @GET("/api/ins/list")
    Observable<HttpResult<List<InsuranceVo>>> list(@Query("platform") String platform);

}
