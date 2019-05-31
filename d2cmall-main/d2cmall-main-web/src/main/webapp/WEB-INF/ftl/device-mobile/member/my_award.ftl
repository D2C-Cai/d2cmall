<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='我的抽奖' title='我的抽奖' button='false' service='false' hastopfix='false'/>
<div class="section">
    <div class="tab-holder">
        <div class="tab tab-suspend">
            <a href="/award/my/list?status=0"<#if status=0> class="on"</#if>>全部</a>
            <a href="/award/my/list?status=1"<#if status=1> class="on"</#if>>进行中</a>
            <a href="/award/my/list?status=2"<#if status=2> class="on"</#if>>已揭晓</a>
        </div>
    </div>

    <#if pager.list?exists && pager.list?size &gt; 0>
        <div class="form lazyload">
            <#list pager.list as list>
                <div class="form-item item-card item-margin clearfix">
                    <div class="top">抽奖时间：${list.createDate?string('yyyy/MM/dd HH:mm:ss')}
                        <em class="float-right">【
                            <#if list.status == -2>未付款已关闭
                        <#elseif list.status == -1>未中奖
                        <#elseif list.status== 0>待付款
                        <#elseif list.status== 1>已抽奖
                        <#elseif list.status== 2>已中奖
                        <#elseif list.status== 3>已发货
                            </#if>】</em></div>
                    <a href="/award/${list.awardId}" class="clearfix">
                        <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                               data-image="${picture_base}/${list.productImg}!120"
                                               alt="${list.awardTitle}"/></span>
                        <span class="title">${list.award.name} <#if list.status gte 2><em class="green">
                                中奖号码【${list.award.winNum}】</em></#if></span>
                        <span class="property">金额：&yen; ${list.price?string('0.00')}</span>
                        <span class="amount"
                              style="word-wrap: break-word;word-break: normal;">幸运号码: <#if list.status == 2>${list.luckyNum?replace(list.winNum,'<strong class="red">'+list.winNum+'</strong>')}<#else>${list.luckyNum}</#if></span>
                    </a>
                    <#if list.status== 0>
                        <div class="bar text-right">
                            <a href="/award/payment/${list.id}" class="button button-red">付款</a>
                        </div>
                    </#if>
                </div>
            </#list>
        </div>
        <div class="pages">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    <#else>
        <div class="no-result">还没有抽过奖哦</div>
    </#if>
</div>
<@m.page_footer />
