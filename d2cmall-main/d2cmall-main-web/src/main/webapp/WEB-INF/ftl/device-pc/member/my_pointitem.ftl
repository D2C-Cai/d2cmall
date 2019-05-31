<#import "templates/public_pc.ftl" as m>
<div class="pages float-right" style="margin:0">
    <@m.p page=result.data.pointItems.pageNumber totalpage=result.data.pointItems.pageCount num=result.data.pointItems.totalCount />
</div>
<table border="0" cellpadding="6" cellspacing="0" width="100%" class="table" style="margin-top:20px;">
    <tr>
        <th>发生时间</th>
        <th>收入</th>
        <th>支出</th>
        <th>剩余积分</th>
        <th>说明</th>
    </tr>
    <#if result.data.pointItems.list?exists && result.data.pointItems.list?size gt 0>
        <#list result.data.pointItems.list as pointItem>
            <tr class="order-item text-center">
                <td class="small">${pointItem.createDate?string("yyyy-MM-dd HH:mm")!""}</td>
                <td>${(pointItem.inNum)!}</td>
                <td>${(pointItem.outNum)!}</td>
                <td>${(pointItem.balance)!}</td>
                <td>${(pointItem.transactionInfo)!}</td>
            </tr>
        </#list>
    </#if>
</table>
<div class="pages float-right" style="margin:0">
    <@m.p page=result.data.pointItems.pageNumber totalpage=result.data.pointItems.pageCount num=result.data.pointItems.totalCount />
</div>
