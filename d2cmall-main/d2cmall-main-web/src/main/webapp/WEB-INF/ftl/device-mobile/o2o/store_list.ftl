<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='实体店' keywords='预售,预售优惠,服装设计师,原创品牌' description='D2C全球好设计' service='false' hastopfix='false'/>
<@m.page_nav_bar channel="store" />
<div class="section lazyload">
    <div class="list">
        <#list list as store>
            <#if store.xy?exists>
                <#assign map_arr=store.xy?replace('，',',')?split(',')>
                <#assign map=map_arr[1]+','+map_arr[0]>
            </#if>
            <div class="item item-vertical">
                <p class="img"><img src="${static_base}/m/img/blank100x45.png"
                                    data-image="${picture_base}${(store.pic)!}"/></p>
                <p class="desc">名称：${(store.name)!}<a class="float-right link-map" data-name="${(store.name)!}"
                                                      data-address="${(store.address)!}/${(store.tel)!}"
                                                      data-lant="${map_arr[1]}" data-long="${map_arr[0]}"
                                                      href="<#if browser=='wechat'>javascript:<#else>//mo.amap.com/?q=${map}&name=${(store.name)!} ${(store.address)!}&dev=0</#if>">查看地图</a>
                </p>
                <p class="desc">地址：${(store.address)!}</p>
                <p class="desc">电话：<a href="tel:${(store.tel)!}">${(store.tel)!}</a></p>
            </div>
        </#list>
    </div>
</div>
<#if browser=='wechat'>
    <script>
        //wx.ready(function () {
        $('.link-map').click(function () {
            var latitude = parseFloat($(this).attr('data-lant')),
                longitude = parseFloat($(this).attr('data-long')),
                store_name = $(this).attr('data-name'),
                sore_address = $(this).attr('data-address');
            wx.openLocation({
                latitude: latitude,
                longitude: longitude,
                name: store_name,
                address: sore_address,
                scale: 15,
                infoUrl: '//m.d2cmall.com'
            });
            return false;
        });
        //});
    </script>
</#if>
<@m.page_footer />