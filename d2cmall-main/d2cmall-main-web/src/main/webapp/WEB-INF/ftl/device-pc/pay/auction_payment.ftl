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
        <script type="text/javascript" src="${static_base}/nc/js/utils/md5.js?t=${refreshTimeStamp}"></script>
    <#else>
        <script type="text/javascript" src="${static_base}/nc/js/compress.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/md5.js?t=${refreshTimeStamp}"></script>
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
                        购物车(<span id="cart-nums-id">0</span>)</a><#if LOGINMEMBER.id!=null><i>｜</i></#if></li>
                <#if LOGINMEMBER.id!=null>
                    <li><a href="/member/logout">退出</a></li></#if>
            </ul>
        </div>
        <div class="layout-response clearfix">
            <div class="header-bar"></div>
            <div class="header-logo"><a href="//www.d2cmall.com">D2C全球好设计</a></div>
            <div class="header-ad" id="header-animotion-ad"></div>
            <div class="cart-step step3">

            </div>
        </div>

    </div>
</header>
<div class="layout layout-response layout-order">
    <form name="pay-form" id="pay-form" action="/payment" method="post" style="padding:15px;">
        <#assign auction=result.datas.auctionMargin/>
        <input type="hidden" name="id" value="${auction.id}"/>
        <input type="hidden" name="sn" value="${(auction.marginSn)}"/>
        <input type="hidden" name="orderType" value="margin"/>
        <div class="pay-order-detail clearfix" style="text-align:center">
            <h1 style="marin:10px 0;">宝贝马上到手，快选择支付方式吧！</h1>
            <p>请在<span class="red">24小时</span>内支付保证金，否则无法参与拍卖！</p>
            <p class="grey" style="margin-top:8px;">应付金额：<span class="display-none"
                                                               id="total-price">${(auction.billTotalFee)}</span><span
                        class="red" style="font-size:24px;font-weight:bold;"
                        id="pay-price">${(auction.billTotalFee)?string("currency")?substring(1)}</span></p>
            <div class="order-info">
                <p class="float-right">${auction.createDate?string("yyyy.MM.dd HH:mm")!""}</p>
            </div>
        </div>
        <h2 class="order-title" style="font-size:16px;border:none;"> 支付方式</h2>
        <div class="payment-div">
            <ul>
                <li class="payment-li on">
                    <p class="commend-pay">推荐支付方式</p>
                    <!-- <li class="payment-li on"><label class="payment-li-label" for="UNIONPAY"><input type="radio" id="UNIONPAY" name="paymentType" value="UNIONPAY" checked="checked" /> 银联支付 &nbsp;&nbsp;&nbsp;需开通网银或支付宝。</label></li> -->
                    <ul class="alipay clearfix">
                        <input type="radio" id="ONLINE" name="type" value="ONLINE" checked="checked"
                               class="online-pay"/>
                        <li class="pay-li" id="defult"><input type="radio" id="ALIPAY" data-type="ALIPAY" name="payment"
                                                              method="creditPay" value="ALIPAY"/><label class="ALIPAY">支付宝<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" id="WXSCANPAY" data-type="WX_SCANPAY" name="payment"
                                                  method="creditPay" value="WX_SCANPAY"/><label class="WXSCANPAY">微信扫码支付<i
                                        class="select"></i></label></li>
                        <#if account?exists && account.status==1 && account.totalAmount &gt;0>
                            <#if !account.password>
                                <li class="pay-li">
                                    <a href="/member/account/info" target="_blank" class="button button-s button-red"
                                       style="position:absolute;left:340px;top:15px;">设置支付密码</a>
                                </li>
                            <#else>
                                <#if auction.billTotalFee gt 0>
                                    <li class="pay-li" data-type="wallet">
                                        <input type="radio" id="WALLET" name="type" value="WALLET"/>
                                        <label class="WALLET"
                                               data-type="WALLET"<#if account.availableTotalAmount lt (auction.billTotalFee)> disabled</#if>>
                                            D2C钱包<i class="select"></i></label>
                                    </li>
                                    <div class="wallet-password"
                                         style="display:none;float:left;" <#if account.availableTotalAmount lt (auction.billTotalFee)> data-money="available"</#if>>

                                        <ul style="float:left;">
                                            <li class="pay-title" style="margin:0;margin-top:5px;">输入支付密码</li>
                                            <li style="float:left;margin:0 15px 0 0;"><input type="password"
                                                                                             id="before-password"
                                                                                             class="input"
                                                                                             size="10"/><input
                                                        type="hidden" name="password" id="pay-password"/></li>
                                        </ul>
                                        <div style="margin-top:20px;float:left;">
                                            钱包可用余额：<strong class="arial"
                                                           style="color:#FF3300;font-size:14px;">&yen; ${account.availableTotalAmount?string("currency")?substring(1)}</strong>
                                            <#if account.availableTotalAmount lt (auction.billTotalFee)>
                                                （余额不足以支付该订单，请选择其他方式或充值钱包）
                                            <#else> &nbsp;&nbsp;
                                                </br>支付后的余额：<strong class="arial" style="color:#FF3300;font-size:14px;">&yen; ${(account.availableTotalAmount-(auction.billTotalFee))?string("currency")?substring(1)}</strong>
                                            </#if>
                                        </div>
                                    </div>
                                </#if>
                            </#if>
                        </#if>
                    </ul>
                    <p class="bank-pay">其他支付方式<i></i></p>
                    <ul class="bank clearfix" style="padding-bottom:25px;display:none;">

                        <li class="pay-li"><input type="radio" class="COMM" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="COMM"/><label class="COMM">交通银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="ICBC" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="ICBC"/><label class="ICBC">中国工商银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="CMB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="CMB"/><label class="CMB">招商银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="CCB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="CCB"/><label class="CCB">中国建设银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="BOC" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="BOC"/><label class="BOC">中国银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="ABC" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="ABC"/><label class="ABC">中国农业银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="SPABANK" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="SPABANK"/><label class="SPABANK">平安银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="CEB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="CEB"/><label class="CEB">中国光大银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="CMBC" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="CMBC"/><label class="CMBC">民生银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="GDB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="GDB"/><label class="GDB">广发银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="CITIC" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="CITIC"/><label class="CITIC">中信银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="HZCB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="HZCB"/><label class="HZCB">杭州银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="NBBANK" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="NBBANK"/><label class="NBBANK">宁波银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="SDB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="SDB"/><label class="SDB">深发展<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="SHBANK" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="SHBANK"/><label class="SHBANK">上海银行<i
                                        class="select"></i></label></li>
                        <li class="pay-li"><input type="radio" class="SPDB" data-type="ALIPAY" name="payment"
                                                  method="bankPay" value="SPDB"/><label class="SPDB">浦发银行<i
                                        class="select"></i></label></li>
                    </ul>
                </li>
                <!-- <li class="payment-li"><label class="payment-li-label" for="OFFLINE"><input type="radio" id="OFFLINE" name="type" value="OFFLINE" /> 银行转账 &nbsp;&nbsp;&nbsp;通过银行转账或现金直接存入D2C指定帐号，需注明订单号。</label></li> -->
            </ul>
        </div>
        <input type="hidden" name="paymentType" value="ONLINE"/>
        <input type="hidden" name="paymethod" value=""/>
        <input type="hidden" name="defaultbank" value=""/>
        <div class="text-center" style="margin-top:20px;">
            <button type="button" name="order-pay" id="order-pay" data-url="/auction/member/mymargin"
                    class="button button-red button-xl finalpay" style="padding:0 40px"> 去付款
            </button>
        </div>
    </form>
</div>
<script type="text/javascript" src="${static_base}/nc/js/modules/page.order.js?t=${refreshTimeStamp}"></script>
<script type="text/javascript">
    $('.bank-pay').click(function () {
        $(this).find('i').toggleClass('up');
        $(this).siblings('.bank').toggle();

    })
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
        <div class="service-content text-info customer" onclick="NTKF.im_openInPageChat();"><a
                    href="javascript:;">客服</a></div>
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
        $('.template-m .cancel').live('click', function () {
            var obj = $(this);
            var id = obj.siblings('input').val();
            $.get('/cart/delete/' + id, function (data) {
                obj.parent().slideUp(function () {
                    obj.parent().remove();
                    countCart();
                });
            }, 'json')
            return false;
        })
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