<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的拍卖' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="auction"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i>我的拍卖</h1>
        <div class="tab tab-user-menu clearfix">
            <ul>
                <li id="margin"><a href="/auction/member/mymargin">保证金记录 </a><i class="interval">|</i></li>
                <li id="offer"><a href="/auction/member/myoffer">出价记录 </a><i class="interval">|</i></li>
            </ul>
        </div>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>

        <table class="table table-lightgrey">
            <tr>
                <th width="28%">拍卖商品</th>
                <th width="28%">出价金额</th>
                <th width="28%">时间</th>
                <th width="16%">状态</th>
            </tr>
            <#if pager.list?exists && pager.list?size &gt; 0>
                <#list pager.list as list>
                    <tr class="item">
                        <td><a href="/auction/product/${list.auctionId}" style="color:#fd555d">${list.auctionTitle}</a>
                        </td>
                        <td><strong>&yen;${list.offer?string("currency")?substring(1)}</strong></td>
                        <td>${list.createDate?string("HH:mm:ss MM/dd")}</td>
                        <td <#if list.status==1 || list.status==8>style="color:#fd555d"</#if>><#if list.status==1>领先<#elseif list.status==0>出局<#elseif list.status==8>得标<#else>删除</#if></td>
                    </tr>
                </#list>
            </#if>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>
<script>
    var path = location.pathname;
    if (path.indexOf("/mymargin") >= 0) {
        $('#margin').addClass('on');
    } else if (path.indexOf("/myoffer") >= 0) {
        $('#offer').addClass('on');
    }
</script>
<@m.page_footer />