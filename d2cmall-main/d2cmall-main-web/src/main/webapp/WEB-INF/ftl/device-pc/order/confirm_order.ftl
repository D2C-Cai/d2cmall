<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#assign LOGINMEMBER=loginMember()/>
<html xmlns="//www.w3.org/1999/xhtml">
<head>
    <title>【D2C全球好设计】_汇集全球好设计,寻找您专属的原创新品- D2CMall.COM</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords" content="d2c官网,D2C,D2CMALL,服装设计,服装设计师,时装设计,服装品牌设计,时尚设计,时装设计师"/>
    <meta name="description"
          content="D2C是一家集潮流风尚、个性设计为一体的设计师集成平台，致力于为消费者推荐全球优秀的设计师品牌，除设计师品牌限量销售外，更有私人定制服务，同时在中国各大城市开设实体店。"/>
    <meta http-equiv="imagetoolbar" content="no"/>
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta property="data-title" content="d2c官网,D2C,D2CMALL,服装设计,服装设计师,时装设计,服装品牌设计,时尚设计,时装设计师"/>
    <meta property="qc:admins" content="3624416613642351446375"/>
    <meta property="wb:webmaster" content="cbc4e89c163dc6cf"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <link rel="apple-touch-icon" href="//static.d2c.cn/common/m/img/ic_launcher.png"/>
    <link rel="apple-touch-startup-image" media="screen and (orientation: portrait)"
          href="//static.d2c.cn/ipad/img/system/start_up_p.png">
    <link rel="apple-touch-startup-image" media="screen and (orientation: landscape)"
          href="//static.d2c.cn/ipad/img/system/start_up_l.png">
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${static_base}/c/js/html5.js"></script>
    <![endif]-->
    <#if profile=='development'>
        <link type="text/css" href="${static_base}/nc/css/com.base.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.iconfont.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.layout.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.element.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.component.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.home.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.crowd.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.showroom.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.product.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.user.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.order.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.star.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.custom.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.store.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.other.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.response.css" rel="stylesheet" media="screen"/>
    <#else>
        <link type="text/css" href="${static_base}/nc/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
              media="screen"/>
    </#if>
    <!--[if lt IE 9]>
    <link type="text/css" href="${static_base}/nc/css/ie.css" rel="stylesheet" media="screen"/>
    <![endif]-->
    <#if profile=='development'>
        <script type="text/javascript" src="${static_base}/nc/js/lib/jquery.1.83.js"></script>
        <script type="text/javascript" src="${static_base}/nc/js/lib/template.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/jquery.cookie.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript"
                src="${static_base}/nc/js/utils/jquery.autocomplete.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/modules/common.js?t=${refreshTimeStamp}"></script>
    <#else>
        <script type="text/javascript" src="${static_base}/nc/js/compress.js?t=${refreshTimeStamp}"></script>
    </#if>
    <!--[if IE 6]>
    <script type="text/javascript" src="${static_base}/c/js/png.js"></script>
    <script type="text/javascript">
        DD_belatedPNG.fix('.png');
    </script>
    <![endif]-->
    <script type="text/javascript">
        var online = [];
        var _hmt = _hmt || [];
        var _py = _py || [];
        _py.push(['a', '9U..Cvv8h4lbWIcOzq05FVADpP']);
        _py.push(['domain', 'stats.ipinyou.com']);
        _py.push(['e', '']);
        var _server_time = '${.now?string("yyyy/MM/dd HH:mm:ss")}';
    </script>
    <!--<script type="text/javascript" src="//cbjs.baidu.com/js/m.js"></script>-->
</head>
<body style="background:#fff">
<#assign servertel="4008403666"/>
<header style="height:160px;box-shadow:none;">
    <div class="header">
        <div class="header-user clearfix">
            <ul>
                <li>
                    <#if LOGINMEMBER.id!=null>
                        <#if LOGINMEMBER.nickname><a href="/member/info">${LOGINMEMBER.nickname}</a><#else><a
                                href="javascript:;">亲爱的用户</a></#if><i>｜</i>
                        <#if !LOGINMEMBER.d2c>
                            <div class="notice-tip animated bounceIn">
                                <a href="/member/bind" target="_blank">绑定D2C账号</a>
                            </div>
                        <#elseif !LOGINMEMBER.nickname||LOGINMEMBER.nickname==''>
                            <div class="notice-tip animated bounceIn">
                                <a href="/member/info" target="_blank">亲，取个昵称把~</a>
                            </div>
                        </#if>
                    <#else>
                        <a href="/member/register" id="user-bar-register" rel="nofollow">注册</a><i>｜</i>
                        <a href="/member/login" id="user-bar-login" rel="nofollow">登录</a><i>｜</i>
                    </#if>
                </li>
                <li><a href="/member/order" id="loginStatus">我的订单</a><i>｜</i></li><!--此id用于刷新购物车时判断用户是否登陆-->
                <li class="relative user-collect"><a href="javascript:;"><span
                                class="collection-down">我的收藏</span></a><i>｜</i>
                    <ul class="collection-detail">
                        <li><a href="/member/interest/collection/list">收藏的商品</a></li>
                        <li style="border:0"><a href="/member/interest/attention/list">收藏的设计师</a></li>
                    </ul>
                </li>

                <li class="relative cart" <#if !LOGINMEMBER.d2c>data-type="login"</#if>><a href="/cart/list"
                                                                                           target="_blank"
                                                                                           class="parent">
                        <div class="template-m display-none">
                            <div class="text-center loading"><h3 class="loading-text">购物车中还没有商品，赶紧选购吧!</h3><img
                                        class="loading-img" src="//static.d2c.cn/img/home/150727/loading.gif"></div>
                            <div class="template-list"></div>
                        </div>
                        购物车<span id="cart-nums-id">0</span></a><#if LOGINMEMBER.id!=null><i>｜</i></#if></li>
                <#if LOGINMEMBER.id!=null>
                    <li><a href="/member/logout">退出</a></li></#if>
            </ul>
        </div>
        <div class="layout-response clearfix">
            <div class="header-bar"></div>
            <div class="header-logo"><a href="//www.d2cmall.com">D2C全球好设计</a></div>
            <div class="header-ad" id="header-animotion-ad"></div>
            <div class="cart-step step2">

            </div>
        </div>

    </div>
