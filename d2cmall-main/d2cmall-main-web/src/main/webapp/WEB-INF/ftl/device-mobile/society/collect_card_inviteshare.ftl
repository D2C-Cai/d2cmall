<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='邀请好友解锁卡片' css="cardcollect"  />
<#assign imgHead='//static.d2c.cn/img/topic/181031/cardcollect/images/'>
<#if member.headPic && member.headPic?index_of('http') != -1>
    <#assign HEADPIC = member.headPic>
<#elseif member.headPic && member.headPic?index_of('http') == -1>
    <#assign HEADPIC = 'https://img.d2c.cn' + member.headPic>
<#else>
    <#assign HEADPIC = 'https://static.d2c.cn/img/home/160627/images/headpic.png'>
</#if>
<div class="card-container">
    <div class="card-invite">
        <div class="invite-header">
            <img src="${HEADPIC}" style="min-width:40px;width:40px;height:40px;border-radius:100%;margin-right:5px;"
                 alt="">
            <ul class="header-text">
                <li style="font-size:18px;">邀请您集卡清空购物车</li>
                <li>更有全场5折/100元现金红包等你拿</li>
            </ul>
        </div>
        <img src="${imgHead}share_header.png" width="100%" alt="">
        <div class="invite-qrcode">
            <img src="/picture/code?type=1&width=50&height=50&noLogo=true&&code=https://m.d2cmall.com/collection/card/share/detail?memberId=${member.id}"
                 width="50" alt="微信扫一扫"/>
            <ul style="color:#C39B5C;margin-left:5px;">
                <li style="font-size:18px;">帮好友集卡</li>
                <li>长按二维码识别</li>
            </ul>
        </div>
    </div>
    <p style="color:#111111;margin-bottom:20px;">分享到</p>
    <div class="share-items">
        <div class="item-icon" data-type="pyq">
            <img src="${imgHead}icon_share_pyq@3x.png" width="100%" alt="">
            <span>朋友圈</span>
        </div>
        <div class="item-icon" data-type="wx">
            <img src="${imgHead}icon_share_wx@3x.png" width="100%" alt="">
            <span>微信好友</span>
        </div>
    </div>
</div>
<script>
    //定义分享的参数
    var _target = encodeURIComponent('/collection/card/share/detail?memberId=${member.id}');
    var _pic = 'https://poster.d2c.cn?tpl=1111card&img=${HEADPIC}&qr=' + _target;
    console.log(_pic);
    var message = {
        handlefunc: '',
        pic: _pic
    }
    $('.item-icon').on('click', function () {
        var type = $(this).attr('data-type');
        if (app_client === true && !isWeChat) {
            message.handlefunc = type === 'pyq' ? 'w_pyq' : 'w_wx';
            $.D2CMerchantBridge(message);
        } else if (isWeChat) {
            var html = '<div class="popup-content">\
				<div style="width:300px;line-height:2;color:#fff;text-align:center;font-size:14px;">\
				<img src="' + _pic + '" width="100%" alt="">\
				<p>长按图片发给好友</p>\
				<a href="javascript:popupModalClose();" class="modal-close"></a>\
				</div>\
				</div>';
            popupModal({content: html});
        } else {
            alert('请在微信中打开~');
        }
    });

</script>




<@m.page_footer />