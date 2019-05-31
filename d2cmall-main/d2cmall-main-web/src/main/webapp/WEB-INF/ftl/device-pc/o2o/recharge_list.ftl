<#import "templates/public_pc.ftl" as m>
<@m.page_header title='会员充值列表' js='utils/jquery.datepicker' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="o2o_recharge_list"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 会员充值列表 （${(store.name)!}）</h1>
        <form id="search-form" name="searcher" method="GET" action="/o2opay/recharge/list">
            <div class="search-form clearfix">
                <div class="search-form-item" style="width:15%">
                    <label>会员账号</label>
                    <input type="text" name="account" value="${(RequestParameters.account)!}" class="input input-s"/>
                </div>
                <div class="search-form-item" style="width:15%">
                    <label>支付流水号</label>
                    <input type="text" name="paySn" value="${(RequestParameters.paySn)!}" class="input input-s"/>
                </div>
                <div class="search-form-item" style="width:26%">
                    <label>开始结束</label>
                    <input type="text" name="begin" value="${(RequestParameters.begin)!}" class="input input-s"
                           style="width:45%"/> - <input type="text" name="end" value="${(RequestParameters.end)!}"
                                                        class="input input-s" style="width:45%"/>
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
                    <label>支付渠道</label>
                    <select id="" name="payChannel" class="input input-s">
                        <option value="">请选择</option>
                        <option value="ALIPAY"<#if RequestParameters.payChannel=='ALIPAY'> selected</#if>>支付宝</option>
                        <option value="UNIONPAY"<#if RequestParameters.payChannel=='UNIONPAY'> selected</#if>>银联支付
                        </option>
                        <option value="CASH"<#if RequestParameters.payChannel=='CASH'> selected</#if>>现金</option>
                    </select>
                </div>
                <div class="search-form-item" style="width:12%">
                    <label>状态</label>
                    <select id="" name="status" class="input input-s">
                        <option value="">请选择</option>
                        <option value="0" <#if RequestParameters.status=='0'>selected</#if>>未审核</option>
                        <option value="1" <#if RequestParameters.status=='1'>selected</#if>>已审核</option>
                        <option value="-1" <#if RequestParameters.status=='-1'>selected</#if>>关闭</option>
                    </select>
                </div>
            </div>
            <div class="search-form-button">
                <input type="hidden" name="storeId" value="${(store.storeId)!}">
                <button type="submit" class="button button-green">查找</button>
                <button type="button" data-url="/o2opay/recharge/excel" class="button button-red excel-export">导出
                </button>
            </div>
        </form>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
        </div>
        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey"
               style="margin-top:20px;">
            <tr>
                <th>会员信息</th>
                <th>交易信息</th>
                <th>实充金额</th>
                <th>红包</th>
                <th>活动名称</th>
                <th>备注</th>
                <th>操作信息</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            <#if pager.list?exists && pager.list?size gt 0>
                <#list pager.list as recharge>
                    <tr class="item">
                        <td style="text-align:left;line-height:180%;">
                            <p>钱包账号：<a class="strong">${(recharge.account.account)!}</a></p>
                            <p>持卡人：<a class="strong">${(recharge.account.cardHolder)!}</a></p>
                            <p>身份证：<a class="strong">${(recharge.account.cardId)!}</a></p>
                            <p>姓名：<a class="strong">${(recharge.memberInfo.name)!}</a></p>
                            <p>昵称：<a class="strong">${(recharge.memberInfo.nickname)!}</a></p>
                            <p>手机：<a class="strong">${(recharge.memberInfo.mobile)!}</a></p>
                            <p>邮箱：<a class="strong">${(recharge.memberInfo.email)!}</a></p>
                        </td>
                        <td style="text-align:left;line-height:180%;">
                            <p>充值时间：<a class="strong">${(recharge.payDate?string("yyyy-MM-dd"))!}</a></p>
                            <p>交易流水号：<a class="strong">${(recharge.paySn)!}</a></p>
                            <p>充值渠道：<a class="strong">${(recharge.payChannelName)!}</a></p>
                        </td>
                        <td class="text-center">${(recharge.rechargeAmount)!}</td>
                        <td class="text-center">${(recharge.giftAmount)!}</td>
                        <!--td>${(recharge.account.totalAmount)!}（实际：${(recharge.account.cashAmount)!}，红包：${(recharge.account.giftAmount)!}）</td-->
                        <td class="text-center">${(recharge.rule.name)!}</td>
                        <td class="text-center">${(recharge.memo)!}</td>
                        <td>
                            <p>创建人：<a href="javascript:;" class="strong">${(recharge.creator)!}</a></p>
                            <p>创建时间：<a href="javascript:;"
                                       class="strong">${(recharge.createDate?string("yyyy-MM-dd"))!}</a></p>
                            <#if recharge.status==-1>
                                <p>关闭人：<a href="javascript:;" class="strong">${(recharge.closer)!}</a></p>
                                <p>关闭时间：<a href="javascript:;"
                                           class="strong">${(recharge.closeDate?string("yyyy-MM-dd"))!}</a></p>
                            <#elseif recharge.status==1>
                                <p>审核人：<a href="javascript:;" class="strong">${(recharge.submitor)!}</a></p>
                                <p>审核时间：<a href="javascript:;"
                                           class="strong">${(recharge.submitDate?string("yyyy-MM-dd"))!}</a></p>
                            </#if>
                        </td>
                        <td class="text-center">
                            <#if recharge.status==0>未审核<#elseif recharge.status==1>已审核<#else>已关闭</#if>
                        </td>
                        <td class="text-center">
                            <#if recharge.status==0 >
                                <button type="button" data-url="/o2opay/recharge/close/${recharge.id}"
                                        template-id="cancel-template" title="取消充值"
                                        class="button button-red ajax-request">取消充值
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
    <div class="form" style="width:350px;">
        <form name="" confirm="确定要取消充值吗？" class="validate-form" action="{{url}}" method="post">
            <div class="form-item form-item-vertical">
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
    $('input[name=begin]').datePicker({
        format: 'Y-m-d',
        date: $('input[name=begin]').val(),
        current: $('input[name=begin]').val(),
        starts: 1,
        position: 'r',
        onBeforeShow: function () {
            $('input[name=begin]').datePickerSetDate($('input[name=begin]').val(), true);
        },
        onChange: function (formated, dates) {
            $('input[name=begin]').val(formated);
            $('input[name=begin]').datePickerPickerHide();
        }
    });
    $('input[name=end]').datePicker({
        format: 'Y-m-d',
        date: $('input[name=end]').val(),
        current: $('input[name=end]').val(),
        starts: 1,
        position: 'r',
        onBeforeShow: function () {
            $('input[name=end]').datePickerSetDate($('input[name=end]').val(), true);
        },
        onChange: function (formated, dates) {
            $('input[name=end]').val(formated);
            $('input[name=end]').datePickerHide();
        }
    });
    $('.recharge-close').click(function () {
        var url = $(this).attr('data-url');
        var str = $('#reason-div div').clone();
        str.attr('data-url', url);
        $(this).float_box({'str': str, 'title': '取消充值', 'direction': 'right'});
        return false;
    });

    $('button[name=close-order-btn]').die().live('click', function () {
        var url = $(this).parent().parent().attr('data-url');
        var closeReason = $('textarea[name=closeReason]').val();
        jConfirm('确定要提交吗？', '', function (r) {
            if (r) {
                $.ajax({
                    'url': url,
                    'data': {closeReason: closeReason},
                    dataType: 'json',
                    type: "post",
                    'success': function (data) {
                        location.reload();
                    }
                });
            }
        });
        return false;
    });
</script>
<@m.page_footer />