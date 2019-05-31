/*购物车*/
/*初始化加载*/
$(".layout-cart input[name=quantity]").utilSetNumber();

function getcart() {
    if ($('input[name="cartItemId"]:checked').size() > 0) {
        $.ajax({
            'url': '/cart/calculate',
            'type': 'post',
            'data': $('#form-cart').serialize(),
            'success': function (data) {
                var obj = $(data),
                    amonut = obj.find('.totalAmount').text();
                totalQuantity = obj.find('.productTotalQuantity').text();
                obj.children('.item-detail').each(function (i, d) {
                    var id = $(d).attr('data-id'),
                        total = $(d).find('.totalProductAmount').text(),
                        flashPromotion = $(d).find('.flashPromotion').text(),
                        goodPromotion = $(d).find('.goodPromotion').text(),
                        orderPromotion = $(d).find('.orderPromotion').text(),
                        item = $(".item[order-id=" + id + "]");
                    productpromotionPrice = $(d).find('.promotionPrice').val(),
                        item.find('.promotion-td .total').html(total);
                    if (flashPromotion != "") {
                        item.find('.promotion-name .three').html("【" + flashPromotion + "】&nbsp;&nbsp;&nbsp");
                    } else {
                        item.find('.promotion-name .three').html("");
                    }
                    if (goodPromotion != "") {
                        item.find('.promotion-name .one').html("【" + goodPromotion + "】&nbsp;&nbsp;&nbsp");
                    } else {
                        item.find('.promotion-name .one').html("");
                    }
                    if (orderPromotion != "") {
                        item.find('.promotion-name .two').html("【" + orderPromotion + "】&nbsp;&nbsp;&nbsp");
                    } else {
                        item.find('.promotion-name .two').html("");
                    }
                    if (productpromotionPrice != 0 && productpromotionPrice != parseInt($(d).find('.original-l').attr('data-price'))) {
                        item.find('.cut-price').html('<span class="symbol">&yen;</span><span class="foreigner-price">' + $(d).find('.productpromotionPrice').val() + '</span>');
                        item.find('.original-l').css('text-decoration', 'line-through');
                    }

                })
                $('.select-count-number').text(totalQuantity);
                $('.totle-price').text(amonut);
            }
        });
    } else {
        $('.totle-price').text('0');
        $('.select-count-number').text('0');
    }


}

$('.layout-cart .check-all').click(function () {
    if ($(this).attr('checked') == 'checked') {
        $.each($('input[name=cartItemId]:not(:disabled)'), function (i, d) {
            $(d).attr('checked', true);
            $(d).parent().parent().addClass('checked');
            $(d).parent().parent().find('input,select').removeAttr('disabled');
        });
    } else {
        $.each($('input[name=cartItemId]:not(:disabled)'), function (i, d) {
            $(d).attr('checked', false);
            $(d).parent().parent().removeClass('checked');
            $(d).parent().parent().find('input:not([type=checkbox]),select').attr('disabled', 'disabled');
        });
    }

    getcart();
    countCartItem();
});

$('.layout-cart input[name=cartItemId]:not(:disabled)').each(function () {
    $(this).parent().parent().addClass('checked');
})


if ($('.layout-cart input[name=cartItemId]:disabled').size() > 0) {
    $.each($('input[name="cartItemId"]:disabled'), function (i, d) {
        $(d).parent().parent().find('input,select').attr('disabled', 'disabled');
    });
}
$('.layout-cart input[name=cartItemId]:not(:disabled)').click(function () {
    var i = $('.layout-cart input[name=cartItemId]').size();
    if ($(this).attr('checked') == 'checked') {
        $(this).parent().parent().addClass('checked');
        $(this).parent().parent().find('input,select').removeAttr('disabled');
        $('button[name=pay]').removeClass('disabled').attr('disabled', false);
        if ($('input[name=cartItemId]:checked').size() == i) {
            $('.check-all').attr('checked', true);
        }
    } else {
        $(this).parent().parent().removeClass('checked');
        $(this).parent().parent().find('input:not([type=checkbox]),select').attr('disabled', 'disabled');
        if ($('input[name="cartItemId"]:checked').size() == 0) {
            $('button[name=pay]').addClass('disabled').attr('disabled', true);
        }
        if ($('input[name=cartItemId]:checked').size() != i) {
            $('.check-all').attr('checked', false);
        }
    }
    getcart();
    countCartItem();
});

