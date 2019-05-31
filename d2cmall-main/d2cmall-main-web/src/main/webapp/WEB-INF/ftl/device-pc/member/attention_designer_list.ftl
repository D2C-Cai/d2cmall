<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我关注的设计师品牌 - 个人中心'/>
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="interest_attention" />
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>我关注的设计师品牌</h1>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
        <#if pager.totalCount &gt; 0 >
            <div class="list clearfix">

                <#list pager.list as memberAttention>
                    <div class="list-item" id="fav-${memberAttention.designerId}">
                        <div class="img border-b-n" style="margin-bottom:-8px;width:9999px;display:table-cell"><p
                                    class="n-img"><a href="/showroom/designer/${memberAttention.designerId}"
                                                     target="_black"><img
                                            src="${picture_base}${(memberAttention.designerPic)!}!/fwfh/230x360"
                                            alt="${memberAttention.designerName}"/></a></p></div>
                        <div class="detail-content">
                            <p class="title">
                                <a href="/showroom/designer/${memberAttention.designerId}" target="_blank" class="arial"
                                   title="${memberAttention.designerName}">${memberAttention.designerName}</a>
                            </p>
                            <p class="button-form">
                                <a class="button pm" href="/showroom/designer/${memberAttention.designerId}"
                                   style="margin-right:15px;">立即前往</a>
                                <a href="javascript:"
                                   data-url="/member/interest/attention/delete/${memberAttention.designerId}"
                                   confirm="确定要取消喜关注吗？取消后无法恢复" data-type="post"
                                   call-function="removeObj(${memberAttention.designerId})"
                                   class="button button-black pm ajax-request">取消关注</a>
                            </p>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>
<script type="text/javascript">
    var removeObj = function (id) {
        $('#fav-' + id).remove();
    }
    var changehe = function () {
        var w = $('.img').eq(0).width();
        var h = (parseInt(w) * 1.12) + 'px';
        $('.img').css('height', h);
        $('.img').css('line-height', h);
        $('.n-img').css('height', h);
    }

    $(window).resize(function () {
        changehe();
    })
    setTimeout("changehe()", 500);
</script>
<@m.page_footer />