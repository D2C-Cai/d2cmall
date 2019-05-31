<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="我的心仪美衣是这款！帮我点赞，D2C送给你" css="poster" description="我的心仪美衣是这款！帮我点赞，D2C送给你"/>
<#assign imgHead='//static.d2c.cn/img/topic/181031/cardcollect/images/'>
<#assign poster=result.datas>
<#if RequestParameters.picId>
    <#assign picId=RequestParameters.picId>
<#else>
    <#assign picId=1>
</#if>
<style>
    #popup-modal-title {
        position: absolute;
        bottom: 15%;
        left: 50%;
    }

    .award-brule {
        display: block;
        position: absolute;
        width: 75px;
        height: 30px;
        top: 55px;
        right: 0;
        z-index: 10;
        background: url(http://static.d2c.cn/img/promo/bargin/icon_helper.png) no-repeat;
        background-size: cover;
    }

    .popup-rule {
        width: 16em;
        padding: 1em;
        background: #FFF;
        background-size: cover;
        border-radius: 1em;
    }

    .rule-title {
        height: 1.3em;
        line-height: 1.3em;
        color: #3F4043;
        font-weight: bold;
        font-size: 1.2em;
        text-align: center;
        margin-bottom: 1em;
    }

    .rule-info {
        height: 13em;
        line-height: 150%;
        overflow: scroll;
        font-size: 12px;
        text-align: left;
        font-weight: normal;
        -webkit-overflow-scrolling: touch;
    }
</style>

<div class="my-section">
    <div class="poster-back">
        <a href="/product/${poster.memberPoster.productId}"><img src="${poster.memberPoster.pic}"
                                                                 style="width:100%"></a>
        <a class="see-product" href="/product/${poster.memberPoster.productId}"></a>
    </div>
    <div class="my-title">
        <img src="//static.d2c.cn/img/topic/181031/posters/${picId}.png" style="width:100%">
    </div>
    <div class="dochoice">
        <div class="choice-item">
            <div class="choice-icon click-ch" data-type="up">
                <img src="//static.d2c.cn/img/topic/181031/posters/up.png">
            </div>
            <p class="choice-num up" onclick="TDAPP.onEvent('用户查看点赞明细')">
                ${poster.memberPoster.likeCount}
            </p>
        </div>
        <div class="choice-item">
            <div class="choice-icon click-ch" data-type="down" style="background:#6100ed">
                <img src="//static.d2c.cn/img/topic/181031/posters/down.png">
            </div>
            <p class="choice-num down" onclick="TDAPP.onEvent('用户查看点赞明细')">
                ${poster.memberPoster.dissCount}
            </p>
        </div>
    </div>
    <div class="rank">
        <div class="rank-detail">
            <span><#if poster.memberPoster.likeCount gt 0 >NO.${poster.ranking}<#else>暂未入榜</#if></span></br>
            <span>当前排名</span>
        </div>
    </div>
    <div class="poster-tips">
        <#if poster.mine==1>
            <img src="//static.d2c.cn/img/topic/181031/posters/oth2.png" style="width:100%">
            <button class="share-button" onclick="TDAPP.onEvent('用户分享海报')">生成分享海报</button>
        <#else>
            <img src="//static.d2c.cn/img/topic/181031/posters/oth1.png" style="width:100%">
            <button class="join-button" onclick="TDAPP.onEvent('用户参加海报活动')">我也要加入</button>
            <button class="back-mine" data-url="" style="display:none">回到我的海报</button>
        </#if>
    </div>
    <div class="poster-promotion">
        <div class="promotion-time">
            <p class="time-title">开奖倒计时</p>
            <p class="time-tips">开奖时间:2018年11月10日 20:00:00</p>
            <div class="count-down counts" data-type="split-time" data-endTime="2018/11/10 20:00:00"
                 data-musttime="true">
                <span class="hour down">00</span> :
                <span class="minute down">00</span> :
                <span class="second down">00</span>
            </div>
            <div style="text-align:center">
                <div class="promotion-rule"><a href="/page/posterRule">活动规则</a></div>
            </div>
        </div>
        <div class="rank-title">
            <p>TOP 30</p>
            <p>打入榜单，衣服归你!</p>
        </div>
        <div class="rank-list">

        </div>
    </div>
</div>
<div class="app-poster" style="display:none">
    <div class="app-banner" style="width:70%;margin:auto">
    </div>
    <p style="color:#111111;margin-bottom:20px;text-align:center;">分享到</p>
    <div class="share-items">
        <div class="item-icon" data-type="pyq">
            <img src="${imgHead}icon_share_pyq@3x.png" width="75%" alt="">
            <span>朋友圈</span>
        </div>
        <div class="item-icon" data-type="wx">
            <img src="${imgHead}icon_share_wx@3x.png" width="75%" alt="">
            <span>微信好友</span>
        </div>
    </div>
</div>
<script id="poster-template" type="text/html">
    <header class="modal-header">
        <div class="header">
            <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
            <div class="header-title">互动记录</div>
        </div>
    </header>
    <div class="like-list" style="margin-top:3em;-webkit-overflow-scrolling : touch ">
        {{each list as value index}}
        <div class="like-items">
            <p class="r-time">{{value.time}}</p>
            <div class="userinfo">
                <div>
                    <img src="{{value.headPic}}" style="display:block;"/>
                    <p>{{value.nickName?value.nickName:'匿名用户'}}</p>
                </div>
                <div class="direct">
                    {{value.direct==1?'点赞了你哟':'踩了你一脚'}}
                </div>
            </div>
        </div>
        {{/each}}
    </div>
</script>
<a href="/page/1212miaowu"><img src="//static.d2c.cn/img/topic/180915/fresh/images/fresh.png?111" width="100%"
                                id="nav"/></a>
<share data-pic="//static.d2c.cn/img/topic/180915/fresh/images/fresh.png?111"></share>
<script id="list-template" type="text/html">
    {{each list as value index}}
    <a class="rank-item" href="/product/{{value.productId}}">
        <div class="rank-tips">NO.{{index+1}}</div>
        <div class="img"><img style="width:100%" src="{{value.pic}}"></div>
        <div class="up-item">
            <p class="name"><img src="{{value.headPic}}" class="headpic"/><span class="nickname">{{value.nickName?value.nickName:'匿名用户'}}</span>
            </p>
            <p>
                <span class="up-icon"><img src="//static.d2c.cn/img/topic/181031/posters/up.png"
                                           style="width:60%"></span><span style="padding-left:6px;vertical-align:-3px">{{value.likeCount}}</span>
            </p>
        </div>
    </a>
    {{/each}}
</script>

<script>
    var minePoster = {
        init: function () {
            this.countDown();
            this.rankList();
            this.watch();
            <#if poster.mine==1>
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false || data.result.datas.isD2C == false) {
                    userLogin();
                }
            })
            <#else>
            $.getJSON('/member/poster/mine', function (data) {
                if (data.result.datas.memberPoster && data.result.datas.memberPoster.id) {
                    var id = data.result.datas.memberPoster.id;
                    $('.back-mine').attr('data-url', '/member/poster/mine?posterId=' + id).show();
                    $('.join-button').hide();
                }
            })
            </#if>
        },
        rankList: function () {
            $.getJSON('/member/poster/tops.json?pageSize=30', function (res) {
                var data = res.result.datas.tops;
                res.result.datas.tops.list.map(function (item, index) {
                    if (item.headPic == null) {
                        item.headPic = '//static.d2c.cn/img/home/160627/images/headpic.png'
                    } else if (item.headPic.indexOf('http') != -1) {
                        item.headPic = item.headPic
                    } else {
                        item.headPic = '${picture_base}' + item.headPic;
                    }
                })

                var html = template('list-template', data)
                $('.rank-list').append(html);

            });
        },
        countDown: function () {
            $('.count-down').countdown()
        },
        watch: function () {
            $('.click-ch').on('click', function () {
                var type = $(this).attr('data-type');
                if (type == 'up') {
                    var direct = 1;
                } else {
                    var direct = -1
                }
                $.ajax({
                    'url': '/member/poster/like',
                    'type': 'post',
                    'data': {'posterId': '${poster.memberPoster.id}', 'direct': direct},
                    'dataType': 'json',
                    'success': function (res) {
                        if (res.result.status == 1) {
                            if (direct == 1) {
                                $('.choice-num.up').text(parseInt($('.choice-num.up').text()) + 1)
                            } else {
                                $('.choice-num.down').text(parseInt($('.choice-num.down').text()) + 1)
                            }
                            toast({position: 'center', type: 'success', message: direct == 1 ? '点赞成功' : '踩成功'});
                        } else {
                            toast({position: 'center', type: 'error', message: res.result.message});
                        }
                    }
                })
            })
            $('.back-mine').on('click', function () {
                var url = $(this).attr('data-url');
                location.href = url
            })
            $('.join-button').on('click', function () {
                location.href = "/member/poster/question"
            })
            $('.share-button').on('click', function () {
                var img = encodeURIComponent('${poster.memberPoster.pic}');
                var url = encodeURIComponent('/member/poster/mine?picId=${RequestParameters.picId}&posterId=${poster.memberPoster.id}');
                if (app_client && !isWeChat) {
                    var html = '<img src="https://poster.d2c.cn?tpl=1111closet&img=' + img + '&qr=' + url + '" width="100%" alt="">';
                    $('.app-poster').show().find('.app-banner').html(html)
                } else {
                    var html = '<div class="popup-content">\
		  		<img src="https://poster.d2c.cn?tpl=1111closet&img=' + img + '&qr=' + url + '" width="100%" alt="">\
				<div style="width:300px;line-height:2;color:#fff;text-align:center;font-size:14px;">\
				<p>长按保存到相册，分享微信好友点赞</p>\
				<a href="javascript:popupModalClose();" class="modal-close"></a>\
				</div>\
				</div>';
                    toast({position: 'center', type: 'success', message: '海报全力绘制中~'});
                    setTimeout(function () {
                        popupModal({content: html});
                    }, 2000)

                }
            })

            var html = '<div class="popup-content"><div class="popup-rule">\
			<a href="javascript:popupModalClose();" class="modal-close" style="bottom:-25%"></a>\
			<div class="rule-title">活动结束</div>\
			<div class="rule-info">\
			<p>活动已经结束啦！活动奖励将在7个工作日内以优惠券的形式发放至获奖用户的账户内，请注意查收~<br /><br /></p>\
			<a href="/page/11shejikuanghuan" style="display:block;width: 72%;height: 30px;line-height: 30px;text-align: center;font-size: 14px;color: #fff;background: #FF0101;border-radius: 20px;margin: 10px auto 15px;">前往双11活动会场>></a>\
			</div>';
            popupModal({
                content: html
            });


            $('.choice-num').on('click', function () {
                $.getJSON('/member/poster/likes.json?posterId=${poster.memberPoster.id}&pageSize=30', function (res) {
                    var data = res.result.datas.likes;
                    if (data.list.length > 1) {
                        res.result.datas.likes.list.map(function (item, index) {
                            if (item.headPic == null) {
                                item.headPic = '//static.d2c.cn/img/home/160627/images/headpic.png'
                            } else if (item.headPic.indexOf('http') != -1) {
                                item.headPic = item.headPic
                            } else {
                                item.headPic = '${picture_base}' + item.headPic;
                            }
                            item.time = formatTime(new Date(item.createDate))
                        })
                        var html = template('poster-template', data);
                        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
                        return false;

                    } else {
                        toast({position: 'center', type: 'success', message: '还没有人来互动呢'});
                    }

                });
            })
            $('.item-icon').on('click', function () {
                var img = encodeURIComponent('${poster.memberPoster.pic}');
                var url = encodeURIComponent('/member/poster/mine?picId=${RequestParameters.picId}&posterId=${poster.memberPoster.id}');
                var _pic = "https://poster.d2c.cn?tpl=1111closet&img=" + img + "&qr=" + url;
                var type = $(this).attr('data-type');
                var message = {
                    handlefunc: '',
                    pic: _pic
                }
                message.handlefunc = type === 'pyq' ? 'w_pyq' : 'w_wx';
                $.D2CMerchantBridge(message);
            })
        }
    }

    function formatTime(date) {
        var year = date.getFullYear()
        var month = date.getMonth() + 1
        var day = date.getDate()
        var hour = date.getHours()
        var minute = date.getMinutes()
        var second = date.getSeconds()

        return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
    }

    function formatNumber(n) {
        n = n.toString()
        return n[1] ? n : '0' + n
    }

    minePoster.init()
</script>

<@m.page_footer />