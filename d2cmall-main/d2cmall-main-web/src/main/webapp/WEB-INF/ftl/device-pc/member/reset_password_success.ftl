<#import "templates/public_pc.ftl" as m>
<@m.page_header title='重置密码成功' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-help lazyload" style="margin-top:20px;">
    <h1 style="margin:0 50px;padding:18px 65px;font:600 20px 'Microsoft Yahei','Pingfang SC','Helvetica Neue';border-bottom:1px solid #CCC">
        找回密码</h1>
    <div class="n_form form-info" style="width:667px;padding:5% 0 5% 0;">
        <div class="password step3_box" style="width:667px;height:52px;text-indent:-9999px">修改成功</div>
        <div class="change-success"><span class="success-type"></span><span>恭喜您修改成功密码,<span id="mes"
                                                                                            style="font-size:30px;vertical-align:initial">4</span>秒后将重新登录</span>
        </div>

    </div>
</div>
<div style="margin-top:20px;" class="layout layout-response layout-help lazyload">
    <div class="password-info" style="padding-left:115px;padding-bottom:30px">
        <h3>为什么要进行身份认证？</h3>
        <p style="margin-bottom:20px;">1.为保障您的账户信息安全，在变更账户中的重要信息时需要身份验证，感谢您的理解与支持。</p>
        <p>2.验证身份遇到问题？请提供用户名，手机号，历史发票，点击右侧联系我司<span class="red">在线客服</span>或者拨打<span class="red">${servertel}</span>咨询。
        </p>
    </div>
</div>
<script>
    function dsb() {
        var i = 4;
        var intervalid;
        intervalid = setInterval(function () {
            if (i == 0) {
                window.location.href = "/member/login";
                clearInterval(intervalid);
            }
            $('#mes').html(i);
            i--;
        }, 1000);
    }

    dsb();
</script>
<@m.page_footer />
