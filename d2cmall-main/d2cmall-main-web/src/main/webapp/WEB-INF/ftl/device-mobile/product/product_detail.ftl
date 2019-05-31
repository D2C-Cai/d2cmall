<#import "templates/public_mobile.ftl" as m>
<#assign FROMAPP=renderUserAgent()/>
<@m.page_header title="商品详情" hastopfix="false" service="no" cart="has-bar" title='${product.name}' js='utils/swiper.min' keywords="${product.name},预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${product.name},消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<style>
    .qr-box {
        width: 20em;
        height: 23.75em;
        background: #FFF;
        text-align: center;
    }

    .box-title {
        padding: 2.5em 0 .625em;
        color: #262626;
        font-weight: bold;
        line-height: 1.5;
    }

    .box-cont-tip {
        padding: 10px 0;
        line-height: 1.5;
        color: #7F7F7F;
        font-size: 12px;
    }

    .box-button {
        border: 0;
        width: 235px;
        height: 40px;
        line-height: 40px;
        background: #FD555D;
        color: #FFF;
    }

    .qr-close {
        width: 1.875em;
        height: 1.875em;
        position: absolute;
        bottom: -40px;
        left: 50%;
        transform: translateX(-50%);
    }

</style>
<div class="header gradient fixed">
    <div class="header-back"></div>
    <div class="header-title"></div>
    <div class="header-btn">
        <button type="button" onclick="location.href='/cart/list'" class="button-icon button-cart"><em
                    class="bounceIn animated my-cart-num">0</em><i class="icon icon-cart-bar"></i></button>
        <button type="button" class="button-icon"><i class="icon icon-share share-button"></i></button>
    </div>
