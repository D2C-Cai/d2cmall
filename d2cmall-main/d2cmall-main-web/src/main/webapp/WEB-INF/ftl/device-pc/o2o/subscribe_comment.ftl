<div class="form">
    <form method="post" class="validate-form" action="/comment/o2o/insert.json">
        <input type="hidden" name="id" value="${(comment.id)!}"/>
        <input type="hidden" name="o2oSubscribeId" value="${o2oSubscribeId}"/>
        <input type="hidden" name="productId" value="${comment.productId}"/>
        <input type="hidden" name="productImg" value="${comment.productImg}"/>
        <input type="hidden" name="device" value="PC"/>
        <input type="hidden" name="skuProperty" value="${comment.skuProperty}"/>
        <div class="form-item">
            <label>商品：</label>
            <span class="rating">
                <span class="star"></span><span class="star"></span><span class="star"></span><span class="star"></span><span
                        class="star"></span>
                <input type="hidden" name="productScore" value="${(comment.productScore)!'5'}"/>
            </span>
        </div>
        <div class="form-item">
            <label>预约速度：</label>
            <span class="rating">
                <span class="star"></span><span class="star"></span><span class="star"></span><span class="star"></span><span
                        class="star"></span>
                <input type="hidden" name="deliverySpeedScore" value="${(comment.deliverySpeedScore)!'5'}"/>
            </span>
        </div>
        <div class="form-item">
            <label>门店服务：</label>
            <span class="rating">
                <span class="star"></span><span class="star"></span><span class="star"></span><span class="star"></span><span
                        class="star"></span>
                <input type="hidden" name="deliveryServiceScore" value="${(comment.deliveryServiceScore)!'5'}"/>
            </span>
        </div>
        <div class="form-item form-item-vertical">
            <label>评价与反馈:</label>
            <textarea style="height: 100px;" name="content" id="content" onKeyDown="checkLength()"
                      onKeyUp="checkLength()" onPaste="checkLength()" class="input">${(comment.content)!}</textarea>
            <div class="tip tip-validate" data-target="content"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>支付金额:</label>
            <input style="height: 50px;" name="cusCost" value="${(comment.cosCost)!}" class="input">
        </div>
        <div class="form-button">
            <button type="submit" class="button button-green">确定</button>
        </div>
    </form>
</div>
<script>
    $('.rating').utilRating();

    function checkLength() {
        var value = $('textarea[name=content]').val();
        if (value.length > 150) {
            $('textarea[name=content]').val(value.substr(0, 150));
        }
    }
</script>