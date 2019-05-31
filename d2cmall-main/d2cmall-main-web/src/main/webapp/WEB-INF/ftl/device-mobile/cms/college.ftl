<#import "templates/public_mobile.ftl" as m>
<@m.page_header  js='' css='college' keywords='全球好设计' description=''/>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">商学院</div>
    </div>
</header>
<div class="college-item clearfix" style="background:#FFF;padding-top:.5em;">
    <#list themeTag.list as value>
        <a href="/theme/tag/${value.id}?name=${value.name}" class="item-element">
            <img src="//img.d2c.cn/${value.pic}" width="100%"/>
            <span class="item-name">${value.name}</span>
        </a>
    </#list>
</div>
<div class="detail-container">

</div>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <a href="/theme/{{value.id}}" class="flex space-between item-gap">
        <div class="item-img"><img src="//img.d2c.cn{{value.pic}}" width="100%"/></div>
        <div class="item-cont">
            <div class="item-title">{{value.title}}</div>
            <div class="item-info">
                <span>{{formatDate(value.createDate)}}</span>
                <span class="float-right">新人买手</span>
            </div>
        </div>
    </a>
    {{/each}}
</script>
<script>
    $.ajax({
        url: '/theme/tag/9',
        type: 'get',
        data: {},
        dataType: 'json',
        success: function (data) {
            var html = template('list-template', data.themes);
            $('.detail-container').html(html);
        }
    });
    template.helper('formatDate', function (str) {
        var date = new Date(str);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        if (month < 10) month = '0' + month;
        if (day < 10) day = '0' + day;
        return year + "-" + month + "-" + day;
    });
</script>
