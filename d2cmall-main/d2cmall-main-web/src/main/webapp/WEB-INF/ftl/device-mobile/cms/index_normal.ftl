<@m.app_download_bar />
<@m.page_nav_bar channel='home' />
<#if pageDefine.fieldDefs.block01.type==0 >
    <div class="clearfix">
        ${(homePage.block01)!}
    </div>
<#else>
    <div class="swiper-container">
        <#if homePage.blocks.block01?size gt 0>
            <div class="swiper-wrapper">
                <#list homePage.blocks.block01 as block>
                    <div class="swiper-slide"><a href="${(block.bindUrl)!}"><img alt="${(block.title)!}"
                                                                                 src="${picture_base}${(block.frontPic)!}"/></a>
                    </div>
                </#list>
            </div>
            <div class="swiper-pagination"></div>
        </#if>
    </div>
</#if>

<div class="section lazyload">
    <!--
    <div class="list">
    <#list newArrival.list as promotion>
        <a href="${mobileBase}/promotion/${promotion.id}" class="item item-vertical">
        <#if promotion.wapBanner>
        <span class="img">${promotion.wapBanner}</span>
        <#elseif promotion.pcBanner>
        <span class="img">${promotion.pcBanner}</span>
        </#if>
        <span class="title">${promotion.name}</span>
        </a>
    </#list>
    </div>
    -->
    <#if promotions?exists >
        <div class="list">
            <#list promotions.list as promotions>
                <a href="${mobileBase}/promotion/${promotions.id}" class="item item-vertical">
                    <#if promotions.wapBanner>
                        <span class="img">${promotions.wapBanner}</span>
                    <#elseif promotions.pcBanner>
                        <span class="img">${promotions.pcBanner}</span>
                    </#if>
                    <span class="title">${promotions.name}</span>
                </a>
            </#list>
        </div>
    </#if>
</div>
<script>
    <#if homePage.blocks.block01?size gt 0>
    var swiper = new Swiper('.swiper-container', {
        autoHeight: true,
        loop: true,
        autoplay: {
            delay: 2500,
            disableOnInteraction: false,
        },
        pagination: {
            el: '.swiper-pagination',
        }
    });
    </#if>
</script>
