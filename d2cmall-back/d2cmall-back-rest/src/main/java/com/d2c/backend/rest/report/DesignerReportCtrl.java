package com.d2c.backend.rest.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.property.HttpProperties;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.member.dto.AdminDto;
import com.d2c.order.model.Order;
import com.d2c.product.model.Brand;
import com.d2c.product.service.BrandService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.CSVUtil;
import com.d2c.util.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/rest/report/designer")
public class DesignerReportCtrl extends BaseCtrl<BaseQuery> {

    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private BrandService brandService;
    @Autowired
    private HttpProperties httpProperties;

    public static void main(String[] args) {
        String brandsStr = "L001";
        String brandsParam = null;
        try {
            brandsParam = URLEncoder.encode(brandsStr.substring(0, brandsStr.length()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        JSONObject json = restTemplate.postForObject(
                "http://192.168.0.141:8071" + "/api/crm/orderitem/countbyday" + "?brands=" + brandsParam, entity,
                JSONObject.class);
        System.out.println(json.toJSONString());
    }

    @Override
    protected Response doList(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BaseQuery searcher) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected String getExportFileType() {
        return "DesignerReport";
    }

    @Override
    protected List<Map<String, Object>> getRow(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String getFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String[] getExportTitles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doHelp(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequestMapping(value = "/sales/count", method = RequestMethod.POST)
    public JSONObject salesCount(String type) throws NotLoginException {
        JSONObject json = getDateObj4Count(type, "/api/crm/orderitem/sales/count");
        return json;
    }

    @RequestMapping(value = "/delivery/count", method = RequestMethod.POST)
    public JSONObject deliveryCount(String type) throws NotLoginException {
        JSONObject json = getDateObj4Count(type, "/api/crm/orderitem/delivery/count");
        return json;
    }

    @RequestMapping(value = "/close/count", method = RequestMethod.POST)
    public JSONObject closeCount(String type) throws NotLoginException {
        JSONObject json = getDateObj4Count(type, "/api/crm/orderitem/close/count");
        return json;
    }

    @RequestMapping(value = "/sales/list", method = RequestMethod.POST)
    public JSONObject sales(Date day, Integer year, Integer month, PageModel page, String storeCode, String skuSn)
            throws NotLoginException {
        JSONObject params = new JSONObject();
        params.put("storeCode", storeCode);
        params.put("skuSn", skuSn);
        JSONObject json = getDateObj(day, year, month, "/api/crm/orderitem/sales/list", page, params);
        return json;
    }

    @RequestMapping(value = "/delivery/list", method = RequestMethod.POST)
    public JSONObject delivery(Date day, Integer year, Integer month, PageModel page, String storeCode, String skuSn)
            throws NotLoginException {
        JSONObject params = new JSONObject();
        params.put("storeCode", storeCode);
        params.put("skuSn", skuSn);
        JSONObject json = getDateObj(day, year, month, "/api/crm/orderitem/delivery/list", page, params);
        return json;
    }

    @RequestMapping(value = "/close/list", method = RequestMethod.POST)
    public JSONObject close(Date day, Integer year, Integer month, PageModel page, String storeCode, String skuSn)
            throws NotLoginException {
        JSONObject params = new JSONObject();
        params.put("storeCode", storeCode);
        params.put("skuSn", skuSn);
        JSONObject json = getDateObj(day, year, month, "/api/crm/orderitem/close/list", page, params);
        return json;
    }

    private JSONObject getDateObj(Date day, Integer year, Integer month, String url, PageModel page, JSONObject params)
            throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getDesignerId() == null) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        List<Brand> brandList = brandService.findByDesignersId(admin.getDesignerId(), null);
        if (brandList == null || brandList.size() == 0) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        String brandsStr = "";
        for (Brand brand : brandList) {
            brandsStr = brand.getCode() + ",";
        }
        Date beginDate = null;
        Date endDate = null;
        if (year != null && month != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            day = calendar.getTime();
            beginDate = DateUtil.getStartOfMonth(day);
            endDate = DateUtil.getEndOfMonth(day);
        } else {
            if (day == null) {
                day = new Date();
            }
            beginDate = DateUtil.getStartOfDay(day);
            endDate = DateUtil.getEndOfDay(day);
        }
        Long beginDateTime = beginDate.getTime();
        Long endDateTime = endDate.getTime();
        String brandsParam = null;
        try {
            brandsParam = URLEncoder.encode(brandsStr.substring(0, brandsStr.length() - 1), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("系统发生错误，请联系客服"));
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        JSONObject json = restTemplate.postForObject(httpProperties.getBossApiUrl() + url + "?brands=" + brandsParam
                + "&beginDate=" + beginDateTime + "&endDate=" + endDateTime + "&pageSize=" + page.getPageSize() + "&p="
                + page.getP() + "&params=" + params.toJSONString(), entity, JSONObject.class, params.toJSONString());
        if (json == null) {
            json = (JSONObject) JSON.toJSON(new ErrorResponse("boss系统连接异常"));
        }
        return json;
    }

    private JSONObject getDateObj4Count(String type, String url) throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getDesignerId() == null) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        List<Brand> brandList = brandService.findByDesignersId(admin.getDesignerId(), null);
        if (brandList == null || brandList.size() == 0) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        String brandsStr = "";
        for (Brand brand : brandList) {
            brandsStr = brand.getCode() + ",";
        }
        Date beginDate = null;
        Date endDate = null;
        Date day = new Date();
        if (type == null || "today".equals(type)) {
            beginDate = DateUtil.getStartOfDay(day);
            endDate = DateUtil.getEndOfDay(day);
        } else {
            beginDate = DateUtil.getStartOfMonth(day);
            endDate = DateUtil.getEndOfMonth(day);
        }
        Long beginDateTime = beginDate.getTime();
        Long endDateTime = endDate.getTime();
        String brandsParam = null;
        try {
            brandsParam = URLEncoder.encode(brandsStr.substring(0, brandsStr.length() - 1), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("系统发生错误，请联系客服。"));
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        JSONObject json = restTemplate.postForObject(httpProperties.getBossApiUrl() + url + "?brands=" + brandsParam
                + "&beginDate=" + beginDateTime + "&endDate=" + endDateTime, entity, JSONObject.class);
        if (json == null) {
            json = (JSONObject) JSON.toJSON(new ErrorResponse("boss系统连接异常"));
        }
        return json;
    }

    @RequestMapping(value = "/excel/{type}", method = RequestMethod.POST)
    public JSONObject excel(HttpServletRequest request, HttpServletResponse response, @PathVariable String type,
                            Date day, Integer year, Integer month) throws BusinessException, NotLoginException {
        SuccessResponse result = new SuccessResponse();
        AdminDto admin = this.getLoginedAdmin();
        String fileName = admin.getUsername() + "_" + "设计师+" + getTypeName(type) + "+报表"; // 报表初始化
        String[] titleNames = null;
        titleNames = getTitleNames(type);
        CSVUtil csvUtil = new CSVUtil();
        csvUtil.setFileName(fileName);
        csvUtil.writeTitleToFile(titleNames);
        PageModel page = new PageModel();
        PageResult<Order> pager = new PageResult<>(page);
        page.setPageSize(PageModel.MAX_PAGE_SIZE);
        int pagerNumber = 1;
        if (admin.getDesignerId() == null) { // 设计师参数
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        List<Brand> brandList = brandService.findByDesignersId(admin.getDesignerId(), null);
        if (brandList == null || brandList.size() == 0) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        String brandsStr = "";
        for (Brand brand : brandList) {
            brandsStr = brand.getCode() + ",";
        }
        Date beginDate = null; // 时间参数
        Date endDate = null;
        if (year != null && month != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            day = calendar.getTime();
            beginDate = DateUtil.getStartOfMonth(day);
            endDate = DateUtil.getEndOfMonth(day);
        } else {
            if (day == null) {
                day = new Date();
            }
            beginDate = DateUtil.getStartOfDay(day);
            endDate = DateUtil.getEndOfDay(day);
        }
        Long beginDateTime = beginDate.getTime();
        Long endDateTime = endDate.getTime();
        String brandsParam = null;
        try {
            brandsParam = URLEncoder.encode(brandsStr.substring(0, brandsStr.length()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("系统发生错误，请联系客服。"));
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        JSONObject countJson = JSON.parseObject(HttpUtil.sendPostHttps(
                httpProperties.getBossApiUrl() + "/api/crm/orderitem/" + type + "/count" + "?brands=" + brandsParam
                        + "&beginDate=" + beginDateTime + "&endDate=" + endDateTime,
                new HashMap<String, Object>(), null));
        pager.setTotalCount(countJson.getJSONObject("datas").getInteger(type + "Count"));
        boolean exportSuccess = true;
        do {
            page.setPageNumber(pagerNumber);
            JSONObject json = JSON.parseObject(HttpUtil.sendPostHttps( // 分页查询数据
                    httpProperties.getBossApiUrl() + "/api/crm/orderitem/" + type + "/list" + "?brands=" + brandsParam
                            + "&beginDate=" + beginDateTime + "&endDate=" + endDateTime + "&pageSize="
                            + page.getPageSize() + "&p=" + page.getP(),
                    new HashMap<String, Object>(), null));
            List<Map<String, Object>> list = getRowList(
                    json.getJSONObject("datas").getJSONObject("pager").getJSONArray("list"), type);
            exportSuccess = csvUtil.writeRowToFile(list);
            pagerNumber = pagerNumber + 1;
        } while (pagerNumber <= pager.getPageCount() && exportSuccess);
        createExcelResult(result, csvUtil.getErrorMsg(), csvUtil.getOutPath());
        saveLog(csvUtil.getFileName(), csvUtil.getOutPath(), csvUtil.getFileSize(), this.getExportFileType());
        return (JSONObject) JSON.toJSON(new SuccessResponse());
    }

    private String[] getTitleNames(String type) {
        switch (type) {
            case "sales":
                return new String[]{"订单支付支付时间", "货号", "SKU", "颜色", "尺码", "数量", "销售终端", "设计师货号"};
            case "close":
                return new String[]{"订单退货完成时间", "货号", "SKU", "颜色", "尺码", "数量", "销售终端", "设计师货号"};
            case "delivery":
                return new String[]{"订单发货时间", "货号", "SKU", "颜色", "尺码", "数量", "销售终端", "设计师货号"};
        }
        return new String[]{""};
    }

    private List<Map<String, Object>> getRowList(JSONArray arrays, String type) {
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String typeTransactionDateName = getTypeTransactionDateName(type);
        for (int i = 0; i < arrays.size(); i++) {
            cellsMap = new HashMap<>();
            JSONObject json = arrays.getJSONObject(i);
            cellsMap.put(typeTransactionDateName, sdf.format(json.getLong("paymentTime")));
            cellsMap.put("货号", json.getString("productSn"));
            cellsMap.put("SKU", json.getString("productSkuSn"));
            cellsMap.put("颜色", json.getString("sale1"));
            cellsMap.put("尺码", json.getString("sale2"));
            cellsMap.put("数量", json.getInteger("quantity"));
            cellsMap.put("销售终端", json.get("shopName") == null ? "官网" : json.getString("shopName"));
            cellsMap.put("设计师货号", json.getString("externalSn"));
            rowList.add(cellsMap);
        }
        return rowList;
    }

    private String getTypeName(String type) {
        switch (type) {
            case "sales":
                return "销售";
            case "close":
                return "退货";
            case "delivery":
                return "发货";
        }
        return "";
    }

    private String getTypeTransactionDateName(String type) {
        switch (type) {
            case "sales":
                return "订单支付支付时间";
            case "close":
                return "订单退货完成时间";
            case "delivery":
                return "订单发货时间";
        }
        return "";
    }

    @RequestMapping(value = "/count/byday", method = RequestMethod.POST)
    public JSONObject countByDay() throws NotLoginException {
        AdminDto admin = this.getLoginedAdmin();
        if (admin.getDesignerId() == null) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        List<Brand> brandList = brandService.findByDesignersId(admin.getDesignerId(), null);
        if (brandList == null || brandList.size() == 0) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("你尚未获得权限"));
        }
        String brandsStr = "";
        for (Brand brand : brandList) {
            brandsStr = brand.getCode() + ",";
        }
        String brandsParam = null;
        try {
            brandsParam = URLEncoder.encode(brandsStr.substring(0, brandsStr.length() - 1), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return (JSONObject) JSON.toJSON(new ErrorResponse("系统发生错误，请联系客服。"));
        }
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(mediaType);
        HttpEntity<JSONObject> entity = new HttpEntity<>(headers);
        JSONObject json = restTemplate.postForObject(
                httpProperties.getBossApiUrl() + "/api/crm/orderitem/countbyday" + "?brands=" + brandsParam, entity,
                JSONObject.class);
        if (json == null) {
            json = (JSONObject) JSON.toJSON(new ErrorResponse("boss系统连接异常"));
        }
        return json;
    }

}
