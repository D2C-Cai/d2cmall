<#import "templates/public_mobile.ftl" as m>
<div id="modal-address-list">
    <header>
        <div class="header">
            <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
            <div class="header-title">选择地址</div>
            <div class="header-btn">
                <button type="button" onclick="modalAddressNew()" class="button button-s button-blue"
                        id="add-new-address-button">新增
                </button>
            </div>
        </div>
    </header>
    <div class="section lazyload">
        <form name="award-form" class="validate-form" action="/award/create" call-back="createSuccess()" method="post">
            <input type="hidden" name="awardId" value="${awardId}"/>
            <input type="hidden" name="quantity" value="${quantity}"/>
            <input type="hidden" name="skuId" value="${skuId}"/>
            <div class="form" id="address-form">
                <#if addresses?size gt 0>
                    <#list addresses as address>
                        <div class="form-item item-address select-address" data-id="${address.id}">
                            <label style="width:100%;"><input type="radio" name="addressId"
                                                              value="${address.id}"<#if address.isdefault> checked</#if> />
                                <p>${address.name} &nbsp;&nbsp;&nbsp; ${address.mobile}</p>
                                <p>${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}${(address.street)!}</p>
                            </label>
                        </div>
                    </#list>
                </#if>
                <div class="form-button">
                    <button type="submit" name="submit-button" class="button button-l button-red">确定</button>
                </div>
            </div>
        </form>
    </div>
</div>
<div id="modal-address-new" class="display-none">
    <header>
        <div class="header">
            <div class="header-back"><a href="javascript:modalAddressList()" class="icon icon-back"></a></div>
            <div class="header-title">新增地址</div>
        </div>
    </header>
    <div class="section lazyload">
        <form name="award-form" class="validate-form" action="/award/create" target="_self" method="post">
            <input type="hidden" name="awardId" value="${awardId}"/>
            <input type="hidden" name="quantity" value="${quantity}"/>
            <input type="hidden" name="skuId" value="${skuId}"/>
            <div class="form" id="address-new-form">
            </div>
        </form>
    </div>
</div>
<script id="address-place-template" type="text/html">
    <p class="form-title">请填写收货地址</p>
    <div class="form-item">
        <input type="text" name="name" placeholder="收货人姓名" data-rule="byte" class="input validate" title="收货人姓名"/>
    </div>
    <div class="form-item">
        <select name="province.code" class="input province validate" title="省份" style="width:32%;">
            <option value="">选择省份</option>
        </select>
        <select name="city.code" class="input city validate" title="城市" style="width:32%;">
            <option value="">选择城市</option>
        </select>
        <select name="district.code" class="input district validate" title="区县" style="width:32%;">
            <option value="">选择区县</option>
        </select>
    </div>
    <div class="form-item">
        <input type="text" name="street" placeholder="路名或街道地址，门牌号" class="input validate" title="详细地址" min-length="4"/>
    </div>
    <div class="form-item">
        <input type="text" name="mobile" placeholder="收货人手机号" class="input validate" title="收货人手机号" min-length="4"
               maxlength="20" onkeyup="this.value=this.value.replace(/\D/g,'')"
               onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
    </div>
    <div class="form-item">
        &nbsp;<input type="checkbox" name="isdefault" value="true" id="isdefault"/><label for="isdefault">设为默认地址</label>
    </div>
    <div class="form-button">
        <button type="submit" name="submit-button" class="button button-l button-red">确定</button>
    </div>
</script>
<script>
    $('input[name="mobile"]').on('change', function () {
        if (!$(this).utilValidateMobile()) {
            $(this).focus();
            $.flashTip({position: 'top', type: 'error', message: '请输入正确手机号码'});
            return false;
        }
    })
</script>
<script>
    <#if addresses?size == 0>
    var html = template('address-place-template', {});
    $('#add-new-address-button').hide();
    $('#address-form').html(html);
    $('#address-form').utilSetArea();
    <#else>
    if ($('.item-address input[type=radio]:checked').size() == 0) {
        $('.item-address:first').find('input[type=radio]').attr('checked', true);
    }
    </#if>


    var modalAddressNew = function () {
        $('#modal-address-new').show();
        $('#modal-address-list').hide();
        var html = template('address-place-template', {});
        $('#address-new-form').html(html);
        $('#address-new-form').utilSetArea();
    }
    var modalAddressList = function () {
        $('#modal-address-list').show();
        $('#modal-address-new').hide();
    }

    var createSuccess = function () {
        var data = $('body').data('return_data');
        <#if unitPrice gt 0>
        location.href = '/award/payment/' + data.awardOrderId;
        <#else>
        location.href = '/award/my/list?type=ZERO&status=0';
        </#if>
    }
</script>