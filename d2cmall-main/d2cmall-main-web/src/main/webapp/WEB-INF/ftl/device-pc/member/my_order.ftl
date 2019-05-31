<#import "templates/public_pc.ftl" as m>
<#assign LOGINMEMBER=loginMember()/>
<@m.page_header title='我的订单' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />

<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="order"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>我的订单 </h1>
        <form id="form-order" name="form_order" method="get" action="/member/order" style="position:relative;">
            <div class="tab tab-user-menu clearfix">
                <ul>
                    <li<#if !searcher.orderStatus> class="on"</#if>><a href="/member/order">所有订单</a><i class="interval">|</i>
                    </li>
                    <li<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='WaitingForPay'> class="on"</#if>><a
                                href="/member/order?orderStatus=WaitingForPay">待付款<#if WaitingForPay gt 0>
                            <span>${WaitingForPay}</span></#if></a><i class="interval">|</i></li>
                    <li<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='WaitingForDelivery'> class="on"</#if>>
                        <a href="/member/order?orderStatus=WaitingForDelivery">待发货<#if WaitingForDelivery gt 0>
                            <span>${WaitingForDelivery}</span></#if></a><i class="interval">|</i></li>
                    <li<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='Delivered'> class="on"</#if>><a
                                href="/member/order?orderStatus=Delivered">待收货<#if Delivered gt 0>
                            <span>${Delivered}</span></#if></a><i class="interval">|</i></li>
                    <li<#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='Success'> class="on"</#if>><a
                                href="/member/order?orderStatus=Success">已收货<#if Success gt 0>
                            <span>${Success}</span></#if></a><i class="interval">|</i></li>
                    <li style="float:right"><input type="" name="productName" value="${RequestParameters.productName!}"
                                                   class="input" placeholder="请输入商品标题搜索"/>
                        <button name="" class="button button-red" style="border-radius:0;margin-left:-5px"><i
                                    class="fa fa-search"></i></button> &nbsp;&nbsp; <span class="more-filter">更多筛选条件<i
                                    class="fa fa-angle-down"></i></span></li>
                </ul>
            </div>
            <div class="search-form order-search-form display-none clearfix">
                <div class="search-form-item" style="width:50%">
                    <label>成交时间</label>
                    <input type="text" name="startDate"
                           value="<#if searcher.startDate?exists>${searcher.startDate?string("yyyy-MM-dd")}</#if>"
                           class="input input-s" style="width:45%"/> - <input type="text" name="endDate"
                                                                              value="<#if searcher.endDate?exists>${searcher.endDate?string("yyyy-MM-dd")}</#if>"
                                                                              class="input input-s" style="width:45%"/>
                </div>
                <div class="search-form-item" style="width:25%">
                    <label>交易状态</label>
                    <select id="" name="orderStatus" class="input input-s">
                        <option value="">请选择</option>
                        <option value="">全部交易</option>
                        <option value="WaitingForPay"
                                <#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='WaitingForPay'>selected</#if>>
                            等待付款
                        </option>
                        <option value="WaitingForDelivery"
                                <#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='WaitingForDelivery'>selected</#if>>
                            等待发货
                        </option>
                        <option value="Delivered"
                                <#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='Delivered'>selected</#if>>
                            等待收货
                        </option>
                        <option value="Success"
                                <#if searcher.orderStatus?exists&&searcher.orderStatus[0]=='Success'>selected</#if>>交易完成
                        </option>
                    </select>
                </div>
                <div class="search-form-item" style="width:25%">
                    <label>设计师/品牌</label>
                    <input type="text" name="designerName"
                           value="<#if searcher.designerName?exists>${searcher.designerName}</#if>"
                           class="input input-s"/>
                </div>
            </div>
        </form>
        <div class="clear">
            <#if LOGINMEMBER.source!='' && LOGINMEMBER.source!='Weibo' && !LOGINMEMBER.d2c>
                <div class="float-left" style="padding-top:12px;line-height:30px;">
                    如果发现订单丢失，请
                    <button type="button"
                            id="get-back-order" <#if LOGINMEMBER.source=='Weixin'> data-text="本次通过微信登录，只能找回同一微信账号下的订单。" data-url="/member/getBackOrder/WeixinKf"<#elseif LOGINMEMBER.source=='QQ'> data-text="本次通过QQ登录，只能找回同一QQ账号下的订单。" data-url="/member/getBackOrder/QQ" </#if>
                            class="button button-s button-red">找回订单
                    </button>
                </div>
            </#if>
            <div class="pages float-right" style="margin:0">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
        </div>

        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey">
            <thead>
            <tr>
                <th colspan="2">宝贝</th>
                <th width="10%">单价</th>
                <th width="7%">数量</th>
                <th width="13%">售后</th>
                <th width="13%">物流状态</th>
                <th width="9%">实付款</th>
                <th width="9%">操作</th>
            </tr>
            </thead>
            <#if (pager.list?size>0)!>
                <tbody>
                <#list pager.list as order>
                    <#assign id = 1/>
                    <tr class="space">
                        <td colspan="7"></td>
                    </tr>
                    <tr class="title">
                        <td colspan="8"><span class="small">${order.createDate?string("yyyy.MM.dd HH:mm:ss")}</span>
                            &nbsp;&nbsp;&nbsp;&nbsp;订单编号：<span class="small">${order.orderSn}</span> &nbsp;&nbsp;&nbsp;&nbsp;【${(order.paymentTypeName)!}
                            】<#if order.paymentId && order.paymentType!=3>&nbsp;&nbsp;&nbsp;&nbsp;支付流水号：<span
                                    class="small">${(order.paymentSn)}</span></#if>
                            <#if order.endTime?exists><span style="color:#fd555d;float:right"
                                                            class="count-down float-right" data-type="split-time"
                                                            data-endTime="${(order.endTime)?string("yyyy/MM/dd HH:mm:ss")}">
                                    剩余支付时间&nbsp;&nbsp;&nbsp;&nbsp;<strong class="hour">00</strong>小时<strong
                                            class="minute">00</strong>分<strong class="second">00</strong>秒</span></#if>
                        </td>
                    </tr>
                    <#list order.orderItems as orderItem>
                        <input type="hidden" name="quantities" value="${orderItem.productQuantity}"/>
                        <input type="hidden" name="skuIds" value="${orderItem.productSkuId}"/>
                        <tr class="item align-top${orderClass}">
                            <td class="text-center border-r-none"></td>
                            <td class="border-l-none border-r-none">
                                <#if (orderItem.orderPromotionAmount>0.0)> <p class="red">
                                    【${orderItem.orderPromotionName} <strong
                                            class="small red">(优惠${orderItem.orderPromotionAmount}元)</strong> 】
                                    </p></#if>
                                <#if (orderItem.promotionAmount>0.0)> <p class="red">【${orderItem.promotionName} <strong
                                            class="small red">(优惠${orderItem.promotionAmount}元)</strong> 】</p></#if>
                                <p class="float-left"><a href="/product/${orderItem.productId}" target="_blank"><img
                                                src="<#if orderItem.sp1?exists>${picture_base}/${orderItem.sp1?eval.img}!80</#if>"
                                                alt="" width="80"/></a></p>
                                <p style="margin-left:90px;margin-top:8px"><a href="/product/${orderItem.productId}"
                                                                              style="line-height:18px;"
                                                                              target="_blank">${orderItem.productName}</a>
                                </p><#if orderItem.crowdItemId !=null && orderItem.crowdItemId gt 0><p><span
                                        class="tags-tip"></span></p></#if>
                                <p style="margin-left:90px"><span class="display-block grey"
                                                                  style="margin-top:4px;line-height:18px;">货号:${orderItem.productSn}&nbsp;&nbsp;${orderItem.sp1?eval.name}：${(orderItem.sp1)?eval.value}&nbsp;&nbsp;<#if orderItem.sp2>${(orderItem.sp2?eval.name)!}：${(orderItem.sp2?eval.value)!}</#if><br/>设计师：${(orderItem.designerName)!}</span>
                                </p>
                                <p class="red"><#if orderItem.after==0>【本商品不支持七天无理由退换货】</#if></p>
                            </td>
                            <td class="text-center small price border-l-none border-r-none">${(orderItem.productPrice)?string("currency")}
                                <br><#if orderItem.originalPrice!=orderItem.productPrice>
                                <s>${(orderItem.originalPrice)?string("currency")}</s></#if></td>
                            <td class="text-center small border-l-none border-r-none">${orderItem.productQuantity}</td>
                            <td class="text-center border-l-none border-r-none">
                                <#if order.type=='distribution'>分销订单
                                <#else>
                                    <#if order.orderStatus &gt;= 2>
                                        <#if orderItem.aftering=='refund'>
                                            <a href="/member/${orderItem.aftering}/list?id=${orderItem.refundId}"
                                               class="blue">${orderItem.afteringStatus}</a></br>
                                        <#elseif orderItem.aftering=='reship'>
                                            <a href="/member/${orderItem.aftering}/list?id=${orderItem.reshipId}"
                                               class="blue">${orderItem.afteringStatus}</a></br>
                                        <#elseif orderItem.aftering=='exchange'>
                                            <a href="/member/${orderItem.aftering}/list?id=${orderItem.exchangeId}"
                                               class="blue">${orderItem.afteringStatus}</a></br>
                                        <#elseif orderItem.after==1>
                                            <#if orderItem.aftered=='refund'>
                                                <a href="/member/${orderItem.aftered}/${orderItem.refundId}"
                                                   template-url="/member/${orderItem.aftered}/${orderItem.refundId}"
                                                   modal-type="pop" modal-width="600"
                                                   class=" blue ajax-request">${orderItem.afteredStatus}</a></br><br>
                                            <#elseif orderItem.aftered=='reship'>
                                                <a href="/member/${orderItem.aftered}/${orderItem.reshipId}"
                                                   template-url="/member/${orderItem.aftered}/${orderItem.reshipId}"
                                                   modal-type="pop" modal-width="600"
                                                   class="blue ajax-request">${orderItem.afteredStatus}</a></br><br>
                                            <#elseif orderItem.aftered=='exchange'>
                                                <a href="/member/${orderItem.aftered}/${orderItem.exchangeId}"
                                                   template-url="/member/${orderItem.aftered}/${orderItem.exchangeId}"
                                                   modal-type="pop" modal-width="600"
                                                   class="blue ajax-request">${orderItem.afteredStatus}</a></br><br>
                                            </#if>
                                            <#if order.paymentType==3>
                                                <#if orderItem.codAfterApply.reship==1 ||orderItem.codAfterApply.refund==1||orderItem.codAfterApply.exchange==1 >
                                                    <a href="javascript:" data-type="${orderItem.productTradeType}"
                                                       id="after-apply-${orderItem.id}"
                                                       class="after-apply button button-s">申请售后</a></#if>
                                                <div class="after-apply-cont display-none">
                                                    <#if orderItem.codAfterApply.reship==1>
                                                        <a href="/member/reship/create/${orderItem.id}"
                                                           class="button button-s" style="margin:5px;">退款退货</a>
                                                    </#if>
                                                    <#if orderItem.codAfterApply.refund==1>
                                                        <a href="/member/refund/create/${orderItem.id}"
                                                           class="button button-s" style="margin:5px;">仅退款</a>
                                                    </#if>
                                                    <#if orderItem.codAfterApply.exchange==1>
                                                        <a href="/member/exchange/create/${orderItem.id}"
                                                           class="button button-s" style="margin:5px;">仅换货</a>
                                                    </#if>
                                                </div>
                                            <#else>
                                                <#if orderItem.onlineAfterApply.reship==1 ||orderItem.onlineAfterApply.refund==1||orderItem.onlineAfterApply.exchange==1 >
                                                    <a href="javascript:" data-type="${orderItem.productTradeType}"
                                                       id="after-apply-${orderItem.id}"
                                                       class="after-apply button button-s">申请售后</a></#if>
                                                <div class="after-apply-cont display-none">
                                                    <#if orderItem.onlineAfterApply.reship==1>
                                                        <a href="/member/reship/create/${orderItem.id}"
                                                           class="button button-s" style="margin:5px;">退款退货</a>
                                                    </#if>
                                                    <#if orderItem.onlineAfterApply.refund==1>
                                                        <a href="/member/refund/create/${orderItem.id}"
                                                           class="button button-s" style="margin:5px;">仅退款</a>
                                                    </#if>
                                                    <#if orderItem.onlineAfterApply.exchange==1>
                                                        <a href="/member/exchange/create/${orderItem.id}"
                                                           class="button button-s" style="margin:5px;">仅换货</a>
                                                    </#if>
                                                </div>
                                            </#if>
                                        </#if>
                                    </#if>
                                </#if>
                            </td>
                            <td class="text-center" style="line-height:250%;">
                                <span>${(orderItem.itemStatusName)!}</span>
                                <#if orderItem.status == 'NORMAL'>
                                <#if orderItem.estimateDate?exists><p>预计发货时间</br>
                                    (${orderItem.estimateDate?string("yyyy/MM/dd")})</br></#if>
                                    </#if>
                                    <#if orderItem.status == 'DELIVERED' || orderItem.status == 'SIGNED'>
                                        <span class="display-block" style="position:relative;"><a href="javascript:;"
                                                                                                  class="strong blue logistics"
                                                                                                  target="_blank"
                                                                                                  data-name="${orderItem.deliveryCorpName}"
                                                                                                  data-com="${orderItem.deliveryCorpCode}"
                                                                                                  data-sn="${orderItem.deliverySn}"
                                                                                                  data-show="off">物流查询</a></span>
                                    </#if>
                                    <#if orderItem.status == 'DELIVERED'>
                                <p>
                                    <button type="button" data-url="/member/orderItem/confirm/${orderItem.id}"
                                            class="button button-s button-red ajax-request" method-type="post"
                                            confirm="是否确认收货？" style="margin-top:10px">确认收货
                                    </button>
                                </p>
                                </#if>
                                <#if orderItem.status == 'SIGNED' || orderItem.status == 'SUCCESS'>
                                    <#if orderItem.commentId>
                                        <p><a href="/comment/list?id=${orderItem.commentId}" class="button button-s">查看评价</a>
                                        </p>
                                    <#else>
                                        <p><a href="/comment/item/edit?orderItemId=${orderItem.id}"
                                              class="button button-s">评价</a></p>
                                    </#if>
                                </#if>
                            </td>
                            <#if id == 1>
                                <#assign id = 2/>
                                <td class="text-center price" rowspan="${order.orderItems?size}">
                                    <p>
                                        <strong style="font-size:14px;">&yen; ${(order.totalPay)?string("currency")?substring(1)}</strong>
                                    <#if order.shippingRates gt 0><p>运费：${order.shippingRates}元</p></#if>
                                    <#if order.couponAmount &gt; 0><br/><p style="color:#999;">
                                        优惠券：${(order.couponAmount)}元</p></#if>
                                    <#if order.promotionAmount &gt; 0><p style="color:#999;">
                                        满减：${(order.promotionAmount)}元</p></#if>
                                    <#if order.cashCardAmount &gt; 0><br/>
                                    <p style="color:#999;">D2C卡：${(order.cashCardAmount)}</p></#if></p>
                                    <p style="color:#999;"><#if order.paymentType==3>货到付款<#elseif order.paymentType==1>支付宝支付<#elseif order.paymentType==4||order.paymentType==5>银行转账<#elseif order.paymentType==7>钱包支付<#elseif order.paymentType==6||order.paymentType==8||order.paymentType==9>微信支付<#elseif order.paymentType==11>优惠券支付<#elseif order.paymentType==18>线下转账<#else>在线支付</#if></p>
                                </td>
                                <td class="text-center" style="line-height:250%;" rowspan="${order.orderItems?size}">
                                    <span class="display-block">${(order.orderStatusName)!}</span>
                                    <span class="display-block"><a href="/member/order/${order.orderSn}" class="blue"
                                                                   target="_blank">订单详情</a></span>
                                    <#if order.orderStatus==1>
                                        <span class="display-block"><a href="/order/payment/${order.orderSn}"
                                                                       class="button button-s button-red"
                                                                       target="_blank">付款</a></span>
                                        <span class="display-block"><a href="javascript:"
                                                                       data-url="/member/order/cancel/${order.orderSn}"
                                                                       method-type="post"
                                                                       confirm="确定要取消该订单吗？取消后无法恢复，请谨慎操作！"
                                                                       modal-type="pop" modal-width="400px"
                                                                       template-id="cancel-reason-template"
                                                                       title="取消订单原因" class="ajax-request blue">取消订单</a></span>
                                    <#elseif (order.orderStatus ==-1 || order.orderStatus ==-2) && !order.sourceId>
                                        <span class="display-block"><a href="javascript:"
                                                                       data-url="/member/order/delete/${order.orderSn}"
                                                                       method-type="post"
                                                                       confirm="确定要删除该订单吗？删除后无法恢复，请谨慎操作！"
                                                                       class="ajax-request">删除</a></span>
                                    <#elseif order.orderStatus==2 && (order.paymentType==0 || order.paymentType==3)>
                                        <span class="display-block"><a href="javascript:"
                                                                       data-url="/member/order/cancel/${order.orderSn}"
                                                                       method-type="post"
                                                                       confirm="确定要取消该订单吗？取消后无法恢复，请谨慎操作！"
                                                                       modal-type="pop" modal-width="400px"
                                                                       template-id="cancel-reason-template"
                                                                       title="取消订单原因" class="ajax-request blue">取消订单</a></span>
                                    </#if>
                                </td>
                            </#if>
                        </tr>
                    </#list>
                </#list>
                </tbody>
            </#if>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>

