<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='确认订单' title='确认订单信息' service='false' hastopfix='false'/>
<div class="section lazyload">
    <form id="confirm-form" class="validate-form" action="/order/create" method="post" call-back="bargainCreateSuccess">
        <input type="hidden" name="orderId" value="${order.id}"/>
        <input type="hidden" name="tempId" value="${order.tempId}"/>

        <div class="form" id="address-list">
            <#if addresses?size gt 0>
                <#list addresses as address>
                    <#if address.isdefault>
                        <div class="form-item item-address" data-id="${address.id}" style="padding-right:2em;">
                            <span class="icon icon-location"></span>
                            <p>${(address.name)!} &nbsp;&nbsp;&nbsp; ${address.mobile}</p>
                            <p>${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}${(address.street)!}</p>
                            <span class="icon icon-arrow-right"></span>
                            <input type="hidden" name="addressId" value="${address.id}"/>
                        </div>
                    <#elseif address_index==0>
                        <div class="form-item item-address" data-id="${address.id}" style="padding-right:2em;">
                            <#if address.id ==0>
                                <span class="icon icon-location"></span>
                                <p>${address.name} &nbsp;&nbsp;&nbsp; ${address.mobile}</p>
                                <p>${(address.province.name)!}${(address.city.name)!}${(address.street)!}</p>
                                <span class="icon icon-arrow-right"></span>
                                <input type="hidden" name="id" value=""/>
                                <input type="hidden" name="addressId" value="${address.id}"/>
                                <input type="hidden" name="name" value="${address.name}"/>
                                <input type="hidden" name="mobile" value="${address.mobile}"/>
                                <input type="hidden" name="province.name" value="${address.province.name}"/>
                                <input type="hidden" name="city.name" value="${address.city.name}"/>
                                <input type="hidden" name="district.name" value="${address.district.name}"/>
                                <input type="hidden" name="street" value="${address.street}"/>
                                <input type="hidden" name="origin" value="Weibo"/>
                            <#else>
                                <span class="icon icon-location"></span>
                                <p>${address.name} &nbsp;&nbsp;&nbsp; ${address.mobile}</p>
                                <p>${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}${(address.street)!}</p>
                                <span class="icon icon-arrow-right"></span>
                                <input type="hidden" name="addressId" value="${address.id}"/>
                            </#if>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
        <div class="form">
            <#assign exceptions=0 /> <#assign cod=1/>
            <#list orderItems?if_exists as orderItem>
                <#if orderItem?exists && orderItem.productSku.status gt 0 && orderItem.productSku.availableStore gt 0>
                    <div class="form-item item-card clearfix">
                        <input type="hidden" name="skuId" value="${orderItem.productSkuId}"/>
                        <input type="hidden" name="partnerId" value="${orderItem.partnerId}"/>
                        <input type="hidden" name="productCombId" value="${(orderItem.productCombId)!0}"/>
                        <input type="hidden" name="bargainId" value="${(bargainId)!0}"/>
                        <input type="hidden" name="partnerStyle" value="${orderItem.partnerStyle}"/>
                        <input type="hidden" name="cartItemId" value="${orderItem.cartItemId}"/>
                        <input type="hidden" name="orderPromotionId" value="${(orderItem.orderPromotionId)!}"/>
                        <input type="hidden" name="goodPromotionId" value="${(orderItem.goodPromotionId)!}"/>
                        <p class="img"><a href="/product/${orderItem.productId}"><img
                                        src="${static_base}/m/img/blank100x157.png"
                                        data-image="${picture_base}/${(orderItem.sp1?eval.img)!}!120"
                                        alt="${orderItem.productName}"/></a></p>
                        <p class="title"><a href="/product/${orderItem.productId}">${orderItem.productName}</a></p>
                        <p class="property">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}
                            &nbsp;&nbsp;&nbsp;${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value}<span>&nbsp;&nbsp;&nbsp;数量：
                	<#if orderItem.quantity gt orderItem.productSku.availableStore>
                        <#assign quantity = orderItem.productSku.availableStore />
                        ${orderItem.productSku.availableStore}
                        <input type="hidden" name="quantity" value="${orderItem.productSku.availableStore}"/>
                        <span class="check-tip"
                              style="left:20%;margin:2em;">&nbsp;&nbsp;&nbsp;目前库存只剩${orderItem.productSKU.availableStore}件</span>
                    <#else>
                        <#assign quantity = orderItem.quantity />
                        ${orderItem.quantity}
                        <input type="hidden" name="quantity" value="${orderItem.quantity}"/>
                    </#if></span></p>
                        <#if orderItem.promotionPrice gt 0>
                            <p class="property price">单价：<span
                                        class="red">&yen;${orderItem.productPrice-orderItem.promotionPrice}</span>
                                &nbsp;&nbsp;&nbsp; 原价：<span class="red"
                                                            style="text-decoration:line-through;">&yen;${orderItem.productPrice}</span>
                                &nbsp;&nbsp;&nbsp; 小计：<span
                                        class="red">&yen;${orderItem.totalPrice-orderItem.promotionAmount}</span>
                            </p>
                        <#else>
                            <p class="property price">单价：<span class="red">&yen;${orderItem.productPrice}</span>
                                <#if orderItem.productPrice lt orderItem.originalPrice>
                                    &nbsp;&nbsp;&nbsp;<span class="red"
                                                            style="text-decoration:line-through;">&yen;${orderItem.originalPrice}</span>
                                </#if>
                                &nbsp;&nbsp;&nbsp;小计：<span
                                        class="red">&yen;${orderItem.totalPrice-orderItem.promotionAmount}</span></p>
                        </#if>
                        <#if orderItem.after==0><p class="property"><span class="red">本商品不支持七天无理由退换货</span></p></#if>
                        <#if orderItem.promotionAmount gt 0><p class="amount"><span
                                    class="green">优惠${(orderItem.promotionAmount)!}元</span></p> </#if>
                    </div>
                <#else>
                    <#assign exceptions=exceptions+1 />
                </#if>
            </#list>
            <#if exceptions gt 0>
                <p class="form-title">以下 ${excptions} 种商品有异常，无法购买。</p>
                <#list orderItems?if_exists as orderItem>
                    <#if !orderItem.productSku?exists || orderItem.productSku.status lte 0 || orderItem.productSku.availableStore lte 0>
                        <div class="form-item item-card item-excption clearfix">
                            <p class="img"><img src="${picture_base}/${(orderItem.sp1?eval.img)!}!60"
                                                alt="${orderItem.productName}"/></p>
                            <p class="title">${orderItem.productName}</p>
                            <p class="property">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}
                                &nbsp;&nbsp;&nbsp;${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value}</p>
                            <p class="amount"><strong
                                        class="price"><#if !orderItem.productSku?exists || orderItem.productSku.status lte 0>该商品已下架
                                    <#elseif (orderItem.productSku.availableStore lte 0 && orderItem.productSku.freezeStore gt 0)>该商品已经卖完了。但还有客户未付款，还有机会购买到。
                                    <#elseif (orderItem.productSku.availableStore lte 0)>该商品已售完
                                    </#if></strong>
                            </p>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
        <div class="form">
            <div class="form-item"><input name="memo" value="" placeholder="留言备注" class="input"/></div>
        </div>
        <div class="form">
            <div class="form-item item-flex"><label>商品金额</label><label class="red" id="product-price"
                                                                       rel="${(order.totalAmount+order.promotionAmount)}">&yen; ${(order.totalAmount+order.promotionAmount)?string("currency")?substring(1)}
                    元</label></div>
            <div class="form-item item-flex"><label>运费</label><label class="red">&yen; <em id="ship-fee"
                                                                                           rel="${order.shippingRates}">${order.shippingRates?string("currency")?substring(1)}</em>
                    元</label></div>
            <div class="form-item item-flex"><label>满减优惠</label><label class="red">&yen; <em id="promotion-amount"
                                                                                             rel="${order.promotionAmount}">${(order.promotionAmount)?string("currency")?substring(1)}</em>
                    元</label></div>
        </div>
        <#assign cod=1/>
        <#list orderItems?if_exists as orderItem>
            <#if orderItem.cod==0>
                <#assign cod=0/>
            </#if>
        </#list>

        <div class="suspend-bar">
            <div class="desc" data-sn="${order.orderSn}">应付：<span class="price">&yen; <strong id="now-pay-price"
                                                                                              class="price"
                                                                                              rel="${order.totalAmount+order.shippingRates}">${order.totalAmount+order.shippingRates}</strong> 元</span>
            </div>
            <input type="hidden" name="cod" value="${cod}"/>
            <div class="buttons">
                <button type="submit" id="submit-button" class="button button-red">去付款</button>
            </div>
        </div>
    </form>
