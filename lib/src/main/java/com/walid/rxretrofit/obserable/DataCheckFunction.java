package com.walid.rxretrofit.obserable;

import com.walid.rxretrofit.exception.ServerResultException;
import com.walid.rxretrofit.interfaces.IHttpResult;

import io.reactivex.functions.Function;

/**
 * Author   : walid
 * Date     : 2020-08-12  13:05
 * Describe :
 */
public class DataCheckFunction<T, R extends IHttpResult<T>> implements Function<R, IHttpResult<T>> {

    public DataCheckFunction() {
    }

    @Override
    public R apply(R r) throws Exception {
        if (r == null) {
            throw new IllegalStateException("数据为空~");
        }
        int code = r.getCode();
        if (!r.success()) {
            throw new ServerResultException(code, r.getMsg());
        }
        return r;
    }

}
