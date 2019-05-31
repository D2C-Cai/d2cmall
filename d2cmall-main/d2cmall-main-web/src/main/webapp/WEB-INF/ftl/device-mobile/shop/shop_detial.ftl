<#import "templates/public_mobile.ftl" as m>
<@m.page_header cart=true hastopfix='true' title='${designer.name}' keywords="设计师-${designer.name},全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<#if !m.FROMAPP>
    <@m.app_download_bar fixed="false" />
</#if>
<share data-title="${designer.name}"
       data-url="${base}/showroom/designer/${designer.id}?parent_id=${m.LOGINMEMBER.partnerId}"></share>
<div class="designer-info">
    <div class="logo">
        <a href="javascript:void(0);" data-url="/member/interest/attention/insert/${designer.id}"
           check-url="/member/interest/attention/check/${designer.id}" id="follow-${designer.id}"
           call-back="interestSuccess(${designer.id},'follow')" class="ajax-request fav"><span
                    class="icon icon-heart-o"></span> <strong>${designer.tfans}</strong></a>
        <img src="${picture_base}${(designer.headPic)!}" id="designer-logo" style="max-width:80%;max-height:5em;"
             alt="${designer.name}"/>
    </div>
    <div class="text">
        ${designer.intro}
    </div>
    <div class="switch" onclick="switchDesignerInfo()">
        ≡
    </div>
</div>
<div class="section lazyload showroom">
    <#if crowdingProduct?exists &&crowdingProduct?size gt 0>
        <!--<h2 class="block-title">预售区</h2>-->
        <div class="list clearfix">
            <#list crowdingProduct as product>
                <a href="/product/${product.id}" class="item item-flex item-gap">
            <span class="img">
              <#if list.store?exists && list.store lt 1><i class="n-product"></i><span class="outp">已售罄</span></#if><img
                        src="${static_base}/m/img/blank100x157.png"
                        data-image="${picture_base}${product.productImageCover}!280" alt="${product.name}"/></span>
                    <span class="title">${product.name}</span>
                    <span class="price">&yen; ${(product.promotionPrice)!}  <#if product.promotionPrice < product.originalPrice> &nbsp;
                            <s>&yen; ${(product.originalPrice)!}</s></#if></span>
                </a>
            </#list>
        </div>
    </#if>
    <#if series?size gt 0>
        <!--<h2 class="block-title">现货区</h2>-->
        <#list series as serie>
            <h3 class="block-subtitle">${serie.name}</h3>
            <div class="list clearfix series-product" data-url="/showroom/series/${serie.id}">
                <div style="height:30em;line-height:30em;text-align:center;font-size:0.8em;">正在加载数据...</div>
            </div>
        </#list>
    <#else>
        <div class="list clearfix series-product" data-url="/showroom/product/${designerId}" data-type="cola">
            <div style="height:30em;line-height:30em;text-align:center;font-size:0.8em;">正在加载数据...</div>
        </div>
    </#if>
</div>
<hr/>
<script id="item-template" type="text/html">
    <div class="lazyload">
        {{each productList as value i}}
        <a href="/product/{{value.id}}<#if m.LOGINMEMBER.partnerId>?parent_id=${RequestParameters.parent_id}</#if>"
           class="item item-flex item-gap">
            <span class="img">{{if value.store<=0}}<i class="n-product"></i><span class="outp">已售罄</span>{{/if}}<img
                        src="${picture_base}{{value.productImageCover}}!300" alt="{{value.name}}"/></span>
            <span class="title">{{value.name}}</span>
            <span class="price"><strong>&yen;</strong><strong class="addprice" data-price="{{value.currentPrice}}">{{value.currentPrice}}</strong>&nbsp;&nbsp;{{if value.currentPrice < value.originalPrice}}<s>&yen; {{value.originalPrice}}</s>{{/if}}</span>
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
    id = "cola-template"
    type = "text/html" >
        < div
    class
    = "lazyload" >
        {
    {
        each
        list as value
        i
    }
    }
    <
    a
    href = "/product/{{value.id}}<#if m.LOGINMEMBER.partnerId>?parent_id=${RequestParameters.parent_id}</#if>"
    class
    = "item item-flex item-gap" >
        < span
    class
    = "img" > {
    {
        if value.store <= 0}
    }<
    i
    class
    = "n-product" > < /i><span class="outp">已售罄</s
    pan > {
    {
        /if}}<img src="${picture_base}{{value.productImageCover}}!300" alt="{{value.name}}" / > < /span>
        < span
    class
        = "title" > {
        {
            value.name
        }
    }<
        /span>
        < span
    class
        = "price" > < strong > & yen;
    <
        /strong><strong  class="addprice" data-price="{{value.currentPrice}}">{{value.currentPrice}}</s
        trong > & nbsp;
    &
        nbsp;
        {
            {
                if value.currentPrice < value.originalPrice}
        }
    <
        s > & yen;
        {
            {
                value.originalPrice
            }
        }
    <
        /s>{{/i
        f
    }
    }<
    /span>
    < /a>
    {
        {
            /each}}
            < /div>
            < script >
            $('.addprice').each(function () {
                var price = $(this).attr('data-price');
                $(this).text($.utilFormatCurrency(price))
            })
    </script>
    </script>

    <
    script >
    function loadProduct() {
        var html = "";
        $('.series-product').each(function () {
            var type = $(this).attr('data-type');
            if ($(this).offset().top <= pageTop()) {
                var this_obj = $(this);
                var url = this_obj.attr("data-url")
                if (url) {
                    this_obj.removeAttr("data-url");
                    $.getJSON(url, function (data) {
                        if (type == 'cola') {
                            html = template('cola-template', data.products);
                        } else {
                            html = template('item-template', data);
                        }
                        this_obj.html(html);
                    });
                }
            }
        });
    }

    var pageTop = function () {
        var d = document,
            y = (navigator.userAgent.toLowerCase().match(/iPad/i) == "ipad") ? window.pageYOffset : Math.max(d.documentElement.scrollTop, d.body.scrollTop);
        return d.documentElement.clientHeight + y;
    }

    loadProduct();
    $(window).bind("scroll", function () {
        loadProduct();
    });


</script>
<@m.page_footer menu=true />