$('select[name="selectOrderPromotionId"]').change(function () {
    var orderPromotionId = $(this).val();
    $(this).parent().find('input[name="orderPromotionId"]').val(orderPromotionId);
    getcart();
})

function countCartItem() {
    //$('.select-count-number').text($('input[name="cartItemId"]:checked').size());
    if ($('input[name="cartItemId"]:checked').size() > 0) {
        $('#cart-pay').removeAttr('disabled');
    } else {
        $('#cart-pay').attr('disabled', 'disabled');
    }

}


//数量加减按钮点击
$('.layout a.num-change').click(function () {
    var obj = $(this).parent().parent();
    var id = obj.find('input[name=skuId]').val();
    var td_obj = $(this).parent();
    var num_obj = $(this).siblings('input');
    var num = parseInt(num_obj.val());
    var bnum = $(".layout input[name=quantity]").val();
    var cmun = parseInt($(".layout .surplus").attr("data-rule"));
    if (isNaN(num)) num = 1;
    if ($(this).attr('data-type') == 'decrease') {
        num = num - 1;
    } else {
        num = num + 1;
    }
    if (num < 1) num = 1;
    if (num > 99) num = 99;
    if (obj.find('input[name=cartItemId]').attr('checked') != 'checked' && obj.find('input[name=cartItemId]').attr('disabled') != "disabled") {
        obj.find('input:not([type=checkbox]),select').removeAttr('disabled');
        obj.find('input[name=cartItemId]').attr('checked', 'checked');
        obj.addClass('checked');
        countCartItem();

    }

    if (cmun != undefined) {
        if (bnum > cmun) {
            $('#cart-pay').attr('disabled', 'disabled');
        } else {
            $('#cart-pay').removeAttr('disabled');
        }
    }
    if (obj.find('input[name=cartItemId]').attr('checked') == 'checked') {
        $.ajax({
            'url': '/cart/cartItem/' + id,
            'type': 'post',
            'data': {'quantity': num, 'skuId': id},
            'dataType': 'json',
            'async': false,
            'success': function (data) {
                if (data.result.status == 1) {
                    td_obj.find('.check-tip').remove();
                    num_obj.val(num);
                    getcart();
                } else if (data.result.status == -1) {
                    if (num <= 1) {
                        if (td_obj.find('.check-tip').size() == 0) {
                            td_obj.append('<div class="check-tip">该件商品已经卖完了</div>');
                        } else {
                            td_obj.find('.check-tip').html('该件商品已经卖完了');
                        }
                        obj.find('input,select').attr('disabled', 'disabled');
                        obj.find('input[name=cartItemId]').removeAttr('checked');
                    } else if (num > 1) {
                        if (td_obj.find('.check-tip').size() == 0) {
                            td_obj.append('<div class="check-tip">库存不足，该商品最多购买' + (num - 1) + '件</div>');
                        } else {
                            td_obj.find('.check-tip').html('库存不足，该商品最多购买' + (num - 1) + '件');
                        }
                    }
                }
            }
        });
    }
});

