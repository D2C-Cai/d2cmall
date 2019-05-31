<div class="form form-small">
    <form class="validate-form" action="/member/bill/pay" method="post">
        <input type='hidden' name='payId' value='${(payitem.id)!}'/>
        <div class="form-item form-item-vertical">
            <label>请输入手机号码</label>
            <input type="text" name="mobile" id="mobile" size="18" title="手机号码" placeholder="输入您绑定的手机号"
                   class="input input-l validate-account" data-rule="mobile" value="${(account.mobile)!}" readonly/>
            <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>请输入验证码</label>
            <input type="text" name="code" id="validate-code" size="18" title="校验码" placeholder="输入收到的短信中验证码"
                   class="input input-l" style="width:60%;" value=""/>
            <button type="button" data-source="${(payitem.sn)!}" data-type="Bill"
                    class="button button-l button-green validate-send">点击获取验证码
            </button>
            <div class="tip tip-validate" data-target="validate-code"></div>
        </div>
        <div class="form-item form-item-vertical">
            <label>请输入支付密码</label>
            <input type="password" name="password" id="password" title="支付密码" placeholder="请输入您的支付密码" value=""
                   class="input input-l"/>
            <div class="tip tip-validate" data-target="password" data-rule="sixDigit"></div>
        </div>
        <div class="form-button">
            <button type="submit" class="button button-l">确定</button>
        </div>
    </form>
</div>