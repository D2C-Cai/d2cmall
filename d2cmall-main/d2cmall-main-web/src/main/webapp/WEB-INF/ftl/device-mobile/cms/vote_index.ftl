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
    <div style="position:relative;width:100%">
        <#if voteDefGroupDto.wapCode><span class="img">${voteDefGroupDto.wapCode}</span></#if>
    </div>
    <div class="text-center padding" style="line-height:250%;padding-top:30%;">
        <form>
            <input type="hidden" name="defId" id="defId" value="${voteDefGroupDto.id}" action="/vote/" method="post"/>
            <#list voteDefGroupDto.list as voteDef>
                <span class="img"><img alt="${(voteDef.name)!}" src="${static_base}/m/img/blank100x45.png"
                                       data-image="${picture_base}${(voteDef.headPic)!}"/></span>
                <span class="title">${(voteDef.name)!}</span>
            </#list>
        </form>
    </div>
</div>
</body>
</html>