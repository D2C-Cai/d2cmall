<#if flag==false>
    <div class="fit-product">
        <h2 class="fit-title">要试穿的商品</h2>
        <div style="overflow-x:auto;overflow-y:hidden">
            <div class="fit-product-list">
                <#if o2oSubscribe.items?exists && o2oSubscribe.items?size &gt; 0>
                    <#list o2oSubscribe.items as item>
                        <div class="o2oSubscribe">
                            <a href="/product/${(item.productId)!}" title="${(item.productName)!}" target="_blank"><img
                                        src="${picture_base}/${(item.sp1?eval.img)!}!120" alt=""/></a>
                            <span class="grey">${(item.sp1?eval.value)!} &nbsp; ${(item.sp2?eval.value)!}</span>
                            <span><a class="delete" href="/o2oSubscribe/deleteItem/${(item.id)!}">取消</a></span>
                        </div>
                    </#list>
                <#else>
                    请先选择商品
                </#if>
            </div>
        </div>
        <div class="text-center" style="padding:10px;">
            <button type="button" class="button button-green" name="next">选好了，我要预约试穿</button>
            <button type="button" class="button button-blue" name="ok">我还要多挑选几件</button>
        </div>
    </div>
    <script>
        $('button[name=ok]').click(function () {
            $.popModalClose();
        });

        $('button[name=next]').click(function () {
            $.popModalClose();
            $.popModal({'url': '/o2oSubscribe/confirm/${o2oSubscribe.id}', datatype: 'html', 'width': 710});
            return false;

        });
        $('.delete').click(function () {
            var obj = $(this).parent().parent();
            $.getJSON($(this).attr('href'), function (data) {
                obj.fadeOut(function () {
                    $(this).remove();
                    if ($(".o2oSubscribe").size() < 1)
                        $(".button-green").hide();
                });
            });
            return false;
        });
    </script>
