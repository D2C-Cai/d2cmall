/*
 * 点击小图显示大图
 */

$('.product-img .img-thumbs a').click(function () {
    var data_img = $(this).find('img').attr('data-image');
    var origi_img = $(this).find('img').attr('ori-image');
    var index = $(this).index();
    var size = $('.thumbs-list a').size();
    $(this).siblings().removeClass('on');
    $(this).addClass('on');
    $('#product-image-middle').attr('src', data_img);
    $('#product-image-middle').attr('data-id', index);
    $('#product-image-middle').attr('data-image', origi_img);

    if ($(this).attr('data-video') != undefined) {
        var video = $(this).attr('data-video');
        var html = '<video id="productVideo" src="' + video + '"></video>';
        $('.video-play-layer').html(html).show();
        var a = $("#productVideo").get(0);
        a.play();
        a.addEventListener('ended', function () {
            $('.video-play-layer').html('').hide();
        }, false)
    } else {
        $('.video-play-layer').hide();
    }
    return false;
});

$('.product-img .img-thumbs a:first').trigger('click');
var hasvideo = $("#product-image-middle").attr('data-video');
var pic_style = $('#product-image-middle').attr('data-picStyle');
var product_type = $('#product-image-middle').attr('data-productType');
$("#product-image-middle").imageZoom({hasvideo: hasvideo});

var timer = null;
$('.product-attr-tab li:first').addClass('on');
$('.product-attr-cont div:first').show();
$('.product-attr-tab li').hover(function () {
    var this_obj = $(this);
    var data_id = this_obj.attr('data-id');
    var obj = $('.product-attr-cont div[data-id=' + data_id + ']');
    timer = setTimeout(function () {
        this_obj.addClass('on');
        this_obj.siblings().removeClass('on');
        obj.show();
        obj.siblings('div[data-id]').hide();
    }, 100);
}, function () {
    clearTimeout(timer);
});
//播放视频

//关闭播放层
$('.video-play-layer').live('click', function () {
    $('.video-play-layer').html('').hide();
});

var mainFont = $('.main-font').text();
//数量增减
$('.increase').click(function () {
    var obj = $(this);
    var skuid = obj.parents('form').find('input[name=skuId]').val();
    if (obj.attr('disabled') == 'disabled') {
        return false;
    }
    if (skuid == '') {
        if ($('.product-sku').hasClass('sec-font')) {
            $.flashTip('请先选择' + mainFont + '和' + $('.sec-font label').text(), 'warning');
        } else {
            $.flashTip('请先选择' + mainFont, 'warning');
        }
        return false;
    }

    var i = parseInt(obj.siblings('.data-num').val()) + 1;
    if (i >= obj.siblings('.store').val() && parseInt(obj.siblings('.store').val()) > 0) {
        i = obj.siblings('.store').val();
        $.flashTip('最多能购买' + i + '件', 'warning');
    }
    obj.siblings('.data-num').val(i);
    if (obj.siblings('.store').val() == 0) {
        $.flashTip('库存不足，无法购买', 'warning');
        obj.siblings('.data-num').val(0);
        return false;
    }

});
$('.decrease').click(function () {
    var obj = $(this);
    var skuid = obj.parents('form').find('input[name=skuId]').val();
    if (obj.attr('disabled') == 'disabled') {
        return false;
    }
    if (skuid == '') {
        if ($('.product-sku').hasClass('sec-font')) {
            $.flashTip('请先选择' + mainFont + '和' + $('.sec-font label').text(), 'warning');
        } else {
            $.flashTip('请先选择' + mainFont, 'warning');
        }
        return false;
    }
    var i = parseInt(obj.siblings('.data-num').val()) - 1;
    if (i <= 0) {
        i = 1;
    }
    if (parseInt(obj.siblings('.data-num').val()) == 0) {
        i = 0;
    }
    obj.siblings('.data-num').val(i);
});
$(".data-num").blur(function () {
    var x = parseInt($(this).val());
    var y = parseInt($(this).siblings('.store').val());

    if (x > y) {
        $(this).val(y);
        $.flashTip('最多能购买' + y + '件', 'warning');
        return false;
    }
});
$('.data-num').utilSetNumber();
/*
 * 点击颜色尺码
 */