//修改数量的时候
$(".layout input[name=quantity]").change(function () {
    var obj = $(this).parent().parent();
    var id = obj.find('input[name=cartItemId]').val();
    var td_obj = $(this).parent();
    var num_obj = $(this);
    var num = parseInt(num_obj.val());
    if (isNaN(num)) num = 1;

    if (num < 1) num = 1;
    if (num > 99) num = 99;

    if (obj.find('input[name=cartItemId]').attr('checked') == 'checked') {
        $.ajax({
            'url': '/cart/cartItem/' + id,
            'type': 'post',
            'data': {'quantity': num, 'skuId': id},
            'dataType': 'json',
            'async': false,
            'success': function (data) {
                if (data.result.status == 1) {
                    td_obj.find('.check-tip').remove();
                    num_obj.val(num);
                    getcart();
                } else if (data.result.status == -1) {
                    if (num <= 1) {
                        if (td_obj.find('.check-tip').size() == 0) {
                            td_obj.append('<div class="check-tip">该件商品已经卖完了</div>');
                        } else {
                            td_obj.find('.check-tip').html('该件商品已经卖完了');
                        }
                    } else if (num > 1) {
                        if (td_obj.find('.check-tip').size() == 0) {
                            td_obj.append('<div class="check-tip">库存不足，该商品最多购买' + (num - 1) + '件</div>');
                        } else {
                            td_obj.find('.check-tip').html('库存不足，该商品最多购买' + (num - 1) + '件');
                        }
                    }
                }
            }
        });
    }
});

//清空购物车
$('.layout button[name=clear]').click(function () {
    jConfirm('确定要清空购物车吗？', '', function (r) {
        if (r) {
            $.ajax({
                'url': '/cart/remove',
                'type': 'post',
                'data': {},
                'dataType': 'text',
                'success': function (data, textStatus) {
                    $('tbody,tfoot').remove();
                    $('#form-cart').remove();
                    $('.cart-empty').show();
                    countCartItem();
                    getcart();
                }
            });
        }
    });
    return false;
});

//初始化购物车商品数量统计
function cartInit() {
    $('.layout-cart .check-all').attr('checked', 'false');
    $('.layout-cart .check-all').click();
    var bnum = $(".layout input[name=quantity]").val();
    var cmun = parseInt($(".layout .surplus").attr('data-rule'));
    if (cmun != undefined) {
        if (bnum > cmun) {
            $('#cart-pay').attr('disabled', 'disabled');
        } else {
            $('#cart-pay').removeAttr('disabled');
        }
    }
    //var i=$('.layout-cart .item[order-id]').size();
    //$('.header-user .cart #cart-nums-id').text(i);
    //countCartItem();
    //getcart();
}

cartInit();


/*确认订单*/
if ($('.layout-order input[name=skuId]').size() == 0) {
    $('#order-confirm').attr('disabled', 'disabled').css('background', '#AAA');
    $('.excptions-items').toggle();
}

//兑换优惠券
$('.layout-order #coupon-btn').click(function () {
    if ($('#coupon-code').val() == '') {
        $('#coupon-code').focus();
        return false;
    }
    var thisobj = $(this);
    jConfirm('确定要兑换吗？兑换后将重刷新页面。不符合条件的优惠券将不显示。', '', function (r) {
        if (r) {
            var t = parseFloat($('#total-amount').text().replace(',', '').replace('￥', '').replace('&yen;', '').replace('$', ''));
            $.post('/coupon/convertCoupon', $('.coupon-data').serialize(), function (data) {
                if (data.result.status == 1) {
                    if (data.result.data.coupon) {
                        jAlert(data.result.msg, '', function () {
                            location.reload();
                        });
                    } else {
                        jAlert(data.result.msg);
                    }

                } else {
                    jAlert('代金券兑换失败！请仔细核代金券上的验证码！');
                }
            }, 'json');
        }
    });
    return false;
});


