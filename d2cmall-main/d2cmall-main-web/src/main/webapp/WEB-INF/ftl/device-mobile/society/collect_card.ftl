<#import "templates/public_mobile.ftl" as m>
<@m.page_header title="集卡赢好礼" css="cardcollect" description="集卡得无门槛红包，全场5折券，清空购物车，确定不来？" />
<#assign imgHead='//static.d2c.cn/img/topic/181031/cardcollect/images/'>
<#assign imgHeadN='//static.d2c.cn/img/topic/190428/520/'>

<div class="card-container">
    <div class="card-header">
        <label class="top-logo"></label>
        <img src="//static.d2c.cn/img/topic/190428/520/home_bg@3x.png" width="100%" alt="">
        <a href="/page/cardrule" class="card-rule"><label>活动规则</label></a>
        <a href="/collection/card/my" class="card-mine">我的奖品</a>
    </div>
    <div class="card-progress">
        <div class="progress-bar">
            <#assign width = 100/9*myCardDefSize>
            <div class="progress-bar-state"
                 style="width:${width}%;<#if width == '100'>border-bottom-right-radius:8px;border-top-right-radius:8px;</#if>"></div>
        </div>

        <div class="progress-describe">
            <label>集3张赢10元红包</label>
            <label>集6张赢20元红包</label>
            <label class="progress-describe-flex"><span>集9张赢<span class="font-bolder">清空购物车</span></span><span>100元红包&frasl;全场6折卡</span></label>
        </div>
        <div class="progress-flex">
            <#if myCardDefSize gte 3 && myCardDefSize lt 6>
                <#if stageResult.THREE == 0>
                    <div class="reward-button" data-count=${myCardDefSize} data-tem="3"><img
                                src="${imgHeadN}icon_bao.png" alt=""/></div>
                <#else>
                    <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_dakai.png"
                                                                                alt=""/></div>
                </#if>
                <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao.png" alt=""/></div>
                <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_da.png" alt=""/>
                </div>
            <#elseif myCardDefSize gte 6 && myCardDefSize lt 9>
                <#if stageResult.THREE == 0>
                    <div class="reward-button" data-count=${myCardDefSize} data-tem="3"><img
                                src="${imgHeadN}icon_bao.png" alt=""/></div>
                <#else>
                    <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_dakai.png"
                                                                                alt=""/></div>
                </#if>
                <#if stageResult.SIX == 0>
                    <div class="reward-button" data-count=${myCardDefSize} data-tem="6"><img
                                src="${imgHeadN}icon_bao.png" alt=""/></div>
                <#else>
                    <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_dakai.png"
                                                                                alt=""/></div>
                </#if>
                <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_da.png" alt=""/>
                </div>
            <#elseif myCardDefSize == 9>
                <#if stageResult.THREE == 0>
                    <div class="reward-button" data-count=${myCardDefSize} data-tem="3"><img
                                src="${imgHeadN}icon_bao.png" alt=""/></div>
                <#else>
                    <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_dakai.png"
                                                                                alt=""/></div>
                </#if>
                <#if stageResult.SIX == 0>
                    <div class="reward-button" data-count=${myCardDefSize} data-tem="6"><img
                                src="${imgHeadN}icon_bao_da.png" alt=""/></div>
                <#else>
                    <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_dakai.png"
                                                                                alt=""/></div>
                </#if>
                <#if stageResult.NINE == 0>
                    <div class="reward-button" data-count=${myCardDefSize} data-tem="9"><img
                                src="${imgHeadN}icon_bao_da.png" alt=""/></div>
                <#else>
                    <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_da_dakai.png"
                                                                                alt=""/></div>
                </#if>
            <#else>
                <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao.png" alt=""/></div>
                <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao.png" alt=""/></div>
                <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_da.png" alt=""/>
                </div>
            </#if>
        </div>
        <div class="progress-flex progress-reward" style="bottom:34px;display:none;">
            <#if myCardDefSize gte 3 && myCardDefSize lt 6>
                <#if stageResult.THREE == 0>
                    <img src="${imgHead}reward1_first.png" alt=""/>
                <#else>
                    <img src="${imgHead}received.png" alt=""/>
                </#if>
                <img src="${imgHead}reward1_second_grey.png" alt=""/>
                <img src="${imgHead}reward1_third_grey.png" alt=""/>
            <#elseif myCardDefSize gte 6 && myCardDefSize lt 9>
                <#if stageResult.THREE == 0>
                    <img src="${imgHead}reward1_first.png" alt=""/>
                <#else>
                    <img src="${imgHead}received.png" alt=""/>
                </#if>
                <#if stageResult.SIX == 0>
                    <img src="${imgHead}reward1_second.png" alt=""/>
                <#else>
                    <img src="${imgHead}received.png" alt=""/>
                </#if>
                <img src="${imgHead}reward1_third_grey.png" alt=""/>
            <#elseif myCardDefSize == 9>
                <#if stageResult.THREE == 0>
                    <img src="${imgHead}reward1_first.png" alt=""/>
                <#else>
                    <img src="${imgHead}received.png" alt=""/>
                </#if>
                <#if stageResult.SIX == 0>
                    <img src="${imgHead}reward1_second.png" alt=""/>
                <#else>
                    <img src="${imgHead}received.png" alt=""/>
                </#if>
                <#if stageResult.NINE == 0>
                    <img src="${imgHead}reward1_third.png" alt=""/>
                <#else>
                    <img src="${imgHead}received.png" alt=""/>
                </#if>
            <#else>
                <img src="${imgHead}reward1_first_grey.png" alt=""/>
                <img src="${imgHead}reward1_second_grey.png" alt=""/>
                <img src="${imgHead}reward1_third_grey.png" alt=""/>
            </#if>
        </div>
        <div class="reward-mine" data-count=${myCardDefSize}>
            <#if myCardDefSize < 3>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">差${3-myCardDefSize}张不同卡抽10元红包</label>
            <#elseif myCardDefSize == 3 && stageResult.THREE == 0>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">领取奖励</label>
            <#elseif myCardDefSize == 3 && stageResult.THREE == 1>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">差3张不同卡抽10元红包</label>
            <#elseif myCardDefSize gt 3 && myCardDefSize lt 6 && stageResult.SIX == 0>
                <label>我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">差${6-myCardDefSize}张不同卡抽20元红包</label>
            <#elseif myCardDefSize == 6 && stageResult.SIX == 0>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">领取奖励</label>
            <#elseif myCardDefSize == 6 && stageResult.SIX == 1>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">差3张不同卡抽10元红包</label>
            <#elseif myCardDefSize gt 6 && myCardDefSize lt 9 && stageResult.NINE == 0>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">差${9-myCardDefSize}张不同卡抽超级大奖</label>
            <#elseif myCardDefSize == 9>
                <label class="myCard-bloder">我的卡片${myCardDefSize}&frasl;9</label>
                <label class="reward-card">已集齐9张卡片</label>
            </#if>
        </div>
        <#--  <div class="reward-button" data-count=${myCardDefSize}>
        <#if myCardDefSize < 3>
        还差${3-myCardDefSize}种卡 领取奖励
        <#elseif myCardDefSize == 3 && stageResult.THREE == 0>
        已有3种卡 点击领奖
        <#elseif myCardDefSize == 3 && stageResult.THREE == 1>
        还差3种卡 领取奖励
        <#elseif myCardDefSize gt 3 && myCardDefSize lt 6 && stageResult.SIX == 0>
        还差${6-myCardDefSize}种卡 领取奖励
        <#elseif myCardDefSize == 6 && stageResult.SIX == 0>
        已有6种卡 点击领奖
        <#elseif myCardDefSize == 6 && stageResult.SIX == 1>
        还差3种卡 领取奖励
        <#elseif myCardDefSize gt 6 && myCardDefSize lt 9 && stageResult.NINE == 0>
        还差${9-myCardDefSize}种卡 领取奖励
        <#elseif myCardDefSize == 9>
        已集齐9张卡
        </#if>
        </div>	  -->
    </div>
    <div id="notice-bar">
        <ul>
            <li>
                <#list recentlyAwards as items>
                    <div>恭喜${items.memberName}${items.awardName}~</div>
                </#list>
            </li>
        </ul>
    </div>
    <#if myCardDefSize == 9>
        <div class="count-down award-count" data-type="split-time" data-defined="millisecond"
             data-endtime="2019/5/19 20:00:00" style="font-size:0;margin:30px 0;">
            <span style="font-size:16px;color:#fff;margin-right:10px;">开奖倒计时</span>
            <span class="hour down-time">00</span>
            <span class="minute down-time">00</span>
            <span class="second down-time">00</span>
        </div>
        <div class="final-award">
            <div class="item-bg front">
                <img src="//img.d2c.cn/2018/11/10/07593567859a8be99da0592b915329e0a460cd.jpg" width="100%" alt="">
            </div>
            <div class="item-bg back"></div>
        </div>
        <div class="award-button">点击开奖</div>

    <#else>
        <div id="card-preview" style="position:relative;margin-top:40px;">
            <div class="disabled-preview" style="position:relative;height:280.8px;">
                <div class="card-mask"></div>
                <img src="//img.d2c.cn//2018/10/29/0806158bf49fa8b1c4c8f07ff3281a57105c31.png" width="60%"
                     height="280.8px;" style="border-radius:4px;" class="disabled-preview-img" alt="">
            </div>
            <div class="swiper-container">
                <div class="swiper-wrapper card-content"></div>
                <div class="swiper-pagination" style="bottom:10px!important;"></div>
            </div>
            <div class="card-shrink">
                <#list allCardArray as shrinkItem>
                    <div class="shrink-item <#if shrinkItem.items?size == 0>disabled</#if>" data-id="${shrinkItem.id}"
                         data-index="${shrinkItem_index}" data-disabled=<#if shrinkItem.items?size == 0>true</#if>>
                        <div>
                            <img src="//img.d2c.cn/${shrinkItem.pic}" width="100%" alt="">
                            <#if shrinkItem.items?size gt 0><span class="card-num">${shrinkItem.items?size}</span></#if>
                        </div>
                        <span>${shrinkItem.name}</span>
                    </div>
                </#list>
            </div>
        </div>
        <div id="card-button">
            <div class="card-draw" data-count="${count}" data-tip="1"><span
                        style="display:inline-block;width: 97%;height:36px;border:1px solid #703814;margin-top:3px;line-height:36px;">点击抽卡&nbsp;&nbsp;&Chi;${count}</span>
            </div>
        </div>
    </#if>
    <div id="card-tasks" style="margin-top:15px;">
        <p class="task-title" style="display:none;"><span class="circle1"></span><span class="circle2"></span><span
                    class="circle3"></span>限时任务<span class="circle3"></span><span class="circle2"></span><span
                    class="circle1"></span></p>
        <div class="task-item" style="display:none;">
            <div class="task-item-left">
                <p style="color:#fff;">测测你的专属穿衣风格</p>
                <p style="color:#FCD8BA;font-size:12px;">抽卡次数+1</p>
            </div>
            <div class="task-item-right" data-type="simpleAnswer" data-share="${wardrobe}"><#if wardrobe==0>去创建<#else>
                    <span style="display:inline-block;width:100%;background:#999999;color:#fff;">已完成</span></#if></div>
        </div>
        <p class="task-title"><span class="circle1"></span><span class="circle2"></span><span class="circle3"></span>每日任务<span
                    class="circle3"></span><span class="circle2"></span><span class="circle1"></span></p>
        <div class="task-item">
            <div class="task-item-left">
                <p style="color:#fff;">分享520主会场给好友</p>
                <p style="color:#FCD8BA;font-size:12px;">抽卡次数+1</p>
            </div>
            <div class="task-item-right" data-type="mainActivityShare"
                 data-share="${share520}"><#if share520==0>去分享<#else><span
                        style="display:inline-block;width:100%;background:#999999;color:#fff;">已完成</span></#if></div>
        </div>
        <div class="task-item">
            <div class="task-item-left">
                <p style="color:#fff;">分享本活动给好友</p>
                <p style="color:#FCD8BA;font-size:12px;">抽卡次数+1</p>
            </div>
            <div class="task-item-right" data-type="simpleShare" data-share="${share}"><#if share==0>去分享<#else><span
                        style="display:inline-block;width:100%;background:#999999;color:#fff;">已完成</span></#if></div>
        </div>
        <div class="task-item">
            <div class="task-item-left">
                <p class="text-title" style="color:#fff;">邀请好友解锁卡片</p>
                <p style="color:#FCD8BA;font-size:12px;">不限制次数</p>
            </div>
            <div class="task-item-right" data-type="inviteShare">去邀请</div>
        </div>
    </div>
    <a href="/page/520zhounianqing"><img src="//static.d2c.cn/img/topic/190913/sbsbs.jpg" width="100%"
                                         style="margin-top:20px;"/></a>
