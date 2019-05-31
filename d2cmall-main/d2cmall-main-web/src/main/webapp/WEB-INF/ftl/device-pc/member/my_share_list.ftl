<#import "templates/public_pc.ftl" as m>
<@m.page_header title='分享赚积分 - 个人中心' />
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray clearfix">
        <@m.page_menu menu_item="task"/>
        <div class="my-cont">
            <h1>分享赚积分</h1>
            <#if pager.list?exists&&pager.list?size gt 0>
                <#list pager.list as shareTaskDef>
                    <a href="javascript:" class="list-item">
                        <span class="barcode">微信上扫一扫分享给朋友<img
                                    src="/picture/code?code=//192.168.5.37:8080/shareTask/detail/${(shareTaskDef.id)!}&type=1&width=150&height=150"
                                    alt=""/></span>
                        <#if shareTaskDef.over>
                            <span class="task-tag end">任务结束</span>
                        <#elseif shareTaskDef.claimed>
                            <span class="task-tag process">已领取</span>
                        <#elseif shareTaskDef.start>
                            <span class="task-tag start">领取任务</span>
                        <#else>
                            即将开始
                        </#if>
                        <span class="title">${(shareTaskDef.title)!}</span>
                        <span class="img">
					<img src="//img.d2c.cn/cdp/1412/d34e9e7397bb66e830707d9dfd0f0cb9" alt="">
                            <!-- <img src="${picture_base}/${(shareTaskDef.bicPic)!}" alt="" /> -->
				</span>
                        <span class="bar">期限：${(shareTaskDef.startTime?string("yyyy年MM月dd日"))!}-${(shareTaskDef.endTime?string("yyyy年MM月dd日"))!}</span>
                    </a>
                </#list>
            <#else>
                <div style="text-align:center;line-height:100px;">
                    暂时没有积分任务可以分享
                </div>
            </#if>
        </div>
    </div>
</div>
<@m.page_footer />