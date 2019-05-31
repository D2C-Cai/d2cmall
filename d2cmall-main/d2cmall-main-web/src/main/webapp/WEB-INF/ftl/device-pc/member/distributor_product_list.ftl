<#import "templates/public_pc.ftl" as m>
<@m.page_header title='商品折扣清单'/>
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="dis_product"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 商品折扣清单</h1>
        <form name="" action="" method="get" style="margin-bottom:20px;">
            <input name="productSn" value="${(RequestParameters.productSn)!}" size="40" class="input"
                   placeholder="输入款号进行搜索"/>
            <button type="submit" name="" class="button button-green">搜索</button>
        </form>
        <div class="tip tip-multi-line tip-outline">
            1.同一款商品多次添加到购物车，会在原购物车商品数量上增加。<br/>
            2.若需要修改最终下单数量可以在购物车里进行修改或删除。<br/>
        </div>
        <div class="pages" style="margin:0">
            <@m.p page=result.datas.setting.pageNumber totalpage=result.datas.setting.pageCount num=result.datas.setting.totalCount />
        </div>
        <table class="table table-grey">
            <tr>
                <th width="36%">商品</th>
                <th width="10%">吊牌价</th>
                <th width="12%">折后价</th>
                <th width="20%">颜色尺码</th>
            </tr>
            <#if result.datas.setting.list?exists && result.datas.setting.list?size gt 0>
                <#list result.datas.setting.list as setting>
                    <tr class="space">
                        <td colspan="6"></td>
                    </tr>
                    <#assign rowspan=setting.product.restSalesproperty.sp1?size />
                    <#list setting.product.restSalesproperty.sp1 as productSku>
                        <tr class="item text-center">
                            <#if productSku_index==0>
                                <td rowspan="${rowspan}" style="text-align:left;line-height:200%;vertical-align:top">
                                    <a href="/product/${setting.product.id}" target="_blank" class="float-left"
                                       style="margin-right:10px;"><img
                                                src="${picture_base}${(setting.product.productImageCover)!}!180"
                                                width="120" height="187" alt="${setting.product.name}"/></a>
                                    <p style="height:140px;">${(setting.product.name)!}</p>
                                    <p>款号：${(setting.product.inernalSn)!}</p>
                                    <p>品牌：${(setting.product.designerName)!}</p>
                                </td>
                                <td rowspan="${rowspan}">
                                    &yen; ${(setting.product.originalPrice?string(',000.00'))!}</td>
                                <td rowspan="${rowspan}" style="font:bold 14px/200% Arial">
                                    <p>&yen; ${((setting.price)?string(',000'))!}</p>
                                </td>
                            </#if>
                            <td style="line-height:200%">
                                <p>
                                    <#if setting.product.colors?exists>
                                    <#list setting.product.colors as color>
                                <p style="color:blue"><${color.value!}> &nbsp;&nbsp;&nbsp;
                                    </#list>
                                    <#if setting.product.sizes?exists>
                                        <#list setting.product.sizes as size>${(size.value)!}
                                        </#list>
                                    </#if>
                                </p> &nbsp;&nbsp;&nbsp;
                                </#if>
                                </p>
                            </td>
                        </tr>
                    </#list>
                </#list>
            </#if>
        </table>
        <div class="pages">
            <@m.p page=result.datas.setting.pageNumber totalpage=result.datas.setting.pageCount num=result.datas.setting.totalCount />
        </div>
    </div>
</div>
<script id="cart-success-template" type="text/html">
    <div class="to-cart-success">
        <h3><a href="javascript:void(0)" onclick="$(this).parent().parent().parent().remove();"><i
                        class="fa fa-close"></i></a>购物车提示</h3>
        <div class="cart-modal-content">
            <p>你选择的商品成功放入购物车</p>
        </div>
        <div class="cart-modal-button">
            <button type="button" class="button button-s button-green"
                    onclick="$(this).parent().parent().parent().remove();">继续购物
            </button>
            <button type="button" class="button button-s button-red" onclick="location.href='/cart/list';">去结算</button>
        </div>
    </div>
</script>
<script>
    //数量增减
    $('.increase').click(function () {
        var obj = $(this);
        var i = parseInt(obj.siblings('.data-num').val()) + 1;
        if (i >= obj.siblings('.store').val() && parseInt(obj.siblings('.store').val()) >= 0) {
            i = obj.siblings('.store').val();
        }
        obj.siblings('.data-num').val(i);
    });
    $('.decrease').click(function () {
        var obj = $(this);
        var i = parseInt(obj.siblings('.data-num').val()) - 1;
        if (i <= 0) {
            i = 0;
        }
        obj.siblings('.data-num').val(i);
    });
    $(".data-num").blur(function () {
        var x = parseInt($(this).val());
        var y = parseInt($(this).siblings('.store').val());

        if (x > y) {
            $(this).val(y);
            $.flash_tips('最多能购买' + y + '件', 'warning');
            return false;
        }
    });
    $('.data-num').number();

    $('button[name=add-cart]').click(function () {
        var obj = $(this).parent().parent().parent();
        var h = 0;
        $.each(obj.find('.data-num'), function (i, d) {
            if ($(d).val() > 0) {
                h++;
            }
        });
        if (h == 0) {
            $.flashTip('至少要选择一件商品才能添加到购物车', 'warning');
            return false;
        }
        $.post('/cart/batchItem.json', obj.find('input').serialize(), function (data) {
            $('.success-buy').show();
            $('#cart-nums-id').data('change', 1);
        });
        return false;
    });
</script>
<@m.page_footer />