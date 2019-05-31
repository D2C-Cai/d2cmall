<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='每日签到'  service='false'/>
<#assign LOGINMEMBER=loginMember()/>
<style>body {
        background-color: #000;
    }

    #popup-modal-outer {
        background: transparent
    }

    .sign-succuss-content {
        width: 85vw;
        min-width: 250px;
        height: 260px;
        border-radius: 13px;
        background: #fff;
        text-align: center;
    }

    .success-title {
        color: grba(0, 0, 0, .85);
        font-weight: bold;
        font-size: 20px;
        margin-top: -50px;
    }

    .list-item a {
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
        overflow: hidden
    }
</style>
<div class="section">
    <div class="sign-head">
        <div class="pro-title">
            <p>赚D币赢红包</p>
            <a href="/page/Redenvelope" class="taskrule"> </a>
            <p>12月05日00:00-12月12日24:00兑换</p>
        </div>
        <div class="sign-content">
            <div class="remind">
                签到提醒
                <div class="re-button"></div>
            </div>
            <div class="signitem">
                <p>我的D币</p>
                <div class="db-detail">
                    <p id="total"></p>
                    <p class="detail-button" onclick="location.href='/page/dbdetail'">收支明细</p></br>
                    <p class="sign-tips">您已连续签到<span>${signRecords.total}天</span>，明日签到可得<span>${tomorrowReward}</span>D币
                    </p>
                </div>
                <div class="sign-jf">
                    <#if signRecords.total && signRecords.total lt 7 && signRecords.total gt 0>
                        <#list signRecords.list?sort_by(["id"]) as list>
                            <div class="sign-it text-center"><p class="ite-day">${list.totalDay}天</p>
                                <div class="old">${list.point}</div>
                            </div>
                        </#list>
                        <#list signRewards as list>
                            <#if list_index gte signRecords.total>
                                <div class="sign-it text-center"><p class="ite-day">${list_index+1}天</p>
                                    <div>${list}</div>
                                </div>
                            </#if>
                        </#list>
                    <#else>
                        <#list signRewards as list>
                            <div class="sign-it text-center"><p class="ite-day">${list_index+1}天</p>
                                <div>${list}</div>
                            </div>
                        </#list>
                    </#if>
                </div>
                <div class="sign-button db-button">
                    签到
                </div>
            </div>
        </div>
    </div>
    <div style="font-size:0;"><img src="//static.d2c.cn/other/1212/bg_02.jpg" style="width:100%"></div>
    <div class="sign-task">
    </div>
    <div class="help-jj" style="position:relative;font-size:0">
        <img src="//static.d2c.cn/other/1212/bg_03.jpg" style="width:100%">
        <p style="position:absolute; top: 50%;left: 50%;transform: translate(-50%, -50%);-webkit-transform: translate(-50%, -50%);font-size:24px;color:#E6AA48;font-weight:bold;">
            打榜赢红包</p>
    </div>
    <div class="task-diss">
        <p class="line1">发布买家秀 找好友围观</p>
        <p class="line2">最高可得100元红包</p>
        <div class="sign-button db-button" style="margin:30px 0 0;width:90%"
             onclick="location.href='/membershare/topic/12'">
            去打榜
        </div>
    </div>
    <div class="sign-product">
        <p class="product-title text-center">D币兑换<a href="/point/shop"><span class="more">查看更多></span></a></p>
        <div class="sign-list clearfix">
        </div>
        <div class="text-center" style="margin:15px 0;color:rgba(0,0,0,.3);font-size:12px;">
            活动由D2C提供，与设备生产商Apple Inc无关
        </div>
        <button style="line-height:44px;height:44px;width:92%;margin:15px auto;background-color:#824E30;color:#fff;text-align:center;border:none;display:block;font-size:16px;"
                onclick="location.href='/point/shop'">D币商城
        </button>
    </div>
</div>
<script id="list-template" type="text/html">
    <div class="lazyload">
        {{each pager.list as value i}}
        <div class="list-item">
            <a href="/dcion/shop/productpoint/{{value.id}}">
                <img src="${picture_base}{{value.detailPic}}!600">
            </a>
            <p class="list-title">{{value.name}}</p>
            <p class="need-db"><span style="color:#c39b5c;font-size:14px">{{value.point}}</span>D币&nbsp;&nbsp;{{if
                value.type=='RED'}}兑换<span style="color:#c39b5c;font-size:14px">{{value.amount}}</span>红包{{/if}}</p>
        </div>
        {{/each}}
    </div>