</header>
<div class="layout layout-response layout-order">
    <form action="/order/create" method="post" id="order-form" class="validate-form" target="_self"
          style="padding:15px;">
        <input type="hidden" name="orderId" value="${order.id}"/>
        <input type="hidden" name="tempId" value="${order.tempId}"/>
        <div style="padding-right:20px;border-right:1px solid #DEDEDE;margin-top:20px;">
            <h2 class="order-title" style="margin-bottom:20px"><a href="/address/list" target="_blank"
                                                                  class="float-right blue">管理地址</a>收货地址</h2>

            <div id="address-list" class="select-address address-list clearfix" name="address-list">
                <#list addresses as address>
                    <div class="address-item<#if address.isdefault> on</#if>" data-id="${address.id}" title="双击可以编辑地址">
                        <p class="first-line"><span class="first-f"><strong>${(address.name)!}</strong>&nbsp;&nbsp;&nbsp;${(address.province.name)?default("")}${(address.city.name)?default("")}</span><#if address.isdefault>
                            <span class="red is-default" rel="${address.id}">【默认】</span><#else><span
                                class="set-default"><a href="javascript:" rel="${address.id}" id="tip${address.id}"
                                                       class="grey">【设为默认】</a></span></#if></p>
                        <p class="second-line"><span
                                    class="address-value">${(address.district.name)!}${address.street}</span>&nbsp;&nbsp;<br/>${address.mobile}
                        </p>
                        <input type="radio" name="addressId" value="${address.id}"<#if address.isdefault> checked</#if>
                               class="display-none"/>
                        <i class="fa check"></i>
                    </div>
                </#list>
                <div class="address-add" style="color:#808080">
                    <span class="fa fa-plus-circle"></span>新增地址
                </div>
            </div>
            <div class="tip tip-validate display-none" data-target="address" data-function="checkAddress"></div>
            <h2 class="order-title" style="margin-bottom:20px"><a href="/cart/list" class="blue float-right">修改购物车</a>购买清单
            </h2>
            <table class="table table-lightgrey">
                <thead>
                <tr>
                    <th class="text-center" style="border-right:none;">宝贝信息</th>
                    <th width="200" style="border-right:none;border-left:none;">单价（元）</th>
                    <th width="200" style="border-right:none;border-left:none;">数量</th>
                    <th width="150" style="border-left:none;">小计（元）</th>
                </tr>
                </thead>
                <tbody>
                <#assign exceptions=0 /> <#assign cod=1/>
                <#list orderItems?if_exists as orderItem>
                    <#if orderItem?exists && orderItem.productSku.status gt 0 && orderItem.productSku.availableStore gt 0>
                        <tr class="item">
                            <td class="text-left info  border-r-none">
                                <input type="hidden" name="skuId" value="${orderItem.productSkuId}"/>
                                <input type="hidden" name="partnerId" value="${orderItem.partnerId}"/>
                                <input type="hidden" name="productCombId" value="${(orderItem.productCombId)!0}"/>
                                <input type="hidden" name="partnerStyle" value="${orderItem.partnerStyle}"/>
                                <input type="hidden" name="cartItemId" value="${orderItem.cartItemId}"/>
                                <input type="hidden" name="orderPromotionId" value="${(orderItem.orderPromotionId)!}"/>
                                <input type="hidden" name="goodPromotionId" value="${(orderItem.goodPromotionId)!}"/>
                                <p class="img" style="margin-top:2px;"><img
                                            src="${picture_base}/${(orderItem.sp1?eval.img)!}!80"
                                            alt="${orderItem.productName}" height="90"/></p>
                                <p style="color:red;"><#if orderItem.after==0><span
                                            class="red">【该商品不支持七天无理由退换货】</span></#if><#if orderItem.cod==0><span>【该商品不支持货到付款】</span></#if><#if orderItem.promotionName gt 0>
                                        <span>
                                        【${(orderItem.promotionName)!}】</span></#if><#if orderItem.orderPromotionName gt 0>
                                        <span>【${(orderItem.orderPromotionName)!}】</span></#if></p>
                                <p><a href="/product/${orderItem.productId}"
                                      target="_blank">${orderItem.productName}</a>
                                <p class="grey">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}
                                    &nbsp;&nbsp;&nbsp;<#if orderItem.sp2 && (orderItem.sp2?eval)?size gt 0 >${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value}</#if>
                                    &nbsp;&nbsp;&nbsp; 设计师：${orderItem.designerName}</p>
                                <p class="green"></p>
                            </td>
                            <td class="text-center border-l-none border-r-none">
                                <#if orderItem.promotionPrice gt 0>
                                    <p>
                                        <strong class="unit price">${(orderItem.productPrice-orderItem.promotionPrice)?string("currency")?substring(1)}</strong>
                                    </p>
                                    <p class="grey" style="font-size:12px;text-decoration:line-through;">
                                        &yen;${orderItem.productPrice?string("currency")?substring(1)}</p>
                                <#else>
                                    <p>
                                        <strong class="unit price">${orderItem.productPrice?string("currency")?substring(1)}</strong>
                                    </p>
                                    <#if orderItem.productPrice!=orderItem.originalPrice><p
                                            style="text-decoration:line-through;">
                                        &yen;${orderItem.originalPrice}</p></#if>
                                </#if>
                            </td>
                            <td class="text-center border-l-none border-r-none">
                                <#if orderItem.quantity gt orderItem.productSku.availableStore>
                                    <#assign quantity = orderItem.productSku.availableStore />
                                    ${orderItem.productSku.availableStore}
                                    <input type="hidden" name="quantity" value="${orderItem.productSku.availableStore}"
                                           size="2" class="input text-center noime"/>
                                    <div class="tip">目前库存只剩${orderItem.productSku.availableStore}件</div>
                                <#else>
                                    <#assign quantity = orderItem.quantity />
                                    ${orderItem.quantity}
                                    <input type="hidden" name="quantity" value="${orderItem.quantity}" size="2"
                                           class="input text-center noime"/>
                                </#if>
                            </td>
                            <td class="text-center border-l-none ">
                                <p class="red">&yen;<strong class="subtotal"
                                                            style="font-size:15px;"> ${(orderItem.totalPrice-orderItem.promotionAmount)?string("currency")?substring(1)}</strong>
                                </p>
                                <#if orderItem.promotionAmount gt 0>
                                    <p class="grey" style="font-size:12px">优惠 ${orderItem.promotionAmount} 元</p>
                                </#if>
                            </td>
                        </tr>
                        <#if orderItem.cod==0>
                            <#assign cod=0/>
                        </#if>
                    <#else>
                        <#assign exceptions=exceptions+1 />
                    </#if>
                </#list>
                <#if exceptions gt 0>
                    <tr class="space item">
                        <td colspan="6"></td>
                    </tr>
                    <#list orderItems?if_exists as orderItem>

                        <#if !orderItem.productSku?exists || orderItem.productSku.status lte 0 || orderItem.productSku.availableStore lte 0>
                            <tr class="exceptions-items item">
                                <td class="border-r-none">
                                    <div class="item">

                                        <#if (orderItem.productSku.availableStore lte 0 && orderItem.productSku.freezeStore gt 0)>
                                            <p>【该商品已经卖完了。但还其他客户未付款，还有机会购买哦~】</p>
                                        </#if>
                                        <p class="img grey" style="margin-top:2px;float:left;position:relative;">
                                            <#if !orderItem.productSku?exists || orderItem.productSku.status lte 0>
                                                <img src="//static.d2c.cn/img/home/160627/images/order-down.png"
                                                     border="0" style="display:block;position:absolute;top:0;left:0;">
                                            <#elseif (orderItem.productSku.availableStore lte 0 && orderItem.productSku.freezeStore gt 0)>
                                                <img src="//static.d2c.cn/img/home/160627/images/order_change.png"
                                                     border="0" style="display:block;position:absolute;top:0;left:0;">
                                            <#elseif (orderItem.productSku.availableStore lte 0)>
                                                <img src="//static.d2c.cn/img/home/160627/images/order-soldout.png"
                                                     border="0" style="display:block;position:absolute;top:0;left:0;">
                                            </#if>
                                            <img src="${picture_base}/${(orderItem.sp1?eval.img)!}!80"
                                                 alt="${orderItem.productName}" height="90"/>
                                        </p>
                                        <p class="grey">${orderItem.productSn}</p>
                                        <p><a href="/product/${orderItem.productId}" target="_blank"
                                              class="grey">${orderItem.productName}</a></p>
                                        <span class="display-block grey arial"
                                              style="margin-top:3px;">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}&nbsp;&nbsp;&nbsp;<#if orderItem.sp2 && (orderItem.sp2?eval)?size gt 0 >${orderItem.sp2?eval.value}：${orderItem.sp2?eval.name}</#if>&nbsp;&nbsp;&nbsp;设计师：${orderItem.designerName}</span>
                                    </div>
                                </td>
                                <td class="border-r-none border-l-none text-center">
                                    <#if orderItem.promotionPrice gt 0>
                                        <p class="grey">&yen;<strong
                                                    class="unit price ">${(orderItem.productPrice-orderItem.promotionPrice)?string("currency")?substring(1)}</strong>
                                        </p>
                                        <p class="grey" style="font-size:12px;text-decoration:line-through;">
                                            &yen;${orderItem.originalPrice?string("currency")?substring(1)}</p>
                                    <#else>
                                        <strong class="unit price grey">&yen;${orderItem.productPrice?string("currency")?substring(1)}</strong>
                                    </#if>
                                </td>
                                <td class="border-r-none border-l-none">
                                </td>
                                <td class="border-l-none">
                                </td>
                            </tr>
                        </#if>
                    </#list>
                </#if>
                <tr class="space">
                    <td colspan="4"></td>
                </tr>
                <tr class="no-border">
                    <td colspan="4" rowspan="1" class="no-padding" style="vertical-align:top">
                        <p class="grey">添加留言或备注</p>
                        <textarea name="memo" style="width:100%" rows="1" class="class=" input"
                        onKeyDown="checkLength()" onKeyUp="checkLength()" onPaste="checkLength()"></textarea>
                    </td>
                </tr>
                <tr class="space">
                    <td colspan="4"></td>
                </tr>

                <tr class="no-border">
                    <td colspan="4" style="padding:0;">
                        <div class="order-toggle-item"
                             <#if ableCoupons?size gt 0 || disableCoupons?size gt 0>style="border-bottom:1px solid #DEDEDE"</#if>>
                            <p class="order-coupon"><strong>优惠券使用<#if ableCoupons?size gt 0><b></b></#if><i
                                            class="coupon-down"></i></strong></p></div>
                        <#if ableCoupons?size gt 0 || disableCoupons?size gt 0>
                            <div class="display-none" style="margin-top:20px;">
                                <div class="tab-small" id="change-tab">
                                    <a href="javascript:" class="selected" data-type="enable">可用优惠券(${ableCoupons?size}
                                        )</a>
                                    <a href="javascript:" data-type="disable">不可用优惠券(${disableCoupons?size})</a>
                                </div>
                                <div>
                                    <div id="coupons-enable"
                                         style="overflow:hidden;border:1px solid #ededed;border-top:none;">
                                        <#if ableCoupons>
                                            <#list ableCoupons as coupon>
                                                <#if coupon.type=="DISCOUNT">
                                                    <div class="order-coupon-item text-center" data-type="enable">
                                                        <i></i>
                                                        <div class="coupon-expiredate">
                                                            <p><strong>${coupon.amount/10}折</strong></p>
                                                            <p style="margin-top:12px;">有效期至</p>
                                                            <p>${coupon.expireDate?string("yyyy年MM月dd日")}</p>
                                                        </div>
                                                        <div class="coupon-show enable">
                                                        </div>
                                                        <div class="coupon-price">
                                                            <p>${coupon.name}</p>
                                                        </div>
                                                        <input type="checkbox" name="coupons" value="${coupon.code}"
                                                               data-type="DISCOUNT" rel="${coupon.amount/100}"
                                                               class="coupon-checkbox display-none"/>
                                                    </div>

                                                <#else>
                                                    <div class="order-coupon-item text-center" data-type="enable">
                                                        <i></i>
                                                        <div class="coupon-expiredate">
                                                            <p><strong><span style="font-size:22px;padding-right:3px;">&yen;</span>${coupon.amount}
                                                                </strong></p>
                                                            <p style="margin-top:12px;">有效期至</p>
                                                            <p>${coupon.expireDate?string("yyyy年MM月dd日")}</p>
                                                        </div>
                                                        <div class="coupon-show enable">
                                                        </div>
                                                        <div class="coupon-price">
                                                            <p>${coupon.name}</p>
                                                            <p style="font-weight:550">（满${coupon.needAmount}可用）</p>
                                                        </div>
                                                        <input type="checkbox" name="coupons" value="${coupon.code}"
                                                               rel="${coupon.amount}"
                                                               class="coupon-checkbox display-none"/>
                                                    </div>
                                                </#if>
                                            </#list>
                                        <#else>
                                            <span style="display:inline-block;width:120px;height:30px;text-align:center;margin-top:30px;margin-bottom:10px;">没有可用的优惠券。</span>
                                        </#if>
                                    </div>

                                    <div id="coupons-disable" style="display:none;">
                                        <#if disableCoupons>
                                            <#list disableCoupons as coupon>
                                                <#if coupon.type=="DISCOUNT">
                                                    <div class="order-coupon-item disable text-center">
                                                        <div class="coupon-expiredate disable">
                                                            <p><strong>${coupon.amount/10}</strong></p>
                                                            <p style="margin-top:12px;">有效期至</p>
                                                            <p>${coupon.expireDate?string("yyyy年MM月dd日")}</p>
                                                        </div>
                                                        <div class="coupon-show disable">
                                                        </div>
                                                        <div class="coupon-price disable">
                                                            <p>${coupon.name}</p>
                                                        </div>
                                                        <a class="direction" data-type="right">使用说明&nbsp;></a>
                                                        <div class="direction-info">
                                                            <p style="margin:0;padding:10px;width:200px;font-size:8px">
                                                                <#if coupon.remark?length gt 91>${coupon.remark?substring(0,91)!}<#else>${coupon.remark}</#if>
                                                            </p>
                                                        </div>
                                                        <input type="checkbox" disabled name="coupons"
                                                               value="${coupon.code}" data-type="DISCOUNT"
                                                               rel="${coupon.amount/100}"
                                                               class="coupon-checkbox display-none"/>
                                                    </div>

                                                <#else>
                                                    <div class="order-coupon-item disable text-center">
                                                        <div class="coupon-expiredate disable">
                                                            <p><strong><span style="font-size:22px;padding-right:3px;">&yen;</span>${coupon.amount}
                                                                </strong></p>
                                                            <p style="margin-top:12px;">有效期至</p>
                                                            <p>${coupon.expireDate?string("yyyy年MM月dd日")}</p>
                                                        </div>
                                                        <div class="coupon-show disable">
                                                        </div>
                                                        <div class="coupon-price disable">
                                                            <p>${coupon.name}</p>
                                                            <p style="font-weight:550">（满${coupon.needAmount}可用）</p>
                                                        </div>
                                                        <a class="direction" data-type="right">使用说明&nbsp;></a>
                                                        <div class="direction-info">
                                                            <p style="margin:0;padding:10px;width:200px;font-size:8px">
                                                                <#if coupon.remark?length gt 91>${coupon.remark?substring(0,91)!}<#else>${coupon.remark}</#if>
                                                            </p>
                                                        </div>
                                                        <input type="checkbox" disabled name="coupons"
                                                               value="${coupon.code}" rel="${coupon.amount}"
                                                               class="coupon-checkbox display-none"/>
                                                    </div>
                                                </#if>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        <#else>
                            <div class="nocoupon">暂无优惠券</div>
                        </#if>
                    </td>
                </tr>
                <tr class="no-border item">
                    <td colspan="4" style="padding-top:60px;padding-left:0">
                        <div class="order-invoice"><p><strong>发票使用</strong></p></div>
                        <div class="nocoupon"><span class="invoice-content">暂不需要</span><a href="javascript:;"
                                                                                          class="invoice"
                                                                                          style="padding-left:20px;font-size:16px;color:#5591D0">修改</a>
                        </div>
                        <input type="hidden" name="drawee" value=""/>
                    </td>
                </tr>
                <tr class="item">
                    <td colspan="3" class="text-right" style="line-height:200%;border:none;">
                        <p>商品总额：</p>
                        <p class="grey">满减优惠：</p>
                        <p class="grey">优惠券：</p>
                        <p class="grey">运费：</p>
                        <p>应付总额：</p>
                    </td>
                    <td class="text-right" style="line-height:200%;border:none;">
                        <p>&yen; <strong id="total-amount"
                                         rel="${order.totalAmount+order.promotionAmount}">${(order.totalAmount+order.promotionAmount)?string("currency")?substring(1)}</strong>
                        </p>
                        <p class="grey"><#if order.promotionAmount gt 0>－</#if>&yen; <strong id="promotion-amount"
                                                                                             rel="${order.promotionAmount}">${(order.promotionAmount)?string("currency")?substring(1)}</strong>
                        </p>
                        <p class="punctuation grey">&yen; <strong id="coupon-amount" rel="">0</strong></p>
                        <p class="grey"><#if order.promotionAmount gt 0>＋</#if>&yen; <strong id="ship-fee"
                                                                                             rel="${order.shippingRates}">${order.shippingRates?string("currency")?substring(1)}</strong>
                        </p>
                        <p>&yen; <strong id="total-price"
                                         rel="${order.totalAmount+order.shippingRates}">${(order.totalAmount+order.shippingRates)?string("currency")?substring(1)}</strong>
                        </p>
                    </td>
                </tr>
                </tbody>
                <tfoot>
                <tr class="text-right item" style="line-height:200%;background:#f8f8f8">
                    <td colspan="4" class="text-right order-tip" style="padding:10px 10px 0 10px"
                        <#if cod==0>data-cod="false"</#if> >
                        <#if cod==0><p style="color:red;clear:both;margin-top:10px" id="order-tips">
                            本订单为定制商品或特殊商品,或收货地址为非中国大陆地区,故不支持货到付款方式</p></#if>
                        <input type="hidden" name="cod" value="${cod}"/>
                        <input type="hidden" name="longitude" value=""/>
                        <input type="hidden" name="latitude" value=""/>
                        <button type="button" style="margin-top:10px" name="" id="order-confirm"
                                class="button button-red button-xl finalpay float-right">提交订单
                        </button>
                        <div class="float-right" style="margin-top:10px;margin-right:10px;">
                            <p id="pay-price-wrap" style="margin:0;line-height:30px">需支付金额：&yen; <strong id="pay-price"
                                                                                                         class="important level-one"><#if order.totalAmount+order.shippingRates lte 0>0<#else>${(order.totalAmount+order.shippingRates)?string("currency")?substring(1)}</#if></strong>
                            </p>
                            <p style="margin:0;line-height:20px;color:#999;"><span>收货信息：</span><span
                                        class="order-address"></span></p> &nbsp; &nbsp;
                        </div>

                    </td>
                </tr>
                </tfoot>
            </table>
        </div>
    </form>
