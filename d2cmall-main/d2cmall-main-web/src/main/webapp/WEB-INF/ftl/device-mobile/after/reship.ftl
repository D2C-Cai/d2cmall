<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="申请退货退款" title='申请退货退款' button='false' service='false'/>
<div class="section">
    <#if (status&gt;0)>
        <div class="tips tip-green">
            <p>申请条件：<br/>先与商家达成一致，退货后请保留物流底单（闲置商品和未加入消保商品仅支持未收到货的退款）</p>
            <p>退款流程：<br/>1.申请退货 〉2.商家发送退货地址给买家 〉3.买家退货并填写退货物流信息 〉4.商家确认收货，退款成功</p>
        </div>
        <div class="form">
            <form action="/member/reship" class="validate-form" redirect-url="/member/order/${reship.orderSn}"
                  method="POST">
                <input type="hidden" name="id" value="<#if reship?exists>${reship.id}</#if>"/>
                <input type="hidden" name="productSkuSn" value="${reship.productSkuSn}"/>
                <input type="hidden" name="orderSn" value="${reship.orderSn}"/>
                <input type="hidden" name="orderItemId" value="${reship.orderItemId}"/>
                <input type="hidden" name="quantity" value="${reship.productQuantity}"/>
                <div class="form-item item-flex">
                    <label>退货原因</label>
                    <select class="input validate" name="reshipReason" id="reship-reason" title="退货原因">
                        <option value="">请选择退货原因</option>
                        <option value="repair"<#if reship?exists && reship.reshipReason == 'repair'> selected</#if>>
                            商品需要维修
                        </option>
                        <option value="damaged"<#if reship?exists && reship.reshipReason == 'damaged'> selected</#if>>
                            收到商品破损
                        </option>
                        <option value="wrong"<#if reship?exists && reship.reshipReason == 'wrong'> selected</#if>>
                            商品错发/漏发
                        </option>
                        <option value="not"<#if reship?exists && reship.reshipReason == 'not'> selected</#if>>收到商品描述不符
                        </option>
                        <option value="quality"<#if reship?exists && reship.reshipReason == 'quality'> selected</#if>>
                            商品质量问题
                        </option>
                        <option value="noReason"<#if reship?exists && reship.reshipReason == 'noReason'> selected</#if>>
                            七天无理由退货
                        </option>
                    </select>
                </div>
                <div class="form-item item-flex">
                    <label>是否收货</label>
                    <#if orderItem.status=='SIGNED'>
                        <input type="radio" name="received" id="rec"
                               value="true" <#if orderItem.status=='SIGNED'> checked</#if>/><label for="rec">已收货</label>
                    <#else>
                        <input type="radio" name="received" id="rec-true"
                               value="true" <#if orderItem.status=='SIGNED'> checked</#if>/><label
                            for="rec-true">已收货</label> &nbsp;&nbsp;
                        <input type="radio" name="received" id="rec-false"
                               value="false" <#if orderItem.status!='SIGNED'> checked</#if>/><label
                            for="rec-false">未收货</label>
                    </#if>
                </div>
                <#if reship.orderPayType==3>
                    <div class="refund-account"
                         style="padding:1em 0.8em 0.8em;color:#FD555D;font-size:0.8em;"><#if orderItem.status!='SIGNED'>该笔交易退款无法原路返回，退款需要汇到您的支付宝账户<#else>该笔交易将原路退回到您的账户</#if></div>
                    <div class="form-item item-flex refund-account<#if orderItem.status!='SIGNED'> display-none</#if>">
                        <label>支付宝账号</label>
                        <input type="text" name="refund.backAccountSn" id="account-sn"
                               value="${(reship.refund.backAccountSn)!}"
                               class="input<#if orderItem.status=='SIGNED'> validate</#if>" placeholder="退款仅支持退到支付宝账号"
                               title="支付宝账号"/>
                    </div>
                    <div class="form-item item-flex refund-account<#if orderItem.status!='SIGNED'> display-none</#if>">
                        <label>支付宝名称</label>
                        <input type="text" name="refund.backAccountName" id="account-name"
                               value="${(reship.refund.backAccountName)!}"
                               class="input<#if orderItem.status=='SIGNED'> validate</#if>" placeholder="支付宝实名认证"
                               title="真实姓名"/>
                    </div>
                <#else>
                    <div style="padding:1em 0.8em 0.8em;color:#FD555D;font-size:0.8em;">
                        该笔退款原路返回您的${(reship.orderPayTypeName)!}</div>
                </#if>
                <div class="form-item item-flex">
                    <label>退货金额</label>
                    <input type="text" class="input money validate" title="退款金额" name="refund.applyAmount"
                           id="total-amount" data-limit="${(orderItem.actualAmount)!}" data-function="checkAmount"
                           style="width:3em;" value="${(reship.refund.totalAmount)!}" placeholder=""/>
                    元(可退 ${orderItem.actualAmount} 元)
                </div>

                <div class="form-item item-flex">
                    <label>退货说明</label>
                    <input type="text" class="input" placeholder="备注及说明" name="memo" value="${(reship.memo)!}"/>
                </div>

                <div class="form-item item-flex">
                    <label>上传凭证</label>
                    <#if reship?exists && reship.evidences!=null>
                        <#list reship.evidenceList as evidence>
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
    <#if reship.orderPayType==3>
    $('input[name=received]').click(function () {
        if ($(this).val() == 'false') {
            $('.refund-account').hide();
            $('#account-sn').removeClass('validate');
            $('#account-name').removeClass('validate');
        } else {
            $('.refund-account').show();
            $('#account-sn').addClass('validate');
            $('#account-name').addClass('validate');
        }
    });
    </#if>
    var checkAmount = function () {
        var val = parseFloat($('#total-amount').val());
        var limit = parseFloat($('#total-amount').attr('data-limit'));
        if (isNaN(val) || val <= 0) {
            return '请填写正确的金额。';
        }
        if (val > limit) {
            return '退货金额不能大于' + limit + '元';
        }
        return true;
    }
</script>
<@m.page_footer />