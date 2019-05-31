<#import "templates/public_pc.ftl" as m>
<@m.page_header title='申请换货' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="order"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>申请换货</h1>
        <#if (status &gt;0)>
            <div class="form-info n_form text-center">
                <div class="exchange step1_box" style="width:868px;height:70px;text-indent:-9999px;margin:auto;">申请换货
                </div>
                <form action="/member/exchange" class="validate-form" method="POST" call-back="myorder">
                    <input type="hidden" name="id" value="<#if exchange?exists>${exchange.id}</#if>"/>
                    <input type="hidden" name="orderSn" value="${exchange.orderSn}"/>
                    <input type="hidden" name="orderId" value="${exchange.orderId}"/>
                    <input type="hidden" name="orderItemId" value="${exchange.orderItemId}"/>
                    <input type="hidden" name="quantity" value="${exchange.quantity}"/>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label><span class="red" style="font-size: 16px;vertical-align: middle;">*</span>换货原因</label>
                        <select class="input" name="exchangeReason" id="exchange-reason" style="width:35%">
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
                                七天无理由退货
                            </option>
                        </select>
                        <div class="tip tip-validate" data-target="exchange-reason"></div>
                    </div>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label><span class="red" style="font-size: 16px;vertical-align: middle;">*</span>换货说明</label>
                        <textarea class="input" style="height:80px;width:35%;" id="exchange-memo" name="memo"
                                  placeholder="请输入您要换的尺码和颜色"><#if exchange?exists>${(exchange.memo)!}</#if></textarea>
                        <div class="tip tip-validate" data-target="exchange-memo"></div>

                        <div class="red" style="display:inline-block;">请填写完整的换货信息（例如：颜色，尺码等）</div>
                    </div>

                    <div class="form-button">
                        <button type="submit" class="button button-red" style="margin-left:-120px">确定</button>
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