<#import "templates/public_pc.ftl" as m>
<@m.page_header title='门店预约单管理' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="o2o_subscribe"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 门店预约单管理</h1>
        <div class="tab tab-user-menu clearfix">
            <ul>
                <li<#if !RequestParameters.status> class="on"</#if>><a href="?">所有预约单</a><i class="interval">|</i></li>
                <li<#if RequestParameters.status==2> class="on"</#if>><a href="?status=2">等待接收<#if WaitingForRec gt 0>
                        <span>${WaitingForRec}</span></#if></a><i class="interval">|</i></li>
                <li<#if RequestParameters.status==3> class="on"</#if>><a
                            href="?status=3">待准备确认<#if WaitingForServer gt 0> <span>${WaitingForServer}</span></#if></a><i
                            class="interval">|</i></li>
                <li<#if RequestParameters.status==4> class="on"</#if>><a
                            href="?status=4">待完成确认<#if FinishForServer gt 0> <span>${FinishForServer}</span></#if></a><i
                            class="interval">|</i></li>
                <li style="float:right">
                    <select id="o2oSubscribeStatus" name="status" class="input"
                            style="font-size:14px;margin-top:-3px;margin-left:100px;"
                            onchange="location.href='?status='+this.value;">
                        <option value="">全部状态</option>
                        <option value="2"<#if RequestParameters.status==2> selected</#if>>等待门店签收</option>
                        <option value="3"<#if RequestParameters.status==3> selected</#if>>待准备确认</option>
                        <option value="4"<#if RequestParameters.status==4> selected</#if>>待完成确认</option>
                        <option value="5"<#if RequestParameters.status==5> selected</#if>>服务已完成</option>
                        <option value="6"<#if RequestParameters.status==6> selected</#if>>客户已评价</option>
                        <option value="-1"<#if RequestParameters.status==-1> selected</#if>>用户取消</option>
                        <option value="-2"<#if RequestParameters.status==-2> selected</#if>>客服取消</option>
                    </select>
                </li>
            </ul>
        </div>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
        </div>
        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey"
               style="word-break:break-all">
            <#if pager.list?exists && pager.list?size &gt; 0>
                <#list pager.list as list>
                    <#if list_index gt 0>
                        <tr class="space">
                            <td colspan="6" style="height:20px;"></td>
                        </tr>
                    </#if>
                    <tr class="title">
                        <td colspan="6">
                            预约单号：<strong>${(list.sn)!}</strong> &nbsp;&nbsp; <span class="grey">|</span>&nbsp;&nbsp;
                            预约时间：<strong>${(list.estimateDate?string("yyyy-MM-dd"))!}&nbsp;&nbsp;${(list.startHour)!}
                                点${(list.startMinute)!}分到${(list.endHour)!}点${(list.endMinute)!}分</strong> &nbsp;&nbsp;&nbsp;
                            <span class="grey">|</span> &nbsp;&nbsp;
                            <#if list.status==2>
                                <button data-url="/o2oSubscribe/startPrepare" confirm="确定接收预约吗？" success-tip="接受预约成功！"
                                        method-type="post" data-param="{'id':${list.id},'memberId':${list.memberId}}"
                                        class="button button-green ajax-request">接收预约
                                </button>
                            <#elseif list.status==3>
                                <button data-url="/o2oSubscribe/endPrepare" confirm="确定完成准备了吗？" success-tip="提交准备成功！"
                                        method-type="post" data-param="{'id':${list.id},'memberId':${list.memberId}}"
                                        class="button button-green ajax-request">完成准备
                                </button>
                            <#elseif list.status==4>
                                <button data-url="/o2oSubscribe/success" method-type="post"
                                        data-param="{'id':${list.id}}" title="门店反馈" template-id="result-template"
                                        modal-type="pop" modal-width="600" class="button button-green ajax-request">完成服务
                                </button>
                            </#if>
                        </td>
                    </tr>
                    <tr class="item text-left">
                        <td width="10%">预约人</td>
                        <td width="15%">${(list.name)!} 【${(list.sex)!}】</td>
                        <td width="10%">预约人电话</td>
                        <td width="25%">${(list.tel)!}<#if list.contact=="电话"> 【首选】 </#if></td>
                        <td width="10%">预约人微信</td>
                        <td width="30%">${(list.wechat)!}<#if list.contact=="微信"> 【首选】 </#if></td>
                    </tr>
                    <tr class="item text-left">
                        <td>预约人数</td>
                        <td>${(list.numbers)!} ${(list.companion)!}</td>
                        <td>预约服务</td>
                        <td>${(list.storeService)!} </td>
                        <td>预约状态</td>
                        <td>
                            ${(list.statusName)!}
                            <#if list.status == -2 >
                                <p>关闭原因：${(list.cancelReason)!}</p>
                            </#if>
                        </td>
                    </tr>
                    <#if list.optionRequire>
                        <tr class="item text-left">
                            <td>留言</td>
                            <td colspan="5">${(list.optionRequire)!}</td>
                        </tr>
                    </#if>
                    <#if list.remark>
                        <tr class="item text-left">
                            <td>客服备注</td>
                            <td colspan="5">${(list.remark)!}</td>
                        </tr>
                    </#if>
                    <#if list.status gt 4>
                        <tr class="item text-left">
                            <td>试衣结果</td>
                            <td colspan="5"><#if list.payStatus==-1>用户已取消<#elseif list.payStatus==0>用户未购买<#elseif list.payStatus==1>用户已购买  &nbsp;&nbsp;&nbsp; 支付金额：&yen; ${(list.payAmount?string("currency")?substring(1))!}  &nbsp;&nbsp;&nbsp; 零售单号：${(list.retailSn)!}</#if>
                                &nbsp;&nbsp;&nbsp; 来店人数：${(list.actualNumbers)!}</td>
                        </tr>
                    </#if>
                    <#if list.status==6>
                        <tr class="item text-left">
                            <td>客户评价</td>
                            <td colspan="5"><p class="text-left">${(list.comment.content)!}</p></td>
                        </tr>
                    </#if>
                    <tr class="item">
                        <td colspan="6">
                            <#list list.items as item>
                                <div class="table-product-item">
                                    <p class="img"><a href="/product/${item.productId}" target="_blank"><img
                                                    src="${picture_base}/${item.sp1?eval.img}!80" alt=""
                                                    height="70"/></a></p>
                                    <p class="title"><a href="/product/${item.productId}" style="line-height:18px;"
                                                        target="_blank">${(item.productName)!}</a></p>
                                    <p class="desc">颜色：${(item.sp1?eval.value)!}&nbsp;&nbsp;尺码：${(item.sp2?eval.value)!}
                                        &nbsp;&nbsp;数量：${(item.quantity)!}&nbsp;&nbsp;价格：${(item.price)!}元</span></p>
                                </div>
                            </#list>
                        </td>
                    </tr>
                </#list>
            </#if>
        </table>

        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>

    </div>
