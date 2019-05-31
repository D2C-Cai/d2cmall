//用户登录
function userLogin() {
    if (app_client == true) {
        //var objData = new LoginDatas();
        //var myData = objData.message;
        //$.D2CMerchantBridge(myData);
        location.href = '/member/login';
    } else {
        location.hash = '#login';
        $.popModal({url: '/member/login?' + new Date().getTime()});
    }
    return false;
}

function userbind() {
    location.hash = '#bind';
    $.popModal({url: '/member/bind?' + new Date().getTime()});
}

//关闭弹出层
function closePopModal() {
    $.popModalClose();
}


//登录参数
function LoginDatas() {
    this.message = {
        handlefunc: 'w_login',
        func: "cessback",
        type: "login"
    };

}

//js调用APP分享
function userShare() {
    if (app_client == true) {
        var objData = new ShareDatas();
        var payData = objData.message;
        $.D2CMerchantBridge(payData)
    }
}

//js询问通知
function noticeFunc() {
    var noticeMessage = {
        handlefunc: 'w_sign_open',
        func: 'noticeback',
        type: 'page'
    }
    $.D2CMerchantBridge(noticeMessage, 'noticeback');
}

//js打开通知
function openNotice() {
    var openNotice = {
        handlefunc: 'w_notice_open',
        func: 'noticeSuback',
        type: 'page'
    }
    $.D2CMerchantBridge(openNotice, 'noticeSuback')
}

//签到成功发送APP
function sendSignStatus() {
    var status = {
        handlefunc: 'w_sign_success',
        func: 'cessback',
        type: 'page'
    }
    $.D2CMerchantBridge(status)
}

//打开通知回调
function noticeSuback(res) {
    if (res.hasOwnProperty("open") && res.open == "open") {
        $('.re-button').addClass('on')
        $.flashTip({position: 'center', type: 'success', message: '签到通知开启'});
    } else {
        $('.re-button').removeClass('on')
        $.flashTip({position: 'center', type: 'success', message: '签到通知关闭'});
    }
}


function noticeback(res) {
    if (res.hasOwnProperty("open") && res.open == "open") {
        $('.re-button').addClass('on')
    } else {
        $('.re-button').removeClass('on')
    }
}

function ajaxRefresh(res) {
    if (isExitsFunction('ajaxF')) {
        ajaxF();
    }
}


//分享参数
function ShareDatas() {
    var title = $('share').attr('data-title') || document.title,
        desc = $('share').attr('data-desc') || $('meta[name="description"]').attr('content'),
        pic = $('share').attr('data-pic') || $('img:eq(0)').attr('src'),
        url = $('share').attr('data-url') || document.location.href;

    this.message = {
        handlefunc: 'w_share',
        func: "cessback",
        type: "share",
        'URL': url,
        'desc': desc,
        'title': title,
        'pic': pic
    };
}


function showProductBrand() {
    $('body').data('scroll_position', $(window).scrollTop());
    $('.list-sort-designer').toggle();
    $('.arrow-icon').toggleClass('up');
    return false;
}

function hideProductBrand() {
    $('.list-sort-designer').hide();
    $('.arrow-icon').removeClass('up');
    var scroll_position = $('body').data('scroll_position');
    $(window).scrollTop(scroll_position);
    return false;
}

function isExitsFunction(funcName) {
    try {
        if (typeof (eval(funcName)) == "function") {
            return true;
        }
    } catch (e) {
    }
    return false;
}

//检查购物车
function countCart() {
    $.ajax({
        'url': '/cart/count?t=' + Math.random(),
        'type': 'get',
        'data': {},
        'dataType': 'json',
        'success': function (data, status) {
            if (data.cartItemCount > 0) {
                $('.my-cart-num').show();
                $('.my-cart-num').text(data.cartItemCount);
                $('.my-cart-num').data('change', 1);
            }

        }
    });
}

function showServiceQQ() {
    $('.service-qq').show().addClass('bounceIn animated');
    return false;
}


