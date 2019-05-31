<#import "templates/public_pc.ftl" as m>
<@m.page_header title='换货物流填写' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="exchange"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>换货详情</h1>

        <div class="form-info n_form text-center">
            <div class="exchange step2_box" style="width:868px;height:70px;text-indent:-9999px;margin:auto;">申请换货</div>

            <form action="/member/exchange/logistic" class="validate-form" method="post" call-back="myexchange"
                  style="margin-top:65px">
                <input type="hidden" name="exchangeId" value="${exchange.id}"/>
                <div class="form-item form-item-vertical" style="text-align:left;margin-left:18%">
                    <label>物流公司</label>
                    <select class="input" name="deliveryCorpName" id="delivery-name" style="width:35%">
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
                           style="width:35%"/>
                    <div class="tip tip-validate" data-target="delivery-sn"></div>
                </div>
                <div class="form-button">
                    <button type="submit" class="button button-green" style="margin-left:-10%">确定</button>
                </div>
            </form>
        </div>

        <div class="grey-content"></div>
        <div class="password-info">
            <h3>换货须知</h3>
            <p style="margin-bottom:20px;">为了保证退回的商品的质量，请您按原包装一起快递给我们。</p>
            <p style="margin-bottom:20px;">请您仔细填写退换货单，或者在退回的件内标注您的订单相关信息，以便我们第一时间给您处理您的退换货，感谢您的理解和支持!</p>
            <p style="margin-bottom:20px;">退货地址:${exchange.backAddress!}</p>
            <p style="margin-bottom:20px;">收件人:${exchange.backConsignee!} &nbsp;&nbsp; ${exchange.backMobile!}</p>
        </div>

    </div>
</div>
<script>
    var myexchange = function () {
        setTimeout(function () {
            location.href = "/member/exchange/list";
        }, "2000");
    }
</script>
<@m.page_footer js='modules/page.user' />