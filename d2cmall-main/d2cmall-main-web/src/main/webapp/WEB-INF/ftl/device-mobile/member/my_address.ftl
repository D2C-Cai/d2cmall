<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='收货地址管理' title='收货地址管理' button='false' service='false' hastopfix='false'/>
<div class="section">
    <div class="padding" style="margin-bottom:-0.6em">
        <button type="button" name="add-new-address" class="button button-l button-red add-new-address"
                style="width:100%;">新增地址
        </button>
    </div>
    <#if addresses?size gt 0>
    <div class="form">
        <#list addresses as address>
            <div class="form-item item-address item-margin" style="padding-left:1em;" data-id="${address.id}">
                <p>${(address.name)!} ${(address.mobile)!}<#if address.isdefault><span class="red">【默认】</span></#if></p>
                <p>${(address.province.name)?default("")}${(address.city.name)?default("")}${(address.district.name)?default("")}<#if address.street?length gt 25>${address.street[0..25]}...<#else>${address.street}</#if></p>
                <div class="text-right" style="margin-top:0.6em;">
                    <#if address.isdefault>
                    <#else>
                        <button type="button" data-url="/address/default/${address.id}" confirm="确定要设置为默认地址吗？"
                                method-type="post" class="button button-outline ajax-request">设为默认
                        </button>
                    </#if>
                    <button type="button" data-url="/address/update" request-url="/address/${address.id}"
                            template-id="address-edit-template" class="button button-outline ajax-request">修改
                    </button>
                    <button type="button" data-url="/address/delete/${address.id}" confirm="确定要删除该地址吗？"
                            method-type="post" call-back="removeItem(${address.id})"
                            class="button button-outline ajax-request">删除
                    </button>
                </div>
            </div>
        </#list>
        <#else>
            <div class="no-result">你还没有收货地址，请先<a href="javascript:" class="add-new-address">添加地址</a></div>
        </#if>
    </div>
</div>
<script id="address-new-template" type="text/html">
    <header>
        <div class="header">
            <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
            <div class="header-title">新增地址</div>
        </div>
    </header>
    <div class="form" id="address-form">
        <form class="validate-form" action="/address/insert" success-tip="地址添加成功！" method="post" id="add_address_form">
            <div class="form-item">
                <input type="text" name="name" placeholder="收货人姓名" data-rule="byte" class="input validate"
                       max-length="10" min-length="2" title="收货人姓名"/>
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
                <input type="text" name="street" placeholder="路名或街道地址，门牌号" class="input validate" title="详细地址"
                       min-length="4"/>
            </div>
            <div class="form-item">
                <input type="text" name="mobile" placeholder="收货人手机号" min-length="5" max-length="20"
                       class="input validate" title="收货人手机号" onkeyup="this.value=this.value.replace(/\D/g,'')"
                       onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
            </div>
            <div class="form-item">
                &nbsp;<input type="checkbox" name="isdefault" value="true" id="isdefault"/><label
                        for="isdefault">设为默认地址</label>
            </div>
            <div class="form-button">
                <input type="hidden" name="longitude" value=""/>
                <input type="hidden" name="latitude" value=""/>
                <button type="button" name="submit-button" id="add-submit-button" class="button button-l button-red">
                    确定
                </button>
            </div>
        </form>
    </div>
    <script>
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
    id = "address-edit-template"
    type = "text/html" >
        < header >
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
    = "header-title" > 编辑地址 < /div>
        < /div>
        < /header>
        < div
    class
    = "form"
    id = "address-form" >
        < form
    class
    = "validate-form"
    action = "/address/update"
    success - tip = "地址编辑成功！"
    method = "POST"
    id = "update_address_form" >
        < input
    type = "hidden"
    name = "id"
    value = "{{address.id}}" / >
        < div
    class
    = "form-item" >
        < input
    type = "text"
    name = "name"
    placeholder = "收货人姓名"
    value = "{{address.name}}"
    data - rule = "byte"
    class
    = "input validate"
    max - length = "10"
    min - length = "2"
    title = "收货人姓名" / >
        < /div>
        < div
    class
    = "form-item" >
        < select
    name = "regionPrefix"
    class
    = "input province validate"
    rel = "{{address.province.code}}"
    title = "省份"
    style = "width:32%;" >
        < option
    value = "" > 选择省份 < /option>
        < /select>
        < select
    name = "regionMiddle"
    class
    = "input city validate"
    rel = "{{address.city.code}}"
    title = "城市"
    style = "width:32%;" >
        < option
    value = "" > 选择城市 < /option>
        < /select>
        < select
    name = "regionSuffix"
    class
    = "input district validate"
    rel = "{{address.district.code}}"
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
    value = "{{address.street}}"
    class
    = "input validate"
    title = "详细地址"
    min - length = "4" / >
        < /div>
        < div
    class
    = "form-item" >
        < input
    type = "text"
    name = "mobile"
    placeholder = "收货人手机号"
    min - length = "5"
    max - length = "20"
    value = "{{address.mobile}}"
    class
    = "input validate"
    title = "收货人手机号"
    onkeyup = "(this.v=function(){this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
    onblur = "this.v();" / >
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
    id = "isdefault"
    {
        {
            if address.isdefault}
    }
    checked
    {
        {
            /if}} / > < label
            for= "isdefault" > 设为默认地址 < /label>
                < /div>
                < div class
            = "form-button" >
                < input
            type = "hidden"
            name = "longitude"
            value = "" / >
                < input
            type = "hidden"
            name = "latitude"
            value = "" / >
                < button
            type = "button"
            name = "submit-button"
            id = "update-submit-button"
        class
            = "button button-l button-red" > 确定 < /button>
                < /div>
                < /form>
                < /div>
                < script >
                $('input[name="mobile"]').on('change', function () {
                    if (!$(this).utilValidateMobile()) {
                        $(this).focus();
                        $.flashTip({position: 'top', type: 'error', message: '请输入正确手机号码'});
                        return false;
                    }
                });
            $('#address-form').utilSetArea();
    </script>
    </script>
    <
    script
    src = "https://webapi.amap.com/maps?v=1.4.8&key=4fc7a9efb33c5f84584ae8d78d5473ef&plugin=AMap.Geocoder" ></script>
<script>
    $('.add-new-address').on('touchstart', function () {
        var html = template('address-new-template', {});
        $.popModal({content: html, inAnimate: 'slideInRight', outAnimate: 'slideOutRight'});
        $('#address-form').utilSetArea();
        return false;
    });
    var removeItem = function (id) {
        $('.form-item[data-id=' + id + ']').addClass('animated zoomOutUp');
        setTimeout(function () {
            $('.form-item[data-id=' + id + ']').remove();
        }, 500);
    }

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
            $('#update_address_form').submit();
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
            streetVal = $('input[name=street]').val();
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