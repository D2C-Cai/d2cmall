<#macro page_header title='' css='' extcss='' js=''  keywords='' description=''>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "//www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <#assign refreshTimeStamp=renderStaticTimeStamp()/>
    <#assign LOGINMEMBER=loginMember()/>
    <html xmlns="//www.w3.org/1999/xhtml">
    <head>
        <title><#if title?length gt 0>${title} - </#if>【D2C全球好设计】_汇集全球好设计,寻找您专属的原创新品- D2CMall.COM</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="keywords"
              content="<#if keywords==''>d2c官网,D2C,D2CMALL,服装设计,服装设计师,时装设计,服装品牌设计,时尚设计,时装设计师<#else>${keywords},全球好设计,设计师品牌,D2C,D2CMALL,潮流风尚,前沿艺术,个性设计,时装设计师平台,国际新锐设计师品牌,买手店,原创设计</#if>"/>
        <meta name="description"
              content="<#if description==''>D2C是一家集潮流风尚、个性设计为一体的设计师集成平台，致力于为消费者推荐全球优秀的设计师品牌，除设计师品牌限量销售外，更有私人定制服务，同时在中国各大城市开设实体店。<#else>${description},全球好设计</#if>"/>
        <meta http-equiv="imagetoolbar" content="no"/>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
        <meta property="data-title"
              content="<#if keywords==''>d2c官网,D2C,D2CMALL,服装设计,服装设计师,时装设计,服装品牌设计,时尚设计,时装设计师<#else>${keywords},全球好设计,D2C,D2CMALL,潮流风尚,前沿艺术,个性设计,时装设计师平台,国际新锐设计师品牌,原创设计</#if>"/>
        <meta property="qc:admins" content="3624416613642351446375"/>
        <meta property="wb:webmaster" content="cbc4e89c163dc6cf"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <link rel="apple-touch-icon" href="//static.d2c.cn/common/m/img/ic_launcher.png"/>
        <link rel="apple-touch-startup-image" media="screen and (orientation: portrait)"
              href="//static.d2c.cn/ipad/img/system/start_up_p.png"/>
        <link rel="apple-touch-startup-image" media="screen and (orientation: landscape)"
              href="//static.d2c.cn/ipad/img/system/start_up_l.png"/>
        <!--[if lt IE 9]>
        <script type="text/javascript" src="${static_base}/c/js/html5.js"></script>
        <![endif]-->
        <#assign servertel='4008403666' />
        <#if profile=='development' || profile=='test'>
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
        <#if css?length gt 0>
            <#list css?split("|") as s>
                <link type="text/css" href="${static_base}/nc/css/${s}.css?t=${refreshTimeStamp}" rel="stylesheet"
                      media="screen"/>
            </#list>
        </#if>
        <!--[if lt IE 9]>
        <link type="text/css" href="${static_base}/nc/css/ie.css" rel="stylesheet" media="screen"/>
        <![endif]-->
        <#if profile=='development' || profile=='test'>
            <script type="text/javascript" src="${static_base}/nc/js/lib/jquery.1.83.js"></script>
            <script type="text/javascript" src="${static_base}/nc/js/lib/template.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript"
                    src="${static_base}/nc/js/utils/jquery.cookie.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript"
                    src="${static_base}/nc/js/utils/jquery.autocomplete.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript" src="${static_base}/nc/js/utils/md5.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript" src="${static_base}/nc/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript" src="${static_base}/nc/js/modules/common.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript"
                    src="${static_base}/nc/js/utils/jquery.fileupload.js?t=${refreshTimeStamp}"></script>
            <script type="text/javascript" src="${static_base}/nc/js/utils/base64.js?t=${refreshTimeStamp}"></script>
            <script src="https://webapi.amap.com/maps?v=1.4.8&key=4fc7a9efb33c5f84584ae8d78d5473ef&plugin=AMap.Geocoder"></script>
        <#else>
            <script type="text/javascript" src="${static_base}/nc/js/compress.js?t=${refreshTimeStamp}"></script>
        </#if>
        <#if js?length gt 0>
            <#list js?split("|") as s>
                <script type="text/javascript" src="${static_base}/nc/js/${s}.js?t=${refreshTimeStamp}"></script>
            </#list>
        </#if>
        <!--[if IE 6]>
        <script type="text/javascript" src="${static_base}/c/js/png.js"></script>
        <script type="text/javascript">
            DD_belatedPNG.fix('.png');
        </script>
        <![endif]-->
        <script type="text/javascript">
            var _hmt = _hmt || [];
            var _server_time = '${.now?string("yyyy/MM/dd HH:mm:ss")}';
            var _memberId = '${LOGINMEMBER.id}';
            var _isD2C = '${LOGINMEMBER.d2c}';
            var _partnerId = '${LOGINMEMBER.partnerId}';
        </script>
    </head>
    <body>

