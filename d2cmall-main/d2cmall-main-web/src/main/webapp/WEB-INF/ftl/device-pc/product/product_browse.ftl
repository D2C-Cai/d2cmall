<#import "templates/public_pc.ftl" as m>
<@m.page_header title='视觉形象大片' js="swiper.min" css="swiper" keywords="预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<div style="position:relative;">
    <div class="pic-swiper swiper-container">
        <div class="swiper-page-nav prev">&lt;</div>
        <div class="swiper-page-nav next">&gt;</div>
        <div class="swiper-wrapper">
            <#list pathList as list>
                <div class="swiper-slide">
                    <#if list.productId!=null>
                        <a href="${base}/product/${list.productId}" target="_blank" class="swiper-buy-button">购买</a>
                    </#if>
                    <#if list_index==0>
                        <img src="${picture_base}${list.path}" alt="">
                    <#else>
                        <img data-src="${picture_base}${list.path}" alt="">
                    </#if></div>
            </#list>
        </div>
    </div>

    <div class="designer-bar">
        <div class="swiper-page-nav prev">&lt;</div>
        <div class="swiper-page-nav next">&gt;</div>
        <div class="designer-swiper swiper-container">
            <div class="swiper-wrapper">
                <#list designerList as designer>
                    <#if designer.headPic?exists>
                        <div class="swiper-slide"
                             data-id="${designer.id}" data-name="${designer.name}"<#if designer_index==0> data-status="current"</#if>>
                        <a href="?designerId=${designer.id}"><img src="${picture_base}${designer.headPic}!120"
                                                                  alt=""></a></div></#if>
                </#list>
            </div>
        </div>
    </div>
</div>
<script>
    var pic_url = '${picture_base}';
    $('.pic-swiper.swiper-container,.pic-swiper .swiper-slide').height($(window).height());
    $(window).resize(function () {
        $('.pic-swiper.swiper-container,.pic-swiper .swiper-slide').height($(window).height());
    });
    var picSwiper = new Swiper('.pic-swiper', {
        autoplay: 5000,
        mousewheelControl: true,
        speed: 750,
        centeredSlides: true,
        onSlideChangeStart: function () {
            var i = picSwiper.activeIndex;
            for (m = i; m < i + 3; m++) {
                var o = $('.pic-swiper .swiper-slide:eq(' + m + ') img');
                o.attr('src', o.attr('data-src')).removeAttr('data-src');
            }

        }
    });
    $('.pic-swiper .swiper-wrapper').on('dblclick', function (e) {
        e.preventDefault();
        $('.designer-bar').slideDown();
    });
    $('.pic-swiper .prev').on('click', function (e) {
        e.preventDefault();
        //$('.designer-bar').slideUp();
        var i = picSwiper.activeIndex;
        if (i <= 0) {
            var obj = $('.designer-swiper .swiper-slide[data-status="current"]').prev();
            if (obj.attr('data-id') == undefined) {
                var slide_obj = $('.designer-swiper .swiper-slide:last');
            } else {
                var slide_obj = obj;
            }
            name_prev = slide_obj.attr('data-name');
            jAlert('将进入“' + name_prev + '”的精彩大片，是否继续？', '', function (r) {
                if (r) {
                    slide_obj.find('a').trigger('click');
                }
            });
        }
        picSwiper.swipePrev();
    });
    $('.pic-swiper .next').on('click', function (e) {
        e.preventDefault();
        //$('.designer-bar').slideUp();
        var i = picSwiper.activeIndex;
        if (i >= picSwiper.slides.length - 1) {
            var obj = $('.designer-swiper .swiper-slide[data-status="current"]'),
                name_current = obj.attr('data-name');
            if (obj.next().attr('data-id') == undefined) {
                var obj_next = $('.designer-swiper .swiper-slide:first');
            } else {
                var obj_next = $('.designer-swiper .swiper-slide[data-status="current"]').next();
            }
            name_next = obj_next.attr('data-name');

            jAlert('“' + name_current + '”的精彩大片已经欣赏完，是否继续欣赏“' + name_next + '”的大片？', '', function (r) {
                if (r) {
                    obj_next.find('a').trigger('click');
                }
            });
        }
        picSwiper.swipeNext();
    });

    var designerSwiper = new Swiper('.designer-swiper', {
        keyboardControl: true,
        mousewheelControl: true,
        slidesPerView: 'auto'
    });

    $('.designer-swiper a').on('click', function () {
        var obj = $(this).parent();
        obj.siblings().removeAttr('data-status');
        obj.attr('data-status', 'current');
        var url = $(this).attr('href');
        $.getJSON(url, function (result) {
            var data = result.pathList;
            picSwiper.removeCallbacks('SlideNext');
            var t = null, v = null, str = '';
            var data_length = data.length;
            var start_i = 0, end_i = start_i + 5, ii = 0;

            if (end_i >= data_length - 1) {
                end_i = data_length - 1;
            }
            picSwiper.removeAllSlides();
            $.each(data, function (i, d) {
                if (d.productId != null) {
                    str = '<a href="/product/' + d.productId + '" target="_blank" class="swiper-buy-button">购买</a>';
                }
                if (i <= end_i) {
                    if (ii < 2) {
                        str += '<img src="' + pic_url + d.path + '" alt="" />';
                    } else {
                        str += '<img data-src="' + pic_url + d.path + '" alt="" />';
                    }
                    picSwiper.appendSlide(str);
                    ii++;
                }
            });
            picSwiper.swipeTo(0);
            picSwiper.addCallback('SlideNext', function () {
                var swiper_length = picSwiper.slides.length - 2;//循环模式下需要-2
                var str = '';
                if (picSwiper.activeIndex == swiper_length - 1) {
                    start_i = swiper_length + 1;
                    end_i = start_i + 5;
                    if (end_i >= data_length - 1) {
                        end_i = data_length - 1;
                    }
                    $.each(data, function (i, d) {
                        if (d.productId != null) {
                            str = '<a href="/product/' + d.productId + '" target="_blank" class="swiper-buy-button">购买</a>';
                        }
                        if (i >= start_i && i <= end_i) {
                            if (ii < 2) {
                                str = '<img src="' + pic_url + d.path + '" alt="" />';
                            } else {
                                str = '<img data-src="' + pic_url + d.path + '" alt="" />';
                            }
                            picSwiper.appendSlide(str);
                        }
                        ii++;
                    });
                }
            });
        });
        return false;
    });
    $('.designer-bar .prev').on('click', function (e) {
        e.preventDefault();
        designerSwiper.swipePrev();
    });
    $('.designer-bar .next').on('click', function (e) {
        e.preventDefault();
        designerSwiper.swipeNext();
    });

    <#if currentDesignerId>
    var current_index = $('.designer-swiper .swiper-slide[data-id="${currentDesignerId}"]').index();
    designerSwiper.swipeTo(current_index);
    </#if>

</script>
<@m.page_footer nofloat=1 nomenu=1 />