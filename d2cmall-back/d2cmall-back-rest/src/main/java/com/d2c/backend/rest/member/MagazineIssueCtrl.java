package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.model.Admin;
import com.d2c.member.model.MagazineIssue;
import com.d2c.member.model.Partner;
import com.d2c.member.query.MagazineIssueSearcher;
import com.d2c.member.service.MagazineIssueService;
import com.d2c.member.service.PartnerService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.ZipUtil;
import com.d2c.util.qrcode.QRcodeEntity;
import com.d2c.util.qrcode.QRcodeUtil;
import com.d2c.util.serial.JsonUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/rest/cms/magazineissue")
public class MagazineIssueCtrl extends BaseCtrl<MagazineIssueSearcher> {

    private final static String appId = "wx58eb0484ce91f38f";
    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private MagazineIssueService magazineIssueService;
    @Autowired
    private SignatureService signatureService;
    @Autowired
    private PartnerService partnerService;

    @Override
    protected Response doList(MagazineIssueSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MagazineIssue> pager = magazineIssueService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(MagazineIssueSearcher searcher) {
        return magazineIssueService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        return "MagazineIssue";
    }

    @Override
    protected List<Map<String, Object>> getRow(MagazineIssueSearcher searcher, PageModel page) {
        PageResult<MagazineIssue> pager = magazineIssueService.findBySearcher(searcher, page);
        List<Map<String, Object>> rowList = new ArrayList<>();
        Map<String, Object> cellsMap = null;
        for (MagazineIssue magazineIssue : pager.getList()) {
            cellsMap = new HashMap<>();
            cellsMap.put("杂志名称", magazineIssue.getMagazineName());
            cellsMap.put("杂志编码", magazineIssue.getCode());
            cellsMap.put("间接分销账号", magazineIssue.getPartnerCode());
            cellsMap.put("直接分销账号", magazineIssue.getPartnerTraderCode());
            rowList.add(cellsMap);
        }
        return rowList;
    }

    @Override
    protected String getFileName() {
        return "杂志编码表";
    }

    @Override
    protected String[] getExportTitles() {
        return new String[]{"杂志名称", "杂志编码", "间接分销账号", "直接分销账号"};
    }

    @Override
    protected Response doHelp(MagazineIssueSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<MagazineIssue> pager = magazineIssueService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        MagazineIssue magazineIssue = magazineIssueService.findById(id);
        return new SuccessResponse(magazineIssue);
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        MagazineIssue magazineIssue = JsonUtil.instance().toObject(data, MagazineIssue.class);
        int update = magazineIssueService.update(magazineIssue);
        SuccessResponse response = new SuccessResponse();
        response.setStatus(update);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        MagazineIssue magazineIssue = JsonUtil.instance().toObject(data, MagazineIssue.class);
        magazineIssue = magazineIssueService.insert(magazineIssue);
        return new SuccessResponse(magazineIssue);
    }

    @Override
    protected Response doDelete(Long id) {
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        return null;
    }

    @RequestMapping(value = "/mark/{status}", method = RequestMethod.POST)
    public Response mark(@PathVariable Integer status, Long id) throws NotLoginException {
        Admin admin = this.getLoginedAdmin();
        magazineIssueService.updateStatus(id, status, admin.getUsername());
        return new SuccessResponse();
    }

    /**
     * 生成二维码
     *
     * @param id
     * @return
     * @throws NotLoginException
     * @throws IOException
     */
    @RequestMapping(value = "/qrcode", method = RequestMethod.POST)
    public Response qrcode(Long[] ids) throws NotLoginException, IOException {
        SuccessResponse result = new SuccessResponse();
        String logName = this.getLoginedAdmin().getUsername() + "_杂志二维码_";
        Date date = new Date();
        int year = DateUtil.getYearOfDate(date);
        int month = DateUtil.getMonthOfYear(date);
        int day = DateUtil.getDayOfMonth(date);
        String baseName = String.valueOf(date.getTime());
        String baseFolder = "/mnt/www/download/qrcode/" + year + "/" + month + "/" + day + "/" + baseName;
        for (Long id : ids) {
            MagazineIssue magazineIssue = magazineIssueService.findById(id);
            String magazineName = magazineIssue.getMagazineName() + "_" + magazineIssue.getCode();
            String folderName = baseFolder + "/" + magazineName;
            File dir = new File(folderName);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            QRcodeEntity qrcodeEntity = new QRcodeEntity();
            qrcodeEntity.setCharacterSet("UTF-8");
            qrcodeEntity.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
            qrcodeEntity.setMargin(4);
            qrcodeEntity.setWidth(468);
            qrcodeEntity.setHeight(468);
            qrcodeEntity.setContent("http://n.d2cmall.com/mag?" + magazineIssue.getCode());
            qrcodeEntity.setLowerContent(magazineIssue.getCode());
            ImageIO.write(QRcodeUtil.createQRCode(qrcodeEntity), "jpg",
                    new File(dir.getAbsolutePath() + "/" + magazineIssue.getCode() + ".jpg"));
        }
        String zipName = baseFolder + ".zip";
        ZipUtil zc = new ZipUtil(zipName);
        zc.compress(baseFolder);
        String fileName = baseFolder.substring(8, baseFolder.length()) + ".zip";
        long size = 0;
        File f = new File(zipName);
        if (f.exists() && f.isFile()) {
            size = f.length();
        }
        this.saveLog(logName + baseName + ".zip", fileName, size, "qrcode_zip");
        return result;
    }

    public void getQrCode(String content, String filePath) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Signature signature = signatureService.findByAppid(appId);
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + signature.getToken();
            Map<String, Object> param = new HashMap<>();
            param.put("scene", content);
            param.put("width", 430);
            param.put("auto_color", true);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class,
                    new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 绑定分销商
     *
     * @param partnerId
     * @param id
     * @return
     */
    @RequestMapping(value = "/bind/partner", method = RequestMethod.POST)
    public Response bindPartner(Long partnerId, Long id) {
        SuccessResponse result = new SuccessResponse();
        Partner partner = partnerService.findById(partnerId);
        magazineIssueService.doBindPartner(id, partnerId, partner.getLoginCode());
        return result;
    }

    /**
     * 导入分销表
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excel/import", method = RequestMethod.POST)
    public Response importPartner(HttpServletRequest request) {
        this.getLoginedAdmin();
        return this.processImportExcel(request, new EachRow() {
            @Override
            public boolean process(Map<String, Object> map, Integer row, StringBuilder errorMsg) {
                String loginCode = String.valueOf(map.get("分销商账号"));
                String magazineCode = String.valueOf(map.get("杂志编号"));
                if (StringUtils.isBlank(loginCode)) {
                    errorMsg.append("第" + row + "行，分销商账号：" + loginCode + "，错误原因：分销商账号 不能为空<br/>");
                    return false;
                }
                if (StringUtils.isBlank(magazineCode)) {
                    errorMsg.append("第" + row + "行，杂志编号：" + magazineCode + "，错误原因：杂志编号 不能为空<br/>");
                    return false;
                }
                Partner partner = partnerService.findByLoginCode(loginCode);
                MagazineIssue magazineIssue = magazineIssueService.findByCode(magazineCode);
                magazineIssueService.doBindPartner(magazineIssue.getId(), partner.getId(), loginCode);
                return true;
            }
        });
    }

}