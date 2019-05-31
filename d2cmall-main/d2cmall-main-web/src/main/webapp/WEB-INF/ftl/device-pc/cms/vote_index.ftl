<html>
<head>
    <title>${result.datas.voteDefGroupDto.name}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport"
          content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui"/>
    <meta http-equiv="cleartype" content="on"/>
    <#if profile=='development' || profile=='test'>
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
    <#if css?length gt 0>
        <#list css?split("|") as s>
            <link type="text/css" href="${static_base}/nc/css/${s}.css?t=${refreshTimeStamp}" rel="stylesheet"
                  media="screen"/>
        </#list>
    </#if>
    <!--[if lt IE 9]>
    <link type="text/css" href="${static_base}/nc/css/ie.css" rel="stylesheet" media="screen"/>
    <![endif]-->
    <#if profile=='development' || profile=='test'>
        <script type="text/javascript" src="${static_base}/nc/js/lib/jquery.1.83.js"></script>
        <script type="text/javascript" src="${static_base}/nc/js/lib/template.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/jquery.cookie.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript"
                src="${static_base}/nc/js/utils/jquery.autocomplete.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/utils/plugin.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript" src="${static_base}/nc/js/modules/common.js?t=${refreshTimeStamp}"></script>
        <script type="text/javascript"
                src="${static_base}/nc/js/utils/jquery.fileupload.js?t=${refreshTimeStamp}"></script>
    <#else>
        <script type="text/javascript" src="${static_base}/nc/js/compress.js?t=${refreshTimeStamp}"></script>
    </#if>
    <#if js?length gt 0>
        <#list js?split("|") as s>
            <script type="text/javascript" src="${static_base}/nc/js/${s}.js?t=${refreshTimeStamp}"></script>
        </#list>
    </#if>
    <#if css?length gt 0>
        <#list css?split("|") as s>
            <link rel="stylesheet" href="${static_base}/nc/css/${s}.css?t=${refreshTimeStamp}"/>
        </#list>
    </#if>
</head>
<body>
<!--<#assign voteDefGroupDto = "${result.datas.voteDefGroupDto}"> -->
<div class="section">
    ------------------------------投票大会-----------------------------
    <tr>

        <td>
            <div>
                <form name="vote-form" id="vote-form" class="validate-form"
                      action="/vote/commit/${result.datas.voteDefGroupDto.id}" method="post">
                    <#list result.datas.voteDefGroupDto.voteDefs as voteDef>
                    <img src="//img.d2c.cn${voteDef.headPic}"/>
                    <br/>
                    姓名：${voteDef.name}
                    <br/>
                    <input type="checkbox" value="${voteDef.id}" name="id"/>
                    <br/>
        <td></td>

        </#list>
        <button type="button" id="ttt">确认投票</button>
        </form>
</div>
<td>

    </tr>


    </div>
    <script>


        $("#ttt").click(function () {
            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    $('body').data('function', '$(".validate-form").submit()');
                    userLogin();
                } else {
                    $(".validate-form").submit();
                }
            });

        })
    </script>
</body>
</html>