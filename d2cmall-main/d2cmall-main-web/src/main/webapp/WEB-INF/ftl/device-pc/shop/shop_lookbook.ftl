<div class="lookbook w-1200" style="position:relative;">
    <div class="lookbook-shadow png"></div>
    <div class="lookbook-left">
        <div class="showroom-item">
            <div class="designer-info">
                <div class="designer-sign">
                    <div class="designer-logo">
                        <img src="${picture_base}${(designer.headPic)!}!60"/>
                    </div>
                    <div class="designer-signature"
                         style="background:url(${picture_base}${(designer.sign.path)!}) no-repeat;"></div>
                </div>
                <div class="designer-follow">
                    <a href="${base}/member/interest/attention/insert/${designer.id}" class="op-attention"
                       data-id="${designer.id}"><em></em>关注(<strong>${designer.fans}</strong>)</a>
                </div>
                <div class="designer-slogan">
                    <div class="designer-name"><a href="${base}/showroom/designer/${designer.id}" target="_blank"
                                                  title="点击打开设计师主页">${designer.name}</a></div>
                    <div class="designer-desc">${designer.slogan}</div>
                </div>
            </div>
        </div>
        <div class="designer-home"><a href="/showroom/designer/${designer.id}" target="_blank"
                                      class="link-home"><em></em>浏览更多作品</a></div>
        <#if series >
            <div class="series-sorts">
                <h3>作品系列</h3>
                <ul>
                    <#list series as sery>
                        <li><em></em><a href="${base}/showroom/lookbook/${designer.id}?seriesId=${sery.id}"
                                        class="lookbook-series">${sery.name}</a></li>
                    </#list>
                </ul>
            </div>
        </#if>
    </div>
    <div class="lookbook-right">
        <ul class="lookbook-imgs">
            <#if  pager.totalCount &gt; 0 >
                <#list pager.list as dc>
                    <li>
                        <a href="javascript:" data-id="${dc.id}" data-url="${picture_base}"
                           class="lookbook-item-img"><img
                                    src="${picture_base}${(dc.firstCompositionPictrue)!}_265"/></a>
                        <div class="pro-item-btns">
                            <#if dc.productId><a href="/product/buy/${dc.productId}" class="op-want" data-id="${dc.id}">
                                    <em></em>我想买</a></#if>
                        </div>
                    </li>
                </#list>
            </#if>

        </ul>
    </div>
    <div class="clearfix"></div>
</div>
