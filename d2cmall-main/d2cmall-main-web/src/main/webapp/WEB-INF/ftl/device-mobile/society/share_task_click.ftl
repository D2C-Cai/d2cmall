<#import "templates/public_mobile.ftl" as m>
<@m.page_header  noheader=true title='${task.title}' keywords="${task.title},预售,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" js='unslider.min'/>
<div class="lazyload"><#if task.shareTaskDef.mobileContent?exists && task.shareTaskDef.mobileContent!=''>${task.shareTaskDef.mobileContent?replace('src=','src="${static_base}/c/images/space.gif" data-src=')}<#else>${(task.shareTaskDef.shareContent?replace('src=','src="${static_base}/c/images/space.gif" data-src='))!}</#if></div>
<share data-title="${(task.shareTaskDef.title)!}" data-url="/shareTask/click/${task.id}"
       data-pic="${picture_base}${(task.shareTaskDef.smallPic)!}!wx.title"></share>
<script>
    var memberId = '${(task.memberInfo.id)!}';
    $.each($('a'), function (i, obj) {
        var url = $(obj).attr('href');
        var str = '';
        if (url.indexOf('/product') != -1 || url.indexOf('/promotion') != -1 || url.indexOf('/tag') != -1) {
            if (url.indexOf('#') != -1) {
                var url_arr = url.split('#');
                $(obj).attr('href', url_arr[0] + '?memberId=' + memberId + '#' + url_arr[1]);
            } else {
                str = url.indexOf('?') != -1 ? '&' : '?';
                $(obj).attr('href', url + str + 'memberId=' + memberId);
            }
        }
    });
    var title = $('share').attr('data-title') || document.title,
        desc = $('share').attr('data-desc') || document.title,
        pic = $('share').attr('data-pic'),
        url = $('share').attr('data-url') || document.location.href;
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        WXShare(title, desc, pic, url);
    }
</script>
<@m.page_footer />