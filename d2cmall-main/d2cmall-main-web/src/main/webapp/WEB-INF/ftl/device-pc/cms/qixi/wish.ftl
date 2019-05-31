<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${wish.displayName}的D2C心愿单' css="qixi" keywords="全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<@m.top_nav />
<div style="width: 100%; height:1038px;position: relative; overflow: hidden;">
    <div style="left: 50%; width: 1920px; margin-left: -960px; position: absolute;">
        <table cellspacing="0" cellpadding="0">
            <tbody>
            <tr>
                <td><img width="360" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_01.jpg"/></td>
                <td><img width="1200" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_02.jpg"/></td>
                <td><img width="360" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_03.jpg"/></td>
            </tr>
            <tr>
                <td><img width="360" height="245" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_04.jpg"/></td>
                <td><img width="1200" height="245" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_05.jpg"/></td>
                <td><img width="360" height="245" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_06.jpg"/></td>
            </tr>
            <tr>
                <td style="position:relative;"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_l.jpg"
                                                    alt="" style="position:absolute;right:0;bottom:-306px;"/><img
                            width="360" height="244" alt=""
                            src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_07.jpg"/></td>
                <td><img width="1200" height="244" alt=""
                         src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_08.jpg"/></td>
                <td style="position:relative;"><img src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_r.jpg"
                                                    alt="" style="position:absolute;left:0;bottom:-147px;"/><img
                            width="360" height="244" alt=""
                            src="//d2c-static.b0.upaiyun.com/img/active/140802/banner_09.jpg"/></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<div id="qixi" class="w-1200 lazyload" style="margin-top:-305px;">
    <div class="wish-list clearfix" style="margin:0;position:relative;">
        <div class="box-corner-l"></div>
        <div class="box-corner-r"></div>
        <div class="wish-product" id="wish-product">
            <#if wishItemList?exists && wishItemList?size gt 0>
                <#list wishItemList as list>
                    <div class="wish-item<#if list_index==0> large</#if>" data-id="${list.id}">
                        <a href="/product/${list.product.id}?skuId=${list.skuId}" target="_blank"><img
                                    src="/static/c/images/space.gif"
                                    data-image="${picture_base}${list.product.productImageCover}<#if list_index==0>!media<#else>!180</#if>"
                                    alt="${list.product.name}"/></a>
                    </div>
                </#list>
                <#assign e=5-wishItemList?size>
                <#if e gt 0>
                    <#list 1..e as i>
                        <div class="wish-item">

                        </div>
                    </#list>
                </#if>
            <#else>
                <div class="wish-item large">
                </div>
                <div class="wish-item">
                </div>
                <div class="wish-item">
                </div>
                <div class="wish-item">
                </div>
                <div class="wish-item">
                </div>
            </#if>
        </div>

        <div class="wish-text">
            <form name="wish-cart" id="wish-cart" action="/qixi/cart/add" method="post">
                <div style="margin:10px 0;height:50px;overflow:hidden;"><img
                            src="//d2c-static.b0.upaiyun.com/img/active/140802/img_1.png" alt=""/></div>
                <div class="wish-word">
                    <p>恰逢七夕，遥想鹊桥横跨银河，牛郎在这头织女在那头，等他俩重逢，估计连说句话的时间都没了。但是咱们之间的距离与之相比，那简直就是脸贴着脸心贴着心，想聊啥聊啥，想做啥做啥。</p>
                    <p>为了庆祝咱们365天火辣甜蜜浪漫的关系，我把最想拥有的东东做成了一张D2C心愿清单，你愿意帮我完成其中任意一项嘛？如果你为了表达对我澎湃汹涌的感情，打算全包了，我也不会拒绝哦！</p>
                    <p class="text-right">--爱你的 ${wish.displayName}</p>
                </div>
                <div class="wish-product-title">
                    <#if wishItemList?exists && wishItemList?size gt 0>
                        <#list wishItemList as list>
                            <#if list.crowdItem?exists><#assign price=list.crowdItem.currentPrice><#else><#assign price=list.product.minPrice></#if>
                            <p>
                                <label class="title"><input type="checkbox" name="sn" class="sku" value=""
                                                            data-id="${list.id}" checked
                                                            style="margin:0 10px 0 0;"/> ${list.product.name}</label>
                                <span class="price" data-price="${price}">&yen; ${price}</span>
                                <input type="hidden" name="skuSn" value="${(list.skuSn)!}"/>
                                <input type="hidden" name="crowdItemId"
                                       value="<#if list.crowdItem!=null >${(list.crowdItem.id)!}<#else>0</#if>"/>
                            </p>
                        </#list>
                    <#else>
                        <div class="text-center">
                            啊，TA分享了一个空的心愿清单给你。
                        </div>
                    </#if>
                </div>

                <div class="wish-buy">
                    <input type="hidden" name="partnerId" value="${wishItemList[0].memberId}"/>
                    <button type="submit" name="">满足TA的心愿</button>
                </div>
            </form>
        </div>
    </div>
    <div class="topic-list" style="margin-top:30px;">
        <a href="" target="_blank"><img src="/static/c/images/space.gif"
                                        data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_1.jpg"
                                        height="394" alt=""/></a>
        <img src="/static/c/images/space.gif" data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_text.jpg"
             height="98" alt=""/>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_2.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_3.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_4.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_5.jpg"
                                                                    height="400" alt=""/></a>
        <a href="" target="_blank" style="margin-bottom:10px;"><img src="/static/c/images/space.gif"
                                                                    data-image="//d2c-static.b0.upaiyun.com/img/active/140802/topic_6.jpg"
                                                                    height="400" alt=""/></a>
    </div>

</div>
<script>
    $('.sku').click(function () {
        var id = $(this).attr('data-id');
        if ($(this).attr('checked') == 'checked') {
            $(this).parent().siblings('input').removeAttr('disabled');
        } else {
            $(this).parent().siblings('input').attr('disabled', 'disabled');
        }
    });
    $('#wish-cart').submit(function () {
        if ($('.sku:checked').size() == 0) {
            jAlert('至少要选择一项才能买单哦！')
            return false;
        }
    });
    with (document) 0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = '//bdimg.share.baidu.com/static/api/js/share.js?cdnversion=' + ~(-new Date() / 36e5)];
</script>
<@m.page_footer />