</div>
<div class="section">
    <div class="swiper-container" id="product-slide">
        <div class="swiper-wrapper" style="align-items:center;">
            <#list product.productImageList as image>
                <div class="swiper-slide">
                    <a href="#"><img
                                src="<#if image?index_of('http')!=-1>${image}<#else>${picture_base}${image}!600</#if>"/></a>
                </div>
            </#list>
        </div>
        <div class="swiper-pagination"></div>
    </div>

    <div class="product-base">
        <h1 class="title"><#if flashPromotion?exists && !productPromotion.flashPromotionOver><span
                    style="color:#fd555d;font-size:.75rem;">【限时购】</span></#if> ${product.name}</h1>
        <#if productPromotion?exists>
            <#if flashPromotion?exists && !productPromotion.flashPromotionOver>
                <div class="product-promotion" style="display:inline-block;color:#fd555d;font-size:.75rem;">【
                    <a href="/flashpromotion/product/session" class="red">${flashPromotion.name}</a>】
                </div>
            <#elseif !productPromotion.promotionOver>
                <div class="product-promotion" style="display:inline-block;color:#fd555d;font-size:.75rem;">【
                    <a href="/promotion/${productPromotion.promotionId}"
                       class="red">${productPromotion.promotionName}</a>】
                </div>
            </#if>
        </#if>
        <#if (orderPromotions?exists && orderPromotions?size gt 0)>
            <div class="product-promotion" style="display:inline-block;color:#fd555d;font-size:.75rem;">满减：
                <#list orderPromotions as orderPromotion>
                    【<a href="/promotion/${orderPromotion.id}" class="red">${orderPromotion.name}</a>&nbsp】
                </#list></div>
        </#if>
        <#if productPromotion?exists && (!productPromotion.promotionOver || !productPromotion.flashPromotionOver)>
            <div class="product-label">
                <label>购买价</label><span class="data-price">&yen; <strong
                            class="current-price">${(productPromotion.currentPrice?string("currency")?substring(1))!}</strong></span>
                &nbsp;&nbsp;&nbsp;销售价 <s>&yen; ${product.minPrice}</s>
            </div>
            <#if product.productTradeType == 'CROSS'>
                <div style="margin-top:10px;font-size:12px;color:#7F7F7F;">
                    <#if product.taxation == 1>
                        本商品税费由商家承担
                    <#else>
                        预计税费 &yen;${(product.minPrice)*(product.taxPrice?number)}，实际税费请以提交订单时为准
                    </#if>
                </div>
            </#if>
        <#elseif (product.minPrice?exists && product.originalPrice?exists&& product.minPrice!=product.originalPrice)>
            <div class="product-label">
                <label>购买价</label><span class="data-price">&yen; <strong
                            class="current-price">${(product.minPrice?string("currency")?substring(1))!}</strong></span>
                <#if product.originalPrice gt product.minPrice>&nbsp;&nbsp;&nbsp;吊牌价 <s>
                    &yen; ${product.originalPrice}</s></#if>
            </div>
            <#if product.productTradeType == 'CROSS'>
                <div style="margin-top:10px;font-size:12px;color:#7F7F7F;">
                    <#if product.taxation  == 1>
                        本商品税费由商家承担
                    <#else>
                        预计税费 &yen;${(product.minPrice)*(product.taxPrice?number)}，实际税费请以提交订单时为准
                    </#if>
                </div>
            </#if>
        <#else>
            <div class="product-label">
                <label>购买价</label><span class="data-price">&yen; <strong
                            class="current-price">${product.minPrice?string("currency")?substring(1)}</strong></span>
            </div>
            <#if product.productTradeType == 'CROSS'>
                <div style="margin-top:10px;font-size:12px;color:#7F7F7F;">
                    <#if product.taxation == 1>
                        本商品税费由商家承担
                    <#else>
                        预计税费 &yen;${(product.minPrice)*(product.taxPrice?number)}，实际税费请以提交订单时为准
                    </#if>
                </div>
            </#if>
        </#if>
    </div>
    <div style="margin-top:-0.8em">
        <@m.app_download_bar fixed="false" />
    </div>
    <#if m.LOGINMEMBER.partnerId?exists &&m.LOGINMEMBER.partnerId!=0 && product.secondRatio gt 0>
        <div class="share-recommender">
            <div class="display-flex space-between">
                <div class="share-income">
                    分享最多可赚 <#if productPromotion?exists && (!productPromotion.promotionOver || !productPromotion.flashPromotionOver)>
                        ${(productPromotion.currentPrice)*(product.secondRatio)*(product.grossRatio)}
                    <#else>${(product.minPrice)*(product.secondRatio)*(product.grossRatio)}</#if>元
                </div>
                <div class="share-tool display-flex flex-end">
                    <div><a href="/product/detail/${product.id}?type=summary"><i
                                    class="icon icon-package"></i><span>素材</span></a></div>
                    <!-- <div><i class="icon icon-qrcode"></i></i><span>二维码</span></div> -->
                    <div class="share-button"><i class="icon icon-share-re"></i><span>分享</span></div>
                </div>
            </div>
        </div>
    </#if>
    <div class="product-promise">
        <#if product.cod==1><span><i>※</i> 支持货到付款</span></#if>
        <span><i>※</i> 授权正品</span>
        <span><i>※</i> <#if product.productTradeType == 'CROSS'>88元包邮<#else>满299顺丰包邮</#if></span>
        <#if product.after==1><span><i>※</i> 七天无理由</span></#if>
        <#if designer.subscribe==1 && product.subscribe == 1><span><i>※</i> 免费门店试穿</span></#if>
    </div>
    <#if flashPromotion?exists && !productPromotion.flashPromotionOver>
        <div class="form">
            <a href="/flashpromotion/product/session" class="form-item">D2C快抢<i class="icon icon-arrow-right right"></i>
                <#if flashPromotion.statusName=='即将开始'>
                    <div class="limit-countdown count-down  float-right" style="font-size:.875rem"
                         data-type="split-time" data-endtime="${flashPromotion.startDate?string("yyyy/MM/dd HH:mm:ss")}"
                         data-musttime="must">
                        距开始&nbsp;<span class="hour down-time" style="float:none;">00</span>:<span
                                class="minute down-time" style="float:none;">00</span>:<span class="second down-time"
                                                                                             style="float:none;">00</span>
                    </div>
                <#else>
                    <div class="limit-countdown count-down float-right" data-type="split-time" style="font-size:.875rem"
                         data-endtime="${flashPromotion.endDate?string("yyyy/MM/dd HH:mm:ss")}" data-musttime="must">
                        距结束&nbsp;<span class="hour down-time" style="float:none;">00</span>:<span
                                class="minute down-time" style="float:none;">00</span>:<span class="second down-time"
                                                                                             style="float:none;">00</span>
                    </div>
                </#if>
            </a>
        </div>
    </#if>
    <div class="form">
        <a href="/showroom/designer/${(designer.id)!}" class="form-item">${(designer.name)!}<i
                    class="icon icon-arrow-right right"></i><span class="grey float-right" style="font-size:12px;">进入设计师店铺</span></a>
    </div>
    <#if (product.remark && product.remark!="") || product.after==0>
        <div class="form">
            <#if (product.remark && product.remark!="")>
                <div class="form-item red" style="line-height:150%;">
                    <span style="font-size:0.9em;">${(product.remark)!}</span>
                </div>
            </#if>
            <#if product.after==0>
                <div class="form-item red" style="line-height:150%;">
                    <span style="font-size:0.9em;">【本商品不支持七天无理由退换货】</span>
                </div>
            </#if>
        </div>
    </#if>
    <div class="product-select">
        <form name="form-buy" id="form-buy" action="/order/buynow" method="post">
            <input type="hidden" name="skuId" value=""/><!-- 货号 -->
            <input type="hidden" name="productId" value="${product.id}"/>
            <div class="product-sku img clearfix" style="position:relative;">
                <#if product.salesproperty>
                <span class="font main-font">${product.salesproperty?eval.sp1[0].name}</span>
                <span class="value">
                <#list product.salesproperty?eval.sp1 as p>
                    <a href="javascript:" id="c-${p.id}" class="color-section" data-type="color" data-id="${p.id}"
                       rel="${p.value}" data-image="<#if p.img!=''>${picture_base}/${p.img}</#if>">${p.value}</a>
                </#list>
                    </#if>
                </span>
            </div>
            <#if product.salesproperty && ((product.salesproperty?eval.sp2)?size>0)>
                <div class="product-sku txt clearfix">
                    <span class="font sec-font">${product.salesproperty?eval.sp2[0].name}</span>
                    <span class="value">
                <#list product.salesproperty?eval.sp2 as p>
                    <a href="javascript:" id="s-${p.id}" class="size-section" data-type="size" data-id="${p.id}"
                       rel="${p.value}">${p.value}</a>
                </#list>
                </span>
                </div>
            </#if>
            <div id="preview-color-img"></div>
            <div class="product-sku clearfix num-crease">
                <span class="font">数量 </span>
                <div class="num-crease-wrap"><span class="num-decrease num-operation" id="decrease"
                                                   data-type="decrease">－</span><span class="num-input">1</span><span
                            class="num-increase num-operation" id="increase" data-type="increase">＋</span><input
                            type="hidden" class="product-count" id="num" name="num" readonly value="1"></div>
                <span class="store-span"></span>
                <input type="hidden" name="store" id="store" value="${(product.availableStore)!}"/>
            </div>
            <div style="height:46px;"></div>
            <div class="layer-buy">
                <#if productPromotion?exists>
                    <#if !productPromotion.flashPromotionOver>
                        <div class="product-times">
                            <div class="count-down red" data-type="toend"
                                 data-startTime="${productPromotion.flashStartDate?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${productPromotion.flashEndDate?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></div>
                        </div>
                    <#elseif !productPromotion.promotionOver>
                        <div class="product-times">
                            <div class="count-down red" data-type="toend"
                                 data-startTime="${productPromotion.startDate?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${productPromotion.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></div>
                        </div>
                    </#if>
                </#if>
                <#if product.status==3>
                    <div class="product-alert">
                        <h3>已售罄。留下手机号，系统会在到货的时候通知您</h3>
                        <input type="text" name="mobilemail" class="input remind-param mobile"
                               value="<#if m.LOGINMEMBER.id!=null><#if (m.LOGINMEMBER.mobile)!=null>${m.LOGINMEMBER.mobile!}<#elseif m.LOGINMEMBER.email?exists>${m.LOGINMEMBER.email!}</#if></#if>"
                               placeholder="输入手机号或邮箱"/>
                        <input type="hidden" name="productId" class="remind-param" value="${product.id}"/>
                        <input type="hidden" name="name" class="remind-param" value="${product.name}"/>
                        <input type="hidden" name="type" class="remind-param" value="ARRIVAL"/>
                        <button type="button" name="remind-btn" class="button button-red remind-send">到货提醒</button>
                    </div>
                <#else>
                    <div class="suspend-bar" style="overflow:hidden;padding:3px">
                        <button type="button" class="button-icon" style="width:12%;float:left;" onclick="ysf.open()"><i
                                    class="icon icon-assistant"></i>客服
                        </button>
                        <#if product.mark==0>
                            <button type="button" class="button disabled" disabled style="width:85%;float:right;">已下架
                            </button>
                        <#elseif store lte 0>
                            <button type="button" class="button disabled" disabled style="width:85%;float:right;">已售完
                            </button>
                        <#else>
                            <#if designer.subscribe==1 && product.subscribe==1>
                                <#assign width='27'>
                            <#else>
                                <#assign width='34'>
                            </#if>
                            <#if designer.subscribe==1 && product.subscribe == 1>
                                <button type="button" data-type="fitting" data-id="${product.id}"
                                        class="operator-button button-icon" style="width:14%;float:left;"><i
                                        class="icon icon-gift"></i>试衣</button></#if>
                            <button type="button" data-url="/member/interest/collection/insert/${product.id}"
                                    id="collect-${product.id}"
                                    check-url="/member/interest/collection/check/${product.id}"
                                    class="button-icon ajax-request"
                                    call-back="interestSuccess(${product.id},'collect')" style="width:14%;float:left;">
                                <i class="icon <#if product.collectioned==1>icon-star<#else>icon-star-o</#if>"></i><#if product.collectioned==1>已收藏<#else>收藏</#if>
                            </button>
                            <button type="button" data-type="buy" class="operator-button button button-red"
                                    style="width:${width}%;margin-left:2%;float:right;margin-right:2%;">立即购买
                            </button>
                            <#if product.cart==1>
                                <button type="button" data-type="cart"class="operator-button button button-white"
                                        style="width:${width}%;float:right">加入购物车</button></#if>
                        </#if>
                    </div>
                </#if>
            </div>
        </form>
    </div>