</#macro>

<#assign refreshTimeStamp=renderStaticTimeStamp()/>
<#macro page_user_menu menu_item=''>
    <div class="user-menu">
        <dl>
            <dt><i class="fa fa-user" style="padding-right:5px;"></i>我的D2C</dt>
        </dl>
        <#if LOGINMEMBER.id!=null &&  LOGINMEMBER.storeId!=null>
            <!--
        <dl>
            <dt><i class="fa fa-skyatlas"></i> D2C门店中心</dt>
            <dd <#if menu_item=='o2o_subscribe'> class="on"</#if>><a href="/o2oSubscribe/list">预约单管理</a> <i class="fa fa-chevron-right"></i></dd>
            <dd <#if menu_item=='o2o_productSkuStock_list'> class="on"</#if>><a href="/productSkuStock/list?type=store">商品库存查询</a> <i class="fa fa-chevron-right"></i></dd>
             <dd <#if menu_item=='o2o_requisition_list'> class="on"</#if>><a href="/requisition/store/list?types=3&status=1">调拨商品-待发货</a> <i class="fa fa-chevron-right"></i></dd>
             <dd <#if menu_item=='o2o_requisition_receive_list'> class="on"</#if>><a href="/requisition/receive/list?types=2&status=0&mstore=1">采购商品-待审核</a> <i class="fa fa-chevron-right"></i></dd>
            <dd <#if menu_item=='o2o_requisitionreceive_list'> class="on"</#if>><a href="/requisition/receive/list?types=2&types=4&status=3&mstore=0">采购商品-待入库</a> <i class="fa fa-chevron-right"></i></dd>
        </dl>
        -->
        </#if>
        <#if LOGINMEMBER.id!=null &&  LOGINMEMBER.designerId!=null>
            <dl>

                <!--
         	<dd <#if menu_item=='o2o_statement_list'> class="on"</#if> ><a href="/statement/list?status=1">设计师对账</a><i class="fa fa-chevron-right"></i></dd>
         	<dd <#if menu_item=='o2o_requisitiondesigner_list'> class="on"</#if>><a href="/requisition/designer/list?status=1" <#if !LOGINMEMBER.agreeDate?exists>onclick="$.removeCookie('not_tips',{ path: '/'});"</#if>>设计师补货列表</a> <i class="fa fa-chevron-right"></i></dd>
         	<dd <#if menu_item=='o2o_requisitiondesigner_reship'> class="on"</#if>><a href="/requisition/designer/list?status=3&isreship=1&type=5">设计师收货列表</a> <i class="fa fa-chevron-right"></i></dd>
         	<dd <#if menu_item=='shop_center_/product/list'> class="on"</#if>><a href="/shop/home?path=/product/list">设计师商品管理</a> <i class="fa fa-chevron-right"></i></dd>
         	<dd <#if menu_item=='o2o_productSkuStock_list'> class="on"</#if>><a href="/productSkuStock/list?type=designer">设计师库存查询</a> <i class="fa fa-chevron-right"></i></dd>
         	<dd <#if menu_item=='designer_agreement'> class="on"</#if>><a href="/requisition/designer/agreement">设计师调拨规则</a> <i class="fa fa-chevron-right"></i></dd>
        	-->
            </dl>
        </#if>
        <dl>
            <dt><i class="fa fa-diamond"></i> 订单中心</dt>
            <dd<#if menu_item=='order'> class="on"</#if>><a href="/member/order">我的订单</a> <i
                        class="fa fa-chevron-right"></i></dd>
            <dd<#if menu_item=='coupon'> class="on"</#if>><a href="/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED">我的优惠券</a>
                <i class="fa fa-chevron-right"></i></dd>
            <dd id="myComment" <#if menu_item=='comment'>class="on"</#if>><a
                        href="/comment/list?source=ORDERITEM">我的评论</a> <i class="fa fa-chevron-right"></i></dd>
            <#--<dd <#if menu_item=='award'> class="on"</#if>><a href="/award/my/list?status=0">我的抽奖</a> <i class="fa fa-chevron-right"></i></dd>-->
            <dd <#if menu_item=='my_subscribe'> class="on"</#if>><a href="/o2oSubscribe/my/list">我的预约单</a> <i
                        class="fa fa-chevron-right"></i></dd>
            <dd <#if menu_item=='auction'> class="on"</#if>><a href="/auction/member/mymargin">我的拍卖</a> <i
                        class="fa fa-chevron-right"></i></dd>
        </dl>
        <dl>
            <dt><i class="fa fa-life-ring"></i> 售后服务</dt>
            <dd<#if menu_item=='refund'> class="on"</#if>><a href="/member/refund/list">仅退款</a> <i
                        class="fa fa-chevron-right"></i></dd>
            <dd<#if menu_item=='exchange'> class="on"</#if>><a href="/member/exchange/list">仅换货</a> <i
                        class="fa fa-chevron-right"></i></dd>
            <dd<#if menu_item=='reship'> class="on"</#if>><a href="/member/reship/list">退款退货</a> <i
                        class="fa fa-chevron-right"></i></dd>
        </dl>
        <dl>
            <dt><i class="fa fa-bullseye"></i> 个人资料</dt>
            <#--<dd<#if menu_item=='avatar'> class="on"</#if>><a href="/member/avatar">设置头像</a> <i class="fa fa-chevron-right"></i></dd> -->
            <dd<#if menu_item=='info'> class="on"</#if>><a href="/member/info">个人信息</a> <i
                        class="fa fa-chevron-right"></i></dd>
            <#if LOGINMEMBER.id!=null && !LOGINMEMBER.d2c>
                <dd<#if menu_item=='bind'> class="on"</#if>><a href="/member/bind">绑定账号</a> <i
                            class="fa fa-chevron-right"></i></dd>
            </#if>
            <#if LOGINMEMBER.id!=null && LOGINMEMBER.d2c>
                <dd<#if menu_item=='security'> class="on"</#if>><a href="/member/security">修改密码</a> <i
                            class="fa fa-chevron-right"></i></dd>
            </#if>
            <dd<#if menu_item=='address'> class="on"</#if>><a href="/address/list">收货地址</a> <i
                        class="fa fa-chevron-right"></i></dd>
        </dl>
        <dl>
            <dt><i class="fa fa-fire"></i> 我的兴趣</dt>
            <dd<#if menu_item=='interest_collect'> class="on"</#if>><a href="/member/interest/collection/list">收藏的商品</a>
                <i class="fa fa-chevron-right"></i></dd>
            <dd<#if menu_item=='interest_attention'> class="on"</#if>><a
                        href="/member/interest/attention/list">关注的设计师</a> <i class="fa fa-chevron-right"></i></dd>
        </dl>
    </div>
