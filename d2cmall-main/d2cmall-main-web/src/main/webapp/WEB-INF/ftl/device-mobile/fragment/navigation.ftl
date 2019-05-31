<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='商品分类'  hastopfix='false'/>
<@m.page_nav_bar notel="商品分类 " channel='navigation'/>
<div class="category-list">
    <#assign navigations=renderNavigation()/>
    <div class="category-search">
        <div class="search-logo"></div>
        <form name="" action="/product/list">
            <button class="icon-search" type="submit"></button>
            <input type="search" name="k" placeholder="搜索品牌或商品" class="input" value="" style="padding-left:2em;"/>
        </form>
    </div>
    <#list navigations as navs>
        <dl<#if navs_index==0> class="on"</#if>>
            <dt>${navs.name}</dt>
            <dd>
                <#list navs.children as child>
                    <h2><em>${child.name}</em><span></span></h2>
                    <#if child.navigationItems?exists && child.navigationItems?size gt 0>
                        <p class="clearfix">
                            <#list child.navigationItems as item>
                                <a href="${(item.url)!}"><img
                                            src="${picture_base}${(item.pic)!}!/both/200x200"/>${(item.title)!}</a>
                            </#list>
                        </p>
                    </#if>
                </#list>
            </dd>
        </dl>
    </#list>
</div>

<script>
    $('.category-list dl dt').on('touchstart', function () {
        $(this).parent().addClass('on');
        $(this).parent().siblings().removeClass('on');
        return false;
    });
</script>
<@m.page_footer />