<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='积分明细' noheader=true service='false' hastopfix='false'/>
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/point/info" class="back">
            <span class="icon icon-chevron-left">积分明细</span>
        </a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
</div>
<div style="height:3.5em;"></div>
<div class="wrap-main">
    <#if result.data.pointItems.list?exists && result.data.pointItems.list?size gt 0>
        <table class="list-table">
            <tr>
                <th>发生时间</th>
                <th>积分变动</th>
                <th>说明</th>
            </tr>
            <#list result.data.pointItems.list as pointItem>
                <tr>
                    <td>${pointItem.createDate?string("yyyy-MM-dd HH:mm")!""}</td>
                    <td><#if pointItem.inNum gt 0>+ ${(pointItem.inNum)!}<#else>- ${(pointItem.outNum)!}</#if></td>
                    <td>${(pointItem.transactionInfo)!}</td>
                </tr>
            </#list>
        </table>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=result.data.pointItems.pageNumber totalpage=result.data.pointItems.pageCount />
        </div>
    <#else>
        <div class="text-center" style="line-height:100px;">暂无数据</div>
    </#if>
</div>
<hr/>
<@m.page_footer />
