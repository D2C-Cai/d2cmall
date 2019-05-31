<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的账单" title='我的账单' button='false' service='false' hastopfix='false'/>
<div class="section">
    <#if pager.list?exists && pager.list?size gt 0>
        <#list pager.list as payItem>
            <div class="form">
                <div class="form-item item-flex">
                    <label>发生时间</label><label>${payItem.createDate?string("yyyy-MM-dd HH:mm")!""}</label>
                </div>
                <div class="form-item item-flex">
                    <label>金额变动</label><label
                            style="color:<#if payItem.factTotalAmount lte 0>red<#else>green</#if>">${(payItem.factTotalAmount)!}</label>
                </div>
                <div class="form-item item-flex">
                    <label>状态</label><label>${(payItem.statusName)!}</label>
                </div>
                <div class="form-item item-flex">
                    <label>说明</label><label>${(payItem.memo)!}</label>
                </div>
                <#if payItem.status==0 && payItem.payType=='PAY'>
                    <div class="form-item text-right">
                        <a href="/member/account/pay/${payItem.id}" class="button button-red text-center">确认支付</a>
                    </div>
                </#if>
            </div>
        </#list>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    <#else>
        <div class="text-center" style="line-height:100px;">暂无数据</div>
    </#if>
</div>
<@m.page_footer />
