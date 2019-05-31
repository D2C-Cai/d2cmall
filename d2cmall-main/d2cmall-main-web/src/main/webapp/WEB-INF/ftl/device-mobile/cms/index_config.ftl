<@m.page_nav_bar />
<style>
    .zs-m-main a, .zs-m-main div {
        line-height: 0%;
    }
</style>
<div class="swipe-wrap clearfix">
    <ul id="swipe-img" class="swipe-img">
        <li class="swipe-item">
            <a href="/crowds/crowditemlist?crowdId=47"><img src="//static.d2c.cn/img/mobile/banner/0529_01.jpg" alt=""/></a>
            <a href="/product/101586"><img src="//static.d2c.cn/img/mobile/banner/0526_02.jpg" alt=""/></a>
            <a href="/product/102242"><img src="//static.d2c.cn/img/mobile/banner/061301.jpg" alt=""/></a>
            <a href="/promotion/19"><img src="//static.d2c.cn/img/mobile/banner/061801.jpg" alt=""/></a>
            <a href="/showroom/designer/10052"><img src="//static.d2c.cn/img/mobile/banner/061805.jpg" alt=""/></a>
        </li>
    </ul>
    <ul id="swipe-position">
        <li class="on"></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>
</div>
<div class="wrap-main">
    <div class="list-main lazyload">
        <h3 style="color:#FF3300">下单 → 生产 → 验货 → 发货 → 线上线下恢复正价</h3>
        <#if  doingPager.totalCount &gt; 0 >
            <#list doingPager.list as crowd>
                <a href="/crowds/crowditemlist?crowdId=${crowd.id}" class="crowd-item">
                    <span class="img"><img src="${static_base}/m/img/blank.gif"
                                           data-src="${picture_base}${(crowd.pcPic)!}" alt=""/></span>
                    <span class="title">${crowd.name}</span>
                    <span class="btn">立刻抢定</span>
                    <span class="count-down" data-startTime="${crowd.beginCrowd?string("yyyy/MM/dd HH:mm:ss")}"
                          data-endTime="${crowd.endCrowd?string("yyyy/MM/dd HH:mm:ss")}"
                          data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
                </a>
            </#list>
        </#if>
        <a href="/promotion/18" class="crowd-item">
            <span class="img"><img src="${static_base}/m/img/blank.gif"
                                   data-src="//static.d2c.cn/img/home/0617/prealse_0617_01.jpg"
                                   alt="Annakiki For Dotacoko 池塘系列 满999减100"/></span>
            <span class="title">Annakiki For Dotacoko 池塘系列 满999减100</span>
            <span class="btn">立刻抢定</span>
            <span class="count-down" data-startTime="2014/06/17 10:00:00" data-endTime="2014/06/24 10:00:00"
                  data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
        </a>
        <a href="/product/101586" class="crowd-item">
            <span class="img"><img src="${static_base}/m/img/blank.gif"
                                   data-src="//img.d2c.cn/cd/43/43/ff4534c51513537bc9ddf2baca7980cf"
                                   alt="5.20 SIMULATION 发光鞋 限量销售"/></span>
            <span class="title">SIMULATION 发光鞋 限量售卖</span>
            <span class="btn">立刻抢定</span>
            <span class="count-down" data-startTime="2014/05/27 10:00:00" data-endTime="2014/06/24 10:00:00"
                  data-serverTime="${.now?string("yyyy/MM/dd HH:mm:ss")}"></span>
        </a>
    </div>
