<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='设计师品牌' back="设计师列表" url="/showroom" title='分类详情'   keywords="设计师现货区,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<#if !m.FROMAPP>
    <span class="goup"></span>
</#if>
<div class="section text-center lazyload">
    <div class="list clearfix">
        <#list pager.list as designer>
            <#if designer.introPic !='' && designer.introPic?length gt 0>
                <a href="/showroom/designer/${designer.id}" class="item item-flex item-gap"><span class="img"><img
                                alt="${designer.name}" src="${picture_base}${(designer.introPic)!}"/></span></a>
            </#if>
        </#list>
    </div>
</div>


<@m.page_footer menu=true />