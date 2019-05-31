<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='拍卖商品' title='D2C-拍卖' css='auction'/>
<#assign productList = result.datas.pager>
<div class="auction-product-list" id="load-more-product">
    <div class="auction-mymargin"><a href="/auction/member/mymargin"><img
                    src="//static.d2c.cn/img/topic/180510/auction/images/button_myauction@3x.png" width="100%" alt=""/></a>
    </div>
    <#if productList.totalCount gt 0>
        <#list productList.list as productItem>
            <div class="product-item auction-flex">
                <div class="product-item-img">
                    <a href="/auction/product/${productItem.id}">
                        <img src="${picture_base}${productItem.product.productImageList[0]}" width="100%" alt=""/>
                        <span class="product-num">${productItem.num}次出价</span>
                    </a>
                </div>
                <div class="product-item-cont">
                    <a href="/auction/product/${productItem.id}" style="display:block;">
                        <div class="product-title">${productItem.title}</div>
                        <div class="product-margin">
                            保证金：&yen; ${productItem.margin?string("currency")?substring(1)}</div>
                        <div class="product-current"><#if productItem.statusName == '正在拍卖'>当前<#elseif productItem.statusName == '即将拍卖'>起拍<#else>结拍</#if>
                            价：<span style="font-weight:bold;color:#F21A1A;">&yen; <span
                                        style="font-size:24px;">${productItem.currentPrice?string("currency")?substring(1)}</span></span>
                        </div>
                        <#if productItem.statusName == '正在拍卖' || productItem.statusName == '即将拍卖'>
                            <div class="product-status">
                                <#if productItem.statusName == '正在拍卖'>
                                    <span class="status-text">拍卖中</span>
                                    <span class="statu-time count-down" data-type="split-time"
                                          data-endtime="${productItem.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                                          data-defined="second">
						<span class="day down-time">00</span>天 
						<span class="hour down-time">00</span> :
						<span class="minute down-time">00</span> :
						<span class="second down-time" style="color:#F21A1A;">00</span>
					结拍</span>
                                </#if>
                                <#if productItem.statusName == '即将拍卖'>
                                    <span class="status-text" style="background:#5C81C3;">未开始</span>
                                    <span class="statu-time count-down" data-type="split-time"
                                          data-endtime="${productItem.beginDate?string("yyyy/MM/dd HH:mm:ss")}"
                                          data-defined="second">
						<span class="day down-time">00</span>天 
						<span class="hour down-time">00</span> : 
						<span class="minute down-time">00</span> : 
						<span class="second down-time" style="color:#F21A1A;">00</span>
					开拍</span>
                                </#if>
                            </div>
                        <#else>
                            <div class="product-status">
                                <span class="status-text" style="background:#CCC;">已结束</span>
                            </div>
                        </#if>
                    </a>
                </div>
            </div>
        </#list>
    <#else>
        <div style="position:absolute;left:50%;top:50%;transform:translate(-50%, -50%);">暂无拍卖商品哦~</div>
    </#if>
</div>
<share data-title="1元起拍 等你来出价！" data-desc="臻选精品 限量拍卖"
       data-pic="//static.d2c.cn/img/home/160627/images/headpic.png"></share>
<div class="load-more scroll-load-more" data-target="load-more-product" template-id="auction-list-template"
     data-url="/auction/list" data-page="${productList.pageNumber}" data-total="${productList.pageCount}">点击加载更多
</div>
<script id="auction-list-template" type="text/html">
    {{each list as productItem}}
    <div class="product-item auction-flex">
        <div class="product-item-img">
            <a href="/auction/product/{{productItem.id}}">
                <img src="${picture_base}{{productItem.product.productImageList[0]}}" width="100%" alt=""/>
                <span class="product-num">{{productItem.num}}人出价</span>
            </a>
        </div>
        <div class="product-item-cont">
            <a href="/auction/product/{{productItem.id}}" class="product-title">{{productItem.title}}</a>
            <div class="product-margin">保证金：&yen; {{toFixed(productItem.margin)}}</div>
            <div class="product-current">当前价：<span style="font-weight:bold;color:#F21A1A;">&yen; <span
                            style="font-size:24px;">{{toFixed(productItem.currentPrice)}}</span></span></div>
            {{if productItem.statusString == '1' || productItem.statusString == '0'}}
            <div class="product-status">
                {{if productItem.statusString == '1'}}
                <span class="status-text">拍卖中</span>
                <span class="statu-time">{{formatDate(productItem.endDate)}} 结拍</span>
                {{/if}}
                {{if productItem.statusString == '0'}}
                <span class="status-text" style="background:#5C81C3;">未开始</span>
                <span class="statu-time">{{formatDate(productItem.beginDate)}} 开拍</span>
                {{/if}}
            </div>
            {{else}}
            <div class="product-status">
                <span class="status-text" style="background:#CCC;">已结束</span>
            </div>
            {{/if}}
        </div>
    </div>
    {{/each}}
</script>
<script>
    if (!document.referrer) $('.return-home').show();
    template.helper('formatDate', function (str) {
        var date = new Date(str),
            year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate(),
            hour = date.getHours(),
            minute = date.getMinutes(),
            second = date.getSeconds();
        if (month < 10) month = '0' + month;
        if (day < 10) day = '0' + day;
        if (hour < 10) hour = '0' + hour;
        if (minute < 10) minute = '0' + minute;
        if (second < 10) second = '0' + second;
        return hour + ":" + minute + ":" + second;
    });
    template.helper('toFixed', function (str) {
        return str.toFixed(2);
    });
    //分享
    $('.share-button').on('touchstart', function () {
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
    $(document).on('touchstart', '.share-wechat-guide .mask-close', function () {
        $('.share-wechat-guide').removeClass('show');
        return false;
    });
</script>
