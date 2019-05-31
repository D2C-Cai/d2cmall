<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="出价记录" title="出价记录" keywords="出价记录" />
<div class="section">
    <table class="table offer-table" style="width:100%;padding:0 10px">
        <tbody>
        <trclass
        ="item">
        <th width="28%">用户</th>
        <th width="28%">出价</th>
        <th width="28%">时间</th>
        <th width="16%">状态</th>
        </tr>
        <#if pager.list?exists && pager.list?size gt 0>
            <#list pager.list as list>
                <tr class="item">
                    <td><#if (list.loginCode?length) gt 8 >***${list.loginCode?substring(7)}<#elseif (!list.loginCode?exists)>${(list.memberNick)!}<#else>${list.loginCode}</#if></td>
                    <td><strong>&yen;${list.offer?string("currency")?substring(1)}</strong></td>
                    <td>${list.createDate?string("HH:mm:ss MM/dd")}</td>
                    <td <#if list.status==1 || list.status==8>style="color:#fd555d"</#if>><#if list.status==1>领先<#elseif list.status==0>出局<#elseif list.status==8>得标<#else>删除</#if></td>
                </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#if pager.list?exists && pager.list?size gt 0>
    <div class="pages consult-pages">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>
</#if>

<@m.page_footer />