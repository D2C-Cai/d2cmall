<#import "templates/public_mobile.ftl" as m>
<@m.page_header title='报名注册' service='false'/>
<div class="section">
    <div style="padding:0.6em;text-align:center;">
        <p style="font-size:1.5em;font-weight: bold;padding-bottom:1em;line-height: 150%;">${def.name}</p>
        <p style="color: #999;padding-bottom:1em;font-size: 0.8em;line-height: 150%">${def.info}</p>
        <hr/>
        <h1 style="font-size:2em;padding:0.2em 0 0 0;">在线报名</h1>
    </div>
    <div class="form">
        <form name="ebox-reg-form" id="ebox-reg-form" class="validate-form" call-back="regSuccess"
              action="/registration/insert" method="post">
            <input type="hidden" name="defId" value="${def.id}"/>
            <div class="form-item item-flex">
                <label>手机</label>
                <input type="text" name="mobile" id="mobile" value="" title="手机" class="input validate"
                       data-rule="mobile" placeholder="请输入您手机号"/>
            </div>
            <div class="form-item item-flex">
                <label>姓名</label>
                <input type="text" name="name" id="name" value="" title="姓名" class="input validate"
                       placeholder="请输入您的姓名"/>
            </div>
            <div class="form-item item-flex">
                <label>参加人数</label>
                <input type="text" name="num" id="num" title="参加人数" value="" class="input validate"
                       placeholder="有几人来参加"/>
            </div>

            <div class="form-button">
                <button type="submit" name="submit-button" class="button button-l button-red">报名确认</button>
            </div>
        </form>
    </div>
</div>
<hr/>
<script>
    function regSuccess() {
        var data = $('body').data('return_data');
        var id = data.result.data.id;
        if (id) location.href = '/registration/success/' + id;
    }
</script>
<@m.page_footer menu=true />