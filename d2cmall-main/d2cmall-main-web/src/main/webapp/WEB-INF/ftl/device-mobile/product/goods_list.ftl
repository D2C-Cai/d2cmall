<#import "templates/public_mobile.ftl" as m>
<#assign FROMAPP=renderUserAgent()/>
<#if title!=null&&title!=''>
    <#assign list_title=title />
</#if>
<#if (RequestParameters.k!='')>
    <#assign list_title='搜索“${RequestParameters.k}”结果'>
<#elseif (RequestParameters.c!='')||(RequestParameters.d!='')||(RequestParameters.t!='')>
    <#if (RequestParameters.t!='')>
        <#assign list_title=list_title+'${(topCate.name)!}'>
    </#if>
    <#if (RequestParameters.c!='')>
        <#if cate?exists>
            <#if cate.parent?exists>
                <#assign list_title=list_title+'${(cate.parent.name)!}'>
            </#if>
            <#assign list_title=list_title+'${(cate.name)!}'>
        </#if>
    </#if>
    <#if (RequestParameters.d!='')>
        <#assign list_title=list_title+'品牌：${(designer.name)!}'>
    </#if>
<#else>
    <#assign list_title=list_title+'所有商品'>
</#if>
<#if list_title==null || list_title==''>
    <#assign list_title='所有商品' />