$('.product-sku .value a').click(function () {
    var types = $('#type-c').attr('data-type')
    var obj = $(this),
        obj_form = obj.parents('form');
    var same_obj = $('.product-sku .value a[data-type="' + obj.attr('data-type') + '"][data-id="' + obj.attr('data-id') + '"]');
    if (obj.attr('class').indexOf('disabled') == -1) {
        if (obj.hasClass('selected')) {
            same_obj.removeClass('selected');
            obj.hasClass('color-section') ? $('.size-section').removeClass('disabled').removeAttr('disabled') : $('.color-section').removeClass('disabled').removeAttr('disabled');

        } else {
            same_obj.siblings().removeClass('selected');
            same_obj.addClass('selected');
        }
        if (obj.hasClass('color-section')) {
            var data_image = $(this).find('img').attr('data-image');
            var mid_image = $('#product-image-middle').attr('src');
            var big_image = $(this).find('img').attr('ori-image');
            if (data_image != mid_image) {
                $('#product-image-middle').attr('src', data_image);
                $('#product-image-middle').attr('data-image', big_image);
            }
        }
        var tips = new Array(), str = '', param = '';


        $.each(obj_form.find('.product-sku .value').find('.selected'), function (i, d) {


            tips.push('"' + $(d).attr('rel') + '"');
            param += str + $(d).attr('data-type') + '=' + $(d).attr('data-id');
            str = '&';
        });

        if (tips.length > 0) {
            $('.product-select-tip').html('你选择了：' + tips);
        } else {
            $('.product-select-tip').html('');
        }
        if (param) {
            $.post('/product/getSKUInfo/' + _product_id, param, function (data) {
                if (data.result.status == 1) {
                    if (types == 'Hassistant') {
                        var store = data.result.store;
                        var sku_id = data.result.skuId;
                        if (typeof (data.result.Size) == 'object') {
                            $('.img a').addClass('disabled').attr('disabled', true);
                            $.each(data.result.Size, function (i, d) {
                                if (d.store > 0)
                                    $('.product-sku .value a[data-type="color"][data-id="' + d.Color_id + '"]').removeClass('disabled').removeAttr('disabled');
                            });
                            if (data.result.Size.length == 1 && data.result.Size[0].store > 0 && obj_form.find('.product-sku .value a[data-type="size"]').size() == 1) {
                                tips = [];

                            }
                        }
                        if (typeof (data.result.Color) == 'object') {
                            $('.txt a').addClass('disabled').attr('disabled', true);
                            $.each(data.result.Color, function (i, d) {
                                if (d.store > 0)
                                    $('.product-sku .value a[data-type="size"][data-id="' + d.Size_id + '"]').removeClass('disabled').removeAttr('disabled');
                            });

                        }
                        if (typeof (data.result.Size) == 'object' && typeof (data.result.Color) == 'object') {
                            $('.current-price').text($.utilFormatCurrency(data.result.price));
                        }
                        var store_text = '';
                        //去除禁用的颜色尺码选择
                        /*$.each($('.product-sku .value a'),function(i,d){
                            if ($(d).attr('disabled')=='disabled') {
                                
                                $(d).removeClass('selected');	
                            }
                        });*/

                        /*var store_text='';
                        if (store>100){
                            store_text='';
                        }else if (store > 0 && store<10){
                            store_text='<strong style="color:#FF3300">快没货了哦，赶紧抢购</strong>';
                        }else if (store <=0){
                            store_text='<strong style="color:#FF3300">已经没货了</strong>';
                        }else{
                            store_text='';
                        }*/
                        var store_text = '';
                        /* 库存要求暂时不显示
                            store_text='<span style="padding-left:10px">(还剩 ';
                            store_text+='<strong style="color:#FF3300">'+store+'</strong>';
                            store_text+=' 件)</span>';
                        */
                        //data.result.price?obj_form.find('.current-price').text($.utilFormatCurrency(data.result.price)):'';
                        store > 0 ? $('.store-span').html(store_text) : $('.store-span').html('');
                        $('.store').val(store);
                        obj_form.find('input[name=skuId]').val(data.result.skuId);
                        /*if(data.result.store<0 || data.result.store==0){
                            $('.product-remark').show();
                        }else{
                            $('.product-remark').hide();
                        }*/
                    } else {
                        var skuId = data.result.Color[0].skuId == undefined ? '' : data.result.Color[0].skuId;
                        var store = data.result.Color[0].store;
                        var store_text = '';
                        store > 0 ? $('.store-span').html(store_text) : $('.store-span').html('');
                        $('.store').val(store);
                        obj_form.find('input[name=skuId]').val(skuId);
                    }

                } else if (data.result.status == -1) {
                    obj_form.find('.product-sku a').removeClass('disabled').removeAttr('disabled');
                    obj_form.find('.store').val(data.result.store);
                    obj_form.find('input[name=skuId]').val('');
                    /*if(data.result.store<0 || data.result.store==0){
                        $('.product-remark').show();
                    }else{
                        $('.product-remark').hide();
                    }*/
                }
            }, 'json');
        } else {
            obj_form.find('input[name=skuId]').val('');
        }
    }
    return false;
});
/*
if($('a.color-section').size()==2){
	$('a.color-section').trigger('click');
}

if ($('a.color-section').size()==1){
    $('a.color-section').trigger('click');
}
if ($('a.size-section').size()==1){
    $('a.size-section').trigger('click');
}*/
/*
 * 购买，加入购物车，门店试衣操作
 */
