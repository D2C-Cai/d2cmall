<#if (cartItemCount<1)>
    <p class="ubc-empty">你的购物车里还没有任何宝贝。赶紧挑几件吧。</p>
<#else>
    <p class="ubc-title">最近加入的宝贝：</p>
    <dl>
        <#list cartItems as value>
            <dd class="ubc-list">
                <div class="ub-product-img"><a href="/product/${value.productSKU.product.id}" class="cart_img"><img
                                src="${picture_base}/${value.productSKU.product.productImageCover}!80"
                                alt="${value.productSKU.product.name}"/></a></div>
                <div class="ub-product-title"><a
                            href="/product/${value.productSKU.product.id}">${value.productSKU.product.name}</a></div>
                <div class="ub-product-price">${value.productSKU.price?string("currency")?substring(0,1)}<strong
                            class="unit unit-price">${value.productSKU.price?string("currency")?substring(1)}</strong>
                </div>
                <div class="ub-product-property">${value.productSKU.sp1?eval.name}
                    :${value.productSKU.sp1?eval.value}  ${value.productSKU.sp2?eval.name}
                    :${value.productSKU.sp2?eval.value} </div>
                <div class="ub-del"><a href="javascript:" class="del" rel="${value.id}">删除</a></div>
            </dd>
        </#list>
        <dd class="ubc-oprator">
            <#if (cartItemCount>5)>
                <span>购物车里还有${cartItemCount-5}种宝贝</span>
            </#if>
            <input type="button" name="go_cart" value="去结算" class="button-l b-b"/>
        </dd>
    </dl>
</#if>