<#assign LOGINMEMBER=loginMember()/>
<html>
<head>
    <title>D2C全球好设计</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
    <meta name="keywords" content="D2C,d2c官网,D2C官网,潮流风尚,前沿艺术,个性设计,时装设计师平台,国际五大时装周T型台上最新的流行元素,欧洲一线设计师品牌,原创设计,设计师品牌,买手店"/>
    <meta name="description" content="D2C是一家集潮流风尚、前沿艺术、个性设计为一体的设计师集成平台。"/>
    <meta http-equiv="cleartype" content="on"/>
    <meta name="apple-touch-fullscreen" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta http-equiv="Expires" content="-1"/>
    <meta http-equiv="pragram" content="no-cache"/>
    <link rel="stylesheet" href="${static_base}/m/css/common.css?150515"/>
    <script src="//static.d2c.cn/common/nm/js/compress.js?t=2016113001"></script>
</head>
<body style="background:url(//static.d2cmall.com/other/qrode/img/share.jpg) no-repeat top center;background-size:contain;background-color:#000">
<#if LOGINMEMBER.id!=null>
    <div>

        <div class="partern-bind" style="overflow:hidden;margin-top:50%;text-align:center;">
            <div style="margin: 0 auto;width:45.5%">
                <a href="javascript:;" style="padding:5px;display:block;position:relative;">
                    <img src="//static.d2cmall.com/img/other/d2clogo.jpg"
                         style="position:absolute;width:20%;left:50%;margin-left:-10%;top:50%;margin-top:-10%;"/>
                    <img src="${qCodePath}" width="100%">
                    <!--<img src="//static.d2cmall.com/other/qrode/img/wechat_service.jpg" alt="微信服务号:d2cmall" width="100%"> -->
                </a>
            </div>
            <div style="margin-top:17%;text-align:center;font-size:19px;color:#fff">
                快让小伙伴与你捆绑吧
            </div>
            <div style="margin:5% auto;width:38%;height:22px;background:#ED3D48;color:#FFF;line-height:22px;padding:5px 0;">
                ${recCode}
            </div>
        </div>
    </div>
</#if>

<script>
    var u = navigator.userAgent;
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    var timeout;

    function say() {
        try {
            var messages = {"handlefunc": "temp_share", "type": "my_share", "code": "${recCode}"}
            console.log(eval(messages))
            window.webkit.messageHandlers.d2cinit.postMessage(eval(messages));
        } catch (err) {
            console.log(err)
        }
    }

    $("body").on('touchstart', function () {
        timeout = setTimeout(function () {
            if (isiOS) {
                say();
            }
        }, 100);
    });

</script>

</body>
</html>