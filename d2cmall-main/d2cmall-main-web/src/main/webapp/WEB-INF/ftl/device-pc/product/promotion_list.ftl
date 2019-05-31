<#import "templates/public_pc.ftl" as m>
<@m.page_header title="${result.datas.tag4Promotion.name} - 活动" keywords='D2C，服装设计师,原创品牌,买手店' description='D2C全球设计师品牌集成平台' js='utils/unslider.min'/>
<@m.top_nav channel="" />
<div class="layout layout-response layout-product clearfix">
    <#if result.datas.promotions.totalCount &gt; 0 >
        <div class="list product-list lazyload clearfix">
            <div class="presale-fullscreen">
                <div class="fullscreen lazyload">
                    <#list result.datas.promotions.list as promotion>
                        <#if promotion.pcBanner>
                            <div class="text-center promotion-list">
                                <div class="promotion-countdown">
                                    <div class="count-down"
                                         data-startTime="${promotion.startTime?string("yyyy/MM/dd HH:mm:ss")}"
                                         data-endTime="${promotion.endTime?string("yyyy/MM/dd HH:mm:ss")}"
                                         data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}" ata-defined="second"
                                         data-type="split-time">
                                        <span class="day down-time">00</span>
                                        <span class="time-split"></span>
                                        <span class="hour down-time">00</span>
                                        <span class="time-split">:</span>
                                        <span class="minute down-time">00</span>
                                        <span class="time-split">:</span>
                                        <span class="second down-time">00</span>
                                        <span class="time-split"> </span>
                                    </div>
                                    <div class="text-tips">
                                        <span class="day" style="background:#fd555d;">天</span>
                                        <span></span>
                                        <span class="hour" style="background:#fd555d;">时</span>
                                        <span></span>
                                        <span class="minute" style="background:#fd555d;">分</span>
                                        <span></span>
                                        <span class="second" style="background:#fd555d;">秒</span>
                                    </div>
                                </div>
                                <a href="${base}/promotion/${promotion.id}" target="_blank" style="display:block;">
                                    ${promotion.pcBanner}
                                </a>
                            </div>
                        </#if>
                        <div class="promotion-title"><p>${promotion.name}</p></div>
                    </#list>
                </div>
            </div>
        </div>
        <div class="pages" id="product-list-bottom-page" style="padding-bottom:30px;">
            <@m.p page=result.datas.promotions.pageNumber totalpage=result.datas.promotions.pageCount num=result.datas.promotions.totalCount />
        </div>
    </#if>
</div>
<@m.page_footer />