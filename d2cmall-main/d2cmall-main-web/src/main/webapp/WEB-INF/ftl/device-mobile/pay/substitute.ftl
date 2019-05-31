<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title='好友代付' js='utils/md5' hastopfix='false'/>
<#assign order=result.datas.order />
<#assign member=result.datas.member />

<style>
    .sup-top {
        background: #fff;
        text-align: center
    }

    .user-infos, .order-info {
        padding-top: 26px;
        text-align: center;
    }

    .user-infos > a {
        width: 50px;
        height: 50px;
        overflow: hidden;
        border-radius: 100%;
        display: inline-block;
    }

    .user-infos .user-nick {
        font-size: 12px;
        margin-top: 10px;
    }

    .order-info p {
        font-size: 14px;
    }

    .order-info .o_d {
        font-size: 28px;
        line-height: 40px;
        font-weight: bold;
        margin-top: 20px;
    }

    .order-info .o_d + p {
        color: rgba(0, 0, 0, .5)
    }

    .counts {
        margin-top: 20px;
        font-size: 14px;
        padding-bottom: 30px;
    }

    .counts > span {
        display: inline-block;
        padding: 2px 3px;
        background: #111;
        color: #fff;
        font-size: 10px;
        border: none
    }

    .counts > span:last-child {
        background: #E83333
    }

    .sup-product {
        margin-top: 10px;
        padding: 15px 15px 18px 15px;
        background: #fff;
        overflow: hidden;
        box-sizing: border-box
    }

    .sup-product .img {
        float: left;
        width: 20%;
        margin-right: 3%;
    }

    .sup-product .img img {
        width: 100%;
        vertical-align: middle
    }

    .sup-product .info {
        float: left;
        font-size: 12px;
        width: 77%;
    }

    .sup-product .title {
        overflow: hidden;
        line-height: 20px;
        height: 40px;
        font-size: 16px;
    }

    .sup-product .attr {
        color: rgba(0, 0, 0, .5);
        font-size: 10px;
        margin-top: 8px;
    }

    .pay-tips {
        color: #b2b2b2;
        font-size: 10px;
        line-height: 150%;
        margin-top: 20px;
        margin-bottom: 3em;
        padding-left: 16px;
    }

    .pay-button {
        position: fixed;
        width: 100%;
        background: #262626;
        color: #fff;
        text-align: center;
        font-size: 18px;
        height: 50px;
        line-height: 50px;
        border: 0;
        bottom: 0;
        left: 0;
    }

    .more-tips {
        margin-top: 20px;
        height: 20px;
        line-height: 20px;
        padding-bottom: 20px;
    }

    .more-tips .more-icon {
        width: 20px;
        height: 20px;
        border-radius: 100%;
        display: inline-block;
        background-size: cover;
        vertical-align: -4px;
        margin-right: 5px;
    }

    .more-tips .more-icon.success {
        background-image: url(http://static.d2c.cn/other/icon_fkcg@2x.png);
    }

    .more-tips .more-icon.error {
        background-image: url(http://static.d2c.cn/other/icon_fksb@2x.png);
    }

    .online-chat {
        display: none
    }
</style>

<div class="sup-top">
    <div class="user-infos">
        <a href="javascript:;">
            <#if member.headPic>
                <img src="<#if member.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${member.headPic}"
                     class="head-pic" style="width:100%"/>
            <#elseif member.thirdHeadPic>
                <img src="${member.thirdHeadPic}" class="head-pic" style="width:100%"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png" style="width:100%"/>
            </#if></a>
        <p class="user-nick">${member.nickname?default('匿名用户')}</p>
    </div>
    <div class="order-info">
        <p>我在D2C看中了商品，快来帮我付款吧</p>
        <p class="o_d">&yen;${order.totalPay}</p>
        <p>代付金额</p>

        <#if order.endTime && order.endTime?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss")>
            <#if order.orderStatus==1>
                <div class="count-down counts" data-type="split-time"
                     data-endTime="${(order.endTime)?string("yyyy/MM/dd HH:mm:ss")}" data-musttime="true">
                    <em>付款时间&nbsp;&nbsp;</em>
                    <span class="hour down">00</span> :
                    <span class="minute down">00</span> :
                    <span class="second down">00</span>
                </div>
            <#elseif order.orderStatus==3 || order.orderStatus==4  || order.orderStatus==8>
                <div class="more-tips">
                    <i class="more-icon success"></i>该订单已成功代付
                </div>
            </#if>
        <#else>
            <#if order.orderStatus==3 || order.orderStatus==4  || order.orderStatus==8>
                <div class="more-tips">
                    <i class="more-icon success"></i>该订单已成功代付
                </div>
            <#else>
                <div class="more-tips">
                    <i class="more-icon error"></i>订单已经超时或者关闭
                </div>
            </#if>


        </#if>
    </div>
</div>
<section>
    <#list order.orderItems?if_exists as orderItem>
        <div class="sup-product clearfix">
            <p class="img">
                <img src="${picture_base}${orderItem.productImg}!120"/>
            </p>
            <div class="info">
                <p class="title">${orderItem.productName}</p>
                <p class="attr">
                    <span style="float: left;">${orderItem.sp1?eval.value} <#if orderItem.sp2 && (orderItem.sp2?eval)?size gt 0 >${orderItem.sp2?eval.value}</#if></span>
                    <span style="float: right;">x${orderItem.quantity}</span>
                </p>
            </div>
        </div>
    </#list>

</section>
<div class="pay-tips">
    <p>· 付款前请您务必于好友进行确认</p>
    <p>· 如果发生退款，已支付金额将会原路退回至您的账户</p>
    <p>. 付款成功状态可能会有延时，不影响具体支付</p>
</div>
<#if order.orderStatus==1 && (order.endTime && order.endTime?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss") gt .now?string("yyyyMMdd HH:mm:ss")?date("yyyyMMdd HH:mm:ss"))>
    <form name="pay-form" id="pay-form" action="/substitute/pay" method="post">
        <input type="hidden" name="id" value="${order.id}"/>
        <input type="hidden" name="sn" value="${(order.orderSn)}"/>
        <input type="hidden" name="orderType" value="order"/>
        <#if browser=='wechat'>
            <input type="hidden" name="paymentType" value="WXPAY"/>
        <#else>
            <input type="hidden" name="paymentType" value="ALIPAY"/>
        </#if>
        <button class="pay-button" type="submit" name="submit-button" id="pay-button">慷慨付款</button>
        <input type="hidden" name="paymethod" value="creditPay"/>
        <input type="hidden" name="defaultbank" value="ALIPAY"/>
    </form>
</#if>
<script>
</script>

<@m.page_footer />

