<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="砍价商城" css="bargain" service="砍价商城"  keywords="全球好设计,设计师品牌,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="限时优惠！和好友一起来砍价" service='false'/>
<#if !m.FROMAPP>
    <@m.app_download_bar fixed="false" />
</#if>
<#if bargainPromotion?exists>
    <div class="bargin-section">
        <div class="bargin-top">
            <img src="${picture_base}${bargainPromotion.coverPic}" style="width: 100%;">
            <a href="javascript:;" class="brule show-rule"> </a>
            <div class="marquee" id="marquee"></div>
        </div>
        <div class="bargin-productinfo" style="position:relative;">
            <a href="/page/520zhounianqing" class="pro-go" style="display:none"><img
                        src="https://static.d2c.cn/img/topic/190509/520/logo_j.png" style="width:100%;"></a>
            <div class="barpro-title">${bargainPromotion.name}</div>
            <#if bargainPromotion.status==1 >
                <div class="bar-times on count-down" data-type="split-time"
                     data-endtime="${bargainPromotion.beginDate?string("yyyy/MM/dd HH:mm:ss")}" data-musttime="must">
                    <span>距离开始时间</span><span class="n-time  hour down-time">00</span><span class="k-time">时</span><span
                            class="n-time  minute down-time">00</span><span class="k-time">分</span><span
                            class="n-time second down-time">00</span><span class="k-time">秒</span></div>
            <#elseif bargainPromotion.status==2>
                <#if bargainPromotion.product.mark!=1 || bargainPromotion.mark!=1>
                    <div class="bar-times off">该商品砍价活动已下架</div>
                <#elseif  bargainPromotion.product.store lt 1>
                    <div class="bar-times off">该砍价商品已售罄</div>
                <#else>
                    <div class="bar-times on count-down" data-type="split-time"
                         data-endtime="${bargainPromotion.endDate?string("yyyy/MM/dd HH:mm:ss")}" data-musttime="must">
                        <span>距离结束时间</span><span class="n-time  hour down-time">00</span><span
                                class="k-time">时</span><span class="n-time  minute down-time">00</span><span
                                class="k-time">分</span><span class="n-time second down-time">00</span><span
                                class="k-time">秒</span></div>
                </#if>
            <#else>
                <div class="bar-times off">该商品砍价活动已结束</div>
            </#if>
        </div>
        <div class="bargin-contents" style="height:100px">
            <div class="pro-price flex">
                <div class="o-price">
                    <p>原价</p>
                    <p style="color:#222;">¥${bargainPromotion.product.minPrice}</p>
                </div>
                <div class="o-price text-right">
                    <p>最低价</p>
                    <p style="color: #f23365;">¥${bargainPromotion.minPrice}</p>
                </div>
            </div>
            <div class="progress-pic">
                <div class="progress-bar"></div>
            </div>

            <div class="bargin-tips">
                已有<span style="font-weight:bold;color:#F23365;font-size:14px;"><#if bargainPromotion.status==1>${bargainPromotion.actualMan}<#else>${bargainPromotion.virtualMan+bargainPromotion.actualMan}</#if></span>人已加入，赶紧召集好友拔刀相助！
            </div>
        </div>
        <#if bargainPromotion.description>
            <div style="font-size:12px;padding:10px;line-height:180%;margin:20px 10px 10px 10px;box-shadow:0 0 5px #CCC;background:#FFFFEE;">
                <strong>温馨提示</strong>：</br>
                ${bargainPromotion.description}
            </div>
        </#if>
        <!--
        <div class="banner-img" style="margin-top:20px;">
            <a href="/bargain/promotion/list?status=1"><img src="//static.d2c.cn/img/promo/bargin/kj-banner.jpg" style="width:100%;"></a></img>
        </div>
        -->
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

        <div class="bargin-detail">
            <div class="t-title" data-url="/product/detail/${bargainPromotion.product.productId}?type=ajax">
                <span>图文详情</span></div>
        </div>
        <div id="product-detail-content" class="product-detail-content">
            <div class="loadding">
                <i class="icon-spinner icon-spin icon-large"></i> 正在加载内容
            </div>
        </div>

        <div style="height:50px"></div>
        <#if bargainPromotion.status==1>
            <div class="bargin-button flex">
                <i class="icon icon-bargin"></i>
                <div class="cur-price" style="margin-left:10px;">原价<span class="fh">¥</span><span
                            class="c-strong">${bargainPromotion.product.minPrice}</span></div>
                <div class="bargin-sue flex" style="flex:4;justify-content:flex-end">
                    <button class="remind" data-id="${bargainPromotion.product.id}" style="color:rgba(242,51,101,1);">
                        开抢提醒
                    </button>
                </div>
            </div>
        <#elseif bargainPromotion.status==2>
            <#if bargainPromotion.product.mark==1 && bargainPromotion.product.store gte 1  &&bargainPromotion.mark==1>
                <div class="bargin-button flex">
                    <i class="icon icon-bargin"></i>
                    <div class="bargin-sue flex" style="flex:4;justify-content:flex-end">
                        <button class="overpromotion" onclick="gobuy()" style="color:rgba(242,51,101,1);">
                            &yen;${bargainPromotion.product.minPrice}&nbsp;购买
                        </button>
                        <button class="overpromotion gobargin" style="background:rgba(242,51,101,1);color:#FFF">发起砍价
                        </button>
                    </div>
                </div>
            <#else>
                <div class="bargin-button flex">
                    <button class="button button-al" onclick="location.href='/bargain/promotion/list?status=1'">
                        看看其他砍价商品
                    </button>
                </div>
            </#if>
        <#else>
            <div class="bargin-button flex">
                <button class="button button-al" onclick="javascript:location.href='/bargain/promotion/list?status=1'">
                    看看其他砍价商品
                </button>
            </div>
        </#if>
    </div>
    <a href="/page/520zhounianqing"><img src="https://static.d2c.cn/img/topic/190509/520/logo_j.png" width="100%"
                                         id="nav"/></a>
    <share data-title="${bargainPromotion.shareTitle}" data-pic="${picture_base}${bargainPromotion.sharePic}"
           data-desc="${bargainPromotion.shareDes?replace('#price',bargainPromotion.minPrice)}"></share>
    <script>
        var page_elem = {
            init: function () {
                $.get('/product/detail/${bargainPromotion.product.productId}?type=ajax', function (data) {
                    $('#product-detail-content').data('isLoad', true);
                    $('#product-detail-content').html(data);
                });
                this.size();
                this.marquee();
                this.lisen();
                this.bargin();
            },
            size: function () {
                $.getJSON('/product/skuStore/${bargainPromotion.product.id}', function (res) {
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
                $.getJSON('/bargain/newest/${bargainPromotion.id}?pageSize=20', function (data) {
                    if (data.list.length > 0) {
                        $.each(data.list, function (ii, item) {
                            if (item.nickname) {
                                if (item.headPic == null) {
                                    item.headPic = '//static.d2c.cn/img/home/160627/images/headpic.png'
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
                                    var html = '<div class="popup-content">\
	       				 		<div id="success-info" class="bargain-help-content">\
	       				 			<div class="popup-help-success-info"><i></i><p>设置成功</p></div>\
	       				 			<div class="popup-text">活动开始前30分钟，<br>将通过短信形式通知你</div>\
									<button type="button" class="button button-black button-small" onclick="popupModalClose()">确定</button>\
								</div>';
                                    popupModal({content: html});
                                }
                            });
                        }
                    });
                })
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
            },
            bargin: function () {
                $('.gobargin').on(click_type, function () {
                    var id = $(this).attr('data-id');
                    var that = this;
                    <#if m.LOGINMEMBER && m.LOGINMEMBER.id && bargainPriceId && bargainPriceId!=''>
                    toast({position: 'center', type: 'error', message: '您已经开过该商品的团，请不要贪心哟~'});
                    setTimeout(function () {
                        location.href = "/bargain/personal/${bargainPriceId}"
                    }, 1500)
                    <#else>
                    $.getJSON('/member/islogin', function (data) {
                        if (data.result.login == false) {
                            userLogin();
                        } else if (data.result.datas.isBind == false) {
                            location.href = "/member/bind"
                            //userbind();
                        } else {
                            $.ajax({
                                'url': '/bargain/create',
                                'type': 'post',
                                'data': {promotionId:${bargainPromotion.id}},
                                'dataType': 'json',
                                'success': function (res, status) {
                                    if (!res.result) {
                                        var id = res.bargainPriceDO.id;
                                        localStorage.setItem('shareTip', true);
                                        toast({position: 'center', type: 'success', message: '成功参与砍价活动'});
                                        setTimeout(function () {
                                            location.href = "/bargain/personal/" + id
                                        }, 700);
                                    } else {
                                        toast({position: 'center', type: 'error', message: res.result.message});
                                    }

                                }
                            });
                        }
                    });
                    </#if>
                })
            }
        }
        page_elem.init();
    </script>
    <script>
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
        $('.icon-bargin').on(click_type, function () {
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    userLogin();
                } else if (data.result.datas.isBind == false) {
                    location.href = "/member/bind"
                    //userbind();
                } else {
                    location.href = "/bargain/promotion/list?status=1"
                }
            });
        })

        function gobuy() {
            <#if browser=='wechat'&& wechat >
            wx.ready(function () {
                if (window.__wxjs_environment === 'miniprogram') {
                    wx.miniProgram.navigateTo({url: '/pages/product/detail?id=' +${bargainPromotion.product.id}});
                } else {
                    location.href = '/product/${bargainPromotion.product.id}';
                }
            })
            <#else>
            location.href = '/product/${bargainPromotion.product.id}';
            TDAPP.onEvent('砍价商品购买');
            </#if>
        }
    </script>


</#if>
<@m.page_footer />