<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="退款详情" title='退款详情' button='false' service='false'/>
<div class="section">
    <#if refund.refundStatus =='APPLY'|| refund.refundStatus == 'WAITFORPAYMENT'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-wait"></div>
            <div class="green"><strong>商家正在处理退款申请</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>・正常情况下，退款申请将达成并安排退款。</p>
                <p>・若有特殊情况，客服会与你取得联系。</p>
                <p>・若长时间未得到处理，请联系我们的客服。</p>
            </div>
        </div>
    <#elseif refund.refundStatus == 'SUCCESS' >
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-ok"></div>
            <div class="green"><strong>商家已经退款成功，请查看你的${(refund.payType.zhString)!}</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>・商家会在2-5个工作日内将货款退到你的卡中。</p>
                <p>・若有其他异常情况，我们客服会与你取得联系。</p>
                <p>・若长时间未收到退款，请与我们客服联系。</p>
            </div>
        </div>
    <#elseif refund.refundStatus == 'CLOSE'>
        <div class="text-center padding" style="line-height:150%;padding-top:1em;">
            <div class="icon icon-excam"></div>
            <div class="red"><strong>商家不同意退款</strong></div>
            <div style="font-size:0.8em;text-align:left;padding:1em;">
                <p>不同意退款的理由是：</p>
                <p style="color:red">${(refundLogs[0].info)!}</p>
                <p>若有疑问或异议，请联系我们的客服。</p>
            </div>
        </div>
    </#if>
    <div class="form">
        <div class="form-item item-flex form-nobor">
            <label>申请金额</label><label>&yen; ${refund.applyAmount}元</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>退货件数</label><label>&yen; ${refund.quantity}件</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>核定金额</label><label>&yen; ${refund.totalAmount}元</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>退款原因</label><#if reship?exists && reship!=null><label>${(reship.reshipReasonName)!}</label><#else>
        <label>${(refund.refundReasonName)!}</label></#if>
        </div>

        <div class="form-item item-flex form-nobor">
            <label>退款编号</label><label>${refund.refundSn}</label>
        </div>
        <div class="form-item item-flex">
            <label>申请时间</label><label>${refund.createDate?string("yyyy/MM/dd HH:mm")}</label>
        </div>
        <div class="form-item item-flex form-nobor">
            <label>退款说明</label><label>${refund.memo}</label>
        </div>
        <#if refund.orderPayType==3>
            <div class="form-item item-flex form-nobor">
                <label>退款支付宝</label><label>${refund.backAccountSn}</label>
            </div>
            <div class="form-item item-flex form-nobor">
                <label>支付宝名称</label><label>${refund.backAccountName}</label>
            </div>
        <#else>
            <div class="form-item item-flex form-nobor">
                <label>退款方式</label><label>原路返回</label>
            </div>
        </#if>
        <#if refundLogs || reshipLogs>
            <div class="form-item item-flex" style="border-bottom:none;font-size:20px;">
                <label>退款进度</label>
            </div>
            <div class="logistics_list" style="margin-top:0">
                <ul>
                    <#list refundLogs as log>
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
