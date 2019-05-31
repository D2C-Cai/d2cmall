<#import "templates/public_mobile.ftl" as m>
<@m.page_header noheader=true title='扫码注册领优惠券' service='false' hastopfix='false'/>
<div class="coupon-register">
    <div class="logo-img"><a href="/"><img src="//static.d2c.cn/common/m/img/reg_logo.png" alt=""/></a></div>
    <div class="coupon-img"><img src="//static.d2c.cn/common/m/img/reg_coupon.jpg" alt=""/></div>
    <div class="coupon-form">
        <form id="coupon-register-form" action="/member/register" method="post">
            <input type="hidden" name="sourceStore" value="${sourceStore}"/>
            <input type="text" name="loginCode" id="loginCode" value="" maxlength="100" placeholder="请输入邮箱或手机"/>
            <input type="password" name="password" id="password" value="" placeholder="请输入密码"/>
            <input type="password" name="rePassword" id="rePassword" value="" placeholder="请确认一次密码"/>
            <button type="submit" name="register" id="register-btn">注 册</button>
        </form>
        <div class="reg-result">
            将该条形码给店员扫描即可享受优惠券。
            <div class="barcode-img">
                <img src=""/>
                <span>DF852855587</span>
            </div>
            <button type="button" name="return" onclick="location.href='/'">浏览更多精彩内容</button>
        </div>
    </div>
</div>

<script>

    $('#coupon-register-form').submit(function () {
        var obj = $(this);
        var account = obj.find('#loginCode');
        var password = obj.find('#password');
        var repassword = obj.find('#rePassword');
        var submit_button = obj.find('button[type=submit]');
        if (account.val() == '') {
            account.focus();
            return false;
        }
        if (password.val() == '') {
            password.focus();
            return false;
        } else if (password.val().length < 6) {
            jAlert('密码位数不能少于6位。');
            return false;
        }
        if (repassword.val() == '') {
            repassword.focus();
            return false;
        }
        if (password.val() != repassword.val()) {
            jAlert('两次输入的密码不一致。');
            return false;
        }
        submit_button.addClass('disabled').attr('disabled', true);
        $.ajax({
            url: '/member/checkLoginCode?' + new Date().getTime(),
            data: {'code': account.val()},
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if (data.result.status < 0) {
                    jAlert(data.result.message);
                    submit_button.removeClass('disabled').removeAttr('disabled');
                    return false;
                } else {
                    $.ajax({
                        url: obj.attr('action'),
                        type: 'post',
                        data: obj.serialize(),
                        dataType: 'json',
                        success: function (data) {
                            submit_button.removeClass('disabled').removeAttr('disabled');
                            if (data.result.email_error) {
                                jAlert(data.result.email_error);
                                return false;
                            } else {
                                $('.barcode-img img').attr('src', '/picture/code?&width=400&height=100&code=' + data.result.data.couponCode);
                                $('.barcode-img span').html(data.result.data.couponCode);
                                $('.reg-result').show();
                            }
                        }
                    });
                }
            }
        });
        return false;
    });

    function check_account(obj) {
        var re_email = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
        var re_mobile = /^(13[0-9]{9})|(16[0-9]{9})|(17[0-9]{9})|(15[0-9][0-9]{8})|(18[0-9][0-9]{8})$/;
        if (!re_email.test(obj.val()) && !re_mobile.test(obj.val())) {
            return false;
        } else {
            return true;
        }
    }
</script>

<@m.page_footer />