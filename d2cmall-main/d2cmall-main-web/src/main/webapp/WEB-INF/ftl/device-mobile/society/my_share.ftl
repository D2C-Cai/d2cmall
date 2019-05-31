<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='${(member.nickname)!}的个人主页'  service="share" hastopfix='true'/>
<div class="fullscreen">
    <div class="user defaultbg">
        <div class="user-photo">
            <#if member.headPic>
                <img src="<#if member.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${member.headPic}"
                     class="head-pic"></img>
            <#elseif member.thirdHeadPic>
                <img src="${member.thirdHeadPic}" class="head-pic"></img>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png"></img>
            </#if>
        </div>
        <div class="user-nick">
            <p class="nick">${(member.nickname)!}</p>
        </div>
    </div>
    <div class="user-info">
        <div class="trends section1">
            <p class="user-red">${(sharesTotalCount)!}</p>
            <p>动态</p>
        </div>
        <div class="fans section1">
            <p class="user-red">${(fansTotalCount)!}</p>
            <p>粉丝</p>
        </div>
        <div class="concern section1">
            <p class="user-red">${(followsTotalCount)!}</p>
            <p>关注</p>
        </div>
    </div>
    <!--
	<div class="myshowtable">
		<div class="table on" data-type="show"><a href="javascript:;"><strong>动态</strong></a></div>
		<div class="table plk" data-type="live" data-url="/live/list?memberId=${member.id}"><a href="javascript:;"><strong>直播</strong></a></div>
	</div>
	<div class="lshow" style="display:none;"></div>	
	-->
    <div class="fshow" style="position:relative;min-height:18rem;background:#fff;">
        <#if pager.list?size &gt; 0>
            <#list pager.list as minepage>
                <div class="myshow-item">
                    <div class="myshow-area">
                        <div class="show-time" data-time="${(minepage.createDate?string("yyyy/MM/dd HH:mm:ss"))!}">
                        </div>
                        <#if minepage.description!=''>
                            <div class="show-content">${minepage.description}</div>
                        </#if>
                        <#if minepage.pics?exists && minepage.pics?size &gt; 0>
                            <div class="show-img">
                                <#if minepage.pics?size==1>
                                    <div class="img-list-1">
                                        <#list minepage.pics as path>
                                            <a href="${picture_base}/${path}"><img
                                                        src="${picture_base}/${path}!/sq/300"></a>
                                        </#list>
                                    </div>
                                <#elseif minepage.pics?size==4>
                                    <div class="img-list-2">
                                        <#list minepage.pics as path>
                                            <a href="${picture_base}/${path}"><img
                                                        src="${picture_base}/${path}!/sq/300"></a>
                                        </#list>
                                    </div>
                                <#else>
                                    <div class="img-list-3">
                                        <#list minepage.pics as path>
                                            <a href="${picture_base}/${path}"><img
                                                        src="${picture_base}/${path}!/sq/300"></a>
                                        </#list>
                                    </div>
                                </#if>
                            </div>
                        </#if>
                        <div class="p-status">
                            <span class="p-time"></span>
                            <span class="p-when"></span>
                        </div>
                        <div class="p-handle">
                            <div class="praise section2">
                                <a href="javascript:void(0);" data-url="/membershare/like/insert/${minepage.id}"
                                   class="favbutton button-clear button-s like-me">
						<span class="p-praise">
						</span>
                                    <span class="n-praise">
							${minepage.likes}
						</span>
                                </a>
                            </div>
                            <div class="comment section2">
                                <a href="/membershare/${minepage.id}">
						<span class="p-comment">
						</span>
                                    <span class="n-comment">
							评论
						</span>
                                </a>
                            </div>
                            <div class="share section2">
                                <a href="javascript:void(0);" class="button share-button button-clear button-s">
						<span class="p-share">
						</span>
                                    <span class="n-share">
							分享
						</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        <#else>
            <div class="no-fshow" style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);">
                <img src="//img.d2c.cn/2018/11/14/030547354de3a637db1bf3396c26e58cf92fda.png"
                     style="width:94px;height:114px;" alt="">
            </div>
        </#if>
    </div>