</div>

<div class="section">
    <div class="product-detail-tab clearfix">
        <a href="javascript:" class="on" data-url="/product/detail/${product.id}?type=ajax" data-type="desc">商品详情</a>
        <a href="javascript:" data-url="/comment/product/${product.id}">客户评价（${countComment}）</a>
        <a href="javascript:" data-url="/consult/product/${product.id}">客户咨询（${countConsult}）</a>
    </div>
    <div id="product-detail-content" class="product-detail-content">
        <div class="loadding">
            <i class="icon-spinner icon-spin icon-large"></i> 正在加载内容
        </div>
    </div>
    <#if relationProducts?exists && relationProducts?size gt 0>
        <div class="block-state" style="margin:1em 0;">
            <hr/>
            <span>相关搭配</span>
        </div>
        <div class="list lazyload clearfix">
            <#list relationProducts as product>
                <a href="${mobileBase}/product/${product.id}" class="item item-flex item-gap">
            <span class="img">
              <img src="${static_base}/m/img/blank100x157.png"
                   data-image="${picture_base}${product.productImageCover}!280" alt=""/>
             </span>
                    <span class="title">${(product.name)!}</span>
                    <span class="price">&yen; ${(product.minPrice)!}</span>
                </a>
            </#list>
        </div>
    </#if>
