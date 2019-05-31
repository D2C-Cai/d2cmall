<#import "templates/public_pc.ftl" as m>
<@m.page_header title=''/>
<@m.top_nav suspend=false />
<style>
    .form .form-item > label:first-child span {
        display: inline-block;
        width: 14px;
        height: 18px;
        text-align: center;
        margin: 0;
        color: #FD555D
    }
</style>
<div class="main-body"
     style="background:url(//static.d2c.cn/common/c/images/brand_bg.jpg) center center no-repeat;background-size:cover;">
    <div class="layout-response clearfix" style="margin:100px auto">
        <div class="brand-form arial">
            <div id="form-read">
                <h1>D2C设计师品牌入驻要求</h1>
                <div style="height:400px;overflow:auto;padding:30px;line-height:180%;">
                    <p>一、 产品质量：</p>
                    <p style="padding-left:20px;">(1).产品经过严格的质量检验，质量检验内容包括成份、外观和尺寸等。</p>
                    <p style="padding-left:20px;">(2).产品的各项指标符合国家对相关产品制定的标准要求。（<a style="color:blue"
                                                                                  href="//static.d2c.cn/other/doc/%E4%BA%A7%E5%93%81%E5%9B%BD%E6%A0%87%E5%8F%82%E8%80%83%E6%96%87%E4%BB%B6.docx">产品国标参考文件</a>）
                    </p>
                    <p style="padding-left:20px;">(3).每件产品要有国家认可的第三方机构出具的质量检测报告（一般指成份检测报告和18401安全类别检测报告） </p>
                    <p>&nbsp;</p>
                    <p>二、 产品资料：</p>
                    <p style="padding-left:20px;">品牌需要按照D2C平台标注要求，在发货前提供对应的全部的产品资料。（<a style="color:blue"
                                                                                       href="//static.d2c.cn/other/doc/%E4%BA%A7%E5%93%81%E8%B5%84%E6%96%99%E8%A1%A8.xls">产品资料表</a>）
                    </p>
                    <p>&nbsp;</p>
                    <p>三、 免责声明与有偿服务：</p>
                    <p style="padding-left:20px;">品牌供给D2C平台的产品不符合国家对该产品的强制性执行标准或者与《消费者权益保护费》相违背的，其产生的后果与损失均由品牌方承担。</p>
                </div>
                <div class="form">
                    <div class="form-button">
                        <input type="button" value="我同意该协议" id="agree" class="button"/>
                    </div>
                </div>
            </div>
            <div id="form-submit" class="display-none">
                <h1>品牌入驻申请</h1>
                <form method="post" class="validate-form" name="brandapply" action="/brandapply"
                      success-tip="恭喜您提交成功，为更好和我们联系沟通，可加微信：miyako1113">
                    <input type="hidden" value="${(brandApply.id)!}" name="id"/>
                    <div class="form">
                        <div class="form-item">
                            <label> <span class="red">*</span>品牌名称(brand name)</label>
                            <input type="text" name="brand" id="brand" value="${(brandApply.brand)!}" maxlength="30"
                                   class="input" style="width:30%"/>
                            <div class="tip tip-validate" data-target="brand"></div>
                        </div>
                        <div class="form-item">
                            <label><span class="red">*</span>设计师名称(designer)</label>
                            <input type="text" name="designers" id="designers" value="${(brandApply.designers)!}"
                                   maxlength="30" class="input" style="width:30%"/>
                            <div class="tip tip-validate" data-target="designers"></div>
                        </div>
                        <div class="form-item">
                            <label><span class="red">*</span>价位段(price segment)</label>
                            <input type="text" name="priceSegment" id="priceSegment"
                                   value="${(brandApply.priceSegment)!}" maxlength="30" class="input"
                                   style="width:30%"/>
                            <div class="tip tip-validate" data-target="priceSegment"></div>
                        </div>
                        <div class="form-item">
                            <label><span class="red">*</span>目标客户(customer groups)</label>
                            <input type="text" name="customerGroups" id="customerGroups"
                                   value="${(brandApply.customerGroups)!}" maxlength="200" class="input"
                                   style="width:50%"/>
                            <div class="tip tip-validate" data-target="priceSegment"></div>
                        </div>

                        <div class="form-item">
                            <label><span class="red">*</span>品牌说明(brand intro)</label>
                            <textarea style="width:60%;height:80px;" name="intro" id="intro" class="input require"
                                      minlength="50" maxlength="200">${(brandApply.intro)!}</textarea>
                            <div class="tip tip-validate" data-target="intro"></div>
                        </div>

                        <div class="form-item" style="display:flex;justify-content:space-around;">
                            <label><span class="red">*</span>lookBook</label>
                            <div style="width:66.5%;">
                                <div class="upload-item">
                                    <span>+</span>
                                    <input type="file" name="file" class="upload-file" data-upload-url="/picture/upload"
                                           accept="image/*" style="width:100px;height:100px;opacity:0;"/>
                                    <input type="hidden" name="lookBook" class="input"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form" style="margin-top:10px;">
                        <div class="form-item">
                            <label><span class="red">*</span>联系人(contactor)</label>
                            <input type="text" name="contactor" id="contactor" value="${(brandApply.contactor)!}"
                                   pattern=".{2,10}" class="input" style="width:30%"/>
                            <div class="tip tip-validate" data-target="contactor"></div>
                        </div>
                        <div class="form-item">
                            <label><span class="red">*</span>电话(telephone)</label>
                            <input type="text" name="telephone" id="telephone" value="${(brandApply.telephone)!}"
                                   pattern=".{5,20}" class="input" style="width:30%"/>
                            <div class="tip tip-validate" data-target="telephone" data-rule="mobile"></div>
                        </div>
                        <div class="form-item">
                            <label> <span class="red">*</span>邮件(email)</label>
                            <input type="text" name="email" id="email" value="${(brandApply.email)!}" pattern=".{5,30}"
                                   class="input" style="width:30%"/>
                            <div class="tip tip-validate" data-target="email" data-rule="email"></div>
                        </div>
                        <div class="form-item">
                            <label><span class="red">*</span>地址(address)</label>
                            <input type="text" name="address" id="address" value="${(brandApply.address)!}"
                                   maxlength="100" class="input" style="width:50%"/>
                            <div class="tip tip-validate" data-target="address"></div>
                        </div>

                        <div class="form-item">
                            <label><span></span>微信(weixin)</label>
                            <input type="text" name="weixin" id="weixin" value="${(brandApply.weixin)!}" maxlength="20"
                                   class="input" style="width:30%"/>
                        </div>

                        <div class="form-item">
                            <label><span></span>微博(weibo)</label>
                            <input type="text" name="weibo" id="weibo" value="${(brandApply.weibo)!}" maxlength="20"
                                   class="input" style="width:30%"/>
                        </div>

                        <div class="form-item">
                            <label><span></span>QQ(Tencent QQ)</label>
                            <input type="text" name="qq" id="qq" value="${(brandApply.qq)!}" maxlength="20"
                                   class="input" style="width:30%"/>
                        </div>
                        <div class="form-item">
                            <label><span></span>官网(website)</label>
                            <input type="text" name="website" id="website" value="${(brandApply.website)!}"
                                   maxlength="30" class="input" style="width:50%"/>
                        </div>
                        <div class="form-button">
                            <input type="button" value="确定" class="button brand-apply-button"/>
                        </div>
                    </div>
                    <p style="padding-top:30px;">1、相关作品请发送邮箱：media@d2cmall.com。</p>
                    <p>2、我们将在1-3个工作日内与您线下联系，请保持您的电话畅通。</p>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    $('#agree').click(function () {
        $('#form-read').hide();
        $('#form-submit').show();
    });
    $('.brand-apply-button').click(function () {
        var val = document.querySelectorAll('.path-input'),
            len = val.length,
            arr = [],
            i;
        for (i = 0; i < len; i++) {
            arr.push(val[i].defaultValue);
        }
        arr = arr.join(",").substring(0);
        $('input[name=lookBook]').val(arr);
        $('form[name=brandapply]').submit();
    });
</script>
<@m.page_footer />