</div>
<div class="bind-form">
    <div class="form-normal">
        <p style="color:rgba(0,0,0,.85);font-size:20px;font-weight:bold;text-align:center;">邀请好友解锁卡片</p>
        <p style="font-size:12px;color:rgba(0,0,0,.5);text-align:center;margin-top:1.25rem;">获无门槛红包，全场5折券，清空购物车</p>
        <input type="number" name="mobile" placeholder="请输入手机号" class="bind-form-mobile"
               style="border:0;border:.5px solid #B2B2B2;border-radius:2px;margin-top:1.5625rem;color:#000;width:100%;padding:.5rem;"/>
        <div class="bind-sure-button" style="color:#FFF;background:#000;margin-top:1.5rem;">确定</div>
    </div>
    <div class="form-next" style="display:none;">
        <p style="color:rgba(0,0,0,.85);font-size:20px;font-weight:bold;text-align:center;">请输入验证码</p>
        <input type="number" name="code" class="validate-code" id="" value="" maxlength="4" autocomplete="off"
               style="opacity:0;position:absolute;top:100px;left:-1000px;">
        <div class="form-flex">
            <div class="code-frame"></div>
            <div class="code-frame"></div>
            <div class="code-frame"></div>
            <div class="code-frame"></div>
        </div>
        <div class="code-error" style="display:none;text-align:center;margin-top:.625rem;color:#F21A1A;font-size:14px;">
            验证码错误请重新输入
        </div>
        <div class="code-modify" style="text-align:center;margin-top:.625rem;color:#842300;font-size:14px;">修改手机号 >
        </div>
        <button class="code-button" data-type="code">重新获取验证码</button>
    </div>
    <i class="bind-form-close"></i>
