<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="设置" title='设置' service='false' hastopfix='false'/>
<div class="section">
    <div class="form">
        <#if !m.LOGINMEMBER.d2c>
            <a href="/member/bind" class="form-item">
                <i class="space"></i>绑定账号<i class="icon icon-arrow-right"></i>
            </a>
        </#if>
        <a href="/member/info" class="form-item">
            <i class="space"></i>修改个人信息<i class="icon icon-arrow-right"></i>
        </a>
        <#if m.LOGINMEMBER.d2c>
            <a href="/member/security" class="form-item">
                <i class="space"></i>修改登录密码<i class="icon icon-arrow-right"></i>
            </a>
        </#if>
        <a href="/address/list" class="form-item">
            <i class="space"></i>收货地址管理<i class="icon icon-arrow-right"></i>
        </a>
    </div>
</div>
<@m.page_footer />