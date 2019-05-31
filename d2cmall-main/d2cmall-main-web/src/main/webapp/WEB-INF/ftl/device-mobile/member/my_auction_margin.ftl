<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="拍卖记录" title='拍卖记录' css="auction" />
<div class="margin-list">
    <#if pager.totalCount gt 0>
        <#list pager.list as marginItem>
            <div class="margin-item">
                <div class="clearfix" style="padding-bottom:5px;border-bottom:1px solid rgba(0,0,0,.04);">
                    <div class="margin-item-img">
                        <a href="/auction/product/${marginItem.auctionId}"><img
                                    src="${picture_base}${marginItem.product.productImageList[0]}"
                                    style="width:70px;height:105px;" alt=""/></a>
                    </div>
                    <div class="margin-item-cont">
                        <div style="line-height:1.125rem;">
                            <#if marginItem.auctionProduct.status == "2"><span
                                    class="auction-icon">${marginItem.auctionProduct.statusName}</span><#elseif marginItem.auctionProduct.status == "0">
                                <span class="auction-icon">${marginItem.auctionProduct.statusName}</span><#else><span
                                    class="auction-icon">${marginItem.auctionProduct.statusName}</span></#if>
                            <span style="color:rgba(33,33,33,.3);font-size:12px;">${marginItem.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                            <span style="color:#F21A1A;font-size:12px;float:right;">${marginItem.statuText}</span>
                        </div>
                        <a href="/auction/product/${marginItem.auctionId}"
                           class="margin-title">${marginItem.auctionTitle}</a>
                        <div class="margin-bond"><a
                                    href="/auction/product/${marginItem.auctionId}">保证金：&yen;${marginItem.margin?string("currency")?substring(1)}</a>
                        </div>
                        <div class="margin-offer cleearfix">
                            <#if marginItem.auctionProduct.currentPrice != null>
                                <span style="font-weight:bold;vertical-align:middle;float:left;">
					当前价：&yen;<span style="font-size:18px;">${marginItem.auctionProduct.currentPrice?string("currency")?substring(1)}</span>
					</span>
                            <#else>
                                <span style="font-weight:bold;font-size:14px;">暂无出价</span>
                            </#if>
                            <#if marginItem.status == 2>
                                <form name="form-auction" id="form-auction"
                                      action="/auction/margin/confirm/${marginItem.marginSn}" method="POST"></form>
                                <span class="margin-status-item pay-item"
                                      data-marginSn="${marginItem.marginSn}">支付尾款</span>
                            </#if>
                            <#if marginItem.status == 8><span  style="margin-left:10px;"
                                                               class="margin-status-item confirm-item"
                                                               data-id="${marginItem.auctionProduct.auctionMarginId}">
                                    确认收货</span><span class="margin-status-item"><a
                                        href="//m.kuaidi100.com/index_all.html?type=${marginItem.deliveryCorpName}&postid=${marginItem.deliverySn}">查看物流</a>
                                </span></#if>
                            <#if marginItem.status == 10><span class="margin-status-item"><a
                                        href="//m.kuaidi100.com/index_all.html?type=${marginItem.deliveryCorpName}&postid=${marginItem.deliverySn}">查看物流</a>
                                </span></#if>
                        </div>
                    </div>
                </div>
                <div class="offer-record" data-id="${marginItem.auctionId}">
                    <span class="offer-record-header">我的出价记录</span>
                    <div class="offer-record-list" style="display:none;min-height:3rem;position:relative;"></div>
                </div>
            </div>
        </#list>
    <#else>
        <div style="position:absolute;left:50%;top:50%;transform:translate(-50%, -50%);">暂无拍卖纪录哦~</div>
    </#if>
</div>
<script id="offer-record-template" type="text/html">
    {{each list as offerList}}
    <div class="offer-record-item auction-flex">
        <div style="font-size:14px;color:rgba(0,0,0,.5);">&yen;{{offerList.offer}}</div>
        <div style="font-size:14px;color:rgba(0,0,0,.5);">{{offerList.statusText}}</div>
        <div style="font-size:10px;color:rgba(0,0,0,.3);">{{formatDate(offerList.modifyDate)}}</div>
    </div>
    {{/each}}
</script>
<script>
    if (!document.referrer) $('.return-home').show();
    var flag = true;
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
        return month + "月" + day + "日 " + hour + ":" + minute;
    });
    $(document).on(click_type, '.offer-record', function () {
        var obj = $(this);
        var id = obj.attr('data-id');
        flag = !flag;
        $.getJSON('/auction/member/myoffer?auctionId=' + id, function (data) {
            if (data.pager.totalCount > 0) {
                var html = template('offer-record-template', data.pager);
                obj.find('.offer-record-list').html(html)
            } else {
                obj.find('.offer-record-list').html('<div style="position:absolute;left:50%;top:50%;transform:translate(-50%, -50%);color:rgba(0,0,0,.5);font-size:14px;">暂无出价记录哦~</div>')
            }
        });
        if (flag) {
            $('.offer-record-header').addClass('offer-up');
        } else {
            $('.offer-record-header').removeClass('offer-up');
        }
        obj.find('.offer-record-list').toggle();
    });
    $(document).on(click_type, '.margin-status-item.pay-item', function () {
        $('#form-auction').submit();
    });
    $(document).on(click_type, '.margin-status-item.confirm-item', function () {
        var obj = $(this);
        var id = obj.attr('data-id');
        $.post('/auction/received/' + id + '.json', function (data) {
            $.flashTip({position: 'bottom', type: 'success', message: data.result.message});
            location.reload();
        })
    });

</script>