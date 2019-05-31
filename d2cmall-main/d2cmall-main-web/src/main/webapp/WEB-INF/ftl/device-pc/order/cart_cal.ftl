<div>
    <div class="shipFee">运费:${result.shipFee}</div>
    <div class="productPromotionAmount">${result.productPromotionAmount}</div>
    <div class="orderPromotionAmount">${result.orderPromotionAmount}</div>
    <div class="orderPromotionNames">${result.orderPromotionNames}</div>
    <div class="productTotalAmount">${result.productTotalAmount}</div>
    <div class="productTotalQuantity">${result.productTotalQuantity}</div>
    <div class="totalAmount">${result.totalAmount?string("currency")?substring(1)}</div>
    <#list result.items as item>
        <div class="item-detail" data-id="${item.key}">
            <div class="item-cart">${item.key}</div>
            <input class="productPrice" type="hidden" value="${item.productPrice}"/>
            <input class="promotionPrice" type="hidden" value="${item.promotionPrice}"/>
            <input class="productpromotionPrice" type="hidden"
                   value="${(item.productPrice-item.promotionPrice)?string("currency")?substring(1)}"/>
            <#if item.flashPromotion?exists>
                <div class="flashPromotion">${item.flashPromotion.name}</div>
            </#if>
            <#if item.goodPromotion?exists>
                <div class="goodPromotion">${item.goodPromotion.name}</div>
            </#if>
            <#if item.orderPromotion?exists>
                <div class="orderPromotion">${item.orderPromotion.name}</div>
            </#if>
            <div class="promotionAmount">${item.promotionAmount}</div>
            <div class="totalProductAmount">
                &yen;${(item.totalProductAmount-item.promotionAmount)?string("currency")?substring(1)}</div>
        </div>
    </#list>
</div>