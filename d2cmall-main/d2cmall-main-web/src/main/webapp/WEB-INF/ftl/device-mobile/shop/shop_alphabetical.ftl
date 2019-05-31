<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='设计师A-Z' keywords="设计师现货区,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" service='false'/>
<@m.page_nav_bar channel="showroom" />
<#if !m.FROMAPP>
    <@m.app_download_bar fixed="false" />
</#if>

<div class="tab-holder">
    <div class="tab tab-suspend">
        <a href="/showroom">热门设计师</a>
        <a href="/showroom/basic/style">按风格</a>
        <a href="/showroom/basic/designArea">按分类</a>
        <a href="/showroom/basic/country">按地区</a>
        <a class="on" href="/showroom/alphabetical">A~Z</a>
    </div>
</div>
<div class="letter-list bounceInRight animated" id="letter-top">
    <div style="height:110%">
        <#list letters as letter>
            <a href="#${letter}">${letter}</a>
        </#list>
    </div>
</div>
<div class="section">
    <#list letters as letter>
        <h2 class="letter-title" id="${letter}">${letter}</h2>
        <div class="list clearfix" style="background:#fff">
            <#list designers as designer>
                <#if designer.pageGroup==letter>
                    <a href="/showroom/designer/${designer.id}" class="designer-link">${designer.name}&nbsp;/&nbsp;<span
                                style="color:#999">${designer.designers}</span><#if designer.recommend==1>
                        <i>HOT</i></#if></a></li>
                </#if>
            </#list>
        </div>
    </#list>
</div>
<hr/>

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