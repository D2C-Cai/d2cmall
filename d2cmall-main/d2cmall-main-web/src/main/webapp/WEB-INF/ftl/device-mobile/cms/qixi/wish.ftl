<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='${wish.displayName}的D2C心愿单' css="qixi" />
<div>
    <a href="/qixi"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/m_banner.jpg" style="width:100%;"
                         alt=""/></a>
</div>
<div class="wish-box clearfix">
    <div class="text-center"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/m_img_1.png" alt=""/></div>
    <div class="wish-words">
        <p>恰逢七夕，遥想鹊桥横跨银河，牛郎在这头织女在那头，等他俩重逢，估计连说句话的时间都没了。但是咱们之间的距离与之相比，那简直就是脸贴着脸心贴着心，想聊啥聊啥，想做啥做啥。</p>
        <p>为了庆祝咱们365天火辣甜蜜浪漫的关系，我把最想拥有的东东做成了一张D2C心愿清单，你愿意帮我完成其中任意一项嘛？如果你为了表达对我澎湃汹涌的感情，打算全包了，我也不会拒绝哦！</p>
        <p class="text-right">--爱你的 ${wish.displayName}</p>
    </div>
    <form name="wish-cart" id="wish-cart" action="/qixi/cart/add" method="post">
        <button type="submit" name="submit-button" id="submit-button" class="button blue">完成TA的心愿单</button>
        <#if wishItemList?exists && wishItemList?size gt 0>
            <div class="lazyload clearfix">
                <#list wishItemList as list>
                    <#if list.crowdItem?exists><#assign price=list.crowdItem.currentPrice><#else><#assign price=list.product.minPrice></#if>
                    <div class="wish-list clearfix" style="position:relative;">
                        <input type="hidden" name="skuSn" value="${(list.skuSn)!}"/>
                        <input type="hidden" name="crowdItemId"
                               value="<#if list.crowdItem!=null >${(list.crowdItem.id)!}<#else>0</#if>"/>
                        <div class="item-checkbox">
                            <span class="icon icon-check-sign"></span>
                        </div>
                        <div class="item-main">
                            <p class="img"><img src="${static_base}/c/images/space.gif"
                                                data-src="${picture_base}${list.product.productImageCover}!180"
                                                alt="${list.product.name}"/></p>
                            <p style="padding-bottom:0.5em;height:5em;">${list.product.name}</p>
                            <p>价格：<strong class="price">&yen; <em class="unit">${price}</em></strong>
                            </p>
                        </div>
                    </div>
                </#list>
                <#assign e=5-wishItemList?size>
                <#if e gt 0>
                    <#list 1..e as i>
                        <div class="wish-item">

                        </div>
                    </#list>

                </#if>
            </div>
        </#if>
        <button type="submit" name="submit-button" id="submit-button" class="button blue">完成TA的心愿单</button>
    </form>
</div>
<div style="margin-top:0.6em;line-height:180%;">
    <a href="/product/list?tagId=36"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072902.jpg"/></a>
    <a href="/product/list?tagId=37"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072903.jpg"/></a>
    <a href="/product/list?tagId=38"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072904.jpg"/></a>
    <a href="/product/list?tagId=39"><img alt="" style="width:100%;"
                                          src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072905.jpg"/></a>
    <a href="/product/102706"><img alt="" style="width:100%;"
                                   src="//d2c-static.b0.upaiyun.com/img/mobile/banner/14072401.jpg"/></a>
</div>
<script>
    $('#wish-cart').submit(function () {
        if ($('input[name=skuSn]:not(:disabled)').size() == 0) {
            jAlert('至少要选择一项才能买单哦！')
            return false;
        }
    });

    $('.item-checkbox').on('touchstart', function () {
        var obj = $(this).parent();
        if ($(this).attr('disabled') == null) {
            var check = $(this).find('.icon');
            var style = check.attr('class');
            if (style.indexOf('icon-check-sign') != -1) {
                check.removeClass('icon-check-sign').addClass('icon-check-empty');
                obj.find('input').attr('disabled', true);
            } else {
                check.removeClass('icon-check-empty').addClass('icon-check-sign');
                obj.find('input').removeAttr('disabled');
            }
            count_total();
        }
    });
</script>
<@m.page_footer />