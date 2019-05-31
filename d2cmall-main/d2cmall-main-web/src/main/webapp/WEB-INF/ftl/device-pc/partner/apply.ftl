<#import "templates/public_pc.ftl" as m>
<@m.page_header title='我的分销单' js="utils/jquery.datepicker" />
<@m.top_nav suspend=false />
<div class="layout layout-response layout-user">
    <div class="layout-user-left">
        <@m.page_user_menu menu_item="partner_info"/>
    </div>
    <#if partner??>
        <div style="width:40px;display:table-cell;background-color:#ededed"></div>
        <div class="layout-user-right">
            <h1><i class="fa fa-caret-right"></i> 修改分销商信息</h1>
            <div class="form form-small" style="width:500px;">
                <div class="tip tip-warning"><i class="fa fa-info-circle"></i>请确保分销商信息的真实性，所有个人信息均严格保密。</div>
                <form name="form-info" class="validate-form" success-tip="个人信息保存成功！" action="/partner/update.json"
                      method="post">
                    <div class="form-item">
                        <label>手机号码</label>
                        <input type="text" name="mobile" id="mobile" value="${(partner.mobile)!}" class="input"
                               maxlength="11"/>
                        <div class="tip tip-validate" data-target="mobile" data-rule="mobile"></div>
                    </div>
                    <div class="form-item">
                        <label>电子邮箱</label>
                        <input type="text" name="email" id="email" value="${(partner.email)!}" class="input"/>
                    </div>
                    <div class="form-item">
                        <label>真实姓名</label>
                        <input type="text" name="name" id="name" minlength="2" maxlength="20" value="${(partner.name)!}"
                               class="input"/>
                        <div class="tip tip-validate" data-target="name"></div>
                    </div>
                    <div class="form-item">
                        <label>性别</label>
                        <label><input type="radio" id="girl" name="sex" value="女"
                                      class="checkbox"<#if partner.sex=='女'> checked</#if> /> 女 </label>
                        &nbsp; &nbsp;
                        <label><input type="radio" id="boy" name="sex" value="男"
                                      class="checkbox"<#if partner.sex=='男'> checked</#if> /> 男</label>
                    </div>
                    <div class="form-item">
                        <label>腾讯QQ</label>
                        <input type="text" name="qq" value="${(partner.qq)!}" class="input"/>
                    </div>
                    <div class="form-item">
                        <label>微信号</label>
                        <input type="text" name="weixin" value="${(partner.weixin)!}" class="input"/>
                    </div>
                    <input type="hidden" name="id" value="${(partner.id)!}" class="input"/>
                    <div class="form-button">
                        <button type="submit" class="button">确定</button>
                    </div>
                </form>
            </div>
        </div>
    <#else>
        <div style="width:40px;display:table-cell;background-color:#ededed"></div>
        <div class="layout-user-right">
            <h1><i class="fa fa-caret-right"></i> 我的分销信息</h1>
        </div>
        <div class="apply-box">
            <h1 style="text-align:center;">D2C分销活动协议</h1>

            <p><strong>一、D2C“全球好设计”</strong>是中国大陆一家的全球好设计（以下称本平台），为全球时尚人士提供原创、新颖、独特、品质、高性价比的设计师产品和品牌。该平台属于杭州迪尔西时尚科技有限公司所有，参加活动用户使用本平台推广、批发、零售本平台服饰的，则默认为同意本协议，并同意成为本平台的分销商(此处的分销是指，同意本平台的协议及所有政策，享受本平台供货，并在本平台货品基础上发展下级经销商或进行直接销售的行为)。
            </p>
            <p><strong>二、分销用户申明:</strong></p>
            <p>1.分销用户保证作为本平台分销商，只在本平台上经销本平台服饰，具体操作参照本平台分销流程。</p>
            <p>2. 分销商发展下级经销商或进行直接销售将可获得根据本平台制定的分成政策享受相应的收益。分销商的收益以本平台发布的分成规则为准。</p>
            <p>3.分销用户保证不进行针对本网站的任何恶意行为。</p>
            <p>4.分销用户不得售卖非从本平台分销渠道的货品。</p>
            <p>3.因本平台只是向分销用户提供产品，不参与分销用户具体运营，所以因分销用户原因产生的纠纷本平台概不负责。</p>
            <p>4.分销用户代销的本平台服饰，所上库存数必需按本平台给予的库存数上架或者调整，如分销用户没有按本平台提供的库存数上货，出现的超卖问题，由分销用户承担。</p>
            <p>5.分销用户在自有所属渠道销售的本平台货品必须按本平台服务标准执行，不得违反客服标准，不得造成对本平台品牌损害。</p>
            <p>6.分销用户利用本平台货品达成的销售必须保证在本平台进行成交，不得利用本平台货品资料和信息、及利用平台名义进行非本平台货品交易。</p>
            <p><strong>三、退、换货：</strong></p>
            <p>1.分销用户在下单之前，必须和买家达成良好沟通，并由买家自行选择所需要尺码。下单时与工作人员确认客户所需尺码。若由于买家自己选择尺码不合适，造成的退换货运费由买家本人承担。</p>
            <p>
                2、分销用户在下单后，联系本平台售后人员获取单号，并关注该单号，如遇问题件，请积极主动联系快递公司和本平台工作人员进行解决。由于快递原因造成的延误，本平台不承担责任。本平台会尽量与快递公司协商帮助处理问题。</p>
            <p>3、若遇买家要求退换货情况，分销用户需告知买家必须保证货物完好，未经洗涤，吊牌齐全，不影响商品的二次销售。如退回货物不符合标准，本平台将不予处理。由此引发的纠纷由分销用户负责与买家协商解决。</p>
            <p>4、若遇买家要求退换货情况，分销用户需告知买家在退换货时填写“退换货情况说明”，如未填写“退换货情况说明”而造成的退换件处理延误，由分销用户负责与买家协商解决。</p>
            <p>注：退换货说明：包括（收货人姓名、联系方式、收货信息、退换货理由）。<br/> 
            <p>5、本平台将拒收任何情况下的邮费到付件和邮政平邮件。所有邮费到付件、邮政平邮包裹本平台将不予签收，因此造成的损失由分销用户自己承担。</p>
            <p>6、如有质量问题，在收到货后7天内由分销用户与本平台工作人员联系协商退换货相关情况，逾期概不负责（期限签收商品7天内）（以物流签收单时间为准）。</p>
            <p><strong>四、 分销用户承诺已认真阅读本分销协议，并在点击“我接受”之后，即表示已与杭州迪尔西时尚科技有限公司自愿达成本协议，并完全接受本协议各条款的约束。</strong></p>
            <p><strong>五、杭州迪尔西时尚科技有限公司拥有本协议最终解释权，并有权根据需要修改本协议的任何条款。一旦修改了本协议的任何条款，将会在网站上提供最新版本（包括其生效日期）。杭州迪尔西时尚科技有限公司鼓励用户定期浏览本协议。</strong>
            </p>
            </p>
            <p style="text-indent:0;font-size:13px;letter-spacing:0;">
                <input type="checkbox" id="apply-check"
                       style="vertical-align:middle; margin-top:-2px; margin-bottom:1px;"/>我同意该分销商协议
            </p>
            <br/>
            <button type="button" id="add-new-address" disabled="disabled" class="button button-red apply"
                    style="margin-left:250px;">申请成为分销商
            </button>
            <div class="apply_ribbon"></div>
        </div>
    </#if>
</div>

<script type="text/javascript">
    $(function () {
        $('#apply-check').change(function () {
            if ($(this).attr('checked')) {
                $('#add-new-address').removeAttr("disabled");
            } else {
                $('#add-new-address').attr('disabled', "true");
            }
        })
        $('.apply').click(function () {
            window.parent.jConfirm('确定要申请成为分销商吗？', '', function (r) {
                if (r) {
                    $.post('/partner/apply', function (data) {
                        if (data.result.status == 1) {
                            window.parent.jAlert(data.result.message + '，请完善分销信息');
                            setTimeout(function () {
                                location.reload();
                            }, 3500);
                        } else {
                            window.parent.jAlert(data.result.message);
                        }
                    }, 'json');
                }
            });
            return false;
        });


    })
</script>
<@m.page_footer js='modules/page.user' />
