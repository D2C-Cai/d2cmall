wx.config({
    debug: false,
    appId: wx_appId,
    timestamp: wx_timestamp,
    nonceStr: wx_nonceStr,
    signature: wx_signature,
    jsApiList: [
        'checkJsApi',
        'onMenuShareTimeline',
        'onMenuShareAppMessage',
        'onMenuShareQQ',
        'onMenuShareWeibo',
        'hideMenuItems',
        'showMenuItems',
        'hideAllNonBaseMenuItem',
        'showAllNonBaseMenuItem',
        'chooseImage',
        'previewImage',
        'uploadImage',
        'downloadImage',
        'getNetworkType',
        'openLocation',
        'getLocation',
        'hideOptionMenu',
        'showOptionMenu',
        'closeWindow',
        'scanQRCode',
        'chooseWXPay',
        'openProductSpecificView',
        'addCard',
        'chooseCard',
        'openCard'
    ]
});

wx.ready(function () {
    var pay = function (data) {
        wx.chooseWXPay({
            timestamp: data.timestamp,
            nonceStr: data.noncestr,
            package: data.package,
            signType: data.signType, // 注意：新版支付接口使用 MD5 加密
            paySign: data.paySign
        });
    };
    var share = function (parame) {
        if (parame.title == undefined) parame.title = document.title;
        if (parame.desc == undefined) parame.desc = $('meta[name="description"]').attr('content');
        if (parame.url == undefined) parame.url = document.location.href;
        wx.onMenuShareTimeline({
            title: parame.title,
            desc: parame.desc,
            link: parame.url,
            imgUrl: parame.pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                var path = window.location.pathname;
                $.ajax({
                    'url': '/share/out',
                    'type': 'post',
                    'data': {url: path},
                    'dataType': 'json',
                    'success': function (res, status) {
                        //console.log(res)
                    }
                });
                if (path.indexOf('/bargain/personal/') != -1) {
                    if (page_elem.hasOwnProperty('helpSelf')) {
                        page_elem.helpSelf()
                    }
                }
            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
        wx.onMenuShareAppMessage({
            title: parame.title,
            desc: parame.desc,
            link: parame.url,
            imgUrl: parame.pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                var path = window.location.pathname;
                $.ajax({
                    'url': '/share/out',
                    'type': 'post',
                    'data': {url: path},
                    'dataType': 'json',
                    'success': function (res, status) {
                        //console.log(res)
                    }
                });
                if (path.indexOf('/bargain/personal/') != -1) {
                    if (page_elem.hasOwnProperty('helpSelf')) {
                        page_elem.helpSelf()
                    }
                }
            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                var path = window.location.pathname;
                jAlert(JSON.stringify(res));

            }
        });
        wx.onMenuShareQQ({
            title: parame.title,
            desc: parame.desc,
            link: parame.url,
            imgUrl: parame.pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                var path = window.location.pathname;
                $.ajax({
                    'url': '/share/out',
                    'type': 'post',
                    'data': {url: path},
                    'dataType': 'json',
                    'success': function (res, status) {
                        //console.log(res)
                    }
                });

            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
        wx.onMenuShareWeibo({
            title: parame.title,
            desc: parame.desc,
            link: parame.url,
            imgUrl: parame.pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                var path = window.location.pathname;
                $.ajax({
                    'url': '/share/out',
                    'type': 'post',
                    'data': {url: path},
                    'dataType': 'json',
                    'success': function (res, status) {
                    }
                });

            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
    }
    var parame = {};
    parame.title = $('share').attr('data-title') || document.title;
    parame.desc = $('share').attr('data-desc') || $('meta[name="description"]').attr('content');
    parame.pic = $('share').attr('data-pic') || $('img:eq(0)').attr('src');
    parame.url = $('share').attr('data-url') || document.location.href;
    share(parame);
});	