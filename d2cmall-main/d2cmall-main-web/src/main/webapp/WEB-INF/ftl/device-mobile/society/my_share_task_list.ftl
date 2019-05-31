<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='分享任务' noheader=true  service='false'/>
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/home" class="back">
            <span class="icon icon-chevron-left">分享任务</span>
        </a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
</div>
<div style="height:2.9em;"></div>
<div class="wrap-main">
    <#if pager.list?exists&&pager.list?size gt 0>
        <#list pager.list as shareTaskDef>
            <a href="/shareTask/detail/${(shareTaskDef.id)!}" class="list-item">
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
        <div class="text-center" style="line-height:100px;">没有任务</div>
    </#if>
</div>
<@m.page_footer />
