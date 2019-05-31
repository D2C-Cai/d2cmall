<#import "templates/public_pc.ftl" as m>
<@m.page_header title='重置密码' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-help lazyload">
    <div class="form-info n_form" style="width:667px;padding:5% 0 5% 0;">
        <div class="password step2_box" style="width:667px;height:52px;text-indent:-9999px">更改密码</div>
        <form id="send-form" style="margin-top:100px" class="validate-form" action="/member/resetPassword"
              call-back="modifySave()" success-tip="false" method="post">
            <input type="hidden" name="loginCode" class="validate-account" value="${loginCode}" data-rule="mobile"/>
            <div class="form-item">
                <label>输入新密码</label>
                <input type="password" name="password" id="password" placeholder="请输入8-20位密码" value=""
                       class="input input-l" style="width:60%"/>
                <div class="tip tip-validate" data-target="password" data-rule="sixDigit"></div>
            </div>
            <div class="form-item">
                <label>确认新密码</label>
                <input type="password" name="repassword" id="confirm-password" placeholder="确认上面输入的密码" value=""
                       class="input input-l" style="width:60%"/>
                <div class="tip tip-validate" data-target="confirm-password" compare-with="password"
                     data-rule="sixDigit"></div>
            </div>
            <div class="form-item">
                <label>输入验证码</label>
                <input type="text" name="code" id="validate-code" size="18" title="验证码" placeholder="输入收到的短信验证码"
                       class="input input-l" style="width:43%;" value="" style="width:40%"/>
                <input type="hidden" name="nationCode" class="mobile-code" value="86"/>
                <button type="button" data-source="" data-type="RETRIEVEPASSWORD"
                        class="button button-l button-gray validate-send nb">获取验证码
                </button>
                <div class="tip tip-validate" data-target="validate-code"></div>
            </div>
            <div class="form-button" style="margin-left:150px">
                <button type="submit" class="button">确定</button>
            </div>
        </form>
    </div>
</div>

<div style="margin-top:20px;" class="layout layout-response layout-help lazyload">
    <div class="password-info" style="padding-left:115px;padding-bottom:30px">
        <h3>为什么要进行身份认证？</h3>
        <p style="margin-bottom:20px;">1.为保障您的账户信息安全，在变更账户中的重要信息时需要身份验证，感谢您的理解与支持。</p>
        <p>2.验证身份遇到问题？请提供用户名，手机号，历史发票，点击右侧联系我司<span class="red">在线客服</span>或者拨打<span class="red">${servertel}</span>咨询。
        </p>
    </div>
</div>
<script>
    /*
    var afterSend=function(){
        var second=60;
        var obj=$('.validate-reset-send');
        var time=setInterval(function(){
            if (second<1){
                clearInterval(time);
                obj.removeAttr('disabled').text('重发验证码');
            }else{
                obj.attr('disabled',true).text(second+'秒后重发');
                second--;
            }
        },1000);
    }
    afterSend();
    $('.validate-reset-send').click(function(){
        $.post('/member/sendResetPassword',{'loginCode':$('.validate-reset-code').val()},function(){
            afterSend();
        });
        return false;
    });
    */
    var modifySave = function () {
        var data = $('body').data('return_data');
        if (data.result.status == -1) {
            jAlert(data.result.message);
        } else {
            location.href = "/member/resetSuccess";
        }
        return false;
    }

</script>
<@m.page_footer />