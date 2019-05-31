<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='我的积分' noheader=true  service='false' hastopfix='false'/>
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/home" class="back">
            <span class="icon icon-chevron-left">我的积分</span>
        </a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
</div>
<div style="height:2.9em;"></div>
<div class="wrap-main">
    <div class="wallet-top">
        <p>剩余积分</p>
        <p class="total">${(account.totalPoint)!}</p>
        <p class="grey">冻结 ${(account.freezePoint)!}</p>
    </div>
    <div class="user-nav">
        <ul>
            <li>
                <a href="/member/point/exchange"><span class="icon icon-signout"></span> 积分转钱包 <span
                            class="icon icon-angle-right"></span></a>
            </li>
            <li>
                <a href="/member/account/pointItem"><span class="icon icon-sort-by-attributes-alt"></span> 积分明细 <span
                            class="icon icon-angle-right"></span></a>
            </li>
        </ul>
    </div>
</div>
<hr/>
<@m.page_footer />
