<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="组合搭配商品" title='${productComb.name}' keywords="${productComb.name},预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${productComb.name},消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<div class="section">
    <div class="product-base" style="padding-bottom:0.6em;">
        <h1 class="title">${productComb.name}</h1>
        <#if productComb.subTitle><h2 class="subtitle">${productComb.subTitle}</h2></#if>
        <div class="product-label">
            <label>套餐价</label><span class="data-price"
                                    id="promo-price"><#if productComb.price lte 0>????.??<#else>&yen; ${productComb.price?string('0.00')}</#if></span>
            <#if productComb.originalCost gt productComb.price>&nbsp;&nbsp;&nbsp;<label style="width:auto;">原价</label>
                <s class="grey">&yen; <strong>${(productComb.originalCost)}</strong></s></#if>
        </div>

    </div>
    <div class="tips" style="margin-top:-0.8em;">请选择套餐内商品的颜色和尺码</div>
    <form name="product-package-buy" id="package-buy" action="/order/comb/buynow" method="post">
        <input name="productCombId" type="hidden" value="${productComb.id}"/>
        <#list productComb.products as product>
            <div class="package-item">
                <a href="${mobileBase}/product/${product.id}" class="pakage-title clearfix">
                    <span class="img"><img src="${picture_base}/${(product.productImageCover)!}!80" alt=""/></span>
                    <span>${product.name}</span>
                </a>
                <div class="product-select">
                    <input type="hidden" name="skuId" value=""/><!-- 货号 -->
                    <input type="hidden" name="productId" value="${product.id}"/>
                    <div class="product-sku img clearfix" style="position:relative;">
                        <span class="font">颜色 </span>
                        <span class="value">
					<#list product.salesproperty?eval.sp1 as p>
                        <a href="javascript:" id="c-${product.id}-${p.id}" class="color-section" data-type="color"
                           data-id="${p.id}" rel="${p.value}"
                           data-image="<#if p.img!=''>${picture_base}/${p.img}</#if>">${p.value}</a>
                    </#list>
					</span>
                    </div>
                    <div class="product-sku txt clearfix">
                        <span class="font">尺码 </span>
                        <span class="value">
					<#list product.salesproperty?eval.sp2 as p>
                        <a href="javascript:" id="s-${product.id}-${p.id}" class="size-section" data-type="size"
                           data-id="${p.id}" rel="${p.value}">${p.value}</a>
                    </#list>
					</span>
                    </div>
                    <input type="hidden" name="store" value=""/>
                </div>
            </div>
        </#list>


        <div class="num-crease text-center" style="padding:0.6em 0;">
            <div style="line-height:150%;">购买数量</div>
            <div class="num-crease-wrap"><span class="num-decrease" id="decrease">－</span><span
                        class="num-input">1</span><span class="num-increase" id="increase">＋</span><input type="hidden"
                                                                                                          class="product-count"
                                                                                                          id="num"
                                                                                                          name="num"
                                                                                                          readonly
                                                                                                          value="1">
            </div>
        </div>
        <div class="suspend-bar">
            <div class="desc">应付：<span class="price">&yen; <strong id="now-pay-price" class="price"
                                                                   rel="${productComb.price}">${productComb.price?string("currency")?substring(1)}</strong> 元</span>
            </div>
            <div class="buttons"><#if productComb.mark==1>
                    <button type="submit" id="submit-button" class="button button-red">立刻购买</button><#else>
                    <div class="desc">已下架</div></#if></div>
        </div>
        <div style="height:4em"></div>
    </form>