</div>
<script id="address-post-template" type="text/html">
    <div class="form">
        <h2 class="pop-title">收货地址管理</h2>
        <form name="address-form" class="validate-form form-cinfo" call-back="{{func}}" action="{{url}}" method="post">
            <input type="hidden" name="id" value="{{id}}"/>
            <div class="form-item">
                <label><span class="red">*</span>收货人</label>
                <input type="text" name="name" id="name" value="{{name}}" class="input"/>
                <div class="tip tip-validate" data-target="name" data-rule="byte"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>所在地区</label>
                <span id="area-selector" class="" data-child="select" data-msg="省份城市必须选择">
                <select name="regionPrefix" id="province" rel="{{regionPrefix}}" class="input province"
                        style="width:130px;">
                    <option value="">选择省份</option> 
                    </select> 
                    <select name="regionMiddle" id="city" rel="{{regionMiddle}}" style="width:100px;"
                            class="input city">
                    <option value="">选择城市</option> 
                    </select> 
                    <select name="regionSuffix" id="district" rel="{{regionSuffix}}" style="width:100px;"
                            class="input district">
                    <option value="">选择区县</option> 
                    </select>
                </span>
                <div class="tip tip-validate" data-target="province|city|district"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>街道地址</label>
                <textarea name="street" cols="45" rows="2" id="street" class="input" minlength="5" maxlength="60">{{street}}</textarea>
                <div class="tip tip-validate" data-target="street"></div>
            </div>

            <div class="form-item">
                <label><span class="red">*</span>手机号码</label>
                <input type="text" name="mobile" id="mobile" maxlength="20" value="{{mobile}}" class="input"
                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
            </div>
            <div class="form-item">
                <label>电话号码</label>
                <input type="text" name="phonePrefix" id="phonePrefix" maxlength="4" value="{{phonePrefix}}"
                       style="width:10%;" class="input utilSetNumber" placeholder="区号"/> - <input type="text"
                                                                                                  name="phoneMiddle"
                                                                                                  id="phoneMiddle"
                                                                                                  maxlength="8"
                                                                                                  value="{{phoneMiddle}}"
                                                                                                  style="width:20%;"
                                                                                                  class="input utilSetNumber"
                                                                                                  placeholder="电话号码"/> -
                <input type="text" maxlength="5" name="phoneSuffix" value="{{phoneSuffix}}" style="width:15%;"
                       class="input" placeholder="分机"/>
            </div>
            <div class="form-button">
                <input type="submit" value="确定" class="button"/>
            </div>
        </form>
    </div>
