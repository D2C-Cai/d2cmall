package com.d2c.backend.rest.order;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.dto.HelpDTO;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.dto.MemberTagRelationDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.model.MemberTag;
import com.d2c.member.query.MemberTagRelationSearcher;
import com.d2c.member.service.MemberInfoService;
import com.d2c.member.service.MemberTagRelationService;
import com.d2c.order.dto.CouponDto;
import com.d2c.order.model.Coupon;
import com.d2c.order.model.CouponDef;
import com.d2c.order.model.CouponDef.CouponType;
import com.d2c.order.query.CouponDefSearcher;
import com.d2c.order.query.CouponSearcher;
import com.d2c.order.service.CouponDefQueryService;
import com.d2c.order.service.CouponDefService;
import com.d2c.order.service.CouponQueryService;
import com.d2c.order.service.CouponService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/promotion/coupon")
public class CouponCtrl extends BaseCtrl<CouponSearcher> {

    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponDefService couponDefService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CouponDefQueryService couponDefQueryService;
    @Autowired
    private CouponQueryService couponQueryService;
    @Autowired
    private MemberTagRelationService memberTagRelationService;

    @Override
    protected List<Map<String, Object>> getRow(CouponSearcher searcher, PageModel page) {
        // 通过查询条件和页面实现的规则，查出适合通用页面的表单数据
        PageResult<CouponDto> pr = couponQueryService.findBySearcher(searcher, page);
        List<CouponDto> list = null;
        if ((list = pr.getList()) == null) {
            list = new ArrayList<>();
        }
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        for (CouponDto coupon : list) {
            cellsMap = new HashMap<>();
            cellsMap.put("金额", coupon.getAmount());
            cellsMap.put("类型", coupon.getCouponTypeName());
            cellsMap.put("密码", coupon.getPassword());
            cellsMap.put("领取日期", coupon.getClaimedTime() == null ? "" : sdf.format(coupon.getExpireDate()));
            cellsMap.put("截止日期", coupon.getExpireDate() == null ? "" : sdf.format(coupon.getExpireDate()));
            cellsMap.put("优惠券使用说明", coupon.getRemark());
            cellsMap.put("优惠券ID", coupon.getId());
            cellsMap.put("领用人ID", coupon.getMemberId());
            cellsMap.put("领用人账号", coupon.getLoginCode());
            cellsMap.put("状态", coupon.getCouponStatusName());
            cellsMap.put("使用条件金额", coupon.getNeedAmount());
            cellsMap.put("类型", coupon.getCouponTypeName());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected int count(CouponSearcher searcher) {
        BeanUt.trimString(searcher);
        int count = couponQueryService.countBySearcher(searcher); // 通过查询规则查找出对应的数量
        return count;
    }

    @Override
    protected String getFileName() {
        return "优惠券表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"金额", "类型", "密码", "领取日期", "截止日期", "优惠券使用说明", "优惠券ID", "领用人ID", "领用人账号", "状态", "使用条件金额",
                "类型"};
    }

    @Override
    protected Response doHelp(CouponSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.analyseCouponOwner(false);
        List<HelpDTO> list = couponQueryService.findHelpDto(searcher, page);
        if (list.size() == 0 && StringUtils.isNotBlank(searcher.getCouponOwner())) {
            searcher.analyseCouponOwner(true);
            list = couponQueryService.findHelpDto(searcher, page);
        }
        SuccessResponse result = new SuccessResponse(list);
        return result;
    }

    @Override
    protected Response doList(CouponSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        searcher.analyseCouponOwner(false);
        PageResult<CouponDto> pager = couponQueryService.findBySearcher(searcher, page);
        if (pager.getTotalCount() == 0 && StringUtils.isNotBlank(searcher.getCouponOwner())) {
            searcher.analyseCouponOwner(true);
            pager = couponQueryService.findBySearcher(searcher, page);
        }
        SuccessResponse result = new SuccessResponse(pager);
        return result;
    }

    @Override
    protected Response findById(Long id) {
        if (id == null) {
            return new ErrorResponse("请输入正确的id");
        }
        return new ErrorResponse("不支持该查找id功能");
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // 这里的批量删除就是将优惠券作废的意思
        if (ids == null || ids.length == 0) {
            return new ErrorResponse("请输入正确的id");
        }
        for (Long couponId : ids) {
            if (couponId == null) {
                continue;
            }
            couponService.doInvalidCoupon(couponId);
        }
        return new SuccessResponse();
    }

    @Override
    protected Response doDelete(Long id) {
        // 这里的批量删除就是将优惠券作废的意思
        if (id == null) {
            return new ErrorResponse("请输入正确的id");
        }
        couponService.doInvalidCoupon(id);
        return new SuccessResponse();
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getExportFileType() {
        return "Coupon";
    }

    /**
     * 跳转到赠送用户现金券界面
     *
     * @param page
     * @param id
     * @return
     */
    @RequestMapping(value = "/giveBonus", method = RequestMethod.POST)
    public Response giveBonus(PageModel page, String[] id) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < id.length; i++) {
            sb.append(id[i] + ",");
        }
        CouponDefSearcher couponDefSearcher = new CouponDefSearcher();
        couponDefSearcher.setType(CouponType.CASH);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        PageResult<CouponDef> pager = couponDefQueryService.findBySearcher(page, couponDefSearcher);
        SuccessResponse result = new SuccessResponse(pager);
        result.put("memberId", sb.toString());
        return result;
    }

