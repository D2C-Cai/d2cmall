<#import "templates/public_pc.ftl" as m>
<@m.page_header title='申请退款' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="order"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>申请退货退款</h1>
        <#if (status &gt;0)>
            <div class="form n_form text-center">
                <div class="reship step1_box" style="width:868px;height:71px;text-indent:-9999px;margin:auto;">申请退货退款
                </div>
                <form action="/member/reship" class="validate-form" method="POST" call-back="myorder">
                    <input type="hidden" name="id" value="<#if reship?exists>${reship.id}</#if>"/>
                    <input type="hidden" name="productSkuSn" value="${reship.productSkuSn}"/>
                    <input type="hidden" name="orderSn" value="${reship.orderSn}"/>
                    <input type="hidden" name="orderId" value="${reship.orderId}"/>
                    <input type="hidden" name="orderItemId" value="${reship.orderItemId}"/>
                    <input type="hidden" name="quantity" value="${reship.quantity}"/>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label style="width:15%;display:inline-block;"><span class="red"
                                                                             style="font-size: 16px;vertical-align: top;">*</span>退货原因</label>
                        <select class="input" name="reshipReason" id="reship-reason" style="width:35%;">
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
                            <option value="not"<#if reship?exists && reship.reshipReason == 'not'> selected</#if>>
                                收到商品描述不符
                            </option>
                            <option value="quality"<#if reship?exists && reship.reshipReason == 'quality'> selected</#if>>
                                商品质量问题
                            </option>
                            <option value="noReason"<#if reship?exists && reship.reshipReason == 'noReason'> selected</#if>>
                                七天无理由退货
                            </option>
                        </select>
                        <div class="tip tip-validate" data-target="reship-reason"></div>
                    </div>
                    <div class="form-item form-item-vertical checkbox" style="text-align:left;margin-left:18%">
                        <label style="width:15%;display:inline-block;"><span class="red"
                                                                             style="font-size: 16px;vertical-align: top;">*</span>是否收货</label>
                        <#if orderItem.status=='SIGNED'>
                            <input type="radio" name="received" id="ss"
                                   value="true"<#if orderItem.status=='SIGNED'> checked</#if> style="display:none"/> 已收货
                            <label for="ss"></label>
                        <#else>
                            <input type="radio" name="received" id="ss"
                                   value="true"<#if orderItem.status=='SIGNED'> checked</#if>
                                   style="display:none"/> 已收货 &nbsp;&nbsp;
                            <label for="ss"></label>
                            <input type="radio" name="received" id="nn"
                                   value="false"<#if orderItem.status!='SIGNED'> checked</#if>style="display:none"/> 未收货
                            <label for="nn"></label>
                        </#if>
                    </div>
                    <#if reship.orderPayType==3>
                        <div class="refund-account<#if orderItem.status!='SIGNED'> display-none</#if>"
                             style="padding:10px;color:#FD555D;display:none;margin-left:-35%"><i
                                    class="fa fa-info-circle"></i>该笔交易退款无法原路返回，退款需要汇到您的支付宝账户
                        </div>
                        <div class="form-item item-flex refund-account<#if orderItem.status!='SIGNED'> display-none</#if>"
                             style="text-align:left;margin-left:18%">
                            <label style="width:15%;display:inline-block;margin-right:0;"><span class="red"
                                                                                                style="font-size: 16px;vertical-align: top;">*</span>支付宝账号</label>
                            <input class="input" name="refund.backAccountSn" id="back-account"
                                   value="${(reship.refund.backAccountSn)!}" placeholder="目前退款仅支持退到支付宝账号" size="40"
                                   style="width:35%;"/>
                            <div class="tip<#if orderItem.status=='SIGNED'>tip-validate</#if>"
                                 data-target="back-account"></div>
                        </div>
                        <div class="form-item item-flex refund-account<#if orderItem.status!='SIGNED'> display-none</#if>"
                             style="text-align:left;margin-left:18%;">
                            <label style="width:15%;display:inline-block;margin-right:0;"><span class="red"
                                                                                                style="font-size: 16px;vertical-align: top;">*</span>支付宝(实名)</label>
                            <input class="input" name="refund.backAccountName" id="backaccount-name"
                                   value="${(reship.refund.backAccountName)!}" placeholder="支付宝实名认证" size="40"
                                   style="width:35%;"/>
                            <div class="tip<#if orderItem.status=='SIGNED'>tip-validate</#if>"
                                 data-target="backaccount-name"></div>
                        </div>
                    <#else>
                        <div style="padding:10px;color:#FD555D;display:none"><i
                                    class="fa fa-info-circle"></i>该笔交易退款原路返回${(reship.orderPayTypeName)!}</div>
                    </#if>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label style="width:15%;display:inline-block;"><span class="red"
                                                                             style="font-size: 16px;vertical-align: top;">*</span>退货金额</label>
                        <input type="text" class="input" name="refund.applyAmount" id="total-amount"
                               data-limit="${orderItem.actualAmount}" style="width:100px"
                               value="${reship.applyAmount}"/> 元 (可退 ${orderItem.actualAmount} 元)
                        <div class="tip tip-validate" data-target="total-amount" data-function="checkAmount"></div>
                    </div>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label style="width:15%;display:inline-block;"><span></span>退货说明</label>
                        <textarea class="input" style="height:60px;width:35%;"
                                  name="memo"><#if reship?exists>${(reship.memo)!}</#if></textarea>
                    </div>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label style="width:15%;display:inline-block;"><span></span>上传凭证</label>
                        <a href="javascript:void(0)" style="display:inline-block">
                            <label class="button button-gray text-upload" style="cursor:pointer;">浏览上传</label>
                            <input type="file" class="upload-file" name="file" data-upload-url="/picture/upload"
                                   accept="image/gif,image/png,image/jpeg" style="display:none"/>
                        </a>

                        <div class="upload-preview<#if reship?exists && reship.evidences!=null> display-block<#else> display-none</#if>">
                            <#if reship?exists && reship.evidences!=null>
                                <#list reship.evidenceList as evidence>
                                    <div class="upload-img">
                                        <a href="${picture_base}/${evidence}" class="img" target="_blank"><img
                                                    src="${picture_base}/${evidence}" alt=""></a>
                                        <a href="javascript:" class="photo-del" title="删除" evidence-id="${evidence.id}">删除</a>
                                    </div>
                                </#list>
                            </#if>
                        </div>
                    </div>

                    <div class="form-button" style="margin-bottom:20px">
                        <button type="submit" class="button button-red" style="margin-left:-10%">确定</button>
                    </div>
                </form>
            </div>
        <#else>
            <div style="padding:160px 180px;">
                <div style="font-size:22px;"><strong>很遗憾，申请售后入口已经关闭!</strong></div>
                <p style="font-size:14px;">亲，您已错过了申请售后的时间段（15天以内），如商品有质量问题，请与客服联系。<br/>联系电话：${servertel}</p>
            </div>
        </#if>

    </div>
</div>
<script>
    <#if reship.orderPayType==3>
    $('input[name=received]').click(function () {
        if ($(this).val() == 'false') {
            $('.refund-account').hide();
            $('.tip[data-target="back-account"]').removeClass('tip-validate');
            $('.tip[data-target="backaccount-name"]').removeClass('tip-validate');
        } else {
            $('.refund-account').show();
            $('.tip[data-target="back-account"]').addClass('tip-validate');
            $('.tip[data-target="backaccount-name"]').addClass('tip-validate');
        }
        $.utilBaseModal.positionUpdate('pop');
    });
    </#if>
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