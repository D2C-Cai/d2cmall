<#import "templates/public_pc.ftl" as m>
<@m.page_header title='会员登录'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap text-center" style="padding-top:100px;height:400px;line-height:35px;">
        <form name="bind" id="bind-form" action="/callback/bindMember" method="post">
            <input type="hidden" name="openId" value="${openId}"/>
            <input type="hidden" name="source" value="${source}"/>
            <input type="hidden" name="type" value="SKIP"/>
            <div>
                您是通过第三方平台登录到D2C网站的，为了确保能更好的服务您，需要提供您的手机号或邮箱。<a href="javascript:" class="skip">以后再说</a>
            </div>
            <div>
                <p>请输入邮箱或手机号</p>
                <p><input type="text" name="loginCode" style="font-size:18px;" class="input-l text-center" value=""/>
                </p>
            </div>
            <div id="have-account" class="display-none">
                <p>这个账号已经注册过了哦，请输入密码继续</p>
                <p><input type="password" name="password" style="font-size:18px;" class="input-l text-center" value=""/>
                </p>
            </div>
            <div id="no-account" class="display-none">
                <p>请设置登录密码，以后也可以用这个账号直接登录网站</p>
                <p></p>
                <p>输入6位以上的密码</p>
                <p><input type="password" name="password" style="font-size:18px;" class="input-l text-center" value=""/>
                </p>
                <p>确认一次输入的密码</p>
                <p><input type="password" name="repassword" style="font-size:18px;" class="input-l text-center"
                          value=""/></p>
            </div>
            <div id="submit-button" class="display-none" style="padding-top:15px;">
                <p>
                    <button type="submit" name="" class="b-b button-l">确定</button>
                </p>
            </div>
        </form>
    </div>
</div>
<script>
    $('#bind-form').submit(function () {
        var form = $(this);
        jConfirm('确定要提交吗？', '', function (r) {
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
    $('.skip').click(function () {
        jConfirm('确定要直接跳过吗？跳过此步骤，将会为你建立一个独立的账户。', '', function (r) {
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
            return false;
        } else {
            $.post('/member/checkLoginCode?' + new Date().getTime(), {'code': login_code.val()}, function (data) {
                $('#submit-button').show();
                if (data.result.status < 0) {
                    $('#no-account').hide();
                    $('#no-account').find('input').attr('disabled', 'disabled');
                    $('#have-account').show();
                    $('#have-account').find('input').removeAttr('disabled');
                    $('input[name=type]').val('LOGIN');
                    return false;
                } else {
                    $('#no-account').show();
                    $('#no-account').find('input').removeAttr('disabled');
                    $('#have-account').hide();
                    $('#have-account').find('input').attr('disabled', 'disabled');
                    $('input[name=type]').val('REGISTER');
                    return false;
                }
            }, 'JSON');
        }
    });


</script>
<@m.page_footer nofloat=true />