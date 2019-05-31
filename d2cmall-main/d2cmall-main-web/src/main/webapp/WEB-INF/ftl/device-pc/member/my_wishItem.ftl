<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的愿望清单'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray">
        <@m.page_menu menu_item="wishItem"/>
        <div class="my-cont">
            <h1>我的愿望清单</h1>
            <div class="pages float-right" style="margin:0">
                <@m.p page=pager.pageNumber totalpage=pager.pageCount />
            </div>
            <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table">
                <tr class="order-item text-center">
                    <td>图片</td>
                    <td>商品名称</td>
                    <td>颜色</td>
                    <td>尺寸</td>
                    <td>操作</td>
                </tr>
                <#if pager.list?exists >
                <#list pager.list as list>
                <tr class="order-item text-center">
                    <td>
                        <img src="${picture_base}/${(list.product.productImageCover)!}!80"/>
                    </td>
                    <td>
                        <#if list.crowdItem?exists><a href='/crowds/product/${list.crowdItem.id}'
                                                      target='_blank'>${list.productName}</a>
                        <#else><a href='/product/${list.product.id}' target='_blank'>${list.productName}</a>
                        </#if>

                    </td>
                    <td>
                        ${list.color}
                    </td>
                    <td>
                        ${list.size}
                    </td>
                    <td>
                        <a href="/wishItem/delete" class="cancelwish" data-id="${(list.id)!}">删除</a>
                    </td>
        </div>
        </tr>
        </#list>
        <#else>
        <tr>
            <td colspan="8" style="line-height:150px;text-align:center;">暂时无记录</td>
        </tr>
        </#if>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
    <div class="clear"></div>
</div>
</div>
<script>
    $('.cancelwish').click(function () {
        var url = $(this).attr('href');
        var id = $(this).attr('data-id');
        var obj = $(this).parent().parent();
        jConfirm('确定要删除愿望吗？取消后无法恢复', '', function (r) {
            if (r) {
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    data: {id: id},
                    success: function (data) {
                        if (data.result.status == 1) {
                            obj.remove();
                        } else {
                            jAlert('删除失败！');
                        }
                    }
                });
            }
        });
        return false;
    });
</script>
<@m.page_footer />