$(function () {
    /*var desktop=false;
    if(("standalone" in window.navigator) && window.navigator.standalone){     
        desktop==true;
    }
    if (!desktop){
        $('body').append('<div style="position:fixed;bottom:50px;left:10px;background:#FFF;right:10px;width:100%;padding:15px;">点击分享--添加到主屏幕</div>')
    }*/
    /*
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
    var snow = new snowFall({maxFlake:40});
    snow.start();
    */
    //var path_array=['/','/showroom','/crowds','/custom','/star'];
    var path = location.pathname;
    /*if (path.indexOf('/member')==-1 && path.indexOf('/o2o')==-1 && path.indexOf('/coupon/')==-1 && path.indexOf('/comment/')==-1 && path.indexOf('/cart')==-1 && path.indexOf('/report')==-1 && path.indexOf('/order')==-1){
        var bar_bottom=$('nav').size()>0?'4em':'0';
        $('body').append('<div style="position:fixed;bottom:'+bar_bottom+';left:0;right:0;background:rgba(0,0,0,0.7);padding:0.6em;">\
                <input type="input" name="mobile" id="mobile-suspend" placeholder="请输入手机号订阅周年庆提醒" style="vertical-align:middle;border:1px solid #FFF;padding:0.2em 0.6em 0 0.6em;height:2.4em;font-size:0.8em;border-radius:2px;width:65%;box-sizing:border-box">\
                <button name="subcript-alert" id="subcript-button-suspend" style="vertical-align:middle;margin-top:-1px;width:32%;padding:0.5em 0.6em 0.4em 0.6em;background:#e21938;height:2.4em;font-size:0.8em;border:1px solid #e21938;color:#FFF;box-sizing:border-box">订阅开始提醒</button>\
        </div>');
        $('#subcript-button-suspend').click(function(){
            var url='/subscribe/mobile';
            var mobile=$(this).siblings('#mobile-suspend').val();
            if (mobile){
                $.post(url,{mobile:mobile},function(data){
                    if (data.loggedIn==false){
                        $('body').data('function','$("#subcript-button-suspend").trigger("click")');
                        user_login();
                        location.href='#login';
                    }else{
                        if (data.result.status==1){
                            jAlert('周年庆开抢提醒设置成功！');
                        }else{
                            jAlert(data.result.msg);
                        }
                    }
                },'json');
                
            }else{
                jAlert('请输入正确的手机号！')
            }
        });
    }
    */
    $('.get-coupon').click(function () {
        var url = $(this).attr('data-url');
        $.getJSON(url, function (data) {
            if (data.loggedIn == false) {
                $('body').data('function', '$(".get-coupon[data-url=\'' + url + '\']").trigger("click")');
                user_login();
                location.href = '#login';
                return false;
            }
            if (data.status == 1) {
                jAlert(data.result.msg);
            } else {
                jAlert(data.result.msg);
            }
        });
        return false
    });
    $(window).resize(function () {
        $('#category-box').height($(window).height());
        $('.catrgory-list').height($(window).height() - $('.button-return').height());
    });
    $('.show-category').on('touchstart', function () {
        location.href = '#category';
        if ($('.box-mask').size() == 0) {
            $('body').append('<div class="box-mask"></div><div id="category-box"><div class="button-return"><span class="icon icon-chevron-left"></span> 返回</div><div class="catrgory-list"></div></div>');
        }
        var ua = navigator.userAgent.toLowerCase();
        $('#category-box').height($(window).height());
        $('.catrgory-list').height($(window).height() - $('.button-return').height() - 50);

        $('.box-mask').show();
        $('#category-box').show();
        $('body').css({'overflow': 'hidden', 'height': $(window).height()});
        if ($('.catrgory-list').html() == '') {
            $('.catrgory-list').html($('#category-nav-hide').html());
        }
        $('.button-return').on('touchstart', function () {
            category_hide();
            return false;
        });
        $('.box-mask').on('touchstart', function () {
            category_hide()
            return false;
        });

        $('.catrgory-list dl dt').on('touchstart', function () {
            $(this).parent().addClass('on');
            $(this).parent().siblings().removeClass('on');
            return false;
        });
        return false;
    });

    $(".lazyload img").unveil(300);

    if ($(".count-down").size() > 0) {
        $(".count-down").each(function () {
            $(this).countdown();
        });
    }

    $('.click-get-coupon').click(function () {
        var code = $(this).attr('data-code');
        if (code) {
            $.getJSON('/member', function (data) {
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

    $('#add-bookmark').on('touchstart', function () {
        var obj = $(this),
            url = obj.attr('data-url'),
            obj_id = obj.attr('id');
        $.getJSON('/member', function (data) {
            if (data.loggedIn == false) {
                $('body').data('function', '$("' + obj_id + '").trigger("touchstart")');
                user_login();
                location.href = "#login";
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

    //关注设计师
    $('.op-fav').on('touchstart', function () {
        var obj = $(this), str = '',
            obj_id = $(this).attr('id'),
            did = obj.attr('data-id'),
            url = obj.attr('data-url'),
            num = parseInt(obj.find('strong').text()),
            type = obj.attr('data-type');
        plus = 'plus-one-' + did;
        $.getJSON('/member', function (data) {
            if (data.loggedIn == false) {
                $('body').data('function', '$("#' + obj_id + '").trigger("touchstart")');
                user_login();
                location.href = "#login";
            } else {
                $.post(url + '?' + new Date().getTime(), function (data) {
                    var str = '';
                    if (type == 'follow') {
                        if (data.status != 0) {
                            obj.find('strong').text(num + 1);
                            str = '关注成功';
                        } else {
                            str = '你已经关注过了';
                        }
                    } else {
                        if (data.status != 0) {
                            obj.find('strong').text(num + 1);
                            str = '喜欢成功';
                        } else {
                            str = '你已经喜欢过了';
                        }
                    }
                    if ($('#' + plus).size() == 0) {
                        str = '<div id="' + plus + '" class="pos-tip">' + str + '</div>';
                        $('body').append(str);
                    } else {
                        $('#' + plus).show();
                    }
                    obj.addClass('on').find('.icon').removeClass('icon-heart-empty').addClass('icon-heart');
                    setTimeout(function () {
                        $('#' + plus).hide();
                    }, 2000);
                }, 'json');
            }
        });
        return false;
    });

    $('.toggle-nav').click(function () {
        if ($('#nav').css('display') == 'none') {
            $(this).addClass('on');
            $('#nav').show();
        } else {
            $(this).removeClass('on');
            $('#nav').hide();
        }
    });
    count_cart();

    window.onhashchange = function () {
        if (location.href.indexOf('#login') == -1) {
            $('#box-ajax').remove();
        }
        if (location.href.indexOf('#category') == -1) {
            category_hide();
        }
    }

    //关闭分享层
    $('.mask-close').on('touchstart', function () {
        $(this).parent().removeClass('show-mask');
        return false;
    });

    //提醒	
    $('button[name=alert]').click(function () {
        var code = $(this).siblings('.code'),
            pid = $(this).siblings('.pid'),
            type = $(this).attr('data-type');
        name = $(this).attr('data-title');
        if (code.val() == '') {
            code.val('').focus();
            return false;
        }
        var re_email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
        var re_mobile = /^[1][3458]\d{9}$/;
        if (!re_email.test(code.val()) && !re_mobile.test(code.val())) {
            jAlert('请输入正确的邮箱或手机号。', function () {
                code.focus();
            });
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
    $('.validate-send').on('click', function () {
        var this_obj = $(this);
        var mobile_obj = $('.validate-mobile');
        var mobile = mobile_obj.val();
        if (mobile == '') {
            mobile_obj.focus();
            return false;
        }
        var second = 60;
        this_obj.attr('disabled', true);

        var mobile = mobile_obj.val();
        var type = this_obj.attr('data-type');
        var source = this_obj.attr('data-source')
        if (source == '' || source == undefined) source = mobile;
        $.ajax({
            url: '/sms/send',
            type: 'post',
            data: {mobile: mobile, source: source, type: type},
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                    var time = setInterval(function () {
                        if (second <= 0) {
                            clearInterval(time);
                            this_obj.removeAttr('disabled').text('重发验证码');
                        } else {
                            this_obj.text(second + '秒后重发');
                            second--;
                        }
                    }, 1000);
                } else {
                    window.parent.jAlert(data.result.msg);
                }
            }
        });
        return false;
    });
});

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
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}

function category_hide() {
    $('.box-mask').hide();
    $('#category-box').hide();
    $('body').css({'overflow': 'auto', 'height': 'auto'});
}

function user_login() {
    //判断当前是否是登录页面
    if (location.href.indexOf('/login') == -1) {
        if ($('#box-ajax').size() == 0) {
            $('body').append('<div id="box-ajax"></div>');
        }
        var url = '/member/login?' + new Date().getTime();
        $.get(url, function (data) {
            $('#box-ajax').html(data);
        });
        return false;
    }
}

function close_ebox() {
    $('#box-ajax').hide();
}

function count_cart() {
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

function format_date(t) {
    return new Date(parseInt(t)).Format("yyyy/MM/dd hh:mm:ss");
}

function format_num(num, n) {
    var len = num.toString().length;
    while (len < n) {
        num = "0" + num;
        len++;
    }
    return num;
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
