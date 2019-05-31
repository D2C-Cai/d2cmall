<form name="commentReply-form" class="validate-form form" action="/comment/reply" method="post" success-tip="追加成功!"
      style="width:444px">
    <p class="comment-list-add">追加评论</p>
    <input type="hidden" value="${commentId}" name="commentId">
    <input type="hidden" value="${replyId}" name="replyId">
    <textarea name="content" id="content" class="input" placeholder="请添加您的追评信息"
              style="width:420px;margin:0 12px;height:100px"></textarea>
    <div class="tip tip-validate" data-target="content"></div>
    <div class="form-button">
        <button type="submit" class="button button-l button-red">提交</button>
    </div>
</form>
