package com.walid.rxretrofitsample.network;

import android.support.annotation.IntDef;

import com.walid.rxretrofit.interfaces.ICodeVerify;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Author   : roy
 * Data     : 2016-09-01  18:53
 * Describe :
 */
public class CodeVerify implements ICodeVerify {

    @IntDef({Code.SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    @interface Code {
        int SUCCESS = 0;
    }

    @Override
    public boolean checkValid(int code) {
        return code == Code.SUCCESS;
    }

    @Override
    public String formatCodeMessage(int code, String message) {
        return message;
    }

}