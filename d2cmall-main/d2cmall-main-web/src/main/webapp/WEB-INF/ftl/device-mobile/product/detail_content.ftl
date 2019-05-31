<#assign FROMAPP=renderUserAgent()/>
<#if RequestParameters.type =='summary'>
    <html>
    <head>
        <meta name="viewport"
              content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
        <meta name="apple-mobile-web-app-capable" content="yes"/>
        <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
        <meta name="format-detection" content="telephone=no"/>
        <script src="${static_base}/nm/js/lib/jquery.min.js?t=${refreshTimeStamp}"></script>
        <#if profile=='development' || profile=='test'>
            <link type="text/css" href="${static_base}/nm/css/swiper.min.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.base.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.element.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.component.css" rel="stylesheet" media="screen"/>
            <link type="text/css" href="${static_base}/nm/css/com.layout.css" rel="stylesheet" media="screen"/>
        <#else>
            <link type="text/css" href="${static_base}/nm/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
                  media="screen"/>
        </#if>
    </head>
    <body>
    <div class="source-material" style="width:100%;height:100%;background:#FFF;">
        <#if product.summary>
            <#assign summary=product.summary?eval>
            <#if (summary.summaryText?size gt 0||summary.summaryImg?size gt 0||summary.summaryVideo?size gt 0)>
                <#if summary.summaryText?size gt 0>
                    <#list summary.summaryText as val>
                        <div style="font-size:1em;line-height:1.5;">${val}</div>
                    </#list>
                </#if>
                <#if summary.summaryImg?size gt 0>
                    <#list summary.summaryImg as val>
                        <div><img src="${picture_base}${val}" alt="" style="width:100%;"/></div>
                    </#list>
                </#if>

                <#if summary.summaryVideo?size gt 0>
                    <#list summary.summaryVideo as val>
                        <a style="display:inline-block;width:100%;height:3em;background:green;text-align:center;color:#FFF;line-height:3em;"
                           href="//img.d2c.cn${val}">观看视频</a>
                    </#list>
                </#if>
            <#else>
                <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);font-size:1.5em;">暂无数据哦~
                </div>
            </#if>
        <#else>
            <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);font-size:1.5em;">暂无数据哦~</div>
        </#if>
    </div>
    </body>
    </html>
