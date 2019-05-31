<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="修改个人信息" title='修改个人信息' service='false' hastopfix='false'/>
<div class="section">
    <div class="tips tip-green"><i class="fa fa-info-circle"></i> 请确保个人信息的真实性，所有个人信息均严格保密。</div>
    <div class="form">
        <form name="form-info" class="validate-form" success-tip="个人信息保存成功！" redirect-url="/member/home"
              action="/member/info.json" method="post">
            <input type="hidden" name="id" value="${member.id}"/>
            <input type="hidden" name="memberInfo.id" value="${member.memberInfo.id}"/>
            <input type="hidden" name="memberInfoId" value="${member.memberInfoId}"/>
            <div class="form-item item-flex">
                <label>昵 &nbsp;&nbsp;&nbsp;&nbsp; 称</label>
                <input type="text" name="nickname" id="nickname" title="昵称" placeholder="取一个昵称" min-length="4"
                       value="${member.nickname}" class="input validate"/>
            </div>
            <div class="form-item item-flex">
                <label>头像</label>
                <#if member.memberInfo.headPic>
                    <img src="<#if member.memberInfo.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${member.memberInfo.headPic}"
                         class="head-pic" style="width:15%;border-radius:100%;"></img>
                <#elseif member.memberInfo.thirdHeadPic>
                    <img src="${member.memberInfo.thirdHeadPic}" class="head-pic"
                         style="width:15%;border-radius:100%;"></img>
                <#else>
                    <img src="//static.d2cmall.com/img/home/160627/images/headpic.png" class="head-pic"
                         style="width:15%;border-radius:300%;"></img>
                </#if>
            </div>
            <!--
            <div class="form-item item-flex">
                <label>手机号码</label>
                <input type="text" name="mobile" id="mobile" title="手机号码" title="手机号码" placeholder="输入常用的手机号" value="${member.mobile}" class="input validate" data-rule="mobile" readonly="readonly"/>
            </div>
            -->
            <div class="form-item item-flex">
                <label>电子邮箱</label>
                <input type="text" name="email" id="email" title="电子邮箱" placeholder="输入常用的电子邮箱" min-length="5"
                       maxlength="30" value="${member.email}" class="input validate"/>
            </div>
            <div class="form-item item-flex">
                <label>真实姓名</label>
                <input type="text" name="memberDetail.name" id="name" placeholder="输入您的真实姓名"
                       value="${member.memberDetail.name}" class="input"/>
            </div>
            <div class="form-item item-flex">
                <label>性 &nbsp;&nbsp;&nbsp;&nbsp; 别</label>
                <input type="radio" id="girl" name="sex" value="女"
                       class="checkbox"<#if member.sex=='女'> checked</#if> /><label for="girl">女 </label>
                <input type="radio" id="boy" name="sex" value="男" class="checkbox"<#if member.sex=='男'> checked</#if> /><label
                        for="boy">男</label>
            </div>
            <div class="form-item item-flex">
                <label>腾讯 QQ</label>
                <input type="text" name="memberDetail.qq" placeholder="输入您的QQ号" value="${member.memberDetail.qq}"
                       class="input"/>
            </div>
            <div class="form-item item-flex">
                <label>微 &nbsp;&nbsp;信&nbsp;&nbsp;号</label>
                <input type="text" name="memberDetail.weixin" placeholder="输入您的微信号"
                       value="${member.memberDetail.weixin}" class="input"/>
            </div>
            <div class="form-button">
                <button type="submit" class="button button-l button-red">确定</button>
            </div>
        </form>
    </div>
</div>
<@m.page_footer />