</script>
<script id="address-item-template" type="text/html">
    <div class="address-item{{if isdefault}} on{{/if}}" data-id="{{id}}">
        <p class="first-first"><span class="first-f"><strong>{{name}}</strong>&nbsp;&nbsp;&nbsp;&nbsp;{{province.name}}{{city.name}}</span>{{if
            isdefault}}<span class="is-default red" rel="{{id}}">【默认】</span>{{else}}<span class="set-default"><a
                        href="javascript:" rel="{{id}}" id="tip{{id}}" class="grey">【设为默认】</a></span>{{/if}}</p>
        <p class="second-line"><span class="address-value">{{district.name}}{{street}}</span> <br/>{{mobile}}</p>
        <input type="radio" name="addressId" value="{{id}}" {{if isdefault}} checked{{/if}} class="display-none" />
        <i class="fa check"></i>
    </div>
</script>

<script id="invoice-template" type="text/html">
    <div class="form">
        <style>
            .input::-webkit-input-placeholder {
                color: #222;
            }

            .input::-ms-input-placeholder {
                color: #222;
            }

            .input:focus, .new-input:active {
                border: 1px solid #FD555D
            }
        </style>
        <div class="form-info" style="font-size:14px;">
            <div class="form-item" style="padding:5px 0 5px 0">
                <label>开具发票</label> <span class="checkbox"><input type="checkbox" name="checkall" checked
                                                                  class="checkbox" id="checkbox"/><label for="checkbox"
                                                                                                         style="background:url(//static.d2c.cn/common/nc/css/img/blue_checked.png) top left no-repeat;width:16px;height:16px;background-size:cover;border:none;margin-top:1px;"> </label></span>
            </div>
            <div class="form-item" style="padding:5px 0 5px 0">
                <label>发票类型</label><span>普通发票</span>
            </div>
            <div class="form-item" style="padding:5px 0 5px 0">
                <label>发票抬头</label> <input type="text" name="mydrawee" value="{{data}}" class="input"
                                           placeholder="请输入个人 / 公司抬头"/>
            </div>
            <div class="form-item" style="padding:5px 0 5px 0;color:#999">
                <p>发票须知</p>
                <p>1.D2C目前支持开具纸质增值税普通发票(“简称普通发票”)</p>
                <p>2.发票的大类别为服装</p>
                <p>3.发票会在确认收货后7-30天内寄出（如申请售后须等待售后处理完毕）</p>
                <p>4.发票默认寄送到购买商品的收货地址，如需更改邮寄地址，请联系我们在线客服</p>
            </div>
        </div>
        <div style="margin:10px 0  50px 0">
            <button class="button button-red button-s" style="margin-right:20px;" id="invoice-sure">保存</button>
            <button class="button button-s button-grey" class="invoice-cancel"
                    onclick="$('.modal-remove').trigger('click');">取消
            </button>
        </div>
    </div>
