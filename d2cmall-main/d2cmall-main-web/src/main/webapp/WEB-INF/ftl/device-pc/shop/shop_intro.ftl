<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${designer.name} - ${designer.designers} - 设计师品牌' keywords="${designer.name},${designer.designers},全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店,买手店" description="${designer.intro?replace('<.*?>','','r')}" />
<@m.top_nav channel="showroom" />
<div class="lazyload">
    ${designer.moreIntro?replace('alt="" src=','alt="" src="${static_base}/c/images/space.gif" data-image=')}
</div>
<@m.page_footer />  