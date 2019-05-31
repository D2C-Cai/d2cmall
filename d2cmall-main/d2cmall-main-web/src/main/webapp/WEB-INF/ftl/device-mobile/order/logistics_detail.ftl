<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="查看物流" title='查看物流' service='false' hastopfix='false'/>
<style>
    .footer-space {
        display: none
    }
</style>
<#if logistics && logistics.status!=-1>
    <div class="logistics_p">
        <div class="p_img">
            <a href="javascript:;" style="height:4.4rem;overflow:hidden;display:block;"><img
                        src="${picture_base}/${productImg}" style="width:100%"></a>
        </div>
        <div class="p_content">
            <p><span class="grey">物流状态&nbsp;&nbsp;&nbsp;</span>&nbsp;<span
                        style="color:#03A3ED">${logistics.statusName}</span></p>
            <p><span class="grey">承运来源：</span>&nbsp;<span>${logistics.deliveryCorpName}</span></p>
            <p><span class="grey">运单编号：</span>&nbsp;<span>${logistics.deliverySn}</span></p>
            <p><span class="grey">官方电话：</span>&nbsp;<a href="tel:${logistics.tel}">${logistics.tel}</a></p>
        </div>
    </div>
    <#if logistics.deliveryInfo>
        <div class="logistics_list">
            <ul>
                <#assign xxx=logistics.deliveryInfo?eval />
                <#list xxx as value>
                    <li>
                        <div class="logis-detail-d">
                            <div class="logis-detail-content">
                                <p>${value.context}</p>
                                <p>${value.ftime}</p>
                            </div>
                        </div>
                    </li>
                    <#assign x=x+1 />
                </#list>
            </ul>
        </div>
    <#else>
        <div style="text-align:center;line-height:3em;height:3em;">
            暂无查询物流信息
        </div>
    </#if>
<#else>
    <script type="text/javascript">
        var a = document.createElement("a");
        a.setAttribute("href", "//m.kuaidi100.com/index_all.html?type=${logistics.deliveryCorpName}&postid=${logistics.deliverySn}&callbackurl=//m.d2cmall.com/member/order/${ordersn}");
        a.setAttribute("id", "kuaidi100");
        a.style.display = "none";
        // 防止反复添加
        if (!document.getElementById("kuaidi100")) {
            document.body.appendChild(a);
        }
        a.click();

    </script>
</#if>

<@m.page_footer />