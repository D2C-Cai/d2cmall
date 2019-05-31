<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='确认拍卖订单' title='确认拍卖信息' service='false' hastopfix='false' css="auction" />
<div class="auction-order">
    <form id="confirm-form" class="validate-form" action="/auction/margin/update/${auctionMargin.id}" method="post"
          call-back="auctionCreateSuccess">
        <div class="auction-address-container">
            <#if addresses?size gt 0>
            <#list addresses as address>
            <#if address.isdefault>
            <div class="auction-order-item" data-id="${address.id}" id="auction-order-list">
                <p style="color:rgba(0,0,0,.85);font-size:18px;font-weight:bold;">${(address.name)!}<span
                            style="margin-left:.625rem;">${address.mobile}</span></p>
                <div style="margin-top:.625rem;font-size:12px;color:rgba(0,0,0,.5);">
                    <span class="icon-location"></span>
                    <span>${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}${(address.street)!}</span>
                </div>
                <span class="icon-right"></span>
                <input type="hidden" name="addressId" value="${address.id}"/>
            </div>
            <#elseif address_index==0>
            <div class="auction-order-item" data-id="${address.id} id=" auction-order-list
            ">
            <p style="color:rgba(0,0,0,.85);font-size:18px;font-weight:bold;">${(address.name)!}<span
                        style="margin-left:.625rem;">${address.mobile}</span></p>
            <div style="margin-top:.625rem;font-size:12px;color:rgba(0,0,0,.5);">
                <span class="icon-location"></span>
                <span>${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}${(address.street)!}</span>
            </div>
            <span class="icon-right"></span>
            <input type="hidden" name="addressId" value="${address.id}"/>
        </div>
        </#if>
        </#list>
        </#if>
</div>
<div class="auction-order-item auction-flex" style="margin-top:.625rem;">
    <div><img src="${picture_base}/${(product.product.productImageCover)!}!80" alt="" style="width:70px;height:105px;"/>
    </div>
    <div style="margin-left:.625rem;">
        <div style="margin-bottom:2.5rem;color:rgba(0,0,0,.85);font-size:14px;font-weight:bold;">${product.title}</div>
        <div style="font-size:12px;color:rgba(0,0,0,.85);">出价：<span style="color:#F21A1A;font-weight:bold;">&yen;<span
                        style="font-size:18px;">${product.currentPrice?string("currency")?substring(1)}</span></span>
        </div>
    </div>
</div>
<div class="auction-order-item" style="margin-top:.625rem;">
    <div class="clearfix" style="line-height:3.125rem;border-bottom:1px solid #F6F6F6;">
        <span style="float:left;color:rgba(0,0,0,.5);font-size:12px;">商品保证金</span>
        <span style="float:right;color:#F21A1A;font-size:14px;">- &yen;${product.margin?string("currency")?substring(1)}</span>
    </div>
    <div class="clearfix" style="line-height:3.125rem;clear:both;">
        <span style="float:left;color:rgba(0,0,0,.5);font-size:12px;">运费</span>
        <span style="float:right;color:#F21A1A;font-size:14px;">+ &yen;0.00</span>
    </div>
</div>
<div class="auction-fix">
    <div class="desc" data-sn="${auctionMargin.marginSn}"
         style="float:left;width:67%;text-align:left;line-height:3rem;padding-left:.625rem;box-sizing:border-box;color:#F21A1A;font-weight:bold;font-size:12px;">
        &yen;<span style="font-size:24px;">${(auctionMargin.billTotalFee?string("currency")?replace('￥',''))}</span>
    </div>
    <button type="submit" class="auction-pay" style="width:33%;">立即支付</button>
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
= "button button-s button-blue" > 新增 < /button>
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
                address.district
            }
        }
        {
            {
                address.street
            }
        }
    <
        /p></
        label >
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
                = "icon icon-back" > < /a></
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
                    < label
            class
                = "choose-country-code"
                id = "choose-country"
                style = "font-size:16px;" > +86 < /label>
                    < input
                type = "text"
                name = "mobile"
                placeholder = "收货人手机号"
            class
                = "input validate"
                title = "收货人手机号"
                min - length = "4"
                maxlength = "20"
                onkeyup = "this.value=this.value.replace(/\D/g,'')"
                onafterpaste = "this.value=this.value.replace(/\D/g,'')" / >
                    < input
                type = "hidden"
                name = "nationCode"
                value = "86"
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
= "auction-order-item"
data - id = "{{id}}"
id = "auction-order-list" >
    < p
style = "color:rgba(0,0,0,.85);font-size:18px;font-weight:bold;" > {
{
    name
}
}<
span
style = "margin-left:.625rem;" > {
{
    mobile
}
}<
/span></
p >
< div
style = "margin-top:.625rem;font-size:12px;color:rgba(0,0,0,.5);" >
    < span
class
= "icon-location" > < /span>
    < span > {
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
/span>
< /div>
< span
class
= "icon-right" > < /span>
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
    $('#auction-order-list').append(html);
    $('#auction-order-list').utilSetArea();
    </#if>

    $(document).on(click_type, '.auction-order-item:eq(0)', function () {
        location.hash = '#address';
        var data = {};
        data.list = address_data;
        data.id = $(this).attr('data-id');
        var html = template('address-list-template', data);
        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
        return false;
    });

    $(document).on(click_type, '.select-address', function () {
        var i = $(this).find('input').val();
        var address = address_data[i];
        var html = template('address-select-template', address);
        $('.auction-address-container').html(html);
        setTimeout(function () {
            closePopModal();
            history.back(1);
        }, 200);
        return false;
    });


    var auctionCreateSuccess = function () {
        var data = $('body').data('return_data');
        var auctionSn = $('.desc').attr('data-sn');
        var params = data.result.datas.params;
        setTimeout(function () {
            location.href = '/payment/prepare/margin/' + auctionSn + '?' + params;
        }, 500);
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
        $('#auction-order-list').html(html);
        setTimeout(function () {
            closePopModal();
            history.back(1);
        }, 200);
    }

</script>
