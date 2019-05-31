// vim: set et sw=4 ts=4 sts=4 ft=javascript fdm=marker ff=unix fenc=utf8 nobomb:
/**
 * 针对移动端的应用安装脚本
 *
 * 说明：本脚本针对 Android 以及 iOS 引导用户安装对应的应用，同时如果在应用已经安装得情况下启动应用。
 *
 * HTML 结构

 <form action="" method="get">
 <button type="submit">Install</button>
 </form>

 * 简单得讲就是
 *
 * 配置应用安装信息
 *
 *
 var configure = {
         "ios": {
             "packageName": "com.d2cmall.buyer",
             "packageUrl": "d2cmall://",
             "appStoreUrl": "http://itunes.apple.com/cn/app/id980211165"
         },

         "android": {
             "packageName": "com.d2cmall.buyer",
             "packageUrl": "app.dxy.cn/medtime/index",
             "fusionPage": "http://fusion.qq.com/cgi-bin/qzapps/unified_jump?appid=12126865"
         }
     };

 * 初始化，然后执行

 Zepto.install($("form"), configure);

 * 即可使用。
 */

(window.jQuery || Zepto)(function ($) {

    // 获取当前的环境信息
    var userAgent = navigator["userAgent"]["toLowerCase"](),
        isiPad = userAgent.match(/ipad/),
        isiPhone = userAgent.match(/iphone|ipod/),
        iOS = isiPad || isiPhone,
        isAndroid = userAgent.match(/android/),
        isWeChat = userAgent.match(/micromessenger/);
    // 初始化安装
    $.install = function (el, config) {

        // 获取两者共同点
        var packageUrl = config[isAndroid ? "android" : "ios"]["packageUrl"],
            packageName = config[isAndroid ? "android" : "ios"]["packageName"];
        var scheme = "";

        if (iOS) {
            scheme = config.ios.appStoreUrl;
        }

        if (isAndroid) {
            var browser = function () {
                return {
                    uc: userAgent.match(/ucbrowser/),
                    xiaomi: userAgent.match(/miui/),
                    firefox: userAgent.match(/firefox/)
                };
            }();

            scheme = "intent://"
                + packageUrl
                + "#Intent;action=android.intent.action.VIEW;"
                + "category=android.intent.category.DEFAULT;"
                + "category=android.intent.category.BROWSABLE;"
                + "scheme=http;end";

            if (browser.xiaomi) {
                var name = packageName || "com.d2cmall.buyer";
                scheme = "intent://" + packageUrl + "#Intent;package=" + name + ";scheme=http;end";
            } else if (browser.uc || browser.firefox) {
                scheme = "http://" + packageUrl;
            }
        }


        if (isWeChat) {
            var url = (isAndroid ? "http://" : "") + packageUrl;

            if (isAndroid && typeof config.android.fusionPage != "undefined" && config.android.fusionPage) {
                location.replace(config.android.fusionPage);
                return;
            }

            // 延时执行，避免 WebView 没有准备好
            setTimeout(function () {
                WeixinJSBridge.invoke("getInstallState", {
                    "packageUrl": url,
                    "packageName": packageName
                }, function (res) {
                    if (res.err_msg.match(/yes/)) {
                        // 如果已经安装，则启动应用即可
                        scheme = packageUrl;
                        $(el).find("button[type=submit]").html("启动应用");
                    }
                });
            }, 500);
        }
        // 如果成功获取到了安装信息
        if (scheme) {
            $(el).submit(function (e) {
                location.replace(scheme);
                return false;
            });
        }
    } // install
});