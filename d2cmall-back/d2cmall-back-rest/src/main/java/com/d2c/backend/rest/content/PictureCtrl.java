package com.d2c.backend.rest.content;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d2c.backend.rest.base.BaseCtrl;
import com.d2c.common.api.page.PageModel;
import com.d2c.common.api.query.model.BaseQuery;
import com.d2c.common.api.response.Response;
import com.d2c.common.api.response.SuccessResponse;
import com.d2c.common.base.thread.MyExecutors;
import com.d2c.common.base.utils.security.MD5Ut;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.member.model.Admin;
import com.d2c.product.model.Product;
import com.d2c.product.service.ProductService;
import com.d2c.product.third.upyun.PictureModel;
import com.d2c.product.third.upyun.core.Picture;
import com.d2c.product.third.upyun.core.UpYun;
import com.d2c.product.third.upyun.core.UpYun.FolderItem;
import com.d2c.util.file.ExcelUtil;
import com.d2c.util.string.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/rest/sys")
public class PictureCtrl extends BaseCtrl<BaseQuery> {

    @Autowired
    protected ProductService productService;

    public static void main(String[] args) throws Exception {
        /**
         * 1.按商品款号，excel中生成图片
         */
        // excelPic();
        /**
         * 2.按商品款号，一个款号一张图片
         */
        // diskPic1();
        /**
         * 3.按商品款号，一个款号一个文件夹一包图片
         */
        // diskPic2();
    }

