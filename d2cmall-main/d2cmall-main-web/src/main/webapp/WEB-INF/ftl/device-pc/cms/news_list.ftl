<#import "templates/public_pc.ftl" as m>
<@m.page_header title='${new.title} - 公告与动态' />
<@m.top_nav />
<div class="main-body bg-user-center">
    <div class="layout-response bg-white shadow-10 clearfix">
        <div class="news-left">
            <#if pager.list?exists>
                <#list pager.list as new>
                    <li>
                        ▪ <a href="/news/${new.id}">${new.title}</a>
                    </li>
                </#list>
            </#if>
            <div class="pages">
                <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
        </div>
        <div class="news-content">
            <h1>${new.title}</h1>
            <div class="content">
                ${new.content}
            </div>
        </div>

    </div>
</div>
<@m.page_footer />