</div>
<div class="bind-mask"></div>
<div class="wx-mini-share share-mask"></div>
<div class="wx-share share-mask"></div>

<share data-title="集卡清空购物车" data-desc="集卡得无门槛红包，全场5折券，清空购物车，确定不来？"
       data-pic="https://img.d2c.cn/2018/10/25/073802a1bd6b3a8fde5c25760a4161c78c0d14.png"
       data-url="https://m.d2cmall.com/collection/card/share/detail?memberId=${m.LOGINMEMBER.id}"></share>

<script id="card-template" type="text/html">
    {{each list as value}}
    <div class="swiper-slide content-item">
        <div class="item-bg front">
            <img src="//img.d2c.cn{{value.pic}}" width="100%" style="border-radius:4px;" alt="">
            <img src="${imgHeadN}biao_fanzhuan@3x.png" class="card-turn" width="100%" alt="">
            <#--  <a href="{{value.url}}" class="card-more">了解更多</a>  -->
        </div>
        <div class="item-bg back" style="width:100%;height:100%">恭喜您！获得{{value.point}}积分</div>
    </div>
    {{/each}}
</script>
<script id="lottery-template" type="text/html">
    {{each list as value i}}
    <li class="card-li" data-index="{{i}}">
        <div class="card-item front"><img src="${imgHead}group2-{{i+1}}.png" width="100%" alt=""></div>
        <div class="card-item back">
            {{if value && value.pic}}
            <img src="//img.d2c.cn{{value.pic}}" width="100%" alt="">
            {{/if}}
        </div>
    </li>
    {{/each}}
