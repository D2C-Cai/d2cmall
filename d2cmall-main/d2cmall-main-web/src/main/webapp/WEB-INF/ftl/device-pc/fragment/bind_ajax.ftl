<div class="account-wrap" style="margin-left:-5px;width:430px;padding:0;border:none;">
    <div class="account-title login-title" data-login="normal">
        <a data-type="message" href="javascript:;" style="float:none;margin:0 auto;color:#000;">帐号绑定</a>
    </div>
    <div class="login-type">
        <div id="login-message">
            <form name="quicky-login" class="validate-form" action="/member/bind" method="post"
                  call-back="bindSuccessReturn" style="border:none;padding:0 16px 37px 16px;">
                <div class="account-input account-now">
                    <input type="hidden" name="nationCode" disabled="disabled" value="86" class="mobile-code"/>
                    <span class="choose-country" id="choose-country"
                          style="position:absolute;line-height:36px;top:10px;"><strong id="mobile-code">+86</strong><em
                                class="fa fa-caret-down" style="top:11px;"></em></span>
                    <span class="input-tip" style="left:80px">请输入手机号</span>
                    <input type="text" name="loginCode" value="" class="validate-account login-l" alt="login"
                           data-rule="mobile" autocomplete="new-password" style="padding-left:80px;"/>
                </div>
                <div class="account-input" style="width:65%">
                    <span class="input-tip">输入短信验证码</span>
                    <input type="text" name="code" value=""/>
                    <button type="button" data-source="" data-type="MEMBERMOBILE" data-register="true"
                            class="button button-grey validate-send pn">获取验证码
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="msg "<#if result?exists && result.status lt 0> style="display:block;padding:0 20px"</#if>><#if result?exists && result.status lt 0>${result.message}</#if></div>
    <div class="account-btn" style="padding:0 20px 40px">
        <input type="hidden" name="path" value="${path}"/>
        <input value="绑定" class="input-sub" type="button"/>
    </div>


</div>

<script type="text/javascript">
    $('.account-input input').focus(function () {
        $(this).prev('.input-tip').hide();
    }).blur(function () {
        if ($(this).val() == "") {
            $(this).prev('.input-tip').show();
        }
    });

    $('.account-input .input-tip').click(function () {
        $(this).next('input').focus();
    });


</script>