<div class="form form-small">
    <form method="post" confirm="确定要退款吗？" class="validate-form" action="/o2opay/back">
        <input type="hidden" name="oldPaymentId" value="${(oldPayment.id)!}"/>
        <input type="hidden" name="canBack" value="${(oldPayment.subTotal-oldPayment.backAmount)!}">
        <div class="form-item">
            <label>会员账号</label>
            <input type="hidden" name="accountSn" value="${(account.account)!}">
            ${(oldPayment.memberInfo.displayName)!} 手机：${(oldPayment.memberInfo.mobile)!}
        </div>
        <div class="form-item">
            <label>收款金额</label>
            <span style="font-size:14px;font-family:'Arial'">&yen; ${(oldPayment.subTotal)!}</span>
        </div>
        <div class="form-item">
            <label>已退金额</label>
            <span style="font-size:14px;font-family:'Arial'">&yen; ${(oldPayment.backAmount)!}</span>
        </div>
        <div class="form-item">
            <label>零售单号</label>
            <input type="text" id="retail-sn" name="retailSn" value="${(oldPayment.transactionInfo)!}"
                   placeholder="零售单号请务必填写正确" class="input" readonly/>
            <div class="tip tip-validate" data-target="retail-sn"></div>
        </div>
        <div class="form-item">
            <label>退款金额</label>
            <input type="text" id="back-amount" name="backAmount"
                   value="${(oldPayment.subTotal-oldPayment.backAmount)!}" class="input number" style="width:100px;"/>
            可退款：<span
                    style="font-size:14px;font-family:'Arial'">&yen; ${(oldPayment.subTotal-oldPayment.backAmount)!}</span>
            <div class="tip tip-validate" data-target="back-amount" data-function="compareAmount()"></div>
        </div>
        <div class="form-item">
            <label>备注说明</label>
            <textarea id="memo" name="memo" value="" class="input" style="height:60px;">${(oldPayment.memo)!}</textarea>
        </div>
        <div class="form-button">
            <button type="submit" class="button">确定</button>
        </div>
    </form>
</div>
<script>
    $('.number').utilSetNumber();
    var n =${(oldPayment.subTotal-oldPayment.backAmount)!};
    var compareAmount = function () {
        var amount = parseFloat($('#back-amount').val());
        if (amount > n) {
            return '退款金额不能大于' + n + '元';
        } else {
            return true;
        }
    }
</script>
