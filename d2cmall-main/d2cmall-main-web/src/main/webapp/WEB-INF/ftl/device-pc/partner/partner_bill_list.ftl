<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的返利' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="partner_bill"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 我的分销单 </h1>
        <form id="form-order" name="form_order" method="get" style="position:relative;">
        </form>
        <div class="clear">
            <div class="pages float-right" style="margin:0">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
        </div>
        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey">
            <thead>
            <tr>
                <th width="10%">昵称</th>
                <th width="10%">时间</th>
                <th width="10%">订单编号</th>
                <th width="10%">商品款号</th>
                <th width="10%">商品数量</th>
                <th width="10%">总金额</th>
                <th width="10%">返利比</th>
                <th width="7%">分成金额</th>
                <th width="12%">状态</th>
            </tr>
            </thead>
            <tbody>
            <#list pager.list as bill>
                <tr class="space">
                    <td colspan="6"></td>
                </tr>
                <tr class="title">
                    <td class="text-center nickname" style="line-height:250%;">${(bill.nickName)!}</td>
                    <td class="text-center"
                        style="line-height:250%;">${(bill.createDate?string("yyyy/MM/dd HH:mm:ss")!)!}</td>
                    <td class="text-center" style="line-height:250%;">${(bill.orderSn)!}</td>
                    <td class="text-center productSn" style="line-height:250%;">${(bill.productSn)!}</td>
                    <td class="text-center" style="line-height:250%;">${(bill.productQuantity)!}</td>
                    <td class="text-center" style="line-height:250%;">¥ ${(bill.actualAmount)!}</td>
                    <td class="text-center"
                        style="line-height:250%;"><#if bill.status==-1>0%<#else>${(bill.ratio)!}%</#if></td>
                    <td class="text-center"
                        style="line-height:250%;"><#if bill.status==-1>0<#else>¥ ${(bill.rebates)!}</#if></td>
                    <td class="text-center" style="line-height:250%;">
                        <#if bill.status==0>订单未完成</#if>
                        <#if bill.status==1>返利未结算</#if>
                        <#if bill.status==2>返利已结算<br/>
                            ${bill.balanceDate?string("yyyy/MM/dd HH:mm:ss")!}</#if>
                        <#if bill.status==-1>订单已关闭，不可结算</#if>
                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>

<script type="text/javascript">
    $('.nickname').each(function () {
        var nickname = $(this).text();
        if (!isNaN(nickname)) {
            if (/^1\d{10,12}$/g.test(nickname)) {
                var num = nickname.substr(0, 7) + '****';
                $(this).text(num);
            }
        }
    })
    $('.productSn').each(function () {
        var productSn = $(this).text();
        var num = productSn.length;
        var new_productSn = productSn.substr(0, num - 4) + '****';
        $(this).text(new_productSn);
    })

</script>
<@m.page_footer js='modules/page.user' />