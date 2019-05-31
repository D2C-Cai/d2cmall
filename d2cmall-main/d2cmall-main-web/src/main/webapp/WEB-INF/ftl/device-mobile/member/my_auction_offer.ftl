<#import "templates/public_mobile.ftl" as m>
<@m.page_header back="我的拍卖" title='我的拍卖' service='false' hastopfix='false'/>
<!--
<div class="section">
	<div class="tab-holder">
        <div class="tab tab-suspend">
            <a href="/auction/member/mymargin">保证金记录</a>
            <a href="/auction/member/myoffer" class="on">出价记录</a>
        </div>
	</div>
	<table class="table offer-table" style="width:100%;padding:0 10px;margin-top:10px">
	<tbody>
 	 <trclass="item">
   		 <th width="28%">拍卖商品</th>
   		 <th width="28%">出价</th>
   		 <th width="28%">时间</th>
   		 <th width="16%">状态</th>
  	</tr>
  	<#if pager.list?exists && pager.list?size gt 0>
		<#list pager.list as list>
			<tr class="item">	
				<td><a href="/auction/product/${list.auctionId}" style="color:#444"><#if list.auctionTitle?length gt 24>${list.auctionTitle?substring(0,24)}<#else>${list.auctionTitle}</#if></a></td>
				<td><strong>&yen;${list.offer?string("currency")?substring(1)}</strong></td>
				<td>${list.createDate?string("HH:mm:ss MM/dd")}</td>
				<td <#if list.status==1 || list.status==8>style="color:#fd555d"</#if>><#if list.status==1>领先<#elseif list.status==0>出局<#elseif list.status==8>得标<#else>删除</#if></td>
			</tr>
		</#list>
  	</#if>
	</tbody>
</table>
<div class="pages" style="margin:0">
		<@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
	</div>
</div>
-->
<@m.page_footer />