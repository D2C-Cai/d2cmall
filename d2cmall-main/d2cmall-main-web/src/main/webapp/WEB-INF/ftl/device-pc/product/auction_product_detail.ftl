<#import "templates/public_pc.ftl" as m>
<#assign LOGINMEMBER=loginMember()/>
<@m.page_header js='utils/jquery.zoom|utils/jquery.datepicker' title='拍卖 - ${result.datas.product.title}' keywords="D2C拍卖,${result.datas.product.title}" description="" />
<@m.top_nav />
<div class="layout layout-response layout-auction clearfix">
    <div class="product-base clearfix">
        <div class="product-img">
            <div class="img-thumbs">
                <#if result.datas.product.product.productImageList?size gt 0 &&result.datas.product.product.productImageList?exists>
                    <#list result.datas.product.product.productImageList as image>
                        <a href="javascript:"><img src="${picture_base}/${image}!120"
                                                   data-image="${picture_base}/${image}!media"
                                                   ori-image="${picture_base}/${image}"/></a>
                    </#list>
                </#if>
            </div>
            <div class="img-media">
                <!--<div class="video-play"><a href="javascript:" class="png" title="播放视频"></a><img src="${static_base}/c/demo/thumbs/1.jpg" data-video="${static_base}/c/demo/video.flv" alt="" /></div>-->
                <div class="middle-image"><img src="${static_base}/c/images/space.gif" id="product-image-middle"
                                               data-image="" data-id="0" alt=""/></div>
                <div class="video-play-layer"></div>
            </div>
        </div>

        <div class="product-info product-normal">
            <div class="product-navi">
                <div class="view-in-mobile">
                    <span>去手机上拍</span>
                    <img src="/picture/code?type=1&width=160&height=160&noLogo=true&&code=//m.d2cmall.com/auction/product/${result.datas.product.id}"/>
                </div>
                <span>D2C拍卖</span>
            </div>
            <div>
                <h1 class="product-title">${result.datas.product.title}</h1>
                <#if result.datas.product.status==1>

                    <#if result.datas.product.doing>
                        <div class="auction-time">
                            <div class="auction-string"><img src="//static.d2c.cn/common/nc/img/auction_on.png"
                                                             width="30"/> 拍卖中
                            </div>
                            <div class="count-down" data-function="countdownOver"
                                 data-starttime="${result.datas.product.beginDate?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endtime="${result.datas.product.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-servertime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-defined="millisecond"></div>
                        </div>
                    <#elseif !result.datas.product.doing && (!myOffer?exists || myOffer?size lte 0 || myOffer[0].status==0)>
                        <div class="auction-time">
                            <div class="auction-string" style="color:gray"><img
                                        src="//static.d2c.cn/common/nc/img/auction.png"
                                        width="30"/> ${result.datas.product.statusName}</div>
                            <#if result.datas.product.statusName=="拍卖结束">
                                <div class="auction-end-time">${result.datas.product.endDate?string("yyyy/MM/dd HH:mm:ss")}</div>
                            <#else>
                                <div class="count-down" data-function="countdownOver"
                                     data-starttime="${result.datas.product.beginDate?string("yyyy/MM/dd HH:mm:ss")}"
                                     data-endtime="${result.datas.product.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                                     data-servertime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                     data-defined="millisecond"></div>
                            </#if>
                        </div>
                    <#elseif !result.datas.product.doing && myOffer?exists && myOffer?size gt 0 && myOffer[0].status==1>
                        <div class="auction-time">
                            <div class="auction-string"><img src="//static.d2c.cn/common/nc/img/auction_on.png"
                                                             width="30"/> 竞拍成功
                            </div>
                            <div class="auction-end-time">${result.datas.product.endDate?string("yyyy/MM/dd HH:mm:ss")}</div>
                        </div>
                    </#if>
                    <div class="auction-price">
                        <#if result.datas.product.doing>
                            <span class='label-name'>当前价格</span>
                        <#else>
                            <span class='label-name'>成交价格</span>
                        </#if>
                        <span class="price"><em>&yen;</em> <strong id="current-price"
                                                                   data-value="${result.datas.product.currentPrice}">${result.datas.product.currentPrice?string('currency')?substring(1)}</strong></span>
                        <span class='label-name-two'>累计出价
                    <span class='price' style="color:#fd555d"> ${(result.datas.offers.list?size)!}</span> 次</span>
                    </div>
                    <div class="auction-price">
                        <span class='label-name'>加价幅度</span>
                        <span class='price-two' id="step-price"
                              data-value="${result.datas.product.stepPrice}"><em>&yen;</em> ${result.datas.product.stepPrice}</span>
                        <span class='label-name-three'>拍卖保证金</span>
                        <span class='price-two'><em>&yen;</em> ${result.datas.product.margin?string('currency')?substring(1)}</span>
                    </div>
                    <div class="auction-price">
                        <span class='auction-tip'>如竞拍出局，保证金将原路返回。</span>
                    </div>
                    <div style="margin-top:0px">
                        <input type="hidden" name="id" value="${result.datas.product.id}"/>
                        <div class="clearfix" style="padding:15px 0 15px 0;float:left">
                            <em class="data-crease Auctiondecrease" unselectable="on"
                                onselectstart="return false;">-</em>
                            <input type="text" name="offer" id="offer-price"
                                   value="${result.datas.product.currentPrice+result.datas.product.stepPrice}"
                                   maxlength="6" class="data-num"
                                   readonly="readonly" <#if result.datas.product.doing> <#if !myMargin?exists || myMargin.status!=1>style="background-color:#F2F2F2" </#if></#if>/>
                            <em class="data-crease Auctionincrease" unselectable="on"
                                onselectstart="return false;">+</em>
                        </div>
                        <#if LOGINMEMBER.id!=null>
                            <#if result.datas.product.doing>
                                <#if myMargin?exists && myMargin.status==1>
                                    <button type="button" class="auction-button" data-type="auction"
                                            data-id="${result.datas.product.id}">立即出价
                                    </button>
                                <#else>
                                    <button type="button" class="auction-button" data-type="margin"
                                            data-id="${result.datas.product.id}">支付保证金
                                    </button>
                                </#if>
                            <#else>
                                <#if myMargin?exists>
                                    <#if myMargin.status==2 || myMargin.status==6 ||  myMargin.status==8 || myMargin.status==-6>
                                        <#if myMargin.status==2>
                                            <form name="form-auction" id="form-auction"
                                                  action="/auction/margin/confirm/${myMargin.marginSn}"
                                                  method="POST"></form>
                                            <button type="button" class="auction-button" data-type="payauction"
                                                    data-id="${result.datas.product.id}">去支付
                                            </button>
                                        <#else>
                                        </#if>
                                    <#elseif myMargin.status==-2 || myMargin.status==-8>
                                        <button type="button" class="auction-button auction-disable"
                                                disabled="disabled">竞拍出局
                                        </button>
                                    <#else>
                                        <button type="button" class="auction-button auction-disable"
                                                disabled="disabled">结果处理中
                                        </button>
                                    </#if>
                                <#else>
                                    <button type="button" class="auction-button auction-disable"
                                            disabled="disabled">${result.datas.product.statusName}</button>
                                </#if>
                            </#if>
                        <#else>
                            <#if result.datas.product.doing>
                                <button type="button" class="auction-button" data-type="margin"
                                        data-id="${result.datas.product.id}">支付保证金
                                </button>
                            <#else>
                                <button type="button" class="auction-button auction-disable"
                                        disabled="disabled">${result.datas.product.statusName}</button>
                            </#if>
                        </#if>
                    </div>
                <#-- 显示我的出价-->

                    <div class="auction-price" style="margin-top:100px">
                        <#if myOffer>
                            <span class='label-name'>我的出价</span>
                            <span class="price"><em>&yen;</em> <strong
                                        data-value="${myOffer[0].offer}">${myOffer[0].offer?string('currency')?substring(1)}</strong></span>
                            <span class='label-name-two'>
	                         <#if myOffer[0].status == 1>
                                 <span class='auction-string' style="color:#FD555D;font-size:20px">领先</span>
	                         <#elseif myOffer[0].status == 0>
                                 <span class='auction-string' style="font-size:20px">出局</span>
                             <#elseif myOffer[0].status == 8>
                                 <span class='auction-string' style="color:#FD555D;font-size:20px">得标</span>
                             </#if>
	                    </span>
                        </#if>
                    </div>

                <#else>
                    <button type="button" class="button button-l disabled" style="width:90%;" disabled="disabled">已下架
                    </button>
                </#if>

            </div>
            <div class="product-attr" style='margin-top:100px'>
                <div class="tab product-attr-tab clearfix">
                    <ul>
                        <li data-id="1">竞拍流程</li>
                    </ul>
                </div>
                <div class="tab-cont product-attr-cont">
                    <div style="line-height:150%;">
                        1、支付保证金 > 2、参与竞价 > 3、竞拍得标 > 4、支付尾款 > 5、拍卖成功
                    </div>
                </div>
                <div class="designer-detail" style='margin-top:50px'>
                    <a class="CustomerCare" style="margin-right:20px;cursor:pointer"
                       onclick="NTKF.im_openInPageChat();"><span style="margin-right:5px"></span>联系客服</a>
                </div>

            </div>
            <div class="product-others">
                <div class="share-box"
                     data-config="{'pic':'${picture_base}/${result.datas.product.product.productImageListFirst}'}">
                    <span class="float-left" style="margin-top:2px;line-height:31px;"> 分享：</span>
                    <a class="share weixin" data-cmd="weixin"></a>
                    <a class="share qq" data-cmd="sqq"></a>
                    <a class="share qzone" data-cmd="qzone"></a>
                    <a class="share weibo" data-cmd="tsina"></a>
                    <a class="share douban" data-cmd="douban"></a>
                </div>
                <div class="product-other-operator">
                    <a href="javascript:" data-url="" success-tip="收藏成功！" call-back="false" class="ajax-request"
                       style="padding-left: 35px;padding-top: 5px;"> <i class="collection"></i>
                        收藏</a>&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <div class="product-relate hide-two">
        </div>
    </div>
    <div id="product-detail" style="height:0;"></div>
    <div class="product-cont-bar" data-offset="50" data-scroll-holder="product-suspend-holder" data-scroll-end="footer"
         data-scroll-bg="product-cont-tab-bar">
        <div class="tab clearfix">
            <ul>
                <li class="on" data-id="desc">商品详情</li>
                <li class="offerList" data-id="offer" data-url="/auction/offer/${result.datas.product.id}">竞拍记录
                    <span>(${(result.datas.offers.list?size)!})</span></li>
            </ul>
        </div>
    </div>
    <div id="product-suspend-holder" class="product-suspend-holder"></div>
    <div class="product-cont-tab-cont">
        <div id="product-detail-desc" class="product-desc lazyload load-process">
            <#if result.datas.product.product.description?exists>
                ${result.datas.product.product.description?replace('<img src=','<img src="${static_base}/c/images/space.gif" data-image=')}
            </#if>

        </div>
        <div id="product-detail-offer" class="product-desc display-none">
        </div>
    </div>
</div>
<div class="product-suspend-bar" id="product-cont-tab-bar" style="display:none;"></div>

<script id="auctionlist" type="text/html">
    <span>其他拍卖</span>
    {{each list as value i}}
    <div class="relate-item">
        <p class="relate-item-pic"><a href="/auction/product/{{value.id}}" target="_blank"><img
                        src="${picture_base}{{value.product.productImageCover}}!120" alt="{{value.title}}"/></a></p>
        <p class="price">&yen;{{value.currentPrice}}</p>
    </div>
    {{/each}}
</script>

<script>
    //获取拍卖列表
    $.getJSON('/auction/list?p=1&pageSize=4', function (data) {
        if (data.result.status == 1) {
            var data = data.result.datas.auctionProductList;
            var html = template('auctionlist', data);
        }
        $('.product-relate').append(html);
    });

    //获取竞价列表

    function countdownOver() {
        location.reload();
    }

    $('.Auctiondecrease').click(function () {
        var step_price = parseInt($('#step-price').attr('data-value'));
        var offer_price = parseInt($('#offer-price').val());
        var current_price = parseInt($('#current-price').attr('data-value'));
        if (offer_price > (current_price + step_price)) {
            $('#offer-price').val(offer_price - step_price);
        } else {
            $.flashTip('出价不能比最低出价低。', 'warning');
        }
        return false;
    });

    $('.Auctionincrease').click(function () {
        var step_price = parseInt($('#step-price').attr('data-value'));
        var offer_price = parseInt($('#offer-price').val());
        $('#offer-price').val(offer_price + step_price);
        return false;
    });

    $('.auction-button').click(function () {
        var button = $(this),
            auctionId = button.attr('data-id'),
            type = button.attr('data-type'),
            current = parseInt($('#current-price').attr('data-value')),
            offer = parseInt($('#offer-price').val()),
            step_price = parseInt($('#step-price').attr('data-value'));
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                userLogin();
            } else {
                if (type == 'margin') {
                    $.post('/auction/margin/create/' + auctionId + '.json', function (data) {
                        if (data.result.status == 1) {
                            var params = data.result.datas.params;
                            var auctionSn = data.result.datas.marginSn;
                            location.href = '/payment/prepare/margin/' + auctionSn + '?' + params;
                        } else {
                            $.flashTip(data.result.message, 'error');
                        }
                    });
                }
                if (type == 'auction') {
                    jConfirm('您确定要出价吗', '确认出价', function (r) {

                        if (r) {
                            $.post('/auction/offer.json', {id: auctionId, offer: offer}, function (data) {
                                if (data.result.status == 1) {
                                    $.flashTip('出价成功', 'success');
                                    setTimeout(function () {
                                        location.reload();
                                    }, 2500);
                                } else if (data.result.status == -2 || data.result.status == -3) {
                                    $.flashTip(data.result.message, 'error');
                                    setTimeout(function () {
                                        location.reload();
                                    }, 2500);
                                } else {
                                    $.flashTip(data.result.message, 'error');
                                    ;
                                }
                            })
                        }
                    })
                }
                if (type == 'payauction') {
                    $('#form-auction').submit();
                }
            }
        });
        return false;
    });

</script>
<@m.page_footer js='modules/page.product' />