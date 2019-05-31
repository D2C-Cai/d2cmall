<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="退货退款详情" title='退货退款详情' button='false' service='false'/>
<div class="section">
    <#if reship.reshipStatus == 'APPLY'||reship.reshipStatus == 'WAITFORRECEIVE'||reship.reshipStatus == 'APPROVE'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-wait"></div>
            <div class="green"><strong>商家正在处理退货申请</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>・正常情况下，退货申请将达成并需要退货给商家。</p>
                <p>・若有特殊情况，客服会与您联系</p>
                <p>・若长时间未得到处理，请联系我们的客服。</p>
            </div>
            <hr/>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p><strong class="orange">请寄出您要退货的商品，并填写快递信息</strong></p>
                <p>为了保证退回的商品的质量，请您按原包装寄回。</p>
                <p>请您仔细填写退换货单，或者在退回的件内标注您的订单相关信息，以便我们第一时间给您处理。</p>
            </div>
        </div>
    <#elseif reship.reshipStatus == 'SUCCESS'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-ok"></div>
            <div class="green"><strong>商家已经收到退货商品</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>・若通过支付宝付款，款项退回到您的支付宝中。</p>
                <p>・若通过信用卡或储蓄卡付款，款项退回到您的卡中。</p>
                <p>・若长时间未收到退款，请与我们客服联系。</p>
            </div>
        </div>
    <#elseif reship.reshipStatus == 'CLOSE'||reship.reshipStatus == 'REFUSE'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-excam"></div>
            <div class="red"><strong>商家拒绝了退货申请</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>拒绝申请的理由是：</p>
                <p style="color:red">${(reshipLogs[0].info)!}</p>
                <p>若有疑问或异议，请联系我们的客服。</p>
            </div>
        </div>
    </#if>
    <div class="form">
        <div class="form-item item-flex  form-nobor">
            <label>申请金额</label><label>&yen; ${reship.refund.applyAmount}元</label>
        </div>
        <div class="form-item item-flex  form-nobor">
            <label>核定金额</label><label>&yen; ${reship.refund.totalAmount}元</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>退货件数</label><label>${reship.actualQuantity}件</label>
        </div>
        <div class="form-item item-flex  form-nobor">
            <label>退货原因</label><label>${(reship.reshipReasonName)!}</label>
        </div>
        <div class="form-item item-flex  form-nobor">
            <label>退款编号</label><label>${reship.reshipSn}</label>
        </div>
        <div class="form-item item-flex">
            <label>申请时间</label><label>${reship.createDate?string("yyyy/MM/dd HH:mm")}</label>
        </div>
        <div class="form-item item-flex  form-nobor">
            <label>退款说明</label><label>${reship.memo}</label>
        </div>
        <#if reship.orderPayType==3>
            <div class="form-item item-flex  form-nobor">
                <label>退款支付宝</label><label>${reship.refund.backAccountSn}</label>
            </div>
            <div class="form-item item-flex  form-nobor">
                <label>支付宝名称</label><label>${reship.refund.backAccountName}</label>
            </div>
        <#elseif reship.orderPayType==6>
            <div class="form-item  form-nobor">
                <span>原路返回到您的钱包中，敬请查收</span>
            </div>
        <#else>
            <div class="form-item  form-nobor">
                <span style="color:#666;font-size:.8em">原路返回到您的银行卡或支付宝中，敬请查收</span>
            </div>
        </#if>
    </div>
    <#if reshipLogs>
        <div class="form" style="margin-bottom:0.5px">
            <div class="form-item item-flex">
                <label style="width:40%">退货退款进度</label>
            </div>
        </div>
        <div class="logistics_list" style="margin-top:0">
            <ul>
                <#list reshipLogs as log>
                    <li>
                        <div class="logis-detail-d">
                            <div class="logis-detail-content">
                                <p>${(log.info)!}</p>
                                <div style="overflow:hidden">
                                    <p class="float-left grey">经办人：<#if log.creator><span
                                                class="creater">${log.creator}</span><#else>D2C售后组</#if></p>
                                    <p class="float-right grey">${log.createDate?string("yy/MM/dd HH:mm")!""}</p>
                                </div>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </#if>
</div>
<script>
    if ($('.creater').size() > 0) {
        $('.creater').each(function () {
            var creater = $(this).text();
            if (creater.length >= 2) {
                creater = creater.substr(0, 1) + '**';
                $(this).text(creater);
            }
        })
    }

</script>
<@m.page_footer />