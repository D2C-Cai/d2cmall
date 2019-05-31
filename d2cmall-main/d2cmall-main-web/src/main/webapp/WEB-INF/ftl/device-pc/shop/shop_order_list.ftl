<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的订单 -' js="datepicker" />
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray">
        <@m.page_menu menu_item="seller"/>
        <div class="my-cont">
            <h1>我的订单</h1>
            <div class="order-search-tip">
                我的订单：<a href="/seller/orderitem?index=2">待付款<span class="small">(${waitingForPay})</span></a> <span
                        class="light-grey">︱</span>
                <a href="/seller/orderitem?index=3">待发货<span class="small">(${waitingForDelivery})</span></a> <span
                        class="light-grey">︱</span>
                <a href="/seller/orderitem?index=4">已发货<span class="small">(${delivered})</span></a>
            </div>
            <div class="form-search">
                <form id="form-order" name="form_order" method="get" action="/seller/orderitem">
                    <input type="hidden" name="pageNow" value=""/>
                    <div class="label" style="width:390px;">
                        <p>宝贝名称 <input type="text" name="productName"
                                       value="<#if queryOrder.productName?exists>${queryOrder.productName}</#if>"
                                       size="46" class="input"></p>
                        <p>成交时间 <input type="text" name="startDate"
                                       value="<#if queryOrder.startDate?exists>${queryOrder.startDate?string("yyyy-MM-dd")}<#else></#if>"
                                       size="18" class="input"/> 至 <input type="text" name="endDate"
                                                                          value="<#if queryOrder.endDate?exists>${queryOrder.endDate?string("yyyy-MM-dd")}<#else></#if>"
                                                                          size="18" class="input"/></p>
                    </div>
                    <div class="label" style="width:260px;">
                        <p>订单状态 &nbsp;
                            <select id="orderStatus" name="index" class="input" style="width:130px;">
                                <option value="0">全部交易</option>
                                <option value="2" <#if index==2>selected</#if>>待付款</option>
                                <option value="3" <#if index==3>selected</#if>>待发货</option>
                                <option value="4" <#if index==4>selected</#if>>已发货</option>
                            </select></p>
                    </div>
                    <div class="float-left text-center"><input type="submit" value="确定" class="button-l b-b"
                                                               style="margin-top:20px;"/></div>
                    <div class="clear"></div>
                </form>
            </div>
            <#if (orders?size>0)!>
                <div class="pages float-right" style="margin:0">
                    <@m.p page=pager.pageNumber totalpage=pager.pageCount />
                </div>
            </#if>
            <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table">
                <thead>
                <tr>
                    <th width="15%">明细创建时间</th>
                    <th colspan="2">宝贝</th>
                    <th width="7%">数量</th>
                    <th width="13%">物流状态</th>
                </tr>
                </thead>
                <#if (orderItems?size > 0)>
                    <tbody>
                    <#list orderItems as orderItem>
                        <tr class="text-center order-item align-top${orderClass}">
                            <td><span class="small">${orderItem.createDate?string("yyyy/MM/dd HH:mm:ss")}</span></td>
                            <td class="border-r-none"><img
                                        src="<#if orderItem?exists>${picture_base}/${(orderItem.sp1?eval.img)!}!80</#if>"
                                        alt="" width="70" height="70"/></td>
                            <td class="text-left border-l-none"><a href="/product/${orderItem.productId}"
                                                                   style="line-height:18px;"
                                                                   target="_blank">${orderItem.productName}</a>
                                <span class="display-block grey"
                                      style="margin-top:4px;line-height:18px;">${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}&nbsp;&nbsp;${orderItem.sp1?eval.name}：${orderItem.sp1?eval.value}<br/>设计师：${orderItem.designerName}</span>
                            </td>
                            <td class="small border-l-none border-r-none">${orderItem.productQuantity}</td>
                            <td style="line-height:250%;">
                                <span class="display-block">${orderItem.itemStatus.name}</span>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                <#else>
                    <tbody>
                    <tr>
                        <td colspan="7" style="line-height:150px;text-align:center;">暂时无记录</td>
                    </tr>
                    </tbody>
                </#if>
            </table>
            <#if (orderItems?size > 0)>
                <div class="pages float-right">
                    <@m.p page=pager.pageNumber totalpage=pager.pageCount />
                </div>
            </#if>
        </div>
        <div class="clear"></div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('a[name=prePage]').click(function () {
            $('input[name=pageNow]').val($(this).attr('id'));
            document.forms[1].submit();
        });

        $('a[name=nextPage]').click(function () {
            $('input[name=nextPage]').val($(this).attr('id'));
            document.forms[1].submit();
        });

        $('a[name=thisPage]').click(function () {
            $('input[name=thisPage]').val($(this).attr('id'));
            document.forms[1].submit();
        });

        $('input[name=startDate]').DatePicker({
            format: 'Y-m-d',
            date: $('input[name=startDate]').val(),
            current: $('input[name=startDate]').val(),
            starts: 1,
            position: 'r',
            onBeforeShow: function () {
                $('input[name=startDate]').DatePickerSetDate($('input[name=startDate]').val(), true);
            },
            onChange: function (formated, dates) {
                $('input[name=startDate]').val(formated);
                $('input[name=startDate]').DatePickerHide();
            }
        });

        $('input[name=endDate]').DatePicker({
            format: 'Y-m-d',
            date: $('input[name=endDate]').val(),
            current: $('input[name=endDate]').val(),
            starts: 1,
            position: 'r',
            onBeforeShow: function () {
                $('input[name=endDate]').DatePickerSetDate($('input[name=endDate]').val(), true);
            },
            onChange: function (formated, dates) {
                $('input[name=endDate]').val(formated);
                $('input[name=endDate]').DatePickerHide();
            }
        });

    });
</script>
<@m.page_footer />