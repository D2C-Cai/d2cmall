<#assign refreshTimeStamp=renderStaticTimeStamp()/>
<html xmlns="//www.w3.org/1999/xhtml">
<head>
    <title>${result.datas.product.name}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#if profile=='development'>
        <link type="text/css" href="${static_base}/nc/css/com.base.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.iconfont.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.layout.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.element.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.component.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.home.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.crowd.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.showroom.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.product.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.user.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.order.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.star.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.custom.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.store.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/page.other.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nc/css/com.response.css" rel="stylesheet" media="screen"/>
    <#else>
        <link type="text/css" href="${static_base}/nc/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
              media="screen"/>
    </#if>
    <#if profile=='development'>
        <script type="text/javascript" src="${static_base}/nc/js/lib/jquery.1.83.js"></script>
        <script type="text/javascript" src="${static_base}/nc/js/lib/template.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/jquery.cookie.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript"
                src="${static_base}/nc/js/utils/jquery.autocomplete.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/modules/common.js?t=${refreshTimeStamp}"></script>
    <#else>
        <script type="text/javascript" src="${static_base}/nc/js/compress.js?t=${refreshTimeStamp}"></script>
    </#if>
    <style>
        body {
            background: #f9f9f9;
            width: 350px;
            min-width: 350px;
            padding: 5px;
        }

        h2 {
            font-size: 14px;;
            margin: 0;
            padding: 10px 0;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            border-spacing: 0;
        }

        table td {
            padding: 5px 8px;
            font-family: Arial;
            font-size: 12px;
        }

        table td .title {
            margin-bottom: 7px;
            height: 29px;
            overflow: hidden;
            line-height: 130%;
        }

        table td .grey {
            margin-bottom: 7px;
        }

        .tdbg td {
            background: #f6fdff
        }

        .current-price {
            font-size: 16px;
            color: #FF6600;
        }
    </style>
    <script>
        var _server_time = '${.now?string("yyyy/MM/dd HH:mm:ss")}';
    </script>
</head>

<body>
<table>
    <tr>
        <td class="text-center"><a href="//www.d2cmall.com/product/${result.datas.product.id}" target="_blank"><img
                        src="${picture_base}${result.datas.product.productImageList[0]}!300" width="250"/></a></td>
    </tr>
    <tr>
        <td>
            <p><a href="//www.d2cmall.com/product/${result.datas.product.id}"
                  target="_blank">${result.datas.product.name}</a></p>
            <p>状态：
                【<#if result.datas.product.status==3>到货通知
            <#else>
                <#if result.datas.product.mark lt 1> 已下架
                <#elseif result.datas.product.store lte 0>已售完
                <#else>正常
                    <#if result.datas.product.subscribe == 1>，可门店预约 </#if>
                </#if>
                </#if>】
            </p>
            <p>货号：${result.datas.product.inernalSn}</p>
            <#if result.productPromotion?exists>
                <p class="product-promotion">${result.productPromotion.name}</p>
            </#if>
            <p>
                售价：<strong
                        class="current-price">&yen; <#if result.datas.product.salePrice lte 0>????.??<#else>${(result.datas.product.salePrice?string("currency")?substring(1))!}</#if></strong>&nbsp;&nbsp;&nbsp;
                吊牌价 &yen;${(result.datas.product.originalPrice)!}
            </p>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <p>
                颜色：<#if result.datas.product.salesproperty><#list result.datas.product.salesproperty?eval.sp1 as p>${p.value}&nbsp;&nbsp;</#list></#if></p>
            <p>
                尺码：<#if result.datas.product.salesproperty><#list result.datas.product.salesproperty?eval.sp2 as p>${p.value}&nbsp;&nbsp;</#list></#if></p>
        </td>
    </tr>
    <#if result.datas.product.remark>
        <tr>
            <td colspan="2">
                <p style="color:#FF3300;">${(result.datas.product.remark)!}</p>
            </td>
        </tr>
    </#if>
</table>
</body>
</html>