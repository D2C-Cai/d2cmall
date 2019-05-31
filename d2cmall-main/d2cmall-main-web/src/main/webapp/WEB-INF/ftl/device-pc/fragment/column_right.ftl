<!--<div class="shadow-10" style="margin-bottom:15px;">
				<a href="/activity/dcs"><img src="${static_base}/images/pjh.jpg" alt="品鉴会" /></a>
			</div>
			-->
<div class="shadow-10 column-cont">
    <div class="tab-menu">
        <h3>公告与动态</h3>
    </div>
    <div class="tab-cont">
        <div class="tab-cont-data" data-id="notice">
            <div class="news-h1">
                <#if article?exists>
                    <a href="/news/${(article.id)!}" target="_blank"><img src="${(article.picture)!}" width="300"
                                                                          alt="${(article.title)!}"/></a>
                    <p style="margin-top:10px;"><a href="/news/${article.id}" target="_blank">${(article.title)!}</a>
                    </p>
                </#if>
            </div>
            <div class="news-list">
                <#if articleList?exists>
                    <#list articleList as article>
                        <p><a href="/news/${article.id}" target="_blank">${article.title}</a></p>
                    </#list>
                </#if>
            </div>
        </div>

    </div>
</div>

<div class="shadow-10 column-cont">
    <div class="tab-menu">
        <h3>服务承诺</h3>
    </div>
    <div class="tab-cont-data">
        <div class="service-promise">
            <img src="${static_base}/images/promise_2.png" alt="中国领先设计师平台"/>
        </div>
        <div class="service-promise">
            <img src="${static_base}/images/promise_1.png" alt="100%正品保证"/>
        </div>
        <div class="service-promise">
            <img src="${static_base}/images/promise_5.png" alt="7天无理由退货"/>
        </div>
        <div class="service-promise">
            <img src="${static_base}/images/promise_4.png" alt="满299顺丰包邮"/>
        </div>

        <div class="service-promise">
            <img src="${static_base}/images/promise_3.png" alt="精美包装"/>
        </div>
    </div>
</div>