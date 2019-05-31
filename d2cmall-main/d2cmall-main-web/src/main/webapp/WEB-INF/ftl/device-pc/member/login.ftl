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
        <script type="text/javascript" src="${static_base}/nc/js/utils/md5.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/base64.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/lib/template.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/jquery.cookie.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript"
                src="${static_base}/nc/js/utils/jquery.autocomplete.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/modules/common.js?t=${refreshTimeStamp}"></script>
    <#else>
        <script type="text/javascript" src="${static_base}/nc/js/utils/md5.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/base64.js?t=${refreshTimeStamp}"></script>
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
<#assign servertel="4008403666"/>

<div class="account-body">
    <div class="logo">
        <a href="//www.d2cmall.com"><img src="//static.d2cmall.com/img/home/160627/images/logo.png"></a>
    </div>
    <div class="account-count">
        <div class="account-show">
            欢迎加入D2C
        </div>
        <div class="account-wrap">
            <div class="account-title login-title" data-login="normal">
                <!-- <a data-type="normal" class="current" href="javascript:;">普通登录</a> -->
                <a data-type="message" href="javascript:;" style="margin:0 auto" id="currentT">手机无密码登录</a>
            </div>
            <div class="login-type">
                <div id="login-message" style="display:none;">
                    <form name="quicky-login" action="/member/quick/login" method="post">
                        <input type="hidden" name="auto" value="0"/>
                        <div class="account-input account-now">
                            <input type="hidden" name="nationCode" disabled="disabled" value="86" class="mobile-code"/>
                            <span class="input-tip" style="left:80px">请输入手机号</span>
                            <input type="text" name="loginCode" value="" class="validate-account login-l" alt="login"
                                   data-rule="mobile" disabled="disabled" style="padding-left:5px;"
                                   autocomplete="new-password"/>
                        </div>
                        <div class="account-input" style="width:65%">
                            <span class="input-tip">输入短信验证码</span>
                            <input type="text" name="code" value=""/>
                            <button type="button" data-source="" data-type="MEMBERMOBILE" data-register="true"
                                    class="button button-grey validate-send pn">获取验证码
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="account-bar">
                <div class="float-left"><a href="/member/forgot" class="forget color">忘记密码</a></div>
                <div class="float-right"><a href="register" class="register" style="color:#fd555d">立即注册</a></div>
            </div>
            <div class="msg "<#if result?exists && result.status lt 0> style="display:block;"</#if>><#if result?exists && result.status lt 0>${result.message}</#if></div>
            <div class="account-btn">
                <input type="hidden" name="path" value="${path}"/>
                <input value="登录" class="input-sub" type="button"/>
            </div>
            <div class="account-bar text-left grey">
                第三方账号登录
            </div>

            <div class="quick-login">
                <div class="float-left">
                    <a href="/member/doQuickLogin/QQ" class="qq-login"></a>
                    <a href="/member/doQuickLogin/Weibo" class="sina-login"></a>
                    <a href="/member/doQuickLogin/Weixin" class="wx-login"></a>
                </div>
            </div>

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
                            src="//static.d2cmall.com/img/home/160627/images/police.png" style="float:left;"/>浙公网安备
                    33010602004682号</a></p>
        </div>
    </div>
</div>
<#if nofloat?length == 0>
    <div class="service-suspend">
        <div class="service-content service-phone"></div>
        <div class="service-content text-info customer" onclick="ysf.open();"><a href="javascript:;">客服</a></div>
        <div class="service-phone-detail">
            ${servertel}
        </div>
    </div>
</#if>
<script src="https://qiyukf.com/script/cdf7ede373825ab5af9d955d1fb3556c.js?uid=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.id}</#if>&name=<#if LOGINMEMBER.id!=null>${LOGINMEMBER.displayName}</#if>"
        defer async></script>
