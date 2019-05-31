<div class="list clearfix" id="load-more-product">
    <#list products.list as list>
        <a href="/product/${list.id}" class="item item-flex item-gap">
		<span class="img">
		<#if list.store?exists && list.store lt 1><i class="n-product"></i><span class="outp">已售罄</span></#if><img
                    src="${picture_base}${list.productImageCover}!300" alt=""/></span>
            <span class="title"><#if list.topical==1><strong
                        style="color:#FD555D">[主推]</strong></#if>${list.name}</span>
            <span class="price">&yen; ${(list.currentPrice?string("currency")?substring(1))!}&nbsp;&nbsp;	<#if (list.currentPrice lt list.originalPrice)>
                    <s>&yen; ${(list.originalPrice)!}</s></#if></span>
        </a>
    </#list>
</div>
   

 