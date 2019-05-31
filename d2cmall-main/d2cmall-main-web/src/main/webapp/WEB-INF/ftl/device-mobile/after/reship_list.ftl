<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的退货申请" url='/member/home' cart=true title='我的退货申请' service='false'/>
<div class="tab-holder">
    <div class="tab tab-suspend">
        <a href="/member/refund/list">退款申请</a>
        <a href="/member/reship/list" class="on">退货申请</a>
        <a href="/member/exchange/list">换货申请</a>
    </div>
</div>

<div class="section lazyload">
    <#if (pager.list?size gt 0)!>
        <!-- <div class="pages" style="margin:0">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>-->
        <div class="form">
            <#list pager.list as reship>
                <div class="form-item item-card item-margin clearfix">
                    <div class="top">退款编号：${reship.reshipSn} <span
                                class="float-right">${(reship.reshipStatusName)!}</span>
                    </div>
                    <a href="/member/reship/${(reship.id)!}" class="clearfix">
                        <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                               data-image="<#if reship.sp1?exists>${picture_base}/${(reship.sp1?eval.img)!}!120</#if>"/></span>
                        <span class="title">${(reship.productName)!}【${(reship.sp2?eval.value)!} ${(reship.sp1?eval.value)!}】</span>
                        <span class="property">货号：${(reship.productSn)!}</span>
                        <span class="property">退款金额：${(reship.refund.totalAmount?string('currency'))!}</span>
                        <#if reship.orderPayType==3>
                            <span class="property">退款方式：支付宝-${(reship.refund.backAccountSn)!}-${(reship.refund.backAccountName)!}</span>
                        <#else>
                            <span class="property">退款方式：原路返回</span>
                        </#if>
                    </a>
                    <div class="bar text-right">
                        <a href="/member/reship/${reship.id}" class="button button-red" style="margin-top:.3em">查看详情</a>
                        <#if reship.reshipStatus &lt;8 &&reship.reshipStatus &gt; -1>
                            <a href="javascript:" data-url="/member/reship/cancel/${reship.id}.json"
                               confirm="确定要取消退款退货申请中吗？" class="ajax-request button button-outline" method-type='post'
                               style="margin-top:.3em">取消申请</a>
                        </#if>
                        <#if reship.reshipStatus==2>
                            <a href="/member/reship/logistic/${reship.id}" class="button button-red"
                               style="float:right;margin-top:.3em">填写快递单号</a>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    <#else>
        <div class="no-result">暂无退款退货申请</div>
    </#if>
</div>
<@m.page_footer />