</script>


<script type="text/javascript" src="${static_base}/nc/js/modules/page.order.js?t=${refreshTimeStamp}"></script>

<script>

    $(document).on('click', '#invoice-sure', function () {
        var val = $("input[name='mydrawee']").val();
        if (val != "" && val.length < 50) {
            $('.invoice-content').text(val);
            $("input[name='drawee']").val(val);
            $('.modal-remove').trigger('click');
        } else if (val.length > 50) {
            $.flashTip('抬头控制在50字以内', 'error');
        } else {
            $('.invoice-content').text('暂不需要');
            $("input[name='drawee']").val('');
            $('.modal-remove').trigger('click');
        }
    })


    $('.invoice').click(function () {
        var data = $("input[name='drawee']").val();
        var html = template('invoice-template', {"data": data});
        $.popModal({content: html, 'width': 500, 'background': '#FFF', 'title': '发票信息'});
        return false;

    })


    var checkAddress = function () {
        if ($('input[name=addressId]:checked').size() == 0) {
            return '请选择地址或新增一个地址';
        } else {
            return true;
        }
    }
    $('#change-tab a').click(function () {
        var type = $(this).attr('data-type');
        $(this).addClass('selected');
        $(this).siblings().removeClass('selected');
        $('#coupons-' + type).show();
        $('#coupons-' + type).siblings().hide();
        return false;
    });

    $('.order-coupon-item .direction').click(function () {
        var obj = $(this);
        if (obj.attr('data-type') == "right") {

            obj.siblings('.direction-info').show();
            obj.siblings('.direction-info').animate({width: "220px"}, 500, function () {
                obj.attr('data-type', 'left');
            });
        } else {
            obj.siblings('.direction-info').animate({width: "0"}, 500, function () {
                obj.siblings('.direction-info').hide();
                obj.attr('data-type', 'right');
            });
        }
    })


