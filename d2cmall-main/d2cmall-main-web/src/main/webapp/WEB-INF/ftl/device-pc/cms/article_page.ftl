<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${article.title}' keywords='预售,预售优惠,服装设计师,原创品牌' description='D2C全球好设计'/>
<@m.top_nav />
<#if article.content>
    <div class="clearfix">
        ${article.content}
    </div>
<#elseif article.mobileContent>
    <div class="lazeload clearfix pc-content-mobile-view">
        <div class="qrcode-box">
            <p>请使用手机浏览，体验更好</p>
            <p><img id="qrcode" src="/picture/code?type=1&width=144&height=144&noLogo=true&&code=//m.d2cmall.com"
                    width="144" alt="微信扫一扫"/><br/>微信扫一扫</p>
        </div>
        ${article.mobileContent}
    </div>
    <script>
        $('#qrcode').attr('src', '/picture/code?type=1&width=144&height=144&noLogo=true&&code=' + encodeURIComponent(location.href));
    </script>
</#if>
<@m.page_footer />
