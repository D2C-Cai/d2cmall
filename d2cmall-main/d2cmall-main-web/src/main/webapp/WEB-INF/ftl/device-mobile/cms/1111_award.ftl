<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='双十一抽奖' css="" description=""/>
<style>
    .img-top, .img-bottom {
        position: relative;
    }

    .img-top img, .img-bottom img {
        display: block;
        vertical-align: middle;
    }

    .footer-space {
        display: none
    }

    .online-chat {
        display: none
    }

    .task-award {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
    }

    .task-award .titles {
        font-size: 12px;
        color: rgba(255, 255, 255, .8);
        text-align: center;
    }

    .award-content {
        position: absolute;
        top: 30px;
        width: 100%;
        left: 0;
    }

    .award-item {
        width: 90%;
        margin: 0 auto 20px;
        display: -webkit-box;
        display: -webkit-flex;
        display: flex;
        height: 74px;
    }

    .award-item .flex-left {
        position: relative;
        width: 25%;
        background: #2F005B;
        height: 100%;
    }

    .award-item .flex-left:after {
        display: block;
        content: '';
        position: absolute;
        width: 0;
        height: 0;
        border-top: 74px solid #F5515F;
        border-left: 20px solid transparent;
        right: 0;
        top: 0
    }

    .award-item .flex-left p:first-child {
        text-align: center;
        font-size: 14px;
        color: #fff;
        font-weight: blod;
        padding-top: 14px;
        padding-right: 12px;
    }

    .award-item .flex-left p:last-child {
        font-size: 10px;
        color: rgba(255, 255, 255, .6);
        padding-top: 8px;
        text-align: center;
        line-height: 16px;
    }

    .award-item .flex-right {
        width: 75%;
        background: -webkit-linear-gradient(left, #F5515F, #9F041B);
        background: linear-gradient(to right, #F5515F, #9F041B);
        display: -webkit-box;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: justify;
        -webkit-justify-content: space-between;
        justify-content: space-between;
        -webkit-box-align: center;
        -webkit-align-items: center;
        align-items: center;
    }

    .award-item .flex-right .need-amout p:first-child {
        font-size: 20px;
        color: #fff;
        font-weight: bold;
        line-height: 30px;
    }

    .award-item .flex-right .need-amout p:last-child {
        font-size: 12px;
        color: rgba(255, 255, 255, .6)
    }

    .award-item .flex-right .award-atauts {
        font-size: 10px;
        color: #fff;
        padding-right: 14px;
    }

    .award-item .flex-right .award-atauts p {
        padding-bottom: 6px;
        color: rgba(255, 255, 255, .6)
    }

    .award-item .flex-right .award-atauts .count-down span {
        display: inline-block;
        padding: 3px 4px;
        background: #222;
    }

    .award-item .flex-right .award-atauts .award-button {
        width: 73px;
    }

    #popup-modal-outer {
        background: transparent !important
    }

    .modal-close {
        position: absolute;
        bottom: -12%;
        left: 50%;
        -webkit-transform: translateX(-50%);
        transform: translateX(-50%);
        width: 30px;
        height: 30px;
        background: url(//static.d2c.cn/img/promo/bargin/icon_close.png) no-repeat;
        background-size: cover;
    }

    #popup-modal-title {
        position: absolute;
        bottom: 15%;
        left: 50%;
    }

    .award-brule {
        display: block;
        position: absolute;
        width: 75px;
        height: 30px;
        top: 55px;
        right: 0;
        z-index: 10;
        background: url(http://static.d2c.cn/img/promo/bargin/icon_helper.png) no-repeat;
        background-size: cover;
    }

    .popup-rule {
        width: 16em;
        padding: 1em;
        background: #FFF;
        background-size: cover;
        border-radius: 1em;
    }

    .rule-title {
        height: 1.3em;
        line-height: 1.3em;
        color: #3F4043;
        font-weight: bold;
        font-size: 1.2em;
        text-align: center;
        margin-bottom: 1em;
    }

    .rule-info {
        height: 25em;
        line-height: 150%;
        overflow: scroll;
        font-size: 12px;
        text-align: left;
        font-weight: normal;
        -webkit-overflow-scrolling: touch;
    }
</style>
<div class="award-page">
    <div class="img-top">
        <img src="http://static.d2c.cn/img/topic/181111/award/pic_main_01.jpg" style="width:100%"/>
        <a href="javascript:;" class="award-brule"> </a>
    </div>
    <div class="img-bottom">
        <img src="http://static.d2c.cn/img/topic/181111/award/pic_main_02.jpg" style="width:100%"/>
        <div class="task-award">
            <p class="titles">以下三个时间段任一时间段内消费满5000元均可参加</p>
        </div>
        <div class="award-content">
            <div class="award-item">
                <div class="flex-left">
                    <p>时段1</p>
                    <p><span style="padding-right:5px;">11.10 20:00</span></br><span style="padding-right:6px;">--11.11 10:00</span>
                    </p>
                </div>
                <div class="flex-right">
                    <div class="need-amout">
                        <p>&yen; ${amount1}</p>
                        <p><#if status==0><#if status1==0>活动还未开始<#else><#if amount1 gte 5000>等待开奖中<#elseif amount1 lt 5000 && status1 == 1>还差${5000-amount1}元,继续加油鸭<#else>你没有完成任务鸭</#if></#if><#else><#if mark1==0 && amount1 gte 5000>快查看中奖结果把<#else>182xxxx3332用户中奖</#if></#if></p>
                    </div>
                    <#if status==0 &&  status1==0>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wks.png" class="award-button"/>
                        </div>
                    <#elseif status==0 && amount1 lt 5000 && status1==1>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_qgw.png" class="award-button"
                                 onclick="location.href='/page/11shejikuanghuan'"/>
                        </div>
                    <#elseif status==0 && amount1 gte 5000 && status1==1 && mark1==0>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_kjl.png"
                                 class="award-button go-award" data-index="1"/>
                        </div>
                    <#elseif status==0 && amount1 gte 5000 && status1==1 && mark1==1>
                        <div class="award-atauts">
                            <p>距开奖</p>
                            <div class="count-down" data-type="split-time"
                                 data-startTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${drawDate?string("yyyy/MM/dd HH:mm:ss")}"><span class="hour">00</span> :
                                <span class="minute">00</span> : <span class="second">00</strong></div>
                        </div>
                    <#elseif status==0 && amount1 lt 5000 && status1==2>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wdb.png" class="award-button"/>
                        </div>
                    <#elseif status==0 && amount1 gte 5000 && status1==2>
                        <div class="award-atauts">
                            <p>距开奖</p>
                            <div class="count-down" data-type="split-time"
                                 data-startTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${drawDate?string("yyyy/MM/dd HH:mm:ss")}"><span class="hour">00</span> :
                                <span class="minute">00</span> : <span class="second">00</strong></div>
                        </div>
                    <#elseif status==1 && amount1 gte 5000>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wzj.png" class="award-button"/>
                        </div>
                    <#elseif status==1 && amount1 lt 5000>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wdb.png" class="award-button"/>
                        </div>
                    </#if>
                </div>
            </div>
            <div class="award-item">
                <div class="flex-left">
                    <p>时段2</p>
                    <p><span style="padding-right:5px;">10.11 10:00</span></br><span style="padding-right:6px;">--11.11 16:00</span>
                    </p>
                </div>
                <div class="flex-right">
                    <div class="need-amout">
                        <p>&yen; ${amount2}</p>
                        <p><#if status==0><#if status2==0>活动还未开始<#else><#if amount2 gte 5000>等待开奖中<#elseif amount2 lt 5000 && status2 == 1>还差${5000-amount2}元,继续加油鸭<#else>你没有完成任务鸭</#if></#if><#else><#if mark2==0 && amount2 gte 5000>快查看中奖结果把<#else>139xxxx2334用户中奖</#if></#if></p>
                    </div>
                    <#if status==0 &&  status2==0>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wks.png" class="award-button"/>
                        </div>
                    <#elseif status==0 && amount2 lt 5000 && status2==1>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_qgw.png" class="award-button"
                                 onclick="location.href='/page/11shejikuanghuan'"/>
                        </div>
                    <#elseif status==0 && amount2 gte 5000 && status2==1 && mark2==0>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_kjl.png"
                                 class="award-button go-award" data-index="2"/>
                        </div>
                    <#elseif status==0 && amount2 gte 5000 && status2==1 && mark2==1>
                        <div class="award-atauts">
                            <p>距开奖</p>
                            <div class="count-down" data-type="split-time"
                                 data-startTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${drawDate?string("yyyy/MM/dd HH:mm:ss")}"><span class="hour">00</span> :
                                <span class="minute">00</span> : <span class="second">00</strong></div>
                        </div>
                    <#elseif status==0 && amount2 lt 5000 && status2==2>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wdb.png" class="award-button"/>
                        </div>
                    <#elseif status==0 && amount2 gte 5000 && status2==2>
                        <div class="award-atauts">
                            <p>距开奖</p>
                            <div class="count-down" data-type="split-time"
                                 data-startTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${drawDate?string("yyyy/MM/dd HH:mm:ss")}"><span class="hour">00</span> :
                                <span class="minute">00</span> : <span class="second">00</strong></div>
                        </div>
                    <#elseif status==1 && amount2 gte 5000>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wzj.png" class="award-button"/>
                        </div>
                    <#elseif status==1 && amount2 lt 5000>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wdb.png" class="award-button"/>
                        </div>
                    </#if>
                </div>
            </div>
            <div class="award-item">
                <div class="flex-left">
                    <p>时段3</p>
                    <p><span style="padding-right:5px;">10.11 16:00</span></br><span style="padding-right:6px;">--11.12 00:00</span>
                    </p>
                </div>
                <div class="flex-right">
                    <div class="need-amout">
                        <p>&yen; ${amount3}</p>
                        <p><#if status==0><#if status3==0>活动还未开始<#else><#if amount3 gte 5000>等待开奖中<#elseif amount3 lt 5000 && status3 == 1>还差${5000-amount3}元,继续加油鸭<#else>你没有完成任务鸭</#if></#if><#else><#if mark3==0 && amount3 gte 5000>快查看中奖结果把<#else>130xxxx2144用户中奖</#if></#if></p>
                    </div>
                    <#if status==0 &&  status3==0>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wks.png" class="award-button"/>
                        </div>
                    <#elseif status==0 && amount3 lt 5000 && status3==1>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_qgw.png" class="award-button"
                                 onclick="location.href='/page/11shejikuanghuan'"/>
                        </div>
                    <#elseif status==0 && amount3 gte 5000 && status3==1 && mark3==0>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_kjl.png"
                                 class="award-button go-award" data-index="3"/>
                        </div>
                    <#elseif status==0 && amount3 gte 5000 && status3==1 && mark3==1>
                        <div class="award-atauts">
                            <p>距开奖</p>
                            <div class="count-down" data-type="split-time"
                                 data-startTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${drawDate?string("yyyy/MM/dd HH:mm:ss")}"><span class="hour">00</span> :
                                <span class="minute">00</span> : <span class="second">00</strong></div>
                        </div>
                    <#elseif status==0 && amount3 lt 5000 && status3==2>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wdb.png" class="award-button"/>
                        </div>
                    <#elseif status==0 && amount3 gte 5000 && status3==2>
                        <div class="award-atauts">
                            <p>距开奖</p>
                            <div class="count-down" data-type="split-time"
                                 data-startTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"
                                 data-endTime="${drawDate?string("yyyy/MM/dd HH:mm:ss")}"><span class="hour">00</span> :
                                <span class="minute">00</span> : <span class="second">00</strong></div>
                        </div>
                    <#elseif status==1 && amount3 gte 5000>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wzj.png" class="award-button"/>
                        </div>
                    <#elseif status==1 && amount3 lt 5000>
                        <div class="award-atauts">
                            <img src="http://static.d2c.cn/img/topic/181111/award/button_wdb.png" class="award-button"/>
                        </div>
                    </#if>

                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $('.go-award').on('click', function () {
        var index = $(this).attr('data-index');
        $.ajax({
            'url': '/membertake/payment/award/1111/insert',
            'type': 'post',
            'data': {'mark': index},
            'dataType': 'json',
            'success': function (res) {
                if (res.result.status == 1) {
                    console.log('参与成功');
                    window.location.reload();
                } else {
                    toast({position: 'center', type: 'error', message: res.result.message});
                }
            }
        })
    })

    $('.award-brule').on(click_type, function () {
        var html = '<div class="popup-content"><div class="popup-rule">\
	<a href="javascript:popupModalClose();" class="modal-close"></a>\
	<div class="rule-title">活动规则</div>\
	<div class="rule-info">\
	<p style="font-weight: bold">如何获得奖励  </p>\
	1、在2018年11月10日20点~11日10点，11日10点~16点，11日16点~24点这三个活动时段期间参加活动。！<br /><br />\
    2、任一活动时段，实付金额满5000元，即可获得1次抽奖机会。  <br /><br />\
    3、到达抽奖页面，点击抽奖 <br /><br />\
    4、中奖后24小时内保持电话畅通，将会有工作人员与您联系，确认奖品发放事宜。<br /><br />\
    <p style="font-weight: bold">参与用户  </p>\
    1、绑定有效手机号码的D2C账户均可参与活动。<br /><br />\
    <p style="font-weight: bold">抽奖资格  </p>\
    1、2018年11月10日20:00:00~2018年11月11日09:59:59、2018年11月11日10:00:00~2018年11月11日15:59:59、2018年11月11日16:00:00~2018年11月11日23:59:59这三个活动时段，在D2C付款成功。<br /><br />\
    2、上述任一活动时段，累计实际支付金额（不含红包、优惠券等优惠金额）满5000元，即可获得1次抽奖机会。同一账户最多可获得3次抽奖机会。<br /><br />\
    <p style="font-weight: bold">抽奖时间  </p>\
    1、 2018年11月10日20:00:00~2018年11月12日11:59:59，逾期无法抽奖<br /><br />\
    <p style="font-weight: bold">奖品清单</p>\
    1、每个活动时段有1台iPhone XS Max 256G（颜色随机）作为奖品，合计3台iPhone XS Max 256G。<br/><br/>\
    <p style="font-weight: bold">奖品发放</p>\
    1、 中奖后24小时内，D2C官方工作人员会主动联系中奖用户，确认奖品发放细节。<br /><br />\
    2、 法律允许范围内，本活动解释权归D2C自身所有。<br /><br />\
    3、 活动过程中，如果出现违规行为（如作弊领取、恶意刷单等），D2C将有权取消您的中奖资格。<br /><br />\
	</div>';
        popupModal({
            content: html
        });
    })

</script>






































<@m.page_footer />