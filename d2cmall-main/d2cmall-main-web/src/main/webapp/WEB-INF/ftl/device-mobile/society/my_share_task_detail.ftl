<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='任务详情' noheader=true  service='false'/>
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/shareTask/list" class="back">
            <span class="icon icon-chevron-left">分享任务详情</span>
        </a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
</div>
<div style="height:2.9em;"></div>
<div class="order-status-bar">
    <pre>${(shareTaskDef.rule)!}</pre>
</div>
<div class="layer-buy" style="padding:0 0.6em 0.6em;background:none;">
    <button type="button" name="share-to-timeline" class="share-btn">分享到朋友圈</button>
</div>
<div class="lazyload">
    <#if shareTaskDef.mobileContent?exists && shareTaskDef.mobileContent!=''>${shareTaskDef.mobileContent?replace('src=','src="${static_base}/c/images/space.gif" data-src=')}<#else>${(shareTaskDef.shareContent?replace('src=','src="${static_base}/c/images/space.gif" data-src='))!}</#if>
</div>
<div class="share-mask">
    <a href="javascript:void(0);" class="mask-close"></a>
</div>
<share data-title="${(shareTaskDef.title)!}" data-url="/shareTask/click/${shareTask.id}"
       data-pic="${picture_base}${(shareTaskDef.smallPic)!}!wx.title"></share>
<script>
    $('.share-btn').on('touchstart', function () {
        <#if browser=='wechat'>
        $('.share-mask').addClass('show-mask');
        <#else>
        var title = $('share').attr('data-title') || document.title,
            desc = $('share').attr('data-desc') || document.title,
            pic = $('share').attr('data-pic'),
            url = $('share').attr('data-url') || document.location.href;
        location.href = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(url) + '&title=' + encodeURIComponent(desc) + '&pic=' + encodeURIComponent(pic) + '&appkey=&searchPic=false';
        </#if>
    });
</script>
<@m.page_footer />
