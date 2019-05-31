<#import "templates/public_pc.ftl" as m>
<@m.page_header title='订单详情' />
<@m.top_nav suspend=false />

<div class="layout layout-response layout-order">
    <#if order!="" && order?exists >
        <div style="padding:15px;">
            <h2 class="order-title" style="border:none;"> 订单信息</h2>
            <div class="pay-order-detail clearfix">

                <ul class="pay-order-info">
                    <li>

                        <p style="border-bottom:1px solid #dedede"><span
                                    class="small">${order.createDate?string("yyyy.MM.dd HH:mm")!""}</span>&nbsp;&nbsp;交易编号：<span
                                    class="small strong">${order.orderSn}</span><span
                                    class="float-right red">${(order.orderStatusName)!}</span></p>
                        <#if order.memo!="">
                            <textarea style="float:right;background:#f0f0f0;margin-top:7px" rows="5" cols="40"
                                      readonly="readonly">
                    	 ${(order.memo)!}
    					</textarea>
                        </#if>
                        <p>收货人：<span class="small">${order.reciver}</span>
                        </p>
                        <p>联系电话：${order.contact}</p>
                        <p style="border-bottom:1px solid #dedede">
                            收货地址：${order.province} ${order.city} ${order.address}</p>
                        <p>
                            支付方式：${(order.paymentTypeName)!}<#if (order.paymentType!=3)>&nbsp;&nbsp;支付流水号：${(order.paymentSn)!}</#if></p>

                    </li>
                </ul>

            </div>
            <h2 class="order-title" style="margin-bottom:12px">商品信息</h2>

            <table class="table table-lightgrey">
                <tr>
                    <th>宝贝</th>
                    <th width="10%">单价（元）</th>
                    <th width="6%">数量</th>
                    <th width="10%">小计</th>
                    <th width="25%">物流信息</th>
                </tr>
                <#assign id = 1/>
                <#list order.orderItems as orderItem>
                    <#assign orderStatus = 0/>
                    <tr class="item">
                        <td style="line-height:200%" class="border-r-none">
                            <p class="float-left">


                                <a href="<#if orderItem?exists>/product/${orderItem.productId}</#if>"
                                   target="_blank"><img
                                            src="<#if orderItem?exists>${picture_base}/${orderItem.sp1?eval.img}!80</#if>"
                                            alt="${orderItem.productName}" width="80"/></a>
                            </p>
                            <p style="margin-left:90px;margin-top:0;margin-bottom:0;"><#if orderItem.promotionName gt 0>【
                                    <span class="red">${(orderItem.promotionName)!}</span>】</#if><#if orderItem.orderPromotionName gt 0>【
                                    <span class="red">${(orderItem.orderPromotionName)!}</span>】</#if></p>
                            <p style="margin-left:90px;margin-top:0;"><a
                                        href="<#if orderItem?exists>/product/${orderItem.productId}</#if>"
                                        target="_blank">${orderItem.productName}</a></p>
                            <p style="margin-left:90px"><span class="display-block grey"
                                                              style="margin-top:5px;">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value} &nbsp;&nbsp; ${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value} &nbsp;&nbsp; 设计师：${orderItem.designerName} </span>
                            </p>
                        </td>
                        <td class="text-center small border-r-none border-l-none">
                            <#if orderItem.promotionPrice gt 0>
                                <p>
                                    <strong class="unit price">${(orderItem.productPrice-orderItem.promotionPrice)?string("currency")?substring(1)}</strong>
                                </p>
                                <p class="grey" style="font-size:12px;text-decoration:line-through;">
                                    &yen;${orderItem.productPrice?string("currency")?substring(1)}</p>
                            <#else>
                                <p>
                                    <strong class="unit price">${orderItem.productPrice?string("currency")?substring(1)}</strong>
                                </p>
                                <p style="text-decoration:line-through;">&yen;${orderItem.originalPrice}</p>
                            </#if>
                        </td>
                        <td class="text-center small border-r-none border-l-none">${orderItem.productQuantity}</td>
                        <td class="text-center small border-l-none border-r-none">
                            &yen; ${(orderItem.productPrice * orderItem.productQuantity)?string("#.##")}</td>
                        <td class="text-center border-l-none" style="line-height:200%">
                            <#if (orderItem.status=='SUCCESS'||orderItem.status=='SIGNED'||orderItem.status=='DELIVERED')>
                                <p>物流公司：${orderItem.deliveryCorpName}</p>
                                <p class="display-block" style="position:relative;">物流单号： <a href="javascript:;"
                                                                                             data-com="${orderItem.deliveryCorpCode}"
                                                                                             data-sn="${orderItem.deliverySn}"
                                                                                             data-show="off"
                                                                                             class="blue logistics">查询物流</a>
                                </p>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </table>
            <p class="text-right grey" style="margin-top:10px;line-height:200%;">商品金额：<span
                        class="small">&yen; ${order.totalAmount?string("currency")?substring(1)}</span></br>
                运 &nbsp; 费：<strong
                        class="small grey"><#if order.shippingRates gt 0>＋</#if>&yen; ${order.shippingRates?string("currency")?substring(1)}</strong></br>
                满减优惠：</label><span
                        class="small grey"><#if order.promotionAmount gt 0>－</#if>&yen; ${(order.promotionAmount)?string("currency")?substring(1)}</span></br>
                优 惠 券：</label><span
                        class="small grey"><#if order.couponAmount gt 0>－</#if>&yen; ${order.couponAmount?string("currency")?substring(1)}</span>
            </p>
            <p class="text-right" style="line-height:200%;font-size:12px">实际支付：<span
                        class="important level-two">&yen; ${(order.totalPay)?string("currency")?substring(1)}</span></p>
            <div class="text-center"><a href="/member/order" class="button button-l button-red">返回我的订单</a></div>
        </div>
    <#else>
        <div style="line-height:130%;text-align:center;font-size:22px;padding-top:50px;color:#999">
            亲~您的订单信息丢失了哟</br>请检查您的帐号是否正确或联系我们在线客服
        </div>
    </#if>

