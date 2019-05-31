package com.d2c.flame.controller.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.model.Comment;
import com.d2c.member.service.CommentService;
import com.d2c.member.service.MemberShareService;
import com.d2c.member.service.WardrobeCollocationService;
import com.d2c.product.service.BrandService;
import com.d2c.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * UpYun通知回调
 */
@RestController
@RequestMapping("/upyun")
public class UpYunController extends BaseController {

    @Autowired
    private MemberShareService memberShareService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private UpyunTaskService upyunTaskService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private WardrobeCollocationService wardrobeCollocationService;

    /**
     * UpYun回调
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/callback", method = {RequestMethod.GET, RequestMethod.POST})
    public void upyunNotify(HttpServletRequest request) throws Exception {
        String code = request.getParameter("status_code");
        String task_ids = request.getParameter("task_id");
        String path = request.getParameter("path[0]");
        // String error = request.getParameter("error");
        // String signature = request.getParameter("signature");
        // String bucket_name = request.getParameter("bucket_name");
        if ("200".equals(code)) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(task_ids);
            upyunTask.setStatus(1);
            if (path.contains(".png") || path.contains(".jpg")) {
                upyunTask.setPic(path);
            } else {
                upyunTask.setVideo(path);
            }
            upyunTask = upyunTaskService.callBackInsert(upyunTask);
            if (upyunTask != null && upyunTask.getSourceId() != null) {
                try {
                    SourceType sourceType = SourceType.valueOf(upyunTask.getSourceType());
                    switch (sourceType) {
                        case PRODUCT:
                            if (upyunTask.getVideo() != null) {
                                productService.updateVideoById(upyunTask.getSourceId(), path);
                            }
                            break;
                        case BRAND:
                            if (upyunTask.getVideo() != null) {
                                brandService.updateVideoById(upyunTask.getSourceId(), path);
                            }
                            break;
                        case SHARE:
                            if (upyunTask.getPic() != null) {
                                memberShareService.updatePicById(upyunTask.getSourceId(), path);
                            }
                            if (upyunTask.getVideo() != null) {
                                memberShareService.updateVideoById(upyunTask.getSourceId(), path);
                            }
                            break;
                        case COMMENT:
                            commentService.updateVideoById(upyunTask.getSourceId(), path);
                            Comment comment = commentService.findById(upyunTask.getSourceId());
                            if (comment.getShareId() != null && upyunTask.getPic() != null) {
                                memberShareService.updatePicById(upyunTask.getSourceId(), path);
                            }
                            if (comment.getShareId() != null && upyunTask.getVideo() != null) {
                                memberShareService.updateVideoById(upyunTask.getSourceId(), path);
                            }
                            break;
                        case WARDROBEC:
                            if (upyunTask.getVideo() != null) {
                                wardrobeCollocationService.updateVideoById(upyunTask.getSourceId(), path);
                            }
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 考拉图片
     *
     * @param request
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    @RequestMapping(value = "/callback/kaola", method = RequestMethod.POST)
    public void callbackKaolaPics(HttpServletRequest request) throws UnsupportedEncodingException, IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            responseStrBuilder.append(inputStr);
        }
        JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
        String param = jsonObject.toJSONString();
        JSONObject obj = JSON.parseObject(param);
        UpyunTask upyunTask = new UpyunTask();
        upyunTask.setTaskIds(obj.getString("task_id"));
        upyunTask.setPic(obj.getString("path"));
        upyunTask.setStatus(1);
        upyunTaskService.insert(upyunTask);
    }

}
