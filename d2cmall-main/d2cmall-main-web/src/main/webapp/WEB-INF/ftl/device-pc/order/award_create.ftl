<form name="award-form" class="validate-form" action="/award/create" call-back="createSuccess()" method="post">
    <input type="hidden" name="awardId" value="${awardId}"/>
    <input type="hidden" name="quantity" value="${quantity}"/>
    <input type="hidden" name="skuId" value="${skuId}"/>
    <#if addresses?size gt 0>
    <h3>选择收货地址</h2>
        <div id="address-list" class="select-address address-list clearfix" name="address-list">
            <#list addresses as address>
                <div class="address-item address-item-vertical<#if address.isdefault> on</#if>" data-id="${address.id}">
                    <span class="fa fa-map-marker"></span> <strong>${(address.name)!}
                        / ${address.mobile}</strong> ${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}${address.street}
                    <input type="radio" name="addressId" value="${address.id}"<#if address.isdefault> checked</#if>
                           class="display-none"/>
                    <i class="fa fa-check"></i>
                </div>
            </#list>
            <div class="address-add address-add-vertical">
                <span class="fa fa-plus-circle"></span>使用其他地址
            </div>
        </div>
        <#else>
        <h3>填写收货地址</h2>
            </#if>
            <#if addresses?size == 0>
                <#assign tip_class=' tip-validate'>
                <#assign input_disabled=''>
            <#else>
                <#assign tip_class=''>
                <#assign input_disabled=' disabled'>
            </#if>
            <div id="address-add" class="form<#if addresses?size gt 0> display-none</#if>">
                <div class="form-item">
                    <label>收货人姓名</label>
                    <input type="text" name="name" id="name" value="" class="input"${input_disabled} />
                    <div class="tip${tip_class}" data-target="name" data-rule="byte"></div>
                </div>
                <div class="form-item">
                    <label>所在地区</label>
                    <span id="area-selector" class="" data-child="select" data-msg="省份城市必须选择">
                <select name="province.code" id="province" class="input province" style="width:130px;"${input_disabled}> 
                <option value="">选择省份</option> 
                </select> 
                <select name="city.code" id="city" style="width:100px;" class="input city"${input_disabled}> 
                <option value="">选择城市</option> 
                </select> 
                <select name="district.code" id="district" style="width:100px;" class="input district"${input_disabled}> 
                <option value="">选择区县</option> 
                </select>
            </span>
                    <div class="tip${tip_class}" data-target="province|city|district"></div>
                </div>
                <div class="form-item">
                    <label>街道地址</label>
                    <textarea name="street" cols="45" rows="2" id="street" class="input" minlength="5"
                              maxlength="60"${input_disabled}></textarea>
                    <div class="tip<#if addresses?size  == 0> tip-validate</#if>" data-target="street"></div>
                </div>
                <div class="form-item">
                    <label>手机号码</label>
                    <input type="text" name="mobile" id="mobile" maxlength="20" value="" size="30"
                           class="input"${input_disabled} onkeyup="this.value=this.value.replace(/\D/g,'')"
                           onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                    <div class="tip<#if addresses?size  == 0> tip-validate</#if>" data-target="mobile"
                         data-rule="mobile"></div>
                </div>
                <div class="form-item">
                    <label>电话号码</label>
                    <input type="text" name="phonePrefix" id="phonePrefix" maxlength="4" value="" style="width:10%;"
                           class="input utilSetNumber" placeholder="区号"${input_disabled} /> - <input type="text"
                                                                                                     name="phoneMiddle"
                                                                                                     id="phoneMiddle"
                                                                                                     maxlength="8"
                                                                                                     value=""${input_disabled}
                                                                                                     style="width:20%;"
                                                                                                     class="input utilSetNumber"
                                                                                                     placeholder="电话号码"/>
                    - <input type="text" maxlength="5" name="phoneSuffix" value=""${input_disabled} style="width:15%;"
                             class="input" placeholder="分机"/>
                </div>
            </div>
            <div class="text-center" style="margin-top:15px;">
                <button type="submit" name="pay" class="button button-green button-xl"> 确定</button>
            </div>
</form>

<script>
    $('#area-selector').utilSetArea();
    if ($('.address-item').size() > 0) {
        if ($('.address-item[class*="on"]').size() == 0) {
            $('.address-item:first').trigger('click');
        }
    }
    var createSuccess = function () {
        var data = $('body').data('return_data');
        location.href = '/award/my/list?status=1';
    }

    $('.address-add').click(function () {
        $(this).addClass('on');
        $(this).siblings().removeClass('on');
        $(this).siblings('.address-item').find('input').removeAttr('checked');
        $('#address-add .form-item .tip').addClass('tip-validate');
        $('#address-add').find('.input').removeAttr('disabled');
        $('#address-add').show();
        $.popModalUpdate();
    });

    $('.address-item').click(function () {
        $(this).siblings().removeClass('on');
        $('#address-add .form-item .tip').removeClass('tip-validate');
        $('#address-add').find('.input').attr('disabled', true);
        $('#address-add').hide();
        $.popModalUpdate();
    });
</script>
