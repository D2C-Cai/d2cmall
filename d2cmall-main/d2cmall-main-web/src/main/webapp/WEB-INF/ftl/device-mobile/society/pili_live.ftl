<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title='撞衫不要慌，直播帮你忙：${result.datas.live.title}' js="live/swfobject|https://cdn.ronghub.com/RongIMLib-2.2.5.min.js |https://static.mlinks.cc/scripts/dist/mlink.min.js" service='false'  keywords="设计师现货区,全球好设计,D2C,Designer To Customer,原创设计,个性,原创设计概念店,设计师品牌集成， 直播，D2C直播" description="${result.datas.live.designerName}正在开播，一起玩耍吧~"  hastopfix='false'/>
<style>
    body {
        background: rgb(0, 0, 0);
        width: 100%;
        height: 100%;
        margin-top: 0px;
    }

    .heightem {
        display: none
    }

    .input:focus, .input:active {
        border: 0 slid #999;
        box-shadow: none;
    }

    .watchinfo {
        position: absolute;
        top: 60%;
        text-align: center;
        width: 80%;
        left: 50%;
        margin-left: -40%;
        color: #FFF;
        font-size: 20px;
        display: -webkit-box;
        display: box;
    }

    .watchinfo div {
        line-height: 125%;
        -moz-box-flex: 3;
        -webkit-box-flex: 3;
        box-flex: 3;
        border: 0;
        text-align: center;
    }

    .end-info {
        position: absolute;
        text-align: center;
        width: 100%;
    }

    .footer-space {
        display: none
    }
