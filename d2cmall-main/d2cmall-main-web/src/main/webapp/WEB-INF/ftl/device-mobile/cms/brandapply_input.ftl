<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="申请入驻" title="申请入驻" keywords="申请入驻" />
<style>
    .upload-item {
        margin-bottom: .3em;
    }
</style>
<div class="section">
    <div class="form" id="form-brand">
        <form name="form-info" class="validate-form brand-apply-form" action="/brandapply"
              confirm="恭喜您提交成功，为更好和我们联系沟通，可加微信：miyako1113" method="post">
            <input type="hidden" value="${(brandApply.id)!}" name="id"/>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red"
                                               style="vertical-align:sub;padding:0 2px;">*</span>品牌名称</label>
                <input type="text" name="brand" id="brand" title="品牌名称" placeholder="必填" value="${(brandApply.brand)!}"
                       class="input validate" max-length="30" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red" style="vertical-align:sub;padding:0 2px;">*</span>设计师</label>
                <input type="text" name="designers" id="designers" title="设计师" placeholder="必填"
                       value="${(brandApply.designers)!}" class="input validate" max-length="30" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red" style="vertical-align:sub;padding:0 2px;">*</span>价位段</label>
                <input type="text" name="priceSegment" id="priceSegment" title="价位段" placeholder="必填"
                       value="${(brandApply.priceSegment)!}" class="input validate" max-length="30" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red"
                                               style="vertical-align:sub;padding:0 2px;">*</span>目标客户</label>
                <input type="text" name="customerGroups" id="customerGroups" title="目标客户" placeholder="必填"
                       value="${(brandApply.customerGroups)!}" max-length="200" class="input validate"
                       style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red" style="vertical-align:sub;padding:0 2px;">*</span>联系人</label>
                <input type="text" name="contactor" id="contactor" title="联系人" placeholder="必填"
                       value="${(brandApply.contactor)!}" class="input validate" min-length="2" max-length="10"
                       style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red" style="vertical-align:sub;padding:0 2px;">*</span>电话</label>
                <input type="text" name="telephone" id="telephone" placeholder="必填" title="电话"
                       value="${(brandApply.telephone)!}" class="input validate" data-rule="mobile" min-length="5"
                       max-length="20" style="width:66%"/>
            </div>

            <div class="form-item item-flex">
                <label style="width:29%"><span class="red" style="vertical-align:sub;padding:0 2px;">*</span>邮件</label>
                <input type="text" name="email" id="email" placeholder="必填" title="邮件" value="${(brandApply.email)!}"
                       class="input validate" data-rule="email" min-length="5" max-length="30" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red" style="vertical-align:sub;padding:0 2px;">*</span>地址</label>
                <input type="text" name="address" id="address" placeholder="必填" title="地址"
                       value="${(brandApply.address)!}" class="input validate" max-length="100" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span class="red"
                                               style="vertical-align:sub;padding:0 2px;">*</span>品牌说明</label>
                <textarea name="intro" id="intro" placeholder="必填" title="品牌说明" class="input validate" max-length="200"
                          rows="4" onKeyDown="checkLength()" onKeyUp="checkLength()" onPaste="checkLength()"
                          style="border:1px solid #ededed;padding-left:10px;width:100%">${(brandApply.intro)!}</textarea>
            </div>
            <div class="form-item item-flex">
                <label style="width:100%"><span class="red"
                                                style="vertical-align:top;padding:0 2px;">*</span>lookBook</label>
                <div class="upload-item">
                    <span style="font-size:1.5em;line-height:2.25em;">+</span>
                    <input type="file" name="file" class="upload-file" data-upload-url="/picture/upload"
                           accept="image/gif,image/jpeg,image/jpg,image/png,image/svg" multiple="multiple"
                           style="width:3.5em;height:3.5em;font-size:1em!important;opacity:0;"/>
                    <input type="hidden" name="lookBook" title="lookBook" class="input validate"/>
                </div>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span style="padding:0 2px;">&nbsp;&nbsp;</span>微信</label>
                <input type="text" name="weixin" id="weixin" placeholder="选填" title="微信" value="${(brandApply.weixin)!}"
                       max-length="20" class="input validate" data-rule="type" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span style="padding:0 2px;">&nbsp;&nbsp;</span>微博</label>
                <input type="text" name="weibo" id="weibo" placeholder="选填" title="微博" value="${(brandApply.weibo)!}"
                       class="input validate" max-length="20" data-rule="type" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span style="padding:0 2px;">&nbsp;&nbsp;</span>QQ</label>
                <input type="text" name="qq" id="qq" placeholder="选填" title="QQ" value="${(brandApply.qq)!}"
                       class="input validate" max-length="20" data-rule="type" style="width:66%"/>
            </div>
            <div class="form-item item-flex">
                <label style="width:29%"><span style="padding:0 2px;">&nbsp;&nbsp;</span>官网</label>
                <input type="text" name="website" id="website" placeholder="选填" title="官网"
                       value="${(brandApply.website)!}" class="input validate" data-rule="type" max-length="30"
                       style="width:66%"/>
            </div>
            <div class="form-button">
                <button type="button" class="button button-l button-black brand-button">确定</button>
            </div>
        </form>
    </div>
</div>
<script>
    function checkLength() {
        var value = $('textarea[name=intro]').val();
        if (value.length > 150) {
            $('textarea[name=intro]').val(value.substr(0, 150));
        }
    }

    $('.brand-button').on('click', function () {
        var val = document.querySelectorAll('.path-input'),
            len = val.length,
            arr = [],
            i;
        for (i = 0; i < len; i++) {
            arr.push(val[i].defaultValue);
        }
        arr = arr.join(",").substring(0);
        $('input[name=lookBook]').val(arr);
        $('.brand-apply-form').submit();
    });
</script>
<@m.page_footer />