</script>

<script>

    //定义所有类型卡片数组
    var allCards = [<#list allCardArray as allArray>{'pic': '//img.d2c.cn' + '${allArray.pic}'}, </#list>];

    //定义用户拥有卡片数组
    var haveCards = [<#list allCardArray as allArray><#list allArray.items as arr>{
        'pic': '${arr.pic}',
        'url': '${arr.url}',
        'point': '${arr.point}',
        'defId': '${arr.defId}'
    }, </#list></#list>];

    //滚动通知
    $('#notice-bar').jcMarquees({'marquee': 'y', 'speed': 40});

    //最终奖励
    $('.award-button').on('click', function () {
        if (!_memberId) {
            userLogin();
            return false;
        }
        $.post('/collection/card/stage/award/NINE', function (data) {
            if (data.result.status == -1) {
                $.flashTip({position: 'center', type: 'error', message: data.result.message, time: 1500});
            } else {
                $('.final-award').addClass('flipped');
                $('.final-award .back').text(data.award.awardName);
                $.flashTip({position: 'center', type: 'success', message: '恭喜你获得终极大奖！', time: 1500});
            }
        }, 'json');
    });

    //领取前两个奖励
    $('.reward-button').each(function () {
        $(this).off('click');
        $(this).on('click', function () {
            if (!_memberId) {
                userLogin();
                return false;
            }
            var count = $(this).attr('data-count');
            var stageValue = '';
            if (${stageResult.THREE} == 0 && count >= 3
        )
            {
                stageValue = 'THREE';
            }
        else
            if (${stageResult.SIX} == 0 && count >= 6
        )
            {
                stageValue = 'SIX';
            }
        else
            {
                //$.flashTip({position:'center',type:'error',message:'未满足条件哦~',time:1500});
                $('.award-button').click();
                return false;
            }
            $.post('/collection/card/stage/award/' + stageValue, function (data) {
                if (data.result.status == 1) {
                    $.flashTip({
                        position: 'center',
                        type: 'success',
                        message: '恭喜你获得了' + data.award.awardName,
                        time: 1500
                    });
                    setTimeout(function () {
                        location.reload();
                    }, 3000);
                } else {
                    $.flashTip({position: 'center', type: 'success', message: data.result.message});
                }
            }, 'json');

        });
    })
    //关闭弹窗
    $('.share-mask').on('click', function () {
        var obj = $(this);
        setTimeout(function () {
            obj.hide();
            location.reload();
        }, 2500);

    });
    //做任务
    $('.task-item-right').on('click', function () {
        if (!_memberId) {
            userLogin();
            return false;
        }
        var obj = $(this);
        var type = obj.attr('data-type');
        var share = obj.attr('data-share');
        if (type === 'simpleAnswer') {
            if (share === '0') {
                location.href = "/member/poster/question";
            } else {
                $.flashTip({position: 'center', type: 'error', message: '限时参加一次', time: 1500});
            }
        } else if (type === 'mainActivityShare') {
            if (share === '0') {
                if (app_client === true && !isWeChat) {
                    /* var data = new ShareDatas();
                     var myData = data.message;
                     $.D2CMerchantBridge(myData);
                     */
                    location.href = "/page/520zhounianqing";
                } else {
                    $.flashTip({position: 'center', type: 'error', message: '请下载D2C app后发起分享', time: 1500});
                }
                /*
                $.get('/collection/card/share.json',function(data){
                    var showName = data.member.nickname!=null?data.member.nickname:data.member.displayName;
                    showName = showName.length<7?showName:showName.substr(0,6)+'...';
                    var URL = "https://www.d2cmall.com/page/520zhounianqing";
                    var _content = '<div class="popup-content">'
                        +'<div class="share_page">'
                        +'<div style="position:absolute;top:0;left:0;bottom:0;right:0;margin:auto;width:281px;height:80vh;"><div class="share-center"><div class="user-name"><span><label>'+ showName +'</label>邀请您</span></div><br><img src="https://chart.googleapis.com/chart?cht=qr&chs=150x150&choe=UTF-8&chld=L|4&chl='+URL+'" alt="微信扫一扫" /><span style="font-size:14px;line-height:21px;color:#FCD8BA;margin-top:12px;">520周年庆</span><span style="font-size:10px;line-height:13px;color:#FCD8BA;">更多精彩等你发现</span></div>'
                        +'<div style="width:281px;margin:14px auto;"><div class="line">分享到</div></div>'
                        +'<div class="share-btn"><div class="item-icon" style="display:block;" data-type="wx"><img src="
                ${imgHeadN}icon_share_wx.png" alt=""><span>微信</span></div><div class="item-icon" style="display:block;" data-type="pyq"><img src="
                ${imgHeadN}icon_share_pyq.png" alt=""><span>朋友圈</span></div><!-- <div class="item-icon" style="display:block;" data-type="bc"><img src="
                ${imgHeadN}icon_share_tp.png" alt=""><span>保存图片</span></div> --></div>'
                        +'<div class="card-drawn-btn-N" style="margin-top:19px;"><span></span></div></div></div></div>';
                    popupModal({
                        content:_content
                    });
                    $('.item-icon').off('click');
                    $('.item-icon').on('click',function(){
                        var type = $(this).attr('data-type');
                        if(type == 'bc') {
                            html2canvas(document.querySelector(".share-center"),{
                                useCORS:true,
                                logging:true,   
                            }).then(canvas => {
                                var url = canvas.toDataURL('image/png')
                                var a = document.createElement('a')
                                var event = new MouseEvent('click')
                                var timestamp = Date.parse(new Date());
                                a.download = timestamp
                                a.href = url
                                a.dispatchEvent(event)
                            });


                        }else{
                            if(app_client === true && !isWeChat) {
                                var data = new ShareDatas();
                                var myData = data.message;
                                $.D2CMerchantBridge(myData);
                            }else if(isWeChat) {
                                if(window.__wxjs_environment === 'miniprogram'){
                                    popupModalClose();
                                    wx.miniProgram.postMessage({data: 'share' });
                                    $('.wx-mini-share').show();
                                }else{
                                    popupModalClose();
                                    $('.wx-share').show();
                                }
								
                                $('.share-mask').on('click',function(){
                                    var obj = $(this);
                                    $.post('/share/out',{'url':'/page/520zhounianqing'},function(data){
                                        if(data.result.status == 1){
                                            obj.hide();
                                            setTimeout(function(){
                                                location.reload();
                                            },2500);
                                        }
                                    },'json');
                                });
                            }else {
                                alert('请在微信中打开');
                            }
                        }
                    });
                    $('.card-drawn-btn-N').off('click');
                    $('.card-drawn-btn-N').on('click', function(){
                        popupModalClose();
                        setTimeout(function(){
                            location.reload();
                        },1000);
                    });
                });*/

            } else {
                $.flashTip({position: 'center', type: 'error', message: '每天只能分享一次哦~', time: 1500});
            }
        } else if (type === 'simpleShare') {
            if (share === '0') {
                $('share').attr('data-url', document.location.href);
                $.get('/collection/card/share.json', function (data) {
                    var showName = data.member.nickname != null ? data.member.nickname : data.member.displayName;
                    showName = showName.length < 7 ? showName : showName.substr(0, 6) + '...';
                    var URL = window.location.href;
                    var _content = '<div class="popup-content">'
                        + '<div class="share_page">'
                        + '<div style="position:absolute;top:0;left:0;bottom:0;right:0;margin:auto;width:281px;height:80vh;"><div class="share-center"><div class="user-name"><span><label>' + showName + '</label>邀请您</span></div><br><img src="/picture/code?type=1&width=50&height=50&noLogo=true&&code=' + URL + '" alt="微信扫一扫" /><span style="font-size:14px;line-height:21px;color:#FCD8BA;margin-top:12px;">赶快扫码,集卡清空购物车</span><span style="font-size:10px;line-height:13px;color:#FCD8BA;">更有全场6折&frasl;100元现金红包等你拿</span></div>'
                        + '<div style="width:281px;margin:14px auto;"><div class="line">分享到</div></div>'
                        + '<div class="share-btn"><div class="item-icon" style="display:block;" data-type="wx"><img src="${imgHeadN}icon_share_wx.png" alt=""><span>微信</span></div><div class="item-icon" style="display:block;" data-type="pyq"><img src="${imgHeadN}icon_share_pyq.png" alt=""><span>朋友圈</span></div></div>'
                        + '<div class="card-drawn-btn-N" style="margin-top:8px;"><span></span></div></div></div></div>';
                    popupModal({
                        content: _content
                    });
                    $('.item-icon').off('click');
                    $('.item-icon').on('click', function () {
                        var type = $(this).attr('data-type');
                        if (type == 'bc') {
                            html2canvas(document.querySelector(".share-center"), {
                                useCORS: true,
                                logging: true,
                            }).then(canvas = > {
                                var url = canvas.toDataURL('image/png')
                                var a = document.createElement('a')
                                var event = new MouseEvent('click')
                                var timestamp = Date.parse(new Date());
                            a.download = timestamp
                            a.href = url
                            a.dispatchEvent(event)
                        })
                            ;
                        } else {
                            if (app_client === true && !isWeChat) {
                                var data = new ShareDatas();
                                var myData = data.message;
                                $.D2CMerchantBridge(myData);
                            } else if (isWeChat) {
                                if (window.__wxjs_environment === 'miniprogram') {
                                    popupModalClose();
                                    wx.miniProgram.postMessage({data: 'share'});
                                    $('.wx-mini-share').show();
                                } else {
                                    popupModalClose();
                                    $('.wx-share').show();
                                }
                                $('.share-mask').on('click', function () {
                                    var obj = $(this);
                                    $.post('/share/out', {'url': '/collection/card/home'}, function (data) {
                                        if (data.result.status == 1) {
                                            obj.hide();
                                            setTimeout(function () {
                                                location.reload();
                                            }, 2500);
                                        }
                                    }, 'json');
                                });
                            } else {
                                $.flashTip({position: 'center', type: 'error', message: '请在微信中打开~', time: 1500});
                            }
                        }
                    });
                    $('.card-drawn-btn-N').off('click');
                    $('.card-drawn-btn-N').on('click', function () {
                        popupModalClose();
                        setTimeout(function () {
                            location.reload();
                        }, 1000);
                    });
                });
            } else {
                $.flashTip({position: 'center', type: 'error', message: '每天只能分享一次哦~', time: 1500});
            }
        } else {
            if (app_client === true && !isWeChat) {
                $('share').attr('data-url', 'https://m.d2cmall.com/collection/card/share/detail?memberId=${m.LOGINMEMBER.id}');
                var data = new ShareDatas();
                var myData = data.message;
                $.D2CMerchantBridge(myData);
            } else {
                $.flashTip({position: 'center', type: 'error', message: '请下载D2C app后发起分享', time: 1500});
            }
            /*
            if(!_isD2C){
                $(".bind-form, .bind-mask").slideDown();
                return false;
            }
            $.get('/collection/card/share.json',function(data){
                var showName = data.member.nickname!=null?data.member.nickname:data.member.displayName;
                showName = showName.length<7?showName:showName.substr(0,6)+'...';
                var _content = '<div class="popup-content">'
                    +'<div class="share_page">'
                    +'<div style="position:absolute;top:0;left:0;bottom:0;right:0;margin:auto;width:281px;height:80vh;"><div class="share-center"><div class="user-name"><span><label>'+ showName +'</label>邀请您</span></div><br><img src="/picture/code?type=1&width=50&height=50&noLogo=true&&code=http://test2.d2cmall.com/collection/card/share/detail?memberId='+data.member.id+'" alt="微信扫一扫" /><span style="font-size:14px;line-height:21px;color:#FCD8BA;margin-top:12px;">帮好友集卡</span><span style="font-size:10px;line-height:13px;color:#FCD8BA;">长按识别二维码</span></div>'
                    +'<div style="width:281px;margin:14px auto;"><div class="line">分享到</div></div>'
                    +'<div class="share-btn"><div class="item-icon" style="display:block;" data-type="wx"><img src="
            ${imgHeadN}icon_share_wx.png" alt=""><span>微信</span></div><div class="item-icon" style="display:block;" data-type="pyq"><img src="
            ${imgHeadN}icon_share_pyq.png" alt=""><span>朋友圈</span></div>  <!-- <div class="item-icon" style="display:block;" data-type="bc"><img src="
            ${imgHeadN}icon_share_tp.png" alt=""><span>保存图片</span></div> --></div>'
                    +'<div class="card-drawn-btn-N" style="margin-top:8px;"><span></span></div></div></div></div>';
                popupModal({
                    content:_content
                });

                //定义分享的参数
                var _target = encodeURIComponent('/collection/card/share/detail?memberId=
            ${member.id}');
                    var _pic = 'https://poster.d2c.cn?tpl=1111card&img=
            ${HEADPIC}&qr=' + _target;
                    var message = {
                        handlefunc:'',
                        pic:_pic
                    }
                });
                
                $('.item-icon').off('click');
                $('.item-icon').on('click',function(){
                    var type = $(this).attr('data-type');
                    if(type == 'bc') {
                        html2canvas(document.querySelector(".share-center"),{
                            useCORS:true,
                            logging:true,   
                        }).then(canvas => {
                            var url = canvas.toDataURL('image/png')
                            var a = document.createElement('a')
                            var event = new MouseEvent('click')
                            var timestamp = Date.parse(new Date());
                            a.download = timestamp
                            a.href = url
                            a.dispatchEvent(event)
                        });
                    }else{
                        if(app_client === true && !isWeChat){       
                            message.handlefunc = type === 'pyq' ? 'w_pyq' : 'w_wx';
                            $.D2CMerchantBridge(message);
                            var data = new ShareDatas();
                            var myData = data.message;
                            $.D2CMerchantBridge(myData);
                        }else if(isWeChat){
                            var html = '<div class="popup-content">\
                                <div style="width:300px;line-height:2;color:#fff;text-align:center;font-size:14px;">\
                                <img src="'+_pic +'" width="100%" alt="">\
                                <p>长按图片发给好友</p>\
                                <a href="javascript:popupModalClose();" class="modal-close"></a>\
                                </div>\
                                </div>';
                            popupModal({content:html});	
                            if(window.__wxjs_environment === 'miniprogram'){
                                popupModalClose();
                                wx.miniProgram.postMessage({data: 'share' });
                                $('.wx-mini-share').show();
                            }else{
                                popupModalClose();
                                $('.wx-share').show();
                            }
                            
                        }else {
                            $.flashTip({position:'center',type:'error',message:'请在微信中打开~',time:1500});
                            alert('请在微信中打开~');
                        }
                    }
                    
                });
                $('.card-drawn-btn-N').off('click');
                $('.card-drawn-btn-N').on('click', function(){
                    popupModalClose();
                    setTimeout(function(){
                        location.reload();
                    },1000);
                });
            
            });
			//location.href="/collection/card/share";
			*/
        }
    });
    //关闭绑定弹窗
    $('.bind-form-close').on('click', function () {
        $(".bind-form, .bind-mask").slideUp();
    });
    //倒计时
    var countdown = 60;

    function countDown() {
        var obj = $(".code-button");
        settime(obj);
    }

    function settime(obj) { //发送验证码倒计时
        if (countdown == 0) {
            obj.removeClass("code-button-grey");
            countdown = 60;
            return false;
        } else {
            countdown--;
            obj.addClass("code-button-grey");
        }
        setTimeout(function () {
            settime(obj)
        }, 1000);
    }

    //绑定按钮操作
    $('.bind-sure-button').on('click', function () {
        var obj = $(this);
        var myReg = /^1\d{10}$/;
        var mobileVal = $('.bind-form-mobile').val();
        if (mobileVal.length == 11 && myReg.test(mobileVal)) {
            $('.form-normal').hide();
            $('.form-next').fadeIn();
            var sign = hex_md5("mobile=" + mobileVal + secretKey),
                appParams = $.base64('encode', "mobile=" + mobileVal + "&sign=" + sign),
                data = {mobile: mobileVal, type: 'MEMBERMOBILE', terminal: 'PC', nationCode: 86, appParams: appParams};
            $.ajax({
                url: '/sms/send/encrypt',  //短信验证码
                type: 'post',
                data: data,
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        $.flashTip({position: 'center', type: 'success', message: '验证码已发送请查收'});
                        countDown();
                    }
                    return false;
                }
            });
        } else {
            $.flashTip({position: 'center', type: 'error', message: '请输入正确的手机号码', time: 1500});
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
                data: {"loginCode": $('.bind-form-mobile').val(), "code": $('.validate-code').val(), "nationCode": 86},
                success: function (data) {
                    if (data.result.status == 1) {
                        location.href = "/collection/card/share";
                    } else {
                        $('input[name=code]').val('');
                        $('.code-error').show();
                    }
                }
            });
        }
    });
    //再次获取验证码
    $('.code-button:not(.code-button-grey)').on('click', function () {
        var obj = $(this);
        if (obj.prop("disabled") == false) {
            var mobileVal = $('.bind-form-mobile').val(),
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
        $('.bind-form-mobile').val('');
    });

    //卡片放大
    $('.card-shrink').on('click', '.shrink-item', function () {
        if (!_memberId) {
            userLogin();
            return false;
        }
        var obj = $(this),
            state = obj.attr('data-disabled'),
            id = obj.attr('data-id'),
            index = obj.attr('data-index'),
            arr = [],
            len = haveCards.length;
        if (state === 'true') {
            $('.disabled-preview').show();
            $('.disabled-preview-img').attr('src', allCards[index].pic);
        } else {
            $('.disabled-preview').hide();
        }
        for (var i = 0; i < len; i++) {
            if (id === haveCards[i].defId) {
                arr.push(haveCards[i]);
            }
        }
        obj.addClass('selected').siblings().removeClass('selected');
        var html = template('card-template', {list: arr});
        $('.swiper-wrapper').html(html);
        var mySwiper = new Swiper('.swiper-container', {
            initialSlide: 1,//初始化时slide的索引
            observer: true,//自动初始化swiper
            observeParents: true,
            slidesPerView: "auto",
            centeredSlides: !0,//居中展示
            spaceBetween: 14,//间距
            on: {
                slideChangeTransitionEnd: function () {
                    $('.swiper-slide').each(function () {
                        if ($(this).find('.item-bg').hasClass('enlarge')) {
                            $(this).find('.item-bg').removeClass('enlarge');
                        }
                        if (!$(this).find('.item-bg').hasClass('original')) {
                            $(this).find('.item-bg').addClass('original');
                        }
                        $(this).find('.back').css({'width': '83.3333%', 'height': '83.3333%'});
                    });
                    $('.swiper-slide-active').find('.item-bg').addClass('enlarge');
                    $('.swiper-slide-active').find('.item-bg').removeClass('original');
                    $('.swiper-slide-active').find('.back').css({'width': '100%', 'height': '100%'});
                    console.log(this.activeIndex);
                },
            },
        });

        mySwiper.slideTo(0, 1000, true);
        //翻转
        $('.card-turn').on('click', function () {
            $(this).parent().parent().addClass('flipped');
            setTimeout(function () {
                $('.card-turn,.card-more').hide();
            }, 1500)
        });
    });


    //默认切换到第一个slide，速度为1秒
    <#if m.LOGINMEMBER.id>
    $('.card-shrink .shrink-item').first().trigger('click');
    </#if>

    //立即抽卡
    $('.card-draw').on('click', function () {
        if (!_memberId) {
            userLogin();
            return false;
        }
        var obj = $(this);
        if (obj.attr('data-tip') == '0') {
            $.flashTip({position: 'center', type: 'success', message: '操作过于频繁,请稍后重试!', time: 1500});
            return false;
        }
        obj.attr('data-tip', '0');
        var count = $(this).attr('data-count');
        if (count === '0') {
            $.flashTip({position: 'center', type: 'success', message: '你还没有抽卡次数，快去做任务吧~', time: 1500});
            var h = $(document).height() - $(window).height();
            $('html, body').animate({scrollTop: h}, 400);
            return false;
        }
        var _contentNew = '<div class="popup-content">'
            + '<div class="card-drawn"><img class="bg-zuo" src="${imgHeadN}bg_zuo.png" width="78px" height="94px" alt=""><img class="bg-you" src="${imgHeadN}bg_you.png" width="137px" height="166px" alt="">'
            + '<div class="card-center"><div class="card-center-inside"><img src="" width="100%" height="300px" style="border-radius:11px;" alt=""></div><img src="${imgHeadN}bg_ka.png" width="100%" style="margin-top:-62px;" alt=""><span class="card-center-tip"></span></div>'
            + '<div class="card-drawn-btn"><span style="display:inline-block;width:162px;height:36px;border:1px solid #703814;margin-top:2px;line-height:36px;"></span></div></div></div>';
        /*popupModal({
            content:_contentNew
        });*/
        var _index = parseInt(Math.random() * (9 - 0 + 1) + 0);
        $.post('/collection/card/select', {'selectIndex': _index}, function (data) {
            if (data.result.status == -1) {
                $.flashTip({position: 'center', type: 'success', message: data.result.message, time: 1500});
                var h = $(document).height() - $(window).height();
                $('html, body').animate({scrollTop: h}, 400);
                obj.attr('data-tip', '1');
                return false;
            }
            popupModal({
                content: _contentNew
            });
            if (awardCard == null || awardCard == "null") {
                $('.card-center-tip').text('好运就在下一次');
                $('.card-drawn-btn > span').text('再接再励');
                $('.card-drawn-btn').off('click');
                $('.card-drawn-btn').on('click', function () {
                    popupModalClose();
                    setTimeout(function () {
                        location.reload();
                        obj.attr('data-tip', '1');
                    }, 1000);
                });
                return false;
            }

            $('.card-center-tip').text('恭喜您获得' + data.awardCard.name + '卡');
            $('.card-center-inside > img').attr('src', "//img.d2c.cn/" + data.awardCard.pic);
            $('.card-drawn-btn > span').text('领取卡片');
            $('.card-drawn-btn').off('click');
            $('.card-drawn-btn').on('click', function () {
                popupModalClose();
                setTimeout(function () {
                    location.reload();
                    obj.attr('data-tip', '1');
                }, 3500);
            });

        }, 'json');
    });

</script>




<@m.page_footer js='utils/html2canvas.min' />