<#import "templates/public_pc.ftl" as m>
<@m.page_header title=''/>
<@m.top_nav suspend=false />
<div class="main-body"
     style="background:url(//static.d2c.cn/common/c/images/brand_bg.jpg) center center no-repeat;background-size:cover;">
    <div class="main-wrap clearfix" style="margin:100px auto">
        <div class="likebrand-form arial">
            <a href="javascript:" class="brand-remove form-close"><span>X</span></a>
            <h1>推荐您所喜欢的设计师品牌</h1>
            <form method="post" name="likebrand" action="/member/mylikebrand/insert">
                <table>
                    <tr>
                        <td>
                            <label>设计师品牌_01</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                        <td>
                            <label>设计师品牌_06</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>设计师品牌_02</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                        <td>
                            <label>设计师品牌_07</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>设计师品牌_03</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                        <td>
                            <label>设计师品牌_08</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>设计师品牌_04</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                        <td>
                            <label>设计师品牌_09</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>设计师品牌_05</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                        <td>
                            <label>设计师品牌_10</label>
                            <input name="interestTitle" class="input interest" value=""/>
                        </td>
                    </tr>
                </table>
                <p style="text-align: center;padding-top: 5px;" class="clearfix">
                    <input type="submit" class="button-l b-b" style="margin-top: 10px;" value="提交">
                </p>
            </form>
        </div>
    </div>
</div>
<script>
    $('form[name=likebrand]').submit(function () {
        var isempty = true;
        $.each($('.interest'), function (i, d) {
            if ($(d).val() != '') {
                isempty = false;
            }
        });
        if (isempty == true) {
            jAlert('至少填写一个您喜欢的设计师品牌！');
            return false;
        }
    });
    $('.brand-remove').click(function () {
        window.opener = null;
        window.open("", "_self");
        window.close();
    });
</script>
<@m.page_footer />