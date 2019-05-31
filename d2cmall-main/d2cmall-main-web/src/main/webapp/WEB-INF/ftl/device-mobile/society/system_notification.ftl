<#assign FROMAPP=renderUserAgent()/>
<#if FROMAPP>
    <html>
    <head>
        <meta name="viewport"
              content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <meta name="format-detection" content="telephone=no"/>
        <script src="${static_base}/nm/js/lib/jquery.min.js?t=${refreshTimeStamp}"></script>
        <link type="text/css" href="${static_base}/nm/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
              media="screen"/>
    </head>
    <body>
</#if>
<style>
    /*系统通知*/
    body {
        background-color: #FFF
    }

    .sys_date {
        margin: 0 0 .9375rem;
    }

    .sys_date p {
        color: #999;
        font-size: 12px;
    }

    .sys_box {
        margin-bottom: 1.25rem
    }

    .sys_box .sys-conbox {
        background: #EEE;
        padding: .9375rem 0.625rem 1.25rem;
        border-radius: .5em;
        position;
        relative;
        margin: 0 .9375rem 0 .9375rem;
        position: relative;
    }

    .sys_box .sys-conbox:before {
        content: '';
        display: block;
        width: 0;
        height: 0;
        border-top: 12px solid transparent;
        border-right: 15px solid #EEE;
        border-bottom: 12px solid transparent;
        position: absolute;
        top: .9375rem;
        left: -.625rem
    }

    .sys_box .sys-conbox .info {
        line-height: 150%;
        color: #666;
        font-size: 13px;
    }

    .sys_box .sys-conbox .title {
        font-size: 14px;
        color: #666;
        margin-bottom: 0.625rem
    }
</style>

<div class="section" style="background:#fff;padding-top:1.25rem">
    <div class="text-center sys_date"><p>${message.createDate?string("yyyy/MM/dd")}</p></div>
    <div>
        <div class="sys_box">
            <div class="sys-conbox">
                <p class="title">尊敬的D2C会员</p>
                <p class="info">${message.content}</p>
            </div>
        </div>
    </div>
</div>
<#if FROMAPP>
    </body>
    </html>
</#if>