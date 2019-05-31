<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='设计师订单查询' noheader=true />
<div class="user-header-bar">
    <div class="bar-con">
        <a href="/member/home" class="back"><span class="icon icon-chevron-left"> 销售订单</span></a>
        <a href="javascript:location.reload();" class="refresh-btn"><span class="icon icon-refresh"></span></a>
        <!-- <a href="javascript:" class="icon icon-search bar-search"></a>-->
    </div>
    <div class="search-bar"
         style="position:absolute;background:#FFF;box-shadow:0 0.1em 0.4em #CCC;display:none;width:100%;top:2.8em;">
        <form id="form-order" name="form_order" method="get" action="/member/order">
            <input type="hidden" name="type" value="1"/>
            <input type="search" name="productName" placeholder="搜索商品名称或订单号" value=""/>
            <button type="submit" name="">搜索</button>
        </form>
    </div>
</div>
<div style="height:2.9em;"></div>
<div class="order-status-bar">
    已售出<span class="small">(${WaitingForComfirm+WaitingForDelivery+Delivered})</span> &nbsp;&nbsp;
    <a href="/seller/order?orderStatus=WaitingForPay">待确认<span class="small">(${WaitingForComfirm})</span></a> &nbsp;&nbsp;
    <a href="/seller/order?orderStatus=WaitingForDelivery,WaitingForComfirm">待发货<span
                class="small">(${WaitingForDelivery})</span></a>&nbsp;&nbsp;
    <a href="/seller/order?orderStatus=Delivered">已发货<span class="small">(${Delivered})</span></a>
</div>
<div class="wrap-main">
    <#if (orderItems?size > 0)>
        <#list orderItems as orderItem>
            <div class="order-item wrap-div" style="margin-bottom:0.6em;">
                <div class="item-main clearfix">
                    <a href="javascript:">
                        <p class="item-top">下单时间：${orderItem.createDate?string("yyyy/MM/dd HH:mm:ss")}</p>
                        <p class="img-big">
                            <img src="<#if orderItem?exists>${picture_base}/${orderItem.productImg}!80</#if>"
                                 style="min-height:5em;" alt="${orderItem.productName}"/>
                            <span class="status"><#if orderItem.status=='DELIVERED'>
                                    已发货
                                <#elseif orderItem.status=='NORMAL'>
                                    等待发货
                                <#else>
                                    已关闭</#if>
				</span>
                        </p>
                        <p class="title-bar" style="height:4em;">${orderItem.productName}</p>
                    </a>
                    <p class="op-bar" style="font-size:0.8em;"><span
                                class="float-left">共${orderItem.productQuantity}件</span> <strong
                                class="price float-right">订单金额：&yen; ${(orderItem.totalPrice-orderItem.promotionAmount)?string("currency")?substring(1)}</strong>
                    </p>
                </div>
            </div>
        </#list>
    </#if>
    <#if pager.pageCount gt 1>
        <div id="load-more" class="load-more" data-page="${pager.pageNumber}" data-total="${pager.pageCount}">点击加载更多
        </div>
    </#if>

</div>
<hr/>
<script>
    $(window).bind("scroll", function () {
        if ($('#load-more').size() > 0) {
            var page = parseInt($('#load-more').attr('data-page')),
                total = parseInt($('#load-more').attr('data-total'));
            if (page < total) {
                if ($(window).scrollTop() + $(window).height() >= $(document).height()) {
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
        $('#load-more').html('<i class="icon-spinner icon-spin"></i> 正在加载...');
        $.ajax({
            url: '?p=' + (page + 1) + '&' + new Date().getTime(),
            dataType: 'json',
            type: 'GET',
            success: function (data) {
                var str = '';
                $.each(data.orderItems, function (i, d) {
                    str += '<div class="order-item wrap-div" style="margin-bottom:0.6em;">\
					<div class="item-main clearfix">\
						<a href="javascript:">\
						<p class="item-top">下单时间：' + format_date(d.createDate) + '</p>\
						<p class="img-big">\
							<img src="${picture_base}/' + d.productSKU.product.productImageCover + '!80" style="min-height:5em;" alt="' + d.productName + '" />\
							<span class="status">';
                    if (d.status == 'DELIVERED') {
                        str += '已发货';
                    } else if (d.status == 'NORMAL') {
                        str += '等待发货';
                    } else {
                        str += '已关闭';
                    }
                    str += '</span>\
						</p>\
						<p class="title-bar" style="height:4em;">' + d.productName + '</p>\
						</a>\
						<p class="op-bar" style="font-size:0.8em;"><span class="float-left">共' + d.productQuantity + '件</span> <strong class="price float-right">订单金额：&yen; ' + format_currency(d.totalPrice - d.promotionAmount) + '</strong></p>\
					</div>\
				</div>';
                });
                $('#load-more').before(str);
                $(".lazyload img").unveil(300);
                $('#load-more').attr('data-page', page + 1).text('点击加载更多');
            }
        });
    }
</script>
<@m.page_footer />