<#import "templates/public_pc.ftl" as m>
<@m.page_header title=''/>
<@m.top_nav suspend=false />
<div class="layout layout-response layout-warning">
    <div class="warning-cont">
        <i class="fa fa-info-circle"></i>
        <h1>对不起，内部服务器发生错误。</h1>
        <p>非常抱歉出现该提示，系统已经记录该错误。</p>
        <p>我们会根据记录来排查修复此错误。</p>
        <p>您也可以将此错误直接反馈给我们客服。</p>
        <p><a href="/" class="button button-s button-green">返回首页</a></p>
    </div>
</div>
<@m.page_footer />