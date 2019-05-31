<#import "templates/public_pc.ftl" as m>
<@m.page_header title='退货退款物流填写' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="reship"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>退货退款详情</h1>

        <div class="form-info n_form text-center">
            <div class="reship step2_box" style="width:868px;height:70px;text-indent:-9999px;margin:auto;">申请退货退款</div>

            <form action="/member/reship/logistic" class="validate-form" method="post" call-back="myreship">
                <input type="hidden" name="shipId" value="${reship.id}"/>
                <#if (reship.orderPayType==3)>
                    <div class="form-item form-item-vertical"
                         style="color:#FD555D;text-align:left;margin-left:18%;padding-left:10px"><i
                                class="fa fa-info-circle"></i>该笔交易退款无法原路返回，退款需要汇到您的支付宝账户
                    </div>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label>支付宝账号</label>
                        <input type="text" name="backAccountSn" id="back-account"
                               value="${(reship.refund.backAccountSn)!}" class="input" placeholder="目前退款仅支持退到支付宝账号"
                               style="width:35%;"/>
                        <div class="tip tip-validate" data-target="back-account"></div>
                    </div>
                    <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                        <label>支付宝姓名</label>
                        <input type="text" name="backAccountName" value="${(reship.refund.backAccountName)!}"
                               class="input" placeholder="财务退款时需核对该支付宝账号的姓名" style="width:35%;"/>
                    </div>
                <#else>
                    <div style="padding:10px;color:#FD555D"><i
                                class="fa fa-info-circle"></i>该笔交易退款原路返回${(reship.orderPayTypeName)!}</div>
                </#if>
                <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                    <label>物流公司</label>
                    <select class="input" name="deliveryCorpName" id="delivery-name" style="width:35%;">
                        <option value="">选择快递公司</option>
                        <option value="顺丰速递">顺丰速递</option>
                        <option value="申通快递">申通快递</option>
                        <option value="圆通快递">圆通快递</option>
                        <option value="中通快递">中通快递</option>
                        <option value="韵达快递">韵达快递</option>
                        <option value="百世汇通">百世汇通</option>
                        <option value="京东快递">京东快递</option>
                        <option value="德邦快递">德邦快递</option>
                        <option value="快捷快递">快捷快递</option>
                        <option value="EMS">EMS</option>
                        <option value="其他">其他</option>
                    </select>
                    <div class="tip tip-validate" data-target="delivery-name"></div>
                </div>
                <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                    <label>快递单号</label>
                    <input type="text" class="input" size="25" name="deliverySn" id="delivery-sn" value=""
                           style="width:35%;"/>
                    <div class="tip tip-validate" data-target="delivery-sn"></div>
                </div>
                <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                    <label>备注说明</label>
                    <textarea class="input" style="height:80px;width:35%" name="memo"></textarea>
                </div>
                <div class="form-button">
                    <button type="submit" class="button button-green" style="margin-left:-10%">确定</button>
                </div>
            </form>
        </div>
        <div class="grey-content"></div>
        <div class="password-info">
            <h3>退货退款须知</h3>
            <p style="margin-bottom:20px;">为了保证退回的商品的质量，请您按原包装一起快递给我们。</p>
            <p style="margin-bottom:20px;">请您仔细填写退换货单，或者在退回的件内标注您的订单相关信息，以便我们第一时间给您处理您的退换货，感谢您的理解和支持!</p>
            <p style="margin-bottom:20px;">退货地址:${reship.backAddress!}</p>
            <p style="margin-bottom:20px;">收件人：${reship.backConsignee!} &nbsp;&nbsp; ${reship.backMobile!}</p>
            <p style="margin-bottom:20px;">退款金额: &nbsp;&nbsp;<strong
                        class="red">${(reship.refund.totalAmount)!}</strong></p>
        </div>

    </div>
</div>
<script>
    var myreship = function () {
        setTimeout(function () {
            location.href = "/member/reship/list";
        }, "2000");
    }
</script>
