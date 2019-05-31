<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的预约" title='我的预约' service='false' hastopfix='false'/>
<div class="section lazyload">
    <div class="tab-holder">
        <div class="tab tab-suspend">
            <a href="/o2oSubscribe/my/list"<#if !RequestParameters.type> class="on"</#if>>所有</a>
            <a href="/o2oSubscribe/my/list?type=0"<#if RequestParameters.type==0> class="on"</#if>>待预约</a>
            <a href="/o2oSubscribe/my/list?type=1"<#if RequestParameters.type==1> class="on"</#if>>预约中</a>
            <a href="/o2oSubscribe/my/list?type=2"<#if RequestParameters.type==2> class="on"</#if>>待服务</a>
            <a href="/o2oSubscribe/my/list?type=3"<#if RequestParameters.type==3> class="on"</#if>>待评价</a>

        </div>
    </div>
    <style>
        .form .form-item.item-flex label + label {
            text-align: left
        }
    </style>
    <#if pager.list?exists && pager.list?size &gt; 0>
        <#list pager.list as list>
            <#if list.status!=0>
                <div class="form">
                    <div class="form-item item-flex">
                        <label>预约编号</label><label>${(list.sn)!}<span
                                    style="float:right;color:#F23365"><#if list.status==-2>客服取消<#elseif list.status==-1>用户取消<#elseif list.status==1>提交待审核<#elseif list.status==2>客服已通知门店<#elseif list.status==3>门店已接收<#elseif list.status==4>门店准备完成<#elseif list.status==5>待评价<#elseif list.status==6>已评价</#if></span></label>
                    </div>
                    <div class="form-item item-flex" style="border-bottom:none;">
                        <label>预约时间</label><label><#if list.estimateDate>${(list.estimateDate?string("yyyy.MM.dd"))!} &nbsp;${(list.startHour)!}:${(list.startMinute?string("#00"))!}-${(list.endHour)!}:${(list.endMinute?string("#00"))!}<#else>还未预约</#if></label>
                    </div>
                    <#if list.storeName!=''>
                        <div class="form-item item-flex" style="border-top:none;">
                            <label style="vertical-align:top">预约门店</label><label><span
                                        style="color:#000;font-size:1.1em">${(list.storeName)!}</span></br><span
                                        style="font-size:10px;color:#999">${(list.storeAddress)!}</span></label>
                        </div>
                        <div class="form-item item-flex"
                             style="border-bottom:none; display: -webkit-flex;display: flex; align-items:center;padding-top:0.65em;padding-bottom:.55em">
                            <label style="width:50%;text-align:center;border-right:1px solid #EDEDED"><a
                                        href="tel:${(list.storeTel)!}"><span class="icon icon-tel"
                                                                             style="width:1.4em;height:1.4em;"></span>&nbsp;&nbsp;&nbsp;<span>${(list.storeTel)!}</span></a></label>
                            <label style="width:50%;text-align:center;"><a
                                        href="//api.map.baidu.com/geocoder?address=${(list.storeAddress)!}&output=html&src=D2C"
                                        id="address"><span class="icon icon-address"
                                                           style="width:1.4em;height:1.4em;"></span>&nbsp;&nbsp;<span>查看地图</span></a></label>
                        </div>
                        <div class="form-item item-flex"
                             style="border-bottom:none;padding-top:0.65em;padding-bottom:.55em;text-align:center">
                            <label class="arrow" style="position:relative;"><i></i><span class="txt">更多信息</span></label>
                        </div>
                    </#if>
                    <div class="toggle-hide" style="display:none">
                        <div class="form-item item-flex" style="border-bottom:none;">
                            <label>预约人</label><label>${(list.name)!}<span
                                        style="float:right;">${list.numbers}人</span></label>
                        </div>
                        <div class="form-item item-flex" style="border-top:none;">
                            <label>预约电话</label><label>${(list.tel)!}</label>
                        </div>
                        <div class="form-item item-flex">
                            <label>预约服务</label><label>${(list.storeService)?replace(',','/')}</label>
                        </div>
                    </div>
                    <#if list.status == -2 >
                        <div class="form-item item-flex">
                            <label>关闭原因</label><label>${(list.cancelReason)!}</label>
                        </div>
                    </#if>
                    <div class="clearfix" style=" border-top: 1px solid #EDEDED;border-bottom: 1px solid #EDEDED;">
                        <#list list.items as item>
                            <div class="form-item item-card clearfix" style="border:none">
                                <a href="/product/${(item.productId)!}">
                                    <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                                           data-image="${picture_base}${(item.sp1?eval.img)!}!275"
                                                           alt="" style="min-height:6em"/></span>
                                    <span class="title">${item.productName} &nbsp; ${(item.sp1?eval.value)!} ${(item.sp2?eval.value)!}</span>
                                </a>
                                <span class="property"
                                      style="position:absolute;bottom:2.3em;left:23%;">${item.sp1?eval.name}：${item.sp1?eval.value} &nbsp;${item.sp2?eval.name}：${item.sp2?eval.value}</span>
                            </div>
                        </#list>
                    </div>
                    <div class="form-item text-right">
                        <#if list.status==1>
                            <button type="button" data-url="/o2oSubscribe/cancel/${list.id}" confirm="确定要取消该预约吗？"
                                    class="button  button-outline button-s ajax-request"
                                    style="width:22%;font-size:10px;">取消预约
                            </button>
                        </#if>
                        <#if list.status gt 4 >
                            <#if list.commentId >
                                <a href="javascript" style="font-size:10px;color:#F23365">已评价</a>
                            <#else>
                                <#list list.items as item>
                                    <#if item_index == 0>
                                        <#assign productImg = "${item.sp1?eval.img}",skuProperty="颜色:${(item.sp1?eval.value)!},尺码:${(item.sp2?eval.value)!}"/>
                                    </#if>
                                </#list>
                                <a href="/comment/o2o/edit?o2oSubscribeId=${list.id}&productImg=${productImg}&skuProperty=${skuProperty}"
                                   class="button button-white button-s text-center" style="width:22%;font-size:10px;">预约评价</a>
                            </#if>
                        </#if>
                        <#if list.status lt 0 >
                            <button type="button" data-url="/o2oSubscribe/delete/${list.id}" confirm="确定要删除预约吗？"
                                    class="button button-outline  button-s ajax-request"
                                    style="width:22%;font-size:10px;">删除
                            </button>
                        </#if>
                    </div>
                </div>
            <#else>
                <div class="form">
                    <#list list.items as item>
                        <div class="item form-item item-card clearfix" style="border:none" data-id="${(item.id)!}">
                            <a href="/product/${(item.productId)!}">
                                <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                                       data-image="${picture_base}${(item.sp1?eval.img)!}!275" alt=""
                                                       style="min-height:6em"/></span>
                                <span class="title">${item.productName} &nbsp; ${(item.sp1?eval.value)!} ${(item.sp2?eval.value)!}</span>
                            </a>
                            <span class="property"
                                  style="position:absolute;bottom:2.3em;left:23%;">${item.sp1?eval.name}：${item.sp1?eval.value} &nbsp;${item.sp2?eval.name}：${item.sp2?eval.value}</span>
                            <a href="javascript:" data-url="/o2oSubscribe/deleteItem/${(item.id)!}" confirm="确定删除预约吗？"
                               success-tip="删除预约成功！" call-back="removeItem(${(item.id)!})"
                               class="icon icon-trash ajax-request reserve-trash"></a>
                        </div>
                    </#list>

                    <div class="form-item text-right op-bar">
                        <button type="button" data-url="/o2oSubscribe/delete/${list.id}" confirm="确定取消预约吗？"
                                class="button button-outline button-s ajax-request"
                                style="width:22%;font-size:10px;margin-right:7px;">取消预约
                        </button>
                        <a href="/o2oSubscribe/confirm/${list.id}" class="button button-black button-s text-center"
                           style="width:22%;font-size:10px;">立即预约</a>
                    </div>

                </div>
            </#if>
        </#list>
        <div class="pages" style="margin:0">
            <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
        </div>
    <#else>
        <div class="no-result">你还没预约过试穿</div>
    </#if>
</div>

<script>
    var removeItem = function (id) {
        $('.item[data-id=' + id + ']').addClass('animated zoomOutUp');
        setTimeout(function () {
            $('.item[data-id=' + id + ']').remove();
            if ($('.item[data-id]').size() <= 0) {
                $('#next').attr('disabled', 'true');
                $('.op-bar,.pages').remove();
            }
        }, 500);
    }

    $('#submit-form').submit(function () {
        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: $(this).serialize(),
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                    window.parent.$.flash_tips('修改成功!', 'success');
                    window.frames['main-frame'].location.reload();
                } else {
                    window.parent.$.flash_tips('修改失败!' + data.result.message, 'error');
                }
                setTimeout(function () {
                    $('#ebox-ebox-remove').trigger('click');
                }, 500);

            }
        });
        return false;
    });
    $('.arrow').on('click', function () {
        var txt = $(this).find('.txt');
        $(this).find('i').toggleClass('up');
        $(this).parent().next('.toggle-hide').toggle();
        if (txt.text() == "更多信息") {
            txt.text('收起');
        } else {
            txt.text('更多信息')
        }

    })
    $('.rating').utilRating();
</script>
<@m.page_footer />
