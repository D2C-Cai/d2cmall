<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='会员中心' cart=true css='iconfont|college' service='false' hastopfix='false'/>
<@m.app_download_bar />
<@m.page_nav_bar channel='mine' />
<div class="section">
    <#if m.LOGINMEMBER.id!=null>
        <div class="user-banner">
            <a href="/member/setting" class="setting">设置</a>
            <#if browser!='wechat'><a href="javascrtipt:" data-url="/member/logout" class="logout">退出</a></#if>
            <#if !m.LOGINMEMBER.d2c>
                <p><a href="/member/bind" style="color:#fd555d">请绑定D2C账号<a></p>
            </#if>
            <#if m.LOGINMEMBER.headPic>
                <img src="<#if m.LOGINMEMBER.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${m.LOGINMEMBER.headPic}"
                     class="head-pic"/>
            <#elseif m.LOGINMEMBER.thirdHeadPic>
                <img src="${m.LOGINMEMBER.thirdHeadPic}" class="head-pic"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png" class="head-pic"></img>
            </#if>
            <p><#if m.LOGINMEMBER.nickname>${m.LOGINMEMBER.nickname}<#else><a href="/member/info">无名氏(取个名字吧)</a></#if>
            </p>
            <p>欢迎进入会员中心</p>
        </div>
    </#if>
    <#if m.LOGINMEMBER.designer!=null>
        <div class="form">
            <a href="/seller/orderitem" class="form-item">
                <i class="space"></i>销售订单<i class="icon icon-arrow-right"></i>
            </a>
        </div>
    </#if>
    <#if m.LOGINMEMBER.partnerId !=null>
        <div class="form">
            <a href="javascript:judgeEnv()" class="form-item clearfix">
                <i class="space"></i>买手中心<i class="icon icon-arrow-right"></i>
            </a>
            <a href="/partner/gift" class="form-item clearfix">
                <i class="space"></i>领取礼包<i class="icon icon-arrow-right"></i>
            </a>
        </div>
    </#if>
    <div class="form clearfix">
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/member/order"</#if> class="form-item"><i
                    class="space"></i>所有订单<i class="icon icon-arrow-right"></i></a>
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/member/order?orderStatus=WaitingForPay"</#if>
           class="form-item item-mini-hori" style="width:20%;"><i
                    class="iconfont icon-daifukuan"></i>待付款<#if WaitingForPay gt 0><em>${WaitingForPay}</em></#if></a>
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/member/order?orderStatus=WaitingForDelivery"</#if>
           class="form-item item-mini-hori" style="width:20%;"><i
                    class="iconfont icon-daifahuo"></i>待发货<#if WaitingForDelivery gt 0>
            <em>${WaitingForDelivery}</em></#if></a>
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/member/order?orderStatus=Delivered"</#if>
           class="form-item item-mini-hori" style="width:20%;"><i
                    class="iconfont icon-daishouhuo"></i>待收货<#if Delivered gt 0><em>${Delivered}</em></#if></a>
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/member/order?orderStatus=Success"</#if>
           class="form-item item-mini-hori" style="width:20%;"><i
                    class="iconfont icon-yishouhuo"></i>已收货<#if Success gt 0><em>${Success}</em></#if></a>
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/member/refund/list"</#if>
           class="form-item item-mini-hori" style="width:20%;border-left:1px solid #EEE;margin-left:-1px;"><i
                    class="iconfont icon-tuikuantuihuo"></i>售后</a>
        <a href="/o2oSubscribe/my/list" class="form-item clearfix">
            <i class="space"></i>我的试穿预约<i class="icon icon-arrow-right"></i>
        </a>
    </div>
    <div class="form clearfix">
        <a href="/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED" class="form-item">
            <i class="space"></i>我的优惠券<i class="icon icon-arrow-right"></i>
        </a>
        <a <#if !m.LOGINMEMBER.d2c>href="/member/bind" <#else>href="/auction/member/mymargin"</#if> class="form-item">
            <i class="space"></i>我的拍卖<i class="icon icon-arrow-right"></i>
        </a>
    </div>
    <div class="form">
        <a href="/member/interest/collection/list" class="form-item">
            <i class="space"></i>我收藏的商品<i class="icon icon-arrow-right"></i>
        </a>
        <a href="/member/interest/attention/list" class="form-item">
            <i class="space"></i>我关注的品牌<i class="icon icon-arrow-right"></i>
        </a>
    </div>
    <div class="form">
        <a href="/brandapply" class="form-item">
            <i class="space"></i>品牌入驻申请<i class="icon icon-arrow-right"></i>
        </a>
    </div>
</div>
<style>
    .qr-box {
        width: 15rem;
        background: #FFF;
        text-align: center;
    }

    .box-title {
        padding: 10px;
        color: #262626;
        font-weight: bold;
        line-height: 1.5;
    }

    .qr-close {
        width: 1.875em;
        height: 1.875em;
        position: absolute;
        bottom: -40px;
        left: 50%;
        transform: translateX(-50%);
    }

</style>
<script>

    //退出登录
    $('.logout').click(function () {
        var url = $(this).attr('data-url');
        jConfirm('确定要退出登录吗？', function (r) {
            if (r) {
                location.href = url;
            }
        });
        return false;
    });

    //判断环境
    function judgeEnv() {
        if (isWeChat) {
            var html = '<div class="popup-content">\
					<div class="qr-box">\
						<p class="box-title">扫描二维码查看哦~</p>\
						<div style="width:9.375em;margin:0 auto;"><img src="//static.d2c.cn//img/topic/180607/cp/images/pic_wechatcode02@3x.png" width="100%"></div>\
						<div class="qr-close"><img src="//static.d2c.cn//img/topic/180607/cp/images/icon_closed@3x.png" width="100%"></div>\
					</div>\
				</div>'
            popupModal({content: html});
            $('#popup-modal-outer').css('background', 'rgba(0,0,0,.7)');
            $('.qr-close').on(click_type, function () {
                popupModalClose()
            });
        } else {
            jConfirm('请前往APP查看哦~', function (r) {
                if (r) {
                    if (iOS) {
                        location.href = "https://itunes.apple.com/us/app/d2c-全球好设计/id980211165?l=zh&ls=1&mt=8";
                    } else if (isAndroid) {
                        location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.d2cmall.buyer";
                    }
                }
            });
        }
        return false;
    }

    /*
    template.helper('formatPrice', function(price, type) {
        if(price){
            var arrayPrice = price.toString().split(".");
            if(type == 'integer') {
                return arrayPrice[0]?arrayPrice[0]:"0";
            }else if (type =='decimal') {
                return arrayPrice[0]+(arrayPrice[1]?arrayPrice[1].length == 1?"."+arrayPrice[1]+"0":"."+arrayPrice[1]:".00");
            }
        }else{
            if(type == 'integer') {
                return "0";
            }else if (type =='decimal') {
                return ".00";
            }
        }
    });
    */
</script>
<@m.page_footer menu=true />