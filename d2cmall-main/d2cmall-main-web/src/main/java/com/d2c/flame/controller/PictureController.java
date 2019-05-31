package com.d2c.flame.controller;

import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.model.MemberInfo;
import com.d2c.product.third.upyun.PictureModel;
import com.d2c.product.third.upyun.core.UpYun;
import com.d2c.util.qrcode.QRcodeEntity;
import com.d2c.util.qrcode.QRcodeUtil;
import com.d2c.util.qrcode.RandomCodeUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class PictureController extends BaseController {

    private final static String appId = "wx6807f3a22c99318f";
    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private SignatureService signatureService;

    /**
     * 生成二维码
     *
     * @param type
     * @param code
     * @param width
     * @param height
     * @param lowerContent
     * @param noLogo
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/picture/code", method = RequestMethod.GET)
    public void qrcode(Integer type, String code, Integer width, Integer height, String lowerContent, Boolean noLogo,
                       HttpServletResponse response) {
        noLogo = noLogo == null ? false : noLogo;
        if (StringUtils.isBlank(code)) {
            return;
        }
        BufferedImage img = null;
        type = type == null ? 0 : type;
        switch (type) {
            case 0:
                img = QRcodeUtil.createBarcode(code.toUpperCase(), width, height);
                break;
            case 1:
                QRcodeEntity zxing = new QRcodeEntity();
                zxing.setCharacterSet("UTF-8");
                zxing.setErrorCorrectionLevel(ErrorCorrectionLevel.H);
                zxing.setFormat("png");
                zxing.setMargin(0);
                zxing.setWidth(width);
                zxing.setHeight(height);
                zxing.setContent(code);
                zxing.setLowerContent(lowerContent);
                if (!noLogo) {
                    zxing.setLogoPath(getSession().getServletContext().getRealPath("/") + "/static/logo.png");
                }
                img = QRcodeUtil.createQRCode(zxing);
                break;
        }
        try {
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(img, "PNG", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 微信二维码
     *
     * @param content
     * @param page
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/weixin/code", method = RequestMethod.POST)
    public void wxcode(String content, String page, HttpServletResponse response) {
        InputStream inputStream = null;
        Signature signature = signatureService.findByAppid(appId);
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + signature.getToken();
            Map<String, Object> param = new HashMap<>();
            param.put("scene", content);
            param.put("page", page);
            param.put("width", 430);
            param.put("auto_color", true);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class,
                    new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            BufferedImage img = ImageIO.read(inputStream);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(img, "PNG", out);
            out.flush();
            out.close();
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
        }
    }

    /**
     * 随机验证码
     *
     * @param width
     * @param height
     * @param stringNum
     * @param lineSize
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/randomcode/getcode", method = RequestMethod.GET)
    public void random(Integer width, Integer height, Integer stringNum, Integer lineSize, HttpServletResponse response,
                       String mobile) {
        RandomCodeUtil randomCodeUtil = new RandomCodeUtil();
        if (width == null) {
            width = randomCodeUtil.getWidth();
        }
        if (height == null) {
            height = randomCodeUtil.getHeight();
        }
        if (lineSize == null) {
            lineSize = randomCodeUtil.getLineSize();
        }
        if (stringNum == null) {
            stringNum = randomCodeUtil.getStringNum();
        }
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 23));
        g.setColor(randomCodeUtil.getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            randomCodeUtil.drowLine(g);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= stringNum; i++) {
            randomString = randomCodeUtil.drowString(g, randomString, i);
        }
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 上传图片
     *
     * @param model
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/picture/upload", method = RequestMethod.POST)
    public String upload(ModelMap model, MultipartFile file) throws Exception {
        MemberInfo memberInfo = this.getLoginMemberInfo();
        File dir = new File(
                PictureModel.PICTURE_LOCAL_STOARGE + "/member/" + memberInfo.getId() % 100 + "/" + memberInfo.getId());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (file.getSize() != 0) {
            String shareName = (int) (Math.random() * 1000000000)
                    + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
            try {
                file.transferTo(new File(dir.getAbsolutePath() + "/" + shareName));
            } catch (IllegalStateException | IOException e) {
                logger.error(e.getMessage());
            }
            String fileName = "member/" + memberInfo.getId() % 100 + "/" + memberInfo.getId() + "/" + shareName;
            File target = new File(dir.getPath() + "/" + shareName);
            UpYun upyun = new UpYun("d2c-pic", "d2cstatic", "d2c123456");
            upyun.writeFile(fileName, target, true);
            model.put("status", 1);
            model.put("fileName", fileName);
        }
        return "fragment/upload_success";
    }

}
