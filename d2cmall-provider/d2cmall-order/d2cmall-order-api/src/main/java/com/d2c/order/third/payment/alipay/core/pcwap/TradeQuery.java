package com.d2c.order.third.payment.alipay.core.pcwap;

import org.dom4j.Node;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TradeQuery implements Serializable {
    // 0 外部订单号,1 账户余额（元）, 2时间, 3流水号, 4支付宝交易号, 5交易对方Email, 6交易对方, 7用户编号, 8收入（元）,
    // 9支出（元）,
    // 10交易场所, 11商品名称, 12类型, 13说明
    // Q14259281804385303_bankPay, 48885.77, 2015年03月10日 03:13:36,
    // 96798889656631, 2015031000001000630057193465, kehuzijinbu021@alipay.com,
    // 支付宝（非注册用户）, 2088502970134638, 498.00, , 支付宝, D2C-vivisisi 叶茜 幻境系列
    // 时尚优雅网纱翘边遮阳礼帽, 在线支付,
    private static final long serialVersionUID = 1L;
    /**
     * 账务类型
     */
    private String type;
    /**
     * 业务类型
     */
    private String business_type;
    /**
     * 支付宝订单号
     */
    private String alipay_order_no;
    /**
     * 商户订单号 r1
     */
    private String merchant_order_no;
    /**
     * 本方支付宝账户ID
     */
    private String self_user_id;
    /**
     * 对方支付宝账户ID
     */
    private String opt_user_account;
    private String opt_user_name;
    /**
     * 收入金额
     */
    private BigDecimal in_amount;
    /**
     * 支出金额
     */
    private BigDecimal out_amount;
    /**
     * 当时账户的余额
     */
    private BigDecimal balance;
    /**
     * 账务备注说明
     */
    private BigDecimal memo;
    /**
     * 创建时间
     */
    private Date create_time;
    /**
     * 交易状态 WAIT_BUYER_PAY:等待买家付款 WAIT_SELLER_SEND_GOODS 买家已付款，等待卖家发货
     * WAIT_BUYER_CONFIRM_GOODS 卖家已发货，等待买家确认 TRADE_FINISHED 交易成功结束 TREADE_COLSED
     * 交易中途关闭 WAIT_SYS_CONFIRM_PAY 支付宝确认买家银行汇款中 WAIT_SYS_PAY_SELLER
     * 买家确认收货，等待支付宝打款给卖家 TREADE_REDUSE 立即支付交易拒绝 TRADE_REFUSE_DEALING 立即支付交易拒绝中
     * TRADE_CANCEL立即支付交易取消
     */
    private String trade_status;
    /**
     * 交易冻结状态,0表示未冻结，1表示冻结
     */
    private String flag_trade_locked;
    public TradeQuery() {
    }
    public TradeQuery(List<String> record) {
        this.merchant_order_no = record.get(0);
        this.alipay_order_no = record.get(4);
        this.opt_user_account = record.get(5);
        this.opt_user_name = record.get(6);
        // TODO 转账还是支付
        this.business_type = record.get(12);
        if (StringUtils.hasLength(record.get(8))) {
            this.in_amount = new BigDecimal(record.get(8));
            in_amount = in_amount.setScale(2, BigDecimal.ROUND_DOWN);
        }
        if (StringUtils.hasLength(record.get(9))) {
            this.out_amount = new BigDecimal(record.get(9));
            out_amount = out_amount.setScale(2, BigDecimal.ROUND_DOWN);
        }
    }
    /**
     * 单笔交易查询接口
     *
     * @param record
     */
    public TradeQuery(Map<String, String> record) {
        this.merchant_order_no = record.get("out_trade_no");
        this.alipay_order_no = record.get("trade_no");
        this.opt_user_account = record.get("buyer_email");
        this.opt_user_name = record.get("buyer_id");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.create_time = (StringUtils.isEmpty(record.get("gmt_create")) ? null
                    : sdf.parse(record.get("gmt_create")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.self_user_id = record.get("seller_id");
        this.business_type = record.get("payment_type");
        if (!StringUtils.isEmpty(record.get("total_fee"))) {
            this.in_amount = new BigDecimal(record.get("total_fee"));
            in_amount = in_amount.setScale(2, BigDecimal.ROUND_DOWN);
        }
        this.trade_status = record.get("trade_status");
        this.flag_trade_locked = record.get("flag_trade_locked");
    }
    /**
     * 财务明细分液查询接口
     *
     * @param node
     */
    public TradeQuery(Node node) {
        this.merchant_order_no = node.selectSingleNode("//merchant_out_order_no").getText();
        this.alipay_order_no = node.selectSingleNode("//trade_no").getText();
        this.opt_user_account = node.selectSingleNode("//buyer_account").getText();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            this.create_time = (StringUtils.isEmpty(node.selectSingleNode("//trans_date").getText()) ? null
                    : sdf.parse(node.selectSingleNode("//trans_date").getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.self_user_id = node.selectSingleNode("//seller_account").getText();
        this.business_type = node.selectSingleNode("//trans_code_msg").getText();
        if (!StringUtils.isEmpty(node.selectSingleNode("//income").getText())) {
            this.in_amount = new BigDecimal(node.selectSingleNode("//income").getText());
            in_amount = in_amount.setScale(2, BigDecimal.ROUND_DOWN);
        }
        if (!StringUtils.isEmpty(node.selectSingleNode("//outcome").getText())) {
            this.out_amount = new BigDecimal(node.selectSingleNode("//outcome").getText());
            out_amount = out_amount.setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getAlipay_order_no() {
        return alipay_order_no;
    }

    public void setAlipay_order_no(String alipay_order_no) {
        this.alipay_order_no = alipay_order_no;
    }

    public String getMerchant_order_no() {
        return merchant_order_no;
    }

    public void setMerchant_order_no(String merchant_order_no) {
        this.merchant_order_no = merchant_order_no;
    }

    public String getSelf_user_id() {
        return self_user_id;
    }

    public void setSelf_user_id(String self_user_id) {
        this.self_user_id = self_user_id;
    }

    public String getOpt_user_account() {
        return opt_user_account;
    }

    public void setOpt_user_account(String opt_user_account) {
        this.opt_user_account = opt_user_account;
    }

    public String getOpt_user_name() {
        return opt_user_name;
    }

    public void setOpt_user_name(String opt_user_name) {
        this.opt_user_name = opt_user_name;
    }

    public BigDecimal getIn_amount() {
        return in_amount;
    }

    public void setIn_amount(BigDecimal in_amount) {
        this.in_amount = in_amount;
    }

    public BigDecimal getOut_amount() {
        return out_amount;
    }

    public void setOut_amount(BigDecimal out_amount) {
        this.out_amount = out_amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMemo() {
        return memo;
    }

    public void setMemo(BigDecimal memo) {
        this.memo = memo;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getTrade_status() {
        return trade_status;
    }

    public void setTrade_status(String trade_status) {
        this.trade_status = trade_status;
    }

    public String getFlag_trade_locked() {
        return flag_trade_locked;
    }

    public void setFlag_trade_locked(String flag_trade_locked) {
        this.flag_trade_locked = flag_trade_locked;
    }

}
