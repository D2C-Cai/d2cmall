<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的推荐会员' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="partner_bind_member"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 我的推荐会员</h1>
        <div class="list clearfix">
            <div class="pages float-right" style="margin:0">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
            <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey">
                <thead>
                <tr>
                    <th width="10%">头像</th>
                    <th width="10%">昵称</th>
                    <th width="10%">时间</th>
                </tr>
                </thead>
                <tbody>
                <#if pager?exists&& pager.list?exists && pager.totalCount &gt; 0 >
                    <#list pager.list as memberInfo>
                        <tr class="title">
                            <td>
                                <div class="bind-headpic">
                                    <#if memberInfo.headPic?exists>
                                        <img src="${picture_base}/${memberInfo.headPic}!/fwfh/300x300" alt=""></img>
                                    <#else>
                                        <img src="//static.d2c.cn/common/nc/css/img/defult_headpic.jpg!/fwfh/300x300"
                                             alt=""></img>
                                    </#if>
                                </div>
                            </td>
                            <td class="text-center nickname" style="line-height:250%;">${(memberInfo.displayName)!}</td>
                            <td class="text-center"
                                style="line-height:250%;">${(memberInfo.recommendDate?string("yyyy/MM/dd HH:mm:ss")!)!}</td>
                        </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('.nickname').each(function () {
        var nickname = $(this).text();
        if (!isNaN(nickname)) {
            if (/^1\d{10,12}$/g.test(nickname)) {
                var num = nickname.substr(0, 7) + '****';
                $(this).text(num);
            }
        }
    })
</script>
<@m.page_footer js='modules/page.user' />    