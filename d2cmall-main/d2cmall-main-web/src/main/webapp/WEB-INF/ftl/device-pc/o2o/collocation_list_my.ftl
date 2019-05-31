<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的搭配'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray clearfix">
        <@m.page_menu menu_item="o2o_my_collocation"/>
        <div class="my-cont">
            <h1>我的搭配
                <button class="button-m b-b" onclick="location.href='/collocation/edit/0'">新增</button>
            </h1>

            <div class="pages" style="margin:0">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
            </div>
            <#if pager.list?exists && pager.list?size &gt; 0>
                <div class="interest-list clearfix">
                    <ul>
                        <#list pager.list as collocation>
                            <li style="width:240px;height:460px;">
                                <span style="font-size:15px;display:block;height:20px;">${(collocation.title)!}</span>
                                <!-- <img src="<${(collocation.pic)!}"/> -->
                                <div style="position:relative"><img src="${picture_base}${(collocation.pic)!}"
                                                                    width="240"/>
                                    <#if collocation.items && collocation.items?size gt 0>
                                        <#list collocation.items as item>
                                            <div class="tag-img-item" id="tag-item-${item.id}"
                                                 style="top:${item.y*100}%;left:${item.x*100}%">
                                                <div class="tag-img-cycle"><i></i><span></span></div>
                                                <div class="tag-img-text"><span>◆</span> ${item.title}</div>
                                                <div class="tag-img-edit display-none" id="tag-edit-${item.id}">
                                                    <span class="arrow"></span>
                                                    <div class="main">
                                                        <input type="hidden" name="id" class="form-input"
                                                               value="0"/><input type="hidden" name="collocationId"
                                                                                 class="form-input" value="${item.id}"/><input
                                                                type="hidden" name="x" class="form-input"
                                                                value="${item.x}"/><input type="hidden" name="y"
                                                                                          class="form-input"
                                                                                          value="${item.y}"/>
                                                        <p>单品标题</p>
                                                        <p><input type="text" name="title" size="30" class="form-input"
                                                                  value="${item.title}"/></p>
                                                        <p>单品价格</p>
                                                        <p><input type="text" name="price" size="30" class="form-input"
                                                                  value="${item.price}"/></p>
                                                        <p>单品连接</p>
                                                        <p><input type="text" name="url" size="30" class="form-input"
                                                                  value="${item.url}"/></p>
                                                        <p class="text-center">
                                                            <button type="button" rel="${item.id}"
                                                                    class="button-l b-b submit">提交
                                                            </button> &nbsp;&nbsp;
                                                            <button type="button" rel="${item.id}"
                                                                    class="button-l cancel">取消
                                                            </button>
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
                                <#if collocation.status<1 >
                                    <div style="padding:5px 0;">
                                        <a href="/collocation/submit/${collocation.id}" class="btn-operator btn-my-pay">提交</a>
                                        <a href="/collocation/edit/${collocation.id}" class="btn-my-pay">编辑</a>
                                        <a href="/collocation/delete/${collocation.id}" class="btn-operator btn-my-pay">删除</a>
                                    </div>
                                </#if>

                            </li>
                        </#list>
                    </ul>
                </div>
            <#else>
                <p style="line-height:150px;text-align:center">暂时无记录</p>
            </#if>
            <div class="pages">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount />
            </div>
        </div>
    </div>
</div>
<script>
    $('.btn-operator').click(function () {
        var text = $(this).text();
        var href = $(this).attr('href');
        window.parent.jConfirm('确定要' + text + '吗？', '', function (r) {
            if (r) {
                $.ajax({
                    url: href,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        if (data.result.status == 1) {
                            location.reload();
                        } else {
                            jAlert(data.result.message);
                        }
                    }
                });
            }
        });
        return false;
    });
</script>
<@m.page_footer />