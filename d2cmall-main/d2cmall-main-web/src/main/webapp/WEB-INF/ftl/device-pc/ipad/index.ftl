<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <title>D2C - 全球好设计</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="keywords"
          content="预售,设计师品牌,全球好设计,SHOWROOM D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店,国际新锐设计师品牌,原创设计"/>
    <meta name="description" content="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场"/>
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta http-equiv="cleartype" content="on"/>
    <link rel="apple-touch-icon-precomposed" href="//static.d2c.cn/common/m/img/ic_launcher.png"/>
    <link rel="apple-touch-startup-image" href="${static_base}/t/img/start_up_l.png"
          media="screen and (orientation:landscape)"/>
    <link rel="apple-touch-startup-image" href="${static_base}/t/img/start_up_p.png"
          media="screen and (orientation:portrait)"/>
    <link rel="stylesheet" type="text/css" href="${static_base}/t/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="${static_base}/t/css/idangerous.swiper.css"/>
    <link rel="stylesheet" type="text/css" href="${static_base}/t/css/common.css"/>
    <script src="${static_base}/t/js/jquery-1.10.1.min.js"></script>
    <script src="${static_base}/t/js/idangerous.swiper.js"></script>
</head>

<body>
<div class="mask-tip"></div>
<div class="load-slide">加载中...<span id="load-num"></span></div>
<div class="showroom-nav">
    <a href="javascript:" class="filter-nav current" data-area="0">全部</a>
    <a href="javascript:" class="filter-nav" data-area="7">女士</a>
    <a href="javascript:" class="filter-nav" data-area="8">男士</a>
    <a href="javascript:" class="filter-nav" data-area="34">鞋包</a>
    <a href="javascript:" class="filter-nav" data-area="33">配饰</a>
    <a href="javascript:" class="filter-letter">A-Z</a>
</div>
<div class="letter-nav">
    <#assign letters=['#','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z']>
    <#list letters as letter>
        <a href="javascript:" data-group="${letter}">${letter}</a>
    </#list>
</div>
<div class="swiper-container">
    <div class="swiper-wrapper">
        <#list list as designer>
            <#if designer_index lte 60>
                <div class="swiper-slide"><img src="${static_base}/c/images/space.gif"
                                               data-src="${picture_base}${designer.path}" alt="${designer.name}"
                                               data-id="${designer.id}" data-name="${designer.name}"/></a></div>
            </#if>
        </#list>
    </div>
</div>

<div class="browser">
    <div class="browser-nav">
        <a href="javascript:">X</a>
        <a href="javascript:history.back(1);">后退</a>
    </div>
    <div class="browser-cont">

    </div>
</div>

<div class="clone">
    <#list list as designer>
        <#if designer_index lte 60>
            <img data-id="${designer.id}" data-name="${designer.name}" data-group="${designer.pageGroup}"
                 data-area="${designer.designArea}" data-src="${picture_base}${designer.path}"/>
        </#if>
    </#list>
</div>
<script>
    var mySwiper = new Swiper('.swiper-container', {
        loop: true,
        grabCursor: true,
        updateOnImagesReady: false,
        onFirstInit: function () {
            var size = $('.swiper-slide').size();
            var ii = (size > 3) ? 3 : size;
            for (i = 0; i < ii; i++) {
                var src = $('.swiper-slide:eq(' + i + ') img').attr('data-src');
                $('.swiper-slide:eq(' + i + ') img').attr('src', src).removeAttr('data-src');
            }
        },
        onSlideChangeStart: function () {
            if ($('.swiper-slide img[data-src]').size() > 0) {
                $.each($('.swiper-slide img[data-src]'), function (i, d) {
                    $(d).attr('src', $(d).attr('data-src')).removeAttr('data-src');
                });
            }
        }
    });

    function orient() {
        $('body,.swiper-slide').height(window.innerHeight);
        if (window.orientation == 0 || window.orientation == 180) {
            $('.swiper-slide img').css({'height': '100%', 'width': 'auto'});
            $("body").attr("class", "portrait");
        } else if (window.orientation == 90 || window.orientation == -90) {
            $('.swiper-slide img').css({'height': 'auto', 'width': '100%'});
            $("body").removeAttr("class");
        }
    }

    orient();
    $(window).bind('orientationchange', function (e) {
        mySwiper.resizeFix();
        orient();
    });
    $('body').on('touchstart', function () {
        $('.letter-nav').removeClass('letter-nav-show');
    });
    $('.filter-letter').on('touchstart', function (e) {
        e.preventDefault();
        $('.letter-nav').toggleClass('letter-nav-show');
        return false;
    });
    $('.filter-nav,.letter-nav a').on('touchstart', function (e) {
        e.preventDefault();
        if ($(this).attr('data-current') != 'true') {
            $(this).siblings().removeAttr('data-current');
            $(this).attr('data-current', 'true');
            $(this).siblings().removeClass('current');
            $(this).addClass('current');
            var area = $(this).attr('data-area');
            var group = $(this).attr('data-group');
            if (area != undefined) {
                if (area == '0') {
                    var imgs = $('.clone').find('img');
                } else {
                    var imgs = $('.clone').find('img[data-area*="' + area + '"]');
                }
            }
            if (group != undefined) {
                var imgs = $('.clone').find('img[data-group="' + group + '"]');
                $('.filter-nav').removeClass('current');
            }

            if (imgs.size() > 0) {
                var t = null, v = null;
                $('#load-num').html('');
                $('.load-slide').addClass('load-slide-show');
                mySwiper.slideTo(0);
                mySwiper.removeAllSlides();
                $.each(imgs, function (i, d) {
                    t = setTimeout(function () {
                        mySwiper.appendSlide('<img src="' + $(d).attr('data-src') + '" alt="' + $(d).attr('data-name') + '" data-id="' + $(d).attr('data-id') + '" />');
                        if (i == imgs.size() - 1) {
                            dtd = true
                        }
                        $('#load-num').html(Math.ceil(((i + 1) / imgs.size()) * 100) + '%');
                    }, '200');
                });
                v = setInterval(function () {
                    if (dtd == true) {
                        $('.load-slide').removeClass('load-slide-show');
                        ;
                        clearInterval(v);
                    }
                }, '300');
            } else {

            }
        } else {
            mySwiper.slideTo(0);
        }
    });

    $('.swiper-wrapper').on('click', '.swiper-slide img', function () {
        var id = $(this).attr('data-id');
        /*$.get('/showroom/designer/10061',function(data){
            $('.browser-cont').html(data);
        });	*/
        location.href = '/showroom/designer/' + id;
        return false;
    });

    //屏蔽ios下上下弹性
    $(window).on('scroll.elasticity', function (e) {
        e.preventDefault();
    }).on('touchmove.elasticity', function (e) {
        e.preventDefault();
    });
    if (("standalone" in window.navigator) && window.navigator.standalone) {
        var noddy, remotes = true;
        document.addEventListener('click', function (event) {
            noddy = event.target;
            // Bubble up until we hit link or top HTML element. Warning: BODY element is not compulsory so better to stop on HTML
            while (noddy.nodeName !== "A" && noddy.nodeName !== "HTML") {
                noddy = noddy.parentNode;
            }
            if ('href' in noddy && noddy.href.indexOf('http') !== -1 && (noddy.href.indexOf(document.location.host) !== -1 || remotes)) {
                event.preventDefault();
                document.location.href = noddy.href;
            }
        }, false);
    }
    setTimeout(function () {
        $('.showroom-nav').addClass('showroom-nav-show');
    }, '1500');
</script>
</body>
</html>
