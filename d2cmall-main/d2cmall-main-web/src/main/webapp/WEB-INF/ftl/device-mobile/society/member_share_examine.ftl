<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='买家秀审核' css='swiper.min' js='utils/swiper.min' keywords="设计师现货区,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场"/>

<div class="tab-holder swiper-container">
    <div class="tableshow swiper-wrapper">
        <a style="text-decoration:underline" class="swiper-slide  <#if RequestParameters.status=='0'>on</#if>"
           href="/membershare/mana/list?status=0">未审核</a>
        <a style="text-decoration:underline" class="swiper-slide <#if RequestParameters.status=='1'>on</#if>"
           href="/membershare/mana/list?status=1">已审核</a>
    </div>
</div>
</div>

<div class="fshow" style="margin-top:1em">
    <#if pager.list?size &gt; 0>
        <#list pager.list as minepage>
            <div class="myshow-item" style="border:none;margin-bottom:.5em">
                <div class="myshow-area">
                    <div class="switch" style="float:right;">
                        <div style="text-align:center;color:#fd555d;font-size:12px">审核</div>
                        <div class="btn_fath clearfix <#if minepage.status==0>off<#elseif  minepage.status==1>on</#if>"
                             onclick="toogle(this)">
                            <div class="move"
                                 data-state="<#if minepage.status==0>off<#elseif  minepage.status==1>on</#if>"
                                 data-id="${minepage.id}"></div>
                            <div class="btnSwitch btn1"></div>
                            <div class="btnSwitch btn2"></div>
                        </div>
                    </div>
                    <#if minepage.nickname!=''>
                        <div class="show-content" style="color:#364779;max-width:75%">${minepage.nickname}</div>
                    </#if>
                    <#if minepage.description!=''>
                        <div class="show-content" style="color:#666;max-width:75%">${minepage.description}</div>
                    </#if>
                    <#if minepage.picsToArray?exists && minepage.picsToArray?size &gt; 0>
                        <div class="show-img">
                            <div class="img-list-3">
                                <#list minepage.picsToArray as path>
                                    <a href="${picture_base}/${path}"><img src="${picture_base}/${path}!/sq/300"></a>
                                </#list>
                            </div>
                        </div>
                    </#if>
                    <div class="p-status" style="border:none;line-height:1.4em">
                        <span class="p-when">${(minepage.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</span>
                    </div>
                    <form action="" id="form_${minepage.id}"
                          method="get" <#if minepage.status==0> hidden="hidden" </#if>>
                        <div style="color:#696969;font-size:12px;font-weight:700">标签</div>
                        <input class="tag" shareId="${minepage.id}" name="bind-${minepage.id}" type="radio" value="1"
                               <#if minepage.tags??><#list minepage.tags?split(",") as tag><#if tag=='1'>checked</#if></#list></#if> /><span
                                style="color:#696969;font-size:5px">热门推荐</span>
                        <input class="tag" shareId="${minepage.id}" name="bind-${minepage.id}" type="radio" value="6"
                               <#if minepage.tags??><#list minepage.tags?split(",") as tag><#if tag=='6'>checked</#if></#list></#if> /><span
                                style="color:#696969;font-size:5px">达人说</span>
                    </form>
                </div>
            </div>
        </#list>
    </#if>
</div>
<div class="pages comment-pages">
    <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
</div>

<script type="text/javascript">
    function toogle(th) {
        var ele = $(th).children(".move");
        var id = ele.attr("data-id");
        if (ele.attr("data-state") == "on") {
            ele.animate({left: "0"}, 300, function () {
                $.post('/membershare/cancelVerify/' + id, function (data) {
                    if (data.result.status == 1) {
                        ele.attr("data-state", "off");
                        $.flashTip({position: 'center', type: 'success', message: '下架成功'});
                        $('#form_' + id).attr("hidden", "hidden");
                    } else {
                        $.flashTip({position: 'center', type: 'success', message: data.result.message});
                    }
                }, 'json');
            });
            $(th).removeClass("on").addClass("off");
        } else if (ele.attr("data-state") == "off") {
            ele.animate({left: '20px'}, 300, function () {
                $(this).attr("data-state", "on");
                $.post('/membershare/verify/' + id, function (data) {
                    if (data.result.status == 1) {
                        ele.attr("data-state", "on");
                        $.flashTip({position: 'center', type: 'success', message: '审核成功'});
                        $('#form_' + id).removeAttr("hidden");
                    } else {
                        $.flashTip({position: 'center', type: 'success', message: data.result.message});
                    }
                }, 'json');
            });
            $(th).removeClass("off").addClass("on");
        }
    }


    var mySwiper = new Swiper('.swiper-container', {
        slidesPerView: 2,
        slidesPerGroup: 2,
        resistanceRatio: 0,
        iOSEdgeSwipeDetection: true,
    })

    $(".tag").click(function () {
        var shareId = $(this).attr("shareId");
        var tagId = $(this).val();
        $.post('/membershare/bind/tag?tagId=' + tagId + '&shareId=' + shareId, function (data) {
            if (data.result.status == 1) {
                $.flashTip({position: 'center', type: 'success', message: '审核成功'});
                var other = tagId == 1 ? 6 : 1;
                $('.tag[value$=' + other + ']').removeAttr('checked');
            } else {
                $.flashTip({position: 'center', type: 'success', message: data.result.message});
            }
        }, 'json');
    });

</script>
<@m.page_footer />