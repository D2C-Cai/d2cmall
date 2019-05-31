<header>
    <div class="header">
        <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
        <div class="header-title">快捷登录</div>
    </div>
</header>
<div class="login-modal">
    <div class="section" id="login-section">
        <div style="text-align:center;margin:1.5em 0;">
            <img src="//static.d2c.cn/common/nm/img/icon_launcher.png" width="100"/>
        </div>
        <div class="form">
            <form id="quick-form" class="validate-form" call-back="loginSucess" action="/member/quick/login"
                  method="post" success-tip="登陆成功~">
                <div class="form-item">
                    <input type="hidden" name="nationCode" disabled="disabled" value="86" class="mobile-code"/>
                    <input type="text" name="loginCode" value="" class="input validate validate-account" title="账号"
                           placeholder="请输入手机号"/>
                </div>
                <div class="form-item">
                    <input type="text" name="code" id="code" value="" class="input validate" title="校验码"
                           placeholder="请输入校验码"/>
                    <button type="button" data-source="" data-type="MEMBERMOBILE"
                            class="button button-white validate-send validate-button">获取校验码
                    </button>
                </div>
                <div id="random-code-insert"></div>
                <div class="form-button">
                    <button type="submit" name="login-button" class="button button-l button-red">登录</button>
                </div>
            </form>


            <div class="text-left" style="padding:1em 0.5em;font-size:0.8em">
                <a href="/member/forgot">忘记密码？</a>
            </div>
        </div>
    </div>
    <!--
    <div class="section display-none" id="register-section">
        <div class="form">
            <form id="register-form" class="validate-form" call-back="registeSucess" action="/member/register" method="post">
                <input type="hidden" name="" value="中国大陆" class="country-code" />
                <input type="hidden" name="nationCode" value="86" class="mobile-code" />
                <div class="form-item item-flex" id="choose-country">
                    <label>国家和地区</label>
                    <span class="note" style="color:#FD555D;max-width:70%;" id="country-code">中国大陆</span>
                </div>
                <div class="form-item item-flex">
                    <label class="choose-country-code" style="font-size:16px;">+86</label>
                    <input type="text" name="loginCode" id="login-code" value="" title="手机号" data-rule="mobile" class="input validate validate-account" placeholder="请输入手机号" />
                </div>
                <div class="form-item">
                    <input type="text" name="code" id="code" value="" class="input validate" title="短信校验码" placeholder="输入短信校验码" />
                    <button type="button" data-source="" data-type="MEMBERMOBILE" data-way="register" class="button button-white validate-send validate-button">获取校验码</button>
                </div>
                <div class="form-item">
                    <input type="password" name="password" id="password" value="" min-length="6" title="登录密码" class="input validate" placeholder="请设置登录密码" />
                </div>
                <div class="form-item">
                    <input type="password" name="rePassword" id="re-password" value="" min-length="6" title="确认密码" compare-with="password" class="input validate" placeholder="请确认一次登录密码" />
                </div>
                <div class="form-button">
                    <input type="hidden" name="auto" value="1" />
                    <button type="submit" name="register-button" id="register-button" class="button button-l button-red">完成注册</button>
                    <a href="javascript:" class="button button-l button-blue login-reg-button" data-id="login">我有账号，我要登录</a>
                </div>        
            </form>
            <div class="text-center" style="padding:1em 0;font-size:0.8em">
                <a href="/member/forgot">忘记密码了？点这里找回密码</a>
            </div>
        </div>
    </div>
    -->
</div>
<div class="block-state">
    <hr/>
    <span>还可使用第三方网站账号登录</span>
</div>
<div class="login-quick">
    <a href="/member/doQuickLogin/QQ" class="icon icon-qq"></a>
    <a href="/member/doQuickLogin/Weibo" class="icon icon-weibo"></a>
</div>
<script id="random-code-template" type="text/html">
    <div class="form-item">
        <input type="text" name="randomCode" value="" class="input validate" title="验证码" style="width:70%;"
               placeholder="请输入右图中的验证码"/>
        <img src="/randomcode/getcode?height=30&time={{timestr}}" class="validate-img" alt="" title="点击换一个验证码"/>
    </div>
</script>
<script>
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
    <#if LOGINERROR gte 3>
    var random_code_html = template('random-code-template', {'timestr': new Date().getTime()});
    $('#random-code-insert').html(random_code_html);
    </#if>
    var loginSucess = function () {
        var action = $('body').data('function');
        if (action) {
            setTimeout(function () {
                closePopModal();
                eval('{' + action + '}');
                countCart();
            }, 500);
        } else {
            location.reload();
        }
    }

    var registeSucess = function () {
        $.flashTip({position: 'top', type: 'error', message: '注册成功，将继续下一步'});
        var action = $('body').data('function');
        if (action) {
            setTimeout(function () {
                closePopModal();
                eval('{' + action + '}');
                countCart();
            }, 500);
        } else {
            location.reload();
        }
    }
    $('#pas-login').on('click', function () {
        var type = $(this).attr('data-type');
        if (type == "password") {
            $('.header-title').text('密码登录');
            $(this).text('快捷登录')
            $(this).attr('data-type', 'quick');
            $('#login-form').show();
            $('#quick-form').hide();

        } else {
            $('.header-title').text('快速登录');
            $(this).text('密码登录')
            $(this).attr('data-type', 'password');
            $('#login-form').hide();
            $('#quick-form').show();
        }
    })


    $('.login-reg-button').on('touchstart', function () {
        var id = $(this).attr('data-id');
        var title = '';
        if (id == 'login') {
            title = '会员登录';
        } else {
            title = '会员注册';
        }
        $('#modal-title').text(title);
        $('#' + id + '-section').siblings().hide();
        $('#' + id + '-section').show();
        return false;
    });
</script>
<script type="text/javascript">
    function convertclk(_traker) {
        _traker._monitorEvent("BUTTON", "id", "reg-btn", "C", "PC_register");
        _traker._monitorEvent("BUTTON", "id", "register-button", "C", "Mobile_register");
        _traker._monitorEvent("BUTTON", "id", "for-coupon-button", "C", "Mobile_register");
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
                _traker._init("d2cmall.com", " 209502 ");
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
</script>