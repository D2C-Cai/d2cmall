<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='${(topic.title)!}' description="${(topic.title)!}"  service="share" hastopfix='false'  />
<#assign memberShares=result.data.memberShares/>
<#assign topic=result.data.topic/>
<style>
    body, html {
        background: #FFF;
        color: #000;
    }

    footer {
        display: none
    }
</style>
<div class="theme-banner">
    <img src="${picture_base}${(topic.pic)!}" alt="话题banner">
    <div class="theme-title">${(topic.title)!}</div>
    <div class="in-num">${topic.shareCount}人参与</div>
</div>
<div class="theme-nemo">
    <p style="color:rgba(0,0,0,0.54);">${(topic.content)!}</p>
    <div class="toggle-more icon-sa">展开</div>
    <div class="toggle-omit icon-sa">收起</div>
</div>
<div class="theme-list">
    <div class="theme-tab">
        <a href="/membershare/topic/${topic.id}" class="<#if !RequestParameters.orderType>current</#if>"
           style="margin-right:55px">热门</a>
        <a href="/membershare/topic/${topic.id}?orderType=new"
           class="<#if RequestParameters.orderType>current</#if>">最新</a>
    </div>
    <#list memberShares.list as list>
        <div class="theme-item">
            <div class="item-info flex">
                <div class="users flex">
                    <img src="<#if list.headPic!=''><#if list.memberHead?index_of('http')!=-1>${list.memberHead}<#else>${picture_base}${list.memberHead}</#if><#else>//static.d2c.cn/common/nm/img/_0001_58.png</#if>">
                    <div class="n-t">
                        <p class="name">${list.memberName}</p>
                        <p class="show-time" data-time="${list.createDate}"></p>
                    </div>
                </div>
                <div class="attend">
                    +关注
                </div>
            </div>
            <div class="item-content">
                <a href="/membershare/topic/${topic.id}"><span
                            class="item-title">#${(topic.title)!}#</span></a><span>${list.description}</span>
            </div>
            <#if list.resourceType=='video'>
                <div class="item-img item-one item-video">
                    <video width="100%" height="100%" src="${picture_base}${list.video}"
                           style="position:absolute;width:100%;height:100%;display:none;background:#000"
                           type="video/mp4" class="videoarea" controls="controls"></video>
                    <a href="javascript:;" class="theme-play"></a>
                    <img src="${picture_base}${(list.pics[0])!}!/both/345x345">
                </div>
            <#else>
                <#if list.pics>
                    <#assign pics=list.pics/>
                    <div class="item-img <#if pics?size==1 > item-one<#elseif pics?size==2> item-two<#elseif pics?size gte 2> item-three</#if>">
                        <#list pics as pic>
                            <#if pic_index lte 2><a href="${picture_base}${pic}"><img
                                        src="${picture_base}${pic}!/both/345x345"></a></#if>
                        </#list>
                        <#if pics?size gt 3>
                            <a class="img-more flex" href="/membershare/${list.id}">
                                <p>更多</p>
                                <p>+6</p>
                            </a>
                        </#if>
                    </div>
                </#if>
            </#if>

            <div class="item-bar flex">
                <div>
                    <span class="btn-like ajax-request" method-type="post"
                          data-url="/membershare/like/insert/${list.id}" call-back="liked(${list.id})"><i
                                class="<#if list.liked==1>icon-liked<#else>icon-like</#if> like-${list.id}  mar5"></i><span
                                class="lin">${list.likeMeCount}</span></span>
                    <span class="btn-comment"><a href="/membershare/${list.id}"><i class="icon-comment mar5"
                                                                                   style="margin-left:25px;"></i>${list.commentCount}</a></span>
                </div>
                <div>
                    <span class="btn-watch"><i class="icon-watchnum mar5"
                                               style="width:28.3px;"></i>${list.watchCount}</span>
                </div>
            </div>
        </div>
    </#list>
    <#if pager.pageCount gt 1>
        <div class="load-more scroll-load-more" data-url="/membershare/topic/${topic.id}"
             data-target="load-more-product" template-id="list-template" data-page="${pager.pageNumber}"
             data-total="${pager.pageCount}">点击加载更多
        </div>
    </#if>
</div>
<a style="display:none;" href="javascript:;" id="openD2cApps">打开APP</a>

<share data-title="大家来看看我的D2C主页" data-pic="${picture_base}${(member.head)}!wx.title"></share>

<script>
    new Mlink({
        mlink: "AKGJ",
        button: document.querySelector("a#openD2cApps"),
        autoLaunchApp: false,
        autoRedirectToDownloadUrl: true,
        downloadWhenUniversalLinkFailed: false,
        inapp: false,
        params: {myUrl: ''}
    })
</script>
<script>
    var userdate = new Date("${.now?string("yyyy/MM/dd HH:mm:ss")}");
    var mydate = new Date("${.now?string("yyyy/MM/dd HH:mm:ss")}");
    mydate.setHours(0);
    mydate.setMinutes(0);
    mydate.setSeconds(0);
    mydate.setMilliseconds(0);
    $('.show-time').each(function (i, d) {
        var time = $(this).attr('data-time');
        var date = new Date(time);
        if (mydate.toDateString() == date.toDateString()) {
            var str = "<p class='fortime'>今天</p>"
            $(this).append(str);
        } else if (mydate.getTime() - date.getTime() <= 86400000) {
            var str = "<p class='fortime'>昨天</p>"
            $(this).append(str);
        } else {
            var m = date.getMonth() + 1;
            var d = date.getDate();
            var str = "<p class='fortime'>" + m + "月" + d + "日</p>";
            $(this).append(str);
        }
        if (userdate.getTime() - date.getTime() <= 3600000 && userdate.getTime() - date.getTime() >= 120000) {
            var timeout = Math.floor((userdate.getTime() - date.getTime()) / 60000);
            var nstr = timeout + '分钟前';
            $(this).parent().find('.p-when').html(nstr);
        } else if (userdate.getTime() - date.getTime() <= 120000) {
            var str = "<p class='fortime'>刚刚</p>"
            $(this).append(str);
        } else {
            var h = date.getHours() >= 10 ? date.getHours() : '0' + date.getHours();
            var mn = date.getMinutes() >= 10 ? date.getMinutes() : '0' + date.getMinutes();
            var nstr = h + ":" + mn;
            $(this).parent().find('.p-when').html(nstr);
        }
    })

    //展开
    $('.toggle-more').on(click_type, function () {
        $('.theme-nemo').height('auto');
        $(this).hide();
        $('.toggle-omit').show();
    })
    //收起
    $('.toggle-omit').on(click_type, function (e) {
        e.stopPropagation();
        $('.theme-nemo').height('66px');
        $(this).hide();
        $('.toggle-more').show();
    })

    //点赞
    function liked(id) {
        var num = parseInt($('.like-' + id).next('.lin').text())
        if ($('.like-' + id).hasClass('icon-like')) {
            $('.like-' + id).removeClass('icon-like').addClass('icon-liked').next('.lin').text((num + 1));
        }
    }

    //关注打开APP
    $('.attend').on(click_type, function () {
        $('#openD2cApps').trigger('click');
    })
    //点击播放视频
    $('.theme-play').on(click_type, function () {
        $('.item-video').find('video').trigger('pause').hide();
        $('.theme-play').show();
        $(this).hide().prev('.videoarea').show().get(0).play();
    })


</script>
<@m.page_footer menu=true />