<#else>
    <#if FROMAPP||!RequestParameters.type>
        <html>
        <head>
            <meta name="viewport"
                  content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
            <meta name="apple-mobile-web-app-capable" content="yes"/>
            <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
            <meta name="format-detection" content="telephone=no"/>
            <script src="${static_base}/nm/js/lib/jquery.min.js?t=${refreshTimeStamp}"></script>
            <#if profile=='development' || profile=='test'>
                <link type="text/css" href="${static_base}/nm/css/swiper.min.css" rel="stylesheet" media="screen"/>
                <link type="text/css" href="${static_base}/nm/css/com.base.css" rel="stylesheet" media="screen"/>
                <link type="text/css" href="${static_base}/nm/css/com.element.css" rel="stylesheet" media="screen"/>
                <link type="text/css" href="${static_base}/nm/css/com.component.css" rel="stylesheet" media="screen"/>
                <link type="text/css" href="${static_base}/nm/css/com.layout.css" rel="stylesheet" media="screen"/>
            <#else>
                <link type="text/css" href="${static_base}/nm/css/common.css?t=${refreshTimeStamp}" rel="stylesheet"
                      media="screen"/>
            </#if>
        </head>
        <body>
    </#if>
    <style>
        .appproduct-detail img {
            width: 100% !important;
        }

        embed {
            width: 100%
        }
    </style>

    <div class="appproduct-detail">
        <#if product.introduction!=''  || product.introduction>
            <h4 class="product-detail-title text-center">商品介绍</h4>
            <div style="padding:12px 4% 15px;color:#666;font-size:0.6em;line-height:20px;">${product.introduction}</div>
        </#if>
        <#if product.attributes || product.sizeJson || product.tryOnReportJson  || product.categoryBannerPic>

            <#if product.categoryBannerPic>
                <a href="${product.categoryBannerUrl}"><img src="${picture_base}/${product.categoryBannerPic}"
                                                            style="width:100%!important;max-height:12rem"></a>
            </#if>
            <#if product.attributes && product.attributes!="null" && product.attributes!=''>
                <h4 class="product-detail-title text-center" style="text-align:center;margin-top:.3em">参数信息</h4>
                <div class="product-detail-attr">
                    <div id="product-attributes-div" data-val='${(product.attributes)!}'></div>
                </div>
            </#if>
            <#if product.sizeJson && product.sizeJson!="null" && product.sizeJson!=''>
                <h4 class="product-detail-title text-center" style="text-align:center;margin-top:.3em">尺码信息</h4>
                <div id="product-size-div" data-val='${(product.sizeJson)!}'></div>
            </#if>
            <#if product.tryOnReportJson && product.tryOnReportJson!="null" && product.tryOnReportJson!=''>
                <h4 class="product-detail-title" style="text-align:center;margin-top:.3em">试穿报告</h4>
                <div id="product-tryOnReport-div" data-val='${(product.tryOnReportJson)!}'></div>
            </#if>
        </#if>
        <#if product.mobileDesc?exists && product.mobileDesc!=''>
            <div style="margin-top:15px;line-height:1.5;">${product.mobileDesc}</div>
        <#else>
            <div style="margin-top:15px;line-height:1.5;">${(product.description)!}</div>
        </#if>
        <script>

            if ($("#product-attributes-div").size() > 0) {
                var attributes_val = eval("(" + $("#product-attributes-div").attr("data-val") + ")");
                var tab = "<table class='product-size-table'>";
                tab += "<tr><th>参数</th>";
                if (attributes_val != 'null') {
                    $.each(attributes_val, function (d, t) {
                        tab += "<th>" + t.name + "</th>";
                    })
                    tab += "</tr><tr><td></td>";
                    $.each(attributes_val, function (s, f) {
                        tab += "<td>" + f.value + "</td>";
                    })
                }
                tab += "</tr></table>";
                $("#product-attributes-div").html(tab);
            }
        </script>

        <script>
            if ($("#product-size-div").size() > 0) {
                var size_val = eval("(" + $("#product-size-div").attr("data-val") + ")");
                var tab = "<table class='product-size-table'>";
                tab += "<tr><th>尺码</th>";
                if (size_val != 'null') {
                    $.each(size_val.header, function (d, t) {
                        tab += "<th>" + t + "</th>";
                    })
                    tab += "</tr>";
                    $.each(size_val.data, function (s, f) {
                        tab += "<tr><td>" + s + "</td>";
                        $.each(f, function (m, n) {
                            tab += "<td>" + n + "</td>";
                        })
                        tab += "</tr>";
                    })
                }
                tab += "</table>";
                $("#product-size-div").html(tab);
            }
        </script>


        <script>
            if ($("#product-tryOnReport-div").size() > 0) {
                var tryOnReport_val = eval("(" + $("#product-tryOnReport-div").attr("data-val") + ")");
                var tab = "<table class='product-size-table'>";
                tab += "<tr><th>试穿报告</th>";
                if (size_val != 'null') {
                    $.each(tryOnReport_val.header, function (d, t) {
                        tab += "<th>" + t + "</th>";
                    })
                    tab += "</tr>";
                    $.each(tryOnReport_val.data, function (s, f) {
                        tab += "<tr><td>" + s + "</td>";
                        $.each(f, function (m, n) {
                            tab += "<td>" + n + "</td>";
                        })
                        tab += "</tr>";
                    })
                }
                tab += "</table>";
                $("#product-tryOnReport-div").html(tab);
            }
        </script>

    </div>
    <#if FROMAPP||!RequestParameters.type>
        </body>
        </html>
    </#if>
</#if>