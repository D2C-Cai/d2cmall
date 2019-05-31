<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我收藏的商品" title='我收藏的商品' service='false' hastopfix='false'/>
<div class="section lazyload" style="margin-top:0.4em;">
    <div class="list clearfix">
        <#if pager.totalCount gt 0 >
            <#list pager.list as memberCollection>
                <div class="item item-flex item-gap" data-id="${memberCollection.productId}"
                     style="padding-bottom:1em;text-align:center">
                    <a href="/product/${memberCollection.productId}" style="display:block;padding-bottom:0.6em">
                <span class="img">
                <img src="${static_base}/m/img/blank100x157.png"
                     data-image="${picture_base}${(memberCollection.productPicFirst)!}!280" alt=""/></span>
                        <span class="title">${memberCollection.productName}</span>
                    </a>
                    <button type="button" data-url="/member/interest/collection/delete/${memberCollection.productId}"
                            confirm="确定取消收藏吗？" call-back="removeItem(${memberCollection.productId})"
                            class="button button-white ajax-request" style="width:50%;padding:0">取消收藏
                    </button>
                </div>
            </#list>
            <div class="pages">
                <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
            </div>
        <#else>
            <div class="no-result">你还没收藏过商品</div>
        </#if>
    </div>
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