<#import "templates/public_pc.ftl" as m>
<@m.page_header title='d2c快抢' keywords="预售,设计师品牌,全球设计师集成平台,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" js='utils/unslider.min|utils/imagesloaded.pkgd.min|utils/masonry.pkgd.min'/>
<@m.top_nav />
<div style="text-align:center;padding:100px 0">
    <div style="display:inline-block;width:460px;">
        <p>请用微信扫一扫查看</p>
        <p><img id="qrcode"
                src="/picture/code?type=1&width=144&height=144&noLogo=true&&code=//m.d2cmall.com/flashpromotion/brand/session"
                width="144" alt="微信扫一扫"/><br/>微信扫一扫</p>
    </div>

    <div style="display:inline-block;width:460px;">
        <p>下载D2C app中查看</p>
        <p><img src="//static.d2c.cn/common/nc/css/img/qrcode_nav.png" alt="下载D2C app"><br/>下载app</p>
    </div>
</div>
<script>
    $('#qrcode').attr('src', '/picture/code?type=1&width=144&height=144&noLogo=true&&code=' + encodeURIComponent(location.href));
</script>
<@m.page_footer />