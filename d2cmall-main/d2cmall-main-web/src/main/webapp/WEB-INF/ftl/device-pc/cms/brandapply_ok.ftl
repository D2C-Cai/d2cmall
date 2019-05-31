<#import "templates/public_pc.ftl" as m>
<@m.page_header title=''/>
<@m.top_nav suspend=false />
<div class="main-body"
     style="background:url(//static.d2c.cn/common/c/images/brand_bg.jpg) center center no-repeat;background-size:cover;">
    <div class="layout-response clearfix" style="margin:100px auto">
        <div class="brand-form arial">
            <p style="font-size:60px;text-align:center;"><#if result.status==1><span class="fa fa-check"
                                                                                     style="color:green"></span><#else>
                    <span class="fa fa-exclamation-triangle" style="color:orange"></span></#if></p>
            <p style="font-size:38px;text-align:center;font-family:'Microsoft Yahei';height:100px;"><#if result.status==1>申请成功，我们会尽快与您联系！<#else>对不起，申请失败！请重试。</#if></p>
        </div>
    </div>
</div>
<@m.page_footer />