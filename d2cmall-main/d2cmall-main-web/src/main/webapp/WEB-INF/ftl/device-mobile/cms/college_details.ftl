<#import "templates/public_mobile.ftl" as m>
<@m.page_header  js='' css='college' keywords='全球好设计' description=''/>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">${RequestParameters.name}</div>
    </div>
</header>
<div class="detail-container">
    <#list themes.list as value>
        <a href="/theme/${value.id}" class="flex space-between item-gap">
            <div class="item-img"><img src="//img.d2c.cn${value.pic}" width="100%"/></div>
            <div class="item-cont">
                <div class="item-title">${value.title}</div>
                <div class="item-info">
                    <span>${value.createDate?string("yyyy-MM-dd")}</span>
                    <span class="float-right">${RequestParameters.name}</span>
                </div>
            </div>
        </a>
    </#list>
</div>
