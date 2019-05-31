/* *
 *功能：设置帐户有关信息及返回路径（基础配置页面）
 *版本：3.0
 *日期：2010-06-18
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.访问支付宝首页(www.alipay.com)，然后用您的签约支付宝账号登陆.
 *2.点击导航栏中的“商家服务”，即可查看

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 * */
package com.d2c.order.third.payment.alipay.core.pcwap;

public class AlipayConfig {

    // partner和key提取方法：登陆签约支付宝账户--->点击“商家服务”就可以看到
    public static String partnerId = "2088911603039004"; // 合作身份者ID
    public static String key = "u9la3qov9301v07ws1jf4r2ln4cggt17"; // 安全检验码
    public static String sellerEmail = "payment@d2cmall.com"; // 签约支付宝账号或卖家收款支付宝帐户
    // 商户的私钥
    // 如果签名方式设置为“0001”时，请设置该参数
    public static String private_key = "";
    // 支付宝的公钥
    // 如果签名方式设置为“0001”时，请设置该参数
    public static String ali_public_key = "";
    public static String antiphishing = "0";// 防钓鱼功能开关，'0'表示该功能关闭，'1'表示该功能开启。默认为关闭
    // 一旦开启，就无法关闭，根据商家自身网站情况请慎重选择是否开启。
    // 申请开通方法：联系我们的客户经理或拨打商户服务电话0571-88158090，帮忙申请开通。
    // 开启防钓鱼功能后，服务器、本机电脑必须支持远程XML解析，请配置好该环境。
    // 若要使用防钓鱼功能，建议使用POST方式请求数据
    public static String input_charset = "utf-8"; // 页面编码
    public static String sign_type = "MD5"; // 加密方式 不需修改
    public static String transport = "http";// 访问模式,根据自己的服务器是否支持ssl访问，若支持请选择https；若不支持请选择http
    public static String mainname = "D2C";// 收款方名称，如：公司名称、网站名称、收款人姓名等
    // notify_url 交易过程中服务器通知的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
    public String notify_url;
    // 付完款后跳转的页面 要用 http://格式的完整路径，不允许加?id=123这类自定义参数
    public String return_url;
    public String return_wap_url;
    public String notify_wap_url;
    public String show_url; // 网站商品的展示地址，不允许加?id=123这类自定义参数
    public String refund_notify_url;// 退款通知回调接口

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getReturn_wap_url() {
        return return_wap_url;
    }

    public void setReturn_wap_url(String return_wap_url) {
        this.return_wap_url = return_wap_url;
    }

    public String getNotify_wap_url() {
        return notify_wap_url;
    }

    public void setNotify_wap_url(String notify_wap_url) {
        this.notify_wap_url = notify_wap_url;
    }

    public String getShow_url() {
        return show_url;
    }

    public void setShow_url(String show_url) {
        this.show_url = show_url;
    }

    public String getRefund_notify_url() {
        return refund_notify_url;
    }

    public void setRefund_notify_url(String refund_notify_url) {
        this.refund_notify_url = refund_notify_url;
    }

}
