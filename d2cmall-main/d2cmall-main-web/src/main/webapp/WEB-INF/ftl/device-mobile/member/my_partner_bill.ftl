<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='售货明细' js='' css='partner' service="false" />
<share data-title="${partnerStore.name}" data-url="${base}/partner/store?parent_id=${partnerStore.partnerId}"
       data-pic="${partnerStore.pic}"></share>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">售货明细</div>
    </div>
</header>
<div id="tab-wraper"></div>
<div id="partner-list"></div>
<div class="no-data-page">
    <img src="//static.d2c.cn//img/nnm/npc/error.png" width="100%">
    <p>暂无数据</p>
</div>
<div class="last-page">已经到最低啦</div>
<script id="tab-template" type="text/html">
    <div class="tab">
        {{each list as value i}}
        <a href="javascript:" data-index="{{value.index}}">{{value.name}}</a>
        {{/each}}
    </div>
</script>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="partner-item">
        <div class="item-container">
            <div class="flex space-between">
                <span>订单编号：{{value.orderSn}}</span>
                <span style="color:#09BB07;">{{value.appStatusName}}</span>
            </div>
            <div class="flex space-between item-gap">
                <div class="item-img"><img src="//img.d2c.cn{{value.productImg}}" width="100%"
                                           alt="{{value.productName}}"></div>
                <div class="item-cont">
                    <div class="item-title">{{value.productName}}</div>
                    <div class="item-info">
                        <p>购买人：{{telHide(value.buyerMemberName)}}</p>
                        <p style="color:rgba(0,0,0,.54);margin-top:5px;">{{formatDate(value.createDate)}}</p>
                    </div>
                </div>
            </div>
            {{if value.deliveryCorpName}}
            <div class="flex space-between item-other">
                <span style="color:rgba(0,0,0,.54);">物流信息：</span>
                <span>{{value.deliveryCorpName}}&nbsp;公司自提<i
                            style="float:right;margin-top:.5em;width:1.2em;height:1.2em;"
                            class="icon icon-arrow-right"></i></span>
            </div>
            {{/if}}
            <div class="flex space-between item-other">
                <span style="color:rgba(0,0,0,.54);">实付：{{value.actualAmount}}</span>
                <span style="color:#F23365;">返利：
			        {{if ${m.LOGINMEMBER.partnerId} != value.partnerId}}
			        	{{formatPrice(value.actualAmount * value.parentRatio)}}
			        {{else}}
			        	{{formatPrice(value.actualAmount * value.partnerRatio)}}	
			        {{/if}}
			        </span>
            </div>
        </div>
    </div>
    {{/each}}
</script>
<script>
    var sectionArray = new Array(
        {'index': 0, 'name': '全部'},
        {'index': 1, 'name': '交易中'},
        {'index': 8, 'name': '已完成'},
        {'index': -1, 'name': '已关闭'}
    );
    var html = template('tab-template', {list: sectionArray});
    $('#tab-wraper').html(html);
    $('#tab-wraper a').on('click', function () {
        $(this).addClass('on').siblings().removeClass('on');
        var index = $(this).attr('data-index');
        var data = parseInt((index == 0) ? 0 : index);
        $.getJSON('/partner/bill?index=' + data, function (result) {
            if (result.partnerBill['list'] != '') {
                var html = template('list-template', result.partnerBill);
                $('#partner-list').html(html);
                $('.last-page').show();
                $('.no-data-page').hide();
            } else {
                $('#partner-list').html('');
                $('.last-page').hide();
                $('.no-data-page').show();
            }
        });
    });
    $('#tab-wraper a[data-index=0]').trigger('click');

    template.helper('telHide', function (str) {
        var reg = /^(\d{3})\d{4}(\d{4})$/;
        return str.replace(reg, "$1****$2");
    });
    template.helper('formatDate', function (str) {
        var date = new Date(str);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        if (month < 10) month = '0' + month;
        if (day < 10) day = '0' + day;
        if (hour < 10) hour = '0' + hour;
        if (minute < 10) minute = '0' + minute;
        if (second < 10) second = '0' + second;
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    });

    template.helper('formatPrice', function (num) {
        return num.toFixed(2);
    });
</script>
<@m.page_footer menu=false />