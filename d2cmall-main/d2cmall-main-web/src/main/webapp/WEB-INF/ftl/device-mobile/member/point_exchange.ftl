<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='积分转钱包' noheader=true service='false'/>
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/point/info" class="back">
            <span class="icon icon-chevron-left">积分转钱包</span>
        </a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
</div>
<div style="height:2.9em;"></div>
<div class="wallet-top clearfix">
    <div style="float:left;width:50%;border-right:1px solid #CCC;margin-right:-1px;">
        <p>目前积分</p>
        <p class="total" id="credit">${(result.data.account.totalPoint)!}</p>
    </div>
    <div style="float:left;width:50%;">
        <p>钱包余额</p>
        <p class="total" id="money">${(result.data.account.totalAmount)!}</p>
    </div>
</div>
<div class="wrap-main">
    <div class="trans-form">
        <form id="pointExchange" action="/member/point/exchange" method="post">
            <div>
                <label>输入你要转换的积分数</label>
                <input type="number" name="exchangePoint" id="trans-credit" value="0"/>
            </div>
            <div style="font-size:30px;line-height:70px;">
                <span class="icon icon-random icon-rotate-90"></span>
            </div>
            <div class="trans-money" id="trans-money">
                0.00
            </div>
            <div style="padding-top:20px;">
                <button type="submit" name="ok" class="button-op" style="width:100%">确定</button>
            </div>
        </form>
    </div>
</div>
<hr/>
<script>
    var credit = parseInt($('#credit').text());
    var money = parseFloat($('#money').text());
    $('#trans-credit').change(function () {
        var trans_credit = parseInt($(this).val());
        if (isNaN(trans_credit)) trans_credit = 0;
        if (trans_credit <= credit && trans_credit > 0) {
            $('#trans-money').html(format_currency(trans_credit / 10));
        } else {
            $('#trans-money').html('<span style="color:#FF3300">ERROR</span>');
            $('#credit').text(credit);
            $('#money').html(format_currency(money));
        }
        return false;
    });
    $('#pointExchange').submit(function () {
        var obj = $('#trans-money-msg');
        var trans_credit = parseInt($('#trans-credit').val());
        if (obj.val() != null || trans_credit <= 0) {
            return false;
        }
        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: $(this).serialize(),
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                    jAlert('积分兑换成功！', function () {
                        location.reload();
                        $('#credit').text(credit - trans_credit);
                        $('#money').html(format_currency(money + trans_credit / 10));
                    });
                } else {
                    window.parent.jAlert(data.result.message);
                }
            }
        });
        return false;
    });
</script>
<@m.page_footer />
