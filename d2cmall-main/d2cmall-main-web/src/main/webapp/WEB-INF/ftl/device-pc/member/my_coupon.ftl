<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的优惠券' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="coupon"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1 style="margin-bottom:14px;">我的优惠券</h1>
        <div class="tab tab-user-menu clearfix">
            <ul>
                <li<#if RequestParameters.status=='UNCLAIMED' || RequestParameters.status=='CLAIMED'> class="on"</#if>>
                    <a href="/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED">未使用</a><i class="interval">|</i></li>
                <li<#if RequestParameters.status=='LOCKED' || RequestParameters.status=='USED'> class="on"</#if>><a
                            href="/coupon/myCoupon?status=LOCKED&status=USED">已使用</a><i class="interval">|</i></li>
                <li<#if RequestParameters.status=='INVALID'> class="on"</#if>><a href="/coupon/myCoupon?status=INVALID">已过期</a><i
                            class="interval">|</i></li>
                <li style='float:right'>
                    <button type="button" class="button" id="convert-button">兑换优惠券</button>
                </li>
            </ul>
        </div>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
        <div class="cards coupon clearfix">
            <#if pager.list?exists >
                <#list pager.list as coupon>
                    <div class="card-item"
                         style="position:relative;<#if RequestParameters.status=='INVALID'>background:#F2F2F2;</#if>">
                        <!--<div class="care-item-img">
	            	<#if coupon.status=="INVALID" || coupon.expired==1>
	            		<img src="//static.d2c.cn/img/home/150727/coupon_2.png" width="80%"/>
	            	</#if>
	            </div>
	            -->
                        <#if RequestParameters.status=='LOCKED' || RequestParameters.status=='USED'>
                            <a href="javascript:;" class="coupon-used"></a>
                        </#if>
                        <#if RequestParameters.status=='INVALID'>
                            <a href="javascript:;" class="coupon-timeout"></a>
                        </#if>
                        <div class="card-item-main">
                            <div class="coupon-card-amount"
                                 <#if RequestParameters.status=='INVALID'>style="background:#CCC;"</#if>>
                                <#if coupon.type=="DISCOUNT">
                                    <i>折扣 ${coupon.amount/10}</i>
                                <#else><i>&yen; ${coupon.amount}</i></#if>
                                <span style="color:#fff;">满${coupon.needAmount}使用</span>
                            </div>
                            <div class="coupon-card-time"
                                 <#if RequestParameters.status=='INVALID'>style="background:#CCC;"</#if>>
                                <p>有效期:${(coupon.enableDate?string("yy/MM/dd HH:mm"))!}
                                    -${(coupon.expireDate?string("yy/MM/dd HH:mm"))!}</p>
                                <#if coupon.consumesTime?exists && (coupon.status=="USED"||coupon.status=="LOCKED")>
                                    <p>使用时间：${coupon.consumesTime?string("yy/MM/dd HH:mm")}</p>
                                </#if>
                            </div>
                            <#if RequestParameters.status!='INVALID'>
                                <div class="coupon-bottom"></div>
                            <#else>
                                <div class="uncoupon-bottom"></div>
                            </#if>
                            <div class="coupon-card-desc">
                                <pre style="word-wrap: break-word;text-align:left;"><#if coupon.remark>${(coupon.remark)!}</#if></pre>
                            </div>
                            <div>
                                <#if coupon.redirectUrl?exists && coupon.redirectUrl!=''&&coupon.status=="CLAIMED" && coupon.expired==0>
                                    <a href="${coupon.redirectUrl}" class="button button-red pm"
                                       style="border-radius:20px;font-size:14px">点击使用</a></#if>
                                <#if coupon.status=="UNCLAIMED" && coupon.expired==0>
                                    <a href="javascript:" data-url="/coupon/activate?id=${coupon.id}"
                                       confirm="确定要激活该优惠券码？" success-tip="恭喜您！优惠券激活成功!" fail-tip="优惠券激活失败！"
                                       class="button button-red ajax-request active-coupon">激活</a>
                                </#if>
                                <#if (coupon.status=="UNCLAIMED" || coupon.status=="CLAIMED")&& coupon.expired==0 && coupon.transfer==true>
                                    <a href="javascript:" data-url="/coupon/transfer"
                                       data-param="{'code':'${coupon.code}','amount':'${coupon.amount}'}"
                                       data-type="post" modal-type="pop" template-id="trans-coupon-template"
                                       class="button button-purple ajax-request trans-coupon"
                                       style="border-radius:20px;font-size:14px;margin-left:10px">转让</a>
                                </#if>
                            </div>

                        </div>

                    </div>
                </#list>
            </#if>
        </div>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>
