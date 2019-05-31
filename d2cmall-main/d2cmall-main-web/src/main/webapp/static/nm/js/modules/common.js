window.onerror = function (errorMessage, scriptURI, lineNumber, columnNumber, errorObj) {
    console.log("错误信息：", errorMessage);
    console.log("出错文件：", scriptURI);
    console.log("出错行号：", lineNumber);
    console.log("出错列号：", columnNumber);
    console.log("错误详情：", errorObj);
}


var userAgent = navigator["userAgent"]["toLowerCase"](),
    isiPad = userAgent.match(/ipad/),
    isiPhone = userAgent.match(/iphone|ipod/),
    iOS = isiPad || isiPhone,
    isAndroid = userAgent.match(/android/),
    isWeChat = userAgent.match(/micromessenger/);
isTX = userAgent.match(/qq/);
isQQBrowser = userAgent.match(/mqqbrowser/);
isQQ = isTX && !isQQBrowser;
isWeiBo = userAgent.match(/weibo/);
var click_type = iOS ? 'touchstart' : 'click';
var secretKey = '8811d44df3c0b408f6fa4a31002db44d';


$(function () {
    if (isAndroid) {
        $('html').addClass('platform-android');
    } else if (iOS) {
        $('html').addClass('platform-ios');
    }
    if (isiPhone && (screen.height == 812 && screen.width == 375) && isWeChat) {
        $('html').addClass('iphonex');
    }

    if ($('#product-slide').size() > 0) {
        var mySwiper = new Swiper('.swiper-container', {
            loop: true,
            pagination: {
                el: '.swiper-pagination',
            }
        })
    }
    //统计购物车
    if ($('.my-cart-num').size() > 0) {
        countCart();
    }

    $(".lazyload,.lazyload img").unveil(350);

    if ($('.scroll-load-more').size() > 0) {
        $('.scroll-load-more').utilScrollLoad();
    }

    if ($('.load-brand-product').size() > 0) {
        var source = '<div class="lazyload">\
			{{each list as value i}}\
			<a href="/product/{{value.id}}" class="item item-flex item-gap">\
			<span class="img">\
			{{if value.store < 1}}<i class="n-product"></i><span class="outp">已售罄</span>{{/if}} <img src="//static.d2c.cn/common/m/img/blank100x157.png" data-image="//img.d2c.cn{{value.productImageCover}}!300" alt="{{value.name}}" /></span>\
			<span class="title">{{value.name}}</span>\
			<span class="price"><strong  class="addprice" data-price="{{value.minPrice}}">&yen;{{value.minPrice}}</strong>&nbsp;&nbsp;{{if value.currentPrice< value.originalPrice}}<s>&yen; {{value.originalPrice}}</s>{{/if}}</span>\
			</a>\
			{{/each}}\
			</div>';
        $('.load-brand-product').each(function () {
            var item = this;
            var brand_id = $(item).attr('data-id');
            if (!brand_id) console.log('缺失品牌id');
            $(item).attr('id', 'brand-product-' + brand_id)
            $(item).after('<div class="load-more load-more-brand-product" data-id="' + brand_id + '" data-url="/showroom/product/' + brand_id + '" data-target="brand-product-' + brand_id + '" data-page="0" data-total="2">点击加载更多</div>');
            $('.load-more-brand-product[data-id="' + brand_id + '"]').utilScrollLoad(source);
        });
    }


    /* 表单校验
     * 只要指定表单的样式为validate-form,自动会使用以下方式进行表单校验
     * 指定了success-tip,则在成功后显示success-tip的文字
     * 指定了confirm，则先弹出确认框，在进行操作
     * 指定call-back，则执行完成后执行call-back
     * 指定tip-type，则提示形式为flashtip和alert
     * form加了target将不经过ajax请求
     * 具体设置请参考plugin.js 里的utilValidateForm;
     */
    $(document).on('submit', '.validate-form', function () {
        var form = $(this);
        var url = form.attr('action'),
            success_tip = form.attr('success-tip'),
            fail_tip = form.attr('fail-tip'),
            confirm_text = form.attr('confirm'),
            call_function = form.attr('call-back'),
            redirect_url = form.attr('redirect-url'),
            target = form.attr('target'),
            is_confirm = $('body').data('is_confirm_form'),
            no_error = form.attr('error-tips'),
            error_function = form.attr('error-function');
        if (form.utilValidateForm()) {
            if (confirm_text && !is_confirm) {
                jConfirm(confirm_text, function (r) {
                    if (r) {
                        form.find('*[type="submit"]').attr('disabled', true);
                        if (target == undefined) {
                            $.ajax({
                                url: url,
                                data: form.serialize(),
                                type: form.attr('method'),
                                dataType: 'json',
                                success: function (data) {
                                    if (data.result.status == -2) {
                                        if ($('#random-code-insert').html() == '') {
                                            var random_code_html = template('random-code-template', {'timestr': new Date().getTime()});
                                            $('#random-code-insert').html(random_code_html);
                                        }
                                    }

                                    if (data.result.status == 1) {
                                        if (success_tip != 'false') {
                                            if (!success_tip) success_tip = data.result.message;
                                            success_tip != '' && $.flashTip({
                                                position: 'center',
                                                type: 'success',
                                                message: success_tip
                                            });
                                        }
                                        if (call_function) {
                                            if (call_function != false) {
                                                try {
                                                    $('body').data('return_data', data);
                                                    if (typeof (eval(call_function)) == "function") {
                                                        eval(call_function + '()');
                                                    }
                                                    ;
                                                } catch (e) {
                                                    alert('不存在' + call_function + '这个函数');
                                                    return false;
                                                }
                                            }
                                        } else {
                                            setTimeout(function () {
                                                redirect_url ? location.href = redirect_url : location.reload();
                                            }, 1000);
                                        }
                                    } else {
                                        if (!fail_tip) fail_tip = data.result.message;
                                        $.flashTip({position: 'center', type: 'error', message: fail_tip});
                                        $('.validate-img').trigger('click');
                                    }
                                    setTimeout(function () {
                                        form.find('*[type="submit"]').removeAttr('disabled');
                                    }, 1000);
                                }
                            });
                        } else {
                            $('body').data('is_confirm_form', true);
                            form.submit();
                            setTimeout(function () {
                                form.find('*[type="submit"]').removeAttr('disabled');
                            }, 1000);
                        }
                    }
                });
                return false;
            } else {
                form.find('*[type="submit"]').attr('disabled', true);
                if (target == undefined) {
                    $.ajax({
                        url: url,
                        data: form.serialize(),
                        type: form.attr('method'),
                        dataType: 'json',
                        success: function (data) {
                            data = eval(data);
                            if (data.result.status == -2) {
                                if ($('#random-code-insert').html() == '') {
                                    var random_code_html = template('random-code-template', {'timestr': new Date().getTime()});
                                    $('#random-code-insert').html(random_code_html);
                                }
                            }
                            if (data.result.status == 1) {
                                if (success_tip != 'false') {
                                    if (!success_tip) success_tip = data.result.message;
                                    success_tip != '' && $.flashTip({
                                        position: 'center',
                                        type: 'success',
                                        message: success_tip
                                    });
                                }
                                if (call_function) {
                                    if (call_function != false) {
                                        try {
                                            $('body').data('return_data', data);
                                            if (typeof (eval(call_function)) == "function") {
                                                eval(call_function + '()');
                                            }
                                            ;
                                        } catch (e) {
                                            alert('不存在' + call_function + '这个函数');
                                            return false;
                                        }
                                    }
                                } else {
                                    setTimeout(function () {
                                        redirect_url ? location.href = redirect_url : location.reload();
                                    }, 2000);
                                }
                            } else {

                                if (!fail_tip) fail_tip = data.result.message;
                                if (no_error == "false") {
                                    if (error_function) {
                                        try {
                                            if (typeof (eval(error_function)) == "function") {
                                                eval(error_function + '()');
                                            }
                                            ;
                                        } catch (e) {
                                            alert('不存在' + error_function + '这个函数');
                                            return false;
                                        }
                                    }
                                } else {
                                    $.flashTip({position: 'center', type: 'error', message: fail_tip});
                                }

                                $('.validate-img').trigger('click');

                            }
                            setTimeout(function () {
                                form.find('*[type="submit"]').removeAttr('disabled');
                            }, 1000);

                        }
                    });
                    return false;
                } else {
                    console.log(target)
                    $('body').data('is_confirm_form', true);
                    //form.submit();
                    setTimeout(function () {
                        form.find('*[type="submit"]').removeAttr('disabled');
                    }, 1000);

                }

            }
        } else {
            return false;
        }

    });

    /* a,button进行ajax处理，只要指定class="ajax-request"
     *
     * <a href="javascript:" data-url="" template-url="" request-url="" data-param="{id:'',name:''}" data-type="get" confirm="" success-tip="" call-function="" redirect-url="" template-id=""></a>
     * <button type="button" data-url="" template-url="" method-type="get" confirm="" success-tip="" call-function="" redirect-url="" template-id=""></button>
     * method-type默认是get，也可以指定为post
     * 
     * 只请求一次，则
     * <a href="javascript:" data-url=""></a>
     * 若请求之前需要确认，则加入confirm属性；希望执行成功后自定义提示语，加入success-tip；
     * 请求完毕后默认刷新本页，加入redirect-url则成功后跳转到该url；
     * 需要成功后执行某个函数，则加入call-back，该属性覆盖redirect-url,call-back=false则不进行任何动作
     * success-tip不设置则取系统返回的信息，设置为false则不提示任何信息
     * ---------------------------------------------
     * 请求后有下一步操作的，使用弹出层（popModal）或浮动层（floatModal）。只负责弹出内容，内容里的下一步操作需要另外写函数方法
     * 一，请求一个模板地址，即请求一个url地址，返回页面内容到popModal和floatModal上，则指定template-url（使用get请求）
     * <a href="javascript:" template-url=""></a>
     * 在输出的内容里进行操作可以结合表单校验
     * 
     * 二，弹出层的内容是页面自定义模板
     * <a href="javascript:" data-url="" template-id=""></a>
     * data-url为指定到模板表单里的action地址
     * 若需要先请求一个url地址返回数据到模板，则指定request-url
     * 
     * 如果指定request-url,则先请求request-url，返回数据后再进行赋值
     * 如果指定template-url，则会去请求template-url，返回一个模板数据
     * 如果指定template-id，则使用id为template-id的模板
     */
    $(document).on('touchstart', '.ajax-request:not(.disabled)', function () {
        var obj = $(this);
        var obj_id = obj.attr('id'),
            data_url = obj.attr('data-url'),
            title = obj.attr('title');
        template_url = obj.attr('template-url'),
            request_url = obj.attr('request-url'),
            template_id = obj.attr('template-id'),
            method_type = obj.attr('method-type'),
            data_param = obj.attr('data-param'),
            confirm_text = obj.attr('confirm'),
            success_tip = obj.attr('success-tip'),
            fail_tip = obj.attr('fail-tip'),
            call_function = obj.attr('call-back'),
            redirect_url = obj.attr('redirect-url');
        if (!method_type) method_type = 'get';
        if (!template_url && !template_id) {//只请求
            if (confirm_text) {
                jConfirm(confirm_text, function (r) {
                    if (r) {
                        $.ajax({
                            url: data_url,
                            type: method_type,
                            data: data_param ? eval('(' + data_param + ')') : {},
                            dataType: 'json',
                            success: function (data) {
                                if (data.result.login == false) {
                                    if (app_client == true) {
                                        $.flashTip({
                                            position: 'center',
                                            type: 'error',
                                            message: '您还未登录或者登录过期啦，请登录后再操作哦'
                                        });
                                    } else {
                                        $('body').data('function', '$("#' + obj_id + '").trigger("touchstart")');
                                        userLogin();
                                    }

                                    return false;
                                }
                                if (data.result.status == 1) {
                                    if (success_tip != "false") {
                                        if (!success_tip) success_tip = data.result.message;
                                        success_tip != '' && $.flashTip({
                                            position: 'center',
                                            type: 'success',
                                            message: success_tip
                                        });
                                    }
                                    if (call_function) {
                                        if (call_function != false) {
                                            try {
                                                if (typeof (eval(call_function)) == "function") {
                                                    eval(call_function + '()');
                                                }
                                                ;
                                            } catch (e) {
                                                alert('不存在' + call_function + '这个函数');
                                                return false;
                                            }
                                        }

                                    } else {
                                        setTimeout(function () {
                                            redirect_url ? location.href = redirect_url : location.reload();
                                        }, 1000);
                                    }
                                } else {
                                    if (!fail_tip) fail_tip = data.result.message;
                                    $.flashTip({position: 'center', type: 'error', message: fail_tip});
                                }
                            }
                        });
                    }
                });
            } else {
                $.ajax({
                    url: data_url,
                    data: data_param ? eval('(' + data_param + ')') : {},
                    type: method_type,
                    dataType: 'json',
                    success: function (data) {
                        if (data.result.login == false) {
                            if (app_client == true) {
                                $.flashTip({position: 'center', type: 'error', message: '您还未登录或者登录过期啦，请登录后再操作哦'});
                            } else {
                                $('body').data('function', '$("#' + obj_id + '").trigger("touchstart")');
                                userLogin();
                            }
                            return false;
                        } else if (!_isD2C) {
                            location.href = "/member/bind";
                        }
                        if (data.result.status == 1) {
                            if (success_tip != "false") {
                                if (!success_tip) success_tip = data.result.message;
                                success_tip != '' && $.flashTip({
                                    position: 'center',
                                    type: 'success',
                                    message: success_tip
                                });
                            }
                            if (call_function) {
                                if (call_function != false) {
                                    try {
                                        if (typeof (eval(call_function)) == "function") {
                                            eval(call_function + '()');
                                        }
                                        ;
                                    } catch (e) {
                                        alert('不存在' + call_function + '这个函数');
                                        return false;
                                    }
                                }
                            } else {
                                setTimeout(function () {
                                    redirect_url ? location.href = redirect_url : location.reload();
                                }, 1000);
                            }
                        } else {
                            if (!fail_tip) fail_tip = data.result.message;
                            $.flashTip({position: 'center', type: 'error', message: fail_tip});
                        }
                    }
                });
            }
            return false;
        } else {
            var data = {};
            if (template_url) {
                $.get(template_url, function (data) {
                    $.popModal({'content': data});
                });
            }
            if (request_url) {//如果有先请求的地址，则先请求
                $.getJSON(request_url, function (result) {
                    data = result.result.datas;
                    data.url = data_url;
                    var html = template(template_id, data);
                    $.popModal({'content': html});
                });
            }
            if (!request_url && template_id) {
                if (data_param) data = eval('(' + data_param + ')');
                data.url = data_url;
                var html = template(template_id, data);
                $.popModal({'content': html});
            }
            return false;
        }
    });
    if ($('a[check-url]').size() > 0) {
        $.each($('a[check-url]'), function (i, o) {
            var url = $(o).attr('check-url');
            if (_memberId != '') {//防止一进去就跳登录
                $.getJSON(url, function (data) {
                    if (!_isD2C) {
                        location.href = "/member/bind";
                    }
                    if (data.result.status == 1) {
                        var style = $(o).find('.icon').attr('class').replace('-o', '');
                        $(o).find('.icon').attr('class', style);
                        $(o).addClass('disabled');
                    }
                })
            }
        });
    }
    /*上传文件*/
    $(document).on('change', '.upload-file', function () {
        var preview_template = '<div class="upload-item" id="{{id}}">\
            <em class="icon icon-close del"></em>\
            <span><img src="https://img.d2c.cn{{path}}" width="100%" alt="" /></span>\
            <input type="hidden" class="path-input" value="{{value}}">\
        </div>';
        var this_obj = $(this);
        var obj = this_obj.parent();
        var upload_url = this_obj.attr('data-upload-url');
        if ($('.upload-item').size() > 9) {
            jAlert('最多只能上传9张图片。');
            return false;
        }
        $.getScript('/static/nm/js/utils/jquery.fileupload.js', function () {
            $.ajaxFileUpload({
                url: upload_url,
                secureuri: false,
                fileObject: this_obj,
                dataType: 'json',
                timeout: 3500,
                success: function (data) {
                    if (data.status == 1) {
                        setTimeout(function () {
                            var render = template.compile(preview_template);
                            var html = render(data);
                            obj.before(html);
                        }, 600);
                    }
                }
            });
        });
    });
    $(document).on('touchstart', '.upload-item .del', function () {
        var obj = $(this).parent();
        var evidenceId = obj.attr('id');
        jConfirm('确定要删除该图片吗？', function (r) {
            obj.remove();
            if (evidenceId != '') {
                $.get('/member/delEvidence/' + evidenceId + ".json", function (data) {
                    alert(data);
                });
            }
        });
        return false;
    });

    /*发送短信*/
    $(document).on('click', '.validate-send:not(.disabled)', function () {
        var this_obj = $(this);
        var account_obj = $('.validate-account');
        var account = account_obj.val();
        var nation_code = $('.mobile-code').val();
        var account_type = account_obj.attr('data-rule');
        var way = this_obj.attr('data-way');
        if (account == '') {
            account_obj.focus();
            return false;
        }
        if (account_type == 'mobile') {
            if (!account_obj.utilValidateMobile()) {
                account_obj.focus();
                $.flashTip({position: 'top', type: 'error', message: '请输入正确手机号码'});
                return false;
            }
        } else {
            if (!account_obj.utilValidateMobile() && !account_obj.utilValidateEmail()) {
                account_obj.focus();
                $.flashTip({position: 'top', type: 'error', message: '请输入正确手机号码或邮箱'});
                return false;
            }
        }
        var test = function (account) {
            $.ajax({
                url: '/member/checkLoginCode?' + new Date().getTime(),
                type: 'post',
                data: {'code': account},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.result.status == -1) {
                        account = false;
                        $.flashTip({position: 'top', type: 'error', message: '该手机号已经注册'});
                    }
                }
            });
            return account;
        }
        if (way == "register") {//注册
            if (!test(account)) {
                return false;
            }
        }
        var type = this_obj.attr('data-type');
        var source = this_obj.attr('data-source');
        var second = 60;
        var sign = hex_md5("mobile=" + account + secretKey);
        var appParams = $.base64('encode', "mobile=" + account + "&sign=" + sign);
        var data = {
            mobile: account,
            source: source,
            type: type,
            terminal: 'PC',
            nationCode: nation_code,
            appParams: appParams
        };
        $.ajax({
            url: '/sms/send/encrypt',
            type: 'post',
            data: data,
            dataType: 'json',
            success: function (data) {
                if (data.result.status == -2) {
                    $('.validate-img').trigger('click');
                }
                if (data.result.status == 1) {
                    this_obj.attr('disabled', 'disabled');
                    this_obj.addClass('disabled');
                    $('#box-modal-remove').trigger('click');
                    var time = setInterval(function () {
                        if (second < 1) {
                            clearInterval(time);
                            this_obj.removeAttr('disabled').text('重发验证码');
                            this_obj.removeClass('disabled');
                        } else {
                            this_obj.text(second + '秒后重发');
                            second--;
                        }
                    }, 1000);
                } else {
                    $(this_obj).removeAttr('disabled');
                    $.flashTip({position: 'top', type: 'error', message: data.result.message});
                }
                return false;
            }
        });
    });

    /*图片验证码*/
    $(document).on('click', '.validate-img', function () {
        var url = $(this).attr('src');
        if (url.indexOf('time=') >= 0 && url.indexOf('?') >= 0) {
            url = url.replace('&time=', 'time=');
            var arr = url.split('time=');
            url = arr[0];
        }
        if (url.indexOf('?') >= 0) {
            url += '&';
        } else {
            url += '?';
        }
        url = url.replace('?&', '?');
        $(this).attr('src', url + 'time=' + new Date().getTime());
        return false;
    });

    /*
     * 简单领取优惠券
     * <div class="get-coupon-simple" data-batchId=""></div>
     * 在元素上指定className get-coupon-simple，指定batchId（优惠券组）或id（单张优惠券）
     */
    $('.get-coupon-simple').on(click_type, function () {
        if (!_memberId) {
            userLogin();
            return false;
        }
        if (!_isD2C) {
            location.href = '/member/bind';
            return false;
        }
        var batchid = $(this).attr('data-batchid'),
            id = $(this).attr('data-id'),
            url = '';
        if (batchid) {
            url = '/coupon/batch/' + batchid;
        }
        if (id) {
            url = '/coupon/receive/' + id;
        }
        $.getJSON(url, function (res) {
            if (res.result.status == 1) {
                jAlert('领取成功~');
            } else {
                toast({position: 'center', type: 'error', message: '超过领取上限啦~', time: 1500});
            }
        });
        return false;
    });

    /* 提醒
     * 指定提醒按钮样式为remind-send，其他需要发送的参数，以文本域（隐藏文本域）形式放在按钮前（同级），同时增加样式名.remind-param
     */
    $('.remind-send').click(function () {
        var mobile = $(this).siblings('.mobile');
        var param = $(this).siblings('.remind-param').serialize();
        if (mobile.val() == mobile.attr('placeholder') || mobile.val() == '') {
            mobile.val('').focus();
            return false;
        }
        if (!mobile.utilValidateMobile() && !mobile.utilValidateEmail()) {
            mobile.focus();
            return false;
        } else {
            $.post('/remind', param, function (data) {
                if (data.result.status == 1) {
                    jAlert('您已经成功添加了短信或邮箱提醒。');
                } else {
                    jAlert(data.result.message);
                }
            }, 'json');
        }
        return false;
    });

    if ($(".count-down").size() > 0) {
        $.each($(".count-down"), function (i, d) {
            $(d).countdown();
        });
    }
    /*国家区号*/

    $(document).on(click_type, '#choose-country', function () {
        var str = new Date().getTime();

        var country_template = '<header class="modal-header">\
            <div class="header">\
            <div class="header-back"><a href="javascript:$.popupModalClose()" class="icon icon-cross"></a></div>\
            <div class="header-title">选择国家地区</div>\
        </div>\
        </header>\
        <section class="modal-body" id="iScroll-wraper-' + str + '">\
        <div>\
            <div class="form">\
            {{each list as item i}}\
                <div class="form-item" style="background:transparent;">{{item.group}}</div>\
                {{each item.list as country ii}}\
                <div class="form-item item-flex country-item" data-code="{{country.mobileCode}}" data-name="{{country.cnName}}" data-displayname="{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}">\
                    <label style="width:auto;">{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}</label>\
                    <span class="note grey">+{{country.mobileCode}}</span>\
                </div>\
                {{/each}}\
            {{/each}}\
            </div>\
        </div>\
        </section>';
        $.getScript('/static/nm/js/utils/json-country.js', function (result) {
            var data = eval(result);
            var countrys = new Array();
            var group = null;
            var m = 0;
            $.each(data, function (i, d) {
                if (d.isTop && d.group == undefined) {
                    if (countrys[0] == undefined) countrys[0] = {};
                    countrys[0].group = '常用地区';
                    if (countrys[0].list == undefined) countrys[0].list = [];
                    countrys[0].list.push(d);
                } else {
                    if (group != d.group) {
                        m++;
                    }
                    group = d.group;
                    if (countrys[m] == undefined) countrys[m] = {};
                    countrys[m].group = group;
                    if (countrys[m].list == undefined) countrys[m].list = [];
                    countrys[m].list.push(d);

                }
            });
            var render = template.compile(country_template);
            var html = render({'list': countrys});
            $.popupModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
            new IScroll('#iScroll-wraper-' + str, {mouseWheel: true, click: true});
        });
        return false;
    });
    $(document).on('click', '.country-item', function () {
        var obj = $(this);
        var code = obj.attr('data-code'), name = obj.attr('data-name'), displayname = obj.attr('data-displayname');
        $('#country-code').text(displayname);
        $('.choose-country-code').text('+' + code);
        $('.country-code').val(name);
        $('.mobile-code').val(code);
        $.popupModalClose();
        return false;
    });


    /* 若要返回投票数字，则约定html结构。指定样式vote-item-num，指定data-id为投票id
     * <span class="vote-item-num" data-id="458"></span> 
     */
    if ($('.vote-item-num').size() > 0) {
        voteCount();
    }

    /*QQ客服*/
    //var qq=[{'qq':'1542167026','name':'客服--小影'},{'qq':'2118549979','name':'客服--小晴'},{'qq':'2109197928','name':'客服--小云'},{'qq':'2253725288','name':'客服--小若'}/*,{'qq':'1417277836','name':'客服--小雨'}*/];
    //$.utilQQOnline('.qq-list',qq);


    window.onhashchange = function () {
        var hashTag = window.location.hash.replace('#', '');
        if (hashTag == 'login') {
            userLogin();
        }
        if (hashTag == '') {
            closePopModal();
        }
    }

    /*活动列表*/
    if ($(".promotion-productlist-list").size() > 0) {
        $(".promotion-productlist-list").each(function () {
            var obj = $(this);
            var url = obj.attr('data-url');
            if (url) {
                $.get(url, function (data) {
                    obj.append(data);
                    obj.find("img").unveil(350)
                });
            }
        })
    }


    /*showroom 回到顶部*/
    $(document).on("scroll", function () {
        var scroll_top = $(window).scrollTop();
        if (scroll_top > 500) {
            $('.goup').css('display', 'block');
        } else {
            $('.goup').css('display', 'none');
        }
    });
    $('.goup').on('touchstart', function () {
        $('html, body').animate({scrollTop: 0}, 400);
    });

    /*报名/领取优惠券  用户信息完善*/
    if ($('.activity-form-d2c').size() > 0) {
        $.ajax({
            url: '/member/check',//检测地址
            type: 'get',
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                    var mobile = data.result.datas.mobile;
                    if (mobile != "") {
                        $('.use_mobile').val(mobile).attr("readonly", "readonly");
                    }
                }
            }
        });
    }

    $('.activity-form-d2c').submit(function () {
        //判断mobile
        var mobile = $('.use_mobile').val();
        if (mobile != '') {
            var form = $(this);
            $('body').data('activity_form', form.serializeObject());
            $.ajax({
                url: '/member/check',  //检测地址
                type: 'get',
                data: form.serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {//注册过
                        activityExcute(form.serializeObject());
                    } else {
                        activityRegister(form.serializeObject());
                    }
                }
            });
        }
        return false;
    });
});
//手机活动页图片宽度处理
$(window).load(function () {
    var path = location.pathname;
    if (path.indexOf("/page") >= 0) {
        var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
        $('img').each(function () {
            if ($(this).width() > width) {
                $(this).css('width', '100%');
            }
        })
    }
})


