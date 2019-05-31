<#import "templates/public_mobile.ftl" as m>
<#if pager.list?size &gt; 0>
    <input type="hidden" id="productId" value="${pager.list[0].productId}"/>
    <#list pager.list as comment>
        <div class="comment-item">
            <div class="comment-item-content">
                <div class="comment-item-author">匿名</div>
                <div class="reviews">
                    <#if comment.content?length gt 0>${comment.content}<#else>未填写评论!</#if>
                </div>
                <#if comment.pictures?exists && comment.pictures?size &gt; 0>
                    <div class="pics clearfix">
                        <#list comment.pictures as path>
                            <a class="hover-img" href="${picture_base}/${path}"><img src="${picture_base}/${path}!120"
                                                                                     alt=""/></a>
                        </#list>
                    </div>
                </#if>
                <!--
        <div class="stars">
                         商品：${comment.productScore}星 &nbsp;
                         包装：${comment.packageScore}星 &nbsp;
                         送货速度：${comment.deliverySpeedScore}星 &nbsp;
                         配送服务：${comment.deliveryServiceScore}星
        </div>-->
            </div>
            <div class="comment-product-sku">
                ${comment.createDate?string("yyyy.MM.dd")} ${(comment.skuProperty)!}
            </div>

            <#if comment.commentReplys?size gt 0>
                <div class="comment-replys">
                    <#list comment.commentReplys as commentReply>
                        <div class="comment-reply-item">
                            <#if commentReply.type='SYSTEM'>解释：<#else>追评：</#if>${commentReply.content}
                            <i>${commentReply.createDate?string("yyyy.MM.dd")}</i>
                        </div>
                    </#list>
                </div>
            </#if>
        </div>
    </#list>
    <div class="pages comment-pages">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>
<#else>
    <div style="line-height:10em;text-align:center;">
        暂时还未有评论
    </div>
</#if>
<script>
    $('.comment-pages a').click(function () {
        var obj = $(this).parent().parent();
        $('html, body').animate({scrollTop: obj.offset().top - 55}, 300);
        var url = $(this).attr('href');
        var productId = $("#productId").val();
        $.get('/comment/product/' + productId + url, function (data) {
            obj.html(data);
        });
        return false;
    });
</script>