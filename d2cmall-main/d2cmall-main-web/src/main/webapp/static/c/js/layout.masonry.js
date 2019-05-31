$(function () {
    var ua = navigator.userAgent.toLowerCase();
    var ie = (jQuery.browser.msie == true) ? ua.match(/msie ([\d.]+)/)[1] : null;
    var pack;
    if (ie == 6.0 || ie == 7.0) {
        pack = 'jquery.masonry.min';
    } else {
        pack = 'masonry.pkgd.min';
    }
    $.ajax({
        url: '/static/js/' + pack + '.js',
        dataType: 'script',
        cache: true,
        success: function () {
            var $container = $('#container').masonry({itemSelector: '.item'});
        }
    });
});