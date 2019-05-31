<#import "templates/public_pc.ftl" as m>
<@m.page_header title='D2C帮${wish.displayName}向你表白' css="qixi" keywords="全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<@m.top_nav />
<div style="width: 100%; height:1038px;position: relative; overflow: hidden;">
    <div style="left: 50%; width: 1920px; margin-left: -960px; position: absolute;">
        <table cellspacing="0" cellpadding="0">
            <tbody>
            <tr>
                <td><img width="360" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_01.jpg"/></td>
                <td><img width="1200" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_02.jpg"/></td>
                <td><img width="360" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_03.jpg"/></td>
            </tr>
            <tr>
                <td><img width="360" height="245" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_04.jpg"/></td>
                <td><img width="1200" height="245" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_05.jpg"/></td>
                <td><img width="360" height="245" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_06.jpg"/></td>
            </tr>
            <tr>
                <td style="position:relative;"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_l.jpg"
                                                    alt="" style="position:absolute;right:0;bottom:-306px;"/><img
                            width="360" height="244" alt=""
                            src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_07.jpg"/></td>
                <td><img width="1200" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_08.jpg"/></td>
                <td style="position:relative;"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_r.jpg"
                                                    alt="" style="position:absolute;left:0;bottom:-147px;"/><img
                            width="360" height="244" alt=""
                            src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_09.jpg"/></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<div id="qixi" class="w-1200 lazyload" style="margin-top:-305px;">
    <div class="box-wall clearfix" id="love-wall">
        <img src="//d2c-static.b0.upaiyun.com/img/active/140802/love_bg.jpg" alt=""/>

        <div class="publish-box show" id="love-view-box">
            <div class="text-center" style="padding:20px 0">
                <img src="//d2c-static.b0.upaiyun.com/img/active/140802/view_1.png" alt=""/>
            </div>
            <div class="love-view-text" id="love-view-text">
                <p>${(wish.who)!}，你造吗，${(wish.when)!}，那是我们第一次见面，有一种心动的感觉荡漾开来，让我明白什么叫做一见倾心，一见钟情。</p>

                <p>你的${(wish.what)!}深深地俘获了我的心，我就这样陷在被你包围的小情绪中，你的每一个眼神、每一个动作都让我无比喜欢，不能自拔。</p>
                <#if wish.dateWhen>
                    <p>${(wish.dateWhen)!}，这是我们第一次约会的日子，对我来说，这个日子与我的生日一样重要，足以铭记一生。这个七夕节，我通过D2C的平台向你发出爱的告白：</p>
                    <p>${(wish.speakTo)!}</p>
                <#else>
                    <p>我一直不够自信，不敢向你告白同你约会，眼看七夕就要到了，我鼓起勇气，通过D2C的平台向你发出爱的告白：${(wish.speakTo)!}</p>
                </#if>
                <p style="text-align:right">爱你的 ${wish.displayName}</p>
                <p style="text-align:right">${wish.createDate?string("yyyy.MM.dd")}</p>
            </div>

            <div class="love-bottom">
                <a href="/qixi"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/love_bottom.jpg" alt=""/></a>
            </div>
        </div>
    </div>

    <div class="topic-list" style="margin-top:30px;">
        <a href="" target="_blank"><img src="/static/c/images/space.gif"
                                        data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_1.jpg"
                                        height="394" alt=""/></a>
        <img src="/static/c/images/space.gif" data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_text.jpg"
             height="98" alt=""/>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_2.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_3.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_4.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_5.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_6.jpg"
                                                                    height="400" alt=""/></a>
    </div>

</div>
<script>
    with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = '//bdimg.share.baidu.com/static/api/js/share.js?cdnversion=' + ~(-new Date() / 36e5)];
</script>
<@m.page_footer />