function hideServiceQQ() {
    $('.service-qq').addClass('zoomOut');
    setTimeout(function () {
        $('.service-qq').hide().removeClass('bounceIn zoomOut animated')
    }, 1000)
    return false;
}

function switchDesignerInfo() {
    $('.designer-info .text').toggle();
}

function interestSuccess(id, type) {
    var obj = $('#' + type + '-' + id);
    var style = obj.find('.icon').attr('class').replace('-o', '');
    var i = parseInt(obj.find('strong').text());
    if (isNaN(i)) i = 0;
    obj.find('.icon').attr('class', style);
    obj.find('strong').text(i + 1);
    obj.addClass('disabled');
    return false;
}

/*投票数字*/
function voteCount() {
    var ids = '', str = '';
    var num = $('.vote-item-num').size();
    $.each($('.vote-item-num'), function (i, d) {
        ids += str + $(d).attr('data-id');
        str = (i < num) ? ',' : '';
    });
    $.post('/vote/count', {'id': ids}, function (data) {
        var votes = data.result.data;
        $.each(votes, function (i, d) {
            $('.vote-item-num[data-id=' + i + ']').text(d);
        });

    }, 'json');
}


/*
 * 投票成功后返回
 */
var voteSuccess = function (id) {
    var i = parseFloat($('.vote-item-num[data-id=' + id + ']:first').text());
    $('.vote-item-num[data-id=' + id + ']').text(i + 1);
}

function installApp() {
    var a = window.location.pathname, s = '';
    a == '/' ? a = '' : s = 'S.load=';
    var config = {
        "ios": {
            debug: false,
            "packageName": "com.d2cmall.buyer",
            "packageUrl": "d2cmall://" + a,
            "appStoreUrl": "https://itunes.apple.com/app/apple-store/id980211165?pt=117419812&ct=wap&mt=8"
        },
        "android": {
            "packageName": "com.d2cmall.buyer",
            "packageUrl": "d2cmall://" + a,
            "fusionPage": "//a.app.qq.com/o/simple.jsp?pkgname=com.d2cmall.buyer",
            "downloadUrl": "//app.d2cmall.com/download/d2cmall-install.apk"
        }
    };
    var packageUrl = config[isAndroid ? "android" : "ios"]["packageUrl"],
        packageName = config[isAndroid ? "android" : "ios"]["packageName"];
    var scheme = "";
    location.href = packageUrl;
    if (isAndroid || iOS) {
        var timeout, t = 1000, hasApp = true;
        var t1 = Date.now();
        var ifr = document.createElement("iframe");
        ifr.setAttribute('src', packageUrl);
        ifr.setAttribute('style', 'display:none');
        document.body.appendChild(ifr);
        timeout = setTimeout(function () {
            var t2 = Date.now();
            if (!t1 || t2 - t1 < t + 10) {
                hasApp = false;
            }

        }, t);

        setTimeout(function () {
            if (!hasApp) {
                location.href = "/page/downapp";
            }
            document.body.removeChild(ifr);
        }, 2000)
    }
    /*  
      if (isWeChat) {
          location.href=config.android.fusionPage;
          return false;
      }
    */
    if (scheme) {
        location.href = scheme;
        return false;
    }
}

/*优惠券，报名用户信息完善*/

//成功,暂时无用
function getCouponSuccess(data) {
    if (data == '' || data == undefined) {
        var data = $('body').data('activity_form');
        $('.d2c-register-form').html('<div style="padding:1em 0;text-align:center;line-height:200%;"><p class="icon icon-ok" style="font-size:1.5em;"></p><p>' + data.result.message + '</p></div>');
        return false;
    } else {
        $('.coupon-register-form').html('<div style="padding:1em 0;text-align:center;line-height:200%;"><p class="icon icon-ok" style="font-size:1.5em;"></p><p>' + data.result.message + '</p></div>');
    }

}

