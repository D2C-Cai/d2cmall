<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="天天抽奖，100%中现金红包，更有机会赢取iphoneX"  js="https://static.mlinks.cc/scripts/dist/mlink.min.js|utils/Rotate"  service="11.11" hastopfix='false' description="天天抽奖，100%中现金红包，更有机会赢取iphoneX" />
<style>
    body {
        background: #ffe507;
    }

    .award-head .nortic {
        width: 260px;
        height: 22px;
        background: url('//static.d2c.cn/other/award/bg_gonggao.png') no-repeat;
        background-size: cover;
        transform: translate(33%, -38px);
        -webkit-transform: translate(33%, -38px);
    }

    #nav {
        position: fixed;
        bottom: 10%;
        right: 5%;
        width: 55px;
    }

    #Marquee {
        height: 22px;
        line-height: 22px;
        overflow: hidden;
        width: 75%;
        margin-left: 25%;
    }

    #Marquee ul li {
        overflow: hidden;
        float: left;
        line-height: 22px;

    }

    #Marquee ul li div {
        float: left;
        color: #fcff00;
        font-size: 12px;
        text-shadow: 1px 1px 4px #fcff00;
        overflow: hidden;
    }

    .award-num {
        margin: -5px auto 14px;
        background: url('//static.d2c.cn/other/award/icon_chance.png') no-repeat;
        background-size: cover;
        width: 162px;
        height: 24.8px;
        line-height: 24.8px;
        color: #38c24d;
        font-size: 12px;
        box-sizing: border-box;
        padding-left: 43px;
    }

    .award-zp {
        position: relative;

    }

    #play_btn {
        position: absolute;
        width: 77px;
        height: 153px;


    }

    .share-btn {
        display: -webkit-flex;
        display: flex;
        justify-content: space-around;
        height: 50px;
        font-size: 0;
    }

    .share-btn > button {
        width: 136px;
        height: 50px;
        line-height: 2px;
        background: url('//static.d2c.cn/other/award/btn_share.png') no-repeat;
        background-size: cover;
        border: none;
        color: #fff;
        font-size: 12px;
    }

    .award-tasked {
        width: 92%;
        height: 132px;
        margin: 10px auto;
        background: #007aa3;
        border-radius: 14px;
        box-sizing: border-box;
        padding: 15px 22px 14px 22px;

    }

    .award-tasked .tasked-title {
        width: 64.93%;
        height: 24px;
        line-height: 24px;
        background: #FCFF00;
        color: #007aa3;
        text-align: center;
        margin: 0 auto;
        font-size: 12px;
        border-radius: 10px;
    }

    .award-tasked .task-line {
        display: -webkit-flex;
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 37.5px;
        line-height: 37.5px;
        padding: 14px 0 6px 0;
        border-bottom: 1px dashed #fcff00;
        box-sizing: border-box;
    }

    .award-tasked .task-line p {
        color: #FCFF00;
        font-size: 12px;
    }

    .award-tasked .task-line a {
        height: 24px;
        width: 76px;
        background: url('//static.d2c.cn/other/award/icon_wancheng.png') no-repeat;
        background-size: cover;
        display: block;
    }

    .award-tasked .task-line a.gocomplete {
        background: url('//static.d2c.cn/other/award/btn_canjia.png') no-repeat;
        background-size: cover;
    }

    .award-tasked .task-line a.do-btn {
        background: url('//static.d2c.cn/other/award/icon_watch.png') no-repeat;
        background-size: cover;
    }

    .hide-content {
        overflow: hidden;
    }

    .see-more {
        width: 100%;
        text-align: center;
        color: #FCFF00;
        font-size: 12px;
        margin-top: 16px;
    }

    .see-more span:after {
        content: "";
        position: absolute;
        width: 6px;
        height: 6px;
        border-left: 1px solid;
        border-bottom: 1px solid;
        top: 1px;
        right: -13px;
        transform: rotate(315deg);
        -webkit-transform: rotate(315deg)
    }

    .bg_page {
        width: 100%;
        height: 100%;
        position: fixed;
        z-index: 99;
        display: none;
        background: rgba(0, 0, 0, .5);
        top: 0;
        left: 0;

    }

    .bg_page .web_bg {
        position: absolute;
        width: 235px;
        height: 175px;
        right: 15px;
        z-index: 3;
        background-image: url(//static.d2c.cn/img/promo/bargin/mark_guide_shar.png);
        background-size: cover;
    }

    #popup-modal-remove {
        display: block;
        position: absolute;
        width: 30px;
        height: 30px;
        background: url(http://static.d2c.cn/img/promo/bargin/icon_close.png) no-repeat;
        background-size: cover;
        margin-left: -15px;
    }

    .popup-rule {
        width: 80%;
        height: 400px;
        background: #FFF;
        box-sizing: border-box;
        margin: 0 auto;
    }

    #popup-modal-title {
        position: absolute;
        bottom: 15%;
        left: 50%;
    }

    #popup-modal-outer {
        background: none;
    }

    #popup-modal-outer .popup-content {
        width: 100%;
        transform: translate(-50%, -60%);
        -webkit-transform: translate(-50%, -60%);
    }

    .rule-info {
        padding: 0 20px;
        height: 320px;
        line-height: 180%;
        overflow: scroll;
        font-size: 12px;
        text-align: left;
        font-weight: normal;
        -webkit-overflow-scrolling: touch;
        color: #A35717;
    }

    #popup-modal-remove .icon {
        display: none;
    }

    .alert-register {
        position: relative;
        text-align: center;
        background: url(http://static.d2c.cn/other/award/bg_tianxie.png) no-repeat;
        background-size: cover;
    }

    .pop-close-cion {
        height: 30px;
        width: 30px;
        background: url(http://static.d2c.cn/img/promo/bargin/icon_close.png) no-repeat;
        background-size: cover;
        position: absolute;
        left: 50%;
        margin-left: -15px;
        bottom: -40px;
    }

    .pop-award-title {
        font-size: 16px;
        margin-bottom: 40px;
        font-weight: 700;
    }

    .alert-register input {
        height: 40px;
        line-height: 40px;
        border: 1px solid #f23365;
        border-radius: 3px;
        padding-left: 10px;
    }


    .alert-register input::-webkit-input-placeholder {
        color: #ccc;
    }

    .logincodes {
        width: 38%;
        height: 40px;
        line-height: 40px;
        border: none;
        background: #F8595C;
        text-align: center;
        color: #fff;
        border-radius: 5px;
    }

    #join-btn {
        background: url(http://static.d2c.cn/other/award/btn_cj.png) no-repeat;
        background-size: cover;
        width: 223px;
        height: 42px;
        margin-top: 25px;
        border: none;
    }

    .alert-result-model {
        position: relative;
        background: transparent !important;
        width: 100%;
        max-width: 100%;
    }
</style>


<img src="http://static.d2c.cn/other/award/bg_zzb.png" style="display:none;">
<div class="bg_page">
    <div class="web_bg"></div>
</div>
<div class="award-head">
    <img src="//static.d2c.cn/other/award/icon_title.png" width="100%">
    <div class="nortic">
        <div id="Marquee">
            <ul>
                <li>
                    <#if  recentlyAttends?size gt 0>
                        <#list recentlyAttends as list>
                            <div>${list.loginNo}获得了${list.awardName}</div>
                        </#list>
                    </#if>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="award-num">
    今天还有<span id="award-c" style="font-size:18px;vertical-align:middle">${(ownCount)!0}</span>次机会
</div>
<div class="award-zp">
    <img src="//static.d2c.cn/other/award/bg_zzb.png" width="100%" id="rote">
    <img src="//static.d2c.cn/other/award/start.png" id="play_btn"/>
</div>
<div class="share-btn">
    <button class="show-rule">活动规则</button>
    <button class="share-btns">分享给朋友</button>
</div>
<p style="text-align:center;font-size:12px;color:#007aa3">本次活动与Apple Inc.无关</p>
<div class="award-tasked">
    <div class="tasked-title">
        完成任务可获得更多抽奖机会
    </div>
    <div class="task-line">
        <p>每天分享抽奖活动页面</p><a href="javascript:;"
                            class="<#if lotterySource&&lotterySource.SHAREACTIVITY==1>com<#else>gocomplete</#if>"
                            data-id="1"></a>
    </div>
    <div class="task-line">
        <p>参与砍价活动</p><a href="javascript:;"
                        class="<#if lotterySource&&lotterySource.BARGAIN==1>com<#else>gocomplete</#if>" data-id="2"></a>
    </div>
</div>
<#if  myAttends?size gt 0>
<div class="award-tasked" style="height:207px">
    <div class="tasked-title">
        我的中奖记录
    </div>
    <div class="hide-content" style="height:112.5px;">
        <#list myAttends as list>
            <div class="task-line">
                <p>您抽中了${list.awardName}</p><#if list.awardLevel!=1 || list.awardLevel!=3 || list.awardLevel!=4> <a
                        href="javascript:;"
                        class="seebtn do-btn <#if !m.FROMAPP><#if  list.awardLevel==5 || list.awardLevel==6 || list.awardLevel==7 || list.awardLevel==8>level2<#elseif list.awardLevel==2>level3</#if></#if>"
                        <#if list.awardLevel==5 || list.awardLevel==6 || list.awardLevel==7 || list.awardLevel==8>data-url="/check/redPacket"
                        <#elseif list.awardLevel==2>data-url="/coupon/memberCoupon"</#if> ></a></#if>
            </div>
        </#list>
    </div>
    <#if  myAttends?size gt 3>
        <div class="see-more">
            <span style="position:relative;">更多</span>
        </div>
    </#if>
    </#if>
</div>
<a href="/page/520quanqiuquai "><img src="//static.d2c.cn/img/topic/180510/shareredpackets/images/earth.png" id="nav"/></a>
<img src="//static.d2c.cn/other/award/icon_aboutus_logo.png"
     style="position:relative;width:70px;left:50%;top:20px;margin-left:-35px;"/ >
<a style="display:none;" id="openD2c" href="javascript:"></a>
</div>
<script>
    <#if !m.FROMAPP>

    var options = [
        {
            mlink: 'AKGJ',
            button: document.querySelectorAll('.level2'),
            params: {myUrl: '/check/redPacket'}
        }, {
            mlink: 'AKGJ',
            button: document.querySelectorAll('.level3'),
            params: {myUrl: '/coupon/memberCoupon'}
        }
    ];
    new Mlink(options);
    </#if>

    var award_ele = {
        init: function () {
            this.lisen();
            this.rule();
            $('#play_btn').css({
                "top": "50%",
                "left": "50%",
                "margin-left": -($('.award-zp').width() * .205 / 2) + "px",
                "margin-top": -($('.award-zp').height() * .405 / 2 + 8) + "px",
                "width": ($('.award-zp').width() * .205) + "px",
                "height": ($('.award-zp').width() * .405) + "px"
            });
        },

        joinD2c: function () {
            $.utilBaseModal.create({
                type: 'alert'
            });
            var html = '<div class="alert-modal-fixed"><div class="alert-modal alert-register" >\
		<a href="javascript:;" class="pop-close-cion" onclick="$.utilAlertModal.remove();"></a>\
		<div class="award-join-info" style="overflow:scroll;padding:20px 24px;font-size:12px;color:#F23365;">\
		<form class="validate-form form d2c-register-form" action="/member/bind" method="post" call-back="activityBind" success-tip="加入D2C成功" style="margin:0;">\
		<div class="pop-award-title">填写手机号大礼收入囊中~</div>\
		<input type="hidden" name="nationCode" class="mobile-code" value="86"/>\
		<input name="loginCode" placeholder="请输入手机号码" pattern="[0-9]" maxlength="11" type="number" class="logininput validate-account validate"  title="手机号" data-rule="mobile" style="width:100%;"/>\
		<div class="code-btn flex" style="margin-top:20px;justify-content:space-between">\
		<input name="code" placeholder="请输入验证码" pattern="[0-9]" maxlength="4" type="number" class="validate" title="短信校验码" style="width:56.67%"/>\
		<button class="logincodes validate-send validate-button"  data-source="" data-type="MEMBERMOBILE" data-random="1" type="button">获取验证码</button></div>\
		<button id="join-btn"></button></div></form>\
		</div></div>';
            $('#' + $.utilAlertModal.type + '-modal-content').html(html);
            $('.alert-modal-fixed').css('top', '20%');
            $('.alert-register').css('height', $('.alert-register').width() * 1.01)
        },
        lisen: function () {
            $('.see-more').on(click_type, function () {
                $(this).hide()
                $('.award-tasked').css('height', 'auto');
                $('.hide-content').css('height', 'auto');
            })
            $('.gocomplete').on(click_type, function () {
                var id = parseInt($(this).attr('data-id'))
                $.getJSON('/member/islogin', function (data) {
                    if (data.result.login == false) {
                        userLogin();
                    } else if (data.result.datas.isBind == false) {
                        that.joinD2c();
                    } else {
                        switch (id) {
                            case 1:
                                $('.share-btns').trigger(click_type);
                                break;
                            case 2:
                                location.href = "/bargain/promotion/list?status=1&bargain=1"
                                break;
                        }
                    }
                });
            })
            $('.share-btns').on(click_type, function () {
                if (!app_client) {
                    $('.bg_page').show();
                    $('.bg_page').on(click_type, function () {
                        $(this).hide();
                    })
                } else {
                    var data = new ShareDatas();
                    var myData = data.message;
                    $.D2CMerchantBridge(myData);
                }


            });
            $('.seebtn').on(click_type, function () {
                if (app_client) {
                    var url = $(this).attr('data-url');
                    location.href = url;
                }
            });

        },
        rule: function () {
            $('.show-rule').on(click_type, function () {
                var html = '<div class="popup-content"><div class="popup-rule">\
				<div class="rule-title"><img src="http://static.d2c.cn/other/award/bg_guize.png" width="100%"/></div>\
				<div class="rule-info">\
				1、活动时间：2018年5月13日-2018年5月19日 23:59:59<br />\
                2、活动期间每天登录即可参加抽奖，分享活动页面和参加砍价活动可多获得一次抽奖机会，每天最多有三次抽奖机会，100%中奖，购物返红包最高返8%，更有机会赢取iPhoneX大奖！<br />\
                3、抽奖得到红包系统会自动发送到用户的红包账户，用于现金抵扣，不支持提现功能，红包不能用于抵扣物流快递费用，红包使用有效期截止到2018年5月20日 23:59:59<br />\
                4、KTZ T恤限量50件,奖品将以等值优惠券的形式发到用户账户，可在下单时使用，使用规则请在优惠券管理功能中查看<br />\
                5、订单返利将以红包形式发往用户账户，仅支持5月20日当天的订单返利，若多次抽中订单返利，则按成功交易订单金额的最高返利比返红包，红包、优惠券不计入实付金额，订单退款退货部分金额将不计入成功交易额中。（例：小G在抽奖活动期间多次抽中5%的8%的订单返利，5月20日当天下3单，实付金额共计10000元，其中退货退款1000元，则成功交易金额则为9000元，系统将在订单完成后3-7个工作日按最高的返利比8%返利到小G红包账号，返利金额为720元）。订单返利红包有效期截止日期为2018年7月20日 23:59:59<br />\
                6、iphone X限量5台，客服将会在1-3个工作日联系您并寄出奖品<br />\
                7、凡参与本活动者,即视为接受活动所有规则，且必须遵守D2C法律声明及其它关于营销活动的相关规定,D2C有权在法律允许范围内对活动规则作出适当调整<br />\
                8、在活动过程中,如果出现违规行为(如作弊领取、恶意刷取等)，我公司有权取消您已获取的奖品，必要时追究法律责任,恶意参与活动的客户将被列入黑名单无法参加此类活动。<br />\
                9、本次活动与Apple Inc.无关<br />\
				</div>';
                popupModal({
                    content: html
                });
            });

        },

    }
    award_ele.init();
    $('#Marquee').jcMarquees({'marquee': 'x', 'margin_right': '50px', 'speed': 20});
</script>
<script type="text/javascript">
    var isLoading = false;//是否正在抽奖
    var $btn = $('#play_btn');// 旋转的div

    $('#play_btn').on(click_type, function () {
        var that = this;
        if (isLoading) {
            toast({position: 'center', type: 'error', message: '请不要多次点击哟~'});
            return false;
        }
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                userLogin();
            } else if (data.result.datas.isBind == false) {
                award_ele.joinD2c();
            } else {
                $.ajax({
                    'url': '/award/start/1',
                    'type': 'post',
                    'data': {},
                    'dataType': 'json',
                    'success': function (res, status) {
                        if (!res.awardRecord) {
                            toast({position: 'center', type: 'error', message: res.result.message});
                        } else {
                            var level = res.awardRecord.awardLevel;
                            var awardName = res.awardRecord.awardName;
                            var awardPic = res.awardRecord.awardPic;
                            StartGame(level);
                        }
                    }
                });
            }
        });
    })

    function StartGame(level) {
        isLoading = true; // 标志为 在执行
        var num = parseInt($('#award-c').text());
        if (num <= 0) num = 1;
        $('#award-c').text(num - 1);
        switch (level) {
            case 1:
                rotateFunc(1, 0);
                break;
            case 2:
                rotateFunc(2, 45);
                break;
            case 3:
                rotateFunc(3, 90);
                break;
            case 4:
                rotateFunc(4, 135);
                break;
            case 5:
                rotateFunc(5, 180);
                break;
            case 6:
                rotateFunc(6, 225);
                break;
            case 7:
                rotateFunc(7, 270);
                break;
            case 8:
                rotateFunc(8, 315);
                break;
        }
    }

    var rotateFunc = function (level, angle) {
        isLoading = true;
        $btn.stopRotate();
        $btn.rotate({
            angle: 0,//初始角度
            duration: 8000, //旋转时间
            animateTo: angle + 3240, //给定的角度,让它根据得出来的结果加上1440度旋转
            callback: function () {
                isLoading = false; // 标志为 执行完毕
                setTimeout(function () {
                    creatAwardRsult(level);
                }, 200);
            }
        });
    };


    function creatAwardRsult(level) {
        var tips = "红包已放到[D2CAPP-我的-红包]中";
        var btnstr = '<button class="award-result" onclick="location.reload()"  style="margin-top:20px;">知道了</button>';
        $.utilBaseModal.create({
            type: 'alert'
        });
        var html = '<div class="alert-modal-fixed"><div class="alert-modal alert-result-model">\
    <img src="//static.d2c.cn/other/award/pic_a' + level + '.png" style="width:100%;margin-top:-40%;"/>\
	<a href="javascript:;" class="pop-close-cion" onclick="location.reload();" style="bottom:28%"></a>'
        $('#' + $.utilAlertModal.type + '-modal-content').html(html);
        $('.alert-modal-fixed').css('top', '20%');

    }

    function activityBind() {
        location.reload();
    }


</script>

<@m.page_footer />
