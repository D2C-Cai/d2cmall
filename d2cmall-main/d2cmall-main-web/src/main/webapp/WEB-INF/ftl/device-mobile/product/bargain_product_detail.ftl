<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="${bargainPriceDO.bargain.name}" css="bargain" js='utils/jquery.qrcode' keywords="全球好设计,设计师品牌,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${bargainPriceDO.bargain.shareDes}" service='false'/>
<#if !m.FROMAPP>
    <@m.app_download_bar fixed="false" />
</#if>
<#if bargainPriceDO?exists>
    <img src="${picture_base}${bargainPriceDO.bargain.sharePic}" style="display:none;">
    <div class="bg_page" onclick="$(this).hide()">
        <div class="web_bg"></div>
    </div>
    <div id="img-bill">

    </div>
    <section class="share-type">
        <div class="share-cp">
            <i class="pop-close" onclick="$(this).parent().parent().hide()">
            </i>
            <div class="friend"><i class="icon-wx"></i>
                <p>发给好友</p></div>
            <div class="billcreat"><i class="icon-hb"></i>
                <p>生成海报</p></div>
        </div>
    </section>
    <div class="bargin-section">
        <#if bargainPriceDO.statusName=='砍价成功'>
            <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                <div class="greytips">你的好友很给力，已帮砍到最低价格哟</div>
            </#if>
        </#if>
        <div class="bargin-top">
            <img src="${picture_base}${bargainPriceDO.bargain.coverPic}" style="width: 100%;">
            <a href="javascript:;" class="brule show-rule"> </a>
            <div class="marquee" id="marquee"></div>
        </div>
        <div class="bargin-productinfo" style="position:relative;">
            <a href="/page/520zhounianqing" class="pro-go" style="display:none"><img
                        src="https://static.d2c.cn/img/topic/190509/520/logo_j.png" style="width:100%;"></a>
            <div class="barpro-title">${bargainPriceDO.bargain.name}</div>
            <#if bargainPriceDO.statusName=='砍价成功' >
                <div style="margin-top:8px;margin-bottom:20px;padding-left:15px;font-size:14px;rgba(35,36,39,.54)">
                    该商品价格已经砍到<span style="font-size:16px;color:#F23365;">最低</span></div>
            <#elseif bargainPriceDO.statusName=='砍价中' >
                <div class="bar-times on count-down" data-type="split-time"
                     data-endtime="${bargainPriceDO.bargain.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                     data-musttime="must"><span>距离结束时间</span><span class="n-time  hour down-time">00</span><span
                            class="k-time">时</span><span class="n-time  minute down-time">00</span><span class="k-time">分</span><span
                            class="n-time second down-time">00</span><span class="k-time">秒</span></div>
            <#elseif bargainPriceDO.statusName=='等待支付' >
                <div class="bar-times off">亲~请尽快支付</div>
            <#else>
                <div class="bar-times off">该商品砍价活动已结束</div>
            </#if>
        </div>
        <div class="bargin-contents">
            <div class="pro-price flex">
                <div class="o-price">
                    <p>原价</p>
                    <p style="color:#F8C26C;">¥${product.minPrice}</p>
                </div>
                <div class="o-price text-right">
                    <p>最低价</p>
                    <p style="color: #f23365;">¥${bargainPriceDO.bargain.minPrice}</p>
                </div>
            </div>
            <div class="progress-pic" style="position:relative">
                <div class="progress-bar">
                    <div class="progress-act-bar">
                        <div class="progress-tip">砍后价${bargainPriceDO.price}</div>
                    </div>
                </div>
            </div>

            <#if bargainPriceDO.statusName=='砍价中'>
            <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                <div class="bargin-dobutton">
                    <button class="shareD2Cbar">寻求帮助</button>
                    <button class="gobuy">去购买 ¥${bargainPriceDO.price}</button>
                </div>
            <#else>
                <div class="bargin-dobutton">
                    <button class="help zoom">帮好友砍一刀</button>
                    <button class="iwant">我要发起砍价</button>
                </div>
            </#if>

            <#elseif bargainPriceDO.statusName=='砍价成功'>
                <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                    <div class="bargin-dobutton">
                        <button class="gobuy">去购买 ¥${bargainPriceDO.price}</button>
                    </div>
                <#else>
                    <div class="bargin-dobutton">
                        <button onclick="javascript:location.href='/bargain/promotion/list?status=1'">查看其他砍价</button>
                    </div>
                </#if>
            <#elseif bargainPriceDO.statusName=='支付超时'>
                <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                    <div class="bargin-dobutton">
                        <button onclick="javascript:location.href='/product/${product.id}'">原价购买</button>
                        <button onclick="javascript:location.href='/bargain/promotion/list?status=1'">查看其他砍价</button>
                    </div>
                <#else>
                    <div class="bargin-dobutton">
                        <button onclick="javascript:location.href='/bargain/promotion/list?status=1'">查看其他砍价</button>
                        <button class="gobargin">发起砍价</button>
                    </div>
                </#if>
            <#elseif bargainPriceDO.statusName=='砍价结束'>
                <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                    <div class="bargin-dobutton">
                        <button class="gobuy">去购买</button>
                    </div>
                <#else>
                    <div class="bargin-dobutton">
                        <button onclick="javascript:location.href='/bargain/promotion/list?status=1'">查看其他砍价</button>
                    </div>
                </#if>
            <#elseif bargainPriceDO.statusName=='等待支付'>
            <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
            <div class="bargin-dobutton">
                <button onclick="javascript:location.href='/member/order'">去支付</button>
            </div>
        </div>
    <#else>
        <div class="bargin-dobutton">
            <button onclick="javascript:location.href='/bargain/promotion/list?status=1'">查看其他砍价</button>
            <button class="gobargin">发起砍价</button>
        </div>
        </#if>
    <#else>
        <div class="bargin-dobutton">
            <button onclick="javascript:location.href='/bargain/promotion/list?status=1'">查看其他砍价</button>
        </div>
        </#if>
    </div>
    <#if bargainPriceDO.description>
        <div style="font-size:12px;padding:10px;line-height:180%;margin:20px 10px 10px 10px;box-shadow:0 0 5px #CCC;background:#FFFFEE;">
            <strong>温馨提示</strong>：</br>
            ${bargainPromotion.description}
        </div>
    </#if>
    <div class="more-bargain">
        <div class="bargin-list-title">
            <p style="font-weight:bold;">砍价商品</p>
            <p><a href="/bargain/promotion/list?status=1" onclick="TDAPP.onEvent('前往砍价商城')"
                  style="color:#FD555D;font-size:12px;padding-right:15px;">砍价商城>>>></a></p>
        </div>
        <div style="overflow:hidden">
            <ul class="bargain-list-content">

            </ul>
        </div>
    </div>
    </div>


    <div class="app-download-bar">
        <div style="background:#FFF;border-bottom:1px solid #EEE;">
            <div class="bar-button">
                <a href="//m.d2cmall.com/page/follownumber" class="open-app"
                   style="background:#FFF;border:1px solid #000;color:#000;margin-top:-5px;box-shadow:none;"
                   onclick="TDAPP.onEvent('立即关注');">立即关注</a>
            </div>
            <div class="bar-icon"><img src="//static.d2c.cn/common/nm/img/icon_launcher.png" alt="D2C-ICON"></div>
            <div class="bar-cont open-app" href="javascript:void(0)" mlink-handling="true">
                <div>
                    <div><p><strong>D2C全球好设计</strong></p>
                        <p>关注公众号，领取700元新人礼包</p></div>
                </div>
            </div>
        </div>
    </div>

    <div class="bargin-size">
        <table class="c-title" style="width:100%">
            <tr>
                <th>颜色</th>
                <th>尺码</th>
                <th style="border:0;">仅剩件数</th>
            </tr>
        </table>
    </div>
    <div class="bargin-nav">
        <div class="nav-items on" style="margin-right:50px;" data-id="1">助力榜单</div>
        <div class="nav-items" data-url="/product/detail/${product.productId}?type=ajax" data-id="2">商品详情</div>
    </div>
    <div class="mybargin-list">
        <#if  bargainPriceDO.helpers?size gt 0>
            <#list bargainPriceDO.helpers?sort_by("helpDate")?reverse as list>
                <div class="mybargin-item">
                    <p class="r-time">${list.helpDate?string("yyyy/MM/dd HH:mm:ss")}</p>
                    <div class="flex usrinfo">
                        <div class="user-pic flex">
                            <#if list.headPic>
                                <img src="<#if list.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${list.headPic}"
                                     class="head-pic"/>
                            <#elseif list.thirdHeadPic>
                                <img src="${list.thirdHeadPic}" class="head-pic"/>
                            <#else>
                                <img src="//static.d2cmall.com/img/home/160627/images/headpic.png"></img>
                            </#if>
                            <span><#if list.userName>${list.userName}<#else>匿名用户</#if></span>
                        </div>
                        <div class="uesr-price">
                            <span style="padding-right:3px;color:rgba(0,0,0,.54)">帮砍掉</span> ${list.subPrice}元
                        </div>
                    </div>
                </div>
            </#list>
        <#else>
            <div style="padding:20px 0;color:rgba(0,0,0,.38);text-align:center;line-height:20px;">
                <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                    <p>还没有好友为你砍价</p>
                    <p>赶紧召集亲朋好友拔刀相助</p>
                <#else>
                    <p>还没有好友为他砍价</p>
                </#if>
            </div>
        </#if>
    </div>

    <div id="product-detail-content" class="product-detail-content" style="display:none">
        <div class="loadding">
            <i class="icon-spinner icon-spin icon-large"></i> 正在加载内容
        </div>
    </div>
    <div class="more-promotion">


    </div>

    <div style="height:50px"></div>
    </div>
    <div class="product-detail" style="display:none">
        <div class="drawer_screen"></div>
        <div class="drawer_attr_box">
            <div class="product-select" style="margin-bottom:0;">
                <div class="close_icon_v" onclick="javascript:$(this).parent().parent().parent().hide();">
                    <image src="//static.d2c.cn/img/promo/bargin/icon_a_close.png" class="close_icon">
                </div>
                <form name="form-buy" id="form-buy" action="/order/bargain/buynow" method="post">
                    <input type="hidden" name="skuId" value=""/><!-- 货号 -->
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <input type="hidden" name="bargainId" value="${bargainPriceDO.id}"/>
                    <div class="product-sku img clearfix" style="position:relative;">
                        <span class="font">颜色 </span>
                        <span class="value">
                <#if product.salesproperty>
                    <#list product.salesproperty?eval.sp1 as p>
                        <a href="javascript:" id="c-${p.id}" class="color-section" data-type="color" data-id="${p.id}"
                           rel="${p.value}" data-image="<#if p.img!=''>${picture_base}/${p.img}</#if>">${p.value}</a>
                    </#list>
                </#if>
                </span>
                    </div>
                    <div class="product-sku txt clearfix">
                        <span class="font">尺码 </span>
                        <span class="value">
                <#if product.salesproperty>
                    <#list product.salesproperty?eval.sp2 as p>
                        <a href="javascript:" id="s-${p.id}" class="size-section" data-type="size" data-id="${p.id}"
                           rel="${p.value}">${p.value}</a>
                    </#list>
                </#if>
                </span>
                    </div>

                    <div class="product-sku clearfix num-crease">
                        <span class="font">数量 </span>
                        <div class="num-crease-wrap"><span class="num-input">1</span><input type="hidden"
                                                                                            class="product-count"
                                                                                            id="num" name="num" readonly
                                                                                            value="1"></div>
                        <span class="store-span"></span>
                        <input type="hidden" name="store" id="store" value="${(product.availableStore)!}"/>
                    </div>
                    <div class="layer-buy">

                        <#if product.status!=3>
                            <div style="overflow:hidden;padding:3px;text-align:center">
                                <#if product.mark==0>
                                    <button type="button" class="button disabled" disabled style="width:85%;">已下架
                                    </button>
                                <#elseif product.store lte 0>
                                    <button type="button" class="button disabled" disabled style="width:85%;">已售完
                                    </button>
                                <#else>
                                    <button type="button" data-type="buy" class="operator-button button button-red"
                                            style="cursor:pointer;height:40px;line-height:40px;width:85%;text-align: center;background: #000;color: #FFF;border:0;">
                                        立即购买
                                    </button>
                                </#if>
                            </div>
                        </#if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    </div>
    </div>
    <a href="/page/520zhounianqing"><img src="https://static.d2c.cn/img/topic/190509/520/logo_j.png" width="100%"
                                         id="nav"/></a>
    <share data-title="${bargainPriceDO.bargain.shareTitle}"
           data-pic="${picture_base}${bargainPriceDO.bargain.sharePic}"
           data-desc="${bargainPriceDO.bargain.shareDes?replace('#price',(bargainPriceDO.price))}"></share>
    <script>
        var page_elem = {
            init: function () {
                $.get('/product/detail/${product.productId}?type=ajax', function (data) {
                    $('#product-detail-content').data('isLoad', true);
                    $('#product-detail-content').html(data);
                });
                var shareSelfData = localStorage.getItem('TEMP.bargainShareList') ? JSON.parse(localStorage.getItem('TEMP.bargainShareList')) : [];
                console.log(shareSelfData);
                var id = '${bargainPriceDO.id}';
                var nowprice =${bargainPriceDO.price};
                var desc = $("share").attr('data-desc').replace('#price', nowprice);
                $("share").attr('data-desc', desc);
                $('meta[name="description"]').attr("content", desc)
                this.size();
                this.marquee();
                this.lisen();
                this.showPrice();
                this.share();
                this.help();
                this.nav();
                this.buy();
                this.getbargainList();
                this.toApp();
                <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
                if (shareSelfData.indexOf(id) < 0) {
                    this.shareSelf();
                }
                </#if>
            },
            toApp: function () {
                var message = {
                    handlefunc: 'w_bill',
                    func: "cessback",
                    type: "bill",
                    originalPrice: '${bargainPriceDO.originalPrice}',
                    price: '${bargainPriceDO.price}',
                    minPrice: '${bargainPriceDO.bargain.minPrice}',
                    pic: '${bargainPriceDO.pic}',
                    productPic: "${product.productImageCover}",
                    pageBackGround: "https://static.d2c.cn/other/bill/billbgs.png",
                    mBackGround: "https://static.d2c.cn/other/bill/bg_plants.png"
                }
                if (app_client) $.D2CMerchantBridge(message)
            },
            getbargainList: function () {
                $.getJSON('/bargain/promotion/list.json?status=1', function (res) {
                    var list = res.list.list;
                    var str = '';
                    $.each(list, function (i, d) {
                        console.log(d.product.productImageCover.indexOf('http') == -1)
                        if (d.product.productImageCover.indexOf('http') == -1) {
                            var img = '${picture_base}' + d.product.productImageCover + '!/both/1000x1400'
                        } else {
                            var img = d.product.productImageCover
                        }
                        str = str + '<li><a href="/bargain/detail/' + d.id + '"><img src="' + img + '"><p class="title">' + d.product.name + '</p><p class="a-price">&yen;' + d.minPrice + '<span style="text-decoration:line-through;font-size:10px;color:#ccc;padding-left:10px;">&yen;' + d.product.minPrice + '</span></p></a></li>'
                    })

                    $('.bargain-list-content').append(str)
                });
            },
            shareSelf: function () {
                var html = '<div class="share-pop-content"><div class="share-icon"><img src="//static.d2c.cn/img/topic/180915/fresh/images/share_group_icon.png"  style="width:100%"/></div><div class="share-pop-center"><div class="title">分享到微信群最多可砍${max}元</view><button  class="share-button">分享到微信群</button></div></div>'
                popupModal({content: html});
                $('.share-button').on('click', function () {
                    popupModalClose();
                    $('.bg_page').show();
                })
                var shareSelfData = localStorage.getItem('TEMP.bargainShareList') ? JSON.parse(localStorage.getItem('TEMP.bargainShareList')) : [];
                var id = '${bargainPriceDO.id}';
                if (shareSelfData.indexOf(id) == -1) {
                    shareSelfData.push(id);
                    localStorage.setItem("TEMP.bargainShareList", JSON.stringify(shareSelfData));
                }
            },
            size: function () {
                $.getJSON('/product/skuStore/${product.id}', function (res) {
                    var str = '';
                    $.each(res.result, function (i, d) {
                        str = str + '<tr><td>' + d.color + '</td><td>' + d.size + '</td><td>' + (d.flashStore + d.freezeStore) + '</td></tr>'
                    });
                    $('.c-title').append(str)
                });
            },
            marquee: function () {
                var html = '';
                var i = 0;
                $.getJSON('/bargain/newest/${bargainPriceDO.bargainId}?pageSize=20', function (data) {
                    if (data.list.length > 0) {
                        $.each(data.list, function (ii, item) {
                            if (item.nickname) {
                                if (item.headPic == null) {
                                    item.headPic = '//static.d2cmall.com/img/home/160627/images/headpic.png'
                                } else if (item.headPic.indexOf('http') != -1) {
                                    item.headPic = item.headPic
                                } else {
                                    item.headPic = '${picture_base}' + item.headPic;
                                }
                                html += '<div><img src="' + item.headPic + '" /><span class="name">' + item.nickname + '</span>被砍到' + item.price + '元</div>';
                                i++;
                            }
                        });
                        if (i > 3) {
                            $('#marquee').html('<div class="marquee-list">' + html + '</div>');
                            setInterval(function () {
                                $('.marquee-list').animate({marginTop: '-4em'}, 100, function () {
                                    $(this).css({"margin-top": "0px"});
                                    $(this).find("div:eq(0),div:eq(1),div:eq(2)").appendTo($(this));
                                });
                            }, 3000);
                        }
                    }

                });
            },


            showPrice: function () {
                var price = Math.abs(${bargainPriceDO.originalPrice-bargainPriceDO.price});
                var tprice = Math.abs(${bargainPriceDO.originalPrice-bargainPriceDO.bargain.minPrice});
                var percent = ((price / tprice) * 100);
                $('.progress-act-bar').width(percent + '%');
                if (percent < 6) {
                    $('.progress-tip').addClass('begin');
                }
                if (percent > 94) {
                    $('.progress-tip').addClass('end');
                }
            },
            lisen: function () {
                $('.show-rule').on(click_type, function () {
                    var html = '<div class="popup-content"><div class="popup-rule">\
				<div class="rule-title">活动规则</div>\
				<div class="rule-info">\
				<p style="font-weight: bold">温馨提示 </p>\
				1、活动期间砍到心仪价请尽快下单哦  避免无库存！<br /><br />\
				2、活动期间同一用户id限砍3件<br /><br />\
                3、同一用户ID 24小时内只能帮砍3次 <br /><br />\
                4、同一用户ID一款砍价商品只能购买一次！同时只能成功支付3次砍价商品 <br /><br />\
                5、如遇到库存为0，还能发起砍价，这种情况是其它用户未付款，您可以继续分享，遇到有库存尽快购买！<br /><br />\
                6、砍价成功后请在5分钟内付款，过期订单将被关闭。<br /><br />\
                <p style="font-weight: bold">规则说明  </p>\
                1、用户直接进入D2C砍价商城主题活动页，选择活动商品，即可发起砍价活动，成为发起者。<br /><br />\
                2、发起者需将活动商品通过分享给其他用户（即砍价者），砍价者即可帮助发起者进行砍价。<br /><br />\
                3、发起者可以在砍价商品活动期间任意时间以优惠价（当前时间节点砍到的价位）进行购买操作，另外在商品活动结束后有24小时的付款时间，逾期则将失去购买活动商品的资格。如在活动期间内砍价成功则以商品底价购买所分享的商品<br /><br />\
                4、砍价者无法以优惠价购买所砍价的商品，除非其成为该商品的发起者。<br /><br />\
                5、同一砍价者就同一发起者分享的同一商品仅能进行一次砍价。<br /><br />\
               6、在活动中，如果用户出现违规行为（包括不限于作弊、使用第三方工具刷单、扰乱系统、实施网络攻击等），D2C将取消违规用户的优惠购买资格，并有权终止其参与本活动。<br /><br />\
				</div>';
                    popupModal({
                        content: html
                    });
                })
                $('.gobargin').on('click touchstart', function () {
                    location.href = "/bargain/detail/${bargainPriceDO.bargain.id}"
                })

            },
            share: function () {
                $('.shareD2Cbar').on('click', function (e) {
                    $('.bg_page').show();
                    TDAPP.onEvent('砍价分享好友');
                    e.stopPropagation();
                });
            },
            help: function () {
                var that = this;
                $('.help').on(click_type, function () {
                    <#if  browser=='wechat'&& wechat >
                    $.getJSON('/member/islogin', function (data) {
                        var isd2c = data.result.datas.isD2C;
                        if (data.result.login == false) {
                            userLogin();
                        } else {
                            $.ajax({
                                'url': '/bargain/add/help',
                                'type': 'post',
                                'data': {bargainPriceId: '${bargainPriceDO.id}', unionId: '${m.LOGINMEMBER.unionId}'},
                                'dataType': 'json',
                                'success': function (res) {
                                    var price = res.price;
                                    var ratio = res.ratio;
                                    if (res.result == 0) {
                                        toast({position: 'center', type: 'error', message: '砍价失败啦~小D正在努力查看'});
                                    } else if (res.result.status == -1) {
                                        toast({position: 'center', type: 'error', message: res.result.message});
                                    } else if (!isd2c) {
                                        that.joind2c(price, ratio);
                                    } else {
                                        var html = '<div class="popup-content">\
									 <i class="bargain-success"></i>\
							 		<div id="success-info" class="bargain-help-content">\
							 			<div class="success-info-text">\
							 			    <p class="big-tips">砍价成功</p>\
							 				<p style="font-size:14px;color:#C1876A;margin-top:10px;">本次你帮他砍掉¥ <span style="font-size:20px;color:#F23365">' + price + '</strong> </span></p>\
										</div>\
										<a class="bargin-color-button"  href="/bargain/detail/${bargainPriceDO.bargain.id}">我也要砍价>>></a>\
									</div>';
                                        popupModal({content: html});
                                        $('.getred').on(click_type, function () {
                                            //page_elem.getredcard();
                                        });
                                        if (price / ${bargainPriceDO.originalPrice} > ratio) {
                                            $('.big-tips').html('<span style="-webkit-animation-delay:0s;animation-delay:0s;">惊</span><span style="-webkit-animation-delay:0.1s;animation-delay:0.1s;">喜</span><span style="-webkit-animation-delay:0.2s;animation-delay:0.2s;">一</span><span style="-webkit-animation-delay:0.3s;animation-delay:0.3s;">刀</span>');
                                        }
                                    }
                                }
                            });
                        }
                    });
                    <#else>
                    alert('请使用微信参与本活动哟~')
                    </#if>

                });
            },
            <#if m.LOGINMEMBER.id==bargainPriceDO.memberId>
            helpSelf: function () {
                $.ajax({
                    'url': '/bargain/add/help',
                    'type': 'post',
                    'data': {bargainPriceId: '${bargainPriceDO.id}', unionId: '${m.LOGINMEMBER.unionId}'},
                    'dataType': 'json',
                    'success': function (res) {
                        var price = res.price;
                        var ratio = res.ratio;
                        if (res.result == 0) {
                            toast({position: 'center', type: 'error', message: '砍价失败啦~小D正在努力查看'});
                        } else if (res.result.status == -1) {
                            toast({position: 'center', type: 'error', message: res.result.message});
                        } else {
                            var html = '<div class="popup-content">\
						 <i class="bargain-success"></i>\
				 		<div id="success-info" class="bargain-help-content">\
				 			<div class="success-info-text">\
				 			    <p class="big-tips">砍价成功</p>\
				 				<p style="font-size:14px;color:#C1876A;margin-top:10px;">本次帮自己砍掉¥ <span style="font-size:20px;color:#F23365">' + price + '</strong> </span></p>\
							</div>\
							<a class="bargin-color-button" onlick="popupModalClose()">右上角邀请好友帮砍吧</a>\
						</div>';
                            popupModal({content: html});
                        }
                    }
                })
            },
            </#if>
            joind2c: function (price, ratio) {
                var html = '<div class="popup-content">\
			<i class="bargain-success"></i>\
		    <form class="validate-form form d2c-register-form" action="/member/bind" method="post" call-back="activityBind" success-tip="加入D2C成功" style="margin:0;">\
		    	<div id="input-mobile" class="bargain-help-content"  style="height:334px;">\
			    	<div class="success-info-text">\
			    		<p class="big-tips">砍价成功</p>\
		 				<p>本次你帮砍<span class="red">¥ <strong>' + price + '</strong> </span></p>\
					</div>\
					<div class="flex" style="justify-content:space-between">\
					 <div class="award-a"><p style="font-size:16px;padding-top:10px;color:#FFF;font-weight:700">700元</p><p style="font-size:10px;margin-top:10px;color:#FFF;">新人专享大礼包</p></div>\
					</div>\
					<input type="hidden" name="nationCode" class="mobile-code" value="86"/>\
			    	<input name="loginCode" id="mobile" placeholder="请输入手机号码" pattern="[0-9]" maxlength="11"  type="number" class="mobile-input validate-account"/>\
			    	<button class="bargin-color-button joined" type="button" >领取以上福利>>></button>\
				</div>\
				<div id="inout-code" class="bargain-help-content" style="height:300px;display:none;">\
					<div class="popup-help-success-info"><p>输入验证码</p></div>\
					<div id="mobile-text">1300000000</div>\
					<div id="change-mobile" style="margin-top:14px;color:#F23365;font-size:12px;text-align:left;padding-left:25px">修改手机号码 > </div>\
					<div class="sms-codes">\
						<input name="code" placeholder="请输入验证码" pattern="[0-9]" maxlength="4" type="number" class="mobile-input"/>\
					</div>\
					<button class="bargin-color-button validate-send validate-button"  data-source="" data-rule="mobile" data-type="MEMBERMOBILE" data-random="1" type="button">获取验证码</button>\
				</div>\
				</form>\
				<div>';
                popupModal({content: html});
                if (price / ${bargainPriceDO.originalPrice} > ratio) {
                    $('.big-tips').html('<span style="-webkit-animation-delay:0s;animation-delay:0s;">惊</span><span style="-webkit-animation-delay:0.1s;animation-delay:0.1s;">喜</span><span style="-webkit-animation-delay:0.2s;animation-delay:0.2s;">一</span><span style="-webkit-animation-delay:0.3s;animation-delay:0.3s;">刀</span>');
                }
                $('.sms-code').on(click_type, function () { //验证码输入框
                    $(this).find('input').focus();
                });
                $('input[name=code]').on('keyup', function (event) {
                    if ($(this).val().length > 4) $(this).val($(this).val().slice(0, 4))
                    var code_value = $(this).val();
                    for (var i = 0; i < 4; i++) {
                        var v = code_value.substr(i, 1);
                        $('.sms-code li').eq(i).text(v);
                    }
                });
                $('.joined').on(click_type, function () {
                    TDAPP.onEvent('砍价新用户绑定');
                    var mobile = $('#mobile').val();
                    var myreg = /^1\d{10}$/;
                    if (mobile.length == 11 && myreg.test(mobile)) {
                        $('#input-mobile').hide();
                        $('#inout-code').show();
                        $('.bargain-success').hide();
                        $('#mobile-text').text('已向' + mobile + '发送验证码');
                        $('.validate-send').trigger('click');
                    } else {
                        toast({position: 'center', type: 'error', message: '请输入正确的手机号码'});
                    }
                })
                $('#change-mobile').on(click_type, function () {
                    $('#input-mobile').show();
                    $('#inout-code').hide();
                    $('.bargain-success').show();
                })
                $("input[name=code]").bind("input propertychange", function () {
                    var code = $(this).val();
                    if (code.length == 4) {
                        $('.d2c-register-form').submit();
                    }
                })
            },
            nav: function () {
                $('.nav-items').on(click_type, function () {
                    var id = $(this).attr('data-id');

                    $(this).addClass('on').siblings('.nav-items').removeClass('on');
                    if (id == '1') {
                        $('.product-detail-content').hide();
                        $('.mybargin-list').show();
                    } else {
                        $('.product-detail-content').show();
                        $('.mybargin-list').hide();
                    }
                })
            },
            buy: function () {
                $('.gobuy').on(click_type, function (e) {
                    var Event = e || event;
                    TDAPP.onEvent('砍价商品购买');
                    $('.product-detail').show();
                    Event.stopPropagation();
                })
                $('.iwant').on(click_type, function () {
                    $.getJSON('/member/islogin', function (data) {
                        if (data.result.login == false) {
                            userLogin();
                        } else if (data.result.datas.isBind == false) {
                            location.href = "/member/bind"
                            //userbind();
                        } else {
                            TDAPP.onEvent('发起砍价');
                            location.href = "/bargain/detail/${bargainPriceDO.bargain.id}"
                        }
                    })
                });
            },
            getredcard: function () {
                $.ajax({
                    'url': '/bargain/receive/redpackets',
                    'type': 'post',
                    'data': {},
                    'dataType': 'json',
                    'success': function (data) {
                        if (data.status == 1) {
                            toast({position: 'center', type: 'success', message: '成功领取5元红包'});
                        } else {
                            toast({position: 'center', type: 'error', message: data.result.message});
                        }
                        setTimeout(function () {
                            location.reload();
                        }, 1000);
                    }
                })
            }
        }
        page_elem.init();


        function activityBind() {
            //page_elem.getredcard();
            $.popupModalClose();
            var html = '<div class="popup-content">\
			 <i class="bargain-success"></i>\
	 		<div id="success-info" class="bargain-help-content">\
	 			<div class="success-info-text">\
	 			    <p class="big-tips">绑定成功</p>\
				</div>\
				<div class="flex" style="justify-content:space-between">\
				 <div class="award-a"><p style="font-size:16px;padding-top:10px;color:#FFF;font-weight:700">520抽奖</p><p style="font-size:10px;margin-top:10px;color:#FFF;">每日一次抽奖机会</p></div>\
				 <div class="award-a"><p style="font-size:16px;padding-top:10px;color:#FFF;font-weight:700">700元</p><p style="font-size:10px;margin-top:10px;color:#FFF;">新人专享大礼包</p></div>\
				</div>\
				<a class="bargin-color-button" href="/page/xinren" onclick="TDAPP.onEvent("砍价新人领取礼包");">领取新人福利>>></a>\
			</div>';
            popupModal({content: html});

        }

        $('.product-sku a').on(click_type, function () {
            var obj = $(this);
            if (obj.attr('class').indexOf('disabled') == -1) {
                if (obj.hasClass('selected')) {
                    obj.removeClass('selected');

                    obj.hasClass('color-section') ? $('.size-section').removeClass('disabled').removeAttr('disabled') : $('.color-section').removeClass('disabled').removeAttr('disabled');
                } else {
                    obj.siblings().removeClass('selected');
                    obj.addClass('selected');

                }
                var tips = new Array(), str = '', param = '';
                $.each($('.product-sku').find('.selected'), function (i, d) {
                    tips.push('"' + $(d).attr('rel') + '"');
                    param += str + $(d).attr('data-type') + '=' + $(d).attr('data-id');
                    str = '&';
                });
                if (param) {
                    $.post('/product/getSKUInfo/${product.id}', param, function (data) {
                        var skuId = data.result.skuId == undefined ? '' : data.result.skuId;
                        var store = data.result.store;
                        if (data.result.status == 1) {
                            if (typeof (data.result.Size) == 'object') {
                                $('.img a').addClass('disabled').attr('disabled', true);
                                $.each(data.result.Size, function (i, d) {
                                    if (d.store > 0)
                                        $('#c-' + d.Color_id).removeClass('disabled').removeAttr('disabled');
                                });
                            }

                            if (typeof (data.result.Color) == 'object') {
                                $('.txt a').addClass('disabled').attr('disabled', true);
                                $.each(data.result.Color, function (i, d) {
                                    if (d.store > 0)
                                        $('#s-' + d.Size_id).removeClass('disabled').removeAttr('disabled');
                                });
                            }

                            if (typeof (data.result.Color) == 'object' && typeof (data.result.Size) == 'object') {
                                $('#store').val(store);
                                $('input[name=skuId]').val(skuId);
                                data.result.store ? $('.store-span').html('') : $('.store-span').html('');
                                //	 data.result.store?$('.store-span').html(' &nbsp;库存 '+store+' 件'):$('.store-span').html('');
                            } else {
                                $('#store').val('');
                                $('input[name=skuId]').val('');
                                $('.store-span').html('');
                            }

                        } else if (data.result.status == -1) {
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

        $('.friend').on(click_type, function (e) {
            $(this).parent().parent().hide();
            $('.bg_page').show();
            e.stopPropagation();
        })
        $('.operator-button').on(click_type, function () {
            //验证是否填写颜色和尺码
            var type = $(this).attr('data-type');
            var skuId = $('input[name=skuId]').val();
            if (skuId == '') {
                $('.product-sku a').addClass('animated shake');
                toast({position: 'bottom', type: 'error', message: '请选择颜色和尺码'});
                setTimeout(function () {
                    $('.product-sku a').removeClass('animated shake');
                }, 600);
                return false;
            }

            //验证是否正确填写数量
            var num = parseInt($('input[name=num]').val());

            if (isNaN(num) || num < 1) {
                toast({position: 'bottom', type: 'error', message: '请选择购买数量'});
                $(window).scrollTop('720');
                return false;
            }
            if ($('input[name=num]').val() > parseInt($('#store').val())) {
                toast({position: 'bottom', type: 'error', message: '最多只能购买' + $('#store').val() + '件。'});
                $('#num').val($('#store').val());
                return false;
            }
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    $('body').data('function', '$("#form-buy").submit()');
                    userLogin();
                } else if (data.result.datas.isBind == false) {
                    location.href = "/member/bind"
                } else {
                    $('#form-buy').submit();
                }
            });

            return false;
        });

        if ($('.alowtime').size() > 0) {
            var time = (new Date($('.alowtime').attr('data-endtime')).getTime()) + 86400000;
            $('.alowtime').attr('data-endtime', new Date(time));
            setTimeout(function () {
                $('.alowtime').countdown()
            }, 1000);
        }

    </script>
    <script>


        function billCreat(width, height, dpi, originalPrice, price, minPrice, pic, productImageCover, qrcode) {
            this.width = width;
            this.height = height;
            this.dpi = dpi;
            this.originalPrice = originalPrice;
            this.price = price;
            this.minPrice = minPrice;
            if (pic.indexOf('http') != -1) {
                this.pic = pic;
            } else if (pic != '' && pic != null) {
                this.pic = 'https://img.d2c.cn' + pic;
            } else {
                this.pic = 'https://static.d2c.cn/img/home/160627/images/headpic.png'
            }
            this.productImageCover = productImageCover;
            this.qrcode = qrcode
        }

        billCreat.prototype = {
            //海报生成
            init: function () {
                $("#img-bill").show();
                var canvas = this.creatCanvas();
                $("#img-bill").append(canvas);
                $.flashTip({position: 'center', type: 'success', message: '海报生成中~'});
                var that = this;
                setTimeout(function () {
                    console.log(document.getElementById("canvas"))
                    $("#img-bill").append(that.toImg(document.getElementById("canvas")));
                }, 2000)

            },
            //创建画布
            creatCanvas: function () {
                var canvas = document.createElement("canvas"), that = this;
                canvas.setAttribute('width', this.width);
                canvas.setAttribute('height', this.height);
                canvas.setAttribute('id', 'canvas');
                //canvas.setAttribute('style', 'display:none');
                canvas.style.width = this.width + 'px'
                canvas.style.height = this.height + 'px'
                var ctx = canvas.getContext("2d");
                ctx.width = this.width;
                ctx.height = this.height;
                var img1 = this.creatImg('https://static.d2c.cn/other/bill/billbgs.png');
                img1.onload = function () {
                    ctx.drawImage(img1, 0, 0, that.width, that.height);
                    var img2 = that.creatImg('http://img.d2c.cn' + that.productImageCover + '!/both/783x909');
                    img2.onload = function () {
                        ctx.drawImage(img2, that.width / 2 - 130.5, that.height * .08, 261, 303);
                        var img3 = that.creatImg('https://static.d2c.cn/other/bill/bg_xinxi.png')
                        img3.onload = function () {
                            ctx.drawImage(img3, that.width / 2 - 130.5, that.height * .08 + 303, 261, 116);
                            ctx.font = "700 12px PingFangSC-Medium";
                            ctx.fillStyle = "#222";
                            ctx.textAlign = "center";
                            ctx.fillText("快来帮我砍价吧！", that.width / 2, that.height * .08 + 327);
                            ctx.translate(that.width / 2 - 116.5, that.height * .08 + 361);
                            ctx.save();
                            ctx.arc(20, 20, 20, 0, 2 * Math.PI);
                            ctx.clip();
                            var img4 = that.creatImg(that.pic);
                            img4.onload = function () {
                                ctx.drawImage(img4, 0, 0, 40, 40);
                                ctx.restore();
                                ctx.font = "12px PingFangSC-Medium";
                                ctx.fillStyle = "#000";
                                ctx.textAlign = "start";
                                var text1 = "我已砍到", price = that.price;
                                ctx.fillText(text1, 50, 12);
                                ctx.fillStyle = "#FD555D";
                                ctx.fillText(price, 53 + ctx.measureText(text1).width, 12);
                                ctx.fillStyle = "#000";
                                ctx.fillText("元，就差你一刀", 56 + ctx.measureText(text1 + price).width, 12);
                                ctx.font = "10px PingFangSC-Medium";
                                ctx.fillText("底价：", 50, 37);
                                ctx.fillStyle = "#999";
                                ctx.fillText("原价：", 130, 37);
                                ctx.fillText(that.originalPrice, 160, 37);
                                ctx.font = "14px PingFangSC-Medium";
                                ctx.fillStyle = "#fd555d";
                                ctx.fillText(that.minPrice, 80, 37);
                                var img5 = that.creatImg(that.qrcode);
                                img5.onload = function () {
                                    ctx.translate(-14, 98);
                                    ctx.drawImage(img5, 0, 0, 100, 100);
                                    var img6 = that.creatImg('https://static.d2c.cn/other/bill/logo.png');
                                    img6.onload = function () {
                                        ctx.drawImage(img6, 114.5, 5, 58, 58);
                                        ctx.font = "11px PingFangSC-Medium";
                                        ctx.fillStyle = "#FFF";
                                        ctx.fillText("长按二维码，帮助好友砍价", 114.5, 78);
                                        var img7 = that.creatImg('https://static.d2c.cn/other/bill/bg_plants.png');
                                        img7.onload = function () {
                                            ctx.translate(261 + (that.width - 261) / 2 - 78, -458.75 - that.height * .08);
                                            ctx.drawImage(img7, 0, 0, 78, 153);
                                            ctx.save();
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
                return canvas;
            },
            //图片画布
            creatImg: function (src) {
                var img = new Image();
                img.src = src;
                img.setAttribute("crossOrigin", 'Anonymous');
                return img;
            },
            //转换图片
            toImg: function (canvas) {
                var image = new Image();
                image.src = canvas.toDataURL('image/png').replace("image/png", "image/octet-stream");
                return image;
            },
        }

        $('.billcreat').on(click_type, function (e) {
            var Event = e || event;
            var width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
            var height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
            var dpi = window.devicePixelRatio
            var originalPrice = '${bargainPriceDO.originalPrice}';
            var price = '${bargainPriceDO.price}';
            var minPrice = '${bargainPriceDO.bargain.minPrice}';
            var pic = '${bargainPriceDO.pic}';
            var productImageCover = '${product.productImageCover}';
            var qrcode = "/picture/code?type=1&width=144&height=144&noLogo=true&&code=http://m.d2cmall.com/bargain/personal/${bargainPriceDO.id}"
            var bill = new billCreat(width, height, dpi, originalPrice, price, minPrice, pic, productImageCover, qrcode);
            bill.init();
            Event.stopPropagation();
            setTimeout(function () {
                $('.share-type').hide();
            }, 500)


        })
    </script>

</#if>
<@m.page_footer />