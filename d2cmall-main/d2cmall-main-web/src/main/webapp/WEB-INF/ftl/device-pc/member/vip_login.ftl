<#import "templates/public_pc.ftl" as m>
<@m.page_header title='会员登录'/>
<@m.top_nav suspend=false />
<div class="main-body bg-vip-login">
    <div class="main-wrap">
        <h1 style="">VIP会员专属通道</h1>
        <form name="form-login" id="form-login" action="/member/viplogin" method="post">
            <input type="hidden" value="${path}" name="path"/>
            <div><font color="red"><#if account_error?exists>${account_error}</#if></font></div>
            <div style="padding:15px;">
                <p><input type="text" name="loginCode" value="输入VIP账号" default="输入VIP账号" class="input"/></p>
            </div>
            <div style="padding:15px;">
                <p><input type="password" name="password" class="input" value="输入登录密码&nbsp;" default="输入登录密码&nbsp;"
                          value="" class="input"/></p>
            </div>
            <input type="checkbox" name="auto" value="1" class="checkbox" id="auto_login" checked
                   style="display:none;"/>
            <div style="padding:15px;">
                <input type="submit" name="submit" id="page-login" value="登 录" class="button"/>
            </div>
        </form>
    </div>
</div>
<script>
    $('.input').focus(function () {
        var def = $(this).attr('default');
        var val = $(this).val();
        if (val == def) {
            $(this).val('');
        }
    });
    $('.input').blur(function () {
        var def = $(this).attr('default');
        var val = $(this).val();
        if (val == '') {
            $(this).val(def);
        }
    });

    $('#form-login').submit(function () {
        if ($('input[name=loginCode]').val() == '' || $('input[name=loginCode]').val() == $('input[name=loginCode]').attr('default')) {
            $('input[name=loginCode]').focus();
            return false;
        }
        if ($('input[name=password]').val() == '' || $('input[name=password]').val() == $('input[name=password]').attr('default')) {
            $('input[name=password]').focus();
            return false;
        }
    });
</script>
<@m.page_footer nofloat=true />