<script type="text/javascript">
    $('body').css('background', '#fff');
    $('.account-title a').click(function () {
        var type = $(this).addClass('current').attr('data-type');
        $(this).parent().attr('data-login', type);
        $(this).siblings().removeClass('current');
        $('#login-' + type).show().find('input').attr('disabled', false);
        $('#login-' + type).find('.mobile-code').val('86');
        $('#login-' + type).find('#choose-country').remove();
        $('#login-' + type).find('.country-data').remove();
        $('#login-' + type).find('.mobile-code').after('<span class="choose-country" id="choose-country"><strong id="mobile-code">+86</strong><em class="fa fa-caret-down"></em></span>');
        $('#login-' + type).siblings().hide().find('input').attr('disabled', true);
        $('#login-' + type).siblings().find('.country-data').remove();
        $('#login-' + type).siblings().find('.mobile-code').val('86');
        $('#login-' + type).siblings().find('#choose-country').remove();
    })


    //用于保存用户登录方式
    var onestep = window.localStorage ? localStorage.getItem("loginType") : Cookie.read("loginType");
    if (onestep != null) {
        if (onestep == "quicky") {
            $('.account-title a[data-type=message]').trigger('click');
        } else {
            $('.account-title a[data-type=normal]').trigger('click');
        }
    } else {
        $('.account-title a[data-type=normal]').trigger('click');
    }
    $('.input-sub').click(function () {
        if ($('.login-title').attr('data-login') == 'normal') {
            var logintype = 'normal';
            if (window.localStorage) {
                localStorage.setItem("loginType", logintype);
            } else {
                Cookie.write("loginType", logintype);
            }
            $('form[name="form-login"]').submit();
        } else {
            var logintype = 'quicky'
            if (window.localStorage) {
                localStorage.setItem("loginType", logintype);
            } else {
                Cookie.write("loginType", logintype);
            }
            $('form[name="quicky-login"]').submit();
        }
    })

    $.each($('form input'), function (i, e) {
        $(e).focus().blur();
    });
    $('.account-input input').focus(function () {
        $(this).prev('.input-tip').hide();
        $('.msg').hide();
    }).blur(function () {
        if ($(this).val() == "") {
            $(this).prev('.input-tip').show();
        }
    });

    $('.account-input .input-tip').click(function () {
        $(this).next('input').focus();
    });

    $('form[name=form-login]').submit(function () {
        var email = $('form[name=form-login] input[name=loginCode]');
        var password = $('form[name=form-login] input[name=password]');
        /*if (!checkemail(loginCode)){
            email.siblings('.tip-err').show().html('请输入正确的邮箱或手机号。');
            return false;
        }*/
        if (email.val() == '') {
            email.focus();
            $('.account-wrap .msg').html('请输入邮箱或手机');
            $('.account-wrap .msg').show();
            return false;
        } else if (password.val() == '') {
            password.focus();
            $('.account-wrap .msg').html('请输入登录密码');
            $('.account-wrap .msg').show();
            return false;
        }
    });

    //修改文本属性 修复autocomplete="new-password"在搜狗下失效的问题
    $("#password").focus(function () {
        changeType("password", "password");
        $("#password").val("");
    })

    function changeType(t, action) {
        var p = document.getElementById(t);
        p.type = action;
    }

    function checkemail(obj) {
        var re_email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        var re_mobile = /^(13[0-9]{9})|(15[0-9][0-9]{8})|(18[0-9][0-9]{8})$/;
        if (!re_email.test(obj.val()) && !re_mobile.test(obj.val())) {
            return false;
        } else {
            return true;
        }
    };

    function keyUp(e) {
        var currKey = 0, e = e || event;
        currKey = e.keyCode || e.which || e.charCode;
        if (currKey == '13') {
            if ($('.modal-outer').size() > 0) {
                $('button[name="send"]').trigger('click');
            } else {
                $('.input-sub').trigger('click');
            }
        }
    }

    document.onkeyup = keyUp;

    $('#currentT').trigger('click')
</script>
</body>
</html>