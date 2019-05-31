<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='重置登录密码' url='/member/login' title='重置登录密码' service='false' hastopfix='false'/>
<div class="section" id="set-password">
    <div class="tips tip-green">重置密码需要向 <strong>${loginCode}</strong> 发送一个校验码，请点击“获取校验码” 。</div>
    <div class="form">
        <form id="send-form"
              class="validate-form"<#if !m.FROMAPP> redirect-url="/member/login"<#else> success-tip="密码修改成功！请重新登录！" call-back="showSuccess"</#if>
              action="/member/resetPassword" method="post">
            <input type="hidden" name="loginCode" class="validate-account" value="${loginCode}"/>
            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
            <div class="form-item item-flex" id="choose-country">
                <label>国家和地区</label>
                <span class="note" style="color:#FD555D;max-width:70%;" id="country-code">中国大陆</span>
            </div>
            <div class="form-item item-flex">
                <!--<label class="choose-country-code" style="font-size:16px;">+86</label>-->
                <input type="text" name="code" id="code" value="" class="input validate" title="校验码"
                       placeholder="请输入校验码"/>
                <button type="button" data-source="" data-type="RETRIEVEPASSWORD"
                        class="button button-white validate-send validate-button">获取校验码
                </button>
            </div>
            <div class="form-item">
                <input type="password" name="password" id="password" value="" class="input validate" title="新密码"
                       min-length="8" max-length="20" placeholder="输入您的8-20位新密码"/>
            </div>
            <div class="form-item">
                <input type="password" name="repassword" id="repassword" value="" class="input validate" title="确认密码"
                       min-length="8" max-length="20" compare-with="password" placeholder="请确认您的新密码"/>
            </div>
            <div class="form-button">
                <button type="submit" name="send-button" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
</div>
<div class="section display-none" id="set-success">
    <div class="text-center padding" style="line-height:250%;padding-top:30%;">
        <div class="icon icon-ok"></div>
        <div class="green"><strong>密码更改成功！请重新登录。</strong></div>
    </div>
</div>
<script>
    function showSuccess() {
        $('#set-password').hide();
        $('#set-success').show();
    }

    /*var afterSend=function(){
        var second=60;
        var obj=$('.validate-reset-send');
        var time=setInterval(function(){
            if (second<1){
                clearInterval(time);
                obj.removeAttr('disabled').text('重发验证码');
            }else{
                obj.attr('disabled',true).text(second+'秒后重发');
                second--;
            }
        },1000);
    }
    afterSend();
    $('.validate-reset-send').click(function(){
        $.ajax({
            url     :   '/member/sendResetPassword',
            data    :   {'loginCode':$('.validate-reset-code').val()},
            type    :   'post',
            success :   function(){
                afterSend();
            }
        });
        return false;
    });*/
</script>
<@m.page_footer menu=true />























