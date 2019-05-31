<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${crowd.name} - 预售' keywords='预售,预售优惠,服装设计师,原创品牌,设计师品牌' description='D2C原创设计师品牌预售' js='unslider.min'/>
<@m.top_nav channel="presale" />
<div class="presale-fullscreen">
    <div class="fullscreen lazyload">
        <div class="fullscreen-content">
            ${(crowd.backCode)!?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
        </div>
        <div class="layout-response list presale-list clearfix">
            <div class="presale-countdown">
                <span class="count-down" data-startTime="${crowd.beginCrowd?string("yyyy/MM/dd HH:mm:ss")}"
                      data-endTime="${crowd.endCrowd?string("yyyy/MM/dd HH:mm:ss")}"
                      data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
            </div>
            <#if result.pager.totalCount &gt; 0 >
                <#list result.pager.list as crowdItem>
                    <div class="list-item">
                        <a href="${base}/product/${crowdItem.productId}" class="img" target="_blank"><img
                                    src="${static_base}/blank/300-467.gif"
                                    data-image="${picture_base}${(crowdItem.product.productImageCover)!}!300"
                                    alt="${crowdItem.product.name}"/></a>
                        <p class="title"><a href="${base}/product/${crowdItem.productId}" target="_blank"
                                            title="${crowdItem.product.name}"
                                            alt="${(crowdItem.product.name)!}">${crowdItem.product.name}</a></p>
                        <p class="operator">
                            <a href="${base}/product/${crowdItem.productId}" target="_blank">立即抢订</a>
                        </p>
                        <p class="price">
                            <span>&yen; ${(crowdItem.currentPrice?string("currency")?substring(1))!}</span>
                            <#if crowdItem.originalCost gt crowdItem.currentPrice><s>
                                &yen; ${(crowdItem.originalCost?string("currency")?substring(1))!}</s></#if>
                        </p>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</div>
<@m.page_footer />