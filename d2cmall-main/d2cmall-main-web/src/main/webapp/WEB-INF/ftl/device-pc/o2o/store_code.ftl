<#import "templates/public_pc.ftl" as m>
<@m.page_header title='实体店详情'/>
<@m.top_nav suspend=false />
<div class="main-body lazyload">
    ${description?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
</div>
<@m.page_footer />