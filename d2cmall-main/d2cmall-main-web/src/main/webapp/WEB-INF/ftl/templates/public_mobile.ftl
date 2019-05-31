<#assign FROMAPP=renderUserAgent()/>
<#assign LOGINMEMBER=loginMember()/>
<#assign servertel="4008403666"/>
<#macro page_header back='' productdetail='' service='' url='' button='' cart='' title='' hastopfix='true' module='' css='' extcss='' js='' keywords='' description=''><!doctype html>
    <#assign refreshTimeStamp=renderStaticTimeStamp()/>
    <html>
    <head>
        <title><#if title?length gt 0>${title}</#if></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="keywords"
              content="<#if keywords==''>D2C,d2c官网,D2C官网,潮流风尚,前沿艺术,个性设计,时装设计师平台,国际五大时装周T型台上最新的流行元素,欧洲一线设计师品牌,原创设计,设计师品牌,买手店<#else>${keywords},${keywords},D2C,d2c官网,D2C官网,潮流风尚,前沿艺术,个性设计,时装设计师平台,国际五大时装周T型台上最新的流行元素,国际新锐设计师品牌,原创设计,设计师品牌,买手店</#if>"/>
        <meta name="description"
              content="<#if description==''>D2C是一家集潮流风尚、前沿艺术、个性设计为一体的设计师集成平台。<#else>${description}</#if>"/>
        <meta name="viewport"
              content="width=device-width,height=device-height,inital-scale=1,maximum-scale=1,user-scalable=0,minimal-ui"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <meta name="format-detection" content="telephone=no"/>
        <meta name="wap-font-scale" content="no">
        <!--<meta name="apple-itunes-app" content="app-id=980211165">-->
        <meta property="data-title"
              content="<#if keywords==''>D2C,D2CMALL,d2c官网,D2C官网,潮流风尚,前沿艺术,个性设计,时装设计师平台,设计师品牌,国际五大时装周T型台上最新的流行元素,欧洲一线设计师品牌,原创设计,买手店<#else>${keywords},设计师品牌,${keywords},D2C,D2CMALL,潮流风尚,前沿艺术,个性设计,时装设计师平台,国际新锐设计师品牌,原创设计,买手店</#if>"/>
        <meta http-equiv="cleartype" content="on"/>
        <link rel="apple-touch-icon-precomposed" href="//static.d2c.cn/common/nm/img/icon_launcher.png"/>
        <link rel="apple-touch-startup-image" href="//static.d2c.cn/common/m/img/start_up_p_320.png"/>
        <#if profile=='development' || profile=='test'>
            <link type="text/css" href="${static_base}/nm/css/swiper.min.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.base.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.element.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.component.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.layout.css" rel="stylesheet" media="screen"/>
        <#else>
            <link type="text/css" href="${static_base}/nm/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
                  media="screen"/>
        </#if>
        <#if css?length gt 0>
            <#list css?split("|") as s>
                <#if s?index_of('http') lt 0>
                    <link type="text/css" href="${static_base}/nm/css/${s}.css?t=${refreshTimeStamp}" rel="stylesheet"
                          media="screen"/>
                <#else>
                    <link type="text/css" href="${s}?t=${refreshTimeStamp}" rel="stylesheet" media="screen"/>
                </#if>
            </#list>
        </#if>

        <script type="text/javascript">
            var app_client = <#if !FROMAPP>false<#else>true</#if>;
            var _hmt = _hmt || [];
            var _server_time = '${.now?string("yyyy/MM/dd HH:mm:ss")}';
            var _memberId = '${LOGINMEMBER.id}';
            var _isD2C = '${LOGINMEMBER.d2c}';
            var _partnerId = '${LOGINMEMBER.partnerId}';
            <#if browser=='wechat'&& wechat >
            var wx_signature = '${wechat.signature}', wx_nonceStr = '${wechat.nonceStr}',
                wx_timestamp = '${wechat.timestamp}', wx_appId = '${wechat.appId}';
            </#if>
        </script>
        <#if browser=='wechat'>
            <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
        </#if>
        <#if profile=='development' || profile=='test'>
            <script src="${static_base}/nm/js/utils/swiper.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/clipboard.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/lib/jquery.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/lib/template.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/jquery.cookie.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/unveil.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/iscroll-lite.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/modules/function.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/modules/common.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/vconsole.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/base64.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nc/js/utils/md5.js?t=${refreshTimeStamp}"></script>
            <script src="https://jic.talkingdata.com/app/h5/v1?appid=D97A7A4764A276A61994E74A423389CC&vn=V1.0&vc=1.0.0"></script>
        <#else>
            <script src="${static_base}/nm/js/utils/swiper.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/utils/clipboard.min.js?t=${refreshTimeStamp}"></script>
            <script src="${static_base}/nm/js/compress.js?t=${refreshTimeStamp}"></script>
            <script src="https://jic.talkingdata.com/app/h5/v1?appid=D97A7A4764A276A61994E74A423389CC&vn=V1.0&vc=1.0.0"></script>
        </#if>
        <#if browser=='wechat'&& wechat >
            <script src="${static_base}/nm/js/lib/wechat.js?t=${refreshTimeStamp}"></script>
        </#if>
        <#if js?length gt 0>
            <#list js?split("|") as s>
                <#if s?index_of('http') lt 0>
                    <script src="${static_base}/nm/js/${s}.js?t=${refreshTimeStamp}"></script>
                <#else>
                    <script src="${s}"></script>
                </#if>
            </#list>
        </#if>
        <!-- <script type="text/javascript" name="baidu-tc-cerfication" data-appid="5418021" src="//apps.bdimg.com/cloudaapi/lightapp.js"></script> -->
    </head>
    <body>
    <#if !FROMAPP>
        <#if back?length gt 0>
            <#if browser!='wechat' && browser!='weibo' >
                <header>
                    <div class="header fixed">
                        <div class="header-back"><a
                                    href="<#if url?length gt 0>${url}<#else>javascript:history.back(1);</#if>"
                                    class="icon icon-back"></a></div>
                        <div class="header-title">${back}</div>
                    </div>
                </header>
            </#if>
        </#if>

        <#if service==''>
            <div class="online-chat">
                <div class="icon icon-assistant" onclick="ysf.open()"></div>
                <span>在线客服</span>
            </div>
        </#if>
    </#if>
