<#import "templates/public_pc.ftl" as m>
<@m.page_header title='设计师中心' />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="shop_center_${path}"/>
    </div>

    <noframes>
        <body>
        </body>
    </noframes>


    <iframe id="frame_content" src="/merchant/index.html?${path}&${profile}" scrolling="no" frameborder="0"
            width="100%"></iframe>
</div>

<script type="text/javascript">
    //去掉iframe的滚动条
    function reinitIframe() {

        var iframe = document.getElementById("frame_content");

        try {

            iframe.height = iframe.contentWindow.document.documentElement.scrollHeight;

        } catch (ex) {
        }

    }

    window.setInterval("reinitIframe()", 200);


</script>

<@m.page_footer />