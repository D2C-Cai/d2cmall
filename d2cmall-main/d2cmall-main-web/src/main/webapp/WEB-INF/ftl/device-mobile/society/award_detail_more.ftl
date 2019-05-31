<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="幸运大“赚”盘"  js="https://static.mlinks.cc/scripts/dist/mlink.min.js"  service="11.11" hastopfix='false' description="赢取iphone X ，购物抽奖最高返50%！" />

<style>
    @-webkit-keyframes falsh {
        from {
            z-index: 1;
        }
        to {
            z-index: 3;
        }
    }

    @keyframes falsh {
        from {
            z-index: 1;
        }
        to {
            z-index: 3;
        }
    }

    .award_banner {
        position: relative;
        overflow: hidden;
        background: url(//static.d2c.cn/img/promo/1225/bg_h5_draw.png) no-repeat;
        background-size: 100% 100%;
    }

    .footer-space {
        display: none;
    }

    .award_banner .draw_announcement {
        position: absolute;
        width: 96.8%;
        height: 22.84%;
        bottom: 0;
        left: 50%;
        margin-left: -48.4%;
        background: url(//static.d2c.cn/img/promo/award/Group_10.png) no-repeat;
        background-size: 100% 100%;
        overflow: hidden;
    }

    .award_active {
        position: relative;
        overflow: hidden;
        background: url(//static.d2c.cn/img/promo/award/bg-1.jpg) no-repeat;
        background-size: 100% 100%;
        top: -1px;
    }

    .award_active .award-bag-1, .award_active .award-bag-2 {
        width: 94.13%;
        position: absolute;
        top: 0;
        left: 50%;
        margin-left: -47.065%;
        height: 85.088%;
        background-size: 100% 100%;
    }

    .award_active .award-bag-1 {
        -webkit-animation: falsh 1.5s infinite;
        animation: falsh 1.5s infinite;
        background: url(//static.d2c.cn/img/promo/1225/light_flash_1.png) no-repeat;
        z-index: 1;
        background-size: 100% 100%;
    }

    .award_active .award-bag-2 {
        background: url(//static.d2c.cn/img/promo/1225/light_flash_2.png) no-repeat;
        z-index: 2;
        background-size: 100% 100%;
    }

    .award_active .award-play {
        width: 84%;
        position: absolute;
        height: 64.33%;
        top: 14.62%;
        left: 50%;
        margin-left: -42%;
        z-index: 4;
    }

    .playtab, .playtab .tbody {
        width: 100%;
        height: 100%;
        max-height: 100%;
        display: block;
    }

    .playtab td {
        width: 34.29%;
        height: 100%;
        text-align: center;
        display: block;
        border-radius: 6px;
        background: #FFF;
        position: relative;
    }

    .playtab td.playcurr {
        background: #FFE34A;
    }

    .playtab td p {
        position: absolute;
        text-align: center;
        font-size: .75rem;
        width: 100%;
        line-height: 1.2rem;
    }

    .playtab tr {
        justify-content: space-between;

    }

    .playtab #tc-tb1, .playtab #tc-tb2 {
        width: 28.03%;
        height: 164.75%
    }

    #tc-tb1 p, #tc-tb2 p {
        bottom: 1rem
    }

    .playtab td.playcurr {
        background: #FFE34A;
    }

    .playtab td .award-pic {
        width: 100%;
        display: inline-block;

    }

    .award_active .award-num {
        position: absolute;
        width: 160px;
        height: 25px;
        background: url(//static.d2c.cn/img/promo/award/icon_chance.png) no-repeat;
        bottom: 20px;
        left: 50%;
        background-size: 100% 100%;
        margin-left: -80px;
        text-align: center;
        color: #F2F2F2;
        line-height: 23px;
        font-size: 12px;
        padding-left: 10px;
    }

    #play_btn {
        position: absolute;
        width: 35.4%;
        top: 40%;
        margin-left: -17.7%;
        left: 50%;
        height: 21.14%;
        background: url(//static.d2c.cn/img/promo/award/icon_draw.png) no-repeat;
        background-size: cover;
        display: block;
        text-align: center;
        line-height: 250%;
        color: rgb(151, 88, 242);
        font-weight: bold;
        text-indent: -9999px;
        border: 0;
        z-index: 10;
    }

    #Marquee {
        height: 18px;
        line-height: 18px;
        overflow: hidden;
        width: 90%;
        margin-top: 7%;
        margin-left: 5%;
    }

    .banner-icon {
        position: absolute;
        width: 20%;
        right: 3%;
        top: 3%;
    }

    .draw_announcement ul li {
        overflow: hidden;
        float: left;
        line-height: 18px;

    }

    .draw_announcement ul li div {
        float: left;
        color: #FFDEF4;
        font-size: 12px;
        text-shadow: 1px 1px 10px #FFDEF4;
        overflow: hidden;
    }

    .socity {
        height: 39px;
        background: url(//static.d2c.cn/img/promo/award/bg-2.jpg) no-repeat;
        background-size: 100%;
        margin-top: -1px;
    }

    .socity .button-so {
        justify-content: space-around;
    }

    .socity .button-so button, .award-result {
        width: 150px;
        height: 39px;
        line-height: 39px;
        border: 0;
        background: url(//static.d2c.cn/img/promo/award/icon_btn.png) no-repeat;
        background-size: cover;
        color: #FFF;
        font-size: 16px;
        font-weight: 600;
        display: inline-block;
    }

    .award-task {
        background: url(//static.d2c.cn/img/promo/1225/999.png) no-repeat;
        background-size: 100% 100%;
        margin-top: -2px;
    }

    .award-task .user-task {
        width: 94.6%;
        margin: 0 auto;
        height: 100%;
        background: url(//static.d2c.cn/img/promo/1225/bg_11.png) no-repeat;
        background-size: 100% 100%;
    }

    .award-task .task-content .task-tilte {
        padding: 16px 0 16px 0;
        text-align: center;
        font-size: 14px;
        color: #F9E889;
    }

    .award-task .tinfo {
        justify-content: space-between;
        height: 30px;
        line-height: 30px;
        color: #FFF;
        text-shadow: 0 2px 2px #7F7F7F;
        margin-bottom: 20px;
    }

    .award-task .tinfo p {
        padding-left: 10px;
        background: url(//static.d2c.cn/img/promo/award/bg_word.png) no-repeat;
        background-size: cover;
        display: block;
        flex: 4;
    }

    .award-task .tinfo .t-btn {
        width: 80px;
        height: 30px;
        text-align: center;
        background: url(//static.d2c.cn/img/promo/award/icon_sbt.png) no-repeat;
        background-size: cover;
        margin-right: 10px;
    }

    .award-task .tinfo .t-btn.complete {
        background: url(//static.d2c.cn/img/promo/award/icon_hbtn.png) no-repeat;
        background-size: cover;
    }

    .award-task .tinfo:last-child .t-btn {
        margin-bottom: 0;
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

    .award-model-banner {
        height: 72px;
        width: 280px;
        background: url(//static.d2c.cn/img/promo/award/bg_pop_rule.png) no-repeat;
        background-size: cover;
        position: relative;
    }

    .pop-close-cion {
        height: 30px;
        width: 30px;
        background: url(//static.d2c.cn/img/promo/award/icon_delete.png) no-repeat;
        background-size: cover;
        position: absolute;
        right: -15px;
        bottom: 22px;
    }

    .award-model-title {
        font-size: 18px;
        padding-bottom: 20px;
        text-align: center;
        font-weight: bold;
    }

    .award-model-info .ct {
        position: relative;
        color: #757575;
        line-height: 22px;
        padding-bottom: 10px;
    }

    .award-model-info .ct:after {
        content: '';
        display: block;
        width: 10px;
        height: 10px;
        background: url(//static.d2c.cn/img/promo/award/icon_pop_point.png) no-repeat;
        background-size: cover;
        left: -15px;
        top: 5px;
        position: absolute;
    }

    .alert-modal {
        background: rgba(255, 255, 255, 0) !important;
    }

    .award-join-banner {
        height: 97px;
        width: 280px;
        background: url(//static.d2c.cn/img/promo/award/bg_pop_info.png) no-repeat;
        background-size: cover;
        position: relative;
    }

    .award-result-banner {
        height: 120px;
        width: 280px;
        background: url(//static.d2c.cn/img/promo/award/bg_pop_win.png) no-repeat;
        background-size: cover;
        position: relative;
    }

    .award-join-info input {
        display: block;
        border: 1px solid #D9D9D9;
        height: 38px;
        line-height: 38px;
        color: #757575;
        padding: 0 15px;
    }

    .award-join-info .logincodes {
        width: 100px;
        height: 38px;
        line-height: 38px;
        border: 1px solid #B74694;
        color: #B74694;
        text-align: center;
        border-radius: 20px;
        margin-left: 2px;
        background: #FFF;
    }

    #join-btn {
        width: 155px;
        height: 40px;
        line-height: 40px;
        background: url(//static.d2c.cn/img/promo/award/icon_btn.png) no-repeat;
        background-size: cover;
        color: #FFF;
        font-size: 16px;
        font-weight: bold;
        border: 0;
        margin: 30px auto 0;
        display: block;
    }

    .award-faie {
        background: url(//static.d2c.cn/img/promo/1225/bg_50.png) no-repeat;
        background-size: 100% 100%;
        margin-top: -10%;
        position: relative;
        z-index: 3;
    }

    .award-mylist {
        width: 94.6%;
        margin: 0 auto;
        height: 100%;
        background: url(//static.d2c.cn/img/promo/1225/back-myaward.png) no-repeat;
        background-size: 100% 100%;
    }

    .award-mylist .award-title {
        text-align: center;
        color: #fff;
        padding-top: 5%;
    }

    .award-rules {
        font-size: .75rem;
        line-height: 1.125rem;
        padding-left: 7.5%;
        padding-right: 3%;
        color: #FFF;
    }

    @media screen and (min-width: 241px) and (max-width: 320px) {
        .award-rules {
            font-size: .625rem;
        }
    }

    .award-rules p {
        position: relative;
    }

    .award-rules p:after {
        position: absolute;
        content: '';
        display: block;
        width: .5rem;
        height: .5rem;
        background: #F9E889;
        border-radius: 100%;
        top: .35rem;
        left: -4%;
    }

    .faie-title {
        padding: 15% 0 3% 0;
        text-align: center;
        font-size: .875rem;
        color: #F9E889;
    }

</style>
<img src="//static.d2c.cn/img/promo/1225/30303.png" style="display:none;">
<div class="bg_page">
    <div class="web_bg"></div>
</div>
<a style="display:none;" id="openD2c" href="javascript:"></a>
<div class="award_page" style="background:#000;">
    <div class="award_banner">
        <div class="banner-icon"><img src="//static.d2c.cn/img/promo/1225/logo.png" style="width:100%;"></div>
        <div class="draw_announcement">
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
    <div class="award_active">
        <div class="award-bag-1"></div>
        <div class="award-bag-2"></div>
        <div class="award-play rel">
            <a class="btn_arr play_btn" id="play_btn" href="javascript:;">立即抽奖</a>
            <table class="playtab" id="tb" cellpadding="0" cellspacing="1">
                <tr class="flex" style="align-items:flex-start">
                    <td id="tc-tb1">
                        <img src="${picture_base}${awardProduducts[5].pic}" class="award-pic">
                        <p>${awardProduducts[5].name}</p>
                    </td>
                    <td>
                        <p>${awardProduducts[3].name}</p>
                        <img src="${picture_base}${awardProduducts[3].pic}" class="award-pic">
                    </td>
                    <td>
                        <p>${awardProduducts[2].name}</p>
                        <img src="${picture_base}${awardProduducts[2].pic}" class="award-pic">
                    </td>
                </tr>

                <tr class="flex" style="margin-top:-15.8%;align-items:flex-end">
                    <td>
                        <p>${awardProduducts[1].name}</p>
                        <img src="${picture_base}${awardProduducts[1].pic}" class="award-pic">
                    </td>
                    <td>
                        <p>${awardProduducts[0].name}</p>
                        <img src="${picture_base}${awardProduducts[0].pic}" class="award-pic">
                    </td>
                    <td id="tc-tb2">
                        <img src="${picture_base}${awardProduducts[4].pic}" class="award-pic">
                        <p>${awardProduducts[4].name}</p>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <#if  myAttends?size gt 0>
        <div class="award-faie">
            <div class="award-mylist">
                <p class="faie-title">
                    我的中奖纪录
                </p>
                <div class="award-title"><#list myAttends as list>您抽中了${list.awardName}</#list></div>
            </div>
        </div>
    </#if>
    <div class="award-task">
        <div class="user-task">
            <div class="task-content" style="padding-left:3%;padding-right:3%;">
                <p class="task-tilte">
                    活动规则
                </p>
                <div class="award-rules">
                    <p>活动时间：2017年12月24日-2018年1月3日</p>
                    <p>活动期间内下单即可获得一次抽奖机会（最多可获得1次），100%中奖，购物返红包最高返50%，更有机会赢取iPhoneX大奖！</p>
                    <p>
                        支持活动期间内所有订单返红包，红包按成功交易订单实付金额返，红包、优惠券不计入实付金额，订单退款退货部分金额将不计入成功交易额中。（例：小G在活动期间多个订单实付金额共计10000元，其中退货退款1000元，成功交易金额则为9000元，小G抽奖抽到50%的订单返红包，最后将得到4500元红包）。</p>
                    <p>红包到账时间：买家确认收货后7个工作日订单交易完成，系统会在订单交易完成后3-5工作日发送到用户的红包账户，红包在[D2C App-我的-红包]中查看。</p>
                    <p>红包用于现金抵扣，无门槛使用，不支持提现，红包使用有效期截止到2018年2月28日。</p>
                    <p>iPhoneX大奖客服会在3-5个工作日联系您并寄出奖品。</p>
                    <p>本活动最终解释权归D2C所有。</p>
                </div>
            </div>
        </div>
    </div>
    <img src="//static.d2c.cn/img/promo/award/bg-4.jpg" width="100%" style="margin-top:-1px;">
</div>


<a href="/page/shuangdanjianianhua" class="twozero"><img src="//static.d2c.cn/img/promo/1225/twodan.jpg"
                                                         width="100%"></a>

<script>
    var award_ele = {
        init: function () {
            this.winWidth = document.documentElement.clientWidth || window.innerWidth || document.body.clientWidth;
            $('.award_banner').css({'width': '100%', 'height': this.winWidth * .7227});
            $('.award_active').css({'width': '100%', 'height': this.winWidth * .912});
            $('.award-task').css({'width': '100%', 'height': this.winWidth * 1.3041});
            $('.award-faie').css({'width': '100%', 'height': this.winWidth * .4293});
            $('.award-task .task-content').css({'padding-top': this.winWidth * .64266 * .1645});
        },

        joinD2c: function () {
            $.utilBaseModal.create({
                type: 'alert'
            });
            var html = '<div class="alert-modal-fixed"><div class="alert-modal" style="width:280px;">\
                <div class="award-join-banner"><a href="javascript:;" class="pop-close-cion" onclick="$.utilAlertModal.remove();" style="bottom:45px;"></a></div>\
                <div class="award-join-info" style="background:#FFF;overflow:scroll;padding:20px 20px 35px 20px ;font-size:16px;text-align: left;font-weight:normal; color:#757575;height:150px;">\
                <form class="validate-form form d2c-register-form" action="/member/bind" method="post" call-back="activityBind" success-tip="加入D2C成功" style="margin:0;">\
                <input type="hidden" name="nationCode" class="mobile-code" value="86"/>\
                <input name="loginCode" placeholder="请输入手机号码" pattern="[0-9]" maxlength="11" type="number" class="logininput validate-account validate"  title="手机号" data-rule="mobile" style="width:240px;"/>\
                <div class="code-btn flex" style="margin-top:20px;justify-content:space-between">\
                <input name="code" placeholder="请输入验证码" pattern="[0-9]" maxlength="4" type="number" class="validate" title="短信校验码" style="width:130px"/>\
                <button class="logincodes validate-send validate-button"  data-source="" data-type="Member" data-random="1" type="button">获取验证码</button></div>\
                <button id="join-btn">立即抽奖</button></div></form>\
                </div></div>';
            $('#' + $.utilAlertModal.type + '-modal-content').html(html);
            $('.alert-modal-fixed').css('top', '20%');
        },
    }
    award_ele.init();
    $('#Marquee').jcMarquees({'marquee': 'x', 'margin_right': '50px', 'speed': 20});
</script>
<script type="text/javascript">
    /*创建数组*/
    function GetSide(m, n) {
        //初始化数组
        //[[0,1,2],[3,4,5],[6,7,8]]
        var arr = [];
        for (var i = 0; i < m; i++) {
            arr.push([]);
            for (var j = 0; j < n; j++) {
                arr[i][j] = i * n + j;
            }
        }
        //获取数组最外圈，其实就是表格的最外圈坐标
        var resultArr = [];
        var X = 0,
            Y = 0,
            direction = "Along"
        while (X >= 0 && X < m && Y >= 0 && Y < n) {
            resultArr.push([X, Y]);
            if (direction == "Along") {
                if (Y == n - 1) {
                    X++;
                } else {
                    Y++;
                }
                if (X == m - 1 && Y == n - 1)
                    direction = "Inverse"
            } else {
                if (Y == 0)
                    X--;
                else
                    Y--;
                if (X == 0 && Y == 0)
                    break;
            }
        }
        return resultArr;
    } //得到新的数组arr
    $('#play_btn').on(click_type, function () {
        var that = this;
        if (isLoading) {
            return false;
        }
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                userLogin();
            } else if (data.result.datas.isBind == false) {
                award_ele.joinD2c();
            } else {
                $.ajax({
                    'url': '/award/startbackred',
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
                            StartGame(level, awardName, awardPic);
                        }
                    }
                });
            }
        });
    })


    var index = 0,           //当前亮区位置
        prevIndex = 0,          //前一位置
        Speed = 300,           //初始速度
        Time,            //定义对象
        arr = GetSide(2, 3),         //初始化数组(横列，竖列)
        tb = document.getElementById("tb"),     //获取表格对象
        cycle = 0,           //转动圈数
        flag = false,           //结束转动标志
        quick = 0,           //加速
        levels = 0,            //等级
        awardNames = '',       //奖品名称
        awardPics = '',        //奖品图片
        triTim = 0,           //奖品位置
        isLoading = false;        //是否转动
    function StartGame(level, awardName, awardPic) {
        isLoading = true;
        index = 0;
        var num = parseInt($('#award-c').text());
        $('#award-c').text(num - 1);
        $("td").removeClass("playcurr");
        document.getElementById("play_btn").disabled = true;
        clearInterval(Time); //停止循环执行Stat函数
        cycle = 0;//默认转动圈数为0
        flag = false;//默认不转动
        Time = setInterval(Star, Speed);//循环执行Star
        levels = level;
        awardNames = awardName;
        awardPics = awardPic;
        switch (level) {
            case 1:
                triTim = 1;
                break;
            case 2:
                triTim = 4;
                break;
            case 3:
                triTim = 2;
                break;
            case 4:
                triTim = 3;
                break;
            case 5:
                triTim = 6;
                break;
            case 6:
                triTim = 5;
                break;
        }
    }

    function Star(num) {
        //跑马灯变速
        if (flag == false) {
            //走五格开始加速
            if (quick == 5) {
                clearInterval(Time);
                Speed = 50;
                Time = setInterval(Star, Speed);
            }
            //跑N圈减速
            if (cycle >= 8) {
                clearInterval(Time);
                Speed = 300;
                flag = true;       //触发结束
                Time = setInterval(Star, Speed);
            }
        }
        if (index >= arr.length) {
            index = 0;
            cycle++;
        }
        tb.rows[arr[index][0]].cells[arr[index][1]].className = "playcurr";
        if (index > 0)
            prevIndex = index - 1;
        else {
            prevIndex = arr.length - 1;
        }
        tb.rows[arr[prevIndex][0]].cells[arr[prevIndex][1]].className = " ";
        index++;
        quick++;

        //结束转动并选中号码
        if (flag == true && cycle >= 10 && index == parseInt(triTim)) {
            quick = 0;
            clearInterval(Time);
            document.getElementById("play_btn").disabled = false;
            isLoading = false;
            setTimeout(function () {
                creatAwardRsult();
            }, 1000);
        }
    }

    function creatAwardRsult() {
        if (levels == 1) {
            var tips = "客服会在1-7个工作日内联系你";
            var btnstr = '<button class="award-result" onclick="location.reload()"  style="margin-top:20px;">知道了</button>'
        } else {
            var tips = "红包已放到[D2CAPP-我的-红包]中"
            var btnstr = '<button class="award-result" onclick="location.reload()"  style="margin-top:20px;">知道了</button>'
        }

        $.utilBaseModal.create({
            type: 'alert'
        });
        var html = '<div class="alert-modal-fixed"><div class="alert-modal" style="width:280px;">\
            <div class="award-result-banner"><a href="javascript:;" class="pop-close-cion" onclick="$.utilAlertModal.remove();" style="bottom:105px"></a></div>\
            <div class="award-join-info" style="background:#FFF;overflow:scroll;padding:20px 20px 25px 20px ;font-size:10px;text-align: center;font-weight:normal; color:#757575;height:130px;">\
            <div class="flex" style="justify-content:center;"><img src="//img.d2c.cn/' + awardPics + '!/both/100x112" style="width:50px;height:56px;display:block;"><div class="awardrest flex" style="margin-left:10px;height:56px;text-align:left;flex-direction:column;justify-content:space-between"><p style="color:#212121;line-height:18px;font-size:18px;font-weight:bold;width:100%;">' + awardNames + '</p><p>' + tips + '</p></div></div>' + btnstr + '</div></div>';
        $('#' + $.utilAlertModal.type + '-modal-content').html(html);
        $('.alert-modal-fixed').css('top', '20%');

    }

    function activityBind() {
        location.reload();
    }
</script>

<@m.page_footer />
