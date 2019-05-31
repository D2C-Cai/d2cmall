var static_base = 'http://static.d2c.cn/common/c';
$(function () {
    $.alerts = {
        draggable: true,
        ok_button: '确 定',
        cancel_button: '取 消',

        alert: function (message, callback) {
            $.alerts.show(message, 'alert', function (result) {
                if (callback) callback(result);
            });
        },
        pop: function (message, config) {

        },
        confirm: function (message, callback) {
            $.alerts.show(message, 'confirm', function (result) {
                if (callback) callback(result);
            });
        },
        remove: function () {
            $('#alert-box-mask').remove();
            $('#alert-box').remove();
        },
        show: function (msg, mtype, callback) {
            var html = '';
            html += '<div id="alert-box-mask" class="box-mask" style="display:block"></div>';
            html += '<div id="alert-box"><div><p id="alert-msg" style="line-height:150%;">' + msg + '</p><p id="alert-buttons"></p></div></div>';
            $('body').append(html);
            switch (mtype) {
                case 'alert':
                    $("#alert-buttons").html('<button type="button" id="alert-ok">' + $.alerts.ok_button + '</button>');
                    $("#alert-ok").on('touchstart', function () {
                        $.alerts.remove();
                        callback(true);
                        return false;
                    });
                    break;
                case 'confirm':
                    $("#alert-buttons").html('<button type="button" id="alert-ok">' + $.alerts.ok_button + '</button>&nbsp;&nbsp;<button type="button" id="alert-cancel">' + $.alerts.cancel_button + '</button>');
                    $("#alert-ok").on('touchstart', function () {
                        $.alerts.remove();
                        if (callback) callback(true);
                        return false;
                    });
                    $("#alert-cancel").on('touchstart', function () {
                        $.alerts.remove();
                        if (callback) callback(false);
                        return false;
                    });
                    break;
            }
            return false;
        }
    }
    // Shortuct functions
    jAlert = function (message, callback) {
        $.alerts.alert(message, callback);
    }
    jConfirm = function (message, callback) {
        $.alerts.confirm(message, callback);
    };

    $.fn.getarea = function () {
        var province_json = static_base + "/js/json-array-of-province.js";
        var city_json = static_base + "/js/json-array-of-city.js";
        var district_json = static_base + "/js/json-array-of-district.js";
        var default_province = '<option value="0">选择省份</option>';
        var default_city = '<option value="0">选择城市</option>';
        var default_district = '<option value="0">选择区县</option>';

        var province = new Array();
        var city = new Array();
        var district = new Array();

        var e = $(this);
        var array;

        var type = e.find('.province').size() > 0 ? 1 : 0;//判断是转换地址还是创建下拉表
        var obj_p = e.find('.province');
        var obj_c = e.find('.city');
        var obj_d = e.find('.district');
        if (province.length == 0 || city.length == 0 || district.length == 0) {
            $.getJSON(province_json, function (data) {
                $.each(data, function (i, d) {
                    province[d.code] = d.name;
                });
                build_list(province, obj_p.attr('rel'), obj_p, null, 0, default_province);
            });
            $.getJSON(city_json, function (data) {
                $.each(data, function (i, d) {
                    city[d.code] = d.name;
                });
                build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, default_city);
            });
            $.getJSON(district_json, function (data) {
                $.each(data, function (i, d) {
                    district[d.code] = d.name;
                });
                build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
            });

            obj_p.change(function () {
                build_list(city, obj_c.attr('rel'), obj_c, obj_p, 2, default_city);
                build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
            });
            obj_c.change(function () {
                build_list(district, obj_d.attr('rel'), obj_d, obj_c, 4, default_district);
            });
        }


        function build_list(data, value, obj, pre_obj, strlen, def) {
            var selected;
            var option;

            for (var i in data) {
                selected = '';
                if (strlen === 0) {
                    if (value !== "" && value !== 0 && i === value) {
                        selected = ' selected';
                        value = '';
                    }
                    option += '<option value="' + i + '"' + selected + '>' + data[i] + '</option>';
                } else {
                    var p = pre_obj.val();
                    if (p.substring(0, strlen) === i.substring(0, strlen)) {
                        if (value !== "" && value !== 0 && i === value) {
                            selected = ' selected';
                            value = '';
                        }
                        option += '<option value="' + i + '"' + selected + '>' + data[i] + '</option>';
                    }
                }

            }
            obj.html(def + option);
        }
    },

        //检测手机号
        $.fn.mobile = function () {
            var re_mobile = /^[1][3456789]\d{9}$/;
            if (re_mobile.test($(this).val()) && $(this).val().length == 11) {
                return true;
            } else {
                return false;
            }
        },

        //倒计时
        $.fn.countdown = function () {
            var obj = $(this);
            var type = obj.attr('data-type');
            var func = obj.attr('data-function');
            var startTime = (new Date(obj.attr("data-startTime"))).getTime();
            var endTime = (new Date(obj.attr("data-endTime"))).getTime();
            var nowTime = (new Date(_server_time)).getTime();
            var st = startTime - nowTime, et = endTime - nowTime;
            var timestr;
            if (type == undefined) {
                type = (st > 0) ? 'tostart' : 'toend';
            }
            timestr = (st > 0) ? st : et;

            output();
            var timer = setInterval(function () {
                output();
                if (timestr <= 0) {
                    clearInterval(timer);
                }
                timestr -= 1000;
            }, 1000);

            function output() {
                var time = expireTime(timestr);
                if (timestr <= 0) {
                    if (typeof (eval(func)) == 'function') {
                        obj.html(eval(func));
                    } else {
                        obj.html('');
                    }
                }
                var str;
                if (type == 'toend') {
                    str = '<span class="normal">还剩';
                    if (time['day'] > 0) {
                        str += '<strong>' + time['day'] + '</strong>天';
                    }
                    str += '<strong>' + time['hour'] + '</strong>小时<strong>' + time['minute'] + '</strong>分<strong>' + time['second'] + '</strong>秒结束</span>';
                } else if (type == 'tostart') {
                    str = '<span class="normal">还有';
                    if (time['day'] > 0) {
                        str += '<strong>' + time['day'] + '</strong>天';
                    }
                    str += '<strong>' + time['hour'] + '</strong>小时<strong>' + time['minute'] + '</strong>分<strong>' + time['second'] + '</strong>秒开始</span>';
                } else {
                    if (time['day'] > 0) {
                        str = '<span class="normal">还剩<strong>' + time['day'] + '</strong>天<strong>' + time['hour'] + '</strong>小时<strong>' + time['minute'] + '</strong>分<strong>' + time['second'] + '</strong>秒结束</span>';
                    } else {
                        str = '<span>还剩 </span><span class="day-time day-h times">' + time['hour'] + '</span> <span class="times">:</span> <span class="day-time day-m times">' + time['minute'] + '</span> <span class="times">:</span> <span class="day-time day-s times">' + time['second'] + '</span> <span>结束</span>';
                    }
                }
                if (type == 'split-time') {
                    obj.find('.day').html(time['day']);
                    obj.find('.hour').html(time['hour']);
                    obj.find('.minute').html(time['minute']);
                    obj.find('.second').html(time['second']);
                } else {
                    obj.html(str);
                }
            }

            function expireTime(time) {
                var array = new Array();
                if (time > 0) {
                    var tDay = Math.floor(time / 86400000);
                    time -= tDay * 86400000;
                    tHour = Math.floor(time / 3600000);
                    time -= tHour * 3600000;
                    if (tHour < 10) {
                        tHour = "0" + tHour;
                    }
                    tMinute = Math.floor(time / 60000);
                    time -= tMinute * 60000;
                    if (tMinute < 10) {
                        tMinute = "0" + tMinute;
                    }
                    tSecond = Math.floor(time / 1000);
                    if (tSecond < 10) {
                        tSecond = "0" + tSecond;
                    }
                    array['day'] = tDay;
                    array['hour'] = tHour;
                    array['minute'] = tMinute;
                    array['second'] = tSecond;
                } else {
                    array['day'] = '00';
                    array['hour'] = '00';
                    array['minute'] = '00';
                    array['second'] = '00';
                }
                return array;
            }
        }
});
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1,                 //月份 
        "d+": this.getDate(),                    //日 
        "h+": this.getHours(),                   //小时 
        "m+": this.getMinutes(),                 //分 
        "s+": this.getSeconds(),                 //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds()             //毫秒 
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}