<#import "templates/public_pc.ftl" as m>


<div class="lazyload">
    <div class="layout-response list clearfix"
         style="width:1125px;margin-top:30px;font-size:14px;font-family:'Microsoft Yahei'">
        <table class="table table-grey offer-table">
            <tbody>
            <tr>
                <th width="28%" style="border-color:#f8f8f8;background:#f8f8f8;">用户</th>
                <th width="28%">出价</th>
                <th width="28%">时间</th>
                <th width="16%">状态</th>
            </tr>
            <#if pager.list?exists && pager.list?size gt 0>
                <#list pager.list as list>
                    <tr class="line-item" style="border-left:none;border-right:none">
                        <td class="border-l-none border-r-none">
                            <#if list.status ==1>
                            <span style="color:#fd555d">
							</#if>
                                <#if (list.loginCode?length) gt 8 >***${list.loginCode?substring(7)}<#elseif (!list.loginCode?exists)>${(list.memberNick)!}<#else>${list.loginCode}</#if>
                                <#if list.status ==1>
							</span>
                            </#if>
                        </td>
                        <td class="border-l-none border-r-none">
                            <strong>&yen;${list.offer?string("currency")?substring(1)}</strong></td>
                        <td class="border-l-none border-r-none">${list.createDate?string("HH:mm:ss MM/dd")}</td>
                        <td class="border-l-none border-r-none">
                            <#if list.status==1 || list.status==8><span style="color:#fd555d"></#if>
                                <#if list.status==1>领先<#elseif list.status==0>出局<#elseif list.status==8>得标<#else>删除</#if>
							</span></td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</div>


