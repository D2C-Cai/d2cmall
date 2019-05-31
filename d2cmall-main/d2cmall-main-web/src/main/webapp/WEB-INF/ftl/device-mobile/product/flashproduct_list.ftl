<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title="D2C快抢" service="12.12" js="utils/swiper.min" css="swiper.min|flashpromotion" keywords="全球好设计,设计师品牌,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="全球好设计  整点限时限量秒杀，先到先得！" service='false'/>
<div><img src="https://static.d2cmall.com/img/promo/limit/limit.jpg" style="display:none"/></div>
<#assign currentIndex=list?size-1>
<#list list as list>
    <#if list.id==currentId>
        <#assign currentIndex=list_index>
    </#if>
</#list>
<div class="swiper-container swiper-container-horizontal limit-banner" id="swiper-container1" style="height:160px;">
    <div class="swiper-wrapper" id="adbanner"></div>
    <div class="swiper-pagination"></div>
</div>
<div class="swiper-container swiper-container-horizontal limit-time" id="swiper-container2">
    <div class="swiper-wrapper">
        <#if list?exists>
            <#list list as lists>
                <#if !lists.end>
                    <div class="swiper-slide limit-time-item <#if list?size ==1>on<#else><#if currentId==lists.id>on</#if></#if>"
                         data-id="${lists.id}">
                        <div><#if lists.startDate?string("yyyyMMdd")?date("yyyyMMdd") gt .now?string("yyyyMMdd")?date("yyyyMMdd")>明日<#elseif lists.startDate?string("yyyyMMdd")?date("yyyyMMdd") lt .now?string("yyyyMMdd")?date("yyyyMMdd")>昨日<#else>今日</#if>${lists.session}</div>
                        <!-- <div><#if lists.sessionName?exists && lists.sessionName!=''>${lists.sessionName}<#elseif currentId==lists.id && lists.startDate?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") lt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")>开抢中<#else>${lists.statusName}</#if> </div>-->
                    </div>
                </#if>
            </#list>
        </#if>
    </div>
