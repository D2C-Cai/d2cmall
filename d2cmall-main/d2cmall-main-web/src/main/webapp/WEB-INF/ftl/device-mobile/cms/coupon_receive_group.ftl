<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='领取优惠券' service='false' hastopfix='false'/>
<style>
    .coupon-head {
        text-align: center;
        position: relative;
    }

    .coupon-head h1 {
        position: absolute;
        top: 1.5em;
        font-weight: 200;
        left: 0;
        right: 0;
        text-align: center;
        font-size: 3em;
        color: #FFF;
        color: #FFF;
    }

    .coupon-head img {
        width: 100%;
        margin-top: -1.5em;
    }

    .coupon-form {
        text-align: center;
        padding: 2em;
    }

    .coupon-form-item {
        padding-bottom: 1em
    }

    .coupon-form-item input {
        border-radius: 3em;
        font-size: 1em;
        padding: 0.7em 0;
        color: #EF5438;
        font-weight: bold;
        background: #FFF;
        text-align: center;
        border: 1px solid #EF5438;
        width: 100%;
    }

    .coupon-form-item input::placeholder {
        line-height: 140%;
        font-weight: normal;
    }

    .coupon-form-item button {
        border-radius: 3em;
        line-height: 2.6em;
        width: 100%;
        border: none;
        background: #EF5438;
        color: #FFF;
    }
</style>
<#if (result.datas.coupon.over)>
    <script>
        jAlert('对不起，活动已于${result.datas.coupon.claimEnd?string('yyyy年MM日dd日')}结束,无法领取哦！', function () {
            location.href = '/'
        });
    </script>
</#if>
<#if result.datas.coupon.wapCode?exists && result.datas.coupon.wapCode!="">
    ${result.datas.coupon.wapCode}
<#else>
    <div class="coupon-head">
        <h1>${result.datas.coupon.name}</h1>
        <img src="//static.d2c.cn/img/other/coupon_head.png?0.1"/>
    </div>
</#if>

<div class="coupon-form">
    <form id="coupon-form" class="validate-form" action="/coupondef/batch/receive/mobile/${result.datas.coupon.id}"
          method="post" call-back="receviesuccess" success-tip="false" data-go="${result.datas.coupon.redirectUrl}">
        <div class="coupon-form-item"><input name="mobile" placeholder="请输入手机号"
                                             value="<#if (result.datas.member?exists)>${result.datas.member.loginCode}</#if>"/>
        </div>
        <div class="coupon-form-item">
            <button type="submit">立刻领取</button>
        </div>
    </form>
</div>

<script>
    var receviesuccess = function () {
        var dourl = $('.validate-form').attr('data-go');
        jGoto('领取成功！', function (r) {
            if (r) {
                location.href = dourl;
            }
        });
    }
</script>

<@m.page_footer menu=true />