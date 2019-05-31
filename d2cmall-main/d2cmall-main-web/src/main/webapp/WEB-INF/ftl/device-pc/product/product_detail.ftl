<#import "templates/public_pc.ftl" as m>
<#assign LOGINMEMBER=loginMember()/>
<@m.page_header js='utils/jquery.zoom|utils/jquery.datepicker' title='${product.name} - ${(designer.name)!}' keywords="${(designer.name)!},${product.name},预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="${(designer.name)!},${product.name},消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<#assign channel='showroom'>
<@m.top_nav channel='${channel}' />
<style>
    .modal-inner .modal-title {
        display: none;
    }

    .modal-inner .modal-content {
        margin: 0;
        padding: 0;
        color: #000;
    }

    .modal-outer {
        border: 0;
    }

    .modal-inner {
        padding: 0;
    }

    .modal-mask {
        opacity: .5 !important;
    }

    .qr-box {
        width: 640px;
        height: 400px;
        text-align: center;
    }

    .box-title {
        background: #FD555D;
        color: #FFF;
        font-size: 16px;
        font-weight: bold;
        height: 68px;
        line-height: 68px;
    }

    .box-cont {
        width: 100%;
        height: 332px;
        padding: 75px 115px 0;
        box-sizing: border-box;
        display: flex;
        justify-content: space-between;
    }

    .box-cont-item .cont-item-tip {
        padding-top: 10px;
        color: #7F7F7F;
        font-size: 12px;
    }

    .qr-close {
        width: 30px;
        height: 30px;
        position: absolute;
        bottom: -100px;
        left: 50%;
        transform: translateX(-50%);
        z-index: 80;
        cursor: pointer;
    }
