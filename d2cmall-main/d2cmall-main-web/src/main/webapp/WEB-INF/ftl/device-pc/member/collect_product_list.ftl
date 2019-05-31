<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我收藏的商品' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="interest_collect"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1>我收藏的商品</h1>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
        <#if pager.totalCount &gt; 0 >
            <div class="list clearfix">
                <#list pager.list as memberCollection>
                    <div class="list-item" id="fav-${memberCollection.productId}">
                        <p class="img" style="display:table-cell;width:700px"><a
                                    href="/product/${memberCollection.productId}" target="_black"><img
                                        src="${picture_base}${(memberCollection.productPicFirst)!}!280"/></a></p>
                        <div class="detail-content">
                            <p class="title">
                                <a href="/product/${memberCollection.productId}" target="_blank" class="arial"
                                   title="${memberCollection.productName}">${memberCollection.productName}</a>
                            </p>
                            <p class="button-form">
                                <a class="button pm" href="/product/${memberCollection.productId}"
                                   style="margin-right:15px;">立即购买</a>
                                <a href="javascript:"
                                   data-url="/member/interest/collection/delete/${memberCollection.productId}"
                                   confirm="确定要取消收藏吗？取消后无法恢复" success-tip="取消收藏成功！" data-type="post"
                                   call-back="removeObj(${memberCollection.productId})"
                                   class="button button-black ajax-request pm">取消收藏</a>
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
        var width = $('.img').eq(0).width();
        var height = (parseInt(width) * 1.557) + 'px';
        $('.img').css('height', height);
        $('.img').css('line-height', height);
    }
    $(window).resize(function () {
        changehe();
    })

    setTimeout("changehe()", 500);
</script>
<@m.page_footer />