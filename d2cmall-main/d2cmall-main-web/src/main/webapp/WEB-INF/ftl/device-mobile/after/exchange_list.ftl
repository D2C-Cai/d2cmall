<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的换货申请" url='/member/home' cart=true title='我的换货申请' service='false'/>
<div class="tab-holder">
    <div class="tab tab-suspend">
        <a href="/member/refund/list">退款申请</a>
        <a href="/member/reship/list">退货申请</a>
        <a href="/member/exchange/list" class="on">换货申请</a>
    </div>
</div>
<div class="section lazyload">
    <#if (pager.list?size gt 0)>
        <!-- <div class="pages" style="margin:0">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>-->
        <div class="form">
            <#list pager.list as exchange>
                <div class="form-item item-card item-margin clearfix">
                    <div class="top">换货编号：${exchange.exchangeSn} <span
                                class="float-right">${(exchange.exchangeStatusName)!}</span>
                    </div>
                    <a href="/member/exchange/${(exchange.id)!}" class="clearfix">
                        <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                               data-image="<#if exchange.oldSp1?exists>${picture_base}/${(exchange.oldSp1?eval.img)!}!120</#if>"/></span>
                        <span class="title">${(exchange.oldProductName)!}【${(exchange.oldSp2?eval.value)!} ${(exchange.oldSp1?eval.value)!}】</span>
                        <#if exchange.skuId>
                            <span class="property">货号：${(exchange.productSn)!}</span>
                            <span class="property">颜色：${(exchange.sp1?eval.value)!}&nbsp;&nbsp;&nbsp;&nbsp;尺码：${(exchange.sp2?eval.value)!}</span>
                        </#if>
                    </a>
                    <div class="bar text-left">
                        <a href="/member/exchange/${exchange.id}" class="button button-red"
                           style="margin-top:.3em">查看详情</a>
                        <#if exchange.exchangeStatus==1>
                            <a href="/member/exchange/logistic/${exchange.id}" class="button button-red"
                               style="margin-top:.3em;float:right">填写快递单号</a>
                        </#if>
                        <#if exchange.exchangeStatus &lt;3 &&exchange.exchangeStatus &gt; -1>
                            <a href="javascript:" data-url="/member/exchange/cancel/${exchange.id}.json"
                               confirm="确定要取消申请中吗？" class="ajax-request button button-outline" method-type='post'
                               style="margin-top:.3em">取消申请</a>
                        </#if>
                        <#if exchange.exchangeStatus==4>
                            <a href="javascript:" data-url="/member/exchange/receive/${exchange.id}.json"
                               confirm="确定收到你想要的商品了吗？" class="ajax-request button button-outline" method-type='post'
                               style="margin-top:.3em">确认收货</a>
                        </#if>

                    </div>
                </div>
            </#list>
        </div>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    <#else>
        <div class="no-result">暂无换货申请</div>
    </#if>
</div>
<@m.page_footer />