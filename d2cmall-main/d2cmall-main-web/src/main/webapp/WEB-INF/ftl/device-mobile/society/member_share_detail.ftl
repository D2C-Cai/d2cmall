<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="" hastopfix='true' title="买家秀详情"  button="false" cart="has-bar"   service='false' description="${(memberShare.description)!}"/>
<style>
    body, html {
        background: #FFF
    }
</style>
<div class="lazyload" style="line-height:70%;margin-top:10px">
    <#if memberShare?exists>
    <div class="theme-list">
        <div class="theme-item">
            <div class="item-info flex">
                <div class="users flex">
                    <img src="<#if memberShare.memberHead!=''><#if memberShare.memberHead?index_of('http')!=-1>${memberShare.memberHead}<#else>${picture_base}${memberShare.memberHead}</#if><#else>//static.d2c.cn/common/nm/img/_0001_58.png</#if>">
                    <div class="n-t">
                        <p class="name">${memberShare.memberName}</p>
                        <p class="show-time" data-time="${memberShare.createDate}"></p>
                    </div>
                </div>
                <div class="attend">
                    +关注
                </div>
            </div>
            <#if memberShare.description>
                <div class="item-content">
                    <#if memberShare.topicId gt 0><a href="/membershare/topic/${memberShare.id}"><span
                                class="item-title">#${(memberShare.topicName)!}#</span></a></#if>
                    <span>${memberShare.description}</span>
                </div>
            </#if>
            <#if list.resourceType=='video'>
                <div class="item-img item-one item-video">
                    <video width="100%" height="100%" src="${picture_base}${memberShare.video}"
                           style="position:absolute;width:100%;height:100%;display:none;background:#000"
                           type="video/mp4" class="videoarea" controls="controls"></video>
                    <a href="javascript:;" class="theme-play"></a>
                    <img src="${picture_base}${(memberShare.pics[0])!}!/both/345x345">
                </div>
            <#else>
                <#if memberShare.pics>
                    <#assign pics=memberShare.pics/>
                    <div class="item-img <#if pics?size==1 > item-one<#elseif pics?size==2> item-two</#if>">
                        <#list pics as pic>
                            <a href="#"><img src="${picture_base}${pic}!/both/345x345"></a>
                        </#list>
                    </div>
                </#if>
            </#if>
            <div class="item-bar flex">
                <div>
                    <span class="btn-like ajax-request" method-type="post"
                          data-url="/membershare/like/insert/${memberShare.id}" call-back="liked(${memberShare.id})"><i
                                class="<#if memberShare.liked==1>icon-liked<#else>icon-like</#if> like-${list.id}  mar5"></i><span
                                class="lin">${memberShare.likeMeCount}</span></span>
                    <span class="btn-comment"><a href="/membershare/${memberShare.id}"><i class="icon-comment mar5"
                                                                                          style="margin-left:25px;"></i>${memberShare.commentCount}</a></span>
                </div>
                <div>
                    <span class="btn-watch"><i class="icon-watchnum mar5"
                                               style="width:28.3px;"></i>${memberShare.watchCount}</span>
                </div>
            </div>
        </div>
        <#if memberShare.productRelations?size gt 0>
            <div class="swiper-containered">
                <div class="product-thcombo swiper-wrapper">
                    <#list memberShare.productRelations as product>
                        <div class="prodct-com swiper-slide">
                            <a href="/product/${product.id}">
                                <img src="${picture_base}${product.img}!/both/80x120">
                                <div class="pro-dom">
                                    <p class="pro-designer">${product.designer}</p>
                                    <p class="pro-title">${product.name}</p>
                                    <p class="pro-price">&yen;${product.minPrice}</p>
                                </div>
                            </a>
                        </div>
                    </#list>
                </div>
            </div>
        </#if>
        <div class="theme-comment">
            <div class="comment-title">
                话题评论
            </div>
            <#if memberShare.comments?size gt 0>
                <#list memberShare.comments as comment>
                    <div class="coment-it theme-item">
                        <div class="usert flex">
                            <div class="users flex">
                                <img src="<#if comment.headPic!=''><#if comment.headPic?index_of('http')!=-1>${comment.headPic}<#else>${picture_base}${comment.headPic}</#if><#else>//static.d2c.cn/common/nm/img/_0001_58.png</#if>">
                                <div class="n-t">
                                    <p class="name">${comment.nickName}</p>
                                    <p class="time">${comment.createDate?substring(6,16)}</p>
                                </div>
                            </div>
                            <div class="like">
                                <span style="display:inline-block;">&nbsp;&nbsp;&nbsp;&nbsp;回复</span>
                            </div>
                        </div>
                        <div class="content">
                            <#if comment.toCommentContent>
                                <div class="to-mycoth" style="margin-top:10px">
                                    回复<span style="color:#000"> ${comment.toNickName}: </span> ${comment.toCommentContent}
                                </div>
                            </#if>
                            <div class="my-content <#if comment.toCommentContent>tooth</#if>">
                                ${comment.content}
                            </div>
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</div>

<share data-title="大家来看看我的D2C买家秀" data-pic="${picture_base}${memberShare.first}!wx.title"></share>
<script>
    <#if browser=='wechat'>
    wx.ready(function () {
        $('.item-img a').click(function () {
            var img = $(this).find('img').attr('src');
            wx.previewImage({
                current: img,
                urls: [
                    <#list memberShare.pics as image>
                    '${picture_base}${image}'<#if image_index lt memberShare.pics?size-1>, </#if>
                    </#list>
                ]
            });
        });
    });
    </#if>

    //点赞
    function liked(id) {
        var num = parseInt($('.like-' + id).next('.lin').text())
        if ($('.like-' + id).hasClass('icon-like')) {
            $('.like-' + id).removeClass('icon-like').addClass('icon-liked').next('.lin').text((num + 1));
        }
    }

    $('.share-button').on('click', function () {
        <#if browser=='wechat'>
        if ($('.share-wechat-guide').size() == 0) {
            var html = '<div class="share-wechat-guide"><a href="javascript:void(0);" class="mask-close"></a></div>';
            $('body').append(html);
        }
        $('.share-wechat-guide').addClass('show');
        return false;
        <#else>
        var title = $('share').attr('data-title') || document.title,
            desc = $('share').attr('data-desc') || document.title,
            pic = $('share').attr('data-pic'),
            url = $('share').attr('data-url') || document.location.href;
        location.href = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(url) + '&title=' + encodeURIComponent(desc) + '&pic=' + encodeURIComponent(pic) + '&appkey=&searchPic=false';
        </#if>
    });
</script>
<#if memberShare.productRelations?size gt 0>
    <script>
        var mySwiper = new Swiper('.swiper-containered', {
            slidesOffsetAfter: 15,
            slidesPerView: 'auto',
            spaceBetween: 15,
            freeMode: true
        })
    </script>
</#if>
<#else>
    对不起，不存在该买家秀。
</#if>
<@m.page_footer menu=true />