</div>
<script id="result-template" type="text/html">
    <div class="form">
        <form name="" class="validate-form" action="{{url}}" method="post">
            <input type="hidden" name="id" value="{{id}}"/>
            <div class="form-item">
                <label>服务结果</label>
                <select name="payStatus" id="pay-status" class="input">
                    <option value="-1">客户已取消</option>
                    <option value="0" selected>客户未购买</option>
                    <option value="1">客户已购买</option>
                </select>
                <div class="tip tip-validate" data-target="pay-status"></div>
            </div>
            <div class="form-item isbuy pay-item display-none">
                <label><span class="red">*</span>支付金额</label>
                <input type="text" name="payAmount" id="pay-amount" value="" class="input"/>
            </div>
            <div class="form-item isbuy retial-item display-none">
                <label><span class="red">*</span>零售单号</label>
                <input type="text" name="retailSn" id="retail-sn" value="" class="input" placeholder="多个零售单用英文逗号隔开"/>
            </div>
            <div class="form-item">
                <label><span class="red">*</span>到店人数</label>
                <input type="text" name="actualNumbers" data-limit="0" id="number" value="" style="width:100px;"
                       class="input"/> 人
                <div class="tip tip-validate" data-target="number" data-function="checkNumber"></div>
            </div>
            <div class="form-item">
                <label>备注说明</label>
                <textarea name="feedback" id="feedback" style="height:60px;" class="input"></textarea>
            </div>
            <div class="form-button">
                <button type="submit" class="button">确定</button>
            </div>
        </form>
    </div>
    <script>
        $('#number').utilSetNumber();
</script>
    </script>
    <
    script >
    var checkNumber = function () {
        var val = parseFloat($('#number').val());
        var limit = parseFloat($('#number').attr('data-limit'));
        if (isNaN(val)) {
            return '请填写正确的数字。';
        }
        if (val < limit) {
            return '数字必须大于等于0';
        }
        return true;
    }
    $('#pay-status').live('change', function () {
        if ($(this).val() == 1) {
            $('.pay-item').show().append('<div class="tip tip-validate" data-target="pay-amount"></div>');
            $('.retial-item').show().append('<div class="tip tip-validate" data-target="retail-sn"></div>');
        } else {
            $('.isbuy').hide().find('.tip-validate').remove();
        }
        $('#pay-amount').utilSetNumber();
    });
</script>
<@m.page_footer />