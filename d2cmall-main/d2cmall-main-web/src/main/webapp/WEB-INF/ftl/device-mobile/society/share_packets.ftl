<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='瓜分红包' css="redpacket" description="凑齐3人一起分现金大红包~"/>
<style>#popup-modal-outer {
        background: rgba(0, 0, 0, .5) !important;
    }</style>
<div class="wechatguide" style="display:none">
    <img src="//static.d2c.cn/img/topic/170612/share.png"/>
</div>
<div style="position:absolute;top:0;left:14px;"><img src="//static.d2c.cn/img/topic/190428/520/hb_bg_jiaobiao.png"
                                                     width="46px" height="46px" alt=""></div>
<div class="mask"></div>
<div class="redpackets-container">
    <div class="packets-rule" onclick="TDAPP.onEvent('红包规则')"><img
                src="//static.d2c.cn/img/topic/190428/520/hb_rule.png" width="100%" alt=""/></div>
    <#if group == null>
        <div class="packets-title"><img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/invite2.png"
                                        width="100%"/></div>
        <div class="packets-body">
            <img src="//static.d2c.cn/img/topic/180813/redpackets/image/redpackets.jpg"
                 style="border-radius:5px;box-shadow:0 5px 10px rgba(0,0,0,0.1)" width="100%" alt="/">
            <button class="packets-button  packets-active" data-type="create" style="bottom:1.875rem;"
                    onclick="TDAPP.onEvent('我要组团')">我要组团
            </button>
        </div>
        <div style="position:relative;">
            <div class="packets-group packets-flex">
                <div class="group-text">
                    <div style="margin-bottom:5px;color:rgba(0,0,0,.85);font-size:16px;font-weight:bold;">已邀请<span
                                style="color:#F21A1A;">0人</span></div>
                    <p style="font-size:10px;color:rgba(0,0,0,.3);">邀请3人即可瓜分红包</p>
                </div>
                <div class="group-portrait">
                    <#list 1..3!0 as i>
                        <div class="group-headpic"><img
                                    src="//static.d2c.cn/img/topic/180510/shareredpackets/images/head@3x.png"
                                    width="100%" alt=""/></div>
                    </#list>
                </div>
            </div>
        </div>
    </#if>
    <#if group != null && group.status == 0>
        <div class="packets-title"><img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/invite2.png"
                                        width="100%"/></div>
        <div class="packets-body">
            <img src="//static.d2c.cn/img/topic/180813/redpackets/image/redpackets.png" width="100%" alt="/">
            <#if myGroupId == group.id>
                <button class="packets-button  packets-active" data-type="invite" style="bottom:1.875rem;"
                        onclick="TDAPP.onEvent('立即邀请')">立即邀请
                </button>
            <#elseif myGroupId != null && myGroupId != group.id>
                <a class="packets-button  packets-mygroup" href="/shareredpackets/${myGroupId}"
                   style="display:block;bottom:1.875rem;color:#842300;" onclick="TDAPP.onEvent('进入我的团')">进入我的团</a>
            <#elseif myGroupId == null>
                <button class="packets-button  packets-create" data-type="create" style="bottom:1.875rem;"
                        onclick="TDAPP.onEvent('我也要组团')">我也要组团
                </button>
            </#if>
        </div>
        <div style="position:relative;">
            <div class="packets-group packets-flex">
                <div class="group-text">
                    <div style="margin-bottom:5px;color:rgba(0,0,0,.85);font-size:16px;font-weight:bold;">已邀请<span
                                style="color:#F21A1A;">${attends?size}人</span></div>
                    <p style="font-size:10px;color:rgba(0,0,0,.3);">邀请${remaining}人即可瓜分红包</p>
                </div>
                <div class="clearfix">
                    <#assign i=3-attends?size />
                    <#list attends as items>
                        <div class="group-headpic">
                            <img src="<#if items.headPic!=''><#if items.headPic?index_of('http')!=-1>${items.headPic}<#else>${picture_base}${items.headPic}</#if><#else>//static.d2c.cn/img/nnm/npc/bg_portrait.png</#if>"
                                 width="100%;" alt=""/>
                            <#if items.initiator == 1>
                                <i class="icon-protait"></i>
                            </#if>
                        </div>
                    </#list>
                    <#list 1..i!0 as i>
                        <div class="group-headpic">
                            <img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/head@3x.png" width="100%"
                                 alt=""/>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
        <div class="packets-form">
            <div class="form-normal">
                <p style="color:rgba(0,0,0,.85);font-size:20px;font-weight:bold;text-align:center;">参团分红包</p>
                <p style="font-size:12px;color:rgba(0,0,0,.5);text-align:center;margin-top:1.25rem;">
                    组团成功后红包将发送到您的账号中</p>
                <input type="number" name="mobile" placeholder="请输入手机号" class="packets-form-mobile"
                       style="border:0;border:.5px solid #B2B2B2;border-radius:2px;margin-top:1.5625rem;color:#000;width:100%;padding:.5rem;"/>
                <button type="button" class="packets-form-button" data-type="sure"
                        style="color:#FFF;background:#000;margin-top:1.5rem;" onclick="TDAPP.onEvent('绑定手机号确定')">确定
                </button>
            </div>
            <div class="form-next" style="display:none;">
                <p style="color:rgba(0,0,0,.85);font-size:20px;font-weight:bold;text-align:center;">请输入验证码</p>
                <input type="number" name="code" class="validate-code" id="" value="" maxlength="4" autocomplete="off"
                       style="opacity:0;position:absolute;top:100px;left:-1000px;">
                <div class="form-input packets-flex">
                    <div class="code-frame"></div>
                    <div class="code-frame"></div>
                    <div class="code-frame"></div>
                    <div class="code-frame"></div>
                </div>
                <div class="code-error"
                     style="display:none;text-align:center;margin-top:.625rem;color:#F21A1A;font-size:14px;">验证码错误请重新输入
                </div>
                <div class="code-modify" style="text-align:center;margin-top:.625rem;color:#842300;font-size:14px;"
                     onclick="TDAPP.onEvent('修改手机号')">修改手机号 >
                </div>
                <button class="packets-button code-button" data-type="code" onclick="TDAPP.onEvent('重新获取验证码')">重新获取验证码
                </button>
            </div>
            <i class="packets-form-close" onclick="TDAPP.onEvent('关闭绑定弹窗')"></i>
        </div>
    </#if>
    <#if group != null && group.status == 1>
        <div class="packets-title"><img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/invite1.png"
                                        width="100%"/></div>
        <div class="packets-body">
            <img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/chai.png" width="100%" alt="/">
            <#if myAttend != null>
                <p class="packets-tips">恭喜您成功瓜分红包</p>
                <div class="packets-money"><span style="font-size:2.75rem;">${myAttend.money}</span>元</div>
            <#else>
                <p class="packets-tips">啊哦&nbsp;&nbsp;你来晚了</p>
                <div class="packets-money" style="font-size:20px;">红包已经瓜分完了</div>
            </#if>
            <#if myGroupId == group.id>
                <button class="packets-button  packets-finish" data-type="finish" style="bottom:5.25rem;color:#f2ab60;">
                    已瓜分成功
                </button>
            <#elseif myGroupId == null>
                <button class="packets-button  packets-create" data-type="create" style="bottom:5.25rem;"
                        onclick="TDAPP.onEvent('我也要组团')">我也要组团
                </button>
            <#elseif myGroupId != group.id && myGroupId != null>
                <a class="packets-button packets-getIn" data-type="getIn" href="/shareredpackets/${myGroupId}"
                   style="display:block;bottom:5.25rem;color:#842300;" onclick="TDAPP.onEvent('进入我的团')">进入我的团</a>
            </#if>
            <a href="/page/520zhounianqing" class="packets-button  packets-more" style="bottom:1.875rem;"
               onclick="TDAPP.onEvent('查看更多福利')">查看更多福利</a>
        </div>
        <div class="packets-luck" style="width:20rem;margin:1.875rem  auto 1.25rem;"><img
                    src="//static.d2c.cn/img/topic/180510/shareredpackets/images/luck.png" width="100%" alt="/"></div>
        <div class="packets-rank-list">
            <#list attends as items>
                <div class="rank-item packets-flex">
                    <div class="rank-info">
                        <div style="position:relative;">
                            <img src="<#if items.headPic!=''><#if items.headPic?index_of('http')!=-1>${items.headPic}<#else>${picture_base}${items.headPic}</#if><#else>//static.d2c.cn/img/nnm/npc/bg_portrait.png</#if>"
                                 style="width:1.875rem;height:1.875rem;border-radius:100%;" alt=""/>
                            <#if items.initiator == 1><i class="icon-protait"
                                                         style="bottom:.75rem;left:.2rem;transform:translateX(0);"></i></#if>
                            <span style="display:inline-block;vertical-align:middle;color:rgba(0,0,0,.85);font-size:16px;margin-left:5px;"><#if items.nickName != null>${items.nickName}<#else>无名氏</#if></span>
                        </div>
                    </div>
                    <div class="rank-money">
                        ${items.money}
                        <#if items_index == 0>
                            <span class="rank-best">手气最佳</span>
                        </#if>
                    </div>
                </div>

            </#list>
        </div>
        <div class="packets-slogan"><img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/slogan@3x.png"
                                         width="100%" alt="/"></div>
    </#if>
    <div style="margin:20px auto;width:90%;">
        <a href="/page/520zhounianqing"><img src="//static.d2c.cn/img/topic/190913/sbsbs.jpg" width="100%" alt=""></a>
    </div>
    <#--  <a href="/page/520zhounianqing"><img src="//static.d2c.cn/img/topic/190913/sbsbs.jpg" width="100%" style="margin-top:20px;" /></a>  -->
