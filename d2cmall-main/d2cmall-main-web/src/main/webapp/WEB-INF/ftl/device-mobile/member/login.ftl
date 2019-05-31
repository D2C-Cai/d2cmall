<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='会员登录' service='false' hastopfix='false'/>
<div class="section">
    <div style="text-align:center;margin:3em 0;">
        <img src="//static.d2c.cn/common/nm/img/icon_launcher.png" width="100"/>
    </div>
    <div class="form">
        <form id="login-form" class="validate-form" redirect-url="${(path)!'/'}" success-tip="登录成功！正在跳转..."
              action="/member/quick/login" method="post">
            <input type="hidden" name="nationCode" disabled="disabled" value="86" class="mobile-code"/>
            <div class="form-item">
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
                <input type="hidden" name="auto" value="0"/>
                <button type="submit" name="login-button" class="button button-l button-red">登录</button>

            </div>
        </form>
        <div class="text-left" style="padding:1em 0.8em;font-size:0.8em">
            <a href="/member/forgot">忘记密码？</a>
            <a href="/member/register" style="float:right">立即注册</a>
        </div>
    </div>
</div>
<div class="block-state">
    <hr/>
    <span>还可使用第三方网站账号登录</span>
</div>
<div class="login-quick">
    <a href="doQuickLogin/QQ" class="icon icon-qq"></a>
    <a href="doQuickLogin/Weibo" class="icon icon-weibo"></a>
</div>
<script id="random-code-template" type="text/html">
    <div class="form-item">
        <input type="text" name="randomCode" value="" class="input validate" title="验证码" style="width:70%;"
               placeholder="请输入右图中的验证码"/>
        <img src="/randomcode/getcode?height=30&time={{timestr}}" class="validate-img" alt="" title="点击换一个验证码"/>
    </div>
</script>
<hr/>
<#if LOGINERROR gte 3>
    <script>
        var random_code_html = template('random-code-template', {'timestr': new Date().getTime()});
        $('#random-code-insert').html(random_code_html);
    </script>
</#if>
<@m.page_footer menu=true />