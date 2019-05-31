<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='收银台' title='收银台' js='utils/md5' hastopfix='false'/>
<div class="tips tip-green">
    若无法在手机端支付大额款项，可以在电脑上登录D2C进入我的订单完成付款.
</div>
<div class="section">
    <#assign order=result.datas.order/>
    <div class="form">
        <form name="pay-form" id="pay-form" action="/payment" method="post">
            <input type="hidden" name="id" value="${order.id}"/>
            <input type="hidden" name="sn" value="${(order.orderSn)}"/>
            <input type="hidden" name="orderType" value="order"/>
            <#if cod==0>
                <div class="form-item item-flex red">
                    <span style="font-size:0.8em"> 该订单暂只支持以下付款方式，如需要其他付款方式，请打开D2C小程序或者下载APP付款</span>
                </div>
            </#if>
            <div class="form-item item-flex item-margin">
                <label>应付金额</label><label class="red">&yen; ${(order.totalPay)} 元</label>
            </div>
            <#if m.LOGINMEMBER.id!=null && m.LOGINMEMBER.distributorId!=null>
            <#elseif cod==1>
                <div class="form-item item-pay normal-pay">
                    <label>
                        <input type="radio" name="paymentType" value="COD"/>
                        <p class="img"><img src="${static_base}/nm/css/img/icon_pay_cod.png"/></p>
                        <p class="title">货到付款(刷卡和现金支付)</p>
                        <p>仅限中国大陆，不支持港澳台及国外。</p>
                    </label>
                </div>
            </#if>
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
            <!-- 暂时关闭钱包支付
            <#if account?exists && account.status==1 && account.totalAmount &gt;0>
            <div class="form-item item-pay wallet-pay">
                <label<#if account.availableTotalAmount lt (order.totalPay)> class="disabled"</#if>>
                    <input type="radio" class="WALLET" name="paymentType" value="WALLET"<#if account.availableTotalAmount lt (order.totalPay)> disabled</#if> />
                    <input type="hidden" name="password" id="pay-password" value="" />
                    <p class="img"><img src="${static_base}/nm/css/img/icon_pay_wallet.png" /></p>
                    <p class="title">D2C钱包</p>
                    <p>可用余额  &yen;${account.availableTotalAmount?string("currency")?substring(1)}<#if account.availableTotalAmount lt (order.totalPay)> ，余额不足</#if></p>
                </label>
            </div>
            </#if>
           -->
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
    var pubsage_sign = "";
    var pubsage_url = "";
    var url = window.location.href;
    var domain = document.domain;
    var mat = url.match(/adtiid=([^&#]*)/);
    var iid = mat ? mat[1] : "";
    if (iid == "" || typeof (iid) == "undefined") {
        var uid = function (cookiekey) {
            var strCookie = document.cookie;
            var arrCookie = strCookie.split(";");
            var value = "";
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                if (cookiekey == arr[0].replace(/(^\s*)|(\s*$)/g, "")) {
                    value = arr[1];
                    break
                }
            }
            return value
        }("_ws_uid");
        if (uid == "" || typeof (uid) == "undefined") {
            pubsage_url = "tca.sagetrc.com"
        } else {
            var strAry = domain.split(".");
            var host = strAry[strAry.length - 2] + "." + strAry[strAry.length - 1];
            var pf = uid.split(".");
            if (pf[0] == "tqh") {
                pubsage_url = "tqh_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tgg") {
                pubsage_url = "tgg_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tsg") {
                pubsage_url = "tsg_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tdsp") {
                pubsage_url = "tdsp_" + pubsage_sign + "." + host
            }
            if (pf[0] != "tsg" && pf[0] != "tgg" && pf[0] != "tqh" && pf[0] != "tdsp") {
                pubsage_url = "trc_" + pubsage_sign + "." + host
            }
        }
    } else {
        if (iid.match("^g") != null) {
            pubsage_url = "tgg.sagetrc.com"
        }
        if (iid.match("^q") != null) {
            pubsage_url = "tqh.sagetrc.com"
        }
        if (iid.match("^s") != null) {
            pubsage_url = "tsg.sagetrc.com"
        }
        if (iid.match("^d") != null) {
            pubsage_url = "tdsp.sagetrc.com"
        }
        if (iid.match("^g|^q|^s|^d") == null) {
            pubsage_url = "t1.sagetrc.com"
        }
    }
    var pubsage_conv_id = 27449;
    var pubsage_conv = pubsage_conv || [];
    (function () {
        try {
            var d = document;
            var at = d.createElement("script");
            at.type = "text/javascript";
            at.async = true;
            at.src = ("https:" == document.location.protocol ? "https:" : "http:") + "//" + pubsage_url + "/trc/atac/conv_x.js?id=" + pubsage_conv_id;
            var s = d.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(at, s)
        } catch (e) {
        }
    })();
    pubsage_conv.push(['setOrderInfo', [["${(order.orderSn)}", "${(order.orderItems[0].productId)!}", "${(order.orderItems[0].productName)!}", "${(order.totalPay)!}"]]])


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

    _hmt.push(["_trackOrder", {
        "orderId": '${(order.orderSn)}',
        "orderTotal": '${(order.totalPay)!}',
        "item": [
            {
                "skuId": "${(order.orderItems[0].productId)!}",
                "skuName": "${(order.orderItems[0].productName)!}",
                "category": "",
                "Price": "${(order.orderItems[0].productPrice)!}",
                "Quantity": "${(order.orderItems[0].productQuantity)!}"
            }
        ]
    }]);
</script>
<script type="text/javascript">
    function _ca_convert(_traker) {
        try {
            setTimeout(function () {
                _traker._traceConversion("Mobile_Order", '{"orderId":"${(order.orderSn)}","total":"${(order.totalPay)}"}');
            }, 1000);
        } catch (e) {
        }
    }
</script>
<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'orderSmtSuccess',
        productId_list: '<#list order.orderItems as orderItem>${orderItem.productId},</#list>',
        p_zp_conversion: '84b94c1dba7ef721b427e2e094908798',
        p_zp_convinfo: '${(order.orderSn)},${(order.totalPay)!}'
    };
</script>
<@m.page_footer />