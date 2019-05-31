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
<body>


<div class="account-body">
    <div class="logo">
        <a href="//www.d2cmall.com"><img src="//static.d2c.cn/img/home/160627/images/logo.png"></a>
    </div>
    <div class="account-count">
        <div class="account-show">
            欢迎加入D2C
        </div>
        <div class="account-wrap">
            <form name="bind" id="form-bind" action="/member/bind" method="post">
                <input type="hidden" name="" value="中国大陆" class="country-code"/>
                <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
                <input type="hidden" name="openId" value="${openId}"/>
                <input type="hidden" name="source" value="${source}"/>
                <div class="account-title bind-title"></div>
                <div class="account-bar text-right"><a href="javascript:;" style="color:#FD555D">使用第三方帐号登录请绑定手机号</a>
                </div>
                <div class="account-input">
                    <span class="choose-country" id="choose-country"><strong id="mobile-code">+86</strong><em
                                class="fa fa-caret-down"></em></span>
                    <span class="input-tip" style="left:26%;">请输入手机号</span>
                    <input type="text" name="loginCode" id="logincode" data-rule="mobile" class="validate-account"
                           value="" style="width:70%"/>
                </div>
                <div class="msg" style="margin-top:-8px;"></div>
                <div class="account-input" style="width:65%">
                    <span class="input-tip">请输入手机短信验证码</span>
                    <input type="text" name="code" value=""/>
                    <button type="button" data-source="" data-type="MEMBERMOBILE"
                            class="button button-grey validate-send pn">获取验证码
                    </button>
                </div>
                <div id="type" data-kaga="has" style="display:none"></div>
                <div class="first-hide" style="display:none">
                    <div class="account-input">
                        <span class="input-tip">请输入8-20位密码</span>
                        <input type="password" name="password" id="password" value=""/>
                    </div>
                    <div class="account-input">
                        <span class="input-tip">请再次输入设置的密码</span>
                        <input type="password" name="rePassword" id="rePassword" value=""/>
                    </div>
                </div>
                <div class="msg" id="errmsg"></div>

                <div style="width:430px;height:150px;background:url(//static.d2c.cn/img/home/160627/images/bind_bg.png) no-repeat;"
                     class="second-hide">

                </div>
                <div class="account-btn">
                    <input type="hidden" name="path" value="${path}"/>
                    <input type="button" value="立即绑定" id="bind-btn" class="input-sub"/>
                </div>
                <div class="service-dtail">
                    <a href="/member/login" style="color:#999">使用D2C帐号登录</a>
                </div>

            </form>
        </div>
    </div>
    <div class="copyright clearfix">
        <div class="copyright-info">
            <p style="margin-right:75px;text-align:right;">Powered byD2C-Copyright © 2015 D2CMALL.COM - All Rights
                Reserved.</p>
            <p style="text-align:left;">杭州迪尔西时尚科技有限公司 &nbsp;联系电话：<span style="color:red">4008-403-666<span></p>
            <p style="margin-right:75px;text-align:right;">增值电信业务经营许可证：浙B2-20150236&nbsp;浙ICP备12034937号-1</p>
            <p style="text-align:left;">本网站用字经北京北大方正电子有限公司授权许可</p>
            <p style="text-align:right;"><a target="_blank"
                                            href="//www.beian.gov.cn/portal/registerSystemInfo?recordcode=33010602004682"
                                            style="display:inline-block;text-decoration:none;padding-left:5px;color:#999;"><img
                            src="//static.d2c.cn/img/home/160627/images/police.png" style="float:left;"/>浙公网安备
                    33010602004682号</a></p>
        </div>
    </div>
</div>
<#if nofloat?length == 0>
    <div class="service-suspend">
        <div class="service-content service-phone"></div>
        <div class="service-content text-info customer" onclick="NTKF.im_openInPageChat();"><a
                    href="javascript:;">客服</a></div>
        <div class="service-phone-detail">
            ${servertel}
        </div>
    </div>
</#if>

<script src="https://qiyukf.com/script/cdf7ede373825ab5af9d955d1fb3556c.js?uid=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.id}</#if>&name=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.displayName}</#if>"
        defer async></script>