</#macro>

<#macro memberAvator member size=''>${avatar_base}/${member.id%100}/${member.id}!${size}</#macro>

<#macro top_nav channel='' suspend=true>
    <header>
        <div class="header">
            <div class="header-user clearfix">
                <ul>
                    <li>
                        <#if LOGINMEMBER.id!=null>
                            <#if LOGINMEMBER.nickname><a href="/member/info">${LOGINMEMBER.nickname}</a><#else><a
                                    href="/member/info">亲爱的用户</a></#if><i>｜</i>
                            <#if !LOGINMEMBER.d2c>
                                <div class="notice-tip animated bounceIn">
                                    <a href="/member/bind" target="_blank">绑定D2C账号</a>
                                </div>
                            <#elseif !LOGINMEMBER.nickname||LOGINMEMBER.nickname==''>
                                <div class="notice-tip animated bounceIn">
                                    <a href="/member/info" target="_blank">亲，取个昵称吧~</a>
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
                        <ul class="collection-detail" style="z-index:3">
                            <li><a href="/member/interest/collection/list">收藏的商品</a></li>
                            <li style="border:0"><a href="/member/interest/attention/list">收藏的设计师</a></li>
                        </ul>
                    </li>

                    <li class="relative cart" <#if LOGINMEMBER.id!=null>data-type="login"</#if>>

                        <a href="/cart/list" target="_blank" class="parent">
                            <#if LOGINMEMBER.id!=null>
                            购物车<span id="cart-nums-id"></span></a>
                        <#else>购物车</a>
                        </#if>

                        <#if LOGINMEMBER.id!=null><i>｜</i></#if></li>
                    <#if LOGINMEMBER.id!=null>
                        <li><a href="/member/logout">退出</a></li></#if>
                </ul>
            </div>
            <div class="layout-response clearfix" style="position:relative;padding-top:20px">
                <div class="header-bar"></div>
                <div class="header-logo"><a href="//www.d2cmall.com">D2C全球好设计</a></div>
                <div class="header-ad" id="header-animotion-ad"></div>

                <div class="header-search">
                    <#assign renderStaticSign=renderStaticSign()/>
                    <form name="" action="/product/list" method="get" target="_blank">
                        <input type="text" name="k" id="header-keyword" class="place-holder-keyword"
                               value="<#if RequestParameters?exists>${RequestParameters.k}</#if>"/>
                        <button type="submit" id="header-search"><a href="javascript:" class="search-icon"><i
                                        class="fa fa-search"></i></a></button>
                        <input type="hidden" name="appParams" value="${renderStaticSign}"/>
                    </form>
                    <div class="hot-links">
                        <#assign searchHot=renderSearchHot()/>
                        <#if searchHot?exists && searchHot>
                            <#list searchHot as tip>
                                <a href="javascript:;" data-url="${base}/${(tip.url)!}" data-id="${(tip.id)!}"
                                   class="<#if tip.status=1>red </#if>hotsearch"
                                   <#if tip_index==0>style="display:none"</#if>>${(tip.keyword)!}</a> <#if tip_index gt 0>&nbsp;&nbsp;&nbsp;&nbsp;</#if>
                            </#list>
                        </#if>
                    </div>
                </div>
                <div class="header-right">

                    <div class="appdown" style="margin-left:60px;">
                        <p style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'">扫描下载D2C APP</p>
                        <img src="//static.d2c.cn/other/qrode/img/qrcode_nav.png" width="105" height="105">
                        <p style="font-family:'Microsoft YaHei',微软雅黑,'MicrosoftJhengHei'">注册即送<span class="red"
                                                                                                    style="font-size:15px;font-weight:600">700</span>元优惠券
                        <p/>
                    </div>
                </div>

            </div>
        </div>
        <div class="navigation<#if suspend==true> scroll-suspend</#if>"<#if channel=='index'> data-offset="-785"</#if>>
            <div class="layout-response">
                <div class="navi-category navi-category-border">
                    <div class="navi-category-title"><a href="/product/list" target="_blank">所有商品</a></div>
                    <div class="navi-category-main <#if channel=='index'>display-block<#else>display-none</#if>">
                        <#list navigations as navs>
                            <dl class="category">
                                <dt>
                                    <strong><a target="_blank"
                                               href="<#if navs.url!="">${navs.url}<#else>/c/${navs.code}</#if>">${navs.name}</a></strong>
                                    <p><#if navs.navigationItems?exists && navs.navigationItems?size gt 0>
                                            <#list navs.navigationItems as item>
                                                <a target="_blank" <#if item.cssStyle?exists && item.cssStyle!=''> style="${item.cssStyle}"</#if>
                                                   href="${(item.url)!}">${(item.title)!}</a>
                                            </#list>
                                        </#if>
                                    </p>
                                </dt>
                                <dd>
                                    <#list navs.children as child>
                                        <ul>
                                            <li>
                                                <strong>${child.name}</strong>
                                                <div class="navi-list-cont">
                                                    <#if child.navigationItems?exists && child.navigationItems?size gt 0>
                                                        <#list child.navigationItems as item>
                                                            <a target="_blank" href="${(item.url)!}">${(item.title)!}
                                                                <em>｜</em></a>
                                                        </#list>
                                                    </#if>
                                                </div>
                                            </li>
                                        </ul>
                                    </#list>
                                </dd>
                            </dl>
                        </#list>
                    </div>
                </div>
                <div class="navi-main">
                    <a href="${base}/"<#if channel=='index'> class="on"</#if>>首页</a>
                    <a href="${base}/showroom"<#if channel=='showroom'> class="on"</#if> target="_blank">设计师品牌</a>
                    <a href="${base}/star"<#if channel=='star'> class="on"</#if> target="_blank">明星风范</a>
                    <a href="${base}/store/list"<#if channel=='store'> class="on"</#if> target="_blank">实体店 </a>
                    <a href="${base}/promotion/910" target="_blank">THEROOM</a>
                </div>
            </div>
        </div>
    </header>
