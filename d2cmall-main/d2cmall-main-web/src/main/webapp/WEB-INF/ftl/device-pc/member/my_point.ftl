<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的积分'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray clearfix">
        <@m.page_menu menu_item="credit"/>
        <div class="my-cont">
            <h1>我的积分</h1>
            <div class="wallet-wrap clearfix">
                <div class="wallet-account">
                    <p>积分余额</p>
                    <p class="total" id="credit">${(account.totalPoint)!}</p>
                    <p class="grey">
                        冻结 <strong>${(account.freezePoint)!}</strong>
                    </p>
                </div>
                <div class="wallet-op">
                    <p>
                        <a href="javascript:" data-url="/member/account/pointItem">积分明细</a>
                        <a href="javascript:" data-url="/member/point/exchange">积分转钱包</a>
                    </p>
                </div>
            </div>
            <div class="wallet-cont">

            </div>
        </div>
    </div>
</div>
<script>
    $('.wallet-op a').click(function () {
        var url = $(this).attr('data-url');
        $.get(url, function (data) {
            $('.wallet-cont').html(data);
        });
        return false;
    });
</script>
<@m.page_footer />