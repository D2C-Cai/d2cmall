<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="修改登录密码" title='修改登录密码' service='false' hastopfix='false'/>
<div class="section">
    <div class="form form-one">
        <form id="form_check" class="validate-form" action="/sms/check" redirect-url="/member/login"
              call-back="changepassword" method="post" success-tip="false">
            <input type="hidden" name="source" class="validate-account" value="${m.LOGINMEMBER.loginCode}"/>
            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
            <input type="hidden" name="type" class="validate-account" value="RETRIEVEPASSWORD" style="width:70%"/>
            <div class="form-item item-flex" id="choose-country">
                <label>国家和地区</label>
                <span class="note" style="color:#FD555D;max-width:70%;" id="country-code">中国大陆</span>
            </div>
            <div class="form-item item-flex">
                <label style="width:100%;"> 重置密码需要验证 <strong>(<span
                                class="choose-country-code">+86</span>) ${m.LOGINMEMBER.loginCode}</strong><br/>请点击下面按钮获取校验码</label>
            </div>
            <div class="form-item item-flex">
                <label>校验码</label>
                <input type="text" name="code" id="code" value="" class="input validate" title="校验码"
                       placeholder="请输入校验码"/>
                <button type="button" data-source="" data-type="RETRIEVEPASSWORD"
                        class="button button-white validate-send validate-button">获取校验码
                </button>
            </div>
            <div class="form-button">
                <button type="submit" name="" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
    <div class="form form-two display-none">
        <form id="modify-password" class="validate-form" action="/member/updatePassword.json"
              success-tip="密码修改成功！请重新登录！" redirect-url="/member/login" method="post">
            <div class="form-item">
                <input type="password" name="newPassword" id="new-password" value="" placeholder="输入您的8位以上新密码"
                       class="input validate" min-length="8" title="新密码"/>
            </div>
            <div class="form-item">
                <input type="password" name="confirm_password" id="confirm-password" value="" placeholder="请确认您的8位以上新密码"
                       class="input validate" compare-with="new-password" title="确认密码"/>
            </div>
            <div class="form-button">
                <button type="submit" name="" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var changepassword = function () {
        $('.form-one').hide();
        $('.form-two').show();
    }
</script>
<@m.page_footer />