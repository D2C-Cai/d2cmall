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
                <th width="10%">保证金额</th>
                <th width="18%">时间</th>
                <th width="16%">状态</th>
                <th width="18%">物流信息</th>
                <th width="10%">操作</th>
            </tr>
            <#if pager.list?exists && pager.list?size &gt; 0>
                <#list pager.list as list>
                    <tr class="item" style="height:46px">
                        <td><a href="/auction/product/${list.auctionId}" style="color:#fd555d">${list.auctionTitle}</a>
                        </td>
                        <td><strong>&yen;${list.margin?string("currency")?substring(1)}</strong></td>
                        <td>${list.createDate?string("HH:mm:ss MM/dd")}</td>
                        <td <#if list.status==2 || list.status==8|| list.status==6|| list.status==10>style="color:#fd555d"</#if>>${list.statuText}</td>
                        <td><#if list.status==8 || list.status == 10><span class="display-block"><a
                                        href="//www.kuaidi100.com/chaxun?com=${list.deliveryCorpCode}&nu=${list.deliverySn}"
                                        class="strong blue" target="_blank">物流查询</a></span></#if></td>
                        <td><#if list.status==8>
                                <button type="button" data-url="/auction/received/${list.id}"
                                        class="button button-s button-red  ajax-request" method-type="post"
                                        confirm="是否确认收货？">确认收货</button></#if></td>
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