<#import "templates/public_pc.ftl" as m>
<@m.page_header title='秒杀活动' keywords="秒杀,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<@m.top_nav />
<div class="main-body">
    <div class="main-wrap w-1200">
        <form name="seckill-form" id="seckill-form" action="/crowd/order/confirm" method="post">
            <input name="crowdItemId" type="hidden" value="8"/>
            <input name="item" type="hidden" value="SZ23307000211901"/>
            <input name="num" type="hidden" size="1" value="1"/>
        </form>
        <div>
            <p>秒杀前，请先登录你的会员账号，若没有账号请先注册</p>
            <p>每个ID限秒杀一台，若用户采取作弊方式秒杀，D2C有权不发货</p>
            <p>秒杀商品不支持退款和退换货</p>
            <p>秒杀结果按最终付款时间为准</p>
            <p></p>
        </div>
        <button class="button-buy" id="seckill-button" type="submit">加载中...</button>
        <span class="count-down" data-function="start" data-startTime="2014/03/06 13:48:00"
              data-endTime="2014/03/06 14:15:00" data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
    </div>
</div>
<script>
    /*进入页面检测时间*/
    var obj = $('.count-down');
    var start_time = (new Date(obj.attr("data-startTime"))).getTime(),
        end_time = (new Date(obj.attr("data-endTime"))).getTime(),
        now_time = (new Date(obj.attr("data-serverTime"))).getTime();

    if (start_time > now_time) {
        obj.attr('data-function', 'start');
        $('#seckill-button').removeClass('disabled').removeAttr('disabled').text('开抢提醒');
    } else {
        obj.attr('data-function', 'end');
        $('#seckill-button').removeClass('disabled').removeAttr('disabled').text('马上抢');
    }

    //开始秒杀倒计时，到点时
    function start() {
        $('#seckill-button').removeClass('disabled').removeAttr('disabled').text('马上抢');
        $('.count-down').attr('data-function', 'end');
        $('.count-down').attr('data-type', 'toend');
        $('.count-down').attr('data-serverTime', $('.count-down').attr('data-startTime'));
        $('.count-down').countdown();
    }

    //秒杀结束后
    function end() {
        $('#seckill-button').addClass('disabled').attr('disabled', 'true').text('秒杀结束');
        $('.count-down').remove();
    }

    $('#seckill-button').click(function () {
        $.getJSON('/member/islogin', function (data) {
            if (data.login == false) {
                $('body').data('function', '$("#seckill-button").trigger("click")');
                user_login();
            } else {
                $.post('/crowds/crowd/' + $('input[name=crowdItemId]').val(), function (result) {
                    var data = result.crowdItem;
                    if (data.availableStore >= 1) {
                        $('#seckill-form').submit();
                    } else {
                        jAlert('已经被别人秒杀走了哦')
                    }
                }, 'json');
            }
        });
        return false;
    });
</script>
<@m.page_footer />