</div>
    <div class="limit-container">
    <#if list?size==0>
    <div style="text-align:center;margin:10px 0;">暂无限时购</div>
    <#else>
    <div class="limit-title"></div>
    <div class="limit-content" id="load-more-product" style="margin-bottom:0.875rem;min-height:5rem;">
        </#if>
    </div>
    <div class="load-more flash-load-more" data-target="load-more-product" template-id="limit-list"
         style="display:none;">点击加载更多
    </div>
    <div style="height:3.125rem;"></div>
    <div class="fixed-taber flex">
        <a href="/flashpromotion/product/session" target="_self"><img
                    src="//static.d2cmall.com/img/promo/limit/bar_timeshop_chosen.png"></a>
        <a href="/flashpromotion/brand/session" target="_self"><img
                    src="//static.d2cmall.com/img/promo/limit/bar_super.png"></a>
    </div>

    <script id="ad-banner" type="text/html">
        {{each picList as value i}}
        <div class="swiper-slide">
            <a href="{{urlList[i]}}"><img src="//img.d2c.cn{{value}}!/both/750x320"></a>
        </div>
        {{/each}}
        <script>
            var mySwiper = new Swiper('#swiper-container1', {
                direction: 'horizontal',
                pagination: '.swiper-pagination',
            });
    </script>
    </script>

    <
    script
    id = "limit-title"
    type = "text/html" >
        < div
    class
    = "limit-title flex"
    style = "padding:0 15px;" >
        < p > {
    {
        flashPromotion.sessionRemark
    }
    }<
    /p>
    {
        {
            if flashPromotion.statusName == "即将开始"}
    }
    <
    div
    class
    = "limit-countdown"
    data - type = "split-time"
    data - endtime = "{{formatDate(flashPromotion.startDate)}}"
    data - musttime = "must" >
        距开始 & nbsp;
    <
    span
    class
    = "hour down-time" > 00 < /span>:<span class="minute down-time">00</s
    pan >
    :<
    span
    class
    = "second down-time" > 00 < /span>
        < /div>
    {
        {else
            if flashPromotion.statusName == "抢购中" || (flashPromotion.statusName == "已开抢" && !flashPromotion.end)}
    }
    <
    div
    class
    = "limit-countdown"
    data - type = "split-time"
    data - endtime = "{{formatDate(flashPromotion.endDate)}}"
    data - musttime = "must" >
        距结束 & nbsp;
    <
    span
    class
    = "hour down-time" > 00 < /span>:<span class="minute down-time">00</s
    pan >
    :<
    span
    class
    = "second down-time" > 00 < /span>
        < /div>
    {
        {
            /if}}
            < /div>
    </script>
    <script id="limit-list" type="text/html">
        {{each pager.list as value i}}
        <div class="limit-product-item flex">
            <a class="p-img" href="/product/{{value.id}}">{{if value.store<=0}}<i class="noshop-mask"><span></span></i>{{/if}}<img
                        src="${picture_base}{{value.productImageCover}}!/both/600x900" style="width:100%;"></a>
            <div class="lproduct-info">
                <p><a href="/product/{{value.id}}">{{if flashPromotion.statusName=="即将开始"}}<span>即将开售</span>{{/if}}{{value.name}}</a>
                </p>
                <div class="text-right">
                    <p class="lproduct-price"><span class="lightgrey">原价&nbsp;</span><span class="lightgrey"
                                                                                           style="text-decoration:line-through;padding-right:16px">&yen;{{value.minPrice}}</span>限时价&nbsp;&nbsp;&yen;<span
                                style="font-size:1.5rem;font-weight: bold;">{{value.flashPrice}}</span>
                    </p>
                    <div class="lproduct-progress flex" style="justify-content:flex-end;">
                        {{if flashPromotion.statusName=="即将开始"}}
                        <div class="flash-button remind" data-id="{{value.id}}">
                            提醒我
                        </div>
                        {{else}}
                        <a class="flash-button buy" href="/product/{{value.id}}">
                            {{if value.store<=0}}去看看{{else}}马上买{{/if}}
                        </a>
                        {{/if}}
                    </div>
                </div>
            </div>
        </div>
        {{/each}}
        <script>
            if ($('.count-down').size() > 0) {
                var time = (new Date(parseInt($('.count-down').attr('data-endTime'))).getTime());
                $('.count-down').attr('data-endtime', new Date(time));
                setTimeout(function () {
                    $('.count-down').countdown()
                }, 100);
            }
    </script>
    </script>

    <
    script >
    $(function () {
        /*var mySwiper2 = new Swiper('#swiper-container2',{
            slidesPerView : 'auto',
            spaceBetween : 8,
            initialSlide :
        ${currentIndex},
        centeredSlides : true,
        preventLinksPropagation:true,
        slideToClickedSlide:true
    });*/
    })
    </script>
    <script>
        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);//search,查询？后面的参数，并匹配正则
            if (r != null) return unescape(r[2]);
            return null;
        }
    </script>
    <script>
        var limit_buy = {
            remind: function (e) {
                var _self = this;
                $(document).on(click_type, '.remind', function (e) {
                    var id = $(this).attr('data-id');
                    var that = this;
                    $.getJSON('/member/islogin', function (data) {
                        if (data.result.login == false) {
                            userLogin();
                        } else if (data.result.datas.isBind == false) {
                            location.href = "/member/bind";
                        } else {
                            $.ajax({
                                'url': '/flashpromotion/remind/' + id,
                                'type': 'post',
                                'data': {},
                                'dataType': 'json',
                                'success': function (data, status) {
                                    if (data.result.status == 1) {
                                        var html = '<div class="popup-content">\
                                    <div id="success-info" class="remind-content">\
                                        <div class="popup-remind-success-info"><i></i><p>开抢提醒</p></div>\
                                        <div class="popup-text">活动开始前<span>3</span>分钟，<br>将通过短信形式通知你</div>\
                                        <button type="button" class="button button-black button-small" onclick="popupModalClose()">确定</button>\
                                    </div>\
                                    </div>';
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
            conference: function () {
                $('.limit-time-item').on('click', function () {
                    var obj = $(this);
                    obj.addClass('on').siblings().removeClass('on');
                    var id = obj.attr('data-id');
                    var url = '/flashpromotion/products/list?id=' + id;
                    $.ajax({
                        'url': url,
                        'type': 'get',
                        'data': {},
                        'dataType': 'json',
                        'success': function (data) {
                            if (data.result.status == 1) {
                                var html_title = template('limit-title', data);
                                var html_content = template('limit-list', data);
                                $('.limit-title').html(html_title);
                                $('.limit-content').html(html_content);
                                $('.load-more').attr('data-url', url);
                                $('.load-more').attr('data-page', data.pager.pageNumber);
                                $('.load-more').attr('data-total', data.pager.pageCount);
                                $('.flash-load-more').show().utilScrollLoad();
                                $('.limit-countdown').countdown()
                            } else {
                                $.flashTip({position: 'center', type: 'success', message: data.result.message});
                            }
                        }
                    });
                });
            },
            adBanner: function () {
                $.get('/adresource/FLASHPROMOTION/FLASHPRODUCT.json', function (data) {
                    var html = template('ad-banner', data.adResource);
                    $('#adbanner').html(html);
                });
            },
            init: function () {
                this.adBanner();
                this.remind();
                this.conference();

                var mySwiper2 = new Swiper('#swiper-container2', {
                    slidesPerView: 'auto',
                    spaceBetween: 8,
                    initialSlide:${currentIndex},
                    centeredSlides: true,
                    preventLinksPropagation: true,
                    slideToClickedSlide: true
                });
                var id = GetQueryString("id");
                if (id != null && id.toString().length > 1) {
                    $('.limit-time-item').each(function (index) {
                        var dataId = $(this).attr('data-id');
                        if (dataId == id) {
                            $(this).click();
                            mySwiper2.slideTo(index);
                        }
                    });
                } else {
                    $('.limit-time-item.on').trigger('click');
                }
            }
        }
        limit_buy.init();
        template.helper('formatDate', function (str) {
            var date = new Date(str);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var minute = date.getMinutes();
            var second = date.getSeconds();
            var hour = date.getHours();
            if (month < 10) month = '0' + month;
            if (day < 10) day = '0' + day;
            if (hour < 10) hour = '0' + hour;
            if (minute < 10) minute = '0' + minute;
            if (second < 10) second = '0' + second;
            return year + "-" + month + "-" + day + ' ' + hour + ':' + minute + ':' + second;
        });
    </script>
<@m.page_footer />