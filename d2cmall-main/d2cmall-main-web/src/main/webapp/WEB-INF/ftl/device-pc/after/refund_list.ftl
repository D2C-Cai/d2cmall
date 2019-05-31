<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的退款申请' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="refund"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1 style="margin-bottom:14px;">我的退款申请</h1>
        <form id="form-order" name="form_order" method="get" action="/member/refund/list" style="position:relative;">
            <div class="tab tab-user-menu clearfix">
                <ul>
                    <li <#if RequestParameters.refundStatus?exists&&RequestParameters.refundStatus=='APPLY,WAITFORPAYMENT'>class="on"
                        style="position:relative"</#if> ><#if (processCount gt 0)><span
                                class="show-num">${processCount}</span></#if><a
                                href="/member/refund/list?refundStatus=APPLY,WAITFORPAYMENT">处理中</a><i class="interval">|</i>
                    </li>
                    <li <#if !RequestParameters.refundStatus?exists>class="on" style="position:relative"</#if> ><a
                                href="/member/refund/list">所有申请</a><i class="interval">|</i></li>
                    <#----
                    <li<#if RequestParameters.refundStatus?exists&&RequestParameters.refundStatus=='WaitingForPay'> class="on"</#if>><a href="/member/refund/list?refundStatus=WaitingForPay">被拒绝<#if WaitingForPay gt 0> <span>${WaitingForPay}</span></#if></a><i class="interval">|</i></li>
                    <li<#if RequestParameters.refundStatus?exists&&RequestParameters.refundStatus=='WaitingForDelivery'> class="on"</#if>><a href="/member/refund/list?refundStatus=WaitingForDelivery">正在处理<#if WaitingForDelivery gt 0> <span>${WaitingForDelivery}</span></#if></a><i class="interval">|</i></li>
                    <li<#if RequestParameters.refundStatus?exists&&RequestParameters.refundStatus=='Delivered'> class="on"</#if>><a href="/member/refund/list?refundStatus=Delivered">已退款<#if Delivered gt 0> <span>${Delivered}</span></#if></a><i class="interval">|</i></li>
                    <li<#if RequestParameters.refundStatus?exists&&RequestParameters.refundStatus=='Success'> class="on"</#if>><a href="/member/refund/list?refundStatus=Success">已收货<#if Success gt 0> <span>${Success}</span></#if></a><i class="interval">|</i></li> -->
                    <li style="float:right"><input type="" name="productName" value="${searcher.productName!}"
                                                   class="input" placeholder="请输入商品标题搜索"/>
                        <button name="" class="button button-red" style="border-radius:0;margin-left:-5px"><i
                                    class="fa fa-search"></i></button> &nbsp;&nbsp; <span class="more-filter">更多筛选条件<i
                                    class="fa fa-angle-down"></i></span></li>
                </ul>
            </div>
            <div class="search-form order-search-form display-none clearfix">
                <div class="search-form-item" style="width:50%">
                    <label>成交时间</label>
                    <input type="text" name="beginCreateDate"
                           value="<#if searcher.beginCreateDate?exists>${searcher.beginCreateDate?string("yyyy-MM-dd")}</#if>"
                           class="input input-s" style="width:45%"/> - <input type="text" name="endCreateDate"
                                                                              value="<#if searcher.endCreateDate?exists>${searcher.endCreateDate?string("yyyy-MM-dd")}</#if>"
                                                                              class="input input-s" style="width:45%"/>
                </div>
                <div class="search-form-item" style="width:25%">
                    <label>退货状态</label>
                    <select id="" name="refundStatus" class="input input-s">
                        <option value="">请选择</option>
                        <option value="">全部</option>
                        <option value="MEMBERCLOSE"
                                <#if searcher.refundStatus?exists&&searcher.refundStatus[0]=='MEMBERCLOSE'>selected</#if>>
                            用户取消退款
                        </option>
                        <option value="CLOSE"
                                <#if searcher.refundStatus?exists&&searcher.refundStatus[0]=='CLOSE'>selected</#if>>
                            商家拒绝退款
                        </option>
                        <option value="CREATE"
                                <#if searcher.refundStatus?exists&&searcher.refundStatus[0]=='CREATE'>selected</#if>>
                            正在申请退款
                        </option>
                        <option value="APPLY"
                                <#if searcher.refundStatus?exists&&searcher.refundStatus[0]=='APPLY'>selected</#if>>
                            客服正在确认
                        </option>
                        <option value="WAITFORPAYMENT"
                                <#if searcher.refundStatus?exists&&searcher.refundStatus[0]=='WAITFORPAYMENT'>selected</#if>>
                            财务正在退款
                        </option>
                        <option value="SUCCESS"
                                <#if searcher.refundStatus?exists&&searcher.refundStatus[0]=='SUCCESS'>selected</#if>>
                            退款成功
                        </option>
                    </select>
                </div>
            </div>
        </form>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
        </div>
        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey">
            <thead>
            <tr>
                <th width="20%">退款信息</th>
                <th width="38%">订单详情</th>
                <th width="14%">金额</th>
                <th width="10%">状态</th>
                <th width="8%">操作</th>
            </tr>
            </thead>
            <#if (pager.list?size gt 0)>
                <tbody>
                <#list pager.list as refund>
                    <tr class="space">
                        <td colspan="7"></td>
                    </tr>
                    <tr class="item">
                        <td style="text-align:left;line-height:180%;">
                            <p>退款编号：${refund.refundSn}</p>
                            <#if refund.reshipId gt 0 ><p>退货编号：${(refund.reshipId)!}</p></#if>
                            <p>退&nbsp; 款&nbsp; 人：${refund.creator}</p>
                            <p>申请时间：${refund.createDate?string("yyyy/MM/dd HH:mm")}</p>
                            <p>退款理由：<#if refund.refundReasonName!=null>${refund.refundReasonName}<#else>退货退款</#if></p>
                            <p>客户备注：${(refund.reship.memo)!}&nbsp;${(refund.memo)!}</p>
                            <#if refund.evidences?exists && refund.evidences !=null >
                                <p>
                                    <#list refund.evidenceList as evidence>
                                        <a href="${picture_base}${evidence}" target="_blank" class="float-left"><img
                                                    src="${picture_base}${evidence}" alt="" height="50"
                                                    style="margin:2px;"/></a>
                                    </#list>
                                </p>
                            </#if>
                        </td>
                        <td class="text-left">
                            <p style="float:left;"><img src="${picture_base}/${(refund.sp1?eval.img)!}!80" width="80"
                                                        alt=""/></p>
                            <div style="margin-left:90px;line-height:200%;">
                                <p style="margin-top:0;">订单编号：<span class="small"><a class="blue"
                                                                                     href="/member/order/${refund.orderSn}"
                                                                                     target="_blank">${refund.orderSn}</a></span><span
                                            style="float:right">【${(refund.orderPayTypeName)!}】</span></p>
                                <p><a href="/product/${(refund.productId)!}"
                                      target="_blank">${(refund.productName)!}</a></p>
                                <p class="grey">货号：${(refund.productSn)!}<br/>颜色：${(refund.sp1?eval.value)!} &nbsp;&nbsp;&nbsp;尺码: ${(refund.sp2?eval.value)!}
                                </p>
                            </div>
                        </td>
                        <td class="text-left">
                            <p>实际支付：${refund.tradeAmount?string("currency")}</p>
                            <p>会员申请退款：${(refund.applyAmount?string('currency'))!}</p>
                            <p>客服审核金额：${(refund.totalAmount?string('currency'))!}</p>
                            <p>退款方式：${refund.payTypeName}</p>
                            <#if refund.orderPayType==3>
                                <p>退款账号:${(refund.backAccountSn)!}</p>
                                <p>账号名称:${(refund.backAccountName)!}</p>
                            <#else>
                                <p>退款方式：原路退回</p>
                            </#if>
                            <p>实际退款：${(refund.payMoney?string('currency'))!}</p>
                        </td>
                        <td class="text-center">
                            ${(refund.refundStatusName)!}
                        </td>
                        <td class="text-center">
                            <p><a href="javascript:" template-url="/member/refund/${refund.id}" modal-type="pop"
                                  modal-width="600" class="ajax-request blue">查看</a></p>
                            <#if refund.refundStatus==1>
                                <p><a href="javascript:" data-url="/member/refund/cancel/${(refund.id)!}.json"
                                      method-type="post" confirm="确定要取消退款申请中吗？" class="ajax-request blue">取消申请</a></p>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </#if>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>

<script type="text/javascript">
    $('input[name=beginCreateDate]').datePicker({
        format: 'Y-m-d',
        date: $('input[name=beginCreateDate]').val(),
        current: $('input[name=beginCreateDate]').val(),
        starts: 1,
        position: 'r',
        onBeforeShow: function () {
            $('input[name=beginCreateDate]').datePickerSetDate($('input[name=beginCreateDate]').val(), true);
        },
        onChange: function (formated, dates) {
            $('input[name=beginCreateDate]').val(formated);
            $('input[name=beginCreateDate]').datePickerHide();
        }
    });

    $('input[name=endCreateDate]').datePicker({
        format: 'Y-m-d',
        date: $('input[name=endCreateDate]').val(),
        current: $('input[name=endCreateDate]').val(),
        starts: 1,
        position: 'r',
        onBeforeShow: function () {
            $('input[name=endCreateDate]').datePickerSetDate($('input[name=endCreateDate]').val(), true);
        },
        onChange: function (formated, dates) {
            $('input[name=endCreateDate]').val(formated);
            $('input[name=endCreateDate]').datePickerHide();
        }
    });

    $('.more-filter').click(function () {
        $('.order-search-form').toggle();
    });
</script>
<@m.page_footer js='modules/page.user' />