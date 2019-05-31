<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="订单详情" url='/member/order' title='订单详情' hastopfix='false'/>
<style>
    .qr-box {
        width: 20em;
        height: 23.75em;
        background: #FFF;
        text-align: center;
    }

    .box-title {
        padding: 2.5em 0 .625em;
        color: #262626;
        font-weight: bold;
        line-height: 1.5;
    }

    .box-cont-tip {
        padding: 10px 0;
        line-height: 1.5;
        color: #7F7F7F;
        font-size: 12px;
    }

    .box-button {
        border: 0;
        width: 235px;
        height: 40px;
        line-height: 40px;
        background: #FD555D;
        color: #FFF;
    }

    .qr-close {
        width: 1.875em;
        height: 1.875em;
        position: absolute;
        bottom: -40px;
        left: 50%;
        transform: translateX(-50%);
    }
</style>
<div class="section lazyload">
    <#if order.endTime?exists && order.endTime?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")>
        <div class="tips text-center">
            <div class="count-down" data-type="split-time"
                 data-endTime="${(order.endTime)?string("yyyy/MM/dd HH:mm:ss")}">请在<strong
                        class="hour">00</strong>小时<strong class="minute">00</strong>分<strong class="second">00</strong>秒内付款，超时将关闭订单
            </div>
        </div>
    </#if>
    <div class="form">
        <div class="form-item item-flex"><label class="grey">订单状态</label><label
                    class="red">${order.orderStatusName}</label></div>
        <div class="form-item item-flex"><label
                    class="grey"><#if order.orderStatus==1||order.orderStatus==-1|| order.paymentType=='COD' ||order.paymentType=='OFFLINE'>实际应付<#else>实际支付</#if></label><label
                    class="red">&yen; ${order.totalPay} 元</div>
    </div>

    <div class="form" id="address-list">
        <div class="form-item item-address" style="padding-right:2em;">
            <span class="icon icon-location"></span>
            <p>${order.reciver} &nbsp;&nbsp;&nbsp; ${order.contact}</p>
            <p>${order.province}${order.city}${order.address}</p>
        </div>
    </div>
    <#if order.drawee>
        <div class="form">
            <div class="form-item item-flex"><label class="grey">发票信息</label><br><label>发票抬头：${(order.drawee)!}</label>
            </div>
        </div>
    </#if>
    <div class="form">
        <#list order.orderItems as orderItem>
            <div class="form-item item-card item-margin clearfix">
                <a href="/product/${orderItem.productId}" class="clearfix">
                    <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                           data-image="<#if orderItem.sp1?exists><#if orderItem.sp1?eval.img?index_of('http')!=-1>${(orderItem.sp1?eval.img)}<#else>${picture_base}/${(orderItem.sp1?eval.img)!}!120</#if></#if>"
                                           alt="${orderItem.productName}"/></span>
                    <span class="title">${orderItem.productName}<#if (orderItem.promotionAmount>0.0)><em
                                class="promotion">${orderItem.promotionName}(优惠${orderItem.promotionAmount}元)</em></#if></span>
                    <span class="property">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}<#if orderItem.sp2 != null>&nbsp;&nbsp;&nbsp;${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value}</#if><#if orderItem.after==0><span
                                class="red">【本件不支持七天无理由退换货】</#if></span>
                <span class="amount"><em class="unit">${orderItem.productPrice}</em></strong>
                    x ${orderItem.productQuantity}&nbsp;&nbsp;实付：<strong
                            class="price">&yen;${(orderItem.actualAmount)}</strong>
                    <em class="float-right">【 ${orderItem.appStatusName}&nbsp;】</em>
                </span>
                </a>
                <#if order.type=='distribution'>
                    <#if orderItem.itemStatus.code gte 2>
                        <div class="bar bar-text text-right">
                            <#if orderItem.deliveryCorpCode=='other'>
                                <a href="//m.kuaidi100.com/index_all.html?type=${orderItem.deliveryCorpName}&postid=${orderItem.deliverySn}&callbackurl=//m.d2cmall.com/member/order/${order.orderSn}"
                                   class="button button-blue">物流查询</a>
                            <#else>
                                <a href="/logistics/info?ordersn=${order.orderSn}&com=${orderItem.deliveryCorpCode}&sn=${orderItem.deliverySn}&productImg=${(orderItem.sp1?eval.img)!}"
                                   class="button button-blue">物流查询</a>
                            </#if>
                            <#if orderItem.status == 'DELIVERED'>
                                <button type="button" data-url="/member/orderItem/confirm/${orderItem.id}"
                                        confirm="确定已经收到货了吗？<br />确认后，该订单即交易完成。" method-type="post"
                                        class="button button-red ajax-request">确认收货</button></#if>
                        </div>
                    </#if>
                <#else>
                    <div class="clearfix bar bar-text">
                        <#if orderItem.itemStatus.code gte 2>
                            <div class="float-left">
                                <#if orderItem.deliveryCorpCode=='other'>
                                    <a href="//m.kuaidi100.com/index_all.html?type=${orderItem.deliveryCorpName}&postid=${orderItem.deliverySn}&callbackurl=//m.d2cmall.com/member/order/${order.orderSn}"
                                       class="button button-blue">物流查询</a>
                                <#else>
                                    <a href="/logistics/info?ordersn=${order.orderSn}&com=${orderItem.deliveryCorpCode}&sn=${orderItem.deliverySn}&productImg=${(orderItem.sp1?eval.img)!}"
                                       class="button button-blue">物流查询</a>
                                </#if>
                                <#if orderItem.status == 'DELIVERED'>
                                    <button type="button" data-url="/member/orderItem/confirm/${orderItem.id}"
                                            confirm="确定已经收到货了吗？<br />确认后，该订单即交易完成。" method-type="post"
                                            class="button button-red ajax-request">确认收货</button></#if>
                            </div>
                        </#if>
                        <div class="float-right text-right">
                            <#if order.orderStatus &gt;= 2 && orderItem.after==1>
                                <#if orderItem.aftering=='refund'>
                                    <a href="/member/${orderItem.aftering}/${orderItem.refundId}"
                                       class="button button-outline">${orderItem.afteringStatus}</a>
                                <#elseif orderItem.aftering=='reship'>
                                    <a href="/member/${orderItem.aftering}/${orderItem.reshipId}"
                                       class="button button-outline">${orderItem.afteringStatus}</a>
                                    <#if orderItem.reship.reshipStatus==2><a
                                        href="/member/reship/logistic/${orderItem.reshipId}" class="button button-red">
                                            请填写快递单号</a></#if>
                                <#elseif orderItem.aftering=='exchange'>
                                    <a href="/member/${orderItem.aftering}/${orderItem.exchangeId}"
                                       class="button button-outline">${orderItem.afteringStatus}</a>
                                    <#if orderItem.exchange.exchangeStatus==1><a
                                        href="/member/exchange/logistic/${orderItem.exchangeId}"
                                        class="button button-red">请填写快递单号</a></#if>
                                    <#if orderItem.exchange.exchangeStatus==4>
                                        <button type="button"data-url="/member/exchange/receive/${orderItem.exchangeId}"
                                                confirm="确定已经收到货了吗？<br />确认后，该订单即交易完成。" method-type="post"
                                                class="button button-red ajax-request">确认收货</button></#if>
                                <#else>
                                    <#if orderItem.aftered=='refund'>
                                        <a href="/member/${orderItem.aftered}/${orderItem.refundId}"
                                           class="button button-outline">${orderItem.afteredStatus}</a>
                                    <#elseif orderItem.aftered=='reship'>
                                        <a href="/member/${orderItem.aftered}/${orderItem.reshipId}"
                                           class="button button-outline">${orderItem.afteredStatus}</a>
                                    <#elseif orderItem.aftered=='exchange'>
                                        <a href="/member/${orderItem.aftered}/${orderItem.exchangeId}"
                                           class="button button-outline">${orderItem.afteredStatus}</a>
                                    </#if>
                                    <#if order.paymentType==3>
                                        <#if orderItem.codAfterApply.reship==1 ||orderItem.codAfterApply.refund==1||orderItem.codAfterApply.exchange==1 >
                                            <a href="javascript:;" class="button button-red app-after">申请售后</a></#if>
                                        <div style="display:none"><#if orderItem.codAfterApply.reship==1>
                                                <a href="/member/reship/create/${orderItem.id}"
                                                   class="button button-red">退款退货</a>
                                            </#if>
                                            <#if orderItem.codAfterApply.refund==1>
                                                <a href="/member/refund/create/${orderItem.id}"
                                                   class="button button-red">仅退款</a>
                                            </#if>
                                            <#if orderItem.codAfterApply.exchange==1>
                                                <a href="/member/exchange/create/${orderItem.id}"
                                                   class="button button-red">仅换货</a>
                                            </#if>
                                        </div>
                                    <#else>
                                        <#if orderItem.onlineAfterApply.reship==1 ||orderItem.onlineAfterApply.refund==1||orderItem.onlineAfterApply.exchange==1 >
                                            <a href="javascript:;" class="button button-red app-after">申请售后</a></#if>
                                        <div style="display:none"><#if orderItem.onlineAfterApply.reship==1>
                                                <a href="/member/reship/create/${orderItem.id}"
                                                   class="button button-red">退款退货</a>
                                            </#if>
                                            <#if orderItem.onlineAfterApply.refund==1>
                                                <a href="/member/refund/create/${orderItem.id}"
                                                   class="button button-red">仅退款</a>
                                            </#if>
                                            <#if orderItem.onlineAfterApply.exchange==1>
                                                <a href="/member/exchange/create/${orderItem.id}"
                                                   class="button button-red">仅换货</a>
                                            </#if></div>
                                    </#if>
                                </#if>
                            </#if>
                        </div>
                    </div>
                </#if>
            </div>
        </#list>
    </div>

    <div class="form">
        <div class="form-item item-flex"><label class="grey">订单编号</label><label>${order.orderSn}</label></div>
        <div class="form-item item-flex"><label
                    class="grey">订单日期</label><label>${order.createDate?string("yyyy/MM/dd HH:mm:ss")}</label></div>
        <div class="form-item item-flex"><label class="grey">订单总计</label><label class="red">&yen; ${order.totalAmount}元
        </div>
        <#if order.couponAmount gt 0>
            <div class="form-item item-flex"><label class="grey">优 惠 券</label><label
                    class="red">&yen; ${order.couponAmount} 元</label></div></#if>

        <#if order.promotionAmount gt 0>
            <div class="form-item item-flex"><label class="grey">商品满减</label><label
                    class="red">&yen;${(order.promotionAmount+order.productPromotionAmount)}元</label></div></#if>

        <#if order.cashCardAmount gt 0>
            <div class="form-item item-flex"><label class="grey">D2C 卡</label><label
                    class="red">&yen; ${order.cashCardAmount} 元</label></div></#if>
        <div class="form-item item-flex"><label class="grey">订单运费</label><label class="red">&yen; ${order.shippingRates}
                元</label></div>

        <#if order.orderStatus==1 || order.orderStatus==-1 || order.paymentType=='COD' || order.paymentType=='OFFLINE'>
            <div class="form-item item-flex"><label class="grey">实际应付</label><label class="red">&yen; ${order.totalPay}
                    元</label></div>
        <#else>
            <div class="form-item item-flex"><label class="grey">支付方式</label><label>${(order.paymentTypeName)!}</div>
            <div class="form-item item-flex"><label class="grey">实际支付</label><label class="red">&yen; ${order.totalPay}
                    元</label></div>
        </#if>
        <#if order.memo>
            <div class="form-item item-flex"><label class="grey">订单留言</label><label>${order.memo}</label></div></#if>
    </div>

    <#if order.orderStatus lt 2>
        <div class="suspend-bar">
            <#if order.orderStatus==1>
                <button type="button" data-url="/member/order/cancel/${order.orderSn}"
                        class="button button-clear ajax-request" template-id="cancel-reason-template"
                        style="width:50%;">取消订单
                </button>
                <#if !order.over><a href="/order/payment/${order.orderSn}" class="button button-red"
                                    style="width:50%;float:right;">去付款</a><#else>
                    <button type="button" class="button" style="width:50%;" onclick="jAlert('订单已经过期或失效，无法再进行付款')">无法付款
                    </button></#if>
            <#elseif order.orderStatus==-1 ||order.orderStatus==-2>
                <button type="button" data-url="/member/order/delete/${order.orderSn}" confirm="确定要删除该订单吗？"
                        redirect-url="/member/order" method-type="post" class="button button-red  ajax-request"
                        style="height:46px;line-height:46px;margin-top:0;">删除订单
                </button>
            </#if>
        </div>
    </#if>
    <#if order.orderStatus==2 && order.paymentType=='COD'>
        <div class="suspend-bar">
            <button type="button" data-url="/member/order/cancel/${order.orderSn}" template-id="cancel-reason-template"
                    class="button button-clear ajax-request">取消订单
            </button>
        </div>
    </#if>
</div>


<script id="cancel-reason-template" type="text/html">
    <header>
        <div class="header">
            <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
            <div class="header-title">取消订单</div>
        </div>
    </header>
    <div class="section">
        <div class="form">
            <form action="{{url}}" class="validate-form" confirm="确定要取消订单吗？" method="post">
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="暂时不考虑" checked>暂时不考虑</label>
                </div>
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="信息错误，重新拍">信息错误，重新拍</label>
                </div>
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="卖家缺货">卖家缺货</label>
                </div>
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="已在门店取货">已在门店取货</label>
                </div>
                <div class="form-button">
                    <button type="submit" name="close-order-btn" class="button button-l button-red">确定</button>
                </div>
            </form>
        </div>
    </div>
</script>

<script id="downloadapp-after-template" type="text/html">
	
</script>

<script>
    $('.app-after').on('touchstart', function () {
        jDownload('<span style="font-size:0.8em">网页端不支持售后申请<br />请下载D2C APP在我的订单中申请</span>');
    });
</script>
<#if order.orderStatus gt 2>
    <@m.page_footer menu=true />
<#else>
    <@m.page_footer />
</#if>

