<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的退款换货' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="exchange"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1 style="margin-bottom:14px;">我的换货单</h1>
        <form id="form-order" name="form_order" method="get" action="/member/exchange/list" style="position:relative;">
            <div class="tab tab-user-menu clearfix">
                <ul>
                    <li<#if RequestParameters.exchangeStatus?exists&&RequestParameters.exchangeStatus=='APPLY,APPROVE,WAITFORRECEIVE,WAITDELIVERED'> class="on" style="position:relative"</#if>><#if (processCount gt 0)>
                        <span class="show-num">${processCount}</span></#if><a
                                href="/member/exchange/list?exchangeStatus=APPLY,APPROVE,WAITFORRECEIVE,WAITDELIVERED">处理中</a><i
                                class="interval">|</i></li>
                    <li<#if !RequestParameters.exchangeStatus> class="on" style="postion:relative"</#if>><#if (totalCount gt 0)>
                        <span class="show-num">${totalCount}</span></#if><a href="/member/exchange/list">所有申请</a><i
                                class="interval">|</i></li>
                    <#--<li<#if RequestParameters.exchangeStatus?exists&&RequestParameters.exchangeStatus[0]=='WaitingForPay'> class="on"</#if>><a href="/member/order?exchangeStatus=WaitingForPay">被拒绝<#if WaitingForPay gt 0> <span>${WaitingForPay}</span></#if></a><i class="interval">|</i></li>
                     <li<#if RequestParameters.exchangeStatus?exists&&RequestParameters.exchangeStatus[0]=='WaitingForDelivery'> class="on"</#if>><a href="/member/order?exchangeStatus=WaitingForDelivery">正在处理<#if WaitingForDelivery gt 0> <span>${WaitingForDelivery}</span></#if></a><i class="interval">|</i></li>
                     <li<#if RequestParameters.exchangeStatus?exists&&RequestParameters.exchangeStatus[0]=='Delivered'> class="on"</#if>><a href="/member/order?exchangeStatus=Delivered">已退款<#if Delivered gt 0> <span>${Delivered}</span></#if></a><i class="interval">|</i></li>
                     <li<#if RequestParameters.exchangeStatus?exists&&RequestParameters.exchangeStatus[0]=='Success'> class="on"</#if>><a href="/member/order?exchangeStatus=Success">已收货<#if Success gt 0> <span>${Success}</span></#if></a><i class="interval">|</i></li>-->
                    <li style="float:right"><input type="" name="productName" value="${searcher.productName!}"
                                                   class="input" placeholder="请输入商品标题搜索"/>
                        <button name="" class="button button-red" style="border-radius:0;margin-left:-5px"><i
                                    class="fa fa-search"></i></button>&nbsp;&nbsp; <span class="more-filter">更多筛选条件<i
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
                    <select id="" name="exchangeStatus" class="input input-s">
                        <option value="">请选择</option>
                        <option value="">全部</option>
                        <option value="REFUSE"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='REFUSE'>selected</#if>>
                            商家拒绝换货
                        </option>
                        <option value="MEMBERCLOSE"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='MEMBERCLOSE'>selected</#if>>
                            用户取消换货
                        </option>
                        <option value="CLOSE"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='CLOSE'>selected</#if>>
                            商家关闭换货
                        </option>
                        <option value="APPLY"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='APPLY'>selected</#if>>
                            正在申请换货
                        </option>
                        <option value="APPROVE"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='APPROVE'>selected</#if>>
                            商家同意换货
                        </option>
                        <option value="WAITFORRECEIVE"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='WAITFORRECEIVE'>selected</#if>>
                            仓库正在收货
                        </option>
                        <option value="WAITDELIVERED"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='WAITDELIVERED'>selected</#if>>
                            商家正在发货
                        </option>
                        <option value="DELIVERED"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='DELIVERED'>selected</#if>>
                            等待用户收货
                        </option>
                        <option value="SUCCESS"
                                <#if searcher.exchangeStatus?exists&&searcher.exchangeStatus[0]=='SUCCESS'>selected</#if>>
                            换货成功
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
                <th width="20%">换货详情</th>
                <th class="text-left" width="38%">订单详情</th>
                <th width="12%">物流编号</th>
                <th width="15%">状态</th>
                <th width="10%">操作</th>
            </tr>
            </thead>
            <#if (pager.list?size gt 0)!>
                <tbody>
                <#list pager.list as exchange>
                    <tr class="space">
                        <td colspan="7"></td>
                    </tr>
                    <tr class="item">
                        <td style="line-height:180%;">
                            <p>换货编 号：${exchange.exchangeSn}</p>
                            <p>申 &nbsp;请 &nbsp;人：${exchange.loginCode!}</p>
                            <p>申请时间：${exchange.createDate?string("yyyy/MM/dd HH:mm")}</p>
                            <p>换货数量：${exchange.quantity}</p>
                            <p>换货理由：${(exchange.exchangeReasonName)!} </p>
                            <p>客户备注：${(exchange.memo)!}</p>
                        </td>
                        <td class="text-left">
                            <#if exchange.skuId>
                                <p style="float:left;"><img src="${picture_base}/${(exchange.sp1?eval.img)!}!80"
                                                            width="80" alt=""/></p>
                                <div style="margin-left:90px;line-height:200%;">
                                    <p>${exchange.oldProductName}</p>
                                    <p style="margin-top:0;">订单编号：<span class="small"><a
                                                    href="/member/order/${exchange.orderSn}" target="_blank"
                                                    class="blue">${exchange.orderSn}</a></span></p>
                                    <p><a href="//www.d2cmall.com/product/${(exchange.productId)!}"
                                          target="_blank">${(exchange.skuName)!}</a></p>
                                    <p class="grey">货号：${(exchange.productSn)!} <br/>颜色：${(exchange.sp1?eval.value)!}
                                        &nbsp;&nbsp;&nbsp;尺码: ${(exchange.sp2?eval.value)!}</p>
                                </div>
                            <#else>
                                <p style="float:left;"><img src="${picture_base}/${(exchange.oldSp1?eval.img)!}!80"
                                                            width="70" alt="" style="float:left;"/></p>
                                <div style="margin-left:90px;line-height:200%;">
                                    <p style="margin-top:0;">订单编号：<span class="small"><a
                                                    href="/member/order/${exchange.orderSn}"
                                                    target="_blank">${exchange.orderSn}</a></span></p>
                                    <p><a href="//www.d2cmall.com/product/${(exchange.oldProductId)!}"
                                          target="_blank">${(exchange.oldProductName)!}</a></p>
                                    <p class="grey">货号：${(exchange.oldProductSn)!}
                                        <br/>颜色：${(exchange.oldSp1?eval.value)!}
                                        &nbsp;&nbsp;&nbsp;尺码: ${(exchange.oldSp2?eval.value)!}</p>
                                </div>
                            </#if>
                        </td>
                        <td class="text-left">
                            <p>寄回物流信息：</p>
                            <p>${(exchange.deliveryCorp)!}</p>
                            <p>
                                <a href="//www.kuaidi100.com/chaxun?com=${exchange.deliveryCorpCode}&nu=${(exchange.deliverySn)!}"
                                   class="strong blue" target="_blank">${(exchange.deliverySn)!}</a></p><br/>
                            <p>发货物流信息：</p>
                            <p>${(exchange.exchangeDeliveryCorp)!}</p>
                            <p>
                                <a href="//www.kuaidi100.com/chaxun?com=${exchange.exchangeDeliveryCorpCode}&nu=${(exchange.exchangeDeliverySn)!}"
                                   class="strong blue" target="_blank">${(exchange.exchangeDeliverySn)!}</a></p>
                        </td>
                        <td class="text-center">
                            <p>客服审核：${(exchange.adminMemo)!}</p>
                            <p>${(exchange.exchangeStatusName)!}</p>
                        </td>
                        <td class="text-center">
                            <a href="javascript:" template-url="/member/exchange/${exchange.id}" modal-type="pop"
                               modal-width="600" class="ajax-request blue">查看</a></br>
                            <#if exchange.exchangeStatus &lt;3 &&exchange.exchangeStatus &gt; -1>
                                <a href="javascript:" data-url="/member/exchange/cancel/${exchange.id}.json"
                                   confirm="确定要取消退款换货申请中吗？" class="ajax-request blue"
                                   method-type='post'>取消申请</a></br></br>
                            </#if>
                            <#if exchange.exchangeStatus==1>
                                <a href="/member/exchange/logistic/${exchange.id}" class="blue">填写快递单号</a></br></br>
                            </#if>
                            <#if exchange.exchangeStatus==4>
                                <a href="javascript:" data-url="/member/exchange/receive/${exchange.id}.json"
                                   confirm="确定收到你想要的商品了吗？" class="ajax-request blue"
                                   method-type='post'>确认收货</a></br></br>
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
            $('input[name=beginCreateDate').datePickerHide();
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