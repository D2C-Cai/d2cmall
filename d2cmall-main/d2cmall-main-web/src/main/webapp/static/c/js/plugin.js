//广告滑动和滚动加载
/*
	开发：王锡强 2012.04.01
	版本：ver 1.0.3
*/
(function ($) {
    var ie6 = ($.browser.msie && ($.browser.version == "6.0") && !$.support.style);
    var static_base = 'http://static.d2c.cn/common/c';

    $.fn.slide_arrow = function (setting) {
        var options = $.extend({
            speed: 1000, //几秒轮换一次
            unit: 'li',//包含的单位
            num: 1, //显示出几个一行
            auto: false
        }, setting);
        var e = $(this);
        var unit = options.unit;
        var num = parseInt(options.num);
        var size = e.find(unit).size();
        var per_width = e.find(unit).width();

        var speed = parseInt(options.speed) * 1000;

        _init();
        var t = setInterval(_auto, speed);


        function _init() {
            e.before('<a href="javascript:" class="arrow-left">&lt;</a><a href="javascript:" class="arrow-right">&gt;</a>');
            e.siblings('a.arrow_left').click(function () {
                _click('left');
            });
            e.siblings('a.arrow_right').click(function () {
                _click('right');
            });
            e.siblings('a.arrow-right,a.arrow-left').mousedown(function () {
                clearInterval(t);
                t = setInterval(_auto, speed);
            });

        }

        function _click(direct) {
            if (direct == 'right') {
                e.animate({left: -(num * per_width) + 'px'}, function () {
                    e.find(unit + ':lt(' + num + ')').appendTo(e);
                    e.css('left', 0);
                });
            }
            if (direct == 'left') {
                e.find(unit + ':gt(' + ((size - num) - 1) + ')').prependTo(e);
                e.css('left', -(num * per_width) + 'px');
                e.animate({left: '0px'});
            }
        }

        function _auto() {
            e.siblings('a.arrow-right').trigger('click');
        }
    }
    $.fn.slide_plugin = function (setting) {
        var options = $.extend({
            speed: 5, //几秒轮换一次
            unit: 'li',//包含的单位
            num: 1, //显示出几个一行
            direction: 'y',//滚动方向，x横向，y纵向
            width: 655,
            height: 310,
            pagesetter: null,
            auto: true
        }, setting);
        var unit = options.unit;
        var e = $(this);
        var o = e.find(unit);
        var size = e.find(unit).size();

        var num = options.num;
        var page = Math.ceil(size / num);
        var speed = parseInt(options.speed) * 1000;
        var direction = options.direction;
        var width = options.width;
        var height = options.height;
        var pagesetter = options.pagesetter;
        var auto = options.auto;
        if (direction == 'x') {
            e.width(size * width);
        }
        _init();
        _click();
        var t = setInterval(_auto, speed);

        function _init() {
            //direction=='y'?e.height(height):e.width(width);

            var str = '';
            for (var i = 1; i <= page; i++) {
                str += '<a href="javascript:"';
                if (1 == i) {
                    str += ' class="on"';
                }
                str += '><span>' + i + '</span></a>';
            }
            _animate(1);
            str != '' && pagesetter == null ? e.after('<div class="num_bar">' + str + '</div>') : $(pagesetter).html('<div class="num_bar">' + str + '</div>');
        }

        function _click() {
            var src = null;
            var s = 'slow';
            var num_bar = pagesetter == null ? e.siblings('.num_bar') : $(pagesetter).find('.num_bar');
            num_bar.find('a').click(function () {
                var ci = $(this).index();
                $(this).siblings().removeClass('on');
                $(this).addClass('on');
                _animate(ci + 1);
                clearInterval(t);
                t = setInterval(_auto, speed);
            });
        }

        function _animate(i) {
            var s = 'slow';
            if (i == 0) {
                s = 'fast';
            }
            //延迟加载
            for (m = (i - 1) * num; m <= i * num - 1; m++) {
                src = o.eq(m).find('img').attr('data-image');
                src && o.eq(m).find('img').attr("src", src).removeAttr("data-image");
            }
            direction == 'y' ? e.animate({top: parseInt('-' + height) * (i - 1)}, s) : e.animate({left: '-' + parseInt(width) * (i - 1) + '%'}, s);
        }

        function _auto() {
            var num_bar = pagesetter == null ? e.siblings('.num_bar') : $(pagesetter).find('.num_bar');
            var current_i = num_bar.find('a.on').index();
            var next = 1;
            next = (current_i + 1) >= page ? 0 : current_i + 1;
            //alert(current_i+','+next);
            num_bar.find('a').eq(next).trigger('click');
        }
    }

    //滚动加载
    $.fn.lazyload = function (setting) {
        var options = $.extend({
            defHeight: 0
        }, setting)

        var defHeight = options.defHeight;
        var defObj = $(this);
        var pageTop = function () {
            var d = document,
                y = (navigator.userAgent.toLowerCase().match(/iPad/i) == "ipad") ? window.pageYOffset : Math.max(d.documentElement.scrollTop, d.body.scrollTop);
            return d.documentElement.clientHeight + y - options.defHeight;
        }

        var objLoad = function () {
            defObj.each(function () {
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
    }

    //遮罩层基本结构
    $.basebox = {
        mask: function (option) {
            var obj = $(this);
            var options = $.extend({
                type: 'box',
                title: '系统提示',
                level: 100,
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
            html += '<div id="' + type + '-ebox-mask" class="ebox-mask"></div>';
            html += '<div id="' + type + '-ebox-outer" class="ebox-outer">';
            html += '<div id="' + type + '-ebox-inner" class="ebox-inner">';
            html += '<div id="' + type + '-ebox-title" class="ebox-title">';
            html += '<a href="javascript:" id="' + type + '-ebox-remove" class="ebox-remove"><span>X</span></a>';

            //if (options.title) {
            //	html+='<span>'+options.title+'</span>';
            //}
            html += '</div>';
            html += '<div id="' + type + '-ebox-content" class="ebox-content"></div>';
            html += '</div>';
            html += '</div>';
            if (ie6) {//if IE 6
                $("body", "html").css({height: "100%", width: "100%"});
                if (document.getElementById(type + "-ebox-hideselect") === null) {//iframe to hide select elements in ie6
                    $("body").append('<iframe id="' + type + '-ebox-hideselect" class="ebox-hideselect" allowTransparency="true" src="about:blank"></iframe>' + html);
                }
            } else {//all others
                if (document.getElementById(type + "-ebox-mask") === null) {
                    $("body").append(html);
                }
            }
            if (options.canclose) {
                $('#' + type + '-ebox-remove').show();
            } else {
                $('#' + type + '-ebox-remove').hide();
            }
            $('#' + type + '-ebox-mask').css({
                'z-index': level,
                'height': $(document).height(),
                'filter:': 'alpha(opacity=' + options.opacity + ')',
                'opacity': (options.opacity / 100),
                '-moz-opacity': (options.opacity / 100),
                'background': options.background
            });
            $('#' + type + '-ebox-outer').css({
                'z-index': level + 1,
                'width': width + 'px',
                'padding': options.padding + 'px'
            });
            $('#' + type + '-ebox-remove').click(function () {
                if (options.closecall) {
                    eval(options.closecall);
                } else {
                    $.basebox.remove(type);
                }
                return false;
            });

        },
        loading: function (type, status) {
            if (status == 'hide') {
                $('#' + type + '-ebox-loading').fadeOut(function () {
                    $(this).remove();
                });
            } else {
                $('#' + type + '-ebox-mask').after('<div id="' + type + '-ebox-loading" class="ebox-loading"></div>');
                var obj = $('#' + type + '-ebox-loading');

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
            var obj = $('#' + type + '-ebox-outer');
            var ewidth = obj.width() + parseInt(obj.css('paddingLeft')) * 2;
            var eheight = obj.height() + parseInt(obj.css('paddingTop')) * 2;

            obj.data('w', width);
            obj.data('t', scrollheight);
            var eleft = (width - ewidth) / 2;
            var etop = (height - eheight) / 2 + (ie6 ? scrollheight : 0);
            //$("#ebox-inn er").html(ewidth);
            if (height <= eheight) {
                etop = 10;
                $('body').data('scrollheight', scrollheight);
                $("body", "html").css({height: obj.height() + 10});
                $(document).scrollTop(0);
                obj.css('position', 'absolute');
            }

            obj.css({left: eleft + "px", top: etop + "px"});
        },
        positionNow: function (type) {
            var doc = document.documentElement;
            var width = self.innerWidth || (doc && doc.clientWidth) || document.body.clientWidth;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var scrollheight = parseInt($(document).scrollTop());

            var w = parseInt($('#' + type + '-ebox-outer').data('w'));
            $('#' + type + '-ebox-outer').data('w', width);

            var eleft = parseInt($('#' + type + '-ebox-outer').css('left'));
            w != width && $('#' + type + '-ebox-outer').css('left', (eleft - parseInt((w - width) / 2)) + "px");
            if (ie6) {
                var t = parseInt($('#' + type + '-ebox-outer').data('t'));
                $('#' + type + '-ebox-outer').data('t', scrollheight);
                var etop = parseInt($('#' + type + '-ebox-outer').css('top')) + (scrollheight - t);//+(ie6?scrollheight:0)
                $('#' + type + '-ebox-outer').css('top', etop + 'px');
            }
        },
        remove: function (type) {
            $('#' + type + '-ebox-close').unbind("click");
            $('#' + type + '-ebox-mask,#' + type + '-ebox-hideselect,#' + type + '-ebox-outer,#' + type + '-ebox-innter').trigger("unload").unbind().remove();
            //$("body","html").css({height: "auto", width: "auto"});
            //$("html").css("overflow","auto");
            if ($('body').data('scrollheight') > 0) {
                $(document).scrollTop($('body').data('scrollheight'));
                $('body').removeData('scrollheight')
            }
            return false;
        }
    }

    //alert

    $.alerts = {
        draggable: true,
        ok_button: '确 定',
        cancel_button: '取 消',

        alert: function (message, title, callback) {
            if (title == null) title = '系统提示';
            $.alerts.show(title, message, null, 'alert', function (result) {
                if (callback) callback(result);
            });
        },

        popwin: function (message, title, callback) {
            if (title == null) title = '提示';
            $.alerts.show(title, message, null, 'popwin', function (result) {
                if (callback) callback(result);
            });
        },

        confirm: function (message, title, callback) {
            if (title == null) title = '确认';
            $.alerts.show(title, message, null, 'confirm', function (result) {
                if (callback) callback(result);
            });
        },

        prompt: function (message, value, title, callback) {
            if (title == null) title = 'Prompt';
            $.alerts.show(title, message, value, 'prompt', function (result) {
                if (callback) callback(result);
            });
        },
        show: function (title, msg, value, mtype, callback) {
            var type = 'alert';
            $.basebox.mask({
                type: type,
                title: title,
                level: 105,
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
            $('#' + type + '-ebox-content').html(html);

            $.basebox.position(type);
            switch (mtype) {
                case 'alert':
                    $('#alert-content>em').attr('class', 'alert');
                    ;
                    $("#alert-bar").html('<input type="button" value="' + $.alerts.ok_button + '" id="alert-ok" class="alert-button" />');
                    $('#alert-ok').focus();
                    $("#alert-ok,#popup_close").click(function () {
                        $.basebox.remove(type);
                        callback(true);
                    });
                    $("#alert-ok").keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) $("#alert-ok").trigger('click');
                    });
                    break;
                case 'popwin':
                    $("#alert-bar").html('<input type="button" value="' + $.alerts.ok_button + '" id="alert-ok" class="alert-button" />');
                    $('#alert-ok').focus();
                    $("#alert-ok").click(function () {
                        $.basebox.remove(type);
                        callback(true);
                    });
                    $("#alert-ok").keypress(function (e) {
                        if (e.keyCode == 13 || e.keyCode == 27) $("#alert-ok").trigger('click');
                    });
                    setTimeout('$.alerts._hide()', 1500);
                    break;
                case 'confirm':
                    $('#alert-content>em').attr('class', 'confirm');
                    ;
                    $('#' + type + '-ebox-outer').width(375);
                    $("#alert-bar").html('<input type="button" value="' + $.alerts.ok_button + '" id="alert-ok" class="alert-button" /> <input type="button" value="' + $.alerts.cancel_button + '" id="alert-cancel" class="alert-button" />');
                    $('#alert-ok').focus();
                    $("#alert-ok").click(function () {
                        $.basebox.remove(type);
                        if (callback) callback(true);
                    });
                    $("#alert-cancel").click(function () {
                        $.basebox.remove(type);
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
                var eheight = $('#' + type + '-ebox-outer').height() + parseInt($('#' + type + '-ebox-outer').css('paddingTop')) * 2;
                height >= eheight && $(window).scroll(function () {
                    $.basebox.positionNow(type);
                });
            }
            $(window).resize(function () {
                $.basebox.positionNow(type)
            });
            if ($.alerts.draggable) {
                $.getScript(static_base + "/js/jquery-ui.min.js", function () {
                    try {
                        $('#' + type + '-ebox-outer').draggable({handle: $('#' + type + '-ebox-title')});
                        $('#' + type + '-ebox-title').css({cursor: 'move'});
                    } catch (e) { /* requires jQuery UI draggables */
                    }
                });
            }
            return false;
        }
    }

    // Shortuct functions
    jAlert = function (message, title, callback) {
        $.alerts.alert(message, title, callback);
    }
    jPopwin = function (message, title, callback) {
        $.alerts.popwin(message, title, callback);
    }

    jConfirm = function (message, title, callback) {
        $.alerts.confirm(message, title, callback);
    }

    $.viewbox = function (option) {
        var doc_width = document.body.clientWidth;
        var type = "viewbox";
        var img_width = 650;
        var options = $.extend({
            url: '',
            ids: null,
            data: null
        }, option);

        $.basebox.mask({
            type: type,
            padding: 0,
            width: 1200,
            canclose: true,
            background: 'url(\'' + static_base + '/css/images/overlay.png\')',
            opacity: 90
        });

        var ids = eval(options.ids);
        var data = eval(options.data);
        var img_height = (img_width / data.firstCompositionPictrue.width) * data.firstCompositionPictrue.height;


        var html = '<div class="view-box-wrap">';
        html += '<div class="view-box-img">';
        if (ids.prev) {
            html += '<div class="view-box-prev"><a href="javascript:">&lt;</a></div>';
        }
        if (ids.next) {
            html += '<div class="view-box-next"><a href="javascript:">&gt;</a></div>';
        }
        html += '<div id="view-box-loading"></div><img src="http://s.ohimg.com/e/img/blank.gif" data-id="' + options.id + '" height="' + img_height + '" id="pop-img" /></div>';
        html += '<div class="view-box-side">';
        html += '<div class="view-box-designer">\
			<div class="avatar"><img src="' + options.url + data.designer.headPicture.path + '_60" alt="' + data.designer.name + '" width="60" /></div>\
			<div class="name"><a href="/showroom/designer/' + data.designer.id + '" target="_blank" title="点击打开设计师主页">' + data.designer.name + '</a></div>\
			<div class="">\
			<a href="/member/interest/add/follow/designer/' + data.designer.id + '" class="op-attention" data-id="' + data.designer.id + '"><em></em>关注(<strong>' + data.designer.fans + '</strong>)</a>\
			<a href="/showroom/designer/' + data.designer.id + '" target="_blank" class="link-home"><em></em>浏览更多作品</a>\
			</div>\
			</div>\
			<div class="comment-form" data-type="DESIGNCOMP" data-id="' + ids.current + '" style="margin-top:15px;">\
				<div class="text-area grey" contenteditable="true" placeholder="我也说一句">我也说一句</div>\
				<div class="post-bar"><button type="button" name="">发表</button></div>\
			</div>\
			<div class="comment-list" data-type="DESIGNCOMP" data-id="' + ids.current + '"></div>';
        html += '</div>';
        html += '<div class="clear"></div></div>';
        $('#' + type + '-ebox-content').html(html);
        $('.view-box-prev,.view-box-next').css('top', ($(window).height() - 110) / 2);
        $('#view-large').css({
            'left': ($('.view-box-img').width() / 2 - 40) + 'px',
            'top': ($(window).height() / 2 - 20) + 'px'
        });
        loadimg(options.url + data.firstCompositionPictrue.path);
        $.basebox.position(type);

        comment_list($('.comment-list'));
        $('.view-box-prev a').die().live('click', function () {
            $('.lookbook-item-img[data-id=' + ids.prev + ']:first').trigger('click');
            return false;
        });
        $('.view-box-next a').die().live('click', function () {
            $('.lookbook-item-img[data-id=' + ids.next + ']:first').trigger('click');
            return false;
        });

        $('#pop-img').click(function () {
            var url = $(this).attr('src');
            window.open(url);
        });

        function loadimg(path) {
            $('#view-box-loading').css({'width': $('.view-box-img').width()}).show();
            $('#view-large').hide();
            $('#pop-img').css({'filter': 'Alpha(Opacity=50)', 'moz-opacity': '0.5', 'opacity': '0.5'});
            var img = new Image();
            img.onload = function () {
                $('#pop-img').css({'filter': '', 'moz-opacity': '1', 'opacity': '1'});
                $('#view-box-loading').hide();
            }
            img.src = path;
            $('#pop-img').attr('src', img.src);
            $('#view-large').attr('href', img.src);
        }
    }

    //弹出层
    $.ebox = function (option) {
        //var obj=$(this);
        var type = "ebox";
        var options = $.extend({
            iframe: false,
            url: null,
            content: null,
            title: '系统提示',
            padding: 1,
            width: 560,
            height: 245,
            datatype: 'JSON',
            canclose: true,
            closecall: null,
            background: '#000000',
            opacity: 30,
            scroll: 'yes'
        }, option);

        $.basebox.mask({
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
        $.basebox.loading(type, 'show');
        if (options.iframe == true) {
            $('#' + type + '-ebox-content').html('<iframe id="' + type + '-ebox-frame" name="' + type + '-ebox-iframe' + Math.round(Math.random() * 1000) + '" width="' + options.width + ' height="' + (options.height - 30) + '" frameborder="0" hspace="0" src="' + options.url + '"></iframe>');
            $.basebox.loading(type, 'hide');
            $.basebox.position(type);
        } else {
            $('#' + type + '-ebox-outer').hide();
            if (options.url) {
                if (options.datatype == 'JSON') {
                    $.getJSON(options.url + '&callback=?', function (data) {
                        $.basebox.loading(type, 'hide');
                        $('#' + type + '-ebox-outer').show();
                        $('#' + type + '-ebox-content').html(data);
                        $.basebox.position(type);
                        ispos = false;
                    });
                } else {
                    $.get(options.url, function (data) {
                        $.basebox.loading(type, 'hide');
                        $('#' + type + '-ebox-outer').show();
                        $('#' + type + '-ebox-content').html(data);
                        $.basebox.position(type);
                        ispos = false;
                    });
                }
            }
            if (options.content) {
                $('#' + type + '-ebox-outer').show();
                $('#' + type + '-ebox-content').html(options.content);
                $.basebox.loading(type, 'hide');
                $.basebox.position(type);
            }
        }


        if (ie6) {
            var doc = document.documentElement;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var eheight = $('#' + type + '-ebox-outer').height() + parseInt($('#' + type + '-ebox-outer').css('paddingTop')) * 2;
            height >= eheight && $(window).scroll(function () {
                $.basebox.positionNow(type);
            });
        }
        $(window).resize(function () {
            $.basebox.positionNow(type)
        });
    }

    $.mybox = function (option) {
        //var obj=$(this);
        var type = "mybox";
        var options = $.extend({
            url: null,
            width: 800,
            height: 500,
            datatype: 'JSON',
            closecall: null,
            opacity: 30
        }, option);

        $.basebox.mask({
            type: type,
            width: options.width,
            height: options.height,
            closecall: options.closecall,
            padding: 0,
            canclose: true,
            background: '#000',
            opacity: options.opacity
        });
        $.basebox.loading(type, 'show');
        $('#' + type + '-ebox-outer').hide();
        if (options.url) {
            if (options.datatype == 'JSON') {
                $.getJSON(options.url + '&callback=?', function (data) {
                    $.basebox.loading(type, 'hide');
                    $('#' + type + '-ebox-outer').show();
                    $('#' + type + '-ebox-content').html(data);
                    $.basebox.position(type);
                    ispos = false;
                });
            } else {
                $.get(options.url, function (data) {
                    $.basebox.loading(type, 'hide');
                    $('#' + type + '-ebox-outer').show();
                    $('#' + type + '-ebox-content').html(data);
                    $.basebox.position(type);
                    ispos = false;
                });
            }
        }
        if (ie6) {
            var doc = document.documentElement;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var eheight = $('#' + type + '-ebox-outer').height() + parseInt($('#' + type + '-ebox-outer').css('paddingTop')) * 2;
            height >= eheight && $(window).scroll(function () {
                $.basebox.positionNow(type);
            });
        }
        $(window).resize(function () {
            $.basebox.positionNow(type)
        });
    }

    $.videobox = function (option) {
        //var obj=$(this);
        var type = "video";
        var options = $.extend({
            url: null,
            title: '系统提示',
            padding: 0,
            width: 600,
            height: 450,
            background: '#000000',
            opacity: 30,
            scroll: 'yes'
        }, option);

        $.basebox.mask({
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
        $.basebox.loading(type, 'show');
        var html = '<div style="width:' + options.width + 'px;height:' + options.height + 'px"><object id="video_player_v4" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="100%" height="100%">';
        html += '<param name="wmode" value="transparent"/>';
        html += '<param name="movie" value="' + options.url + '>';
        html += '<param name="quality" value="high">';
        html += '<param name="allowFullScreen" value="true" />';
        html += '<embed src="' + options.url + '" allowFullScreen="true" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="100%" height="100%"></embed>';
        html += '</object></div>';


        $('#' + type + '-ebox-outer').show();
        $('#' + type + '-ebox-content').html(html);
        $.basebox.loading(type, 'hide');
        $.basebox.position(type);


        if (ie6) {
            var doc = document.documentElement;
            var height = self.innerHeight || (doc && doc.clientHeight) || document.body.clientHeight;
            var eheight = $('#' + type + '-ebox-outer').height() + parseInt($('#' + type + '-ebox-outer').css('paddingTop')) * 2;
            height >= eheight && $(window).scroll(function () {
                $.basebox.positionNow(type);
            });
        }
        $(window).resize(function () {
            $.basebox.positionNow(type)
        });
    }

    $.fn.getdate = function (option) {
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
                    e_day.append('<option value="' + i + '"' + selected + '>&nbsp;' + i + '&nbsp;</option>');
                }
            }
        }
    }

    $.fn.getarea = function () {
        var province_json = "/static/c/js/json-array-of-province.js";
        var city_json = "/static/c/js/json-array-of-city.js";
        var district_json = "/static/c/js/json-array-of-district.js";
        var default_province = '<option value="0">选择省份</option>';
        var default_city = '<option value="0">选择城市</option>';
        var default_district = '<option value="0">选择区县</option>';

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
            var option;

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
    }

    //文本框只能输入数字，并屏蔽输入法和粘贴
    $.fn.number = function () {
        $(this).css("ime-mode", "disabled");
        this.bind("keypress", function (e) {
            var code = (e.keyCode ? e.keyCode : e.which);  //兼容火狐 IE
            if (!$.browser.msie && (e.keyCode == 0x8)) {    //火狐下不能使用退格键
                return;
            }
            return code >= 48 && code <= 57;
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
            if (/\D/.test(this.value)) {
                this.value = this.value.replace(/\D/, '');
            }
        });
    },

        //表单校验
        $.fn.validate = function () {
            var error = 0;
            var msg = null;
            var ids = null;
            var m = 0;
            $.each($(this), function (i, d) {
                msg = null;
                var obj = $(d);
                var val = obj.val();
                var type = obj.attr('data-type');
                var with_obj = obj.attr('data-with');
                var child = obj.attr('data-child');//判断是否是容器内的元素
                var label = obj.prev().text();
                var min_len = obj.attr('minlength');

                if (child != '' && child != undefined) {
                    m = 0;
                    $.each(obj.find('select'), function (o, p) {
                        if ($(p).val() == '0') {
                            m++;
                        }
                    });
                    if (m > 0) {
                        msg = obj.attr('data-msg');
                    }
                } else {
                    if (with_obj != '' && with_obj != undefined) {
                        ids = with_obj.split(',');
                        m = 0;
                        $.each(ids, function (x, y) {
                            if ($('#' + y).val() == '') {
                                m++;
                            }
                        });
                        if (val == '' && m > 0) {
                            msg = obj.siblings('.input-msg').html();
                            if (msg == null) msg = label + '不能为空！';
                        }
                    } else {
                        if (val == '') {
                            msg = obj.siblings('.input-msg').html();
                            if (msg == null) msg = label + '不能为空！';
                        }
                    }
                }

                if (min_len != '' && min_len != undefined) {
                    if (val.length < min_len) {
                        msg = '字符长度不能少于' + min_len + '位';
                    }
                }
                if (type == 'mobile' && val != '') {
                    if (!obj.mobile()) {
                        msg = "请输入正确的手机号码";
                    }
                }
                if (type == 'email' && val != '') {
                    if (!obj.email()) {
                        msg = "请输入正确的电子邮箱";
                    }
                }
                if (type == 'number' && val != '') {
                    if (parseInt(val) > parseInt(obj.attr('data-num-limit'))) {
                        msg = "输入的数字不能超过" + obj.attr('data-num-limit');
                    }
                }
                if (msg != null) {
                    obj.addClass('error');
                    if (obj.siblings('.input-msg').size() > 0) {
                        obj.siblings('.input-msg').addClass('error').show().html(msg);
                    } else {
                        obj.parent().append(' <span class="input-msg error">' + msg + '</span>');
                    }
                    error++;
                } else {
                    obj.removeClass('error');
                    obj.siblings('.input-msg').removeClass('error').hide();
                }
            });
            if (error == 0) {
                return true;
            } else {
                return false;
            }
        },

        //检测手机号
        $.fn.mobile = function () {
            $(this).css("ime-mode", "disabled");
            var re_mobile = /^[1][3456789]\d{9}$/;
            if (re_mobile.test($(this).val()) && $(this).val().length == 11) {
                return true;
            } else {
                return false;
            }
        },

        //检测邮箱
        $.fn.email = function () {
            $(this).css("ime-mode", "disabled");
            var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if (re_email.test($(this).val())) {
                return true;
            } else {
                return false;
            }
        },

        //闪现提示
        $.flash_tips = function (str, type) {
            if (type == null || type == '') type = 'success';
            if ($('#flash-tips').size() > 0) {
                $('#flash-tips').show();
            } else {
                $('body').append('<div id="flash-tips" class="flash-tips-' + type + '"><i></i>' + str + '</div>');
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
            }, '3000');
        },

        //浮动box
        $.fn.float_box = function (option) {
            var options = $.extend({
                url: null,
                str: null,
                title: null,
                direction: 'right'
            }, option);

            var e = $(this),
                id = 'float-box-' + e.attr('id'),
                offset = e.offset(),
                t = null;
            e.parent().css('position', 'relative');
            $('.d2c-float-box').remove();
            if ($('#' + id).size() > 0) {
                $('#' + id).show();
            } else {
                var str = '<div id="' + id + '" class="d2c-float-box">';
                str += '<h3><a href="javascript:" class="close">x</a><span>' + options.title + '</span></h3>';
                str += '<div class="d2c-box-cont"></div>';
                str += '</div>';
                e.after(str);
                if (options.direction == 'left') {
                    $('#' + id).css({'left': 0});
                } else {
                    $('#' + id).css({'right': 0});
                }
            }

            if (options.url) {
                $('#' + id + ' .d2c-box-cont').html('<div style="width:150px;line-height:50px;text-align:center;">正在加载...请稍后</div>');
                $.get(options.url, function (data) {
                    $('#' + id + ' .d2c-box-cont').html(data);
                });
            } else {
                $('#' + id + ' .d2c-box-cont').html(options.str);
            }

            $('.d2c-float-box a.close').click(function () {
                $(this).parent().parent().remove();
                return false;
            });

        },

        //倒计时
        $.fn.countdown = function () {
            var obj = $(this);
            var type = obj.attr('data-type');
            var func = obj.attr('data-function');
            var startTime = (new Date(obj.attr("data-startTime"))).getTime();
            var endTime = (new Date(obj.attr("data-endTime"))).getTime();
            var nowTime = new Date(_server_time).getTime();
            var st = startTime - nowTime, et = endTime - nowTime;
            var timestr;
            if (type == undefined) {
                type = (st > 0) ? 'tostart' : 'toend';
            }
            timestr = (st > 0) ? st : et;

            output();
            var timer = setInterval(function () {
                output();
                if (timestr <= 0) {
                    clearInterval(timer);
                }
                timestr -= 1000;
            }, 1000);

            function output() {
                var time = expireTime(timestr);
                if (timestr <= 0) {
                    if (typeof (eval(func)) == 'function') {
                        obj.html(eval(func));
                    } else {
                        obj.html('');
                    }
                }

                var str = s_str = e_str = '';
                if (type == 'split-time') {
                    obj.find('.day').html(time['day']);
                    obj.find('.hour').html(time['hour']);
                    obj.find('.minute').html(time['minute']);
                    obj.find('.second').html(time['second']);
                } else {
                    if (type == 'toend') {
                        s_str = '还剩';
                        e_str = '结束';
                    } else if (type == 'tostart') {
                        s_str = '还有';
                        e_str = '开始';
                    }

                    str += '<span class="normal">' + s_str;
                    if (time['day'] > 0) {
                        str += '<strong>' + time['day'] + '</strong>天';
                    }
                    str += '<strong>' + time['hour'] + '</strong>小时<strong>' + time['minute'] + '</strong>分<strong>' + time['second'] + '</strong>秒' + e_str + '</span>';
                    if (time['day'] == 0 && time['hour'] == 0 && time['minute'] == 0 && time['second'] == 0) {
                        str = '已经结束';
                    }
                    obj.html(str);
                }
            }

            function expireTime(time) {
                var array = new Array();
                if (time > 0) {
                    var tDay = Math.floor(time / 86400000);
                    time -= tDay * 86400000;
                    tHour = Math.floor(time / 3600000);
                    time -= tHour * 3600000;
                    if (tHour < 10) {
                        tHour = "0" + tHour;
                    }
                    tMinute = Math.floor(time / 60000);
                    time -= tMinute * 60000;
                    if (tMinute < 10) {
                        tMinute = "0" + tMinute;
                    }
                    tSecond = Math.floor(time / 1000);
                    if (tSecond < 10) {
                        tSecond = "0" + tSecond;
                    }
                    array['day'] = tDay;
                    array['hour'] = tHour;
                    array['minute'] = tMinute;
                    array['second'] = tSecond;
                } else {
                    array['day'] = '00';
                    array['hour'] = '00';
                    array['minute'] = '00';
                    array['second'] = '00';
                }
                return array;
            }
        }
})(jQuery);