<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='邀你成为D2C时尚买手' css='' service="false" />
<#assign parentId=RequestParameters.parent_id />
<#assign nickName=RequestParameters.name />
<#assign headPic=RequestParameters.avatar />
<style>
    footer, .footer-space, .app-download-bar, .online-chat {
        display: none;
    }

    img {
        display: block;
        width: 100%;
    }

    .content-container {
        width: 100%;
        position: relative;
        background: #000;
    }

    .gift-header {
        text-align: center;
        padding: 20px 0;
        color: #FFF;
    }

    .gift-header .head-pic {
        display: inline-block;
        width: 2.5rem;
        height: 2.5rem;
        vertical-align: middle;
        border-radius: 100%;
        margin-right: 10px;
    }

    .gift-item {
        display: block;
        float: left;
        width: 44%;
        margin: 0 0 5% 4%;
    }

    .gift-item .gift-cont {
        background: #FFF;
        width: 100%;
        color: #000;
    }

    .gift-cont .title {
        height: 3.5rem;
        line-height: 1.5;
        padding: 8px;
        box-sizing: border-box;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
    }

    .gift-cont .price {
        color: #000;
        padding: 10px 0 10px 10px;
        font-weight: 550;
        text-align: center;
    }

    .gift-more {
        width: 100px;
        margin: 10px auto;
        padding: 8px 10px;
        border: 1px solid #FFF;
        color: #FFF !important;
    }

    .open-shop {
        position: fixed;
        bottom: 0;
        left: 0;
        z-index: 99;
        width: 100%;
        height: 3rem;
        line-height: 3rem;
        text-align: center;
        font-size: 16px;
        font-weight: bold;
        color: #FFF;
        background: #c29a60;
    }
</style>
<div class="content-container">
    <#if nickName && headPic>
        <div class="gift-header">
            <img src="" id="headPic" class="head-pic"/>
            <span style="display:inline-block;vertical-align:middle;"><span id="nickName"></span>邀您成为买手</span>
        </div>
    </#if>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_03.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_04.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_05.jpg" width="100%" alt=""></div>
    <div class="gift-wrapper clearfix"></div>
    <div style="width:100%;display:flex;justify-content:center;text-align:center;">
        <a href="/page/gift?parent_id=${parentId}" class="gift-more">查看更多</a>
    </div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_07.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_08.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_09.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_10.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_11.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_12.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_13.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_14.jpg" width="100%" alt=""></div>
    <div><img src="https://static.d2c.cn/img/topic/180910/gift/images/gift_15.jpg" width="100%" alt=""></div>
    <div style="height:3rem;"></div>
    <div class="open-shop"
         data-current="<#if m.LOGINMEMBER.partnerId==parentId>invite<#else>join</#if>"><#if m.LOGINMEMBER.partnerId==parentId>我要邀请<#else>我要加入</#if></div>
    <div class="share-wechat-guide">
        <a href="javascript:void(0);" class="mask-close"></a>
    </div>
</div>
<share data-title="成为D2C时尚买手" data-desc="分享好物，轻松赚钱" data-pic="http://img.d2c.cn/app/a/18/05/08/mini.png"></share>

<p>
    <script id="list-template" type="text/html">
        {{each list as value i}}
        <a href="/product/{{value.id}}?parent_id=${parentId}" class="gift-item">
            <div class="gift-img"><img data-none src="//img.d2c.cn/{{value.productImageCover}}!300"
                                       alt="{{value.name}}"></div>
            <div class="gift-cont">
                <p class="title">{{value.name}}</p>
                <p class="price">&yen;{{value.salePrice}}<span
                            style="font-size:14px;font-weight:normal;text-decoration:line-through;margin-left:10px;color:#999;">&yen;{{value.originalPrice}}</span>
                </p>
            </div>
        </a>
        {{/each}}
    </script>
</p>
<p>
    <script>
        var headPic = decodeURIComponent('${headPic}');
        if (headPic.indexOf('http://img.d2c.cn') != -1) {
            headPic = headPic.substring(17);
        }
        var nickName = decodeURIComponent('${nickName}');
        $('#headPic').attr('src', headPic);
        $('#nickName').text(nickName);
        //礼包商品
        $.getJSON('/partner/gift/list?pageSize=4', function (data) {
            var html = template('list-template', data.pager);
            $('.gift-wrapper').append(html);
            return false;
        });
        $('.open-shop').on(click_type, function () {
            var current = $(this).attr('data-current');
            if (current === 'join') {
                var top = $('.gift-wrapper').offset().top - 150;
                $("html,body").animate({"scrollTop": top}, 500)
                setTimeout(function () {
                    location.href = "/page/gift?parent_id=${parentId}";
                }, 1500)
            } else {
                if (app_client === true) {
                    var message = {
                        handlefunc: 'w_giftShare',
                        func: "cessback",
                        type: "page"
                    }
                    $.D2CMerchantBridge(message)
                } else {
                    if (isWeChat) {
                        $('.share-wechat-guide').addClass('show');
                        $(document).on(click_type, '.share-wechat-guide .mask-close', function () {
                            $('.share-wechat-guide').removeClass('show');
                            return false;
                        });
                    } else {
                        var title = $('share').attr('data-title') || document.title,
                            desc = $('share').attr('data-desc') || document.title,
                            pic = $('share').attr('data-pic'),
                            url = $('share').attr('data-url') || document.location.href;
                        location.href = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(url) + '&title=' + encodeURIComponent(desc) + '&pic=' + encodeURIComponent(pic) + '&appkey=&searchPic=false';
                    }
                }
            }
        })
    </script>
</p>

<@m.page_footer menu=false />