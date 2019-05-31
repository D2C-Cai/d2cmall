<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${promotion.name} - 活动' keywords='D2C，预售,预售优惠,服装设计师,原创品牌,买手店' description='D2C全球设计师品牌集成平台' js='utils/unslider.min'/>
<@m.top_nav channel="" />
<#if  promotion.customCode!=null && promotion.customCode!=''>
    <div class="lazyload">
        ${promotion.customCode?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
    </div>
<#else>
    <#if promotion.banner>
        <div class="text-center">
            ${promotion.banner}
        </div>
    </#if>
    <div class="layout layout-response layout-product clearfix">
        <#if products.totalCount &gt; 0 >
            <div class="list product-list lazyload clearfix">
                <#list products.list as product>
                    <div class="list-item">
                        <#if product.productSellType=='PRESELL'>
                            <!--	<span class="tag crowd"></span> -->
                        <#elseif product.productSellType=='CUSTOM'>
                            <!--  <span class="tag customize"></span> -->
                        <#elseif product.productSellType=='SPOT'>
                            <span class="tag spot"></span>
                        </#if>
                        <a href="/product/${product.productId}" class="img"
                           target="_blank"><#if product.store?exists && product.store lt 1><i class="sale"></i><i
                                class="satip">已售罄</i></#if><img src="${static_base}/blank/300-467.gif"
                                                                data-image="${picture_base}${(product.productImageCover)!}!300"
                                                                alt="${(product.name)!}"/> </a>
                        <p class="title"><#if product.topical==1><strong style="color:#FD555D">[主推]</strong></#if><a
                                    href="/product/${product.productId}" target="_blank"
                                    title="${(product.name)!}">${(product.name)!}</p>
                        <p class="price">
                            <span>&yen; ${(product.currentPrice)!}</span>
                            <#if (product.currentPrice?exists && product.currentPrice lt product.originalPrice)> &nbsp;
                                <s>&yen; ${(product.originalPrice)!}</s></#if>
                        </p>
                        </a>
                    </div>
                </#list>
            </div>
            <div class="pages" id="product-list-bottom-page" style="padding-bottom:30px;">
                <@m.p page=products.pageNumber totalpage=products.pageCount num=products.totalCount />
            </div>
        </#if>
    </div>
</#if>

<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'listPage',
        productId_list: '<#if products?exists><#list products.list as product>${product.productId},</#list></#if>'
    };
</script>
<@m.page_footer />