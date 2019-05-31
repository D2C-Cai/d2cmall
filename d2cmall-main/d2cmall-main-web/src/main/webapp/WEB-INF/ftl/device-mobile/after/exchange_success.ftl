<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="换货详情" title='换货详情' button='false' service='false'/>
<div class="section">
    <#if exchange.exchangeStatus == 'APPLY'||exchange.exchangeStatus == 'APPROVE'||exchange.exchangeStatus == 'WAITFORRECEIVE'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-wait"></div>
            <div class="green"><strong>商家正在处理换货申请</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>・正常情况下，换货申请将达成并需要退货给商家。</p>
                <p>・若有特殊情况，客服会与您联系</p>
                <p>・若长时间未得到处理，请联系我们的客服。</p>
            </div>
            <hr/>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p><strong class="orange">请寄出您要换货的商品，并填写快递信息</strong></p>
                <p>为了保证退回的商品的质量，请您按原包装寄回。</p>
                <p>请您仔细填写退换货单，或者在退回的件内标注您的订单相关信息，以便我们第一时间给您处理。</p>
            </div>
        </div>
    <#elseif exchange.exchangeStatus == 'WAITDELIVERED'||exchange.exchangeStatus == 'DELIVERED'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-ok"></div>
            <div class="green"><strong>商家已经收到换货商品</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>・若长时间未有进度反馈，请与我们客服联系。</p>
            </div>
        </div>
    <#elseif exchange.exchangeStatus == 'SUCCESS'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-ok"></div>
            <div class="green"><strong>用户已经收到换货商品</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>若有疑问或异议，请联系我们的客服。</p>
            </div>
        </div>
    <#elseif exchange.exchangeStatus == 'CLOSE'||exchange.exchangeStatus == 'REFUSE'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-excam"></div>
            <div class="red"><strong>商家拒绝了换货申请</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>拒绝申请的理由是：</p>
                <p style="color:red">${(exchangeLogs[0].info)!}</p>
                <p>若有疑问或异议，请联系我们的客服。</p>
            </div>
        </div>
    </#if>
    <div class="form">
        <div class="form-item item-flex form-nobor">
            <label>换货原因</label><label>${(exchange.exchangeReasonName)!}</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>换货编号</label><label> ${exchange.exchangeSn}</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>申请时间</label><label>${exchange.createDate?string("yyyy/MM/dd HH:mm")}</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>换货说明</label><label>${(exchange.memo)!}</label>
        </div>
        <#if exchangeLogs>
        <div class="form-item item-flex" style="border-bottom:none;font-size:20px;">
            <label>退款进度</label>
        </div>
        <div class="logistics_list" style="margin-top:0">
            <ul>
                <#list exchangeLogs as log>
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