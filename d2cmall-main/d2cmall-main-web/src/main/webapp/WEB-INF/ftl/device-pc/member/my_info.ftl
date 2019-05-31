<#import "templates/public_pc.ftl" as m>
<@m.page_header title='修改个人信息  - 个人中心' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="info"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>修改个人信息</h1>
        <div class="form-info n_form" style="width:100%;">
            <form name="form-info" class="validate-form" success-tip="个人信息保存成功！" action="/member/info.json"
                  method="post">
                <input type="hidden" name="id" value="${member.id}"/>
                <input type="hidden" name="memberInfo.id" value="${member.memberInfo.id}"/>
                <input type="hidden" name="memberInfoId" value="${member.memberInfoId}"/>
                <div class="form-item">
                    <label><span></span>头像</label>
                    <#if member.memberInfo.headPic>
                        <img src="<#if member.memberInfo.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${member.memberInfo.headPic}"
                             class="head-pic"></img>
                    <#elseif member.memberInfo.thirdHeadPic>
                        <img src="${member.memberInfo.thirdHeadPic}" class="head-pic"></img>
                    <#else>
                        <img src="//static.d2cmall.com/img/home/160627/images/headpic.png" class="head-pic"></img>
                    </#if>
                </div>
                <div class="form-item">
                    <label><span>*</span>昵称</label>
                    <input type="text" name="nickname" id="nickname" value="${member.nickname}" class="input"/>

                    <span class="prompts">请输入4-20位字符，可使用中文、英文、数字及其组合</span>
                    <div class="tip tip-validate" data-target="nickname" data-rule="nickname" min-length="0"
                         byte-max="30" byte-min="4"></div>

                </div>
                <div class="form-item">
                    <label><span>*</span>真实姓名</label>
                    <input type="text" name="memberDetail.name" id="name" minlength="0" maxlength="20"
                           value="${member.memberDetail.name}" class="input"/>
                    <span class="prompts">请输入4-20位字符，可使用中文或英文</span>
                    <div class="tip tip-validate" data-target="name" data-rule="name"></div>
                </div>
                <div class="form-item checkbox">
                    <label><span>*</span>性别</label>
                    <label style="color:#ff60c9"><input type="radio" id="girl" name="sex" value="女"
                                                        style="width:2%;"<#if member.sex=='女'> checked</#if> /><label
                                for="girl"></label> 女 </label>
                    &nbsp; &nbsp;
                    <label style="color:#598afb"><input type="radio" id="boy" name="sex" value="男"
                                                        style="width:2%;"<#if member.sex=='男'> checked</#if> /><label
                                for="boy"></label> 男</label>
                </div>
                <!--
                <div class="form-item">
                    <label><span></span>手机号码</label>
                    <input type="text" name="memberInfo.mobile" id="mobile" value="${member.memberInfo.mobile}" class="input"  readonly="readonly"/>
                    <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
                </div>
                -->
                <div class="form-item">
                    <label><span></span>生日</label>
                    <span id="birthday" rel="${(member.birthday?string("yyyy/MM/dd"))!""}">
                    <select name="year" class="input" style="width:80px;"></select> 年
                    <select name="month" class="input" style="width:60px;"></select> 月
                    <select name="day" class="input" style="width:65px;"></select> 日
                    </span>
                </div>
                <div class="form-item">
                    <label><span></span>腾讯QQ</label>
                    <input type="text" name="memberDetail.qq" value="${member.memberDetail.qq}" class="input"/>
                </div>
                <div class="form-item">
                    <label><span></span>微信号</label>
                    <input type="text" name="memberDetail.weixin" value="${member.memberDetail.weixin}" class="input"/>

                </div>
                <div class="form-item">
                    <label><span>*</span>电子邮箱</label>
                    <input type="text" name="email" id="email" value="${member.email}" class="input"/>
                    <div class="tip tip-validate" data-target="email" data-rule="email" min-length="0"
                         max-length="30"></div>
                </div>
                <div class="form-button" style="margin-left:156px;">
                    <button type="submit" class="button">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    $('#area-selector').utilSetArea();
    $('#birthday').utilSimpleDate();
</script>
<@m.page_footer />