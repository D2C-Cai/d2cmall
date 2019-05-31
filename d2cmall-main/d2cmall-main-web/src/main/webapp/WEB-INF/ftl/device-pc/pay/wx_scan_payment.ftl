<#import "templates/public_pc.ftl" as m>
<@m.page_header title='微信扫码支付'  js=''/>
<style>
    body {
        background: #333;
    }
</style>
<div class="wxpay-wrap">
    <h3 class="wxpay-title">使用微信扫一扫，即可支付</h3>
    <div class="wxpay-price">应支付金额：<strong>${(totalFee)}</strong> 元</div>
    <div class="wxpay-code">
        <img src="/picture/code?type=1&width=250&height=250&noLogo=true&code=${(code_url)!}" class="wxpay-code-img"/>
        <img src="${static_base}/nc/img/wxpay_tip.png" class="wxpay-tip"/>
    </div>
</div>
<script>
    setInterval(function () {
        $.get('/member/orderstatus/${(orderSn)!}', function (data) {
            if (data.result.status == 1) {
                $.flashTip('订单支付成功！', 'success');
                setTimeout(function () {
                    location.href = '/member/order/${(orderSn)!}?gaOrdersn=${(orderSn)!}';
                }, 2000);
            } else if (data.result.status == 3) {
                $.flashTip('支付成功！', 'success');
                setTimeout(function () {
                    location.href = '/auction/member/mymargin';
                }, 2000);
            }
        }, 'json');
    }, 2000);
</script>
</body>
</html>