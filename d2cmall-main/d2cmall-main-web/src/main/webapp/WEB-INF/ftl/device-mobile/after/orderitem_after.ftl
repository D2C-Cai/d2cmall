<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="申请售后" title='申请售后' button='false' service='false'/>
<div class="section">
    <div class="form">
        <div class="form-item item-card item-margin clearfix">
            <a href="/product/${orderItem.productId}" class="clearfix">
                <span class="img"><img
                            src="<#if orderItem.sp1?exists>${picture_base}/${(orderItem.sp1?eval.img)!}!120</#if>"
                            alt="${orderItem.productName}"/></span>
                <span class="title">${orderItem.productName}<#if (orderItem.promotionAmount>0.0)><em
                            class="promotion">${orderItem.promotionName}(优惠${orderItem.promotionAmount}
                        元)</em></#if></span>
                <span class="property">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}&nbsp;&nbsp;&nbsp;${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value}</span>
                <span class="amount"><em class="unit">${orderItem.productPrice}</em></strong>
                    x ${orderItem.productQuantity}&nbsp;&nbsp;金额：<strong
                            class="price">&yen;${(orderItem.actualAmount)!}</strong>
                 <em class="float-right">【 ${orderItem.appStatusName}&nbsp;】</em>
                </span>
            </a>
        </div>
    </div>
    <div class="form">
        <#if order.paymentType=='COD'>
            <#if orderItem.codAfterApply.exchange==1>
                <a href="/member/exchange/create/${orderItem.id}" class="form-item">
                    <i class="icon"></i>我要申请换货
                    <i class="icon icon-arrow-right"></i>
                </a>
            </#if>
            <#if orderItem.codAfterApply.reship==1>
                <a href="/member/reship/create/${orderItem.id}" class="form-item">
                    <i class="icon"></i>我要申请退款退货
                    <i class="icon icon-arrow-right"></i>
                </a>
            </#if>
            <#if orderItem.codAfterApply.refund==1>
                <a href="/member/refund/create/${orderItem.id}" class="form-item">
                    <i class="icon"></i>我要申请退款
                    <i class="icon icon-arrow-right"></i>
                </a>
            </#if>
        <#else>
            <#if orderItem.onlineAfterApply.exchange==1>
                <a href="/member/exchange/create/${orderItem.id}" class="form-item">
                    <i class="icon"></i>我要申请换货
                    <i class="icon icon-arrow-right"></i>
                </a>
            </#if>
            <#if orderItem.onlineAfterApply.reship==1>
                <a href="/member/reship/create/${orderItem.id}" class="form-item">
                    <i class="icon"></i>我要申请退款退货
                    <i class="icon icon-arrow-right"></i>
                </a>
            </#if>
            <#if orderItem.onlineAfterApply.refund==1>
                <a href="/member/refund/create/${orderItem.id}" class="form-item">
                    <i class="icon"></i>我要申请退款
                    <i class="icon icon-arrow-right"></i>
                </a>
            </#if>
        </#if>
    </div>
</div>
<@m.page_footer />