</style>
<share data-pic="//static.d2c.cn/common/nm/css/img/app_icon.png"></share>
<#assign live = result.datas.live>
<#if live.status==4|| live.status==8>
    <input type="hidden" name="id" value="${live.id}"/>
    <div class="video">
        <div id="player">
            <video id="video" webkit-playsinline="true" x-webkit-airplay="true" playsinline="true"
                   x5-video-player-type="h5" x5-video-player-fullscreen="true" loop="loop" width="100%" preload="auto"
                   style="background: rgb(0, 0, 0);object-fit: fill;">
                <source type="application/x-mpegurl"
                        src="<#if live.status==4>${live.hlsUrl}<#elseif live.status==8>${live.replayUrl}</#if>">
                <source type="application/vnd.apple.mpegurl"
                        src="<#if live.status==4>${live.hlsUrl}<#elseif live.status==8>${live.replayUrl}</#if>">
            </video>
        </div>
    </div>
    <a style="display:none;" href="javascript:;" id="openD2cApp">打开APP</a>
    <div class="txguide" onclick="$(this).hide()"></div>
    <div class="brguide" onclick="$(this).hide()"></div>
    <div class="live-top-nav">
        <div class="live-user-info">
            <a href="javascript:;" class="head-pic">
                <#if live.headPic>
                    <img src="<#if live.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${live.headPic}"
                         class="head-pic"/>
                <#elseif live.thirdHeadPic>
                    <img src="${live.thirdHeadPic}" class="head-pic"/>
                <#else>
                    <img src="//static.d2c.cn/img/home/160627/images/headpic.png"></img>
                </#if>
                <span class="rank user_lv" style="width:.5rem;height:.51375rem;right:.2rem;bottom:-.1rem"></span>
            </a>
            <dl class="item">
                <dt>${live.nickname}</dt>
                <dd><span class="watchcout">0</span>人观看</dd>
            </dl>
        </div>
    </div>
    <div class="bg-icon">
        <#if live.cover>
            <div
            style="position: absolute;width: 100%;height: 100%;top: 0;background-image: url(${picture_base}${(live.cover)!});background-position: center center;background-size: cover;"></div></#if>
        <div class="live-pause">
            <div class="pause-wrap">
                <a href="javascript:;" style="width:4rem;height:4rem;border-radius:50%;"><img
                            src="//static.d2c.cn/common/nm/css/img/icon_live_play.png"
                            style="width:4rem;height:4rem;border-radius:50%;" id="players"></a>
            </div>
        </div>
    </div>
    <section class="product-list">

    </section>
    <section class="like-userl">
        <a href="javascript:;" class="d2cDownApp" style="color:#FFF">打开D2C关注主播</a>
    </section>
    <section class="user_live_room" style="min-width:100%">
        <figure class="user_avatar">
            <#if live.headPic>
                <img src="<#if live.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${live.headPic}" class="head-pic"/>
            <#elseif live.thirdHeadPic>
                <img src="${live.thirdHeadPic}" class="head-pic"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png"></img>
            </#if>
            <span class="rank user_lv"></span>
        </figure>
        <dl class="user_desc">
            <dt>${live.nickname}</dt>
            <dt style="font-size:.75rem">教你穿出FASHION最潮范</dt>
        </dl>
        <a class="enter_live_room d2cDownApp" href="javascript:;">进入直播间</a>
    </section>
    <img src="${picture_base}${(live.cover)!}"
         style="width:100%;position:absolute;top:0;left:0;display:block;z-index:10;height:100%;filter: blur(10px);display:none"
         class="bg-all">
    <div style="width:100%;background-color:#000;opacity:0.7;position:absolute;top:0;left:0;display:block;z-index:20;height:100%;display:none"
         class="all">
        <div>
            <#if live.headPic>
                <img src="<#if live.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${live.headPic}"
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"/>
            <#elseif live.thirdHeadPic>
                <img src="${live.thirdHeadPic}" class=""
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png"
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"></img>
            </#if>
            <p class="end-info" style="top:31%;color:#FFF">${live.nickname}</p>
            <p class="end-info" style="top:36%;">${live.title}</p>
            <p class="end-info" style="top:43%;color:#FFF;font-size:20px;">直播已经结束~</p>
            <div class="watchinfo">

            </div>
        </div>
    </div>

    <script>
        var height = $(window).height();
        $('.all').height(height)
    </script>
    <#if live.status==4>
        <section class="btm-menu">
            <div class="wrap-menu">
                <a href="javascript:;" class="gobuy"></a>
                <div class="w-input">
                    <input class="input" id="content" name="content" type="text" placeholder="快来和大家说点什么~">
                </div>
                <span class="line-qa"></span>
                <span class="send-msg">发送</span>
                <a href="javascript:;" class="share-btn">分享</a>
            </div>
        </section>
        <section class="content-box">
            <div class="room-chat-scroller" id="message">
                <ul class="room-chat-messages" id="list-info">
                    <li class="room-chat-item">
                        <div class="item-ctn clearfix">
                            <div class="wrap-enter">
                                <span class="info" style="color:#FDC33E">正在连接聊天服务器...</span>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
    </#if>
    <script>


        $('.d2cDownApp').click(function () {
            if (iOS) {
                document.querySelector("#openD2cApp").click();
            } else {
                if (isWeChat || isQQ) $('.brguide').show(); else document.querySelector("#openD2cApp").click();
            }
        })

        var id = $('input[name="id"]').val();
        var height = $(window).height();
        $('#video').height(height);

        //监听自动播放
        var media = document.getElementById("video");
        media.controls = false;
        media.addEventListener('play', function (e) {
            var userAgents = navigator["userAgent"]["toLowerCase"]();
            if (userAgents.indexOf('wifi') < 0) setTimeout(function () {
                $.flashTip({position: 'center', type: 'normal', message: '您不在wifi环境'});
            }, 1500)
            $('.bg-icon').hide();
        }, false);
        media.addEventListener('ended', function (e) {
            media.pause();
        }, false);

        if (iOS) {
            if ((isWeChat || isQQ) && 0 != $("#video").length) {
                try {
                    setTimeout(function () {
                        $("#video")[0].play();
                        document.addEventListener("WeixinJSBridgeReady", function () {
                            $("#video")[0].play();
                        }, false);
                    }, 1000)
                } catch (err) {
                    console.log(err)
                }
            }
        }
        //获取直播详情
        $.get('/zegolive/count/' + id + '.json', function (data) {
            if (data.result.status == 4) {
                var realTime = data.result.datas.realTime;
                var watchcount = realTime.watchingCount;
                var str = '';
                $('.watchcout').html(watchcount)
            }
        });


        //点击播放按钮事件
        $('#players').on('click', function () {
            var media = document.getElementById("video");
            media.play();
            $(this).parent().parent().parent().parent().hide();
        })
        //HTML本地存储判断是否已经关注该设计师
        var vote = JSON.parse(localStorage.getItem('mylive')) || [];
        var id =${live.memberId};
        if ($.inArray(id, vote) != -1) {
            $('.live-like').hide();
        }
        //关注成功回调
        var flSuccess = function (id) {
            vote.push(id);
            localStorage.setItem('mylive', JSON.stringify(vote));
            $('.live-like').hide()
            return false;
        }
        var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
        var ih = parseInt(width) * 0.186;
        $('.live-top').height(ih);


    </script>
    <script>
        new Mlink({
            mlink: "AKGJ",
            button: document.querySelector("a#openD2cApp"),
            autoLaunchApp: false,
            autoRedirectToDownloadUrl: true,
            downloadWhenUniversalLinkFailed: false,
            inapp: false,
            params: {myUrl: '/live/${live.id}'}
        })
    </script>
