<div class="form">
    <h2 class="pop-title">退款详情</h2>
    <div class="form-cinfo" style="padding:0">
        <table class="table table-grey">
            <tr class="title">
                <td>退款基本信息</td>
            </tr>
            <tr class="item">
                <td>
                    <table class="no-border" width="100%">
                        <tr>
                            <td>退款类型：退款</td>
                            <td>申请金额：<strong class="arial rmb">${refund.applyAmount?string("currency")}</strong></td>
                            <td>核定金额：<strong class="arial rmb">${refund.totalAmount?string("currency")}</strong></td>
                        </tr>
                        <tr>
                            <td>退款编号：<span class="small">${refund.refundSn}</span></td>
                            <td>申请时间：<span class="small">${refund.createDate?string("yyyy/MM/dd HH:mm")}</span></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td colspan="3">退款原因：
                                <#if reship?exists && reship!=null>
                                    ${(reship.reshipReasonName)!}
                                <#else>
                                    ${(refund.refundReasonName)!}
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">退款说明：${refund.memo}</td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <#if refund.orderPayType==3>
                                    退款支付宝号：${(refund.backAccountSn)!}，支付宝名称：${(refund.backAccountName)!}
                                <#else>
                                    退款方式：原路退回
                                </#if>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <table class="table table-grey" style="margin-top:10px;">
            <tr class="title">
                <td>退款退货处理信息</td>
            </tr>
            <#if refund.refundStatus =='APPLY'|| refund.refundStatus == 'WAITFORPAYMENT'>
                <tr class="item">
                    <td>
                        <h2>商家正在处理退款申请</h2>
                        <p>・正常情况下，退款申请将达成并在一定期限内退款给你。</p>
                        <p>・若有特殊情况，商家将拒绝申请，并会与你取得联系。</p>
                        <p>・若长时间未得到处理，请联系我们的客服。</p>
                    </td>
                </tr>
            <#elseif refund.refundStatus == 'SUCCESS' >
                <tr class="item">
                    <td>
                        <h2>商家退款已经完成，请查看你的${(refund.payType.zhString)!}</h2>
                        <p>・商家会在2-5个工作日内将货款退到你的卡中。</p>
                        <p>・若长时间未收到退款，请与我们客服联系。</p>
                    </td>
                </tr>
            <#elseif refund.refundStatus == 'CLOSE'>
                <tr class="item">
                    <td>
                        <h2>商家不同意退款</h2>
                        <p>不同意退款的理由是：</p>
                        <p style="color:red">${(refundLogs[0].info)!}</p>
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
    </div>
    </table>
</div>