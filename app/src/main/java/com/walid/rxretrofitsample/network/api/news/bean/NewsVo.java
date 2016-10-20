package com.walid.rxretrofitsample.network.api.news.bean;

import com.zqxq.hulk.bean.server.annotation.ApiModelProperty;

/**
 * Author   : walid
 * Data     : 2016-10-10  13:44
 * Describe :
 */

public class NewsVo {

    @ApiModelProperty(value = "是否为最新版本")
    private boolean latest;

    @ApiModelProperty(value = "客户端当前版本")
    private String clientVersion;

    @ApiModelProperty(value = "最新版本")
    private String latestVersion;

    @ApiModelProperty(value = "主要")
    private String major;

    @ApiModelProperty(value = "次要的")
    private String minor;

    @ApiModelProperty(value = "修正")
    private String revision;

    @ApiModelProperty(value = "是否强制更新")
    private boolean mandatory;

    @ApiModelProperty(value = "发布时间")
    private long releaseDate;

    @ApiModelProperty(value = "android下载版本")
    private String androidUrl;

    @ApiModelProperty(value = "更新日志")
    private String changeLog;

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }
}