<#else>
    <div class="subscribe-box">
        <form name="fitting-form" action="/o2oSubscribe/<#if o2oSubscribe.status !=1>create<#else>update</#if>"
              class="validate-form" method="post"
              target="<#if RequestParameters.target=='self'>_self<#else>_blank</#if>">
            <div class="subscribe-body">
                <div class="item" <#if o2oSubscribe.status==1>style="display: none;"</#if>>
                    <label class="label">选择门店：</label>
                    <select class="input" id="store-id" name="storeId">
                        <option value="">请选择</option>
                        <#if stores?exists&&stores?size gt 0>
                            <#list stores as store>
                                <option value="${store.id}" data-address="${store.address}" data-tel="${store.tel}"
                                        data-map="${store.bdxy}" data-startHour="${store.startHour}"
                                        data-startMinute="${store.startMinute}" data-endHour="${store.endHour}"
                                        data-endMinute="${store.endMinute}"
                                        data-storeService="${store.storeService}">${store.name}
                                </option>
                            </#list>
                        </#if>
                    </select>
                    <span class="store-time"></span>
                    <div class="store-info"></div>
                    <div class="tip tip-validate" data-target="store-id"></div>
                </div>
                <div class="item" <#if o2oSubscribe.status==1>style="display: none;"</#if>>
                    <label class="label">如何称呼：</label>
                    <input type="text" name="name" id="name" value="${(o2oSubscribe.name)!}" class="input" size="16"/>&nbsp;&nbsp;
                    <input type="radio" name="sex" value="女" <#if o2oSubscribe.sex=='女'> checked</#if>/> 女士&nbsp;
                    <input type="radio" name="sex" value="男" <#if o2oSubscribe.sex=='男'> checked</#if>/> 男士
                    <div class="tip tip-validate" data-target="name"></div>
                </div>
                <div class="item">
                    <label class="label">联系电话：</label>
                    <input type="text" name="tel" id="tel" value="${(o2oSubscribe.tel)!}" class="input" size="16"/>&nbsp;&nbsp;&nbsp;
                    <label class="label">微信号码：</label>
                    <input type="text" name="wechat" value="${(o2oSubscribe.wechat)}" class="input" size="16"/>
                    <div class="tip tip-validate" data-target="tel-weixin" data-function="checkContact"></div>
                </div>
                <div class="item">
                    <label class="label">联系方式：</label>
                    <label><input type="radio" name="contact"
                                  <#if o2oSubscribe.contact==""||o2oSubscribe.contact=="电话">checked</#if> value="电话"/>
                        电话</label>&nbsp;&nbsp;
                    <label><input type="radio" name="contact" <#if o2oSubscribe.contact=="短信">checked</#if> value="短信"/>
                        短信</label>&nbsp;&nbsp;
                    <label><input type="radio" name="contact" <#if o2oSubscribe.contact=="微信">checked</#if> value="微信"/>
                        微信（记得填写微信号码哦！）</label>&nbsp;&nbsp;
                </div>
                <div class="item">
                    <label class="label">来店人数：</label>
                    <select class="input" name="numbers" value="${(o2oSubscribe.numbers)!1}">
                        <option <#if o2oSubscribe.numbers=="1">selected</#if> value="1">1人</option>
                        <option <#if o2oSubscribe.numbers=="2">selected</#if> value="2">2人</option>
                        <option <#if o2oSubscribe.numbers=="3">selected</#if> value="3">3人</option>
                        <option <#if o2oSubscribe.numbers=="4">selected</#if> value="4">4人</option>
                        <option <#if o2oSubscribe.numbers=="5">selected</#if> value="5">5人</option>
                        <option <#if o2oSubscribe.numbers=="6">selected</#if> value="6">6人</option>
                        <option <#if o2oSubscribe.numbers=="7">selected</#if> value="7">7人</option>
                        <option <#if o2oSubscribe.numbers=="8">selected</#if> value="8">8人</option>
                        <option <#if o2oSubscribe.numbers=="9">selected</#if> value="9">9人</option>
                        <option <#if o2oSubscribe.numbers=="10">selected</#if> value="10">10人以上</option>
                    </select>
                    <span>（3人及以上有精美礼品送哦！）</span>
                </div>
                <div class="item partner" style="display:none;">
                    <label class="label">来店伴侣：</label>
                    <label><input type="checkbox" name="companions" value="朋友" checked/>朋友</label>&nbsp;&nbsp;
                    <label><input type="checkbox" name="companions" value="家人"/>家人</label>&nbsp;&nbsp;
                    <label><input type="checkbox" name="companions" value="同事"/>同事</label>&nbsp;&nbsp;
                    <label><input type="checkbox" name="companions" value="小朋友"/>小朋友</label>&nbsp;&nbsp;
                    <div class="tip tip-validate" data-target="companion" data-function="checkCompanions"></div>
                </div>
                <div class="item">
                    <label class="label">到店日期：</label><!--${(o2oSubscribe.estimateDate?string("yyyy-MM-dd"))!}-->
                    <input type="hidden" name="changeDate" value=""/>
                    <input type="text" name="estimateDate" id="estimateDate" value="" class="input text-center require"
                           size="10" data-msg="请选定一个日期" data-time="${o2oSubscribe.estimateDate?string("yyyy-MM-dd")}"/>
                    <select class="input" name="startHour" id="startHour"
                            <#if o2oSubscribe.startHour!="">data-time="${o2oSubscribe.startHour}"</#if>>
                    </select>
                    <select class="input" name="startMinute">
                        <option <#if o2oSubscribe.startMinute=="0">selected</#if> value="0">0分</option>
                        <option <#if o2oSubscribe.startMinute=="15">selected</#if> value="15">15分</option>
                        <option <#if o2oSubscribe.startMinute=="30">selected</#if> value="30">30分</option>
                        <option <#if o2oSubscribe.startMinute=="45">selected</#if> value="45">45分</option>
                    </select>
                    至
                    <select class="input" name="endHour" id="endHour"
                            <#if o2oSubscribe.endHour!="">data-time="${o2oSubscribe.endHour}"</#if>>
                    </select>
                    <select class="input" name="endMinute">
                        <option <#if o2oSubscribe.endMinute=="0">selected</#if> value="0">0分</option>
                        <option <#if o2oSubscribe.endMinute=="15">selected</#if> value="15">15分</option>
                        <option <#if o2oSubscribe.endMinute=="30">selected</#if> value="30">30分</option>
                        <option <#if o2oSubscribe.endMinute=="45">selected</#if> value="45">45分</option>
                    </select>

                </div>
                <div class="item">
                    <p style="color: #FF6600;">为了能更好的服务您，我们需要一些时间准备，所以建议您提前三天预定。</p>
                    <p style="color: #FF6600;">预约成功到店后出示自己尊贵预约身份让店员知道是门店试衣的，才会有一对一更好的服务。</p>

                </div>
                <div class="item display-none" id="storeServices">
                    <p>我们提供以下<strong style="color:blue">免费服务</strong>，请您选择：
                    <p>
                    <p><span></span></p>
                </div>
                <div class="item">
                    <p>如果您需求还有其它个性化服务，D2C会尽最大可能地满足您：</p>
                    <p><textarea style="width: 100%;" name="optionRequire" rows="4"
                                 class="input">${(o2oSubscribe.optionRequire)!}</textarea></p>
                    <p style="color:#FF6600;">注:如您需要法式牛奶咖啡、提前预定电影票、预约订餐等！
                        <!--如果您喜欢喝热咖啡，麻烦您填写一下种类，如:炭烧咖啡、法式牛奶咖啡、摩卡咖啡、提前购买电影票等。--></p>
                </div>
            </div>
            <div class="text-center">
                <input type="hidden" name="subscribeId" value="${(o2oSubscribe.id)!}">
                <button type="button" id="subscribe-button" class="button button-green button-l subscribe-button">提交预约
                </button>
            </div>
        </form>
    </div>

    <script type="text/javascript">
        var s = $('#startHour').attr('data-time');
        var e = $('#endHour').attr('data-time');
        var u = $('#estimateDate').attr('data-time');

        s == undefined ? s = 10 : s = s;
        e == undefined ? e = 11 : e = e;
        $('#startHour').html(createTime(8, 22, s));
        $('#endHour').html(createTime(8, 22, e));

        function createTime(start, end, selected) {
            var str = '';
            for (var i = start; i < (end + 1); i++) {
                if (i == selected) {
                    str = str + "<option value='" + i + "'selected>" + i + "点</option>";
                } else {
                    str = str + "<option value='" + i + "'>" + i + "点</option>";
                }
            }
            return str;
        }


        $('button[name=next]').click(function () {
            if ($('.fit-product-list img').size() > 0) {
                $('.fit-product').hide();
                $('.fit-product').siblings('.fit-form').show();
            } else {
                jAlert('现在还没商品哦，请先挑选要试穿的商品', '', function () {
                    $('#ebox-ebox-remove').trigger('click');
                });
            }
        });
        $('select[name=storeId]').change(function () {
            var _this = $(this).find('option:selected');
            var address = _this.attr('data-address'), tel = _this.attr('data-tel'), map = _this.attr('data-map')
                , startHour = _this.attr('data-startHour'), startMinute = _this.attr('data-startMinute')
                , endHour = _this.attr('data-endHour'), endMinute = _this.attr('data-endMinute')
                , storeService = _this.attr('data-storeService');
            startMinute = startMinute == 0 ? '00' : startMinute;
            endMinute = endMinute == 0 ? '00' : endMinute;
            var map_arr = map.split(',');
            map = map_arr[1] + ',' + map_arr[0];//百度地图的坐标需要调换位置
            if (address && tel) {
                $('.store-time').html('(营业时间：' + startHour + ':' + startMinute + '-' + endHour + ':' + endMinute + ')<a href="//map.baidu.com/?latlng=' + map + '&title=D2C ' + _this.text() + '&content=' + address + '&autoOpen=true&l" style="color:blue" target="_blank">[查看地图]</a> ');
                $('.store-info').show().html('<p>' + address + ' / ' + tel + '</p>');

            } else {
                $('.store-info').hide();
            }
            var str = '';
            if (storeService) {
                var storeServices = storeService.split(",");
                for (i = 0; i < storeServices.length; i++) {
                    str = str + '<label style="line-height:30px;"><input type="checkbox" name="storeService" value="' + storeServices[i] + '" /> ' + storeServices[i] + '</label> &nbsp;';
                }
            }
            $('#storeServices').show();
            $('#storeServices span').html(str);
            $.popModalUpdate();
        });
        <#if o2oSubscribe.storeId?exists && o2oSubscribe.storeId>
        $('select[name=storeId] option[value=${o2oSubscribe.storeId}]').attr('selected', true);
        $('select[name=storeId]').trigger('change');
        var customerservice = '${o2oSubscribe.storeService}';
        var storeService = customerservice.split(",");
        $.each(storeService, function (i, d) {
            $("input[name='storeService'][value=" + d + "]").attr("checked", "checked");
        });

        </#if>
        $("input[name='storeService']").click(function () {
            if ($(this).attr('checked') != "checked") {
                $(this).removeAttr('checked');
            } else {
                $(this).attr('checked', 'checked');
            }
        });
        var todate = new Date(new Date().getTime() + 259200000);
        var mydate = new Date(u);
        $('input[name=estimateDate]').val(mydate.getFullYear() + '-' + (mydate.getMonth() + 1) + '-' + mydate.getDate());
        $('input[name=changeDate]').val(todate.getFullYear() + '-' + (todate.getMonth() + 1) + '-' + todate.getDate());

        $('input[name=estimateDate]').datePicker({
            format: 'Y-m-d 00:00:00',
            date: $('input[name=estimateDate]').val(),
            current: $('input[name=estimateDate]').val(),
            starts: 1,
            position: 'r',
            onRender: function (date) {
                return {
                    disabled: (date.valueOf() < todate.valueOf()),
                }
            },
            onChange: function (formated, dates) {
                $('input[name=estimateDate]').val(formated);
                $('input[name=estimateDate]').datePickerHide();
            }
        });

        $('select[name=numbers]').change(function () {
            var num = $(this).val();
            if (num == 1) {
                $('.partner').hide();
            } else {
                $('.partner').show();
            }
            $.popModalUpdate();
        });
        var checkCompanions = function () {
            var num = $('select[name=numbers]').val();
            if (num > 1) {
                if ($('input[name=companions]:checked').size() == 0) {
                    return '至少要选择一种关系';
                }
            }
            return true;
        }
        var checkContact = function () {
            var error = 0;
            if ($('input[name=tel]').val() == '' && $('input[name=wechat]').val() == '') {
                $('input[name=tel]').focus();
                return '电话和微信必须留一个。';
            }
            if ($('input[name=contact]:checked').val() == '微信' && $('input[name=wechat]').val() == '') {
                $('input[name=wechat]').focus();
                return '您选择了微信联系，请填写微信号。'
            }
            return true;
        }
        $('#subscribe-button').click(function () {
            var form = $('form[name=fitting-form]');
            var store = form.find('#store-id').val();
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

        })
    </script>
</#if>