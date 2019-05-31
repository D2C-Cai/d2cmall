<#import "templates/public_mobile.ftl" as m>
<@m.page_header  title='消息详情' back='消息详情' />
<style>
    body {
        background: #FFF;
    }

    .online-chat {
        display: none;
    }

    .update-button {
        width: 6.25rem;
        height: 1.875rem;
        line-height: 1.875rem;
        text-align: center;
        margin: 2.5rem auto 0;
        border: 1px solid #262626;
        color: #262626;
        font-size: 12px;
        cursor: pointer;
    }
</style>
<div style="position:absolute;left:50%;top:45%;transform:translate(-50%,-50%);">
    <img src="//static.d2c.cn/img/topic/180528/shirt/images/update.png" width="100%"/>
    <div class="update-button">立即更新</div>
</div>
<script>
    $(document).on('click', '.update-button', function () {
        if (iOS) {
            location.href = "https://itunes.apple.com/us/app/d2c-quan-qiu-hao-she-ji/id980211165?l=zh&ls=1&mt=8";
        } else {
            location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.d2cmall.buyer";
        }
    });
</script>
<@m.page_footer/>