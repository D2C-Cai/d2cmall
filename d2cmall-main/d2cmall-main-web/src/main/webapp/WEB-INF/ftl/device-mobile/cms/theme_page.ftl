<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="${theme.title}" js='' css='' title='${theme.title}' keywords='全球好设计' description=''/>
<div class="clearfix lazyload" style="position:relative;height:100%;">
    <#if !m.FROMAPP>
        <div class="return-home">
            <a href="/">回首页</a>
        </div>
    </#if>
    <#if theme.wapContent>
        ${theme.wapContent?replace('img src=','img src="${static_base}/c/images/space.gif" data-image=')}
    <#elseif theme.pcContent>
        ${theme.pcContent?replace('img src=','img src="${static_base}/c/images/space.gif" data-image=')}
    </#if>
    <#if theme.metaDescription?exists>
        <div style="background:#FFF;">
            <#assign themeDescription=theme.metaDescription?eval>
            <#if themeDescription?size gt 0>
                <#list themeDescription as val>
                    <#if val.type =='image'>
                        <div class="article-img" style="padding:3%;">
                            <#if val.url>
                                <a href="${val.url}"><img src="${picture_base}${val.image}" width="100%" alt=""/></a>
                            <#else>
                                <img src="${picture_base}${val.image}" width="100%" alt=""/>
                            </#if>
                        </div>
                    </#if>
                    <#if val.type=='text'>
                        <div class="article-text" style="padding:3%;line-height:1.5;">
                            <#if val.url>
                                <p><a href="${val.url}">${val.text}</a></p>
                            <#else>
                                <p>${val.text}</p>
                            </#if>
                        </div>
                    </#if>
                    <#if val.type=='video'>
                        <div class="article-video" style="padding:3%;position:relative;">
                            <a href="//img.d2c.cn${val.video}">
                                <img src="//img.d2c.cn/${val.screenshot}" width="100%" alt=""/>
                                <span style="background:url(//static.d2c.cn/img/topic/180324/springer/images/icon_play@3x.png) no-repeat;background-size:cover;position:absolute;left:50%;top:50%;transform:translate(-50%,-50%);display:inline-block;width:4em;height:4em;"></span>
                            </a>
                        </div>
                    </#if>
                    <#if val.type=='product'>
                        <div class="article-product" data-id="${val.productId}"
                             style="display:flex;justify-content:space-bewteen;padding:3%;">
                            <a href="/product/${val.productId}" style="display:block;width:50%;"><img
                                        src="${picture_base}${val.productPic}" width="100%" alt=""></a>
                            <div class="product-cont" style="width:100%;margin-left:20px;">
                                <div class="product-text">
                                    <p style="color:#212121;font-size:1.2em;line-height:2em;" class="product-brand"></p>
                                    <a href="/product/${val.productId}"
                                       style="display:block;height:6em;overflow:hidden;font-size:.875em;padding-top:.5em;color:#212121;line-height:1.5;"
                                       class="product-name"></a>
                                </div>
                                <div class="product-price"
                                     style="display:flex;justify-content:space-between;line-height:2em;">
                                    <div>精选价：<strong class="price-num"></strong></div>
                                    <div style="width:5em;height:2em;line-height:2em;text-align:center;background:#232427;">
                                        <a style="color:#FFF;" href="/product/${val.productId}">去购买</a></div>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
    </#if>
</div>
<script>
    if (!document.referrer) $('.return-home').show();
    $('.article-product').each(function () {
        var obj = $(this);
        var id = obj.attr('data-id');
        $.getJSON('/product/' + id, function (data) {
            var designer = data.product.designerName;
            var name = data.product.name;
            var price = data.product.originalPrice;
            obj.children().find('.product-brand').text(designer);
            obj.children().find('.product-name').text(name);
            obj.children().find('.price-num').html('<span>&yen;</span>' + price);
        });
    });
</script>
<@m.page_footer menu=false />