$('.product-operator-button').click(function () {
    var obj = $(this);
    var obj_form = obj.parents('form');
    var type = obj.attr('data-type');
    var skuid = obj_form.find('input[name=skuId]').val();
    var crowd_item_id = obj_form.find('input[name=crowdItemId]').val();
    var product_type = obj.attr('data-productType');
    if (skuid == '') {
        $.flashTip('请选择颜色和尺码。', 'warning');
        return false;
    }
    var num = parseInt(obj_form.find('input[name=num]').val());
    num = $.trim(num);
    if (isNaN(num) || num < 1) {
        $.flashTip('请选择你要购买的数量。', 'warning');
        return false;
    }
    if (obj_form.find('input[name=num]').val() > parseInt(obj_form.find('.store').val())) {
        $.flashTip('最多只能购买' + obj_form.find('.store').val() + '件。', 'warning');
        obj_form.find('.data-num').val(obj_form.find('.store').val());
        return false;
    }
    var data_form = obj.attr('data-form');
    if (type == 'cart') {
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', '$(".product-operator-button[data-form=' + data_form + '][data-type=cart]").trigger("click")');
                userLogin();
            } else if (data.result.datas.isBind == false) {
                $('body').data('function', '$(".product-operator-button[data-form=' + data_form + '][data-type=cart]").trigger("click")');
                location.href = "/member/bind";
            } else {
                if (product_type == 'CROSS') {
                    var html = ['<div class="qr-box">',
                        '<p  class="box-title">该商品暂只支持APP和小程序购买</p>',
                        '<div class="box-cont">',
                        '<div class="box-cont-item">',
                        '<img src="//static.d2c.cn//img/topic/180607/cp/images/pic_wechatcode@3x.png" style="width:150px;height:150px;">',
                        '<div class="cont-item-tip">扫码进入D2C小程序</div>',
                        '</div>',
                        '<div class="box-cont-item">',
                        '<img src="//static.d2c.cn//img/topic/180607/cp/images/pic_appqrcode@3x.png" style="width:150px;height:150px;">',
                        '<div class="cont-item-tip">扫码下载D2C APP</div>',
                        '</div>',
                        '</div>',
                        '<div class="qr-close"><img src="//static.d2c.cn//img/topic/180607/cp/images/icon_closed@3x.png" width="100%"></div>',
                        '</div>'].join("");
                    $.popModal({content: html, 'width': '640'});
                    $('.qr-close').on('click', function () {
                        $.popModalClose();
                    });
                } else {
                    $.ajax({
                        'url': '/cart/add/' + skuid + '/' + num + '?t=' + new Date().getTime(),
                        'type': 'post',
                        'data': {},
                        'dataType': 'json',
                        'success': function (data, status) {
                            if (data.result.status != -1) {
                                var html = template('cart-success-template', data);
                                if (obj_form.siblings('.product-cart-modal').size() == 0) {
                                    obj_form.after('<div class="product-cart-modal"></div>');
                                }
                                obj_form.siblings('.product-cart-modal').html(html);
                                $('#cart-nums-id').html('(' + data.result.datas.totalNum + ')');
                                $('#cart-nums-id').data('change', 1);
                            } else {
                                $.flashTip(data.result.message, 'warning');
                            }
                        }
                    });
                }
            }
        })
    } else if (type == "fitting") {
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', '$(".product-operator-button[data-form=' + data_form + '][data-type=fitting]").trigger("click")');
                userLogin();
            } else {
                $.popModal({
                    'url': '/o2oSubscribe/edit?skuId=' + skuid + '&quantity=' + num,
                    datatype: '',
                    'width': 560
                })
            }
        });
    } else {
        var data_form = obj.attr('data-form');
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', '$(".product-operator-button[data-form=' + data_form + '][data-type=buy]").trigger("click")');
                userLogin();
            } else {
                if (product_type == 'CROSS') {
                    var html = ['<div class="qr-box">',
                        '<p  class="box-title">该商品暂只支持APP和小程序购买</p>',
                        '<div class="box-cont">',
                        '<div class="box-cont-item">',
                        '<img src="//static.d2c.cn//img/topic/180607/cp/images/pic_wechatcode@3x.png" style="width:150px;height:150px;">',
                        '<div class="cont-item-tip">扫码进入D2C小程序</div>',
                        '</div>',
                        '<div class="box-cont-item">',
                        '<img src="//static.d2c.cn//img/topic/180607/cp/images/pic_appqrcode@3x.png" style="width:150px;height:150px;">',
                        '<div class="cont-item-tip">扫码下载D2C APP</div>',
                        '</div>',
                        '</div>',
                        '<div class="qr-close"><img src="//static.d2c.cn//img/topic/180607/cp/images/icon_closed@3x.png" width="100%"></div>',
                        '</div>'].join("");
                    $.popModal({content: html, 'width': '640'});
                    $('.qr-close').on('click', function () {
                        $.popModalClose();
                    });
                } else {
                    obj_form.submit();
                }
            }
        });
    }
    return false;
});

