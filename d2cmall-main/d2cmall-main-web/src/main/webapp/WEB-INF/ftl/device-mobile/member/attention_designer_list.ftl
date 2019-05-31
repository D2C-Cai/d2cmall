<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我关注的品牌" title='我关注的品牌' service='false' hastopfix='false'/>
<div class="section lazyload" style="margin-top:0.4em;">
    <#if pager.totalCount &gt; 0 >
        <div class="list clearfix">
            <#list pager.list as memberAttention>
                <div class="item item-flex item-gap text-center" data-id="${memberAttention.designerId}"
                     style="padding-bottom:1em;">
                    <a href="/showroom/designer/${memberAttention.designerId}"
                       style="display:block;padding-bottom:0.6em;width:100%;text-align:center;">
                        <span class="img"
                              style="padding:1em;width:100%;display:table-cell;height:10.5em;box-sizing:border-box; vertical-align:middle;"><img
                                    src="${static_base}/m/img/blank.gif"
                                    data-image="${picture_base}${(memberAttention.designerPic)!}!120" alt=""
                                    style="vertical-align:middle;"/></span>
                        <span class="title"
                              style="border-top:1px dotted #CCC;height:1em;">${memberAttention.designerName}</span>
                    </a>
                    <button type="button" data-url="/member/interest/attention/delete/${memberAttention.designerId}"
                            confirm="确定取消关注吗？" call-back="removeItem(${memberAttention.designerId})"
                            class="button button-white ajax-request" style="width:50%;padding:0;">取消关注
                    </button>
                </div>
            </#list>
            <div class="pages">
                <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
            </div>
        </div>
    <#else>
        <div class="no-result">你还没关注过品牌</div>
    </#if>
</div>
<script>
    var removeItem = function (id) {
        $('.item[data-id=' + id + ']').addClass('animated zoomOutUp');
        setTimeout(function () {
            $('.item[data-id=' + id + ']').remove();
        }, 500);
    }
</script>
<@m.page_footer />