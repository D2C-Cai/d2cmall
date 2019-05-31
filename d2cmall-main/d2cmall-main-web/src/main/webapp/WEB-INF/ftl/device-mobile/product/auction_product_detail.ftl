<#import "templates/public_mobile.ftl" as m>
<#assign offerList = result.datas.offers>
<#assign productDet = result.datas.product>
<@m.page_header back="拍卖商品" title='${productDet.product.name}' css='auction' js='utils/swiper.min'/>
<style>#popup-modal-outer {
        background: rgba(0, 0, 0, .5) !important;
    }</style>
<div class="auction-detail-container">
    <div class="auction-detail-header auction-flex auction-base" data-id="${productDet.id}">
        <div class="header-price">
            <p style="font-weight:bold;color:#FFBA4B;">&yen;<span
                        style="font-size:24px;">${productDet.currentPrice}</span></>
            <div style="color:#C39B5C;line-height:1rem;">
                <img src="//static.d2c.cn/img/topic/180510/auction/images/pic_dangqianjia@3x.png"
                     style="width:3.57rem;height:1rem;vertical-align:middle;"/>
                <#if productDet.statusName == '即将拍卖'>
                    <span>未开始</span>
                <#else>
                    <span>累计出价次数${offerList.totalCount}次</span>
                </#if>
            </div>
        </div>
        <div class="header-count">
            <#if productDet.statusName == '正在拍卖'>
                <p>距离结束还有</p>
                <div class="count-down" data-type="split-time"
                     data-endtime="${productDet.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                     data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}" data-defined="second">
                    <span class="day down-time">00</span>天
                    <span class="hour down-time">00</span> :
                    <span class="minute down-time">00</span> :
                    <span class="second down-time" style="color:#F21A1A;">00</span>
                </div>
            <#elseif productDet.statusName == '即将拍卖'>
                <p>距离开始还有</p>
                <div class="count-down" data-type="split-time"
                     data-endtime="${productDet.beginDate?string("yyyy/MM/dd HH:mm:ss")}"
                     data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}" data-defined="second">
                    <span class="day down-time">00</span>天
                    <span class="hour down-time">00</span> :
                    <span class="minute down-time">00</span> :
                    <span class="second down-time" style="color:#F21A1A;">00</span>
                </div>
            <#else>
                <p>已结束</p>
                <div class="count-down" data-type="split-time"
                     data-endtime="${productDet.beginDate?string("yyyy/MM/dd HH:mm:ss")}"
                     data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}" data-defined="second">
                    <span class="hour down-time">00</span> :
                    <span class="minute down-time">00</span> :
                    <span class="second down-time" style="color:#F21A1A;">00</span>
                </div>
            </#if>
        </div>
        <div class="header-plus">
            <p>保证金 &yen;${productDet.margin}</p>
            <p>起加额 &yen;${productDet.stepPrice}</p>
        </div>
    </div>
    <div class="auction-swiper-product" style="position:relative;">
        <#if productDet.product.productImageList?size gt 0>
            <div class="swiper-container" id="product-slide">
                <div class="swiper-wrapper">
                    <#list productDet.product.productImageList as image>
                        <div class="swiper-slide">
                            <a href="#"><img src="${picture_base}${image}!600" width="100%" alt=""/></a>
                        </div>
                    </#list>
                </div>
                <div class="swiper-pagination"></div>
            </div>
        </#if>
        <#if offerList.totalCount gt 0>
            <div class="auction-offers-list">
                <#list offerList.list as offerItem>
                    <div class="offers-item">
                        <div class="offers-item-font" style="width:7.8125rem;">${offerItem.memberNick}</div>
                        <div class="offers-item-font" style="width:5.75rem;">&yen; ${offerItem.offer}</div>
                        <div class="offers-item-font">${offerItem.statusText}</div>
                        <div class="offers-item-time">${offerItem.createDate?string("MM/dd HH:mm:ss")}</div>
                    </div>
                </#list>
                <div class="auction-product-open"></div>
            </div>
        <#else>
            <div class="offers-item-none">暂无竞拍的人哦~</div>
        </#if>
    </div>
    <div class="auction-product-text auction-flex">
        <p style="line-height:1.5;font-size:1.125rem;color:rgba(0,0,0,.85);">${productDet.title}</p>
        <!--<div data-url="/member/interest/collection/insert/${productDet.product.id}" id="collect-${productDet.product.id}" check-url="/member/interest/collection/check/${productDet.product.id}" class="button-icon ajax-request" call-back="interestSuccess(${productDet.product.id},'collect')"><i class="icon icon-star-o" style="width:1.75rem;height:1.75rem;"></i></div>-->
    </div>
    <div class="auction-detail-process">
        <div class="auction-flex auction-process-header">
            <p style="font-size:16px;color:rgba(0,0,0,.85);">竞拍流程</p>
            <p style="font-size:14px;color:rgba(0,0,0,.3);" class="auction-rule">规则详情 ></p>
        </div>
        <div class="auction-flex" style="font-size:11px;color:rgba(0,0,0,.5);">
            <span><img src="//static.d2c.cn/img/topic/180510/auction/images/01@3x.png" width="100%"
                       class="detail-process-icon" alt=""/>支付保证金</span>
            <span><img src="//static.d2c.cn/img/topic/180510/auction/images/02@3x.png" width="100%"
                       class="detail-process-icon" alt=""/>参与竞拍</span>
            <span><img src="//static.d2c.cn/img/topic/180510/auction/images/03@3x.png" width="100%"
                       class="detail-process-icon" alt=""/>竞拍得标</span>
            <span><img src="//static.d2c.cn/img/topic/180510/auction/images/04@3x.png" width="100%"
                       class="detail-process-icon" alt=""/>支付尾款</span>
            <span><img src="//static.d2c.cn/img/topic/180510/auction/images/05@3x.png" width="100%"
                       class="detail-process-icon" alt=""/>竞拍成功</span>
        </div>
    </div>
    <div class="product-detail-tab clearfix">
        <a href="javascript:" class="on" data-url="/product/detail/${productDet.product.id}?type=ajax"
           style="width:100%;text-align:left;padding-left:.9375rem;font-weight:bold;font-size:.9em;">商品详情</a>
    </div>
    <div id="product-detail-content" class="product-detail-content">
        <div class="loadding">
            <i class="icon-spinner icon-spin icon-large"></i>正在加载内容...
        </div>
    </div>
    <div style="height:3rem;background:#1F1F1F!important;"></div>
    <div class="auction-fix">
        <#if !m.FROMAPP>
            <a href="javascript:ysf.open();" class="auction-assistant"><i class="icon icon-assistant"
                                                                          style="width:2.1rem;height:1.9rem;margin-top:.2rem;background-size:80%;"></i><br/>客服</a>
        <#else>
            <a href="/customer/service" class="auction-assistant"><i class="icon icon-assistant"
                                                                     style="width:2.1rem;height:1.9rem;margin-top:.2rem;"></i><br/>客服</a>
        </#if>
        <#if productDet.status == 1>
            <#if productDet.doing>
                <#if myMargin?exists && myMargin.status==1>
                    <div class="auction-fix-wrap">
                        <div class="wrap-item">
                            <span id="decrease"
                                  style="border-right:1px solid #EEE;padding:0 .5rem;font-weight:800;">－</span>
                            <span class="num-input" style="color:#B2B2B2;font-size:14px;"
                                  data-price="${productDet.stepPrice}">最低加${productDet.stepPrice}元</span>
                            <span id="increase"
                                  style="border-left:1px solid #EEE;padding:0 .5rem;font-weight:800;">＋</span>
                            <input type="hidden" class="product-count" id="num" name="num" readonly
                                   value="${productDet.currentPrice + productDet.stepPrice}"
                                   data-price="${productDet.currentPrice + productDet.stepPrice}"/>
                        </div>
                    </div>
                    <button type="button" data-type="auction" data-sn="${margin.marginSn}"
                            class="auction-pay auction-button" style="width:33%;">立即出价
                    </button>
                <#else>
                    <button type="button" data-type="margin" class="auction-pay auction-button">支付保证金<br/><span
                                style="color:rgba(255,255,255,.5);font-size:10px;font-weight:normal;padding-top:2px;">若出局保证金将将全额退回</span>
                    </button>
                </#if>
            <#else>
                <#if myMargin?exists>
                    <#if myMargin.status == 2 || myMargin.status == 6 ||  myMargin.status == 8 || myMargin.status == 10>
                        <#if myMargin.status == 2>
                            <form name="form-auction" id="form-auction"
                                  action="/auction/margin/confirm/${myMargin.marginSn}" method="POST"></form>
                            <div style="font-size:13px;color:#FFCACA;background:rgba(242,26,26,.5);position:absolute;bottom:3rem;left:0;width:100%;line-height:1.875rem;">
                                恭喜您成功中标！请尽快支付尾款
                            </div>
                            <button type="button" data-type="payauction" data-sn="${myMargin.marginSn}"
                                    class="auction-pay auction-button">支付尾款<br/><span
                                        style="font-size:12px;color:rgba(0,0,0,.5);">超时未支付将扣除保证金</span></button>
                        <#else>
                            <button type="button" data-type="auctionout" class="auction-pay" disabled="disabled">竞拍成功
                            </button>
                        </#if>
                    <#elseif myMargin.status == -2 || myMargin.status == -8 || myMargin.status = -6>
                        <button type="button" data-type="auctionout" class="auction-pay" disabled="disabled"
                                style="color:rgba(255,255,255,.5)">拍卖已结束
                        </button>
                    <#else>
                        <div class="auction-pay" style="line-height:3rem;">拍卖结果即将揭晓</div>
                    </#if>
                <#else>
                    <button type="button" data-type="auctionout" class="auction-pay auction-button" disabled="disabled"
                            style="color:rgba(255,255,255,.5)">${productDet.statusName}</button>
                </#if>
            </#if>
        <#else>
            <button type="button" class="auction-pay" disabled="disabled" style="color:rgba(255,255,255,.5)">已下架
            </button>
        </#if>
    </div>
