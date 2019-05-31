package com.d2c.order.third.payment.alipay.core.app;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public static String partner = "2088801519149110";
    // 商户的私钥
    public static String private_key = "MIICXwIBAAKBgQDxNHqRrOWX50Scgcv14dDn7UoLXBv6No3t3BwvVxSHFohRY8J5EV4LMVgYLEBz6NXkrwF/RdZJxjLD0KLYvM6NQF3mS32umLO9HThHi/ZVMGJvXpvnjJUOSDdF+tqVpzTMxHDje0f9R6d6AGMI0SW/u07tEMoWWR7zferEwi+YnQIDAQABAoGBAJs3Fwy5QwGOTCOejt6KUwF8PCK1QjewdYK0GtsH4WjQwiYF2TZJS8hOF49uooc0NLg1OfRpv2y4AyDpGHH6hbs19TX6IUKlvRPL2LMbsJZmgemcwRzbFtApmCChVZwVBDjifQ3rJnNYTDkDkte0cti18t698TL2WHmilOd8jqiNAkEA+ugwFYE2VinwjBeutr0RJ+meBAbdpfw11A8FTyG6UE+l/ygH8PiLZwtKYmBHauPzetOLpDcdcU9dRrn7T31ucwJBAPYZ4CXnm5OYhbD5j0H98/Q4QQk5eLe+MCXfrO/K0OtK1Bb43I8PozlOD2jowtL9YqY1oR7HQXvnW6GkGL8siK8CQQCRjg8O+qWN+MiOTNLTOf1w3QsJJP6CYzzK52faDyPpoTsYOZ3ZwiYEwj0FsvrbXKAL+hsBSePvru3asNvEsmxXAkEA1Aq5cY9cIdCxqCluQIrh7hOmHZ+SHqbU710IsVdLqN/BW8Wyq+TqWw6iAl9Cte4yWEku+Mjv6CUQc3ZjCB0D6wJBAJwX4HyGvuzssfU7zSFUy6RhFNLa/MmnS+OTwHp6e/xly0DrKFhL4HHZVdDnvxKCMZUspyPEyCc3yGBMNn1YDo0=";
    // 支付宝的公钥，无需修改该值
    public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    // ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
    //pay@d2cmall.com 新账号
    public static String partner2 = "2088911603039004";
    public static String private_key2 = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANq7q86K2n48CSBOCfkJ6Tzin1ZjKTx6cMgX5wu563LqHxf13AZTwP5f2dAGHJGhIOtMpDl1M2SfnjufDNKGY3CN39esWqwNLeo1t0CoqAfy7szyAK1IvvNcW+B8SyjQJel9WoZYpTYBrzIoCl+9MFTPMEuNyU1oQZs+idk8Jjx/AgMBAAECgYBJCfno0jz7ghiaDPUPj9uuVQ5XzmzqoQggCLtCEycoDD2txo9eHYGIb4FQEXpgtqlDaSg9d4FdzF8OEaI6el2hjcAPQmdx/JVbVA780f8U82DYK2D1EsSqUg1f3Zc1H1YYKDv6Mu48fFu9C6lPHuh0JaFWBqNd74Z8aCTXn1DD4QJBAPkb3bLUNINRQKDSUbf+XWybuYncn2hlhKD9g9eIVL+gNrW15cMrFxXYhQaBAeDI7qWfWFsAyASgW46TBjQs+5MCQQDgyLDPSX3+2Z85V3hnbCWPMavmX3mYXNXsdQid8LMjz07x5RQUa0tj55lAWFe6iAY2yoImFhiESS/LRrOJxUblAkAVuBpCR0I02Na7rtq1IhZfK8ynenbjkUZZCwF9v0M0WfGrMirQM2eV3gTrJkBVqu8zvYrklFLcJGcXLTZzQiDFAkBdOH2zCYx6I3BYDIF/iqcs8nGS0aeQ23dQX9zZ+1efkzrvMIyaR0D1xreUqs5/KihBksxU1TcHrX5ntAODMFBNAkEA7fMgZzREQ+LXZbnxhCOSvHM1Ug8xAMeglssfb0XrEdYq6SO6VqKAUw/HoZkFlKkc8KhV4SUq0Lu9OduZNMUsjg==";
    public static String ali_public_key2 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    // 调试用，创建TXT日志文件夹路径
    public static String log_path = "D:\\";
    // 字符编码格式 目前支持 gbk 或 utf-8
    public static String input_charset = "utf-8";
    // 签名方式 不需修改
    public static String sign_type = "RSA";

}
