<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='收银台' title='收银台' js='utils/md5' hastopfix='false'/>
<div class="tips tip-green">
    若无法在手机端支付大额款项，可以在电脑上登录D2C进入我的订单完成付款.
</div>
<div class="section">
    <div class="form">
        <form name="pay-form" id="pay-form" action="/payment" method="post">
            <input type="hidden" name="id" value="${result.datas.couponOrder.id}"/>
            <input type="hidden" name="sn" value="${(result.datas.couponOrder.orderSn)}"/>
            <input type="hidden" name="orderType" value="coupon"/>
            <div class="form-item item-flex item-margin">
                <label>应付金额</label><label class="red">&yen; ${(result.datas.couponOrder.totalAmount)} 元</label>
            </div>
            <#if browser=='wechat'>
                <div class="form-item item-pay wechat-pay">
                    <label>
                        <input type="radio" name="paymentType" value="WXPAY"/>
                        <p class="img"><img src="${static_base}/nm/css/img/icon_pay_weixin.png"/></p>
                        <p class="title">微信支付</p>
                        <p>使用微信支付，安全方便快捷</p>
                    </label>
                </div>
            <#else>
                <div class="form-item item-pay normal-pay">
                    <label>
                        <input type="radio" name="paymentType" value="ALIPAY"/>
                        <p class="img"><img src="${static_base}/nm/css/img/icon_pay_alipay.png"/></p>
                        <p class="title">手机支付宝</p>
                        <p>使用支付宝支付，保证有充足的余额哦</p>
                    </label>
                </div>
            </#if>
            <div class="form-button">
                <button type="submit" name="submit-button" id="pay-button" class="button button-l button-red">确定
                </button>
            </div>
            <input type="hidden" name="paymethod" value="creditPay"/>
            <input type="hidden" name="defaultbank" value="ALIPAY"/>
        </form>
    </div>
</div>
<script id="wallet-pay-template" type="text/html">
    <div class="form">
        <form name="" class="validate-form" action="/member/account/password" call-back="checkWalletPassword"
              method="post">
            <div class="form-item">
                <input type="password" name="" id="modal-password" value="" class="input input-l text-center validate"
                       title="支付密码" placeholder="请输入钱包支付密码"/>
                <input type="hidden" name="password" id="md5-password"/>
            </div>
            <div class="form-button">
                <button type="submit" name="submit-button" id="pay-button" class="button button-red">确定支付</button>
            </div>
        </form>
    </div>
</script>
<script>
    $('.wallet-pay label:not(.disabled)').on('touchstart', function () {
        var html = template('wallet-pay-template', {});
        setTimeout(function () {
            $.utilBaseModal.create({
                type: 'box',
                inAnimate: 'zoomIn',
                outAnimate: 'zoomOut',
                scrollLock: true,
            });
            $('#box-modal-content').html(html);
        }, 300);
    });

    $('.normal-pay label:not(.disabled)').on('touchstart', function () {
        $('#pay-form').attr('action', '/payment');
    });
    $('.wechat-pay label:not(.disabled)').on('touchstart', function () {
        $('#pay-form').attr('action', '/payment/weixin/gz');
    });

    $(document).on('blur', '#modal-password', function () {
        $('#md5-password').val(hex_md5($(this).val()));
    });

    $('#pay-form').submit(function () {
        if ($('input[name=paymentType]:checked').val() == '' || $('input[name=paymentType]:checked').val() == undefined) {
            //$.flashTip({position:'center',type:'error',message:'请选择支付方式！'});
            jAlert('请选择支付方式！');
            return false;
        }
        if ($('.wallet-pay input[type=radio]:checked').val() != undefined) {
            if ($('#pay-password').val() == '') {
                jAlert('支付密码不能为空');
                return false;
            }
        }

    });

    function checkWalletPassword() {
        $('.wallet-pay input[type=radio]').attr('checked', 'checked');
        $('#pay-password').val($('#md5-password').val());
        $('#pay-form').submit();
    }

</script>

<@m.page_footer />