/*
 * 尺码
 */

if ($('#product-size-container').attr('data-val')) {
    var size_val = eval('(' + $('#product-size-container').attr('data-val') + ')');
    var tab = '<table class="table table-grey">';
    tab += '<tr><th>尺码</th>';
    $.each(size_val.header, function (d, t) {
        tab += '<th>' + t + '</th>';
    });
    tab += '</tr>';
    $.each(size_val.data, function (s, f) {
        tab += '<tr class="item text-center"><td>' + s + '</td>';
        $.each(size_val.header, function (d, t) {
            tab += '<td>' + f[t] + '</td>';
        });
        tab += '</tr>';
    });
    tab += '</table>';
    $('#product-size-container').html(tab);
}
/*
 * 相关搭配选择加入购物车
 */
/* 选择全部  */
$('.product-pairs .check-all').click(function () {
    var obj_form = $(this).parents('form');
    if ($(this).attr('checked') == 'checked') {
        $.each(obj_form.find('.checkbox'), function (i, d) {
            $(d).attr('checked', true);
            $(d).parent().parent().find('input,select,.data-crease').removeAttr('disabled');
        });
        obj_form.find('*[type=submit]').removeClass('disabled').removeAttr('disabled');
    } else {
        $.each(obj_form.find('.checkbox'), function (i, d) {
            $(d).attr('checked', false);
            $(d).parent().parent().find('input:not([type=checkbox]),select,.data-crease').attr('disabled', 'disabled');
        });
        obj_form.find('*[type=submit]').addClass('disabled').attr('disabled', 'disabled');
    }
});
$('.product-pairs .checkbox').click(function () {
    var checked = $(this).attr('checked');
    var obj = $(this).parent().parent();
    var obj_form = $(this).parents('form');
    if (checked) {
        $(this).attr('checked', true);
        obj.find('input:not([type=checkbox]),select,.data-crease').removeAttr('disabled');
        obj_form.find('*[type=submit]').removeClass('disabled').removeAttr('disabled');
    } else {
        $(this).attr('checked', false);
        obj.find('input:not([type=checkbox]),select,.data-crease').attr('disabled', 'disabled');
        if ($('.product-pairs .checkbox[checked]').size() == 0) {
            obj_form.find('*[type=submit]').addClass('disabled').attr('disabled', 'disabled');
        }
    }
});

