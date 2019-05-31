<div class="account-wrap" style="margin-left:-5px;width:430px;padding:0;border:none;">
    <div class="account-title login-title" data-login="normal">
        <!-- <a data-type="normal"  href="javascript:;">普通登录</a> -->
        <a data-type="message" href="javascript:;" class="current" id="currenT">手机无密码登录</a>
    </div>
    <div class="login-type">
        <div id="login-message">
            <form name="quicky-login" class="validate-form" action="/member/quick/login" method="post"
                  call-back="loginSuccessReturn" style="border:none;padding:0 16px 37px 16px;">
                <div class="account-input account-now">
                    <input type="hidden" name="nationCode" disabled="disabled" value="86" class="mobile-code"/>
                    <span class="input-tip" style="left:80px">请输入手机号</span>
                    <input type="text" name="loginCode" value="" class="validate-account login-l" alt="login"
                           data-rule="mobile" disabled="disabled" autocomplete="new-password"/>
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

    <div class="account-bar" style="padding:0 20px">
        <div class="float-left"><a href="/member/forgot" class="forget color">忘记密码</a></div>
        <div class="float-right"><a href="/member/register" class="register" style="color:#fd555d">立即注册</a></div>
    </div>
    <div class="msg "<#if result?exists && result.status lt 0> style="display:block;padding:0 20px"</#if>><#if result?exists && result.status lt 0>${result.message}</#if></div>
    <div class="account-btn" style="padding:0 20px">
        <input type="hidden" name="path" value="${path}"/>
        <input value="登录" class="input-sub" type="button"/>
    </div>
    <div class="account-bar text-left grey" style="padding:0 20px">
        第三方账号登录
    </div>

    <div class="quick-login" style="padding:0 20px">
        <div class="float-left">
            <a href="/member/doQuickLogin/QQ" class="qq-login"></a>
            <a href="/member/doQuickLogin/Weibo" class="sina-login"></a>
            <a href="/member/doQuickLogin/Weixin" class="wx-login"></a>
        </div>
    </div>
</div>

<script type="text/javascript">
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
            $('form[name="form-login"]').submit();
        } else {
            $('form[name="quicky-login"]').submit();
        }
    })

    $('.account-input input').focus(function () {
        $(this).prev('.input-tip').hide();
    }).blur(function () {
        if ($(this).val() == "") {
            $(this).prev('.input-tip').show();
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

    function checkemail(obj) {
        var re_email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        var re_mobile = /^(13[0-9]{9})|(15[0-9][0-9]{8})|(18[0-9][0-9]{8})$/;
        if (!re_email.test(obj.val()) && !re_mobile.test(obj.val())) {
            return false;
        } else {
            return true;
        }
    };


    //广告
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

    function convertclk(_traker) {
        _traker._monitorEvent("BUTTON", "id", "reg-btn", "C", "PC_register");
//monitor click
    }

    $('#currenT').trigger('click');
</script>