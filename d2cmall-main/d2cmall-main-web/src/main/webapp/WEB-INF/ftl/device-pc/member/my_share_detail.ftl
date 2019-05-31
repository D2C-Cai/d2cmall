<#import "templates/public_pc.ftl" as m>
<@m.page_header title='分享赚积分 - 个人中心' />
<@m.top_nav />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray clearfix">
        <@m.page_menu menu_item="task"/>
        <div class="my-cont">
            <h1>分享赚积分</h1>
        </div>
    </div>
</div>
<share data-title="${(shareTaskDef.title)!}" data-url="/shareTask/click/${shareTask.id}"
       data-pic="${picture_base}${shareTaskDef.smallPic}!wx.title"></share>
<script>

</script>
<@m.page_footer />
