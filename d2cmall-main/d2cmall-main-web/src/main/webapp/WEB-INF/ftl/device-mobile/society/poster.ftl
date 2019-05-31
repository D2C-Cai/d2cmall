<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='测一测你的心仪美衣,D2C送给你' css="poster" description="测一测你的心仪美衣,D2C送给你"/>
<#assign question1=[{'name':'白羊座','time':'3.21-4.19','key':2},{'name':'金牛座','time':'4.20-5.20','key':3},{'name':'双子座','time':'5.21-6.21','key':4},{'name':'巨蟹座','time':'6.22-7.22','key':1},{'name':'狮子座','time':'7.23-8.22','key':2},{'name':'处女座','time':'8.23-9.22','key':3},{'name':'天秤座','time':'9.23-10.23','key':4},{'name':'天蝎座','time':'10.24-11.22','key':1},{'name':'射手座','time':'11.23-12.23','key':2},{'name':'摩羯座','time':'12.22-1.19','key':3},{'name':'水瓶座','time':'1.20-2.18','key':4},{'name':'双鱼座','time':'2.19-3.20','key':1}]>
<#assign question2=[{'pic':'//static.d2c.cn/img/topic/181031/posters/q_01.jpg','name':'条纹','key':1},{'pic':'//static.d2c.cn/img/topic/181031/posters/q_02.jpg','name':'几何','key':1},{'pic':'//static.d2c.cn/img/topic/181031/posters/q_03.jpg','name':'图案','key':2},{'pic':'//static.d2c.cn/img/topic/181031/posters/q_04.jpg','name':'字母','key':2},{'pic':'//static.d2c.cn/img/topic/181031/posters/q_05.png','name':'纯色','key':3}]>
<#assign question3=[{'name':'文学','title':'了解过去，认识自己，预见未来'},{'name':'音乐','title':'音乐是思维者的声音'},{'name':'运动','title':'生命的滋润剂，青春的美容师'},{'name':'休闲','title':'闲暇不是心灵的充实，而是为了心灵得到休息'},{'name':'美食','title':'我们爱DE不是食物本身，而是一起分享的那个人'}]>
<#assign question4=[{'name':'浪漫'},{'name':'优雅'},{'name':'古典'},{'name':'摩登'},{'name':'甜美'},{'name':'简约'}]>
<#assign question5=[{'name':'羽绒'},{'name':'大衣'},{'name':'毛衣'},{'name':'外套'},{'name':'连衣裙'}]>

<div class="page" id="page">
    <div class="game-begin">
        <img src="//static.d2c.cn/img/topic/181031/posters/first.jpg?2222" style="width:100%;margin:auto;"/>
        <div class="begin-button">立即答题</div>
        <a href="/page/posterRule" class="rulelink">活动规则</a>
    </div>
    <div style="display:none" class="ques-con">
        <div class="question-progress">
            <div class="question-line"></div>
            <div class="question-num">
                <span class="on" style="left:-1%;">1</span>
                <span style="left:22%">2</span>
                <span style="left:44%">3</span>
                <span style="right:22%">4</span>
                <span style="right:-1%">5</span>
            </div>
        </div>
        <div class="question-content">
            <div class="question-title">
                <img src="//static.d2c.cn/img/topic/181031/posters/a_01.png" style="width:100%;">
            </div>
            <div class="question-ans">
                <#list question1 as item>
                    <div class="question-line question-ans-item constellation" data-index="${item.key}" data-qindex="0"
                         data-constellation="${item_index+1}">
                        <div class="radio-box"><input class="magic-radio" type="radio" name="radio"
                                                      id="r${item_index}"/></div>
                        <label class="item-name" for="r${item_index}"><p>${item.name}</p>
                            <p>${item.time}</p></label>
                    </div>
                </#list>
            </div>
        </div>
        <div class="question-content">
            <div class="question-title">
                <img src="//static.d2c.cn/img/topic/181031/posters/a_02.png" style="width:100%;">
            </div>
            <div class="question-ans">
                <#list question2 as item>
                    <div class="question-block question-ans-item" data-index="${item.key}" data-qindex="1">
                        <label for="a${item_index}"><img src="${item.pic}" style="width:100%"/></label>
                        <div class="radio-box" style="margin-top:5px;display:block"><input class="magic-radio"
                                                                                           type="radio" name="radio"
                                                                                           id="a${item_index}"/>${item.name}
                        </div>
                    </div>
                </#list>
            </div>
        </div>
        <div class="question-content">
            <div class="question-title">
                <img src="//static.d2c.cn/img/topic/181031/posters/a_03.png" style="width:100%;">
            </div>
            <#list question3 as item>
                <div class="question-teta question-ans-item" data-index="${item_index+1}" data-qindex="2">
                    <div class="radio-line">
                        <input class="magic-radio" type="radio" name="radio" id="b${item_index}"/>
                        <label class="option-button" for="b${item_index}">${item.name}</label>
                    </div>
                    <p class="sub-title">${item.title}</p>
                </div>
            </#list>
        </div>
        <div class="question-content">
            <div class="question-title">
                <img src="//static.d2c.cn/img/topic/181031/posters/a_04.png" style="width:100%;">
            </div>
            <#list question4 as item>
                <div class="question-teta question-ans-item" data-index="${item_index+1}" data-qindex="3">
                    <div class="radio-line">
                        <input class="magic-radio" type="radio" name="radio" id="c${item_index}"/>
                        <label class="option-button" for="c${item_index}">${item.name}</label>
                    </div>
                </div>
            </#list>
        </div>
        <div class="question-content">
            <div class="question-title">
                <img src="//static.d2c.cn/img/topic/181031/posters/a_05.png" style="width:100%;">
            </div>
            <#list question5 as item>
                <div class="question-teta question-ans-item" data-index="${item_index+1}" data-qindex="4">
                    <div class="radio-line">
                        <input class="magic-radio" type="radio" name="radio" id="d${item_index}"/>
                        <label class="option-button" for="d${item_index}">${item.name}</label>
                    </div>
                </div>
            </#list>
        </div>
        <div class="question-next-button" onclick="TDAPP.onEvent('海报下一题')">
            下一题
        </div>
        <div class="seeResult" style="display:none" onclick="TDAPP.onEvent('海报提交答案')">
            确定
        </div>
    </div>
