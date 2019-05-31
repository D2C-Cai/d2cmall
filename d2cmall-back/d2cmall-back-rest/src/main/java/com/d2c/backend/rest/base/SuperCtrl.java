package com.d2c.backend.rest.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.d2c.common.api.query.model.RoleQuery;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.AssertException;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.frame.backweb.control.BackControl;
import com.d2c.member.dto.AdminDto;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MemberInfo;
import com.d2c.member.service.AdminService;
import com.d2c.member.service.MemberInfoService;
import com.d2c.product.service.BrandService;
import com.d2c.product.third.upyun.PictureModel;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.ExcelUtil;
import com.d2c.util.file.ExportFileBean;
import com.d2c.util.serial.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SuperCtrl extends BackControl {

    public static final boolean PERMISSION_DEBUG = false; // 是否屏蔽权限
    @Autowired
    protected AdminService adminService;
    @Autowired
    protected MemberInfoService memberInfoService;
    @Autowired
    protected BrandService brandService;

    /**
     * 获取Post中Body的对象
     */
    public <T> T getBean(Class<T> clz) {
        return getBean(getJSONObject(), clz);
    }

    public <T> T getBean(JSONObject json, Class<T> clz) {
        return JsonUtil.instance().toObject(json, clz);
    }

    /**
     * 请求签名验证
     */
    public JSONObject getJSONObject() {
        try {
            String json = receivePost();
            JSONObject jsonObj = JSONObject.parseObject(json, Feature.OrderedField);
            return jsonObj.getJSONObject("data");
        } catch (Exception e) {
            throw new BaseException(e);
        }
    }

    @RequestMapping(method = RequestMethod.OPTIONS, value = "/**")
    public void options() {
    }

    /**
     * request payload ajax 请求的JSON数据预处理
     */
    private String receivePost() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String reqBody = sb.toString();
        return URLDecoder.decode(reqBody, "utf-8");
    }

    /**
     * 获取登录用户, 1.从cookie中获取token数据 2.拿着token去缓存中查找Admin，判断是否有效
     *
     * @return
     * @throws NotLoginException
     */
    protected AdminDto getLoginedAdmin() {
        String tgt = getRequest().getHeader("accesstoken");
        Admin admin = adminService.findAdminByTicket(tgt);
        if (admin == null) {
            MemberInfo member = memberInfoService.findByToken(tgt);
            if (member != null && member.getDesignerId() != null) {
                return this.getAdminDto(tgt, member);
            } else if (member != null && member.getStoreId() != null) {
                return this.getAdminDto(tgt, member);
            } else {
                throw new NotLoginException("用户未登录或已经过期，请重新登陆！");
            }
        } else {
            AdminDto dto = new AdminDto();
            BeanUtils.copyProperties(admin, dto);
            return dto;
        }
    }

    protected AdminDto getAdminDto(String token, MemberInfo member) {
        AdminDto newAdmin = new AdminDto();
        newAdmin.setTgt(token);
        newAdmin.setMemberId(member.getId());
        newAdmin.setNickname(member.getNickname());
        newAdmin.setHeadPic(member.getHeadPic());
        newAdmin.setUsername(member.getLoginCode());
        newAdmin.setDesignerId(member.getDesignerId());
        newAdmin.setAgreeDate(member.getAgreeDate());
        newAdmin.setDisplayName(member.getDisplayName());
        newAdmin.setStoreId(member.getStoreId());
        return newAdmin;
    }

    protected void initSearcherByRole(AdminDto admin, RoleQuery searcher) {
        if (admin.getDesignerId() != null) {
            List<Long> brandIds = brandService.findIdsByDesignersId(admin.getDesignerId());
            searcher.setBrandIds(brandIds);
        } else if (admin.getStoreId() != null) {
            searcher.setStoreId(admin.getStoreId());
        }
    }

    /**
     * 检查时间间隔(注：防止数据量太大)
     */
    protected boolean checkDate(SuccessResponse result, Date startDate, Date endDate, Long limitTime) {
        Long oneDay = 24 * 60 * 60 * 1000L;
        if (limitTime == null) {
            limitTime = 365 * oneDay; // 默认限制时间
        }
        Date now = new Date();
        if (startDate == null) {
            startDate = new Date(now.getTime() - oneDay);
        }
        if (endDate == null) {
            endDate = now;
        }
        if (endDate.getTime() - startDate.getTime() > limitTime) {
            result.setStatus(-1);
            result.setMessage("最多导出" + limitTime / oneDay + "天的数据！");
            return false;
        }
        return true;
    }

    /**
     * 检查时间间隔(注：防止数据量太大)
     */
    protected boolean checkDate(SuccessResponse result, Date startDate, Date endDate) {
        return this.checkDate(result, startDate, endDate, null);
    }

    /**
     * 创建excel文件
     */
    protected boolean createExcel(ExcelUtil excelUtil, String[] titleNames, List<Map<String, Object>> rowList) {
        excelUtil.createExcel(titleNames, rowList);
        return excelUtil.getExportFileBean().getErrorMsg().length() == 0;
    }

    /**
     * 创建excel文件
     */
    protected boolean createQrcodeExcel(ExcelUtil excelUtil, List<String> inernalSNs, List<BufferedImage> imgs,
                                        int rowNum) {
        excelUtil.exportQRcodeExcel(inernalSNs, imgs, rowNum);
        return excelUtil.getExportFileBean().getErrorMsg().length() == 0;
    }

    /**
     * 将excel文件路径抛给js
     */
    protected void createExcelResult(SuccessResponse result, ExportFileBean fileBean) {
        if (fileBean.getErrorMsg().length() > 0) {
            result.setStatus(-1);
            result.setMessage(fileBean.getErrorMsg().toString());
        } else {
            result.put("paths", fileBean.getDownloadPath());
        }
    }

    /**
     * 将excel文件路径抛给js
     */
    protected void createExcelResult(SuccessResponse result, StringBuilder errMsg, String downLoadPath) {
        if (errMsg.length() > 0) {
            result.setStatus(-1);
            result.setMessage(errMsg.toString());
        } else {
            result.put("paths", downLoadPath);
        }
    }

    protected List<Map<String, Object>> getExcelData(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartRequest.getFile("file");
        if (commonsMultipartFile == null) {
            throw new AssertException("文件上传不成功！");
        }
        if (commonsMultipartFile.getSize() > 5 * 1024 * 1024) {
            throw new AssertException("文件不能大于5M！");
        }
        String realFileName = commonsMultipartFile.getOriginalFilename();
        if (StringUtils.isBlank(realFileName)) {
            throw new AssertException("文件未提交！");
        }
        String extension = org.springframework.util.StringUtils.getFilenameExtension(realFileName).toLowerCase();
        if (!(extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx"))) {
            throw new AssertException("导入异常：不支持的格式，格式要求:xls,xlsx");
        }
        ExcelUtil excelUtil = new ExcelUtil();
        List<Map<String, Object>> excelData;
        try {
            excelData = excelUtil.getExcelData(commonsMultipartFile.getFileItem().getInputStream());
            return excelData;
        } catch (Exception e) {
            throw new AssertException("文件内容异常！");
        }
    }

    @ExceptionHandler({NotLoginException.class})
    @ResponseBody
    public Response handleNotLoginException(NotLoginException ex) {
        Response result = new ErrorResponse("没有登陆");
        result.setLogin(false);
        return result;
    }

    @ExceptionHandler({BusinessException.class, AssertException.class})
    @ResponseBody
    public Response handleBusinessException(BaseException ex) {
        Response result = new ErrorResponse(ex.getMessage());
        return result;
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response handleException(Exception ex) {
        logger.error(ex.getMessage(), ex);
        Response result = new ErrorResponse(ex.getMessage());
        return result;
    }

    /**
     * dubbo
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public Response handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        Response result = new ErrorResponse("服务调用异常：" + ex.getMessage());
        return result;
    }

    protected File writeToFile(CommonsMultipartFile item) throws IOException {
        String EXCEL_SAVE_PATH = "/mnt/www/download";// 保存路径
        Date date = new Date();
        int year = DateUtil.getYearOfDate(date);
        int month = DateUtil.getMonthOfYear(date);
        int day = DateUtil.getDayOfMonth(date);
        String folderName = "/excel/" + year + "/" + month + "/" + day;
        File dir = new File(EXCEL_SAVE_PATH + folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        SimpleDateFormat format = new SimpleDateFormat("yy_MM_dd_HH_mm_ss");
        String currentTime = format.format(new Date());
        String fileName = currentTime + "_" + item.getOriginalFilename();
        String outPath = dir.getPath() + "/" + fileName;
        File file = new File(outPath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            item.transferTo(file);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return file;
    }

    protected void uploadPic2CloudStore(String pictureUrl) {
        if (pictureUrl == null || pictureUrl.length() < 1) {
            logger.error("文件地址不正确！");
        }
        PictureModel picture;
        try {
            picture = PictureModel.class.newInstance().newInstanceFromUrl(pictureUrl);
        } catch (Exception e) {
            throw new BusinessException("本地不存在此文件：" + pictureUrl);
        }
        picture.setPath(pictureUrl);
        picture.upload2CloudStore();
    }

}
