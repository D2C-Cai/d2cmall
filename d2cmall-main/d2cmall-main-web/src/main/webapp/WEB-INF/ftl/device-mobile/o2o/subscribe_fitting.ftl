<#import "templates/public_mobile.ftl" as m>
<@m.page_header back='预约商品' title='预约商品' service='false' hastopfix='false' js="utils/datapick/mobiscroll_002|utils/datapick/mobiscroll_004|utils/datapick/mobiscroll|utils/datapick/mobiscroll_003|utils/datapick/mobiscroll_005"   css="datapick/mobiscroll|datapick/mobiscroll_002|datapick/mobiscroll_003" />
<#if o2oSubscribe.items?exists && o2oSubscribe.items?size &gt; 0>
    <style>
        input[type=checkbox]:before, input[type=checkbox]:checked:before, input[type=radio]:before, input[type=radio]:checked:before {
            width: 1.2em;
            height: 1.2em;
            margin-top: .15em;
        }

        input[type=checkbox], input[type=radio] {
            margin-right: 4px;
        }

        .form .form-item.item-flex label {
            color: #999;
        }

        .server-list label {
            width: 30%;
            padding: .8em 0
        }

        .form .form-item label[for] {
            font-size: .8em
        }

        .item-flex .iic {
            width: 1.2em;
            height: 1.2em;
            vertical-align: middle;
            opacity: .6;
            position: absolute;
            right: 1.5em;
            top: 1.2em;
        }

        input[type=time]::-webkit-datetime-edit-text::before {
            content: ':';
        }
    </style>
    <div class="section lazyload" id="product">
        <div class="form">
            <#list o2oSubscribe.items as item>
                <div class="item form-item item-card  item-margin clearfix" data-id="${(item.id)!}">
                    <a href="/product/${(item.productId)!}">
                        <span class="img"><img src="${static_base}/m/img/blank100x157.png"
                                               data-image="${picture_base}${(item.sp1?eval.img)!}!275" alt=""
                                               style="min-height:6em"/></span>
                        <span class="title">${item.productName} &nbsp; ${(item.sp1?eval.value)!} ${(item.sp2?eval.value)!}</span>
                    </a>
                    <span class="property"
                          style="position:absolute;bottom:.6em;left:23%;">${item.sp1?eval.name}：${item.sp1?eval.value} &nbsp;${item.sp2?eval.name}：${item.sp2?eval.value}</span>
                    <a href="javascript:" data-url="/o2oSubscribe/deleteItem/${(item.id)!}" confirm="确定取消预约吗？"
                       success-tip="取消预约成功！" call-back="removeItem(${(item.id)!})"
                       class="icon icon-trash ajax-request reserve-trash"></a>
                </div>
            </#list>
        </div>
        <div style="height:3em"></div>
        <div class="suspend-bar">
            <button type="button" class="button button-l button-wblack" style="width:45.5%;margin-left:3%;" id="back">
                再挑几件
            </button>
            <button type="button" class="button button-l button-black button-one"
                    style="width:45.5%;margin-left:3%;margin-right:3%;" id="next">立即预约
            </button>
        </div>
    </div>
    <div class="section display-none" id="form">
        <div class="form">
            <form name="fitting-form" class="validate-form" action="/o2oSubscribe/create.json" method="post"
                  target="_self">
                <div class="form-item item-flex">
                    <i class="icon icon-arrow-right iic" style="top:.8em"></i>
                    <label>预约门店</label>
                    <select name="storeId" class="input validate" title="预约实体店">
                        <option value="">各城市门店列表</option>
                        <#if stores?exists&&stores?size gt 0>
                            <#list stores as store>
                                <option value="${store.id}"
                                        data-id="${store_index}"<#if o2oSubscribe.storeId?exists && o2oSubscribe.storeId==store.id> selected</#if>>${store.name}</option>
                            </#list>
                        </#if>
                    </select>
                    <div class="form-item-tip display-none" id="store-info"
                         style="padding-left:26%;font-size:10px;color:#999">
                    </div>
                </div>
                <input type="hidden" value="" id="store-id"/>
                <div class="form-item item-flex" style="margin-bottom:.45em">
                    <label>预约时间<br/>&nbsp;</label>
                    <div style="width:70%;display:inline-block;">
                        <input type="date" name="estimateDate" value="" class="input"/><br/>
                        <input id="StartTime" name="startTime" runat="server" class="input" readonly="readonly"
                               style="width:30%;"/>
                        <input id="EndTime" name="endTime" runat="server" class="input" readonly="readonly"
                               style="width:30%;"/>
                        <!--
                        <select name="startHour" id="startHour" class="input" style="width:16%;"></select>
                        <select name="startMinute" class="input" style="width:16%;">
                            <option value="0">0分</option>
                            <option value="15">15分</option>
                            <option value="30">30分</option>
                            <option value="45">45分</option>
                        </select> - &nbsp;
                        <select name="endHour" id="endHour" class="input" style="width:16%;"></select>
                        <select name="endMinute" class="input" style="width:16%;">
                            <option value="0">0分</option>
                            <option value="15">15分</option>
                            <option value="30">30分</option>
                            <option value="45">45分</option>
                        </select>
                        -->
                    </div>
                </div>
                <div style="font-size:.5em;color:#999;text-align:center">我们需要一些时间准备，所以建议您提前三天预定</div>
                <div class="form-item item-flex" style="margin-top:.45em">
                    <label>预约人</label>
                    <input type="text" name="name" value="${(o2oSubscribe.name)!}" title="您的称呼" placeholder="填写您的称谓"
                           class="input validate"/>
                </div>
                <div class="form-item item-flex">
                    <label>性 &nbsp;&nbsp;&nbsp;&nbsp; 别</label>
                    <input type="radio" id="girl" name="sex"
                           value="女" <#if member?exists><#if member.memberInfo.sex=='女'> checked</#if><#else> checked</#if> /><label
                            for="girl">女士</label>
                    <input type="radio" id="boy" name="sex"
                           value="男" <#if member?exists><#if member.memberInfo.sex=='男'> checked</#if></#if> /><label
                            for="boy">先生</label>
                </div>
                <div class="form-item item-flex">
                    <label>预约电话</label>
                    <input type="tel" name="tel" value="${(o2oSubscribe.tel)!}" title="您的联系方式" placeholder="填写您的手机号"
                           class="input validate" data-rule="num"/>
                </div>
                <div class="form-item item-flex">
                    <label>微 &nbsp;信 &nbsp;号</label>
                    <input type="text" name="wechat" value="${(o2oSubscribe.wechat)!}" placeholder="填写您的微信号"
                           class="input"/>
                </div>
                <!--
                <div class="form-item item-flex">
                    <label>首选方式</label>
                    <input type="radio" id="tel" name="contact" checked value="电话" /><label for="tel">电话</label>
                    <input type="radio" id="sms" name="contact" value="短信" /><label for="sms">短信</label>
                    <input type="radio" id="wx" name="contact" value="微信" /><label for="wx">微信</label>
                </div>
                 -->
                <!--
                <div class="form-item item-flex">
                    <label>来店伴侣<br />&nbsp;</label>
                    <div style="width:70%;display:inline-block;">
                        <input type="checkbox" id="fr" name="companions" checked value="朋友" /><label for="fr">朋友</label> &nbsp;
                        <input type="checkbox" id="fa" name="companions" value="家人" /><label for="fa">家人</label><br />
                        <input type="checkbox" id="em" name="companions" value="同事" /><label for="em">同事</label> &nbsp;
                        <input type="checkbox" id="ch" name="companions" value="小朋友" /><label for="ch">小朋友</label>
                    </div>
                </div>
                -->

                <div class="form-item display-none" id="store-services" style="margin-top:.8em">
                    <label style="font-size:0.8em;color:#999">我们为您提供以下<em style="color:#000">免费服务</em>，请您选择：</label>
                    <div class="form-item item-flex" style="padding: .9em 0 0.4em 0;border-top:none;">
                        <i class="icon icon-arrow-right iic"></i>
                        <label>到店人数</label>
                        <select class="input" name="numbers">
                            <option value="1">1人</option>
                            <option value="2">2人</option>
                            <option value="3">3人</option>
                            <option value="4">4人</option>
                            <option value="5">5人</option>
                            <option value="6">6人</option>
                            <option value="7">7人</option>
                            <option value="8">8人</option>
                            <option value="9">9人</option>
                            <option value="10">10人以上</option>
                        </select>
                    </div>
                    <div style="padding-top:0.6em;" class="server-list"></div>
                </div>
                <!--
            <div class="form-item">
                <textarea name="optionRequire" class="input" placeholder="如果您需求还有其它个性化服务，D2C会尽最大可能地满足您">${(o2oSubscribe.optionRequire)!}</textarea>
                <div class="form-item-tip style="padding-left:26%;font-size:10px">注:如您需要法式牛奶咖啡、提前预定电影票、预约订餐等！。
                </div>
            </div>-->
                <div class="form-button">
                    <input type="hidden" name="subscribeId" value="${(o2oSubscribe.id)!}">
                    <button type="button" id="subscribe-button" name="send-button" class="button button-l button-red">
                        确定
                    </button>
                </div>
            </form>
        </div>
    </div>
