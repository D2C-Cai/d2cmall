package com.d2c.backend.rest.member;

import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.page.PageResult;
import com.d2c.common.api.response.ErrorResponse;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.exception.NotLoginException;
import com.d2c.common.base.utils.BeanUt;
import com.d2c.member.model.Admin;
import com.d2c.member.model.Magazine;
import com.d2c.member.model.MagazinePage;
import com.d2c.member.query.MagazinePageSearcher;
import com.d2c.member.query.MagazineSearcher;
import com.d2c.member.service.MagazineIssueService;
import com.d2c.member.service.MagazinePageService;
import com.d2c.member.service.MagazineService;
import com.d2c.util.date.DateUtil;
import com.d2c.util.file.ZipUtil;
import com.d2c.util.qrcode.QRcodeEntity;
import com.d2c.util.qrcode.QRcodeUtil;
import com.d2c.util.serial.JsonUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest/cms/magazine")
public class MagazineCtrl extends BaseCtrl<MagazineSearcher> {

    @Autowired
    private MagazineService magazineService;
    @Autowired
    private MagazineIssueService magazineIssueService;
    @Autowired
    private MagazinePageService magazinePageService;

    @Override
    protected Response doList(MagazineSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Magazine> pager = magazineService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected int count(MagazineSearcher searcher) {
        return magazineService.countBySearcher(searcher);
    }

    @Override
    protected String getExportFileType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected List<Map<String, Object>> getRow(MagazineSearcher searcher, PageModel page) {
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
    protected Response doHelp(MagazineSearcher searcher, PageModel page) {
        BeanUt.trimString(searcher);
        PageResult<Magazine> pager = magazineService.findBySearcher(searcher, page);
        return new SuccessResponse(pager);
    }

    @Override
    protected Response findById(Long id) {
        Magazine magazine = magazineService.findById(id);
        return new SuccessResponse(magazine);
    }

    @Override
    protected Response doUpdate(Long id, JSONObject data) {
        Magazine magazine = JsonUtil.instance().toObject(data, Magazine.class);
        int status = magazineService.update(magazine);
        SuccessResponse response = new SuccessResponse();
        response.setStatus(status);
        return response;
    }

    @Override
    protected Response doInsert(JSONObject data) {
        Magazine magazine = JsonUtil.instance().toObject(data, Magazine.class);
        magazine = magazineService.insert(magazine);
        return new SuccessResponse(magazine);
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
        magazineService.updateStatus(id, status, admin.getUsername());
        return new SuccessResponse();
    }

    /**
     * 批量发行N本杂志
     *
     * @param id
     * @param quantity
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/bath/create", method = RequestMethod.POST)
    public Response bathCreate(Long id, Integer quantity) {
        if (quantity > 1000) {
            return new ErrorResponse("一次性最多生成1000条！");
        }
        magazineIssueService.doCreate(id, quantity);
        return new SuccessResponse();
    }

    /**
     * 生成二维码
     *
     * @param ids
     * @return
     * @throws NotLoginException
     * @throws IOException
     */
    @RequestMapping(value = "/qrcode", method = RequestMethod.POST)
    public Response qrcode(Long[] ids) throws NotLoginException, IOException {
        SuccessResponse result = new SuccessResponse();
        String logName = this.getLoginedAdmin().getUsername() + "_杂志页二维码_";
        Date date = new Date();
        int year = DateUtil.getYearOfDate(date);
        int month = DateUtil.getMonthOfYear(date);
        int day = DateUtil.getDayOfMonth(date);
        String baseName = String.valueOf(date.getTime());
        String baseFolder = "/mnt/www/download/qrcode/" + year + "/" + month + "/" + day + "/" + baseName;
        for (Long id : ids) {
            Magazine magazine = magazineService.findById(id);
            String magazineName = magazine.getName() + "_" + magazine.getId();
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
            MagazinePageSearcher searcher = new MagazinePageSearcher();
            searcher.setMagazineId(magazine.getId());
            PageResult<MagazinePage> pager = magazinePageService.findBySearcher(searcher, new PageModel());
            for (MagazinePage item : pager.getList()) {
                qrcodeEntity.setContent("http://n.d2cmall.com/page?" + magazine.getId() + "-" + item.getId());
                qrcodeEntity.setLowerContent(magazine.getId() + "-" + item.getSort());
                ImageIO.write(QRcodeUtil.createQRCode(qrcodeEntity), "jpg", new File(dir.getAbsolutePath() + "/"
                        + magazine.getId() + "_" + item.getSort() + "_" + item.getName() + ".jpg"));
            }
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

}
