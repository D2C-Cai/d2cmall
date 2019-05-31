package com.d2c.order.third.payment.wxpay.core;

import com.d2c.order.third.payment.wxpay.client.WxPayUtil;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class WeixinPayHelper {

    /**
     * 公众账号ID
     */
    private String appid;
    /**
     * 商户号
     */
    private String mch_id;
    /**
     * 随机字符串
     */
    private String nonce_str;
    /**
     * 签名
     */
    private String sign;
    /**
     * 商品描述
     */
    private String body;
    /**
     * 商户订单号
     */
    private String out_trade_no;
    /**
     * 总金额,单位分
     */
    private String total_fee;
    /**
     * 终端IP
     */
    private String spbill_create_ip;
    /**
     * 通知地址
     */
    private String notify_url;
    /**
     * 交易类型
     */
    private String trade_type;
    /**
     * 商品ID
     */
    private String product_id;
    /**
     * 用户标识，trade_type=JSAPI，此参数必传
     */
    private String openid;
    /**
     * 附加字段
     */
    private String attach;

    /**
     * 参数检查是否通过
     */
    private Boolean checkParams() {
        return StringUtil.hasBlack(new Object[]{appid, mch_id, nonce_str, body, out_trade_no, spbill_create_ip,
                notify_url, trade_type, total_fee});
    }

    /**
     * 订单接口请求xml
     */
    public String paramXml(String pay_key) {
        if (checkParams()) {
            throw new RuntimeException("参数缺失！");
        }
        HashMap<String, String> map = this.createParamMap();
        if ("JSAPI".equals(trade_type)) {
            if (StringUtils.isBlank(openid)) {
                throw new RuntimeException("参数缺失！");
            }
            map.put("openid", openid);
        } else if ("NATIVE".equals(trade_type)) {
            if (StringUtils.isBlank(product_id)) {
                throw new RuntimeException("参数缺失！");
            }
            map.put("product_id", product_id);
        } else {
            throw new RuntimeException("未知支付类型！");
        }
        map.put("sign", WxPayUtil.createSign(map, pay_key));
        return WxPayUtil.mapToXml(map);
    }

    public HashMap<String, String> createParamMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appid", this.getAppid());
        map.put("body", this.getBody());
        map.put("mch_id", this.getMch_id());
        map.put("nonce_str", this.getNonce_str());
        map.put("notify_url", this.getNotify_url());
        map.put("out_trade_no", this.getOut_trade_no());
        map.put("spbill_create_ip", this.getSpbill_create_ip());
        map.put("total_fee", this.getTotal_fee());
        map.put("trade_type", this.getTrade_type());
        map.put("attach", this.getAttach());
        return map;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

}
