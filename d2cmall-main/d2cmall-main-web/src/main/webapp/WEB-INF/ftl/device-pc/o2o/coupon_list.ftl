<#import "templates/public_pc.ftl" as m>
<@m.page_header title='门店激活优惠券' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="o2o_storecoupon"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 门店激活优惠券</h1>
        <div class="form" style="width:500px;margin-bottom:20px;">
            <div class="form-title"><i class="fa fa-columns"></i> 优惠券码 <span>请输入优惠券码</span></div>
            <form method="post" success-tip="优惠券激活成功！" class="validate-form text-center" action="/coupon/usedCoupon">
                <div class="form-item">
                    <input type="text" name="code" id="code" size="30" class="input"/>
                    <button type="submit" name="convert" class="button button-purple">立即激活</button>
                    <div class="tip tip-validate" data-target="code" style="margin:0;"></div>
                </div>
            </form>
        </div>
        <#if pager.list?exists && pager.list?size gt 0>
            <div class="pages" style="margin:0">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
            <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table">
                <tr>
                    <th>门店</th>
                    <th>类型</th>
                    <th>金额（元）</th>
                    <th>时间</th>
                    <th>状态</th>
                    <th>使用说明</th>
                    <th>操作员</th>
                </tr>
                <#list pager.list as coupon>
                    <tr class="order-item text-center">
                        <td>${coupon.storeName}</td>
                        <td><#if coupon.type=="CASH">现金优惠券<#else>密码优惠券</#if></td>
                        <td>${coupon.amount}(满${coupon.needAmount}使用)</td>
                        <td>
                            <p>开始时间：${(coupon.claimedTime?string("yy/MM/dd HH:mm:ss"))!}</p>
                            <p>截止时间：${coupon.expireDate?string("yy/MM/dd HH:mm:ss")}</p>
                            <#if coupon.consumesTime?exists && (coupon.status=="USED"||coupon.status=="LOCKED")>
                                <p> 消费时间：${coupon.consumesTime?string("yy/MM/dd HH:mm:ss")}</p>
                            </#if>
                        </td>
                        <td>
                            <#if coupon.status=="CLAIMED" && coupon.isExpired==1> 已激活
                            <#elseif coupon.status=="LOCKED">已使用
                            <#elseif coupon.status=="USED">已使用
                            <#elseif coupon.status=="INVALID" || coupon.isExpired==0 >已过期
                            <#elseif coupon.status=="UNCLAIMED">未激活
                            </#if>
                        </td>
                        <td style="position:relative;">
                            <#if coupon.remark>${(coupon.remark)!}</#if>
                        </td>
                        <td>
                            ${(coupon.storeMemberName)!}
                        </td>
                    </tr>
                </#list>
            </table>
            <div class="pages">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount />
            </div>
        </#if>
    </div>
</div>
<script>

    $('#coupon-convert').submit(function () {
        if ($(this).find('input[name=code]').val() == '') {
            $(this).find('input[name=code]').focus();
            return false;
        }
        var thisobj = $(this);
        jConfirm('确定要立即兑换使用吗？', '', function (r) {
            if (r) {
                $.post(thisobj.attr('action'), thisobj.serialize(), function (data) {
                    if (data.result.success) {
                        location.reload();
                        $.flash_tips('优惠券兑换使用成功！', 'success');
                    } else {
                        jAlert('优惠券兑换失败！请仔细核优惠券上的优惠码！');
                    }
                }, 'json');
            }
        });
        return false;
    });
</script>
<@m.page_footer />