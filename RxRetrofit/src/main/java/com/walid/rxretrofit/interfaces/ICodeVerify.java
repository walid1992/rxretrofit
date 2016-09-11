package com.walid.rxretrofit.interfaces;

/**
 * Author   : walid
 * Data     : 2016-08-18  15:58
 * Describe : Code 校验
 */
public interface ICodeVerify {

    boolean checkValid(int code);

    String formatCodeMessage(int code, String message);

}
