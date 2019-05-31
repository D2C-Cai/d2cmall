<#import "templates/public_pc.ftl" as m>
<@m.page_header title='搭配管理'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray clearfix">
        <@m.page_menu menu_item="o2o_collocation"/>
        <div class="my-cont">
            <h1>搭配秀</h1>
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
    $('.submit-search').click(function () {
        $('#search-form').attr('action', $(this).attr('data-url'));
        $('#search-form').submit();
    });

    $('button[name=close-order-btn]').die().live('click', function () {
        var id = $(this).parent().parent().attr('data-id');
        var url = $(this).parent().parent().attr('data-url');
        var feedback = $('textarea[name=feedback]').val();
        var payAmount = $('input[name=payAmount]').val();
        var payStatus = $('select[name=payStatus]').val();
        var actualNumbers = $('input[name=actualNumbers]').val();
        var retailSn = $('input[name=retailSn]').val();
        var reg = /^[0-9]*[1-9][0-9]*$/;
        if (!(payAmount == null || payAmount == '' || reg.test(payAmount))) {
            return false;
        }
        jConfirm('确定要提交吗？', '', function (r) {
            if (r) {
                $.ajax({
                    'url': url,
                    'data': {
                        id: id,
                        feedback: feedback,
                        payAmount: payAmount,
                        payStatus: payStatus,
                        actualNumbers: actualNumbers,
                        retailSn: retailSn
                    },
                    dataType: 'json',
                    type: "post",
                    'success': function (data) {
                        location.reload();
                    }
                });
            }
        });
        return false;
    });
    $('.select-value').live('change', function () {
        var i = $(this).val();
        if (i == 1) {
            $('.select-span').show();
        } else {
            $('.select-span').hide();
        }
    });
</script>
<@m.page_footer />