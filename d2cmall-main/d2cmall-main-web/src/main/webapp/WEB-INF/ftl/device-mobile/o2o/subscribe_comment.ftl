<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="预约评价" title='预约评价' service='false' hastopfix='false'/>
<div class="section lazyload">
    <div class="form">
        <form method="post" class="validate-form" action="/comment/o2o/insert.json"
              redirect-url="/o2oSubscribe/my/list">
            <input type="hidden" name="id" value="${(comment.id)!}"/>
            <input type="hidden" name="o2oSubscribeId" value="${o2oSubscribeId}"/>
            <input type="hidden" name="productId" value="${comment.productId}"/>
            <input type="hidden" name="productImg" value="${comment.productImg}"/>
            <input type="hidden" name="device" value="Mobile"/>
            <input type="hidden" name="skuProperty" value="${comment.skuProperty}"/>
            <div class="form-item item-flex" style="font-size:10px;color:#999;border:none">满意请打5星哦~</div>
            <div class="form-item item-flex">
                <label>商品满意度</label>
                <label style="width:70%;text-align:left;" class="rating"><span class="w-star"></span><span
                            class="w-star"></span><span class="w-star"></span><span class="w-star"></span><span
                            class="w-star"></span> <input type="hidden" name="productScore"
                                                          value="${(comment.productScore)!'5'}"/></label>
            </div>
            <div class="form-item item-flex">
                <label>预约速度</label>
                <label style="width:70%;text-align:left;" class="rating"><span class="w-star"></span><span
                            class="w-star"></span><span class="w-star"></span><span class="w-star"></span><span
                            class="w-star"></span> <input type="hidden" name="deliverySpeedScore"
                                                          value="${(comment.deliverySpeedScore)!'5'}"/></label>
            </div>
            <div class="form-item item-flex">
                <label>门店服务</label>
                <label style="width:70%;text-align:left;" class="rating"><span class="w-star"></span><span
                            class="w-star"></span><span class="w-star"></span><span class="w-star"></span><span
                            class="w-star"></span><input type="hidden" name="deliveryServiceScore"
                                                         value="${(comment.deliveryServiceScore)!'5'}"/></label>
            </div>
            <div class="form-item item-flex" style="margin-top:.9em">
                <label>消费金额</label>
                <label style="width:70%"><input name="cusCost" value="${(comment.cosCost)!}" class="input validate"
                                                data-rule="num" title="消费金额" placeholder="请输入您的消费金额"
                                                type="tel"/></label>
            </div>
            <div class="form-item item-flex">
                <textarea style="height: 100px;font-size:.8em" name="content" id="content" onKeyDown="checkLength()"
                          onKeyUp="checkLength()" onPaste="checkLength()" placeholder="请写一些您的评价或建议吧~"
                          class="input">${(comment.content)!}</textarea>
            </div>
            <div class="form-button">
                <button type="submit" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
</div>
<script>
    function checkLength() {
        var value = $('textarea[name=content]').val();
        if (value.length > 150) {
            $('textarea[name=content]').val(value.substr(0, 150));
        }
    }

    $('.rating').utilRating();
</script>
<@m.page_footer />

