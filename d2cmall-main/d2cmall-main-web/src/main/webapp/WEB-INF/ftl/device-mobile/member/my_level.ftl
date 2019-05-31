<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='我的等级'  service="point" hastopfix='false' />
<#assign level=member.datas/>

<style>
    .heightem {
        display: none
    }
</style>
<div class="page level-page">
    <div class="my-level">
        <div class="lev">
            我的等级 <#if level.memberDetail.level gt 0><i
                class="level-${level.memberDetail.level} level-icon"></i><#else> &nbsp;普通会员</#if>
        </div>
        <div class="lev-line">
            <div class="user-head">
                <img src="<#if level.memberInfo.headPic!=''><#if level.memberInfo.headPic?index_of('http')!=-1>${level.memberInfo.headPic}<#else>${picture_base}${level.memberInfo.headPic}</#if><#else>//static.d2c.cn/common/nm/img/_0001_58.png</#if>">
            </div>
            <div class="line-grey">
                <#if level.memberDetail.level gt 0>
                    <div class="vd vd-1 flex" data-level="${level.memberDetail.level-1}"><p>
                        v${level.memberDetail.level-1}</p>
                    <p class="pp">0</p></div></#if>
                <#if level.memberDetail.level lt 4>
                    <div class="vd vd-2 flex" data-level="${level.memberDetail.level+1}"><p>
                        v${level.memberDetail.level+1}</p>
                    <p class="pp">0</p></div></#if>
                <#if level.memberDetail.level lt 3>
                    <div class="vd vd-3 flex" data-level="${level.memberDetail.level+2}"><p>
                        v${level.memberDetail.level+2}</p>
                    <p class="pp">0</p></div></#if>
            </div>
            <div class="line-yel">
            </div>
        </div>
        <div class="lev-tips">
            <#if level.memberDetail.level lt 5>再消费<span class="need-amount"></span>元即可升级成V${level.memberDetail.level+1}
            <#else>
                您已经是最高等级的会员啦
            </#if>
        </div>
        <div class="lev-db">
            <i class="db db-icon"></i>${level.memberDetail.integration}D币
        </div>
    </div>
    <div class="my-con">
        <p>我的特权</p>
        <div class="prerogative">

        </div>
    </div>
</div>
<div class="body-back"></div>
<div class="masked"></div>

<script id="member-level" type="text/html">
    {{each list as value index}}
    <div class="prero-list">
        {{if index==4|| index==6|| index==7}}
        <div class="wait">敬请期待</div>
        {{/if}}
        <a href="{{if value.status==0}}javascript:;{{else}}{{value.href}}{{/if}}"><img
                    src="{{value.src}}{{value.status}}.png"></a>
        <p>{{value.title}}</p>
    </div>
    {{/each}}
</script>
<script>

    var my_level = {
        init: function () {
            var height = screen.height;
            $('.my-con').height(parseInt(height) - parseInt($('.my-level').height()) - 65);
        },
        choose: function (lev, isclick) {
            var additionalAmount = <#if level.memberDetail.additionalAmount>${(level.memberDetail.additionalAmount)!}<#else>0</#if>;
            switch (lev) {
                case 0:
                    var statu = [1, 0, 1, 0, 0, 0, 0, 0];
                    if (!isclick) {
                        $('.line-grey div:eq(0)').find('.pp').text('1');
                        $('.line-grey div:eq(1)').find('.pp').text('1k');
                        $('.need-amount').text('1');
                        $('.line-yel').width('50%')
                    }
                    break;
                case 1:
                    var statu = [1, 0, 1, 1, 0, 0, 0, 0];
                    if (!isclick) {
                        $('.line-grey div:eq(0)').addClass('vd-yel').find('.pp').text('0');
                        $('.line-grey div:eq(1)').find('.pp').text('1K');
                        $('.line-grey div:eq(2)').find('.pp').text('1W');
                        $('.need-amount').text(1000 - additionalAmount);
                        $('.line-yel').width('50%')
                    }
                    break;
                case 2:
                    var statu = [1, 1, 1, 1, 0, 0, 0, 0];
                    if (!isclick) {
                        $('.line-grey div:eq(0)').addClass('vd-yel').find('.pp').text('0');
                        $('.line-grey div:eq(1)').find('.pp').text('1W');
                        $('.line-grey div:eq(2)').find('.pp').text('3W');
                        $('.need-amount').text(10000 - additionalAmount);
                        $('.line-yel').width('50%')
                    }
                    break;
                case 3:
                    var statu = [1, 1, 1, 1, 0, 0, 0, 0]
                    if (!isclick) {
                        $('.line-grey div:eq(0)').addClass('vd-yel').find('.pp').text('1K');
                        $('.line-grey div:eq(1)').find('.pp').text('3W');
                        $('.line-grey div:eq(2)').find('.pp').text('6W');
                        $('.need-amount').text(30000 - additionalAmount);
                        $('.line-yel').width('50%')
                    }
                    break;
                case 4:
                    var statu = [1, 1, 1, 1, 0, 1, 0, 0]
                    if (!isclick) {
                        $('.line-grey div:eq(0)').addClass('vd-yel').find('.pp').text('1W');
                        $('.line-grey div:eq(1)').find('.pp').text('6W');
                        $('.need-amount').text(60000 - additionalAmount);
                        $('.line-yel').width('50%')
                    }
                    break;
                case 5:
                    var statu = [1, 1, 1, 1, 0, 1, 0, 0]
                    if (!isclick) {
                        $('.line-grey div:eq(0)').addClass('vd-yel').find('.pp').text('3W');
                        $('.line-yel').width('100%')
                    }
                    break;
            }
            var data = {
                list: [
                    {title: '购物送D币', src: '//static.d2c.cn/common/nm/img/buy_', status: 0, href: '/page/dbtq'},
                    {title: '尊享客服', src: '//static.d2c.cn/common/nm/img/kf_', status: 0, href: '/page/kftq'},
                    {title: '免费试衣', src: '//static.d2c.cn/common/nm/img/reserve_', status: 0, href: '/page/shiyitq'},
                    {title: '生日礼券', src: '//static.d2c.cn/common/nm/img/bir_', status: 0, href: '/page/birprev'},
                    {title: '升级礼包', src: '//static.d2c.cn/common/nm/img/level_', status: 0, href: 'javascript:;'},
                    {title: '线下活动', src: '//static.d2c.cn/common/nm/img/pro_', status: 0, href: '/page/xxhd'},
                    {title: '会员专享价', src: '//static.d2c.cn/common/nm/img/vip_', status: 0, href: 'javascript:;'},
                    {title: 'D币超值兑', src: '//static.d2c.cn/common/nm/img/db_', status: 0, href: 'javascript:;'},
                ]
            };
            for (i = 0; i < 8; i++) {
                data.list[i].status = statu[i];
            }
            var html = template('member-level', data);
            $('.prerogative').html(html);
        }
    }
    my_level.init();
    my_level.choose(${level.memberDetail.level}, false);

    $('.line-grey .vd').on(click_type, function () {
        var le = $(this).attr('data-level');
        my_level.choose(parseInt(le), true);
    })
    $('.user-head').on(click_type, function () {
        my_level.choose(${level.memberDetail.level}, true);
    })

</script>




<@m.page_footer menu=false />