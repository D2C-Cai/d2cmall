<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的预约单' js='utils/jquery.datepicker' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="my_subscribe"/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i> 我的门店试穿预约单</h1>
        <div class="pages float-right" style="margin:0">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount num=pager.totalCount />
        </div>
        <table border="0" cellpadding="6" cellspacing="0" width="100%" class="table table-grey"
               style="word-break:break-all">
            <#if pager.list?exists && pager.list?size &gt; 0>
                <#list pager.list as list>
                    <tr class="space">
                        <td colspan="6" style="height:20px;"></td>
                    </tr>
                    <tr class="title">
                        <td colspan="6" class="text-right">
                            <#if list.status == 0 >
                                <button template-url="/o2oSubscribe/confirm/${list.id}?target=self" modal-type="pop"
                                        modal-width="710" class="button button-green ajax-request"><i
                                            class="fa fa-check"></i> 提交预约
                                </button>
                                <button data-url="/o2oSubscribe/delete/${list.id}" confirm="确定删除预约吗？"
                                        success-tip="预约删除成功！" class="button button-red ajax-request"><i
                                            class="fa fa-close"></i> 删除
                                </button>
                            </#if>
                            <#if list.status==1>
                                <button template-url="/o2oSubscribe/confirm/${list.id}?target=self" modal-type="pop"
                                        modal-width="710" class="button button-green ajax-request"><i
                                            class="fa fa-check"></i> 修改预约
                                </button>
                                <button data-url="/o2oSubscribe/cancel/${list.id}" confirm="确定取消预约吗？"
                                        success-tip="预约取消成功！" class="button button-red ajax-request"><i
                                            class="fa fa-close"></i> 取消预约
                                </button>
                            </#if>
                            <#if list.status gt 4 >
                                <#if list.commentId >
                                    <a href="/comment/list?source=O2OSUBSCRIBE&id=${list.commentId}"
                                       class="button button-green"><i class="fa fa-comment"></i> 查看评价</a>
                                <#else>
                                    <#list list.items as item>
                                        <#if item_index == 0>
                                            <#assign productImg = "${item.sp1?eval.img}",skuProperty="颜色:${(item.sp1?eval.value)!},尺码:${(item.sp2?eval.value)!}"/>
                                        </#if>
                                    </#list>
                                </#if>
                                <#if !list.commentId?exists>
                                    <button template-url="/comment/o2o/edit?o2oSubscribeId=${list.id}&productImg=${productImg}&skuProperty=${skuProperty}"
                                            modal-type="pop" modal-width="500" class="button button-green ajax-request">
                                        <i class="fa fa-comment"></i>评价
                                    </button>
                                </#if>
                            </#if>

                            <#if list.status lt 0 >
                                <button data-url="/o2oSubscribe/delete/${list.id}" confirm="确定删除预约吗？"
                                        success-tip="预约删除成功！" class="button button-red ajax-request"><i
                                            class="fa fa-close"></i> 删除
                                </button>
                            </#if>
                        </td>
                    </tr>
                    <tr class="item">
                        <td class="text-left">门店</td>
                        <td colspan="5">
                            <#if list.storeId!=null >${(list.storeName)!} (${(list.storeAddress)!}/ ${(list.storeTel)!})  
                                <a href="//api.map.baidu.com/geocoder?address=${(list.storeAddress)!}&output=html"
                                   style="color:blue" target="_blank">[查看地图]</a>
                            <#else>未指定
                            </#if>
                        </td>
                    </tr>
                    <tr class="item text-left">
                        <td>预约人</td>
                        <td><p>${(list.name)!}</p></td>
                        <td>预约人电话</td>
                        <td><p>${(list.tel)!}</p></td>
                        <td>预约人微信</td>
                        <td><p>${(list.wechat)!}</p></td>
                    </tr>
                    <tr class="item text-left">
                        <td width="10%">预约单号</td>
                        <td width="15%"><p style="color:#2374cd">${(list.sn)!}</p></td>
                        <td width="10%">预约时间</td>
                        <td width="20%"><p>${(list.estimateDate?string("yyyy-MM-dd"))!} ${(list.startHour)!}
                                点${(list.startMinute)!}分 到 ${(list.endHour)!}点${(list.endMinute)!}分</p></td>
                        <td width="10%">预约服务</td>
                        <td width="25%"><p>${(list.storeService)!}</p></td>
                    </tr>
                    <tr class="item text-left">
                        <td>预约人数</td>
                        <td>${(list.numbers)!}</td>
                        <td>首选联系方式</td>
                        <td>${(list.contact)!}</td>
                        <td>状态</td>
                        <td>${(list.statusName)!}
                            <#if list.status == -2 >
                                &nbsp;&nbsp;&nbsp;<span style="color:#FF0000">关闭原因：${(list.cancelReason)!}</span>
                            </#if>
                        </td>
                    </tr>
                    <tr class="item text-left">
                        <td>留言</td>
                        <td colspan="5"><p class="text-left">${(list.optionRequire)!}</p></td>
                    </tr>
                    <tr class="item">
                        <td colspan="6">
                            <#list list.items as item>
                                <div class="table-product-item" data-id="${item.id}">
                                    <p class="img"><a href="/product/${item.productId}" target="_blank"><img
                                                    src="${picture_base}/${item.sp1?eval.img}!80" alt=""
                                                    width="70"/></a></p>
                                    <p class="title"><a href="/product/${item.productId}" style="line-height:18px;"
                                                        target="_blank">${(item.productName)!}</a></p>
                                    <p class="desc">颜色：${(item.sp1?eval.value)!}&nbsp;&nbsp;尺码：${(item.sp2?eval.value)!}
                                        &nbsp;&nbsp;数量：${(item.quantity)!}&nbsp;&nbsp;价格：${(item.price)!}元</span></p>
                                    <#if list.status == 0 >
                                        <p class="del">
                                            <button data-url="/o2oSubscribe/deleteItem/${item.id}" confirm="确定要取消此件商品吗？"
                                                    call-back="remove(${item.id})"
                                                    class="button button-s button-red ajax-request"><i
                                                        class="fa fa-close"></i> 删除
                                            </button>
                                        </p>
                                    </#if>
                                </div>
                            </#list>
                        </td>
                    </tr>
                </#list>
            </#if>
        </table>
        <div class="pages float-right">
            <@m.p page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    </div>
</div>
<script>
    var remove = function (id) {
        $('.table-product-item[data-id=' + id + ']').remove();
    }


</script>
<@m.page_footer />