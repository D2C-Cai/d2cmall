<#import "device-mobile/report/common.ftl" as m>
<@m.page_header title='用户确认支付' noheader=true service='false' hastopfix='false'/>
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/account/bill" class="back">
            <span class="icon icon-chevron-left">消费充值记录</span>
        </a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
</div>
<div style="height:3.5em;"></div>
<div class="wrap-main">
    <div class="login-box">
        <form id="submit-form" action="/member/bill/pay" method="post">
            <p>
                <input type='hidden' name='payId' value='${(payitem.id)!}'/>
                <input type="text" class="validate-mobile" name="mobile" value="${(account.mobile)!}"
                       data-rel="${(payitem.sn)!}" placeholder="输入您的手机号：" readonly/>
                <button id="button-sms" type="button" class="button-m validate-send validate-button">获取校验码</button>
                <input type="text" name="code" value="" class="input-l" placeholder="输入您的短信校验码"/>
                <input type="password" name="password" value="" placeholder="输入您的支付密码："/>
                <button type="submit" name="submit" style="background:red">确定</button>
            </p>
        </form>
    </div>
</div>
<script>
    $('.validate-send').click(function () {
        var this_obj = $(this);
        var mobile_obj = $('.validate-mobile');
        var mobile = mobile_obj.val();
        if (mobile == '') {
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
                        location.href = '/member/account/payItem';
                    } else {
                        alert(data.result.message);
                    }
                }
            });
        }
        return false;
    });
</script>
<@m.page_footer/>