var pullSkuData = function (obj_p) {

    var param = str = '';
    $.each(obj_p.find('.select-sku'), function (i, d) {
        param += str + $(d).attr('data-type') + '=' + $(d).val();
        str = '&';
    });
    if (param) {
        $.post('/product/getSKUInfo/' + obj_p.find('input[name=productId]').val(), param, function (data) {
            if (data.result.status == 1) {
                store = parseInt(data.result.store);
                if (isNaN(store)) store = 0;
                if (typeof (data.result.Color) == 'object' && typeof (data.result.Size) == 'object') {
                    if (data.result.price <= 0) {
                        obj_p.find('.sku-price').text('????.??');
                    } else {
                        var a = "¥" + $.utilFormatCurrency(data.result.price)
                        obj_p.find('.sku-price').text(a);
                    }
                    if (data.result.store > 0) {
                        obj_p.find('input[name=skuId]').val(data.result.skuId);
                    }


                }
                obj_p.find('.store').val(store);

                if (store <= 0) {
                    obj_p.find('.data-num').val(0);
                    obj_p.find('input[name=skuId],.data-num').attr('disabled', true);
                    obj_p.find('.checkbox').attr('checked', false);
                } else {
                    obj_p.find('.data-num').val(1);
                    obj_p.find('input[name=skuId],.data-num').removeAttr('disabled');
                    obj_p.find('.checkbox').attr('checked', true);
                }
            } else if (data.status == -1) {
                obj_p.find('.product-sku a').removeClass('disabled').removeAttr('disabled');
                obj_p.find('.store').val(data.result.store);
                obj_p.find('input[name=skuId]').val('');
            }
        }, 'json');
    } else {

        obj_p.find('input[name=skuId]').val('');
    }
};
/*
$.each($('.select-sku'),function(i,d){
	var obj_p=$(d).parent().parent();
	pullSkuData(obj_p);
});*/
$('.select-sku').change(function () {
    var obj = $(this),
        obj_p = obj.parent().parent(),
        type = obj.attr('data-type');
    if (type == 'color') {
        var img = obj.find('option:selected').attr('rel');
        obj_p.find('.pair-img').attr('src', img);
    }
    pullSkuData(obj_p);
});


$('.multi-add-cart').click(function () {
    var obj = $(this).parents('form');
    var h = obj.find('input[name=skuId]:not(:disabled)').size();
    var m = 0;
    $.each(obj.find('input[name=skuId]:not(:disabled)'), function (i, d) {
        if ($(d).val() == '') {
            m++;
        }
    });

    if (h == 0) {
        $.flashTip('至少要选择一件商品才能添加到购物车', 'warning');
        return false;
    }
    if (m > 0) {
        $.flashTip('选中的商品必须选择颜色尺码才能加入购物车', 'warning');
        return false;
    }
    $.getJSON('/member/islogin', function (data) {
        if (data.result.login == false) {
            $('body').data('function', '$(".multi-add-cart").trigger("click")');
            userLogin();
        } else {
            $.post('/cart/batch/add.json', obj.find('input').serialize(), function (data) {
                if (data.result.status != -1) {

                    var html = template('cart-success-template', data);
                    if (obj.siblings('.product-cart-modal').size() == 0) {
                        obj.after('<div class="product-cart-modal"></div>');
                    }
                    obj.siblings('.product-cart-modal').html(html);
                    $('#cart-nums-id').text('(' + data.result.datas.totalNum + ')');
                    $('#cart-nums-id').data('change', 1);
                } else {
                    $.flashTip(data.result.message, 'warning');
                }
            });
        }
    });
    return false;
});

$('.product-cont-bar .tab li').click(function () {
    $('html, body').animate({scrollTop: $('#product-detail').offset().top}, 300);
    var id = $(this).attr('data-id');
    var url = $(this).attr('data-url')
    $(this).addClass('on');
    $(this).siblings().removeClass('on');
    $('#product-detail-' + id).show();
    $('#product-detail-' + id).siblings('div[id]').hide();
    if (url) {
        $.get(url, function (data) {
            $('#product-detail-' + id).html(data);
        });
    }
    return false;
});
$('.comment-count').click(function () {
    $('.product-cont-bar .tab li[data-id=comment]').trigger('click');
});

$('.suspend-cart-button button').click(function () {
    $('html, body').animate({scrollTop: 100}, 300);
})

