<#import "templates/public_pc.ftl" as m>
<#if title!=null&&title!=''>
    <#assign list_title=title />
</#if>
<#if title!=null&&title!=''>
    <#assign list_title=title />
</#if>
<#if (RequestParameters.k!='')>
    <#assign list_title='搜索“${RequestParameters.k}”结果'>
<#elseif (RequestParameters.c!='')||(RequestParameters.d!='')||(RequestParameters.t!='')>
    <#if (RequestParameters.t!='')>
        <#assign list_title=list_title+'${(topCate.name)!}'>
    </#if>
    <#if (RequestParameters.c!='') &&  cate?exists >
        <#if cate.parent?exists>
            <#assign list_title=list_title+'${(cate.parent.name)!}'>
        </#if>
        <#assign list_title=list_title+'${(cate.name)!}'>
    </#if>
    <#if (RequestParameters.d!='')>
        <#assign list_title=list_title+'品牌：${(designer.name)!}'>
    </#if>
<#else>
    <#assign list_title=list_title+'所有商品'>
</#if>

<#if nav!>
    <#if nav.seoTitle>
        <#assign list_title=list_title+nav.seoTitle />
    <#else>
        <#assign list_title=list_title+nav.name />
    </#if>
</#if>
<#if list_title==null || list_title==''>
    <#assign list_title='所有商品' />
