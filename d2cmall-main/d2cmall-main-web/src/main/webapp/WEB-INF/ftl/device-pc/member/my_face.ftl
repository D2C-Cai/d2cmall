<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的头像  - 个人中心'/>
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu/>
    </div>
    <div style="width:40px;display:table-cell;background-color:#ededed"></div>
    <div class="layout-user-right">
        <h1><i class="fa fa-caret-right"></i>编辑头像</h1>
        <div class="grid-form">
            <div class="float-left" style="width:130px;margin-right:40px;">
                <p>当前头像</p>
                <img name="imageFile" src="<@m.memberAvator member 'medium' />"
                     onerror="javascript:this.src='${home_picture_base}/face.jpg'" width="120" height="120"
                     id="head_pic" style="padding:3px;border:1px solid #CCC;"/>
            </div>
            <div class="float-left" style="border-left:1px solid #CCC;padding-left:25px;">
                <p>上传新头像</p>
                <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                        codebase="//fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=10,0,0,0"
                        width="500" height="405" id="FlashVars" align="middle">
                    <param name="movie" value="../static/face.swf"/>
                    <param name="wmode" value="transparent"/>
                    <param name="FlashVars" value="saveFace=saveFace?&url=images%2Ffacebg.jpg&imgsize=100&"/>
                    <!--[if !IE]>-->
                    <object type="application/x-shockwave-flash" data="${static_base}/face.swf" width="500" height="405"
                            FlashVars="saveFace=saveFace?<#if status?exists>status=${status}</#if>&url=images%2Ffacebg.jpg&imgsize=100&"
                            wmode="transparent" allowScriptAccess="always">
                        <!--<![endif]-->
                        <p><span style="display:none;"><embed src="images/blank.swf"
                                                              type="application/x-shockwave-flash" wmode="transparent"/></span><em
                                    class="s2" style="position:relative;top:3px;">该浏览器尚未安装flash插件，<a
                                        href="//www.adobe.com/go/getflashplayer" target="_blank">点击安装</a></em></p>
                        <!--[if !IE]>-->
                    </object>
                    <!--<![endif]-->
                </object>
            </div>
        </div>
    </div>
    <div class="clear"></div>
</div>
<script type="text/javascript">
    $(function () {

    });
</script>

<@m.page_footer />