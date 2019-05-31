<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的钱包' js='utils/jquery.datepicker' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="wallet"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>我的钱包</h1>
        <#if !account.password || !account.mobile>
            <div class="form-info n_form text-center">
                <form name="form_mobile" class="validate-form" success-tip="支付密码设置成功"
                      action="/member/account/changePassword.json" method="post" style="border:0;">
                    <#if account.mobile>
                        <div class="form-item">
                            <label>您已经绑定手机</label>
                            <input type="text" name="mobile" id="mobile" title="手机号码" placeholder="输入常用能接收到短信的手机号"
                                   data-rule="mobile" class="input input-l validate-account"
                                   value="${(account.mobile)!}" readonly/>
                            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
                            <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
                        </div>
                    <#else>
                        <div class="form-item">
                            <label>请输入手机号码</label>
                            <div style="display:inline-block;position:relative;width:30%;">
                                <span class="choose-country" id="choose-country"
                                      style="position:absolute;line-height:36px;top:2px;"><strong
                                            id="mobile-code">+86</strong><em class="fa fa-caret-down"
                                                                             style="top:11px;"></em></span>
                                <input type="text" name="mobile" id="mobile" style="width:100%;padding-left:65px;"
                                       title="手机号码" placeholder="输入常用能接收到短信的手机号" data-rule="mobile"
                                       class="input input-l validate-account" value="${(account.mobile)!}"/>
                            </div>
                            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
                            <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
                        </div>
                    </#if>
                    <div class="form-item">
                        <label>请输入验证码</label>
                        <input type="text" name="code" id="validate-code" size="18" title="验证码"
                               placeholder="输入收到的短信中4位数字校验码" class="input input-l" style="width:20%" value=""/>
                        <button type="button" data-source="${(account.account)!}" data-type="ACCOUNT"
                                class="button  button-l nb validate-send ps button-gray" style="width:9.7%;">获取验证码
                        </button>
                        <div class="tip tip-validate" data-target="validate-code"></div>
                    </div>
                    <div class="form-item">
                        <label>请输入支付密码</label>
                        <input type="password" name="newPassword1" id="password" title="支付密码"
                               placeholder="输入8-20位数字字母组成的密码" value="" class="input input-l"/>
                        <div class="tip tip-validate" data-target="password" data-rule="sixDigit"></div>
                    </div>
                    <div class="form-item">
                        <label>请确认支付密码</label>
                        <input type="password" name="newPassword2" id="confirm-password" title="确认密码"
                               placeholder="确认上面输入的支付密码" value="" class="input input-l"/>
                        <div class="tip tip-validate" data-target="confirm-password" compare-with="password"
                             data-rule="sixDigit"></div>
                    </div>
                    <div class="form-button">
                        <button type="submit" class="button button-l">确定</button>
                    </div>
                </form>
            </div>
        <#else>
            <div class="wallet-top" style="margin-bottom:100px;">
                <span>现金：</span><span class="price"
                                      style="margin-right:40px">&yen; ${(account.cashAmount?string("currency")?substring(1))!}</span>
                <span>红包：</span><span class="price"
                                      style="margin-right:40px">&yen; ${(account.giftAmount?string("currency")?substring(1))!}</span>
                <span>可用金额：</span><span class="price"
                                        style="margin-right:40px">&yen;${(account.availableTotalAmount?string("currency")?substring(1))!}</span>
                <span>冻结资金：</span><span
                        class="price">&yen;${(account.freezeAmount?string("currency")?substring(1))!}</span>&nbsp;
                <div class="operator">
                    <a href="javascript:" template-url="/member/account/changePassword" modal-type="pop"
                       modal-width="600" class="button button-outline ajax-request ps">修改支付密码</a>
                </div>
            </div>
            <div class="mybills">
                <div>
                    <h1 style="border:none;padding:bottom:0;">我的账单</h1>
                    <div class="so-mybils">

                    </div>
                </div>
            </div>
        </#if>
    </div>
</div>
</div>
<input class="pageNumber" type="hidden" value='${page.pageNumber}'/>
<input class="pageSize" type="hidden" value='${page.pageSize}'/>
<script type="text/javascript">
    p = $(".pageNumber").val(),
        size = $(".pageSize").val(),
        $.get('/member/account/bill?p=' + p + '&pageSize=' + size, function (data) {
            $('.so-mybils').html(data);
        });
</script>
<@m.page_footer />