<script type="text/javascript">
    $('body').css('background', '#fff');
    var pubsage_sign = "";
    var pubsage_url = "";
    var url = window.location.href;
    var domain = document.domain;
    var mat = url.match(/adtiid=([^&#]*)/);
    var iid = mat ? mat[1] : "";
    if (iid == "" || typeof (iid) == "undefined") {
        var uid = function (cookiekey) {
            var strCookie = document.cookie;
            var arrCookie = strCookie.split(";");
            var value = "";
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                if (cookiekey == arr[0].replace(/(^\s*)|(\s*$)/g, "")) {
                    value = arr[1];
                    break
                }
            }
            return value
        }("_ws_uid");
        if (uid == "" || typeof (uid) == "undefined") {
            pubsage_url = "tca.sagetrc.com"
        } else {
            var strAry = domain.split(".");
            var host = strAry[strAry.length - 2] + "." + strAry[strAry.length - 1];
            var pf = uid.split(".");
            if (pf[0] == "tqh") {
                pubsage_url = "tqh_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tgg") {
                pubsage_url = "tgg_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tsg") {
                pubsage_url = "tsg_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tdsp") {
                pubsage_url = "tdsp_" + pubsage_sign + "." + host
            }
            if (pf[0] != "tsg" && pf[0] != "tgg" && pf[0] != "tqh" && pf[0] != "tdsp") {
                pubsage_url = "trc_" + pubsage_sign + "." + host
            }
        }
    } else {
        if (iid.match("^g") != null) {
            pubsage_url = "tgg.sagetrc.com"
        }
        if (iid.match("^q") != null) {
            pubsage_url = "tqh.sagetrc.com"
        }
        if (iid.match("^s") != null) {
            pubsage_url = "tsg.sagetrc.com"
        }
        if (iid.match("^d") != null) {
            pubsage_url = "tdsp.sagetrc.com"
        }
        if (iid.match("^g|^q|^s|^d") == null) {
            pubsage_url = "t1.sagetrc.com"
        }
    }
    var pubsage_conv_id = 27449;
    var pubsage_conv = pubsage_conv || [];
    (function () {
        try {
            var d = document;
            var at = d.createElement("script");
            at.type = "text/javascript";
            at.async = true;
            at.src = ("https:" == document.location.protocol ? "https:" : "http:") + "//" + pubsage_url + "/trc/atac/conv_x.js?id=" + pubsage_conv_id;
            var s = d.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(at, s)
        } catch (e) {
        }
    })();
    $.each($('form input'), function (i, e) {
        $(e).focus().blur();
    });
    $('.account-input input').focus(function () {
        $(this).prev('.input-tip').hide();
    }).blur(function () {
        if ($(this).val() == "") {
            $(this).prev('.input-tip').show();
        }
    });

    $('.account-input .input-tip').click(function () {
        $(this).next('input').focus();
    });

    $('#logincode').blur(function () {
        var obj = $('#logincode');
        if (!obj.utilValidateMobile()) {
            obj.parent().next('div').show().html('请输入正确的手机号码');
            return false;
        }
        if (obj.val() != '') {
            $.post('/member/checkLoginCode?' + new Date().getTime(), {'code': obj.val()}, function (data) {
                if (data.result.status == -1) {
                    $('.first-hide').hide();
                    $('.second-hide').show();
                    obj.parent().next('div').show().html('该帐号已注册D2C帐号，不需要设置密码')
                    $('#type').attr('data-kaga', 'has');
                } else {
                    $('.first-hide').show();
                    $('.second-hide').hide();
                    obj.parent().next('div').hide();
                    $('#type').attr('data-kaga', 'no');
                }
            }, 'json');
        }

    });

    $('#bind-btn').click(function () {
        if ($('#form-bind input[name=loginCode]').val() == '') {
            $('#errmsg').html('请输入手机号');
            $('#form-bind input[name=loginCode]').focus();
            $('#errmsg').show();
            return false;
        }
        if ($('#form-bind input[name=loginCode]').val().length > 20) {
            $('#errmsg').html('手机号码格式不正确');
            $('#form-bind input[name=loginCode]').focus();
            $('#errmsg').show();
            return false;
        }
        if ($('#form-bind input[name=code]').val() == '') {
            $('#errmsg').html('请输入短信校验码');
            $('#form-bind input[name=code]').focus();
            $('#errmsg').show();
            return false;
        }
        if ($('#type').attr('data-kaga') == "no") {
            if ($('#password').val().length < 8 || $('#password').val().length > 20) {
                $('#errmsg').html('密码应该大于等于8小于20位字数');
                $('#password').focus();
                $('#errmsg').show();
                return false;
            }
            if ($('#rePassword').val() != $('#password').val()) {
                $('#errmsg').html('两次密码输入不一致');
                $('#rePassword').focus();
                $('#errmsg').show();
                return false;
            }
        }


        var account = $('#form-bind input[name=loginCode]');
        //var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        //var re_mobile = /^[1][3456789]\d{9}$/;
        if (!account.utilValidateMobile()) {
            $('#errmsg').html('请输入正确的手机号');
            return false;
        } else {
            $.post($('#form-bind').attr('action'), $('#form-bind').serialize(), function (res) {
                if (res.result.status == -1) {
                    $('#errmsg').html(res.result.message).show();
                    return false;
                } else {
                    window.location.href = "//www.d2cmall.com";
                }
            }, 'JSON');
        }
    });
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
</body>
</html>