String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}


Date.prototype.pattern = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时
        "H+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    var week = {
        "0": "\u65e5",
        "1": "\u4e00",
        "2": "\u4e8c",
        "3": "\u4e09",
        "4": "\u56db",
        "5": "\u4e94",
        "6": "\u516d"
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(E+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}


function isEmpty(value) {
    if (value != undefined && value != null && value != "")
        return false;
    else
        return true;
}

/*判断是不是微信浏览器*/
function isweixin() {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

function isIE() { //ie?  
    if (!!window.ActiveXObject || "ActiveXObject" in window)
        return true;
    else
        return false;
}

function compaerDate(d1, d2) {


    var date1 = new Date(d1)
    var date2 = new Date(d2)

    if (Date.parse(date1) > Date.parse(date2)) {
        return 1;
    } else if (Date.parse(date1) == Date.parse(date2)) {
        return 0;
    } else if (Date.parse(date1) < Date.parse(date2)) {
        return -1;
    }
}

/* 微信小程序跳转配置*/
function parseUrl(url) {
    //判断URL是否是完整的http链接
    if (url.indexOf('http') != -1) {
        var parse_url = /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        var names = ['url', 'scheme', 'slash', 'host', 'port', 'path', 'query', 'hash'];
    } else {
        var parse_url = /^(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        var names = ["url", "path", "query", "hash"];
    }
    var result = parse_url.exec(url);

    var res = {};
    for (var i = 0; i < names.length; i++) {
        res[names[i]] = result[i];
    }
    return res;
}

function navigateTo(url) {//解析规则
    if (url.indexOf('http://m.d2cmall.com') != -1) url = url.replace('http://m.d2cmall.com', '');
    var data = this.parseUrl(url);
    var returnUrl = '';
    if (/product\/[0-9]/.test(data.path)) {//商品详情
        var product_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/product/detail?id=' + product_id;
    } else if (data.path.indexOf('product/list') != -1) {//商品详情
        returnUrl = '/pages/product/list' + (data.query ? '?' + data.query : '');
    } else if (data.path.indexOf('product/detail') != -1) {//商品内容
        returnUrl = '/pages/webview/direct?url=' + encodeURIComponent(url);
    } else if (/productComb\/[0-9]/.test(data.path)) {//组合商品
        var combined_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/product/combined?id=' + combined_id;
    } else if (data.path.indexOf('designer/list') != -1) {//设计师列表
        returnUrl = '/pages/brand/list' + (data.query ? '?' + data.query : '');
    } else if (/showroom\/designer\/[0-9]/.test(data.path)) {//品牌详情
        var brand_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/brand/detail?id=' + brand_id;
    } else if (data.path.indexOf('flashpromotion/product/session') != -1) {//限时购
        returnUrl = '/pages/flash/session?type=product';
    } else if (data.path.indexOf('flashpromotion/brand/session') != -1) {//限时购
        returnUrl = '/pages/flash/brand?type=brand';
    } else if (/promotion\/[0-9]/.test(data.path)) {//活动
        var promotion_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/promotion/detail?id=' + promotion_id;
    } else if (data.path.indexOf('promotion/items') != -1) {//活动列表
        console.log(data.query);
        if (data.query.indexOf('sectionId=') != -1) {
            returnUrl = '/pages/index/section' + (data.query ? '?' + data.query : '');
        } else {
            returnUrl = '/pages/promotion/list' + (data.query ? '?' + data.query : '');
        }
    } else if (data.path.indexOf('collage/products/list') != -1) {//拼团列表
        returnUrl = '/pages/groupon/list';
    } else if (data.path.indexOf('mycollage/list') != -1) {//我的拼团
        returnUrl = '/pages/groupon/list?type=mine';
    } else if (/collage\/[0-9]/.test(data.path)) {//拼团详情
        var collage_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/groupon/product?id=' + collage_id;
    } else if (data.path.indexOf('coupondef/buynow') != -1) {//购买优惠券
        returnUrl = '/pages/payment/coupon?' + data.query;
    } else if (/coupon\/batch\/[0-9]/.test(data.path)) {//领取优惠券组
        var coupon_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/coupon/receive?type=batch&id=' + coupon_id;
    } else if (/shareredpackets\/[0-9]/.test(data.path)) {//红包裂变
        var packet_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/sharepack/detail?id=' + packet_id;
    } else if (/coupon\/[0-9]/.test(data.path)) {//领取单张优惠券
        var coupon_id = data.path.substr(data.path.lastIndexOf('/') + 1)
        returnUrl = '/pages/coupon/receive?type=single&id=' + coupon_id;
    } else if (data.path.indexOf('membershare/list') != -1) {//买家秀列表
        returnUrl = '/pages/share/list' + (data.query ? '?' + data.query : '');
    } else if (data.path.indexOf('page/physicalStores') != -1) {//实体店
        returnUrl = '/pages/store/list';
    } else if (data.path.indexOf('bargain/promotion/list') != -1) {//砍价
        returnUrl = '/pages/bargain/index';
    } else {//其他跳转到webview
        returnUrl = '/pages/webview/direct?url=' + encodeURIComponent(url);
    }
    wx.miniProgram.navigateTo({
        url: returnUrl,
    })
}

if (isWeChat) {
    //alert('判断在微信中');
    wx.ready(function () {
        //alert('判断启动微信JS')
        if (window.__wxjs_environment === 'miniprogram') {
            $('body').append('<div class="background-fixed"></div>');
            $(document).on('click', 'a[href]', function () {
                var href = $(this).attr('href');
                if (href.indexOf('javascript:') == -1 && href.indexOf('#') == -1 && $(this).attr('target') != 'self') {
                    navigateTo(href);
                    return false;
                }
            });
            if (_partnerId != '' && location.href.indexOf('page') != -1 || location.href.indexOf('promotion') != -1) {
                $('body').append('<div style="position:fixed;top:15px;right:0;z-index:999;">\
					<div class="copy-button" style="cursor:pointer;padding:.6em 1em;margin-top:1em;border-radius:1.5em 0 0 1.5em;background:rgba(0,0,0,.7);color:#FFF;font-size:.8em;">复制链接</div>\
				</div>');
                var clipboard = new ClipboardJS('.copy-button', {
                    text: function () {
                        var url = location.href;
                        if (url.indexOf('parent_id') == -1) {
                            url = (url.indexOf('?') != -1) ? (url + '&parent_id=' + _partnerId) : (url + '?parent_id=' + _partnerId);
                        }
                        return url;
                    }
                });
                clipboard.on('success', function (e) {
                    toast({'position': 'center', 'message': '地址复制成功'});
                });
            }
        }
    })
}


//跨境商品弹窗
function popupHtml() {
    var html = ['<div class="popup-content">',
        '<div class="qr-box">',
        '<p class="box-title">该商品暂只支持APP和小程序购买',
        '<div style="width:9.375em;margin:0 auto;"><img src="//static.d2c.cn//img/topic/180607/cp/images/pic_wechatcode02@3x.png" width="100%"></div>',
        '<p class="box-cont-tip">扫码进入D2C小程序<br/>或</p>',
        '<button class="box-button">下载D2C APP</button>',
        '<div class="qr-close"><img src="//static.d2c.cn//img/topic/180607/cp/images/icon_closed@3x.png" width="100%"></div>',
        '</div>',
        '</div>'].join("");
    popupModal({content: html});
    $('#popup-modal-outer').css('background', 'rgba(0,0,0,.5)');
    $('.box-button').on(click_type, function () {
        if (iOS) {
            location.href = "https://itunes.apple.com/us/app/d2c-全球好设计/id980211165?l=zh&ls=1&mt=8 ";
        } else if (isAndroid) {
            location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.d2cmall.buyer";
        }
    });
    $('.qr-close').on(click_type, function () {
        popupModalClose()
    });
    return false;
}





