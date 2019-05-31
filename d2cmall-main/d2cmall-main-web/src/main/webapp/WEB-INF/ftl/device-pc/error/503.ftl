<#import "templates/public_pc.ftl" as m>
<@m.page_header title=''/>
<@m.top_nav suspend=false />
<div class="layout layout-response layout-warning">
    <div class="warning-cont">
        <i class="fa fa-info-circle"></i>
        <h1 style="padding-top:25px;">${result.message}</h1>
    </div>
</div>
<@m.page_footer />