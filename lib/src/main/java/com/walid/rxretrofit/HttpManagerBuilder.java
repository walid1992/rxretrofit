package com.walid.rxretrofit;

import com.walid.rxretrofit.bean.RetrofitParams;
import com.walid.rxretrofit.interfaces.ICodeVerify;

/**
 * Author   : walid
 * Data     : 2018-04-11  15:58
 * Describe : HttpManager 工厂类
 */
public class HttpManagerBuilder {

    private ICodeVerify codeVerify;
    private RetrofitParams params;
    private String baseUrl;

    private HttpManagerBuilder() {
    }

    public static HttpManagerBuilder create() {
        return new HttpManagerBuilder();
    }

    public HttpManagerBuilder setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public HttpManagerBuilder setCodeVerify(ICodeVerify codeVerify) {
        this.codeVerify = codeVerify;
        return this;
    }

    public HttpManagerBuilder setParams(RetrofitParams params) {
        this.params = params;
        return this;
    }

    public HttpManager build() {
        return new HttpManager(baseUrl, codeVerify, params);
    }

}
