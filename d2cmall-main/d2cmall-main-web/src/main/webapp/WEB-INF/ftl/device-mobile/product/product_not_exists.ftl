<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='出错啦！'/>
<div class="wrap-main">
    <div class="login-box" style="line-height:2em;">
        <p style="font-size:3em;color:#fc8705;line-height:2em;"><span class="icon icon-exclamation-sign"></span></p>
        <p>该商品可能已经下架或删除，请点击"返回"首页浏览其他商品。</p>
        <p>&nbsp;</p>
        <p>
            <button type="button" name="return" onclick="location.href='/';" style="width:50%;">返回首页</button>
        </p>
        <p>&nbsp;</p>
    </div>
</div>
<hr/>
<@m.page_footer />