package com.d2c.logger.support;

/**
 * 11.发货提醒 12.到货提醒 13.调拨单提醒 14.对账单提醒 <br>
 * 21.订单未支付 22.退款通知 23.退货通知 24.换货通知 25.购买回复 26.投诉反馈回复 27.门店预约通知  28.代付提醒 29.其他 <br>
 * 31.优惠券到账提醒 32.优惠券过期提醒 33.钱包充值 34.钱包消费 35.红包消息 <br>
 * 41.点赞提醒 42.关注 43.关注用户发布买家秀提醒 44.买家秀评论和回复提醒 45.直播提醒 <br>
 * 51.开抢提醒 52.货到通知 <br>
 * 61.活动精选 <br>
 * 71.设计师上新 72.品牌推荐<br>
 * 81.提现单提醒 82.返利单提醒 83.账户流水 84.访客提醒 85.邀请注册提醒 86.关店提醒 89.其他
 */
public class MsgResource {

    public static String getTypeName(Integer type) {
        if (type == null) {
            return "";
        }
        switch (type) {
            case 1:
                return "物流通知";
            case 2:
                return "商品提醒";
            case 3:
                return "系统通知";
            case 4:
                return "系统通知";
            case 5:
                return "广场动态";
            case 6:
                return "其他";
            case 7:
                return "广场动态";
            case 8:
                return "广场动态";
            case 9:
                return "广场动态";
            case 10:
                return "系统通知";
            case 11:
                return "物流通知";
            case 12:
                return "物流通知";
            case 13:
                return "系统通知";
            case 14:
                return "系统通知";
            case 21:
                return "系统通知";
            case 22:
                return "系统通知";
            case 23:
                return "系统通知";
            case 24:
                return "系统通知";
            case 25:
                return "系统通知";
            case 26:
                return "系统通知";
            case 27:
                return "系统通知";
            case 28:
                return "系统通知";
            case 29:
                return "系统通知";
            case 31:
                return "我的资产";
            case 32:
                return "我的资产";
            case 33:
                return "我的资产";
            case 34:
                return "我的资产";
            case 35:
                return "我的资产";
            case 41:
                return "广场动态";
            case 42:
                return "广场动态";
            case 43:
                return "广场动态";
            case 44:
                return "广场动态";
            case 45:
                return "广场动态";
            case 51:
                return "商品提醒";
            case 52:
                return "商品提醒";
            case 61:
                return "活动精选";
            case 71:
                return "品牌推荐";
            case 72:
                return "品牌推荐";
            case 81:
                return "买手服务消息";
            case 82:
                return "买手服务消息";
            case 83:
                return "买手服务消息";
            case 84:
                return "买手服务消息";
            case 85:
                return "买手服务消息";
            case 86:
                return "买手服务消息";
            case 89:
                return "买手服务消息";
            default:
                return "其他";
        }
    }

}
