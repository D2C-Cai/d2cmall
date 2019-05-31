//var static_base='//static.d2c.cn/common/nc/js/utils';
var static_base = '/static/nc/js/utils/';
(function ($) {

    var ie6 = ($.browser.msie && ($.browser.version == "6.0") && !$.support.style);

    //遮罩层基本结构
    $.utilBaseModal = {
        mask: function (option) {
            var obj = $(this);
            var options = $.extend({
                type: 'box',
                title: '',
                level: 90,
                padding: 7,
                width: 500,
                height: 245,
                canclose: true,
                closecall: null,
                background: '#000000',
                opacity: 55
            }, option);


            var type = options.type;
            var level = parseInt(options.level);
            var width = parseInt(options.width);
            var title = options.title;

            var html = '';
            html += '<div id="' + type + '-modal-mask" class="modal-mask"></div>';
            html += '<div id="' + type + '-modal-outer" class="modal-outer">';
            html += '<div id="' + type + '-modal-inner" class="modal-inner">';
            html += '<div id="' + type + '-modal-title" class="modal-title">';
            html += '<a href="javascript:" id="' + type + '-modal-remove" class="modal-remove"><i class="model-close"></i></a>';

            if (options.title) {
                html += '<p>' + options.title + '</p>';
            }
            html += '</div>';
            html += '<div id="' + type + '-modal-content" class="modal-content"></div>';
            html += '</div>';
            html += '</div>';
            if (ie6) {//if IE 6
                $("body", "html").css({height: "100%", width: "100%"});
                if (document.getElementById(type + "-modal-hideselect") === null) {//iframe to hide select elements in ie6
                    $("body").append('<iframe id="' + type + '-modal-hideselect" class="modal-hideselect" allowTransparency="true" src="about:blank"></iframe>' + html);
                }
            } else {//all others
                if (document.getElementById(type + "-modal-mask") === null) {
                    $("body").append(html);
                }
            }
            if (options.canclose) {
                $('#' + type + '-modal-remove').show();
            } else {
                $('#' + type + '-modal-remove').hide();
            }
            $('#' + type + '-modal-mask').css({
                'z-index': level,
                'height': $(document).height(),
                'filter:': 'alpha(opacity=' + options.opacity + ')',
                'opacity': (options.opacity / 100),
                '-moz-opacity': (options.opacity / 100),
                'background': options.background
            });
            $('#' + type + '-modal-outer').css({
                'z-index': level + 1,
                'width': width + 'px',
                'padding': options.padding + 'px'
            });
            $('#' + type + '-modal-remove').click(function () {
                if (options.closecall) {
                    eval(options.closecall + "()");
                } else {
                    $.utilBaseModal.remove(type);
                }
                return false;
            });

        },
        loading: function (type, status) {
            if (status == 'hide') {
                $('#' + type + '-modal-loading').fadeOut(function () {
                    $(this).remove();
                });
            } else {
                $('#' + type + '-modal-mask').after('<div id="' + type + '-modal-loading" class="modal-loading"></div>');
                var obj = $('#' + type + '-modal-loading');

                var doc = document.documentElement;
                var width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth;
                var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
                var scrollheight = parseInt($(document).scrollTop());
                var ewidth = obj.width();
                var eheight = obj.height();


                var eleft = (width - ewidth) / 2;
                var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0) - 20;

                obj.css({left: eleft + "px", top: etop + "px"});
            }
        },
        position: function (type) {
            var width = $('body').width();
            var height = $(window).height();
            var scrollheight = parseInt($(document).scrollTop());
            var obj = $('#' + type + '-modal-outer');
            var ewidth = obj.width() + parseInt(obj.css('paddingLeft')) * 2;
            var eheight = obj.height() + parseInt(obj.css('paddingTop')) * 2;

            obj.data('w', width);
            obj.data('t', scrollheight);
            var eleft = (width - ewidth) / 2;
            var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0);

            //$("#modal-inn er").html(ewidth);
            if (height <= eheight) {
                etop = 5;
                $('body').data('scrollheight', scrollheight);
                $("body", "html").css({height: obj.height() + 10});
                $(document).scrollTop(0);
                obj.css('position', 'absolute');
            }

            obj.css({left: eleft + "px", top: etop + "px"});
        },
        positionUpdate: function (type) {
            var doc = document.documentElement;
            var width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var scrollheight = parseInt($(document).scrollTop());
            var obj = $('#' + type + '-modal-outer');
            var w = parseInt(obj.data('w'));
            $('#' + type + '-modal-outer').data('w', width);
            var eleft = parseInt(obj.css('left'));
            var eheight = obj.height() + parseInt(obj.css('paddingTop')) * 2;
            var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0);

            w != width && obj.css('left', (eleft - parseInt((w - width) / 2)) + "px");
            if (height <= eheight) {
                etop = 5;
                $('body').data('scrollheight', scrollheight);
                $("body", "html").css({height: obj.height() + 10});
                $(document).scrollTop(0);
                obj.css('position', 'absolute');
            }

            obj.css({left: eleft + "px", top: etop + "px"});
        },
        remove: function (type) {
            $('#' + type + '-modal-close').unbind("click");
            $('#' + type + '-modal-mask,#' + type + '-modal-hideselect,#' + type + '-modal-outer,#' + type + '-modal-innter').trigger("unload").unbind().remove();
            //$("body","html").css({height: "auto", width: "auto"});
            //$("html").css("overflow","auto");
            if ($('body').data('scrollheight') > 0) {
                $(document).scrollTop($('body').data('scrollheight'));
                $('body').removeData('scrollheight')
            }
            return false;
        }
    },

        //alert

        $.utilAlertModal = {
            draggable: true,
            ok_button: '确 定',
            cancel_button: '取 消',

            alert: function (message, title, callback) {
                if (title == null) title = '系统提示';
                $.utilAlertModal.show(title, message, null, 'alert', function (result) {
                    if (callback) callback(result);
                });
                return false;
            },

            popwin: function (message, title, callback) {
                if (title == null) title = '提示';
                $.utilAlertModal.show(title, message, null, 'popwin', function (result) {
                    if (callback) callback(result);
                });
            },

            confirm: function (message, title, callback) {
                if (title == null) title = '确认';
                $.utilAlertModal.show(title, message, null, 'confirm', function (result) {
                    if (callback) callback(result);
                });
                return false;
            },

            prompt: function (message, value, title, callback) {
                if (title == null) title = 'Prompt';
                $.utilAlertModal.show(title, message, value, 'prompt', function (result) {
                    if (callback) callback(result);
                });
                return false;
            },
            show: function (title, msg, value, mtype, callback) {
                var type = 'alert';
                $.utilBaseModal.mask({
                    type: type,
                    title: title,
                    level: 100,
                    padding: 1,
                    width: 340,
                    height: 150,
                    canclose: true,
                    background: '#FFFFFF',
                    opacity: 10
                });
                var html = '';
                html += '<div id="alert-content"><em></em><div>' + msg + '</div></div>';
                html += '<div id="alert-bar"></div>';
                $('#' + type + '-modal-content').html(html);

                $.utilBaseModal.position(type);
                switch (mtype) {
                    case 'alert':
                        $('#alert-content>em').attr('class', 'fa fa-exclamation-circle');
                        ;
                        $("#alert-bar").html('<button type="button" id="alert-ok" class="button button-s button-red">' + $.utilAlertModal.ok_button + '</button>');
                        $('#alert-ok').focus();
                        $("#alert-ok,#popup_close").click(function () {
                            $.utilBaseModal.remove(type);
                            callback(true);
                        });
                        $("#alert-ok").keypress(function (e) {
                            if (e.keyCode == 13 || e.keyCode == 27) $("#alert-ok").trigger('click');
                        });
                        break;
                    case 'popwin':
                        $("#alert-bar").html('<button type="button" id="alert-ok" class="button button-s button-green">' + $.utilAlertModal.ok_button + '</button>');
                        $('#alert-ok').focus();
                        $("#alert-ok").click(function () {
                            $.utilBaseModal.remove(type);
                            callback(true);
                        });
                        $("#alert-ok").keypress(function (e) {
                            if (e.keyCode == 13 || e.keyCode == 27) $("#alert-ok").trigger('click');
                        });
                        setTimeout('$.utilAlertModal._hide()', 1500);
                        break;
                    case 'confirm':
                        $('#alert-content>em').attr('class', 'fa fa-question-circle');
                        ;
                        $('#' + type + '-modal-outer').width(375);
                        $("#alert-bar").html('<button type="button" id="alert-ok" class="button button-s button-green">' + $.utilAlertModal.ok_button + '</button> <button type="button" id="alert-cancel" class="button button-s">' + $.utilAlertModal.cancel_button + '</button>');
                        $('#alert-ok').focus();
                        $("#alert-ok").click(function () {
                            $.utilBaseModal.remove(type);
                            if (callback) callback(true);
                        });
                        $("#alert-cancel").click(function () {
                            $.utilBaseModal.remove(type);
                            if (callback) callback(false);
                        });
                        $("#alert-ok, #alert-cancel").keypress(function (e) {
                            if (e.keyCode == 13) $("#alert-ok").trigger('click');
                            if (e.keyCode == 27) $("#alert-cancel").trigger('click');
                        });
                        break;
                }
                if (ie6) {
                    var doc = document.documentElement;
                    var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
                    var eheight = $('#' + type + '-modal-outer').height() + parseInt($('#' + type + '-modal-outer').css('paddingTop')) * 2;
                    height >= eheight && $(window).scroll(function () {
                        $.utilBaseModal.positionUpdate(type);
                    });
                }
                $(window).resize(function () {
                    $.utilBaseModal.positionUpdate(type)
                });
                if ($.utilAlertModal.draggable) {
                    $.getScript('/static/nc/js/utils/jquery.ui.js', function () {
                        try {
                            $('#' + type + '-modal-outer').draggable({handle: $('#' + type + '-modal-title')});
                            $('#' + type + '-modal-title').css({cursor: 'move'});
                        } catch (e) { /* requires jQuery UI draggables */
                        }
                    });
                }
                return false;
            }
        },

        // Shortuct functions
        jAlert = function (message, title, callback) {
            $.utilAlertModal.alert(message, title, callback);
        },
        jPopwin = function (message, title, callback) {
            $.utilAlertModal.popwin(message, title, callback);
        },

        jConfirm = function (message, title, callback) {
            $.utilAlertModal.confirm(message, title, callback);
        },

        //普通弹出层
        $.popModal = function (option) {
            //var obj=$(this);
            var type = "pop";
            var options = $.extend({
                iframe: false,
                url: null,
                content: null,
                title: '',
                padding: 0,
                width: 560,
                height: 245,
                datatype: 'JSON',
                canclose: true,
                closecall: null,
                background: '#000000',
                opacity: 30,
                scroll: 'yes'
            }, option);
            $.utilBaseModal.mask({
                type: type,
                title: options.title,
                padding: options.padding,
                width: options.width,
                height: options.height,
                canclose: options.canclose,
                closecall: options.closecall,
                background: options.background,
                opacity: options.opacity
            });
            $.utilBaseModal.loading(type, 'show');
            if (options.iframe == true) {
                $('#' + type + '-modal-content').html('<iframe id="' + type + '-modal-frame" name="' + type + '-modal-iframe' + Math.round(Math.random() * 1000) + '" width="' + options.width + ' height="' + (options.height - 30) + '" frameborder="0" hspace="0" src="' + options.url + '"></iframe>');
                $.utilBaseModal.loading(type, 'hide');
                $.utilBaseModal.position(type);
            } else {
                $('#' + type + '-modal-outer').hide();
                if (options.url) {
                    if (options.datatype == 'JSON') {
                        $.getJSON(options.url + '&callback=?', function (data) {
                            $.utilBaseModal.loading(type, 'hide');
                            $('#' + type + '-modal-outer').show();
                            $('#' + type + '-modal-content').html(data);
                            $.utilBaseModal.position(type);
                            ispos = false;
                        });
                    } else {
                        $.get(options.url, function (data) {
                            $.utilBaseModal.loading(type, 'hide');
                            $('#' + type + '-modal-outer').show();
                            $('#' + type + '-modal-content').html(data);
                            $.utilBaseModal.position(type);
                            ispos = false;
                        });
                    }
                }
                if (options.content) {
                    $('#' + type + '-modal-outer').show();
                    $('#' + type + '-modal-content').html(options.content);
                    $.utilBaseModal.loading(type, 'hide');
                    $.utilBaseModal.position(type);
                }
            }


            if (ie6) {
                var doc = document.documentElement;
                var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
                var eheight = $('#' + type + '-modal-outer').height() + parseInt($('#' + type + '-modal-outer').css('paddingTop')) * 2;
                height >= eheight && $(window).scroll(function () {
                    $.utilBaseModal.positionUpdate(type);
                });
            }
            $(window).resize(function () {
                $.utilBaseModal.positionUpdate(type);
            });
        },
        $.popModalClose = function () {
            $.utilBaseModal.remove('pop');
        },
        $.popModalUpdate = function () {
            $.utilBaseModal.positionUpdate('pop');
        },


        //浮动窗口
        $.fn.floatModal = function (option) {
            var options = $.extend({
                url: null,
                str: null,
                title: null,
                direction: 'right'
            }, option);

            var e = $(this),
                id = 'float-modal-' + e.attr('id'),
                offset = e.offset(),
                t = null;
            e.parent().css('position', 'relative');
            $('.float-modal').remove();
            if ($('#' + id).size() > 0) {
                $('#' + id).show();
            } else {
                var str = '<div id="' + id + '" class="float-modal">';
                str += '<h3><a href="javascript:" class="close"><i class="fa fa-close"></i></a><span>' + options.title + '</span></h3>';
                str += '<div class="modal-cont"></div>';
                str += '</div>';
                e.after(str);
                if (options.direction == 'left') {
                    $('#' + id).css({'left': 0});
                } else {
                    $('#' + id).css({'right': 0});
                }
            }

            if (options.url) {
                $('#' + id + ' .modal-cont').html('<div style="width:150px;line-height:50px;text-align:center;">正在加载...请稍后</div>');
                $.get(options.url, function (data) {
                    $('#' + id + ' .modal-cont').html(data);
                });
            } else {
                $('#' + id + ' .modal-cont').html(options.str);
            }

            $('.float-modal a.close').click(function () {
                $(this).parent().parent().remove();
                return false;
            });

        },

        //闪现提示
        $.flashTip = function (str, type) {
            var type_style = '';
            if (type == null || type == '') type = 'success';
            if (type == 'success') {
                type_style = 'check-circle';
            }
            if (type == 'warning') {
                type_style = 'exclamation-circle';
            }
            if (type == 'error') {
                type_style = 'ban';
            }
            if ($('#flash-tips').size() > 0) {
                $('#flash-tips').show();
            } else {
                $('body').append('<div id="flash-tips" class="flash-tips-' + type + '"><i class="fa fa-' + type_style + '"></i>' + str + '</div>');
            }
            var obj = $('#flash-tips'),
                doc = document.documentElement,
                width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth,
                height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight,
                scrollheight = parseInt($(document).scrollTop()),
                ewidth = obj.width() + parseInt(obj.css('paddingLeft')) + parseInt(obj.css('paddingRight')) + parseInt(obj.css('borderLeftWidth')) * 2,
                eheight = obj.height(),
                eleft = (width - ewidth) / 2,
                etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0) - 20;
            obj.css({left: eleft + "px", top: etop + "px"});
            obj.click(function () {
                obj.fadeOut(function () {
                    $(this).remove();
                });
            });
            setTimeout(function () {
                obj.fadeOut(function () {
                    $(this).remove();
                });
            }, '3500');
        },


        /*
	 * 滚动加载插件
	 * 用法
	 * 图片懒加载
	 * <div class="lazyload"><img src="blank.png" data-image="真实图片地址" /><img src="blank.png" data-image="真实图片地址" /></div>
	 * 内容懒加载
	 * <div class="lazyload"><div data-url="URL地址"></div></div>
	 * $('.lazyload').lazyload();
	 *
	 */
        $.fn.lazyload = function (setting) {
            var options = $.extend({
                height: 0
            }, setting);

            var height = options.height;
            var obj = $(this);
            var pageTop = function () {
                var d = document,
                    y = (navigator.userAgent.toLowerCase().match(/iPad/i) == "ipad") ? window.pageYOffset : Math.max(d.documentElement.scrollTop, d.body.scrollTop);
                return d.documentElement.clientHeight + y - options.height;
            }

            var objLoad = function () {
                obj.each(function () {
                    if ($(this).offset().top <= pageTop()) {
                        var this_obj = $(this);
                        var src = this_obj.attr("data-image");
                        var url = this_obj.attr("data-url")
                        src && this_obj.attr("src", src).removeAttr("data-image")
                        if (url) {
                            this_obj.removeAttr("data-url");
                            $.get(url, function (data) {
                                this_obj.html(data);
                            });
                        }
                    }
                });
            }
            objLoad();
            $(window).bind("scroll", function () {
                objLoad();
            });
        },

        /*
	 * 日期下拉插件，主要用于生日
	 */

        $.fn.utilSimpleDate = function (option) {
            var options = $.extend({
                date: null,
                split: '/'
            }, option);

            var e = $(this);
            var nd;
            var d = new Date();
            var all_year = d.getFullYear() - 14;
            var this_year;
            var this_month;
            var this_day;
            if (options.date) {
                nd = options.date.split(options.split);
            } else {
                nd = e.attr('rel').split(options.split);
            }
            this_year = nd[0];
            this_month = nd[1];
            this_day = nd[2];

            var e_year = e.find('select[name=year]');
            var e_month = e.find('select[name=month]');
            var e_day = e.find('select[name=day]');

            var i = 0;
            var selected = '';
            e_year.append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
            for (i = all_year; i > all_year - 56; i--) {
                selected = this_year == i ? ' selected' : '';
                e_year.append('<option value="' + i + '"' + selected + '>&nbsp;' + i + '&nbsp;</option>');
            }
            e_month.append('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
            for (i = 1; i <= 12; i++) {
                selected = this_month == i ? ' selected' : '';
                e_month.append('<option value="' + i + '"' + selected + '>&nbsp;' + i + '&nbsp;</option>');
            }
            e_year.change(function () {
                build_days(e_day.val());
            });
            e_month.change(function () {
                build_days(e_day.val());
            });

            if (this_day != null) {
                build_days(this_day);
            }

            function build_days(this_day) {
                var end_day = 31;
                var year = parseInt(e_year.val());
                var month = parseInt(e_month.val());
                if (month < 8 && month % 2 == 0) {
                    end_day = 30;
                    if (month == 2) {
                        end_day = 28;
                        if (year != 0 && year % 4 == 0) {
                            end_day = 29;
                        }
                    }
                } else {
                    if (month >= 8 && month % 2 != 0) {
                        end_day = 30;
                    }
                }

                if (month) {
                    e_day.html('<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>');
                    for (i = 1; i <= end_day; i++) {
                        selected = this_day == i ? ' selected' : '';
                        if (i < 10 && i > 0) {
                            i = '0' + i;
                        }
                        e_day.append('<option value="' + i + '"' + selected + '>&nbsp;' + i + '&nbsp;</option>');
                    }
                }
            }
        },


        /* 省市区三级联动
	 * 数据采用独立js文件，分成了province，city，district三个json文件
	 * 用法
	 * <div id="area-selector">
	 * <select class="province" rel="浙江省"></select><select class="city" rel="杭州市"></select><select class="district" rel="下城区"></select>
	 * </div>
	 * $('#area-selector').utilSetArea();
	 * 指定rel值，可以设置默认值
	 */

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

            if (province.length == 0 || city.length == 0 || district.length == 0) {
                $.when($.getJSON(province_json, function (data) {
                    $.each(data, function (i, d) {
                        province[d.code] = d.name;
                    });
                }), $.getJSON(city_json, function (data) {
                    $.each(data, function (i, d) {
                        city[d.code] = d.name;
                    });
                }), $.getJSON(district_json, function (data) {
                    $.each(data, function (i, d) {
                        district[d.code] = d.name;
                    });
                })).then(function () {
                    if (type == 0) {
                        $.each(e, function (i, d) {
                            array = $(d).attr('rel').split(',');
                            $(d).html(province[array[0]] + city[array[1]] + district[array[2]]);
                        });
                    } else {
                        var obj_p = e.find('.province');
                        var obj_c = e.find('.city');
                        var obj_d = e.find('.district');
                        build_list(province, obj_p.attr('rel'), obj_p, null, 0, default_province);
                        build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, default_city);
                        build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
                        obj_p.change(function () {
                            build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, default_city);
                            build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
                        });
                        obj_c.change(function () {
                            build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
                        });
                    }
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

        //文本框只能输入数字，并屏蔽输入法和粘贴
        $.fn.utilSetNumber = function () {
            $(this).css("ime-mode", "disabled");
            this.bind("keypress", function (e) {
                var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE
                if (!$.browser.msie && (e.keyCode == 0x8)) {    //火狐下不能使用退格键
                    return;
                }
                return code >= 48 && code <= 57 || code == 46;
                ;
            });
            this.bind("blur", function () {
                if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
                    this.value = this.value.substr(0, this.value.length - 1);
                } else if (isNaN(this.value)) {
                    this.value = "";
                }
            });
            this.bind("paste", function () {
                var s = clipboardData.getData('text');
                if (!/\D/.test(s)) ;
                value = s.replace(/\D/, '');
                return false;
            });
            this.bind("dragenter", function () {
                return false;
            });
            this.bind("keyup", function () {
                this.value = this.value.replace(/[^\d.]/g, "");
                //必须保证第一个为数字而不是.
                this.value = this.value.replace(/^\./g, "");
                //保证只有出现一个.而没有多个.
                this.value = this.value.replace(/\.{2,}/g, ".");
                //保证.只出现一次，而不能出现两次以上
                this.value = this.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
            });
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
            var obj_form = $(this);
            var errors = 0;
            if (obj_form.find('.tip-validate').size() > 0) {
                $.each(obj_form.find('.tip-validate'), function (i, o) {
                    var error = 0, message, target_array = [], target_type = 0;
                    var obj_target = $(o).attr('data-target'),
                        validate_rule = $(o).attr('data-rule'),
                        min_length = $(o).attr('min-length'),
                        max_length = $(o).attr('max-length'),
                        compare_with = $(o).attr('compare-with'),
                        validate_function = $(o).attr('data-function');
                    if (obj_target.indexOf('|') != -1) {
                        target_array = obj_target.split('|');
                        target_type = 1;
                    } else if (obj_target.indexOf('&') != -1) {
                        target_array = obj_target.split('&');
                        target_type = 2;
                    } else {
                        target_array.push(obj_target);
                        target_type = 0;
                    }
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
                        $.each(target_array, function (ii, oo) {
                            if ($('#' + oo).size() > 0 && $('#' + oo).val() == '') {
                                error++;
                            }
                        });
                        message = '该项不能为空，请正确输入';
                    }
                    if (validate_rule == 'mobile') {
                        $.each(target_array, function (ii, oo) {
                            if ($('#' + oo).size() > 0 && !$('#' + oo).utilValidateMobile()) {
                                error++;
                            }
                        });
                        message = '格式不正确，请输入正确手机号码';
                    }
                    if (min_length > 0) {

                        $.each(target_array, function (ii, oo) {
                            if ($('#' + oo) != '' && $('#' + oo).val().length < min_length) {
                                error++;
                            }
                        });
                        message = '长度不能小于' + min_length + '位';
                    }
                    if (max_length > 0) {
                        $.each(target_array, function (ii, oo) {
                            if ($('#' + oo) != '' && $('#' + oo).val().length > max_length) {
                                error++;
                            }
                        });
                        message = '长度不能大于' + max_length + '位';
                    }

                    if (validate_rule == 'nickname') {
                        var a = parseInt($(o).attr('byte-max'));
                        var b = parseInt($(o).attr('byte-min'));
                        var bytemax = a ? a : 30, bytemin = b ? b : 4;
                        $.each(target_array, function (ii, oo) {
                            console.log(target_array)
                            if (($('#' + oo).size() > 0 && !$('#' + oo).utilValidateName()) || $.utilValidateByteLength($('#' + oo).val()) < bytemin || $.utilValidateByteLength($('#' + oo).val()) > bytemax) {
                                error++;
                            }
                        });
                        message = '含有特殊符号或者昵称长度不符合要求';
                    }
                    if (validate_rule == 'name') {
                        $.each(target_array, function (ii, oo) {
                            if (($('#' + oo).size() > 0 && !$('#' + oo).utilValidateName()) || $('#' + oo).val().length < 2) {
                                error++;
                            }
                        });
                        message = '含有特殊符号或者名称长度不符合要求';
                    }
                    if (validate_rule == 'email') {
                        $.each(target_array, function (ii, oo) {
                            if ($('#' + oo).size() > 0 && !$('#' + oo).utilValidateEmail()) {
                                error++;
                            }
                        });
                        message = '格式不正确，请输入正确邮箱地址';
                    }
                    if (validate_rule == 'byte') {
                        var a = parseInt($(o).attr('byte-max'));
                        var b = parseInt($(o).attr('byte-min'));
                        var bytemax = a ? a : 30, bytemin = b ? b : 4;
                        $.each(target_array, function (ii, oo) {
                            if ($.utilValidateByteLength($('#' + oo).val()) < bytemin || $.utilValidateByteLength($('#' + oo).val()) > bytemax)
                                error++;

                        });
                        message = '长度必须在' + bytemin + '字符到' + bytemax + '字符之间';
                    }
                    if (validate_rule == 'sixDigit') {
                        $.each(target_array, function (ii, oo) {
                            if ($('#' + oo).size() > 0 && ($('#' + oo).val().length < 8 || $('#' + oo).val().length > 20)) {
                                error++;
                            }
                        });
                        message = '密码必须大于8位小于20位';
                    }
                    if (compare_with) {
                        if ($('#' + obj_target).val() != $('#' + compare_with).val()) {
                            error++;
                            message = "两次输入的值不一致";
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
                        $(o).show().html(message);
                    } else {
                        $(o).hide();
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
            if (re_mobile.test($(this).val()) && $(this).val().length > 4) {
                return true;
            } else {
                return false;
            }
        },
        /*检查姓名合法*/

        $.fn.utilValidateName = function () {
            $(this).css("ime-mode", "disabled");
            var re_name = /^[\u4E00-\u9FA5A-Za-z0-9_]+$/;
            if (re_name.test($(this).val())) {
                return true;
            } else {
                return false;
            }
        },
        /*返回字节判断字符长度*/
        $.utilValidateByteLength = function (val) {
            var Zhlength = 0;// 全角
            var Enlength = 0;// 半角
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
        $.utilFormatCurrency = function (num) {
            if (!isNaN(num)) {
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
            }
        },
        $.fn.utilTransAmountToCN = function (num) {
            for (i = num.length - 1; i >= 0; i--) {
                num = num.replace(",", "");
                num = num.replace(" ", "");
            }
            num = num.replace("￥", "");
            if (isNaN(num)) {
                alert("请检查小写金额是否正确");
                return;
            }
            part = String(num).split(".");
            newchar = "";
            for (i = part[0].length - 1; i >= 0; i--) {
                if (part[0].length > 10) {
                    alert("位数过大，无法计算");
                    return "";
                }
                tmpnewchar = "";
                perchar = part[0].charAt(i);
                switch (perchar) {
                    case "0":
                        tmpnewchar = "零" + tmpnewchar;
                        break;
                    case "1":
                        tmpnewchar = "壹" + tmpnewchar;
                        break;
                    case "2":
                        tmpnewchar = "贰" + tmpnewchar;
                        break;
                    case "3":
                        tmpnewchar = "叁" + tmpnewchar;
                        break;
                    case "4":
                        tmpnewchar = "肆" + tmpnewchar;
                        break;
                    case "5":
                        tmpnewchar = "伍" + tmpnewchar;
                        break;
                    case "6":
                        tmpnewchar = "陆" + tmpnewchar;
                        break;
                    case "7":
                        tmpnewchar = "柒" + tmpnewchar;
                        break;
                    case "8":
                        tmpnewchar = "捌" + tmpnewchar;
                        break;
                    case "9":
                        tmpnewchar = "玖" + tmpnewchar;
                        break;
                }
                switch (part[0].length - i - 1) {
                    case 0:
                        tmpnewchar = tmpnewchar + "元";
                        break;
                    case 1:
                        if (perchar != 0) tmpnewchar = tmpnewchar + "拾";
                        break;
                    case 2:
                        if (perchar != 0) tmpnewchar = tmpnewchar + "佰";
                        break;
                    case 3:
                        if (perchar != 0) tmpnewchar = tmpnewchar + "仟";
                        break;
                    case 4:
                        tmpnewchar = tmpnewchar + "万";
                        break;
                    case 5:
                        if (perchar != 0) tmpnewchar = tmpnewchar + "拾";
                        break;
                    case 6:
                        if (perchar != 0) tmpnewchar = tmpnewchar + "佰";
                        break;
                    case 7:
                        if (perchar != 0) tmpnewchar = tmpnewchar + "仟";
                        break;
                    case 8:
                        tmpnewchar = tmpnewchar + "亿";
                        break;
                    case 9:
                        tmpnewchar = tmpnewchar + "拾";
                        break;
                }
                newchar = tmpnewchar + newchar;
            }


            if (num.indexOf(".") != -1) {
                if (part[1].length > 2) {
                    part[1] = part[1].substr(0, 2);
                }
                for (i = 0; i < part[1].length; i++) {
                    tmpnewchar = "";
                    perchar = part[1].charAt(i);
                    switch (perchar) {
                        case "0":
                            tmpnewchar = "零" + tmpnewchar;
                            break;
                        case "1":
                            tmpnewchar = "壹" + tmpnewchar;
                            break;
                        case "2":
                            tmpnewchar = "贰" + tmpnewchar;
                            break;
                        case "3":
                            tmpnewchar = "叁" + tmpnewchar;
                            break;
                        case "4":
                            tmpnewchar = "肆" + tmpnewchar;
                            break;
                        case "5":
                            tmpnewchar = "伍" + tmpnewchar;
                            break;
                        case "6":
                            tmpnewchar = "陆" + tmpnewchar;
                            break;
                        case "7":
                            tmpnewchar = "柒" + tmpnewchar;
                            break;
                        case "8":
                            tmpnewchar = "捌" + tmpnewchar;
                            break;
                        case "9":
                            tmpnewchar = "玖" + tmpnewchar;
                            break;
                    }
                    if (i == 0) tmpnewchar = tmpnewchar + "角";
                    if (i == 1) tmpnewchar = tmpnewchar + "分";
                    newchar = newchar + tmpnewchar;
                }
            }
            while (newchar.search("零零") != -1) {
                newchar = newchar.replace("零零零", "");
                newchar = newchar.replace("零零", "零");
                newchar = newchar.replace("零亿", "亿");
                newchar = newchar.replace("亿万", "亿");
                newchar = newchar.replace("零万", "万");
                newchar = newchar.replace("零元", "元");
                newchar = newchar.replace("零角", "");
                newchar = newchar.replace("零分", "");
                if (newchar.charAt(newchar.length - 1) == "元" || newchar.charAt(newchar.length - 1) == "角") {
                    newchar = newchar + "整"
                }
            }
            newchar = newchar.replace("零元", "元");
            return newchar;
        },
        //倒计时
        $.fn.countdown = function () {
            var obj = $(this);
            var type = obj.attr('data-type');
            var defined = obj.attr('data-defined');
            var func = obj.attr('data-function');
            var startTime = (new Date(obj.attr("data-startTime"))).getTime();
            var endTime = (new Date(obj.attr("data-endTime"))).getTime();
            var nowTime = (new Date(_server_time)).getTime();
            var st = startTime - nowTime, et = endTime - nowTime;
            var timestr;
            if (type == undefined) {
                type = (st > 0) ? 'tostart' : 'toend';
            }
            var decrease = 1000;
            if (defined == 'millisecond') {//如果定义的是毫秒则计时器设为100毫秒一次
                decrease = 100;
            }

            timestr = (st > 0) ? st : et;

            output();
            var timer = setInterval(function () {
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
                    time_tip = '已经开始';
                }
                var time = expireTime(timestr);
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

            function expireTime(time) {
                var array = new Array();
                if (time > 0) {
                    var tDay = Math.floor(time / 86400000);
                    tHour = Math.floor((time / 3600000) % 24);
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
                var num = obj.find('.star').size();
                var input_num = parseInt(obj.find('input').val());
                if (input_num == NaN) input_num = 0;
                if (input_num > 0) {
                    for (var n = 4; n >= (5 - input_num); n--) {
                        obj.find('.star:eq(' + n + ')').addClass('checked');
                    }
                }
                obj.find('.star').click(function () {
                    var i = $(this).index();
                    var obj_p = $(this).parent();
                    $(this).siblings('input').val(5 - i);
                    obj_p.find('.star').removeClass('checked');
                    for (var m = 4; m >= i; m--) {
                        obj_p.find('.star:eq(' + m + ')').addClass('checked');
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
        },
        //时间戳转时间
        $.unixtime = function (param) {
            var timestamp = param.replace(/^\s+|\s+$/, '');
            if (/^\d{10}$/.test(timestamp)) {
                timestamp *= 1000;
            } else if (/^\d{13}$/.test(timestamp)) {
                timestamp = parseInt(timestamp);
            } else {
                alert('时间戳格式不正确！');
                return;
            }

            function format(timestamp) {
                var time = new Date(timestamp);
                var year = time.getFullYear();
                var month = (time.getMonth() + 1) > 9 && (time.getMonth() + 1) || ('0' + (time.getMonth() + 1))
                var date = time.getDate() > 9 && time.getDate() || ('0' + time.getDate())
                var hour = time.getHours() > 9 && time.getHours() || ('0' + time.getHours())
                var minute = time.getMinutes() > 9 && time.getMinutes() || ('0' + time.getMinutes())
                var second = time.getSeconds() > 9 && time.getSeconds() || ('0' + time.getSeconds())
                var YmdHis = year + '-' + month + '-' + date
                    + ' ' + hour + ':' + minute + ':' + second;
                return YmdHis;
            }

            var YmdHis = format(timestamp);
            return YmdHis;
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