</div>
<div class="my-resultbill" style="display:none">
    <div class="img">
        <img src="" style="width:100%" class="into"/>
        <div class="mask-title"><span class="title"></span><span class="symbol">RMB</span><span class="price">元</span>
        </div>
        <div class="refresh"></div>
    </div>
    <div class="praise">
        <img src="//static.d2c.cn/img/topic/181031/posters/a_06.png" style="width:100%;">
    </div>
    <div class="praise-button" style="background: #ff0101;width: 50%;color: #fff;border: none;">
        确定
    </div>
    <div style="text-align:center;font-size:12px;margin:10px;">确定之后无法更改哟~</div>
    <div class="reset" onclick="TDAPP.onEvent('海报重新答题')">重新答题</div>
</div>
<div class="product-detail" style="background:#fff;display:none;padding-top:20px">
    <div class="t-title">
        <span>图文详情</span>
    </div>
    <div id="product-content">

    </div>
</div>
<share data-pic="//static.d2c.cn/img/topic/180915/fresh/images/fresh.png?111"></share>
<script>
    var poster = {
        ansList: [],
        products: [],
        choices: [],
        productId: null,
        back: null,
        index: 0,
        official: 1,
        currentIndex: 0,
        watch: function () {
            var that = this;
            //开始答题按钮
            $('.begin-button').on('click', function () {
                $('.game-begin').hide();
                $('.ques-con').show()
                $('.question-content').eq(0).show()
            })
            //选项
            $('.question-ans-item').on('click', function () {
                var index = $(this).attr('data-index');
                var qindex = parseInt($(this).attr('data-qindex'))
                that.ansList[qindex] = parseInt(index)
                if ($(this).hasClass('constellation')) {
                    var official = $(this).attr('data-constellation')
                    that.official = official
                }
            })
            //下一题
            $('.question-next-button').on('click', function () {
                var index = that.index;
                if (that.ansList[index]) {
                    $('.question-content').eq(index + 1).show().siblings('.question-content').hide();
                    $('.question-num span').eq(index + 1).addClass('on').siblings().removeClass('on');
                    document.body.scrollTop = document.documentElement.scrollTop = 0;
                    if (that.index == 3) {
                        $('.question-next-button').hide();
                        $('.seeResult').show()
                    }
                    that.index++;
                } else {
                    toast({position: 'center', type: 'error', message: '亲还未有选择答案呢~'});
                }
            })
            //查看结果
            $('.seeResult').on('click', function () {
                var constellation = that.ansList[0];
                if (that.ansList.length != 5) {
                    toast({position: 'center', type: 'error', message: '亲还未有选择答案呢~'});
                } else {
                    var data = {
                        'constellation': that.ansList[0],
                        'pattern': that.ansList[1],
                        'interest': that.ansList[2],
                        'style': that.ansList[3],
                        'category': that.ansList[4]
                    };
                    $.ajax({
                        'url': '/member/poster/select.json',
                        'type': 'post',
                        'data': data,
                        'dataType': 'json',
                        'success': function (res) {
                            if (res.result.status == 1 && res.result.datas && res.result.datas.hasOwnProperty('products') && res.result.datas.products.length > 0) {
                                var products = res.result.datas.products;
                                that.products = products.sort(function (a, b) {
                                    return a.id - b.id
                                });
                                that.choices = res.result.datas.choices.sort(function (a, b) {
                                    return parseInt(a.id) - parseInt(b.id)
                                });
                                that.showResult();
                            } else {
                                toast({
                                    position: 'center',
                                    type: 'error',
                                    message: res.result.message ? res.result.message : '亲，出错了哟'
                                });
                            }
                        }
                    })
                }


            })
            //点击刷新
            $('.refresh').on('click', function () {
                if (that.products.length <= 1) {
                    toast({position: 'center', type: 'error', message: '亲,没有匹配到更多商品了'});
                } else {
                    that.showResult();
                    that.getProductDetail()
                }

            })
            //重新答题
            $('.reset').on('click', function () {
                that.reset();
            })
            //生成海报
            $('.praise-button').on('click', function () {
                $.getJSON('/member/islogin', function (data) {
                    if (data.result.login == false) {
                        userLogin();
                    } else {
                        var currentIndex = that.currentIndex - 1 < 0 ? 0 : that.currentIndex - 1;
                        var id = that.products[currentIndex].id;
                        var material;
                        that.choices.map(function (value, index) {
                            if (value.id == id) {
                                material = that.choices[index]
                            }
                        })
                        that.creatPoster(material)
                    }
                })
            })
        },
        getProductDetail: function () {
            var url = '/product/detail/' + this.productId + '?type=ajax'
            $.get(url, function (res) {
                $('#product-content').html(res)
            });
        },
        showResult: function () {
            var length = this.products.length;
            var index = this.currentIndex;
            if (length > 1 && index + 1 <= length) {
                $('#page').hide();
                $('.my-resultbill').find('.into').attr('src', this.choices[index].pic);
                $('.mask-title').find('.title').text(this.products[index].name)
                $('.mask-title').find('.price').text(this.products[index].minPrice + '元')
                this.productId = this.products[index].id;
                this.back = this.choices[index].back
                setTimeout(function () {
                    $('.my-resultbill').show();
                    $('.product-detail').show()
                }, 500)
                this.currentIndex++;
                this.getProductDetail();
            } else {
                $('#page').hide();
                $('.my-resultbill').find('.into').attr('src', this.choices[0].pic);
                $('.mask-title').find('.title').text(this.products[0].name)
                $('.mask-title').find('.price').text(this.products[0].minPrice + '元');
                this.productId = this.products[0].id;
                this.back = this.choices[0].back
                setTimeout(function () {
                    $('.my-resultbill').show();
                    $('.product-detail').show();
                }, 500)
                this.currentIndex = length == 1 ? 0 : 1;
                this.getProductDetail();
            }
        },
        reset: function () {
            this.index = 0;
            this.ansList = [];
            $('.question-content').eq(0).show().siblings('.question-content').hide();
            $('.question-num span').eq(0).addClass('on').siblings().removeClass('on');
            $('.question-next-button').show();
            $('.seeResult').hide()
            $('#page').show();
            $('.my-resultbill').hide()
            $('.product-detail').hide()
        },
        creatPoster: function (material) {
            var that = this;
            var data = {'productId': this.productId, 'pic': this.back}
            $.ajax({
                'url': '/member/poster/create',
                'type': 'post',
                'data': data,
                'dataType': 'json',
                'success': function (res) {
                    if (res.result.status == 1) {
                        var posterId = res.result.datas.memberPoster.id;
                        toast({position: 'center', type: 'success', message: '创建专属海报成功！'});
                        setTimeout(function () {
                            location.href = "/member/poster/mine?picId=" + that.official + "&posterId=" + posterId
                        }, 500)
                    } else {
                        toast({
                            position: 'center',
                            type: 'error',
                            message: res.result.message ? res.result.message : '亲，出错了哟'
                        });
                        setTimeout(function () {
                            location.href = "/member/poster/mine?picId=" + that.official + "&posterId=" + res.result.datas.posterId;
                        }, 2000)
                    }
                }
            })
        },
        init: function () {
            this.watch();
        }
    }
    poster.init();
</script>


<@m.page_footer />