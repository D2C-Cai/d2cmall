package com.d2c.util.serial;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 工具类 - 编号生成
 */
public class SerialNumUtil {

    public static final String NORMAL_ORDER_SN_PREFIX = "Q";// 普通订单编号前缀
    public static final String VIRTUAL_ORDER_SN_PREFIX = "VQ"; // 虚拟订单编号前缀
    public static final String AUCTION_MARGIN_SN_PREFIX = "AM";// 拍卖订单编号前缀
    public static final String COUPON_ORDER_SN_PREFIX = "CO";// 优惠券单编号前缀
    public static final String O2O_SUBSCRIBE_SN_PREFIX = "T";// 门店预约单编号前缀
    public static final String PAY_ITEM_SN_PREFIX = "P";// 钱包交易流水单号前缀
    public static final String REDRACKETS_SN_PREFIX = "RP";// 红包交易流水单号前缀
    public static final String REFUND_SN_PREFIX = "R";// 退款单编号前缀
    public static final String RESHIP_SN_PREFIX = "R";// 退货单编号前缀
    public static final String EXCHANGE_SN_PREFIX = "R";// 退货单编号前缀
    public static final String REQUISITION_SN_PREFIX = "RE";// 调拨单编号前缀
    public static final String COMPLAINT_SN_PREFIX = "C";// 投诉单编号前缀
    public static final String MEMCARD_SN_PREFIX = "MC";// 会员卡号前缀
    public static final String CASHCARD_SN_PREFIX = "D";// D2C卡号前缀
    public static final String LIVE_CHANNELID_PREFIX = "LVCID";// 直播的频道ID前缀
    public static final String LIVE_STREAMID_PREFIX = "LVSID";// 直播的流ID前缀
    public static final String SHARE_TASK_SN_PREFIX = "ST";// 分享任务编号前缀
    public static final String MAGAZINE_ISSUE_PREFIX = "MI"; // 杂志发行流水号
    public static final String MAGAZINE_PAGE_PREFIX = "MP"; // 杂志页编号前缀
    public static final String PARTNER_BILL_SN_PREFIX = "PB";// 分销返利单编号前缀
    public static final String PARTNER_CASH_SN_PREFIX = "PC";// 分销提现单编号前缀
    public static final String COLLAGE_GROUP_SN_PREFIX = "G";// 拼团团队编号前缀
    public static final String POINT_EXCHANGE_SN_PREFIX = "PE";// 积分兑换流水号
    public static final String COLLECTION_CARD_SN_PREFIX = "CCD";// 集卡任务编号前缀

    /**
     * 普通订单编号
     *
     * @return
     */
    public synchronized static String buildOrderSn(String memberId) {
        if (memberId.length() > 4) {
            memberId = memberId.substring(memberId.length() - 4);
        }
        return NORMAL_ORDER_SN_PREFIX + System.currentTimeMillis() + StringUtils.leftPad(memberId, 4, "0");
    }

