<ul>
    <#list result.data['pager'].list as product>
        <li>
            <p class="img"><a href="/product/${product.id}" class="product-list-img" target="_blank"><img
                            src="${picture_base}${product.productImageCover}!275" alt="${product.name}"/></a></p>
            <p class="txt"><a href="/product/${product.id}" target="_blank">${product.name}</a></p>
            <p class="price">
                <span>&yen; ${product.minPrice?string('currency')?substring(1)}</span> &nbsp;&nbsp;
                <#if product.minPrice < product.originalPrice> &nbsp;&nbsp;&nbsp;<s class="data-price"><em
                        class="currency">&yen;</em>${(product.originalPrice?string("currency")?substring(1))!}</s></#if>
            </p>
            <p class="op">
                <a href="/product/${product.id}" target="_blank">立即抢购</a>
            </p>
        </li>
    </#list>
    <div class="clearfix"></div>
</ul>