<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='成为D2C时尚合伙人' css='partner' service="false" />
<div class="join-partner-img">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_01.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_02.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_03.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_04.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_05.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_06.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_07.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_08.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_09.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_10.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_11.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_12.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_13.jpg" width="100%">
    <img src="//static.d2c.cn/miniapp/180119/partner-intro_14.jpg" width="100%">
</div>
<div class="submit-fix">
    <form action="/partner/invited" method="post" class="validate-form" id="partner-form" success-tip="加入合伙人成功">
        <input type="hidden" name="parentId" value="${RequestParameters.parent_id}"/>
        <button type="button" class="button-partner">成为合伙人</button>
    </form>
</div>
<script>
    $('.button-partner').click(function () {
        if (!_memberId) {
            userLogin();
        } else if (!_isD2C) {
            location.href = "/member/bind";
        } else if (isWeChat) {
            $('#partner-form').attr('redirect-url', '/member/home');
            $('#partner-form').submit();
        } else if (app_client) {
            $('#partner-form').attr('redirect-url', '/selfservice/partner');
            $('#partner-form').submit();
        }
    });
</script>
<@m.page_footer menu=false />