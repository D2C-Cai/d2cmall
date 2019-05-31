<#import "templates/public_pc.ftl" as m>
<@m.page_header title=''/>
<@m.top_nav suspend=false />
<div class="main-body"
     style="background:url(//static.d2c.cn/common/c/images/brand_bg.jpg) center center no-repeat;background-size:cover;">
    <div class="main-wrap clearfix" style="margin:100px auto">
        <div class="likebrand-form arial" style="height:200px;text-align: center;">
            <h1 style="line-height:200px;"><#if ok>提交成功!<#else>对不起，提交失败！请重试。</#if></h1>
        </div>
    </div>
</div>
<@m.page_footer />