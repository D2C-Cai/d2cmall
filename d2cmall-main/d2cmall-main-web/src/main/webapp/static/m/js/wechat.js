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
    var share = function () {
        var title = $('share').attr('data-title') || document.title,
            desc = $('share').attr('data-desc') || $('meta[name="description"]').attr('content'),
            pic = $('share').attr('data-pic') || $('img:eq(0)').attr('src'),
            url = $('share').attr('data-url') || document.location.href;

        wx.onMenuShareTimeline({
            title: title,
            desc: desc,
            link: url,
            imgUrl: pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                jAlert('分享成功！');
            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
        wx.onMenuShareAppMessage({
            title: title,
            desc: desc,
            link: url,
            imgUrl: pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                jAlert('分享成功！');
            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
        wx.onMenuShareQQ({
            title: title,
            desc: desc,
            link: url,
            imgUrl: pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                jAlert('分享成功！');
            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
        wx.onMenuShareWeibo({
            title: title,
            desc: desc,
            link: url,
            imgUrl: pic,
            trigger: function (res) {
                //alert('用户点击发送给朋友');
            },
            success: function (res) {
                jAlert('分享成功！');
            },
            cancel: function (res) {
                jAlert('你取消了分享。');
            },
            fail: function (res) {
                jAlert(JSON.stringify(res));
            }
        });
    }
    share();
});	