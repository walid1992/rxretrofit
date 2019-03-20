package com.walid.rxretrofit.bean;

import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Dns;
import okhttp3.Interceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;

/**
 * Author   : walid
 * Data     : 2016-08-18  16:00
 * Describe : 配置参数
 */
public class RetrofitParams {

    private int connectTimeoutSeconds;
    private int readTimeoutSeconds;
    private int writeTimeoutSeconds;
    private ArrayList<Interceptor> interceptors;
    private ArrayList<Interceptor> networkInterceptors;
    private Converter.Factory converterFactory;
    private CallAdapter.Factory callAdapterFactor;
    private SSLSocketFactory sslSocketFactory;
    private X509TrustManager x509TrustManager;
    private HostnameVerifier hostnameVerifier;
    private boolean debug;
    private Dns dns;

    public RetrofitParams() {
    }

    public int getConnectTimeoutSeconds() {
        return connectTimeoutSeconds;
    }

    public RetrofitParams setConnectTimeoutSeconds(int connectTimeoutSeconds) {
        this.connectTimeoutSeconds = connectTimeoutSeconds;
        return this;
    }

    public int getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public RetrofitParams setReadTimeoutSeconds(int readTimeoutSeconds) {
        this.readTimeoutSeconds = readTimeoutSeconds;
        return this;
    }

    public int getWriteTimeoutSeconds() {
        return writeTimeoutSeconds;
    }

    public RetrofitParams setWriteTimeoutSeconds(int writeTimeoutSeconds) {
        this.writeTimeoutSeconds = writeTimeoutSeconds;
        return this;
    }

    public ArrayList<Interceptor> getInterceptors() {
        return interceptors;
    }

    public RetrofitParams setInterceptors(ArrayList<Interceptor> interceptors) {
        this.interceptors = interceptors;
        return this;
    }

    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    public RetrofitParams setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
        return this;
    }

    public ArrayList<Interceptor> getNetworkInterceptors() {
        return networkInterceptors;
    }

    public RetrofitParams setNetworkInterceptors(ArrayList<Interceptor> networkInterceptors) {
        this.networkInterceptors = networkInterceptors;
        return this;
    }

    public CallAdapter.Factory getCallAdapterFactor() {
        return callAdapterFactor;
    }

    public RetrofitParams setCallAdapterFactor(CallAdapter.Factory callAdapterFactor) {
        this.callAdapterFactor = callAdapterFactor;
        return this;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    public X509TrustManager getX509TrustManager() {
        return x509TrustManager;
    }

    public RetrofitParams sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.sslSocketFactory = sslSocketFactory;
        return this;
    }

    public RetrofitParams sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager x509TrustManager) {
        this.sslSocketFactory = sslSocketFactory;
        this.x509TrustManager = x509TrustManager;
        return this;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hostnameVerifier;
    }

    public RetrofitParams setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    public Dns getDns() {
        return dns;
    }

    public RetrofitParams setDns(Dns dns) {
        this.dns = dns;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
