package com.d2c.member.third.qiniu;

public final class Config {

    public static final String VERSION = "2.1.0";
    public static final String APIHTTPScheme = "http://";
    public static final String APIUserAgent = String.format("pili-sdk-java/%s %s %s/%s", VERSION,
            System.getProperty("java.version"), System.getProperty("os.name"), System.getProperty("os.arch"));
    public static final String APIHost = "pili.qiniuapi.com";
    public static final String RTCAPIHost = "rtc.qiniuapi.com";
    public static final String accessKey = "Q1sVShGMxx-VSrVv9BWVIyNbFDwhtOTsDmltazMy";
    public static final String secretKey = "tp8wpLgQ6i2iiBXw6tSu9u6z488Fqa-eCVEfGtd5";
    public static final String hubName = "d2cmall";
    public static final String pushUrl = "pili-publish.appserver.d2cmall.com";
    public static final String rtmpUrl = "pili-live-rtmp.appserver.d2cmall.com";
    public static final String hlsUrl = "pili-live-hls.appserver.d2cmall.com";
    public static final String hdlUrl = "pili-live-hdl.appserver.d2cmall.com";
    public static final String picUrl = "pili-live-snapshot.appserver.d2cmall.com";
    public static final String replayUrl = "http://pili-vod.appserver.d2cmall.com/";

}
