<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='分销商品清单' noheader=true />
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/home" class="back">
            <span class="icon icon-chevron-left">分销商品清单</span>
        </a>
        <a href="javascript:location.reload();" class="bar-btn"><span class="icon icon-refresh"></span></a>
        <a href="/cart/list" class="bar-btn"><em class="my-cart-num">0</em><span class="icon icon-shopping-cart"></span></a>
    </div>
</div>
<div style="height:2.9em;"></div>
<div class="order-status-bar">
    1.同一款商品多次添加，会在原购物车商品数量上增加。<br/>
    2.若需要修改最终下单数量可以在购物车里进行修改或删除。<br/>
</div>
<div class="search-bar">
    <form name="" action="" action="get">
        <input name="productSn" value="${(RequestParameters.productSn)!}" class="input-l" placeholder="输入款号进行搜索"/>
        <button type="submit" name="">搜索</button>
    </form>
</div>
<div class="wrap-main lazyload">
    <#if result.data.setting?exists && result.data.setting.list?exists && result.data.setting.list?size gt 0>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=result.data.setting.pageNumber totalpage=result.data.setting.pageCount />
        </div>
        <#list result.data.setting.list as setting>
            <div class="order-item wrap-div" style="margin-bottom:0.6em;">
                <div class="item-main clearfix">
                    <p class="img-big" style="min-height:10em;">
                        <img src="${static_base}/m/img/blank.gif"
                             data-src="${picture_base}${(setting.product.productImageCover)!}!180"
                             alt="${setting.product.name}"/>
                    </p>
                    <p class="title-bar" style="height:3.5em;">${(setting.product.name)!}</p>
                    <p class="price-bar">货 &nbsp; 号：${(setting.product.inernalSn)!}</p>
                    <p class="price-bar">品 &nbsp; 牌：${(setting.product.designer.name)!}</p>
                    <p class="price-bar">吊牌价：&yen; ${(setting.product.originalPrice?string(',000.00'))!}</p>
                    <p class="price-bar">折 &nbsp; 扣：${(setting.discount*10)!} 折</p>
                    <p class="price-bar">折后价：&yen; ${(setting.price?string(',000.00'))!}</p>
                </div>
                <form class="item-cart">
                    <div class="item-sku-list">
                        <#list setting.product.productSKUSet as productSku>
                            <ul class="clearfix">
                                <li>
                                    <input type="hidden" name="item" value="${(productSku.sn)!}"/>
                                    <p><#if productSku.sp1!=null>${(productSku.sp1?eval.value)!} &nbsp;</#if><#if productSku.sp2!=null>${(productSku.sp2?eval.value)!} &nbsp;</#if><#if productSku.sp3!=null>${(productSku.sp3?eval.value)!}</#if>
                                    </p>
                                </li>
                                <li>
                                    <div class="num-crease-wrap"><span class="icon-minus decrease"></span><input
                                                type="text" class="product-count num" name="quantity" readonly size="1"
                                                value="0"><span class="icon-plus increase"></span></div>
                                    <input type="hidden" class="store" value="${(productSku.store)!}"/>
                                </li>
                            </ul>
                        </#list>
                    </div>
                </form>
                <div class="layer-buy">
                    <button type="button" class="add-cart button-buy cyan" style="width:100%">加入购物车</button>
                </div>
            </div>
        </#list>
        <div class="pages">
            <@m.simple_pager page=result.data.setting.pageNumber totalpage=result.data.setting.pageCount />
        </div>
    <#else>
        <div class="text-center" style="line-height:100px;">暂无数据</div>
    </#if>
</div>
<hr/>
<script>
    $('.decrease').on('touchstart', function () {
        var obj = $(this);
        var num_obj = obj.siblings('.num');
        var i = parseInt(num_obj.val()) - 1;
        if (i <= 0) {
            i = 0;
        }
        num_obj.val(i);
    });
    $('.increase').on('touchstart', function () {
        var obj = $(this);
        var num_obj = obj.siblings('.num');
        var store_obj = obj.parent().siblings('.store');
        var i = parseInt(num_obj.val()) + 1;
        if (i >= parseInt(store_obj.val())) {
            jAlert('最多只能买' + store_obj.val() + '件');
            i = store_obj.val();
        }
        num_obj.val(i);
        return false;
    });

    $('.add-cart').click(function () {

        var obj = $(this).parent().parent();
        var h = 0;
        $.each(obj.find('.num'), function (i, d) {
            if ($(d).val() > 0) {
                h++;
            }
        });
        if (h == 0) {
            jAlert('至少要选择一件商品才能添加到购物车');
            return false;
        }
        var str = split = '';
        $.each(obj.find('.item-cart').find('input'), function (i, d) {
            if ($(d).attr('name')) {
                str += split + $(d).attr('name') + '=' + $(d).val();
                split = '&';
            }
        });
        $.post('/cart/batchItem.json', str, function (data) {
            var html = '';
            html += '<div id="alert-box-mask" class="box-mask" style="display:block"></div>';
            html += '<div id="alert-box"><div><p id="alert-msg">成功加入购物车！</p><p id="alert-buttons">\
		<button onclick="$(\'#alert-box-mask\').remove();$(\'#alert-box\').remove();">继续挑选</button><button onclick="location.href=\'/cart/list\';">去结算</button></p></div></div>';
            $('body').append(html);
            count_cart();
        });
        return false;
    });
</script>
<@m.page_footer />
