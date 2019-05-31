<#import "templates/public_pc.ftl" as m>
<@m.page_header title='设计师品牌' keywords="设计师官网,d2c官网,设计师品牌,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" />
<@m.top_nav channel="showroom" />


<div class="layout layout-response layout-showroom clearfix">
    <div class="suspend-navs-showroom">
        <a href="/showroom">热门设计师</a>
        <a href="${base}/showroom/alphabetical">A ~ Z</a>
        <a class="nav-show-detail" href="javascript:;" data-url="/showroom/category/style" data-status="off">按风格</a>
        <div class="suspend-navs-children">
            <div class="nav-children">
            </div>
        </div>
        <a class="nav-show-detail" href="javascript:;" data-url="/showroom/category/designArea"
           data-status="off">按分类</a>
        <div class="suspend-navs-children">
            <div class="nav-children">
            </div>
        </div>
        <a class="nav-show-detail" href="javascript:;" data-url="/showroom/category/country" data-status="off">按地区</a>
        <div class="suspend-navs-children">
            <div class="nav-children">
            </div>
        </div>
    </div>
    <div class="letter-top-wrap layout-response scroll-suspend" data-anchor="true"
         data-scroll-holder="letter-top-holder">
        <div class="letter-top" id="letter-top">
            <#list letters as letter>
                <a href="#${letter}" data-height="100">${letter}</a>
            </#list>
        </div>
    </div>
    <div id="letter-top-holder" class="display-none" style="height:30px;"></div>
    <#list letters as letter>
        <div class="letter-wrap clearfix" id="${letter}">
            <div class="letter-left">
                ${letter}
            </div>
            <div class="letter-right">
                <ul>
                    <#list designers as designer>
                        <#if designer.pageGroup==letter>
                            <li><a href="/showroom/designer/${designer.id}" target="_blank">${designer.name}<span
                                            class="grey">&nbsp;/&nbsp;${designer.designers}</span><#if designer.recommend==1>
                                    <i>HOT</i> </#if></a></li>

                        </#if>
                    </#list>
                </ul>
            </div>
        </div>
    </#list>
</div>

<script type="text/javascript">

    var nav_t = [];
    $('.suspend-navs-showroom .nav-show-detail').hover(function (event) {
        var obj = $(this);
        var url = obj.attr('data-url');
        var status = obj.attr('data-status');
        if (status == 'off') {
            $.getJSON(url, function (res) {
                $.each(res.datas, function (i, d) {
                    var a = '<a class="nav-children-fl" data-id="' + d.id + '" data-type="' + d.type + '" href="javascript:;">' + d.name + '</a>';
                    obj.next('.suspend-navs-children').find('.nav-children').append(a);
                });
            });
            obj.attr('data-status', 'on');
        }
        $(this).siblings('.nav-show-detail').removeClass('gon');
        $(this).siblings('.suspend-navs-children').hide();
        $(this).addClass('gon');
        obj.next('.suspend-navs-children').show();
    }, function () {
        if ($('.suspend-navs-showroom:contains(window.event.srcElement)')) {
            $(this).addClass('gon');
            $(this).next('.suspend-navs-children').show();
        } else {
            $(this).removeClass('gon');
            $(this).next('.suspend-navs-children').hide();
        }
    })
    $('.suspend-navs-showroom').hover(function () {

    }, function () {
        $('.nav-show-detail').removeClass('gon');
        $('.suspend-navs-children').hide();
    })

    $('.suspend-navs-children').hover(function () {
        $(this).prev().addClass('gon');
        $(this).show();
    }, function () {
        $(this).prev().removeClass('gon');
        $(this).hide();
    })

    //记录用户行为  二级导航hover
    $('.suspend-navs-children').on('click', '.nav-children-fl', function () {
        var id = $(this).attr('data-id');
        var pi = $(this).parent().parent().prev().attr('data-id');
        var type = $(this).attr('data-type');
        nav_t[0] = pi;
        nav_t[1] = id;
        if (window.localStorage) {
            localStorage.setItem("menuTitle", nav_t);
        } else {
            Cookie.write("menuTitle", nav_t);
        }
        location.href = '/showroom/list?' + type + '=' + id;
    })

    //读取用户记录并执行操作
    var strStoreDate = window.localStorage ? localStorage.getItem("menuTitle") : Cookie.read("menuTitle");
    if (strStoreDate) {
        $(".nav-show-detail[data-id=" + strStoreDate.split(",")[0] + "]").trigger('mouseenter');
        setTimeout(function () {
            $(".nav-show-detail[data-id=" + strStoreDate.split(",")[0] + "]").next('.suspend-navs-children').find(".nav-children-fl[data-id=" + strStoreDate.split(",")[1] + "]").css('color', '#FD555D');
        }, 100);
    }


</script>

<@m.page_footer />