<#import "templates/public_pc.ftl" as m>
<@m.page_header title='会员充值' js='utils/jquery.datepicker' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="o2o_recharge"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 会员充值</h1>
        <div class="form form-small">
            <#if rules?exists && rules?size gt 0>
                <div class="tip tip-warning"><i class="fa fa-info-circle"></i>
                    <#list rules as rule>
                        <#if !rule.over>
                            <p>${(rule.name)!}</p>
                        </#if>
                    </#list>
                </div>
            </#if>
            <form method="post" confirm="确定要充值吗?请务必保证金额正确！" class="validate-form" action="/o2opay/recharge">
                <div class="form-item form-item-vertical">
                    <label>充值时间</label>
                    <input type="text" name="payDate" id="pay-date" value="${(.now?string("yyyy-MM-dd"))!}"
                           class="input input-l">
                    <div class="tip tip-validate" data-target="pay-date"></div>
                </div>
                <div class="form-item form-item-vertical">
                    <label>会员账号</label>
                    <input type="text" name="accountSn" id="account-sn" value="" placeholder="请输入会员的登录账号"
                           class="input input-l">
                    <div class="tip tip-validate" data-target="account-sn"></div>
                    <div id="account-info">
                    </div>
                </div>
                <div class="form-item form-item-vertical">
                    <label>充值方式</label>
                    <select id="pay-channel" name="payChannel" class="input input-l" style="width:100%">
                        <option value="CASH"<#if RequestParameters.paymentType=='CASH'> selected</#if>>现金</option>
                        <option value="ALIPAY"<#if RequestParameters.paymentType=='ALIPAY'> selected</#if>>支付宝</option>
                        <option value="UNIONPAY"<#if RequestParameters.paymentType=='UNIONPAY'> selected</#if>>银联支付
                        </option>
                    </select>
                    <div class="tip tip-validate" data-target="pay-channel"></div>
                </div>
                <div class="form-item form-item-vertical online-pay display-none">
                    <label>支付账号</label>
                    <input type="text" id="pay-account" name="payAccount" value="" placeholder="充值时使用的支付宝账号或银行卡号等"
                           class="input input-l">
                </div>
                <div class="form-item form-item-vertical online-pay display-none">
                    <label>支付流水号</label>
                    <input type="text" id="pay-sn" name="paySn" value="" placeholder="通过支付宝或银行支付时的流水单号"
                           class="input input-l">
                </div>
                <div class="form-item form-item-vertical">
                    <label>充值金额</label>
                    <input type="text" id="recharge-amount" name="rechargeAmount" maxlength="6" value=""
                           placeholder="输入正整数" class="input input-l">
                    <div id="amount-chinese" style="font:normal 30px 'Microsoft Yahei';padding-top:5px;"></div>
                    <div class="tip tip-validate" data-target="recharge-amount"></div>
                </div>
                <div class="form-item form-item-vertical">
                    <label>备注说明</label>
                    <textarea id="memo" name="memo" value="" class="input input-l"
                              style="width:100%;height:60px;"></textarea>
                </div>
                <div class="form-button">
                    <button type="submit" class="button">确定</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script id="account-info-template" type="text/html">
    总金额：{{availableTotalAmount}} 元 <span class="grey">︱</span>
    可用金额：{{availableCashAmount}} 元 <span class="grey">︱</span>
    可用红包：{{availableGiftAmount}}
</script>
<script>
    $('#pay-channel').change(function () {
        if ($(this).val() == 'CASH') {
            $('.online-pay').hide();
        } else {
            $('.online-pay').show();
        }
    });
    $('#recharge-amount').utilSetNumber();

    $('#recharge-amount').keyup(function () {
        $('#amount-chinese').html($.fn.utilTransAmountToCN($(this).val()));
        $('#amount-chinese').siblings('.tip-validate').hide();
    });
    $('input[name=payDate]').datePicker({
        format: 'Y-m-d',
        date: $('input[name=payDate]').val(),
        current: $('input[name=payDate]').val(),
        starts: 1,
        position: 'r',
        onBeforeShow: function () {
            $('input[name=payDate]').datePickerSetDate($('input[name=payDate]').val(), true);
        },
        onChange: function (formated, dates) {
            $('input[name=payDate]').val(formated);
            $('input[name=payDate]').datePickerHide();
        }
    });
</script>
<@m.page_footer />