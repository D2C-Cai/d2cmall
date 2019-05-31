<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的评论' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="comment"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1 style="margin-bottom:14px;"> 我的评论</h1>
        <div class="tab tab-user-menu clearfix">
            <ul>
                <li<#if RequestParameters.source!='O2OSUBSCRIBE'> class="on"</#if>><a
                            href="/comment/list?source=ORDERITEM">商品订单</a><i class="interval">|</i></li>
                <li<#if RequestParameters.source=='O2OSUBSCRIBE'> class="on"</#if>><a
                            href="/comment/list?source=O2OSUBSCRIBE">预约订单</a><i class="interval">|</i></li>
            </ul>
        </div>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
        <table class="comment-font table table-grey ">
            <#assign x = 0>
            <#assign y = 0>
            <#if pager.list?exists && pager.list?size &gt; 0>
                <#list pager.list as list>
                    <#if list.source=='ORDERITEM'>
                        <#assign x = 1>
                        <tr class="line-item"
                            <#if list.commentReplys?size&gt;1>style="border-bottom:none!important"</#if> >
                            <td class="text-center" width="16%" colspan="2">
                                <img src="${picture_base}/${list.productImg}!/fwfh/160x220" alt="${list.title}"
                                     width="100%"/>
                                <p class="text-left grey">
                                    <span><#list list.skuProperty?split(',') as property>${property}</span>&nbsp;&nbsp;</#list>
                                </p>
                            </td>
                            <td class="comment-reply" style="vertical-align:top">
                                <div class="comment-score grey"><span data-star="${list.productScore}">商品评价：<span
                                                class="red"></span></span>
                                    <span style="float:right">
					<#if list.device?exists><#if list.device=='PC'>来自PC<#elseif list.device=='MOBILE'>来自WAP<#elseif list.device=='APPBUYER'||list.device=='APPIOS'>来自app(ios)<#elseif list.device=='APPANDROID'>来自app(Android)</#if></#if>  
					&nbsp;&nbsp;&nbsp;
					<span>${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</span></span></div>
                                <div class="my-comment">
                                    <div class="comment-content" style="font-size:16px!important">${list.content}</div>
                                </div>
                                <div class="clearfix show-img">
                                    <#if list.pictures?exists && list.pictures?size &gt; 0>
                                        <#list list.pictures as path>
                                            <div class="float-left">
                                                <img src="${picture_base}/${path}" height="120" alt="">
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
                                <#if list.commentReplys?size&gt;0>
                                    <#list list.commentReplys as commentReply>
                                        <#if commentReply.type=='SYSTEM' &&  commentReply_index lt 1>
                                            <div class="replys first">
                                                <div class="clearfix"><a class="text-right" style="width:18%;"><span
                                                                style="font-size:16px">D2C回复</span></br><span
                                                                style="color:#999;"> ${commentReply.createDate?string("yyyy-MM-dd HH:mm:ss")} </span></a><a
                                                            style="padding-left:20px;line-height:150%;">${commentReply.content}</a>
                                                </div>
                                            </div>
                                        </#if>
                                        <#if commentReply_index lt 1 && !commentReply_has_next && commentReply.type=='SYSTEM'>
                                            <p class="text-right" style="margin-top:20px;margin-bottom:0;"><a
                                                        href="javascripts:" class="ajax-request button button-s"
                                                        modal-type="pop" modal-width="474px"
                                                        template-url="/comment/reply/edit?commentId=${list.id}&replyId=${commentReply.id}">追评评论</a>
                                            </p>
                                        </#if>
                                    </#list>
                                </#if>

                                <#if !list.verified><p><a href="javasript:"
                                                          template-url="<#if list.source=='ORDERITEM'>/comment/item/edit?orderItemId=${list.sourceId}&commentId=${list.id}<#else>/comment/o2o/edit?o2oSubscribeId=${list.sourceId}&commentId=${list.id}</#if>"
                                                          modal-type="pop" modal-width="500"
                                                          class="ajax-request button button-s float-right"
                                                          style="margin-right:10px;">修改评论</a></p></#if>

                            </td>
                        </tr>
                        <#if list.commentReplys?size&gt;1>
                            <tr class="line-item" style="border-top:none!important">
                                <td style="padding:10px"></td>
                                <td width="15%" class="text-right"
                                    style="vertical-align:top;border-top:1px solid #eee;">
                                    <div class="float-left verticalline">
                                        <#list list.commentReplys as commentReply>
                                            <#if commentReply.type!='SYSTEM'>
                                                <span style="color:#4a91e3;font-size:16px">追评</span></br>
                                                <span>${commentReply.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                                            </#if>
                                        </#list>
                                    </div>
                                    <div class="float-right" style="width:1px;min-height:70px;background:#eee;">
                                    </div>
                                </td>
                                <td style="border-top:1px solid #eee;">
                                    <#list list.commentReplys as commentReply>
                                        <#if commentReply_index gte 1>
                                            <div class="replys <#if commentReply.type=='SYSTEM'>first<#else>second</#if>">
                                                <div class="clearfix"><#if commentReply.type=='SYSTEM'><a
                                                            class="text-right" style="width:18%;"><span
                                                                style="font-size:16px">D2C回复</span></br><span
                                                                style="color:#999;"> ${commentReply.createDate?string("yyyy-MM-dd HH:mm:ss")} </span>
                                                        </a>
                                                        <a style="padding-left:20px;line-height:150%;">${commentReply.content}</a><#else>${commentReply.content}</#if>
                                                </div>
                                            </div>
                                        </#if>
                                    </#list>
                                </td>
                            </tr>
                            <tr class="space">
                                <td colspan="3"></td>
                            </tr>
                        </#if>
                    <#elseif list.source=='O2OSUBSCRIBE'>
                        <#assign y = 1>
                        <tr class="line-item">
                            <td class="text-center" width="15%">
                                <img src="${picture_base}/${list.productImg}!/fwfh/160x220" alt="${list.title}"
                                     width="100%"/>
                                <p class="text-left grey"><#if list.skuProperty && list.skuProperty!=''><#list list.skuProperty?split(',') as property>
                                        <span>${property}</span></#list></#if></p>
                            </td>
                            <td class="comment-reply">
                                <div class="comment-score grey"><span data-star="${list.productScore}">商品评价：<span
                                                class="red"></span></span> <span
                                            style="float:right"><span>${list.createDate?string("yyyy-MM-dd HH:mm:ss")}</span></span>
                                </div>
                                <div class="my-comment">
                                    <div class="comment-content" style="font-size:16px">${list.content}</div>
                                </div>
                                <div class="clearfix show-img">
                                    <#if list.pictures?exists && list.pictures?size &gt; 0>
                                        <#list list.pictures as path>
                                            <div class="float-left">
                                                <img src="${picture_base}/${path}" height="120" alt="">
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
                                <#if list.commentReplys?size&gt;0>
                                    <#list list.commentReplys as commentReply>
                                        <#if commentReply.type=='SYSTEM' &&  commentReply_index lt 1>
                                            <div class="replys first">
                                                <div class="clearfix"><a class="text-right" style="width:18%;"><span
                                                                style="font-size:16px">D2C回复</span></br><span
                                                                style="color:#999;"> ${commentReply.createDate?string("yyyy-MM-dd HH:mm:ss")} </span></a><a
                                                            style="padding-left:20px;line-height:150%;">${commentReply.content}</a>
                                                </div>
                                            </div>
                                        </#if>
                                        <#if commentReply_index lt 1 && !commentReply_has_next && commentReply.type=='SYSTEM'>
                                            <p class="text-right" style="margin-top:20px;margin-bottom:0;"><a
                                                        href="javascripts:" class="ajax-request button button-s"
                                                        modal-type="pop" modal-width="474px"
                                                        template-url="/comment/reply/edit?commentId=${list.id}&replyId=${commentReply.id}">追评评论</a>
                                            </p>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if !list.verified><p><a href="javasript:"
                                                          template-url="<#if list.source=='ORDERITEM'>/comment/item/edit?orderItemId=${list.sourceId}&commentId=${list.id}<#else>/comment/o2o/edit?o2oSubscribeId=${list.sourceId}&commentId=${list.id}</#if>"
                                                          modal-type="pop" modal-width="500"
                                                          class="ajax-request button button-s float-right"
                                                          style="margin-right:10px;">修改评论</a></p></#if>

                            </td>
                        </tr>
                        <#if list.commentReplys?size&gt;0>
                            <tr class="line-item">
                                <td width="15%" class="text-right" style="vertical-align:top">
                                    <div class="float-left" style="margin-top:3%;width:93%;padding-right:5px;">
                                        <#list list.commentReplys as commentReply>
                                            <#if commentReply.type!='SYSTEM'>
                                                <span style="color:#4a91e3;font-size:16px">追评</span></br>
                                                <span>${commentReply.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                                            </#if>
                                        </#list>
                                    </div>
                                    <div class="float-right" style="width:1px;min-height:70px;background:#eee;">
                                    </div>
                                </td>
                                <td style="border-top:1px solid #eee;">
                                    <#list list.commentReplys as commentReply>
                                        <#if commentReply_index gte 1>
                                            <div class="replys <#if commentReply.type=='SYSTEM'>first<#else>second</#if>">
                                                <div class="clearfix"><#if commentReply.type=='SYSTEM'><a
                                                            class="text-right" style="width:18%;"><span
                                                                style="font-size:16px">D2C回复</span></br><span
                                                                style="color:#999;"> ${commentReply.createDate?string("yyyy-MM-dd HH:mm:ss")} </span>
                                                        </a>
                                                        <a style="padding-left:20px;line-height:150%;">${commentReply.content}</a><#else>${commentReply.content}</#if>
                                                </div>
                                            </div>
                                        </#if>
                                    </#list>
                                </td>
                            </tr>
                            <tr class="space">
                                <td colspan="3"></td>
                            </tr>
                        </#if>
                    </#if>
                </#list>
            </#if>
            <#if x==0 && RequestParameters.source!='O2OSUBSCRIBE'>
                <div style="background-color:#F5F5F5;height:50px;line-height:50px;padding:0 20px;clear:both;margin:60px 0 10px;letter-spacing:2px">
                    暂无商品评论
                </div>
            <#elseif y==0 && RequestParameters.source=='O2OSUBSCRIBE'>
                <div style="background-color:#F5F5F5;height:50px;line-height:50px;padding:0 20px;clear:both;margin:60px 0 10px;letter-spacing:2px">
                    暂无预约评论
                </div>
            </#if>

        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>
<script type="text/javascript">
    $.each($('span[data-star]'), function (i, obj) {
        var str = ''
        var num = $(obj).attr('data-star');
        for (var i = 0; i < num; i++) {
            str += '<span class="r-star"></span>';
        }
        for (var i = 0; i < 5 - num; i++) {
            str += '<span class="w-star"></span>';
        }
        $(obj).find('span').append(str);
    })
</script>
<@m.page_footer js='modules/page.user' />