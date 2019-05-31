<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="砍价商城" css="bargain" description="限时优惠！和好友一起来砍价" />
<#if !m.FROMAPP>
    <@m.app_download_bar fixed="false" />
</#if>

<div><img src="https://static.d2c.cn/img/promo/bargin/share_icon.jpg" style="display:none;width:100%;"/></div>
<div style="position:relative;height:100%;">
    <#if !m.FROMAPP>
        <div class="return-home">
            <a href="/">回首页</a>
        </div>
    </#if>
    <style>
        .bargin-info a:visited {
            color: #444;
        }
    </style>
    <div class="swiper-container" id="bargain-slide">
        <div class="swiper-wrapper" style="align-items:center;">
        </div>
        <div class="swiper-pagination"></div>
    </div>
    <div class="bargin-content" style="padding:0 15px 15px 15px;background-color:#FFF;">
        <div class="bargin-navs flex">
            <div class="nav-itemss" data-url="/bargain/promotion/list?status=1" data-status="1">砍价中</div>
            <div class="nav-itemss" data-url="/bargain/promotion/list?status=0" data-status="1">即将开始</div>
            <#if m.LOGINMEMBER.id!=null && m.LOGINMEMBER.d2c>
                <div class="nav-itemss" data-url="/bargain/mine/list" data-status="2">我的砍价</div></#if>
        </div>
        <div class="bargin-list" id="load-more-product" style="min-height:5em;"></div>
    </div>
</div>
<div class="load-more bargain-load-more" data-target="load-more-product" template-id="product-barginlist"
     style="display:none;">点击加载更多
</div>
<share data-title="快跟我一起来砍价，明星同款最低砍到0元" data-pic="https://static.d2c.cn/img/promo/bargin/share_icon.jpg"
       data-desc="限时限量，最低可以砍到0元"></share>
<script id="my-barginlist" type="text/html">
    {{if list.length>0}}
    {{each list as value i}}
    <div class="bargin-item">
        <div class="bargin-product">
            <a href="/bargain/personal/{{value.id}}">
                <img src="//img.d2c.cn/{{value.bargain.coverPic}}" style="width:100%">
            </a>
            <input type="hidden" value="{{value.originalPrice}}" class="originalPrice"/>
            <input type="hidden" value="{{value.bargain.minPrice}}" class="minPrice"/>
            <input type="hidden" value="{{value.price}}" class="price"/>
            {{if value.statusName=='砍价成功' || value.statusName=='砍价结束'}}
            <div class="my-bargin-mask" onclick="location.href='/bargain/personal/{{value.id}}'">
                <div class="mask-tips">
                    <div>距离最后支付时间仅剩</div>
                    <div style="color:#F23365;font-size:18px;line-height:26px;" class="count-down alowtime"
                         data-type="split-time" data-endTime="{{value.bargain.endDate}}" data-musttime="must"><span>
                    <span class="hour down-time">00</span> <span class="time-split">:</span> <span
                                    class="minute down-time">00</span> <span class="time-split">:</span> <span
                                    class="second down-time">00</span></span></div>
                    <div>超过时间将自动恢复原价，请尽快支付~</div>
                </div>
            </div>
            {{/if}}
        </div>
        <div class="bargin-info"><a href="/bargain/personal/{{value.id}}">{{value.bargain.name}}</a></div>
        <div style="padding:0 10px">
            <div class="my-bragin-press">
                <div class="prsss-price flex">
                    <div>原价:¥{{value.originalPrice}}</div>
                    <div>底价:¥{{value.bargain.minPrice}}</div>
                </div>
                <div class="pic-press" style="position:relative">
                    <div class="progress-bar">
                        <div class="progress-act-bar">
                            <div class="progress-tip">{{value.price}}</div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="my-bragin-price flex" style="justify-content:space-between;">
                <div class="my-bragin-status">{{value.statusName}}</div>
                <div class="my-bragin-lsat">
                    {{if value.statusName=='支付超时'}}
                    底价<span style="padding-left:5px;font-size:12px;font-weight:bold">¥</span><span
                            style="font-size:20px;font-weight:bold">{{value.bargain.minPrice}}</span>
                    {{else}}
                    砍后价<span style="padding-left:5px;font-size:12px;font-weight:bold">¥</span><span
                            style="font-size:20px;font-weight:bold">{{value.price}}</span>
                    {{/if}}

                    {{if value.statusName=='购买成功'}}
                    <div class="my-bragin-button" onclick="location.href='/bargain/promotion/list?status=1'">看看其他</div>
                    {{else if value.statusName=='等待支付'}}
                    <div class="my-bragin-button" onclick="gopay()">去支付</div>
                    {{else if value.statusName=='支付超时'}}
                    <div class="my-bragin-button" onclick="location.href='/bargain/promotion/list?status=1'">看看其他</div>
                    {{else}}
                    <a class="my-bragin-button" href="/bargain/personal/{{value.id}}">去购买</a>
                    {{/if}}
                </div>
            </div>
        </div>
    </div>
    {{/each}}
    {{else}}
    <div class="no-result"><img src="//static.d2c.cn/img/promo/bargin/icon_mine_empty.png" style="width:50%;"/></div>
    {{/if}}
    <script>
        setTimeout(function () {
            $.each($(".bargin-item"), function (i, d) {
                if ($(d).find('.count-down').size() > 0) {
                    var time = (new Date(parseInt($(d).find('.count-down').attr('data-endTime'))).getTime() + 86400000);
                    $(d).find('.count-down').attr('data-endtime', new Date(time));
                    setTimeout(function () {
                        $('.alowtime').countdown()
                    }, 500);
                }
                var originalPrice = parseInt($(this).find('.originalPrice').val());
                var minPrice = parseInt($(this).find('.minPrice').val());
                var price = parseInt($(this).find('.price').val())
                var price = Math.abs(originalPrice - price);
                var tprice = Math.abs(originalPrice - minPrice);
                var percent = ((price / tprice) * 100);
                $(d).find('.progress-act-bar').width(percent + '%');
                if (percent < 6) {
                    $(d).find('.progress-tip').addClass('begin');
                }
                if (percent > 94) {
                    $(d).find('.progress-tip').addClass('end');
                }
            });
        }, 200)