</div>
<script>
    $('#decrease').on('touchstart', function () {
        var p = $('#now-pay-price').attr('rel');
        var i = parseInt($('#num').val()) - 1;
        if (i <= 0) {
            i = 1;
        }
        $('#num').val(i);
        $('.num-input').text(i);
        $('#now-pay-price').text($.utilFormatCurrency(i * p));
        return false;
    });
    $('#increase').on('touchstart', function () {
        var p = $('#now-pay-price').attr('rel');
        var i = parseInt($('#num').val()) + 1;
        $('#num').val(i);
        $('.num-input').text(i);
        $('#now-pay-price').text($.utilFormatCurrency(i * p));
        return false;
    });


    $('.product-sku a').on('touchstart', function () {
        var obj = $(this), package_item = $(this).parent().parent().parent(),
            product_id = package_item.find('input[name=productId]').val();
        if (obj.attr('class').indexOf('disabled') == -1) {
            if (obj.hasClass('selected')) {
                obj.removeClass('selected');
                obj.hasClass('color-section') ? $('.size-section').removeClass('disabled').removeAttr('disabled') : $('.color-section').removeClass('disabled').removeAttr('disabled');
            } else {
                obj.siblings().removeClass('selected');
                obj.addClass('selected');
                if (obj.attr('data-image')) {
                    package_item.find('.img img').attr('src', obj.attr('data-image') + '!120');
                }
            }
            var tips = new Array(), str = '', param = '';

            $.each(package_item.find('.selected'), function (i, d) {
                param += str + $(d).attr('data-type') + '=' + $(d).attr('data-id');
                str = '&';
            });


            if (param) {
                $.post('/product/getSKUInfo/' + product_id, param, function (data) {
                    var skuId = data.result.id == undefined ? '' : data.result.id;
                    var store = 0;
                    if (data.result.status == 1) {
                        if (typeof (data.result.Size) == 'object') {
                            package_item.find('.img a').addClass('disabled').attr('disabled', true);
                            $.each(data.result.Size, function (i, d) {
                                if (d.store > 0)
                                    $('#c-' + product_id + '-' + d.Color_id).removeClass('disabled').removeAttr('disabled');
                            });

                        }

                        if (typeof (data.result.Color) == 'object') {
                            package_item.find('.txt a').addClass('disabled').attr('disabled', true);
                            $.each(data.result.Color, function (i, d) {
                                if (d.store > 0)
                                    $('#s-' + product_id + '-' + d.Size_id).removeClass('disabled').removeAttr('disabled');
                            });

                        }
                        if (typeof (data.result.Color) == 'object' && typeof (data.result.Size) == 'object') {
                            store = data.result.store;
                            package_item.find('input[name=store]').val(store);
                            package_item.find('input[name=skuId]').val(skuId);
                        } else {
                            package_item.find('input[name=store]').val('');
                            package_item.find('input[name=skuId]').val('');
                        }


                    } else if (data.stauts == -1) {
                        package_item.find('.product-sku a').removeClass('disabled').removeAttr('disabled');
                        package_item.find('input[name=store]').val('');
                        package_item.find('input[name=skuId]').val('');
                    }
                }, 'json');
            } else {
                $('input[name=skuId]').val('');
            }
        }
        return false;
    });
    $('#submit-button').on('touchstart', function () {
        var error = false;
        var arr = store = new Array();
        $.each($('input[name=skuId]'), function (i, item) {
            if ($(item).val() == "") {
                arr.push(i);
                error = true;
            }
        });

        if (error == true) {
            jAlert('有 <strong>' + arr.length + '</strong> 件商品没有选择颜色和尺码！');
            return false;
        }

        $.each($('input[name=store]'), function (i, item) {
            if ($(item).val() < $('input[name=num]').val()) {
                store.push(i);
                error = true;
            }
        });

        if (error == true) {
            jAlert('有 <strong>' + arr.length + '</strong> 件商品库存不足以购买 ' + $('input[name=num]').val() + ' 套！');
            return false;
        }

        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', '$("#package-buy").submit()');
                userLogin();
            } else if (data.result.datas.isBind == false) {
                location.href = "/member/bind"
            } else {
                $('#package-buy').submit();
            }
        });
    });
</script>
<@m.page_footer />