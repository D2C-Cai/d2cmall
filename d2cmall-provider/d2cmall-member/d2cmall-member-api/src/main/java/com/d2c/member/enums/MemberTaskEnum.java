package com.d2c.member.enums;

public enum MemberTaskEnum {
    COLLECTION_ADD("收藏商品"),
    CART_ADD("加入购物车"),
    PRODUCT_COMMENT("评价商品"),
    ORDER_SUCCESS("下单后订单完结"),
    BRAND_ATTENTION("关注品牌"),
    SHARE_ADD("发布买家秀"),
    SHARE_LIKE("点赞买家秀"),
    SHARE_COMMENT("评论买家秀"),
    FRIST_REGISTER("注册成功"),
    FRIST_MESSAGE_LIST("打开消息通知");
    String show;

    MemberTaskEnum(String show) {
        this.show = show;
    }

    @Override
    public String toString() {
        return show;
    }
}