</div>
<share data-title="${productDet.product.name}" data-desc="1元起拍 等你来出价！"
       data-pic="//static.d2c.cn/img/home/160627/images/headpic.png"></share>
<script>
    //拍卖规则
    $(document).on(click_type, '.auction-rule', function () {
        var html = ['<div class="popup-content">',
            '<div class="auction-rule-container">',
            '<h2 style="text-align:center;font-size:1.25rem;color:rgba(0,0,0,.85);font-weight:bold;">拍卖规则</h2>',
            '<div style="margin-top:1.25rem;font-size:.875rem;color:rgba(0,0,0,.5);height:15.625rem;overflow:auto;-webkit-overflow-scrolling:touch;">${productDet.rule}</div>',
            '<i class="rule-close" onclick="popupModalClose()"></i>',
            '</div>',
            '</div>'].join("");
        popupModal({content: html});
    });

    var flag = true;
    //商品轮播
    $(document).on(click_type, '.auction-product-open', function () {
        var obj = $(this);
        $('.offers-item, .offers-item-none').toggle();
        flag = !flag;
        if (!flag) {
            obj.addClass('auction-open-down');
            $('.auction-offers-list').css('overflow-y', 'initial');
        } else {
            obj.removeClass('auction-open-down');
            $('.auction-offers-list').css('overflow-y', 'scroll');
        }
    });

    //商品详情
    var load_url = $('.product-detail-tab a.on').attr('data-url');
    $(window).bind('scroll', function () {
        if (!$('#product-detail-content').data('isLoad')) {
            var scroll_top = $(window).scrollTop();
            if (scroll_top + parseInt($(window).height()) >= $(document).height() * 0.8) {
                $.get(load_url, function (data) {
                    $('#product-detail-content').data('isLoad', true);
                    $('#product-detail-content').html(data);
                });
            }
        }
    });
    //减价按钮
    $('#decrease').on('touchstart', function () {
        var originPrice = parseInt($('#num').attr('data-price'));
        var calPrice = parseInt($('#num').val());
        var finalPrice = calPrice - parseInt($('.num-input').attr('data-price'));
        if (finalPrice < originPrice) {
            jAlert("不能低于当前最低出价");
            return false;
        } else {
            $('#num').val(finalPrice);
            $('.num-input').text('出价' + finalPrice + '元');
            return false;
        }
    });
    //加价按钮
    $('#increase').on('touchstart', function () {
        var stepPrice = parseInt($('.num-input').attr('data-price'));
        var finalPrice = stepPrice + parseInt($('#num').val());
        $('#num').val(finalPrice);
        $('.num-input').text('出价' + finalPrice + '元');
        return false;
    });
    //支付系列动作
    $('.auction-button').on('touchstart', function () {
        var type = $(this).attr('data-type');
        var auction_id = $('.auction-base').attr('data-id');
        if (type == 'margin') {
            if (_memberId) {
                if (_isD2C) {
                    $.post('/auction/margin/create/' + auction_id + '.json', function (data) {//交保证金
                        if (data.result.status == 1) {
                            var params = data.result.datas.params;
                            var auctionSn = data.result.datas.marginSn;
                            location.href = '/payment/prepare/margin/' + auctionSn + '?' + params;
                        } else {
                            $.flashTip({position: 'bottom', type: 'error', message: data.result.message});
                        }
                    });
                } else {
                    location.href = "/member/bind"
                }
            } else {
                userLogin();
            }
        } else if (type == 'payauction') {
            if (_memberId) {
                if (_isD2C) {
                    $('#form-auction').submit();
                } else {
                    location.href = "/member/bind"
                }
            } else {
                $('body').data('function', '$(".auction-button[data-type=payauction]").trigger("touchstart")');
                userLogin();
            }
        } else if (type == 'auction') {
            var id = $('.auction-base').attr('data-id');
            var offer = $('#num').val();
            if (_memberId) {
                if (_isD2C) {
                    jConfirm('您将出价' + offer + '元', function (r) {
                        if (r) {
                            $.post('/auction/offer.json', {id: id, offer: offer}, function (data) {
                                if (data.result.status == 1) {
                                    $.flashTip({position: 'bottom', type: 'success', message: '出价成功'});
                                    setTimeout(function () {
                                        location.reload();
                                    }, 1500);
                                } else if (data.result.status == -2 || data.result.status == -3) {
                                    $.flashTip({position: 'bottom', type: 'error', message: data.result.message});
                                    setTimeout(function () {
                                        location.reload();
                                    }, 1500);
                                } else {
                                    $.flashTip({position: 'bottom', type: 'error', message: data.result.message});
                                }
                            });
                        }
                    });
                } else {
                    location.href = "/member/bind"
                }
            } else {
                $('body').data('function', '$(".auction-button[data-type=auction]").trigger("touchstart")');
                userLogin();
            }
        }
        return false;
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