<#else>

</#if>
<script id="store-info-template" type="text/html">
    <div style="line-height:150%;">
        <p>{{address}}</p>
        <p>电话：{{tel}}</p>
        <p>营业时间：{{startHour}}:{{startMinute == 0 ? '00' : startMinute}} - {{endHour}}:{{endMinute == 0 ? '00' :
            endMinute}}</p>
    </div>
</script>

<script id="store-service-template" type="text/html">
    {{each services as value i}}
    <label for="service-{{i}}" style="white-space:nowrap;line-height:100%;"><input type="checkbox" name="storeService"
                                                                                   value="{{value}}"
                                                                                   id="service-{{i}}"/>{{value}}</label>
    {{/each}}
</script>
<script>
    <#if stores?exists&&stores?size gt 0>
    var store_data = [<#list stores as store>{
        'id': '${store.id}',
        'name': '${store.name}',
        'map': '${store.xy}',
        'address': '${store.address}',
        'tel': '${store.tel}',
        'startHour': '${store.startHour}',
        'startMinute': '${store.startMinute}',
        'endHour': '${store.endHour}',
        'endMinute': '${store.endMinute}',
        'service': '${store.storeService}'
    }<#if store_index lt (stores?size-1)>, </#if></#list>];
    </#if>
    var removeItem = function (id) {
        $('.item[data-id=' + id + ']').addClass('animated zoomOutUp');
        setTimeout(function () {
            $('.item[data-id=' + id + ']').remove();
            if ($('.item[data-id]').size() <= 0) {
                $('#next').attr('disabled', 'true');
                // location.reload();
            }
        }, 500);
    }
    $('#next').on('touchstart', function () {
        if ($(this).attr('disabled') == 'disabled') {
            return false;
        }
        document.title = "预约信息";
        $('.header-title').text('预约信息');
        $('#product').addClass('animated fadeOutUp').fadeOut();
        $('#form').show().addClass('animated fadeInUp');
        $(window).scrollTop(0);
        return false;
    });
    $('#back').on('touchstart', function () {
        if (app_client == false) {
            history.back(1);
        } else {
            <#if RequestParameters.productId>
            location.href = "/product/${RequestParameters.productId!}"
            <#else>
            history.back(1);
            </#if>
        }
        return false;
    });

    /*预约时间设置*/
    var fit_date = new Date(new Date().getTime() + 259200000);
    $('input[name=startTime]').val('08:00');
    $('input[name=endTime]').val('11:00');

    $('input[name=estimateDate]').val(fit_date.getFullYear() + '-' + $.utilFormatNumber((fit_date.getMonth() + 1), 2) + '-' + $.utilFormatNumber(fit_date.getDate(), 2));

    var opt = {};
    opt.time = {preset: 'time'};
    opt.default = {
        theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        lang: 'zh',
        showNow: true,
        nowText: "现在",
    };
    var optTime = $.extend(opt['time'], opt['default']);
    $("#StartTime").mobiscroll(optTime).time(optTime);
    $("#EndTime").mobiscroll(optTime).time(optTime);


    $("select[name=storeId]").change(function () {
        var i = $(this).find('option:selected').attr('data-id');
        var store = store_data[i];
        $('#store-id').val(i);
        if (i != '') {
            var info = template('store-info-template', store);
            $('#store-info').show().html(info);
            if (store.service) {
                var store_services = store.service.split(",");
                var services = template('store-service-template', {'services': store_services});
                $('#store-services').show();
                $('#store-services .server-list').html(services);
            }
        } else {
            $('#store-info').hide();
            $('#store-services').hide();
        }
    });

    $('#subscribe-button').click(function () {
        var form = $('form[name=fitting-form]');
        var store = form.find('#store-id').val();
        var reg = new RegExp("^[0-9]*$");
        var tel = form.find('input[name="tel"]').val();
        if (reg.test(tel)) {
            if (store != '') {
                $.ajax({
                    url: form.attr('action'),
                    data: form.serialize(),
                    dataType: 'JSON',
                    type: 'post',
                    success: function (data) {
                        if (data.result.status == -1) {
                            window.parent.jAlert(data.result.message);
                        } else {
                            window.location = "/o2oSubscribe/my/list";
                        }
                    }
                });
            } else {
                jAlert('请先选择门店哟~');
            }
        } else {
            $.flashTip({position: 'center', type: 'error', message: '手机号码必须是数字'});
        }


    })

    <#if o2oSubscribe.storeId?exists && o2oSubscribe.storeId>
    $('select[name=storeId]').val(${o2oSubscribe.storeId}).attr('selected', true);
    $('select[name=storeId]').trigger('change');
    </#if>

</script>
<@m.page_footer />