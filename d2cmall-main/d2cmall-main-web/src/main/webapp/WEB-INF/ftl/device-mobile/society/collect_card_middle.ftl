<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='集卡赢好礼' css="cardcollect" description="集卡得无门槛红包，全场5折券，清空购物车，确定不来？" />
<#assign MEMBERID = RequestParameters.memberId>
<#assign imgHead='//static.d2c.cn/img/topic/181031/cardcollect/images/'>
<#assign imgHeadN='//static.d2c.cn/img/topic/190428/520/'>
<#if shareHeadPic && shareHeadPic?index_of('http') != -1>
    <#assign HEADPIC = shareHeadPic>
<#elseif shareHeadPic && shareHeadPic?index_of('http') == -1>
    <#assign HEADPIC = 'https://img.d2c.cn' + shareHeadPic>
<#else>
    <#assign HEADPIC = 'https://static.d2c.cn/img/home/160627/images/headpic.png'>
</#if>


<div class="card-container">
    <div class="card-header">
        <label class="top-logo"></label>
        <img src="//static.d2c.cn/img/topic/190428/520/home_bg@3x.png" width="100%" alt="">
        <a href="/page/cardrule" class="card-rule"><label>活动规则</label></a>
        <a href="/collection/card/my" class="card-mine">我的奖品</a>
    </div>
    <div class="card-progress">
        <div class="progress-bar"></div>
        <#assign width = 9*myCardDefSize>

        <div class="progress-describe">
            <label>集3张赢10元红包</label>
            <label>集6张赢20元红包</label>
            <label class="progress-describe-flex"><span>集9张赢<span class="font-bolder">清空购物车</span></span><span>100元红包&frasl;全场6折卡</span></label>
        </div>
        <div class="progress-flex">

            <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao.png" alt=""/></div>
            <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao.png" alt=""/></div>
            <div class="reward-button" data-count=${myCardDefSize}><img src="${imgHeadN}icon_bao_da.png" alt=""/></div>

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
        <div class="reward-mine" data-count=${myCardDefSize} style="width:100%">
            <label class="myCard-bloder">好友还差${9-myCardDefSize}张卡片就能抽清空购物车大奖</label>
            <label class="reward-card">帮好友抽卡,你也有机会获得红包或积分哦</label>
        </div>
    </div>
    <div id="notice-bar">
        <ul>
            <li>
            </li>
        </ul>
    </div>
    <div id="card-preview" style="position:relative;margin-top:24px;">
        <div class="card-shrink-new">
        </div>
    </div>
    <#--  <div style="margin:15px 4% 0;padding:10px 12px 12px 15px;background:#957575;">
        <img src="${HEADPIC}" style="float:left;width:40px;height:40px;border-radius:100%;margin-right:5px;"  alt="">
        <p style="color:#fff;text-align:left;">帮我抽卡清空购物车，还有全场5折，100元现金红包大礼</p>
    </div>  -->
    <div class="card-lottery" style="width:92%;margin:0 4%;display:none;">
        <p style="color:#2C0904;font-size:12px;padding-top:20px;">点击帮好友抽卡，赢取大礼</p>
        <ul></ul>
    </div>
    <div id="card-button">
        <div class="card-draw" data-tip="1"><span
                    style="display:inline-block;width:97%;height:36px;border:1px solid #703814;margin-top:3px;line-height:36px;">帮好友抽卡</span>
        </div>
    </div>
    <a href="/collection/card/home"
       style="background:#fff;width:92%;height:44px;line-height:44px;display:block;margin: 19px auto 5px;font-size:16px;color:#793F16;letter-spacing: 3px;"><span
                style="display:inline-block;width:97%;height:36px;border:1px solid #703814;margin-top:3px;line-height:36px;">我也要参加</span></a>
    <a href="/page/520zhounianqing"><img src="//static.d2c.cn/img/topic/190913/sbsbs.jpg" width="100%"
                                         style="margin-top:20px;"/></a>
</div>



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
<script id="allCards-template" type="text/html">
    {{each list as value}}
    <div class="shrink-item" data-id={{value.id}} data-index={{value_index}} data-disabled=true>
        <div>
            <img src="http://img.d2c.cn/{{value.pic}}" width="100%" alt="">
        </div>
        <span>{{value.name}}</span>
    </div>
    {{/each}}
