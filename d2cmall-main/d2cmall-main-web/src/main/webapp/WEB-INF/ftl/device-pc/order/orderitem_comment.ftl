<#import "templates/public_pc.ftl" as m>
<@m.page_header title='评价晒单' />
<@m.top_nav suspend=false />
<div class="layout layout-response">
    <p style="height:54px;line-height:54px;font-size:14px"><a href="/member/order">我的D2C</a>&nbsp;>&nbsp;<a
                href="/member/order">我的订单</a>&nbsp;>&nbsp;<a href="javascript:;">订单：${orderItem.orderSn}</a></p>
</div>
<div class="layout layout-response layout-order" style="margin-top:0">
    <div style="padding:5px 90px 80px;text-align:center">
        <h1 class="order-title" style="border:none;">评价晒单</h1>
        <p style="padding:5px 0;color:#999">订单号：<span style="color:#444">${orderItem.orderSn}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ${orderItem.createDate?string("yyyy.MM.dd HH:mm")!""}
        </p>
        <div style="border-top:1px solid #CCC;padding-top:5px;padding-bottom:63px;border-bottom:1px solid #CCC;overflow:hidden">
            <div style="float:left;width:33%;text-align:center;">
                <div style="margin-top:60px;border-right:1px solid #CCC;">
                    <p><img src="${picture_base}/${orderItem.sp1?eval.img}" height="200"></p>
                    <p class="title"
                       style="width:210px;margin:0 auto;padding-top:10px;line-height:130%;color:#999;height:45px;overflow:hidden">${orderItem.productName}</p>
                    <p style="width:210px;margin:0 auto;padding-top:8px;line-height:130%;color:#999">
                        颜色:${orderItem.sp1Value} 尺码:${orderItem.sp2Value}</p>
                    <p style="width:210px;margin:0 auto;padding-top:8px;">
                        <strong>&yen;&nbsp;${orderItem.productPrice}</strong></p>
                </div>
            </div>
            <div class="form" style="float:left;width:67%;margin-top:5px;">
                <form method="post" class="validate-form form-cinfo" action="${action}.json"
                      style="margin-top:0;border:none" call-back="returnorder">
                    <input type="hidden" name="id" value="${(comment.id)!}"/>
                    <input type="hidden" name="productId" value="${comment.productId}"/>
                    <input type="hidden" name="orderItemId" value="${orderItemId}"/>
                    <input type="hidden" name="anonymous" value="1"/>
                    <div style="float:left;width:10%;text-align:right;padding-top:29px;margin-right:10%;font-size:14px;font-weight:600">
                        满意度打分
                    </div>
                    <div style="float:left;width:80%;text-align:left;">
                        <div class="form-item" style="margin-top:5px">
                            <div style="width:50%;float:left;">
                                <label class="first" style="color:#999;font-size:14px">商品评分：</label>
                                <span class="rating">
			                <span class="star"></span><span class="star"></span><span class="star"></span><span
                                            class="star"></span><span class="star"></span>
			                <input type="hidden" name="productScore" value="${(comment.productScore)!'5'}"/>
			            </span>
                            </div>
                            <div style="width:50%;float:left;">
                                <label style="color:#999;font-size:14px">商品包装：</label>
                                <span class="rating">
			                <span class="star"></span><span class="star"></span><span class="star"></span><span
                                            class="star"></span><span class="star"></span>
			                <input type="hidden" name="packageScore" value="${(comment.packageScore)!'5'}"/>
			            </span>
                            </div>
                        </div>
                        <div class="form-item">
                            <div style="width:50%;float:left;">
                                <label width="25%" style="color:#999;font-size:14px">物流速度：</label>
                                <span class="rating">
			                <span class="star"></span><span class="star"></span><span class="star"></span><span
                                            class="star"></span><span class="star"></span>
			                 <input type="hidden" name="deliverySpeedScore"
                                    value="${(comment.deliverySpeedScore)!'5'}"/>
			            </span>
                            </div>
                            <div style="width:50%;float:left;">
                                <label width="25%" style="color:#999;font-size:14px">配送服务：</label>
                                <span class="rating">
			                <span class="star"></span><span class="star"></span><span class="star"></span><span
                                            class="star"></span><span class="star"></span>
			                <input type="hidden" name="deliveryServiceScore"
                                   value="${(comment.deliveryServiceScore)!'5'}"/>
			            </span>
                            </div>
                        </div>
                    </div>
                    <div style="float:left;width:10%;text-align:right;padding-top:29px;margin-right:10%;font-size:14px;font-weight:600">
                        评价晒单
                    </div>
                    <div style="float:left;width:80%;text-align:left;padding-top:19px;">
                        <div class="form-item">
                            <textarea style="height:130px;width:90%" name="content" onKeyDown="checkLength()"
                                      onKeyUp="checkLength()" onPaste="checkLength()"
                                      class="input">${(comment.content)!}</textarea>
                            <div style="padding-top:20px;">
                                <a href="javascript:void(0)" style="display:inline-block;">
                                    <input type="file" class="upload-file upload-pic" name="file"
                                           data-upload-url="/picture/upload" accept="image/gif,image/png,image/jpeg"/>
                                    <span style="display:inline-block;margin-left:10px;" class="text-upload">添加图片</span>
                                </a>
                                <div class="upload-preview<#if comment.pictures?exists> display-block<#else> display-none</#if>"
                                     style="margin-left:0">
                                    <#if comment.pictures?exists>
                                        <#list comment.pictures as path>
                                            <div class="upload-img">
                                                <a href="${picture_base}/${path}" class="img" target="_blank"><img
                                                            src="${picture_base}/${path}" height="100" alt=""/></a>
                                                <a href="javascript：void(0)" class="photo-del" title="删除">删除</a>
                                                <input type="hidden" name="path" class="path-input" value="${path}">
                                            </div>
                                        </#list>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="form-button checkbox" style="margin-top:20px;">
            <button type="button" class="button button-red button-l subbuton" style="font-size:18px">提交</button>
            <label style="margin-left:20px;color:#999"><input type="checkbox" id="anonymous" value="1" checked/><label
                        for="anonymous"> </label>匿名评价</label>
        </div>
    </div>
</div>
<script>
    $('.rating').utilRating();

    function checkLength() {
        var value = $('textarea[name=content]').val();
        if (value.length > 150) {
            $('textarea[name=content]').val(value.substr(0, 150));
        }
    }


    $('.subbuton').click(function () {
        $('.validate-form').submit();
    })

    $('#anonymous').click(function () {
        var t = $('input[name=anonymous]').val();
        if (t == 1) {
            $('input[name=anonymous]').val(0);
        } else {
            $('input[name=anonymous]').val(1);
        }

    })

    var returnorder = function () {
        setTimeout("location.href='/member/order'", 2000);
    }
</script>

<@m.page_footer js='modules/page.user' />