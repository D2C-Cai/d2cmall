<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title="D2C拼团，尖货好物一起拼"  service="12.12"  css="flashpromotion"  keywords="全球好设计,设计师品牌,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="拼团商品即将开始，邀请好友一起拼" service='false'/>

<style>
    .lproduct-price {
        margin-bottom: 3px;
    }

    .popup-content {
        width: auto;
        height: auto;
        background: none;
    }

    .qr-box {
        width: 20em;
        height: 23.75em;
        background: #FFF;
        text-align: center;
    }

    .box-title {
        padding: 2.5em 0 .625em;
        color: #262626;
        font-weight: bold;
        line-height: 1.5;
    }

    .box-cont-tip {
        padding: 10px 0;
        line-height: 1.5;
        color: #7F7F7F;
        font-size: 12px;
    }

    .box-button {
        border: 0;
        width: 235px;
        height: 40px;
        line-height: 40px;
        background: #FD555D;
        color: #FFF;
    }

    .qr-close {
        width: 1.875em;
        height: 1.875em;
        position: absolute;
        bottom: -40px;
        left: 50%;
        -webkit-transform: translateX(-50%);
        transform: translateX(-50%);
    }
</style>
<#assign collageList=result.data.collageList>
<#if collageList?exists>
    <#list collageList.list as lists>
        <div class="limit-product-item flex">
            <a class="p-img" href="/collage/${lists.id}"><img src="${picture_base}${lists.product.img}!/both/600x900"
                                                              style="width:100%;"></a>
            <div class="lproduct-info">
                <p><a href="/collage/${lists.id}">${lists.product.name}</a></p>
                <#if lists.product.subTitle><p
                        style="font-size:10px;color:#999;-webkit-line-clamp:1">${lists.product.subTitle}</p></#if>
                <div>
                    <p class="lproduct-price"><span class="lightgrey">原价&nbsp;</span><span class="lightgrey"
                                                                                           style="text-decoration:line-through;">&yen;${lists.product.minPrice}</span>
                    </p>
                    <div class="lproduct-progress flex" style="justify-content:space-between;">
                        <div style="font-size:12px;">&nbsp;<span style="color:#fd555d;">${lists.memberCount}</span>人团&nbsp;&yen;<span
                                    style="font-size:1.5rem;font-weight: bold;">${lists.product.collagePrice}</span>
                        </div>
                        <#if lists.promotionStatus==0>
                            <div class="flash-button remind" onclick="popupHtml()">
                                提醒我
                            </div>
                        <#elseif lists.promotionStatus==1>
                            <a class="flash-button buy" href="/collage/${lists.id}">
                                去拼团
                            </a>
                        <#else>
                            <div class="flash-button" style="background:#fff;">
                                已结束
                            </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
    </#list>
</#if>

<div class="limit-container">

    <div class="limit-content" id="load-more-product" style="margin-bottom:0.875rem;">

    </div>
</div>
<div class="load-more flash-load-more" data-target="load-more-product" template-id="limit-list" data-url="/collage/list"
     style="display:none" data-page="${collageList.index}" data-total="${collageList.total}">点击加载更多
</div>


    </script>
    <
    script
    id = "limit-list"
    type = "text/html" >
        {
    {
        each
        list as value
        i
    }
    }
    <
    div
    class
    = "limit-product-item flex" >
        < a
    class
    = "p-img"
    href = "/collage/{{value.id}}" > < img
    src = "${picture_base}{{value.product.img}}!/both/600x900"
    style = "width:100%;" > < /a>
        < div
    class
    = "lproduct-info" >
        < p > < a
    href = "/collage/{{value.id}}" > {
    {
        value.collagePromotionName
    }
    }<
    /a></
    p >
    {
    {
        if value.product.subTitle}
    }<
    p
    style = "font-size:10px;color:#999" > {
    {
        value.product.subTitle
    }
    }<
    /p>{{/i
    f
    }
    }
    <
    div >
    < p
    class
    = "lproduct-price" > < span
    class
    = "lightgrey" > 原价 & nbsp;
    <
    /span><span class="lightgrey" style="text-decoration:line-through;">&yen;{{value.product.minPrice}}</s
    pan >
    < /p>
    < div
    class
    = "lproduct-progress flex"
    style = "justify-content:space-between;" >
        < div
    style = "font-size:12px;" > & nbsp;
    <
    span
    style = "color:#fd555d;" > {
    {
        value.memberCount
    }
    }<
    /span>人团&nbsp;&yen;<span style="font-size:1.5rem;font-weight: bold;">{{value.product.collagePrice}}</s
    pan > < /div>
    {
        {
            if value.promotionStatus == 0}
    }
    <
    div
    class
    = "flash-button remind"
    onclick = "popupHtml()" >
        提醒我
        < /div>
    {
        {else
            if value.promotionStatus == 1}
    }
    <
    a
    class
    = "flash-button"
    href = "/collage/{{value.id}}" >
        去拼团
        < /a>
    {
        {else
        }
    }
    <
    div
    class
    = "flash-button"
    style = "background:#fff;" >
        已结束
        < /div>
    {
        {
            /if}}
            < /div>
            < /div>
            < /div>
            < /div>
            {
                {
                    /each}}
</script>
<script>
    $(function () {
        if ($('.flash-load-more').size() > 0) {
            $('.flash-load-more').utilScrollLoads();
        }
    })


    $.fn.utilScrollLoads = function (source) {
        var element = $(this),
            can_load = true,
            url = element.attr('data-url'),
            target = element.attr('data-target'),
            template_id = element.attr('template-id');
        var conn_str = (url.indexOf('?') != -1) ? '&' : '?';
        $(window).bind("scroll", function () {
            var page = parseInt(element.attr('data-page')),
                total = parseInt(element.attr('data-total'));

            if (page < total) {
                if ((parseInt($(window).scrollTop()) + parseInt($(window).height())) > $(document).height() - 100) {
                    loadMorePage();
                }
            } else {
                can_load = false;
                element.html('已经全部加载完了').css('color', '#CCC');
            }
        });
        if (element.offset().top < $(document).height() - 100) {
            loadMorePage();
        }
        element.click(function () {
            loadMorePage();
        });

        function loadMorePage() {

            var page = parseInt(element.attr('data-page')),
                total = parseInt(element.attr('data-total'));
            if (can_load == true && page < total) {
                element.html('共' + total + '页 正在加载第' + (page + 1) + '页...').css('color', '');
                $.ajax({
                    url: url + conn_str + 'p=' + (page + 1) + '&' + new Date().getTime(),
                    dataType: 'json',
                    type: 'get',
                    beforeSend: function () {
                        can_load = false;
                    },
                    success: function (result) {

                        if (result.result.data.collageList) {

                            var res = result.result;
                            if (res.data.collageList.next) {
                                can_load = true;
                            } else {
                                can_load = false;
                            }
                            if (res.data.collageList.list.length > 0) {
                                var html = '';
                                template.config("escape", false);
                                if (template_id) {
                                    html = template(template_id, res.data.collageList);
                                } else {
                                    var render = template.compile(source);
                                    html = render(res);
                                }

                                $('#' + target).append(html);
                                $('#' + target + ' img').unveil(300);
                                element.attr('data-page', page + 1).text('点击加载更多').css('color', '');
                                element.attr('data-total', res.pageCount)

                            } else {
                                can_load = false;
                                element.html('已经全部加载完了').css('color', '#CCC');
                            }
                        }
                    }
                });
            }
        }
    }
</script>
<@m.page_footer />