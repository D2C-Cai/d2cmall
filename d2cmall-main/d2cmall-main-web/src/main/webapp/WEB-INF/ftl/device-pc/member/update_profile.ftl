<#import "templates/public_pc.ftl" as m>
<@m.page_header title='完善个人信息' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-help lazyload">
    <div class="form">
        <form name="form-update-size" class="validate-form" action="/member/info" method="post" redirect-url="/"
              style="padding:15px;">
            <div class="tip tip-warning"><i class="fa fa-info-circle"></i> 网站在任何情况下都不会主动透露个人隐私。</div>
            <div class="float-left" style="width:40%;margin-right:5%;">
                <h2 class="form-info">完善基本信息</h2>
                <div class="form-item">
                    <label>手机</label>
                    <input type="text" name="memberInfo.mobile" id="mobile" value="${member.memberInfo.mobile}"
                           class="input" placeholder=""/>
                    <div class="tip tip-validate" data-target="mobile"></div>
                </div>
                <div class="form-item">
                    <label>邮箱</label>
                    <input type="text" name="memberInfo.email" id="email" value="${member.memberInfo.email}"
                           class="input" placeholder=""/>
                    <div class="tip tip-validate" data-target="email" data-rule="email"></div>
                </div>
                <div class="form-item">
                    <label>昵称</label>
                    <input type="text" name="memberInfo.nickname" id="nickname" value="${member.memberInfo.nickname}"
                           class="input" placeholder=""/>
                    <div class="tip tip-validate" data-target="nickname" byte-min="4" byte-max="30"></div>
                </div>
                <div class="form-item">
                    <label>真实姓名</label>
                    <input type="text" name="memberDetail.name" value="${member.memberDetail.name}" class="input"
                           placeholder=""/>
                </div>
                <div class="form-item">
                    <label>腾讯QQ</label>
                    <input type="text" name="memberDetail.qq" value="${member.memberDetail.qq}" class="input"
                           placeholder=""/>
                </div>
                <div class="form-item">
                    <label>微信</label>
                    <input type="text" name="memberDetail.weixin" value="${member.memberDetail.weixin}" class="input"
                           placeholder=""/>
                </div>
            </div>
            <div class="float-left" style="width:30%">
                <h2 class="form-info">补充尺码信息</h2>
                <div class="form-item checkbox">
                    <label>性别</label>
                    <label style="color:#ff60c9"><input type="radio" id="girl" data-type="0" name="memberInfo.sex"
                                                        value="女" class="checkbox sex"
                                                        style="width:2%;"<#if member.memberInfo.sex=='女'> checked</#if> /><label
                                for="girl"></label> 女 </label>
                    &nbsp; &nbsp;
                    <label style="color:#598afb"><input type="radio" id="boy" data-type="0" name="memberInfo.sex"
                                                        value="男" class="checkbox sex"
                                                        style="width:2%;"<#if member.memberInfo.sex=='男'> checked</#if> /><label
                                for="boy"></label> 男</label>
                </div>
                <div class="form-item">
                    <label>身高</label>
                    <input type="text" name="memberDetail.height" size="20" maxlength="3"
                           value="${member.memberDetail.height}" class="input noime"/> 厘米
                </div>
                <div class="form-item">
                    <label>体重</label>
                    <input type="text" name="memberDetail.weight" size="20" maxlength="3"
                           value="${member.memberDetail.weight}" class="input noime"/> 公斤
                </div>
                <div class="form-item">
                    <label>胸围</label>
                    <input type="text" name="memberDetail.chest" data-type="1" size="20" maxlength="3"
                           value="${member.memberDetail.chest}" class="input noime size"/> 厘米
                </div>
                <div class="form-item">
                    <label>腰围</label>
                    <input type="text" name="memberDetail.waistline" data-type="2" size="20" maxlength="3"
                           value="${member.memberDetail.waistline}" class="input noime size"/> 厘米
                </div>
                <div class="form-item">
                    <label>臀围</label>
                    <input type="text" name="memberDetail.hipline" data-type="3" size="20" maxlength="3"
                           value="${member.memberDetail.hipline}" class="input noime size"/> 厘米
                </div>
                <div class="form-item">
                    <label>脚长</label>
                    <input type="text" name="memberDetail.footLength" data-type="4" size="20" maxlength="3"
                           value="${member.memberDetail.footLength}" class="input noime size"/> 厘米
                </div>
            </div>
            <div class="float-right" style="margin:50px 40px 0 0;">
                <div>
                    <img src="${static_base}/c/images/size/account_size_sg.jpg"/>
                </div>
                <div id="size-1-1" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_xw.jpg"/>
                </div>
                <div id="size-1-2" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_yw.jpg"/>
                </div>
                <div id="size-1-3" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_tw.jpg"/>
                </div>
                <div id="size-1-4" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_jc.jpg"/>
                </div>
                <div id="size-0-1" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_sx.jpg"/>
                </div>
                <div id="size-0-2" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_yww.jpg"/>
                </div>
                <div id="size-0-3" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_tww.jpg"/>
                </div>
                <div id="size-0-4" class="display-none">
                    <img src="${static_base}/c/images/size/account_size_jcw.jpg"/>
                </div>
            </div>
            <!--
            <div class="clearfix">
                <h2 class="form-info">您希望在D2C上看到哪些设计师品牌？</h2>
                <div class="form-item form-item-vertical float-left" style="width:20%;">
                    <label>品牌1</label>
                    <input type="text" name="interestTitle" value="" class="input" />
                </div>
                <div class="form-item form-item-vertical float-left" style="width:20%;">
                    <label>品牌2</label>
                    <input type="text" name="interestTitle" value="" class="input" />
                </div>
                <div class="form-item form-item-vertical float-left" style="width:20%;">
                    <label>品牌3</label>
                    <input type="text" name="interestTitle" value="" class="input" />
                </div>
                <div class="form-item form-item-vertical float-left" style="width:20%;">
                    <label>品牌4</label>
                    <input type="text" name="interestTitle" value="" class="input" />
                </div>
            </div>
            -->

            <div class="text-center clearfix" style="padding-top:20px;">
                <input type="hidden" name="id" value="${member.id}"/>
                <input type="hidden" name="memberInfo.id" value="${member.memberInfo.id}"/>
                <input type="hidden" name="memberInfoId" value="${member.memberInfoId}"/>
                <button type="submit" class="button button-l button-red">保存设置</button> &nbsp;&nbsp;&nbsp;
                <a href="/" class="button button-l button-clear">以后再说</a>
            </div>
        </form>
    </div>
