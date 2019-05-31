// var static_base='http://static.d2c.cn/common/nm/js/utils',timer;
var static_base = '/static/nm/js/utils/', timer;
window.uniqueId = 0;
(function ($) {
    //遮罩层基本结构
    $.utilBaseModal = {
        create: function (option) {
            var obj = $(this);
            var options = $.extend({
                type: 'pop',
                inAnimate: '',
                outAnimate: '',
                backClose: false,
                scrollLock: false,
                canClose: true,
                remove: true
            }, option);
            if (options.remove == false) {
                if ($('#' + options.type + '-modal-outer').size() > 0) {
                    $('#' + options.type + '-modal-mask').show();
                    $('#' + options.type + '-modal-outer').show();
                    options.inAnimate != '' && $('#' + options.type + '-modal-outer').addClass(options.inAnimate);
                }
            }
            if ($('#' + options.type + '-modal-outer').size() <= 0) {
                var html = '';
                html += '<div id="' + options.type + '-modal-mask" class="modal-mask"></div>\
    			<div id="' + options.type + '-modal-outer" class="modal-outer animated ' + (options.inAnimate != '' ? options.inAnimate : '') + '">\
    			<div id="' + options.type + '-modal-inner" class="modal-inner">';
                if (options.canClose == true) {
                    html += '<div id="' + options.type + '-modal-title" class="modal-title">\
    				<a href="javascript:" id="' + options.type + '-modal-remove" class="modal-remove"><i class="icon icon-close"></i></a>\
    				</div>';
                }
                html += '<div id="' + options.type + '-modal-content" class="modal-content"></div>\
    			</div>\
    			</div>';
                $('body').append(html);
            }
            if (options.scrollLock == true) {
                $('body').data('scroll_position', $(window).scrollTop());
                $('body').css({'overflow': 'hidden', 'height': $(window).height()});
                $(window).scrollTop(0);
            }

            $('#' + options.type + '-modal-remove').click(function () {
                $.utilBaseModal.remove(options.type, options);
                return false;
            });
            console.log(options.backClose);

            if (options.backClose == true) {
                $('#' + options.type + '-modal-mask').on('click', function () {
                    $('#' + options.type + '-modal-remove').trigger('click');
                    return false;
                });
            }

        },
        remove: function (type, options) {
            if (options == undefined || options == '') {
                options = {inAnimate: '', outAnimate: '', scrollLock: ''};
            }

            $('#' + type + '-modal-remove').unbind("click");
            if (options.inAnimate != '') {
                $('#' + type + '-modal-outer').removeClass(options.inAnimate);
            }
            if (options.outAnimate != '') {
                $('#' + type + '-modal-outer').addClass(options.outAnimate);
                timer = setTimeout(function () {
                    $('#' + type + '-modal-outer').removeClass(options.inAnimate + ' ' + options.outAnimate);
                    options.remove == true ? $('#' + type + '-modal-outer').remove() : $('#' + type + '-modal-outer').hide();
                }, 200);
            } else {
                options.remove == true ? $('#' + type + '-modal-outer').remove() : $('#' + type + '-modal-outer').hide();
            }
            options.remove == true ? $('#' + type + '-modal-mask').remove() : $('#' + type + '-modal-mask').hide();
            if (options.scrollLock == true) {
                $('body').css({'overflow': 'auto', 'height': 'auto'});
                var scroll_position = $('body').data('scroll_position');
                scroll_position && $(window).scrollTop(scroll_position);
            }
            return false;
        }
    }
    $.utilAlertModal = {
        ok_button: '确 定',
        cancel_button: '取 消',
        type: 'alert',
        download_button: '立即下载',
        wait_button: '稍后下载',
        use_button: '立即使用',
        nouse_button: '稍后使用',
        alert: function (message, callback) {
            $.utilAlertModal.show(message, 'alert', function (result) {
                if (callback) callback(result);
            });
        },
        confirm: function (message, callback) {
            $.utilAlertModal.show(message, 'confirm', function (result) {
                if (callback) callback(result);
            });
        },
        tip: function (message) {
            $.utilAlertModal.show(message, 'tip');
        },
        download: function (message, callback) {
            $.utilAlertModal.show(message, 'download', function (result) {
                if (callback) callback(result);
            });
        },
        use: function (message, callback) {
            $.utilAlertModal.show(message, 'use', function (result) {
                if (callback) callback(result);
            });
        },
        remove: function () {
            $('#' + $.utilAlertModal.type + '-modal-remove').trigger('click');
        },
        show: function (msg, mtype, callback) {
            $.utilBaseModal.create({
                type: $.utilAlertModal.type
            });
            var html = '<div class="alert-modal-fixed"><div class="alert-modal">\
				<p class="alert-title">系统提示</p>\
				<p class="alert-msg">' + msg + '</p>\
				<p class="alert-buttons"></p>\
				</div></div>';
            $('#' + $.utilAlertModal.type + '-modal-content').html(html);
            switch (mtype) {
                case 'alert':
                    $(".alert-buttons").html('<button type="button" class="alert-ok" style="width:100%;">' + $.utilAlertModal.ok_button + '</button>');
                    $(".alert-ok").on('click', function () {
                        $.utilAlertModal.remove();
                        callback(true);
                        return false;
                    });
                    break;
                case 'confirm':
                    $(".alert-buttons").html('<button type="button" class="alert-cancel" style="width:50%;">' + $.utilAlertModal.cancel_button + '</button><button type="button" class="alert-ok" style="width:50%;">' + $.utilAlertModal.ok_button + '</button>');
                    $(".alert-ok").on('click', function () {
                        $.utilAlertModal.remove();
                        if (callback) callback(true);
                        return false;
                    });
                    $(".alert-cancel").on('click', function () {
                        $.utilAlertModal.remove();
                        if (callback) callback(false);
                        return false;
                    });
                    break;
                case 'tip':
                    $(".alert-buttons").remove();
                    $(".alert-msg").css('paddingBottom', '1.5em');
                    break;
                case 'download':
                    $(".alert-buttons").html('<button type="button" class="wait-download" style="width:50%;">' + $.utilAlertModal.wait_button + '</button><button type="button" class="download-button" style="width:50%;"><a href="http://t.cn/RLstL36" style="color:#FFF">' + $.utilAlertModal.download_button + '</a></button>');
                    $(".wait-download").on('click', function () {
                        $.utilAlertModal.remove();
                        if (callback) callback(false);
                        return false;
                    });
                    break;
                case 'use':
                    $(".alert-buttons").html('<button type="button" class="alert-ok" style="width:50%"><a href="/page/xinyi">' + $.utilAlertModal.use_button + '</a></button><button type="button" class="alert-cancel" style="width:50%">' + $.utilAlertModal.nouse_button + '</button>');
                    $(".alert-cancel").on('click', function () {
                        $.utilAlertModal.remove();
                        if (callback) callback(true);
                        return false;
                    });
                    break;
            }
            return false;
        }
    }
    // Shortuct functions
    jAlert = function (message, callback) {
        $.utilAlertModal.alert(message, callback);
    },
        jConfirm = function (message, callback) {
            $.utilAlertModal.confirm(message, callback);
        },
        jTip = function (message) {
            $.utilAlertModal.tip(message);
        },
        jDownload = function (message, callback) {
            $.utilAlertModal.download(message, callback);
        },
        jGoto = function (message, callback) {
            $.utilAlertModal.use(message, callback);
        },

        //普通弹出层
        $.popModal = function (option) {
            //var obj=$(this);
            var type = "pop";
            var options = $.extend({
                url: null,
                content: null,
                inAnimate: 'slideInUp',
                outAnimate: 'slideOutDown',
            }, option);

            $.utilBaseModal.create({
                type: type,
                inAnimate: options.inAnimate,
                outAnimate: options.outAnimate,
                scrollLock: true,
            });
            if (options.url) {
                $.get(options.url, function (data) {
                    $('#' + type + '-modal-content').html(data);
                });
            }
            if (options.content) {
                $('#' + type + '-modal-content').html(options.content);
            }
        },
        $.popModalClose = function () {
            $('#pop-modal-remove').trigger('click');
            return false;
        },
        //弹出层中的弹出层
        $.popupModal = function (option) {
            //var obj=$(this);
            var type = "popup";
            var options = $.extend({
                url: null,
                content: null,
                canClose: true,
                backClose: false,
                inAnimate: 'fadeIn',
                outAnimate: 'fadeOut',
            }, option);

            $.utilBaseModal.create({
                type: type,
                canClose: options.canClose,
                backClose: options.backClose,
                inAnimate: options.inAnimate,
                outAnimate: options.outAnimate,
                scrollLock: options.scrollLock
            });
            if (options.url) {
                $.get(options.url, function (data) {
                    $('#' + type + '-modal-content').html(data);
                });
            }
            if (options.content) {
                $('#' + type + '-modal-content').html(options.content);
            }
        },
        $.popupModalClose = function () {
            $('#popup-modal-remove').trigger('click');
            return false;
        },
        popupModal = function (option) {
            $.popupModal(option);
        },
        popupModalClose = function () {
            $.utilBaseModal.remove('popup', {'remove': true, 'scrollLock': false});
            return false;
        },
        $.flashTip = function (option) {
            clearTimeout(timer);
            var options = $.extend({
                position: 'top',//top,center,bottom
                type: 'success',//success,warning,error,normal
                message: '',
                time: 3000
            }, option);
            if ($('.flash-tips').size() > 0) {
                $('.flash-tips').attr('class', 'flash-tips flash-tips-type-' + options.type).text(options.message);
            } else {
                $('body').append('<div class="flash-tips flash-tips-type-' + options.type + '">' + options.message + '</div>');
            }
            $('.flash-tips').addClass('flash-tips-pos-' + options.position).show();
            timer = setTimeout(function () {
                $('.flash-tips').fadeOut();
            }, options.time);
        },
        toast = function (option) {
            $.flashTip(option);
        },
        //jsbridge(IOS Android 独立的写法)

        $.D2CMerchantBridge = function (data, hanlename) {
            //终端判断
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if (isAndroid && app_client) {
                //call android native method
                try {
                    window.WebViewJavascriptBridge.callHandler(
                        'd2cinit'
                        , data
                        , function (responseData) {
                        }
                    );
                } catch (err) {
                    console.log(err)
                }

                function connectWebViewJavascriptBridge(callback) {
                    if (window.WebViewJavascriptBridge) {
                        callback(WebViewJavascriptBridge)
                    } else {
                        document.addEventListener(
                            'WebViewJavascriptBridgeReady'
                            , function () {
                                callback(WebViewJavascriptBridge)
                            },
                            false
                        );
                    }
                }

                connectWebViewJavascriptBridge(function (bridge) {
                    if (window.uniqueId == 0) {
                        window.uniqueId = 1;
                        bridge.init(function (message, responseCallback) {
                            // responseCallback(data);

                        });
                    }
                    //注册JS回调方法
                    bridge.registerHandler('noticeback', function (data, responseCallback) {
                        console.log('回调成功', data)
                        window.respons = data
                    });
                    bridge.registerHandler('noticeSuback', function (data, responseCallback) {
                        console.log('回调成功', data)
                        window.respons = data
                    });
                })
            } else if (isiOS && app_client) {
                window.webkit.messageHandlers.d2cinit.postMessage(data);
            }
        },

        $.fn.utilSetArea = function () {
            var province_json = static_base + "/json-array-of-province.js";
            var city_json = static_base + "/json-array-of-city.js";
            var district_json = static_base + "/json-array-of-district.js";
            var default_province = '<option value="">选择省份</option>';
            var default_city = '<option value="">选择城市</option>';
            var default_district = '<option value="">选择区县</option>';
            var province = new Array();
            var city = new Array();
            var district = new Array();
            var e = $(this);
            var array;
            var type = e.find('.province').size() > 0 ? 1 : 0;//判断是转换地址还是创建下拉表
            var obj_p = e.find('.province');
            var obj_c = e.find('.city');
            var obj_d = e.find('.district');

            if (province.length == 0 || city.length == 0 || district.length == 0) {
                $.getJSON(province_json, function (data) {
                    $.each(data, function (i, d) {
                        province[d.code] = d.name;
                    });
                    build_list(province, obj_p.attr('rel'), obj_p, null, 0, default_province);
                });
                setTimeout(function () {
                    $.getJSON(city_json, function (data) {
                        $.each(data, function (i, d) {
                            city[d.code] = d.name;
                        });
                        build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, default_city);
                    });
                }, 100);

                setTimeout(function () {
                    $.getJSON(district_json, function (data) {
                        $.each(data, function (i, d) {
                            district[d.code] = d.name;
                        });
                        build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
                    });
                }, 200);


                obj_p.change(function () {
                    if ($(this).val() == '') {
                        build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, default_city);
                        build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
                    } else {
                        build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, '');
                        build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, '');
                    }
                });
                obj_c.change(function () {
                    build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, '');
                });
            }


            function build_list(data, value, obj, pre_obj, strlen, def) {
                var selected;
                var option = '';

                for (var i in data) {
                    selected = '';
                    if (strlen === 0) {
                        if (value !== "" && value !== 0 && i === value) {
                            selected = ' selected';
                            value = '';
                        }
                        option += '<option value="' + i + '"' + selected + '>' + data[i] + '</option>';
                    } else {
                        var p = pre_obj.val();
                        if (p.substring(0, strlen) === i.substring(0, strlen)) {
                            if (value !== "" && value !== 0 && i === value) {
                                selected = ' selected';
                                value = '';
                            }
                            option += '<option value="' + i + '"' + selected + '>' + data[i] + '</option>';
                        }
                    }
                }
                obj.html(def + option);
            }
        },

        $.fn.utilScrollLoad = function (source) {
            var element = $(this),
                can_load = true,
                url = element.attr('data-url'),
                target = element.attr('data-target'),
                template_id = element.attr('template-id');
            var conn_str = (url.indexOf('?') != -1) ? '&' : '?';
            $(window).off('scroll').bind("scroll", function () {
                var page = parseInt(element.attr('data-page')),
                    total = parseInt(element.attr('data-total'));
                if (page < total) {
                    if ((parseInt($(window).scrollTop()) + parseInt($(window).height())) > $(document).height() - 100) {
                        loadMorePage();
                    }
                } else {
                    can_load = false;
                    element.html('已经全部加载完了').css('color', '#CCC');
                }
            });
            if (element.offset().top < $(document).height() - 100) {
                loadMorePage();
            }
            element.click(function () {
                loadMorePage();
            });

            function loadMorePage() {
                var page = parseInt(element.attr('data-page')),
                    total = parseInt(element.attr('data-total'));
                if (can_load == true && page < total) {
                    element.html('共' + total + '页 正在加载第' + (page + 1) + '页...').css('color', '');
                    $.ajax({
                        url: url + conn_str + 'p=' + (page + 1) + '&' + new Date().getTime(),
                        dataType: 'json',
                        type: 'get',
                        beforeSend: function () {
                            can_load = false;
                        },
                        success: function (result) {
                            if (result.pager) {
                                var res = result;
                                if (res.pager.next) {
                                    can_load = true;
                                } else {
                                    can_load = false;
                                }
                                if (res.pager.list.length > 0) {
                                    var html = '';
                                    template.config("escape", false);
                                    if (template_id) {
                                        html = template(template_id, res);
                                    } else {
                                        var render = template.compile(source);
                                        html = render(res);
                                    }
                                    $('#' + target).append(html);
                                    $('#' + target + ' img').unveil(300);
                                    element.attr('data-page', page + 1).text('点击加载更多').css('color', '');
                                    element.attr('data-total', res.pageCount)

                                } else {
                                    can_load = false;
                                    element.html('已经全部加载完了').css('color', '#CCC');
                                }
                            } else {
                                var res = result.products ? result.products : result.list;
                                if (res.next) {
                                    can_load = true;
                                } else {
                                    can_load = false;
                                }
                                if (res.list.length > 0) {
                                    var html = '';
                                    template.config("escape", false);
                                    if (template_id) {
                                        html = template(template_id, res);
                                    } else {
                                        var render = template.compile(source);
                                        html = render(res);
                                    }
                                    $('#' + target).append(html);
                                    $('#' + target + ' img').unveil(300);
                                    element.attr('data-page', page + 1).text('点击加载更多').css('color', '');
                                    element.attr('data-total', res.pageCount)
                                } else {
                                    can_load = false;
                                    element.html('已经全部加载完了').css('color', '#CCC');
                                }
                            }
                        }
                    });
                }
            }
        },
        /*
         * 表单校验 utilValidateForm
         * <form name="" class=validate-form">
         * <input type="text" name="" id="name"><span class="tip-validate" data-target="id" data-rule="" data-function="function name"></span>
         * </form>
         * 在tip-validate中设置属性data-target(验证的目标ID，可以多个目标，使用|间隔或&间隔)
         * 设置data-rule，使用内置的方法来判断，默认为empty，如果多个rule可以使用，隔开，将按照顺序进行验证，内置rule有
         * mobile 验证手机     email 验证邮箱 number 只能输入数字   empty 验证是否为空  length|0|5 判断字符数 第一个数字是最小字符数，第二个数字是最多字符数
         * 当data-target有多个值且dat-rule不为empty，则所有的data-target校验全部使用data-rule去校验
         * 设置compare-with,与其值做比较。data-target只能为单一值
         * 设置data-function，使用单独的函数来判断，无需指定target。通常用于判断条件复杂，需要另外写函数来判断，只需写函数名称，不需要带();
         * data-function示例
         * var functionName=function(){
         * 	if (){
         * 		return '错误信息提示';
         * 	}else{
         * 		return true;
         * 	}
         * }
         */
        $.fn.utilValidateForm = function () {
            var obj_form = $(this), errors = 0;
            if (obj_form.find('.validate').size() > 0) {
                $.each(obj_form.find('.validate'), function (i, o) {
                    var message, error = 0;
                    var title = $(o).attr('title'),
                        validate_rule = $(o).attr('data-rule'),
                        compare_with = $(o).attr('compare-with'),
                        min_length = $(o).attr('min-length'),
                        max_length = $(o).attr('max-length'),
                        validate_function = $(o).attr('data-function');
                    if (min_length != '' && min_length != undefined) {
                        min_length = parseInt(min_length);
                    } else {
                        min_length = 0;
                    }
                    if (max_length != '' && max_length != undefined) {
                        max_length = parseInt(max_length);
                    } else {
                        max_length = 0;
                    }
                    if (validate_rule == undefined && validate_function == undefined) {
                        validate_rule = 'empty';
                    }
                    if (validate_rule == 'empty') {
                        if ($(o).val() == '') {
                            error++;
                            $(o).focus();
                            message = title + '不能为空';
                        }
                    }
                    if (min_length > 0) {
                        if ($(o).val() != '' && $(o).val().length < min_length) {
                            error++;
                            $(o).focus();
                            message = title + '长度不能小于' + min_length + '位';
                        }
                    }
                    if (validate_rule == 'byte') {
                        var nval = $(o).val().replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");
                        $(o).val(nval)
                        if ($.utilValidateByteLength(nval) <= 0 || $.utilValidateByteLength(nval) > 30) {
                            error++;
                            $(o).focus();
                            message = title + '长度在15个汉字或者30个字母之内';
                        }
                    }
                    if (validate_rule == 'num') {
                        var val = $(o).val();
                        if (!val.match(/^[\+\-]?\d*?\.?\d*?$/)) {
                            error++;
                            $(o).focus();
                            message = title + '必须为数字';
                        }
                    }
                    if (max_length > 0) {
                        if ($(o).val().length > max_length) {
                            error++;
                            $(o).focus();
                            message = title + '长度不能大于' + max_length + '位';
                        }
                    }

                    if (validate_rule == 'mobile') {
                        if (!$(o).utilValidateMobile()) {
                            error++;
                            $(o).focus();
                            message = '请输入正确手机号码';
                        }
                    }
                    if (validate_rule == 'email') {
                        if (!$(o).utilValidateEmail()) {
                            error++;
                            $(o).focus();
                            message = '请输入正确邮箱地址';
                        }
                    }
                    if (compare_with) {
                        if ($(o).val() != $('#' + compare_with).val()) {
                            error++;
                            $(o).focus();
                            message = '两次输入的密码不一致';
                        }
                    }

                    if (validate_function) {//判断是否存在自定义判断函数
                        try {
                            if (typeof (eval(validate_function)) == "function") {
                                if (eval(validate_function + '()') != true) {
                                    error++;
                                    message = eval(validate_function + '()');
                                }
                            }
                            ;
                        } catch (e) {
                            alert('不存在' + validate_function + '这个函数');
                            return false;
                        }
                    }
                    if (error > 0) {
                        errors++;
                        $.flashTip({position: 'center', type: 'error', message: message});
                        return false;
                    }
                });
                if (errors > 0) {
                    return false;
                } else {
                    return true;
                }

            } else {
                return true;
            }
        },
        /* 检测手机号
         * 用法
         * <input type="text" name="mobile" class="mobile" />
         * $('.mobile').utilValidateMobile();
         */

        $.fn.utilValidateMobile = function () {
            $(this).css("ime-mode", "disabled");
            //var re_mobile = /^[1][3456789]\d{9}$/;
            var re_mobile = /^[0-9]*$/;
            if (re_mobile.test($(this).val())) {
                return true;
            } else {
                return false;
            }
        },
        /* 检测字符串字节长度
         * 用法
         */
        $.utilValidateByteLength = function (val) {
            var Zhlength = 0;// 全角
            var Enlength = 0;// 半角
            val = val.replace(/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F]/g, "");/*去除emoji表情*/
            for (var i = 0; i < val.length; i++) {
                if (val.substring(i, i + 1).match(/[^\x00-\xff]/ig) != null)
                    Zhlength += 1;
                else
                    Enlength += 1;
            }
            // 返回当前字符串字节长度
            return (Zhlength * 2) + Enlength;
        },
        /* 检测邮箱
         * 用法
         * <input type="text" name="email" class="email" />
         * $('.email').utilValidateEmail();
         */
        $.fn.utilValidateEmail = function () {
            $(this).css("ime-mode", "disabled");
            var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if (re_email.test($(this).val())) {
                return true;
            } else {
                return false;
            }
        },
        $.utilFormatNumber = function (num, n) {
            var len = num.toString().length;
            while (len < n) {
                num = "0" + num;
                len++;
            }
            return num;
        },
        $.utilFormatCurrency = function (num) {
            if (num) {
                num = num.toString().replace(/\$|\,/g, '');
                if (isNaN(num))
                    num = "0";
                sign = (num == (num = Math.abs(num)));
                num = Math.floor(num * 100 + 0.50000000001);
                cents = num % 100;
                num = Math.floor(num / 100).toString();
                if (cents < 10)
                    cents = "0" + cents;
                for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                    num = num.substring(0, num.length - (4 * i + 3)) + ',' +
                        num.substring(num.length - (4 * i + 3));
                return (((sign) ? '' : '-') + num + '.' + cents);
            } else {
                return null;
            }
        },
        /* 无缝滚动
        * 用法
        * <div id="Marquee"><ul><li><div>内容</div></li></ul> </div>
        * $('#Marquee').jcMarquees({ 'marquee':'x','margin_right':'50px','speed':20 });
        */
        $.fn.jcMarquees = function (options) {
            var defaults = {
                'marquee': 'x',
                'margin_bottom': '0',
                'margin_right': '0',
                'speed': '10'
            };
            var options = $.extend(defaults, options);
            return this.each(function () {
                var $marquee = $(this),
                    $marquee_scroll = $marquee.children('ul'),
                    $marquee_width = $marquee.parent('#Marquee').width()
                $marquee_scroll.append("<li class='clone'>" + "</li>");
                $marquee_scroll.find('li').eq(0).children().clone().appendTo('.clone');
                var $marquee_left = $marquee_scroll.find('li');
                if (options.marquee == 'x') {
                    var x = 0;
                    $marquee_scroll.css('width', '4000%');
                    $marquee_left.find('div').css({'margin-right': options.margin_right});

                    function Marquee_x() {
                        $marquee.scrollLeft(++x);
                        _margin = parseInt($marquee_left.find('div').css('margin-right'));
                        if (x == $marquee_left.width()) {
                            x = 0
                        }
                        ;
                    };
                    var MyMar = setInterval(Marquee_x, options.speed);
                }
                if (options.marquee == 'y') {
                    var y = 0;
                    $marquee_scroll.css('height', '4000%');
                    $marquee_left.find('div').css('margin-bottom', options.margin_bottom);

                    function Marquee_y() {
                        $marquee.scrollTop(++y);

                        _margin = parseInt($marquee_left.find('div').css('margin-bottom'));
                        if (y == $marquee_left.height() + _margin) {
                            y = 0
                        }
                        ;
                    };
                    var MyMar = setInterval(Marquee_y, options.speed);
                }
                ;
            });
        }
    //倒计时
    $.fn.countdown = function () {
        var obj = $(this);
        var timer;
        var type = obj.attr('data-type');
        var defined = obj.attr('data-defined');
        var func = obj.attr('data-function');
        var must = obj.attr('data-musttime');
        var startTime = (new Date(obj.attr("data-startTime"))).getTime();
        var endTime = (new Date(obj.attr("data-endTime"))).getTime();
        var nowTime = (new Date(_server_time)).getTime();
        var st = startTime - nowTime, et = endTime - nowTime;

        var timestr;
        if (type == undefined) {
            type = (st > 0) ? 'tostart' : 'toend';
        }
        must = must || '';
        var decrease = 1000;
        if (defined == 'millisecond') {//如果定义的是毫秒则计时器设为100毫秒一次
            decrease = 100;
        }

        timestr = (st > 0) ? st : et;

        clearInterval(timer);
        output();
        timer = setInterval(function () {
            output();
            if (timestr < 0) {
                clearInterval(timer);
            }
            timestr -= decrease;
        }, decrease);

        function output() {
            var countdown_template, time_tip;
            if (type == 'toend') {
                countdown_template = '<span class="normal">还剩~timestamp~结束</span>';
                time_tip = '已经结束';
            } else if (type == 'tostart') {
                countdown_template = '<span class="normal">还有~timestamp~开始</span>';
                time_tip = '已经结束';
            }
            var time = expireTime(timestr, must);
            if (timestr <= 0) {
                if (typeof (eval(func)) == 'function') {
                    obj.html(eval(func));
                } else {
                    obj.html(time_tip);
                }
            } else {
                if (type == 'split-time') {
                    obj.find('.day').html(time['day']);
                    obj.find('.hour').html(time['hour']);
                    obj.find('.minute').html(time['minute']);
                    obj.find('.second').html(time['second']);
                    obj.find('.millisecond').html(time['millisecond']);
                } else {
                    var timestamp = '';
                    if (time['day'] > 0) {
                        timestamp += '<strong>' + time['day'] + '</strong>天';
                    }
                    if (time['hour'] > 0) {
                        timestamp += '<strong>' + time['hour'] + '</strong>小时';
                    }
                    timestamp += '<strong>' + time['minute'] + '</strong>分<strong>' + time['second'] + '</strong>秒';
                    if (defined == 'millisecond') {
                        timestamp += '<strong>' + time['millisecond'] + '</strong>';
                    }
                    var str = countdown_template.replace('~timestamp~', timestamp);
                    obj.html(str);
                }

            }
        }

        function expireTime(time, must) {
            var array = new Array();
            if (time > 0) {
                var tDay = Math.floor(time / 86400000);
                if (must) {
                    tHour = Math.floor(time / 3600000);
                } else {
                    tHour = Math.floor((time / 3600000) % 24);
                }
                if (tHour < 10) {
                    tHour = "0" + tHour;
                }
                tMinute = Math.floor((time / 60000) % 60);
                if (tMinute < 10) {
                    tMinute = "0" + tMinute;
                }
                tSecond = Math.floor((time / 1000) % 60);
                if (tSecond < 10) {
                    tSecond = "0" + tSecond;
                }
                tMillisecond = Math.floor((time / 100) % 10);
                array['day'] = tDay;
                array['hour'] = tHour;
                array['minute'] = tMinute;
                array['second'] = tSecond;
                array['millisecond'] = tMillisecond;
            } else {
                array['day'] = '00';
                array['hour'] = '00';
                array['minute'] = '00';
                array['second'] = '00';
                array['millisecond'] = '0';
            }
            return array;
        }
    },
        $.fn.utilRating = function () {
            $.each($(this), function (x, y) {
                var obj = $(y);
                var num = obj.find('.w-star').size();
                var input_num = parseInt(obj.find('input').val());
                if (input_num == NaN) input_num = 0;
                if (input_num > 0) {
                    for (var n = 4; n >= (5 - input_num); n--) {
                        obj.find('.w-star:eq(' + n + ')').addClass('checked');
                    }
                }
                obj.find('.w-star').click(function () {
                    var i = $(this).index();
                    var obj_p = $(this).parent();
                    $(this).siblings('input').val(5 - i);
                    obj_p.find('.w-star').removeClass('checked');
                    for (var m = 4; m >= i; m--) {
                        obj_p.find('.w-star:eq(' + m + ')').addClass('checked');
                    }
                });
            });
        },
        $.fn.serializeObject = function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name] !== undefined) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
})(jQuery);

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
} 