<#import "templates/public_mobile.ftl" as m>
<#assign FROMAPP=renderUserAgent()/>
<@m.page_header title="D2C拼团，尖货好物一起拼" hastopfix="false" service="no" cart="has-bar"  js='utils/swiper.min' keywords=",预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${collagePromotion.name}" />
<style>
    .collage-size {
        background: #fff;
        padding-bottom: 20px;
    }

    .collage-size .title {
        padding: 10px 0 15px 10px;
        font-size: 14px;
    }

    .collage-size td {
        text-align: center;

    }

    .collage-size tr:nth-child(2n-1) {
        background: #FAF8F8;
    }

    .collage-size table {
        width: 90%;
        margin: 0 auto;
        font-size: 10px;
    }

    .collage-size table tr {
        height: 30px;
        line-height: 30px;
        font-weight: 500 !important;
    }

    .qr-box {
        width: 20em;
        height: 23.75em;
        background: #FFF;
        text-align: center;
    }

    .box-title {
        padding: 2.5em 0 .625em;
        color: #262626;
        font-weight: bold;
        line-height: 1.5;
    }

    .box-cont-tip {
        padding: 10px 0;
        line-height: 1.5;
        color: #7F7F7F;
        font-size: 12px;
    }

    .box-button {
        border: 0;
        width: 235px;
        height: 40px;
        line-height: 40px;
        background: #FD555D;
        color: #FFF;
    }

    .qr-close {
        width: 1.875em;
        height: 1.875em;
        position: absolute;
        bottom: -40px;
        left: 50%;
        -webkit-transform: translateX(-50%);
        transform: translateX(-50%);
    }

</style>
<div class="header gradient fixed">
    <div class="header-back"></div>
    <div class="header-title"></div>
    <div class="header-btn">
        <button type="button" onclick="location.href='/cart/list'" class="button-icon button-cart"><em
                    class="bounceIn animated my-cart-num">0</em><i class="icon icon-cart-bar"></i></button>
        <button type="button" class="button-icon"><i class="icon icon-share share-button"></i></button>
    </div>
</div>
<div class="section">
    <div class="swiper-container" id="product-slide">
        <div class="swiper-wrapper" style="align-items:center;">
            <#list product.imgs as image>
                <div class="swiper-slide">
                    <a href="#"><img
                                src="<#if image?index_of('http')!=-1>${image}<#else>${picture_base}${image}!600</#if>"/></a>
                </div>
            </#list>
        </div>
        <div class="swiper-pagination"></div>
    </div>
    <div class="product-pt-banner">
        <div class="pt-price"><span style="font-size:20px">&yen;${product.collagePrice}<span
                        style="text-decoration:line-through;font-size:12px;margin-left:5px;">&yen;${product.price}</span>
        </div>
        <div class="pt-num">${collagePromotion.memberCount}人团</div>
        <#if collagePromotion.promotionStatus==1>
            <div class="count-down pt-time" data-type="split-time" data-endtime="${collagePromotion.endDate}"
                 data-musttime="must">距离活动结束 &nbsp;<span class="hour down-time">00</span>:<span
                        class="minute down-time">00</span>:<span class="second down-time">00</span></div>
        <#elseif collagePromotion.promotionStatus==0>
            <div class="count-down pt-time" data-type="split-time" data-endtime="${collagePromotion.beginDate}"
                 data-musttime="must">距离活动开始 &nbsp;<span class="hour down-time">00</span>:<span
                        class="minute down-time">00</span>:<span class="second down-time">00</span></div>
        <#else>
            <div class="pt-time">活动已结束</div>
        </#if>
    </div>
    <div class="product-base">
        <h1 class="title">${product.name}</h1>
    </div>
    <div style="margin-top:-0.8em">
        <@m.app_download_bar fixed="false" />
    </div>
    <#if m.LOGINMEMBER.partnerId?exists &&m.LOGINMEMBER.partnerId!=0 && product.secondRatio gt 0>
        <div class="share-recommender">
            <div class="display-flex space-between">
                <div class="share-income">
                    分享最多可赚 <#if productPromotion?exists && (!productPromotion.promotionOver || !productPromotion.flashPromotionOver)>
                        ${(productPromotion.currentPrice)*(product.secondRatio)*(product.grossRatio)}
                    <#else>${(product.minPrice)*(product.secondRatio)*(product.grossRatio)}</#if>元
                </div>
                <div class="share-tool display-flex flex-end">
                    <div><a href="/product/detail/${product.id}?type=summary"><i
                                    class="icon icon-package"></i><span>素材</span></a></div>
                    <!-- <div><i class="icon icon-qrcode"></i></i><span>二维码</span></div> -->
                    <div class="share-button"><i class="icon icon-share-re"></i><span>分享</span></div>
                </div>
            </div>
        </div>
    </#if>
    <div style="background:#fff;padding:15px;">
        <div class="other-team">
            <p class="other-title">以下拼团可以直接参加</p>
            <#if groupList?size gt 0>
                <#list groupList as lists>
                    <div class="other-team-item">
                        <div class="user-infot" style="white-space:nowrap;">
                            <figure>
                                <#if lists.headPic>
                                    <img src="<#if lists.headPic?index_of('http') lt 0 || lists.headPic?index_of('https') lt 0>//img.d2c.cn</#if>${lists.headPic}"
                                         style="width:100%"></img>
                                <#else>
                                    <img src="//static.d2c.cn/img/home/160627/images/headpic.png"
                                         style="width:100%"></img>
                                </#if>
                            </figure>
                            <span style="max-width:70px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;display:inline-block;vertical-align:middle">${lists.initiatorName}</span>
                        </div>
                        <div class="other-staute" style="font-size:12px;">
                            还差<span style="color:red;font-size:12px;">${lists.memberCount-lists.currentMemberCount}</span>人成团<span
                                    class="count-down pt-time" data-type="split-time" data-endtime="${lists.endDate}"
                                    data-musttime="must" style="font-size:10px;margin-left:5px;">仅剩<span
                                        class="hour down-time">00</span>:<span class="minute down-time">00</span>:<span
                                        class="second down-time">00</span></span>
                        </div>
                        <button class="other-join" onclick="popupHtml()">去参团</button>
                    </div>

                </#list>
            <#else>
                <p>暂无相关团哟~</p>
            </#if>
        </div>
    </div>
    <div class="product-promise" style="background:#fff;border:none;margin-top:10px;">
        <span style="color:#222;font-size:14px;">拼团规则</span>
        <span style="color:#999">邀请好友</span>
        <span style="color:#999">成团后发货</span>
        <span style="color:#999">拼团失败后自动退款</span>
    </div>
