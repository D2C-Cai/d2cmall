<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的退款申请" url='/member/home' cart=true title='我的退款申请' service='false'/>
<div class="tab-holder">
    <div class="tab tab-suspend">
        <a href="/member/refund/list" class="on">退款申请</a>
        <a href="/member/reship/list">退货申请</a>
        <a href="/member/exchange/list">换货申请</a>
    </div>
</div>

<div class="section lazyload">
    <#if (pager.list?size gt 0)>
        <!-- <div class="pages" style="margin:0">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>-->
        <div class="form">
            <#list pager.list as refund>
                <#if refund.reshipId==null>
                    <div class="form-item item-card item-margin clearfix">
                        <div class="top">退款编号：${refund.refundSn}
                            <span class="float-right">${(refund.refundStatusName)!}</span>
                        </div>
                        <a href="/member/refund/${(refund.id)!}" class="clearfix">
                            <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                                   data-image="<#if refund.productImg?exists>${picture_base}/${(refund.sp1?eval.img)!}!120</#if>"/></span>
                            <span class="title">${(refund.productName)!}【${(refund.sp2?eval.value)!} ${(refund.sp1?eval.value)!}】</span>
                            <span class="property">货号：${(refund.productSn)!}</span>
                            <span class="property">退款金额：${(refund.totalAmount?string('currency'))!}</span>
                            <#if refund.orderPayType==3>
                                <span class="property">退款方式：支付宝 - ${(refund.backAccountName)!}-${(refund.backAccountSn)!}</span>
                            <#else>
                                <span class="property">退款方式：原路返回</span>
                            </#if>
                        </a>
                        <div class="bar text-right">
                            <#if refund.refundStatus==1>
                                <a href="javascript:" data-url="/member/refund/cancel/${(refund.id)!}.json"
                                   method-type="post" confirm="确定要取消退款申请中吗？" class="ajax-request button button-outline"
                                   style="margin-top:.3em">取消申请</a>
                            </#if>
                            <a href="/member/refund/${refund.id}" class="button button-red"
                               style="margin-top:.3em">查看详情</a>
                        </div>
                    </div>
                </#if>
            </#list>
        </div>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    <#else>
        <div class="no-result">暂无退款申请</div>
    </#if>
</div>
<@m.page_footer />