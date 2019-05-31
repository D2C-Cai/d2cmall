<#import "templates/public_pc.ftl" as m>
<@m.page_header title='门店收款管理 (${(store.name)!})' js='utils/jquery.datepicker' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="o2o_receive_list"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 门店收款管理 (${(store.name)!})</h1>
        <form id="search-form" name="searcher" method="GET" action="/o2opay/bill">
            <div class="search-form clearfix">
                <div class="search-form-item" style="width:15%">
                    <label>会员账号</label>
                    <input type="text" name="account" value="${(RequestParameters.account)!}" class="input input-s"/>
                </div>
                <div class="search-form-item" style="width:15%">
                    <label>交易流水号</label>
                    <input type="text" name="paySn" value="${(RequestParameters.paySn)!}" class="input input-s"/>
                </div>
                <div class="search-form-item" style="width:26%">
                    <label>开始结束</label>
                    <input type="text" name="startDate" value="${(RequestParameters.startDate)!}" class="input input-s"
                           style="width:45%"/> - <input type="text" name="endDate"
                                                        value="${(RequestParameters.endDate)!}" class="input input-s"
                                                        style="width:45%"/>
                </div>
                <div class="search-form-item" style="width:20%">
                    <label>金额范围</label>
                    <input type="text" name="" value="" class="input input-s" style="width:40%"/> - <input type="text"
                                                                                                           name=""
                                                                                                           value=""
                                                                                                           class="input input-s"
                                                                                                           style="width:40%"/>
                </div>
                <div class="search-form-item" style="width:12%">
                    <label>状态</label>
                    <select id="" name="status" class="input input-s">
                        <option value="">请选择</option>
                        <option value="0" <#if RequestParameters.status=='0'>selected</#if>>未支付</option>
                        <option value="1" <#if RequestParameters.status=='1'> selected</#if>>已支付</option>
                        <option value="-1"<#if RequestParameters.status=='-1'> selected</#if>>关闭</option>
                    </select>
                </div>
            </div>
            <div class="search-form-button">
                <input type="hidden" name="storeId" value="${(store.storeId)!}">
                <button type="submit" class="button button-green">查找</button>
                <button type="button" data-url="/o2opay/bill/excel" class="button button-red excel-export">导出</button>
            </div>
        </form>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
        </div>
        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey">
            <tr>
                <th width="20%">会员信息</th>
                <th width="28%">交易信息</th>
                <th width="13%">支出</th>
                <th>状态</th>
                <th width="20%">备注</th>
                <th>操作</th>
            </tr>
            <#if pager.list?exists && pager.list?size gt 0>
                <#list pager.list as bill>
                    <tr class="item text-center">
                        <td style="text-align:left;line-height:180%;">
                            <p>钱包账号：<a class="strong">${(bill.account.account)!}</a></p>
                            <#----<p>账号金额：${(bill.selfAccount.totalAmount)!}(实际:${(bill.selfAccount.cashAmount)!},红包:${(bill.selfAccount.giftAmount)!})</p>
                            <p>身份证：<a class="strong">${(bill.selfAccount.cardId)!}</a></p>---->
                            <p>姓名：<a class="strong">${(bill.account.name)!}</a></p>
                        </td>
                        <td style="text-align:left;line-height:180%;">
                            <p>创建时间：<a class="strong">${(bill.transactionTime?string("yyyy-MM-dd HH:mm:ss"))!}</a></p>
                            <p>交易流水号：<a class="strong">${(bill.sn)!}</a></p>
                            <p>零售单号：<a class="strong">${(bill.transactionInfo)!}</a></p>
                            <p>交易类型：<a class="strong">${(bill.payTypeName)!}</a></p>
                        </td>
                        <td style="text-align:left;line-height:180%;">
                            <p>实际：${(bill.factAmount)!0}元</p>
                            <p>红包：${(bill.factGiftAmount)!0}元</p>
                        </td>
                        <td>${(bill.statusName)!}</td>
                        <td>${(bill.memo)!}</td>
                        <td>
                            <#if bill.status==1  && bill.payType=='PAY' && bill.canBack==1>
                                <button type="button" template-url="/o2opay/back/${bill.id}" modal-type="pop"
                                        class="button button-red ajax-request">退款
                                </button>
                            <#elseif bill.status==0  && bill.payType=='PAY'>
                                <button type="button" data-url="/o2opay/receive/close/${bill.id}"
                                        template-id="cancel-template" data-param="{'text':'取消收款'}"
                                        class="button button-red ajax-request">取消收款
                                </button>
                            <#elseif bill.status==0  && bill.payType=='REFUND'>
                                <button type="button" data-url="/o2opay/receive/close/${bill.id}"
                                        template-id="cancel-template" data-param="{'text':'取消退款'}"
                                        class="button button-red ajax-request">取消退款
                                </button>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </#if>

        </table>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
        </div>
    </div>
</div>
<script id="cancel-template" type="text/html">
    <div class="form" style="padding:10px 0;width:450px;">
        <form name="" confirm="确定要{{text}}吗？" class="validate-form" action="{{url}}" method="post">
            <div class="form-item">
                <label>取消原因</label>
                <textarea name="closeReason" id="close-reason" style="height:60px;" class="input"></textarea>
                <div class="tip tip-validate" data-target="close-reason"></div>
            </div>
            <div class="form-button">
                <button type="submit" class="button button-red">确定</button>
            </div>
        </form>
    </div>
</script>
<script>
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
</script>
<@m.page_footer />