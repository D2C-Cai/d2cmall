<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='D2C帮${wish.displayName}向你表白' css="qixi" />
<div>
    <a href="/qixi"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/m_banner.jpg" style="width:100%;"
                         alt=""/></a>
</div>
<div class="love-box love-text">
    <div class="text-center"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/m_view_1.png" style="width:70%;"
                                  alt=""/></div>
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
    <div style="margin-top:2em;">
        <button type="button" name="join-button" id="join-button" onclick="location.href='/qixi';" class="button">
            我也要参加活动
        </button>
    </div>
</div>
<div style="margin-top:0.6em;line-height:180%;">
    <a href="/product/list?tagId=36"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072902.jpg"/></a>
    <a href="/product/list?tagId=37"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072903.jpg"/></a>
    <a href="/product/list?tagId=38"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072904.jpg"/></a>
    <a href="/product/list?tagId=39"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072905.jpg"/></a>
    <a href="/product/102706"><img alt="" style="width:100%;"
                                   src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072401.jpg"/></a>
</div>
<@m.page_footer />