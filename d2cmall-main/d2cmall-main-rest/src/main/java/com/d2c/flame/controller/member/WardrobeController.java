package com.d2c.flame.controller.member;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.UpyunTask;
import com.d2c.logger.model.UpyunTask.SourceType;
import com.d2c.logger.service.UpyunTaskService;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.Wardrobe;
import com.d2c.member.model.WardrobeCategory;
import com.d2c.member.model.WardrobeCollocation;
import com.d2c.member.query.WardrobeCollocationSearcher;
import com.d2c.member.query.WardrobeSearcher;
import com.d2c.member.service.WardrobeCategoryService;
import com.d2c.member.service.WardrobeCollocationService;
import com.d2c.member.service.WardrobeService;
import com.d2c.product.service.ProductThirdService;
import com.d2c.util.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 衣橱
 *
 * @author Lain
 */
@RestController
@RequestMapping(value = "/v3/api/wardrobe")
public class WardrobeController extends BaseController {

    @Autowired
    private WardrobeService wardrobeService;
    @Autowired
    private WardrobeCategoryService wardrobeCategoryService;
    @Autowired
    private WardrobeCollocationService wardrobeCollocationService;
    @Autowired
    private UpyunTaskService upyunTaskService;
    @Reference
    private ProductThirdService productThirdService;

