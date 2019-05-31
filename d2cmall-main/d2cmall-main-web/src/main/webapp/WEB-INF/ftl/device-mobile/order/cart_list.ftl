<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='购物车' service='false' hastopfix='false'/>
<@m.page_nav_bar channel='cart' />
<#if browser!='wechat' && !wechat >
    <header>
        <div class="header fixed">
            <div class="header-title">购物车</div>
        </div>
    </header>
</#if>
<style>
    .qr-box {
        width: 20em;
        height: 23.75em;
        background: #FFF;
        text-align: center;
    }

    .box-title {
        padding: 2.5em 0 .625em;
        color: #262626;
        font-weight: bold;
        line-height: 1.5;
    }

    .box-cont-tip {
        padding: 10px 0;
        line-height: 1.5;
        color: #7F7F7F;
        font-size: 12px;
    }

    .box-button {
        border: 0;
        width: 235px;
        height: 40px;
        line-height: 40px;
        background: #FD555D;
        color: #FFF;
    }

    .qr-close {
        width: 1.875em;
        height: 1.875em;
        position: absolute;
        bottom: -40px;
        left: 50%;
        transform: translateX(-50%);
    }
</style>
<div class="section lazyload">
    <#if (cart==null || cart.typeCount lt 1)!>
        <div class="cart-empty">您的购物车没有商品哦~</div>
    <#else>
        <div class="form">
            <form name="form-cart" id="form-cart" action="/order/confirm" method="POST" style="width:100%">
                <#list cart?if_exists.itemMapById?if_exists?values as cartItem>
                    <#if cartItem.productSku?exists>
                        <#if cartItem.productTradeType == 'CROSS'>
                            <div class="form-item item-card item-checkbox item-margin clearfix cart-disable">
                                <input type="checkbox" class="cartItem cartchecked cross-check"/>
                                <p class="promotion-name"><span class="one red"></span><span
                                            class="three red"></span><span class="two red"></span></p>
                                <p class="img"><a href="/product/${cartItem.productId}"><img
                                                src="${static_base}/m/img/blank100x157.png"
                                                data-image="<#if cartItem.sp1?eval.img?index_of('http')!=-1>${(cartItem.sp1?eval.img)}<#else>${picture_base}/${(cartItem.sp1?eval.img)}</#if>"
                                                alt="${cartItem.productName}"/></a></p>
                                <p class="title"><a href="/product/${cartItem.productId}">${cartItem.productName}</a>
                                </p>
                                <#if cartItem.choiceOrderPromotions?exists && cartItem.choiceOrderPromotions?size gt 1>
                                    <div class="cart-select custom-select">
                                        <select name="selectOrderPromotionId" class="input-s" style="width:50%">
                                            <#list cartItem.choiceOrderPromotions as orderPromotion>
                                                <option value="${orderPromotion.id}"
                                                        <#if cartItem.orderPromotionId==orderPromotion.id>selected="selected"</#if>>${orderPromotion.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </#if>
                                <p class="property">${cartItem.sp1?eval.name}
                                    ：${cartItem.sp1?eval.value} <#if cartItem.sp2 != null>&nbsp;${cartItem.sp2?eval.name}：${cartItem.sp2?eval.value}</#if></p>
                                <p class="amount"><span>吊牌价：<strong class="price original-l"
                                                                    data-price="${cartItem.originalPrice}">&yen;${cartItem.originalPrice}</strong></span>&nbsp;&nbsp;<span><strong
                                                class="price cut-price"></strong></span></p>
                                <#if cartItem.after==0><p class="amount"><span class="red">本件不支持七天无理由退换货</span>
                                </p></#if>
                            </div>
                        <#else>
                            <div class="form-item item-card item-checkbox item-margin clearfix <#if (cartItem.productSku.availableStore gt 0) && (!cartItem.over)><#else>cart-disable</#if>"
                                 data-id="${cartItem.id}" data-cartType="available">
                                <input type="hidden" name="cartItemId" value="${cartItem.id}"
                                       class="id" <#if (cartItem.productSku.availableStore gt 0) && (!cartItem.over)> <#else> disabled</#if> />
                                <input type="hidden" name="skuId" value="${cartItem.productSkuId}"/>
                                <input type="hidden" name="orderPromotionId" value="${(cartItem.orderPromotionId)!}"/>
                                <input type="hidden" name="goodPromotionId" value="${(cartItem.goodPromotionId)!}"/>
                                <input type="checkbox"
                                       class="cartItem cartchecked"<#if (cartItem.productSku.availableStore gt 0) > checked<#else> disabled</#if>/>
                                <p class="promotion-name"><span class="one red"></span><span
                                            class="three red"></span><span class="two red"></span></p>
                                <p class="img"><a href="/product/${cartItem.productId}"><img
                                                src="${static_base}/m/img/blank100x157.png"
                                                data-image="${picture_base}/${(cartItem.sp1?eval.img)!}!120"
                                                alt="${cartItem.productName}"/></a><#if (cartItem.productSku.availableStore gt 0) && (!cartItem.over)><#else>
                                    <span style="position:absolute;width:100%;height:100%;background-color:#fff;filter:alpha(opacity=50);-moz-opacity:0.5;opacity:0.5;top:0;left:0;"></span></#if>
                                </p>
                                <p class="title"><a
                                            href="/product/${cartItem.productId}"><#if cartItem.flashPromotion?exists && !cartItem.productPromotion.flashPromotionOver>
                                            <span style="color:#FD555D">【限时购】</span></#if>${cartItem.productName}</a>
                                </p>
                                <#if cartItem.choiceOrderPromotions?exists && cartItem.choiceOrderPromotions?size gt 1>
                                    <div class="cart-select custom-select">
                                        <select name="selectOrderPromotionId" class="input-s" style="width:50%">
                                            <#list cartItem.choiceOrderPromotions as orderPromotion>
                                                <option value="${orderPromotion.id}"
                                                        <#if cartItem.orderPromotionId==orderPromotion.id>selected="selected"</#if>>${orderPromotion.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </#if>
                                <p class="property">${cartItem.sp1?eval.name}
                                    ：${cartItem.sp1?eval.value} <#if cartItem.sp2 != null>&nbsp;${cartItem.sp2?eval.name}：${cartItem.sp2?eval.value}</#if></p>
                                <p class="amount"><span>吊牌价：<strong class="price original-l"
                                                                    data-price="${cartItem.originalPrice}">&yen;${cartItem.originalPrice}</strong></span>&nbsp;&nbsp;<span><strong
                                                class="price cut-price"></strong></span></p>
                                <#if cartItem.after==0><p class="amount"><span class="red">本件不支持七天无理由退换货</span>
                                </p></#if>
                                <div class="bar clearfix">
                                    <div class="float-right promotion-td"
                                         style="line-height:1em;font-size:1em;margin-top:.5em">共计：<strong class="price"
                                                                                                          style="font-size:1.2em"><em
                                                    class="total-item red"></em></strong></div>
                                    <div class="float-left" style="width:30%;"><a href="javascript:"
                                                                                  data-url="/cart/delete/${cartItem.id}"
                                                                                  confirm="确定要从购物车中移出该商品吗？"
                                                                                  success-tip="商品从购物车中成功移出！"
                                                                                  call-back="removeItem(${cartItem.id})"
                                                                                  class="icon icon-trash ajax-request"></a>
                                    </div>
                                    <div class="float-left">
                                        <#if cartItem.over>
                                            <input type="hidden" name="quantity" value="0" size="2" class="quantity"
                                                   disabled/><p
                                                style="margin-top:0.4em;font-size:1em;color:#FD555D!important">
                                            该件商品已经下架</p>
                                        <#elseif (cartItem.productSku.availableStore lte 0 && cartItem.productSku.freezeStore gt 0)>
                                            <input type="hidden" name="quantity"
                                                   value="${cartItem.productSku.availableStore}" size="2" maxlength="3"
                                                   class="quantity" disabled/><p
                                                style="margin-top:0.5em;font-size:0.8em;color:#FD555D!important">
                                            还有机会抢到</p>
                                        <#elseif (cartItem.productSku.availableStore lte 0)>
                                            <input type="hidden" name="quantity" value="0" size="2" class="quantity"
                                                   disabled/><p
                                                style="margin-top:0.4em;font-size:1em;color:#FD555D!important">
                                            该件商品已经卖完了</p>
                                        <#elseif (!cartItem.isMeetInventory(cartItem.productSku.availableStore))>
                                            <div class="num-crease-wrap"><span class="num-decrease">－</span><span
                                                        class="num-input">${cartItem.productSku.availableStore}</span><span
                                                        class="num-increase">＋</span><input type="hidden"
                                                                                            name="quantity"
                                                                                            value="${cartItem.productSku.availableStore}"
                                                                                            size="2" readonly
                                                                                            maxlength="3"></div>
                                            <div class="check-tip" style="margin-left:-1.3em;">
                                                目前库存只剩${cartItem.productSku.availableStore}件
                                            </div>
                                        <#else>
                                            <div class="num-crease-wrap"><span class="num-decrease">－</span><span
                                                        class="num-input">${cartItem.quantity}</span><span
                                                        class="num-increase">＋</span><input type="hidden"
                                                                                            name="quantity"
                                                                                            value="${cartItem.quantity}"
                                                                                            size="2" readonly
                                                                                            maxlength="3"></div>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </#if>
                    </#if>
                </#list>
                <!--<a href="javascript:" class="clear-cart float-left" style="margin-top:0.5em;"><i class="icon icon-trash"></i> 清空购物车</a>-->
                <div class="suspend-bar" style="border-bottom:1px solid #F0F0F0">
                    <div class="checkbox">
                        <input type="checkbox" checked id="check-all"/>
                    </div>
                    <div class="desc">总计：<strong class="price">&yen;<span
                                    class="totle-price">${(cart.totalPrice)!}</span>元</strong></div>
                    <div class="buttons">
                        <button type="button" id="submit-button" class="button button-red">结算(<span
                                    id="total-amount"></span>)
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </#if>
</div>
<div class="product-recomment" style="margin:0 1%">

