<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title="D2C快抢" service="12.12" js="utils/swiper.min" css="swiper.min|flashpromotion" keywords="全球好设计,设计师品牌,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="全球好设计  整点限时限量秒杀，先到先得！" service='false'/>
<img src="https://static.d2cmall.com/img/promo/limit/limit.jpg" style="display:none"/>
<div class="brand-banner">
    <img src="${picture_base}${flashPromotion.brandPic}" style="width:100%"/>
</div>
<div class="limit-content" style="margin-bottom:.875rem" id="load-more-product">
    <div class="limit-title flex" style="padding:0 15px;">
        <#if flashPromotion.statusName=='即将开始'>
            <div class="limit-countdown count-down" style="margin:0 auto;" data-type="split-time"
                 data-endtime="${flashPromotion.startDate?string("yyyy/MM/dd HH:mm:ss")}" data-musttime="must">
                距开始&nbsp;<span class="hour down-time">00</span>:<span class="minute down-time">00</span>:<span
                        class="second down-time">00</span>
            </div>
        <#else>
            <div class="limit-countdown count-down" data-type="split-time" style="margin:0 auto;"
                 data-endtime="${flashPromotion.endDate?string("yyyy/MM/dd HH:mm:ss")}" data-musttime="must">
                距结束&nbsp;<span class="hour down-time">00</span>:<span class="minute down-time">00</span>:<span
                        class="second down-time">00</span>
            </div>
        </#if>
    </div>
    <#if pager.list?exists>
        <#list pager.list as lists>
            <div class="limit-product-item flex">
                <a class="p-img" href="/product/${lists.id}"><#if lists.store lte 0><i class="noshop-mask"><span></span></i></#if>
                    <img src="${picture_base}${lists.productImageCover}!/both/600x900" style="width:100%;"></a>
                <div class="lproduct-info">
                    <p><#if flashPromotion.statusName=="即将开始"><span>即将开售</span> </#if>${lists.name}</p>
                    <div class="text-right">
                        <p class="lproduct-price"><span class="lightgrey">原价&nbsp;</span><span class="lightgrey"
                                                                                               style="text-decoration:line-through;padding-right:16px;">&yen;${lists.minPrice}</span>限时价&nbsp;&nbsp;&yen;<span
                                    style="font-size:1.5rem;font-weight: bold;">${lists.flashPrice}</span>
                        </p>
                        <div class="lproduct-progress flex" style="justify-content:flex-end;">
                            <#if flashPromotion.statusName=="即将开始">
                                <div class="flash-button remind" data-id="${lists.id}">
                                    提醒我
                                </div>
                            <#else>
                                <a class="flash-button buy" href="/product/${lists.id}">
                                    <#if lists.store lte 0>去看看<#else>马上买</#if>
                                </a>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </#list>
    </#if>
</div>
<#if pager.pageCount gt 1>
    <div class="load-more scroll-load-more" data-url="/flashpromotion/products/list?id=${flashPromotion.id}"
         data-target="load-more-product" template-id="list-template" data-page="${pager.pageNumber}"
         data-total="${pager.pageCount}">点击加载更多
    </div>
</#if>
<div style="height:3.125rem">
    <div class="fixed-taber flex">
        <a href="/flashpromotion/product/session"><img src="//static.d2cmall.com/img/promo/limit/bar_timeshop.png"></a>
        <a href="/flashpromotion/brand/session"><img
                    src="//static.d2cmall.com/img/promo/limit/bar_super_chosen.png"></a>
    </div>
</div>

<script id="list-template" type="text/html">
    {{each list as value i}}

    <div class="limit-product-item flex">
        <a class="p-img" href="/product/{{value.id}}">{{if value.store<=0}}<i class="noshop-mask"><span></span></i>{{/if}}<img
                    src="${picture_base}{{value.productImageCover}}!/both/600x900" style="width:100%;"></a>
        <div class="lproduct-info">
            <p><a href="/product/{{value.id}}"><#if flashPromotion.statusName=='即将开始'><span>即将开售</span></#if>
                    {{value.name}}</a></p>
            <div class="text-right">
                <p class="lproduct-price"><span class="lightgrey">原价&nbsp;</span><span class="lightgrey"
                                                                                       style="text-decoration:line-through;padding-right:16px;">&yen;{{value.minPrice}}</span>限时价&nbsp;&nbsp;&yen;<span
                            style="font-size:1.5rem;font-weight: bold;">{{value.flashPrice}}</span>
                </p>
                <div class="lproduct-progress flex" style="justify-content:flex-end;">
                    <#if flashPromotion.statusName=='即将开始'>
                        <div class="flash-button remind" data-id="{{value.id}}">
                            提醒我
                        </div>
                    <#else>
                        <a class="flash-button buy" href="/product/{{value.id}}">
                            {{if value.store<=0}}去看看{{else}}马上买{{/if}}
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
    </a>
    {{/each}}
</script>
<script>
    var limit_buy = {
        remind: function () {
            var _self = this;
            $(document).on(click_type, '.remind', function (e) {
                var evt = e ? e : window.event;
                evt.stopPropagation();
                var id = $(this).attr('data-id');
                var that = this;
                $.getJSON('/member/islogin', function (data) {
                    if (data.result.login == false) {
                        userLogin();
                    } else if (data.result.datas.isBind == false) {
                        location.href = "/member/bind"
                        //userbind();
                    } else {
                        $.ajax({
                            'url': '/flashpromotion/remind/' + id,
                            'type': 'post',
                            'data': {},
                            'dataType': 'json',
                            'success': function (data, status) {
                                if (data.result.status == 1) {
                                    var html = '<div class="popup-content"><div id="success-info" class="remind-content"><div class="popup-remind-success-info"><i></i><p>开抢提醒</p></div><div class="popup-text">活动开始前<span>3</span>分钟，<br>将通过短信形式通知你</div><button type="button" class="button button-black button-small" onclick="popupModalClose()">确定</button></div></div>';
                                    popupModal({content: html});
                                } else {
                                    $.flashTip({position: 'center', type: 'success', message: data.result.message});
                                }

                            }
                        });
                    }
                });
            })
        },
        init: function () {
            this.remind();

        }

    }
    limit_buy.init();

</script>

<@m.page_footer />