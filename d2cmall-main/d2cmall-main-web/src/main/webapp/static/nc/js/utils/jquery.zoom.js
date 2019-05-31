(function ($) {
    $.fn.imageZoom = function (options) {
        var settings = {
            xzoom: 500,
            yzoom: 755,
            offset: 10,
            position: "BTL",
            preload: 1,
            hasvideo: 'false'
        };
        if (options) {
            $.extend(settings, options);
        }
        var noalt = '';
        var self = this;
        $(this).bind("mouseenter",
            function (ev) {
                var image_left = $(this).offset().left;
                var image_top = $(this).offset().top;
                var image_width = $(this).get(0).offsetWidth;
                var image_height = $(this).get(0).offsetHeight;
                var box_left = $(this).parent().offset().left;
                var box_top = $(this).parent().offset().top;
                var box_width = $(this).parent().width();
                var box_height = $(this).parent().height();
                noalt = $(this).attr("alt");
                var big_image = $(this).attr("data-image");
                $(this).attr("alt", '');
                if ($("div.zoom-div").get().length == 0) {
                    $(document.body).append("<div class='zoom-div'><img class='big-img' src='" + big_image + "'/></div><div class='zoom-mask'>&nbsp;</div>");
                }
                if (settings.position == "BTR") {
                    if (box_left + box_width + settings.offset + settings.xzoom > screen.width) {
                        leftpos = box_left - settings.offset - settings.xzoom;
                    } else {
                        leftpos = box_left + box_width + settings.offset;
                    }
                } else {
                    leftpos = image_left - settings.xzoom - settings.offset;
                    if (leftpos < 0) {
                        leftpos = image_left + image_width + settings.offset;
                    }
                }
                $("div.zoom-div").css({
                    top: box_top,
                    left: leftpos
                });
                $("div.zoom-div").width(settings.xzoom);
                $("div.zoom-div").height(settings.yzoom);
                $("div.zoom-div").show();
                $(this).css('cursor', 'crosshair');
                $(document.body).mousemove(function (e) {
                    mouse = new MouseEvent(e);
                    if (mouse.x < image_left || mouse.x > image_left + image_width || mouse.y < image_top || mouse.y > image_top + image_height) {
                        mouseOutImage();
                        return;
                    }
                    if (settings.hasvideo == 'true') {
                        if (mouse.x < image_left + 70 && mouse.y > image_top + image_height - 70) {
                            mouseOutImage();
                            return;
                        }
                    }
                    var bigwidth = $(".big-img").get(0).offsetWidth;
                    var bigheight = $(".big-img").get(0).offsetHeight;
                    var scaley = 'x';
                    var scalex = 'y';
                    if (isNaN(scalex) | isNaN(scaley)) {
                        var scalex = (bigwidth / image_width);
                        var scaley = (bigheight / image_height);
                        $("div.zoom-mask").width((settings.xzoom) / scalex);
                        $("div.zoom-mask").height((settings.yzoom) / scaley);
                        $("div.zoom-mask").css('visibility', 'visible');
                    }
                    xpos = mouse.x - $("div.zoom-mask").width() / 2;
                    ypos = mouse.y - $("div.zoom-mask").height() / 2;
                    xposs = mouse.x - $("div.zoom-mask").width() / 2 - image_left;
                    yposs = mouse.y - $("div.zoom-mask").height() / 2 - image_top;
                    xpos = (mouse.x - $("div.zoom-mask").width() / 2 < image_left) ? image_left : (mouse.x + $("div.zoom-mask").width() / 2 > image_width + image_left) ? (image_width + image_left - $("div.zoom-mask").width()) : xpos;
                    ypos = (mouse.y - $("div.zoom-mask").height() / 2 < image_top) ? image_top : (mouse.y + $("div.zoom-mask").height() / 2 > image_height + image_top) ? (image_height + image_top - $("div.zoom-mask").height()) : ypos;
                    $("div.zoom-mask").css({
                        top: ypos,
                        left: xpos
                    });
                    $("div.zoom-div").get(0).scrollLeft = xposs * scalex;
                    $("div.zoom-div").get(0).scrollTop = yposs * scaley;
                });
            });

        function mouseOutImage() {
            $(self).attr("alt", noalt);
            $(document.body).unbind("mousemove");
            $("div.zoom-mask").remove();
            $("div.zoom-div").remove();
        }

        count = 0;
        if (settings.preload) {
            $('body').append("<div style='display:none;' class='jq-preload-" + count + "'></div>");
            $(this).each(function () {
                var imagetopreload = $(this).attr("data-image");
                var content = jQuery('div.jq-preload-' + count + '').html();
                jQuery('div.jq-preload-' + count + '').html(content + '<img src=\"' + imagetopreload + '\">');
            });
        }
    }
})(jQuery);

function MouseEvent(e) {
    this.x = e.pageX;
    this.y = e.pageY;
}