<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的退款退货' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="reship"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1> 我的退款退货申请</h1>
        <form id="form-order" name="form_order" method="get" action="/member/reship/list" style="position:relative;">
            <div class="tab tab-user-menu clearfix">
                <ul>
                    <li<#if RequestParameters.reshipStatus?exists&&RequestParameters.reshipStatus=='APPLY,WAITFORRECEIVE,APPROVE'> class="on" style="position:relative;"</#if>><#if (processCount gt 0)>
                        <span class="show-num">${processCount}</span></#if><a
                                href="/member/reship/list?reshipStatus=APPLY,WAITFORRECEIVE,APPROVE">处理中</a><i
                                class="interval">|</i></li>
                    <li<#if !RequestParameters.reshipStatus> class="on" style="position:relative;"</#if>><#if (totalCount gt 0)>
                        <span class="show-num">${totalCount}</span></#if><a href="/member/reship/list">所有申请</a><i
                                class="interval">|</i></li>
                    <#-- <li<#if RequestParameters.reshipStatus?exists&&RequestParameters.reshipStatus[0]=='-3'> class="on"</#if>><a href="/member/reship/list?reshipStatus=-3">被拒绝<#if WaitingForPay gt 0> <span>${WaitingForPay}</span></#if></a><i class="interval">|</i></li>
                     <li<#if RequestParameters.reshipStatus?exists&&RequestParameters.reshipStatus[0]!='-3'&&RequestParameters.reshipStatus[0]!='8'> class="on"</#if>><a href="/member/reship/list?reshipStatus=0">正在处理<#if WaitingForDelivery gt 0> <span>${WaitingForDelivery}</span></#if></a><i class="interval">|</i></li>
                     <li<#if RequestParameters.reshipStatus?exists&&RequestParameters.reshipStatus[0]=='8'> class="on"</#if>><a href="/member/reship/list?reshipStatus=8">已收货<#if Success gt 0> <span>${Success}</span></#if></a><i class="interval">|</i></li>-->
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
                    <select id="" name="reshipStatus" class="input input-s">
                        <option value="">请选择</option>
                        <option value="">全部</option>
                        <option value="REFUSE"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='REFUSE'>selected</#if>>
                            商家拒绝退货
                        </option>
                        <option value="MEMBERCLOSE"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='MEMBERCLOSE'>selected</#if>>
                            用户取消退货
                        </option>
                        <option value="CLOSE"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='CLOSE'>selected</#if>>
                            商家关闭退货
                        </option>
                        <option value="APPLY"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='APPLY'>selected</#if>>
                            正在申请退货
                        </option>
                        <option value="WAITFORRECEIVE"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='WAITFORRECEIVE'>selected</#if>>
                            仓库正在收货
                        </option>
                        <option value="APPROVE"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='APPROVE'>selected</#if>>
                            商家同意退货
                        </option>
                        <option value="SUCCESS"
                                <#if searcher.reshipStatus?exists&&searcher.reshipStatus[0]=='SUCCESS'>selected</#if>>
                            仓库确认收货
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
                <th width="18%">退货详情</th>
                <th class="text-left" width="30%">订单详情</th>
                <th width="12%">物流编号</th>
                <th width="20%">金额</th>
                <th width="11%">状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <#if (pager.list?size gt 0)!>
                <tbody>
                <#list pager.list as reship>
                    <tr class="space">
                        <td colspan="7"></td>
                    </tr>
                    <tr class="item">
                        <td style="line-height:180%;">
                            <p>编 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：${reship.reshipSn}</p>
                            <p>申 &nbsp;请 &nbsp;人：${reship.creator!}</p>
                            <p>申请时间：${reship.createDate?string("yyyy/MM/dd HH:mm")}</p>
                            <p>退货数量：${reship.quantity}</p>
                            <p>退货理由：${(reship.reshipReasonName)!} </p>
                            <p>客户备注：${(reship.memo)!}</p>
                            <#if reship.evidences?exists && reship.evidences !=null>
                                <p>
                                    <#list reship.evidenceList as evidence>
                                        <a href="${picture_base}${evidence}" target="_blank" class="float-left"><img
                                                    src="${picture_base}${evidence}" alt="" height="50"
                                                    style="margin:2px;"/></a>
                                    </#list>
                                </p>
                            </#if>
                        </td>
                        <td class="text-left">

                            <p style="float:left;"><img src="${picture_base}/${(reship.sp1?eval.img)!}!80" width="80"
                                                        alt=""/></p>
                            <div style="margin-left:90px;line-height:200%;">
                                <p style="margin-top:0;">订单编号：<span class="small"><a
                                                href="/member/order/${reship.orderSn}" target="_blank"
                                                class="blue">${reship.orderSn}</a></span><span
                                            class="float-right">【${(reship.orderPayTypeName)!}】</span></p>
                                <p><a href="//www.d2cmall.com/product/${(reship.productId)!}"
                                      target="_blank">${(reship.productName)!}</a></p>
                                <p class="grey">货号：${(reship.productSn)!}<br/>颜色：${(reship.sp1?eval.value)!} &nbsp;&nbsp;&nbsp;尺码: ${(reship.sp2?eval.value)!}
                                </p>
                                <p class="grey">收货状态：<#if reship.received>已收货<#else>未收货</#if></p>
                            </div>
                        </td>
                        <td class="text-center"><p> ${reship.deliveryCorpName}</p>
                            <p><a class="blue"
                                  href="//www.kuaidi100.com/chaxun?com=${reship.deliveryCorpCode}&nu=${(reship.deliverySn)!}">${reship.deliverySn} </a>
                            </p></td>
                        <td>
                            <p>交易额：${reship.refund.tradeAmount?string("currency")}</p>
                            <p>会员申请退款额：${(reship.refund.applyAmount?string("currency"))!}</p>
                            <p>客服审核金额：${(reship.refund.totalAmount?string("currency"))!}</p>
                            <#if reship.orderPayType==3>
                                <p>退款账号:${(reship.refund.backAccountSn)!}</p>
                                <p>账号名称:${(reship.refund.backAccountName)!}</p>
                            <#else>
                                <p>退款方式：原路退回</p>
                            </#if>
                        </td>
                        <td class="text-center">
                            <p>客服审核：${(reship.customerMemo)!}</p>
                            <p>${(reship.reshipStatusName)!}</p>
                        </td>
                        <td class="text-center">
                            <a href="javascript:" template-url="/member/reship/${reship.id}" modal-type="pop"
                               modal-width="600" class="ajax-request blue" style="padding-bottom:10px">查看</a></br>
                            <#if reship.reshipStatus &lt;8 &&reship.reshipStatus &gt; -1>
                                <a href="javascript:" data-url="/member/reship/cancel/${reship.id}.json"
                                   confirm="确定要取消退款退货申请中吗？" class="ajax-request blue"
                                   method-type='post'>取消申请</a></br></br>
                            </#if>
                            <#if reship.reshipStatus==2>
                                <a href="/member/reship/logistic/${reship.id}" class=" blue">填写快递单号</a></br></br>
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