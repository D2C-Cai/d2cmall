<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='提现明细' js='utils/iscroll-lite' css='partner' service="false" />
<share data-title="${partnerStore.name}" data-url="${base}/partner/store?parent_id=${partnerStore.partnerId}"
       data-pic="${partnerStore.pic}"></share>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">我的提现</div>
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
        <a href="javascript:" data-index="{{value.status}}">{{value.name}}</a>
        {{/each}}
    </div>
</script>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="partner-item">
        <div class="item-container">
            <div class="flex flex-start margin-bottom">
                <div style="width:4.6em;height:4.6em;margin-right:5%;"><img
                            src="//static.d2c.cn/img/nnm/npc/icon_mine_cash@2x.png" width="100%"></div>
                <div class="item-detail">
                    <p style="font-size:1.2em;margin-bottom:.6em;"><strong>{{value.statusName}}</strong></p>
                    <p style="color:#9e9e9e;">申请编号：{{value.sn}}</p>
                    <p style="font-size:.75em;color:#c6c6c7;margin-top:.6em;">{{formatDate(value.createDate)}}</p>
                </div>
            </div>
            <div style="float:right;margin-top:-6em;"><span style="font-size:.75em;">&yen;&nbsp;</span><strong
                        style="font-size:1.2em;">{{value.applyAmount | formatPrice:'decimal'}}</strong></div>
            {{if value.statusName == '已支付'}}
            <p class="item-other">支付时间： &nbsp;{{formatDate(value.createDate)}}</p>
            <p class="item-other">支付流水： &nbsp;{{value.paySn}}</p>
            <p class="item-other">支付账户： &nbsp;{{value.applyAccount}}</p>
            {{/if}}
            {{if value.refuseReason}}<p class="cash-tips">拒绝原因：&nbsp;{{value.refuseReason}}</p>{{/if}}

        </div>
    </div>
    {{/each}}
</script>
<script>
    var sectionArray = new Array(
        {'status': 0, 'name': '全部'},
        {'status': 1, 'name': '未支付'},
        {'status': 8, 'name': '已支付'},
        {'status': -1, 'name': '拒绝'}
    );
    var html = template('tab-template', {list: sectionArray});
    $('#tab-wraper').html(html);
    $('#tab-wraper a').on('click', function () {
        $(this).addClass('on').siblings().removeClass('on');
        var index = $(this).attr('data-index');
        var data;
        if (index == 0) {
            data = {};
        } else {
            data = {status: index};
        }
        $.getJSON('/partner/cash', data, function (result) {
            if (result.partnerCash.list != '') {
                var html = template('list-template', result.partnerCash);
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
    template.helper('formatDate', function (str) {
        var date = new Date(str);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hour = date.getHours();
        var minute = date.getMinutes();
        var second = date.getSeconds();
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    });
    template.helper('formatPrice', function (price, type) {
        if (price) {
            var arrayPrice = price.toString().split(".");
            if (type == 'integer') {
                return arrayPrice[0] ? arrayPrice[0] : "0";
            } else if (type == 'decimal') {
                return arrayPrice[0] + (arrayPrice[1] ? arrayPrice[1].length == 1 ? "." + arrayPrice[1] + "0" : "." + arrayPrice[1] : ".00");
            }
        } else {
            if (type == 'integer') {
                return "0";
            } else if (type == 'decimal') {
                return ".00";
            }
        }
    });
</script>
    </script>
<@m.page_footer menu=false />