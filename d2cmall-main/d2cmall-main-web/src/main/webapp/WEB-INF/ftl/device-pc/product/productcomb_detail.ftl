<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${productComb.name}' keywords="${productComb.name},预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${productComb.name},消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" js='utils/unslider.min' />
<@m.top_nav />
<div class="layout layout-response layout-product clearfix">
    <div class="breadcrumb">
        <i class="fa fa-caret-right"></i> <a href="/">D2C首页</a> &nbsp;>&nbsp; <span>组合搭配</span> &nbsp;>&nbsp;
        <span>${productComb.name}</span>
    </div>
    <div class="product-package clearfix">
        <div class="product-package-img" id="slide-img">
            <ul>
                <#list productComb.products as product>
                    <li><a href="${base}/product/${product.id}" target="_blank" title="" alt=""><img
                                    id="large-img-${product.id}"
                                    src="${picture_base}/${(product.productImageCover)!}!media"><span>${product.name}</span></a>
                    </li>
                </#list>
            </ul>
        </div>
        <div class="product-package-info">
            <h1 class="title">${productComb.name}</h1>
            <#if productComb.subTitle!=''><h2>${productComb.subTitle}</h2></#if>
            <div class="prices">
                <span class="current"><span>套餐价</span>&nbsp; &yen; <#if productComb.price &lt; 0>????.??<#else>${productComb.price?string('0.00')}</#if></span>
                <#if productComb.originalCost gt productComb.price>
                    <span class="original">原价  <s>&yen;${productComb.originalCost}</s></span>
                    <span class="original">立省 ${(productComb.originalCost - productComb.price)} 元</span>
                </#if>
            </div>
            <form id="product-package-form" action="/order/comb/buynow" method="post">
                <input name="productCombId" type="hidden" value="${productComb.id}"/>
                <#list productComb.products as product>
                    <div class="item" data-id="${product.id}">
                        <input name="skuId" type="hidden" value=""/>
                        <input type="hidden" name="store" value=""/>
                        <a href="${base}/product/${product.id}" target="_blank" class="item-img" target="_blank"><img
                                    width="80" height="125"
                                    src="${picture_base}/${(product.productImageCover)!}!120"></a>
                        <p class="title"><a href="${base}/product/${product.id}"
                                            target="_blank">${product.name}</a><#if product.mark==0><span class="red">【已下架】</span></#if>
                        </p>
                        <div class="product-sku" style="height:100px">
                            <div class="img" style="width:88%">
                                <label>颜色 </label>
                                <div class="value">
                                    <#list product.salesproperty?eval.sp1 as p>
                                        <a href="javascript:" class="color-section c-${product.id}-${p.id}"
                                           data-type="color" data-id="${p.id}" data-self="${product.id}${p.id}"
                                           rel="${p.value}" title="${p.value}"><#if p.img><span class="img"><img
                                                        src="${picture_base}/${p.img}!50"
                                                        data-image="${picture_base}/${p.img}!media"
                                                        ori-image="${picture_base}/${p.img}" alt=""/></span></#if>
                                            <i>已选择</i></a>
                                    </#list>
                                </div>
                            </div>
                            </br>
                            <div class="txt">
                                <label>尺码 </label>
                                <div class="value">
                                    <#list product.salesproperty?eval.sp2 as p>
                                        <a href="javascript:" class="size-section s-${product.id}-${p.id}"
                                           data-type="size" data-id="${p.id}" data-self="${product.id}${p.id}"
                                           rel="${p.value}"><span>${p.value}</span><i>已选择</i></a>
                                    </#list>
                                </div>
                            </div>
                        </div>
                        <div style="clear:left"><span class="sku-select">请选择颜色尺码</span><span class="store-span"></span>
                        </div>
                    </div>
                </#list>
                <div class="product-package-buy">
                    <span class="select-count"
                          style="float:left;font-size:14px;margin-left:1%;margin-right:5px">购买</span>
                    <div class="product-package-num">
                        <em class="data-crease decrease" unselectable="on" onselectstart="return false;">-</em>
                        <input type="text" name="num" value="1" size="1" maxlength="3" class="data-num"/>
                        <em class="data-crease increase" unselectable="on" onselectstart="return false;">+</em>
                    </div>
                    &nbsp;&nbsp; <span style="vertical-align:-webkit-baseline-middle">商品总价：&yen;</span> <strong
                            class="totle-price"><#if productComb.price &lt; 0>????.??<#else>${productComb.price?string('0.00')}</#if></strong>
                    &nbsp;&nbsp;
                    <#if productComb.mark == 1>
                        <button type="button" name="pay" class="button button-xl button-red">立即结算</button>
                    <#else>
                        <button type="button" class="button button-xl" disabled>已经下架</button>
                    </#if>
                </div>
            </form>
        </div>
    </div>
    <div class="product-cont-bar scroll-suspend" data-offset="50" data-scroll-holder="product-suspend-holder"
         data-scroll-bg="product-cont-tab-bar" data-scroll-end="product-hot">
        <div class="tab clearfix">
            <ul>
                <li class="on" data-id="desc">商品详情</li>
                <li data-id="comment"
                    data-url="/productComb/comment?ids=<#list productComb.products?if_exists as product>${product.id}<#if product_index!=productComb.products?size-1 >,</#if></#list>">
                    客户评价 <span></span></li>
            </ul>
        </div>

        <div class="cart-view">
            <span>手机上购买</span>
            <img src="/picture/code?type=1&width=120&height=120&noLogo=true&&code=//m.d2cmall.com/productComb/${productComb.id}"/>
        </div>

    </div>

    <div id="product-detail" style="height:0;"></div>
    <div id="product-suspend-holder" class="product-suspend-holder"></div>
    <div class="product-cont-tab-cont" style="clear:right">
        <div class="product-detail-right">
            <div class="product-info-suspend scroll-suspend" data-offset="70" data-scroll-end="product-hot">
                <form name="form-buy" id="form-buy" action="/order/comb/buynow" method="post">
                    <input name="productCombId" type="hidden" value="${productComb.id}"/>
                    <#list productComb.products as product>
                        <div class="item" data-id="${product.id}"
                             style="border-bottom:1px dotted #CCC;margin-bottom:10px">
                            <input name="skuId" type="hidden" value=""/>
                            <input type="hidden" name="store" value=""/>
                            <h1 class="product-title">${product.name}</h1>
                            <div class="product-sku" style="border-top:none">
                                <div class="img clearfix">
                                    <label>颜色 </label>
                                    <div class="value">
                                        <#if product.salesproperty>
                                            <#list product.salesproperty?eval.sp1 as p>
                                                <a href="javascript:" class="color-section c-${product.id}-${p.id}"
                                                   data-type="color" data-id="${p.id}" data-self="${product.id}${p.id}"
                                                   rel="${p.value}" title="${p.value}"><#if p.img><span class="img"><img
                                                                src="${picture_base}/${p.img}!50"
                                                                data-image="${picture_base}/${p.img}!media"
                                                                ori-image="${picture_base}/${p.img}" alt=""/>
                                                    </span></#if><i>已选择</i></a>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                                <div class="txt clearfix">
                                    <label>尺码 </label>
                                    <div class="value">
                                        <#if product.salesproperty>
                                            <#list product.salesproperty?eval.sp2 as p>
                                                <a href="javascript:" class="size-section s-${product.id}-${p.id}"
                                                   data-type="size" data-id="${p.id}" data-self="${product.id}${p.id}"
                                                   rel="${p.value}"><span>${p.value}</span><i>已选择</i></a>
                                            </#list>
                                        </#if>
                                    </div>
                                </div>
                                <div style="clear:left;color:#FF6600"><span class="sku-select">请选择颜色尺码</span><span
                                            class="store-span" style="color:#000"></span></div>
                            </div>
                        </div>
                    </#list>

                    <div class="txt clearfix" style="margin-top:20px">
                        <label>数量</label>
                        <div class="value">
                            <em class="data-crease decrease" unselectable="on" onselectstart="return false;">-</em>
                            <input type="text" name="num" value="1" size="1" maxlength="3" class="data-num"/>
                            <em class="data-crease increase" unselectable="on" onselectstart="return false;">+</em>
                            <input type="hidden" class="store" value=""/>
                            <span class="store-span"></span>
                        </div>
                    </div>
                    <div class="clearfix" style="margin-top:10px;position:relative;">
                        <#if productComb.mark == 1>
                            <button name="pay" type="button" id="combo-buy" class="button button- button-red"
                                    data-form="bottom" data-type="buy" style="width:60%">立即购买
                            </button>
                        <#else>
                            <button type="button" class="button button-l" style="width:60%" disabled>已经下架</button>
                        </#if>
                    </div>


                </form>
            </div>
        </div>
        <div id="product-detail-desc" class="product-desc lazyload load-process">
            <#if productComb.description?exists && productComb.description!=''>
                ${productComb.description?replace('<img src=','<img src="${static_base}/c/images/space.gif" data-image=')}
            <#elseif productComb.mobileDesc?exists && productComb.mobileDesc!=''>
                ${productComb.mobileDesc}
            </#if>

        </div>
        <div id="product-detail-comment" class="product-comment display-none">
        </div>
    </div>
