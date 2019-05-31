<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='找回密码' title='找回密码' service='false' hastopfix='false'/>
<div class="section">
    <div class="tips tip-warining">如果您忘记密码，需要输入你的手机号或邮箱（登录账号），根据系统发送的校验码进行密码重置操作。</div>
    <div class="form">
        <form id="send-form" action="/member/sendResetPassword" method="post" target="_self">
            <div class="form-item">
                <input type="text" name="loginCode" id="forget-code" value="" class="input"
                       placeholder="请输入你的登录账号(手机或邮箱)"/>
            </div>
            <div class="form-button">
                <button type="submit" name="send-button" id="send-button" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
</div>
<script>
    $('#send-button').on('touchstart', function () {
        if ($('#forget-code').val() == "") {
            $('#forget-code').focus();
            return false;
        }
        $.ajax({
            url: '/member/forgetLoginCode',
            type: 'post',
            data: {"loginCode": $('#forget-code').val()},
            dataType: 'json',
            success: function (data) {
                if (data.result.status == -1) {
                    $.flashTip({position: 'top', type: 'error', message: data.result.message});
                    return false;
                } else {
                    $('#send-form').submit();
                }
            }
        });
        return false;
    });
</script>
<hr/>
<@m.page_footer menu=true />