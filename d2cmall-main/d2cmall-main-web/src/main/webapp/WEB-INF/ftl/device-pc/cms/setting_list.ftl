<#import "templates/public_pc.ftl" as m>
<@m.page_header title='邮件订阅'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray">
        <@m.page_menu menu_item="email"/>
        <div class="my-cont">
            <div style="font-size: 14px;">
                <#if email?exists>
                    为了您能准确收到D2C邮件，请确认您的邮箱是否正确：<input style="color:red" type="text" name="email" value="${(email)!}">
                <#else>
                    为了您能准确收到D2C邮件， <a href="/member/info" style="color:red">请填写您的邮箱地址 </a>
                </#if>
            </div>
            <#if buyList?size &gt; 0>
                <div class="setting">
                    <h1>购物提醒邮件</h1>
                    <#list buyList as list>
                        <span class="block">
						<input type="checkbox" name="value" value="${list.value}" data-id="${list.id}"
                               <#if list.value==1>checked="checked"</#if>/>
						${list.name}
					</span>
                    </#list>
                </div>
            </#if>
            <#if accountList?size &gt; 0>
                <div class="setting">
                    <h1>账户提醒邮件</h1>
                    <#list accountList as list>
                        <span class="block">
					<input type="checkbox" name="value" value="${list.value}" data-id="${list.id}"
                           <#if list.value==1>checked="checked"</#if>/>
					${list.name}
					</span>
                    </#list>
                </div>
            </#if>
            <#if productList?size &gt; 0>
                <div class="setting">
                    <h1>商品提醒邮件</h1>
                    <#list productList as list>
                        <span class="block">
					<input type="checkbox" name="value" value="${list.value}" data-id="${list.id}"
                           <#if list.value==1>checked="checked"</#if>/>
					${list.name}	
					</span>
                    </#list>
                </div>
            </#if>
            <!--<#if promotionList?size &gt; 0>
			<div>
				<h1 style="padding: 10px;font-size: 15px;border: solid #ccc;border-width: 1px 1px 0 1px;margin: 10px 0 0 0;">促销/活动邮件</h1>
				<table class="setting-table">
				<#list promotionList as list>
					<tr>	
					<td><input type="checkbox" name="value" value="${list.value}" data-id="${list.id}"<#if list.value==1>checked="checked"</#if>/>订阅</td>
					<td>${list.name}</td>
					<td>${list.name}</td>
					</tr>			
				</#list>
				</table>
			</div>
			</#if>-->
        </div>
        <div class="clear"></div>
    </div>
</div>
<script>
    $('input[name=email]').change(function () {
        var val = $(this).val();
        $.get('/member/updateEmail', {email: val}, function (data) {
            if (data.result.status == 1) {
                window.parent.$.flash_tips("邮箱变更成功。", 'success');
            } else {
                window.parent.$.flash_tips('邮箱变更失败。', 'error');
            }
        }, 'json');
    });
    $('input[name=value]').click(function () {
        var id = $(this).attr('data-id');
        var val = $(this).val();
        if (val == '0') {
            val = '1';
            $(this).val('1');
        } else if (val == '1') {
            val = '0';
            $(this).val('0');
        }
        $.get('/setting/updateValue/' + id, {value: val}, function (data) {
            if (data.result.status == 1) {
                var msg = "";
                if (val == 0) {
                    msg = "取消订阅";
                } else if (val == '1') {
                    msg = "订阅成功";
                }
                window.parent.$.flash_tips(msg, 'success');
            } else {
                window.parent.$.flash_tips('操作失败。', 'error');
            }
        }, 'json');
    });
</script>
<@m.page_footer />