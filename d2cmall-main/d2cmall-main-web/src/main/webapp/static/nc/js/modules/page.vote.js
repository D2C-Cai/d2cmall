/* 若要返回投票数字，则约定html结构。指定样式vote-item-num，指定data-id为投票id
 * <span class="vote-item-num" data-id="458"></span> 
 */
if ($('.vote-item-num').size() > 0) {
    var ids = '', str = '';
    var num = $('.vote-item-num').size();
    $.each($('.vote-item-num'), function (i, d) {
        ids += str + $(d).attr('data-id');
        str = (i < $('.vote-item-num').size()) ? ',' : '';
    });
    $.post('/vote/count?' + new Date().getTime(), {'id': ids}, function (data) {
        var votes = data.result.data;
        $.each(votes, function (i, d) {
            $('.vote-item-num[data-id=' + i + ']').text(d);
        });

    }, 'json');
}
/*
 * 投票成功后返回
 */
var voteSuccess = function (id) {
    var i = parseFloat($('.vote-item-num[data-id=' + id + ']').text());
    $('.vote-item-num[data-id=' + id + ']').text(i + 1);
}
