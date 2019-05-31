<#import "templates/public_pc.ftl" as m>
<@m.page_header title='收货地址管理  - 个人中心' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="address"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>收货地址管理</h1>
        <div style="padding-bottom:10px">
            <button type="button" id="add-new-address" class="button pm">新增地址</button>
        </div>
        <div class="address_mana">
            <#if addresses?size gt 0>
                <#list addresses as address>
                    <div class="address-count" data-id="${address.id}" style="postion:relative;">
                        <a href="javascript:" data-url="/address/delete/${address.id}" confirm="确定要删除此地址吗？"
                           call-back="remove(${address.id})" class="ajax-request address-remove" method-type="post"><i
                                    class="model-close"></i></a>
                        <h4 style="margin-bottom:24px">${address.name}<#if address.isdefault><span
                                    style="font-size:16px;color:#fd555d">【默认】</span></#if></h4>
                        <p><span class="grey w-50">收货人</span>&nbsp;&nbsp;<span>${address.name}</span></p>
                        <p>
                            <span class="grey w-50">手机</span>&nbsp;&nbsp;<span><#if address.phonePrefix!=''>${(address.phonePrefix)!}${(address.phoneMiddle)!}${address.phoneSuffix}</#if> <#if (address.mobile?length) gt 8 >${address.mobile?substring(0,3)}****${address.mobile?substring(7)}<#else>${address.mobile} </#if> </span>
                        </p>
                        <p style="margin-bottom:0;"><span
                                    class="grey w-50">地址</span>&nbsp;&nbsp;<span><#if address.province?exists>${(address.province.name)!}${(address.city.name)!}${(address.district.name)!}</#if></span></br>
                            <span class="w-50"></span>&nbsp;&nbsp;<span>${address.street}</span></p>
                        <p class="text-right" style="position:absolute;right:20px;bottom:20px;margin-bottom:0;">
                            <#if !address.isdefault>
                            <a href="javascript:" data-url="/address/default/${address.id}" method-type="post"
                               class="ajax-request" style="color:#fd555d;font-size:14px;">设为默认</a>
                            </#if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="javascript:" request-url="/address/${address.id}"
                               template-id="address-edit-template" data-url="/address/update" modal-type="pop"
                               modal-width="550" class="ajax-request" style="font-size:14px;color:#4a90e2">编辑</a>
                        </p>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</div>
<script id="address-new-template" type="text/html">
    <div class="form">
        <h2 class="pop-title">新增收货地址</h2>
        <form name="form-info" class="validate-form form-cinfo" action="/address/insert" method="POST"
              id="add_address_form">
            <div class="form-item">
                <label><span class="red">*</span>收货人</label>
                <input type="text" name="name" id="name" value="" class="input"/>
                <div class="tip tip-validate" data-target="name" data-rule="name" min-length="2" max-length="10"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>所在地区</label>
                <span id="area-selector" class="" data-child="select" data-msg="省份城市必须选择">
                <select name="regionPrefix" id="province" class="input province" style="width:130px;"> 
                    <option value="">选择省份</option> 
                    </select> 
                    <select name="regionMiddle" id="city" style="width:100px;" class="input city"> 
                    <option value="">选择城市</option> 
                    </select> 
                    <select name="regionSuffix" id="district" style="width:100px;" class="input district"> 
                    <option value="">选择区县</option> 
                    </select>
                </span>
                <div class="tip tip-validate" data-target="province|city|district"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>街道地址</label>
                <textarea name="street" cols="45" rows="2" id="street" class="input" minlength="5"
                          maxlength="100"></textarea>
                <div class="tip tip-validate" data-target="street"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>手机号码</label>
                <input type="text" name="mobile" id="mobile" maxlength="20" value="" class="input mobile"
                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                <div class="tip tip-validate" data-target="mobile" data-rule="mobile" min-length="5"></div>
            </div>
            <div class="form-item">
                <label>电话号码</label>
                <input type="text" name="phonePrefix" id="phonePrefix" maxlength="4" value="" style="width:10%;"
                       class="input number" placeholder="区号"/> - <input type="text" name="phoneMiddle" id="phoneMiddle"
                                                                        maxlength="8" value="" style="width:20%;"
                                                                        class="input number" placeholder="电话号码"/> -
                <input type="text" maxlength="5" name="phoneSuffix" value="" style="width:15%;" class="input"
                       placeholder="分机"/>
            </div>
            <div class="form-button">
                <input type="hidden" name="longitude" value=""/>
                <input type="hidden" name="latitude" value=""/>
                <button type="button" value="确定" id="add-submit-button" class="button">确定</button>
            </div>
        </form>
    </div>
</script>

