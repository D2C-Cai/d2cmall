$(document).ready(function () {
//首页实体店
    if ($('#store-list').size() > 0) {
        var store_list = [
            {
                'city': '长春',
                'name': '卓展',
                'address': '吉林省长春市重庆路1255号卓展购物中心4楼',
                'tel': '0431-88483292',
                'img': '//static.d2c.cn/img/home/150420/store/18.jpg',
                'href': '/store/code/23'
            },
            {
                'city': '长春',
                'name': '欧亚商都',
                'address': '长春市工农大路1218号，欧亚商都  3楼   D2C专柜 ',
                'tel': '0431-85187393',
                'img': '//static.d2c.cn/img/home/150420/store/18.jpg',
                'href': '/store/code/23'
            },
            {
                'city': '重庆',
                'name': '重庆北城天街店',
                'address': '重庆江北区观音桥北城天街X-1F-003 ',
                'tel': '023 67953056',
                'img': '//static.d2c.cn/img/home/150420/store/11.jpg',
                'href': '/store/code/10'
            },
            {
                'city': '福建',
                'name': '厦门SM广场店',
                'address': '福建省厦门市嘉禾路厦门SM城市广场二期蓝宝石二楼D2C专柜',
                'tel': '0592-5062967',
                'img': '//static.d2c.cn/img/home/150420/store/store_sm.jpg',
                'href': '/store/code/10'
            },
            {
                'city': '杭州',
                'name': '杭州大厦店',
                'address': '杭州市下城区杭州大厦C座三楼 ',
                'tel': '0571-87660190',
                'img': '//static.d2c.cn/img/home/150420/store/11.jpg',
                'href': '/store/code/10'
            },
            {
                'city': '杭州',
                'name': '万象城',
                'address': '浙江省杭州市江干区富春路万象城购物中心4楼487号商铺',
                'tel': '0571-81186238',
                'img': '//static.d2c.cn/img/home/150420/store/13.jpg',
                'href': '/store/code/4'
            },
            {
                'city': '哈尔滨',
                'name': '尚都新天地',
                'address': '哈尔滨南岗区建设街142号尚都新天地2层',
                'tel': '0451_87588602',
                'img': '//static.d2c.cn/img/home/150420/store/33.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '合肥',
                'name': '银泰',
                'address': '安徽省合肥市庐阳区 长江中路98号',
                'tel': '0551-65852159',
                'img': '//static.d2c.cn/img/home/150420/store/15.jpg',
                'href': '/store/code/13'
            },
            {
                'city': '江苏',
                'name': '扬州金鹰店',
                'address': '扬州金鹰购物中心文昌店3楼 D2C 专柜 扬州市汶河南路120号 ',
                'tel': '0514-87116163',
                'img': '//static.d2c.cn/img/home/150420/store/29.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '江苏',
                'name': '徐州金鹰店',
                'address': '江苏省徐州市鼓楼区金鹰彭城广场店三楼D2C专柜 ',
                'tel': '0516-83701665',
                'img': '//static.d2c.cn/img/home/150420/store/29.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '无锡',
                'name': '无锡百联奥特莱斯',
                'address': '无锡市新吴区（原新区）锡勤路18-28号，无锡百联奥特莱斯A5-107 ',
                'tel': '',
                'img': '//static.d2c.cn/img/home/150420/store/29.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '吉林',
                'name': '欧亚大卖场',
                'address': '吉林省长春市朝阳区欧亚卖场开运街5178号  一楼9号门  D2C专柜',
                'tel': '0431-85529040',
                'img': '//static.d2c.cn/img/home/150420/store/29.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '昆明',
                'name': '云南昆明店',
                'address': '云南省昆明市崇仁街9号顺城购物中心E2-08A',
                'tel': '0871-63637720',
                'img': '//static.d2c.cn/img/home/150420/store/12.jpg',
                'href': '/store/code/8'
            },
            {
                'city': '南京',
                'name': '德基',
                'address': '南京市中山路18号德基广场二期F415号商铺',
                'tel': '025-86777422',
                'img': '//static.d2c.cn/img/home/150420/store/06.jpg',
                'href': '/store/code/19'
            },
            {
                'city': '青岛',
                'name': '万象城',
                'address': '山东省青岛市市南区山东路10号华润万象城 L226B号 D2C',
                'tel': '13396482102',
                'img': '//static.d2c.cn/img/home/150420/store/22.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '湖北',
                'name': '武汉国际广场店',
                'address': '武汉市江岸区解放大道690号武汉国际广场A区3楼',
                'tel': '027-85321088',
                'img': '//static.d2c.cn/img/home/150420/store/14.jpg',
                'href': '/store/code/20'
            },
            {
                'city': '石家庄',
                'name': '北国商城',
                'address': '石家庄中山东路188号北国商城三楼C区D2C',
                'tel': '0311-89669373',
                'img': '//static.d2c.cn/img/home/150420/store/20.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '沈阳',
                'name': '万象城',
                'address': '辽宁省沈阳市和平区青年大街286号华润万象城4F-415-D2C',
                'tel': '024-31379266',
                'img': '//static.d2c.cn/img/home/150420/store/24.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '沈阳',
                'name': '兴隆大奥莱店',
                'address': '沈阳市浑南新区营盘北街5号兴隆大奥莱A座3楼D2C',
                'tel': '024-31263091',
                'img': '//static.d2c.cn/img/home/150420/store/21.jpg',
                'href': '/store/code/24'
            },
            {
                'city': '新疆',
                'name': '美美百货',
                'address': '新疆乌鲁木齐市沙依巴克区友好北路 美美购物中心 二楼 D2C',
                'tel': '0991-4819050',
                'img': '//static.d2c.cn/img/home/150420/store/21.jpg',
                'href': '/store/code/24'
            }
        ];
        var name_str = detail_str = address_str = '';
        $.each(store_list, function (i, store) {
            name_str += '<a href="javascript:"';
            if (i == 0) {
                name_str += ' class="on"';
                detail_str = '<div><img src="' + store.img + '" alt="' + store.city + store.name + '" /></div>';
                address_str = '<div class="store-address"><h3 style="margin:0;padding-top:10px;">' + store.city + store.name + '店 &nbsp;&nbsp;' + store.tel + '</h3><p class="clearfix" style="margin:0;padding-top:10px;line-height:20px"><span class="float-left">地址：</span><span style="display:block;margin-left:35px;">' + store.address + '</span></p></div></div>';
            }
            name_str += ' data-id="' + i + '">' + store.city + '<em>' + store.name + '</em></a>';
        });
        $('#store-list').addClass('clearfix').html(name_str);
        $('#store-detail').html(detail_str);
        $('#store-address').addClass('clearfix').html(address_str);
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
                address_str = '<div class="store-address"><h3 style="margin:0;padding-top:10px;">' + store.city + store.name + '店 &nbsp;&nbsp;' + store.tel + '</h3><p class="clearfix" style="margin:0;padding-top:10px;line-height:20px"><span class="float-left">地址：</span><span style="display:block;margin-left:35px;">' + store.address + '</span></p></div>';
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
        $(".starshow  ul li:eq(1)").css("background", "url(//static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
        $(".starshow  ul li:eq(1)").find("a").append("<img src='//static.d2c.cn/img/home/150422/star/img/tringle.png' class='left-t' width='4' height='64'/>");
        $(".starshow  ul li:eq(11)").css("background", "url(//static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
        $(".starshow  ul li:eq(11)").find("a").append("<img src='//static.d2c.cn/img/home/150422/star/img/tringle.png' class='left-t' width='4' height='64'/>");
        $('.starshow ul li a').hover(function () {
            var obj = $(this);
            t = setTimeout(function () {
                var href = obj.attr("href");
                obj.parent().parent().parent().find('.star-hover-img').attr('href', href);
                var url = obj.attr("data-rel");
                obj.parent().parent().parent().find('.pic-box ').attr('src', url);
                var tleft = "<img src='//static.d2c.cn/img/home/150422/star/img/tringle.png'  class='left-t' width='4' height='64'/>";
                var tright = "<img src='//static.d2c.cn/img/home/150422/star/img/tringle_2.png'  class='right-t' width='4' height='64'/>";
                var id = $(".starshow ul li a").index(obj);
                if (id % 2 == 1) {
                    if (obj.parent().find('.left-t').length == 0) {
                        obj.append(tleft);
                    }
                    obj.parent().css("background", "url(//static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
                    obj.parent().siblings().css("background", "");
                    obj.parent().siblings().find('.left-t').remove();
                    obj.parent().siblings().find('.right-t').remove();

                } else {
                    if (obj.parent().find('.right-t').length == 0) {
                        obj.append(tright);
                    }
                    obj.parent().css("background", "url(//static.d2c.cn/img/home/150422/star/img/btn-hover.png)");
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
    $('.float-with-hover').hover(function () {
        $(this).find('.float-with-btn img').addClass('top');
    }, function () {
        $(this).find('.float-with-btn img').removeClass('top');
    });
    $('.hover-img-one').hover(function () {
        $(this).find('.hover-img-two').addClass('on');
    }, function () {
        $(this).find('.hover-img-two').removeClass('on');
    });

})