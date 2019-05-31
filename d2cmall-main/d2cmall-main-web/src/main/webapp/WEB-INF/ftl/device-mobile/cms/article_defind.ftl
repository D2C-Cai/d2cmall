<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='${article.title}' js='utils/iSlider' css='iSlider' keywords='预售,预售优惠,服装设计师,原创品牌' description='${article.shareContent}'/>
<#if !m.FROMAPP>
    <@m.app_download_bar />
</#if>
<div style="position:relative;height:100%;">
    <#if !m.FROMAPP>
        <div class="return-home">
            <a href="/">回首页</a>
        </div>
    </#if>


    <style>
        .online-chat {
            display: none;
        }

        .clearfix {
            position: relative;
            width: 100%;
            height: 100%;
        }

        .islide-item {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

        .islide-item-1 {
            background: url(//static.d2c.cn/img/topic/171130/time-machine/time_1.jpg) no-repeat;
            background-size: 100%;
        }

        .islide-item-2 {
            background: url(//static.d2c.cn/img/topic/171130/time-machine/time_2.jpg) no-repeat;
            background-size: 100%;
        }

        .islide-item-3 {
            background: url(//static.d2c.cn/img/topic/171130/time-machine/time_3.jpg) no-repeat;
            background-size: 100%;
        }

        .islide-item-4 {
            background: url(//static.d2c.cn/img/topic/171130/time-machine/time_4.jpg) no-repeat;
            background-size: 100%;
        }

        .islide-item-5 {
            background: url(//static.d2c.cn/img/topic/171130/time-machine/time_5.jpg) no-repeat;
            background-size: 100%;
        }

        .islide-item-7 {
            background: url(//static.d2c.cn/img/topic/171130/time-machine/time_6.jpg) no-repeat;
            background-size: 100%;
        }

        .time-member {
            position: absolute;
            top: 8%;
            left: 10%;
            color: #000;
        }

        .time-member .nickname {
            font-size: 24px;
        }

        .time-member .jointime {
            font-size: 22px;
            font-weight: bold;
            margin-top: 6%;
        }

        .time-list {
            position: absolute;
            top: 20%;
            right: 10%;
            line-height: 200%;
            font-size: 20px;
            font-weight: bold;
            text-align: center;
            color: #000;
        }

        .time-cost {
            position: absolute;
            top: 20vh;
            left: 10%;
            color: #cc7435;
            font-size: 40px;
        }

        .time-cost .sumCost {
            margin-top: 14vh;
        }

        .time-cost .rank {
            margin-top: 11vh;
        }

        .time-product {
            position: absolute;
            top: 40%;
            left: 10%;
            line-height: 200%;
            color: #000;
        }

        .time-product .or-time {
            font-size: 20px;
            font-weight: bold;
        }

        .time-product .or-title {
            font-size: 24px;
        }

        .time-product .or-product {
            font-size: 18px;
            font-weight: bold;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            line-height: 36px;
            max-height: 36px;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
        }

        .time-product .or-price {
            font-size: 24px;
            font-weight: bold;
        }

        .goto {
            display: block;
            position: absolute;
            left: 0;
            right: 0;
            bottom: 0;
            top: 0;
        }

        .fullscreen-loading {
            display: block;
            position: fixed;
            z-index: 9;
            left: 0;
            right: 0;
            bottom: 0;
            top: 0;
            background: #FFF url(//static.d2c.cn/img/topic/171130/time-machine/loading.gif) center center no-repeat;
        }

        .iphonex .time-cost {
            top: 13vh
        }
    </style>
    <div class="wrap" id="wrap">
        <div class="islide-item islide-item-1"></div>
        <div class="islide-item islide-item-2">
            <div class="time-member">
                <p class="nickname"></p>
                <p class="jointime"></p>
            </div>
        </div>
        <div class="islide-item islide-item-3">
            <div class="time-list"></div>
        </div>
        <div class="islide-item islide-item-4">
            <div class="time-cost">
                <p class="sumCost"></p>
                <p class="rank"></p>
            </div>
        </div>
        <div class="islide-item islide-item-5">
            <div class="time-product">
                <p class="or-time"></p>
                <p class="or-title">您购买了一件</p>
                <p class="or-product"></p>
                <p class="or-price"></p>
            </div>
        </div>
        <div class="islide-item islide-item-6" id="vip-pic"></div>
        <div class="islide-item islide-item-7" id="end-pic"></div>
    </div>
    <!-- 加载提示 _S -->
    <div class="fullscreen-loading"></div>

    <share data-title="D2C VIP独家记忆" data-desc="给大家介绍一下，这是我在D2C全球好设计的Fashion Timeline"
           data-pic="http://static.d2c.cn/common/nm/img/icon_launcher.png"></share>

    <script>

        function shareToFriend() {
            var html = '<div class="share-wechat-guide" onclick="closeShare()"><a href="javascript:closeShare()" class="mask-close"></a></div>';
            $('body').append(html);
            setTimeout(function () {
                $('.share-wechat-guide').fadeIn();
            }, 10);
        }

        function closeShare() {
            $('.share-wechat-guide').fadeOut(function () {
                $(this).remove();
            });
        }

        $(function () {
            //获取时间
            var formatTime = function (dates) {
                var date = new Date(dates);
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                var day = date.getDate();
                return [year, month, day].join("/");
            };
            //获取url
            var getUrlParam = function (name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = location.search.substr(1).match(reg);  //匹配目标参数
                if (r != null) return unescape(r[2]);
                return null; //返回参数值
            };
            //获取记忆数据
            var getInformation = function (url) {
                $.getJSON(url, function (data) {
                    setTimeout(function () {
                        $('.fullscreen-loading').hide();
                    }, 200);
                    var nickname = data.memberInfo.nickname;
                    var cost = data.info.sumCost;
                    var rank = data.info.rank;
                    var jointime = formatTime(data.memberInfo.createDate);
                    var brandsName = data.info.brandsName;

                    if (brandsName.indexOf(",") != -1) {
                        var brandsNameArray = brandsName.split(",");
                        var str = "";
                        $.each(brandsNameArray, function (i, obj) {
                            if (i <= 3) {
                                str += '<p>' + obj + '</p>';
                            }
                        });
                    } else {
                        str = "<p>" + brandsName + "</p>";
                    }
                    $(".time-list").append(str);
                    if (data.info.MaxCostOrderItem) {
                        var ordertime = formatTime(data.info.MaxCostOrderItem.createDate);
                        var orderproduct = data.info.MaxCostOrderItem.productName;
                        var orderproductprice = data.info.MaxCostOrderItem.productPrice;
                    } else {
                        var ordertime = "";
                        var orderproduct = "";
                        var orderproductprice = "";
                    }
                    $(".nickname").text('亲爱的' + nickname);
                    $(".jointime").text(jointime);
                    $(".sumCost").text(cost + '元');
                    $(".rank").text(rank + '%');
                    $(".or-time").text(ordertime);
                    $(".or-product").text(orderproduct);
                    $(".or-price").text('价值' + orderproductprice + '元');
                    if (data.info) {
                        var i;
                        if (cost < 30000 && cost >= 8000) {
                            i = 1;
                        } else if (cost >= 30000) {
                            i = 2;
                        } else {
                            i = 3;
                        }
                        $("#vip-pic").css({
                            "background-image": "url(//static.d2c.cn/img/topic/171130/time-machine/time_special_" + i + ".jpg)",
                            "background-size": "100%"
                        });
                    }
                    if (id != _memberId) {
                        $("#end-pic").css({
                            "background-image": "url(//static.d2c.cn/img/topic/171130/time-machine/miaowu.jpg)",
                            "background-size": "100%"
                        });
                        $("#end-pic").html('<a href="/page/1212miaowujie" class="goto"></a>');

                    } else {
                        $("#end-pic").html('<a href="javascript:shareToFriend();" class="goto"></a>');
                    }
                    setTimeout(function () {
                        var myslider = new iSlider({
                            wrap: '#wrap',
                            item: '.islide-item'
                        });

                    }, 100);

                });
            };
            var id = getUrlParam("memberId");//获取id
            if (!id) {
                if (_memberId) {
                    location.href = "?memberId=" + _memberId;
                    getInformation("/myinfo1212");
                } else {
                    userLogin();
                    return false;
                }
            } else {
                var strUrl = "/myinfo1212" + "?memberId=" + id;
                getInformation(strUrl);

            }

        })
    </script>


</div>
<script>
    if (!document.referrer) $('.return-home').show();
</script>
<@m.page_footer menu=true />