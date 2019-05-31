<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="填写物流信息" title='退货退款详情' button='false' service='false'/>
<div class="section">
    <div class="tips tip-green">
        <p>为了保证退回的商品的质量，请您按原包装一起快递给我们。</p>
        <p>退货地址:${reship.backAddress!}</p>
        <p>收件人：${reship.backConsignee!} &nbsp;&nbsp; ${reship.backMobile!}</p>
        <p>退款金额:<strong>${(reship.refund.totalAmount)!}</strong></p>
    </div>
    <div class="form">
        <form name="" action="/member/reship/logistic" class="validate-form"
              redirect-url="/member/order/${reship.orderSn}" method="post">
            <input type="hidden" name="shipId" value="${reship.id}"/>
            <#if (reship.orderPayType==3)>
                <div style="padding:0.6em;">填写退款账号信息</div>
                <div style="padding:1em 0.8em 0.8em;color:#FD555D;font-size:0.8em;">该笔交易退款无法原路返回，退款需要汇到您的支付宝账户</div>
                <div class="form-item item-flex">
                    <label>支付宝账号</label>
                    <input type="text" name="backAccountSn" value="${reship.refund.backAccountSn}"
                           class="input validate" placeholder="退款仅支持退到支付宝账号" title="支付宝账号"/>
                </div>
                <div class="form-item item-flex">
                    <label>支付宝名称</label>
                    <input type="text" name="backAccountName" value="${reship.refund.backAccountName}" class="input"
                           placeholder="财务退款需核对支付宝账号的姓名"/>
                </div>
            <#else>
                <div style="padding:1em 0.8em 0.8em;color:#FD555D;font-size:0.8em;">
                    该笔退款原路返回您的${(reship.orderPayTypeName)!}</div>
            </#if>
            <div style="padding:0.6em;margin-top:0.6em;">填写退货快递信息</div>
            <div class="form-item item-flex">
                <label>物流公司</label>
                <select name="deliveryCorpName" class="input validate" title="快递公司">
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
            </div>
            <div class="form-item item-flex">
                <label>快递单号</label>
                <input type="text" name="deliverySn" class="input validate" placeholder="填写快递单号" title="快递单号"/>
            </div>
            <div class="form-item item-flex">
                <label>简要说明</label>
                <input type="text" name="memo" class="input"/>
            </div>
            <div class="form-button">
                <button type="submit" name="ok" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
</div>
<@m.page_footer />