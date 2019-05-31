<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${designer.name}设计师品牌 , ${designer.designers}设计师品牌 , 设计师品牌,${designer.name}官网,d2c官网,${designer.designers}官网' js="utils/jquery.tinyscrollbar" keywords="${designer.name},${designer.designers},${designer.name}官网,${designer.designers}官网,d2c官网,设计师${designer.designers},全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${designer.intro?replace('<.*?>(u0022)(u0027)','a','r')},${designer.name},${designer.designers},${designer.name}官网,${designer.designers}官网,d2c官网,设计师${designer.designers},全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" />
<@m.top_nav channel="showroom" />
<!-- style="background-image:url('<#if designer.background>${picture_base}${(designer.background.path)!}<#else>/static/c/images/bg_runway.jpg')</#if>" -->
<!--
<div class="breadcrumb">
当前所在位置：<a href="${base}">D2C首页</a> &nbsp;&nbsp;>&nbsp;&nbsp;
    <a href="${base}/showroom">设计师品牌</a> &nbsp;&nbsp;>&nbsp;&nbsp;
    <a href="${base}/showroom/designer/${(designer.id)!}">${(designer.name)!}</a>
</div>-->
<#if designer.designerStore>
    <div class="designer-detail-fullscreen">
        <div class="fullscreen">
            <div class="fullscreen-content">
                <#if designer.video>
                    <a href="javascript:;" class="fullscreen-video" data-video="${picture_base}${designer.video}"></a>
                </#if>
                ${designer.designerStore}
            </div>
        </div>
    </div>
</#if>
<div class="layout layout-response layout-showroom lazyload">
    <#if designer.headPic?exists>
        <div class="designer-detail clearfix">
        <div class="info">
            <h2><strong>设计师/品牌简介</strong></h2>
            <h1>
                <strong class="name">${designer.designers}</strong> &nbsp;&nbsp;
                <button data-url="/member/interest/attention/insert/${designer.id}.json" method-type="post"
                        success-tip="关注成功" call-back="followIncrease(${designer.id})"
                        id="designer-follow-${designer.id}" class="button button-s  ajax-request"><i
                            class="fa <#if designer.attentioned==1>fa-heart<#else>fa-heart-o</#if>"></i><#if designer.attentioned==1>已关注<#else>关注</#if>
                    (<strong>${designer.tfans}</strong>)
                </button> &nbsp;
                <#if designer.moreIntro && isPreview==null><a href="${base}/showroom/designerintro/${designer.id}"
                                                              target="_blank" class="button button-s button-purple">更多详情
                    <i class="fa fa-angle-right"></i></a></#if>
                <#if isPreview><a href="${base}/showroom/designerintro/${designer.id}?isPreview=${isPreview}"
                                  target="_blank" class="button button-s button-purple">更多详情预览 <i
                            class="fa fa-angle-right"></i></a></#if>
            </h1>
            <div class="simple-info">
                ${designer.intro}
            </div>
        </div>
        <div class="logo" style="height:auto;">
            <img src="${picture_base}${(designer.headPic)!}" alt="${designer.name}"/>
        </div>
        </div>
    </#if>
    <#if series?size gt 2>
        <div class="tabs-container scroll-suspend clearfix" data-anchor="true" data-offset="37"
             data-scroll-bg="tabs-holder-bg" data-scroll-holder="tabs-holder">
            <div class="slider-tabs">
                <div class="viewport">
                    <div class="slider-tab overview">
                        <#list series as serie>
                            <a href="#holder-${serie.id}">${serie.name}</a>
                        </#list>
                    </div>
                </div>
                <div class="tabs-gradient">
                    <div><i class="fa fa-angle-down"></i></div>
                </div>
                <div class="tabs-switch">
                    <#list series as serie>
                        <a href="#holder-${serie.id}">${serie.name}</a>
                    </#list>
                </div>
            </div>
        </div>
        <div class="tabs-holder clearfix" id="tabs-holder" style="display: none;"></div>
        <div class="tabs-holder-bg" id="tabs-holder-bg" style="display: none;"></div>
    </#if>
    <#if series?size gt 0>
        <div class="list designer-product clearfix">
            <!--<h2>现货区</h2>-->
            <#list series as serie>
                <div class="series-name clearfix" id="holder-${serie.id}">${serie.name}</div>
                <div class="series-product" data-url="/showroom/series/${serie.id}.json" style="min-height:565px;">
                    <div style="text-align:center;padding-top:200px;"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        <div class="list designer-product clearfix">
            <div class="series-product" data-url="/showroom/product/${designerId}.json" style="min-height:565px;"
                 data-type="cola">
                <div style="text-align:center;padding-top:200px;"><i class="fa fa-spinner fa-pulse fa-3x fa-fw"></i>
                </div>
            </div>
        </div>
    </#if>