</div>
<share data-title="我在D2C领到了88元大红包，快来和我一起拆" data-desc="名额有限 拆成功即发红包，最高可得88元"
       data-pic="http://static.d2c.cn/img/topic/180817/clear/images/share.png"></share>
<script>
    <#if m.LOGINMEMBER.id!=null> //判断登录信息
    <#if !m.LOGINMEMBER.d2c && group.status == 0>
    $(".packets-form, .mask").slideDown();
    <#elseif m.LOGINMEMBER.d2c && myGroupId && id == 0>
    location.href = "/shareredpackets/" + "${myGroupId}";
    </#if>
    <#if m.LOGINMEMBER.d2c && myGroupId != group.id && group.status == 0>
    $.flashTip({position: 'center', type: 'success', message: '${result.message}'});
    <#elseif m.LOGINMEMBER.d2c && myGroupId != group.id && group.status == 1>
    $.flashTip({position: 'center', type: 'success', message: '${result.message}'});
    </#if>
    </#if>
    //倒计时
    var countdown = 60;

    function countDown() {
        var obj = $(".code-button");
        settime(obj);
    }

    function settime(obj) { //发送验证码倒计时
        if (countdown == 0) {
            obj.removeClass("code-button-grey");
            obj.attr('disabled', false);
            countdown = 60;
            return false;
        } else {
            obj.attr('disabled', true);
            countdown--;
            obj.addClass("code-button-grey");
        }
        setTimeout(function () {
            settime(obj)
        }, 1000);
    }

    //关闭弹窗
    $(document).on(click_type, '.packets-form-close', function () {
        $(".packets-form, .mask").hide();
    });

    //表单按钮操作
    $(document).on(click_type, '.packets-form-button', function () {
        var obj = $(this);
        var type = obj.attr('data-type');
        var myReg = /^1\d{10}$/;
        var mobileVal = $('.packets-form-mobile').val();
        if (type == 'sure') {
            if (mobileVal.length == 11 && myReg.test(mobileVal)) {
                $('.form-normal').hide();
                $('.form-next').fadeIn();
                var sign = hex_md5("mobile=" + mobileVal + secretKey),
                    appParams = $.base64('encode', "mobile=" + mobileVal + "&sign=" + sign),
                    data = {
                        mobile: mobileVal,
                        type: 'MEMBERMOBILE',
                        terminal: 'PC',
                        nationCode: 86,
                        appParams: appParams
                    };
                $.ajax({
                    url: '/sms/send/encrypt',  //短信验证码
                    type: 'post',
                    data: data,
                    dataType: 'json',
                    success: function (data) {
                        if (data.result.status == 1) {
                            countDown();
                            $.flashTip({position: 'center', type: 'success', message: '验证码已发送请查收'});
                        }
                        return false;
                    }
                });
            } else {
                $.flashTip({position: 'center', type: 'error', message: '请输入正确的手机号码'});
            }
        }
    });
    //验证码输入框
    $('.code-frame').on('click', function () { //验证码输入框
        $('input[name=code]').focus();
    });
    $('input[name=code]').on('keyup', function (event) {
        if ($(this).val().length > 4) $(this).val($(this).val().slice(0, 4))
        var code_value = $(this).val();
        for (var i = 0; i < 4; i++) {
            var v = code_value.substr(i, 1);
            $('.code-frame').eq(i).text(v);
        }
    });
    //判断验证码输入是否有误
    $(".validate-code").bind("input propertychange", function () {
        var codeVal = $(this).val();
        if (codeVal.length == 4) {
            $.ajax({
                url: "/member/bind", //注册
                type: 'post',
                dataType: 'json',
                data: {
                    "loginCode": $('.packets-form-mobile').val(),
                    "code": $('.validate-code').val(),
                    "nationCode": 86
                },
                success: function (data) {
                    if (data.result.status == 1) {
                        location.reload();
                    } else {
                        $('input[name=code]').val('');
                        $('.code-error').show();
                    }
                }
            });
        }
    });
    //再次获取验证码
    $(document).on(click_type, '.code-button', function () {
        var obj = $(this);
        if (obj.prop("disabled") == false) {
            var mobileVal = $('.packets-form-mobile').val(),
                sign = hex_md5("mobile=" + mobileVal + secretKey),
                appParams = $.base64('encode', "mobile=" + mobileVal + "&sign=" + sign),
                data = {mobile: mobileVal, type: 'MEMBERMOBILE', terminal: 'PC', nationCode: 86, appParams: appParams};
            $.ajax({
                url: '/sms/send/encrypt',  //短信验证码
                type: 'post',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        countDown();
                        $.flashTip({position: 'center', type: 'success', message: '验证码已发送请查收'});
                    }
                    return false;
                }
            });
        }
    });
    //返回修改手机号码
    $(".code-modify").on("click", function () {
        $('.form-next').hide();
        $('.form-normal').fadeIn();
        $('.packets-form-mobile').val('');
    });
    //规则
    $(document).on(click_type, '.packets-rule', function () {
        var html = '<div class="popup-content">\
			<div class="packets-rule-container">\
			<h2 style="text-align:center;font-size:20px;color:rgba(0,0,0,.85);font-weight:bold;">活动规则</h2>\
			<div style="margin-top:1.25rem;font-size:14px;color:rgba(0,0,0,.5);line-height:1.8;">\
			<p>活动截止时间：2019/05/20 23:59:59</p>\
			<p>参与活动规则：</p>\
			<p>①.用户可邀请好友共同拆红包，满3人则拆红包成功，共同瓜分总金额为88的红包，其中随机一人获得 手气最佳红包<p>\
			<p>②.活动期间，帮助其他用户只可拆1次 ，发起拆红包也只可发起1次</p>\
			<p>③. 本次红包的奖励将会由系统自动分配到D2CAPP-我的-红包中，该红包不支持提现，下单时可抵用</p>\
			</div>\
			<i class="rule-close" onclick="popupModalClose()"></i>\
			</div>\
			</div>';
        popupModal({content: html});
    });
    //按钮操作
    $(document).on(click_type, '.packets-button', function () {
        var obj = $(this);
        var type = obj.attr('data-type');
        if (type == 'invite') {
            if (_memberId) {
                if (!app_client) {
                    $('.wechatguide, .mask').show();
                    $(document).on('touchstart', '.mask', function () {
                        $('.wechatguide, .mask').hide();
                        return false;
                    });
                } else {
                    var data = new ShareDatas();
                    var myData = data.message;
                    $.D2CMerchantBridge(myData);
                }
            } else {
                userLogin();
            }
        }
        if (type == 'create') {
            if (_memberId) {
                $.ajax({
                    url: "/shareredpackets/create", //创团
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.result.status == 1) {
                            console.log(data);
                            var id = data.shareRedPacketsGroup.id;
                            $.flashTip({position: 'center', type: 'success', message: '恭喜你，创团成功了！'});
                            //$.flashTip({position:'center',type:'success',message:data.result.message});
                            setTimeout(function () {
                                location.href = "/shareredpackets/" + id; //创团成功，跳转到相应url
                            }, 1500);
                        } else {
                            $.flashTip({position: 'center', type: 'success', message: '每人只能开一次团咯~'});
                            setTimeout(function () {

                                location.href = "/shareredpackets/" + data.myGroupId;
                            }, 1500);
                        }
                    }
                });
            } else {
                userLogin();
            }
        }
    });

</script>
<@m.page_footer />