</div>







<script id="address-place-template" type="text/html">
    <p class="form-title">首次购物，请填写收货地址</p>
    <input type="hidden" name="addressId" value="0"/>
    <div class="form-item">
        <input type="text" name="name" placeholder="收货人姓名" data-rule="byte" class="input validate" title="收货人姓名"/>
    </div>
    <div class="form-item">
        <select name="regionPrefix" class="input province validate" title="省份" style="width:32%;">
            <option value="">选择省份</option>
        </select>
        <select name="regionMiddle" class="input city validate" title="城市" style="width:32%;">
            <option value="">选择城市</option>
        </select>
        <select name="regionSuffix" class="input district validate" title="区县" style="width:32%;">
            <option value="">选择区县</option>
        </select>
    </div>
    <div class="form-item">
        <input type="text" name="street" placeholder="路名或街道地址，门牌号" class="input validate" title="详细地址" min-length="4"/>
    </div>
    <div class="form-item item-flex">
        <input type="text" name="mobile" placeholder="收货人手机号" class="input validate" min-length="4" maxlength="20"
               title="收货人手机号" onkeyup="this.value=this.value.replace(/\D/g,'')"
               onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
    </div>
    <div class="form-item">
        &nbsp;<input type="checkbox" name="isdefault" value="true" id="isdefault"/><label for="isdefault">设为默认地址</label>
    </div>
    <script>
        $('input[name="mobile"]').on('change', function () {
            if (!$(this).utilValidateMobile()) {
                $(this).focus();
                $.flashTip({position: 'top', type: 'error', message: '请输入正确手机号码'});
                return false;
            }
        })
        $('input[name="street"]').on('blur', function () {
            if ($(this).val().length <= 4) {
                jAlert('详细地址必须大于4位哟');
            }

        })
