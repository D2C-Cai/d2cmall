<div class="list clearfix" id="load-more-product">
    <#list list as list>
        <a href="/product/${list.id}" class="item item-flex item-gap">
		<span class="img">
		<#if list.productSellType=='PRESELL'><!--<img src="//static.d2c.cn/common/nm/css/img/icon_cornermark.png" style="width:25%;position:absolute;top:0;left:0;z-index:2">--></#if>
            <#if list.productSellType=='CUSTOM'><!--<img src="//static.d2c.cn/common/nm/css/img/icon_customize.png" style="width:25%;position:absolute;top:0;left:0;z-index:2">--></#if>
            <#if list.productSellType=='SPOT'><img src="//static.d2c.cn/common/nm/css/img/icon_exist_big.png"
                                                   style="width:25%;position:absolute;top:0;left:0;z-index:2"></#if>
            <#if list.store?exists && list.store lt 1><i class="n-product"></i><span class="outp">已售罄</span></#if><img
                    src="${static_base}/m/img/blank100x157.png"
                    data-image="${picture_base}${list.productImageCover}!300" alt=""/></span>
            <span class="title"><#if list.topical==1><strong
                        style="color:#FD555D">[主推]</strong></#if>${list.name}</span>
            <span class="price">&yen; ${(list.currentPrice?string("currency")?substring(1))!}&nbsp;&nbsp;	<#if (list.currentPrice lt list.originalPrice)>
                    <s>&yen; ${(list.originalPrice)!}</s></#if></span>
        </a>
    </#list>
</div>
   

 