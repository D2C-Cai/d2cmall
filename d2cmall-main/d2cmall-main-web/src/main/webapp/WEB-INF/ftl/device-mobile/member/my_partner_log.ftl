<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='账户明细' js='utils/iscroll-lite' css='partner' service="false" />
<share data-title="${partnerStore.name}" data-url="${base}/partner/store?parent_id=${partnerStore.partnerId}"
       data-pic="${partnerStore.pic}"></share>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">账户明细</div>
    </div>
</header>
<div id="partner-list"></div>
<div class="no-data-page">
    <img src="//static.d2c.cn//img/nnm/npc/error.png" width="100%">
    <p>暂无数据</p>
</div>
<div class="last-page">已经到最低啦</div>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="partner-item">
        <div class="item-container">
            <div class="flex flex-start">
                <div class="item-img">
                    {{if value.sourceType == 'BILL'}}
                    <img src="//static.d2c.cn/img/nnm/npc/icon_mine_back@2x.png" width="100%">
                    {{else}}
                    <img src="//static.d2c.cn/img/nnm/npc/icon_mine_cash@2x.png" width="100%">
                    {{/if}}
                </div>
                <div class="item-detail">
                    <p style="font-size:1.2em;margin-bottom:.6em;"><strong>{{if value.sourceType ==
                            'BILL'}}返利单{{else}}提现申请{{/if}}{{value.statusName}}</strong></p>
                    <p style="font-size:.75em;color:#9e9e9e;">返利单号：{{value.sourceSn}}</p>
                    <p style="font-size:.75em;color:#c6c6c7;margin-top:.6em;">{{formatDate(value.createDate)}}</p>
                </div>
            </div>
            <div style="float:right;margin-top:-4.5em;"><span style="font-size:.75em;">&yen;&nbsp;</span><strong
                        style="font-size:1.2em;">{{value.amount}}</strong></div>
        </div>
    </div>
    {{/each}}
</script>
<script>
    $.getJSON('/partner/log', function (data) {
        if (data.partnerLog.list != '') {
            var html = template('list-template', data.partnerLog);
            $('#partner-list').html(html);
            $('.last-page').show();
            $('.no-data-page').hide();
        } else {
            $('#partner-list').html('');
            $('.last-page').hide();
            $('.no-data-page').show();
        }
    });
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
</script>
<@m.page_footer menu=false />