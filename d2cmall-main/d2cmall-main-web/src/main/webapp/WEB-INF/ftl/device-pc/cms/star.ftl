<#import "templates/public_pc.ftl" as m>
<@m.page_header title='明星风范' keywords="预售,设计师品牌,全球好设计,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店" description="消费者可以通过平台了解设计师的产品理念和创作灵感，树立设计师形象，提升设计师品牌影响力，扩大设计师产品市场" js='utils/unslider.min|utils/imagesloaded.pkgd.min|utils/masonry.pkgd.min'/>
<@m.top_nav channel="star" />
<div class="starbanner-fullscreen" data-spam="1.1">
    <#if pageDefine.fieldDefs.block04.type==0 >
        <div class="star-slide-screen" id="top-slide">
            <ul>
                ${(homePage.block04)!}
            </ul>
        </div>
    <#else>
        <div class="star-slide-screen" id="top-slide">
            <ul>
                <#if homePage.blocks.block04?size gt 0>
                    <#list homePage.blocks.block04 as block>
                        <li data-title="${(block.title)!}" <#if block.margin!=''> style="background:${(block.margin)!}"</#if>>
                            <a href="${(block.bindUrl)!}" target="_blank"><img src="${picture_base}${(block.midPic)!}"
                                                                               alt="${(block.title)!}"/></a>
                        </li>
                    </#list>
                </#if>
            </ul>
        </div>
    </#if>
    <div class="banner-title display-none">
        <ul>
            <li class="arrow"><a class="prev" href="javascript:">&lt;</a></li>
            <li class="content"> &nbsp;</li>
            <li class="arrow"><a class="next" href="javascript:">&gt;</a></li>
        </ul>
    </div>
</div>
<#if pageDefine.fieldDefs.block10.status==1>
    <#if pageDefine.fieldDefs.block10.type==0>
        ${homePage.block10?replace('src=','src="/static/c/images/space.gif" data-image=')}
    <#else>
        <div class="layout-response" id="waterfall">
            <div class="gutter-sizer"></div>
            <#if pager?exists && pager.list?exists && pager.list?size gt 0>
                <#list pager.list as block>
                    <#if block.frontPic?exists>
                        <div class="item">
                            <a href="${(block.bindUrl)!}" target="_blank">
                                <img src="${picture_base}${(block.frontPic)!}!/fw/250/strip/true" class="img"
                                     alt="${(block.title)!}" width="247"/>
                                <span class="bottom text-center">
            ${(block.title)!}
            </span>
                            </a>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
        <#if pager?exists && pager.pageCount gt 1>
            <div id="load-more" class="clearfix w-1200 load-more" data-page="${pager.pageNumber}"
                 data-total="${pager.pageCount}"><i></i></div>
        </#if>
    </#if>
</#if>
<#if pageDefine.fieldDefs.block08.status==1>
    <div class="w-1200 clearfix lazyload">
        <#if pageDefine.fieldDefs.block08.type==0>
            <#if homePage.block08?exists>
                ${homePage.block08?replace('src=','src="/static/c/images/space.gif" data-image=')}
            </#if>
        <#else>
            <#list homePage.blocks.block08 as block>
                <div class="float-left"<#if block.margin!=''> style="margin:${(block.margin)!}"</#if>>
                    <a href="${(block.bindUrl)!}" target="_blank"><img width="${(block.width)!}"
                                                                       height="${(block.height)!}"
                                                                       alt="${(block.title)!}"
                                                                       src="/static/c/images/space.gif"
                                                                       data-image="${picture_base}${(block.frontPic)!}!/fw/250/strip/true"/></a>
                </div>
            </#list>
        </#if>
    </div>
</#if>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="item {{id}}">
        <a href="{{value.bindUrl}}" target="_blank">
            <img src="${picture_base}{{value.frontPic}}!/fw/250/strip/true" class="img" alt="{{value.title}}"
                 width="247"/>
            <span class="bottom text-center">
            {{value.title}}
            </span>
        </a>
    </div>
    {{/each}}
</script>
<script>
    $(document).ready(function (e) {
        var unslider04 = $('#top-slide').unslider({
                dots: true,
                fluid: true

            }),
            data04 = unslider04.data('unslider');

    });

    var $container = $('#waterfall').masonry({
        animate: true,
        itemSelector: '.item',
        gutter: ".gutter-sizer"
    });

    $container.imagesLoaded().progress(function () {
        $container.masonry('layout');
    });


    var can_load = true;
    $(window).bind("scroll", function () {
        if ($('#load-more').size() > 0) {
            var page = parseInt($('#load-more').attr('data-page')),
                total = parseInt($('#load-more').attr('data-total'));
            if (page < total) {
                if ($(document).height() - $(window).scrollTop() - $(window).height() < 600) {
                    load_page();
                }
            } else {
                $('#load-more').remove();
            }
        }
    });
    $('#load-more').click(function () {
        load_page();
    });

    function load_page() {
        var page = parseInt($('#load-more').attr('data-page')),
            total = parseInt($('#load-more').attr('data-total'));
        if (can_load == true) {
            $.ajax({
                url: '/blocks?id=${homePage.id}' + '&pageSize=10&p=' + (page + 1) + '&' + new Date().getTime(),
                dataType: 'json',
                type: 'GET',
                beforeSend: function () {
                    can_load = false;
                },
                success: function (result) {
                    if (!result.pager.next) {
                        $('#load-more').remove();
                    } else {
                        var myid = 'm-' + new Date().getTime();
                        var data = {'id': myid, 'list': result.pager.list};
                        var html = template('list-template', data);
                        $container.append(html).masonry('appended', $('.' + myid));
                        $container.imagesLoaded().progress(function () {
                            $container.masonry('layout');
                        });
                        can_load = true;
                        $('#load-more').attr('data-page', page + 1);
                    }
                }
            });
        }
    }
</script>
<script type="text/javascript">
    window.__zp_tag_params = {
        pagetype: 'listPage',
        productId_list: '<#if pager.list?exists && pager.list?size gt 0><#list pager.list as block>${block.bindUrl?substring(9)},</#list></#if>'
    };
</script>
<@m.page_footer />