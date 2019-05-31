package com.d2c.behavior.entity.enums;

public enum OperateLogType {
    /**
     * NAVIGATION:导航搜索 O2OCREATE:门店预约单创建 O2OCONFIRM:门店预约单确认
     * O2OSUBMIT:门店预约单提交. ORDER:订单创建 MAIN:首页 SHOWROOM:设计师品牌 STAR:明星风范
     * STORE:实体店首页
     * <p>
     * V_XXX APP各个监控锚点 O_XXX APP各个业务锚点
     * <p>
     * V_SUBMODULE（首页）, V_SHARETAG（广场卖家秀）, V_DESIGNERTAG（设计师店铺）, V_PROMOTION（活动商品）, V_CROWD（组合商品）,
     * V_ARTICLE（文章页）, V_SHARE（分享）, V_STYLE（设计师风格）, V_AREA（设计师分类）, V_COUNTRY（设计师地区）, V_PRODUCTCATE（商品列表）,
     * V_PRODUCT("查看商品详情"), V_DESIGNER（设计师店铺）, V_PRODUCTCOMB（组合商品详情）,
     * O_CART（点击购物车）, O_ORDER（订单提交成功）, O_REGISTER（注册）, O_LOGIN（登录）,
     */
    V_SUBMODULE("首页"),
    V_NAVIGATION("打开导航页面"),
    V_SHARETAG("打开广场卖家秀"),
    V_DESIGNER("查看设计师详情"),
    V_DESIGNERTAG("设计师店铺"),
    V_PROMOTION("活动商品"),
    V_CROWD("打开组合商品"),
    V_ARTICLE("打开文章页"),
    V_SHARE("打开分享页"),
    V_STYLE("设计师风格"),
    V_AREA("打开按地区分类"),
    V_COUNTRY("打开按国家分类"),
    V_TOPCATE("打开按商品分类"),
    V_LIVE("打开直播页面"),
    V_PRODUCT("查看商品详情"),
    V_PRODUCTCATE("商品列表"),
    V_PRODUCTCOMB("组合商品详情"),
    O_CART("购物车添加商品"),
    O_ORDER("商品订单提交"),
    O_REGISTER("注册用户"),
    O_LOGIN("用户登录"),
    //***************
    MAIN("首页"),
    NAVIGATION("导航搜索"),
    O2OCREATE("门店预约单创建"),
    O2OCONFIRM("门店预约单确认"),
    O2OSUBMIT("门店预约单提交"),
    ORDER("订单创建"),
    SHOWROOM("设计师品牌"),
    STAR("明星风范"),
    STORE("实体店首页"),
    //********************
    LOGIN("用户登录"),
    CARTPRODUCT("购物车添加商品"),
    ORDERPRODUCT("购买商品订单"),
    DESIGNCOMPOSITION,
    AD,
    FEMALE("女装"),
    MALE("男装"),
    VIEWACTIVITY, VIEWARTICLE, VIEWDESIGNER, VIEWPRODUCT,
    CROWD, PERSONAL, REGISTER,
    STORELIST,
    //********************************
    OTHER("其他");
    String name;

    OperateLogType() {
    }

    OperateLogType(String name) {
        this.name = name;
    }

    public static String getName(String key) {
        try {
            return valueOf(key).name;
        } catch (Exception e) {
            return OTHER.name;
        }
    }

    public String getName() {
        return name;
    }
}