</script>
    </script>
    <
    script
    id = "address-list-template"
    type = "text/html" >
        < div
    id = "modal-address-list" >
        < header
    class
    = "modal-header" >
        < div
    class
    = "header" >
        < div
    class
    = "header-back" > < a
    href = "javascript:closePopModal()"
    class
    = "icon icon-cross" > < /a></
    div >
    < div
    class
    = "header-title" > 选择地址 < /div>
        < div
    class
    = "header-btn" >
        < button
    type = "button"
    onclick = "modalAddressNew()"
    class
    = "button button-s button-blue"
    style = "font-size:0.8em;" > 新增 < /button>
        < /div>
        < /div>
        < /header>
        < section
    class
    = "modal-body" >
        < div
    class
    = "form" >
        {
    {
        each
        list as address
        i
    }
    }
    <
    div
    class
    = "form-item item-address select-address"
    data - id = "{{address.id}}" >
        < label
    style = "width:100%;" > < input
    type = "radio"
    name = "check-id"
    value = "{{i}}"
    {
        {
            if id == address.id}
    }
    checked
    {
        {
            /if}} / >
            < p > {
            {
                address.name
            }
        } &
            nbsp;
        &
            nbsp;
        &
            nbsp;
            {
                {
                    address.mobile
                }
            }
        <
            /p>
            < p > {
            {
                address.province
            }
        }
            {
                {
                    address.city
                }
            }
            {
                {
                    if id != 0}
            }
            {
                {
                    address.district
                }
            }
            {
                {
                    /if}}{{address.street}}</
                    p > < /label>
                    < /div>
                    {
                        {
                            /each}}
                            < /div>
                            < /section>
                            < /div>
                            < div
                            id = "modal-address-new"
                        class
                            = "display-none" >
                                < header
                        class
                            = "modal-header" >
                                < div
                        class
                            = "header" >
                                < div
                        class
                            = "header-back" > < a
                            href = "javascript:modalAddressList()"
                        class
                            = "icon icon-cross" > < /a></
                            div >
                            < div
                        class
                            = "header-title" > 新增地址 < /div>
                                < /div>
                                < /header>
                                < section
                        class
                            = "modal-body" >
                                < div
                        class
                            = "form" >
                                < form
                        class
                            = "validate-form"
                            action = "/address/insert"
                            call - back = 'modalAddressPostSuccess'
                            method = "post" >
                                < input
                            type = "hidden"
                            name = "id"
                            value = "" / >
                                < div
                        class
                            = "form-item" >
                                < input
                            type = "text"
                            name = "name"
                            placeholder = "收货人姓名"
                            data - rule = "byte"
                        class
                            = "input validate"
                            title = "收货人姓名" / >
                                < /div>
                                < div
                        class
                            = "form-item" >
                                < select
                            name = "regionPrefix"
                        class
                            = "input province validate"
                            title = "省份"
                            style = "width:32%;" >
                                < option
                            value = "" > 选择省份 < /option>
                                < /select>
                                < select
                            name = "regionMiddle"
                        class
                            = "input city validate"
                            title = "城市"
                            style = "width:32%;" >
                                < option
                            value = "" > 选择城市 < /option>
                                < /select>
                                < select
                            name = "regionSuffix"
                        class
                            = "input district validate"
                            title = "区县"
                            style = "width:32%;" >
                                < option
                            value = "" > 选择区县 < /option>
                                < /select>
                                < /div>
                                < div
                        class
                            = "form-item" >
                                < input
                            type = "text"
                            name = "street"
                            placeholder = "路名或街道地址，门牌号"
                        class
                            = "input validate"
                            title = "详细地址"
                            min - length = "4" / >
                                < /div>
                                < div
                        class
                            = "form-item item-flex" >
                                < input
                            type = "text"
                            name = "mobile"
                            placeholder = "收货人手机号"
                        class
                            = "input validate"
                            title = "收货人手机号"
                            min - length = "5"
                            max - length = "20"
                            data - rule = "num" / >
                                < input
                            type = "hidden"
                            name = "nationCode"
                            value = ""
                        class
                            = "mobile-code" / >
                                < /div>
                                < div
                        class
                            = "form-item" >
                                & nbsp;
                        <
                            input
                            type = "checkbox"
                            name = "isdefault"
                            value = "true"
                            id = "isdefault" / > < label
                            for= "isdefault" > 设为默认地址 < /label>
                                < /div>
                                < div class
                            = "form-button" >
                                < button
                            type = "submit"
                            name = "submit-button"
                        class
                            = "button button-l button-red" > 确定 < /button>
                                < /div>
                                < /form>
                                < /div>
                                < /section>
                                < /div>
                                < script >
                                $('input[name="mobile"]').on('change', function () {
                                    if (!$(this).utilValidateMobile()) {
                                        $(this).focus();
                                        $.flashTip({position: 'top', type: 'error', message: '请输入正确手机号码'});
                                        return false;
                                    }
                                })
    </script>
    </script>
    <
    script
    id = "address-select-template"
    type = "text/html" >
        < div
    class
    = "form-item item-address"
    data - id = "{{id}}"
    style = "padding-right:2em;" >
        < span
    class
    = "icon icon-location" > < /span>
        < p > {
    {
        name
    }
    } &
    nbsp;
    &
    nbsp;
    &
    nbsp;
    {
        {
            mobile
        }
    }
    <
    /p>
    < p > {
    {
        province
    }
    }
    {
        {
            city
        }
    }
    {
        {
            district
        }
    }
    {
        {
            street
        }
    }
    <
    /p>
    < span
    class
    = "icon icon-arrow-right" > < /span>
        < input
    type = "hidden"
    name = "addressId"
    value = "{{id}}" / >
        < /div>
