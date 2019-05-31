<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='买家秀评论审核'  keywords="设计师现货区,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场"/>
<div class="category-search" style="padding:.5em 0.8em;height: 2.6em;">
    <form name="" action="/membershare/comment/list" style="width:100%;margin-left:0;">
        <button class="icon-search" type="submit" style="width:1em;height:1em;top:.8em"></button>
        <input type="search" name="content" placeholder="输入评论内容关键字" class="input" value=""
               style="padding-left:2em;height: 2.5em;line-height: 1.6em;"/>
    </form>
</div>

</div>
<#if result.list?size &gt; 0>
    <#list result.datas.list as comment>

        <div class="form clearfix">
            <div class="form-item" style="font-size:14px;line-height:180%;">会员ID：${comment.memberId}
                <div class="switch" style="float:right;margin-top:-.2em">
                    <div class="btn_fath clearfix <#if comment.blockShareComment==0>off<#elseif  comment.blockShareComment==1>on</#if>"
                         onclick="toogle(this)">
                        <div class="move"
                             data-state="<#if comment.blockShareComment==0>off<#elseif  comment.blockShareComment==1>on</#if>"
                             data-id="${comment.id}"
                             data-url="/membershare/blockShareComment/${comment.memberId}?status="></div>
                        <div class="btnSwitch btn1"></div>
                        <div class="btnSwitch btn2"></div>
                    </div>
                </div>
                <div style="color:#8B0A50;font-size:12px;float:right;margin-right:.2rem">禁止评论</div>
            </div>
            <div class="form-item" style="font-size:14px;line-height:180%;">会员昵称：${comment.nickName}</div>
            <div class="form-item" style="color:#666;font-size:12px;line-height:180%;">${comment.createDate}
                <div class="switch" style="float:right;margin-top:-.4em">
                    <div class="btn_fath clearfix <#if comment.verified==false>off<#elseif  comment.verified==true>on</#if>"
                         onclick="toogle(this)">
                        <div class="move"
                             data-state="<#if comment.verified==false>off<#elseif comment.verified==true>on</#if>"
                             data-id="${comment.id}"
                             data-url="/membershare/comment/verify/${comment.id}?verified="></div>
                        <div class="btnSwitch btn1"></div>
                        <div class="btnSwitch btn2"></div>
                    </div>
                </div>
                <div style="color:#8B0A50;font-size:12px;float:right;margin-right:.2rem">审核</div>
            </div>
            <div class="form-item"
                 style="font-size:14px;line-height:150%;word-wrap: break-word;">${comment.content}</div>

        </div>
    </#list>
</#if>
<div class="pages comment-pages">
    <@m.simple_pager page=result.index totalpage=result.pageCount />
</div>
<script type="text/javascript">
    function toogle(th) {
        var ele = $(th).children(".move");
        var id = ele.attr("data-id");
        var url = ele.attr("data-url");
        if (ele.attr("data-state") == "on") {
            ele.animate({left: "0"}, 300, function () {
                $.post(url + '0', function (data) {
                    if (data.result.status == 1) {
                        ele.attr("data-state", "off");
                        $.flashTip({position: 'center', type: 'success', message: '下架成功'});
                    } else {
                        $.flashTip({position: 'center', type: 'success', message: data.result.message});
                    }
                }, 'json');
            });
            $(th).removeClass("on").addClass("off");
        } else if (ele.attr("data-state") == "off") {
            ele.animate({left: '20px'}, 300, function () {
                $.post(url + '1', function (data) {
                    if (data.result.status == 1) {
                        ele.attr("data-state", "on");
                        $.flashTip({position: 'center', type: 'success', message: '操作成功'});
                    } else {
                        $.flashTip({position: 'center', type: 'success', message: data.result.message});
                    }
                }, 'json');
            });
            $(th).removeClass("off").addClass("on");
        }
    }


</script>
<@m.page_footer />