<#elseif live.status==-1>
    <img src="${picture_base}${(live.cover)!}"
         style="width:100%;position:absolute;top:0;left:0;display:block;z-index:10;height:100%;filter: blur(10px);"
         class="bg-all">
    <div style="width:100%;background-color:#000;opacity:0.7;position:absolute;top:0;left:0;z-index:20" class="all">
        <div>
            <#if live.headPic>
                <img src="<#if live.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${live.headPic}"
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em;z-index:12;"/>
            <#elseif live.thirdHeadPic>
                <img src="${live.thirdHeadPic}" class=""
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png"
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"></img>
            </#if>
            <p class="end-info" style="top:31%;color:#FFF">${live.nickname}</p>
            <p class="end-info" style="top:36%;">${live.title}</p>
            <p class="end-info" style="top:43%;color:#FFF;font-size:20px;">直播已经结束~</p>
            <div class="watchinfo">

            </div>
        </div>
    </div>
    <script>
        var height = $(window).height();
        $('.all').height(height)
    </script>
<#elseif live.status==-2>
    <style>
        body {
            background: rgb(255, 255, 255);
        !important
        }

        .blur {
            filter: url(blur.svg#blur);
            -webkit-filter: blur(15px);
            -moz-filter: blur(15px);
            -ms-filter: blur(15px);
            filter: blur(15px);
            filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=15, MakeShadow=false);
        }

        .live-t {
            position: absolute;
            width: 84%;
            left: 50%;
            margin-left: -42%;
            margin-top: 7.8125rem;
            text-align: center;
        }

        .live-t .live-s {
            position: relative;
            width: 4.5rem;
            height: 4.5rem;
            border-radius: 100%;
            margin: 0 auto;
        }
    </style>
    <img style="width:102%;position:absolute;top:0;left:0;height:100%;margin-left:-1%;margin-right:-1%;"
         src="${picture_base}${(live.cover)!}" class="blur">
    <div class="live-t">
        <div class="live-s">
            <#if live.headPic>
                <img src="<#if live.headPic?index_of('http') lt 0>//img.d2c.cn</#if>${live.headPic}"
                     style="border-radius:100%;width:100%;"/>
            <#elseif live.thirdHeadPic>
                <img src="${live.thirdHeadPic}" class=""
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"/>
            <#else>
                <img src="//static.d2c.cn/img/home/160627/images/headpic.png"
                     style="width:4em;height:4em;position:absolute;border-radius:100%;left:50%;top:15%;margin-left:-2em"></img>
            </#if>
            <span class="rank user_lv" style="width:1rem;height:1rem;"></span>
        </div>
        <p style="margin-top:.5625rem;font-size:.86875rem;color:#FFF;">${live.nickname}</p>
        <p style="margin-top:1.875rem;font-size:1.25rem;color:#FFF;"><#if live.title?length gt 14>${live.title?substring(0,14)}...<#else>${live.title}</#if></p>
        <p style="margin-top:1.25rem;font-size:1.125rem;color:#FD5562;">${live.previewDate?string("MM月dd日  HH:mm")}
            开始直播</p>
        <p>
    </div>
    <a href="javascript:;" class="live-top d2cDownApp" style="display:block;width:100%;position:absolute;bottom:0;"><img
                src="//static.d2c.cn/common/nm/css/img/live_share.jpg" style="width:100%"></a>
