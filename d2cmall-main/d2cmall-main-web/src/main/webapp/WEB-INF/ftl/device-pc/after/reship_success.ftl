<div class="form">
    <h2 class="pop-title">退款退货详情</h2>
    <div class="form-cinfo" style="padding:0">
        <table class="table table-grey">
            <tr class="title">
                <td>退款退货基本信息</td>
            </tr>
            <tr class="item">
                <td>
                    <table class="no-border" width="100%">
                        <tr>
                            <td>退货类型：退货退款</td>
                            <td>退货金额：<strong class="arial rmb">${reship.refund.totalAmount?string("currency")}</strong>
                            </td>
                            <td>退货原因：${(reship.reshipReasonName)!}</td>
                        </tr>
                        <tr>
                            <td>退货编号：<span class="small">${reship.reshipSn}</span></td>
                            <td>申请时间：<span class="small">${reship.createDate?string("yyyy/MM/dd HH:mm")}</span></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="3">退货说明：${(reship.memo)!}</td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <#if reship.orderPayType==3>退款支付宝号：${(reship.refund.backAccountSn)!}，支付宝名称：${(reship.refund.backAccountName!)}
                                <#elseif reship.orderPayType==7>
                                    <div class="form-item">
                                        <span>原路退回到您的钱包中，敬请查收</span>
                                    </div>
                                <#else>
                                    <div class="form-item">
                                        <span>原路退回到您的银行卡或支付宝中，敬请查收</span>
                                    </div>
                                </#if>
                            </td>
                        </tr>

                        <#if reship?exists && reship.evidences !=null >
                            <tr>
                                <td colspan="3">
                                    <#list reship.evidenceList as evidence>
                                        <a href="${picture_base}${evidence}" class="img" target="_blank"
                                           style="float-left"><img src="${picture_base}${evidence}!80" width="60"
                                                                   alt=""></a>
                                    </#list>
                                </td>
                            </tr>
                        </#if>
                    </table>
                </td>
            </tr>
        </table>

        <table class="table table-grey" style="margin-top:10px;">
            <tr class="title">
                <td>退款退货处理信息</td>
            </tr>
            <#if reship.reshipStatus == 'APPLY'||reship.reshipStatus == 'WAITFORRECEIVE'||reship.reshipStatus == 'APPROVE'>
                <tr class="item">
                    <td>
                        <h2>商家正在处理退货申请</h2>
                        <p>・若有特殊情况，商家将拒绝申请。</p>
                        <p>・若长时间未得到处理，请联系我们的客服。</p>
                    </td>
                </tr>
                <tr class="item">
                    <td>
                        <p><strong>请寄出你要退货的商品，并填写快递信息</strong></p>
                        <p>为了保证退回的商品的质量，请您按原包装递回。</p>
                        <p>请您仔细填写退换货单，或者在退回的件内标注您的订单相关信息，以便我们第一时间给您处理您的退换货。</p>
                    </td>
                </tr>
            <#elseif reship.reshipStatus == 'SUCCESS'>
                <tr class="item">
                    <td>
                        <h2>商家已经收到退货</h2>
                        <p>・若通过支付宝付款，款项退回到您的支付宝中。</p>
                        <p>・若长时间未收到退款，请与我们客服联系。</p>
                    </td>
                </tr>
            <#elseif reship.reshipStatus == 'CLOSE'||reship.reshipStatus == 'REFUSE'>
                <tr class="item">
                    <td>
                        <h2>商家拒绝了退货申请</h2>
                        <p style="color:red">${reshipLogs[0].info}</p>
                        <p>若有疑问或异议，请联系我们的客服。</p>
                    </td>
                </tr>
            </#if>
        </table>

        <table class="table table-grey" style="margin-top:10px;">
            <#list refundLogs as log>
                <tr class="item">
                    <td>${log.createDate?string("yyyy-MM-dd HH:mm")!""}</td>
                    <td>${(log.info)!}</td>
                </tr>
            </#list>
            <#list reshipLogs as log>
                <tr class="item">
                    <td>${log.createDate?string("yyyy-MM-dd HH:mm")!""}</td>
                    <td>${(log.info)!}</td>
                </tr>
            </#list>
        </table>
    </div>
</div>