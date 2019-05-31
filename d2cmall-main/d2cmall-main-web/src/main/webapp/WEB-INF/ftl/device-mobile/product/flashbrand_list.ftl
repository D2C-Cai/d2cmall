<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title="D2C快抢" css="swiper.min|flashpromotion"  service="12.12" js="utils/swiper.min" keywords="全球好设计,设计师品牌,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="全球好设计  整点限时限量秒杀，先到先得！" service='false'/>
<img src="https://static.d2cmall.com/img/promo/limit/limit.jpg" style="display:none"/>
<#assign currentIndex=list?size-1>
<#list list as list>
    <#if list.id==currentId>
        <#assign currentIndex=list_index>
    </#if>
</#list>
<div class="swiper-container swiper-container-horizontal limit-banner" id="swiper-container1" style="height:160px;">
    <div class="swiper-wrapper" id="adbanner">

    </div>
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
                        <div><#if lists.sessionName?exists && lists.sessionName!=''>${lists.sessionName}<#elseif currentId==lists.id && lists.startDate?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") lt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")>开抢中<#else>${lists.statusName}</#if> </div>
                    </div>
                </#if>
            </#list>
        </#if>
    </div>
</div>
<div class="limit-content" style="margin-bottom:0.875rem">
    <#if list?size==0>
        <div style="text-align:center;margin:10px 0;">暂无限时购</div>
    </#if>
</div>

<div style="height:3.125rem">
    <div class="fixed-taber flex">
        <a href="/flashpromotion/product/session"><img src="//static.d2cmall.com/img/promo/limit/bar_timeshop.png"></a>
        <a href="/flashpromotion/brand/session"><img
                    src="//static.d2cmall.com/img/promo/limit/bar_super_chosen.png"></a>
    </div>
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

        })
</script>
    </script>


    <
    script
    id = "limit-list"
    type = "text/html" >
        < div
    class
    = "limit-title flex"
    style = "padding:0 15px;" >
        < p > 精选大牌，限时快抢
    < /p>
    {
        {
            if brands[0].statusName == "即将开始"}
    }
    <
    div
    class
    = "limit-countdown count-down"
    data - type = "split-time"
    data - endtime = "{{brands[0].startDate}}"
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
            if brands[0].statusName == "抢购中" || (brands[0].statusName == "已开抢" && !brands[0].end)}
    }
    <
    div
    class
    = "limit-countdown count-down"
    data - type = "split-time"
    data - endtime = "{{brands[0].endDate}}"
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
            {
                {
                    each
                    brands as value
                    i
                }
            }
        <
            div
        class
            = "limit-brand-item" >
                < div
        class
            = "limit-brand-img"
            style = "position: relative" >
                < a
            href = "/flashpromotion/products/list?id={{value.id}}" >
                < img
            src = "${picture_base}{{value.brandPic}}"
            style = "width:100%" >
                < /a>
                < /div>
                < div
        class
            = "swiper-container swiper-container-horizontal swiper-container3" >
                < div
        class
            = "swiper-wrapper"
            style = "margin-top:.3rem;" >
                {
            {
                each
                value.products as product
                i
            }
        }
        <
            div
        class
            = "limit-brand-product swiper-slide" >
                < a
            href = "/product/{{product.id}}" >
                < img
            src = "${picture_base}{{product.productImageCover}}!300"
            style = "width:100%;" >
                < p
        class
            = "text-center" > & yen;
            {
                {
                    product.flashPrice
                }
            }
        &
            nbsp;
        &
            nbsp;
        <
            span
        class
            = "grey small"
            style = "text-decoration:line-through;font-weight:normal" > & yen;
            {
                {
                    product.minPrice
                }
            }
        <
            /span></
            p >
            < /a>
            < /div>
            {
                {
                    /each}}
                    < /div>
                    < /div>

                    < /div>
                    {
                        {
                            /each}}
                            < script >
                            if ($('.count-down').size() > 0) {
                                var time = (new Date(parseInt($('.count-down').attr('data-endTime'))).getTime());
                                $('.count-down').attr('data-endtime', new Date(time));
                                setTimeout(function () {
                                    $('.count-down').countdown()
                                }, 100);
                            }
                            var mySwiper3 = new Swiper('.swiper-container3', {
                                slidesPerView: 'auto',
                                spaceBetween: 8,
                                slidesPerView: 3,
                                slidesPerGroup: 3,
                            })

    </script>
    </script>


    <
    script >
    $(function () {
        var mySwiper2 = new Swiper('#swiper-container2', {
            slidesPerView: 'auto',
            spaceBetween: 8,
            initialSlide:${currentIndex},
            centeredSlides: true,
            preventLinksPropagation: true,
            slideToClickedSlide: true,
        })
    })
</script>
<script>
    var limit_buy = {
        conference: function () {
            $('.limit-time-item').on('click', function () {
                $(this).addClass('on').siblings().removeClass('on');
                var id = $(this).attr('data-id');
                $.ajax({
                    'url': '/flashpromotion/brand/list?id=' + id,
                    'type': 'get',
                    'data': {},
                    'dataType': 'json',
                    'success': function (data, status) {
                        if (data.result.status == 1) {
                            var html = template('limit-list', data);
                            $('.limit-content').html(html);
                        } else {
                            $.flashTip({position: 'center', type: 'success', message: data.result.message});
                        }

                    }
                });

            })
        },
        adBanner: function () {

            $.get('/adresource/FLASHPROMOTION/FLASHBRAND.json', function (data) {
                var html = template('ad-banner', data.adResource);
                $('#adbanner').html(html);
            });

        },
        init: function () {
            this.conference();
            this.adBanner();
            $('.limit-time-item.on').trigger('click');
        }

    }
    limit_buy.init();
</script>
<@m.page_footer />