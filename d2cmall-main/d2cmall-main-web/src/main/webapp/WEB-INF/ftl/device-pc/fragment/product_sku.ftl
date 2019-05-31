<div class="fav-cart" rel="${product.id}">
    <a href="javascript:void(0)" class="close"> x </a>
    <div class="left">
        <p class="product-sku img">
            <span class="font">颜色：</span>
            <#list result.data.product.salesproperty?eval.sp1 as p>
                <a href="javascript:" id="c-${product.id}-${p.id}" data="${p.id}" rel="${p.value}"><#if p.img!=''><span
                            class="img"><img src="${picture_base}/${p.img}!50" alt="${p.value}" width="40"/>
                    </span></#if><i>已选择</i></a>
            </#list>
        </p>
        <p class="product-sku txt">
            <span class="font">尺码：</span>
            <#list result.data.product.salesproperty?eval.sp1 as p>
                <a href="javascript:" id="s-${product.id}_${p.id}" data="${p.id}"
                   rel="${p.value}"><span>${p.value}</span><i>已选择</i></a>
            </#list>
        </p>
        <p class="product-sku" style="padding:0;height:30px;">
            <span class="font">数量：</span><input type="text" name="num" value="1" size="1" maxlength="3"
                                                class="data-num float-left"/>
            <span class="data-crease float-left">
			<em class="increase">+</em>
			<em class="decrease">-</em>
			</span>
            <span class="float-left" style="padding-top:5px;">(库存还有 <span id="storge_${product.id}"
                                                                          class="small red storge">${product.stock}</span>  件)</span>
        </p>
        <p class="product_tip"></p>
        <p><input type="hidden" name="item" value=""/><!-- 货号 -->
            <input type="hidden" name="productId" value="${product.id}" id="productId"/>
            <input type="button" name="addCart_ok" value="确认添加" class="button-l b-b"/>
            &nbsp;&nbsp;&nbsp;&nbsp;<a class="cancel" href="javascript:">取消</a>
        </p>
    </div>
    <div class="clear"></div>
    <div>