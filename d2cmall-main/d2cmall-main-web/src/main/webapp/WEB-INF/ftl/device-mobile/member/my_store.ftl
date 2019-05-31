<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='${partnerStore.name}' js='utils/iscroll-lite' css='store' service="false" />
<share data-title="${partnerStore.name}"
       data-url="${base}/partner/store?partnerId=${partnerStore.partnerId}&parent_id=${partnerStore.partnerId}"
       data-pic="${partnerStore.pic}"></share>
<div class="shop-head flex space-around">
    <div class="flex">
        <div class="shop-logo"><img
                    src="<#if partnerStore.pic><#if partnerStore.pic?index_of('//img.d2c.cn') == -1 && partnerStore.pic?index_of('https') == -1 &&partnerStore.pic?index_of('//static.d2c.cn') == -1>//img.d2c.cn<#else>${partnerStore.pic}</#if><#else>//static.d2c.cn/img/home/160627/images/headpic.png</#if>"
                    style="width:5em;height:5em;"></div>
        <div class="shop-text">
            <div style="font-size:1.2em;"><#if partnerStore.name>${partnerStore.name}<#else>无名氏</#if></div>
            <div style="color:#999;">肯定是好货啦！</div>
        </div>
    </div>
    <div class="share">
        <view class="icon icon-qrcode" bindtap="showQRcode"></view>
        <div class="icon icon-share-re share-button"
             data-share="<#if partnerStore.name>${partnerStore.name}<#else>无名氏</#if>,${base}/partner/store?partnerId=${partnerStore.partnerId}&parent_id=${partnerStore.partnerId},<#if partnerStore.pic><#if partnerStore.pic?index_of('//img.d2c.cn') == -1 && partnerStore.pic?index_of('https') == -1 &&partnerStore.pic?index_of('//static.d2c.cn') == -1>//img.d2c.cn<#else>${partnerStore.pic}</#if><#else>//static.d2c.cn/img/home/160627/images/headpic.png</#if>"></div>
    </div>
    <#if partnerStore.memberId==m.LOGINMEMBER.id>
        <div class="shop-edit">
            <a href="/partner/create/store" style="color:#FFF;">设置自己的店铺</a>
        </div>
    </#if>
</div>

<div id="tab-wraper"></div>
<div class="lazyload">
    <div class="list clearfix" id="load-more-product"></div>
    <div class="load-more" data-url="" data-target="load-more-product" template-id="list-template" data-page=""
         data-total="">点击加载更多
    </div>
</div>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="item item-horizontal">
        <div class="img">
            <a href="/product/{{value.id}}?parent_id=${partnerStore.partnerId}"><img
                        src="${static_base}/m/img/blank100x157.png"
                        data-image="${picture_base}{{value.productImageCover}}!300" alt=""/></a>
        </div>
        <div class="info">
            <p class="title">{{if value.topical==1}}<strong style="color:#FD555D">[主推]</strong>{{/if}}{{value.name}}</p>
            <#if partnerStore.memberId==m.LOGINMEMBER.id>
                <p>&yen; {{value.currentPrice}}</p>
            <#else>
                <p><span class="price">&yen; {{value.currentPrice}}</span>&nbsp;&nbsp;{{if (value.currentPrice <
                    value.originalPrice)}}<s>&yen; {{value.originalPrice}}</s>{{/if}}</p>
            </#if>
        </div>
    </div>
    {{/each}}
</script>

<script id="tab-template" type="text/html">
    <div class="tab-holder" id="tab-holder">
        <div class="tab">
            {{each list as value i}}
            <a href="javascript:" data-index="{{i}}">{{value.name}}</a>
            {{/each}}
        </div>
    </div>
</script>
<script>
    //type的值为1则表示大类，2则表示小类，3表示tagId
    var sectionArray = new Array(
        {'id': 0, 'name': '全部', 'type': '0'},
        {'id': 1, 'name': '女装', 'type': '1'},
        {'id': 6, 'name': '男装', 'type': '1'},
        {'id': 7, 'name': '童装', 'type': '1'},
        {'id': 3, 'name': '配饰', 'type': '1'},
        {'id': 4, 'name': '生活家居', 'type': '1'}
    );
    var html = template('tab-template', {list: sectionArray});
    $('#tab-wraper').html(html);
    var my_scroll = new IScroll("#tab-holder", {click: true, scrollX: true, scrollY: false});
    //切换tab列表
    $('#tab-wraper a').on('click', function () {
        my_scroll.scrollToElement(this, 1200, true);
        $(this).addClass('on').siblings().removeClass('on');
        var index = $(this).attr('data-index');
        var data = {p: 1};
        if (sectionArray[index].type == 1) {
            data.t = sectionArray[index].id;
        }
        if (sectionArray[index].type == 2) {
            data.c = sectionArray[index].id;
        }
        if (sectionArray[index].type == 3) {
            data.tagId = sectionArray[index].id;
        }
        $.getJSON('/product/pager', data, function (result) {
            var html = template('list-template', result);
            $('#load-more-product').html(html);
            $(".lazyload,.lazyload img").unveil(350);
            delete data.p;
            $('.load-more').attr('data-url', '/product/pager?' + $.param(data));
            $('.load-more').attr('data-page', result.pager.pageNumber);
            $('.load-more').attr('data-total', result.pager.pageCount);
            $('.load-more').addClass('scroll-load-more');
            $('.scroll-load-more').utilScrollLoad();
        });
    });
    $('#tab-wraper a[data-index=0]').trigger('click');

    function hideMask() {
        setTimeout(function () {
            $('.big-qrcode').hide();
        }, 200);
    }

    $('.icon-qrcode').on(click_type, function () {
        var bigQrcode = ['<div class="big-qrcode">',
            '<a class="mask-qrcode" href="javascript:hideMask();"></a>',
            '<div class="qrcode-content">',
            '<img src="<#if partnerStore.pic><#if partnerStore.pic?index_of('//img.d2c.cn') == -1 && partnerStore.pic?index_of('https') == -1 &&partnerStore.pic?index_of('//static.d2c.cn') == -1>//img.d2c.cn<#else>${partnerStore.pic}</#if><#else>//static.d2c.cn/img/home/160627/images/headpic.png</#if>">',
            '</div>',
            '</div>'
        ].join("");
        setTimeout(function () {
            $('body').append(bigQrcode);
        }, 200);
    });
    $('.share-button').on(click_type, function () {
        var parame = $(this).attr('data-share').split(',');
        var share_parame = {
            'title': parame[0],
            'url': parame[1],
            'pic': parame[2],
        };
        <#if browser=='wechat'>
        if ($('.share-wechat-guide').size() == 0) {
            var html = '<div class="share-wechat-guide"><a href="javascript:void(0);" class="mask-close"></a></div>';
            $('body').append(html);
        }
        $('.share-wechat-guide').addClass('show');
        return false;
        <#else>
        location.href = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(parame[1]) + '&title=' + encodeURIComponent(parame[0]) + '&pic=' + encodeURIComponent(parame[2]) + '&appkey=&searchPic=false';
        </#if>
    });
    $(document).on('touchstart', '.share-wechat-guide .mask-close', function () {
        $('.share-wechat-guide').removeClass('show');
        return false;
    });
</script>
<@m.page_footer menu=false />