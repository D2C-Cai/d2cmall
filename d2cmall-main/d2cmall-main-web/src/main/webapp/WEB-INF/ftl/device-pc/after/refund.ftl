<#import "templates/public_pc.ftl" as m>
<@m.page_header title='申请退款' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="order"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>申请退款</h1>
        <#if  (status &gt;0)>
            <div class="form-info n_form text-center">
                <div class="refund step1_box" style="width:667px;height:52px;text-indent:-9999px;margin:auto;">申请退款
                </div>
                <form action="/member/refund" class="validate-form" method="POST" call-back="myorder">
                    <input type="hidden" name="id" value="<#if refund?exists>${refund.id}</#if>"/>
                    <input type="hidden" name="productSkuSn" value="${refund.productSkuSn}"/>
                    <input type="hidden" name="orderId" value="${refund.orderId}"/>
                    <input type="hidden" name="orderSn" value="${refund.orderSn}"/>
                    <input type="hidden" name="orderItemId" value="${refund.orderItemId}"/>
                    <input type="hidden" name="quantity" value="${refund.quantity}"/>
                    <input type="hidden" name="refundStatus" value="1"/>
                    <div class="form-item  form-item-vertical" style="text-align:left;margin-left:18%">
                        <label><span class="red" style="font-size: 16px;vertical-align: middle;">*</span>退款原因</label>
                        <select class="input" name="refundReason" id="refund-reason" style="width:35%">
                            <option value="">请选择退款原因</option>
                            <option value="wrong"<#if refund?exists && refund.refundReason == 'wrong'> selected</#if>>
                                拍错了
                            </option>
                            <option value="no"<#if refund?exists && refund.refundReason == 'no'> selected</#if>>不想要了
                            </option>
                            <option value="stock"<#if refund?exists && refund.refundReason == 'stock'> selected</#if>>
                                商品缺货
                            </option>
                            <option value="error"<#if refund?exists && refund.refundReason == 'error'> selected</#if>>
                                订单信息错误
                            </option>
                            <option value="consensus"<#if refund?exists && refund.refundReason == 'consensus'> selected</#if>>
                                协商一致退款
                            </option>
                        </select>
                        <div class="tip tip-validate" data-target="refund-reason"></div>
                    </div>
                    <#if refund.orderPayType==3>
                        <div style="padding:10px;color:#FD555D;display:none"><i class="fa fa-info-circle"></i>该笔交易退款无法原路返回，退款需要汇到您的支付宝账户
                        </div>
                        <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                            <label><span class="red"
                                         style="font-size: 16px;vertical-align: middle;">*</span>支付宝账号</label>
                            <input class="input" name="backAccountSn" id="back-account"
                                   value="${(refund.backAccountSn)!}" placeholder="目前退款仅支持退到支付宝账号" size="40"
                                   style="width:35%"/>
                            <div class="tip tip-validate" data-target="back-account"></div>
                        </div>
                        <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                            <label><span class="red"
                                         style="font-size: 16px;vertical-align: middle;">*</span>支付宝(实名)</label>
                            <input class="input" name="backAccountName" id="backaccount-name"
                                   value="${(refund.backAccountName)!}" placeholder="支付宝实名认证" size="40"
                                   style="width:35%"/>
                            <div class="tip tip-validate" data-target="backaccount-name"></div>
                        </div>
                    <#else>
                        <div style="padding:10px;color:#FD555D;text-align:left;margin-left:18%"><i
                                    class="fa fa-info-circle"></i>该笔交易退款原路返回${(refund.orderPayTypeName)!}</div>
                    </#if>

                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label><span class="red" style="font-size: 16px;vertical-align: middle;">*</span>退款金额</label>
                        <input type="text" class="input" name="applyAmount" id="total-amount"
                               data-limit="${orderItem.actualAmount}" style="width:100px"
                               value="${orderItem.actualAmount}"/> 元(可退 ${orderItem.actualAmount} 元)
                        <div class="tip tip-validate" data-target="total-amount" data-function="checkAmount"></div>
                    </div>

                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label><span></span>退款说明</label>
                        <textarea class="input" style="height:60px;width:35%"
                                  name="memo"><#if refund?exists>${refund.memo}</#if></textarea>
                    </div>

                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label><span></span>上传凭证</label>
                        <a href="javascript:void(0)" style="display:inline-block">
                            <label class="button button-gray text-upload" style="cursor:pointer;">浏览上传</label>
                            <input type="file" class="upload-file" name="file" data-upload-url="/picture/upload"
                                   accept="image/gif,image/png,image/jpeg" style="display:none"/>
                        </a>
                        <div class="upload-preview<#if refund?exists && refund.evidences!=null> display-block<#else> display-none</#if>">
                            <#if refund?exists && refund.evidences!=null>
                                <#list refund.evidenceList as evidence>
                                    <div class="upload-img">
                                        <a href="${picture_base}/${evidence}" class="img" target="_blank"><img
                                                    src="${picture_base}/${evidence}" alt=""></a>
                                    </div>
                                </#list>
                            </#if>
                        </div>
                    </div>

                    <div class="form-button">
                        <button type="submit" class="button button-red">确定</button>
                    </div>
                </form>
            </div>
        <#elseif isExpired==true>
            <div style="padding:160px 180px;">
                <div style="font-size:22px;"><strong>很遗憾，申请售后入口已经关闭!</strong></div>
                <p style="font-size:14px;">亲，您已错过了申请售后的时间段（15天以内），如商品有质量问题，请与客服联系。<br/>联系电话：${servertel}</p>
            </div>
        </#if>
    </div>
</div>
<script>
    $('#total-amount').utilSetNumber();
    var checkAmount = function () {
        var val = parseFloat($('#total-amount').val());
        var limit = parseFloat($('#total-amount').attr('data-limit'));
        if (isNaN(val)) {
            return '请填写正确的金额。';
        }
        if (val > limit) {
            return '金额不能大于' + limit + '元';
        }
        return true;
    }
    var myorder = function () {
        setTimeout(function () {
            location.href = "/member/order"
        }, "2000");
    }

</script>
<@m.page_footer js='modules/page.user' />