</div>
<div class="section">
    <div class="collage-size">
        <div class="title">商品库存</div>
        <table class="c-title">
            <#if skuList?size gt 1>
                <tr>
                    <th></th>
                    <#assign i=0 />
                    <#list skuList as sku>
                        <#if sku_index gt 0>
                            <#if skuList[sku_index].size== skuList[sku_index-1].size>
                                <#assign type='size' />
                            <#else>
                                <#assign type='color' />
                            </#if>
                        </#if>

                        <#if type="size">
                            <#if  skuList[sku_index].size != skuList[sku_index-1].size>
                                <#assign i=i+1 />
                            </#if>
                        <#else>
                            <#if  skuList[sku_index].color != skuList[sku_index-1].color>
                                <#assign i=i+1 />
                            </#if>
                        </#if>
                    </#list>
                    <#list skuList as sku>

                        <#if type="size">
                            <#if  skuList[sku_index].size != skuList[sku_index-1].size>
                                <th>${sku.size}</th>
                            </#if>
                        <#else>
                            <#if  skuList[sku_index].color != skuList[sku_index-1].color>
                                <th>${sku.color}</th>
                            </#if>
                        </#if>
                    </#list>
                </tr>
                <#assign y=skuList?size/i />
                <#list skuList as sku>
                    <#if  sku_index lt y>
                        <tr>
                            <td>
                                <#if type="size">${sku.color}<#else>${sku.size}</#if>
                            </td>
                            <#list skuList as sku>

                                <#if type="size">
                                    <#if  skuList[sku_index].size != skuList[sku_index-1].size>
                                        <td>
                                            <#if sku.flashStore-sku.freezeStore gt 0> ${sku.flashStore-sku.freezeStore}<#else>0</#if>
                                        </td>
                                    </#if>
                                <#else>
                                    <#if  skuList[sku_index].color != skuList[sku_index-1].color>
                                        <td>
                                            <#if sku.flashStore-sku.freezeStore gt 0> ${sku.flashStore-sku.freezeStore}<#else>0</#if>
                                        </td>
                                    </#if>
                                </#if>
                            </#list>
                        </tr>
                    </#if>
                </#list>
            <#elseif skuList?size == 1>
                <tr>
                    <th>${skuList[0].size}</th>
                </tr>
                <tr>
                    <td>${skuList[0].color}</td>
                </tr>
            </#if>
        </table>
    </div>
    <div id="product-detail"></div>
    <div class="layer-buy">
        <div class="suspend-bar" style="overflow:hidden;padding:3px">
            <button type="button" class="button-icon" style="width:12%;float:left;" onclick="ysf.open()"><i
                        class="icon icon-assistant"></i>客服
            </button>
            <button type="button" class="button-icon" style="width:12%;float:left;"
                    onclick="location.href='/collage/list'"><i class="icon"
                                                               style="background:url(http://static.d2c.cn/other/icon_pintuanshangcheng.png);background-size:cover;"></i>商城
            </button>
            <#if product.mark==0>
                <button type="button" class="button disabled" disabled
                        style="width:60%;float:right;margin-right:5%;background:#999;color:#FFF;border:none;">商品已下架
                </button>

            <#else>
                <#if collagePromotion.promotionStatus==1>
                    <button type="button" data-type="buy" onclick="location.href='/product/${product.id}';"
                            class="operator-button button"
                            style="width:34%;margin-left:2%;float:right;margin-right:2%;background:#000;color:#FFF;border:none;line-height:20px;font-size:10px;">
                        &yen;${product.minPrice}</br>单独购买
                    </button>
                    <button type="button" data-type="cart" onclick="popupHtml()" class="operator-button button"
                            style="width:34%;float:right;background:#fd555d;border:none;line-height:20px;font-size:10px;">
                        &yen;${product.collagePrice}</br>${collagePromotion.memberCount}人团
                    </button>
                <#elseif collagePromotion.promotionStatus==0>
                    <button type="button"" class="operator-button button" style="width:60%;float:right;margin-right:5%;background:#999;color:#FFF;border:none;">暂未开始</button>
                <#else>
                    <button type="button"" class="operator-button button" style="width:60%;float:right;margin-right:5%;background:#999;color:#FFF;border:none;">活动结束</button>
                </#if>
            </#if>
        </div>
    </div>
</div>
<script>
    $(function () {
        $.get('/product/detail/${product.id}?type=ajax', function (data) {
            $('#product-detail').html(data);
        });
    })


</script>

<@m.page_footer  />