<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='会员注册' service='false' hastopfix='false'/>
<div class="section">
    <h1 style="margin:2em 0 0 0;text-align:center;">注册D2C会员</h1>
    <div class="form">
        <form id="register-form" class="validate-form" redirect-url="${path!'/'}" action="/member/register"
              method="post">
            <input type="hidden" name="" value="中国大陆" class="country-code"/>
            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
            <div class="form-item item-flex" id="choose-country">
                <label>国家和地区</label>
                <span class="note" style="color:#FD555D;max-width:70%;" id="country-code">中国大陆</span>
            </div>
            <div class="form-item item-flex">
                <label class="choose-country-code" style="font-size:16px;">+86</label>
                <input type="text" name="loginCode" id="login-code" value="" title="手机号" data-rule="mobile"
                       class="input validate validate-account" placeholder="请输入手机号"/>
            </div>
            <div class="form-item item-flex">
                <label>验证码</label>
                <input type="text" name="code" id="code" value="" class="input validate" title="短信校验码"
                       placeholder="请输入短信校验码"/>
                <button type="button" data-source="" data-type="MEMBERMOBILE" data-way="register"
                        class="button button-s button-white validate-send validate-button">获取校验码
                </button>
            </div>
            <div class="form-item item-flex">
                <label>登录密码</label>
                <input type="password" name="password" id="password" value="" min-length="8" title="字母数字符号组合"
                       class="input validate" max-length="20" placeholder="请设置8-20位登录密码"/>
            </div>
            <div class="form-item item-flex">
                <label>确认密码</label>
                <input type="password" name="rePassword" id="re-password" value="" min-length="8" max-length="20"
                       title="重新输入登录密码" compare-with="password" class="input validate" placeholder="请确认一次登录密码"/>
            </div>
            <div class="form-button">
                <input type="hidden" name="auto" value="0"/>
                <button type="submit" name="register-button" id="register-button" class="button button-l button-red">
                    完成注册
                </button>
                <a href="/member/login" class="button button-l button-blue">我有账号，我要登录</a>
            </div>
        </form>
        <div class="text-center" style="padding:1em 0;font-size:0.8em">
            <a href="/member/forgot">忘记密码了？点这里找回密码</a>
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

<@m.page_footer menu=true />