</#macro>
<#macro page_nav_bar notel='' channel='' module=''>
    <#if !FROMAPP>
        <nav>
            <a href="${mobileBase}/" class="one<#if channel=='home'> on</#if>">
                <span class="icon icon-home"></span>
                <p>首页</p>
            </a>
            <a href="${mobileBase}/showroom" class="two<#if channel=='showroom'> on</#if>">
                <#if newCount gt 0><em class="bounceIn animated">+42</em></#if>
                <span class="icon icon-earth"></span>
                <p>设计师品牌</p>
            </a>
            <a href="/navigation" class=" three<#if channel=='navigation'> on</#if>">
                <span class="icon icon-cate"></span>
                <p>商品分类 </p>
            </a>

            <a href="${mobileBase}/cart/list" class="four<#if channel=='cart'> on</#if>" style="position:relative;">
                <em class="bounceIn animated my-cart-num">0</em>
                <span class="icon icon-cart"></span>
                <p>购物车</p>
            </a>
            <a href="${mobileBase}/member/home" class="five <#if channel=='mine'> on</#if>" style="position:relative;">
                <span class="icon icon-mine"></span>
                <p>我的</p>
            </a>
            <!-- <a href="/store/list" class="one<#if channel=='store'> on</#if>">
        <span class="icon icon-store"></span>
        <p>实体店</p>
     </a>
    -->
        </nav>
        <#assign nottel="false"/>
        <#if notel?length == 0 && nottel=="true">
            <div class="service-tel">客服电话：<a href="tel:${servertel}">${servertel}</a> (工作日 9:00 - 24:00)</div>
        </#if>

    </#if>
