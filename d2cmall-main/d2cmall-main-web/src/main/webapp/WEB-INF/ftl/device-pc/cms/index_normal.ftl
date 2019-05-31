<@m.top_nav channel="index" />
<!--div style="position:fixed;left:50%;top:50%;margin-left:-600px;margin-top:-300px;z-index:99;box-shadow:0 0 5px #CCC;">
	<a href="javascript:" onclick="$(this).parent().slideUp();return false;" style="display:block;position:absolute;width:49px;height:47px;right:0;top:0;z-index:99">
	<img src="${static_base}/c/images/space.gif" width="49" height="47" /></a>
	<a href="/qixi" target="_blank" style="position:relative;z-index:1"><img src="//static.d2c.cn/img/home/0729/1200-600.jpg" alt="" /></a>
</div-->
<div class="float-search">
    <form name="" action="/product/list" method="get" target="_blank">
        <input type="text" name="k" id="header-keyword" class="place-holder-keyword" value=""/>
        <button type="submit" id="float-search"><a href="javascript:" class="search-icon"><i
                        class="fa fa-search"></i></a></button>
    </form>
</div>
<div class="banner-home" data-spam="1.1" data-pagetype="index">
    <#if pageDefine.fieldDefs.block01.type==0 >
        <div class="banner" id="banner-home">
            <ul>
                ${(homePage.block01)!}
            </ul>
        </div>
    <#else>

        <div class="banner" id="banner-h">
            <ul>
                <#if homePage.blocks.block01?size gt 0>
                    <#list homePage.blocks.block01 as block>
                        <li data-title="${(block.title)!}" <#if block.margin!=''> style="background:${(block.margin)!}"</#if>>
                            <a href="${(block.bindUrl)!}" target="_blank"><img
                                        src="${picture_base}${(block.frontPic)!}!/strip/true" alt="${(block.title)!}"/></a>
                        </li>
                    </#list>
                </#if>
            </ul>
            <dl>
                <dd>
                    <a class="slider-arrow prev" href="javascript:">&lt;</a>
                    <a class="slider-arrow next" href="javascript:">&gt;</a>
                </dd>
            </dl>
        </div>
    </#if>
