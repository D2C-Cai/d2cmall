<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='报名成功啦！'/>
<div class="section">
    <div class="text-center padding" style="line-height:250%;padding-top:3em;">
        <div class="icon icon-ok"></div>
        <div class="green"><strong>${reg.name}，恭喜您，报名成功！</strong></div>
        <div>请准时参加哦，我们恭候您的大驾光临！</div>
        <div style="font-size:0.8em;line-height:150%;">${def.info}</div>
        <div>&nbsp;</div>
        <div>
            <button type="button" name="return" onclick="location.href='/';" style="width:80%;"
                    class="button button-l button-red">访问网站
            </button>
        </div>
    </div>
</div>
<hr/>
<@m.page_footer menu=true />
