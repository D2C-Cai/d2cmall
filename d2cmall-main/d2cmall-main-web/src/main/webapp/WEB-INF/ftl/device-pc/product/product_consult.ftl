<#import "templates/public_pc.ftl" as m>
<#if pager.list?size &gt; 0>
    <div class="consult-container">
    <div class="consult-top">
        <button class="button-top-consult">发表咨询</button>
    </div>
    <#list pager.list as consult>
        <#if consult.status==2>
            <div class="consult-item">
            <div class="d2cuser" style="color:#999;">
                <span>网&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;友：</span><span>${consult.nickName}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span
                        class="time">${(consult.createDate?string("yyyy-MM-dd HH:mm:ss"))!}</span>
            </div>
            <div class="d2csonsult">
                <span class="consult-content">咨询内容：</span><#if consult.question?length gt 0><span
                        class="consult-content" style="width:75%;">${consult.question}</span></#if>
            </div>
            <#if consult.status==2>
                <div class="d2cans">
                    <div><span class="consult-reply">小D 回复：</span><span class="consult-reply""
                        style="width:75%;">${consult.reply}</span></div>
                    <span class="replydate"
                          style="color:#999;">${consult.replyDate?string("yyyy-MM-dd HH:mm:ss")}</span>
                </div></div>
            </#if>
            </div>
        </#if>
    </#list>
    <div class="consult-publish">购买之前有疑问？请
        <button class="button-consult" id="button-consult">发表咨询</button>
    </div>
    <div class="pages cousult-pages"><@m.p page=pager.pageNumber totalpage=pager.pageCount /></div>
    </div>
<#else>
    <div style="padding-top:300px;padding-bottom:90px;text-align:center;overflow:hidden;">
        <span>暂时还未有咨询</span>
        <div class="consult-publish" style="margin:90px auto;width:250px;float:none;position:static;">购买之前有疑问？请
            <button class="button-consult" id="button-consult">发表咨询</button>
        </div>
    </div>
</#if>
<div class="w-consult" style="display:none;">
    <form id="consult-form" name="consult-form" class="validate-form" action="/consult/insert" method="post"
          success-tip="咨询提交成功，请等待回复！">
        <input type="hidden" name="device" value="PC"/>
        <input type="hidden" value="${productId}" name="productId">
        <h5>发表咨询</h5>
        <p>声明：您可在购买前对产品原料、颜色、运输、库存等方面进行咨询，我们有专人进行回复！回复仅在当时对提问者有效，仅供其他亲们参考！咨询回复的工作时间为：周一至周五，9:00至17:30，请亲们耐心等待工作人员回复。</p>
        <h5 style="padding-top:0">咨询内容</h5>
        <textarea class="r-consult input" name="question" id="question"></textarea>
        <div class="tip tip-validate" data-target="question"></div>
        <button type="button" class="s-consult" style="width:14%">提交咨询</button>
    </form>
</div>
<script type="text/javascript">
    $('.button-consult').click(function () {
        $(this).parent().hide();
        $('.w-consult').slideToggle();
        $('.consult-top').hide();
    })

    $('.button-top-consult').click(function () {
        $(this).parent().hide();
        $('.w-consult').show();
        $('.consult-publish').hide();
        var topheight = $('.cousult-pages').offset().top;
        $('html, body').animate({scrollTop: topheight}, 300);
    })
    $('.cousult-pages a').click(function () {
        var obj = $('.consult-container');
        var productId = $('input[name=productId]').val();
        var url = $(this).attr('href');
        var nurl = '/' + productId + url;
        $('html, body').animate({scrollTop: obj.offset().top - 55}, 300);
        $.get('/consult/product/' + nurl, function (data) {
            obj.parent().html(data);
        });
        return false;
    });


    $('.s-consult').click(function () {
        $.getJSON('/member/islogin', function (data) {
            if (data.result.login == false) {
                $('body').data('function', '$("#consult-form").submit()');
                userLogin();
            } else {
                $('#consult-form').submit();
            }
        });
    })
</script>