</div>
<share data-title="大家来看看我的D2C主页" data-pic="${picture_base}${(member.head)}!wx.title"></share>
<script>
    var userdate = new Date("${.now?string("yyyy/MM/dd HH:mm:ss")}");
    var mydate = new Date("${.now?string("yyyy/MM/dd HH:mm:ss")}");
    mydate.setHours(0);
    mydate.setMinutes(0);
    mydate.setSeconds(0);
    mydate.setMilliseconds(0);
    $('.show-time').each(function (i, d) {
        var time = $(this).attr('data-time');
        var date = new Date(time);
        if (mydate.toDateString() == date.toDateString()) {
            var str = "<p class='fortime'>今天</p>"
            $(this).append(str);
        } else if (mydate.getTime() - date.getTime() <= 86400000) {
            var str = "<p class='fortime'>昨天</p>"
            $(this).append(str);
        } else {
            var m = changenum(date.getMonth() + 1);
            var d = date.getDate();
            var str = "<p class='fortime'>" + d + "<span class='small-time'>" + m + "月</span></p>";
            $(this).append(str);
        }
        if (userdate.getTime() - date.getTime() <= 3600000) {
            var timeout = Math.floor((userdate.getTime() - date.getTime()) / 60000);
            var nstr = timeout + '分钟前';
            $(this).parent().find('.p-when').html(nstr);
        } else {

            var h = date.getHours() >= 10 ? date.getHours() : '0' + date.getHours();
            var mn = date.getMinutes() >= 10 ? date.getMinutes() : '0' + date.getMinutes();
            var nstr = h + ":" + mn;
            $(this).parent().find('.p-when').html(nstr);
        }
    })
    $('.like-me').on('click', function () {
        var obj = $(this);
        var i = parseInt(obj.parent().find('.n-praise').text());
        $.ajax({
            url: obj.attr("data-url"),
            type: 'post',
            data: '',
            dataType: 'json',
            success: function (data) {
                if (data.result.status == 1) {
                    obj.parent().find('.n-praise').text(i + 1);
                    $.flashTip({position: 'center', type: 'error', message: "点赞成功"});
                } else {
                    $.flashTip({position: 'center', type: 'error', message: "已经点赞过了"});
                }
            }
        });

    })

    /*
    $('.table').on('click',function(){
        var type=$(this).attr('data-type');
        $(this).siblings().removeClass('on');
         $(this).addClass('on');
        if(type=="show"){
            $('.fshow').show();
            $('.lshow').hide();
        }else{
            $('.lshow').show();
            $('.fshow').hide();
        }

    })
    $('.plk').on('click',function(){
     if($('.myshowtable').data('isread')!=true){
         var url=$(this).attr('data-url');
             $.get(url,function(data){
                $('.myshowtable').data('isread',true);
                $('.lshow').html(data);
                },'html');
         }
    })
    */

    $('.share-button').on('click', function () {
        <#if browser=='wechat'>
        if ($('.share-wechat-guide').size() == 0) {
            var html = '<div class="share-wechat-guide"><a href="javascript:void(0);" class="mask-close"></a></div>';
            $('body').append(html);
        }
        $('.share-wechat-guide').addClass('show');
        return false;
        <#else>
        var title = $('share').attr('data-title') || document.title,
            desc = $('share').attr('data-desc') || document.title,
            pic = $('share').attr('data-pic'),
            url = $('share').attr('data-url') || document.location.href;
        location.href = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(url) + '&title=' + encodeURIComponent(desc) + '&pic=' + encodeURIComponent(pic) + '&appkey=&searchPic=false';
        </#if>
    });
</script>
<@m.page_footer menu=true />