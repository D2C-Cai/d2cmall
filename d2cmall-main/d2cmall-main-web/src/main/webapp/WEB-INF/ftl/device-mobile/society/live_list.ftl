<#if pager.list?size &gt; 0>
    <#list pager.list as livelist>
        <div class="mylive-item">
            <div class="mylive-area" style="padding-top:0;">
                <p class="text-right live-member"><#if livelist.status==1>${livelist.viewerCount}人正在观看<#else><span
                            style="float:left;color:#000">往期录播</span>${livelist.totalViewerCount}人观看过</#if></p>
                <a href="live/{livelist.id}" class="live-in">
                    <#if livelist.status==1><span class="live-logo"><i class="live-icon"></i>正在直播</span><#else><span
                            class="video-logo"></span></#if>

                    <#if livelist.coverPic><img src="${picture_base}/${livelist.coverPic}!/sq/300" alt=""
                                                style="width:100%;"><#elseif livelist.headPic><img
                        src="${picture_base}/${livelist.headPic}!/sq/300" alt="" style="width:100%;"></#if>
                </a>
                <p class="live-title">${livelist.title}</p>
            </div>
        </div>
    </#list>
</#if>