//检查优惠券
function checkOrderAmount() {
    var t = parseFloat($('#total-price').attr('rel'));
    var a = parseFloat($('#total-amount').attr('rel'));
    var p = parseFloat($('#promotion-amount').attr('rel'));
    var ship = parseFloat($('#ship-fee').attr('rel'));
    var c = parseFloat($('input[name=coupons]:checked').attr('rel'));
    var c_type = $('input[name=coupons]:checked').attr('data-type');
    var card = parseFloat($('input[name=cashCardId]:checked').attr('rel'));
    $('.fu').remove();
    if (isNaN(c)) c = 0;
    if (isNaN(p)) p = 0;
    if (isNaN(card)) card = 0;
    var tc = c_type == 'DISCOUNT' ? (a - p) * c + ship : t - c;
    var z = c_type == 'DISCOUNT' ? (a - p) * (1 - c) : c;

    if (z > 0) $('.punctuation').prepend('<span class="fu">－</span>');
    $('#coupon-amount').text($.utilFormatCurrency(z));
    if ((tc - card) <= 0) {
        if (ship == 0) {
            $('#pay-price-wrap').hide();
            $('#order-confirm').text('确认下单')
        }
        $('#pay-price').text($.utilFormatCurrency(ship));
        $('#total-price').text($.utilFormatCurrency(ship));
    } else {
        $('#pay-price-wrap').show();
        $('#order-confirm').text('去付款')
        $('#pay-price').text($.utilFormatCurrency(tc - card));
        $('#total-price').text($.utilFormatCurrency(tc - card));
    }

}

$('.order-toggle-item').click(function () {
    $(this).siblings().toggle();
    $(this).find('i').toggleClass('coupon-down');
    $(this).find('i').toggleClass('coupon-up');

});


//优惠券选择
$('.order-coupon-item').click(function () {
    if ($(this).attr('data-type') == "enable") {
        $(this).addClass('on');
        $(this).siblings().removeClass('on');
        $(this).find('input[name=coupons]').attr('checked', 'checked');
        $(this).siblings().find('input[name=coupons]').removeAttr('checked');
    }
    checkOrderAmount();

})


if ($('#coupons-enable input[name=coupons]').size() > 0) {
    $('.order-coupon-item[data-type=enable]:first').trigger('click');
    $('.order-toggle-item').trigger('click');
}

$('.card-checkbox').live('click', function () {
    if ($(this).attr('checked') == 'checked') {
        $(this).parent().parent().siblings().find('.card-checkbox').removeAttr('checked');
    }
    setTimeout(function () {
        checkOrderAmount();
    }, 100);

});


//D2C卡
$('.d2c-card-button').click(function () {
    $.each($('.d2c-card'), function (i, d) {
        if ($(d).val() == '') {
            jAlert('请填写D2C卡编号和密码。', '', function () {
                $(d).focus();
            })
            return false;
        }
    });
    var url = $(this).attr('data-url');
    $.post(url, $('.d2c-card').serialize(), function (data) {
        if (data.result.status == 1) {
            $('.d2c-card').val('');
            var card = data.result.data.card;
            var card_date = new Date(parseInt(card.expireDate));
            var str = '<tr class="text-center">\
				<td><input type="checkbox" name="cashCardId" class="card-checkbox" value="' + card.id + '" rel="' + card.available + '" /></td>\
				<td>' + card.code + '</td>\
				<td>' + card.amount + '</td>\
				<td>' + card.available + '</td>\
				<td>' + card_date.format("yyyy年MM月dd日") + '</td>\
				</tr>';
            $('#d2c-card-list tr:last').after(str);
            if ($('#d2c-card-list input[type=checkbox]:checked').size() == 0) {
                $('#d2c-card-list tr:last').find('input[type=checkbox]').trigger('click');
            }
        } else {
            jAlert(data.result.msg);
        }
    }, 'json');
    return false;
});


/*新增地址保存完成后调用函数*/
var addSuccessCallFunction = function () {
    var data = $('body').data('return_data');
    var html = template('address-item-template', data.result.datas.address);
    $.popModalClose();
    $('.address-list').prepend(html);
    $('.address-list .address-item:first').trigger('click');
}
/*编辑地址保存完成后调用函数*/
var editSuccessCallFunction = function () {
    var data = $('body').data('return_data');
    var html = template('address-item-template', data.addressDto);
    $.popModalClose();
    $('.address-list .address-item[data-id=' + data.address.id + ']').remove();
    $('.address-list').prepend(html);
    $('.address-list .address-item:first').trigger('click');
}