</#macro>

<#macro app_download_bar fixed="">
    <#if !FROMAPP>
        <script src="https://static.mlinks.cc/scripts/dist/mlink.min.js"></script>
        <div class="app-download-bar">
            <div<#if fixed?length == 0 || fixed=="true"> class="fixed"</#if>>
                <div class="bar-button">
                    <a href="javascript:" class="open-app">立即打开</a>
                </div>
                <div class="bar-close"><a href="javascript:$('.app-download-bar').remove()"><span
                                class="icon icon-cross"></span></a></div>
                <div class="bar-icon"><img src="//static.d2c.cn/common/nm/img/icon_launcher.png" alt="D2C-ICON"/></div>
                <div class="bar-cont open-app">
                    <div class="bar-slide">
                        <div><p><strong>一站买遍@全球好设计 </strong></p>
                            <p>客服电话：4008403666（工作日9:00-24:00）</p></div>
                        <div><p><strong>网罗时尚@全球好设计</strong></p>
                            <p>全球好设计，尽在D2C</p></div>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $('.open-app').click(function () {
                if (iOS) {
                    $(".open-app").click();
                } else {
                    if (isWeChat || isQQ) $('.brguide').show(); else $(".open-app").click();
                }
            })

            var a = window.location.pathname;
            <#if RequestParameters.parent_id>
            var urlList = [
                {'url': '/showroom/designer/'},
                {'url': '/promotion/'},
                {'url': '/flashpromotion/product/session'},
                {'url': '/flashpromotion/brand/session'},
                {'url': '/product/list(\\?.*)'},
                {'url': '/URL/page'},
                {'url': '/product/'},
                {'url': '/productComb/'},
                {'url': '/designer/list/'},
                {'url': '/bargain/promotion/list?status=1&bargain=1'}
            ];

            function checkUrl(arr, value) {
                arr.forEach(function (currentValue) {
                    if (value.indexOf(currentValue.url) != -1) {
                        if (value.indexOf('?') != -1) {
                            value += '&parent_id=' +${RequestParameters.parent_id};
                        } else {
                            value += '?parent_id=' +${RequestParameters.parent_id};
                        }
                    }
                });
                return value;
            }

            a = checkUrl(urlList, a);
            </#if>
            new Mlink({
                mlink: "AKGJ",
                button: $('.open-app'),
                autoLaunchApp: false,
                autoRedirectToDownloadUrl: true,
                downloadWhenUniversalLinkFailed: false,
                inapp: false,
                params: {myUrl: a}
            })
        </script>
    </#if>
</#macro>



<#macro page_footer menu='' module='' js=''>
<#if !FROMAPP>
<#if menu?length gt 0>
    <footer>
        <a href="/">首页</a>
        <#if LOGINMEMBER.id!=null>
            <a href="/member/order">我的订单</a>
            <#if browser!='wechat'>
                <a href="/member/logout">退出</a>
            </#if>
        <#else>
            <a href="/member/login">登录</a>
            <a href="/member/register">注册</a>
        </#if>
        <a href="javascript:window.scrollTo(0,0);">返回顶部</a>
    </footer>
</#if>
<#if js?length gt 0>
<#list js?split("|") as s>
    <script type="text/javascript" src="${static_base}/nm/js/${s}.js"></script>
</#list>
</#if>
    <div class="footer-space" style="height:3em;"></div>
