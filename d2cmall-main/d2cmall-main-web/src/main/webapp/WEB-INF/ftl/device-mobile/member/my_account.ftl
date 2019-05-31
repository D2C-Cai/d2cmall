<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的钱包" title='我的钱包' button='false' service='false' hastopfix='false'/>
<div class="section">
    <#if !account.password || !account.mobile>
    <div class="tips tip-green ">使用钱包必须设置支付密码</div>
    <div class="form">
        <form name="form-info" id="form-info" class="validate-form" redirect-url="/member/account/info"
              action="/member/account/changePassword.json" method="post">
            <input type="hidden" name="nationCode" value="86" class="mobile-code"/>
            <#if account.mobile>
                <div class="form-item item-flex">
                    <label class="choose-country-code" style="font-size:16px;">+86</label>
                    <input type="text" name="" class="input" value="${(account.mobile)!}" disabled/>
                    <input type="hidden" name="mobile" class="validate validate-account" title="手机号码" data-rule="mobile"
                           value="${(account.mobile)!}"/>
                </div>
            <#else>
                <div class="form-item item-flex" id="choose-country">
                    <label>国家和地区</label>
                    <span class="note" style="color:#FD555D;max-width:70%;" id="country-code">中国大陆</span>
                </div>
                <div class="form-item item-flex">
                    <label class="choose-country-code" style="font-size:16px;">+86</label>
                    <input type="text" name="mobile" class="input validate validate-account" title="手机号码"
                           data-rule="mobile" placeholder="请输入常用的手机号码" value="${(account.mobile)!}"/>
                </div>
            </#if>
            <div class="form-item item-flex">
                <label>校 验 码</label>
                <input type="text" name="code" id="code" value="" class="input validate" title="校验码"
                       placeholder="请输入校验码"/>
                <button type="button" data-source="${(account.account)!}" data-type="MEMBERMOBILE"
                        class="button button-white validate-send validate-button">获取校验码
                </button>
            </div>
            <div class="form-item item-flex">
                <label>支付密码</label>
                <input type="password" name="newPassword1" id="newPassword1" class="input" min-length="6"
                       placeholder="请输入6位以上支付密码" value=""/>
            </div>

            <div class="form-item item-flex">
                <label>确认密码</label>
                <input type="password" name="newPassword2" id="newPassword2" class="input" compare-with="newPassword1"
                       placeholder="请确认一次密码" value=""/>
            </div>

            <div class="form-button">
                <button type="submit" name="submit-button" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
<#else>

    <div class="user-wallet">
        <p>钱包余额</p>
        <p class="total">&yen; ${(account.totalAmount?string("currency")?substring(1))!}</p>
        <p class="grey">现金 &yen; ${(account.cashAmount?string("currency")?substring(1))!} &nbsp;&nbsp;红包
            &yen; ${(account.giftAmount?string("currency")?substring(1))!}</p>
        <p class="grey">可用金额 &yen; ${(account.availableTotalAmount?string("currency")?substring(1))!} &nbsp;&nbsp; 冻结金额
            &yen;${(account.freezeAmount?string("currency")?substring(1))!}</p>
    </div>
    <div class="form">
        <!-- <a href="/member/account/recharge" class="form-item">
            <i class="icon"></i>我要充值<i class="icon icon-arrow-right"></i>
        </a> -->
        <a href="/member/account/bill" class="form-item">
            <i class="icon"></i>我的账单<i class="icon icon-arrow-right"></i>
        </a>
    </div>

    <div class="form">
        <!--<a href="/member/account/changePassword" class="form-item">
                 <i class="icon icon-signin"></i>修改支付密码<i class="icon icon-arrow-right"></i>
             </a>
         </div>-->
        </#if>
    </div>
    <@m.page_footer />
