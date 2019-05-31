<#import "templates/public_pc.ftl" as m>
<div class="pages float-right" style="margin:0">
    <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
</div>
<table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey">
    <tr>
        <th>时间</th>
        <th>存入</th>
        <th>支出</th>
        <th>状态</
        >
        <th>备注</th>
        <th>操作</th>
    </tr>
    <#if pager.list?exists && pager.list?size gt 0>
        <#list pager.list as bill>
            <tr class="item text-center">
                <td class="small" style="height:40px">${bill.createDate?string("yyyy-MM-dd HH:mm")!""}</td>
                <td>
                    <strong style="color:<#if bill.factTotalAmount &gt; 0>green<#else>black</#if>"><#if bill.factTotalAmount &gt; 0>${(bill.factTotalAmount)!}<#else>0.00</#if></strong>
                </td>
                <td>
                    <strong style="color:<#if bill.factTotalAmount &lt; 0>red<#else>black</#if>"><#if bill.factTotalAmount &lt; 0>${(bill.factTotalAmount)!}<#else>0.00</#if></strong>
                </td>
                <td>${(bill.statusName)!}</td>
                <td style="width:30%">${(bill.memo)!}</td>
                <td><#if bill.status==0 && bill.payType=='PAY'>
                        <button type="button" template-url="/member/bill/pay/${bill.id}" modal-type="pop"
                                class="button button-green ajax-request"><i class="fa fa-check"></i> 确认支付
                        </button>
                        <button type="button" data-url="/member/bill/close/${bill.id}" method-type="post"
                                confirm="确定要取消支付吗？" class="button button-red ajax-request"><i class="fa fa-close"></i>
                            取消支付
                        </button>
                    </#if>
                </td>
            </tr>
        </#list>
    </#if>
</table>
<div class="pages float-right" style="margin:0">
    <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
</div>