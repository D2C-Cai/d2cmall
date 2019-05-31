<#import "templates/public_pc.ftl" as m>
<@m.page_header title='撞衫不要慌，直播帮你忙：${result.datas.live.title}'   keywords="预售,设计师品牌,全球设计师集成平台,SHOWROOM,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成店"   js='lib/swfobject|utils/jquery.zoom' description="${result.datas.live.designerName}正在开播，一起玩耍吧~" />
<@m.top_nav />
<style>
    body {
        background: #fff
    }
</style>
<#assign live = result.datas.live>
<#if live.status==4 ||  live.status==8>
    <script type="text/javascript" language="JavaScript">
        //Powered By smvv @hi.baidu.com/smvv21
        function flashChecker() {
            var hasFlash = 0;         //是否安装了flash
            var flashVersion = 0; //flash版本
            var isIE = /*@cc_on!@*/0;      //是否IE浏览器
            if (isIE) {
                var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
                if (swf) {
                    hasFlash = 1;
                    VSwf = swf.GetVariable("$version");
                    flashVersion = parseInt(VSwf.split(" ")[1].split(",")[0]);
                }
            } else {
                if (navigator.plugins && navigator.plugins.length > 0) {
                    var swf = navigator.plugins["Shockwave Flash"];
                    if (swf) {
                        hasFlash = 1;
                        var words = swf.description.split(" ");
                        for (var i = 0; i < words.length; ++i) {
                            if (isNaN(parseInt(words[i]))) continue;
                            flashVersion = parseInt(words[i]);
                        }
                    }
                }
            }
            return {f: hasFlash, v: flashVersion};
        }

        var fls = flashChecker();
        var s = "";
        if (!fls.f) {
            alert("您的浏览器没有安装flash插件或者flash已被禁用");
        }
    </script>
    <div style="width:1000px;margin:0 auto;height:80px;border:1px solid #e5e4e4;font-family: \5FAE\8F6F\96C5\9ED1,arial,sans-serif">
        <div style="float:left;">
            <#if live.headPic>
                <img src="<#if live.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${live.headPic}"
                     style="width:80px;height:80px"/>
            <#elseif live.thirdHeadPic>
                <img src="${live.thirdHeadPic}" style="width:92px;height:92px"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png" style="width:92px;height:92px"/>
            </#if>
        </div>
        <div style="float:left;max-width:800px">
            <p style="font-size:20px;line-height:22px;white-space:nowrap;text-overflow:ellipsis;font-weight:bolder;color:#2c3e50;padding:8px 0 0 10px;">
                ${live.title}
            </p>
            <p style="font-size:14px;line-height:16px;padding:6px 0 0 10px;">
                <a href="/showroom/designer/${live.designerId}" style="color:#838c9a;"
                   target="_blank">${live.designerName}>${live.brandName}</a>
            </p>
            <p style="font-size:14px;line-height:16px;color:#2c3e50;padding:6px 0 0 10px;">
                ${live.nickname}
            </p>
        </div>
        <div>
            <p style="margin-top:60px;float:right;font-size:14px;line-height:20px;bottom:5px;color:#2c3e50;padding-right:50px;position:relative;background:url(//static.d2c.cn/common/nc/css/img/view_in_mobile.png) center right no-repeat">
                <span class="show-web">手机观看</span>
                <img src="/picture/code?type=1&width=120&height=120&noLogo=true&&code=//t.cn/RLstL36"
                     style="position:absolute;top:25px;left:-11px;z-index:2;display:none" class="img-code"/>
            </p>
        </div>
    </div>
    <div style="width:1000px;margin:0 auto;min-height:400px;margin-top:10px;position:relative;margin-bottom:10px;">
        <div id="player"></div>
    </div>
    <script>

        var flashvars = {
            // M3U8 url, or any other url which compatible with SMP player (flv, mp4, f4m)
            // escaped it for urls with ampersands
            src: escape('<#if live.status==4>${live.hlsUrl}<#elseif live.status==8>${live.replayUrl}</#if>'),
            // url to OSMF HLS Plugin
            plugin_m3u8: "//static.d2c.cn/common/nm/js/live/HLSProviderOSMF.swf",
        };
        var params = {
            // self-explained parameters
            allowFullScreen: false,
            allowScriptAccess: "always",
            bgcolor: "#FFF"
        };
        var attrs = {
            name: "player"
        };
        swfobject.embedSWF(
            // url to SMP player
            "//static.d2c.cn/common/nm/js/live/StrobeMediaPlayback.swf",
            // div id where player will be place
            "player",
            // width, height
            "100%", "485",
            // minimum flash player version required
            "10.2",
            // other parameters
            null, flashvars, params, attrs
        );
        $('.show-web').hover(function () {
            $('.img-code').show();
        }, function () {
            $('.img-code').hide();
        })
    </script>
<#elseif live.status!=4 && live.status!=8>
    <div class="layout layout-response layout-warning">
        <div class="warning-cont">
            <i class="fa fa-info-circle"></i>
            <h1>对不起，直播已经结束。</h1>
            <p>请您稍后刷新页面观看主播精彩录播。</p>
            <p><a href="/" class="button button-s button-green">返回首页</a></p>
        </div>
    </div>

</#if>

<@m.page_footer />