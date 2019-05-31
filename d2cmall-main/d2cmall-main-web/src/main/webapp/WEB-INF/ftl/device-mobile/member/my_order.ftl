<#import "templates/public_mobile.ftl" as m>
<#if searcher.orderStatus?exists>
    <#if searcher.orderStatus[0]=='WaitingForPay'>
        <#assign orderStatusName='待付款' />
    <#elseif searcher.orderStatus[0]=='WaitingForDelivery'>
        <#assign orderStatusName='待发货' />
    <#elseif searcher.orderStatus[0]=='Delivered'>
        <#assign orderStatusName='待收货' />
    <#elseif searcher.orderStatus[0]=='Success'>
        <#assign orderStatusName='已完成' />
    <#else>
        <#assign orderStatusName='所有' />
    </#if>
<#else>
    <#assign orderStatusName='所有' />
</#if>
<@m.page_header back="${orderStatusName}订单" url='/member/home' cart=true title='${orderStatusName}订单' hastopfix='false'/>
<style>
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
        transform: translateX(-50%);
    }
</style>
<div class="user-header-bar display-none">
    <div class="search-bar"
         style="position:absolute;background:#FFF;box-shadow:0 0.1em 0.4em #CCC;display:none;width:100%;top:2.8em;">
        <form id="form-order" name="form_order" method="get" action="/member/order">
            <input type="hidden" name="type" value="1"/>
            <input type="search" name="productName" placeholder="搜索商品名称或订单号" value=""/>
            <button type="submit" name="">搜索</button>
        </form>
    </div>
</div>
<div class="tab-holder">
    <div class="tab tab-suspend">
        <a href="/member/order"<#if !searcher.orderStatus> class="on"</#if>>所有</a>
        <a href="/member/order?orderStatus=WaitingForPay"<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='WaitingForPay'> class="on"</#if>>待付款<#if WaitingForPay gt 0>
            <em>${WaitingForPay}</em></#if></a>
        <a href="/member/order?orderStatus=WaitingForDelivery"<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='WaitingForDelivery'> class="on"</#if>>待发货<#if WaitingForDelivery gt 0>
            <em>${WaitingForDelivery}</em></#if></a>
        <a href="/member/order?orderStatus=Delivered"<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='Delivered'> class="on"</#if>>待收货<#if Delivered gt 0>
            <em>${Delivered}</em></#if></a>
        <a href="/member/order?orderStatus=Success"<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='Success'> class="on"</#if>>已收货</a>
    </div>
</div>
    <div class="section lazyload">
<#if m.LOGINMEMBER.source!='' && m.LOGINMEMBER.source!='Weibo' && !m.LOGINMEMBER.d2c>
    <div class="form text-center">
        如果发现订单丢失，请
        <button type="button"
                id="get-back-order" <#if m.LOGINMEMBER.source=='WeixinGz'> data-text="本次通过微信登录，只能找回同一微信账号下的订单。" data-url="/member/getBackOrder/WeixinGz"<#elseif m.LOGINMEMBER.source=='QQ'> data-text="本次通过QQ登录，只能找回同一QQ账号下的订单。" data-url="/member/getBackOrder/QQ" </#if>
                class="button button-red">找回订单
        </button>
    </div>
</#if>
<#if (pager.list?size>0)!>
<!-- <div class="pages" style="margin:0">
		<@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
	</div>-->
