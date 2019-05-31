<#import "templates/public_pc.ftl" as m>
<@m.page_header title='搭配' js='jquery.fileupload|jquery.drag'/>
<@m.top_nav suspend=false />
<div class="main-body bg-user-center">
    <div class="main-wrap bg-white border-gray clearfix">
        <@m.page_menu menu_item="o2o_my_collocation"/>
        <div class="my-cont">
            <h1>搭配</h1>
            <div style="margin-top:15px;" class="clearfix grid-form">
                <div class="float-left" style="margin-right:20px;">
                    <div style="width:300px;min-height:400px;padding:10px;border:1px solid #CCC;box-shadow:0 0 3px #CCC;">
                        <div class="upload-button<#if (collocation.pic)!> display-none</#if>"
                             style="margin:60% 0 0 36%;">
                            <a href="javascript:void(0)" class="upload-a btn-my-upload"><span>上传照片</span><input
                                        type="file" class="upload-file" name="file"
                                        accept="image/gif,image/png,image/jpeg"></a>
                        </div>
                        <div class="upload-preview<#if (collocation.pic)!><#else> display-none</#if>">
                            <div style="position:relative;"><#if collocation.pic><img
                                    src="${picture_base}${(collocation.pic)!}" class="tag-main" alt=""
                                    width="300"/></#if>
                                <#if collocation.items && collocation.items?size gt 0>
                                    <#list collocation.items as item>
                                        <div class="tag-img-item" id="tag-item-${item.id}"
                                             style="top:${item.y*100}%;left:${item.x*100}%">
                                            <div class="tag-img-cycle"><i></i><span></span></div>
                                            <div class="tag-img-text"><span>◆</span> ${item.title}</div>
                                            <div class="tag-img-edit display-none" id="tag-edit-${item.id}">
                                                <span class="arrow"></span>
                                                <div class="main">
                                                    <input type="hidden" name="id" class="form-input"
                                                           value="${item.id}"/><input type="hidden" name="collocationId"
                                                                                      class="form-input"
                                                                                      value="${(collocation.id)!0}"/><input
                                                            type="hidden" name="x" class="form-input"
                                                            value="${item.x}"/><input type="hidden" name="y"
                                                                                      class="form-input"
                                                                                      value="${item.y}"/>
                                                    <p>单品标题</p>
                                                    <p><input type="text" name="title" size="30" class="form-input"
                                                              value="${item.title}"/></p>
                                                    <p>单品价格</p>
                                                    <p><input type="text" name="price" size="30" class="form-input"
                                                              value="${item.price}"/></p>
                                                    <p>单品连接</p>
                                                    <p><input type="text" name="url" size="30" class="form-input"
                                                              value="${item.url}"/></p>
                                                    <p class="text-center">
                                                        <button type="button" rel="${item.id}"
                                                                class="button-l b-b submit">提交
                                                        </button> &nbsp;&nbsp;
                                                        <button type="button" rel="${item.id}" class="button-l cancel">
                                                            取消
                                                        </button>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                        <a href="javascript:" class="icon-cross photo-del" title="删除"></a>
                    </div>
                </div>
                <form id="submit-form" action="/collocation/${action}" method="post" class="float-left"
                      style="width:500px;">
                    <input type="hidden" name="type" value="CUSTOMER"/>
                    <input type="hidden" id="collection-id" name="id" value="${(collocation.id)!0}"/>
                    <input type="hidden" name="picture.path" id="picture" value="${(collocation.pic)!}" accept="image/*"
                           class="path-input"/>
                    <p>搭配标题</p>
                    <p style="margin:5px 0;"><input name="title" id="title" value="${(collocation.title)!}"
                                                    class="input-l" style="width:90%;"/></p>
                    <p class="tips-validate red display-none" id="title-tip" for="title"><i></i>标题不能为空</p>
                    <p style="margin:5px 0;">搭配描述</p>
                    <p><textarea name="description" id="description" class="input-l"
                                 style="width:90%;height:150px;">${(collocation.description)!}</textarea></p>
                    <p class="text-center" style="margin:5px 0;">
                        <button type="submit" name="button-save" class="button-l b-b">保存</button>
                    </p>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $('.tag-main').live('click', function (e) {
        var width = $('.tag-main').width(), height = $('.tag-main').height(), offset = $('.tag-main').offset();

        var id_input = $('#collection-id');
        if (id_input.val() <= 0) {
            var form = $('#submit-form');
            $.ajax({
                url: form.attr('action'),
                type: form.attr('method'),
                data: form.serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        id_input.val(data.result.data.id);
                        form.attr('action', '/collocation/edit');
                    } else {
                        jAlert(data.result.message);
                    }
                }
            });
        }

        var _id = new Date().getTime();
        var _this = $(this), _parent = $(this).parent();
        _parent.css('position', 'relative');

        var click_x = e.pageX - offset.left, click_y = e.pageY - offset.top;
        if ($('.tag-img-mask').size() == 0) {
            _parent.append('<div class="tag-img-mask" style="width:' + width + 'px;height:' + height + 'px"></div>');
        } else {
            $('.tag-img-mask').show();
        }
        _parent.append('<div class="tag-img-item" id="tag-item-' + _id + '" style="top:' + (click_y - 5) + 'px;left:' + (click_x - 5) + 'px"></div>');
        $('#tag-item-' + _id).append('<div class="tag-img-cycle"><i></i><span></span></div>');
        var str = '<div class="tag-img-edit" id="tag-edit-' + _id + '">\
		<span class="arrow"></span>\
		<div class="main">\
		<input type="hidden" name="id" class="form-input" value="0" /><input type="hidden" name="collocationId" class="form-input" value="' + id_input.val() + '" /><input type="hidden" name="x" class="form-input" value="' + (click_x / width) + '" /><input type="hidden" name="y" class="form-input" value="' + (click_y / height) + '" />\
		<p>单品标题</p>\
		<p><input type="text" name="title" size="30" class="form-input" value="" /></p>\
		<p>单品价格</p>\
		<p><input type="text" name="price" size="30" class="form-input" value="" /></p>\
		<p>单品连接</p>\
		<p><input type="text" name="url" size="30" class="form-input" value="" /></p>\
		<p class="text-center"><button type="button" rel="' + _id + '" class="button-l b-b submit">提交</button> &nbsp;&nbsp; <button type="button" rel="' + _id + '" class="button-l cancel">取消</button></p>\
		</div>\
		</div>';
        $('#tag-item-' + _id).append(str);
        $('.tag-img-item').draggable({
            containment: "parent",
            cursor: "move"
        });
        $('.tag-img-item').on("dragstop", function (event, ui) {
            var x = parseInt($(this).css('left')) / width, y = parseInt($(this).css('top')) / height;
            $(this).find('input[name=x]').val(x);
            $(this).find('input[name=y]').val(y);
        });
        return false;
    });

    $('.tag-img-item').draggable({
        containment: ".upload-preview",
        cursor: "move"
    });
    $('.tag-img-item').on("dragstop", function (event, ui) {
        var width = $('.tag-main').width(), height = $('.tag-main').height(), offset = $('.tag-main').offset();
        var x = parseInt($(this).css('left')) / width, y = parseInt($(this).css('top')) / height;
        $(this).find('input[name=x]').val(x);
        $(this).find('input[name=y]').val(y);
        $('.submit').trigger('click');
    });

    $('.tag-img-text').live('click', function () {
        $(this).siblings('.tag-img-edit').show();
        $('.tag-img-mask').show();
    });

    $('.submit').live('click', function () {
        var id = $(this).attr('rel');
        var obj = $('#tag-item-' + id);
        if (obj.find('input[name=title]').val() != '' && obj.find('input[name=price]').val() != '' && obj.find('input[name=url]').val() != '') {
            var item_from_id = obj.find('input[name=id]');
            $.ajax({
                url: '/collocation/item/edit',
                type: 'post',
                data: obj.find('.form-input').serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        item_from_id.val(data.result.data.id);
                    } else {
                        jAlert(data.result.message);
                    }
                }
            });
            $('#tag-edit-' + id).hide();
            $('#tag-item-' + id).append('<div class="tag-img-text"><span>◆</span> ' + $('#tag-item-' + id).find('input[name=title]').val() + '</div>');
            /*if ($('#tag-item-'+id).find('.tag-img-text').size()==0){
                $('#tag-item-'+id).append('<div class="tag-img-text"><span>◆</span> '+$('#tag-item-'+id).find('input[name=title]').val()+'</div>');
            }*/
            $('.tag-img-mask').hide();
        }
        return false;
    });

    $('.cancel').live('click', function () {
        var id = $(this).attr('rel');
        if ($('#tag-item-' + id).find('.tag-img-text').size() == 0) {
            $('#tag-item-' + id).remove();
        } else {
            $('#tag-item-' + id).find('.tag-img-edit').hide();
        }
        $('.tag-img-mask').hide();
    });
    $('#submit-form').submit(function () {
        var form = $(this);
        var title = $('#title').val();
        var picture = $('#picture').val();
        if (title == '' || picture == '') {
            picture == '' && $('#picture-tip').show();
            title == '' && $('#title-tip').show();
        } else {
            $('#picture-tip').hide();
            $('#title-tip').hide();
            $.ajax({
                url: form.attr('action'),
                type: form.attr('method'),
                data: form.serialize(),
                dataType: 'json',
                success: function (data) {
                    if (data.result.status == 1) {
                        location.href = "/collocation/my";
                    } else {
                        jAlert(data.result.message);
                    }
                }
            });

        }
        return false;
    });
    $('.upload-file').die().live('change', function () {
        var this_obj = $(this);
        var type = $(this).attr("data-type");
        var id = $(this).attr('rel');
        var form = $('#submit-form');
        var obj = this_obj.parent();
        obj.attr('disabled', true).find('span').text('正在上传...');
        $.ajaxFileUpload({
            url: '/collocation/picture/' + form.find('input[name=id]').val(),
            secureuri: false,
            fileObject: this_obj,
            dataType: 'json',
            success: function (data) {
                obj.attr('disabled', false).find('span').text('上传图片');
                if (data.status == 1) {
                    obj.parent().hide();
                    obj.parent().siblings('.upload-preview').show();
                    obj.parent().siblings('.upload-preview').find('div').html('<img src="' + data.path + '" class="tag-main" alt="" width="300" />');
                    $('.path-input').val(data.value);
                }
            },
            error: function (data, status, e) {
                alert('未上传成功，文件格式不正确（只支持.jpg,.png,.gif）或者文件过大');
                obj.attr('disabled', false).find('span').text('上传图片');
            }
        });
    });
    $('.photo-del').click(function () {
        var obj = $(this).parent();
        window.parent.jConfirm('确定要删除此头像吗？', '', function (r) {
            if (r) {
                obj.siblings('.upload-button').show();
                obj.hide();
                obj.siblings('.path-input').val('');
            }
        });
        return false;
    });
</script>
<@m.page_footer />