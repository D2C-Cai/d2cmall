<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='今日数据' js='utils/iscroll-lite' css='iconfont' service="false" />
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">数据中心</div>
    </div>
</header>
<div id="summary-list"></div>
<script id="list-template" type="text/html">
    <div class="data-mask">访客数据将在1月26日开放</div>
    <div class="summary-datas">
        <strong>近7日访客</strong>
        <div class="data-box">
            <div class="data-item" style="border-right:1px solid #EEE;">
                <p style="font-size:0.8em;color:#999;">今日访客</p>
                <p><strong>0人次</strong></p>
            </div>
            <div class="data-item" style="border-right:1px solid #EEE;">
                <p style="font-size:0.8em;color:#999;">昨日访客</p>
                <p><strong>0人次</strong></p>
            </div>
            <div class="data-item">
                <p style="font-size:0.8em;color:#999;">访客列表</p>
                <p><strong>0人次</strong></p>
            </div>
        </div>
    </div>
    <div class="summary-datas">
        <strong>今日数据</strong>
        <div class="data-box">
            <div class="data-item" style="border-right:1px solid #EEE;">
                <p style="font-size:0.8em;color:#999;">今日订单</p>
                <p><strong>{{todayData.count}}单</strong></p>
            </div>
            <div class="data-item" style="border-right:1px solid #EEE;">
                <p style="font-size:0.8em;color:#999;">今日收益</p>
                <p><strong>{{formatPrice(todayData.rebate)}}元</strong></p>
            </div>
            <div class="data-item">
                <p style="font-size:0.8em;color:#999;">今日销售额</p>
                <p><strong>{{formatPrice(todayData.amount)}}元</strong></p>
            </div>
        </div>
    </div>
    <div class="summary-datas">
        <strong>数据概览</strong>
        <div class="data-box">
            <div class="data-item" style="border-right:1px solid #EEE;">
                <p style="font-size:0.8em;color:#999;">订单总数</p>
                <p><strong>{{allData.count}}单</strong></p>
            </div>
            <div class="data-item" style="border-right:1px solid #EEE;">
                <p style="font-size:0.8em;color:#999;">收益总数</p>
                <p><strong>{{formatPrice(allData.rebate)}}元</strong></p>
            </div>
            <div class="data-item">
                <p style="font-size:0.8em;color:#999;">销售额总数</p>
                <p><strong>{{formatPrice(allData.amount)}}元</strong></p>
            </div>
        </div>
    </div>
</script>
<script>
    $.getJSON('/partner/summary', function (data) {
        var val = data.result.datas;
        var html = template('list-template', val);
        $('#summary-list').append(html);
    });
    template.helper('formatPrice', function (num) {
        return num.toFixed(2);
    });
</script>
<@m.page_footer menu=false />