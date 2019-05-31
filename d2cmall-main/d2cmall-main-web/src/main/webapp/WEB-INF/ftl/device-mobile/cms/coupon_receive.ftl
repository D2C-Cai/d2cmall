<html>
<head>
    <title>${result.datas.coupon.name}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
    <meta name="description" content="${result.datas.coupon.name}"/>
    <meta name="keywords" content="${result.datas.coupon.name}"/>
    <meta http-equiv="cleartype" content="on"/>
    <meta name="apple-touch-fullscreen" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta http-equiv="Expires" content="-1"/>
    <meta http-equiv="pragram" content="no-cache"/>
    <#if profile=='development'>
        <link type="text/css" href="${static_base}/nm/css/com.base.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nm/css/com.element.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nm/css/com.component.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nm/css/com.layout.css" rel="stylesheet" media="screen"/>
    <#else>
        <link type="text/css" href="${static_base}/nm/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
              media="screen"/>
    </#if>

    <#if profile=='development' || profile=='test'>
        <script src="${static_base}/nm/js/lib/jquery.min.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/lib/template.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/utils/jquery.cookie.min.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/utils/unveil.min.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/utils/iscroll-lite.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/modules/function.js?t=${refreshTimeStamp}"></script>
        <script src="${static_base}/nm/js/modules/common.js?t=${refreshTimeStamp}"></script>
    <#else>
        <script src="${static_base}/nm/js/compress.js?t=${refreshTimeStamp}"></script>
    </#if>

    <#if browser=='wechat'>
        <script src="//res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
        <script src="//static.d2c.cn/light/js/wechat.js"></script>
    </#if>
    <#if css?length gt 0>
        <#list css?split("|") as s>
            <link rel="stylesheet" href="${static_base}/nm/css/${s}.css?t=${refreshTimeStamp}"/>
        </#list>
    </#if>
</head>
<body style="background:#000">
<style>
    .full-screen img {
        width: 100%
    }
</style>
<div style="position:relative;width:100%" class="full-screen">
    <div class="top-downloadl">
        <div style="position:fixed;z-index:22;width:100%;" class="img-downl">
            <a href="//t.cn/RLstL36" style="display:block;"><img src="//static.d2c.cn/img/other/download.png"
                                                                 style="width:100%;"/></a>
        </div>
    </div>
    <#if result.datas.coupon.wapCode?exists && result.datas.coupon.wapCode!="">${result.datas.coupon.wapCode}<#else><img
            src="//static.d2cmall.com/img/topic/160826/testest.png" width="100%"></#if>
</div>


<div class="section">
    <div class="text-center">
        <#if (result.datas.coupon.over)>
        <div style="padding-top:7.5px;">
            <a href="/" class="button button-l button-red">优惠券已经过了领用时间了，下次早点来</a>
        </div>
        <#else>
        <div>

        </div>
        <#if (result.datas.member?exists && result.datas.member.d2c)>
        <form id="coupon-form" class="validate-form" action="${result.datas.receiveUrl}" method="post"
              call-back="receviesuccess" success-tip="false" data-go="${result.datas.coupon.redirectUrl}">
            <div class="coupon-main clearfix" style="height:80px">
                <div class="coupon-left" style="height:100%">

                    <#if result.datas.coupon.type=="DISCOUNT">
                        <p style="font-size:20px;padding-top:15px">折扣 ${result.datas.coupon.amount/10}</strong></p>
                    <#else>
                        <p style="font-size:20px;padding-top:15px">&yen;<strong>${result.datas.coupon.amount}</strong>
                        </p>
                    </#if>
                    <p style="font-size:10px;">满${result.datas.coupon.needAmount}元使用</p>
                </div>
                <div class="coupon-right">
                    <p style="font-size:15px">${result.datas.coupon.name}</p>
                    <p style="font-size:10px">有效期：${(result.datas.coupon.enableDate?string("yy/MM/dd"))!}
                        - ${(result.datas.coupon.expireDate?string("yy/MM/dd"))!}</p>
                </div>
                <input type="hidden" name="couponId" value="${result.datas.coupon.id}"/>
            </div>
            <#else>
            <form id="coupon-form" class="validate-form" action="${result.datas.receiveUrl}" method="post"
                  call-back="receviesuccess" success-tip="false" data-go="${result.datas.coupon.redirectUrl}">
                <div class="form-item">
                    <input type="hidden" name="code" id="code" value="${result.datas.code}"/>
                    <input type="text" name="mobile" id="mobile" value="" class="input validate validate-account"
                           title="手机号码" placeholder="请输入手机号码"
                           style="height:40px;display:block;width:100%;border-radius:3px;text-align:center;font-size:15px;margin-bottom:10px;padding:12px 0;line-height:120%"/>
                    <!--input type="text" name="code" id="code" value="" class="input validate" title="校验码" placeholder="请输入校验码"  style="height:70px;line-height:70px;border-radius:8px;text-align:center;font-size:22px;width:64.5%;margin-right:3.45%;padding:22px 0"/-->
                    <!--button type="button" data-source="COUPON" data-type="COUPON" class="button button-white validate-send validate-button" style="height:70px;line-height:70px;border-radius:8px;text-align:center;font-size:22px;width:31%;padding:0">获取校验码</button-->
                </div>
                </#if>
                <div class="coupon-button" style="margin-top:10px;">
                    <button class="button button-red" type="submit"
                            style="width: 100%;line-height:105%;font-size: 15px;height:40px;line-height:40px;border-radius:3px">
                        立即领取
                    </button>
                </div>
                </#if>
            </form>
    </div>
</div>
<script>
    var iw = $('.img-downl img').width();
    var ih = parseInt(iw) * 0.16;
    var it = (parseInt(iw) / 750 * 855 + ih) + 'px';
    $('.top-downloadl').height(ih);
    $('.section').css({
        "position": "absolute",
        "width": "77%",
        "left": "50%",
        "margin-left": "-38.5%",
        "height": "125px",
        "top": it
    });

    var receviesuccess = function () {
        var dourl = $('.validate-form').attr('data-go');
        jGoto('领取成功！', function (r) {
            if (r) {
                location.href = dourl;
            }
        });
    }
</script>
</body>
</html>