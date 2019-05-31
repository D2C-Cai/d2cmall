<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='账号绑定' service='false' hastopfix='false'/>
<div class="order-status-bar">
    通过与微信绑定D2C账号能更好的让我们服务您。<br/>若不想绑定，也可直接跳过，系统会为您创建一个账号。
</div>
<div class="wrap-main">
    <div class="login-box">
        <form name="bind" id="bind-form" action="/callback/bindMember" method="post">
            <input type="hidden" name="openId" value="${openId}"/>
            <input type="hidden" name="source" value="${source}"/>
            <input type="hidden" name="type" value="SKIP"/>

            <div>
                <p style="height:2em">请输入您的账号</p>
                <p><input type="text" name="loginCode" value="" class="text-center" placeholder="输入手机号"/></p>
            </div>
            <div id="have-account" class="display-none">
                <p style="padding-top:2em;height:2em">这个账号已经注册过了哦，请输入密码继续</p>
                <p><input type="password" name="password" value="" class="text-center" placeholder="输入登录密码"/></p>
            </div>
            <div id="no-account" class="display-none">
                <p style="padding-top:2em;height:2em">设置一个登录密码，以后可用此账号直接登录</p>
                <p><input type="password" name="password" value="" class="text-center" placeholder="输入6位以上的密码"/></p>
                <p><input type="password" name="repassword" value="" class="text-center" placeholder="确认一次输入的密码"/></p>
            </div>
            <div style="padding-top:15px;">
                <p>
                    <button type="submit" id="submit-button" style="width:49%;" class="display-none">确定</button>
                    <button type="button" id="next-button" style="width:49%;">下一步</button>
                    <button type="button" id="cancel-button" style="width:49%;" class="warning">跳过</button>
                </p>
            </div>
        </form>
    </div>
</div>
<hr/>
<script>
    $('#bind-form').submit(function () {
        var form = $(this);
        jConfirm('确定要提交吗？', function (r) {
            if (r) {
                $.ajax({
                    url: form.attr('action'),
                    type: form.attr('method'),
                    data: form.serialize(),
                    dataType: 'json',
                    success: function (data) {
                        if (data.result.status == 1) {
                            location.href = "/";
                        } else {
                            jAlert(data.result.message);
                        }
                    }
                });
            }
        });
        return false;
    });
    $('#cancel-button').click(function () {
        jConfirm('确定要直接跳过吗？跳过此步骤，将会为你建立一个独立的账户。', function (r) {
            if (r) {
                $('input[name=type]').val('SKIP');
                $('#bind-form').submit();
            }
            return false;
        });
    });

    $('input[name=loginCode]').blur(function () {
        var login_code = $('input[name=loginCode]');
        var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        var re_mobile = /^[1][3456789]\d{9}$/;
        if (!re_email.test(login_code.val()) && !re_mobile.test(login_code.val())) {
            jAlert('你输入的账号格式不对，必须是邮箱或手机号');
            return false;
        } else {
            $.ajax({
                url: '/member/checkLoginCode',
                type: 'post',
                data: {'code': login_code.val()},
                dataType: 'json',
                success: function (data) {
                    $('#submit-button').show();
                    $('#next-button').hide();
                    if (data.result.status == 1) {
                        $('#no-account').show();
                        $('#no-account').find('input').removeAttr('disabled');
                        $('#have-account').hide();
                        $('#have-account').find('input').attr('disabled', 'disabled');
                        $('input[name=type]').val('REGISTER');
                    } else {
                        $('#no-account').hide();
                        $('#no-account').find('input').attr('disabled', 'disabled');
                        $('#have-account').show();
                        $('#have-account').find('input').removeAttr('disabled');
                        $('input[name=type]').val('LOGIN');
                        return false;
                    }
                }
            })
        }
    });
</script>
<@m.page_footer />