</script>

<script>
    <#if addresses?size gt 0>
    var address_data = [<#list addresses as address>{
        'id': '${address.id}',
        'name': '${address.name}',
        'mobile': '${address.mobile}',
        'province': '${address.province.name}',
        'city': '${address.city.name}',
        'district': '${address.district.name}',
        'street': '${address.street}',
        'isdefault': '${address.isdefault}'
    }<#if address_index lt (addresses?size-1)>, </#if></#list>];
    <#else>
    var html = template('address-place-template', {});
    $('#address-list').append(html);
    $('#address-list').utilSetArea();
    </#if>
    var card_data = [<#list cashCards as card>{
        'id': '${card.id}',
        'code': '${card.code}',
        'available': '${card.available}',
        'amount': '${card.amount}',
        'expireDate': '${card.expireDate?string("yyyy年MM月dd日")}'
    }<#if card_index lt (cashCards?size-1)>, </#if></#list>];

    //小能客服

    if ($('input[name=skuId]').size() <= 0) {
        $('#submit-button').attr('disabled', 'disabled');
    }

    $(document).on(click_type, '#address-list>.item-address', function () {
        location.hash = '#address';
        var data = {};
        data.list = address_data;
        data.id = $(this).attr('data-id');
        var html = template('address-list-template', data);
        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
        return false;
    });

    $(document).on(click_type, '#invoices-change', function () {
        var data = {};
        var html = template('invoices-template', data);
        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
        return false;
    });

    $(document).on(click_type, '#invoices-button', function () {
        var invoices = $(this).parent().find('.mydrawee').val();
        if (invoices == '' || invoices.length > 50) {
            $.flashTip({position: 'center', type: 'error', message: '抬头在1-50字之间'});
        } else {
            $('.invoices-text').html(invoices);
            $("input[name='drawee']").val(invoices);
            setTimeout(function () {
                closePopModal();
            }, 200);
        }
        return false;
    });

    $(document).on(click_type, '.select-address', function () {
        var i = $(this).find('input').val();
        var address = address_data[i];
        var html = template('address-select-template', address);
        $('#address-list').html(html);
        setTimeout(function () {
            closePopModal();
            history.back(1);
        }, 200);
        return false;
    });


    $(document).on(click_type, '#coupon-change a', function () {
        var type = $(this).attr('data-type');
        $(this).addClass('selected');
        $(this).siblings().removeClass('selected');
        $('#coupons-' + type).show();
        $('#coupons-' + type).siblings().hide();
        return false;
    });


    $('#coupon-item').on(click_type, function () {
        location.hash = '#coupon';
        var html = template('coupon-list-template', {'selected': $('#coupons').val()});
        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
        return false;
    });

    $('#d2c-card-item').on(click_type, function () {
        location.hash = '#card';
        var data = {};
        data.list = card_data;
        data.selected = $('#cash-card-id').val();
        var html = template('d2c-card-list-template', data);
        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
        return false;
    });

    $(document).on('change', '#coupons-enable input[type=radio]', function () {
        var i = $(this).val();
        var p = $(this).attr('rel');
        var type = $(this).attr('data-type');
        $('#coupons').val(i);
        $('#coupons').attr('rel', p);
        $('#coupons').attr('data-type', type);
        setTimeout(function () {
            calculateTotal();
            closePopModal();
            history.back(1);
        }, 200);
        return false;
    });


    $(document).on(click_type, '#d2c-card-list input[type=radio]', function () {
        var i = $(this).val();
        var p = $(this).attr('rel');
        $('#cash-card-id').val(i);
        $('#cash-card-id').attr('rel', p);
        setTimeout(function () {
            calculateTotal();
            closePopModal();
            history.back(1);
        }, 200);
        return false;
    });

    var bargainCreateSuccess = function () {
        var data = $('body').data('return_data');
        var orderSn = data.result.datas.order.orderSn;
        var params = data.result.datas.params;
        location.href = '/payment/prepare/order/' + orderSn + '?' + params;

    }
    var modalCardNew = function () {
        $('#modal-d2c-card-new').show();
        $('#modal-d2c-card-new').utilSetArea();
        $('#modal-d2c-card-list').hide();
    }
    var modalCardList = function () {
        $('#modal-d2c-card-list').show();
        $('#modal-d2c-card-new').hide();
    }

    var modalAddressNew = function () {
        $('#modal-address-list').addClass('display-none');
        $('#modal-address-new').removeClass('display-none');
        $('#modal-address-new').utilSetArea();
        return false;
    }
    var modalAddressList = function () {
        $('#modal-address-list').removeClass('display-none');
        $('#modal-address-new').addClass('display-none');
    }
    var modalAddressPostSuccess = function () {
        var data = $('body').data('return_data');
        var address = data.result.datas.address;
        address.province = data.result.datas.address.province.name;
        address.city = data.result.datas.address.city.name;
        address.district = data.result.datas.address.district.name;
        address_data.push(address);
        var html = template('address-select-template', address);
        $('#address-list').html(html);
        setTimeout(function () {
            closePopModal();
            history.back(1);
        }, 200);
    }

</script>


<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'orderConfirm',
        productId_list: '<#if orderItems><#list orderItems?if_exists as orderItem>${orderItem.productId},</#list></#if>',
        totalPrice: '<#if orderItems> ${(order.totalAmount+order.shippingRates)}</#if>',
        totalNum: '${orderItems?size}'
    };
</script>
<@m.page_footer />