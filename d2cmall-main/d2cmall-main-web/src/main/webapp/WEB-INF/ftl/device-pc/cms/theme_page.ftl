<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${theme.title}' keywords='D2C全球好设计' description='D2C全球好设计'/>
<#if theme.wapContent>
    <div class="lazeload clearfix pc-content-mobile-view">
        <div class="qrcode-box">
            <p>请使用手机浏览，体验更好</p>
            <p><img id="qrcode" src="/picture/code?type=1&width=144&height=144&noLogo=true&&code=//m.d2cmall.com"
                    width="144" alt="微信扫一扫"/><br/>微信扫一扫</p>
        </div>
        ${theme.wapContent}
    </div>
    <script>
        $('#qrcode').attr('src', '/picture/code?type=1&width=144&height=144&noLogo=true&&code=' + encodeURIComponent(location.href));
    </script>
<#elseif theme.pcContent>
    ${theme.pcContent}
</#if>
<@m.page_footer />