</script>
<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'orderConfirm',
        productId_list: '<#if order ><#list orderItems?if_exists as orderItem>${orderItem.productId},</#list></#if>',
        totalPrice: '<#if order >${(order.totalAmount+order.shippingRates)}</#if>',
        totalNum: '${orderItems?size}'
    };
</script>


<div id="footer" class="footer lazyload">
    <div class="qrcode">
        <div class="qrcode-img">
            <img src="//static.d2c.cn/other/qrode/img/download.png" width="950" height="188" alt="">
            <img src="//static.d2c.cn/other/qrode/img/qrcode_nav.png" width="150" height="150" alt="" class="nav">
            <img src="//static.d2c.cn/other/qrode/img/qrcode_wechat.png" width="150" height="150" alt="" class="wechat">
        </div>
    </div>
    <div class="footer-menu">
        <div class="layout-response">
            <div class="clearfix">
                <div class="border-right">
                    <dl>
                        <dt><i class="footer-nav"></i>服务中心</dt>
                        <dd class="first-line">服务热线：<span style="color:#FD555D">${servertel}</span></dd>
                        <dd> 服务时间：工作日 9:00-24:00</dd>
                    </dl>
                    <dl>
                        <dt><i class="footer-nav"></i>帮助中心 </i></dt>
                        <dd class="first-line"><a href="${base}/page/faq_shipping" target="_blank"
                                                  rel="nofollow">购物流程</a></dd>
                        <dd><a href="${base}/page/faq_shipments" target="_blank" rel="nofollow">发货说明</a></dd>
                        <dd><a href="${base}/page/faq_coupon" target="_blank" rel="nofollow">优惠说明</a></dd>
                        <dd><a href="${base}/page/faq_payment" target="_blank" rel="nofollow">支付方式</a></dd>
                    </dl>
                    <dl>
                        <dt><i class="footer-nav"></i>关于我们</dt>
                        <dd class="first-line"><a href="${base}/page/about" target="_blank" rel="nofollow">D2C介绍</a>
                        </dd>
                        <dd><a href="${base}/page/job" target="_blank" rel="nofollow" style="color:#FD555D">招贤纳士</a>
                        </dd>
                        <dd><a href="${base}/store/list" target="_blank" rel="nofollow">D2C实体店</a></dd>
                        <dd><a href="${base}/page/contact" target="_blank" rel="nofollow">联系我们</a></dd>
                    </dl>
                    <div class="footer-promise">
                        <div class="footer-promise-item">
                            <span class="freedelivery"></span>
                            <p>满299顺丰包邮</p>
                        </div>
                        <div class="footer-promise-item">
                            <span class="refund"></span>
                            <p>七天无理由退货</p>
                        </div>
                        <div class="footer-promise-item">
                            <span class="fitting"></span>
                            <p>免费门店试衣</p>
                        </div>
                        <div class="footer-promise-item">
                            <span class="genuineproduct"></span>
                            <p>授权正品</p>
                        </div>
                    </div>
                </div>
                <dl class="d2c-email">
                    <dt style="margin-left:35px;"><img src="${static_base}/c/images/space.gif"
                                                       data-image="//static.d2c.cn/other/qrode/img/d2c_email.png"
                                                       width="254" height="52" alt="订阅电子报"/></dt>
                    <dd class="none subscribe-email">
                        <form name="subscribe-email" class="validate-form" success-tip="恭喜，您成功订阅了我们的邮件！"
                              call-back="false" action="/subscribe/email" method="post">
                            <input type="text" name="email" id="subscribe-email" value="" placeholder="请输入邮箱"/>
                            <button type="submit">订阅</button>
                            <div class="tip tip-validate" data-target="subscribe-email"></div>
                        </form>
                    </dd>
                    <dd class="none service">
                        <img src="${static_base}/c/images/space.gif"
                             data-image="//static.d2c.cn/other/qrode/img/wechat_service.jpg" alt="微信服务号:d2cmall"
                             width="150" height="150"/>
                        <p>服务号：d2cmall</p>
                    </dd>
                    <dd class="none service-detail">
                        <p><strong>设计师品牌入驻及艺人造型剧组合作</strong></p>
                        <p>Email：media@d2cmall.com</p>
                        <p>在线申请(online application)：<a href="/brandapply" target="_blank" style="color:#fc668f;"
                                                       rel="nofollow">立即申请</a></p>
                        <p style="margin-top:18px;"><strong>投诉与建议(Feedback)</strong>
                        <p>
                        <p>CEO Email：ceo@d2cmall.com
                        <p>
                    </dd>
                    <dd class="none clearfix share-weibo">
                        <a href="//weibo.com/myd2c" target="_blank"><span></span>
                            <p>关注D2C官方微博</p></a>
                    </dd>
                </dl>
            </div>
            <div class="footer-copyright">
                <div class="copyright-info">
                    <p style="margin-right:75px;text-align:right;">Powered byD2C-Copyright © 2015 D2CMALL.COM - All
                        Rights Reserved.</p>
                    <p style="text-align:left;">杭州迪尔西时尚科技有限公司 &nbsp;联系电话：${servertel}</p>
                    <p style="margin-right:75px;text-align:right;">增值电信业务经营许可证：浙B2-20150236&nbsp;浙ICP备12034937号-1</p>
                    <p style="text-align:left;">本网站用字经北京北大方正电子有限公司授权许可</p>
                    <p style="text-align:right;"><a target="_blank"
                                                    href="//www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010602004682"
                                                    style="display:inline-block;text-decoration:none;padding-left:5px;color:#666;"><img
                                    src="//static.d2c.cn/img/home/160627/images/police.png" style="float:left;"/>浙公网安备
                            33010602004682号</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
