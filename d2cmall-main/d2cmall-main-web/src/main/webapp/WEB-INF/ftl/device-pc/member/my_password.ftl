<#import "templates/public_pc.ftl" as m>
<#assign LOGINMEMBER=loginMember()/>
<@m.page_header title='修改密码 - 个人中心' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="security"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1> 修改登录密码</h1>
        <div class="form-info n_form  text-center">
            <ul class="step">
                <div class="step-count">
                    <li class=" step_box">
                        <form name="form_check" class="validate-form" action="/sms/check" redirect-url="/member/login"
                              method="post" call-back="first" success-tip:
                        "false">
                        <div class="password step1_box st">
                            步骤1
                        </div>
                        <div class="form-item form-item-vertical" style="margin-top:100px;margin-left:-120px">
                            <label>账户</label>
                            <span style="width:30%;text-align:left;"><strong
                                        class="mymobile">${LOGINMEMBER.loginCode}</strong></span>
                        </div>
                        <div class="form-item form-item-vertical" style="margin-left:-30px">
                            <label>收到的验证码</label>
                            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
                            <input type="text" class="input input-l" name="code" value="" placeholder="验证码"/>
                            <input type="hidden" name="source" class="validate-account" value="${LOGINMEMBER.loginCode}"
                                   style="width:70%"/>
                            <input type="hidden" name="type" class="validate-account" value="RETRIEVEPASSWORD"
                                   style="width:70%"/>
                            <button type="button" data-source="" data-type="Member"
                                    class="button button-gray validate-send button-l nb">获取验证码
                            </button>
                        </div>
                        <div class="form-button">
                            <button type="sumbit" class="button">提交</button>
                        </div>
                        </form>
                    </li>
                    <li class="step_box">
                        <form name="form_password" class="validate-form" action="/member/updatePassword.json"
                              redirect-url="/member/login" method="post" call-back="second" success-tip:
                        "false">
                        <div class="password step2_box st">
                            步骤2
                        </div>
                        <div class="form-item form-item-vertical">
                            <label>输入新密码</label>
                            <input type="password" name="newPassword" id="new-password" value="" maxlength="20"
                                   class="input input-l" placeholder="8-20个字母、数字或者符号"/>
                            <div class="tip tip-validate" data-target="new-password" data-rule="sixDigit"></div>
                        </div>
                        <div class="form-item form-item-vertical">
                            <label>确认新密码</label>
                            <input type="password" name="confirm_password" id="confirm-password" value="" maxlength="20"
                                   class="input input-l" placeholder="这里要重复输入一下你的密码"/>
                            <div class="tip tip-validate" data-target="confirm-password"
                                 compare-with="new-password"></div>
                        </div>
                        <div class="form-item form-item-vertical" style="margin-left:120px;">
                            <label>验证码</label>
                            <input type="text" name="randomCode" id="confirm-pic" class="input input-l" value=""
                                   placeholder="请输入验证码"/>
                            <img src="/randomcode/getcode" alt="" title="点击换一个验证码" class="validate-img"/>
                            <span class="change-img text-left">看不清？</br><span class="red">换一张</span></span>
                            <div class="tip tip-validate" data-target="confirm-pic" data-rule="empty"></div>
                        </div>
                        <div class="form-button">
                            <button type="sumbit" class="button">提交</button>
                        </div>
                        </form>
                    </li>
                    <li class="step_box">
                        <div class="password step3_box st">
                            步骤3
                        </div>
                        <div class="change-success"><span class="success-type"></span><span>恭喜您修改成功密码,<span id="mes"
                                                                                                            style="font-size:30px;vertical-align:initial">4</span>秒后将重新登录</span>
                        </div>
                    </li>
                </div>
            </ul>


        </div>
        <div class="grey-content"></div>
        <div class="password-info">
            <h3>为什么要进行身份认证？</h3>
            <p style="margin-bottom:20px;">1.为保障您的账户信息安全，在变更账户中的重要信息时需要身份验证，感谢您的理解与支持。</p>
            <p>2.验证身份遇到问题？请提供用户名，手机号，历史发票，点击右侧联系我司<span class="red">在线客服</span>或者拨打<span class="red">${servertel}</span>咨询。
            </p>
        </div>
    </div>
</div>
<script>
    var mobile = $('.mymobile').text();
    mobile = mobile.substr(0, 3) + '****' + mobile.substr(7);
    $('.mymobile').text(mobile);

    var first = function () {
        var num = "-100%";
        $('.step-count').animate({"left": num}, 500);
    }
    var second = function () {
        var num = "-200%";
        $('.step-count').animate({"left": num}, 500);
        setTimeout("dsb()", 500);
    }

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

    $('.change-img').click(function () {
        $(".validate-img").trigger("click");
    })
</script>
<@m.page_footer />