</#if>
    <script>
        $(function () {
            if (_partnerId != '') {
                $.each($('a'), function (i, d) {
                    if ($(d).prop('href').indexOf('javascript:') == -1 && $(d).prop('href') != undefined) {
                        var map_str = [];
                        var url_prefix = '';
                        if ($(d).prop('href').indexOf('#') != -1) {
                            var map_arr = $(d).attr('href').split('#');
                            map_str = '#' + map_arr[1];
                            url_prefix = map_arr[0];
                        } else {
                            url_prefix = $(d).prop('href');
                        }
                        if (url_prefix.indexOf('?') != -1) {
                            $(d).prop('href', url_prefix + '&parent_id=' + _partnerId + map_str);
                        } else {
                            $(d).prop('href', url_prefix + '?parent_id=' + _partnerId + map_str);
                        }
                    }
                });
            }
            <#if profile=='development' || profile=='test'>
            window.vConsole = new window.VConsole({
                defaultPlugins: ['system', 'network', 'element', 'storage'], // 可以在此设定要默认加载的面板
                maxLogNumber: 1000,
                // disableLogScrolling: true,
                onReady: function () {
                    //console.log('vConsole is ready.');
                },
                onClearLog: function () {
                    //console.log('on clearLog');
                }
            });
            </#if>
        });
        <#if profile=='test'>
        <#assign c = "-0101">
        </#if>


        <#if !FROMAPP>
        if (("standalone" in window.navigator) && window.navigator.standalone) {
            var noddy, remotes = true;
            document.addEventListener('click', function (event) {
                noddy = event.target;
                // Bubble up until we hit link or top HTML element. Warning: BODY element is not compulsory so better to stop on HTML
                while (noddy.nodeName !== "A" && noddy.nodeName !== "HTML") {
                    noddy = noddy.parentNode;
                }
                if ('href' in noddy && noddy.href.indexOf('http') !== -1 && (noddy.href.indexOf(document.location.host) !== -1 || remotes)) {
                    event.preventDefault();
                    document.location.href = noddy.href;
                }
            }, false);
        }
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?fbed72c37b3bea898ae59225408ea7a5<#if profile=='test'>${c}</#if>";
        var bds = document.getElementsByTagName("script")[0];
        bds.parentNode.insertBefore(hm, bds);

        //七鱼客服
        var qy = document.createElement("script");
        qy.src = "https://qiyukf.com/script/cdf7ede373825ab5af9d955d1fb3556c.js?uid=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.id}</#if>&name=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.displayName}</#if><#if  profile=='development' || profile=='test'>&groupid=263041<#else>&groupid=199569</#if>";
        var qys = document.getElementsByTagName("script")[0];
        qys.parentNode.insertBefore(qy, qys);


        /*合力亿捷云客服
        function customerConsult() {
            if($('#open-link').size()==0){
                $('body').append('<a href="" id="open-link"></a>');
            }
            var linkUrl = "https://im.7x24cc.com/phone_webChat.html?accountId=N000000006249&chatId=ea307499-ab03-403f-8127-af8faf016a31<#if LOGINMEMBER.id!=null>&visitorId=${LOGINMEMBER.id}&nickName=${LOGINMEMBER.displayName}</#if>";
	$('#open-link').attr('href',linkUrl)[0].click();
}
*/


        </#if>
    </script>

    </body>
    </html>
</#macro>

<#macro simple_pager page totalpage num=''>
    <#if (request.queryString)??>
        <#assign requestParams=request.queryString?replace("\\&?p=(\\d+)\\&?","","r") />
        <#if requestParams?has_content>
            <#assign requestParams = '&' + requestParams />
        </#if>
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
    <#if num><span class="status">共  ${num} 条记录</span>
    </#if>
    <#if currentPage-1 gt 0><a href="?p=${currentPage-1}${requestParams}">〈 上一页</a>
    <#else><span class="disabled">〈 上一页</span>
    </#if>
    <span class="status">${page} / ${totalpage}</span>
    <#if currentPage lt totalpage><a href="?p=${currentPage+1}${requestParams}">下一页 〉</a>
    <#else><span class="disabled">下一页 〉</span>
    </#if>
</#macro>
