<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的抽奖' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="award"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i>我的抽奖</h1>
        <div class="tab tab-user-menu clearfix">
            <ul>
                <li<#if status==0> class="on"</#if>><a href="/award/my/list?status=0">全部 </a><i class="interval">|</i>
                </li>
                <li<#if status==1> class="on"</#if>><a href="/award/my/list?status=1">进行中 </a><i class="interval">|</i>
                </li>
                <li<#if status==2> class="on"</#if>><a href="/award/my/list?status=2">已揭晓 </a><i class="interval">|</i>
                </li>
            </ul>
        </div>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>

        <table class="table table-lightgrey">
            <tr>
                <th>订单号/幸运号</th>
                <th>抽奖时间</th>
                <th>名称</th>
                <th>奖品</th>
                <#-- <th>金额</th>-->
                <th>活动时间</h>
                <th>状态</th>
                <#-- <th width="10%">操作</th> -->
            </tr>
            <#if pager.list?exists && pager.list?size &gt; 0>
                <#list pager.list as list>
                    <tr class="item text-center">
                        <td class="border-r-none">
                            ${list.orderSn}<#if list.type='ZERO'>/<span style="color:blue">${list.luckyNum}</span></#if>
                        </td>
                        <td>${list.createDate?string('yy/MM/dd HH:mm:ss')}</td>
                        <td class="border-r-none">
                            <a href="/award/${list.awardId}" style="line-height:18px;"
                               target="_blank">${list.awardTitle}</a>
                        </td>
                        <td class="border-r-none"><a href="/award/${list.awardId}"><img
                                        src="${picture_base}/${list.productImg}!80" alt="" height="70"/></a></td>
                        <#--  <td>&yen; ${list.price?string('0.00')}</td>-->
                        <td style="line-height:150%;">${list.award.begin?string('yy/MM/dd HH:mm:ss')}
                            <br/>至<br/>${list.award.end?string('yy/MM/dd HH:mm:ss')}</td>
                        <td>
                            <#if list.status == -2>未付款已关闭
                            <#elseif list.status == -1>未中奖
                            <#--  <#elseif list.status== 0>待付款-->
                            <#elseif list.status== 1>待揭晓
                            <#elseif list.status== 2>已中奖
                            <#elseif list.status== 3>已发货
                            </#if></td>
                        <#-- <td><#if list.status == 0><a href="/award/payment/${list.id}" class="button button-s button-red button-outline" target="_blank">付款</a></#if></td> -->
                    </tr>
                    <#if list.type='SEVERAL'>
                        <tr class="item text-center">
                            <td>
                                幸运号码
                            </td>
                            <td class="text-left" colspan="7">
                                <p style="word-break:break-all"><#if list.status == 2>${(list.luckyNum)?replace(list.award.winNum,'<strong style="color:red">'+list.award.winNum+'</strong>')?replace(',',' ')!}<#else>${list.luckyNum}</#if></p>
                            </td>
                        </tr>
                    </#if>
                </#list>
            </#if>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>
<script>
    $('#submit-form').submit(function () {
        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: $(this).serialize(),
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                    window.parent.$.flash_tips('修改成功!', 'success');
                    window.frames['main-frame'].location.reload();
                } else {
                    window.parent.$.flash_tips('修改失败!' + data.result.message, 'error');
                }
                setTimeout(function () {
                    $('#ebox-ebox-remove').trigger('click');
                }, 500);

            }
        });
        return false;
    });
</script>
<@m.page_footer />