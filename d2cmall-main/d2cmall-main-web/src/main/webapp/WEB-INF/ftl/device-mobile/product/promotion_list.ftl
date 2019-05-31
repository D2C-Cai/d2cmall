<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="${result.datas.tag4Promotion.name}" />
<div class="lazyload">
    <#if pager.totalCount &gt; 0 >
        <div class="list" id="load-more-item">
            <#list pager.list as promotion>
                <div class="item item-vertical">
                    <a href="/promotion/${promotion.id}">
                        <span class="img">${promotion.wapBanner}</span>
                        <p class="title"><strong style="font-size:1.2em">${promotion.name}</strong></p>
                        <#if promotion.description><p class="desc">${promotion.description}</p></#if>
                    </a>
                </div>
            </#list>
        </div>
        <#if pager.pageCount gt 1>
            <#if RequestParameters.tagId!=''>
                <#assign param='&tagId='+RequestParameters.tagId>
            </#if>
            <#if RequestParameters.pageSize!=''>
                <#assign param=param+'&pageSize='+RequestParameters.pageSize>
            </#if>
            <div class="load-more scroll-load-more" data-url="/promotion/items?${param}" data-target="load-more-item"
                 template-id="list-template" data-page="${pager.pageNumber}" data-total="${pager.pageCount}">点击加载更多
            </div>
        </#if>
    <#else>
        <hr/>
        <div class="text-center" style="line-height:10em">
            未找到更多的活动
        </div>
    </#if>
</div>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="item item-vertical">
        <a href="/promotion/{{value.id}}">
            <span class="img">{{value.wapBanner}}</span>
            <p class="title"><strong style="font-size:1.2em">{{value.name}}</strong></p>
            {{if value.description!=''||value.description==value.name}}<p class="desc">{{value.description}}</p>{{/if}}
        </a>
    </div>
    {{/each}}
</script>
<@m.page_footer menu=false module='promotion' />