</div>
<div class="layout layout-response container lazyload">
    <#if pageDefine.fieldDefs.block03.status==1>
        <div class="row small-banner">
            <#if pageDefine.fieldDefs.block03.type==0>
                <#if homePage.block03?exists>
                    ${homePage.block03?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                </#if>
            <#else>
                <#list homePage.blocks.block03 as block>
                    <#if block_index == 0 >
                        <div class="col-5">
                            <a href="${(block.bindUrl)!}" target="_blank" class="hover-img-one"><img
                                        src="${static_base}/c/images/space.gif"
                                        data-image="${picture_base}${(block.frontPic)!}"/><#if block.endPic!=null>
                            <img width="${(block.width)!}" class="hover-img-two" height="${(block.height)!}"
                                 alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                                 data-image="${picture_base}${(block.endPic)!}!/strip/true"/>
                                </#if></a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index==4>hidden-two<#elseif block_index==3>hidden-one</#if>">
                            <a href="${(block.bindUrl)!}" target="_blank" class="hover-img-one"><img
                                        src="${static_base}/c/images/space.gif"
                                        data-image="${picture_base}${(block.frontPic)!}"/><#if block.endPic!=null>
                            <img width="${(block.width)!}" class="hover-img-two" height="${(block.height)!}"
                                 alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                                 data-image="${picture_base}${(block.endPic)!}!/strip/true"/>
                                </#if></a>
                        </div>
                    </#if>
                </#list>
            </#if>
        </div>
    </#if>

    <div class="suspend-navs suspend-navs-home scroll-suspend" data-anchor="true" data-offset="30">
        <a href="#navs-active">热卖</a>
        <a href="#navs-promotion">活动</a>
        <a href="#navs-starstyle">明星</a>
        <a href="#navs-cloth-woman">女士</a>
        <a href="#navs-cloth-man">男士</a>
        <a href="#navs-cloth-child">童装</a>
        <a href="#navs-shoes">鞋履</a>
        <a href="#navs-acc">配饰</a>
        <a href="#navs-bags">包袋</a>
        <a href="#navs-household">家居</a>
    </div>

    <!--设计师新品-->
    <div class="title clearfix"><img src="//static.d2c.cn/img/home/160627/images/designer.png" border="0"/></div>
    <div class="row">
        <#if pageDefine.fieldDefs.block07.status==1>
            <#if pageDefine.fieldDefs.block07.type==0>
                <#if homePage.block07?exists>
                    ${homePage.block07?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                </#if>
            <#else>
                <#list homePage.blocks.block07 as block>
                    <div class="float-with-hover col-4 fade-out <#if block_index==4 || block_index==9>hidden-two<#elseif block_index==5 || block_index==10>hidden-one</#if>">
                        <a href="${(block.bindUrl)!}" target="_blank"><img alt="${(block.title)!}"
                                                                           src="${static_base}/c/images/space.gif"
                                                                           data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                                                           height="315" width="215"/></a>
                        <#if block.endPic!=null>
                            <div class="float-with-btn"><a href="${(block.bindUrl)!}" target="_blank"><img
                                            width="${(block.width)!}" height="${(block.height)!}"
                                            alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                                            data-image="${picture_base}${(block.endPic)!}!/strip/true"/></a></div>
                        </#if>
                    </div>
                </#list>
            </#if>
        </#if>
    </div>

    <!--热卖单品-->
    <!--<div class="title clearfix" id="navs-active"><img src="//static.d2c.cn/img/home/160627/images/hot.png" width="645" height="50"/></div>
    <div class="row" data-spam="1.4">	
    <#if  pageDefine.fieldDefs.block05.type==0 >
        <#if homePage.block05?exists>
        ${homePage.block05?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
        </#if>
    <#else>
        <#list homePage.blocks.block05 as block >
        <div class="col-4">
            <a href="${(block.bindUrl)!}" target="_blank" class="hoverbg"><img alt="${(block.title)!}" src="${static_base}/c/images/space.gif"  data-image="${picture_base}${(block.frontPic)!}!/strip/true" height="335" width="215"/></a>
        </div>
    </#list>
    </#if>
    </div>-->

    <!--活动区-->
    <div class="title clearfix" id="navs-promotion"><img src="//static.d2c.cn/img/home/160627/images/promotion.png"
                                                         border="0"/></div>
    <div class="row">
        <#if pageDefine.fieldDefs.block10.status==1>
            <#if pageDefine.fieldDefs.block10.type==0>
                <#if homePage.block10?exists>
                    ${homePage.block10?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                </#if>
            <#else>
                <#list homePage.blocks.block10 as block>
                    <div class="col-activity fade-out">
                        <a href="${(block.bindUrl)!}" target="_blank"><img alt="${(block.title)!}"
                                                                           src="${static_base}/c/images/space.gif"
                                                                           data-image="${picture_base}${(block.frontPic)!}!/both/765x335"
                                                                           height="335" width="765"/></a>
                    </div>
                </#list>
            </#if>
        </#if>
    </div>

    <!--明星风范1-->
    <div class="title clearfix" id="navs-starstyle"><img src="//static.d2c.cn/img/home/160627/images/star.png"
                                                         border="0"/></div>
    <div class="row">
        <#--<#if pageDefine.fieldDefs.block13.status==1>
            <#if pageDefine.fieldDefs.block13.type==0>
                <#if homePage.block13?exists>
            ${homePage.block13?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                </#if>
            <#else>
                <#list homePage.blocks.block13 as block>
            <div class="col-4 fade-out <#if block_index==6 || block_index==13>hidden-one<#elseif block_index==5 || block_index==12>hidden-two</#if>">
                <a href="${(block.bindUrl)!}" target="_blank"><img alt="${(block.title)!}" src="${static_base}/c/images/space.gif"  data-image="${picture_base}${(block.frontPic)!}!/strip/true" height="340" width="215"/></a>
                <#if block.endPic!=null>
                <div class="float-no-btn"><a href="${(block.bindUrl)!}" target="_blank"><img width="${(block.width)!}" height="${(block.height)!}" alt="${(block.title)!}" src="${static_base}/c/images/space.gif" data-image="${picture_base}${(block.endPic)!}!/strip/true"/></a></div>
                </#if>
            </div>
                </#list>
            </#if>
        </#if>-->
    </div>
    <!--明星风范2-->
    <div class="row">
        <#if pageDefine.fieldDefs.block15.status==1>
            <#if pageDefine.fieldDefs.block15.type==0>
                <#if homePage.block15?exists>
                    ${homePage.block15?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                </#if>
            <#else>
                <div class="starshow">
                    <a href="${(homePage.blocks.block15[1].bindUrl)!}" target="_blank" class="star-hover-img"><img
                                src="${picture_base}${(homePage.blocks.block15[1].frontPic)!}!/strip/true"
                                alt="${(block.title)!}" class="pic-box" width="580" height="320"/></a>
                    <ul>
                        <#list homePage.blocks.block15 as block>
                            <#if block_index lt 10>
                                <li><a href="${(block.bindUrl)!}" target="_blank"
                                       data-rel="${picture_base}${(block.frontPic)!}!/strip/true"><img
                                                src="${static_base}/c/images/space.gif"
                                                data-image="${picture_base}${(block.endPic)!}!/strip/true" width="93"
                                                height="64"/></a></li>
                            </#if>
                        </#list>
                    </ul>
                </div>
                <div class="starshow">
                    <a href="${(homePage.blocks.block15[11].bindUrl)!}" target="_blank" class="star-hover-img"><img
                                src="${picture_base}${(homePage.blocks.block15[11].frontPic)!}!/strip/true"
                                alt="${(block.title)!}" class="pic-box" width="580" height="320"/></a>
                    <ul>
                        <#list homePage.blocks.block15 as block>
                            <#if block_index  gt 9>
                                <li><a href="${(block.bindUrl)!}" target="_blank"
                                       data-rel="${picture_base}${(block.frontPic)!}!/strip/true"><img
                                                src="${static_base}/c/images/space.gif"
                                                data-image="${picture_base}${(block.endPic)!}!/strip/true" width="93"
                                                height="64"/></a></li>
                            </#if>
                        </#list>
                    </ul>
                </div>
            </#if>
        </#if>
    </div>

    <!--女士-->
    <div class="clear" id="navs-cloth-woman"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?t=1"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/women.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/home/160627/images/bg-woman.png) no-repeat ">
            <div class="small-navs">
                <#if pageDefine.fieldDefs.block28.status==1>
                    <#if pageDefine.fieldDefs.block28.type==0>
                        <#if homePage.block28?exists>
                            ${homePage.block28?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block19.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block19[0].frontPic!=null && homePage.blocks.block19[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block19[0].bindUrl)!}" target="_blank" class="${className} hoverbg">
                <img alt="${(homePage.blocks.block19[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block19[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block19[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block19[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block19?size gt 0>
            <#list homePage.blocks.block19 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10 || block_index==17>hidden-one<#elseif block_index==3 || block_index==9 || block_index==16>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--男士-->
    <div class="clear" id="navs-cloth-man"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?t=6"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/men.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/home/160627/images/bg-men.png) no-repeat">
            <div class="small-navs ">
                <#if pageDefine.fieldDefs.block29.status==1>
                    <#if pageDefine.fieldDefs.block29.type==0>
                        <#if homePage.block29?exists>
                            ${homePage.block29?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block21.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block21[0].frontPic!=null && homePage.blocks.block21[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block21[0].bindUrl)!}" target="_blank" class="${className} hoverbg">
                <img alt="${(homePage.blocks.block21[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block21[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block21[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block21[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block21?size gt 0>
            <#list homePage.blocks.block21 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10>hidden-one<#elseif block_index==3 || block_index==9>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--儿童-->
    <div class="clear" id="navs-cloth-child"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?t=7"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/kid.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/home/160627/images/bag-kid.png) no-repeat">
            <div class="small-navs ">
                <#if pageDefine.fieldDefs.block36.status==1>
                    <#if pageDefine.fieldDefs.block36.type==0>
                        <#if homePage.block36?exists>
                            ${homePage.block36?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block21.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block21[0].frontPic!=null && homePage.blocks.block27[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block27[0].bindUrl)!}" target="_blank" class="${className} hoverbg">
                <img alt="${(homePage.blocks.block27[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block27[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block27[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block27[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block27?size gt 0>
            <#list homePage.blocks.block27 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10>hidden-one<#elseif block_index==3 || block_index==9>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--鞋屡-->
    <div class="clear" id="navs-shoes"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?c=1652&t=2"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/shoes.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/home/160627/images/bg-shoes.png) no-repeat">
            <div class="small-navs ">
                <#if pageDefine.fieldDefs.block30.status==1>
                    <#if pageDefine.fieldDefs.block30.type==0>
                        <#if homePage.block30?exists>
                            ${homePage.block30?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block23.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block23[0].frontPic!=null && homePage.blocks.block23[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block23[0].bindUrl)!}" target="_blank" class="${className}">
                <img alt="${(homePage.blocks.block23[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block23[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block23[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block23[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block23?size gt 0>
            <#list homePage.blocks.block23 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10>hidden-one<#elseif block_index==3 || block_index==9>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--配饰-->
    <div class="clear" id="navs-acc"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?t=3"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/bg-accessories1.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/nnm/npc/bg-accessories.png) no-repeat">
            <div class="small-navs ">
                <#if pageDefine.fieldDefs.block31.status==1>
                    <#if pageDefine.fieldDefs.block31.type==0>
                        <#if homePage.block31?exists>
                            ${homePage.block31?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block24.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block24[0].frontPic!=null && homePage.blocks.block19[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block24[0].bindUrl)!}" target="_blank" class="${className}">
                <img alt="${(homePage.blocks.block24[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block24[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block24[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block24[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block24?size gt 0>
            <#list homePage.blocks.block24 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10>hidden-one<#elseif block_index==3 || block_index==9>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--包袋-->
    <div class="clear" id="navs-bags"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?t=2"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/bags.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/home/160627/images/bg-bags.png) no-repeat">
            <div class="small-navs ">
                <#if pageDefine.fieldDefs.block32.status==1>
                    <#if pageDefine.fieldDefs.block32.type==0>
                        <#if homePage.block32?exists>
                            ${homePage.block32?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block25.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block25[0].frontPic!=null && homePage.blocks.block25[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block25[0].bindUrl)!}" target="_blank" class="${className} hoverbg">
                <img alt="${(homePage.blocks.block25[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block25[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block25[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block25[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block25?size gt 0>
            <#list homePage.blocks.block25 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10>hidden-one<#elseif block_index==3 || block_index==9>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--家居-->
    <div class="clear" id="navs-household"></div>
    <div class="clearfix title" style="margin-top:55px"><a href="//www.d2cmall.com/product/list?t=4"
                                                           target="_blank"><img
                    src="//static.d2c.cn/img/home/160627/images/house.png" border="0"/></a></div>
    <div class="row row-navs">
        <div class="col-3 men" style="background:url(//static.d2c.cn/img/home/160627/images/bg-home.png) no-repeat">
            <div class="small-navs ">
                <#if pageDefine.fieldDefs.block35.status==1>
                    <#if pageDefine.fieldDefs.block35.type==0>
                        <#if homePage.block35?exists>
                            ${homePage.block35?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                        </#if>
                    </#if>
                </#if>
            </div>
        </div>
        <#if pageDefine.fieldDefs.block26.status==1>
        <div class="float-left col-9">
            <#assign className="hoverbg"/>
            <#if homePage.blocks.block26[0].frontPic!=null && homePage.blocks.block26[0].endPic!=null>
                <#assign className="hover-fade"/>
            </#if>
            <a href="${(homePage.blocks.block26[0].bindUrl)!}" target="_blank" class="${className} hoverbg">
                <img alt="${(homePage.blocks.block26[0].title)!}" src="${static_base}/c/images/space.gif"
                     data-image="${picture_base}${(homePage.blocks.block26[0].frontPic)!}!/strip/true" width="490"
                     height="322"/>
                <#if homePage.blocks.block26[0].endPic!=null>
                    <img width="490" height="322" alt="${(block.title)!}" src="${static_base}/c/images/space.gif"
                         data-image="${picture_base}${(homePage.blocks.block26[0].endPic)!}!/strip/true"
                         class="hover-img"/>
                </#if>
            </a>
        </div>
        <#if homePage.blocks.block26?size gt 0>
            <#list homePage.blocks.block26 as block>
                <#if block_index gt 0>
                    <#if block_index == 5>
                        <div class="float-left col-8">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="435" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="435" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    <#else>
                        <div class="col-4 <#if block_index ==4 || block_index==10>hidden-one<#elseif block_index==3 || block_index==9>hidden-two</#if>">
                            <#assign className="hoverbg"/>
                            <#if block.frontPic!=null && block.endPic!=null>
                                <#assign className="hover-fade"/>
                            </#if>
                            <a alt="${(block.title)!}" class="${className} hoverbg" href="${(block.bindUrl)!}"
                               target="_blank">
                                <img src="${static_base}/c/images/space.gif"
                                     data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                     alt="${(block.title)!}" width="215" height="322"/>
                                <#if block.endPic!=null>
                                    <img width="315" height="322" alt="${(block.title)!}"
                                         src="${static_base}/c/images/space.gif"
                                         data-image="${picture_base}${(block.endPic)!}!/strip/true" class="hover-img"/>
                                </#if>
                            </a>
                        </div>
                    </#if>
                </#if>
            </#list>
        </#if>
    </div>
    </#if>

    <!--设计师LOGO-->
    <div class="title clearfix"><img src="//static.d2c.cn/img/home/160627/images/designer_brand.png" border="0"/></div>
    <div class="row clearfix">
        <#if pageDefine.fieldDefs.block17.status==1>
            <#if pageDefine.fieldDefs.block17.type==0>
                <#if homePage.block17?exists>
                    ${homePage.block17?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
                </#if>
            <#else>
                <#list homePage.blocks.block17 as block>
                    <div class="col-2 <#if block_index ==13 || block_index==27 || block_index==41 || block_index==55 || block_index==12 || block_index==26 || block_index==40 || block_index==54>hidden-one<#elseif block_index==11 || block_index==25 || block_index==39 || block_index==53 || block_index==10 || block_index==24 || block_index==38 || block_index==52>hidden-two</#if>">
                        <a href="${(block.bindUrl)!}" target="_blank" class="hoverbg"><img alt="${(block.title)!}"
                                                                                           src="${static_base}/c/images/space.gif"
                                                                                           data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                                                                           height="45"
                                                                                           width="105"/>${(block.title)!}
                        </a>
                    </div>
                </#list>
            </#if>
        </#if>
    </div>

    <!--实体店-->
    <#if pageDefine.fieldDefs.block16.status==1>
    <div class="space"></div>
    <div class="clearfix">
        <#if pageDefine.fieldDefs.block16.type==0>
            <#if homePage.block16?exists>
                ${homePage.block16?replace('src=','src="${static_base}/c/images/space.gif" data-image=')}
            </#if>
        <#else>
            <#list homePage.blocks.block16 as block>
                <div class="col-2 <#if block_index ==13 || block_index==27 || block_index==41 || block_index==55 || block_index==12 || block_index==26 || block_index==40 || block_index==54>hidden-one<#elseif block_index==11 || block_index==25 || block_index==39 || block_index==53 || block_index==10 || block_index==24 || block_index==38 || block_index==52>hidden-two</#if>">
                    <a href="${(block.bindUrl)!}" target="_blank"><img alt="${(block.title)!}"
                                                                       src="${static_base}/c/images/space.gif"
                                                                       data-image="${picture_base}${(block.frontPic)!}!/strip/true"
                                                                       height="45" width="105"/></a>
                </div>
            </#list>
        </#if>
        </#if>
    </div>
    <div class="space"></div>
</div>
<script>
    $('body').css('background', '#fff');

    var unslider = $('#banner-h').unslider({
            dots: true,
            fluid: true

        }),
        data = unslider.data('unslider');
    $('#banner-h').parent().find('.slider-arrow').click(function () {
        var fn = this.className.split(' ')[1];
        unslider.data('unslider')[fn]();
    });

    $('.index-designer li .logo').hover(function () {
        var img = $(this).attr('data-hover');
        var obj = $(this).parent().parent();
        var i = obj.index();
        var n = parseInt(i / 4);
        var style = '';
        style = ((n % 2) == 0) ? 'left:138px;' : 'right:138px;';
        if (obj.find('.hover-div').size() > 0) {
            obj.find('.hover-div').show();
        } else {
            obj.append('<div class="hover-div" style="' + style + '"><img src="' + img + '" alt="品牌" /></div>');
        }
    }, function () {
        var obj = $(this).parent().parent();
        obj.find('.hover-div').hide();
    });
</script>