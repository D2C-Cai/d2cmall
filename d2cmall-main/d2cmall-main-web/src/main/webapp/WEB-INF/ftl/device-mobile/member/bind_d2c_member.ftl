<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="绑定D2C账号" title='绑定D2C账号' service='false' hastopfix='false'/>
<div class="section">
    <div class="tips tip-warining">通过第三方登录的会员请绑定D2C账户</div>
    <div class="form">
        <#if !m.LOGINMEMBER.d2c>
            <form class="validate-form" action="/member/bind" redirect-url="${(path)!'/'}" method="post">
                <input type="hidden" name="openId" value="${member.openId}"/>
                <input type="hidden" name="source" value="${member.source}"/>
                <input type="hidden" name="" value="中国大陆" class="country-code"/>
                <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
                <div class="form-item item-flex" id="choose-country">
                    <label>国家和地区</label>
                    <span class="note" style="color:#FD555D;max-width:70%;"><em id="country-code">中国大陆</em>(<em
                                class="choose-country-code">+86</em>)</span>
                </div>
                <div class="form-item">
                    <input type="text" name="loginCode" id="loginCode" value="" class="input validate validate-account"
                           title="账号" placeholder="请输入你的D2C账号(邮箱或手机)"/>
                </div>
                <div class="message"
                     style="background:#FFFFF0;width:100%;box-sizing:border-box;border:1px dotted #FF3300;font-size:0.6em;padding:0.8em;margin-top:0.1em;display:none;z-index:9"></div>
                <div id="type" data-kaga="has" style="display:none"></div>
                <div class="form-item">
                    <input type="text" name="code" id="code" value="" class="input validate" title="校验码"
                           placeholder="请输入校验码"/>
                    <button type="button" data-source="" data-type="MEMBERMOBILE"
                            class="button button-white validate-send validate-button">获取校验码
                    </button>
                </div>
                <div class="password-item form-item" style="display:none">
                    <input type="password" name="password" value="" class="input" id="password" title="密码"
                           placeholder="请输入8-20位字母数字密码"/>
                </div>
                <div class="password-item form-item" style="display:none">
                    <input type="password" name="rePassword" value="" class="input" id="rePassword" title="确认密码"
                           placeholder="请确认输入的密码"/>
                </div>
                <div class="form-button">
                    <button type="button" name="sure-button" id="sure-button" class="button button-l button-red">确定
                    </button>
                </div>
            </form>
        <#else>
            <p style="text-align:center;line-height:50px;">你已经绑定过账号了</p>
        </#if>
    </div>
</div>
<script>
    $('#loginCode').blur(function () {
        var obj = $('#loginCode');
        if (obj.val() != '') {
            $.post('/member/checkLoginCode?' + new Date().getTime(), {'code': obj.val()}, function (data) {
                if (data.result.status == -1) {
                    $('.password-item').hide();
                    obj.parent().next('div').show().html('该帐号已注册D2C帐号，不需要设置密码');
                    $('body').data('mustPassword', false);
                } else {
                    $('.password-item').show();
                    obj.parent().next('div').hide();
                    $('body').data('mustPassword', true);
                }
            }, 'json');
        }
    });

    $('#sure-button').on('click', function () {
        if ($('body').data('must-password') == true) {
            if ($('#password').val().length < 8 || $('#password').val().length > 20) {
                $.flashTip({position: 'center', type: 'error', message: '请输入8-20位密码'});
                return false;
            }
            if ($('#rePassword').val() != $('#password').val()) {
                $.flashTip({position: 'center', type: 'error', message: '两次密码不一致'});
                return false;
            }
        }
        $('.validate-form').submit();
    })

</script>


<@m.page_footer />