//图片上传
$('.upload-file').die().live('change', function () {
    var preview_template = '<div class="upload-img">\
		<a href="{{path}}" class="img" target="_blank">{{if path.indexOf("http")>0}}<img src="{{path}}" alt="" />{{else}}<img src="http://img.d2c.cn{{path}}" alt="" />{{/if}}</a>\
		<a href="javascript:void(0)" class="photo-del">删除</a>\
		<input type="hidden" name="path" class="path-input" value="{{value}}">\
		</div>';
    var this_obj = $(this);
    var obj = this_obj.parent();
    var upload_url = this_obj.attr('data-upload-url');
    var preview = obj.siblings('.upload-preview');
    obj.attr('disabled', true).find('span').text('上传中...');
    if (preview.find('img').size() >= 5) {
        jAlert('最多只能上传5张图片。', '', function () {
            obj.attr('disabled', false).find('span').text('浏览上传');
        });
        return false;
    }
    $.getScript('/static/nc/js/utils/jquery.fileupload.js', function () {
        $.ajaxFileUpload({
            url: upload_url,
            secureuri: false,
            fileObject: this_obj,
            dataType: 'json',
            timeout: 5000,
            success: function (data) {
                if (data.status == 1) {
                    setTimeout(function () {
                        var render = template.compile(preview_template);
                        var html = render(data);
                        preview.append(html);
                        obj.attr('disabled', false).find('span').text('浏览上传');
                        preview.show();
                        $.utilBaseModal.positionUpdate('pop');
                    }, 600);
                }
            },
            error: function (data, status, e) {
                alert('未上传成功，文件格式不正确（只支持.jpg,.png,.gif）或者文件过大');
                obj.attr('disabled', false).find('span').text('浏览上传');
            }
        });
    });
});
$('.text-upload').live("click", function () {
    $(this).siblings('.upload-file').trigger('click');
});

$(".photo-del").live("click", function () {
    var obj = $(this).parent(),
        obj_parent = obj.parent();
    obj.remove();
    obj_parent.find('img').size() == 0 && obj_parent.hide();
    var evidenceId = $(this).attr('evidence-id');
    if (evidenceId != '' && evidenceId != undefined) {
        $.get('/member/delEvidence/' + evidenceId + ".json", function (data) {
        });
    }
    $.utilBaseModal.positionUpdate('pop');
    return false;
});
