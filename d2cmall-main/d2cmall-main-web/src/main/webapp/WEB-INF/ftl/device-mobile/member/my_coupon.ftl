<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的优惠券" title='我的优惠券' service='false' hastopfix='false'/>
<div class="section">
    <div class="tab-holder">
        <div class="tab tab-suspend">
            <a href="/coupon/myCoupon?status=UNCLAIMED&status=CLAIMED"<#if RequestParameters.status=='UNCLAIMED' || RequestParameters.status=='CLAIMED'> class="on"</#if>>未使用</a>
            <a href="/coupon/myCoupon?status=LOCKED&status=USED"<#if RequestParameters.status=='LOCKED' || RequestParameters.status=='USED'> class="on"</#if>>已使用</a>
            <a href="/coupon/myCoupon?status=INVALID"<#if RequestParameters.status=='INVALID'> class="on"</#if>>已过期</a>
        </div>
    </div>

    <div style="font-size:0.8em;padding:2em 0.6em 1em 1em;text-align:center;">
        <button type="button" class="button button-l button-white" onclick="$('#convert-coupon').toggle();">
            如果您有优惠券密码，点此兑换
        </button>
    </div>

    <div class="form display-none" id="convert-coupon" style="margin-top:-0.6em;">
        <form method="post" action="/coupon/convertCoupon" class="validate-form form-item">
            <input type="text" name="password" class="input validate" title="优惠券密码" style="width:70%;"
                   placeholder="请输入优惠券的密码"/>
            <button type="submit" name="convert" class="button button-red float-right">兑换</button>
        </form>
    </div>

    <#if pager.list?exists >
        <#list pager.list as coupon>
            <div class="coupon-item">
                <div class="coupon-main clearfix">
                    <div class="coupon-left">
                        <#if coupon.type=="DISCOUNT">
                            <p>折扣 ${coupon.amount/10}</strong></p>
                        <#else><p>&yen;<strong>${coupon.amount}</strong></p></#if>
                        <p>满${coupon.needAmount}元使用</p>
                    </div>
                    <div class="coupon-right">
                        <p class="coupon-title">${coupon.name} <#if (RequestParameters.status=='UNCLAIMED' || RequestParameters.status=='CLAIMED') && coupon.transfer>
                                <span class="transfer" data-id="${coupon.code}">转赠</span></#if></p>
                        <p>有效期：${(coupon.enableDate?string("yy/MM/dd"))!}
                            - ${(coupon.expireDate?string("yy/MM/dd"))!}</p>
                    </div>
                </div>
                <div class="coupon-other">
                    <#if coupon.consumesTime?exists>
                        <div>消费时间 ${coupon.consumesTime?string("yy/MM/dd HH:mm")}</div></#if>
                    <div>${coupon.remark}</div>
                </div>
                <#if !coupon.transfer>
                    <div class="coupon-tip <#if coupon.status=='INVALID'>is-expired<#elseif (coupon.status=='LOCKED' || coupon.status=='USED')>is-used<#elseif (coupon.status=="UNCLAIMED")>is-unclaimed</#if>">
                        <#if coupon.redirectUrl?if_exists && coupon.redirectUrl!='' && coupon.status=="CLAIMED"><a
                            href="${coupon.redirectUrl}" class="button button-blue button-l"
                            style="padding:0 1.1em;margin-top:10px;">使用</a></#if>
                    </div>
                </#if>
                <#if (coupon.status=="UNCLAIMED")&& coupon.expired==0>
                    <div class="coupon-button">
                        <#if coupon.status=="UNCLAIMED">
                            <a href="javascript:" data-url="/coupon/activate?id=${coupon.id}" confirm="确定要激活该优惠券码？"
                               success-tip="恭喜您！优惠券激活成功!" fail-tip="优惠券激活失败！"
                               class="button button-red ajax-request active-coupon">激活</a>
                        </#if>
                        <#if coupon.transfer==true>
                            <a href="javascript:" data-url="/coupon/transfer"
                               data-param="{'code':'${coupon.code}','amount':'${coupon.amount}'}" data-type="post"
                               template-id="trans-coupon-template"
                               class="button button-purple ajax-request trans-coupon">转让</a>
                        </#if>
                    </div>
                </#if>
            </div>
        </#list>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </#if>
</div>
<script id="trans-coupon-template" type="text/html">
    <header>
        <div class="header">
            <div class="header-back"><a href="javascript:closePopModal()" class="icon icon-cross"></a></div>
            <div class="header-title">转让优惠券</div>
        </div>
    </header>
    <div class="section">
        <div class="form">
            <form name="" confirm="确定要转让优惠券码？" success-tip="恭喜您，优惠券转让成功！" class="validate-form" action="{{url}}"
                  method="post">
                <input type="hidden" name="code" value="{{code}}"/>
                <p class="form-title">把 {{amount}}元 优惠券转给</p>
                <div class="form-item">
                    <input type="text" name="loginCode" id="coupon-code" value="" placeholder="请输入对方的登录账号"
                           class="input validate">
                </div>
                <div id="user-info">
                </div>
                <div class="form-button">
                    <button type="submit" class="button button-l button-red">确定</button>
                </div>
            </form>
        </div>
    </div>
    <script>
        $('#coupon-code').on('blur', function () {
            var login_code = $(this).val();
            if (login_code) {
                $.ajax({
                    url: "/member/findInfo",
                    type: "get",
                    data: {loginCode: login_code},
                    dataType: 'json',
                    success: function (data) {
                        if (data.result.status == 1) {
                            var html = template('user-info-template', data.result.data.member);
                            $('#user-info').html(html);
                            $('button[type=submit]').removeAttr('disabled');
                        } else {
                            $('#user-info').html(data.result.message);
                            $('button[type=submit]').attr('disabled', true);
                        }
                    }
                });
            } else {
                $('#user-info').html('');
            }
            return false;
        });
</script>
    </script>
    <
    script
    id = "user-info-template"
    type = "text/html" >
        < div
    style = "font-size:0.7em;padding:1em;line-height:150%;" >
        < p > {
    {
        nickname
    }
    }
    {
        {
            mobile
        }
    }
    <
    /p>
    < p > 核对以上信息准确无误后再进行转让！<
    /p>
    < /div>
</script>
<script>
    $('.coupon-main').click(function () {
        $(this).siblings('.coupon-other').toggle();
    });

    $('.transfer').on('click', function (e) {
        e.stopPropagation();
        var id = $(this).attr('data-id')
        location.href = '/coupon/transfer/' + id + '?fromId=' + _memberId
    })
</script>
<@m.page_footer />