</div>
<div class="lazyload clearfix zs-m-main">
    <div class="float-left" style="width:45%;"><a href="/product/101911"><img
                    src="//static.d2c.cn/img/mobile/0603/0529_01_01.jpg" alt="" style="width:100%;"/></a></div>
    <div class="float-left" style="width:55%;"><a href="/product/102152"><img
                    src="//static.d2c.cn/img/mobile/0603/0529_01_02.jpg" alt="" style="width:100%;"/></a></div>
    <div class="float-left clearfix" style="width:60%;">
        <img src="//static.d2c.cn/img/mobile/0603/0603_02.jpg" alt="" style="width:100%;"/>
        <a href="/product/101911"><img src="//static.d2c.cn/img/mobile/0603/0603_04.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>
    <div class="float-left" style="width:40%;">
        <a href="/product/101911"><img src="//static.d2c.cn/img/mobile/0603/0603_03.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>

    <div class="clearfix"><a href="/product/101911"><img src="//static.d2c.cn/img/mobile/0603/0603_05.jpg" alt=""
                                                         style="width:100%;"/></a></div>
    <div class="clearfix"><a href="/product/101911"><img src="//static.d2c.cn/img/mobile/0603/0603_06.jpg" alt=""
                                                         style="width:100%;"/></a></div>
    <div class="clearfix"><img src="//static.d2c.cn/img/mobile/0620/0603_07.jpg" alt="" style="width:100%;"/></div>
    <div class="float-left clearfix" style="width:40%;"><a href="/product/101907"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_07.jpg" alt="" style="width:100%;"/></a></div>
    <div style="width:60%;" class="float-left">
        <a href="/product/101902"><img src="//static.d2c.cn/img/mobile/0603/0603_08.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101905"><img src="//static.d2c.cn/img/mobile/0603/0603_09.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>

    <div class="float-left clearfix" style="width:50%;"><a href="/product/101920"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_10.jpg" alt="" style="width:100%;"/></a></div>
    <div style="width:50%;" class="float-left">
        <a href="/product/101918" style="width:100%;"><img src="//static.d2c.cn/img/mobile/0603/0603_11.jpg" alt=""
                                                           style="width:100%;"/></a>
        <a href="/product/101924" style="width:100%;"><img src="//static.d2c.cn/img/mobile/0603/0603_12.jpg" alt=""
                                                           style="width:100%;"/></a>
    </div>

    <div style="width:40%;" class="float-left clearfix">
        <a href="/product/101904"><img src="//static.d2c.cn/img/mobile/0603/0603_13.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101912" style="width:40%;"><img src="//static.d2c.cn/img/mobile/0603/0603_15.jpg" alt=""
                                                          style="width:100%;"/></a>
    </div>
    <div class="float-left" style="width:60%;"><a href="/product/101911"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_14.jpg" alt="" style="width:100%;"/></a></div>

    <div class="float-left" style="width:50%;clearfix">
        <a href="/product/101929"><img src="//static.d2c.cn/img/mobile/0603/0603_16.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101926"><img src="//static.d2c.cn/img/mobile/0603/0603_19.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>
    <div class="float-left" style="width:50%;">
        <a href="/product/101929"><img src="//static.d2c.cn/img/mobile/0603/0603_17.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101926"><img src="//static.d2c.cn/img/mobile/0603/0603_18.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>

    <div class="float-left clearfix" style="width:50%;">
        <a href="/product/101931"><img src="//static.d2c.cn/img/mobile/0603/0603_20.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101926"><img src="//static.d2c.cn/img/mobile/0603/0603_22.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>
    <div class="float-left" style="width:50%;"><a href="/product/102025"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_21.jpg" alt="" style="width:100%;"/></a></div>

    <div class="float-left clearfix" style="width:40%;"><a href="/product/101917"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_23.jpg" alt="" style="width:100%;"/></a></div>
    <div class="float-left" style="width:60%;"><a href="/product/101899"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_24.jpg" alt="" style="width:100%;"/></a></div>

    <div class="float-left clearfix" style="width:50%;"><a href="/product/101925"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_25.jpg" alt="" style="width:100%;"/></a></div>
    <div class="float-left" style="width:50%;"><a href="/product/101928"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_26.jpg" alt="" style="width:100%;"/></a></div>

    <div class="float-left clearfix" style="width:40%;"><a href="/product/101927"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_27.jpg" alt="" style="width:100%;"/></a></div>
    <div class="float-left" style="width:60%;"><a href="/product/101922"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_28.jpg" alt="" style="width:100%;"/></a></div>

    <div class="float-left clearfix" style="width:40%;">
        <a href="/product/101909"><img src="//static.d2c.cn/img/mobile/0603/0603_29.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101898"><img src="//static.d2c.cn/img/mobile/0603/0603_32.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>
    <div class="float-left" style="width:20%;"><a href="/product/101909"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_30.jpg" alt="" style="width:100%;"/></a></div>
    <div class="float-left" style="width:40%;">
        <a href="/product/101901"><img src="//static.d2c.cn/img/mobile/0603/0603_31.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101898"><img src="//static.d2c.cn/img/mobile/0603/0603_33.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>

    <div class="float-left clearfix" style="width:40%;">
        <a href="/product/101906"><img src="//static.d2c.cn/img/mobile/0603/0603_34.jpg" alt=""
                                       style="width:100%;"/></a>
        <a href="/product/101910"><img src="//static.d2c.cn/img/mobile/0603/0603_36.jpg" alt=""
                                       style="width:100%;"/></a>
    </div>
    <div class="float-left" style="width:60%;"><a href="/product/101900"><img
                    src="//static.d2c.cn/img/mobile/0603/0603_35.jpg" alt="" style="width:100%;"/></a></div>

    <div class="clearfix" style="width:100%;"><img src="//static.d2c.cn/img/mobile/0603/0603_37.jpg" alt=""
                                                   style="width:100%;"/></div>
    <div class="clearfix" style="width:100%;"><img src="//static.d2c.cn/img/mobile/0603/0603_38.jpg" alt=""
                                                   style="width:100%;"/></div>
    <div class="clearfix" style="width:100%;"><img src="//static.d2c.cn/img/mobile/0603/0603_39.jpg" alt=""
                                                   style="width:100%;"/></div>
    <div class="clearfix" style="width:100%;"><img src="//static.d2c.cn/img/mobile/0603/0603_40.jpg" alt=""
                                                   style="width:100%;"/></div>
    <div class="clearfix" style="width:100%;"><a href="/showroom"><img
                    src="//static.d2c.cn/img/mobile/0617/showroom-bannar.jpg" alt="" style="width:100%;"/></a></div>
</div>
<script>
    var elem = document.getElementById('swipe-img');
    window.mySwipe = Swipe(elem, {
        auto: 3000,
        callback: function (pos) {
            var i = bullets.length;
            while (i--) {
                bullets[i].className = ' ';
            }
            bullets[pos].className = 'on';
        }
    });
    var bullets = document.getElementById('swipe-position').getElementsByTagName('li');
</script>