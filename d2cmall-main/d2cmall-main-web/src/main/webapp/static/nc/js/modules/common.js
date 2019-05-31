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
};
var secretKey = '8811d44df3c0b408f6fa4a31002db44d';
$(function () {
    var path_array = ['/', '/showroom', '/crowds', '/custom', '/star'];
    var path = location.pathname;
    var not_show_top_ad = $.cookie('not_show_top_ad');
    var not_show_top_big_ad = $.cookie('not_show_top_big_ad');
    var not_show_newpop = $.cookie("not_show_newpop");
    /*if(path.indexOf("/member")==-1&&path.indexOf("/o2o")==-1&&path.indexOf("/coupon/")==-1&&path.indexOf("/comment/")==-1&&path.indexOf("/cart")==-1&&path.indexOf("/order")==-1&&path.indexOf("/payment")==-1&&not_show_newpop==undefined){
        var showpop='<div id="black" style="display: block; position: fixed; top: 0%; left: 0%; width: 100%; height: 100%; background-color: black; z-index:1000; -moz-opacity: 0.7; opacity:.70; filter: alpha(opacity=70);"></div><div id="showpup" style="position:fixed;margin:auto;left:0; right:0; top:0; bottom:0;width:850px;height:550px;z-index:1001"><div style="position:relative;"><a href="javascript:" id="" class="newpop-close" style="position:absolute;right:10px;"><i class="fa fa-close" style="font-size:35px;color:#FFF;"></i></a>			<img src="//static.d2c.cn/img/topic/160125/login.jpg" width="850" height="550" alt="d2c新年红包袋大派送">	</div></div>';
        $("body").prepend(showpop);
        
        
        $(".newpop-close").click(function(){
        $(this).parent().parent().remove();
        $("#black").css("display","none");
        $.cookie("not_show_newpop","3",{expires:3})
        })
    
    }*/


    //$.removeCookie('not_show_top_ad');
    //$.removeCookie('not_show_top_big_ad');
    //if ($.inArray(path,path_array)!=-1){
    /*if (path.indexOf('/member')==-1 && path.indexOf('/o2o')==-1 && path.indexOf('/coupon/')==-1 && path.indexOf('/comment/')==-1 && path.indexOf('/cart')==-1 && path.indexOf('/order')==-1){
        //$('.navigation').removeClass('scroll-suspend');
        $('body').append('<div style="position:fixed;background:#000;box-shadow:0 0 5px #CCC;height:60px;bottom:0;left:0;right:0;z-index:2;">\
            <div class="layout-response">\
                <div class="float-left" style="margin-left:50px;"><img src="//static.d2c.cn/img/home/150520/change/fix_01.jpg" width="300" height="59" boder="0"/></div>\
                <div class="float-left" style="padding-left:3%;line-height:60px;"><span style="padding:8px 10px;border-radius:2px;color:white;background-color:#f03552" class="time count-down" data-starttime="2015/05/20 10:00:00">还有<span class="day"></span> 天<span class="hour"></span>小时<span class="minute"></span>分钟</span> </div>\
                <form class="float-left validate-form" success-tip="恭喜，提醒订阅成功！" call-back="false" action="/subscribe/mobile" method="post" style="float:right;margin-top:15px;">\
                <input name="mobile" id="subscribe-mobile" size="20" type="text" class="input input-s" placeholder="请输入手机号"/>\
                <button class="button button-s button-purple" type="submit">订阅开始提醒</button></div>\
            </form></div></div>');
        
    }
    
    $('.header-logo').html('<div style="position:relative;"><a href="/" style="position:absolute;display:block;top:0;left:0;width:260px;height:80px;background:transparent;margin-top:10px;z-index:2" title="回D2C首页"></a><object style="margin-top:15px;" id="logo-movie" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="260" height="80"><param name="movie" value="//static.d2c.cn/img/home/151225/logo.swf"><param name="wmode" value="transparent"><param name="quality" value="high"><param name="allowScriptAccess" value="always"><embed wmode="transparent" src="//static.d2c.cn/img/home/151225/logo.swf" quality="high" style="margin-top:10px;" autoplay="true" width="260" height="80" type="application/x-shockwave-flash" allowscriptaccess="always" name="logo-movie"></object>');*/
    /*if (path.indexOf('/member')==-1 && path.indexOf('/o2o')==-1 && path.indexOf('/coupon/')==-1 && path.indexOf('/comment/')==-1 && path.indexOf('/cart')==-1 && path.indexOf('/order')==-1 && path.indexOf('/payment')==-1 && not_show_top_ad==undefined){
        var str='<div id="top-wrap" style="position:relative;width:100%;height:120px;overflow:hidden;text-align:center;">\
            <div id="small-ad" style="position:absolute;width:1920px;left:50%;margin-left:-960px;overflow:hidden;z-index:2;text-align:center;">\
            <a href="javascript:" id="black" class="top-ad-close" style="position:absolute;top:10px;right:40px;"><i class="fa fa-close" style="font-size:20px;color:#FFF;"></i></a>\
            <a href="//www.d2cmall.com/page/qianbao" target="_blank"><img src="//static.d2c.cn/img/topic/060125/dingtong1.jpg" width="1920" height="120" alt="" /></a></div>\
            \
        </div>';
        var small_h=150,big_h=780; 
        $('body').prepend(str);
    
        $('.top-ad-close').click(function(){
            $(this).parent().parent().remove();
            $.cookie('not_show_top_ad','1', {expires:1})

        });
        if (not_show_top_big_ad==undefined){
            var st=setTimeout(function(){
                $('#small-ad').slideUp();
                $('#top-wrap').animate({'height':big_h});
                $.cookie('not_show_top_big_ad','1', {expires:1});
            },500);
            var bt=setTimeout(function(){
                $('#top-wrap').animate({'height':small_h},function(){
                    $('#small-ad').slideDown();
                });
            },6500);
        }
         
        $('#small-ad').hover(function(){
            clearTimeout(st);
            st=setTimeout(function(){
                $('#small-ad').slideUp();
                $('#top-wrap').animate({'height':big_h});
            },500);			
        },function(){
            clearTimeout(st);
        });
            
        $('#big-ad').hover(function(){			
        },function(){
            clearTimeout(bt);
            $('#small-ad').slideDown();
            $('#top-wrap').animate({'height':small_h});
        });
    
        
        
    }
    */
    //热卖商品处理
    if ($("#navs-active")) {
        var winWidth, prolen, hotpro = $("#navs-active").next('.row').children('.col-4');
        prolen = hotpro.size();
        window.innerWidth ? winWidth = window.innerWidth : winWidth = document.body.clientWidth;
        var changem = function () {
            if (parseInt(winWidth) > 1350 && parseInt(winWidth) < 1700) {
                prolen = 24;
            } else if (parseInt(winWidth) < 1350) {
                prolen = 25;
            } else {
                prolen = 28;
            }
            hotpro.each(function (i, d) {
                if ((i + 1) > prolen) {
                    $(this).hide();
                } else {
                    $(this).show();
                }
            })
        }
        $(window).resize(function () {
            window.innerWidth ? winWidth = window.innerWidth : winWidth = document.body.clientWidth;
            changem();
        })
        changem();
    }


    if ($('.place-holder-keyword').val() == '') {
        $('.place-holder-keyword').val('');
    }


    function search_form_submit() {
        $('#header-search-form').submit();
    }

    $('#header-keyword').autocomplete("/membersearch.json", {
        width: (parseInt($('#header-keyword').width()) + 16),
        onItemSelect: search_form_submit,
        cacheLength: 1,
        maxItemsToShow: 10,
        autoFill: false
    });

    if ($('.navi-category-main').hasClass('display-none')) {
        $('.navi-category').hover(function () {
            $('.navi-category-main').slideDown();
        }, function () {
            $('.navi-category-main').hide();
        });
    }
    $('.scroll-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 300);
        return false;
    });
    $(window).bind("scroll", function () {
        var scroll_top = $(window).scrollTop();
        if ($('.navi-category-main').hasClass('display-block')) {
            if (scroll_top > 785) {
                $('.navi-category-main').hide();
            } else {
                $('.navi-category-main').show();
            }
        }
        if (scroll_top > 60) {
            $('.scroll-item').show();
        } else {
            $('.scroll-item').hide();
        }
    });
    /*
     * 悬浮 
     * 用法案例
     * <div class="scroll-suspend" data-anchor="true" data-scroll-bg="" data-scroll-end="" data-scroll-holder=""></div>
     * data-anchor为true，则会查找下属a标签的href（页面锚点）如navs-men，当滚动到锚点时，会自动给a='#nav-men'加上class=on
     * data-scroll-bg 则为悬浮层提供背景，需指定页面上的一个元素id，通过该id的样式来达到目的
     * data-scroll-end 悬浮层到达该元素id，则结束滚动悬浮
     * data-scroll-holder 当悬浮层滚动悬浮的时候，该悬浮层所占用的位置将变空，导致原页面有位移现象，故用一个替代元素占用其高度
     * data-style 加入样式
     */
    if ($('.scroll-suspend').size() > 0) {
        var suspendScrollAnchor = function (this_obj, scroll_top) {
            var obj_point = new Array(),
                obj_point_top = new Array(),
                obj_point_scope = new Array();
            $.each(this_obj.find('a'), function (i, d) {
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
            this_obj.find('a').removeClass('on');
            var i = 0;
            $.each(obj_point, function (index, objs) {
                if (scroll_top >= obj_point_top[index]) {
                    i = index;
                }
            });
            this_obj.find('a[href=' + obj_point[i] + ']').addClass('on');
        };

        $('.scroll-suspend[data-anchor=true] a').click(function () {
            if ($(this).attr('href').indexOf('#') != -1) {
                var height = $(this).attr('data-height');
                if (isNaN(height)) height = 0;
                height = parseInt(height);
                var id = $(this).attr('href');
                var obj_height = $(id).offset().top;
                if (height > 0) {
                    $('html, body').animate({scrollTop: (obj_height - 30 - height)}, 300);
                } else {
                    $('html, body').animate({scrollTop: obj_height - 30}, 300);
                }

                return false;
            }
        });
        $.each($('.scroll-suspend'), function (i, obj) {
            var offset_top = $(obj).offset().top,
                suspend_bar = $(obj).attr('data-scroll-bg'),
                scroll_end = $(obj).attr('data-scroll-end'),
                scroll_holder = $(obj).attr('data-scroll-holder'),
                anchor = $(obj).attr('data-anchor'),
                data_top = $(obj).attr('data-offset');
            var page_type = $('.banner-home').attr('data-pagetype');
            if (!data_top) data_top = 0;
            if (scroll_end) {
                $(window).bind("scroll", function () {
                    var scroll_top = $(window).scrollTop();
                    var end_top = $('#' + scroll_end).offset().top - $(obj).height();
                    if (scroll_top > offset_top - data_top && scroll_top < end_top) {
                        $(obj).addClass('suspend-fixed');
                        suspend_bar && $('#' + suspend_bar).show();
                        scroll_holder && $('#' + scroll_holder).show();
                        $(obj).find('.navi-category').removeClass('navi-category-border');

                        if (typeof (page_type) != "undefined") {
                            if ($('.navigation').hasClass('suspend-fixed')) {
                                $('.float-search').show();
                            }
                        }
                    } else {
                        $(obj).find('.navi-category').addClass('navi-category-border');
                        $(obj).removeClass('suspend-fixed');
                        if (typeof (page_type) != "undefined") {
                            if ($('.navigation').hasClass('suspend-fixed')) {
                                $('.float-search').show();
                            }
                        }
                        suspend_bar && $('#' + suspend_bar).hide();
                        scroll_holder && $('#' + scroll_holder).hide();
                    }
                    anchor && suspendScrollAnchor($(obj), scroll_top);
                });

            } else {
                $(window).bind("scroll", function () {
                    var scroll_top = $(window).scrollTop();
                    if (scroll_top > offset_top - data_top) {
                        $(obj).addClass('suspend-fixed');
                        suspend_bar && $('#' + suspend_bar).show();
                        scroll_holder && $('#' + scroll_holder).show();
                        $(obj).find('.navi-category').removeClass('navi-category-border');
                        if (typeof (page_type) != "undefined") {
                            if ($('.navigation').hasClass('suspend-fixed')) {
                                $('.float-search').show();
                            }
                        }
                    } else {
                        $(obj).find('.navi-category').addClass('navi-category-border');
                        $(obj).removeClass('suspend-fixed');
                        suspend_bar && $('#' + suspend_bar).hide();
                        scroll_holder && $('#' + scroll_holder).hide();
                        if (typeof (page_type) != "undefined") {
                            if (!$('.navigation').hasClass('suspend-fixed')) {
                                $('.float-search').hide();
                            }
                        }
                    }
                    anchor && suspendScrollAnchor($(obj), scroll_top);
                });
            }

        });
    }
    /*悬浮层展开搜索*/
    $('.service-suspend .service-phone').hover(function () {
        $('.service-phone-detail').addClass('show');
    }, function () {
        $('.service-phone-detail').removeClass('show');
    });
    /*侧翼悬浮快捷通道*/
    $('.service-suspend .text-info').hover(function () {
        $(this).find('a').addClass('show');
    }, function () {
        $(this).find('a').removeClass('show');
    });

    $('.lazyload,.lazyload img').lazyload();


    /* 表单校验
     * 只要指定表单的样式为validate-form,自动会使用以下方式进行表单校验
     * 指定了success-tip,则在成功后显示success-tip的文字
     * 指定了confirm，则先弹出确认框，在进行操作
     * 指定call-back，则执行完成后执行call-back
     * 指定tip-type，则提示形式为flashtip和alert
     * form加了target将不经过ajax请求
     * 具体设置请参考plugin.js 里的utilValidateForm;
     */
    $('.validate-form').die().live('submit', function () {
        var form = $(this);
        var url = form.attr('action'),
            success_tip = form.attr('success-tip'),
            fail_tip = form.attr('fail-tip'),
            confirm_text = form.attr('confirm'),
            call_function = form.attr('call-back'),
            redirect_url = form.attr('redirect-url'),
            target = form.attr('target'),
            is_confirm = $('body').data('is_confirm_form');
        if (form.utilValidateForm()) {
            if (confirm_text && !is_confirm) {
                jConfirm(confirm_text, '', function (r) {
                    if (r) {
                        if (target == undefined) {
                            $.ajax({
                                url: url,
                                data: form.serialize(),
                                type: form.attr('method'),
                                dataType: 'json',
                                beforeSend: function () {
                                    form.find('button[type=submit],input[type=submit]').attr('disabled', true);
                                },
                                success: function (data) {
                                    setTimeout(function () {
                                        form.find('button[type=submit],input[type=submit]').removeAttr('disabled');
                                    }, 2000);
                                    if (data.result.status == -2) {
                                        if ($('#random-code-insert').html() == '') {
                                            var random_code_html = template('random-code-template', {'timestr': new Date().getTime()});
                                            $('#random-code-insert').html(random_code_html);
                                        }
                                    }
                                    if (data.result.status == 1) {
                                        if (success_tip != 'false') {
                                            if (!success_tip) success_tip = data.result.message;
                                            success_tip != '' && $.flashTip(success_tip, 'success');
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
                                        $.flashTip(fail_tip, 'warning');
                                        $('.validate-img').trigger('click');
                                    }
                                }
                            });
                        } else {
                            $('body').data('is_confirm_form', true);
                            form.submit();
                        }
                    }
                });
                return false;
            } else {
                if (target == undefined) {
                    $.ajax({
                        url: url,
                        data: form.serialize(),
                        type: form.attr('method'),
                        dataType: 'json',
                        beforeSend: function () {
                            form.find('button[type=submit],input[type=submit]').attr('disabled', true);
                        },
                        success: function (data) {
                            setTimeout(function () {
                                form.find('button[type=submit],input[type=submit]').removeAttr('disabled');
                            }, 2000);
                            if (data.result.status == -2) {
                                if ($('#random-code-insert').html() == '') {
                                    var random_code_html = template('random-code-template', {'timestr': new Date().getTime()});
                                    $('#random-code-insert').html(random_code_html);
                                }
                            }
                            if (data.result.status == 1) {
                                if (success_tip != 'false') {
                                    if (!success_tip) success_tip = data.result.message;
                                    success_tip != '' && $.flashTip(success_tip, 'success');
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
                                $.flashTip(fail_tip, 'warning');
                                $('.validate-img').trigger('click');
                            }
                        }
                    });
                    return false;
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

    $('.ajax-request').die().live('click', function () {
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
            modal_type = obj.attr('modal-type'),
            modal_width = obj.attr('modal-width'),
            success_tip = obj.attr('success-tip'),
            fail_tip = obj.attr('fail-tip'),
            call_function = obj.attr('call-back'),
            redirect_url = obj.attr('redirect-url');
        if (!method_type) method_type = 'get';
        if (!template_url && !template_id) {//只请求
            if (confirm_text) {
                jConfirm(confirm_text, '', function (r) {
                    if (r) {
                        $.ajax({
                            url: data_url,
                            type: method_type,
                            data: data_param ? eval('(' + data_param + ')') : {},
                            dataType: 'json',
                            success: function (data) {
                                if (data.result.login == false) {
                                    $('body').data('function', '$("#' + obj_id + '").trigger("click")');
                                    userLogin();
                                    return false;
                                }
                                if (data.result.status == 1) {
                                    if (success_tip != "false") {
                                        if (!success_tip) success_tip = data.result.message;
                                        $.flashTip(success_tip, 'success');
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
                                    $.flashTip(fail_tip, 'warning');
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
                            $('body').data('function', '$("#' + obj_id + '").trigger("click")');
                            userLogin();
                            return false;
                        }

                        if (data.result.status == 1) {
                            if (success_tip != "false") {
                                if (!success_tip) success_tip = data.result.message;
                                $.flashTip(success_tip, 'success');
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
                            $.flashTip(fail_tip, 'warning');
                        }
                    }
                });
            }
            return false;
        } else {
            var data = {};
            if (template_url) {
                $.get(template_url, function (data) {
                    if (modal_type == 'pop') {
                        if (!modal_width) modal_width = 450;
                        $.popModal({'content': data, 'width': modal_width});

                    } else {
                        obj.floatModal({'str': data, 'title': title, 'direction': 'right'});
                    }
                });
            }
            if (request_url) {//如果有先请求的地址，则先请求
                $.getJSON(request_url, function (result) {
                    data = result.result.datas;
                    data.url = data_url;
                    var html = template(template_id, data);
                    if (modal_type == 'pop') {
                        if (!modal_width) modal_width = 450;
                        $.popModal({'content': html, 'width': modal_width});
                    } else {
                        obj.floatModal({'str': html, 'title': title, 'direction': 'right'});
                    }
                });
            }
            if (!request_url && template_id) {
                if (data_param) data = eval('(' + data_param + ')');
                data.url = data_url;
                var html = template(template_id, data);
                if (modal_type == 'pop') {
                    if (!modal_width) modal_width = 450;
                    $.popModal({'content': html, 'width': modal_width});
                } else {
                    obj.floatModal({'str': html, 'title': title, 'direction': 'right'});
                }
            }
            return false;
        }
    });

    /* 短信发送
     * 指定要手机号码的样式为validate-mobile,发送按钮的样式为validate-send
     */
    $('.validate-send').die().live('click', function () {
        var obj = $(this);
        var account_obj = $('.validate-account');
        var account = account_obj.val();
        var nation_code = $('.mobile-code').val();
        var account_type = account_obj.attr('data-rule');
        var register = obj.attr('data-register');
        var type = obj.attr('data-type') || '';
        if (account == '') {
            account_obj.focus();

            return false;
        }
        var source = obj.attr('data-source');
        if (source == '' || source == undefined) source = account;
        if (account_type == 'mobile') {
            if (!account_obj.utilValidateMobile()) {
                account_obj.focus();
                if (account_obj.siblings('.tip-validate').size() > 0) {
                    account_obj.siblings('.tip-validate').show().text('请输入正确的手机号');
                } else {
                    jAlert('请输入正确的手机号');
                }
                return false;
            } else {
                account_obj.siblings('.tip-validate').hide();
            }
            sendCode(account, source, type, nation_code, this)
        } else {
            if (!account_obj.utilValidateMobile() && !account_obj.utilValidateEmail()) {
                account_obj.focus();
                if (account_obj.siblings('.tip-validate').size() > 0) {
                    account_obj.siblings('.tip-validate').show().text('请输入正确手机号码或邮箱');
                } else {
                    jAlert('请输入正确手机号码或邮箱');
                }
                return false;
            } else {
                account_obj.siblings('.tip-validate').hide();
            }
        }


        var test = function (o) {
            $.ajax({
                url: '/member/forgetLoginCode',
                type: 'post',
                data: {loginCode: account},
                dataType: 'json',
                async: false,
                success: function (data) {
                    if (data.result.status == -1) {
                        o = false;
                        jAlert('请输入已经注册的手机号');
                    }
                }
            });
            return o;
        }
        /*	if(register!=undefined){
                if(!test(register)){
                    return false;
                }
            }
        */
        //var second=60;

        /*
        var type=obj.attr('data-type');
        obj.attr('disabled','disabled');
        var html='<div>\
            <input type="text" name="randomCode" id="pop-sms-random-code" class="input input-l text-left" style="text-transform:Uppercase;font-size:16px;width:40%;" size="15" placeholder="输入图片中的验证码" />\
            <img src="/randomcode/getcode?time='+new Date().getTime()+'&mobile='+account+'" class="validate-img" alt="" title="点击换一个验证码" />\
            </div>\
            <div style="padding-top:20px;text-align:center;padding-bottom:5px">\
            <button type="button" name="send" onclick="sendCode(\''+account+'\',\''+source+'\',\''+type+'\',\''+nation_code+'\',this)" class="button" style="font-size:16px">确定</button>\
            </div>';
        $.utilBaseModal.mask({
            type            :   'box',
            level           :   100,
            padding         :   1,
            width			:	300,
            height			:	150,
            canclose        :   true,
            background      :   '#000'
        });
        $('#box-modal-content').html(html);
        $.utilBaseModal.position('box');
        */
        return false;
        /*$.ajax({
            url      : '/sms/send',
            type     : 'post',
            data     : {mobile:account,source:source,type:type,terminal:'PC'},
            dataType : 'json',
            success  : function(data){
                if(data.result.status==1){
                    var time=setInterval(function(){
                        if (second<1){
                            clearInterval(time);
                            obj.removeAttr('disabled').text('重发验证码');
                        }else{
                            obj.text(second+'秒后重发');
                            second--;
                        }    
                    },1000);
                }else{
                    $.flashTip(data.result.message,'warning');
                    obj.removeAttr('disabled').text('重发验证码');
                }   
            }
        });*/
    });
    /*图片验证码*/
    $('.validate-img').die().live('click', function () {
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
        $(this).attr('src', url + 'time=' + new Date().getTime() + '&mobile=' + $('.validate-account').val());
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
            jAlert('手机号码格式不正确');
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
    /*
     * 报表导出
     */
    $('.excel-export').click(function () {
        var form = $(this).parents('form');
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
                            window.parent.jAlert(data.result.message);
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
        if (config.desc == undefined) config.desc = '';
        var type = $(this).attr('class').replace('share ', '');
        var open_url;
        if (type == 'weixin') {
            $.getScript("/static/nc/js/utils/jquery.qrcode.js", function () {
                obj.find('.barcode').html('');
                var str = '<div class="share-to-weixin">\
					<p class="title"><a class="float-right" style="font-size:16px;" href="javascript:" onclick="$(this).parent().parent().remove();"><i class="fa fa-close"></i></a>分享给微信好友和朋友圈</p>\
					<p class="barcode"></p>\
					<p class="info">打开微信，点击“发现”，使用 “扫一扫”扫描二维码，打开网页，点击右上角分享到我的朋友圈。</p>\
					</div>';
                obj.append(str);
                obj.find('.barcode').qrcode({width: 200, height: 200, text: config.url});
            });
            return false;
        }
        if (type == 'weibo') {
            open_url = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&pic=' + encodeURIComponent(config.pic) + '&appkey=1698482307&searchPic=false';
        }
        if (type == 'qq') {
            open_url = '//connect.qq.com/widget/shareqq/index.html?url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&pic=' + encodeURIComponent(config.pic) + '&summary=&site=d2cmall';
        }
        if (type == 'qzone') {
            open_url = '//sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&desc=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic) + '&summary=&site=';
        }
        if (type == 'renren') {
            open_url = '//widget.renren.com/dialog/share?resourceUrl=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&description=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic);
        }
        if (type == 'douban') {
            open_url = '//www.douban.com/share/service?href=' + encodeURIComponent(config.url) + '&name=' + encodeURIComponent(config.title) + '&text=' + encodeURIComponent(config.desc) + '&image=' + encodeURIComponent(config.pic);
        }
        if (type == 'tqq') {
            open_url = '//share.v.t.qq.com/index.php?c=share&a=index&url=' + encodeURIComponent(config.url) + '&title=' + encodeURIComponent(config.title) + '&text=' + encodeURIComponent(config.desc) + '&pic=' + encodeURIComponent(config.pic);
        }
        window.open(open_url);
        return false;
    });

    if ($(".count-down").size() > 0) {
        $(".count-down").each(function () {
            $(this).countdown();
        });
    }
    if ($("#cart-nums-id").size() > 0) {
        if ($('#loginStatus').size() > 0) {
            countCart();
        }
    }
    var country_template = '{{each list as item i}}'
        + '<div class="option-item" style="background:transparent;">{{item.group}}</div>'
        + '{{each item.list as country ii}}'
        + '<div class="option-item country-item" data-code="{{country.mobileCode}}" data-name="{{country.cnName}}" data-displayname="{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}">'
        + '    <label>{{if item.group!=\'常用地区\'}}{{country.enName}} {{/if}}{{country.cnName}}</label>'
        + '    <span class="note grey">{{country.mobileCode}}</span>'
        + '</div>'
        + '{{/each}}'
        + '{{/each}}';
    $('#choose-country').die().live('click', function (e) {
        if ($('.country-data').size() == 0) {
            $(this).after('<div class="country-data"></div>');
        } else {
            $('.country-data').toggle();
        }

        if ($('.country-data').html() == '') {
            $.getScript('/static/nc/js/utils/json-country.js', function (result) {
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
                $('.country-data').html(html).show();
            });
        }
        return false;
    });
    $('body').click(function (e) {
        if (e.currentTarget.id != 'choose-country') {
            $('.country-data').hide();
        }
    });
    $('.country-item').die().live('click', function () {
        var obj = $(this);
        var code = obj.attr('data-code'), name = obj.attr('data-name'), displayname = obj.attr('data-displayname');
        $('#mobile-code').text('+' + code);
        $('.country-code').val(name);
        $('.mobile-code').val(code);
        $('.country-data').hide();
        return false;
    });
    /*视频播放*/
    $('.video-player').click(function () {
        var video_path = $(this).attr('data-video');
        var video_apple_path = $(this).attr('data-apple-video');
        var str = '';
        if (!browser.versions.iPad || video_apple_path == undefined) {
            str = '<embed width="100%" height="100%" pluginspage="//www.macromedia.com/go/getflashplayer" src="//static.d2c.cn/common/o/flvplayer.swf" type="application/x-shockwave-flash" allowfullscreen="true" flashvars="vcastr_file=' + video_path + '&amp;LogoText=&amp;IsAutoPlay=1" quality="high" />';
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
    /*报名/优惠券注册登录功能*/
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
        //领取优惠券判断
        $('.activity-form-d2c').submit(function () {
            couponFormSubmit($(this));
        });
    }

});


/*
 * 会员登录
 */
var userLogin = function () {
    //判断当前是否是登录页面
    var url = '/member/login?' + new Date().getTime();
    $.popModal({'url': url, datatype: '', 'width': '450px'});
    return false;
}

var countCart = function () {
    $.getJSON('/cart/count?t=' + Math.random(), function (data) {
        if (data.cartItemCount > 0) {
            $('#cart-nums-id').text('(' + data.cartItemCount + ')');
            $('#cart-nums-id').data('change', 1);
        }
    });
}

/*登录注册后执行函数*/
function loginSuccessReturn() {
    var action = $('body').data('function');
    if (action) {
        $.popModalClose();
        setTimeout(eval('{' + action + '}'), 1000);
    } else {
        location.reload();
    }
}

function registerSuccessReturn() {
    var action = $('body').data('function');
    if (action) {
        $.popModalClose();
        setTimeout(eval('{' + action + '}'), 1000);
    } else {
        location.reload();
    }
}

//发送验证码
function sendCode(account, source, type, nation_code, obj_m) {
    //var random_code=$('#pop-sms-random-code').val();
    $(obj_m).attr('disabled', true);
    console.log(account)
    var sign = hex_md5("mobile=" + account + secretKey)
    var appParams = $.base64('encode', "mobile=" + account + "&sign=" + sign)
    var data = {
        mobile: account,
        source: source,
        type: type,
        terminal: 'PC',
        nationCode: nation_code,
        appParams: appParams
    }
    var second = 60;
    var obj = $('.validate-send');
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
                obj.attr('disabled', 'disabled');
                $('#box-modal-remove').trigger('click');
                var time = setInterval(function () {
                    if (second < 1) {
                        clearInterval(time);
                        obj.removeAttr('disabled').text('重发验证码');
                    } else {
                        obj.text(second + '秒后重发');
                        second--;
                    }
                }, 1000);
            } else {
                $(obj_m).removeAttr('disabled');
                $.flashTip(data.result.message, 'warning');
            }
        }
    });

    return false;
}

/*GET领取优惠券*/
$('.promotion-coupon').on('click', function () {
    var couponId = $(this).attr('data-id');
    $.get("/coupon/batch/" + couponId, function (data) {
        if (data.result.status == 1) {
            $.flashTip('领取成功', 'success');
        } else if (data.result.login == false) {
            userLogin();
            return false;
        } else {
            $.flashTip(data.result.message, 'warning');
        }
    }, 'json')
})

function couponFormSubmit(form) {
    var mobile = form.find('input[name=mobile]');
    if (mobile != '') {
        $('body').data('activity_form', form.serializeObject())
        $.ajax({
            url: '/member/check',//检测地址
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
}


//优惠券领取成功回调
function activityExcuteSuccess(data, parame) {
    $.popModalClose();
    var html_template = '<div>\
                    <div class="to-cart-success" style="background:rgba(255,255,255,0.95)">\
                    <div class="cart-modal-content"><h2 style="color:#FF6600;">恭喜您获得专属D2C红包{{amount}}元 </h2>\
                    <p class="txt-note">可在我的优惠券中查看或下单时选择抵扣。请转发此页面，让您的朋友也试试手气。</p></div>\
                    <div class="cart-modal-button"><button type="button" class="button button-s button-green" style="width:70px;" onclick="$.popModalClose();">确定</button>\
                    </div></div></div>';
    if (parame.template == '' || parame.template == undefined) {
        var render = template.compile(html_template);
        var condata = {'amount': data.result.datas.coupon.amount}
        var html = render(condata);
        $.popModal({'content': html, datatype: '', 'width': '350'});
        return false;
    } else {
        var html = template(parame.template);
        $('#success-lottery').append(html);
    }
}

//执行活动程序 
function activityExcute(parame) {
    if (parame == '' || parame == undefined) {
        parame = $('body').data('activity_form');
    }
    $.ajax({
        url: parame.url,
        type: 'post',
        data: {'mobile': parame.mobile},
        dataType: 'json',
        success: function (data) {
            if (data.result.status == 1) {
                if (parame.mobile != "") {
                    $('.use_mobile').attr("readonly", "readonly");
                }
                activityExcuteSuccess(data, parame);
            } else {
                $.flashTip(data.result.message, 'error');
            }
        }
    });
}

//注册
function activityRegister(parame) {
    var temp = '<div class="form">\
                <h3 style="text-align:center;font-size:16px;top:-4px" class="pop-title">{{title}}</h3>\
                    <form name="modal-reg-form" class="validate-form" method="post" action="/member/join/d2c" call-back="activityExcute" success-tip="false">\
                        <div class="form-item form-item-vertical">\
                            <label>请输入手机号</label>\
                            <div class="form-item-mobile" style="width:100%;">\
                                <span class="choose-country" id="choose-country" style="position:absolute;line-height:36px;"><span id="mobile-code"><strong>+86</strong></span><span class="fa fa-caret-down" style="top:11px;"><em></em></span></span>\
                                <input type="text" name="mobile" id="mobile" minlength="2" maxlength="20" value="{{mobile}}" placeholder="" data-rule="mobile" readonly="readonly" class="input input-l validate-account">\
                            </div>\
                            <input type="hidden" name="nationCode" value="86" class="mobile-code">\
                            <input type="hidden" name="source" value="{{source}}" class="activityRegister-source">\
                            <div class="tip tip-validate" data-target="reg_login_code" data-rule="mobile"></div>\
                        </div>\
                        <div class="form-item form-item-vertical">\
                            <label>请输入短信校验码</label>\
                            <input type="text" name="code" id="validate-code" size="18" title="短信校验码" placeholder="" class="input input-l" style="width:58%;" value="">\
                            <button type="button" data-source="" data-type="Member" class="button button-l button-green validate-send" style="width:40%">获取校验码</button>\
                            <div class="tip tip-validate" data-target="validate-code"></div>\
                        </div>\
                        <div class="form-button" style="margin-bottom:20px">\
                            <button type="submit" class="button button-l button-red" style="width:60%;">加入D2C</button>\
                        </div>\
                    </form>\
                </div>';

    var condata = {'title': parame.title, 'mobile': parame.mobile, 'source': parame.source};
    var render = template.compile(temp);
    var html = render(condata);
    $.popModal({'content': html, datatype: '', 'width': '350'});
    return false;
}


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
    $.getScript('/static/nc/js/utils/jquery.fileupload.js', function () {
        $.ajaxFileUpload({
            url: upload_url,
            secureuri: false,
            fileObject: this_obj,
            dataType: 'json',
            timeout: 5000,
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
$(document).on('click', '.upload-item .del', function () {
    var obj = $(this).parent();
    //var evidenceId=obj.attr('id');
    jConfirm('确定要删除该图片吗？', '', function (r) {
        if (r) {
            obj.remove();
        }
    });
    return false;
});


 