<#if nofloat?length == 0>
    <div class="service-suspend">
        <div class="service-content service-phone"></div>
        <div class="service-content text-info customer" onclick="ysf.open();"><a href="javascript:;">客服</a></div>
        <div class="service-content text-info cart"><a href="/cart/list">购物车</a></div>
        <div class="service-content text-info collection"><a href="/member/interest/collection/list">收藏</a></div>
        <div class="service-content text-info order"><a href="/member/order">订单</a></div>
        <!--<div class="wpa" style="width:40px;height:80px;" width="40" height="80"><script src="//wpa.b.qq.com/cgi/wpa.php?key=XzkzODAyNTAxM18zMTQ1MThfNDAwODQwMzY2Nl8"></script></div>-->
        <div class="service-phone-detail">
            ${servertel}
        </div>
    </div>
</#if>
<script src="https://qiyukf.com/script/cdf7ede373825ab5af9d955d1fb3556c.js?uid=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.id}</#if>&name=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.displayName}</#if>"
        defer async></script>
<script src="https://webapi.amap.com/maps?v=1.4.8&key=4fc7a9efb33c5f84584ae8d78d5473ef&plugin=AMap.Geocoder"></script>
<script>
    //提交订单
    $('#order-confirm').click(function () {
        addParams();
        setTimeout(function () {
            $('#order-form').submit();
        }, 800);
    });

    //添加地址经纬度
    function addParams() {
        var geocoder = new AMap.Geocoder({
            city: '全国'// city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
        });
        var addressVal = $('.address-item.on .second-line .address-value').text();//这里是需要转化的地址
        geocoder.getLocation(addressVal, function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
                //result为对应的地理位置详细信息
                var locationVal = result.geocodes[0],
                    longitudeVal = locationVal.location.lng,
                    latitudeVal = locationVal.location.lat;
                $('input[name=longitude]').val(longitudeVal);
                $('input[name=latitude]').val(latitudeVal);
            }
        })
        return true;
    }