</script>
<script id="task-template" type="text/html">
    <img src="//static.d2c.cn/other/1212/task_bg.png" width="100%" id="taskbg"/>
    <div class="task-section">
        <p class="task-h5">完成以下任务，获取更多D币</p>
        {{each list as item i}}
        <div class="task-title">
            {{if item[0]&& item[0].type=='COMMON_TASK'}}
            日常任务
            {{else if item[0]&& item[0].type=='NEW_TASK' && (item.length>1 || (item.length==1 &&
            item[0].code=='FRIST_MESSAGE_LIST' || (item[0].code=='FRIST_REGISTER' && item[0].state!='TODO_TASK')))}}
            新人任务
            {{else if item[0]&& item[0].type=='TIME_TASK'}}
            限时任务
            {{else if item[0]&& item[0].type=='DAY_TASK'}}
            每日任务
            {{/if}}
        </div>
        <div class="task-content">
            {{each item as value index}}
            {{if value.code=='FRIST_REGISTER' && value.state=='TODO_TASK'}}
            {{else}}
            <div class="task-item">
                <div class="task-info">
                    <p class="task-item-title">
                        {{value.name}} {{if value.max > 1}}<span>{{value.count}}/{{value.max}}</span>{{/if}}
                    </p>
                    <p class="task-sbutitle">
                        {{value.subName}}
                    </p>
                </div>
                <div class="task-button {{if value.state=='AWARD_TASK'}}wait{{else if value.state=='DONE_TASK'}}over{{/if}}"
                     data-url="{{value.url}}" data-type="{{value.state}}" data-code="{{value.code}}">
                    {{if value.state=='AWARD_TASK'}}去领取{{else if value.state=='DONE_TASK'}}已完成{{else}}去完成{{/if}}
                </div>
            </div>
            {{/if}}
            {{/each}}
        </div>
        {{/each}}
    </div>
    <div style="text-indent:-999px">11</div>
    <script>
        $('.task-button').on('click', function () {
            var url = $(this).attr('data-url');
            var type = $(this).attr('data-type');
            var code = $(this).attr('data-code');
            var that = $(this);
            if (type === 'TODO_TASK') {
                if (url && url != '') {
                    location.href = url;
                } else {
                    toast({position: 'center', type: 'success', message: '请打开消息通知'});
                }
            } else if (type === 'AWARD_TASK') {
                $.get("/member/task/award/" + code + '.json', function (res) {
                    if (res.status == 1) {
                        toast({position: 'center', type: 'success', message: '领取成功！'});
                        that.removeClass('wait').addClass('over').text('已完成');
                        ajaxF()
                    } else {
                        toast({position: 'center', type: 'success', message: res.result.message});
                    }
                });
            }
        })
</script>
</script>


<
script >
$(function () {
    setTimeout(function () {
        $('.sign-content').css('height', $('.sign-content').width() * .93);
        $('.task-diss').css('height', $('.task-diss').width() * .77);
        var imgH = '-' + $('#taskbg').height() + 'px';
        $('.task-section').css({
            "transform": "translate(0," + imgH + ")",
            "margin-bottom": imgH,
            "-webkit-transform": "translate(0," + imgH + ")",
        })
    }, 300)
    $('.sign-button').on('click', function () {
        $.post("/member/sign.json", function (res) {
            if (res.result.status == 1) {
                var day = res.result.datas.dailySign.totalDay;
                var point = res.result.datas.dailySign.point;
                sendSignStatus();
                signsuccess(day, point);
            } else {
                $.flashTip({position: 'center', type: 'success', message: res.result.message});
            }
        });
    })


    function signsuccess(day, point) {
        var html = '<div class="popup-content"><div class="sign-succuss-content">\
	  <img src="http://static.d2c.cn/img/other/pc_lihua.png" style="width:100%"><p class="success-title">连续签到' + day + '天</p><p style="margin:25px 0 15px;color:#e83333;font-size:14px;">获得+' + point + 'D币</p><p style="color:rgba(0,0,0,.5);font-size:12px;">明日签到可获得+${tomorrowReward}D币</p>\
	  <button class="button" style="background:#000;height:44px;width:80%;color:#fff;line-height:44px;margin-top:40px;" onclick="location.reload();">知道了</button>\
	  </div><div>';
        popupModal({content: html});
    }

    $.get("/pointproduct/list.json", function (res) {
        var list_html = template('list-template', res);
        $('.sign-list').append(list_html)
        var height = $('.list-item a').width() + 'px'
        $('.list-item a').css('height', height)
    });

    function getTaskList() {
        $.get("/member/task/list.json", function (res) {
            var arr = [], data = {}
            for (let i in res.list[0]) {
                arr.push(res.list[0][i]);
            }
            data.list = arr;
            var html = template('task-template', data);
            $('.sign-task').html(html)
        });
    }


    $.get("/member/info.json", function (res) {
        var point = res.member.memberDetail.integration;
        $('#total').html('<a href="/page/mudb">' + point + '</a>')
    });
    //延时询问开关状态
    setTimeout(function () {
        noticeFunc()
        makeClass('no')
    }, 210)

    $('.sign-tips').on("click", function () {
        var html = '<div class="popup-content"><div class="sign-succuss-content" style="height:220px;width:78vw;">\
	  	<p class="success-title"  style="margin-top:0;padding-top:25px;">连续签到规则</p><p style="color: rgba(0,0,0,.5);font-size: 12px;line-height: 20px; width: 80%;margin: 20px auto 0;">一个签到周期为7天，连续签到可获得更多的D币，如果中途中断签到则从第一天累计计算</p>\
	  <button class="button" style="background:#000;height:44px;width:80%;color:#fff;line-height:44px;margin-top:20px;" onclick="location.reload();">知道了</button>\
	  </div><div>';
        popupModal({content: html});
    })

    $('.re-button').on('click', function () {
        openNotice();
        makeClass()
    })

    function makeClass(a) {
        if (isAndroid) {
            setTimeout(function () {
                if (window.respons == 'open') {
                    $('.re-button').addClass('on')
                    if (a != 'no') {
                        $.flashTip({position: 'center', type: 'success', message: '签到通知开启'});
                    }

                } else {
                    $('.re-button').removeClass('on')
                    if (a != 'no') {
                        $.flashTip({position: 'center', type: 'success', message: '签到通知关闭'});
                    }
                }
            }, 100);
        }

    }

    getTaskList();

    window.ajaxF = function () {
        getTaskList();
    }


})
</script>


<@m.page_footer />