</#if>
<@m.page_header title='${list_title}' keywords="${list_title},预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<@m.page_nav_bar />
<#if !FROMAPP>
</#if>
<div class="section lazyload">
    <#if !FROMAPP>
        <h2 class="block-title"
            style="background:#fff;line-height:2.8em;height:2.8em;font-size:.8em;text-align:left;padding:0 0 0 1.2em">
            <#if (RequestParameters.k!='')>搜索“${RequestParameters.k}”结果
            <#elseif (RequestParameters.tagId!='')>
                ${title}
            <#elseif (RequestParameters.c!='')||(RequestParameters.d!='')||(RequestParameters.t!='')>
                <#if (RequestParameters.t!='')>
                    <a href="?t=${(topCate.id)!}">${(topCate.name)!}</a>
                </#if>
                <#if (RequestParameters.c!='')>
                    <#if cate.parent?exists>&nbsp; <span class="split"> 〉</span> <a
                        href="?t=${(topCate.id)!}&c=${cate.parent.id}">${(cate.parent.name)!}</a></#if>
                    &nbsp; <span class="split"> 〉</span> <a href="?t=${(topCate.id)!}&c=${cate.id}">${(cate.name)!}</a>
                </#if>
            <#else>所有商品 </#if>
        </h2>
        <#if (RequestParameters.d!='')><h3 class="block-subtitle">品牌：<a
                    href="/showroom/designer/${RequestParameters.d}">${(designer.name)!}</a></h3></#if>
    </#if>
    <#if pager.list?exists && pager.list?size gt 0>
        <div class="list-sort" style="margin-top:-0.1em;">
            <#assign param=''>
            <#if RequestParameters.c!=''>
                <#assign param=param+'&c='+RequestParameters.c>
            </#if>
            <#if RequestParameters.d!=''>
                <#assign param=param+'&d='+RequestParameters.d>
            </#if>
            <#if RequestParameters.k!=''>
                <#assign param=param+'&k='+RequestParameters.k>
            </#if>
            <#if RequestParameters.tagId!=''>
                <#assign param=param+'&tagId='+RequestParameters.tagId>
            </#if>
            <#if RequestParameters.t!=''>
                <#assign param=param+'&t='+RequestParameters.t>
            </#if>
            <#if RequestParameters.o=='dd' || RequestParameters.o=='da'>
                <#if RequestParameters.o=='dd'>
                    <a href="?o=da${param}" class="on" title="点击按上架最晚时间排序">上架时间 <i class="sort-icon down"></i></a>
                <#else>
                    <a href="?o=dd${param}" class="on" title="点击按上架最早时间排序">上架时间 <i class="sort-icon up"></i></a>
                </#if>
            <#else>
                <a href="?o=dd${param}" title="点击按上架最晚时间排序">上架时间 <i class="sort-icon"></i></a>
            </#if>

            <#if RequestParameters.o=='pd' || RequestParameters.o=='pa'>
                <#if RequestParameters.o=='pd'>
                    <a href="?o=pa${param}" class="on" title="点击按价格从高到低排序">价格 <i class="sort-icon down"></i></a>
                <#else>
                    <a href="?o=pd${param}" class="on" title="点击按价格从低到高排序">价格 <i class="sort-icon up"></i></a>
                </#if>
            <#else>
                <a href="?o=pd${param}" title="点击按价格从高到低排序">价格 <i class="sort-icon"></i></a>
            </#if>
            <#if RequestParameters.o=='sd' || RequestParameters.o=='sa'>
                <#if RequestParameters.o=='sd'>
                    <a href="?o=sa${param}" class="on" title="点击按销量从少到多排序">销量 <i class="sort-icon down"></i></a>
                <#else>
                    <a href="?o=sd${param}" class="on" title="点击按销量从多到少排序">销量 <i class="sort-icon up"></i></a>
                </#if>
            <#else>
                <a href="?o=sd${param}" title="点击按销量从多到少排序">销量 <i class="sort-icon"></i></a>
            </#if>

        </div>
        <#if relationDesigners?exists && relationDesigners?size gt 0>
            <#assign designer_param=''>
            <#if RequestParameters.c!=''>
                <#assign designer_param=designer_param+'&c='+RequestParameters.c>
            </#if>
            <#if RequestParameters.k!=''>
                <#assign designer_param=designer_param+'&k='+RequestParameters.k>
            </#if>
            <#if RequestParameters.tagId!=''>
                <#assign designer_param=designer_param+'&tagId='+RequestParameters.tagId>
            </#if>
            <#if RequestParameters.t!=''>
                <#assign designer_param=designer_param+'&t='+RequestParameters.t>
            </#if>
            <div class="list-sort-designer">
                <a href="?${designer_param}">全部品牌</a>
                <#list relationDesigners as designer>
                    <a href="?d=${designer.value.id}${designer_param}"<#if RequestParameters.d==designer.value.id> class="on"</#if>>${designer.value.name}</a>
                </#list>
                <div class="pickup" onclick="hideProductBrand()">收起</div>
            </div>
        </#if>

        <div class="list clearfix" id="load-more-product">
            <#list pager.list as list>
                <a href="/product/${list.id}" class="item item-flex item-gap">
		<span class="img">
		<#if list.store?exists && list.store lt 1><i class="n-product"></i><span class="outp">已售罄</span></#if><img
                    src="${static_base}/m/img/blank100x157.png"
                    data-image="${picture_base}${list.productImageCover}!300" alt=""/></span>
                    <span class="title"><#if list.topical==1><strong
                                style="color:#FD555D">[主推]</strong></#if>${list.name}</span>
                    <span class="price">&yen; ${(list.minPrice?string("currency")?substring(1))!}</span>
                </a>
            </#list>
        </div>
        <#if pager.pageCount gt 1>
            <#if RequestParameters.o!=''>
                <#assign param=param+'&k='+RequestParameters.o>
            </#if>
            <div class="load-more scroll-load-more" data-url="/goods/pager?${param}" data-target="load-more-product"
                 template-id="list-template" data-page="${pager.pageNumber}" data-total="${pager.pageCount}">点击加载更多
            </div>
        </#if>
    <#else>
        <hr/>
        <div class="text-center" style="line-height:10em">
            未找到更多的商品
        </div>
    </#if>
</div>
<script id="list-template" type="text/html">
    <div class="lazyload">
        {{each pager.list as value i}}
        <a href="/product/{{value.id}}" class="item item-flex item-gap">
        <span class="img lazyload">
        {{if value.store < 1}}<i class="n-product"></i><span class="outp">已售罄</span>{{/if}}<img
                    src="${static_base}/m/img/blank100x157.png"
                    data-image="${picture_base}{{value.productImageCover}}!300" alt="{{value.name}}"/></span>
            <span class="title">{{value.name}}</span>
            <span class="price"><strong class="addprice"
                                        data-price="{{value.minPrice}}">&yen;{{value.minPrice}}</strong>&nbsp;&nbsp;{{if value.currentPrice< value.originalPrice}}<s>&yen; {{value.originalPrice}}</s>{{/if}}</span>
        </a>
        {{/each}}
    </div>
    <script>
        $('.addprice').each(function () {
            var price = $(this).attr('data-price');
            $(this).text($.utilFormatCurrency(price))
        })
</script>
</script>
<
script
type = "text/javascript" >
window.__zp_tag_params = {
    pagetype: 'listPage',
    productId_list: '<#if pager.list?exists && pager.list?size gt 0><#list pager.list as list>${list.id},</#list></#if>'
};
</script>
<@m.page_footer />

