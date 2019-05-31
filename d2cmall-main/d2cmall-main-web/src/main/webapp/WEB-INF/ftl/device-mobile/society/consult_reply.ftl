<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='商品咨询回复'  service='' hastopfix='false'/>
<style>
    .reply-container {
        background: #fff;
    }

    .reply-container img {
        width: 100%;
    }

    .product-detail {
        padding: 0.9375rem;
        border-bottom: 1px solid #f3f5f9;
    }

    .product-pic {
        float: left;
        display: block;
        width: 4rem;
        margin-right: 0.625rem;
    }

    .product-name {
        display: block;
        overflow: hidden;
        color: rgba(35, 36, 39, .87);
        font-size: 0.875rem;
        line-height: 1.6rem;
    }

    .product-price {
        display: block;
        overflow: hidden;
        line-height: 2rem;
    }

    .reply-info {
        padding: 1.25rem 0.9375rem;
        overflow: hidden;
    }

    .user-pic {
        float: left;
        display: block;
        width: 4rem;
        margin-right: 0.625rem;
    }

    .user-pic img {
        border-radius: 50%;
    }

    .user-name {
        display: inline-block;
        height: 4rem;
        line-height: 4rem;
        color: rgba(35, 36, 39, .87);
        font-size: 0.9375rem;
        overflow: hidden;
    }

    .user-words {
        margin-top: 0.625rem;
        line-height: 1.2rem;
        color: rgba(35, 36, 39, .54);
        font-size: 0.875rem;
    }

    .reply-detail {
        padding: 0 0.9375rem 2rem;
        line-height: 1.2rem;
    }
</style>
<header>
    <div class="header fixed">
        <div class="header-back"><a href="/member/home" class="icon icon-back"></a></div>
        <div class="header-title">咨询回复</div>
    </div>
</header>
<div class="reply-container">
    <div class="product-detail clearfix">
        <span class="product-pic"><img src="${picture_base}${consult.productPic}"></span>
        <span class="product-name">${consult.productName}</span>
        <span class="product-price"><span
                    style="color:rgba(35,36,39,.87);font-size:14px;">&yen;${consult.salePrice}</span><s
                    style="color:rgba(35,36,39,.38);font-size:11px;margin-left:5px;">&yen;${consult.originalPrice}</s></span>
    </div>
    <div class="reply-info clearfix">
        <span class="user-pic"><img src="${picture_base}${consult.headPic}"></span>
        <span class="user-name">${consult.nickName}</span>
        <p class="user-words">${consult.question}</p>
    </div>
    <div class="reply-detail">
        <span style="color:#f23365;">回复：</span>
        <span style="color:rgba(35,36,39,.87);font-size:0.875rem;">${consult.reply}</span>
    </div>
</div>