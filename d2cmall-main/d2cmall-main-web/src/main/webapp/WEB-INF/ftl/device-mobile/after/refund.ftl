<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="申请退款" title='申请退款' button='false' service='false' />
<div class="section">
    <#if  (status &gt;0)>
        <div class="tips tip-green">
            <p>申请条件：请与商家达成一致不退货仅退款。</p>
            <p>退款流程：1.申请退款 〉2. 卖家同意退款申请 〉3.退款成功</p>
        </div>
        <div class="form">
            <form action="/member/refund" class="validate-form" redirect-url="/member/order/${refund.orderSn}"
                  method="POST">
                <input type="hidden" name="id" value="<#if refund?exists>${refund.id}</#if>"/>
                <input type="hidden" name="productSkuSn" value="${refund.productSkuSn}"/>
                <input type="hidden" name="orderSn" value="${refund.orderSn}"/>
                <input type="hidden" name="orderItemId" value="${refund.orderItemId}"/>
                <input type="hidden" name="quantity" value="${refund.quantity}"/>
                <input type="hidden" name="refundStatus" value="1"/>
                <div class="form-item item-flex">
                    <label>退款原因</label>
                    <select name="refundReason" id="refund-reason" class="input validate" title="退款原因">
                        <option value="">请选择退款原因</option>
                        <option value="wrong"<#if refund?exists && refund.refundReason == 'wrong'> selected</#if>>拍错了
                        </option>
                        <option value="no"<#if refund?exists && refund.refundReason == 'no'> selected</#if>>不想要了
                        </option>
                        <option value="stock"<#if refund?exists && refund.refundReason == 'stock'> selected</#if>>商品缺货
                        </option>
                        <option value="error"<#if refund?exists && refund.refundReason == 'error'> selected</#if>>
                            订单信息错误
                        </option>
                        <option value="consensus"<#if refund?exists && refund.refundReason == 'consensus'> selected</#if>>
                            协商一致退款
                        </option>
                    </select>
                </div>
                <#if refund.orderPayType==3>
                    <div style="padding:1em 0.8em 0.8em;color:#FD555D;font-size:0.8em;">该笔退款交易无法原路返回，退款需要汇到您的支付宝账户</div>
                    <div class="form-item item-flex">
                        <label>支付宝账号</label>
                        <input type="text" name="backAccountSn" value="${refund.backAccountSn}" class="input validate"
                               placeholder="退款仅支持退到支付宝账号" title="支付宝账号"/>
                    </div>
                    <div class="form-item item-flex">
                        <label>支付宝名称</label>
                        <input type="text" name="backAccountName" value="${refund.backAccountName}"
                               class="input validate" placeholder="支付宝实名认证" title="真实姓名"/>
                    </div>
                <#else>
                    <div style="padding:1em 0.8em 0.8em;color:#FD555D;font-size:0.8em;">
                        该笔退款原路返回您的${(refund.orderPayTypeName)!}</div>
                </#if>
                <div class="form-item item-flex">
                    <label>退款金额</label>
                    <input type="text" class="input money validate" title="退款金额" name="applyAmount" id="total-amount"
                           data-limit="${orderItem.actualAmount}" data-function="checkAmount" style="width:3em;"
                           value="${(refund.applyAmount)!}" placeholder=""/> 元(可退 ${orderItem.actualAmount} 元)
                </div>

                <div class="form-item item-flex">
                    <label>退款说明</label>
                    <input type="text" class="input" placeholder="备注及说明" name="memo" value="${(refund.memo)!}"/>
                </div>

                <div class="form-item item-flex">
                    <label>上传凭证</label>
                    <#if refund?exists && refund.evidences!=null>
                        <#list refund.evidenceList as evidence>
                            <div class="upload-item" id="${evidence.id}">
                                <em class="icon icon-close"></em>
                                <span><img src="${picture_base}/${evidence}" alt=""></span>
                                <input type="hidden" name="path" class="path-input" value="${evidence.val}">
                            </div>
                        </#list>
                    </#if>
                    <div class="upload-item" style="overflow:hidden">
                        <span>＋</span>
                        <input type="file" class="upload-file" name="file" data-upload-url="/picture/upload"
                               accept="image/gif,image/png,image/jpeg"/>
                    </div>
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
    var checkAmount = function () {
        var val = parseFloat($('#total-amount').val());
        var limit = parseFloat($('#total-amount').attr('data-limit'));
        if (isNaN(val) || val <= 0) {
            return '请填写正确的金额。';
        }
        if (val > limit) {
            return '退款金额不能大于' + limit + '元';
        }
        return true;
    }
</script>
<@m.page_footer />