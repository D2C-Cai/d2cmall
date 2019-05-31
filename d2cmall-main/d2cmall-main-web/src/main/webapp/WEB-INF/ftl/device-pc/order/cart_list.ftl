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
<#assign servertel="4008403666"/>
<body style="background:#fff">
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
                <#if LOGINMEMBER.id!=null>
                    <li><a href="/member/logout">退出</a></li></#if>
            </ul>
        </div>
        <div class="layout-response clearfix">
            <div class="header-bar"></div>
            <div class="header-logo"><a href="//www.d2cmall.com">D2C全球好设计</a></div>
            <div class="header-ad" id="header-animotion-ad"></div>
            <div class="cart-step step1">
            </div>
        </div>

    </div>
</header>
<div class="layout layout-response layout-cart clearfix">
    <div class="cart-empty display-none">
        <p>您的购物车还是空的，赶紧行动吧！<a href="/">返回首页</a></p>
    </div>
    <#if (cart==null || cart.typeCount lt 1)!>
        <script>
            $('.cart-empty').show();
        </script>
    <#else>
        <script>
            $('#cart-nums-id').text(${cart.typeCount});
        </script>
        <form name="form-cart" id="form-cart" action="/order/confirm" method="POST" style="padding:15px;">
            <!--
            <div class="cart-status">
              <span>购物车状态</span>
              <span class="process-bar">
                  <em id="cart-bar-percent" style="width:cart?if_exists.itemMapById?if_exists?size*2}%"></em>
              </span>
              <span><strong id="cart-bar-num">cart?if_exists.itemMapById?if_exists?size?default(0)}</strong>/50</span>
            </div>-->
            <table class="table table-lightgrey" style="margin-top:10px;">
                <thead>
                <tr>
                    <th style="text-align:left;border-right:none;" colspan="2" class="checkbox"><input type="checkbox"
                                                                                                       name="checkall"
                                                                                                       checked
                                                                                                       class="checkbox check-all"
                                                                                                       id="checkbox"/><label
                                for="checkbox"> </label>全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;宝贝信息
                    </th>
                    <th width="14%" style="border-right:none;border-left:none;">优惠活动</th>
                    <th width="10%" style="border-right:none;border-left:none;">单价（元）</th>
                    <th width="15%" style="border-right:none;border-left:none;">数量</th>
                    <th width="8%" style="border-right:none;border-left:none;">小计（元）</th>
                    <th width="8%" style="border-left:none;">操作</th>
                </tr>
                </thead>
                <tbody>
                <tr class="space">
                    <td colspan="6"></td>
                </tr>
                <#list cart?if_exists.itemMapById?if_exists?values as cartItem>

                    <#if cartItem.productSku?exists>
                        <tr class="item" order-id="${cartItem.id}">
                            <td class="text-center border-r-none checkbox">
                                <input type="checkbox" name="cartItemId" value="${cartItem.id}"
                                       id="${cartItem.id}" <#if (cartItem.productSku.availableStore gt 0) && (!cartItem.over)> checked<#else> disabled</#if>
                                       class="left"/><label for="${cartItem.id}"></label>
                            </td>
                            <td class="text-left border-l-none border-r-none">
                                <input type="hidden" name="skuId" value="${cartItem.productSkuId}"/>
                                <p class="promotion-name"><span class="one"></span><span class="three"></span><span
                                            class="two"></span></p>
                                <p class="img"><a href="/product/${cartItem.productId}" target="_blank"><img
                                                src="${picture_base}/${cartItem.sp1?eval.img}!80"
                                                alt="${cartItem.productName}" height="90"/></a></p>
                                <p><a href="/product/${cartItem.productId}"
                                      target="_blank"><#if cartItem.flashPromotion?exists && !cartItem.productPromotion.flashPromotionOver>
                                            <span style="color:#FD555D">【限时购】</span></#if>${cartItem.productName} </a>
                                    <!--<span class="tags-tip" title="新品首发的商品请在一小时内提交订单，否则系统将自动移出购物车。">首发</span>--></p>
                                <p class="grey">${cartItem.sp1?eval.name}：${cartItem.sp1?eval.value}
                                    &nbsp;&nbsp;&nbsp; <#if cartItem.sp2>${cartItem.sp2?eval.name}：${cartItem.sp2?eval.value} &nbsp;&nbsp;&nbsp;</#if>
                                    设计师：${cartItem.designerName} &nbsp;&nbsp; <#if cartItem.after==0><span class="red">本商品不支持七天无理由退换货</span></#if>
                                </p>
                            </td>
                            <td class="text-left border-l-none border-r-none">
                                <input type="hidden" name="goodPromotionId" value="${cartItem.goodPromotionId}"/></span>
                                <br/>
                                <input type="hidden" name="orderPromotionId"
                                       value="${cartItem.orderPromotionId}"/></span>
                                <#if cartItem.choiceOrderPromotions?exists && cartItem.choiceOrderPromotions?size gt 1>
                                    <select name="selectOrderPromotionId" class="input-s" style="width:100%">
                                        <#list cartItem.choiceOrderPromotions as orderPromotion>
                                            <option value="${orderPromotion.id}"
                                                    <#if cartItem.orderPromotionId==orderPromotion.id>selected="selected"</#if>>${orderPromotion.name}</option>
                                        </#list>
                                    </select>
                                </#if>
                            </td>
                            <td class="text-center border-l-none border-r-none">
                                <p class="unit price original-l" data-price="${cartItem.originalPrice}">
                                    &yen;${cartItem.originalPrice?string("currency")?substring(1)}</p>
                                <p><strong class="unit price cut-price"></strong></p>
                            </td>
                            <td class="text-center border-l-none border-r-none">
                                <#if cartItem.over>
                                    <span class="num-change">-</span>
                                    <input type="text" name="quantity" value="0" size="2"
                                           class="input disabled text-center noime"/>
                                    <span class="num-change">+</span>
                                    <div class="tip">该件商品已经下架</div>
                                <#elseif (cartItem.productSku.availableStore lte 0 && cartItem.productSku.freezeStore gt 0)>
                                    <span class="num-change">-</span>
                                    <input type="text" name="quantity" value="${cartItem.productSku.availableStore}"
                                           size="2" maxlength="3" class="input disabled text-center noime"
                                           readonly="readonly"/>
                                    <span class="num-change">+</span>
                                    <div class="tip">还有${cartItem.productSku.freezeStore}件未付款，要抓紧哟</div>
                                <#elseif (cartItem.productSku.availableStore lte 0)>
                                    <span class="num-change">-</span>
                                    <input type="text" name="quantity" value="0" size="2"
                                           class="input disabled text-center noime"/>
                                    <span class="num-change">+</span>
                                    <div class="tip">该件商品已经卖完了</div>
                                <#elseif (!cartItem.isMeetInventory(cartItem.productSku.availableStore))>
                                    <a class="surplus" data-rule="${cartItem.productSku.availableStore}"
                                       style="display:none;visible:hidden" href="javascript:"></a>
                                    <a href="javascript:" data-type="decrease" class="num-change">-</a>
                                    <input type="text" name="quantity" value="${cartItem.quantity}" size="2"
                                           maxlength="3" class="input text-center noime" readonly="readonly"/>
                                    <a href="javascript:" data-type="increase" class="num-change">+</a>
                                    <div class="tip">只能购买${cartItem.productSku.availableStore}件</div>
                                <#else>
                                    <a class="surplus" data-rule="${cartItem.productSku.availableStore}"
                                       style="display:none;visible:hidden" href="javascript:"></a>
                                    <a href="javascript:" data-type="decrease" class="num-change">-</a>
                                    <input type="text" name="quantity" value="${cartItem.quantity}" size="2"
                                           maxlength="3" class="input text-center noime" readonly="readonly"/>
                                    <a href="javascript:" data-type="increase" class="num-change">+</a>
                                </#if>
                            </td>
                            <td class="text-center promotion-td border-l-none border-r-none">
                                <strong class="total"></strong>
                            </td>
                            <td class="text-center border-l-none">
                                <!-- <a href="/product/collection/${cartItem.productId}" data-id="${cartItem.productId}" class="add-action" rel="fav">加入收藏</a><br /><br /> -->
                                <p><a href="javascript:" data-url="/cart/delete/${cartItem.id}" method-type="get"
                                      confirm="确定要从购物车中移除该商品吗？" call-back="remove(${cartItem.id})" class="ajax-request">删除</a>
                                </p>
                            </td>
                        </tr>
                    </#if>
                </#list>
                </tbody>
                <tfoot>
                <tr class="space">
                    <td colspan="6"></td>
                </tr>
                <tr class="item">
                    <td class="text-left border-r-none relative" colspan="2" style="padding:15px 10px 13px 10px">
                        <button type="button" name="clear" class="button clear order" style="font-size:16px;"/>
                        清空购物车</button>
                    </td>
                    <td class="text-right border-l-none  relative" colspan="5" style="padding:15px 170px 13px 10px">已选商品
                        <span class="important level-two select-count-number"></span> 件
                        &nbsp;&nbsp;&nbsp;商品总价：&yen; <strong class="important level-one totle-price"></strong>
                        &nbsp;&nbsp;&nbsp;
                        <button type="submit" name="pay" id="cart-pay" class="button order pay">立即结算</button>
                    </td>
                </tr>
                </tfoot>
            </table>
        </form>
    </#if>