</div>

<div id="product-hot"></div>
</div>

<script>
    var slide = $('#slide-img').unslider({dots: true,});
    var slide_data = slide.data('unslider');

    $('.product-sku a').click(function () {
        var obj = $(this), package_item = obj.parent().parent().parent().parent();
        var same_obj = $('.product-sku .value a[data-type="' + obj.attr('data-type') + '"][data-self="' + obj.attr('data-self') + '"]');
        var paren = same_obj.parent().parent().parent().parent();
        var product_id = package_item.attr('data-id');
        if (obj.attr('class').indexOf('disabled') == -1) {
            if (obj.hasClass('selected')) {
                same_obj.removeClass('selected');
                obj.hasClass('color-section') ? $('.size-section').removeClass('disabled').removeAttr('disabled') : $('.color-section').removeClass('disabled').removeAttr('disabled');
            } else {
                same_obj.siblings().removeClass('selected');
                same_obj.addClass('selected');
            }
        }
        if (obj.hasClass('color-section')) {

            var index = $(this).attr('index');
            slide_data.to(index);
            var data_image = $(this).find('img').attr('data-image');
            var mid_image = $('#large-img-' + product_id).attr('src');
            if (data_image != mid_image) {
                $('#large-img-' + product_id).attr('src', data_image);
            }
        }


        var str = '', param = '', select_tips = [];
        $.each(package_item.find('.selected'), function (i, d) {
            select_tips.push('"' + $(d).attr('rel') + '"');

            param += str + $(d).attr('data-type') + '=' + $(d).attr('data-id');
            str = '&';
        });
        if (select_tips.length > 0) {
            paren.find('.sku-select').html('你选择了：' + select_tips);
        } else {
            paren.find('.sku-select').html('请选择颜色尺码');
        }
        if (param) {
            $.post('/product/getSKUInfo/' + product_id, param, function (data) {
                if (data.result.status == 1) {
                    var store = data.result.store;
                    if (typeof (data.result.Size) == 'object') {
                        paren.find('.img a').addClass('disabled').attr('disabled', true);
                        $.each(data.result.Size, function (i, d) {
                            if (d.store > 0)
                                $('.c-' + product_id + '-' + d.Color_id).removeClass('disabled').removeAttr('disabled');
                        });

                    }
                    if (typeof (data.result.Color) == 'object') {
                        paren.find('.txt a').addClass('disabled').attr('disabled', true);
                        $.each(data.result.Color, function (i, d) {
                            if (d.store > 0)
                                $('.s-' + product_id + '-' + d.Size_id).removeClass('disabled').removeAttr('disabled');
                        });
                    }

                    data.result.store ? paren.find('.store-span').html(' &nbsp;库存' + data.result.store + '件') : paren.find('.store-span').html('');

                    paren.find('input[name=store]').val(store);
                    if (typeof (data.result.Color) == 'object' && typeof (data.result.Size) == 'object') {
                        if (data.result.skuId != undefined) {
                            paren.find('input[name=skuId]').val(data.result.skuId);
                        }
                    } else {
                        paren.find('input[name=skuId]').val('');
                    }
                    paren.removeClass('no-select');
                } else if (data.result.status == -1) {
                    $('.product-sku a').removeClass('disabled').removeAttr('disabled');
                    paren.find('input[name=store]').val(store);
                    if (data.result.skuId != undefined) {
                        paren.find('input[name=skuId]').val('');
                    }
                }
            }, 'json');
        }
        return false;
    });

    var height = $('.product-detail-right').height();


    //设置默认值
    var colorsection, sizesection;
    $.each($('.package-item'), function (i, item) {
        colorsection = $(item).find('a.color-section'), sizesection = $(item).find('a.size-section');
        if (colorsection.size() == 1) {
            colorsection.trigger('click');
        }
        if (sizesection.size() == 1) {
            sizesection.trigger('click');
        }
    })

    //数量增减
    var totalprice;
    $('.increase').click(function () {
        var i = parseInt($('input[name=num]').val()) + 1;
        $('input[name=num]').val(i);
        totalprice = $.utilFormatCurrency(${productComb.price?string('0.00')} * i
    )
        $('.totle-price').html(totalprice);
    });
    $('.decrease').click(function () {
        var i = parseInt($('input[name=num]').val()) - 1;
        if (i <= 0) {
            i = 1;
        }
        $('input[name=num]').val(i);
        totalprice = $.utilFormatCurrency(${productComb.price?string('0.00')} * i
    )
        $('.totle-price').html(totalprice);
    });


    $('.product-cont-bar .tab li').click(function () {
        $('html, body').animate({scrollTop: $('#product-detail').offset().top}, 300);
        var id = $(this).attr('data-id');
        var url = $(this).attr('data-url')
        $(this).addClass('on');
        $(this).siblings().removeClass('on');
        $('#product-detail-' + id).show();
        $('#product-detail-' + id).siblings('div[id]').hide();
        if (url) {
            $.get(url, function (data) {
                $('#product-detail-' + id).html(data);
            });
        }
        return false;
    });


    /*修改套数*/
    $("input[name=num]").change(function () {
        totalprice = $.utilFormatCurrency(${productComb.price?string('0.00')} * parseFloat($(this).val())
    )
        $('.totle-price').html(totalprice);
    });

    $('button[name=pay]').click(function () {

        var error = false;
        var arr = store = new Array();
        $.each($('input[name=skuId]'), function (i, item) {
            if ($(item).val() == "") {
                arr.push(i);
                error = true;
            }
        });

        if (error == true) {
            $.each(arr, function (i, d) {
                $('.item:eq(' + d + ')').addClass('no-select');
            });
            jAlert('有 <strong>' + (arr.length / 2) + '</strong> 件商品没有选择颜色和尺码！');
            return false;
        }

        $.each($('input[name=store]'), function (i, d) {
            if (parseInt($(d).val()) < parseInt($('input[name=num]').val())) {
                store.push(i);
                error = true;
            }
        });

        if (error == true) {
            $.each(arr, function (i, d) {
                $('.package-product-item:eq(' + d + ')').addClass('no-select');
            });
            jAlert('有 <strong>' + (arr.length / 2) + '</strong> 件商品库存不足以购买 ' + $('input[name=num]').val() + ' 套！');
            return false;
        }
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', '$("#product-package-form").submit()');
                userLogin();
            } else {
                $('#product-package-form').submit();
            }
        });
    });
</script>

<@m.page_footer />