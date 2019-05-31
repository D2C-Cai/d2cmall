<div class="item-list clearfix" style="margin:0 auto">
    <ul>
        <#if list?exists>
            <#list list as product>
                <li>
                    <div class="promotionshow">
                        <p class="img hover-fade">
                            <a href="${base}/product/${product.id}" target="_blank">
                                <img src="${picture_base}${(product.productImageCover)!}!275" alt="${(product.name)!}"/>
                                <#if hover==1>
                                    <img class="hover-img" src="//static.d2c.cn/img/topic/product/${product.id}.png"
                                         alt=""/>
                                </#if>
                            </a>
                        </p>
                        <p class="txt"><a href="${base}/product/${product.id}" target="_blank">${product.name}</a></p>
                        <p class="price">
                            <span>&yen; ${(product.currentPrice?string("currency")?substring(1))!}</span>
                            <#if (product.currentPrice?exists && product.currentPrice lt product.originalPrice)> &nbsp;
                                <s>&yen; ${(product.originalPrice?string("currency")?substring(1))!}</s></#if>
                        </p>
                        <p class="op"><a href="${base}/product/${product.id}" target="_blank">立即抢订</a></p>
                    </div>
                </li>
            </#list>
        </#if>
    </ul>
</div>
 