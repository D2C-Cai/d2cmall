<#import "templates/public_pc.ftl" as m>
<@m.page_header title='实体店' keywords='预售,预售优惠,服装设计师,原创品牌' description='D2C全球好设计' />
<@m.top_nav channel="store" suspend=false />
<div class="layout layout-response layout-store lazyload">
    <div class="tab store-tab">
        <ul>
            <li<#if RequestParameters.by!='map'> class="on"</#if>><a href="?by=area">按地区查看</a></li>
            <li<#if RequestParameters.by=='map'> class="on"</#if>><a href="?by=map">使用地图查看</a></li>
        </ul>
    </div>
    <div class="city-list">
        <a href="javascript:">所有</a>
        <a href="javascript:">北京</a>
        <a href="javascript:">上海</a>
        <a href="javascript:">深圳</a>
        <a href="javascript:">杭州</a>
        <a href="javascript:">南京</a>
        <a href="javascript:">重庆</a>
        <a href="javascript:">成都</a>
        <a href="javascript:">青岛</a>
        <a href="javascript:">沈阳</a>
        <a href="javascript:">长春</a>
        <a href="javascript:">合肥</a>
        <a href="javascript:">郑州</a>
        <a href="javascript:">石家庄</a>
        <a href="javascript:">昆明</a>
        <a href="javascript:">南通</a>
        <a href="javascript:">宜兴</a>
        <a href="javascript:">南宁</a>
        <a href="javascript:">温州</a>
    </div>
    <#if RequestParameters.by!='map'>
        <div class="list store-list clearfix">
            <#list list as store>
                <#if store.xy?exists>
                    <#assign map_arr=store.bdxy?replace('，',',')?split(',')>
                </#if>
                <div class="list-item" data-name="${(store.name)!}" data-address="${(store.address)!}"
                     data-tel="${(store.tel)!}" data-lant="${map_arr[0]}" data-long="${map_arr[1]}">
                    <a href="javascript:" class="img"><img alt="" border="0" src="/static/blank/500-288.gif"
                                                           data-image="${picture_base}${(store.pic)!}!600"/></a>
                    <p><strong>${(store.name)!}</strong></p>
                    <p>地址：${(store.address)!}</p>
                    <p>电话：<a href="tel:${(store.tel)!}">${(store.tel)!}</a></p>
                </div>
            </#list>
        </div>
    <#else>
        <div class="store-map clearfix">
            <div class="store-map-list">
                <ul>
                    <#list list as store>
                        <li data-id="${store_index}" data-name="${(store.name)!}">
                            <strong>${store.name}</strong><br/>${store.address}<br/><i
                                    class="fa fa-phone"></i> ${store.tel}</li>
                    </#list>
                </ul>
            </div>
            <div id="map" class="store-map-baidu">
            </div>
        </div>
    </#if>