</script>
<script id="notic-template" type="text/html">
    {{each list as value}}
    <div>恭喜{{value.memberName}}{{value.awardName}}~</div>
    {{/each}}
</script>
<script>
    var allCardsHtml = "";
    var noticHtml = "";
    $.get('/collection/card/home.json', function (data) {
        //所有类型卡片
        console.log(data.allCardArray);
        allCardsHtml = template('allCards-template', {list: data.allCardArray});
        noticHtml = template('notic-template', {list: data.recentlyAwards});
        $('.card-shrink-new').html(allCardsHtml);
        $('#notice-bar > ul > li').html(noticHtml);
    });

    //滚动通知
    $('#notice-bar').jcMarquees({'marquee': 'y', 'speed': 40});

    //立即抽卡
    $('.card-draw').on('click', function () {
        var obj = $(this);
        if (obj.attr('data-tip') == '0') {
            $.flashTip({position: 'center', type: 'success', message: '操作过于频繁,请稍后重试!', time: 1500});
            return false;
        }
        obj.attr('data-tip', '0');
        var _index = parseInt(Math.random() * (12 - 0 + 1) + 0);
        $.post('/collection/card/select', {'selectIndex': _index, memberId:${MEMBERID}}, function (data) {
            if (data.result.status == -1) {
                $.flashTip({position: 'center', type: 'success', message: data.result.message, time: 1500});
                var h = $(document).height() - $(window).height();
                $('html, body').animate({scrollTop: h}, 400);
                obj.attr('data-tip', '1');
                return false;
            }

            var _contentNew = '<div class="popup-content">'
                + '<div class="card-drawn"><img class="bg-zuo" src="${imgHeadN}bg_zuo.png" width="78px" height="94px" alt=""><img class="bg-you" src="${imgHeadN}bg_you.png" width="137px" height="166px" alt="">'
                + '<div class="card-center"><div class="card-center-inside"><img src="" width="100%" height="300px" style="border-radius:11px;" alt=""></div><img src="${imgHeadN}bg_ka.png" width="100%" style="margin-top:-62px;" alt=""><span class="card-center-tip"></span></div>'
                + '<div class="card-drawn-btn-N"><span></span></div></div></div>';
            popupModal({
                content: _contentNew
            });

            if (awardCard == null || awardCard == "null") {
                $('.card-center-tip').text('好运就在下一次');
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

            $('.card-center-tip').text('帮好友抽中' + data.awardCard.name + '卡');
            $('.card-center-inside > img').attr('src', "//img.d2c.cn/" + data.awardCard.pic);
            $('.card-drawn-btn-N').off('click');
            $('.card-drawn-btn-N').on('click', function () {
                popupModalClose();
                setTimeout(function () {
                    location.reload();
                    obj.attr('data-tip', '1');
                }, 3500);
            });
        }, 'json');
    });

    /*
        //定义抽卡默认数组
        var lotteryArr = new Array(12);
        var html = template('lottery-template',{list:lotteryArr});
        $('.card-lottery ul').html(html);

        //翻转
        var clickState = 0;
        var resultArr = [];
        $('.card-item').on('click',function(){
            if(clickState === 1) {
                return false;
            };
            var _index = $(this).parent().attr('data-index');
            $.post('/collection/card/select',{'selectIndex':_index,memberId:${MEMBERID}},function(data){
			if(data.result.status == -1) {
				$.flashTip({position:'center',type:'error',message:data.result.message,time:1500});
				return false;
			}
			var otherResult = data.resultCards;
			for(var j=0;j<otherResult.length;j++){
				if(otherResult[j] === null) {
					resultArr.push({'pic':'/2018/10/25/033146bc0eb047835852285e765a77edd73322.png'});
				}else {
					resultArr.push({'pic':otherResult[j].pic})
				}
			}
			var resultHtml = template('lottery-template',{list:resultArr});
			$('.card-lottery ul').html(resultHtml);
			setTimeout(function(){
				$('.card-lottery ul').find('li').eq(_index).addClass('flipped');		
			},50);
			setTimeout(function(){
				$.flashTip({position:'bottom',type:'success',message:'帮好友抽到一张' + data.awardCard.name + '卡',time:1500});
				$('.card-lottery ul').find('li:not(:eq(' + _index + '))').addClass('flipped-grey');
			},1500);

		},'json');
		clickState = 1;  
	});
    */

</script>






<@m.page_footer />