<#import "templates/public_pc.ftl" as m>
<#if pager.list?size &gt; 0>
    <#list pager.list as comment>
        <table class="comment-item" style="word-wrap: break-word;word-break:break-all;">
            <tr>
                <td>
                    <p class="comment-content"
                       style="font-size: 14px;"><#if comment.content?length gt 0>${comment.content}<#else>未填写评论!</#if></p>
                    <#if comment.pictures?exists && comment.pictures?size &gt; 0>
                        <div class="clearfix float-left commentimg-list">
                            <#list comment.pictures as path>
                                <div class="float-left commentimg-div">
                                    <a class="hover-img" style="position:relative;" href="javascript:"><img
                                                src="${picture_base}/${path}!120" height="60" alt=""/>
                                        <img style="display:none;position:absolute;top:40px;" class="comment-hover-img"
                                             src="${picture_base}/${path}" height="350" alt=""/>
                                    </a>
                                </div>
                            </#list>
                        </div>
                    </#if>
                    <div class="clearfix float-left comment-content" style="color:#A8A5A5;">
                        <span class="float-left">商品:${comment.productScore}&nbsp;&nbsp;&nbsp;</span>
                        <span class="float-left">商品包装:${comment.packageScore}&nbsp;&nbsp;&nbsp;</span>
                        <span class="float-left">送货速度:${comment.deliverySpeedScore}&nbsp;&nbsp;&nbsp;</span>
                        <span class="float-left">配送服务:${comment.deliveryServiceScore}&nbsp;&nbsp;&nbsp;</span>
                    </div>
                </td>
                <td width="130" style="color:#666;position:relative;">
                    <div><p>${(comment.skuProperty)!}</p></div>
                </td>
                <td width="120" style="color:#666;position:relative;">
                    <p>匿名
                        ${comment.createDate?string("yyyy-MM-dd")}</p>
                </td>
            </tr>
            <#list comment.commentReplys as commentReply>
                <tr>
                    <td>
                        <div class="pingjia-list">
                            <#if commentReply.type='SYSTEM'>解释：<#else>追评：</#if>${commentReply.content}<br/>
                    </td>
                    <td>
                    </td>
                    <td>
                        <p style="position:relative;left:30px;color:#AF874D;display:inline;">${commentReply.createDate?string("yyyy-MM-dd")}</p>
                    </td>
                </tr>
            </#list>
        </table>

    </#list>
    <div class="pages comment-a">
        <@m.p page=pager.pageNumber totalpage=pager.pageCount />
    </div>
<#else>
    <div style="line-height:600px;text-align:center;">
        暂时还未有评论
    </div>
</#if>
<script>
    $('.pages a').click(function () {
        var objurl = $(this).attr('href');
        $.ajax({
            url: '/comment/product/${comment.productId}' + objurl,
            type: 'get',
            dataType: 'html',
            success: function (data) {
                $('#comment').html(data);
            }
        })
        return false;
    });

    $('.hover-img').click(function () {
        $(this).parent().siblings().find(".comment-hover-img").hide();
        $(this).find('.comment-hover-img').slideToggle("2000");
    });
</script>