<div class="form">
    <#list pager.list as order>
        <div class="form-item item-card item-margin clearfix" data-id="${order.id}">
            <#if order.endTime?exists && order.endTime?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")>
                <div class="count-down" data-type="split-time"
                     data-endTime="${(order.endTime)?string("yyyy/MM/dd HH:mm:ss")}">请在<strong class="hour">00</strong>小时<strong
                            class="minute">00</strong>分<strong class="second">00</strong>秒内付款，超时将关闭订单
                </div>
            </#if>
            <div class="top">下单时间：${order.createDate?string("yyyy/MM/dd HH:mm:ss")}
                <span class="float-right">${order.orderStatusName}</span>
            </div>
            <#list order.orderItems as orderItem>
                <a href="/member/order/${order.orderSn}" class="clearfix">
                    <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                           data-image="<#if orderItem.sp1?exists><#if (orderItem.sp1?eval.img)?index_of('http') != -1>${(orderItem.sp1?eval.img)!}<#else>${picture_base}/${(orderItem.sp1?eval.img)!}!120</#if></#if>"
                                           alt="${orderItem.productName}"/></span>
                    <span class="title">${orderItem.productName}</span>
                    <span class="property">货号：${orderItem.productSn}</span>
                    <span class="property">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}<#if orderItem.sp2 != null>&nbsp;&nbsp;&nbsp;${orderItem.sp2?eval.name}：${orderItem.sp2?eval.value}</#if></span>
                    <span class="amount"><em
                                class="unit">${orderItem.productPrice}</em><#if orderItem.originalPrice!=orderItem.productPrice>
                        <s>${(orderItem.originalPrice)?string("currency")}</s></#if></strong>
                        x ${orderItem.productQuantity}&nbsp;&nbsp;实付：<strong
                                class="price">&yen;${(orderItem.actualAmount / orderItem.productQuantity)}</strong>
               	<#if orderItem.estimateDate?exists><span style="margin-top:0.3em;display:block;">
                    预计发货时间&nbsp;&nbsp;(${orderItem.estimateDate?string("yyyy/MM/dd")})</span></#if>
               	<em class="float-right">【 ${orderItem.appStatusName}&nbsp;】</em></span>
                </a>
            </#list>
            <div class="bar bar-text text-right"><span class="float-left"
                                                       style="color:#FF3300;">【<#if order.paymentType==3>货到付款<#elseif order.paymentType==1>支付宝支付<#elseif order.paymentType==4||order.paymentType==5>银行转账<#elseif order.paymentType==7>钱包支付<#elseif order.paymentType==6||order.paymentType==8||order.paymentType==9>微信支付<#elseif order.paymentType==11>优惠券支付<#elseif order.paymentType==18>线下转账<#else>在线支付</#if>】</span>共有${order.orderItems?size}
                款商品 &nbsp;&nbsp;共计：<strong class="price">&yen; ${(order.totalPay)}</strong></div>
            <#if order.orderStatus lt 2>
                <div class="bar text-right">
                    <#if order.orderStatus==1>
                        <a href="javascript:" data-url="/member/order/cancel/${order.orderSn}"
                           template-id="cancel-reason-template" class="button button-outline ajax-request">取消订单</a>
                        <#if order.orderStatus==1 && !order.over>
                            <#if order.orderItems[0].productTradeType == 'CROSS'>
                                <a href="javascript:alertHtml()" class="button button-red">去付款</a>
                            <#else>
                                <a href="/order/payment/${order.orderSn}" class="button button-red">去付款</a>
                            </#if>
                        </#if>
                    <#elseif (order.orderStatus ==-1 || order.orderStatus ==-2) && !order.sourceId>
                        <a href="javascript:" data-url="/member/order/delete/${order.orderSn}" confirm="确定要删除该订单吗?"
                           method-type="post" call-back="removeItem('${order.id}')"
                           class="button button-outline  ajax-request">删除订单</a>
                    </#if>
                </div>
            </#if>
            <#if order.orderStatus==2 && (order.paymentType==0||order.paymentType==3)>
                <div class="bar text-right">
                    <a href="javascript:" data-url="/member/order/cancel/${order.orderSn}" confirm="确定要取消该订单吗?"
                       template-id="cancel-reason-template" class="button button-outline ajax-request">取消订单</a>
                </div>
            </#if>

        </div>
    </#list>
    <div class="pages" style="margin:0">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>
    <#else>
        <div class="no-result">
            <p>暂无<#if orderStatusName!='所有'>${orderStatusName}</#if>订单</p>
        </div>
    </#if>
</div>
<script id="cancel-reason-template" type="text/html">
    <header>
        <div class="header">
            <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
            <div class="header-title" id="modal-title">取消订单</div>
        </div>
    </header>
    <div class="section">
        <div class="form">
            <form action="{{url}}" class="validate-form" confirm="确定要取消订单吗？" method="post">
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="暂时不考虑" checked>暂时不考虑</label>
                </div>
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="信息错误，重新拍">信息错误，重新拍</label>
                </div>
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="卖家缺货">卖家缺货</label>
                </div>
                <div class="form-item item-text">
                    <label><input type="radio" name="reason" value="已在门店取货">已在门店取货</label>
                </div>
                <div class="form-button">
                    <button type="submit" name="close-order-btn" class="button button-l button-red">确定</button>
                </div>
            </form>
        </div>
    </div>
</script>
<script>
    var removeItem = function (id) {
        var remove_timer;
        clearTimeout(remove_timer);
        var obj = $('.form-item[data-id="' + id + '"]');
        obj.addClass('animated flipOutX');
        remove_timer = setTimeout(function () {
            obj.remove();
        }, 500);
    }
    $('#get-back-order').click(function () {
        var text = $(this).attr('data-text');
        var url = $(this).attr('data-url');
        jConfirm(text, function (r) {
            if (r) {
                location.href = url;
            }
        });
    });

</script>
<@m.page_footer />