</script>
    </script>
    <
    script
    id = "product-barginlist"
    type = "text/html" >
        {
    {
        if list.length > 0}
    }
    {
        {
            each
            list as value
            i
        }
    }
    <
    div
    class
    = "bargin-item" >
        < div
    class
    = "bargin-product" >
        < a
    href = "/bargain/detail/{{value.id}}" >
        < img
    src = "//img.d2c.cn/{{value.coverPic}}"
    style = "width:100%" >
        < /a>
        < div
    class
    = "bargin-title flex" >
        {
    {
        if statued == 0 && value.status == 1}
    }
    <
    p
    class
    = "count-down"
    data - type = "split-time"
    data - endtime = "{{value.beginDate}}"
    data - musttime = "must" > 距离开始仅剩
    :<
    span
    class
    = "white" >
        < span
    class
    = "hour down-time" > 00 < /span> <span class="time-split">:</s
    pan >
    < span
    class
    = "minute down-time" > 00 < /span><span class="time-split">:</s
    pan >
    < span
    class
    = "second down-time" > 00 < /span></s
    pan > < /p>
    {
        {else
            if statued == 1 && value.status == 2}
    }
    <
    p
    class
    = "count-down"
    data - type = "split-time"
    data - endtime = "{{value.endDate}}"
    data - musttime = "must" > 距离结束还剩
    :<
    span
    class
    = "white" >
        < span
    class
    = "hour down-time" > 00 < /span> <span class="time-split">:</s
    pan >
    < span
    class
    = "minute down-time" > 00 < /span><span class="time-split">:</s
    pan >
    < span
    class
    = "second down-time" > 00 < /span></s
    pan > < /p>
    {
        {else
            if (statued == 1 && value.status == 0) ||
            statued == 0 && value.status == 0
        }
    }
    <
    p > 活动已砍完 < /p>
    {
        {
            /if}}
            {
                {
                    if statued == 1 && value.status != 1}
            }
        <
            p > < span
        class
            = "white" > {
            {
                value.virtualMan + value.actualMan
            }
        }<
            /span>人参与</
            p >
            {
            {
                /if}}
                < /div>
                < /div>
                < div
            class
                = "bargin-info" >
                    < a
                href = "/bargain/detail/{{value.id}}" > {
                {
                    value.name
                }
            }<
                /a>
                < /div>
                < div
            class
                = "bargin-price" >
                    < p
            class
                = "float:left;" >
                    < span
            class
                = "fz-14"
                style = "color:rgba(0,0,0,.54)" > 底价 < /span><span style="font-weight:bold;padding-left:5px;">¥&nbsp;</s
                pan > < span
            class
                = "fz-20"
                style = "font-weight:bold;color:rgba(0,0,0,.87)" > {
                {
                    value.minPrice
                }
            }<
                /span>
                < span
            class
                = "origin"
                style = "text-decoration:line-through;padding-left:15px;" > & nbsp;
                原价 & nbsp;
                ¥ & nbsp;
                {
                    {
                        value.product.minPrice
                    }
                }
            <
                /span>
                < /p>
                {
                    {
                        if statued == 1 && value.status == 0}
                }
            <
                button
            class
                = "bargin-but but-grey"
                disable
                onclick = "javascript:alert('该商品砍完啦，请砍价其他商品哟~')" > 已砍完 < /button>
                {
                    {else
                        if statued == 0 && value.status == 1}
                }
            <
                button
            class
                = "bargin-but remind"
                data - id = "{{value.id}}" > 提醒我 < /button>
                {
                    {else
                        if statued == 0 && value.status == 0}
                }
            <
                button
            class
                = "bargin-but but-grey"
                disable
                onclick = "javascript:alert('该商品砍完啦，请砍价其他商品哟~')" > 已砍完 < /button>
                {
                    {else
                        if statued == 0 && value.status == -1}
                }
            <
                button
            class
                = "bargin-but but-grey"
                disable > 已下架 < /button>
                {
                    {else
                        if statued == 1 && value.status == 2}
                }
            <
                a
            class
                = "bargin-but"
                href = "/bargain/detail/{{value.id}}" > 去砍价 < /a>
                {
                    {
                        /if}}
                        < /div>
                        < /div>
                        {
                            {
                                /each}}
                                {
                                    {else
                                    }
                                }
                            <
                                div
                            class
                                = "no-result" > < img
                                src = "//static.d2c.cn/img/promo/bargin/icon_mine_empty.png"
                                style = "width:50%;" / > < /div>
                                {
                                    {
                                        /if}}
                                        < script >
                                        setTimeout(function () {
                                            $.each($(".count-down"), function (i, d) {
                                                $(d).countdown()
                                            });
                                        }, 200)
    </script>
    </script>

    <
    script >
    if (!document.referrer) $('.return-home').show();

    var page_elem = {
        init: function () {
            $.get('/ad/MAIN/BARGAIN.json', function (data) {
                if (data.adResource) {
                    var str = "";
                    data.adResource.picList.map(function (item, index) {
                        if (data.adResource.urlList[index] != '') {
                            str += '<div class="swiper-slide"><a href="' + data.adResource.urlList[index] + '"><img src="${picture_base}' + item + '"></a></div>'
                        } else {
                            str += '<div class="swiper-slide"><img src="${picture_base}' + item + '"></div>'
                        }

                    });
                    $('.swiper-wrapper').html(str);
                    var mySwiper = new Swiper('.swiper-container', {
                        pagination: {
                            el: '.swiper-pagination',
                        },
                        observer: true,
                        loop: true
                    })
                }
            });
            this.lisen();
            this.nav();
        },
        lisen: function () {
            var that = this;
            $(document).on(click_type, '.remind', function () {
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
                            'url': '/bargain/remind/' + id,
                            'type': 'get',
                            'data': {},
                            'dataType': 'json',
                            'success': function (data, status) {
                                if (data.result == 1) {
                                    var html = '<div class="popup-content">\
		       				 		<div id="success-info" class="bargain-help-content">\
		       				 			<div class="popup-help-success-info"><i style=" margin: -3em auto 0.5em auto;width: 3em;height: 3em;"></i><p>设置成功</p></div>\
		       				 			<div class="popup-text">活动开始前30分钟，<br>将通过短信形式通知你</div>\
										<button type="button" class="button button-black button-small" onclick="popupModalClose()">确定</button>\
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
        nav: function () {
            $(document).on(click_type, '.nav-itemss', function () {
                var url = $(this).attr('data-url');
                var status = $(this).attr('data-status');
                $(this).addClass('on').siblings('.nav-itemss').removeClass('on');
                $.getJSON(url, function (res) {
                    if (status == 1) {
                        var data = res.list;
                        var statued = res.searcher.status;
                        data.statued = statued;
                    } else {
                        var data = res.pager;
                    }
                    data.list.map(function (item) {
                        item.beginDate = new Date(item.beginDate).toString();
                        item.endDate = new Date(item.endDate).toString();
                        return item;
                    });
                    if (status == 1) {
                        var html = template('product-barginlist', data);
                        $('.bargin-list').html(html);
                        $('.load-more').attr('data-url', url);
                        $('.load-more').attr('data-page', data.pageNumber);
                        $('.load-more').attr('data-total', data.pageCount);
                        data.list.length > 0 && $('.bargain-load-more').show().utilScrollLoad();
                    } else {//我的砍价详情
                        $('.bargain-load-more').hide();
                        var html = template('my-barginlist', data);
                        $('.bargin-list').html(html);
                    }
                });
            });
            $('.nav-itemss:eq(0)').trigger(click_type);
        }
    }
    page_elem.init();
    window.onload = function () {
        var event = document.createEvent('Events');
        event.initEvent('touchstart', true, true);
        $('.nav-itemss:eq(0)').get(0).dispatchEvent(event);
    }
</script>
<script>
    function gopay() {
        <#if browser=='wechat'&& wechat >
        wx.ready(function () {
            if (window.__wxjs_environment === 'miniprogram') {
                wx.miniProgram.navigateTo({url: 'pages/member/order/list'});
            } else {
                location.href = '/member/order'
            }
        })
        <#else>
        location.href = '/member/order';
        TDAPP.onEvent('砍价商品购买');
        </#if>
    }

</script>
<@m.page_footer />