</#if>
<#if nav! && nav.seoKeywords><#assign seoKeywords=nav.seoKeywords /><#else><#assign seoKeywords='${list_title},预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店' /></#if>
<#if nav! && nav.seoDescription><#assign seoDescription=nav.seoDescription /><#else><#assign seoDescription='消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场' /></#if>
<@m.page_header title='${list_title}' keywords="${seoKeywords}" description="${seoDescription}" js='utils/jquery.tinyscrollbar'/>
<@m.top_nav />
<div class="layout layout-response layout-product lazyload">
    <div class="breadcrumb">
        <#if pager.totalCount gt 0><span class="float-right">共找到 <strong>${(pager.totalCount)!}</strong> 件商品
            </span></#if>
        <i class="fa fa-caret-right"></i> <a href="/">D2C首页</a>
        <#if (RequestParameters.k!='')> &nbsp;>&nbsp; <span class="search-bar"><input type="text" name="k"
                                                                                      value="${RequestParameters.k}"
                                                                                      class="input input-s"/><i
                    class="fa fa-search"></i>
        <#elseif (RequestParameters.tagId!='')>
            &nbsp;>&nbsp; <span style="color:#333">专题</span> &nbsp;>&nbsp; <span style="color:#333">${title}</span>
            <#elseif (RequestParameters.c!='')||(RequestParameters.d!='')||(RequestParameters.t!='')>
                <#if (RequestParameters.t!='')>
                    &nbsp;>&nbsp; <a href="?t=${(topCate.id)!}">${(topCate.name)!}</a>
                </#if>
        <#if (RequestParameters.c!='')>
                <#if cate?exists>
                    <#if cate.parent?exists> &nbsp;>&nbsp; <a
                        href="?t=${(topCate.id)!}&c=${cate.parent.id}">${(cate.parent.name)!}</a></#if>
	        &nbsp;>&nbsp; <a href="?t=${(topCate.id)!}&c=${cate.id}">${(cate.name)!}</a>
                </#if>
            </#if>
        <#if (RequestParameters.d!='')> &nbsp;>&nbsp;<a href="?d=${(designer.id)!}">品牌：${(designer.name)!}</a></#if>
            <#else>
                &nbsp;>&nbsp; <span style="color:#333">所有商品</span>
            </#if>
    </div>
    <#if cate?exists>
        <#if cate.parent?exists>
            <#assign cateParentId=cate.parent.id />
        <#else>
            <#assign cateParentId=RequestParameters.c />
        </#if>
    </#if>
    <div class="layout-left layout-product-left">
        <div class="select-condition">
            <#if (topCategorys?exists && topCategorys?size gt 0) || (categorys?exists && categorys?size gt 0)>
                <dl class="category">
                    <#if topCategorys?exists && topCategorys?size gt 0>
                        <#list topCategorys as top>
                            <dt>
                                <i class="fa <#if (RequestParameters.t==top.value.id) && categorys?exists && categorys?size gt 0>fa-angle-down<#else>fa-angle-right</#if>"></i>
                                <a href="?t=${(top.value.id)!}">${(top.value.name)!}</a></dt>
                            <#if (RequestParameters.t==top.value.id) && categorys?exists && categorys?size gt 0>
                                <#list categorys as item>
                                    <dd class="category-level-${item.value.depth}<#if RequestParameters.c==item.value.id> on</#if><#if item.value.depth==2 && item.value.parentId!=cateParentId> display-none</#if>"><#if item.value.depth==1>
                                        <span
                                        class="fa<#if item.value.id==cateParentId> fa-minus-square-o<#else> fa-plus-square-o</#if> toggle-category-child"></span> </#if>
                                        <a href="?t=${(top.value.id)!}&c=${(item.value.id)!}">${(item.value.name)!}</a>
                                    </dd>
                                </#list>
                            </#if>
                        </#list>
                    </#if>
                </dl>
            </#if>
            <#if relationDesigners?exists && relationDesigners?size gt 0>
                <#assign url='' />
                <#if (RequestParameters.t!='')>
                    <#assign url=url+'&t='+RequestParameters.t />
                </#if>
                <#if (RequestParameters.c!='')>
                    <#assign url=url+'&c='+RequestParameters.c />
                </#if>
                <#if (RequestParameters.k!='')>
                    <#assign url=url+'&k='+RequestParameters.k />
                </#if>
                <dl class="brand">
                    <dt><i class="fa fa-caret-down"></i> 设计师/品牌</dt>
                    <dd>
                        <div id="product-brand-scrollbar" class="scrollbar brand-section">
                            <div class="scrollbar">
                                <div class="track">
                                    <div class="thumb">
                                        <div class="end"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="viewport">
                                <div class="overview">
                                    <#list relationDesigners as relationDs>
                                        <a href="?d=${(relationDs.value.id)!}${url}"<#if RequestParameters.d==relationDs.value.id> class="on"</#if>>${(relationDs.value.name)!}</a></#list>
                                </div>
                            </div>
                        </div>
                    </dd>
                    <#if relationDesigners?size gt 25>
                        <dd>
                            <div><a style="padding:5px 10px 5px 20px;display:block;font-size:11px;margin-top:10px"
                                    href="javascript:;" id="mode">滚动查看更多</a></div>
                        </dd>
                    </#if>
                </dl>
            </#if>
        </div>
    </div>
    <div class="layout-right layout-product-right">
        <#if pager.list?exists && pager.list?size gt 0>
            <div class="list-filter scroll-suspend" data-scroll-end="product-list-bottom-page" data-offset="37">
                <div class="list-sort">
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
                            <a href="?o=da${param}" class="on" title="点击按上架最早时间排序">上架时间 <i class="fa fa-caret-down"></i></a>
                        <#else>
                            <a href="?o=dd${param}" class="on" title="点击按上架最晚时间排序">上架时间 <i
                                        class="fa fa-caret-up"></i></a>
                        </#if>
                    <#else>
                        <a href="?o=da${param}" title="点击按上架最早时间排序">上架时间 <i class="fa fa-caret-down"></i></a>
                    </#if>
                    <#if RequestParameters.o=='sd' || RequestParameters.o=='sa'>
                        <#if RequestParameters.o=='sd'>
                            <a href="?o=sa${param}" class="on" title="点击按销量从少到多排序">销量 <i
                                        class="fa fa-caret-down"></i></a>
                        <#else>
                            <a href="?o=sd${param}" class="on" title="点击按销量从多到少排序">销量 <i class="fa fa-caret-up"></i></a>
                        </#if>
                    <#else>
                        <a href="?o=sd${param}" title="点击按销量从多到少排序">销量 <i class="fa fa-caret-down"></i></a>
                    </#if>
                    <#if RequestParameters.o=='pd' || RequestParameters.o=='pa'>
                        <#if RequestParameters.o=='pd'>
                            <a href="?o=pa${param}" class="on" title="点击按价格从低到高排序">价格 <i
                                        class="fa fa-caret-down"></i></a>
                        <#else>
                            <a href="?o=pd${param}" class="on" title="点击按价格从高到低排序">价格 <i class="fa fa-caret-up"></i></a>
                        </#if>
                    <#else>
                        <a href="?o=pd${param}" title="点击按价格从高到低排序">价格 <i class="fa fa-caret-down"></i></a>
                    </#if>
                </div>
                <div class="pages float-right">
                    <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
                </div>
            </div>
            <div class="list product-list clearfix">
                <#list pager.list as product>
                    <div class="list-item">

                        <a href="/product/${product.id}" class="img" target="_blank">
                            <#if product.productSellType=='PRESELL'>
                                <!--	<span class="tag crowd"></span> -->
                            <#elseif product.productSellType=='CUSTOM'>
                                <!--  <span class="tag customize"></span> -->
                            <#elseif product.productSellType=='SPOT'>
                                <span class="tag spot"></span>
                            </#if>
                            <#if product.store?exists && product.store lt 1><i class="sale"></i><i
                                class="satip">已售罄</i></#if><img src="${static_base}/blank/300-467.gif"
                                                                data-image="${picture_base}${(product.productImageCover)!}!300"
                                                                alt="${(product.name)!}"/> </a>
                        <p class="title"><#if product.topical==1><strong style="color:#FD555D">[主推]</strong></#if><a
                                    href="/product/${product.id}" target="_blank" title="${(product.name)!}">
                            ${(product.name)!}</p>
                        <p class="price">
                            <span><span class="symbol">&yen;</span><span
                                        class="foreigner-price">${(product.currentPrice)!}</span></span>
                            <#if (product.currentPrice?exists && product.currentPrice lt product.originalPrice)> &nbsp;
                                <s><span class="symbol">&yen;</span><span
                                        class="foreigner-price">${(product.originalPrice)!}</span></s></#if>
                        </p>
                        </a>
                    </div>
                </#list>
            </div>
            <div class="pages" id="product-list-bottom-page" style="padding-bottom:30px;">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
        <#else>
            <div style="line-height:560px;width:100%;"
                 class="text-center"><#if searcher.keywords!=''>呜~，没有找到与“${searcher.keywords}”相关的商品哦，小D会加油完善的哟<#else>请稍后，新品陆续在上架中</#if></div>
        </#if>
    </div>