</div>

<script type="text/javascript" src="${static_base}/nc/js/modules/page.order.js?t=${refreshTimeStamp}"></script>
<script type="text/javascript">

    var remove = function (id) {
        $('tr.item[order-id=' + id + ']').remove();
        if ($('tr.item').size() <= 1) {
            window.location = "/cart/list";
        }
        cartInit();

    }

    !function (w, d, e) {
        var _money = '';
        var _productList = '<#list cart?if_exists.itemMapById?if_exists?values as cartItem>${cartItem.productId},${cartItem.quantity};</#list>';
        var b = location.href, c = d.referrer, f, s, g = d.cookie, h = g.match(/(^|;)\s*ipycookie=([^;]*)/),
            i = g.match(/(^|;)\s*ipysession=([^;]*)/);
        if (w.parent != w) {
            f = b;
            b = c;
            c = f;
        }
        ;u = '//stats.ipinyou.com/cvt?a=' + e('9U.tB.xSXrzPTx4cBWjYdyVMd1wX') + '&c=' + e(h ? h[2] : '') + '&s=' + e(i ? i[2].match(/jump\%3D(\d+)/)[1] : '') + '&u=' + e(b) + '&r=' + e(c) + '&rd=' + (new Date()).getTime() + '&Money=' + e(_money) + '&ProductList=' + e(_productList) + '&e=';

        function _() {
            if (!d.body) {
                setTimeout(_(), 100);
            } else {
                s = d.createElement('script');
                s.src = u;
                d.body.insertBefore(s, d.body.firstChild);
            }
        }

        _();
    }(window, document, encodeURIComponent);

</script>
<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'cart',
        productId_list: '<#list cart?if_exists.itemMapById?if_exists?values as cartItem>${cartItem.productId},</#list>',
        totalPrice: '',
        totalNum: '<#if cart>${cart.typeCount}</#if>'
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