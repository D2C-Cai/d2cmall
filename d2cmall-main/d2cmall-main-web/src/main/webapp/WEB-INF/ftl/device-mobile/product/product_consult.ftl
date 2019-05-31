<#import "templates/public_mobile.ftl" as m>
<#if pager.list?size &gt; 0>
    <input type="hidden" value="${productId}" name="productId">
    <#list pager.list as consult>
        <div class="consult-item">
            <div class="consult-author">
                <span class="author-name">${consult.nickName}</span><span
                        class="author-date">${consult.createDate?string("yyyy-MM-dd HH:mm:ss")}</span>
            </div>
            <div class="consult-question">
                <span class="quesbg"></span>
                <div class="quescon"><span>${consult.question}</span></div>
            </div>
            <div class="consult-reply">
                <span class="replybg"></span>
                <div class="reply"><span>${consult.reply}</span></div>
            </div>
        </div>
    </#list>
    <div class="pages consult-pages">
        <@m.simple_pager page=pager.pageNumber totalpage=pager.pageCount />
    </div>
<#else>
    <div style="line-height:10em;text-align:center;">
        暂时还未有咨询
    </div>
</#if>
<script>
    $('.consult-pages a').click(function () {
        var obj = $(this).parent().parent();
        $('html, body').animate({scrollTop: obj.offset().top - 55}, 300);
        var productId = $('input[name=productId]').val();
        var url = $(this).attr('href');
        var nurl = '/' + productId + url;
        $.get('/consult/product' + nurl, function (data) {
            obj.html(data);
        });
        return false;
    });
</script>