</div>
<share data-title="${product.name}"
       data-url="${mobileBase}/product/${product.id}<#if m.LOGINMEMBER.partnerId>?parent_id=${m.LOGINMEMBER.partnerId}</#if>"
       data-pic="${picture_base}${product.productImageList[0]}!wx.title"></share>
<script>

    if (!document.referrer) $('.header .header-back').html('<a href="/" class="back-home"><span class="icon icon-home"></span>回首页</a>');

    //详情页图片加载
    $(window).bind('scroll', function () {
        if (!$('#product-detail-content').data('isLoad')) {
            var scroll_top = $(window).scrollTop();
            if (scroll_top + parseInt($(window).height()) >= $(document).height() * 0.8) {
                $('#product-detail-content').data('isLoad', true);
                $.get(load_url, function (data) {
                    $('#product-detail-content').html(data);
                });
            }
        }
    });

    //详情页类目切换
    var load_url = $('.product-detail-tab a.on').attr('data-url');
    $('.product-detail-tab a').click(function () {
        $(this).siblings().removeClass('on');
        $(this).addClass('on');
        var url = $(this).attr('data-url');
        $.get(url, function (data) {
            $('#product-detail-content').data('isLoad', true);
            $('#product-detail-content').html(data);
        });
        return false;
    });

    //wechat图片
    <#if browser=='wechat'>
    wx.ready(function () {
        $('.slide-item a').click(function () {
            var img = $(this).find('img').attr('src');
            wx.previewImage({
                current: img,
                urls: [
                    <#list product.productImageList as image>
                    '${picture_base}${image}'<#if image_index lt product.productImageList?size-1>, </#if>
                    </#list>
                ]
            });
        });
        $(document).on('click', '#preview-color-img a', function () {
            var img = $(this).attr('href');
            wx.previewImage({
                current: img,
                urls: ['' + img + '']
            });
            return false;
        });
    });
    </#if>
    //分享
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
    $(document).on(click_type, '.share-wechat-guide .mask-close', function () {
        $('.share-wechat-guide').removeClass('show');
        return false;
    });

    //规格筛选
    $('.product-sku a').on('touchstart', function () {
        var obj = $(this);
        if (obj.attr('class').indexOf('disabled') == -1) {
            if (obj.hasClass('selected')) {
                obj.removeClass('selected');
                if (obj.attr('data-image')) {
                    $('#preview-color-img').hide();
                }
                obj.hasClass('color-section') ? $('.size-section').removeClass('disabled').removeAttr('disabled') : $('.color-section').removeClass('disabled').removeAttr('disabled');
            } else {
                obj.siblings().removeClass('selected');
                obj.addClass('selected');
                if (obj.attr('data-image')) {
                    $('#preview-color-img').show().html('<a href="' + obj.attr('data-image') + '"><img src="' + obj.attr('data-image') + '" width="120" alt="" /></a>');
                }
            }
            var tips = new Array(), str = '', param = '';
            $.each($('.product-sku').find('.selected'), function (i, d) {
                tips.push('"' + $(d).attr('rel') + '"');
                param += str + $(d).attr('data-type') + '=' + $(d).attr('data-id');
                str = '&';
            });
            if (param) {
                $.post('/product/getSKUInfo/${product.id}', param, function (data) {
                    if (data.result.status == 1) {
                        <#if product.salesproperty && ((product.salesproperty?eval.sp2)?size>0)>
                        var skuId = data.result.skuId == undefined ? '' : data.result.skuId;
                        var store = data.result.store;
                        if (typeof (data.result.Size) == 'object') {
                            $('.img a').addClass('disabled').attr('disabled', true);
                            $.each(data.result.Size, function (i, d) {
                                console.log(d.store)
                                if (d.store > 0)
                                    $('#c-' + d.Color_id).removeClass('disabled').removeAttr('disabled');
                            });
                        } else if (typeof (data.result.Color) == 'object') {
                            $('.txt a').addClass('disabled').attr('disabled', true);

                            $.each(data.result.Color, function (i, d) {
                                if (d.store > 0)
                                    $('#s-' + d.Size_id).removeClass('disabled').removeAttr('disabled');
                            });
                        }
                        if (typeof (data.result.Color) == 'object' && typeof (data.result.Size) == 'object') {
                            data.result.price ? $('.current-price').text((data.result.price)) : '';
                            $('#store').val(store);
                            $('input[name=skuId]').val(skuId);
                            data.result.store ? $('.store-span').html('') : $('.store-span').html('');
                            //   data.result.store?$('.store-span').html(' &nbsp;库存 '+store+' 件'):$('.store-span').html('');
                        } else {
                            $('#store').val('');
                            $('input[name=skuId]').val('');
                            $('.store-span').html('');
                        }
                        <#else>
                        var skuId = data.result.Color[0].skuId == undefined ? '' : data.result.Color[0].skuId;
                        var store = data.result.Color[0].store;
                        data.result.Color[0].price ? $('.current-price').text((data.result.price)) : '';
                        $('#store').val(store);
                        $('input[name=skuId]').val(skuId);
                        store ? $('.store-span').html('') : $('.store-span').html('');
                        </#if>
                    } else if (data.result.stauts == -1) {
                        $('.product-sku a').removeClass('disabled').removeAttr('disabled');
                        $('#store').val('');
                        $('input[name=skuId]').val('');
                    }
                }, 'json');
            } else {
                $('input[name=skuId]').val('');
            }
        }
        return false;
    });

    //数量操作
    var mainFont = $('.main-font').text();//主规格名称
    $('.num-operation').on('touchstart', function () {
        var type = $(this).attr('data-type');
        if ($('.color-section').hasClass('selected')) {
            if (type == 'decrease') {
                var i = parseInt($('#num').val()) - 1;
                if (i <= 0) {
                    i = 1;
                    $.flashTip({position: 'center', type: 'error', message: '不能再减哦~'});
                }
                $('#num').val(i);
                $('.num-input').text(i);
            }
            if (type == 'increase') {
                var i = parseInt($('#num').val()) + 1;
                if (i >= parseInt($('#store').val())) {
                    $.flashTip({position: 'center', type: 'error', message: '最多只能购买' + $('#store').val() + '件。'});
                    i = $('#store').val();
                }
                $('#num').val(i);
                $('.num-input').text(i);
            }
        } else {
            $.flashTip({position: 'center', type: 'error', message: '请选择' + mainFont});
        }
        return false;
    });


    //按钮系列操作
    $('.operator-button').on('touchstart', function () {
        //验证是否填写主规格和副规格(若有副规格)
        var type = $(this).attr('data-type');
        var skuId = $('input[name=skuId]').val();
        if (skuId == '') {
            $('.product-sku a').addClass('animated shake');
            if ($('.product-sku').hasClass('txt')) {
                $.flashTip({position: 'bottom', type: 'error', message: '请选择' + mainFont + $('.sec-font').text()});
            } else {
                $.flashTip({position: 'bottom', type: 'error', message: '请选择' + mainFont});
            }
            $(window).scrollTop('720');
            setTimeout(function () {
                $('.product-sku a').removeClass('animated shake');
            }, 600);
            return false;
        }
        //验证是否正确填写数量
        var num = parseInt($('input[name=num]').val());
        if (isNaN(num) || num < 1) {
            $.flashTip({position: 'bottom', type: 'error', message: '请选择购买数量'});
            $(window).scrollTop('720');
            return false;
        }
        if ($('input[name=num]').val() > parseInt($('#store').val())) {
            $.flashTip({position: 'bottom', type: 'error', message: '最多只能购买' + $('#store').val() + '件。'});
            $('#num').val($('#store').val());
            return false;
        }
        if (type == 'cart') {
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    $('body').data('function', '$(".operator-button[data-type=cart]").trigger("touchstart")');
                    userLogin();
                } else if (data.result.datas.isBind == false) {
                    location.href = "/member/bind"
                } else {
                    if ('${product.productTradeType}' == 'CROSS') {
                        popupHtml();
                    } else {
                        $.ajax({
                            'url': '/cart/add/' + skuId + '/' + num + '?' + 't=' + new Date().getTime(),
                            'type': 'post',
                            'data': {},
                            'dataType': 'json',
                            'success': function (data, status) {
                                if (data.result.status == 1) {
                                    $.flashTip({position: 'bottom', type: 'success', message: '成功加入购物车'});
                                    $('.my-cart-num').text(data.result.datas.countNum).addClass('bounceIn');
                                    setTimeout(function () {
                                        $('.my-cart-num').removeClass('bounceIn');
                                    }, 1000);
                                } else {
                                    $.flashTip({position: 'bottom', type: 'error', message: data.result.message});
                                }
                            }
                        });
                    }
                }
            });
        } else if (type == 'fitting') {
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    $('body').data('function', '$(".operator-button[data-type=fitting]").trigger("touchstart")');
                    userLogin();
                } else if (data.result.datas.isBind == false) {
                    location.href = "/member/bind"
                } else {
                    location.href = '/o2oSubscribe/edit?skuId=' + skuId + '&quantity=' + num;
                }
            });
        } else {
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    $('body').data('function', '$("#form-buy").submit()');
                    userLogin();
                } else if (data.result.datas.isBind == false) {
                    location.href = "/member/bind"
                } else {
                    if ('${product.productTradeType}' == 'CROSS') {
                        popupHtml();
                    } else {
                        $('#form-buy').submit();
                    }
                }
            });
        }
        return false;
    });

    <#if !FROMAPP>
    //七鱼客服
    ysf.on({
        'onload': function () {
            ysf.product({
                show: 1, // 1为打开， 其他参数为隐藏（包括非零元素）
                title: '${(product.name)!}',
                desc: '${(designer.name)!}',
                picture: <#if product.productImageList>'${picture_base}/${product.productImageList[0]}</#if>',
                note: '${(product.topCategory?eval.name)!}',
                url: '//www.d2cmall.com/product/${product.id}'
            })
        }
    })


    /*合力亿捷云客服
    function detailsConsult() {
        if($('#open-link').size()==0){
            $('body').append('<a href="" id="open-link"></a>');
        }
        var linkUrl = "https://im.7x24cc.com/phone_webChat.html?accountId=N000000006249&chatId=ea307499-ab03-403f-8127-af8faf016a31<#if LOGINMEMBER.id!=null>&visitorId=${LOGINMEMBER.id}&nickName=${LOGINMEMBER.displayName}</#if>&businessParam={productId:${product.id}}";
	$('#open-link').attr('href',linkUrl)[0].click();
}
*/

    </#if>
</script>
<@m.page_footer  />