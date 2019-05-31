<div style="padding:5% 0;width:400px;">
    <form id="submit-form" action="/member/account/pay" method="post">
        <input type='hidden' name='payId' value='${(payitem.id)!}'/>
        <table>
            <tr>
                <td width="150px">输入手机号码：</td>
                <td width="230px"><input type="text" class="validate-mobile" name="mobile" value="${(account.mobile)!}"
                                         data-type="mobile" class="input-l" data-rel="${(payitem.sn)!}" readonly/>
            </tr>
            <tr>
                <td width="150px">输入短信验证码：</td>
                <td width="230px"><input type="text" name="code" value="" size="6" class="input-l"/>
                    <button id="button-sms" class="button-m validate-send">点击获取验证码</button>
                </td>
            </tr>
            <tr>
                <td width="150px">输入您的支付密码：</td>
                <td width="230px"><input type="password" name="password" value="" class="input-l"/></td>
            </tr>
            <tr class="text-center">
                <td colspan="2"><input type="submit" name="submit" value="确定" class="button-l b-b"/></td>
            </tr>
        </table>
    </form>
</div>
<script>
    $('.validate-send').click(function () {
        var this_obj = $(this);
        var mobile_obj = $('.validate-mobile');
        var mobile = mobile_obj.val();
        if (!mobile_obj.mobile()) {
            mobile_obj.focus();
            return false;
        }
        var second = 60;
        this_obj.attr('disabled', true);
        var time = setInterval(function () {
            if (second <= 0) {
                clearInterval(time);
                this_obj.removeAttr('disabled').text('重发校验码');
            } else {
                this_obj.text(second + '秒后重发');
                second--;
            }
        }, 1000);
        var mb = $('input[name=mobile]').val();
        var sn = $('input[name=mobile]').attr('data-rel')
        $.ajax({
            url: '/sms/send',
            type: 'post',
            data: {mobile: mb, source: sn, type: 'Bill'},
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                } else {
                    window.parent.jAlert(data.result.message);
                }
            }
        });
        return false;
    });
    $('#submit-form').submit(function () {
        var mobile = $('input[name=mobile]').val();
        var code = $('input[name=code]').val();
        var password = $('input[name=password]').val();
        if (mobile == "" || code == "" || password == "") {
            return false;
        } else {
            $.ajax({
                url: $(this).attr('action'),
                type: 'post',
                data: $(this).serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        window.location.href = '/member/account/bill';
                    } else {
                        window.parent.jAlert(data.result.message);
                    }
                }
            });
        }
        return false;
    });
    $(function () {
        $('input[name=mobile]').number();
    });
</script>