</style>
<div class="layout layout-response layout-product clearfix">
    <div class="product-base clearfix">
        <div class="product-img">
            <div class="img-thumbs">
                <#list product.productImageList as image>
                    <a href="javascript:">
                    <#if image?index_of('http')!=-1>
                        <img src="${image}" data-image="${image}!media" ori-image="${image}"/></a>
                    <#else>
                        <img src="${picture_base}/${image}!120" data-image="${picture_base}/${image}!media"
                             ori-image="${picture_base}/${image}"/></a>
                    </#if>
                </#list>
                <#if product.video><a href="javascript:" class="video-play"data-video="${picture_base}${product.video}">
                    <span></span><img src="${picture_base}/${image}!120"
                                      data-image="${picture_base}/${product.productImageList[0]}!media"
                                      ori-image="${picture_base}/${product.productImageList[0]}"/></a></#if>
            </div>
            <div class="img-media" style="display:table;height:790px;">
                <div class="middle-image" style="display:table-cell;vertical-align:middle;">
                    <img src="${static_base}/c/images/space.gif" id="product-image-middle" data-image="" data-id="0"
                         data-picStyle="${product.picStyle}" data-productType="${product.productTradeType}"
                         data-video="<#if product.video>true<#else>false</#if>" alt=""/>
                </div>
                <div class="video-play-layer"></div>
                <#if LOGINMEMBER.distributorId!=null><a id="download-pic" href="javascript:"
                                                        style="position:absolute;display:block;bottom:0;right:0;background:#000;color:#FFF;padding:6px 10px;">下载图片</a></#if>
            </div>
        </div>
        <div class="product-info product-normal">
            <div class="product-navi">
                <a id="type-c" style="display:none"
                   data-type="<#if product.salesproperty&& (product.salesproperty?eval.sp2)?size gt 0 >Hassistant<#else>Nassistant</#if>"> </a>
                <div class="view-in-mobile">
                    <span>手机上购买</span>
                    <img src="/picture/code?type=1&width=120&height=120&noLogo=true&&code=https://m.d2cmall.com/product/${product.id}"/>
                </div>
                <a target="_blank"
                   href="/product/list?t=${(product.topCategory?eval.id)!}">${(product.topCategory?eval.name)!}</a>
                &nbsp;>&nbsp;
                <#list product.productCategory?eval as item>
                    <a target="_blank" href="/product/list?c=${item.id}">${item.name}</a>
                    <#if item_index lt (product.productCategory?eval)?size-1>
                        &nbsp;>&nbsp;
                    </#if>
                </#list>
            </div>
            <#if designer.headPic?exists>
                <div class="product-brand"><a href="${base}/showroom/designer/${(designer.id)!}" target="_blank"
                                              title="点击打开设计师主页">${(designer.name)!}</a> &nbsp; <i
                        class="fa fa-caret-right"></i></div></#if>
            <div><h1 class="product-title">
                    ${product.name}</h1>
                <br/>
                <#if productPromotion?exists>
                    <#if flashPromotion?exists && !productPromotion.flashPromotionOver>
                        【
                        <div class="product-promotion" style="display:inline-block;color:red"><a
                                    href="/flashpromotion/product/${flashPromotion.id}">${flashPromotion.name}</a>
                        </div>】
                    <#elseif !productPromotion.promotionOver>
                        【
                        <div class="product-promotion" style="display:inline-block;color:red"><a
                                    href="/promotion/${productPromotion.promotionId}">${productPromotion.promotionName}</a>
                        </div>】
                    </#if>
                </#if>
                <#if (orderPromotions?exists && orderPromotions?size gt 0)>
                    满减：
                    <#list orderPromotions as orderPromotion>
                        【
                        <div class="product-promotion" style="display:inline-block;color:red"><a
                                    href="/promotion/${orderPromotion.id}">${orderPromotion.name}</a></div>】
                    </#list>
                </#if>
            </div>
            <#if product.subTitle?exists && product.subTitle>
                <h2 class="product-subtitle">${product.subTitle}</h2>
            </#if>
            <div class="product-prices clearfix">
                <#if (productPromotion?exists && (!productPromotion.promotionOver || !productPromotion.flashPromotionOver))>
                    <span class="product-price-current">&yen; <strong
                                class="current-price">${(productPromotion.currentPrice?string("currency")?substring(1))!}</strong></span>
                    &nbsp;
                    <span class="product-price-original">吊牌价  <s>&yen;${product.originalPrice?string("currency")?substring(1)}</s></span>
                    &nbsp;
                <#elseif (product.minPrice?exists && product.originalPrice?exists&& product.minPrice!=product.originalPrice)>
                    <span class="product-price-current">&yen; <strong
                                class="current-price">${(product.minPrice?string("currency")?substring(1))!}</strong></span>
                    &nbsp;
                    <#if product.originalPrice gt product.minPrice><span class="product-price-original">
                        吊牌价  <s>&yen;${(product.originalPrice?string("currency")?substring(1))!}</s></span></#if>
                <#elseif (product.minPrice?exists)>
                    <span class="product-price-current">&yen; <strong
                                class="current-price">${(product.minPrice?string("currency")?substring(1))!}</strong></span>
                <#else>
                    <span class="product-price-current">&yen; <strong
                                class="current-price">${(product.originalPrice?string("currency")?substring(1))!}</strong></span>
                </#if>
                <span class="product-price-original">货号：${product.inernalSn}</span>
            </div>
            <#if product.productTradeType == 'CROSS'>
                <div style="color:#7F7F7F;font-size:12px;border-top: 1px dotted #CCC;padding:10px 0;"><#if product.taxation == 1>本商品税费由商家承担<#else>预计税费：￥${(product.minPrice)*(product.taxPrice?number)} 实际税费请以提交订单时为准</#if></div>
            </#if>
            <#if productPromotion?exists>
                <#if flashPromotion?exists && !productPromotion.flashPromotionOver>
                    <#if flashPromotion.statusName=='即将开始'>
                        <div class="crowd-count-down">
                            <i class="fa fa-clock-o"></i><span class="count-down"
                                                               data-endTime="${productPromotion.flashStartDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                               data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                                               data-type="split-time" data-musttime="must">
	              距D2C快抢开始&nbsp;<span class="hour down-time">00</span>:<span class="minute down-time">00</span>:<span
                                        class="second down-time">00</span>
	                </span>
                        </div>
                    <#else>
                        <div class="crowd-count-down">
                            <i class="fa fa-clock-o"></i> <span class="count-down"
                                                                data-endTime="${productPromotion.flashEndDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                                data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                                                data-type="split-time" data-musttime="must">
	                距D2C快抢结束&nbsp;<span class="hour down-time">00</span>:<span class="minute down-time">00</span>:<span
                                        class="second down-time">00</span>
	                </span>
                        </div>
                    </#if>
                <#elseif !productPromotion.promotionOver>
                    <div class="crowd-count-down">
                        <i class="fa fa-clock-o"></i> <span class="count-down"
                                                            data-startTime="${productPromotion.startDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                            data-endTime="${productPromotion.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                            data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
                    </div>
                </#if>
            </#if>
            <form name="form-buy" id="form-buy" action="/order/buynow" method="post">
                <div class="product-sku">
                    <div class="img clearfix">
                        <#if product.salesproperty>
                            <div class="value">
                                <label class="main-font">${product.salesproperty?eval.sp1[0].name}</label>
                                <#list product.salesproperty?eval.sp1 as p>
                                    <a href="javascript:" class="color-section c-${p.id}" data-type="color"
                                       data-id="${p.id}" rel="${p.value}" title="${p.value}">
                                        <#if p.img!=''>
                                            <span class="img">
                        		<#if p.img?index_of('http')!=-1>
                                    <img src="${p.img}" data-image="${p.img}" ori-image="${p.img}" alt=""/>
                        		<#else>
                                    <img src="${picture_base}/${p.img}!50" data-image="${picture_base}/${p.img}!media"
                                         ori-image="${picture_base}/${p.img}" alt=""/>
                                </#if>
                        	</span>
                                        </#if>
                                        <i>已选择</i>
                                    </a>
                                </#list>
                            </div>
                        </#if>
                    </div>
                    <#if product.salesproperty && ((product.salesproperty?eval.sp2)?size>0)>
                        <div class="txt clearfix sec-font">
                            <label>${product.salesproperty?eval.sp2[0].name}</label>
                            <div class="value">
                                <#list product.salesproperty?eval.sp2 as p>
                                    <a href="javascript:" class="size-section s-${p.id}" data-type="size"
                                       data-id="${p.id}" rel="${p.value}"><span>${p.value}</span><i>已选择</i></a>
                                </#list>
                            </div>
                        </div>
                    </#if>
                    <div class="txt clearfix">
                        <label>数量</label>
                        <div class="value">
                            <em class="data-crease decrease" unselectable="on" onselectstart="return false;">-</em>
                            <input type="text" name="num" value="1" size="1" maxlength="3" class="data-num"/>
                            <em class="data-crease increase" unselectable="on" onselectstart="return false;">+</em>
                            <input type="hidden" class="store" value=""/>
                            <span class="store-span"></span>
                        </div>
                    </div>
                </div>
                <div class="product-tip">
                    <span class="product-select-tip"></span>
                    <#if product.remark>
                        &nbsp;&nbsp;&nbsp;<span class="product-remark">${product.remark}</span>
                    </#if>
                    &nbsp;&nbsp;&nbsp;<#if product.after==0><span class="red"
                                                                  style="font-weight:blod;">【本商品不支持七天无理由退换货】</span></#if>
                </div>
                <#if product.status==3>
                    <div class="product-alert">
                        <div>已售罄。留下你的手机号或邮箱，系统会在到货的时候通知您</div>
                        <input type="text" name="mobilemail"
                               value="<#if LOGINMEMBER.id!=null><#if (LOGINMEMBER.mobile)!=null>${LOGINMEMBER.mobile!}<#elseif LOGINMEMBER.email?exists>${LOGINMEMBER.email!}</#if></#if>"
                               placeholder="输入手机号或邮箱" class="input remind-param mobile"/>
                        <input type="hidden" name="name" class="remind-param" value="${product.name}"/>
                        <input type="hidden" name="productId" class="remind-param" value="${product.id}"/>
                        <input type="hidden" name="type" class="remind-param" value="ARRIVAL"/>
                        <button type="button" name="remind-btn" class="button button-green remind-send">到货提醒</button>
                    </div>
                <#else>
                    <div class="clearfix" style="margin:3px 10%;position:relative;">
                        <input type="hidden" name="skuId" value=""/>
                        <input type="hidden" name="productId" value="${product.id}"/>
                        <#if product.mark lt 1>
                            <button type="button" class="button button-l disabled" style="width:90%;" disabled>已下架
                            </button>
                        <#elseif store lte 0>
                            <button type="button" class="button button-l disabled" style="width:90%;" disabled>已售空
                            </button>
                        <#else>
                            <#if designer.subscribe==1 &&product.subscribe == 1>
                                <#assign width='30%' />
                            <#else>
                                <#assign width='44%' />
                            </#if>
                            <button type="button" class="product-operator-button first gobuynow" data-form="top"
                                    data-type="buy" style="width:${width};"
                                    data-productType="${product.productTradeType}">立即购买
                            </button>
                            <#if product.cart==1>
                                <button type="button" class="gobuynow product-operator-button second" data-form="top"
                                        data-type="cart" style="width:${width};"
                                        data-productType="${product.productTradeType}">加入购物车</button></#if>
                            <#if designer.subscribe==1 && product.subscribe == 1>
                                <button type="button" class="product-operator-button third gobuynow" data-form="top"
                                        data-type="fitting" style="width:36%;">免费门店试衣
                                </button>
                                <div class="fit-tip">免费预约新款到D2C实体店，免费试穿体验</div>
                            </#if>
                        </#if>
                    </div>
                </#if>
            </form>
            <#if designer.headPic?exists>
                <div class="product-attr">
                    <div class="tab product-attr-tab clearfix">
                        <ul>
                            <#if designer.headPic?exists>
                                <li data-id="1">设计师|品牌</li></#if>
                        </ul>
                    </div>
                    <div class="tab-cont product-attr-cont">
                        <#if designer.headPic?exists>
                            <div data-id="1" class="display-none" style="line-height:150%;">
                                <a href="${base}/showroom/designer/${(designer.id)!}" target="_blank" title="点击打开设计师主页"
                                   class="designer-logo"><img src="${picture_base}${(designer.headPic)!}!120"
                                                              alt="${(designer.name)!}"/></a>
                                <#assign designerIntro=designer.intro?replace("<.*?>","","r") />
                                <#if designerIntro?length gt 200>${(designerIntro?substring(0,200))!}...<a
                                        href="${base}/showroom/designer/${(designer.id)!}" target="_blank"
                                        class="more-info">更多>></a><#else>${designerIntro}</#if>
                            </div>
                        </#if>
                    </div>
                    <#if designer.headPic?exists>
                        <div class="designer-detail">
                            <a href="javascript:ysf.open();" class="CustomerCare"
                               style="margin-right:20px;cursor:pointer"><span style="margin-right:5px"></span>联系客服</a><a
                                    class="shop" style="cursor:pointer"
                                    href="${base}/showroom/designer/${(designer.id)!}" target="_blank"><span
                                        style="background-position:-267px -589px"></span>进入店铺</a>
                        </div>
                    </#if>
                </div>
            </#if>
            <div class="product-others">
                <div class="share-box" data-config="{'pic':'${picture_base}/${product.productImageList[0]}'}">
                    <span class="float-left" style="margin-top:2px;line-height:31px;"> 分享：</span>
                    <a class="share weixin" data-cmd="weixin"></a>
                    <a class="share qq" data-cmd="sqq"></a>
                    <a class="share qzone" data-cmd="qzone"></a>
                    <a class="share weibo" data-cmd="tsina"></a>
                    <a class="share douban" data-cmd="douban"></a>
                </div>
                <div class="product-other-operator">
                    <a href="javascript:" id="collection-${product.id}"
                       data-url="/member/interest/collection/insert/${product.id}" success-tip="收藏成功！"
                       call-back="collectsu" class="ajax-request" style="padding-left: 35px;padding-top: 5px;"> <i
                                class="collection <#if product.collectioned==1>hascollect</#if>"></i>
                        <span class="detailcollect"><#if product.collectioned==1>已收藏<#else>收藏</#if></span></a>&nbsp;&nbsp;
                </div>
            </div>
        </div>
        <div class="product-relate hide-two">
            <span>猜你喜欢</span>
            <#if recommendProducts?exists && recommendProducts?size gt 0>
                <#list recommendProducts as items>
                    <#if items_index lt 5>
                        <div class="relate-item">
                            <p class="relate-item-pic"><a href="${base}/product/${items.id}" target="_blank"><img
                                            src="<#if items.productImageCover?index_of('http') != -1>${items.productImageCover}<#else>${picture_base}/${items.productImageCover}!120</#if>"
                                            alt="${items.name}"/></a></p>
                            <p class="price">&yen;${items.minPrice?string('currency')?substring(1)}</p>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
    </div>
    <#if relationProducts?exists && relationProducts?size gt 0>
        <div class="product-pairs">
            <form action="" method="post">
                <table class="table table-lightgrey">
                    <tr class="title text-center">
                        <td width="5"><input type="checkbox" name="checkall" class="check-all" value="" checked
                                             class="checkbox" style="margin-top:3px;"/></td>
                        <td>相关搭配</td>
                        <td width="100">颜色</td>
                        <td width="100">尺码</td>
                        <td width="120">数量</td>
                        <td width="130">单价</td>
                    </tr>
                    <#list relationProducts as product>
                        <tr class="item">
                            <td class="text-center"><input type="checkbox" name="" value="" checked
                                                           class="checkbox"/><input type="hidden" name="productId"
                                                                                    value="${product.id}"/>
                                <input type="hidden" name="skuId" value=""/></td>
                            <td>
                                <p class="img"><a href="${base}/product/${product.id}" target="_blank"
                                                  title="${product.name}"><img
                                                src="${picture_base}/${product.productImageCover}!120" class="pair-img"
                                                width="50" alt="${product.name}"/></a>
                                <p>
                                <p class="category">${(product.topCategory?eval.name)!} >
                                    <#list product.productCategory?eval as item>
                                        ${item.name}
                                        <#if item_index lt (product.productCategory?eval)?size-1>
                                            >
                                        </#if>
                                    </#list>
                                </p>
                                <p class="title"><a href="${base}/product/${product.id}"
                                                    target="_blank">${product.name}</a></p>

                            </td>
                            <td class="text-center">
                                <select name="color" class="select-sku input" style="width:100%" data-type="color">
                                    <option value="">请选择</option>
                                    <#if product.salesproperty>
                                        <#list product.salesproperty?eval.sp1 as p>
                                            <option rel="<#if p.img!=''>${picture_base}/${p.img}!120</#if>"
                                                    value="${p.id}">${p.value}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </td>
                            <td class="text-center">
                                <select name="size" class="select-sku input" style="width:100%" data-type="size">
                                    <option value="">请选择</option>
                                    <#if product.salesproperty>
                                        <#list product.salesproperty?eval.sp2 as p>
                                            <option value="${p.id}">${p.value}</option>
                                        </#list>
                                    </#if>
                                </select>
                            </td>
                            <td class="text-center"><input type="hidden" class="store" value="0"/>
                                <em class="data-crease decrease" unselectable="on" onselectstart="return false;">-</em>
                                <input type="text" name="quantity" value="1" size="1" maxlength="3" readonly
                                       class="data-num"/>
                                <em class="data-crease increase" unselectable="on" onselectstart="return false;">+</em>
                            </td>
                            <td class="text-center sku-price">&yen;
                                <span>${product.minPrice?string("currency")?substring(1)}</span></td>
                        </tr>
                    </#list>
                </table>
                <div class="text-right" style="margin-top:10px;">
                    <button type="submit" class="button button-l button-red multi-add-cart">加入到购物车</button>
                </div>
            </form>
        </div>
    </#if>
    <div id="product-detail" style="height:0;"></div>
    <div class="product-cont-bar scroll-suspend" data-offset="50" data-scroll-holder="product-suspend-holder"
         data-scroll-end="product-hot" data-scroll-bg="product-cont-tab-bar">
        <div class="tab clearfix">
            <ul>
                <li class="on" data-id="desc">商品详情</li>
                <li data-id="comment" data-url="/comment/product/${product.id}">客户评价 <span>(${countComment})</span></li>
                <li data-id="consult" data-url="/consult/product/${product.id}">商品咨询 <span>(${countConsult})</span></li>
            </ul>
        </div>
        <#if product.cart==1>
            <div class="cart-view">

                <span>手机上购买</span>
                <img src="/picture/code?type=1&width=120&height=120&noLogo=true&&code=//m.d2cmall.com/product/${product.id}"/>
            </div>
        </#if>
    </div>
    <div id="product-suspend-holder" class="product-suspend-holder"></div>
    <div class="product-cont-tab-cont">
        <div class="product-detail-right">
            <div class="product-info-suspend scroll-suspend" data-offset="70" data-scroll-end="product-hot">
                <h1 class="product-title">${product.name}</h1>
                <div class="product-prices clearfix">
                    <#if (productPromotion?exists && (!productPromotion.promotionOver || !productPromotion.flashPromotionOver))>
                        <span class="product-price-current">&yen; <strong
                                    class="current-price">${productPromotion.currentPrice?string("currency")?substring(1)}</strong></span>
                        &nbsp;
                        <span class="product-price-original">吊牌价  <s>&yen;${product.originalPrice?string("currency")?substring(1)}</s></span>
                        &nbsp;
                    <#elseif product.minPrice==product.originalPrice>
                        <span class="product-price-current">&yen; <strong
                                    class="current-price">${product.minPrice?string("currency")?substring(1)}</strong></span>
                    <#else>
                        <span class="product-price-current">&yen; <strong
                                    class="current-price">${product.minPrice?string("currency")?substring(1)}</strong></span>
                        &nbsp;
                        <#if product.minPrice lt product.originalPrice><span class="product-price-original">吊牌价  <s>&yen;${product.originalPrice?string("currency")?substring(1)}</s>
                            </span></#if>
                        &nbsp;
                    </#if>
                </div>
                <#if productPromotion?exists>
                    <#if !productPromotion.flashPromotionOver>
                        <div class="crowd-count-down">
                            <i class="fa fa-clock-o"></i> <span class="count-down" data-type="toend"
                                                                data-startTime="${productPromotion.flashStartDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                                data-endTime="${productPromotion.flashEndDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                                data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
                        </div>
                    <#elseif !productPromotion.promotionOver>
                        <div class="crowd-count-down">
                            <i class="fa fa-clock-o"></i> <span class="count-down" data-type="toend"
                                                                data-startTime="${productPromotion.startDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                                data-endTime="${productPromotion.endDate?string("yyyy/MM/dd HH:mm:ss")}"
                                                                data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
                        </div>
                    </#if>
                </#if>
                <form name="form-buy-small" id="form-buy-small" action="/order/buynow" method="post">
                    <div class="product-sku">
                        <div class="img clearfix">
                            <#if product.salesproperty>
                                <label>${product.salesproperty?eval.sp1[0].name}</label>
                                <div class="value">
                                    <#list product.salesproperty?eval.sp1 as p>
                                        <a href="javascript:" class="color-section c-${p.id}" data-type="color"
                                           data-id="${p.id}" rel="${p.value}" title="${p.value}">
                                            <#if p.img>
                                                <span class="img">
	                        		<#if p.img?index_of('http')!=-1>
                                        <img src="${p.img}" data-image="${p.img}" ori-image="${p.img}"
                                             style="width:50px;height:50px;" alt=""/>
	                        		<#else>
                                        <img src="${picture_base}/${p.img}!50"
                                             data-image="${picture_base}/${p.img}!media"
                                             ori-image="${picture_base}/${p.img}" style="width:50px;height:50px;"
                                             alt=""/>
                                    </#if>
	                        	</span>
                                            </#if>
                                            <i>已选择</i>
                                        </a>
                                    </#list>
                                </div>
                            </#if>
                        </div>
                        <#if product.salesproperty && ((product.salesproperty?eval.sp2)?size>0)>
                            <div class="txt clearfix">
                                <label>${product.salesproperty?eval.sp2[0].name}</label>
                                <div class="value">
                                    <#list product.salesproperty?eval.sp2 as p>
                                        <a href="javascript:" class="size-section s-${p.id}" data-type="size"
                                           data-id="${p.id}" rel="${p.value}"><span>${p.value}</span><i>已选择</i></a>
                                    </#list>
                                </div>
                            </div>
                        </#if>
                        <div class="txt clearfix">
                            <label>数量</label>
                            <div class="value">
                                <em class="data-crease decrease" unselectable="on" onselectstart="return false;">-</em>
                                <input type="text" name="num" value="1" size="1" maxlength="3" class="data-num"/>
                                <em class="data-crease increase" unselectable="on" onselectstart="return false;">+</em>
                                <input type="hidden" class="store" value=""/>
                                <span class="store-span"></span>
                            </div>
                        </div>
                    </div>
                    <div class="product-select-tip"></div>
                    <#if product.remark>
                        <div class="product-remark">${product.remark}</div>
                    </#if>
                    <#if product.status==3>
                        <div class="product-alert">
                            <div>已售罄。留下你的手机号，系统会在到货的时候通知您</div>
                            <input type="text" name="mobilemail"
                                   value="<#if LOGINMEMBER.id!=null><#if (LOGINMEMBER.mobile)!=null>${LOGINMEMBER.mobile!}<#elseif LOGINMEMBER.email?exists>${LOGINMEMBER.email!}</#if></#if>"
                                   placeholder="输入手机号或邮箱" class="input remind-param mobile"/>
                            <input type="hidden" name="name" class="remind-param" value="${product.name}"/>
                            <input type="hidden" name="productId" class="remind-param" value="${product.id}"/>
                            <input type="hidden" name="type" class="remind-param" value="ARRIVAL"/>
                            <button type="button" name="remind-btn" class="button button-green remind-send">到货提醒
                            </button>
                        </div>
                    <#else>
                        <div class="clearfix" style="margin-top:10px;position:relative;">
                            <input type="hidden" name="skuId" value=""/><!-- 货号 -->
                            <input type="hidden" name="productId" value="${product.id}"/>
                            <#if product.mark lt 1 >
                                <button type="button" class="button button-l disabled" style="width:90%;" disabled>已下架
                                </button>
                            <#elseif store lte 0>
                                <button type="button" class="button button-l disabled" style="width:90%;" disabled>已售空
                                </button>
                            <#else>
                                <#if designer.subscribe==1 && product.subscribe == 1>
                                    <#assign width='29%' />
                                <#else>
                                    <#assign width='44%' />
                                </#if>
                                <button type="button" class="product-operator-button first" data-form="bottom"
                                        data-type="buy" style="width:${width};"
                                        data-productType="${product.productTradeType}">立即购买
                                </button>
                                <#if product.cart==1>
                                    <button type="button" class="product-operator-button second" data-form="bottom"
                                            data-type="cart" style="width:${width};"
                                            data-productType="${product.productTradeType}">加入购物车</button></#if>
                                <#if !(crowdItem?exists)><#if designer.subscribe==1 && product.subscribe == 1>
                                    <button type="button" class="product-operator-button third gobuynow"
                                            data-form="bottom" data-type="fitting" style="width:36%;">免费门店试衣
                                    </button>
                                    <div class="fit-tip">免费预约新款到D2C实体店，免费试穿体验</div>
                                </#if>
                                </#if>
                            </#if>
                        </div>
                    </#if>
                </form>
            </div>
        </div>
        <div id="product-detail-desc" class="product-desc lazyload load-process">
            <#if product.categoryBannerPic>
                <a href="${product.categoryBannerUrl}"><img src="${picture_base}/${product.categoryBannerPic}"></a>
            </#if>
            <h4 class="product-detail-title">商品简介</h4>
            <#if product.introduction>
                <p style="color:#666;line-height:20px;">${product.introduction}</p>
            </#if>

            <#if product.attributes || product.sizeJson>

                <#if product.attributes && product.attributes!="null"&& product.attributes!="">
                    <h4 class="product-detail-title">参数信息</h4>
                    <div class="product-detail-attr">
                        <div id="product-attributes-div" data-val='${(product.attributes)!}'></div>
                    </div>
                </#if>
                <#if product.sizeJson && product.sizeJson!="null" && product.sizeJson!=''>
                    <h4 class="product-detail-title">尺码信息</h4>
                    <div id="product-size-div" data-val='${(product.sizeJson)!}'></div>
                </#if>

            </#if>
            <#if product.tryOnReportJson && product.tryOnReportJson!="null" && product.tryOnReportJson!=''>
                <h4 class="product-detail-title">试穿报告</h4>
                <div id="product-tryOnReportJson-div" data-val='${(product.tryOnReportJson)!}'></div>
            </#if>

            <#if product.description?exists && product.description!=''>
                ${product.description?replace('<img src=','<img src="${static_base}/c/images/space.gif" data-image=')}
            <#elseif product.mobileDesc?exists && product.mobileDesc!=''>
                ${product.mobileDesc}
            </#if>
        </div>
        <div id="product-detail-comment" class="product-comment display-none">
        </div>
        <div id="product-detail-consult" class="product-consult display-none">
        </div>

    </div>

    <#if hotSaleProducts ?exists && hotSaleProducts?size &gt; 0>
        <div class="product-hot clearfix" id="product-hot">
            <span>热卖商品</span>
            <#list hotSaleProducts as product>
                <div>
                    <p class="relate-item-pic"><a href="/product/${product.id}" target="_blank"><img
                                    src="<#if product.productImageCover?index_of('http')!=-1>${product.productImageCover}<#else>${picture_base}/${product.productImageCover}!280</#if>"
                                    alt="${product.name}"/></a></p>
                    <p class="relate-item-name"><a href="/product/${product.id}" target="_blank">${product.name}</a></p>
                    <p class="price">&yen; ${product.currentPrice?string('currency')?substring(1)}</p>
                </div>
            </#list>
        </div>
    </#if>
</div>
<div class="product-suspend-bar" id="product-cont-tab-bar" style="display:none;"></div>
<script id="cart-success-template" type="text/html">
    <div class="to-cart-success">
        <h3><a href="javascript:void(0)" onclick="$(this).parent().parent().parent().remove();"><i
                        class="model-close"></i></a></h3>
        <div class="cart-modal-content">
            <p style="font-size:24px;line-height:45px;color:#fd555d;font-weight:900;"><span
                        class="model-success"></span>成功加入购物车</p>
            <p class="txt-note" style="margin-top:24px;font-size:14px">购物车共{{result.datas.totalNum}}件商品，合计 &yen;{{result.datas.totalAmount}}</p>
        </div>
        <div class="cart-modal-button" style="margin-top:5px">
            <button type="button" class="button button-xl" onclick="continueShop()" style="margin-right:30px">继续购物
            </button>
            <button type="button" class="button button-xl button-red" onclick="location.href='/cart/list';">去结算</button>
        </div>
    </div>
</script>
<script>
    if ($("#product-attributes-div").size() > 0) {
        var attributes_val = eval("(" + $("#product-attributes-div").attr("data-val") + ")");
        var tab = "<table class='table table-grey' border='0' cellspacing='0' style='width:80%;margin:0 auto;'>";
        tab += "<tr class='item'><th>本款参数</th>";
        if (attributes_val != 'null') {
            $.each(attributes_val, function (d, t) {
                tab += "<th class='text-center'>" + t.name + "</th>";
            })
            tab += "</tr><tr class='item'><td></td>";
            $.each(attributes_val, function (s, f) {
                tab += "<td class='text-center'>" + f.value + "</td>";
            })
        }
        tab += "</tr></table>";
        $("#product-attributes-div").html(tab);
    }
</script>

<script>

    if ($("#product-tryOnReportJson-div").size() > 0) {
        var tryOnReportJson_val = eval("(" + $("#product-tryOnReportJson-div").attr("data-val") + ")");
        var tab = "<table class='table table-grey' border='0' cellspacing='0' style='width:80%;margin:0 auto;'>";
        tab += "<tr class='item'><th>试穿报告</th>";
        if (tryOnReportJson_val != 'null') {
            $.each(tryOnReportJson_val.header, function (d, t) {
                tab += "<th class='text-center'>" + t + "</th>";
            })
            tab += "</tr>";
            $.each(tryOnReportJson_val.data, function (s, f) {
                tab += "<tr class='item'><td class='text-center'>" + s + "</td>";
                $.each(f, function (m, n) {
                    tab += "<td class='text-center'>" + n + "</td>";
                })
                tab += "</tr>";
            })
        }
        tab += "</table>";
        $("#product-tryOnReportJson-div").html(tab);
    }
</script>



<script>
    $('#download-pic').on('click', function () {
        var url = $('#product-image-middle').attr('src').replace('!media', '');
        window.open(url);
        return false;
    });
    if ($("#product-size-div").size() > 0) {
        var size_val = eval("(" + $("#product-size-div").attr("data-val") + ")");
        var tab = "<table class='table table-grey' border='0' cellspacing='0' style='width:80%;margin:0 auto;'>";
        tab += "<tr class='item'><th>尺码</th>";
        if (size_val != 'null') {
            $.each(size_val.header, function (d, t) {
                tab += "<th class='text-center'>" + t + "</th>";
            })
            tab += "</tr>";
            $.each(size_val.data, function (s, f) {
                tab += "<tr class='item'><td class='text-center'>" + s + "</td>";
                $.each(f, function (m, n) {
                    tab += "<td class='text-center'>" + n + "</td>";
                })
                tab += "</tr>";
            })
        }
        tab += "</table>";
        $("#product-size-div").html(tab);
    }
</script>
<script type="text/javascript">
    function toFixed(a, b) {
        return a * b;
    }

    var _product_id = '${product.id}';

    $('.product-operator-button[data-type=fitting]').hover(function () {
        $(this).next('.fit-tip').fadeIn();
    }, function () {
        $(this).next('.fit-tip').hide();
    });

    var collectsu = function () {
        $('.detailcollect').text('已收藏');
        $('.collection').addClass('hascollect');
    }
    $(function () {
        //设置默认值
        if ($('.product-sku:first .color-section').size() == 1) {
            $('.product-sku:first .color-section').trigger('click');
        }
        //七鱼客服
        ysf.on({
            'onload': function () {
                ysf.product({
                    show: 1, // 1为打开， 其他参数为隐藏（包括非零元素）
                    title: '${(product.name)!}',
                    desc: '${(designer.name)!}',
                    picture: <#if product.productImageList>'${picture_base}/${product.productImageList[0]}</#if>',
                    note: '${(product.topCategory?eval.name)!}',
                    url: '//www.d2cmall.com/product/${product.id}'
                })
            }
        })


    });

    function continueShop() {
        $(this).parent().parent().parent().remove();
        location.reload();
    }

    _hmt.push(["_trackEvent", "商品", "详情", "商品_详情_" + $.cookie("baiduad")]);

    var _goodsData = {
        id: "${product.id}",
        soldOut: <#if product.mark lt 1 ||product.store lt 1>1<#else>0</#if>,
        category: "${(product.topCategory?eval.name)!}",
        categoryId: "${(product.topCategory?eval.id)!}",
        name: "${product.name}",
        price: "${product.originalPrice}",
        imgUrl: "${picture_base}/${product.productImageList[0]}!180",
        productUrl: "//www.d2cmall.com/product/${product.id}",
        domain: "//www.d2cmall.com/product/${product.id}",
        brand: "${designer.name}",
        promotion: "",
        discount: "",
        origPrice: ""
    };
    //_py.push(["pi",_goodsData]);

    //晶贊
    window.__zp_tag_params = {
        pagetype: 'detail',
        productId: "${product.id}",
        stock: '<#if product.mark lt 1 || product.store lt 1>0<#else>1</#if>',
        p_zp_prodstype: '488344c5fffd18a06d9521eda1a618ee',
        p_zp_prods: {
            'outerid': '${product.id}',
            'name': '${product.name}',
            'category': '${(product.topCategory?eval.name)!}',
            'subCategory': '<#if product.productCategory>${(product.productCategory?eval)[0].name}</#if>',
            'brand': '${(product.designer.name)!}',
            'price': '${product.originalPrice}',
            'value': '${product.originalPrice}',
            'discountRate': '',
            'priceUnit': '¥',
            'image': '${picture_base}/${product.productImageList[0]}!180',
            'loc': '//www.d2cmall.com/product/${product.id}',
            'stock': '<#if product.mark lt 1 || product.store lt 1>0<#else>1</#if>',
        }
    };

</script>
<@m.page_footer js='modules/page.product' />