<#elseif live.status!=0 && live.status!=4 &&  live.status!=8>
    <header>
        <div class="header fixed">
            <div class="header-back"><a href="//m.d2cmall.com" class="icon icon-back"></a></div>
            <div class="header-title">D2C直播</div>
        </div>
    </header>
    <div style="margin-top:4rem;text-align:center">
        <img src="//static.d2c.cn/common/nm/css/img/404.png" style="width:80%;">
    </div>
    <script>
        $('body').css('background', '#FFF')
    </script>
</#if>

<#if live.status==4>
    <script id="live-productlist" type="text/html">
        <div id="product-c">
            {{each products as item i}}
            <div class="product-item">
                <div class="product-img">
                    <i>{{products.length-i}}</i>
                    <img src="${picture_base}/{{item.img}}!/both/112x144" width="100%" height="100%;">
                </div>
                <div class="product-info">
                    <p class="p-title">{{item.name}}</p>
                    <p class="p-price"><span><i class="f-a">&yen;&nbsp;</i>{{item.minPrice}}</span>{{if item.minPrice <
                        item.originalPrice }}<s>&yen;&nbsp;{{item.originalPrice}}</s>{{/if}}</p>
                </div>
                <div class="forbuy text-center" data-id="{{item.id}}" data-name="{{item.name}}">
                    去购买
                </div>
            </div>
            {{/each}}
        </div>
    </script>
    <script>
        $(function () {
// 初始化
            var userId, token, nickanme, headpic, isLogin;
            RongIMClient.init("cpj2xarlcs58n");
            var chatRoomId = "${live.streamId}"; // 聊天室 Id。
            var count = 0;// 拉取最近聊天最多 50 条。

//获取用户token
            function getstatus() {
                $.get('/live/token.json', function (data) {
                    var token = data.result.datas.token;
                    $.get('/member/info.json', function (res) {
                        var member = res.member;
                        member.token = token;
                        getmember(member);
                    })
                })
            }

//获取用户信息并登录融云
            function getmember(data) {
                console.log(data)
                token = data.token;
                nickanme = data.nickname;
                userId = data.id;
                if (data.headPic == '') {
                    headpic = "//static.d2c.cn/img/home/160627/images/headpic.png"
                } else {
                    headpic = data.headPic;
                }
                getRongIMClient()
            }

            $.getJSON('/member/islogin', function (data) {
                if (data.result.login == false) {
                    login("false");
                } else {
                    login("true");
                }

            });

            function login(data) {
                isLogin = data;
            }

            function getRongIMClient() {
                // 设置连接监听状态 （ status 标识当前连接状态）
                // 连接状态监听器
                RongIMClient.setConnectionStatusListener({
                    onChanged: function (status) {
                        switch (status) {
                            //链接成功
                            case RongIMLib.ConnectionStatus.CONNECTED:
                                // console.log('链接成功');
                                break;
                            //正在链接
                            case RongIMLib.ConnectionStatus.CONNECTING:
                                //console.log('正在链接');
                                break;
                            //重新链接
                            case RongIMLib.ConnectionStatus.DISCONNECTED:
                                console.log('断开连接');
                                break;
                            //其他设备登录
                            case RongIMLib.ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT:
                                console.log('其他设备登录');
                                break;
                            //网络不可用
                            case RongIMLib.ConnectionStatus.NETWORK_UNAVAILABLE:
                                console.log('网络不可用');
                                break;
                        }
                    }
                });
                //必须设置监听器后，再连接融云服务器
                // 消息监听器
// 连接融云服务器。
                RongIMClient.connect(token, {
                    onSuccess: function (userId) {
                        //console.log("Login successfully." + userId);
                        enterChatroom();
                    },
                    onTokenIncorrect: function () {
                        console.log('token无效');
                    },
                    onError: function (errorCode) {
                        var info = '';
                        switch (errorCode) {
                            case RongIMLib.ErrorCode.TIMEOUT:
                                info = '超时';
                                break;
                            case RongIMLib.ErrorCode.UNKNOWN_ERROR:
                                info = '未知错误';
                                break;
                            case RongIMLib.ErrorCode.UNACCEPTABLE_PaROTOCOL_VERSION:
                                info = '不可接受的协议版本';
                                break;
                            case RongIMLib.ErrorCode.IDENTIFIER_REJECTED:
                                info = 'appkey不正确';
                                break;
                            case RongIMLib.ErrorCode.SERVER_UNAVAILABLE:
                                info = '服务器不可用';
                                break;
                        }
                        console.log(errorCode);
                    }
                });
            }

            RongIMClient.setOnReceiveMessageListener({
                // 接收到的消息
                onReceived: function (message) {
                    switch (message.messageType) {
                        case RongIMClient.MessageType.TextMessage:
                            // 发送的消息内容将会被打印
                            MsgChange(message.content, 1);
                            break;
                        case RongIMClient.MessageType.VoiceMessage:
                            // 对声音进行预加载
                            // message.content.content 格式为 AMR 格式的 base64 码
                            RongIMLib.RongIMVoice.preLoaded(message.content.content);
                            break;
                        case RongIMClient.MessageType.ImageMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.DiscussionNotificationMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.LocationMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.RichContentMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.DiscussionNotificationMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.InformationNotificationMessage:
                            MsgChange(message.content, 4)
                            break;
                        case RongIMClient.MessageType.ContactNotificationMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.ProfileNotificationMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.CommandNotificationMessage:
                            if (message.content.data = "OFF" && message.content.name == ":QL") {
                                var media = document.getElementById("video");
                                $('.all').show();
                                $('.bg-all').show();
                                $('.like-userl').hide();
                                $('.content-box').hide();
                            }
                            // do something...
                            break;
                        case RongIMClient.MessageType.CommandMessage:
                            // do something...
                            break;
                        case RongIMClient.MessageType.D2CMessage:
                            // do something..

                            installMsg(message.content, 1);
                            break;
                        case RongIMClient.MessageType.PresentMessage:
                            // do something...
                            installMsg(message.content, 5);
                            break;
                        case RongIMClient.MessageType.UnknownMessage:
                            // do something...
                            break;

                        default:
                        // do something...
                    }
                }
            });


            //进入聊天室
            function enterChatroom() {
                RongIMClient.getInstance().joinChatRoom(chatRoomId, count, {
                    onSuccess: function () {
                        var str = '<li class="room-chat-item"> <div class="item-ctn clearfix"> <div class="wrap-enter"> <span class="info" style="color:#fba432">成功加入聊天室</span></div> </div> </li>'
                        $('#list-info').append(str);
                        if (isLogin == "true") {
                            enterSuccess();
                        }
                        var ii = parseInt($('.watchcout').text())
                        var data = {"watchCount": ii + 1}
                        mesRigter()		//注册自定义消息
                        giftRigter();	//注册礼物消息
                        PersonMessage("joinCharRoom", 3);
                        sendTheMessage(data, 'join')
                    },
                    onError: function (error) {
                        console.log('进入聊天室失败' + error)
                    }
                });
            }

            //注册礼物消息用于监听
            function giftRigter() {
                var messageName = "PresentMessage"; // 消息名称。
                var objectName = "D2C:PresentMessage"; // 消息内置名称，请按照此格式命名。
                var mesasgeTag = new RongIMLib.MessageTag(true, true);// 消息是否保存是否计数，true true 保存且计数，false false 不保存不计数。
                var propertys = ["presentId", "presentUrl", "presentName", "user"]; // 消息类中的属性名。
                RongIMClient.registerMessageType(messageName, objectName, mesasgeTag, propertys);

            }

            //注册自定义消息用于会话
            function mesRigter() {
                var messageName = "D2CMessage"; // 消息名称。
                var objectName = "D2C:TxtMsg"; // 消息内置名称，请按照此格式命名。
                var mesasgeTag = new RongIMLib.MessageTag(true, true);// 消息是否保存是否计数，true true 保存且计数，false false 不保存不计数。
                var propertys = ["userId", "userName", "userPic", "content", "type"]; // 消息类中的属性名。
                RongIMClient.registerMessageType(messageName, objectName, mesasgeTag, propertys);
            }

            //自定义消息类型用于移动端同步聊天消息
            function PersonMessage(text, type) {
                var type = type;    //type区分文本  关注  进入直播间消息  退出直播间  1 2 3 4

                var data = {userId: userId, userName: nickanme, userPic: headpic, content: text, type: type};
                sendTheMessage(data, "normal")
            }

//发送消息 
            function sendTheMessage(data, type) {
                if (type == "join") {
                    var msg = new RongIMLib.CommandNotificationMessage({data: data});
                } else if (type == "buy") {
                    var msg = new RongIMLib.InformationNotificationMessage(data);
                } else {
                    var msg = new RongIMClient.RegisterMessage.D2CMessage(data);
                }
                var conversationtype = RongIMLib.ConversationType.CHATROOM;  //聊天类型为聊天室。
                var targetId = chatRoomId; // 目标 Id
                RongIMClient.getInstance().sendMessage(conversationtype, targetId, msg, {
                        onSuccess: function (message) {

                            //message 为发送的消息对象并且包含服务器返回的消息唯一Id和发送消息时间戳
                            if (type == "normal") installMsg(message.content, 1);
                        },
                        onError: function (errorCode, message) {
                            var info = '';
                            switch (errorCode) {
                                case RongIMLib.ErrorCode.TIMEOUT:
                                    info = '超时';
                                    break;
                                case RongIMLib.ErrorCode.UNKNOWN_ERROR:
                                    info = '未知错误';
                                    break;
                                case RongIMLib.ErrorCode.REJECTED_BY_BLACKLIST:
                                    info = '在黑名单中，无法向对方发送消息';
                                    break;
                                case RongIMLib.ErrorCode.NOT_IN_DISCUSSION:
                                    info = '不在讨论组中';
                                    break;
                                case RongIMLib.ErrorCode.NOT_IN_GROUP:
                                    info = '不在群组中';
                                    break;
                                case RongIMLib.ErrorCode.NOT_IN_CHATROOM:
                                    info = '不在聊天室中';
                                    break;
                                default :
                                    info = 'x';
                                    break;
                            }
                            console.log('发送失败:' + info);
                        }
                    }
                );
            }

            //处理发送成功的消息
            function installMsg(data, num) {
                //num 1文本 4通知 5礼物
                var co = data.content, str, h = $('#message').height(), ih = $('#list-info').height(),
                    i = $('.room-chat-item').size();
                var s = $('.room-chat-scroller').scrollTop();
                switch (num) {
                    case  4:
                        if (co == "joinCharRoom") {
                            str = '<li class="room-chat-item"> <div class="item-ctn enter clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#6DF2E4">' + data.userName + ' 进入了直播间</span></span></div> </div> </li>'
                            changeUser(1);
                        } else if (co == "quitCharRoom") {
                            changeUser(0);
                            return false;
                        } else if (co == "FOLLOW") {
                            str = '<li class="room-chat-item"> <div class="item-ctn enter clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#fba432">' + data.userName + '</span> 关注了主播</span></div> </div> </li>'
                        } else if (co == "SHARE") {
                            str = '<li class="room-chat-item"> <div class="item-ctn enter clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#fba432">' + data.userName + '</span> 分享了直播</span></div> </div> </li>'
                        } else if (co == "BUY") {
                            str = '<li class="room-chat-item"> <div class="item-ctn enter clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#FDC33E">' + data.userName + '</span>正在去买</span></div> </div> </li>'
                        }
                        break;
                    case 1:
                        if (co == "joinCharRoom") {
                            str = '<li class="room-chat-item"> <div class="item-ctn enter clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#fba432">' + data.userName + '</span> 进入了直播间</span></div> </div> </li>'
                            changeUser(1);
                        } else if (co == "quitCharRoom") {
                            changeUser(0);
                            return false;
                        } else {
                            str = '<li class="room-chat-item"> <div class="item-ctn clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#96CEF5">' + data.userName + '</span>:' + co + '</span></div> </div> </li>'
                        }
                        break;
                    case 5:
                        str = '<li class="room-chat-item"> <div class="item-ctn enter clearfix"> <div class="wrap-enter"> <span class="info"><span style="color:#fba432">' + data.user.name + '</span> 送出了' + data.presentName + '</span></div> </div> </li>'
                        break;
                }
                $('#list-info').append(str);
                var hs = $('.room-chat-item:last').height();
                if (h <= ih) {
                    $('.room-chat-scroller').animate({scrollTop: hs + ih}, 400);
                }
                if (i == 20) {
                    $('.room-chat-item:first').remove();
                }

            }

            //进入聊天室成功回调
            function enterSuccess() {
                $.ajax({
                    'url': '/live/in/${live.id}.json',
                    'type': 'post',
                    'success': function (data) {
                        //进入成功
                    }
                });
                return false;
            }

            //转换用户格式
            function MsgChange(data, type) {
                var userId = data.user.id;
                var userName = data.user.name;
                var content = data.content ? data.content : data.message;
                var type = type;
                var datas = {userId: userId, userName: userName, content: content}
                installMsg(datas, type);
            }

            //发送文本消息
            $('.send-msg').click(function () {
                var text = $('input[name="content"]').val();
                if (isLogin == "true") {
                    if (text != '' && text.length <= 32) {
                        PersonMessage(text, 1);
                        $('input[name="content"]').val('');
                        $('.wrap-menu').css('background', 'transparent').find('.w-input').removeClass('on');
                        $('.line-qa').hide();
                        $('.send-msg').hide();
                        $('.gobuy').show();
                        $('.share-btn').show();
                    } else if (text.length > 32) {
                        alert('不能超过32字哟~')
                    }
                } else {
                    document.querySelector(".enter_live_room").click();
                }

            })

            //进入退出直播间人数加减
            function changeUser(type) {
                var i = parseInt($('.watchcout').text());
                type == 0 ? $('.watchcout').text(--i) : $('.watchcout').text(++i)
            }

            var timer = true;
            //点击购买
            $('.gobuy').on('click', function (e) {
                if (timer == false) return false;
                timer = false;
                $.get("/live/recommend/list?liveId=${live.id}", function (e) {
                    if (e.result.status == 1) {
                        if (e.result.datas.products.length > 0) {
                            var data = e;
                            var html = template('live-productlist', data.result.datas);
                            $('.product-list').append(html).show();

                        } else {
                            $.flashTip({position: 'center', type: 'error', message: '暂无商品'});
                        }
                    } else {
                        $.flashTip({position: 'center', type: 'error', message: e.result.message});
                    }
                    timer = true;
                }, 'JSON');
                return false;
            })
            //点击购买

            $(document).on(click_type, '.forbuy', function () {
                var id = $(this).attr('data-id');
                var url = "/product/" + id;
                var name = $(this).attr('data-name');
                var data = {
                    message: 'BUY',
                    user: {icon: headpic, id: userId, name: nickanme},
                    extra: {productId: id, productName: name}
                }
                sendTheMessage(data, "buy");
                location.href = url;
                return false;
            })
            //点击视频隐藏商品
            $('video,.bg-icon').on(click_type, function () {
                $('#product-c').remove();
                $('.product-list').hide();
                $('.wrap-menu').css('background', 'transparent').find('.w-input').removeClass('on');
                $('.line-qa').hide();
                $('.send-msg').hide();
                $('.gobuy').show();
                $('.share-btn').show();
            })

            //退出成功回调
            function outroom() {
                $.ajax({
                    'url': '/live/out/${live.id}.json',
                    'type': 'post',
                    'async': false,
                    'success': function (data) {
                        //退出成功
                    }
                });
                return false;
            }

            $('#content').click(function () {
                setTimeout(function () {
                    $("#content").focus();
                }, 0);
            })

            //监听输入框焦点
            $("#content").focus(function () {
                $('.wrap-menu').css('background', '#FFF').find('.w-input').addClass('on');
                $('.line-qa').show();
                $('.send-msg').show();
                $('.gobuy').hide();
                $('.share-btn').hide();
            });

            $('.share-btn').on('click', function () {
                if (isWeChat || isQQ) {
                    $('.txguide').show();
                } else {
                    var title = $('share').attr('data-title') || document.title,
                        desc = $('share').attr('data-desc') || document.title,
                        pic = $('share').attr('data-pic'),
                        url = $('share').attr('data-url') || document.location.href;
                    location.href = '//service.weibo.com/share/share.php?url=' + encodeURIComponent(url) + '&title=' + encodeURIComponent(desc) + '&pic=' + encodeURIComponent(pic) + '&appkey=&searchPic=false';
                }
            })

            //监听页面onbeforeunload事件
            window.unonload = function () {
                PersonMessage('quitCharRoom', 4);
                outroom();
            }

            getstatus();

        })
    </script>
</#if>



<@m.page_footer />