    /**
     * 后台赠送现金券
     *
     * @param memberId
     * @param defineId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bonus", method = RequestMethod.POST)
    public Response doBonus(String memberId, Long defineId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        String[] memberIds = memberId.split(",");
        CouponDef couponDef = couponDefQueryService.findById(defineId);
        if (!couponDefQueryService.checkQuantity(couponDef, memberIds.length)) {
            result.setStatus(-1);
            result.setMessage("超出发行总量！");
            return result;
        }
        for (int i = 0; i < memberIds.length; i++) {
            MemberInfo memberInfo = memberInfoService.findById(Long.valueOf(memberIds[i]));
            Coupon coupon;
            try {
                coupon = couponDefService.doClaimedCoupon(defineId, Long.valueOf(memberIds[i]),
                        memberInfo.getLoginCode(), admin.getUsername(), "backend", true);
                if (coupon == null || coupon.getId() <= 0) {
                    result.setStatus(-1);
                    result.setMessage("不满足发放条件！\n请检查优惠券发行总量，单人限领以及限领时间！");
                    break;
                }
            } catch (BusinessException e) {
                logger.error(e.getMessage());
                result.setMessage(e.getMessage());
                result.setStatus(-1);
            }
        }
        return result;
    }

    /**
     * 后台赠送现金券
     *
     * @param memberTagId
     * @param defineId
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/bonus/tag", method = RequestMethod.POST)
    public Response doBonus(Long[] memberTagId, Long defineId) throws NotLoginException {
        SuccessResponse result = new SuccessResponse();
        Admin admin = this.getLoginedAdmin();
        CouponDef couponDef = couponDefQueryService.findById(defineId);
        if (couponDef == null) {
            result.setStatus(-1);
            result.setMessage("优惠券不存在！");
            return result;
        }
        for (int ii = 0; memberTagId != null && ii < memberTagId.length; ii++) {
            PageModel pageModel = new PageModel();
            pageModel.setPageSize(500);
            int pagerNumber = 1;
            MemberTagRelationSearcher searcher = new MemberTagRelationSearcher();
            searcher.setTagId(memberTagId[ii]);
            PageResult<MemberTag> pageResult = new PageResult<>();
            int totalCount = memberTagRelationService.countBySearch(searcher);
            pageResult.setTotalCount(totalCount);
            pageResult.setPageCount(totalCount / pageModel.getPageSize());
            boolean exportSuccess = true;
            do {
                pageModel.setPageNumber(pagerNumber);
                PageResult<MemberTagRelationDto> memberRelation = memberTagRelationService.findBySearch(pageModel,
                        searcher);
                for (int i = 0; i < memberRelation.getList().size(); i++) {
                    MemberTagRelationDto dto = (memberRelation.getList().get(i));
                    Coupon coupon;
                    try {
                        coupon = couponDefService.doClaimedCoupon(defineId, Long.valueOf(dto.getMemberInfo().getId()),
                                dto.getMemberInfo().getLoginCode(), admin.getUsername(), "backend", true);
                        if (coupon == null || coupon.getId() <= 0) {
                            result.setStatus(-1);
                            result.setMessage("不满足发放条件！\n请检查优惠券发行总量，单人限领以及限领时间！");
                            break;
                        }
                    } catch (BusinessException e) {
                        logger.error(e.getMessage());
                        result.setMessage(e.getMessage());
                        result.setStatus(-1);
                        return result;
                    }
                }
                pagerNumber = pagerNumber + 1;
            } while (pagerNumber <= pageResult.getPageCount() + 1 && exportSuccess);
        }
        return result;
    }

    /**
     * 发放优惠券
     *
     * @param id
     * @param sendmark
     * @return
     * @throws NotLoginException
     */
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Response doSend(Long[] id, String sendmark) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        for (Long couponId : id) {
            if (couponId == null) {
                continue;
            }
            couponService.doSendCoupon(couponId, admin.getUsername(), sendmark);
        }
        Response resp = new SuccessResponse();
        resp.setMessage("发放成功");
        return resp;
    }

    /**
     * 作废优惠券
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    public Response doInvalid(Long[] id) {
        for (Long couponId : id) {
            if (couponId == null) {
                continue;
            }
            couponService.doInvalidCoupon(couponId);
        }
        return new SuccessResponse();
    }

    /**
     * 生成未领用的密码券
     *
     * @param defineId
     * @return
     */
    @RequestMapping(value = "/createbonus/{defineId}", method = RequestMethod.POST)
    public Response createPasswdCoupon(@PathVariable Long defineId) {
        SuccessResponse result = new SuccessResponse();
        CouponDef couponDef = couponDefQueryService.findById(defineId);
        if (!couponDefQueryService.checkQuantity(couponDef, couponDef.getQuantity())) {
            result.setStatus(-1);
            result.setMessage("超出发行总量！");
            return result;
        }
        int res = couponDefService.doCreatePasswdCoupon(defineId, couponDef.getQuantity());
        if (0 == res) {
            result.setStatus(-1);
            result.setMessage("生成不成功!");
        }
        return result;
    }

}