<script id="convert-coupon-template" type="text/html">
    <div class="card-item">
        <div class="card-item-main card-model" style="text-align:center;">
            <h2 class="coupon-card-type"
                style="font-weight:bold;padding-top:0px;padding-bottom:5px;letter-spacing:3px;margin-top:-16px;font-size:20px">
                兑换代金券</h2>
            <form method="post" class="validate-form" success-tip="false" confirm="确定要兑换代金券吗？"
                  action="/coupon/convertCoupon" style="padding:15px;" call-back="successcoupon">
                <p>
                    <input type="text" name="password" id="coupon-password" style="width:100%;margin-bottom:20px"
                           class="input input-l" placeholder="请输入代金券上的代码"/>
                    <input type="submit" name="convert" value="兑&nbsp;换" class="button button-red button-l"
                           style="font-size:16px;margin-left:10px"/>
                </p>
                <p class="tip-validate display-none" data-target="coupon-password"></p>
            </form>
        </div>
    </div>
</script>

<script id="trans-coupon-template" type="text/html">
    <div class="form">
        <form name="" confirm="确定要转让优惠券码？" success-tip="恭喜您，优惠券转让成功！" class="validate-form" action="{{url}}"
              method="post">
            <input type="hidden" name="code" value="{{code}}"/>
            <div class="form-item form-item-vertical">
                <label>把 优惠券转给</label>
                <input type="text" name="loginCode" id="login-code" value="" placeholder="请输入对方的登录账号"
                       class="input input-l">
                <div class="tip tip-validate" data-target="login-code"></div>
                <div id="user-info">
                </div>
            </div>
            <div class="form-button">
                <button type="submit" class="button">确定</button>
            </div>
        </form>
    </div>
</script>
<script id="user-info-template" type="text/html">
    <p style="margin:0;padding-top:10px;">{{nickname}} {{mobile}}</p><!--{{nickname}} {{mobile}} {{name}}-->
    <p style="margin:0;padding-top:10px;">核对以上信息准确无误后再进行转让！</p>
</script>

<script>
    $('#convert-button').click(function () {
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                userLogin();
            } else {
                var html = template('convert-coupon-template', {});
                $.popModal({'content': html, 'width': 400});
                return false;
            }
        });

    });
    $('#login-code').die().live('blur', function () {
        var login_code = $(this).val();
        $('#user-info').siblings('.tip-validate').hide();
        if (login_code) {
            $.ajax({
                url: "/member/findInfo",
                type: "get",
                data: {loginCode: login_code},
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        var html = template('user-info-template', data.result.datas.member);
                        $('#user-info').html(html);
                        $('button[type=submit]').removeAttr('disabled');
                    } else {
                        $('#user-info').html(data.results.message);
                        $('button[type=submit]').attr('disabled', true);
                    }
                }
            });
        } else {
            $('#user-info').html('');
        }
        return false;
    });
    var successcoupon = function () {
        var str = '<p style="font-size:32px;line-height:45px;color:#fd555d;font-weight:900;margin-bottom:20px;"><span class="model-success"></span>兑换成功</p><a class="button" href="//www.d2cmall.com" style="height:40px;lin-height:40px;margin-bottom:20px">立即使用</a>';
        $('.card-model').html(str);
        setTimeout(function () {
            location.reload()
        }, 3000)
    }
</script>
<@m.page_footer />