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
            <div class="cart-step step2">

            </div>
        </div>

    </div>
</header>
<div class="layout layout-response layout-order">
    <form action="/auction/margin/update/${auctionMargin.id}" method="post" class="validate-form"
          call-back="auctionConfirm" style="padding:15px;">

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
                        <p class="second-line">${(address.district.name)!}${address.street}
                            &nbsp;&nbsp;<br/>${address.mobile}</p>
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
            <h2 class="order-title" style="margin-bottom:20px">购买清单</h2>
            <table class="table table-lightgrey">
                <thead>
                <tr>
                    <th class="text-center" style="border-right:none;">宝贝信息</th>
                    <th width="200" style="border-right:none;border-left:none;">竞拍得标价</th>
                    <th width="200" style="border-right:none;border-left:none;">拍卖保证金</th>
                    <th width="150" style="border-left:none;">小计（元）</th>
                </tr>
                </thead>
                <tbody>
                <tr class="item">
                    <td class="text-left info  border-r-none">
                        <p class="img" style="margin-top:2px;"><img
                                    src="${picture_base}/${(product.product.productImageCover)!}!80"
                                    alt="${product.title}" height="90"/></p>
                        <p><a href="/product/${product.id}" target="_blank">${product.title}</a>
                    </td>
                    <td class="text-center border-l-none border-r-none">
                        ${product.currentPrice?string("currency")?substring(1)}
                    </td>
                    <td class="text-center border-l-none border-r-none">
                        ${product.margin?string("currency")?substring(1)}
                    </td>
                    <td class="text-center border-l-none ">

                    </td>
                </tr>
                </tbody>
                <tfoot>
                <tr class="text-right item" style="line-height:200%;background:#f8f8f8">
                    <td colspan="4" class="text-right" style="padding:10px 10px 0 10px">
                        <button type="submit" style="margin-top:10px" name="" id="auction-confirm"
                                class="button button-red button-xl finalpay float-right">提交订单
                        </button>
                        <div class="float-right" style="margin-top:10px;margin-right:10px;">
                            <p id="pay-price-wrap" class="desc" data-sn="${auctionMargin.marginSn}"
                               style="margin:0;line-height:30px">需支付金额：&yen; <strong id="pay-price"
                                                                                     class="important level-one">${(auctionMargin.billTotalFee)}</strong>
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
        <p class="first-first"><span class="first-f"><strong>{{name}}</strong>{{province.name}}{{city.name}}</span>{{if
            isdefault}}<span class="is-default red" rel="{{id}}">【默认】</span>{{else}}<span class="set-default"><a
                        href="javascript:" rel="{{id}}" id="tip{{id}}" class="grey">【设为默认】</a></span>{{/if}}</p>
        <p class="second-line">{{district.name}}{{street}} <br/>{{mobile}}</p>
        <input type="radio" name="addressId" value="{{id}}" {{if isdefault}} checked{{/if}} class="display-none" />
        <i class="fa check"></i>
    </div>
</script>
<script type="text/javascript" src="${static_base}/nc/js/modules/page.order.js?t=${refreshTimeStamp}"></script>
<script>

    var checkAddress = function () {
        if ($('input[name=addressId]:checked').size() == 0) {
            return '请选择地址或新增一个地址';
        } else {
            return true;
        }
    }
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
    <script type="text/javascript">
        var auctionConfirm = function () {
            var data = $('body').data('return_data');
            var auctionSn = $('.desc').attr('data-sn');
            var params = data.result.datas.params;
            location.href = '/payment/prepare/margin/' + auctionSn + '?' + params;
        }

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