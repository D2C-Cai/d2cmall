$('.product-img .img-thumbs a').click(function () {
    $('.video-play-layer').hide();
    var data_img = $(this).find('img').attr('data-image');
    var origi_img = $(this).find('img').attr('ori-image');
    var index = $(this).index();
    var size = $('.thumbs-list a').size();
    $(this).siblings().removeClass('on');
    $(this).addClass('on');
    $('#product-image-middle').attr('src', data_img);
    $('#product-image-middle').attr('data-id', index);
    $('#product-image-middle').attr('data-image', origi_img);
    return false;
});
$('.product-img .img-thumbs a:first').trigger('click');

$('.product-cont-bar .tab li').click(function () {
    $('html, body').animate({scrollTop: $('#product-detail').offset().top}, 300);
    var id = $(this).attr('data-id');
    var url = $(this).attr('data-url')
    $(this).addClass('on');
    $(this).siblings().removeClass('on');
    $('#product-detail-' + id).show();
    $('#product-detail-' + id).siblings('div[id]').hide();
    if (url) {
        $.get(url, function (data) {
            $('#product-detail-' + id).html(data);
        });
    }
    return false;
});