</div>
<script>
    var pubsage_sign = "";
    var pubsage_url = "";
    var url = window.location.href;
    var domain = document.domain;
    var mat = url.match(/adtiid=([^&#]*)/);
    var iid = mat ? mat[1] : "";
    if (iid == "" || typeof (iid) == "undefined") {
        var uid = function (cookiekey) {
            var strCookie = document.cookie;
            var arrCookie = strCookie.split(";");
            var value = "";
            for (var i = 0; i < arrCookie.length; i++) {
                var arr = arrCookie[i].split("=");
                if (cookiekey == arr[0].replace(/(^\s*)|(\s*$)/g, "")) {
                    value = arr[1];
                    break
                }
            }
            return value
        }("_ws_uid");
        if (uid == "" || typeof (uid) == "undefined") {
            pubsage_url = "tca.sagetrc.com"
        } else {
            var strAry = domain.split(".");
            var host = strAry[strAry.length - 2] + "." + strAry[strAry.length - 1];
            var pf = uid.split(".");
            if (pf[0] == "tqh") {
                pubsage_url = "tqh_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tgg") {
                pubsage_url = "tgg_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tsg") {
                pubsage_url = "tsg_" + pubsage_sign + "." + host
            }
            if (pf[0] == "tdsp") {
                pubsage_url = "tdsp_" + pubsage_sign + "." + host
            }
            if (pf[0] != "tsg" && pf[0] != "tgg" && pf[0] != "tqh" && pf[0] != "tdsp") {
                pubsage_url = "trc_" + pubsage_sign + "." + host
            }
        }
    } else {
        if (iid.match("^g") != null) {
            pubsage_url = "tgg.sagetrc.com"
        }
        if (iid.match("^q") != null) {
            pubsage_url = "tqh.sagetrc.com"
        }
        if (iid.match("^s") != null) {
            pubsage_url = "tsg.sagetrc.com"
        }
        if (iid.match("^d") != null) {
            pubsage_url = "tdsp.sagetrc.com"
        }
        if (iid.match("^g|^q|^s|^d") == null) {
            pubsage_url = "t1.sagetrc.com"
        }
    }
    var pubsage_conv_id = 27449;
    var pubsage_conv = pubsage_conv || [];
    (function () {
        try {
            var d = document;
            var at = d.createElement("script");
            at.type = "text/javascript";
            at.async = true;
            at.src = ("https:" == document.location.protocol ? "https:" : "http:") + "//" + pubsage_url + "/trc/atac/conv_x.js?id=" + pubsage_conv_id;
            var s = d.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(at, s)
        } catch (e) {
        }
    })();

    $('.noime').utilSetNumber();
    $('.size').live('focus', function () {
        var i = $('.sex:checked').attr('data-type'),
            ii = $(this).attr('data-type');
        $('#size-' + i + '-' + ii).show().siblings().hide();
    });

</script>
<@m.page_footer />