<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="申请换货" title='申请换货' button='false' service='false'/>
<div class="section">
    <#if (status&gt;0)>
        <div class="tips tip-green">
            <p>申请条件：<br/>先与商家达成一致，换货后请保留物流底单（闲置商品和未加入消保商品仅支持未收到货的退款）</p>
            <p>换货流程：<br/>1.申请换货 > 2.商家审核申请 > 3.买家换货并填写换货物流信息 > 4.商家确认收货并换货发出</p>
        </div>
        <div class="form">
            <form action="/member/exchange" class="validate-form" redirect-url="/member/order/${exchange.orderSn}"
                  method="POST">
                <input type="hidden" name="id" value="<#if exchange?exists>${exchange.id}</#if>"/>
                <input type="hidden" name="orderSn" value="${exchange.orderSn}"/>
                <input type="hidden" name="orderId" value="${exchange.orderId}"/>
                <input type="hidden" name="orderItemId" value="${exchange.orderItemId}"/>
                <input type="hidden" name="quantity" value="${exchange.quantity}"/>
                <div class="form-item item-flex">
                    <label>换货原因</label>
                    <select class="input validate" name="exchangeReason" id="exchange-reason" title="换货原因">
                        <option value="">请选择换货原因</option>
                        <option value="repair"<#if exchange?exists && exchange.exchangeReason == 'repair'> selected</#if>>
                            商品需要维修
                        </option>
                        <option value="damaged"<#if exchange?exists && exchange.exchangeReason == 'damaged'> selected</#if>>
                            收到商品破损
                        </option>
                        <option value="wrong"<#if exchange?exists && exchange.exchangeReason == 'wrong'> selected</#if>>
                            商品错发/漏发
                        </option>
                        <option value="not"<#if exchange?exists && exchange.exchangeReason == 'not'> selected</#if>>
                            收到商品描述不符
                        </option>
                        <option value="quality"<#if exchange?exists && exchange.exchangeReason == 'quality'> selected</#if>>
                            商品质量问题
                        </option>
                        <option value="noReason"<#if exchange?exists && exchange.exchangeReason == 'noReason'> selected</#if>>
                            七天无理由换货
                        </option>
                    </select>
                </div>
                <div class="form-item item-flex">
                    <label>换货说明</label>
                    <input type="text" title="换货说明" class="input validate" placeholder="备注及说明" name="memo"
                           value="${(exchange.memo)!}"/>
                </div>
                <div class="form-button">
                    <button type="submit" class="button button-l button-red">确定</button>
                </div>
            </form>
        </div>
    <#else>
        <div class="text-center padding" style="line-height:250%;padding-top:3em;">
            <div class="icon icon-excam"></div>
            <div class="red"><strong>很遗憾，申请售后入口已经关闭!</strong></div>
            <div>亲，您已错过了申请售后的时间段（15天以内），如商品有质量问题，请与客服联系。</div>
            <div>联系电话：${servertel}</div>
        </div>
    </#if>
</div>
<script>
</script>
<@m.page_footer />