</script>
<div class="display-none">
    <script type="text/javascript">
        (function () {
            if (browser.versions.iPad) {
                $('a,area').removeAttr('target');
            }
            /*baidu*/
            var hm = document.createElement("script");
            hm.src = "//hm.baidu.com/hm.js?ea7e967d9c7781daffcad278586bf28f";
            var bds = document.getElementsByTagName("script")[0];
            bds.parentNode.insertBefore(hm, bds);
            /*pinyou*/
            if ("_goodsData" in window && window._goodsData != "undefined") {
                _py.push(['pi', _goodsData]);
            }
            -function (d) {
                var s = d.createElement('script'),
                    e = d.body.getElementsByTagName('script')[0];
                e.parentNode.insertBefore(s, e),
                    f = 'https:' == location.protocol;
                s.src = (f ? 'https' : 'http') + '://' + (f ? 'fm.ipinyou.com' : 'fm.p0y.cn') + '/j/adv.js';
            }(document);

            /*jingzan*/
            (function (param) {
                var c = {query: [], args: param || {}};
                c.query.push(["_setAccount", "636"]);
                (window.__zpSMConfig = window.__zpSMConfig || []).push(c);
                var zp = document.createElement("script");
                zp.type = "text/javascript";
                zp.async = true;
                zp.src = ("https:" == document.location.protocol ? "https:" : "http:") + "//cdn.zampda.net/s.js";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(zp, s);
            })(window.__zp_tag_params);

            /*
            boya
            */
            function convertclk(_traker) {
                //monitor click
            }

            var _doc = document.getElementsByTagName('head')[0];
            var script = document.createElement('script');
            script.setAttribute('type', 'text/javascript');
            //CA 标准监测代码
            script.setAttribute('src', '//ca.cubead.com/cubead_ca.js?seq=' + Math.floor(Math.random() * (99999999 + 1)));
            _doc.appendChild(script);
            script.onload = script.onreadystatechange = function () {
                if ((!this.readyState) || (this.readyState == 'loaded') || (this.readyState == 'complete')) {
                    try {
                        _traker._init("d2cmall.com", "209502");
                        if (typeof (convertclk) == "function") {
                            convertclk(_traker);
                        }
                        if (typeof (_ca_convert) == "function") {
                            _ca_convert(_traker);
                        }
                    } catch (e) {
                    }
                    script.onload = script.onreadystatechange = null;
                }
            }
        })();
    </script>
    <script>
        var t;
        var path = location.pathname;
        if (path.indexOf('/cart') == '-1' && path.indexOf('/order/confirm') == '-1' && path.indexOf('/order/buynow') == '-1') {
            $('.cart').hover(function () {
                var obj = $(this);
                if ($('.template-m li').size() == 0) {
                    if ($(this).attr('data-type') == 'login') {
                        hoverCart();
                    }
                }
                setTimeout(function () {
                    if ($('.template-m li').size() >= 4) {
                        $('.template-m ul').addClass('height');
                    } else {
                        $('.template-m ul').removeClass('height');
                    }
                    obj.find('.template-m').show();
                }, 200);
            }, function () {
                $(this).find('.template-m').hide();
            })
        }

        function checkLength() {
            var value = $('textarea[name=memo]').val();
            if (value.length > 100) {
                $('textarea[name=memo]').val(value.substr(0, 100));
            }
        }


        //展开我的收藏
        $('.user-collect').hover(function () {
            $(this).find('.collection-detail').show();
        }, function () {
            $(this).find('.collection-detail').hide();
        })

    </script>

</div>
</body>
</html>