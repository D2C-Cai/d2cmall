<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='' url='' title='好友送了你一张大额优惠券，快来领取把~' description="好友送你一张优惠券，点击领取吧" service='false' css="flashpromotion" hastopfix='false'/>
<#assign LOGINMEMBER=loginMember()/>
<style>
    body {
        background: #fff;
    }

    .share-button, .lq-button, .use-button, .diabale-button {
        background: #fd555d;
        color: #fff;
        width: 70%;
        border: none;
        margin: 10px auto;
    }

    .slide-imgs {
        width: 90%;
        overflow-y: hidden;
        overflow-x: auto;
        -webkit-overflow-scrolling: touch;
        white-space: nowrap;
        margin: 0 auto;
    }

    .product-li {
        background: #fff;
        padding-top: 10px;
    }

    .product-li .li-tips {
        height: 16px;
        line-height: 16px;
        color: #232323;
        font-size: 10px;
        text-align: center;
    }

    .flash-element {
        display: inline-block;
        width: 28%;
        position: relative;
    }

    .success-tip {
        text-align: center;
        display: none;
        margin-top: 15px;
    }

    .icon-scww {
        width: 40px;
        height: 40px;
        background: url(http://static.d2c.cn/img/other/icon_chenggong.png) no-repeat;
        background-size: cover;
        display: inline-block;
        vertical-align: middle;
    }

    .scwww-p {
        font-size: 20px;
        font-weight: bold;
        vertical-align: middle;
        margin-left: 8px;
    }

    .mask {
        position: fixed;
        left: 0px;
        top: 0px;
        right: 0px;
        bottom: 0px;
        background: rgba(0, 0, 0, .5);
        z-index: 19;
    }

    .bind-form {
        padding: 15px 20px;
        background: #fff;
        position: absolute;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;
        width: 275px;
        height: 270px;
        margin: auto;
        text-align: center;
    }

    .bind-form .icon-close {
        position: absolute;
        right: 20px;
        width: 20px;
        height: 20px;
    }

    .icon-close {
        background-image: url(http://static.d2c.cn/other/close.png);
        background-size: contain;
    }

    .bind-form .title {
        font-size: 14px;
        text-align: center;
        font-weight: bold;
        line-height: 22px;
        margin-bottom: 10px;
    }

    .bind-form .bind-input {
        margin-bottom: 25px;
        height: 35px;
        line-height: 35px;
        position: relative;
    }

    .bind-form .bind-input > label {
        display: inline-block;
        width: 65px;
        font-size: 12px;
        color: #757575;
    }

    .bind-form .bind-input > input {
        width: 196px;
        border: 1px solid #c6c6c7;
        height: 32px;
        line-heiht: 32px;
        padding-left: 10px;
        font-size: 12px;
    }

    .bind-form .bind-button {
        width: 225px;
        height: 40px;
        background: #000;
        color: #fff;
        text-align: center;
        font-size: 12px;
        line-height: 40px;
        border: none
    }

    .bind-form .bind-input .send-code {
        position: absolute;
        background: #232323;
        color: #fff;
        line-height: 32px;
        height: 32px;
        text-align: center;
        right: 2px;
        top: 4px;
        font-size: 12px;
        width: 75px;
        border: none
    }

    .share-mask img {
        position: absolute;
        top: 20px;
        right: 20px;
        width: 30%;
    }
</style>
<div>
    <div class="share-mask mask" style="display:none">
        <img src="http://static.d2c.cn/other/invite_newuser_xcx.jpeg">
    </div>


    <div class="success-tip">
        <i class="icon-scww"></i><span class="scwww-p">领取成功</span>
        <p style="font-size:12px;margin-top:8px;">优惠券已放入D2C账户${LOGINMEMBER.loginCode}</p>
    </div>
    <div class="coupon-item" style="margin-bottom:0;border:1px solid #fd555d;">
        <div class="coupon-main clearfix">
            <div class="coupon-left">
                <#if result.datas.coupon.type=="DISCOUNT">
                    <p>折扣 ${result.datas.coupon.amount/10}</strong></p>
                <#else><p>&yen;<strong>${result.datas.coupon.amount}</strong></p></#if>
                <p>满${result.datas.coupon.needAmount}元使用</p>
            </div>
            <div class="coupon-right">
                <p>${result.datas.coupon.name}</p>
                <p>有效期：${(result.datas.coupon.enableDate?string("yy/MM/dd"))!}
                    - ${(result.datas.coupon.expireDate?string("yy/MM/dd"))!}</p>
            </div>
        </div>
    </div>
    <#if result.datas.products?exists >
        <div class="product-li">
            <p class="li-tips">*可购买以下商品*</p>
            <div class="flash-list slide-imgs">
                <#list result.datas.products as product>
                    <#if product_index lt 6>
                        <div class="flash-element">
                            <a href="/product/${product.id}" style="position:relative;display:block;">
                                <img data-src="" src="${picture_base}${product.productImageCover}!300" width="100%">
                            </a>
                        </div>
                    </#if>
                </#list>
            </div>
        </div>

    </#if>
    <#if result.datas.from.id == LOGINMEMBER.id>
        <div style="text-align:center;">
            <button class="share-button button" onclick=" $('.share-mask').show();">立即分享</button>
        </div>
    <#elseif result.datas.coupon.memberId== result.datas.from.id>
        <div style="text-align:center;">
            <button class="lq-button button">我要领取</button>
        </div>
        <div style="text-align:center;">
            <button class="use-button button" style="display:none" onclick="location.href='http://m.d2cmall.com'">去使用
            </button>
        </div>
    <#else>
        <div style="text-align:center;">
            <button class="diabale-button button" style="background-color:#999">优惠券已领完</button>
        </div>
    </#if>

    <div class="product-li">
        <p class="li-tips">*必抢尖货*</p>
        <div id="flash-c"></div>
    </div>
</div>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="limit-product-item flex">
        <a class="p-img" href="/product/{{value.id}}">{{if value.store<=0}}<i class="noshop-mask"><span></span></i>{{/if}}<img
                    src="${picture_base}{{value.productImageCover}}!/both/600x900" style="width:100%;"></a>
        <div class="lproduct-info">
            <p><a href="/product/{{value.id}}">{{value.name}}</a></p>
            <div class="text-right">
                <p class="lproduct-price"><span class="lightgrey">原价&nbsp;</span><span class="lightgrey"
                                                                                       style="text-decoration:line-through;padding-right:16px;">&yen;{{value.minPrice}}</span>限时价&nbsp;&nbsp;&yen;<span
                            style="font-size:1.5rem;font-weight: bold;">{{value.flashPrice}}</span>
                </p>
                <div class="lproduct-progress flex" style="justify-content:flex-end;">
                    <a class="flash-button buy" href="/product/{{value.id}}">
                        {{if value.store<=0}}去看看{{else}}马上买{{/if}}
                    </a>
                </div>
            </div>
        </div>
    </div>
    </a>
    {{/each}}
</script>
<script>
    $.get('/flashpromotion/product/session.json', function (data) {
        var id = data.currentId;
        $.get('/flashpromotion/products/list.json?id=' + id + '&pageSize=40', function (res) {
            var datas = res.pager;
            var html = template('list-template', datas);
            $('#flash-c').append(html)
        });
    });

    $('.lq-button').on('click', function () {
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                userLogin();
            } else if (data.result.datas.isBind == false) {
                bindD2c();
            } else {
                $.ajax({
                    'url': '/coupon/transfer',
                    'type': 'post',
                    'data': {code: '${result.datas.coupon.code}', fromId:${result.datas.from.id}},
                    'dataType': 'json',
                    'success': function (data, status) {
                        if (data.result.status == 1) {
                            $.flashTip({position: 'center', type: 'error', message: '恭喜您，领取成功'});
                            $('.lq-button').hide();
                            $('.use-button').show();
                            $('.success-tip').show();
                        } else {
                            $.flashTip({position: 'center', type: 'error', message: data.result.message});
                        }
                    }
                });
            }
        });
    })

    function bindD2c() {
        var html = '<div id="bind-form" class="mask">\
	 <div class="bind-form">\
	  <form class="validate-form form d2c-register-form" action="/member/bind" method="post" call-back="activityBind" success-tip="加入D2C成功">\
	 <i class="icon icon-close"></i><p class="title">加入D2C</p>\
	  <input type="hidden" name="nationCode" value="86" class="mobile-code" />\
	 <div class="bind-input"><label>区号</label><input placeholder="" readonly="readonly" value="+86(中国大陆)"></div>\
	 <div class="bind-input"><label>手机号</label><input class="validate validate-account" placeholder="手机号" type="number" name="loginCode" id="login-code"></div>\
	 <div class="bind-input"><button class="send-code validate-send validate-button" data-type="MEMBERMOBILE" data-way="register" type="button">发送验证码</button><label>验证码</label><input placeholder="验证码"   name="code"></div>\
	 <button class="bind-button"> 绑定</button>\
	 </div></form></div>';
        $('body').append(html);
        $('.icon-close').on('click', function () {
            $('#bind-form').remove();
        })
    }

    function activityBind() {
        $('.lq-button').trigger('click')
    }

    $('.share-mask').on('click', function () {
        $(this).hide();
    })


</script>

<@m.page_footer menu=false />























