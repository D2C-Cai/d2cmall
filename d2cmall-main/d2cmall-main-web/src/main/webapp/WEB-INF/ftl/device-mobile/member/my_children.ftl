<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='我的团队' js='utils/iscroll-lite' css='partner' service="false" />
<header>
    <div class="header fixed">
        <div class="header-back"><a href="javascript:history.back(1);" class="icon icon-back"></a></div>
        <div class="header-title">我的团队</div>
    </div>
</header>
<div class="child-invite">邀请好友加入</div>
<div id="partner-list"></div>
<div class="last-page">已经到最底啦</div>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="partner-item">
        <div class="item-container">
            <div class="flex flex-start">
                <div class="child-rank">{{i+1}}</div>
                <div style="width:4.6em;height:4.6em;"><img
                            src="{{if value.headPic}}{{value.headPic}}{{else}}//static.d2c.cn/img/home/160627/images/headpic.png{{/if}}"
                            style="width:100%;border-radius:50%;"></div>
                <div class="child-detail">
                    <p>{{value.name}}</p>
                    <p>总共创造了{{value.totalAmount}}元业绩</p>
                </div>
            </div>
        </div>
    </div>
    {{/each}}
</script>
<script>
    $.getJSON('/partner/children', function (data) {
        $('.last-page').show();
        var html = template('list-template', data.pager);
        $('#partner-list').html(html);
    });
    $('.child-invite').on(click_type, function () {
        jAlert('请到D2C-app或D2C小程序中邀请~');
    });
</script>
<@m.page_footer menu=false />