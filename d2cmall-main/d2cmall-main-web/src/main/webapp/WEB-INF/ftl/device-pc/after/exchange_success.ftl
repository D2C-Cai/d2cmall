<div class="form">
    <h2 class="pop-title">换货详情</h2>
    <div class="form-cinfo" style="padding:0">
        <table class="table table-grey">
            <tr class="title">
                <td>换货基本信息</td>
            </tr>
            <tr class="item">
                <td>
                    <table class="no-border" width="100%">
                        <tr>
                            <td>换货原因：${(exchange.exchangeReasonName)!}</td>
                        </tr>
                        <tr>
                            <td>换货编号：<span class="small">${exchange.exchangeSn}</span></td>
                        </tr>
                        <tr>
                            <td>申请时间：<span class="small">${exchange.createDate?string("yyyy/MM/dd HH:mm")}</span></td>
                        </tr>
                        <tr>
                            <td>换货说明：${(exchange.memo)!}</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

        <table class="table table-grey" style="margin-top:10px;">
            <tr class="title">
                <td>换货处理信息</td>
            </tr>
            <#if exchange.exchangeStatusName == 'APPLY'||exchange.exchangeStatusName == 'APPROVE'||exchange.exchangeStatusName == 'WAITFORRECEIVE'>
                <tr class="item">
                    <td>
                        <h2>商家正在处理换货申请</h2>
                        <p>・若有特殊情况，商家将拒绝申请。</p>
                        <p>・若长时间未得到处理，请联系我们的客服。</p>
                    </td>
                </tr>
                <tr class="item">
                    <td>
                        <p><strong>请寄出你要退换的商品，并填写快递信息</strong></p>
                        <p>为了保证退回的商品的质量，请您按原包装递回。</p>
                        <p>请您仔细填写退换货单，或者在退回的件内标注您的订单相关信息，以便我们第一时间给您处理您的退换货。</p>
                    </td>
                </tr>
            <#elseif exchange.exchangeStatusName == 'WAITDELIVERED'>
                <tr class="item">
                    <td>
                        <h2>商家已经收到退货，正在帮您发出新货</h2>
                        <p>・若长时间未有进度反馈，请与我们客服联系。</p>
                    </td>
                </tr>
            <#elseif exchange.exchangeStatusName == 'DELIVERED'>
                <tr class="item">
                    <td>
                        <h2>商家已经发出新货</h2>
                        <p>・若长时间未有进度反馈，请与我们客服联系。</p>
                    </td>
                </tr>
            <#elseif exchange.exchangeStatusName == 'SUCCESS'>
                <tr class="item">
                    <td>
                        <h2>用户已经收到换货商品</h2>
                        <p>・若有疑问或异议，请联系我们的客服。</p>
                    </td>
                </tr>
            <#elseif exchange.exchangeStatusName == 'CLOSE'||exchange.exchangeStatusName == 'REFUSE'>
                <tr class="item">
                    <td>
                        <h2>商家拒绝了退货申请</h2>
                        <p style="color:red">${exchangeLogs[0].info}</p>
                        <p>若有疑问或异议，请联系我们的客服。</p>
                    </td>
                </tr>
            </#if>
        </table>

        <table class="table table-grey" style="margin-top:10px;">
            <#list exchangeLogs as log>
                <tr class="item">
                    <td>${log.createDate?string("yyyy-MM-dd HH:mm")!""}</td>
                    <td>${(log.info)!}</td>
                </tr>
            </#list>
        </table>
    </div>
</div>