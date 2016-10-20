package com.walid.rxretrofitsample.network.api.news.bean;

public class InsuranceVo {

    public int id;
    public String title;
    public String thumb;
    public String depositEntry;
    public String paymentMax;
    public String guaranteeScope;
    public String guaranteePeople;
    public int salesVolume;
    public String advertisement;
    public String chargeNotice;
    public String detailUrl;

    @Override
    public String toString() {
        return "InsuranceVo{" +
                "advertisement='" + advertisement + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", thumb='" + thumb + '\'' +
                ", depositEntry='" + depositEntry + '\'' +
                ", paymentMax='" + paymentMax + '\'' +
                ", guaranteeScope='" + guaranteeScope + '\'' +
                ", guaranteePeople='" + guaranteePeople + '\'' +
                ", salesVolume=" + salesVolume +
                ", chargeNotice='" + chargeNotice + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                '}';
    }
}
