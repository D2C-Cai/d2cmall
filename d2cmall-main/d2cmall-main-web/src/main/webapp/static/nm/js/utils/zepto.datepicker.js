Zepto.datepicker = function (target) {
    var datepicker = {
        init: function () {
            this._target = target;
            this._date = new Date();
            this._formatDate();
        },
        bind: function () {
            this._picker = (function () {
                var arr = [];
                arr.push('<div class="datepicker-box">');
                arr.push('	<div class="datepicker-header">');
                arr.push('		<span class="datepicker-pre"><b></b></span>');
                arr.push('		<span class="datepicker-next"><b></b></span>');
                arr.push('		<h4></h4>');
                arr.push('	</div>');
                arr.push('	<table class="datepicker-body">');
                arr.push('		<thead>');
                arr.push('			<tr>');
                arr.push('				<th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th class="datepicker-weekend">六</th><th class="datepicker-weekend">日</th>');
                arr.push('			</tr>');
                arr.push('		</thead>');
                arr.push('		<tbody>');
                arr.push('		</tbody>');
                arr.push('	</table>');
                arr.push('</div>');
                return $(arr.join(''));
            })();
            this._generateDays();

            var p = this;
            this._picker.find('span').on('touchstart', function () {
                $(this).addClass('hover');
            }).on('touchend', function () {
                $(this).removeClass('hover');
            }).click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                if ($(this).hasClass('datepicker-pre')) {
                    p._date.setMonth(p._date.getMonth() - 1);
                } else {
                    p._date.setMonth(p._date.getMonth() + 1);
                }
                p._generateDays();
            });

            $(document).click(function (e) {
                if (e.target != p._picker[0] && e.target != p._target[0]) {
                    target.removeClass('on');
                    p._picker.hide();
                }
            });
            return this;
        },
        insert: function () {
            this._picker.insertAfter(this._target);
        },
        show: function () {
            this._picker.show();
        },
        hide: function () {
            this._picker.hide();
        },
        _generateDays: function () {
            var year = this._date.getFullYear()
                , month = this._date.getMonth() + 1
                , day = this._date.getDate()
                , days = new Date(new Date(year, month, 1) - 24 * 60 * 60 * 1000).getDate()
                , week = (function () {
                var tDate = new Date(year, month - 1, 1);
                var week = tDate.getDay();
                if (week == 0) {
                    week = 7;
                }
                return week;
            })();

            this._picker.find('h4').html(year + ' 年 ' + month + ' 月');

            var arr = []
                , d = 1;

            arr.push('<tr>');
            for (var j = 1; j < week; j++) {
                arr.push('<td>&nbsp;</td>');
            }
            for (var j = week; j < 8; j++, d++) {
                arr.push('<td class="datepicker-td');
                if (d == day) {
                    arr.push(' cur');
                }
                if (j >= 6) {
                    arr.push(' datepicker-weekend');
                }
                arr.push('">');
                arr.push('<a href="/detailReport?year=' + year + '&month=' + month + '&day=' + d + '">' + d + '</a>');
                arr.push('</td>');
            }
            arr.push('</tr>');

            for (var i = 0, l = Math.ceil((days + week) / 7) - 2; i < l; i++) {
                arr.push('<tr>');
                for (var j = 1; j < 8; j++, d++) {
                    arr.push('<td class="datepicker-td');
                    if (d == day) {
                        arr.push(' cur');
                    }
                    if (j >= 6) {
                        arr.push(' datepicker-weekend');
                    }
                    arr.push('">');
                    arr.push('<a href="/detailReport?year=' + year + '&month=' + month + '&day=' + d + '">' + d + '</a>');
                    arr.push('</td>');
                }
                arr.push('</tr>');
            }

            var l = days - d + 1;
            if (l != 0) {
                arr.push('<tr>');
                for (var i = 0; i < l; i++, d++) {
                    arr.push('<td class="datepicker-td');
                    if (d == day) {
                        arr.push(' cur');
                    }
                    if (i >= 6) {
                        arr.push(' datepicker-weekend');
                    }
                    arr.push('">');
                    arr.push('<a href="/detailReport?year=' + year + '&month=' + month + '&day=' + d + '">' + d + '</a>');
                    arr.push('</td>');
                }
                for (var i = l + 1; i < 8; i++) {
                    arr.push('<td>&nbsp;</td>');
                }
                arr.push('</tr>');
            }
            this._picker.find('tbody')[0].innerHTML = arr.join('');

        },
        _formatDate: function () {
            var weekDays = ['日', '一', '二', '三', '四', '五', '六'];
        }
    };

    datepicker.init();

    var initialised = false;

    var self = this;
    target.on('touchstart', function () {
        $(this).addClass('on');
        if (!initialised) {
            datepicker.bind().insert();
            initialised = true;
        }
        datepicker.show();
    });
};

$.fn.datepicker = function (options) {
    $.datepicker(this);
};