/*构造地址弹出框*/
var constructAddress = function (data) {
    var html = template('address-post-template', data);
    var close = $('.address-list .address-item').size() > 0 ? true : false;
    $.popModal({'content': html, 'width': 600, 'canclose': close});
    $('#area-selector').utilSetArea();
}

//点击增加地址
$('.layout-order .address-list .address-add').click(function () {
    var data = {'url': '/address/insert', 'func': 'addSuccessCallFunction'}
    constructAddress(data);
});

//双击编辑地址
$('.layout-order .address-list .address-item').live('dblclick', function () {
    var id = $(this).attr('data-id');
    $.get('/address/' + id, function (result) {
        var data = {};
        data = result.result.datas.address;
        data.url = '/address/update';
        data.func = 'editSuccessCallFunction';
        constructAddress(data);
    }, 'json');
    return false;
});
//点击设置默认收货地址
$('.layout-order .address-item .set-default a').live('click', function () {
    var obj = $(this).parent().parent().parent().parent();
    var id = $(this).attr('rel');
    obj.find('span.is-default').after('<span class="set-default"><a href="javascript:" rel="' + obj.find('span.is-default').attr('rel') + '" id="tip' + obj.find('span.is-default').attr('rel') + '" class="grey">【设为默认】</a></span>');
    obj.find('span.is-default').remove();
    $(this).parent().after('<span class="red is-default" rel="' + id + '">【默认】</span>');
    $(this).parent().remove();

    //发送请求，修改当前默认地址
    $.ajax({
        url: '/address/default/' + id,
        type: 'post',
        dataType: 'json',
        success: function (data) {
            $.flashTip('成功设置为默认收货地址。', 'success');
        }
    });
});
//当地址列表为空时，则弹出增加地址
if ($('.layout-order .address-list .address-item').size() == 0) {
    $('.address-list .address-add').trigger('click');
}
//选择一个地址时，改变样式，选中input
$('.select-address .address-item').live('click', function () {
    var str1 = $(this).find('.first-f').text();
    var str2 = $(this).find('.second-line').text();
    var str = str1 + str2;
    var strname = $(this).find('.first-f').html();
    if (strname.indexOf('台湾') != -1 || strname.indexOf('特别行政区') != -1 || strname.indexOf('国外') != -1) {
        if ($('#order-tips').size() <= 0) {
            $('.order-tip').prepend('<p style="color:red;clear:both;margin-top:10px" id="order-tips">本订单为定制商品或特殊商品,或收货地址为非中国大陆地区,故不支持货到付款方式</p>');
        }
    } else {
        var ccod = $('.order-tip').attr('data-cod');
        if (ccod != "false") $('#order-tips').remove();
    }
    $(this).siblings().removeClass('on');
    $(this).addClass('on');
    $(this).find('input[type=radio]').attr('checked', 'checked');
    $('.order-address').text(str);
});
//当没有默认地址时，进入页面后默认选中第一个
if ($('.layout-order input[name=address]:checked').size() == 0) {
    $('.address-list .address-item:first').trigger('click');
}

