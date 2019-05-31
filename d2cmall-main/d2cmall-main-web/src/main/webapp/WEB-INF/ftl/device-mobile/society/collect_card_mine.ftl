<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='奖励记录' css="cardcollect"  />
<div class="card-container"
     style="position:relative;overflow:hidden;height:100vh;background:linear-gradient(180deg,#230F06 0%,#281110 100%);background: -webkit-linear-gradient(180deg,#230F06 0%,#281110 100%);background: -o-linear-gradient(180deg,#230F06 0%,#281110 100%);background: -moz-linear-gradient(180deg,#230F06 0%,#281110 100%);">
    <img src="//static.d2c.cn/img/topic/190428/520/hb_bg_jiaobiao.png" width="46px" height="46px" alt=""
         style="position:absolute;top:0;left:14px;z-index:8;">
    <div class="myreward">
        <div class="card-tab"></div>
        <div style="height:40px;"></div>
        <div class="card-info"></div>
    </div>

</div>


<script id="tab-template" type="text/html">
    {{each list as value i}}
    <div class="tab-element" data-index="{{i}}" data-url="{{value.url}}" data-type="{{value.type}}">{{value.name}}</div>
    {{/each}}
</script>

<script id="award-template" type="text/html">
    {{if list.length > 0}}
    {{each list as value i}}
    <div class="info-items">
        <span style="font-size:14px;color:#793F16;">{{value.awardName}}</span>
        <span style="font-size:12px;color:rgba(121,63,22,.5);">{{formatTime(value.createDate)}}</span>
    </div>
    {{/each}}
    {{else}}
    <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);font-size:14px;color:#b2b2b2;">暂无数据~
    </div>
    {{/if}}
</script>

<script id="card-template" type="text/html">
    {{if list.length > 0}}
    {{each list as value i}}
    <div class="info-items">
        <div style="font-size:12px;color:#793F16;">
            {{if value.fromName !=null}}
            {{value.fromName | stringFunc}}</span>帮你抽到一张{{value.defName}}
            {{else}}
            自己抽到一张{{value.defName}}
            {{/if}}
        </div>
        <div style="font-size:12px;color:rgba(121,63,22,.5);">{{formatTime(value.createDate)}}</div>
    </div>
    {{/each}}
    {{else}}
    <div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);font-size:14px;color:#b2b2b2;">暂无数据~
    </div>
    {{/if}}
</script>

<script>
    $('body').css('background', '#fff');
    var tabArr = [
        {
            'name': '我的奖品',
            'url': '/collection/card/award',
            'type': 'myAward'
        },
        {
            'name': '好友集卡',
            'url': '/collection/card/my'
        }
    ];
    var tabHtml = template('tab-template', {list: tabArr});
    $('.card-tab').html(tabHtml);
    $('.card-tab .tab-element').on('click', function () {
        var obj = $(this),
            url = obj.attr('data-url'),
            type = obj.attr('data-type');
        obj.addClass('selected').siblings().removeClass('selected');
        $.getJSON(url, function (data) {
            if (type == 'myAward') {
                var html = template('award-template', {list: data.awards});
            } else {
                var html = template('card-template', {list: data.cardArray});
            }
            $('.card-info').html(html);
        });
    });
    $('.card-tab .tab-element[data-index=0]').trigger('click');

    //返回正确格式时间   2018/05/23 17:21:00
    template.helper('formatTime', function (str) {
        var date = new Date(str);
        var year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate(),
            hour = date.getHours(),
            minute = date.getMinutes(),
            second = date.getSeconds();
        var t1 = [month, day].map(formatNumber).join('月');
        var t2 = [hour, minute].map(formatNumber).join(':');
        return t1 + '日' + ' ' + t2
    });

    function formatNumber(n) {
        var str = n.toString();
        return str[1] ? str : '0' + str
    }

    template.helper('stringFunc', function (str) {
        if (str.length > 6) {
            return str.slice(0, 7) + '...'
        } else {
            return str
        }
    });


</script>


<@m.page_footer />