function activityExcuteSuccess(data, parame) {
    if (parame.template == '' || parame.template == undefined) {
        if ($('.d2c-register-form').size() > 0) {
            data.result.message = parame.suctitle ? parame.suctitle : data.result.message;
            $('.d2c-register-form').html('<div style="padding:1em 0;text-align:center;line-height:200%;"><p class="icon icon-ok" style="font-size:1.5em;"></p><p>' + data.result.message + '</p></div>');
        } else {
            parame.suctitle ? $.flashTip({
                position: 'center',
                type: 'error',
                message: parame.suctitle
            }) : $.flashTip({position: 'center', type: 'error', message: '奖励已发送至账户，请注意查收'});
        }
    } else {
        var html = template(parame.template);
        $('#success-lottery').append(html);
    }
}

//执行活动程序 
function activityExcute(parame) {
    closeregistertemp();
    if (parame == '' || parame == undefined) {
        parame = $('body').data('activity_form');
    }
    $.ajax({
        url: parame.url,
        type: 'post',
        data: {'mobile': parame.mobile},
        dataType: 'json',
        success: function (data) {
            if (data.result.status == 1) {
                activityExcuteSuccess(data, parame);
            } else {
                $.flashTip({position: 'center', type: 'error', message: data.result.message});
            }
        }
    });
}

//注册
function activityRegister(parame) {
    var temp = '<div class="coupon-bind" style="z-index:11;position:fixed;top:30%;width:80%;left:5%;background: rgba(255,255,255,0.95);box-shadow: 0 0 10px #333;padding:1rem">\
			<a href="javascript:" class="modal-remove" id="close-d2c-buttonmessage" style="position:absolute;top:-15px;right:-15px" onclick><i class="icon icon-close"></i></a>\
			<form class="validate-form form d2c-register-form" action="/member/join/d2c" method="post" call-back="activityExcute" success-tip="false">\
            <input type="hidden" name="source" value="{{source}}"/><input type="hidden" name="nationCode" class="mobile-code" value="86"/><p style="line-height:2em;text-align:center;" class="coupon-text">{{title}}</p>\
            <div class="form-item item-flex" style="1px solid #EDEDED">\
             <label style="line-height:2.8em">手机号</label><input type="text" name="mobile" id="mobile" value="{{mobile}}" title="手机号" data-rule="mobile" class="input validate validate-account" placeholder="请输入手机号"></div> <div class="form-item item-flex" style="1px solid #EDEDED">\
            <label style="line-height:2.8em">验证码</label><input type="text" name="code" id="code" value="" class="input validate" title="短信校验码" placeholder="请输入短信校验码"><button type="button" data-source="" data-type="Member" class="button button-green validate-send validate-button">获取校验码</button></div>\
            <div style="text-align:center;padding-top:10px;"><button type="submit" name="coupon-button" id="for-coupon-button" class="button button-red">加入D2C</button></div></form></div>';
    var condata = {'title': parame.title, 'mobile': parame.mobile, 'source': parame.source};
    var render = template.compile(temp);
    var html = render(condata);
    $('body').append(html);
    return false;
}

//关闭注册窗口
function closeregistertemp() {
    $('.coupon-bind').remove();
}

//关闭注册窗口
$(document).on('click', '#close-d2c-buttonmessage', function () {
    closeregistertemp();
});


/*GET领取优惠券*/
$('.promotion-coupon').on('click', function () {
    var couponId = $(this).attr('data-id');
    $.get("/coupon/batch/" + couponId, function (data) {
        if (data.result.status == 1) {
            $.flashTip({position: 'center', type: 'error', message: '领取成功'});
        } else if (data.result.login == false) {
            userLogin();
            return false;
        } else {
            $.flashTip({position: 'center', type: 'error', message: data.result.message});
        }
    }, 'json')
})

//数字转换中文
function changenum(m) {
    switch (m) {
        case 1:
            return "一";
            break;
        case 2:
            return "二";
            break;
        case 3:
            return "三";
            break;
        case 4:
            return "四";
            break;
        case 5:
            return "五";
            break;
        case 6:
            return "六";
            break;
        case 7:
            return "七";
            break;
        case 8:
            return "八";
            break;
        case 9:
            return "九";
            break;
        case 10:
            return "十";
            break;
        case 11:
            return "十一";
            break;
        case 12:
            return "十二";
            break;
    }
}