</#macro>

<#macro page_footer notop='' nomenu='' nofloat='' js=''>
<#if nomenu?length == 0>
    <div id="footer" class="footer lazyload">
        <div class="qrcode">
            <div class="qrcode-img">
                <img src="//static.d2c.cn/other/qrode/img/download_new.png" width="950" height="188" alt="">
                <img src="//static.d2c.cn/other/qrode/img/qrcode_nav.png" width="150" height="150" alt="" class="nav">
                <img src="//static.d2c.cn/other/qrode/img/qrcode_wechat.png" width="150" height="150" alt=""
                     class="wechat">
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
                            <dt><i class="footer-nav"></i>帮助中心</dt>
                            <dd class="first-line"><a href="${base}/page/faq_shipping" target="_blank" rel="nofollow">购物流程</a>
                            </dd>
                            <dd><a href="${base}//page/faq_auction" target="_blank" rel="nofollow">拍卖说明</a></dd>
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
                        <p style="text-align:left;">杭州迪尔西时尚科技有限公司 &nbsp;联系电话：<span style="color:red">${servertel}</span>
                        </p>
                        <p style="margin-right:75px;text-align:right;">
                            增值电信业务经营许可证：浙B2-20150236&nbsp;浙ICP备12034937号-1</p>
                        <p style="text-align:left;">本网站用字经北京北大方正电子有限公司授权许可</p>
                        <p style="text-align:right;"><a target="_blank"
                                                        href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33019902000183"
                                                        style="display:inline-block;text-decoration:none;padding-left:5px;color:#666;"><img
                                        src="//static.d2c.cn/img/home/160627/images/police.png" style="float:left;"/>浙公网安备
                                33019902000183号</a></p>
                    </div>
                    <a href="https://ss.knet.cn/verifyseal.dll?sn=e17052533010067889nm6y000000&comefrom=trust"
                       style="display:block;background:url(//static.d2c.cn/common/nc/img/trast.jpg) no-repeat;width:140px;height:60px;"></a>
                </div>
            </div>
        </div>
    </div>