    /**
     * 衣橱列表
     *
     * @param query
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult list(WardrobeSearcher query, PageModel page) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        query.setMemberId(memberInfo.getId());
        PageResult<Wardrobe> pager = wardrobeService.findBySearcher(query, page);
        JSONArray array = new JSONArray();
        pager.forEach(w -> array.add(w.toJson()));
        result.putPage("myWardrobes", pager, array);
        return result;
    }

    /**
     * 新增衣橱
     *
     * @param wardrobe
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseResult insert(Wardrobe wardrobe) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        wardrobe.setMemberId(memberInfo.getId());
        wardrobe.setNickName(memberInfo.getDisplayName());
        wardrobe.setLoginCode(memberInfo.getLoginCode());
        WardrobeCategory category = wardrobeCategoryService.findById(wardrobe.getCategoryId());
        wardrobe.setTopName(category.getTopName());
        wardrobe.setCategoryName(category.getName());
        wardrobe = wardrobeService.insert(wardrobe);
        result.put("wardrobe", wardrobe.toJson());
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseResult update(Wardrobe wardrobe) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        WardrobeCategory category = wardrobeCategoryService.findById(wardrobe.getCategoryId());
        wardrobe.setTopName(category.getTopName());
        wardrobe.setCategoryName(category.getName());
        wardrobe.setLastModifyMan(memberInfo.getLoginCode());
        wardrobeService.update(wardrobe);
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseResult delete(Long[] id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        wardrobeService.deleteByMemberId(id, memberInfo.getId());
        return result;
    }

    /**
     * 衣橱分类
     *
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseResult category(String topName) {
        ResponseResult result = new ResponseResult();
        List<WardrobeCategory> list = wardrobeCategoryService.findByTop(topName, 1);
        JSONObject obj = new JSONObject();
        list.forEach(wca -> {
            if (obj.getJSONArray(wca.getTopName()) == null) {
                obj.put(wca.getTopName(), new JSONArray());
            }
            obj.getJSONArray(wca.getTopName()).add(wca.toJson());
        });
        result.put("topCategorys", obj);
        return result;
    }

    /**
     * 新增衣橱搭配
     *
     * @param wardrobeCollocation
     * @return
     */
    @RequestMapping(value = "/collocation/insert", method = RequestMethod.POST)
    public ResponseResult insertCollocation(WardrobeCollocation wardrobeCollocation, String taskIds) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        wardrobeCollocation.setMemberId(memberInfo.getId());
        wardrobeCollocation.setNickname(memberInfo.getDisplayName());
        wardrobeCollocation.setLoginCode(memberInfo.getLoginCode());
        wardrobeCollocation.setHeadPic(memberInfo.getHeadPic());
        if (wardrobeCollocation.getTransactionTime() == null) {
            wardrobeCollocation.setTransactionTime(new Date());
        }
        if (StringUtils.isNotBlank(taskIds) && wardrobeCollocation.getId() != null) {
            // 短视频转码任务
            this.processTaskIds(taskIds, wardrobeCollocation.getId());
        }
        wardrobeCollocation = wardrobeCollocationService.insert(wardrobeCollocation);
        result.put("wardrobeCollocation", wardrobeCollocation.toJson());
        return result;
    }

    /**
     * 衣橱搭配列表
     *
     * @param wardrobeCollocationSearcher
     * @param page
     * @return
     */
    @RequestMapping(value = "/collocation/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult collocationList(WardrobeCollocationSearcher searcher) {
        ResponseResult result = new ResponseResult();
        Long memberId = searcher.getMemberId();
        searcher.setStatus(1);
        searcher.setOpen(1);
        try {
            MemberInfo memberInfo = this.getLoginMemberInfo();
            if (memberId == null || memberId.equals(memberInfo.getId())) {
                searcher.setMemberId(memberInfo.getId());
                memberId = memberInfo.getId();
                searcher.setStatus(null);
                searcher.setOpen(null);
            }
        } catch (NotLoginException e) {
            if (searcher.getMemberId() == null) {
                result.putPage("myWardrobeCollocations", new PageResult<>(), new JSONArray());
                return result;
            }
        }
        PageResult<WardrobeCollocation> pager = wardrobeCollocationService.findMine(memberId, searcher,
                new PageModel(1, 100));
        JSONArray array = new JSONArray();
        pager.forEach(wco -> array.add(wco.toJson()));
        result.putPage("myWardrobeCollocations", pager, array);
        return result;
    }

    @RequestMapping(value = "/collocation/update", method = RequestMethod.POST)
    public ResponseResult collocationUpdate(WardrobeCollocation wardrobeCollocation) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        wardrobeCollocation.setLastModifyMan(memberInfo.getLoginCode());
        wardrobeCollocation.setStatus(null);
        wardrobeCollocation.setOpen(null);
        wardrobeCollocationService.update(wardrobeCollocation);
        return result;
    }

    @RequestMapping(value = "/collocation/delete", method = RequestMethod.POST)
    public ResponseResult deleteCollocation(Long[] id) {
        ResponseResult result = new ResponseResult();
        MemberInfo memberInfo = this.getLoginMemberInfo();
        wardrobeCollocationService.deleteByMemberId(id, memberInfo.getId());
        return result;
    }

    /**
     * 别人的搭配
     *
     * @return
     */
    @RequestMapping(value = "/collocation/recommend/list", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult recommend(PageModel page) {
        ResponseResult result = new ResponseResult();
        // MemberInfo memberInfo = this.getLoginMemberInfo();
        WardrobeCollocationSearcher query = new WardrobeCollocationSearcher();
        query.setStatus(1);
        // query.setNoMemberId(memberInfo.getId()); 25921允许看到自己的
        query.setOpen(1);
        PageResult<WardrobeCollocation> pager = wardrobeCollocationService.findBySearcher(query, page);
        JSONArray array = new JSONArray();
        pager.forEach(wco -> array.add(wco.toJson()));
        result.putPage("recommends", pager, array);
        return result;
    }

    @RequestMapping(value = "/image/recognition", method = RequestMethod.POST)
    public ResponseResult imageRecognition(String picUrl) {
        ResponseResult result = new ResponseResult();
        // 用户上传图片搜索
        JSONArray array = new JSONArray();
        if (StringUtil.isNotBlank(picUrl)) {
            array = productThirdService.imageRecognition(picUrl);
        }
        result.put("array", array);
        return result;
    }

    /**
     * 更新视频
     *
     * @param taskIds
     * @param collocationId
     */
    private void processTaskIds(String taskIds, Long collocationId) {
        String[] taskIdss = taskIds.split(",");
        for (String taskId : taskIdss) {
            UpyunTask upyunTask = new UpyunTask();
            upyunTask.setTaskIds(taskId);
            upyunTask.setStatus(0);
            upyunTask.setSourceType(SourceType.WARDROBEC.toString());
            upyunTask.setSourceId(collocationId);
            upyunTask = upyunTaskService.insert(upyunTask);
            if (upyunTask.getStatus() == 1 && upyunTask.getVideo() != null) {
                wardrobeCollocationService.updateVideoById(collocationId, upyunTask.getVideo());
            }
        }
    }

}
