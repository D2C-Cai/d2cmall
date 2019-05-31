<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='开通我的小店' css='store' service="false"/>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">买手店设置</div>
    </div>
</header>
<div class="set-box">
    <form action="/partner/update/store" class="validate-form" method="post" redirect-url="/partner/store"
          success-tip="店铺信息更新成功">
        <div class="set-item flex space-between">
            <div class="item-label">店铺头像</div>
            <div><img src="//static.d2c.cn/img/home/160627/images/headpic.png"
                      style="width:4em;height:4em;border-radius:100%;"><i
                        style="float:right;margin-top:.5em;background-size:50%;opacity:.6;"
                        class="icon icon-arrow-right"></i></div>
        </div>
        <div class="set-item flex space-between">
            <div class="item-label">店铺名称</div>
            <div class="item-alter"><span style="color:#444;">穷人啊啊啊的店铺</span><i
                        style="float:right;margin-top:.5em;background-size:50%;opacity:.6;"
                        class="icon icon-arrow-right"></i></div>
        </div>
        <div class="set-item flex space-between">
            <div class="item-label">
                <p>店铺口号</p>
                <p class="grey">肯定是好货啦！</p>
            </div>
            <div class="item-alter">点击修改<i style="float:right;margin-top:.5em;background-size:50%;opacity:.6;"
                                           class="icon icon-arrow-right"></i></div>
        </div>
    </form>
</div>
<!--
<div class="section">
    <#if m.LOGINMEMBER.partnerId gt 0>
    <form action="/partner/create/store" method="post" class="validate-form" redirect-url="/partner/store" success-tip="店铺开通成功">
    <#else>
    <form action="/partner/quick/apply" method="post" class="validate-form" call-back="createStore">
    </#if>
         <div class="setup-wraper">
            <div class="shop-logo">
            <#if m.LOGINMEMBER.headPic>
            <img src="<#if m.LOGINMEMBER.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${m.LOGINMEMBER.headPic}" class="head-pic" />
            <input type="hidden" name="pic" value="<#if m.LOGINMEMBER.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${m.LOGINMEMBER.headPic}" />
            <#elseif m.LOGINMEMBER.thirdHeadPic>
            <img src="${m.LOGINMEMBER.thirdHeadPic}" class="head-pic" />
            <input type="hidden" name="pic" value="${m.LOGINMEMBER.thirdHeadPic}" />
            <#else>
            <img src="//static.d2c.cn/img/home/160627/images/headpic.png" class="head-pic" />
            <input type="hidden" name="pic" value="//static.d2c.cn/img/home/160627/images/headpic.png" />
            </#if>
            </div>
            
            <div class="split">
                <span>给你的小店取个名字呗</span>     
            </div>
            
            <div>
               <input type="text" name="name" value="${m.LOGINMEMBER.nickname}的店铺" maxlength="20" class="input" />
            </div>
            <div>
                <button type="submit" class="button button-black button-l">申请开通</button>
            </div>
            <div>
                <a href="https://doc.d2cmall.com/agreement/recommender.html" style="font-size:0.7em;color:blue">开通买手店默认同意D2C推手服务协议</a>
            </div>
        </div>
    </form>
</div>
<script>
function createStore(){
    $('form').attr('action','/partner/create/store');
    $('form').attr('redirect-url','/partner/store');
    $('form').attr('success-tip','店铺开通成功');
    $('form').removeAttr('call-back');
    $('form').submit();
}

</script>
-->
<@m.page_footer menu=false />