    /**
     * 虚拟订单编号
     *
     * @return
     */
    public synchronized static String buildVirtualOrderSn() {
        return VIRTUAL_ORDER_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 拍卖订单编号
     *
     * @return
     */
    public synchronized static String buildAuctionMarginSn() {
        return AUCTION_MARGIN_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 优惠券单编号
     *
     * @return
     */
    public synchronized static String buildCouponOrderSn() {
        return COUPON_ORDER_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 门店预约单编号
     *
     * @return
     */
    public synchronized static String buildO2oSubscribeSn() {
        return O2O_SUBSCRIBE_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 钱包交易流水单号
     *
     * @return
     */
    public synchronized static String buildPayItemSn(String accountId) {
        if (accountId.length() > 4) {
            accountId = accountId.substring(accountId.length() - 4);
        }
        return PAY_ITEM_SN_PREFIX + getDateStr() + System.currentTimeMillis() + StringUtils.leftPad(accountId, 4, "0");
    }

    /**
     * 红包交易流水单号
     *
     * @return
     */
    public synchronized static String buildRedpacketsSn() {
        return REDRACKETS_SN_PREFIX + getDateStr() + System.currentTimeMillis();
    }

    /**
     * 退款单编号
     *
     * @return
     */
    public synchronized static String buildRefundSn() {
        return REFUND_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 退货单编号
     *
     * @return
     */
    public synchronized static String buildReshipSn() {
        return RESHIP_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 换货单编号
     *
     * @return
     */
    public synchronized static String buildExchageSn() {
        return EXCHANGE_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 调拨单的编号
     *
     * @return
     */
    public synchronized static String buildRequisitionSn() {
        return REQUISITION_SN_PREFIX + getDateStr() + System.currentTimeMillis();
    }

    /**
     * 投诉单编号
     *
     * @return
     */
    public synchronized static String buildComplaintSn() {
        return COMPLAINT_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 会员卡编号
     *
     * @return
     */
    public synchronized static String buildMemberCardSn() {
        return MEMCARD_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * D2C卡号
     *
     * @param code
     * @param amount
     * @return
     */
    public synchronized static String buildCashCardSn(String code, BigDecimal amount) {
        return CASHCARD_SN_PREFIX + (amount == null ? 0 : amount.intValue()) + getDateStr() + code;
    }

    /**
     * 直播的频道ID
     *
     * @return
     */
    public synchronized static String buildLiveChannelID() {
        return LIVE_CHANNELID_PREFIX + System.currentTimeMillis();
    }

    /**
     * 直播的流ID
     *
     * @return
     */
    public synchronized static String buildLiveStreamID() {
        return LIVE_STREAMID_PREFIX + System.currentTimeMillis();
    }

    /**
     * 分享任务编号
     *
     * @return
     */
    public synchronized static String buildShareTaskSn() {
        return SHARE_TASK_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 杂志发行流水号
     *
     * @return
     */
    public synchronized static String buildMagazineIssueSn() {
        return MAGAZINE_ISSUE_PREFIX + System.currentTimeMillis();
    }

    /**
     * 杂志页编号
     *
     * @return
     */
    public synchronized static String buildMagazinePageSn() {
        return MAGAZINE_PAGE_PREFIX + System.currentTimeMillis();
    }

    /**
     * 分销返利单编号
     *
     * @return
     */
    public synchronized static String buildPatrnerBillSn() {
        return PARTNER_BILL_SN_PREFIX + getDateStr() + System.currentTimeMillis();
    }

    /**
     * 分销提现单编号
     *
     * @return
     */
    public synchronized static String buildPatrnerCashSn() {
        return PARTNER_CASH_SN_PREFIX + getDateStr() + System.currentTimeMillis();
    }

    /**
     * 拼团的团编号
     *
     * @return
     */
    public synchronized static String buildCollageGroupSn() {
        return COLLAGE_GROUP_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 积分兑换编号
     *
     * @return
     */
    public synchronized static String buildPointExchangeSn() {
        return POINT_EXCHANGE_SN_PREFIX + System.currentTimeMillis();
    }

    /**
     * 采购单编号
     *
     * @param storeCode
     * @return
     */
    public synchronized static String buildRequisitionItemSn(String storeCode) {
        return storeCode + "_" + System.currentTimeMillis();
    }

    /**
     * 对账单编号
     *
     * @param designerCode
     * @param settleYear
     * @param settleMonth
     * @return
     */
    public synchronized static String buildStatementSn(String designerCode, int settleYear, int settleMonth) {
        String randomNum = String.valueOf(System.currentTimeMillis());
        return designerCode + settleYear + settleMonth + randomNum.substring(randomNum.length() - 3);
    }

    /**
     * 集卡任务编号
     *
     * @return
     */
    public synchronized static String buildCollectionCardSn() {
        return COLLECTION_CARD_SN_PREFIX + System.currentTimeMillis();
    }

    private static String getDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyMM");
        return df.format(new Date());
    }

    /**
     * 生成随即密码
     *
     * @return
     * @parampwd_len生成的密码的总长度
     * @return密码的字符串
     */
    public static String getRandomNum(int pwdLen) {
        final int maxNum = 36;
        int i;// 生成的随机数
        int count = 0;
        char[] str = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwdLen) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum));// 生成的数最大为36-1
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString().toUpperCase();
    }

    /**
     * 生成随即密码
     *
     * @return
     * @parampwd_len生成的密码的总长度
     * @return密码的字符串
     */
    public static String getRandomNumber(int pwdLen) {
        final int maxNum = 36;
        int i;// 生成的随机数
        int count = 0;
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwdLen) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum));// 生成的数最大为36-1
            if (count == 0) {
                if (i > 0 && i < str.length) {
                    pwd.append(str[i]);
                    count++;
                }
            } else {
                if (i >= 0 && i < str.length) {
                    pwd.append(str[i]);
                    count++;
                }
            }
        }
        return pwd.toString().toUpperCase();
    }

}