</div>
<script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=dqwGBiBmEacXmFLGlDEQsAAC"></script>
<script>
    <#if RequestParameters.by=='area'||RequestParameters.by==''>
    $('.city-list a').click(function () {
        $(this).siblings().removeClass('on');
        $(this).addClass('on');
        var city = $(this).text();
        if (city != '所有') {
            $('.list-item').hide();
            $.each($('.list-item'), function (i, d) {
                var store_name = $(d).attr('data-name');
                if (store_name.indexOf(city) != -1) {
                    if ($(d).find('img').attr('data-image')) {
                        $(d).find('img').attr('src', $(d).find('img').attr('data-image')).removeAttr('data-image');
                    }
                    $(d).fadeIn('fast');
                }
            });
        } else {
            $('.list-item').show();
        }
        return false;
    });
    $('.list-item').click(function () {
        var str = '<div id="map" style="height:500px;margin:-22px -15px -15px -15px;"></div>';
        $.popModal({content: str, width: 700});
        var name = $(this).attr('data-name'),
            address = $(this).attr('data-address'),
            tel = $(this).attr('data-tel'),
            lant = $(this).attr('data-lant'),
            long = $(this).attr('data-long'),
            img = $(this).find('img').attr('src');
        var info = '<h4 style="margin:0 0 5px 0;padding:0.2em 0;font-size:14px;">' + name + '</h4>\
        <p style="font-size:12px;">地址：' + address + '</p>\
        <p style="font-size:12px;">电话：' + tel + '</p>\
        </div>';
        var map = new BMap.Map("map");
        var point = new BMap.Point(lant, long);
        var marker = new BMap.Marker(point);
        var infoWindow = new BMap.InfoWindow(info);  // 创建信息窗口对象
        //map.setMapStyle({style:'googlelite'});
        map.addControl(new BMap.NavigationControl());
        map.centerAndZoom(point, 19);
        map.enableScrollWheelZoom();
        map.enableContinuousZoom();
        map.addOverlay(marker);
        //map.openInfoWindow(infoWindow,point);
        marker.addEventListener("click", function () {
            this.openInfoWindow(infoWindow);
            document.getElementById('store-img').onload = function () {
                infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
            }
        });
        return false;
    });
    <#else>
    var store_data = [
        <#list list as store>
        <#if store.xy?exists>
        <#assign map_arr=store.bdxy?replace('，',',')?split(',')>
        </#if>
        <#if store_index!=(list?size)-1>
        <#assign period=','>
        <#else>
        <#assign period=''>
        </#if>
        {
            'id': '${store.id}',
            'name': '${store.name}',
            'address': '${store.address}',
            'tel': '${store.tel}',
            'lant': '${map_arr[0]}',
            'long': '${map_arr[1]}',
            'img': '${picture_base}${(store.pic)!}!280'
        }${period}
        </#list>
    ];
    var map = new BMap.Map("map", {});                        // 创建Map实例
    map.centerAndZoom(new BMap.Point(123.000, 36.000), 5);     // 初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom();                        //启用滚轮放大缩小
    map.addControl(new BMap.NavigationControl());
    var opts = {
        enableMessage: true//设置允许信息窗发送短息
    };
    var opts = {
        width: 350     // 信息窗口宽度
    };
    $.each(store_data, function (i, d) {
        var marker = new BMap.Marker(new BMap.Point(d.lant, d.long));  // 创建标注
        var content = '<h4 style="margin:0 0 5px 0;padding:0.2em 0;font-size:14px;">' + d.name + '</h4>\
        <p style="font-size:12px;">地址：' + d.address + '</p>\
        <p style="font-size:12px;">电话：' + d.tel + '</p>\
        <p style="text-align:center"><img src="' + d.img + '" alt="" height="120" /></p>\
        </div>';
        map.addOverlay(marker);               // 将标注添加到地图中
        addClickHandler(content, marker);
    });

    function addClickHandler(content, marker) {
        marker.addEventListener("click", function (e) {
                openInfo(content, e)
            }
        );
    }

    function openInfo(content, e) {
        var p = e.target;
        var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
        var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
        map.openInfoWindow(infoWindow, point); //开启信息窗口
    }

    $('.store-map-list li').click(function () {
        var id = $(this).attr('data-id');
        $(this).siblings().removeClass('on');
        $(this).addClass('on');
        if (id != '') {
            map.centerAndZoom(new BMap.Point(store_data[id].lant, store_data[id].long), 18);
        } else {
            map.centerAndZoom(new BMap.Point(123.000, 36.000), 5);
        }
    });
    $('.city-list a').click(function () {
        $(this).siblings().removeClass('on');
        $(this).addClass('on');
        var city = $(this).text();
        if (city != '所有') {
            map.centerAndZoom(city, 12);
            $('.store-map-list li').hide();
            $.each($('.store-map-list li'), function (i, d) {
                var store_name = $(d).attr('data-name');
                if (store_name.indexOf(city) != -1) {
                    $(d).show();
                }
            });
        } else {
            map.centerAndZoom(new BMap.Point(123.000, 36.000), 5);
            $('.store-map-list li').show();
        }
        return false;
    });
    </#if>
</script>
<@m.page_footer />