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
    .appIntro img {
        width: 100%
    }
</style>
<#if brand.appIntro>
    <div class="clearfix appIntro">
        ${brand.appIntro}
    </div>
</#if>

<#if FROMAPP>
    </body>
    </html>
</#if>