</div>
<div style="height:3em;"></div>

<script id="recomment-template" type="text/html">
    <div class="maylike" style="position:relative;height:2.5625rem;margin:0 3%">
        <span style="position:absolute;padding:0 18px;rgb(200,200,200);color:rgb(200,200,200);font-size:12px;left:33.5%;top:35%;z-index:2;background:#F2F2F2;">其他小伙伴在看</span>
    </div>
    <div class="list clearfix">
        {{each list as value i}}
        <a href="/product/{{value.id}}" class="item item-flex item-gap">
            <span class="img"><img src="${picture_base}{{value.img}}!300" alt="{{value.name}}"/>  {{if value.store < 1}}<i
                        class="n-product"></i><span class="outp">已售罄</span>{{/if}}</span>
            <span class="title">{{value.name}}</span>
            <span class="price"><strong>&yen; {{value.minPrice}}</strong>{{if value.originalPrice && value.originalPrice > value.minPrice}}&nbsp;&nbsp;<s>&yen;{{value.originalPrice}}</s>{{/if}}</span>
        </a>
        {{/each}}
    </div>
</script>

<script>

    //console.log(parseFloat(200.01*100-200*100)/100);解决parseFloat精度的bug，先所有数字乘以100，再除100

    //跨境商品弹窗
    $(document).on('click', '.cross-check', function () {
        var obj = $(this);
        popupHtml();
        obj.removeAttr('checked');
    });

    $.getJSON("/product/recommend", function (res) {
        if (res.status == 1) {
            var html = template("recomment-template", res.data.recommends);
            $('.product-recomment').append(html)
        }
    })
    //购物车计算
    var getcart = function () {
        if ($('.cartItem:checked').size() > 0) {
            $.ajax({
                'url': '/cart/calculate',
                'type': 'post',
                'data': $('#form-cart').serialize(),
                datatype: 'json',
                'async': false,
                'success': function (data) {
                    var obj = $(data),
                        amonut = obj.find('.totalAmount').text();
                    productTotalQuantity = obj.find('.productTotalQuantity').text();
                    obj.children('.item-detail').each(function (i, d) {
                        var id = $(d).attr('data-id'),
                            total = $(d).find('.totalProductAmount').text(),
                            goodPromotion = $(d).find('.goodPromotion').text(),
                            orderPromotion = $(d).find('.orderPromotion').text(),
                            productpromotionPrice = $(d).find('.productPrice').val(),
                            item = $(".item-card[data-id=" + id + "]");
                        item.find('.promotion-td .total-item').html(total);
                        if (goodPromotion != "") {
                            item.find('.promotion-name .one').html("【" + goodPromotion + "】&nbsp;&nbsp;&nbsp");
                        } else {
                            item.find('.promotion-name .one').html("");
                        }
                        if (orderPromotion != "") {
                            item.find('.promotion-name .two').html("【" + orderPromotion + "】&nbsp;&nbsp;&nbsp");
                        } else {
                            item.find('.promotion-name .two').html("");
                        }
                        if (productpromotionPrice != 0 && parseInt(productpromotionPrice) != parseInt(item.find('.original-l').attr('data-price'))) {
                            item.find('.cut-price').html('&yen;' + $(d).find('.productpromotionPrice').val());
                            item.find('.original-l').css('text-decoration', 'line-through');
                        }

                    })
                    $('#total-amount').text(productTotalQuantity);
                    $('.totle-price').text(amonut);
                }
            });
        } else {
            $('.totle-price').text('0');
            $('#total-amount').text('0');
        }
    }
    var countTotal = function () {
        var count = $('.id:not(:disabled)').size();
        //$('#total-amount').text(count);
        if (count > 0) {
            $('#submit-button').removeAttr('disabled');
        } else {
            $('#submit-button').attr('disabled', true);
        }
    }
    var countTotalPrice = function () {
        var count = 0;
        $.each($('.id:not(:disabled)'), function (i, obj) {
            count += parseFloat($(obj).parent().find('.total-item').text().replace(',', '') * 100);
        });
        count = count / 100;
        return count;
    }

    var removeItem = function (id) {
        var remove_timer;
        clearTimeout(remove_timer);
        var obj = $('.item-card[data-id=' + id + ']');
        obj.addClass('animated flipOutX');
        remove_timer = setTimeout(function () {
            obj.remove();
            countTotal();
            getcart();
            if ($('.form-item').size() == 0) {
                $('.section').html('<div class="cart-empty">当前购物车是空的</div>');
            }
        }, 500);

    }
    getcart();
    countTotal();
    if ($('.id:disabled').size() > 0) {
        $('.id:disabled').parent().find('input').attr('disabled', 'disabled');
        $('.id:disabled').parent().find('.total-item').text('0')
    }


    $('.num-decrease,.num-increase').on('touchstart', function () {
        var quantity_input = $(this).siblings('input');
        var num_span = $(this).siblings('.num-input')
        var obj = $(this).parents('.form-item');
        var quantity = parseInt(quantity_input.val());

        if ($(this).attr('class') == 'num-decrease') {
            quantity = quantity - 1;
            if (quantity <= 0) {
                quantity = 1;
            }
        }
        if ($(this).attr('class') == 'num-increase') {
            quantity = quantity + 1;
            if (quantity >= 999) {
                quantity = 999;
            }
        }

        var id = obj.find('input[name=skuId]').val();
        if (isNaN(quantity) || quantity < 1) {
            quantity = 1;
        }
        $.ajax({
            'url': '/cart/cartItem/' + id,
            'type': 'post',
            'data': {'quantity': quantity, 'skuId': id},
            'dataType': 'json',
            'async': false,
            'success': function (data) {
                if (data.result.status == 1) {
                    quantity_input.val(quantity);
                    num_span.text(quantity);
                    getcart();
                } else {
                    if (quantity <= 1) {
                        $.flashTip({position: 'center', type: 'error', message: '该件商品已经卖完了'});
                        obj.find('input[type=checkbox]').removeAttr('checked');
                        obj.find('input').attr('disabled', true);
                    } else {
                        $.flashTip({position: 'center', type: 'error', message: '最多只可购买' + (quantity - 1) + '件'});
                    }
                }
            }
        });
    });

    $('.cartItem:not(:disabled)').click(function () {
        var i = $('.cartItem').size();
        if ($(this).attr('checked') == 'checked') {
            $(this).parent().find('input[type!=checkbox]').attr('disabled', true);
            $(this).removeAttr('checked');
            if ($('.cartItem:checked').size() != i) {
                $('#check-all').removeAttr('checked');
                $('#check-all').prop('checked', false);
            }
        } else {
            $(this).parent().find('input[type!=checkbox]').removeAttr('disabled');
            $(this).attr('checked', 'checked');
            if ($('.cartItem:checked').size() == i) {
                $('#check-all').attr('checked', 'checked');
                $('#check-all').prop('checked', true);
            }
        }
        countTotal();
        getcart();
    });
    $('#check-all').click(function () {
        var obj = $('[data-cartType="available"] .cartItem:not(:disabled)');
        if ($(this).attr('checked') == 'checked') {
            $.each(obj, function (i, d) {
                $(d).parent().find('input[type!=checkbox]').attr('disabled', true);
                $(d).removeAttr('checked');
                $(d).prop('checked', false);
            });
            getcart();
            countTotal();
            $(this).attr('checked', false);
        } else {
            $(this).attr('checked', 'checked');
            $.each(obj, function (i, d) {
                if ($(d).attr('checked') != 'checked') {
                    $(d).attr('checked', 'checked');
                    $(d).prop('checked', true);
                    $(d).parent().find('input[type!=checkbox]').removeAttr('disabled');
                }
            });
            getcart();
            countTotal();
        }
    });

    $('select[name="selectOrderPromotionId"]').change(function () {
        var orderPromotionId = $(this).val();
        $(this).parent().parent().find('input[name="orderPromotionId"]').val(orderPromotionId);
        getcart();
    })


    $('#form-cart').submit(function () {
        if ($('.id:not(:disabled)').size() == 0) {
            jAlert('请在购物车里勾选你要购买的商品');
            return false;
        }
        //$('#submit-button').attr('disabled','disabled');
    });

    $('#submit-button').click(function () {
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', 'location.reload();');
                userLogin();
                location.href = '#login';
            } else if (data.result.datas.isBind == false) {
                location.href = "/member/bind"
            } else {
                $('#form-cart').submit();
            }
        });
        return false;
    });
    /*
    $('.clear-cart').on('touchstart',function(){
        jConfirm('确定要清空购物车吗？',function(r){
            if (r){
                $.ajax({
                    'url':'/cart/remove',
                    'type':'post',
                    'dataType':'json',
                    'success':function(data , textStatus){
                        $('.wrap-main').html('<div class="cart-empty">当前购物车是空的</div>');
                        $('#cart-num').text(0);
                    }
                });
            }
        });
        return false;
    });
    */

    !function (w, d, e) {
        var _money = countTotalPrice();
        var _productList = '<#list cart?if_exists.itemMapById?if_exists?values as cartItem>${cartItem.productId},${cartItem.quantity};</#list>';
        var b = location.href, c = d.referrer, f, s, g = d.cookie, h = g.match(/(^|;)\s*ipycookie=([^;]*)/),
            i = g.match(/(^|;)\s*ipysession=([^;]*)/);
        if (w.parent != w) {
            f = b;
            b = c;
            c = f;
        }
        ;u = '//stats.ipinyou.com/cvt?a=' + e('9U.tB.xSXrzPTx4cBWjYdyVMd1wX') + '&c=' + e(h ? h[2] : '') + '&s=' + e(i ? i[2].match(/jump\%3D(\d+)/)[1] : '') + '&u=' + e(b) + '&r=' + e(c) + '&rd=' + (new Date()).getTime() + '&Money=' + e(_money) + '&ProductList=' + e(_productList) + '&e=';

        function _() {
            if (!d.body) {
                setTimeout(_(), 100);
            } else {
                s = d.createElement('script');
                s.src = u;
                d.body.insertBefore(s, d.body.firstChild);
            }
        }

        _();
    }(window, document, encodeURIComponent);

</script>
<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'cart',
        productId_list: ' <#list cart?if_exists.itemMapById?if_exists?values as cartItem>${cartItem.productId},</#list>',
        totalPrice: '',
        totalNum: ''
    };
</script>
<@m.page_footer />