</div>
<script>
    $('.toggle-category-child').click(function () {
        var obj = $(this).parent();
        $(this).toggleClass('fa-plus-square-o').toggleClass('fa-minus-square-o');
        obj.nextUntil('dt,dd.category-level-1').toggle();
    });
    $('#product-brand-scrollbar').tinyscrollbar();
    $('.more').click(function () {
        var obj = $(this).siblings('.select-section');
        if (obj.hasClass('on')) {
            $(this).html('更多 <i class="fa fa-angle-down"></i>');
            $('.select-section').removeClass('on');
        } else {
            $(this).html('收起 <i class="fa fa-angle-up"></i>');
            $('.select-section').addClass('on');
        }
        return false;
    });
    $('.search-bar i').click(function () {
        var val = $(this).siblings('input').val();
        if (val) {
            location.href = '/product/list?k=' + val;
        }
    });
    //改变币种


    var favIncrease = function (id) {
        var obj = $('#product-' + id);
        //var i=parseInt(obj.find('strong').text());
        obj.find('i').removeClass('fa-heart-o').addClass('fa-heart');
        //obj.find('strong').text(i+1);
        obj.removeClass('ajax-request');
    };
</script>
<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'listPage',
        productId_list: '<#if pager.list?exists && pager.list?size gt 0><#list pager.list as product>${product.id},</#list></#if>'
    };
</script>
<@m.page_footer />