<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='买家秀'  service='false'  hastopfix='true'/>
<div class="section lazyload" style="padding-top:0.5em;">
    <div class="list clearfix" id="load-more-product">
        <#list pager.list as list>
            <div class="item item-flex item-gap">
            <span class="img"><a href="/membershare/${(list.id)!}"><img src="${static_base}/m/img/blank100x157.png"
                                                                        data-image="${picture_base}${(list.first)!}!300"
                                                                        width="100%" alt=""/></a>
            <i class="num-tip">☒ ${(list.pic?split(',')?size)!}张图</i>
            </span>
                <div style="padding:0.5em;">
                    <a href="javascript:void(0);" data-url="/member/interest/like/insert/${(list.id)!}"
                       success-tip="false" call-back="likeSuccess(${(list.id)!})"
                       class="ajax-request fav button button-red">赞一下</a>
                    <span style="float:right;color:#FD555D;font-family:arial;line-height:2.5em;"
                          id="like-${(list.id)!}"><strong>${list.likes+list.vlikes}</strong>赞</span>
                </div>
            </div>
        </#list>
    </div>
    <div class="load-more scroll-load-more" data-url="/membershare/list?bcd=${RequestParameters.bcd}"
         data-target="load-more-product" template-id="list-template" data-page="${pager.pageNumber}"
         data-total="${pager.pageCount}">点击加载更多
    </div>
</div>
<hr/>
<script id="list-template" type="text/html">
    {{each list as value i}}
    <div class="item item-flex item-gap">  
        <span class="img"><a href="/membershare/{{value.id}}"><img src="${static_base}/m/img/blank100x157.png"
                                                                   data-image="${picture_base}{{value.first}}!300"
                                                                   width="100%" alt=""/></a>
        <i class="num-tip">☒ {{value.pic | count}}张图</i>
        </span>
        <div style="padding:0.6em;">
            <a href="javascript:void(0);" data-url="/member/interest/like/insert/{{value.id}}" success-tip="false"
               style="line-height:30px;vertical-align:middle;" call-back="likeSuccess({{value.id}})"
               class="ajax-request fav button button-red">赞一下</a>
            <span style="float:right;color:#FD555D;font-family:arial;line-height:2.5em;" id="like-{{value.id}}"><strong>{{value.likes}}</strong>赞</span>
        </div>
    </div>
    {{/each}}
</script>
<script>
    template.helper('count', function (data) {
        return data.split(',').length;
    });

    function likeSuccess(id) {
        var vote = JSON.parse(localStorage.getItem('MSHARE')) || [];
        var obj = $('#like-' + id);
        var i = parseInt(obj.find('strong').text());
        if (isNaN(i)) i = 0;
        if ($.inArray(id, vote) != -1) {
            $.flashTip({position: 'center', type: 'error', message: '已经点过赞了'});
        } else {
            obj.find('strong').text(i + 1);
            vote.push(id);
            localStorage.setItem('MSHARE', JSON.stringify(vote));
            $.flashTip({position: 'center', type: 'success', message: '点赞成功'});
        }
        return false;
    }
</script>
<@m.page_footer menu=true />