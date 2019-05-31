var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {//移动终端浏览器版本信息 
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
            iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }()
}
$(function () {
    $('h1.header-logo').after('<div style="float:left;position:relative;margin-left:-100px;margin-top:10px;width:150px"><a href="/page/chuyou" style="display:block;position:absolute;top:0;" target="_blank"><img src="/static/c/images/space.gif" width="140" height="60"></a><embed src="http://static.d2c.cn/img/other/150430/51.swf" quality="high" width="140" height="60" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer"></div>');
    //生成excel
    $('.excel-export-button').click(function () {
        var form = $('#search-form');
        var url = $(this).attr('data-url');
        var _this = $(this);
        var _text = _this.text();
        jConfirm('确定要导出报表吗？', '', function (r) {
            if (r) {
                _this.attr('disabled', true).text('正在生成数据...');
                $.ajax({
                    url: url,
                    type: form.attr('method'),
                    data: form.serialize(),
                    dataType: 'json',
                    success: function (data) {
                        _this.removeAttr('disabled').text(_text);
                        if (data.result.status > 0) {
                            var paths = data.result.data.paths;
                            if (paths.length > 1) {
                                var str = '数据较大，分成多个文件下载：<br />';
                                for (var i = 0; i < paths.length; i++) {
                                    str += ' &nbsp;<a href="' + paths[i] + '">文件' + (i + 1) + '</a> &nbsp;';
                                }
                                window.parent.jAlert(str);
                            } else {
                                window.location.href = paths[0];
                            }

                        } else {
                            window.parent.jAlert(data.result.msg);
                        }
                    },
                    statusCode: {
                        404: function () {
                            alert('服务端发生错误');
                        }, 500: function () {
                            alert('服务端发生错误');
                        }
                    },
                    error: function () {

                    }
                });
            }
        });
        return false;
    });
    var path_array = ['/', '/showroom', '/crowds', '/custom', '/star'];
    var path = location.pathname;
    var not_show_top_ad = $.cookie('not_show_top_ad');
    var not_show_top_big_ad = $.cookie('not_show_top_big_ad');
    //$.removeCookie('not_show_top_ad');
    //$.removeCookie('not_show_top_big_ad');
    if ($.inArray(path, path_array) != -1 && not_show_top_ad == undefined) {
        var str = '<div id="top-wrap" style="position:relative;background:#f03552;width:100%;height:100px;overflow:hidden;text-align:center;">\
			<div id="small-ad" style="position:absolute;width:1920px;left:50%;margin-left:-960px;overflow:hidden;z-index:2;text-align:center;">\
			<a href="javascript:" id="" class="top-ad-close" style="right:20px;"></a>\
			<img src="http://static.d2c.cn/img/home/150520/banner/01.jpg" width="1095" height="105" alt="" /></div>\
			<div id="big-ad" style="height:696px;width:100%;position:absolute;width:1920px;left:50%;margin-left:-960px;overflow:hidden;z-index:1;background:#f03552">\
			<img src="http://static.d2c.cn/img/home/150520/banner/02.jpg" width="1235" height="696" alt="" />\
			</div>\
		</div>';
        var small_h = 100, big_h = 800;
        $('body').prepend(str);
        if (not_show_top_big_ad == undefined) {
            var st = setTimeout(function () {
                $('#small-ad').slideUp();
                $('#top-wrap').animate({'height': big_h});
                $.cookie('not_show_top_big_ad', '1', {expires: 1});
            }, 500);
            var bt = setTimeout(function () {
                $('#top-wrap').animate({'height': small_h}, function () {
                    $('#small-ad').slideDown();
                });
            }, 6500);
        }

        $('#small-ad').hover(function () {
            clearTimeout(st);
            st = setTimeout(function () {
                $('#small-ad').slideUp();
                $('#top-wrap').animate({'height': big_h});
            }, 1000);
        }, function () {
            clearTimeout(st);
        });
        $('#big-ad').hover(function () {
        }, function () {
            clearTimeout(bt);
            $('#small-ad').slideDown();
            $('#top-wrap').animate({'height': small_h});
        });

        $('.top-ad-close').click(function () {
            $(this).parent().parent().remove();
            $.cookie('not_show_top_ad', '1', {expires: 1})
        });
    }

    /*
    if (!browser.versions.trident){
        function snowFall(snow) {
            snow = snow || {};
            this.maxFlake = snow.maxFlake || 200;
            this.flakeSize = snow.flakeSize || 10;
            this.fallSpeed = snow.fallSpeed || 1;
            this.status = 0;
        }

        requestAnimationFrame = window.requestAnimationFrame ||
                                window.mozRequestAnimationFrame ||
                                window.webkitRequestAnimationFrame ||
                                window.msRequestAnimationFrame || 
                                window.oRequestAnimationFrame ||
                                function(callback) { setTimeout(callback, 1000 / 60); };
        cancelAnimationFrame = window.cancelAnimationFrame ||
                                window.mozCancelAnimationFrame ||
                                window.webkitCancelAnimationFrame ||
                                window.msCancelAnimationFrame || 
                                window.oCancelAnimationFrame;

        snowFall.prototype.start = function(){
            if(this.status == 1 || this.status == 4){
                return false;
            }
            this.status = 1;
            snowCanvas.apply(this);
            createFlakes.apply(this);
            drawSnow.apply(this)
        }

        snowFall.prototype.stop = function(){
            if(this.status == 2 || this.status == 0 || !this.canvas){
                return false;
            }
            this.pause();
            this.status = 2;
            this.canvas.parentNode.removeChild(this.canvas);
            this.canvas = null;
        }

        snowFall.prototype.pause = function(){
            if(this.status == 3){
                return false;
            }
            this.status = 3;
            cancelAnimationFrame(this.loop)
        };
        snowFall.prototype.resume = function(){
            if(this.status == 3 && this.canvas){
                this.status = 4;
                this.loop = requestAnimationFrame(function() {
                    drawSnow.apply(that)
                });
            }
        };
        

        function snowCanvas() {
            var snowcanvas = document.createElement("canvas");
            snowcanvas.id = "snowfall";
            snowcanvas.width = window.innerWidth;
            snowcanvas.height = window.innerHeight;
            snowcanvas.setAttribute("style", "position: fixed; top: 0; left: 0; z-index: 2999; pointer-events: none;");
            document.getElementsByTagName("body")[0].appendChild(snowcanvas);
            this.canvas = snowcanvas;
            this.ctx = snowcanvas.getContext("2d");
            window.onresize = function() {
                snowcanvas.width = window.innerWidth;
                snowcanvas.height = window.innerHeight
            }
        }

        function flakeMove(canvasWidth, canvasHeight, flakeSize, fallSpeed) {
            this.x = Math.floor(Math.random() * canvasWidth);
            this.y = Math.floor(Math.random() * canvasHeight);
            this.size = Math.random() * flakeSize + 2;
            this.maxSize = flakeSize;
            this.speed = Math.random() * 1 + fallSpeed;	
            this.fallSpeed = fallSpeed;
            this.velY = this.speed;
            this.velX = 0;
            this.stepSize = Math.random() / 30;
            this.step = 0
        }

        flakeMove.prototype.update = function() {
            var x = this.x,
                y = this.y;

            this.velX *= 0.98;
            if (this.velY <= this.speed) {
                this.velY = this.speed
            }
            this.velX += Math.cos(this.step += .05) * this.stepSize;

            this.y += this.velY;
            this.x += this.velX;
            if (this.x >= canvas.width || this.x <= 0 || this.y >= canvas.height || this.y <= 0) {
                this.reset(canvas.width, canvas.height)
            }
        };

        flakeMove.prototype.reset = function(width, height) {
            this.x = Math.floor(Math.random() * width);
            this.y = 0;
            this.size = Math.random() * this.maxSize + 2;
            this.speed = Math.random() * 1 + this.fallSpeed;
            this.velY = this.speed;
            this.velX = 0;
        };

        flakeMove.prototype.render = function(ctx) {
            var snowFlake = ctx.createRadialGradient(this.x, this.y, 0, this.x, this.y, this.size);
            snowFlake.addColorStop(0, "rgba(255, 255, 255, 0.9)");
            snowFlake.addColorStop(.5, "rgba(255, 255, 255, 0.5)");
            snowFlake.addColorStop(1, "rgba(255, 255, 255, 0)");
            ctx.save();
            ctx.fillStyle = snowFlake;
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
                ctx.fill();
                ctx.restore();
            };

            function createFlakes() {
                var maxFlake = this.maxFlake,
                    flakes = this.flakes = [],
                    canvas = this.canvas;
                for (var i = 0; i < maxFlake; i++) {
                    flakes.push(new flakeMove(canvas.width, canvas.height, this.flakeSize, this.fallSpeed))
                }
            }

            function drawSnow() {
                var maxFlake = this.maxFlake,
                    flakes = this.flakes;
                ctx = this.ctx, canvas = this.canvas, that = this;
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                for (var e = 0; e < maxFlake; e++) {
                    flakes[e].update();
                    flakes[e].render(ctx);
                }
                this.loop = requestAnimationFrame(function() {
                    drawSnow.apply(that);
                });
            }
            var snow = new snowFall({maxFlake:150});
            snow.start();
    }else{
        var $flake 			= $('<div id="snowbox" />').css({'position': 'absolute', 'top': '-50px'}).html('&#10052;'),
            documentHeight 	= $(document).height(),
            documentWidth	= $(document).width(),
            defaults		= {
                                minSize		: 5,		//雪花的最小尺寸
                                maxSize		: 23,		//雪花的最大尺寸
                                newOn		: 500,		//雪花出现的频率
                                flakeColor	: "#FFFFFF"	
                            },
            options			= $.extend({}, defaults, options);
        
        var interval		= setInterval( function(){
            var startPositionLeft 	= Math.random() * documentWidth - 100,
                 startOpacity		= 0.5 + Math.random(),
                sizeFlake			= options.minSize + Math.random() * options.maxSize,
                endPositionTop		= documentHeight - 40,
                endPositionLeft		= startPositionLeft - 100 + Math.random() * 500,
                durationFall		= documentHeight * 10 + Math.random() * 5000;
            $flake.clone().appendTo('body').css({
                        left: startPositionLeft,
                        opacity: startOpacity,
                        'font-size': sizeFlake,
                        color: options.flakeColor
                    }).animate({
                        top: endPositionTop,
                        left: endPositionLeft,
                        opacity: 0.2
                    },durationFall,'linear',function(){
                        $(this).remove()
                    }
                );
                
        }, options.newOn);
    }	
    var path=location.pathname;
    var xmas_ad=$.cookie('xmas_ad');
    var path_array=['/'];
    
    if ($.inArray(path,path_array)!=-1){
        $('body').append('<div class="xmas-tree"><audio id="audio-media" loop="loop"></audio><span class="xmas-close"></span><a href="javascript:" class="play-audio"><i class="icon-music"></i></a><img src="http://static.d2c.cn/img/home/141222/merry.png" alt="点击播放音乐" /></div>');	
        $('.play-audio').click(function(){
            var audio=$('#audio-media')[0];
            if (!audio.src){
                audio.src='http://static.d2c.cn/media/jingle_bells.mp3';
            }
            if (audio.paused){
                $(this).addClass('is-play');
                audio.play();
            }else{
                $(this).removeClass('is-play');
                audio.pause();
            }
        });
        $('.xmas-tree .xmas-close').click(function(){
            $('.xmas-tree').hide();
            $.cookie('xmas_ad','1', {expires:1});
            return false;
        });
    }
    */

    $('.place-holder-keyword').attr('placeholder', '搜索“蝴蝶卫衣”试试');

    function search_form_submit() {
        $('#header-search-form').submit();
    }

    $('#header-keyword').autocomplete(
        "/membersearch.json",
        {
            width: 220,
            onItemSelect: search_form_submit,
            cacheLength: 1,
            maxItemsToShow: 10,
            autoFill: false
        }
    );
    var t;
    $('.hover-menu').hover(function () {
        clearTimeout(t);
        var type = $(this).attr('data-type');
        t = setTimeout(function () {
            if ($('.nav-hover').css('display') == 'none') {
                $('.nav-hover').slideDown();
            }
            $('.nav-' + type).siblings().hide();
            $('.nav-' + type).show();
        }, '200');
    }, function () {
        clearTimeout(t);
        var type = $(this).attr('data-type');
        t = setTimeout(function () {
            $('.nav-hover').slideUp('fast');
        }, '200');
    });
    $('.nav-hover').hover(function () {
        clearTimeout(t);
    }, function () {
        t = setTimeout(function () {
            $('.nav-hover').slideUp('fast');
        }, '200');
    });

    //ajax取标签产品
    if ($('.ajax-product-tag').size() > 0) {
        $.each($('.ajax-product-tag'), function (i, d) {
            var tag = $(this).attr('data-tag');
            $(this).attr('data-url', '/product/tags?pageSize=100&tagId=' + tag + '&' + new Date().getTime());
        });
        $('.ajax-product-tag').lazyload();
    }


    //处于购物车页面则不执行
    if (location.href.indexOf('cart/list') == -1) {
        $('.user-bar-cart-num').mouseover(function () {
            var ready = $('#user-bar-cart').data('ready');
            var change = $('#cart-nums-id').data('change');
            $(this).find('a.parent').addClass('on');
            $('#user-bar-cart').show();
            if (ready == undefined || change == 1) {
                get_cart_list();
                $('#user-bar-cart').data('ready', 1);
                $('#cart-nums-id').data('change', 0)
            }
        }).mouseout(function () {
            $(this).find('a.parent').removeClass('on');
            $('#user-bar-cart').hide();
        });

        $('input[name=go_cart]').live('click', function () {
            location.href = '/cart/list';
        });


        $('#user-bar-cart a.del').live('click', function () {
            var id = $(this).attr('rel');
            jConfirm('确定要把这件商品移出购物车吗？', '', function (r) {
                if (r) {
                    $.ajax({
                        'url': '/cart/delete/' + id,
                        'type': 'delete',
                        success: function (data) {
                            get_cart_list();
                            count_cart();
                        }
                    });
                }
            });

            return false;
        });
    }

    $('.click-get-coupon').click(function () {
        var code = $(this).attr('data-code');
        if (code) {
            $.getJSON('/member/islogin', function (data) {
                if (data.loggedIn == false) {
                    $('body').data('function', '$(".click-get-coupon[data-code=\'' + code + '\']").trigger("click")');
                    user_login();
                } else {
                    $.getJSON('/coupon/receive/' + code, function (data) {
                        jAlert(data.result.msg);
                    });
                }
            });
        }
        return false;
    });
    $('.img-replace').hover(function () {
        var src = $(this).attr('src'), replace = $(this).attr('data-replace')
        $(this).attr('src', replace);
        $(this).attr('data-replace', src);
    }, function () {
        var src = $(this).attr('src'), replace = $(this).attr('data-replace')
        $(this).attr('src', replace);
        $(this).attr('data-replace', src);
    });

    //加载的时候添加购物袋上的数量
    if ($("#cart-nums-id").size() > 0) {
        count_cart();
    }
    /*
    $('.bookmark').click(function(){
        var ctrl = (navigator.userAgent.toLowerCase()).indexOf('mac') != -1 ? 'Command/Cmd':'Ctrl';
        if(document.all) {
           window.external.addFavorite(window.location,document.title);
        }else if(window.sidebar){ 
            window.sidebar.addPanel(document.title,window.location);
        }else{
            jAlert('该浏览器不支持自动添加，请Ctrl+D手动加入收藏夹');
        }
        return false;
    });*/
    $('.header-search .select-span,.header-search .select-div').hover(function () {
        $('.header-search .select-div').show();
    }, function () {
        $('.header-search .select-div').hide();
    });
    $('.header-search .select-div a').click(function () {
        var id = $(this).attr('rel'),
            name = $(this).text();
        $(this).addClass('selected');
        $(this).siblings().removeClass('selected');
        $(this).prependTo($('.header-search .select-div'));
        $('input[name=type]').val(id);
        $('.header-search .select-div').prev('span').text(name);
        $('.header-search .select-div').hide();
        return false;
    });
    $('.notice-tip a.close').click(function () {
        $(this).parent().fadeOut();
        return false;
    });
    //分享按钮		
    $('.share-box a.share').click(function () {
        var appkey = new Array();
        appkey['sina'] = '1698482307';
        appkey['qq'] = '';
        appkey['qzone'] = '';
        var obj = $(this).parent();
        var config = eval('(' + obj.attr('data-config') + ')');
        ;
        if (config.url == '' || config.url == undefined) config.url = location.href;
        if (config.title == '' || config.title == undefined) config.title = document.title;
        var type = $(this).attr('class').replace('share ', '');
        var open_url;
        if (type == 'weixin') {
            $.getScript("/static/c/js/jquery.qrcode.min.js", function () {
                var str = '<div class="share-to-weixin">\
					<p class="title"><a class="share-box-close" href="javascript:" onclick="$(this).parent().parent().remove();"></a>分享给微信好友和朋友圈</p>\
					<p class="barcode"></p>\
					<p class="info">打开微信，点击“发现”，使用 “扫一扫” 即可将网页分享到我的朋友圈。</p>\
					</div>';
                obj.append(str);
                obj.find('.barcode').qrcode({width: 200, height: 200, text: config.url});
            });
            return false;
        }
        if (type == 'weibo') {
            open_url = 'http://service.weibo.com/share/share.php?url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic) + '&appkey=1698482307&searchPic=false';
        }
        if (type == 'qq') {
            open_url = 'http://connect.qq.com/widget/shareqq/index.html?url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&desc=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic) + '&summary=&site=d2cmall';
        }
        if (type == 'qzone') {
            open_url = 'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&desc=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic) + '&summary=&site=';
        }
        if (type == 'renren') {
            open_url = 'http://widget.renren.com/dialog/share?resourceUrl=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&description=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic);
        }
        if (type == 'douban') {
            open_url = 'http://www.douban.com/share/service?href=' + encodeURIComponent(config.url) + '&name=' + encodeURIComponent(config.title) + '&text=' + encodeURIComponent(config.desc) + '&image=' + encodeURIComponent(config.pic);
        }
        if (type == 'tqq') {
            open_url = 'http://share.v.t.qq.com/index.php?c=share&a=index&url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&text=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic);
        }
        window.open(open_url);
        return false;
    });

    $('.show-nav-tip').hover(function () {
        $(this).siblings('.nav-tip').fadeIn();
    }, function () {
        $(this).siblings('.nav-tip').fadeOut();
    });

    //返回顶部
    if ($('.gotop').size() > 0) {
        $(window).bind("scroll", function () { //浏览器滚动条触发事件
            var scrollTop = $(window).scrollTop();
            if (scrollTop > 0) {
                $('.gotop').show();
            } else {
                $('.gotop').hide();
            }
        });
        //返回顶部响应事件
        $('.gotop').click(function () {
            $(this).addClass('gotop-over');
            $('html, body').animate({scrollTop: 0}, 300);
            var current = $(this);
            setTimeout(function () {
                current.hide();
                current.removeClass('gotop-over');
            }, 500);
            return false;
        });
    }
    if (!browser.versions.mobile) {
        if ($('.fixed-header').size() > 0) {
            var offset_top = $('#header-nav').offset().top;
            $(window).bind("scroll", function () { //浏览器滚动条触发事件
                var scrollTop = $(window).scrollTop();
                if (scrollTop > offset_top) {
                    $('#header-nav').css({'position': 'fixed', 'top': '0'});
                    $('#header-nav .nav-notice').hide();
                } else {
                    $('#header-nav').css({'position': 'relative', 'top': '0'});
                    $('#header-nav .nav-notice').show();
                }
            });
        }
        if ($('#letter-top').size() > 0) {
            var letter_offset_top = $('#letter-top').offset().top;
            $(window).bind("scroll", function () { //浏览器滚动条触发事件
                var scrollTop = $(window).scrollTop();
                if (scrollTop > letter_offset_top - 33) {
                    $('#letter-top').css({'position': 'fixed', 'top': '37px'});
                } else {
                    $('#letter-top').css({'position': 'relative', 'top': '0'});
                }
            });
        }
        if ($('.category-menu-v').size() > 0) {
            var doc_height = $(document).height() - 480,
                obj_height = $('.category-menu-v').height();

            if ($('.category-menu-v').height() > $(window).height() - 60) {

            } else {
                $(window).bind("scroll", function () {
                    var scrollTop = $(window).scrollTop();

                    var style = 'fixed';
                    if (scrollTop > 120) {
                        $('.category-menu-v').removeClass('absolute').addClass(style);
                    } else {
                        $('.category-menu-v').removeClass('fixed').removeClass('fixed-165').removeClass('absolute');
                    }
                    if (doc_height - obj_height <= $('.category-menu-v').position().top) {
                        $('.category-menu-v').removeClass(style).addClass('absolute');
                    }
                });
            }
        }
    }
    if ($(window).width() < 1870) {

        $('.service-suspend').addClass('suspend-1280');

        $('.service-suspend .suspend-side').hover(function () {
            $('.service-suspend').stop().animate({'right': '0px'});
            $('.service-suspend .suspend-side').fadeOut();
        });
        $('.service-suspend').hover(function () {
        }, function () {
            $('.service-suspend').stop().animate({'right': '-137px'});
            $('.service-suspend .suspend-side').fadeIn();
        });
        var is_read_tip = $.cookie('suspend_tip');
        if (is_read_tip != 1) {
            $('.service-suspend .suspend-tip').show();
        }
        /*
        $('.service-suspend .suspend-side').hover(function(){
            $('.service-suspend').animate({'right':'0px'});
            //$('.service-suspend .suspend-side').hide();
            return false;
        });
        $('.service-suspend').hover(function(){
        },function(){
            $('.service-suspend').animate({'right':'-150px'});
            $('.service-suspend .suspend-side').show();
        });*/
    } else {
        $('.service-suspend').addClass('suspend-1600');
        $('.service-suspend .suspend-side').hide();
    }

    $('.service-qq-icon').hover(function () {
        $(this).find('.service-qq').show();
    }, function () {
        $(this).find('.service-qq').hide();
    });

    $('.tip-close').click(function () {
        $(this).parent().hide();
        $.cookie('suspend_tip', '1', {expires: 10});
        return false;
    });
    if ($('.float-navs').size() > 0) {
        var offset_top_float = $('.float-navs').offset().top;

        $(window).bind("scroll", function () { //浏览器滚动条触发事件
            var obj_point = new Array(),
                obj_point_top = new Array(),
                obj_point_scope = new Array();
            $.each($('.float-navs a'), function (i, d) {
                var id = $(d).attr('href');
                if (id.indexOf('#') != -1) {
                    if ($(id).size() == 0) {
                        //$(d).remove();
                    } else {
                        obj_point_top.push($(id).offset().top - 64);
                        obj_point_scope.push($(id).offset().top + $(id).height() - 64);
                        obj_point.push(id);
                    }
                }
            });
            var scrollTop = $(window).scrollTop();
            if (scrollTop > offset_top_float - 50) {
                $('.float-navs').addClass('fixed');
            } else {
                $('.float-navs').removeClass('fixed');
            }
            $('.float-navs a').removeClass('on');
            var i = 0;
            $.each(obj_point, function (index, objs) {
                if (scrollTop >= obj_point_top[index]) {
                    i = index;
                }
            });
            $('.float-navs a[href=' + obj_point[i] + ']').addClass('on');
        });

        $('.float-navs a').click(function () {
            if ($(this).attr('href').indexOf('#') != -1) {
                var id = $(this).attr('href').replace('#', '');
                var obj_height = $('#' + id).offset().top;
                $('html, body').animate({scrollTop: obj_height - 25}, 300);
                return false;
            }
        });

        $('.float-search-icon,.float-search').hover(function () {
            $('.float-search').addClass('show');
        }, function () {
            $('.float-search').removeClass('show');
        });
    }
    if ($('.nav-notice').size() > 0) {
        $.getJSON('/news', function (result) {
            var data = result.result.data.news;
            var str = '';
            $.each(data, function (i, d) {
                str += '<li>▪ <a href="/news/' + d.id + '" target="_blank">' + d.title + '</a></li>';
            });
            $('.nav-notice ul').html(str);
        });
    }

    //关注按钮
    $(".op-attention").die().live('click', function () {
        var obj = $(this),
            did = obj.attr('data-id'),
            url = obj.attr('href'),
            num = parseInt(obj.find('strong').text()),
            plus = 'plus-two-' + did;
        $.getJSON('/member/islogin', function (data) {
            if (data.loggedIn == false) {
                $('body').data('function', '$(".op-attention[data-id=' + did + ']").trigger("click")');
                user_login();
            } else {
                $.post(url + '?' + new Date().getTime(), function (data) {
                    if (data.status != 0) {
                        obj.find('strong').text(num + 1);
                        if ($('#' + plus).size() == 0) {
                            str = '<div id="' + plus + '" class="abs-ani-tip" style="top:' + (obj.offset().top - 100) + 'px;left:' + (obj.offset().left + obj.outerWidth() / 2 - 20) + 'px;">+1</div>';
                            $('body').append(str);
                        }
                        $('#' + plus).animate({
                            fontSize: '0px',
                            top: obj.offset().top + 5,
                            left: (obj.offset().left + obj.outerWidth() / 2),
                            opacity: 0
                        }, '2500', function () {
                            $(this).remove();
                        });
                    } else {
                        if ($('#h-' + plus).size() == 0) {
                            str = '<div id="h-' + plus + '" class="abs-tip" style="top:' + (obj.offset().top - 40) + 'px;left:' + (parseFloat(obj.offset().left) + (parseFloat(obj.outerWidth()) / 2) - 60) + 'px;">你已经关注过了</div>';
                            $('body').append(str);
                        } else {
                            $('#h-' + plus).show();
                        }
                        setTimeout(function () {
                            $('#h-' + plus).hide();
                        }, 2000);
                        obj.addClass('disabled');
                    }
                }, 'json');
            }
        });
        return false;
    });

    //喜欢，关注
    $(".op-like,.button-fav-detail,.favorite").die().live('click', function () {
        var obj = $(this), str = '',
            did = obj.attr('data-id'),
            url = obj.attr('href'),
            num = parseInt(obj.find('strong').text()),
            classname = $(this).attr('class'),
            plus = 'plus-one-' + did;
        $.getJSON('/member/islogin', function (data) {
            if (data.loggedIn == false) {
                $('body').data('function', '$(".' + classname + '[data-id=' + did + ']").trigger("click")');
                user_login();
            } else {
                $.post(url + '?' + new Date().getTime(), function (data) {
                    if (data.count != 0) {
                        obj.find('strong').text(num + 1);
                        if ($('#' + plus).size() == 0) {
                            str = '<div id="' + plus + '" class="abs-ani-tip" style="top:' + (obj.offset().top - 100) + 'px;left:' + (obj.offset().left + obj.outerWidth() / 2 - 20) + 'px;">+1</div>';
                            $('body').append(str);
                        }
                        $('#' + plus).animate({
                            fontSize: '0px',
                            top: obj.offset().top + 5,
                            left: (obj.offset().left + obj.outerWidth() / 2),
                            opacity: 0
                        }, '2500', function () {
                            $(this).remove();
                        });
                    } else {
                        if ($('#h-' + plus).size() == 0) {
                            str = '<div id="h-' + plus + '" class="abs-tip" style="top:' + (obj.offset().top - 40) + 'px;left:' + (parseFloat(obj.offset().left) + (parseFloat(obj.outerWidth()) / 2) - 60) + 'px;">你已经' + data.msg + '过了</div>';
                            $('body').append(str);
                        } else {
                            $('#h-' + plus).show();
                        }
                        setTimeout(function () {
                            $('#h-' + plus).remove();
                        }, 2000);
                    }
                }, 'json');
            }
        });
        return false;
    });

    //收藏
    $(".bookmark-bom,.bookmark-top").die().live('click', function () {
        var obj = $(this),
            did = obj.attr('data-id'),
            url = obj.attr('href'),
            classname = $(this).attr('class');
        $.getJSON('/member/islogin', function (data) {
            if (data.loggedIn == false) {
                $('body').data('function', '$(".' + classname + '[data-id=' + did + ']").trigger("click")');
                user_login();
            } else {
                $.post(url + '?' + new Date().getTime(), function (data) {
                    if (data.count != 0) {
                        jAlert('该商品成功加入到我的收藏。');
                    } else {
                        jAlert('该商品之前已经加入过收藏了。');
                    }
                }, 'json');
            }
        });
        return false;
    });

    $('.lazyload,.lazyload img').lazyload();
    /*
    $('.login-app a,.quick-login a').die().live('click',function(){
        var url=$(this).attr('href');
        var width=700,height=600;
        var left = (screen.width) ? (screen.width-width)/2 : 0;
        var top = (screen.height) ? (screen.height-height)/2 : 0;
        window.open(url,'loginByApp','width='+width+',height='+height+',left=' + left + ',top=' + top + ',toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no');
        return false;
    });*/

    $('.lookbook-item-img').die().live('click', function () {
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-url');
        var prev_id = $(this).parent().prev().find('.lookbook-item-img').attr('data-id');
        var next_id = $(this).parent().next().find('.lookbook-item-img').attr('data-id');
        var round_ids = new Object();
        round_ids.current = id;
        round_ids.prev = prev_id;
        round_ids.next = next_id;

        $.getJSON('/showroom/designerComposition/' + id, function (result) {
            var data = result.designerComposition;
            $.viewbox({url: url, data: data, ids: round_ids});
        });
        return false;
    });

    /*评论*/
    $('.comment-form .text-area').live('focus', function () {
        var text = $(this).text();
        var placeholder = $(this).attr('placeholder');
        if (text == placeholder) {
            $(this).removeClass('grey').text('');
        }
    }).live('blur', function () {
        var text = $(this).text();
        var placeholder = $(this).attr('placeholder');
        if (text == '') {
            $(this).addClass('grey').text(placeholder);
        }
    });
    $('.comment-form .post-bar button').live('click', function () {
        var obj = $(this);
        var obj_area = obj.parent().siblings('.text-area')
        var text = obj_area.text();
        var id = obj.parent().parent().attr('data-id');
        var type = obj.parent().parent().attr('data-type');
        var placeholder = obj_area.attr('placeholder');
        if (text == placeholder || text == '') {
            obj_area.focus().text('');
            return false;
        }
        if (id && type && text) {
            $.getJSON('/member/islogin', function (data) {
                if (data.loggedIn == false) {
                    $('body').data('function', '$(".comment-form .post-bar button").trigger("click")');
                    user_login();
                } else {
                    $.post('/comment/create?', {sourceId: id, source: type, content: text}, function () {
                        comment_list($('.comment-list'));
                        obj_area.text(placeholder).addClass('grey');
                    });
                }
            });
        }
        return false;
    });
    $('.tab-menu a').click(function () {
        $(this).addClass('on').siblings().removeClass('on');
        var id = $(this).attr('data-id');
        $('.tab-cont .tab-cont-data').hide();
        $('.tab-cont .tab-cont-data[data-id=' + id + ']').show();
        return false;
    });
    if ($(".count-down").size() > 0) {
        $(".count-down").each(function () {
            $(this).countdown();
        });
    }

    //提醒
    $('button[name=alert_mobile]').click(function () {
        var code = $(this).siblings('.code'),
            pid = $(this).siblings('.pid'),
            type = $(this).attr('data-type');
        name = $(this).attr('data-title');
        if (code.val() == code.attr('placeholder')) {
            code.val('').focus();
            return false;
        }
        var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        var re_mobile = /^[1][3458]\d{9}$/;
        if (!re_email.test(code.val()) && !re_mobile.test(code.val())) {
            code.focus();
            return false;
        } else {
            $.post('/remind', {
                productId: pid.val(),
                remindType: type,
                mobilemail: code.val(),
                name: name
            }, function (data) {
                if (data.result.status == 1) {
                    jAlert('您已经成功添加了短信或邮箱提醒。');
                } else {
                    jAlert(data.result.msg);
                }
            }, 'json');
        }
        return false;
    });
    $('button[name=alert_qq]').click(function () {
        QQ_clock($(this).attr('data-title'), $(this).attr('data-time'), 5, $(this).attr('data-url'));
    });

    $('#update-profile-click').click(function () {
        $.mybox({url: '/member/updateProfile?' + new Date().getTime(), 'datatype': 'html'});
    });

    /*
    $.each($('*[data-spam]'),function(i,obj){
        var spam=$(this).attr('data-spam');
        var m=0;
        $.each($(obj).find('a'),function(ii,o){
            var url=$(o).attr('href');
            var str='';
            if (url!='#'&&url.indexOf('javascript:')==-1){
                m++;
                if (url.indexOf('#')!=-1){
                    var url_arr=url.split('#');
                    $(o).attr('href',url_arr[0]+'?spam='+spam+'.'+(m)+'#'+url_arr[1]);	
                }else{
                    str=url.indexOf('?')!=-1?'&':'?';
                    $(o).attr('href',url+str+'spam='+spam+'.'+(m));		
                }	
            }			
        });		
    });*/

    $('.video-player').click(function () {
        var video_path = $(this).attr('data-video');
        var video_apple_path = $(this).attr('data-apple-video');
        var str = '';
        if (!browser.versions.iPad || video_apple_path == undefined) {
            str = '<embed width="100%" height="100%" pluginspage="http://www.macromedia.com/go/getflashplayer" src="http://static.d2c.cn/common/o/flvplayer.swf" type="application/x-shockwave-flash" allowfullscreen="true" flashvars="vcastr_file=' + video_path + '&amp;LogoText=&amp;IsAutoPlay=1" quality="high" />';
        } else {
            str = '<video src="' + video_apple_path + '" width="100%" height="100%" autoplay="autoplay" style="background:#000;"></video>';
        }
        $(this).parent().html(str);
        return false;
    });
    $.each($('.video-player'), function (i, d) {
        if ($(d).attr('data-auto') == '1') {
            $(d).trigger('click');
        }
    });

    //hover显示，其他缩进
    $('.d2c-slide-hover').hover(function () {
        $(this).parent().find('.d2c-slide-hover').removeClass('on');
        $(this).addClass('on');
    });

    $('.d2c-click-nav a').click(function () {
        var id = $(this).attr('data-id');
        if (id) {
            $('.d2c-click-nav a').removeClass('on');
            $(this).addClass('on');
            $('.d2c-click-show a').removeClass('on');
            $('.d2c-click-show a[data-id=' + id + ']').addClass('on');
            return false;
        }
    });

    $('.star-style-name a').click(function () {
        var id = $(this).attr('data-id');
        if (id) {
            $('.star-style-name a').siblings().removeClass('on');
            $(this).addClass('on');
            $(this).parent().parent().siblings('.star-style-img').find('a').removeClass('on');
            $(this).parent().parent().siblings('.star-style-img').find('a[data-id=' + id + ']').addClass('on');
            return false;
        }
    });

    //邮件订阅
    $('#subscribe-email').submit(function () {
        var email = $(this).find('input[name=email]');

        if (email.val() == '') {
            email.focus();
            $('.subscribe-tip').show().html('请输入Email地址');
            return false;
        }
        if (!email.email()) {
            $('.subscribe-tip').show().html('请输入有效的Email地址');
            return false;
        }
        $('.subscribe-tip').hide();
        $.post($(this).attr('action'), $(this).serialize(), function (data) {
            if (data.result.status == 1) {
                jAlert('邮件订阅成功！');
            } else {
                jAlert('该email已经订阅过邮件了。');
            }
        }, 'json');
        return false;
    });

    //首页实体店
    if ($('#store-list').size() > 0) {
        var store_list = [
            {
                'city': '北京',
                'name': '来福士',
                'address': '北京市东城区东直门南大街1号来福士购物中心2层09铺',
                'tel': '010-84098064',
                'img': 'http://static.d2c.cn/img/home/150420/store/01.jpg',
                'href': '/store/code/5'
            },
            {
                'city': '重庆',
                'name': '万象城	',
                'address': '重庆市九龙坡区重庆华润中心万象城第 LG层LG193号商铺',
                'tel': '023-68419856',
                'img': 'http://static.d2c.cn/img/home/150420/store/02.jpg',
                'href': '/store/code/17'
            },
            {
                'city': '北京',
                'name': '国贸',
                'address': '北京市朝阳区建国门外大街1号国贸商城三期负一楼3B 109 110商铺',
                'tel': '010-85351656',
                'img': 'http://static.d2c.cn/img/home/150420/store/03.jpg',
                'href': '/store/code/22'
            },
            {
                'city': '重庆',
                'name': '盈嘉中心',
                'address': '重庆市江北观音桥步行街2号融恒时代广场盈嘉中心L3-13号',
                'tel': '023-67694975',
                'img': 'http://static.d2c.cn/img/home/150420/store/04.jpg',
                'href': '/store/code/18'
            },
            {
                'city': '北京',
                'name': '蓝色港湾',
                'address': '北京市朝阳区朝阳公园路6号院2号楼',
                'tel': '010-59056133',
                'img': 'http://static.d2c.cn/img/home/150420/store/05.jpg',
                'href': '/store/code/9'
            },
            {
                'city': '南京',
                'name': '德基',
                'address': '南京市中山路18号德基广场二期F415号商铺',
                'tel': '025-86777422',
                'img': 'http://static.d2c.cn/img/home/150420/store/06.jpg',
                'href': '/store/code/19'
            },
            {
                'city': '北京',
                'name': '朝北大悦城',
                'address': '北京市朝阳区朝阳北路101号朝阳大悦城2F-06号',
                'tel': '010-85519493',
                'img': 'http://static.d2c.cn/img/home/150420/store/07.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '北京',
                'name': '新光天地',
                'address': '北京市朝阳区建国路87号华贸商城（新光天地）B1082  D2C专柜',
                'tel': '010-57381342',
                'img': 'http://static.d2c.cn/img/home/150420/store/09.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '南通',
                'name': '八佰伴',
                'address': '南通市人民中路45号八佰伴1楼',
                'tel': '0513-89093776',
                'img': 'http://static.d2c.cn/img/home/150420/store/10.jpg?0.1',
                'href': '/store/code/16'
            },
            {
                'city': '杭州',
                'name': '湖滨银泰',
                'address': '浙江省杭州市上城区延安路245号湖滨银泰四期B1-122',
                'tel': '0571-89779375',
                'img': 'http://static.d2c.cn/img/home/150420/store/11.jpg',
                'href': '/store/code/10'
            },
            {
                'city': '昆明',
                'name': '顺城',
                'address': '昆明市崇仁街9号顺城购物中心E2-08A',
                'tel': '0871-63637720',
                'img': 'http://static.d2c.cn/img/home/150420/store/12.jpg',
                'href': '/store/code/8'
            },
            {
                'city': '杭州',
                'name': '万象城',
                'address': '浙江省杭州市江干区富春路万象城购物中心4楼487号商铺',
                'tel': '0571-81186238',
                'img': 'http://static.d2c.cn/img/home/150420/store/13.jpg',
                'href': '/store/code/4'
            },
            {
                'city': '深圳',
                'name': '海岸城',
                'address': '深圳市南山区文心五路33号海岸城购物中心二层203-205号铺',
                'tel': '0755-86359420',
                'img': 'http://static.d2c.cn/img/home/150420/store/14.jpg',
                'href': '/store/code/20'
            },
            {
                'city': '合肥',
                'name': '银泰',
                'address': '安徽省合肥市庐阳区 长江中路98号',
                'tel': '0551-65852159',
                'img': 'http://static.d2c.cn/img/home/150420/store/15.jpg',
                'href': '/store/code/13'
            },
            {
                'city': '大庆',
                'name': '松雷',
                'address': '大庆市让胡路区中原路3号 大庆松雷购物休闲广场',
                'tel': '0459-5926950',
                'img': 'http://static.d2c.cn/img/home/150420/store/17.jpg',
                'href': '/store/code/6'
            },
            {
                'city': '长春',
                'name': '卓展',
                'address': '吉林省长春市重庆路1255号卓展购物中心4楼',
                'tel': '0431-88483292',
                'img': 'http://static.d2c.cn/img/home/150420/store/18.jpg',
                'href': '/store/code/23'
            },
            {
                'city': '常州',
                'name': '吾悦广场',
                'address': '江苏省常州市武进区武宜北路19号武进吾悦广场1F-110',
                'tel': '0519-85523990',
                'img': 'http://static.d2c.cn/img/home/150420/store/19.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '石家庄',
                'name': '北国商城',
                'address': '石家庄中山东路188号北国商城三楼C区D2C',
                'tel': '0311-89669373',
                'img': 'http://static.d2c.cn/img/home/150420/store/20.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '温州',
                'name': '银泰',
                'address': '温州市解放南路9号温州世贸中心银泰2楼D2C',
                'tel': '0577-88008509',
                'img': 'http://static.d2c.cn/img/home/150420/store/21.jpg',
                'href': '/store/code/24'
            }
        ];
        var name_str = detail_str = address_str = '';
        $.each(store_list, function (i, store) {
            name_str += '<a href="javascript:"';
            if (i == 0) {
                name_str += ' class="on"';
                detail_str = '<div><img src="' + store.img + '" alt="' + store.city + store.name + '" /></div>';
                address_str = '<div class="store-address"><h3>' + store.city + store.name + '店</h3><p class="clearfix"><span class="float-left">地址：</span><span style="display:block;margin-left:35px;">' + store.address + '</span></p><p class="clearfix">电话：' + store.tel + '</p></div></div>';
            }
            name_str += ' data-id="' + i + '">' + store.city + '<em>' + store.name + '</em></a>';
        });
        $('#store-list').html(name_str);
        $('#store-detail').html(detail_str);
        $('#store-address').html(address_str);
        var t;
        $('#store-list a').hover(function () {
            var obj = $(this);
            t = setTimeout(function () {
                var i = obj.attr('data-id');
                obj.siblings().removeClass('on');
                obj.addClass('on');
                var store = store_list[i];
                detail_str = '<div><img src="' + store.img + '" alt="' + store.city + store.name + '" /></div>';
                $('#store-detail').html(detail_str);
                address_str = '<div class="store-address"><h3>' + store.city + store.name + '店</h3><p class="clearfix"><span class="float-left">地址：</span><span style="display:block;margin-left:35px;">' + store.address + '</span></p><p class="clearfix">电话：' + store.tel + '</p></div>';
                $('#store-address').html(address_str);
            }, '200');

        }, function () {
            clearTimeout(t);
        });
    }
    if ($('#home-active-banner').size() > 0) {
        var i = m = n = 0;
        var t;
        var speed = 2000;
        var h = 508;
        $.each($('.active-big-show img'), function (i, obj) {
            if ($(obj).attr('data-image')) {
                $(obj).attr("src", $(obj).attr('data-image')).removeAttr("data-image");
            }
        });
        t = setInterval(function () {
            $('.active-big-show').animate({top: -(h * m)});
            m = m + 1;
            if (m > 7) {
                m = 0;
            }
        }, speed);
        $(".active-small-show a").hover(function () {
            var active_index = $(this).index();
            m = active_index;
            n = setTimeout(function () {
                $('.active-big-show').animate({top: -(h * active_index)});
            }, 300);
        }, function () {
            clearTimeout(n);
        });
        $(".home-active").hover(function () {
                clearInterval(t);
            }, function () {
                t = setInterval(function () {
                    $('.active-big-show').animate({top: -(h * m)});
                    m++;
                    if (m > 7) {
                        m = 0;
                    }
                }, speed);
            }
        )
    }


    if ($('.crowd-crowditem-list').size() > 0) {
        $.each($('.crowd-crowditem-list'), function (i, n) {
            id = $(n).attr('data-id');
            x = $(n).attr('rel');
            $.ajax({
                url: '/crowds/itemlist/' + id + '?hover=' + x + '',
                type: 'get',
                success: function (data) {
                    $(n).html(data);
                }
            });
        });
    }
    if ($('.starshow').size() > 0) {
        var t;
        $(".starshow  ul > li:even").addClass("even");
        $(".starshow  ul li:eq(1)").css("background", "url(http://static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
        $(".starshow  ul li:eq(1)").find("a").append("<img src='http://static.d2c.cn/img/home/150422/star/img/tringle.png' class='left-t' width='4' height='64'/>");
        $(".starshow  ul li:eq(11)").css("background", "url(http://static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
        $(".starshow  ul li:eq(11)").find("a").append("<img src='http://static.d2c.cn/img/home/150422/star/img/tringle.png' class='left-t' width='4' height='64'/>");
        $('.starshow ul li a').hover(function () {
            var obj = $(this);
            t = setTimeout(function () {
                var href = obj.attr("href");
                obj.parent().parent().parent().find('.star-hover-img').attr('href', href);
                var url = obj.attr("data-rel");
                obj.parent().parent().parent().find('.pic-box ').attr('src', url);
                var tleft = "<img src='http://static.d2c.cn/img/home/150422/star/img/tringle.png'  class='left-t' width='4' height='64'/>";
                var tright = "<img src='http://static.d2c.cn/img/home/150422/star/img/tringle_2.png'  class='right-t' width='4' height='64'/>";
                var id = $(".starshow ul li a").index(obj);
                if (id % 2 == 1) {
                    if (obj.parent().find('.left-t').length == 0) {
                        obj.append(tleft);
                    }
                    obj.parent().css("background", "url(http://static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
                    obj.parent().siblings().css("background", "");
                    obj.parent().siblings().find('.left-t').remove();
                    obj.parent().siblings().find('.right-t').remove();

                } else {
                    if (obj.parent().find('.right-t').length == 0) {
                        obj.append(tright);
                    }
                    obj.parent().css("background", "url(http://static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
                    obj.parent().siblings().css("background", "");
                    obj.parent().siblings().find('.right-t').remove();
                    obj.parent().siblings().find('.left-t').remove();
                }
                ;
            }, 200);
        }, function () {
            clearTimeout(t);
        });
    }

    /*
    if($('.promotion-productlist-list').size()>0){
        $.each($('.promotion-productlist-list'), function(i, n){
            id=$(n).attr('data-id');
            x=$(n).attr('rel');
            $.ajax({
                url      : '/promotion/productlist/'+id+'?hover='+x+'',
                type     : 'get',
                success  : function(data){  
                    $(n).html(data);		
                }
            });	
        });	
    }*/
    $('.load-more').click(function () {
        var crowd = $(this).parent().prev('.promotion-productlist-list')
        if (crowd.hasClass('on')) {
            $(this).parent().prev('.promotion-productlist-list').removeClass('on');
        } else {
            $(this).parent().prev('.promotion-productlist-list').addClass('on');
        }
        ;
    });
});

//star show js

//end star show
function comment_list(obj) {
    var id = obj.attr('data-id');
    var type = obj.attr('data-type');
    $.get('/comment/list?' + new Date().getTime(), {sourceId: id, source: type}, function (data) {
        obj.html(data);
    });
}

//加入收藏喜欢操作
function favorite_like_action(type, id) {
    $('.add-action[rel=' + type + '][data-id=' + id + ']').trigger('click');
}

//加入收藏喜欢操作
function attention_action(id) {
    $('.designer-attention[data-id=' + id + ']').trigger('click');
}

//关闭弹出层
function close_ebox() {
    $('#ebox-ebox-inner .ebox-title a').trigger('click');
}

//登录
function user_login() {
    //判断当前是否是登录页面
    var url = '/member/login?' + new Date().getTime();
    $.ebox({'url': url, datatype: '', 'width': '700'});
    return false;
}

function get_cart_list() {
    $.get('/cart/html/select?' + Math.random(), function (data) {
        $('#user-bar-cart').html(data);
    });
}

function count_cart() {
    $.ajax({
        'url': '/cart/count?t=' + Math.random(),
        'type': 'get',
        'data': {},
        'dataType': 'json',
        'success': function (data, status) {
            $('#cart-nums-id').text(data.cartItemCount);
            $('#cart-nums-id').data('change', 1);
        }
    });
}

function format_currency(num) {
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

function QQ_clock(content, time, advance, url) {
    var width = 600, height = 480;
    var left = (screen.width) ? (screen.width - width) / 2 : 0;
    var top = (screen.height) ? (screen.height - height) / 2 : 0;
    window.open('http://qzs.qq.com/snsapp/app/bee/widget/open.htm#content=' + encodeURIComponent(content) + '&time=' + encodeURIComponent(time) + '&advance=' + advance + '&url=' + encodeURIComponent(url), 'alert_window', 'width=' + width + ',height=' + height + ',left=' + left + ',top=' + top + ',toolbar=no,location=no');
}

