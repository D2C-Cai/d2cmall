<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='设计师品牌' hastopfix='true' keywords="设计师现货区,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<@m.page_nav_bar channel="showroom" />
<#if !m.FROMAPP>
    <@m.app_download_bar fixed="false" />
</#if>

<div class="tab-holder">
    <div class="tab tab-suspend">
        <a class="on" href="/showroom">热门设计师</a>
        <a href="/showroom/basic/style">按风格</a>
        <a href="/showroom/basic/designArea">按分类</a>
        <a href="/showroom/basic/country">按地区</a>
        <a href="/showroom/alphabetical">A~Z</a>
    </div>
</div>
<#if !m.FROMAPP>
    <span class="goup"></span>
</#if>
<div class="section text-center lazyload">
    <h2 class="block-title">热门设计师</h2>
    <div class="list clearfix">
        <#list list as designer>
            <#if designer.introPic !='' && designer.introPic?length gt 0>
                <a href="/showroom/designer/${designer.id}" class="item item-flex item-gap"><span class="img"><img
                                alt="${designer.name}" src="${static_base}/m/img/blank100x157.png"
                                data-image="${picture_base}${(designer.introPic)!}"/></span></a>
            </#if>
        </#list>
    </div>
    <div style="font-size:0.8em;padding:1em 0 2em 0">查看更多设计师，请按风格、区域、分类进行查看</div>
</div>

<script type="text/javascript">
    var t = $(".tab-holder").offset().top, tabtop;
    app_client == 1 ? tabtop = 0 : tabtop = $('.img-top').width() * 0.148;

    $(window).scroll(function () {
        if ($(document).scrollTop() >= t) {
            $('.tab-holder').css({"position": "fixed", "top": tabtop})
        } else {
            $('.tab-holder').css({"position": "static", "top": 0})
        }
    })
</script>

<@m.page_footer />