/*订单付款*/
$('#order-pay').click(function () {
    var obj = $(this);
    var id = $('#pay-form input[name=id]').val();
    var sn = $('#pay-form input[name=sn]').val();
    var type = $('input[name=paymentType]').val();
    var ordertype = $('input[name=orderType]').val();
    var alert_text = obj.attr('data-alert');
    if (ordertype == undefined) ordertype = 'order';
    if (alert_text == undefined) alert_text = '若支付成功，请按“确定”进入我的订单。如果支付失败，请联系我们的客服专员。';
    if (type == 'ONLINE' || type == '') {
        jAlert('请选择支付宝或银行进行支付。');
        return false;
    } else if (type == 'ALIPAY' || type == 'WX_SCANPAY' || type == 'COMMPAY' || type == 'PAYPAL') {
        var url = obj.attr('data-url');
        if (url == undefined || url == '') {
            url = '/member/order/' + sn + '?gaOrdersn=' + sn;
        }
        if (type == 'WX_SCANPAY') {
            location.href = '/payment/weixin/kf?id=' + id + '&paymentType=WX_SCANPAY&orderType=' + ordertype;
            return false;
        }
        if (type == 'COMMPAY' || ($('input[name=paymentType]').val() != '' && $('input[name=paymethod]').val() != '')) {
            jConfirm(alert_text, '', function (r) {
                if (r) {
                    location.href = url;
                }
            });
            setTimeout(function () {
                $('#pay-form').attr('target', '_blank').submit();
                obj.attr('disabled', true);
            }, 100);
        } else {
            jAlert('请选择支付宝或银行进行支付。');
            return false;
        }
    } else if (type == 'COD') {
        jConfirm('确定使用“货到付款” 的方式吗？', '', function (r) {
            if (r) {
                obj.attr('disabled', true).text('正在提交...');
                setTimeout(function () {
                    $('#pay-form').removeAttr('target').submit();
                }, 2000);
            }
        });
    } else if (type == 'WALLET') {
        var ava = $('.wallet-password').attr('data-money');
        if (ava == "available") {
            jAlert('钱包余额不足，请充值或者更换支付方式');
            return false;
        }
        /*
        if ($('#before-password').val()==''||$('#before-password').val().length<6){
            jAlert('请输入正确的支付密码！','',function(){
                $('#before-password').focus();
            });	
            return false;		
        }else{
            $('#pay-password').val(hex_md5($('#before-password').val()));
            $.post('/member/account/password',{'password':$('#pay-password').val()},function(data){
                if (data.result.status==1){
                    $('#pay-form').removeAttr('target').submit();
                    obj.attr('disabled',true);
                }else{
                    jAlert(data.result.message);
                    return false;
                }
            },'json');
        }
        */
    }
    setTimeout(function () {
        obj.removeAttr('disabled');
    }, 1000);
});

$('.alipay .pay-li,.bank .pay-li').die().live('click', function () {
    $(this).parent().parent().find('label').removeClass('on');
    $(this).find('label').addClass('on');
    $(this).parent().parent().find('input').attr('checked', false);
    $(this).find('input').attr('checked', true);
    if ($(this).find('label').attr('data-type') == 'COD' || $(this).find('label').attr('data-type') == 'WALLET') {
        $('input[name=paymentType]').val($(this).find('label').attr('data-type'));
        $('.online-pay').attr('checked', false);
        $('input[name=paymethod]').val('');
        $('input[name=defaultbank]').val('');
    } else {
        $('input[name=paymentType]').val($(this).find('input').attr('data-type'));
        $('input[name=paymethod]').val($(this).find('input').attr('method'));
        $('input[name=defaultbank]').val($(this).find('input').val());
        $('.online-pay').attr('checked', true);
    }
    if ($(this).find('label').attr('data-type') == 'WALLET') {
        var orderpay = $('.wallet-user').attr('data-all');
        var aval = $('.wallet-user').attr('data-ava');
        var html = template('wallet-pay-template', {'TotalAmount': aval, 'totalPay': orderpay});
        $.popModal({'content': html, 'width': 426});
    }


});

function paycheck() {
    var passward = $('#pop-password').val();
    if (passward == '' || passward.length < 6) {
        $('#wallet-tip').html('密码格式或长度不正确');
    } else {
        var onpassward = hex_md5(passward);
        $.post('/member/account/password', {'password': onpassward}, function (data) {
            if (data.result.status == 1) {
                $('#pay-password').val(onpassward);
                $.popModalClose();
                $('#pay-form').removeAttr('target').submit();
            } else {
                $('#wallet-tip').html(data.result.message);
                return false;
            }
        }, 'json');
    }
}

//默认选中支付宝
if ($('.alipay .pay-li').size() > 0) {
    $('.alipay .pay-li:first').trigger('click');
}
