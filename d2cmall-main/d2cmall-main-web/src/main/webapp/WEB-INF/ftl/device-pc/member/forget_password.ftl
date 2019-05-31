<#import "templates/public_pc.ftl" as m>
<@m.page_header title='重置密码' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-help lazyload" style="margin-top:20px;">
    <h1 style="margin:0 50px;padding:18px 65px;font:600 20px 'Microsoft Yahei','Pingfang SC','Helvetica Neue';border-bottom:1px solid #CCC">
        找回密码</h1>
    <div class="n_form form-info" style="width:667px;padding:5% 0 5% 0;">
        <div class="password step1_box" style="width:667px;height:52px;text-indent:-9999px">验证身份</div>
        <form id="send-form" style="margin-top:100px" class="validate-form" action="/member/sendResetPassword"
              method="post" target="_self">

            <div class="form-item" style="border:none;">
                <label>登录账户</label>
                <input type="text" name="loginCode" id="login-code" class=" input input-l" size="30" style="width:60%"/>
                <div class="tip tip-validate" data-target="login-code" data-function="validateLoginCode"></div>
            </div>

            <div class="form-button" style="margin-left:150px">
                <button type="submit" class="button" style="font-size:14px">下一步</button>
            </div>
        </form>
    </div>
</div>
<div style="margin-top:20px;" class="layout layout-response layout-help lazyload">
    <div class="password-info" style="padding-left:115px;padding-bottom:30px">
        <h3>为什么要进行身份认证？</h3>
        <p style="margin-bottom:20px;">1.为保障您的账户信息安全，在变更账户中的重要信息时需要身份验证，感谢您的理解与支持。</p>
        <p>2.验证身份遇到问题？请提供用户名，手机号，历史发票，点击右侧联系我司<span class="red">在线客服</span>或者拨打<span class="red">4008403666</span>咨询。
        </p>
    </div>
</div>
<script>
    var validateLoginCode = function () {
        var code = $('#login-code');
        var error = 0, msg = null;
        if (code.val() == '') {
            error++;
            return '请输入手机号或邮箱';
        } else if (!code.utilValidateMobile() && !code.utilValidateEmail()) {
            error++;
            return '请输入正确的手机号或邮箱';
        }
        if (error == 0) {
            $.when($.ajax({
                url: '/member/forgetLoginCode',
                data: {"loginCode": code.val()},
                type: 'post',
                dataType: 'json'
            })).then(function (data) {
                if (data.result.status == -1) {
                    $('.tip[data-target="login-code"]').html(data.result.message).show();
                    return false;
                } else {
                    $('.tip[data-target="login-code"]').hide();
                    $('#send-form').removeClass('validate-form');
                    $('#send-form').submit();
                }
            }).fail(function () {
                setTimeout(function () {
                    $('.tip[data-target="login-code"]').html('请求失败，请重试').show();
                }, 2000);
                return false;
            });
        }
    }
</script>
<@m.page_footer />