<script id="cancel-reason-template" type="text/html">
    <div class="form" style="width:250px;">
        <form name="" success-tip="订单取消成功！" class="validate-form" action="{{url}}" method="post">
            <div style="padding:10px 10px 0 10px;">
                <select name="closeReason" class="input input-l" id="reason-select" style="width:100%;">
                    <option value="暂时不考虑">暂时不考虑</option>
                    <option value="信息错误，重新拍">信息错误，重新拍</option>
                    <option value="卖家缺货">卖家缺货</option>
                    <option value="已在门店取货">已在门店取货</option>
                </select>
                <div class="tip tip-validate" data-target="reason-select"></div>
            </div>
            <div class="form-button">
                <button type="submit" class="button button-red">确定取消</button>
            </div>
        </form>
    </div>
</script>

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
            debugger
            var com = obj.attr('data-com');
            var comName = obj.attr('data-name');
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
                        if (com == 'other')
                            location.href = "//www.kuaidi100.com/chaxun?com=" + comName + "&nu=" + sn;
                        else
                            location.href = "//www.kuaidi100.com/chaxun?com=" + com + "&nu=" + sn;
                    }
                })
            }
            obj.parent().find('.logistics_detail').show();
        }, function () {
            $(this).parent().find('.logistics_detail').hide();
        })

        $('input[name=startDate]').datePicker({
            format: 'Y-m-d',
            date: $('input[name=startDate]').val(),
            current: $('input[name=startDate]').val(),
            starts: 1,
            position: 'r',
            onBeforeShow: function () {
                $('input[name=startDate]').datePickerSetDate($('input[name=startDate]').val(), true);
            },
            onChange: function (formated, dates) {
                $('input[name=startDate]').val(formated);
                $('input[name=startDate]').datePickerHide();
            }
        });

        $('input[name=endDate]').datePicker({
            format: 'Y-m-d',
            date: $('input[name=endDate]').val(),
            current: $('input[name=endDate]').val(),
            starts: 1,
            position: 'r',
            onBeforeShow: function () {
                $('input[name=endDate]').datePickerSetDate($('input[name=endDate]').val(), true);
            },
            onChange: function (formated, dates) {
                $('input[name=endDate]').val(formated);
                $('input[name=endDate]').datePickerHide();
            }
        });
        $('.more-filter').click(function () {
            $('.order-search-form').toggle();
        });

        $('.buy-again').click(function () {
            var id = $(this).attr('data-id');
            $('#form-order-' + id).submit();
        });

        $('#get-back-order').click(function () {
            var text = $(this).attr('data-text');
            var url = $(this).attr('data-url');
            jConfirm(text, '', function (r) {
                if (r) {
                    location.href = url;
                }
            });
        });

        $('.after-apply').click(function () {
            var type = $(this).attr('data-type');
            if (type == "CROSS") {
                alert('跨境商品售后请联系客服')
            } else {
                var str = $(this).siblings('.after-apply-cont').html();
                str = '<div style="padding:5px;width:260px;text-align:center;">' + str + '</div>';
                $(this).floatModal({str: str, title: '选择售后服务类型'});
                return false;
            }

        });
        $('.float-modal a.ajax-request').live('click', function () {
            $('.float-modal').remove();
        });
    });
</script>
<@m.page_footer js='modules/page.user' />