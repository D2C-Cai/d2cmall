<div class="form">
    <div class="tip tip-warning"><i class="fa fa-info-circle"></i> 官方唯一接受收款支付宝账号：<strong>payment@d2cmall.com</strong>
    </div>
    <form class="validate-form" action="/member/account/recharge" method="post" success-tip="充值成功成功！">
        <input type="hidden" name="payChannel" value="ALIPAY"/>
        <div class="form-item form-item-vertical">
            <label>输入支付时间</label>
            <input type="text" name="payDate" id="pay-date" value="${(.now?string("yyyy-MM-dd"))!}" class="input"/>
            <div class="tip tip-validate" data-target="pay-date"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>输入您的支付宝</label>
            <input type="text" name="payAccount" id="pay-account" value="" class="input"/>
            <div class="tip tip-validate" data-target="pay-account"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>输入支付流水号</label>
            <input type="text" name="paySn" id="pay-sn" value="" class="input"/>
            <div class="tip tip-validate" data-target="pay-sn"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>输入您支付的金额</label>
            <input type="text" name="rechargeAmount" id="recharge-amount" value="" class="input"/>
            <div class="tip tip-validate" data-target="recharge-amount"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>备注</label>
            <textarea name="memo" value="" rows="3" class="input"></textarea>
        </div>
        <div class="form-button">
            <button type="submit" class="button button-l">确定</button>
        </div>
    </form>
</div>
<script>
    function formatNum(num) {
        var str = '';
        if (num.indexOf('.') != -1) {
            num = num + '0000';
            var arr = num.split('.');
            str = arr[0] + '.' + arr[1].substr(0, 2);
        } else {
            str = num + '.00';
        }
        return str;
    }

    $('#recharge-amount').blur(function () {
        var val = $(this).val();
        $(this).val(formatNum(val));
    });
    $('#pay-date').datePicker({
        format: 'Y-m-d',
        date: $('#pay-date').val(),
        current: $('#pay-date').val(),
        starts: 1,
        position: 'r',
        onBeforeShow: function () {
            $('#pay-date').datePickerSetDate($('#pay-date').val(), true);
        },
        onChange: function (formated, dates) {
            $('#pay-date').val(formated);
            $('#pay-date').datePickerHide();
        }
    });
</script>