</#if>
    <div class="scroll-top scroll-item">
        <a href="javascript:">TOP</a>
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
<#if js?length gt 0>
<#list js?split("|") as s>
    <script type="text/javascript" src="${static_base}/nc/js/${s}.js"></script>
</#list>
</#if>

    <div class="display-none">
        <script id="cart-template" type="text/html">
            <ul>
                {{each cart.items as value i}}
                <li>
                    <input type="hidden" value="{{value.id}}" rel="{{value.id}}"/>
                    <div class="img"><a href="/product/{{value.productId}}" target="_blank"><img
                                    src="${picture_base}{{value.sp1Img}}!180" width="40"/></a></div>
                    <div class="price"><strong>&yen;{{value.price}}<span class="grey" style="padding-left:3px;">X{{value.quantity}}</span></strong>
                    </div>
                    <div class="title-div"><a href="/product/{{value.productId}}" target="_blank" class="title">{{value.productName}}<a>
                    </div>
                    <div style="float:right;margin-top:5px;" class="cancel"><a href="javascript:;">删除</a></div>
                    <div class="float-left" style="margin-top:5px;"><a href="/product/{{value.productId}}"
                                                                       target="_blank"><span class="color">颜色：{{value.colorValue}}</span>&nbsp;&nbsp;<span
                                    class="color">尺码：{{value.sizeValue}}</span></a></div>
                </li>
                {{/each}}
            </ul>
            <#--<div class="mycart"><a class="button button-s button-red" href="/cart/list" target="_blank">去购物车</a></div> -->
        </script>
        <script>
            //小购物车列表
            var hoverCart = function () {
                $.get('/cart/list/show', function (data) {
                    if (data.result.status == 1) {
                        var cart = {};
                        cart = data.result.datas;
                        for (x in cart) {
                            if (cart[x].items == '') {
                                $('.template-m .loading-img').hide();
                                $('.template-m .loading-text').show();
                            } else {
                                $('.template-m .loading-text').hide();
                                var html = template('cart-template', cart);
                                $('.template-m .template-list').html(html);
                                $.each($('.template-m li .title'), function (i, d) {
                                    if ($('.template-m li').size() >= 3) {
                                        $('.template-m ul').addClass('height');
                                    }
                                    //var obj=$(d).text();
                                    //var obj_change=obj.substr(0,13)+'...'; //截取标题前13个字符
                                    //$(d).text(obj)
                                });
                                $('.template-m .loading-img').hide();
                            }
                        }
                    } else {
                        $.flashTip(data.result.message, 'warning')
                    }
                }, 'json');
            }

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
                var n = $('#cart-nums-id');
                var a = parseInt(n.text())
                $.get('/cart/delete/' + id, function (data) {
                    obj.parent().slideUp(function () {
                        obj.parent().remove();
                        if (a > 0) {
                            n.text(a - 1);
                        }
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

            //填充默认搜索
            var searchhot = $('.hot-links').find('a').eq(0).text();
            if (searchhot && $('#header-keyword').val() == '') {
                $('#header-keyword').val(searchhot);
            }

            //统计搜索词点击次数
            $('.hotsearch').click(function () {
                var url = $(this).attr('data-url');
                var id = $(this).attr('data-id');
                $.get('/membersearchsum/count?id=' + id, function (data) {
                    window.location.href = url;
                })
            })
        </script>
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

                /*七鱼客服*/
                var qy = document.createElement("script");
                qy.src = "https://qiyukf.com/script/cdf7ede373825ab5af9d955d1fb3556c.js?uid=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.id}</#if>&name=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.displayName}</#if><#if  profile=='development' || profile=='test'>&groupid=263041<#else>&groupid=199569</#if>";
                var qys = document.getElementsByTagName("script")[0];
                qys.parentNode.insertBefore(qy, qys);
            })();
        </script>
    </div>
    <!--
<script type="text/javascript">
	var currentPath = window.location.pathname;
	//合力亿捷云客服
	function customerHandle(){
		if($('#open-link').size()==0){
		    $('body').append('<a href="" id="open-link"></a>');
		}
		var linkUrl = "https://im.7x24cc.com/phone_webChat.html?accountId=N000000006249&chatId=ea307499-ab03-403f-8127-af8faf016a31<#if LOGINMEMBER.id!=null>&visitorId=${LOGINMEMBER.id}&nickName=${LOGINMEMBER.displayName}</#if>";
		if(currentPath.indexOf('/product') !=-1){
			linkUrl += '&businessParam={productId:${product.id}}';
		}
		$('#open-link').attr({'target':'_blank','href':linkUrl})[0].click();
	}
</script>
-->

    </body>
    </html>
</#macro>

<#assign navigations=renderNavigation()/>
<#macro navigation path='' id=''>
    <dl>
        <#list navigations as navs>
            <#if navs.path?index_of(path)!=-1><#assign style='show'><#else><#assign style=''></#if>
            <dd class="first"><a href="<#if navs.URL!="">${navs.URL}<#else>/c/${navs.code}</#if>">${navs.name}
                    <span>${navs.code}</span></a>
                <#if id==navs.id><em></em></#if>
            </dd>
            <#list navs.children as child>
                <dd class="second${style}"><a
                            href="<#if child.URL!="">${child.URL}<#else>/c/${child.code}</#if>">${child.name}
                        <span>${child.code}</span></a>
                    <#if id==child.id><em></em></#if>
                </dd>
            </#list>
        </#list>
    </dl>
</#macro>

<#macro p page totalpage num=''>
    <#if (request.queryString)??>
        <#assign requestParams=request.queryString?replace("\\&?p=(\\d+)\\&?","","r") />
        <#assign requestParams=requestParams?replace("<","&lt;","i") />
        <#assign requestParams=requestParams?replace(">","&gt;","i") />
        <#assign requestParams=requestParams?replace("script","","i") />
    </#if>
    <#assign currentPage=page?number >
    <#if currentPage-4 gt 0>
        <#assign beginPage = currentPage-4 />
    <#else>
        <#assign beginPage = 1 />
    </#if>
    <#if totalpage-currentPage lt 4>
        <#assign beginPage = beginPage - (4-totalpage + currentPage)  />
        <#if beginPage lt 1>
            <#assign beginPage = 1 />
        </#if>
    </#if>
    <#if num><span class="status">共 ${totalpage} 页 ${num} 条记录</span>
    </#if>
    <#setting url_escaping_charset="UTF-8">
    <#if currentPage-1 gt 0><a href="?p=${currentPage-1}&${requestParams}" class="page-pn">上一页</a>
    <#else><span class="disabled page-pn"> 上一页</span>
    </#if>
    <#if currentPage gt 5 && totalpage gt 10 ><a href="?p=1&${requestParams}">1</a> <span>...</span>
    </#if>
    <#assign endPage=beginPage+8 />
    <#if endPage gt totalpage>
        <#assign endPage=totalpage />
        <#assign beginPage=endPage-8 />
    </#if>
    <#if beginPage lt 1>
        <#assign beginPage = 1 />
    </#if>
    <#if endPage lt 1>
        <#assign endPage = 1 />
    </#if>
    <#list beginPage..endPage as x>
        <#if x == currentPage><span class="current">${x}</span>
        <#else><a href="?p=${x}&${requestParams}">${x}</a>
        </#if>
    </#list>
    <#if currentPage lte totalpage - 5 && totalpage gt 10><span>...</span> <a
            href="?p=${totalpage}&${requestParams}">${totalpage}</a>
    </#if>
    <#if currentPage lt totalpage><a href="?p=${currentPage+1}&${requestParams}" class="page-pn">下一页 </a>
    <#else><span class="disabled page-pn">下一页 </span>
    </#if>
</#macro>

<#macro simple_pager page totalpage num=''>
    <#if (request.queryString)??>
        <#assign requestParams=request.queryString?replace("\\&?p=(\\d+)\\&?","","r") />
        <#assign requestParams=requestParams?replace("<","&lt;","i") />
        <#assign requestParams=requestParams?replace(">","&gt;","i") />
        <#assign requestParams=requestParams?replace("script","","i") />
    </#if>
    <#assign currentPage=page?number >
    <#setting url_escaping_charset="UTF-8">
    <#if currentPage-4 gt 0>
        <#assign beginPage = currentPage-4 />
    <#else>
        <#assign beginPage = 1 />
    </#if>
    <#if totalpage-currentPage lt 4>
        <#assign beginPage = beginPage - (4-totalpage + currentPage)  />
        <#if beginPage lt 1>
            <#assign beginPage = 1 />
        </#if>
    </#if>
    <#if num><span class="status">共  ${num} 条记录</span>
    </#if><span class="status">${page} / ${totalpage}</span>
    <#if currentPage-1 gt 0><a href="?p=${currentPage-1}&${requestParams}" class="page-pn">上一页</a>
    <#else><span class="disabled page-pn"> 上一页</span>
    </#if>
    <#if currentPage lt totalpage><a href="?p=${currentPage+1}&${requestParams}" class="page-pn">下一页</a>
    <#else><span class="disabled page-pn">下一页</span>
    </#if>
</#macro>