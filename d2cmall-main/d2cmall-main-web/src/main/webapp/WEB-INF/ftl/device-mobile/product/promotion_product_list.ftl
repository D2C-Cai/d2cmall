<#import "templates/public_mobile.ftl" as m>
<#if promotion.mobileCode!=null && promotion.mobileCode!=''>
<@m.page_header title='${promotion.name}' js='utils/iSlider' css='iSlider' keywords='预售,预售优惠,服装设计师,原创品牌' description='${promotion.description}'/>
<script>

</script>
<#if promotion.smallPic>
<img src="${picture_base}${promotion.smallPic}" style="display:none">
</#if>
<#if promotion.endTime?datetime lt .now?datetime >
<script>
jTip('对不起，活动已于${promotion.endTime?string('yyyy年MM日dd日 HH:mm')}结束，请及时访问哦！');
</script>
</#if>
<#if !m.FROMAPP>
<@m.app_download_bar />
</#if>
<div class="clearfix lazyload" style="position:relative;height:100%">
<#if !m.FROMAPP>
<div class="return-home">
	<a href="/">回首页</a>
</div>
</#if>
${promotion.mobileCode}
</div>
<#else>
<@m.page_header title='${promotion.name}' module='promotion' keywords='预售,预售优惠,服装设计师,原创品牌' description='${promotion.description}'/>
<#if promotion.smallPic>
<img src="${picture_base}${promotion.smallPic}" style="display:none">
</#if>
<#if promotion.endTime?datetime lt .now?datetime >
<div class="mask-filter">
</div>
<script>
jTip('对不起，活动已于${promotion.endTime?string('yyyy年MM日dd日 HH:mm')}结束，请及时访问哦！');
</script>
</#if>
<#if !m.FROMAPP>
<div class="return-home">
	<a href="/">回首页</a>
</div>
<@m.app_download_bar />
</#if>
<#if promotion.wapBanner>
<div class="text-center wapbanner">
${promotion.wapBanner}    	
</div>
</#if>
<div class="section lazyload">
	<#if !m.FROMAPP>
	<h2 class="block-title">${promotion.name}</h2>
	</#if>
	<#if (promotion.endTime?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss"))>				
	<h3 class="block-subtitle">
		<span class="count-down" data-startTime="${promotion.startTime?string("yyyy/MM/dd HH:mm:ss")}" data-endTime="${promotion.endTime?string("yyyy/MM/dd HH:mm:ss")}"></span>
	</h3>
	</#if>
    <#if products.totalCount &gt; 0 >
    <div class="list clearfix" id="load-more-product">
    <#list products.list as list>
	<a href="/product/${list.id}" class="item item-flex item-gap">
		<span class="img">
		<#if list.store?exists && list.store lt 1><i class="n-product"></i><span class="outp">已售罄</span></#if><img src="${static_base}/m/img/blank100x157.png" data-image="${picture_base}${list.productImageCover}!300" alt="" /></span>
		<span class="title"><#if list.topical==1><strong style="color:#FD555D">[主推]</strong></#if>${list.name}</span>
		<span class="price">&yen; ${(list.currentPrice?string("currency")?substring(1))!}&nbsp;&nbsp;	<#if (list.currentPrice lt list.originalPrice)><s>&yen; ${(list.originalPrice)!}</s></#if></span>
	</a>
    </#list>
    </div>
    <div class="load-more scroll-load-more" data-url="/promotion/${promotion.id}?" data-target="load-more-product" template-id="list-template" data-page="${products.pageNumber}" data-total="${products.pageCount}">点击加载更多</div>  
    </#if>
</div>


<script id="list-template" type="text/html">
<div class="lazyload">
    {{each list as value i}}
    <a href="/product/{{value.id}}" class="item item-flex item-gap">
        <span class="img lazyload">
            {{if value.store < 1}}<i class="n-product"></i><span class="outp">已售罄</span>{{/if}}<img src="${static_base}/m/img/blank100x157.png" data-image="${picture_base}{{value.productImageCover}}!300" alt="{{value.name}}" /></span>
        <span class="title">{{value.name}}</span>
        <span class="price"><strong  class="addprice" data-price="{{value.currentPrice}}">&yen;{{value.currentPrice}}</strong>&nbsp;&nbsp;{{if value.currentPrice< value.originalPrice}}<s>&yen; {{value.originalPrice}}</s>{{/if}}</span>
    </a>
    {{/each}}
    </div>
    <script>
    	$('.addprice').each(function(){
    		var price=$(this).attr('data-price');
    		$(this).text($.utilFormatCurrency(price))
    	})
    </script>
</script>
</#if>

<#if !m.FROMAPP>
<script type="text/javascript">
if (!document.referrer) $('.return-home').show();
window.__zp_tag_params = {
pagetype: 'listPage', 
productId_list:'<#if products?exists><#list products.list as product>${product.id},</#list></#if>'};
</script>
<#else>
<script>
	var message={
	 handlefunc:'w_page',
     func: "cessback",
	 type: "page",
	 pageBackGround:"${promotion.backgroundUrl}",
	}
	if(app_client) $.D2CMerchantBridge(message)
</script>	
</#if>
<@m.page_footer menu=true module='promotion' />