</div>
<script id="item-template" type="text/html">
    {{each productList as value i}}
    <div class="list-item">
        {{if value.productSellType=='PRESELL'}}
        <!--	<span class="tag crowd"></span> -->
        {{else if value.productSellType=='CUSTOM'}}
        <!--  <span class="tag customize"></span> -->
        {{else if value.productSellType=='SPOT'}}
        <span class="tag spot"></span>
        {{/if}}
        <a href="javascript:" data-url="/member/interest/collection/insert/{{value.id}}" method-type="post"
           success-tip="false" call-back="favIncrease({{value.id}})" class="favorite ajax-request"
           id="product-{{value.id}}"><i class="fa {{if value.collectioned==1}}fa-heart {{else}} fa-heart-o{{/if}} "></i>
            我喜欢</a>
        <a href="/product/{{value.id}}" class="img" target="_blank">{{if value.store < 1}}<i class="sale"></i><i
                    class="satip">已售罄</i>{{/if}}<img src="${picture_base}{{value.productImageCover}}!300"
                                                     alt="{{value.name}}"></a>
        <p class="title"><a href="/product/{{value.id}}" target="_blank" title="{{value.name}}">{{value.name}}</a></p>
        <p class="price">
            <span>&yen;{{value.currentPrice}}</span>
            <span> {{if value.currentPrice < value.originalPrice}} <s>{{value.originalPrice}}</s>{{/if}}</span>
        </p>
    </div>
    {{/each}}
</script>
<script id="cola-template" type="text/html">
    {{each list as value i}}
    <div class="list-item">
        {{if value.productSellType=='PRESELL'}}
        <!--	<span class="tag crowd"></span> -->
        {{else if value.productSellType=='CUSTOM'}}
        <!--  <span class="tag customize"></span> -->
        {{else if value.productSellType=='SPOT'}}
        <span class="tag spot"></span>
        {{/if}}
        <a href="javascript:" data-url="/member/interest/collection/insert/{{value.id}}" method-type="post"
           success-tip="false" call-back="favIncrease({{value.id}})" class="favorite ajax-request"
           id="product-{{value.id}}"><i class="fa {{if value.collectioned==1}}fa-heart {{else}} fa-heart-o{{/if}} "></i>
            我喜欢</a>
        <a href="/product/{{value.id}}" class="img" target="_blank">{{if value.store < 1}}<i class="sale"></i><i
                    class="satip">已售罄</i>{{/if}}<img src="${picture_base}{{value.productImageCover}}!300"
                                                     alt="{{value.name}}"></a>
        <p class="title"><a href="/product/{{value.id}}" target="_blank" title="{{value.name}}">{{value.name}}</a></p>
        <p class="price">
            <span>&yen;{{value.currentPrice}}</span>
            <span> {{if value.currentPrice < value.originalPrice}} <s>{{value.originalPrice}}</s>{{/if}}</span>
        </p>
    </div>
    {{/each}}
</script>
<script>
    var a = true;
    if ($('.slider-tab').width() < $('.slider-tabs').width()) {
        $('.tabs-gradient').hide();
    } else {
        $(".slider-tabs").tinyscrollbar({axis: 'x'});
        $('.tabs-gradient').click(function () {
            $('.tabs-switch').toggleClass('active');
            $(this).toggleClass('active');
            a == true ? a = false : a = true;
        });
    }

    function loadProduct() {
        var html = "";
        $('.series-product').each(function () {
            var type = $(this).attr('data-type');
            if ($(this).offset().top <= pageTop()) {
                var this_obj = $(this);
                var url = this_obj.attr("data-url");
                if (url) {
                    this_obj.removeAttr("data-url");
                    $.get(url, function (data) {
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
        if (($('.viewport').width() > $('.slider-tab').width()) && ($('.tabs-container').hasClass('suspend-fixed')) && a == true) {
            $('.tabs-gradient').trigger('click');
        }
    });

    var followIncrease = function (id) {
        var obj = $('#designer-follow-' + id);
        var i = parseInt(obj.find('strong').text());
        obj.find('i').removeClass('fa-heart-o').addClass('fa-heart');
        obj.find('strong').text(i + 1);
        obj.removeClass('ajax-request');
    };

    var favIncrease = function (id) {
        var obj = $('#product-' + id);
        //var i=parseInt(obj.find('strong').text());
        obj.find('i').removeClass('fa-heart-o').addClass('fa-heart');
        //obj.find('strong').text(i+1);
        obj.removeClass('ajax-request');
    };

    $('.fullscreen-video').click(function () {
        var url = $(this).attr('data-video');
        var html = '<div><div class="b-c" style="position:fixed;opacity: 0.3; background: rgb(0, 0, 0);width:100%;z-index:66;top:0;left:0;"></div><div class="v-c" style="width:900px;height:550px;position:absolute;left:50%;margin-left:-450px;top:400px;z-index:88"><a href="javascript:;" class="pop-close" style="position: absolute;top: 0;right: 20px;width: 30px;height: 30px;line-height: 30px;cursor: pointer;z-index:99;color: #FFF;font-size: 35px;" onclick="$(this).parent().parent().remove()">×</a><embed width="100%" height="100%" pluginspage="//www.macromedia.com/go/getflashplayer" src="//static.d2c.cn/common/o/flvplayer.swf" type="application/x-shockwave-flash" allowfullscreen="true" flashvars="vcastr_file=' + url + '&amp;LogoText=&amp;IsAutoPlay=1" quality="high" /></div></div>';
        $('body').append(html);
        $('.b-c').height(window.screen.height)
    })

</script>
<@m.page_footer />