<script id="address-edit-template" type="text/html">
    <div class="form">
        <h2 class="pop-title">修改收货地址</h2>
        <form name="form-info" class="validate-form form-cinfo" action="/address/update" method="POST"
              id="update_address_form">
            <input type="hidden" name="id" value="{{address.id}}"/>
            <div class="form-item">
                <label><span class="red">*</span>收货人</label>
                <input type="text" name="name" id="name" value="{{address.name}}" class="input"/>
                <div class="tip tip-validate" data-target="name" data-rule="byte" byte-max="30" byte-min="4"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>所在地区</label>
                <span id="area-selector" class="" data-child="select" data-msg="省份城市必须选择">
                <select name="regionPrefix" rel="{{address.province.code}}" id="province" class="input province"
                        style="width:130px;">
                    <option value="">选择省份</option> 
                    </select> 
                    <select name="regionMiddle" rel="{{address.city.code}}" id="city" style="width:100px;"
                            class="input city">
                    <option value="">选择城市</option> 
                    </select> 
                    <select name="regionSuffix" rel="{{address.district.code}}" id="district" style="width:100px;"
                            class="input district">
                    <option value="">选择区县</option> 
                    </select>
                </span>
                <div class="tip tip-validate" data-target="province|city|district"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>街道地址</label>
                <textarea name="street" cols="45" rows="2" id="street" class="input" minlength="5" maxlength="100">{{address.street}}</textarea>
                <div class="tip tip-validate" data-target="street"></div>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>手机号码</label>
                <input type="text" name="mobile" id="mobile" maxlength="20" value="{{address.mobile}}" class="input"
                       onkeyup="this.value=this.value.replace(/\D/g,'')"
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
            </div>
            <div class="form-item">
                <label>电话号码</label>
                <input type="text" name="phonePrefix" id="phonePrefix" maxlength="4" value="{{address.phonePrefix}}"
                       style="width:10%;" class="input number" placeholder="区号"/> - <input type="text"
                                                                                           name="phoneMiddle"
                                                                                           id="phoneMiddle"
                                                                                           maxlength="8"
                                                                                           value="{{address.phoneMiddle}}"
                                                                                           style="width:20%;"
                                                                                           class="input number"
                                                                                           placeholder="电话号码"/> - <input
                        type="text" maxlength="5" name="phoneSuffix" value="{{address.phoneSuffix}}" style="width:15%;"
                        class="input" placeholder="分机"/>
            </div>
            {{if address.isdefault}} <input type="hidden" name="isdefault" id="isdefault" value="true"/>{{/if}}
            <div class="form-button">
                <input type="hidden" name="longitude" value=""/>
                <input type="hidden" name="latitude" value=""/>
                <button type="button" value="确定" id="update-submit-button" class="button">确定</button>
            </div>
        </form>
    </div>
    <script>
        $('form').utilSetArea();
</script>
    </script>
    <
    script
    type = "text/javascript"
    src = "https://webapi.amap.com/maps?v=1.4.8&key=4fc7a9efb33c5f84584ae8d78d5473ef&plugin=AMap.Geocoder" ></script>
<script type="text/javascript">
    $('#area-selector').utilSetArea();

    var remove = function (id) {
        $('.address-count[data-id=' + id + ']').remove();
    }

    $('.item').hover(function () {
        $(this).css('background', '#F2F2F2');
        $(this).find('.set').show();
    }, function () {
        $(this).css('background', '');
        $(this).find('.set').hide();
    });

    $('#add-new-address').on('click', function () {
        var html = template('address-new-template', {});
        $.popModal({content: html, 'width': 600});
        $('form').utilSetArea();
        return false;
    });

    //新增地址
    $(document).on('click', '#add-submit-button', function () {
        addParams();
        setTimeout(function () {
            $('#add_address_form').submit();
        }, 500);
    })
    //修改地址
    $(document).on('click', '#update-submit-button', function () {
        addParams();
        setTimeout(function () {
            $('form#update_address_form').submit();
        }, 500);
    })

    //添加地址经纬度
    function addParams() {
        var geocoder = new AMap.Geocoder({
            city: '全国'// city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
        });
        var provinceVal = $('select[name=regionPrefix] option:selected').text(),
            cityVal = $('select[name=regionMiddle] option:selected').text(),
            districtVal = $('select[name=regionSuffix] option:selected').text(),
            streetVal = $('textarea[name=street]').val();
        var addressVal = provinceVal + cityVal + districtVal + streetVal;//这里是需要转化的地址
        geocoder.getLocation(addressVal, function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
                //result为对应的地理位置详细信息
                var locationVal = result.geocodes[0],
                    longitudeVal = locationVal.location.lng,
                    latitudeVal = locationVal.location.lat;
                $('input[name=longitude]').val(longitudeVal);
                $('input[name=latitude]').val(latitudeVal);
            }
        })
        return true;
    }


</script>
<@m.page_footer />