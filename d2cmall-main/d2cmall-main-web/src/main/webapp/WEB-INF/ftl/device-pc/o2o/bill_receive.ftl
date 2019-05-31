<#import "templates/public_pc.ftl" as m>
<@m.page_header title='门店收款' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="o2o_receive"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 门店收款</h1>
        <div class="form form-small">
            <div class="tip tip-warning"><i class="fa fa-info-circle"></i> 门店收款只能从会员的钱包中扣除相应的金额。</div>
            <form method="post" success-tip="收款成功！请转入“收款管理”中再次确认。" confirm="确定要收取该客户款项吗?请务必保证金额和单号正确！"
                  redirect-url="/o2opay/bill" class="validate-form" action="/o2opay/receive">
                <div class="form-item form-item-vertical">
                    <label>会员账号</label>
                    <input type="text" name="accountSn" id="account-sn" value="" placeholder="请输入会员的登录账号"
                           class="input input-l">
                    <div class="tip tip-validate" data-target="account-sn"></div>
                    <div id="account-info">
                    </div>
                </div>
                <div class="form-item form-item-vertical">
                    <label>收款金额</label>
                    <input type="text" id="receive-amount" name="receiveAmount" maxlength="6" value=""
                           placeholder="输入正整数" class="input input-l">
                    <div id="amount-chinese" style="font:normal 30px 'Microsoft Yahei';padding-top:5px;"></div>
                    <div class="tip tip-validate" data-target="receive-amount"></div>
                </div>
                <div class="form-item form-item-vertical">
                    <label>零售单号</label>
                    <input type="text" id="retail-sn" name="retailSn" value="" placeholder="零售单号请务必填写正确"
                           class="input input-l">
                    <div class="tip tip-validate" data-target="retail-sn"></div>
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
    $('#recharge-amount').utilSetNumber();

    $('#receive-amount').keyup(function () {
        $('#amount-chinese').html($.fn.utilTransAmountToCN($(this).val()));
        $('#amount-chinese').siblings('.tip-validate').hide();
    });
    $('#account-sn').blur(function () {
        var account_sn = $(this).val();
        $('#account-info').siblings('.tip-validate').hide();
        if (account_sn) {
            $.ajax({
                url: "/o2opay/ajax/account",
                type: "get",
                data: {accountSn: account_sn},
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        var html = template('account-info-template', data.result.data.account);
                        $('#account-info').html(html);
                        $('button[type=submit]').removeAttr('disabled');
                    } else {
                        $('#account-info').html(data.result.message);
                        $('button[type=submit]').attr('disabled', true);
                    }
                }
            });
        } else {
            $('#account-info').html('');
        }
        return false;
    });
</script>
<@m.page_footer />

