<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='领取礼包' css='' service="false" />
<#assign partnerId=m.LOGINMEMBER.partnerId />
<#if browser!='wechat' && !wechat >
    <header>
        <div class="header fixed">
            <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
            <div class="header-title">领取礼包</div>
        </div>
    </header>
</#if>
<style>
    .box-wrapper {
        width: 20rem;
        padding: 30px 0;
        box-sizing: border-box;
        background: #FFF;
        color: #000;
        font-size: 12px;
        text-align: center;
    }

    .box-wrapper .title {
        font-size: 16px;
        font-weight: bold;
        margin: 15px 0;
    }

    .box-wrapper .sub-title {
        line-height: 2;
    }

    .box-wrapper .box-input {
        width: 80%;
        height: 2rem;
        line-height: 2rem;
        padding-left: 15px;
        margin: 20px 0;
        color: #000;
        border: 1px solid rgba(0, 0, 0, .5);
        outline: 0;
    }

    .box-wrapper .box-button {
        width: 80%;
        height: 2rem;
        line-height: 2rem;
        background: #999;
        margin: 0 auto;
        border: 0;
    }


</style>
<script>
    var html = '<div class="popup-content">\
				<div class="box-wrapper">\
					<p class="title">恭喜您成功购买开店礼包！</p>\
					<p class="sub-title">请输入您的微信号，获取620元新买手礼包</p>\
					<p class="sub-title">完成最后一步买手认证，成功开启属于你的买手店</p>\
					<input type="text" placeholder="输入您的微信号" class="box-input">\
					<div class="box-button">确定</div>\
				</div>\
			</div>'
    popupModal({content: html});
    $('#popup-modal-outer').css('background', 'rgba(0,0,0,.5)');
    //按钮操作
    $('.box-button').on(click_type, function () {
        var inputVal = $('.box-input').val();
        if (!inputVal) {
            $.flashTip({position: 'center', type: 'error', message: '请输入您的微信号哦~', time: 1000});
        } else {
            /*待优化promise
            var def = $.Deferred();
            function ajaxRequest(url,data){
                return $.ajax({
                    url:url,
                    type: data!=null ? 'post' : 'get',
                    data: data,
                    dataType:'json',
                    success:function(data) {
                        def.resolve(data)
                    }
                })

            }
            ajaxRequest('/coupon/batch/157',{})
            .then(function(res1){
                if(res1.result.status === 1){
                    return ajaxRequest('/coupon/batch/158',{})
                }else{
                    console.log('抢光啦')
                    return def.reject();//停止请求
                }
            })
            .then(function(res2){
                console.log(res2)
                //return ajaxRequest('/partner/update',{id:
            ${partnerId},weixin:inputVal})
			})
			.then(function(res3){
				console.log(res3)
			})
			*/
            //$.ajaxSettings.async = false;
            $.getJSON('/coupon/batch/157', function (res) {
                if (res.result.status === 1) {
                    $.getJSON('/coupon/batch/158', function (res1) {
                        if (res1.result.status === 1) {
                            $.post('/partner/update', {id:${partnerId}, weixin: inputVal}, function (res2) {
                                if (res2.result.status === 1) {
                                    $.flashTip({
                                        position: 'center',
                                        type: 'success',
                                        message: '快去我的优惠券查看礼包吧~',
                                        time: 1000
                                    });
                                    setTimeout(function () {
                                        location.href = "/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED";
                                    }, 2000)
                                } else {
                                    $.flashTip({
                                        position: 'center',
                                        type: 'error',
                                        message: res2.result.message,
                                        time: 1000
                                    });
                                }
                            }, 'json');
                        } else {
                            $.flashTip({position: 'center', type: 'error', message: res.result.message, time: 1000});
                            setTimeout(function () {
                                history.back(1);
                            }, 2000)
                        }
                    })
                } else {
                    $.flashTip({position: 'center', type: 'error', message: res.result.message, time: 1000});
                    setTimeout(function () {
                        history.back(1);
                    }, 2000)
                }
            });
        }
    });

</script>

<@m.page_footer menu=false />