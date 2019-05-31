<#import "templates/public_pc.ftl" as m>
<#if pager.list?size &gt; 0>
    <#list pager.list as comment>
        <div class="pcomment-item">
            <div class="item-stars" data-star="${((comment.productScore))?substring(0,1)}">
                <div class="stars">

                </div>
                <p class="grey">${comment.createDate?string("yyyy.MM.dd")}</p>
                <p class="grey">${(comment.skuProperty)!}</p>
            </div>
            <div class="comment-content">
                <p><#if comment.content?length gt 0>${comment.content}<#else>未填写评论!</#if></p>
                <#if comment.pictures?exists && comment.pictures?size &gt; 0>
                    <div class="pics clearfix">
                        <#list comment.pictures as path>
                            <a class="hover-img" href="${picture_base}${path}" target="_blank"><img
                                        src="${picture_base}${path}!120" height="100%" alt=""/></a>
                        </#list>
                    </div>
                </#if>
            </div>
            <div class="comment-form">
                <div class="head-pic">
                    <#if comment.headPic=="" || !comment.headPic>
                        <a href="javascript:;" class="head">
                            <img src="//static.d2c.cn/img/home/160627/images/headpic.png" width="30"
                                 style="border-radius:50%;"/>
                        </a>
                    <#else>
                        <#if comment.headPic?index_of("http")!=-1>
                            <a href="javascript:;" class="head">
                                <img src="${comment.headPic}" width="30" style="border-radius:50%;"/>
                            </a>
                        <#else>
                            <a href="javascript:;" class="head">
                                <img src="${picture_base}${comment.headPic}" width="30" style="border-radius:50%;"/>
                            </a>
                        </#if>
                    </#if>
                    <span style="line-height:30px;margin-left:5px;">匿名</span>
                </div>
            </div>
            <#if comment.commentReplys?size gt 0>
                <#list comment.commentReplys as commentReply>
                    <#if commentReply.verified==1 || commentReply.type='SYSTEM'>
                        <#if commentReply.type='SYSTEM'>
                            <div class="replay-item clearfix">
                                <div class="reply" style="margin-left:16%;margin-top:20px;">
                                    ${commentReply.content}
                                    <p class="grey"
                                       style="margin-top:10px">${commentReply.createDate?string("yyyy.MM.dd")}</p>
                                </div>
                            </div>
                        <#else>
                            <div class="verified clearfix">
                                <div class="prozp" style="width:16%;color:#4a91e3;">
                                    <span style="margin-left:26%">匿名&nbsp;追评</span>
                                </div>
                                <div class="prozp" style="width:68%;">
                                    ${commentReply.content}
                                </div>
                                <div class="prozp" style="width:12%;color:#999;font-size:12px;text-align:center">
                                    ${commentReply.createDate?string("yyyy.MM.dd")}
                                </div>
                            </div>
                        </#if>
                    </#if>
                </#list>
            </#if>

        </div>
    </#list>
    <div class="pages comment-pages">
        <@m.p page=pager.pageNumber totalpage=pager.pageCount />
    </div>
<#else>
    <div style="line-height:600px;text-align:center;">
        暂时还未有评论
    </div>
</#if>
<script>
    $('.comment-pages a').click(function () {
        var obj = $(this).parent().parent();
        $('html, body').animate({scrollTop: obj.offset().top - 55}, 300);
        var url = $(this).attr('href');
        $.get('/comment/product/${(searcher.productId)!}' + url, function (data) {
            obj.html(data);
        });
        return false;
    });
    $.each($('.item-stars[data-star]'), function (i, obj) {
        var str = ''
        var num = $(obj).attr('data-star');
        for (var i = 0; i < num; i++) {
            str += '<span class="r-star"></span>';
        }
        for (var i = 0; i < 5 - num; i++) {
            str += '<span class="w-star"></span>';
        }
        $(obj).find('.stars').append(str);
    })
</script>

