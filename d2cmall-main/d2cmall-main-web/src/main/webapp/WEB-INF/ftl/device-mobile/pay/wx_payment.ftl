<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="微信安全支付页面" title='微信安全支付页面' hastopfix='false'/>
<div class="section">
    <div class="text-center padding" style="line-height:300%;padding-top:20%;">
        <div><img src="${static_base}/nm/img/wxpay-logo.png"/></div>
        <div id="payment-tip"><strong class="green">订单创建成功，正在接入微信进行支付</strong></div>
    </div>
</div>
<script>
    function onBridgeReady() {
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId": "${appId}",     //公众号名称，由商户传入
                "timeStamp": "${timeStamp}",         //时间戳，自1970年以来的秒数
                "nonceStr": "${nonceStr}", //随机串
                "package": "${package}",
                "signType": "MD5",         //微信签名方式:
                "paySign": "${paySign}" //微信签名
            },
            function (res) {
                var str = '';
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    str = '<strong class="green">微信支付成功，将跳转到“我的订单”。</span>';
                } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                    str = '<span class="red">支付被取消，可以在订单管理页面重新进行支付。</span>';
                } else if (res.err_msg == "get_brand_wcpay_request:fail") {
                    str = '<span class="red">支付失败，可以在订单管理页面重新进行支付。</span>';
                }
                $('#payment-tip').html(str);
                setTimeout(function () {
                    window.location.href = "/member/order"; //跳转到我的订单
                }, 1000);
            }
        );
    }

    if (typeof WeixinJSBridge == "undefined") {
        if (document.addEventListener) {
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        } else if (document.attachEvent) {
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    } else {
        onBridgeReady();
    }
</script>
<@m.page_footer />