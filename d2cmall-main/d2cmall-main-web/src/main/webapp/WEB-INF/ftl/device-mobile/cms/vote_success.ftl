<html>
<head>
    <title>${result.message}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
    <meta http-equiv="cleartype" content="on"/>
    <#if profile=='development'>
        <link type="text/css" href="${static_base}/nm/css/com.base.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nm/css/com.element.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nm/css/com.component.css" rel="stylesheet" media="screen"/>
        <link type="text/css" href="${static_base}/nm/css/com.layout.css" rel="stylesheet" media="screen"/>
    <#else>
        <link type="text/css" href="${static_base}/nm/css/common.css??t=${refreshTimeStamp}" rel="stylesheet"
              media="screen"/>
    </#if>
    <#if css?length gt 0>
        <#list css?split("|") as s>
            <link rel="stylesheet" href="${static_base}/nm/css/${s}.css?t=${refreshTimeStamp}"/>
        </#list>
    </#if>
</head>
<body>
<div class="section">
    <div class="text-center padding" style="line-height:250%;padding-top:30%;">
        <div class="icon <#if result.status==1>icon-ok<#else>icon-excam</#if>"></div>
        <div class="<#if result.status==1>green<#else>red</#if>"><strong>${result.message}</strong></div>
    </div>
</div>
</body>
</html>