    /**
     * 按商品款号，excel中生成图片
     */
    public static void excelPic() throws Exception {
        ExcelUtil excelUtil = new ExcelUtil("商品图片匹配", "管理员");
        PageModel page = new PageModel(1, 500);
        while (true) {
            InputStream fis = new FileInputStream("D:/z_temp_all.xls");
            List<Map<String, Object>> excelData = excelUtil.getExcelData(fis, page);
            if (excelData == null || excelData.size() <= 0) {
                break;
            }
            List<Map<String, Object>> newData = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> map : excelData) {
                String productSn = String.valueOf(map.get("商品代码"));
                Object picObject = map.get("图片");
                Map<String, Object> item = new HashMap<>();
                item.put("商品代码", productSn);
                System.out.println(productSn);
                if (picObject != null) {
                    try {
                        item.put("图片", ImageIO
                                .read(new URL("http://img.d2c.cn/" + String.valueOf(picObject).split(",")[0] + "!80")));
                    } catch (Exception e) {
                        item.put("图片", ImageIO.read(
                                new URL("http://img.d2c.cn/2018/04/26/06270183dbb852e2bb461457fd98f0e079fcd5.jpg!80")));
                    }
                } else {
                    item.put("图片", ImageIO.read(
                            new URL("http://img.d2c.cn/2018/04/26/06270183dbb852e2bb461457fd98f0e079fcd5.jpg!80")));
                }
                newData.add(item);
            }
            excelUtil.createExcel(new String[]{"商品代码", "图片"}, newData);
            page.next();
        }
    }

    /**
     * 按商品款号，一个款号一张图片
     */
    public static void diskPic1() throws Exception {
        InputStream fis = new FileInputStream("D:/z_temp_pic.xls");
        ExcelUtil excelUtil = new ExcelUtil("商品图片匹配", "管理员");
        List<Map<String, Object>> excelData = excelUtil.getExcelData(fis);
        String baseFolder = "D:/pic_直播";
        ExecutorService limitThreadPool = MyExecutors.newLimit(10);
        for (Map<String, Object> map : excelData) {
            limitThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String productSn = String.valueOf(map.get("商品款号"));
                        String folderName = baseFolder;
                        File dir = new File(folderName);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        if (map.get("商品图片") == null)
                            return;
                        String productPics = String.valueOf(map.get("商品图片"));
                        String[] picArray = productPics.split(",");
                        try {
                            BufferedImage img = ImageIO.read(new URL("http://img.d2c.cn" + picArray[0]));
                            ImageIO.write(img, "jpg", new File(folderName + "/" + productSn + ".jpg"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 按商品款号，一个款号一个文件夹一包图片
     */
    public static void diskPic2() throws Exception {
        InputStream fis = new FileInputStream("D:/z_temp_pic.xls");
        ExcelUtil excelUtil = new ExcelUtil("商品图片匹配", "管理员");
        List<Map<String, Object>> excelData = excelUtil.getExcelData(fis);
        String baseFolder = "D:/pic_直播";
        ExecutorService limitThreadPool = MyExecutors.newLimit(10);
        for (Map<String, Object> map : excelData) {
            limitThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String productSn = String.valueOf(map.get("商品款号"));
                        String folderName = baseFolder + "/" + productSn;
                        File dir = new File(folderName);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        if (map.get("商品图片") == null)
                            return;
                        int picIndex = 0;
                        String productPics = String.valueOf(map.get("商品图片"));
                        String[] picArray = productPics.split(",");
                        for (int i = 0; i < picArray.length; i++) {
                            try {
                                BufferedImage img = ImageIO.read(new URL("http://img.d2c.cn" + picArray[i]));
                                ImageIO.write(img, "jpg",
                                        new File(folderName + "/" + productSn + "(" + (picIndex + 1) + ").jpg"));
                                picIndex++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        String detailPics = String.valueOf(map.get("商品详情"));
                        List<String> picList = StringUtil.getHTMLImg(detailPics);
                        for (int i = 0; i < picList.size(); i++) {
                            try {
                                BufferedImage img = ImageIO.read(new URL(picList.get(i).replace("https", "http")));
                                ImageIO.write(img, "jpg",
                                        new File(folderName + "/" + productSn + "(" + (picIndex + 1) + ").jpg"));
                                picIndex++;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 获取图片路径，生成json文件
     *
     * @throws IOException
     */
    @RequestMapping(value = "/picture/json", method = RequestMethod.POST)
    public Response json() {
        SuccessResponse result = new SuccessResponse();
        try {
            this.writeFile("campains.json", "/ipad/pic/campains");
            this.writeFile("personal-style.json", "/ipad/pic/personal-style");
            this.writeFile("star-style.json", "/ipad/pic/star-style");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        Map<String, Object> map = new TreeMap<String, Object>();
        map.put("/product/list", "返回");
        result.setDatas(map);
        result.setMessage("文件创建成功！");
        return result;
    }

    private void writeFile(String fileName, String upYunPath) throws IOException {
        UpYun upyun = new UpYun("d2c-mobile");
        JSONArray jsonArray = new JSONArray();
        this.createString(upYunPath, jsonArray, upyun);
        upyun.writeFile("/ipad/" + fileName, jsonArray.toString());
    }

    private void createString(String upYunPath, JSONArray jsonArray, UpYun upyun) {
        List<FolderItem> list = upyun.readDir(upYunPath);
        if (list == null) {
            return;
        }
        for (FolderItem item : list) {
            if (item == null) {
                continue;
            }
            if ("Folder".equals(item.type)) {
                String subUpYunPath = upYunPath + "/" + item.name;
                this.createString(subUpYunPath, jsonArray, upyun);
            } else {
                jsonArray.add(upYunPath + "/" + item.name);
            }
        }
    }

    /**
     * 详情页图片上传
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/picture/custom", method = RequestMethod.POST)
    public Response upload(@RequestParam(value = "file", required = false) MultipartFile[] files,
                           @RequestParam(value = "filedata", required = false) MultipartFile[] filedata) throws Exception {
        SuccessResponse result = new SuccessResponse();
        Map<String, Object> uploadResult = this.storeCustomPictureAndVideo(files, PictureModel.class);
        List<Picture> pictures = (List<Picture>) uploadResult.get("pictures");
        if (pictures.size() > 0)
            result.put("pictures", pictures);
        return result;
    }

    private <T extends Picture> Map<String, Object> storeCustomPictureAndVideo(MultipartFile[] files,
                                                                               Class<T> pictureClass) throws Exception {
        List<Picture> pictures = new ArrayList<Picture>();
        Map<String, Object> result = new HashMap<String, Object>();
        for (MultipartFile file : files) {
            File dir = null;
            Picture picture = pictureClass.newInstance();
            dir = new File(Picture.PICTURE_LOCAL_STOARGE + "/" + picture.getSubdirectory() + "/"
                    + picture.getDisplayYearAndMonth());
            if (!dir.exists())
                dir.mkdirs();
            String originalFileName = new Date().getTime() + "_" + file.getOriginalFilename();
            File newFile = new File(dir.getAbsolutePath() + "/" + picture.getRandomFileName(originalFileName));
            // 如果已经存在,先删除
            if (newFile.exists())
                newFile.delete();
            file.transferTo(newFile);
            String md5Name = MD5Ut.getFileMD5String(newFile);
            File destFile = new File(newFile.getParent() + "/" + md5Name);
            if (!destFile.exists()) {
                newFile.renameTo(destFile);
            }
            picture.initImageFile(destFile);
            picture.setFileName(originalFileName);
            picture.setImageFile(destFile);
            picture.setPath("/" + picture.getSubdirectory() + "/" + picture.getDisplayYearAndMonth() + "/" + md5Name);
            picture.resize();
            picture.upload2CloudStore();
            pictures.add(picture);
        }
        result.put("pictures", pictures);
        return result;
    }

    /**
     * 图片上传
     *
     * @throws Exception
     */
    @RequestMapping(value = "/picture/upload/{policy}", method = RequestMethod.GET)
    public Response pictureUpload(@PathVariable String policy) throws Exception {
        SuccessResponse result = new SuccessResponse();
        String sign = policy + "&" + UpYun.FORM_API_SECRET_D2C_PIC;
        result.put("sign", MD5Util.encodeMD5Hex(sign));
        return result;
    }

    /**
     * 视频上传
     *
     * @throws Exception
     */
    @RequestMapping(value = "/vedio/upload/{policy}", method = RequestMethod.GET)
    public Response vedioUpload(@PathVariable String policy) throws Exception {
        SuccessResponse result = new SuccessResponse();
        String sign = policy + "&" + UpYun.FORM_API_SECRET_D2C_VEDIO;
        result.put("sign", MD5Util.encodeMD5Hex(sign));
        return result;
    }

    /**
     * 商品从网上拉图片写入excel
     *
     * @param path
     * @throws Exception
     */
    @RequestMapping(value = "/picture/excel", method = RequestMethod.POST)
    public Response excelPic(HttpServletRequest request) throws Exception {
        Admin admin = this.getLoginedAdmin();
        // InputStream fis = new FileInputStream("D:/123.xls");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartRequest.getFile("file");
        ExcelUtil excelUtil = new ExcelUtil("商品图_" + admin.getUsername(), admin.getUsername());
        PageModel page = new PageModel(1, 500);
        while (true) {
            InputStream fis = commonsMultipartFile.getFileItem().getInputStream();
            List<Map<String, Object>> excelData = excelUtil.getExcelData(fis, page);
            if (excelData == null || excelData.size() <= 0) {
                break;
            }
            List<Map<String, Object>> newData = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> map : excelData) {
                String productSn = String.valueOf(map.get("款号"));
                Product product = productService.findByProductSn(productSn);
                Map<String, Object> item = new HashMap<>();
                item.put("款号", productSn);
                if (product != null) {
                    try {
                        item.put("图片",
                                ImageIO.read(new URL("http://img.d2c.cn/" + product.getProductImageCover() + "!80")));
                    } catch (Exception e) {
                        item.put("图片", ImageIO.read(
                                new URL("http://img.d2c.cn/2018/04/26/06270183dbb852e2bb461457fd98f0e079fcd5.jpg!80")));
                    }
                } else {
                    item.put("图片", ImageIO.read(
                            new URL("http://img.d2c.cn/2018/04/26/06270183dbb852e2bb461457fd98f0e079fcd5.jpg!80")));
                }
                newData.add(item);
            }
            createExcel(excelUtil, new String[]{"款号", "图片"}, newData);
            page.next();
        }
        this.saveLog(excelUtil.getExportFileBean().getFileName(), excelUtil.getExportFileBean().getDownloadPath(),
                excelUtil.getExportFileBean().getFileSize(), "productPic");
        return new SuccessResponse("图片生成中...请前往下载中心下载！");
    }

    @Override
    protected List<Map<String, Object>> getRow(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected int count(BaseQuery searcher) {
        // TODO Auto-generated method stub
        return 0;
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
    protected Response doList(BaseQuery searcher, PageModel page) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doBatchDelete(Long[] ids) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Response doDelete(Long id) {
        // TODO Auto-generated method stub
        return null;
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
        return null;
    }

}