</div>

<script id="logistics-detail" type="text/html">
    <div class="logistics_detail">
        <p><span class="grey">物流状态&nbsp;&nbsp;&nbsp;</span>&nbsp;<span class="blue">{{statusName}}</span></p>
        <p><span class="grey">承运来源：</span>&nbsp;<span>{{deliveryCorpName}}</span></p>
        <p><span class="grey">运单编号：</span>&nbsp;<span>{{deliverySn}}</span></p>
        <p><span class="grey">官方电话：</span>&nbsp;<span>{{tel}}</span></p>
        <div class="logistics_list">
            <ul>
                {{each deliveryInfo as value i}}
                <li>
                    <div class="logis-detail-d">
                        <div class="logis-detail-content">
                            <p>{{value.context}}</p>
                            <p>{{value.ftime}}</p>
                        </div>
                    </div>
                </li>
                {{/each}}
            </ul>
        </div>
    </div>
</script>


<script type="text/javascript">
    $(function () {
        $('.logistics').hover(function () {
            var obj = $(this);
            var com = obj.attr('data-com');
            var sn = obj.attr('data-sn');
            var s = obj.attr('data-show');
            if (s == "off") {
                $.getJSON("/logistics/info?com=" + com + "&sn=" + sn, function (res) {
                    if (res.logistics && res.logistics.status != -1 && res.logistics.deliveryInfo) {
                        res.logistics.deliveryInfo = eval("(" + res.logistics.deliveryInfo + ")");
                        var html = template("logistics-detail", res.logistics);
                        obj.parent().append(html).find('.logistics_detail').show();
                        obj.attr('data-show', 'on');
                    } else {
                        location.href = "//www.kuaidi100.com/chaxun?com=" + com + "&nu=" + sn;
                    }
                })
            }
            obj.parent().find('.logistics_detail').show();
        }, function () {
            $(this).parent().find('.logistics_detail').hide();
        })
    });
</script>
<@m.page_footer />