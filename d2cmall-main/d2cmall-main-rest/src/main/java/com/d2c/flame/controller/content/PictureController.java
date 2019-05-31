package com.d2c.flame.controller.content;

import com.alibaba.dubbo.config.annotation.Reference;
import com.d2c.common.api.response.ResponseResult;
import com.d2c.common.base.exception.BusinessException;
import com.d2c.common.base.utils.security.MD5Util;
import com.d2c.flame.controller.base.BaseController;
import com.d2c.logger.model.Signature;
import com.d2c.logger.service.SignatureService;
import com.d2c.member.model.Partner;
import com.d2c.member.service.PartnerService;
import com.d2c.product.search.model.SearcherProduct;
import com.d2c.product.search.service.ProductSearcherQueryService;
import com.d2c.product.third.upyun.core.Author;
import com.d2c.util.date.DateUtil;
import com.d2c.util.qrcode.QRcodeEntity;
import com.d2c.util.qrcode.QRcodeUtil;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * 图片
 *
 * @author Lain
 * @version 3.0
 */
@RestController
@RequestMapping(value = "/v3/api")
public class PictureController extends BaseController {

    private static RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private SignatureService signatureService;
    @Autowired
    private PartnerService partnerService;
    @Reference
    private ProductSearcherQueryService productSearcherQueryService;

    /**
     * upyun签名
     *
     * @param data
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/upyun/signature", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseResult signature(String method, String uri, String date, String policy) throws Exception {
        ResponseResult result = new ResponseResult();
        String signature = Author.sign("d2c", MD5Util.encodeMD5Hex("d2c123456"), method, uri, date, policy, "");
        result.put("signature", signature);
        return result;
    }

    /**
     * 生成 type=0:条形码，type=1:二维码
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
    public void code(Integer type, String code, Integer width, Integer height, String lowerContent, Boolean noLogo,
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
     * @param appId
     * @param width
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/weixin/code", method = RequestMethod.GET)
    public void wxcode(String content, String page, String appId, Integer width, HttpServletResponse response)
            throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            BufferedImage qrCode = this.wxQrCode(appId, width, content, page);
            if (qrCode != null) {
                ImageIO.write(qrCode, "PNG", outputStream);
            }
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 商品分享海报
     *
     * @param appId     appID
     * @param content   具体内容
     * @param page      小程序页面
     * @param productId 商品ID
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/product/poster", method = {RequestMethod.GET, RequestMethod.POST})
    public void productPoster(String appId, String content, String page, Long productId, HttpServletResponse response)
            throws Exception {
        SearcherProduct product = productSearcherQueryService.findById(String.valueOf(productId));
        if (product == null) {
            throw new BusinessException("商品不存在！");
        }
        int width = 500;
        int height = 750;
        // 商品底图
        BufferedImage background = this.upyunPic(product.getProductImageCover(), width, height);
        // 小程序码
        BufferedImage qrCode = this.wxQrCode(appId, width, content, page);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            // 制作海报
            BufferedImage poster = this.drawPoster(background, qrCode, product);
            ImageIO.write(poster, "PNG", outputStream);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 分销分享证书
     *
     * @param appId     appID
     * @param content   具体内容
     * @param page      小程序页面
     * @param partnerId 分销ID
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/partner/poster", method = {RequestMethod.GET, RequestMethod.POST})
    public void partnerPoster(String appId, String content, String page, Long partnerId, HttpServletResponse response)
            throws Exception {
        Partner partner = partnerService.findById(partnerId);
        if (partner == null) {
            throw new BusinessException("分销商不存在！");
        }
        int width = 750;
        int height = 1050;
        // 证书底图
        BufferedImage background = this.upyunPic("http://static.d2c.cn/img/other/authorize.jpg", width, height);
        // 小程序码
        BufferedImage qrCode = this.wxQrCode(appId, width, content, page);
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            // 制作海报
            BufferedImage poster = this.drawPoster(background, qrCode, partner);
            ImageIO.write(poster, "PNG", outputStream);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private BufferedImage wxQrCode(String appId, Integer width, String content, String page) throws IOException {
        InputStream inputStream = null;
        try {
            Signature signature = signatureService.findByAppid(appId);
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + signature.getToken();
            Map<String, Object> param = new HashMap<>();
            param.put("scene", content);
            param.put("page", page);
            param.put("width", width == null ? 430 : width);
            param.put("auto_color", true);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity<?> requestEntity = new HttpEntity<>(param, headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class,
                    new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            BufferedImage qrCode = ImageIO.read(inputStream);
            return qrCode;
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

    private BufferedImage upyunPic(String imgUrl, Integer width, Integer height) throws IOException {
        InputStream inputStream = null;
        try {
            if (!imgUrl.contains("http")) {
                imgUrl = "http://img.d2c.cn" + imgUrl;
            }
            imgUrl = imgUrl + "!/canvas/" + width + "x" + height + "/fw/" + width + "/force/true/cvscolor/FFFFFFFF";
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity<?> requestEntity = new HttpEntity<>(headers);
            ResponseEntity<byte[]> entity = restTemplate.exchange(imgUrl, HttpMethod.GET, requestEntity, byte[].class,
                    new Object[0]);
            byte[] result = entity.getBody();
            inputStream = new ByteArrayInputStream(result);
            BufferedImage qrCode = ImageIO.read(inputStream);
            return qrCode;
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

    private BufferedImage drawPoster(BufferedImage background, BufferedImage qrCode, SearcherProduct product) {
        Graphics graphics = background.getGraphics();
        // 二维码
        int qrWidth = background.getWidth() / 5;
        int qrHeight = qrWidth;
        int margin = background.getWidth() / 100;
        graphics.drawImage(qrCode, background.getWidth() - qrWidth - margin, background.getHeight() - qrHeight - margin,
                qrWidth, qrHeight, null);
        // 商品标题
        String title = product.getName();
        int fontSize = qrWidth / 5;
        int shadowSize = 1;
        graphics.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, fontSize));
        int cursor_x = 3 * margin;
        int cursor_y = background.getHeight() - qrHeight - margin + fontSize;
        int shadow_x = cursor_x + shadowSize;
        int shadow_y = cursor_y + shadowSize;
        // 文本框最大宽度
        int MAX_TEXT_WIDTH = background.getWidth() - qrWidth - (3 + 1 + 1) * margin;
        // 文本框最大行数
        int MAX_LINE = 2;
        int width = 0; // 当前行累计宽度
        int line = 1; // 当前行号
        int init_num = 0; // 字符串截取起点
        int text_num = 0; // 字符串截取个数
        StringCharacterIterator iterator = new StringCharacterIterator(title);
        while (line <= MAX_LINE) {
            while (width <= MAX_TEXT_WIDTH) {
                char c = iterator.next();
                if (c == StringCharacterIterator.DONE) {
                    break;
                }
                if (c >= 0x4E00 && c <= 0x9FA5 || c == '\b') {
                    width += fontSize;
                } else {
                    width += (fontSize / 1.5);
                }
                text_num++;
            }
            shadow_y = shadow_y + (line - 1) * (fontSize + margin);
            cursor_y = cursor_y + (line - 1) * (fontSize + margin);
            // 先绘制阴影
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawString(title.substring(init_num, init_num + text_num), shadow_x, shadow_y);
            // 再绘制文本
            graphics.setColor(new Color(255, 255, 255));
            graphics.drawString(title.substring(init_num, init_num + text_num), cursor_x, cursor_y);
            // 文本换行
            line++;
            width = 0;
            init_num = text_num;
            text_num = 0;
            iterator.previous();
        }
        // 商品销售价
        Currency currency = Currency.getInstance(Locale.CHINA);
        String currentPrice = currency.getSymbol() + String.valueOf(product.getCurrentPrice());
        String originalPrice = currency.getSymbol() + String.valueOf(product.getOriginalPrice());
        fontSize = qrWidth / 3;
        graphics.setFont(new Font("微软雅黑", Font.PLAIN, fontSize));
        cursor_x = 3 * margin;
        cursor_y = cursor_y + fontSize + 2 * margin;
        shadow_x = cursor_x + shadowSize;
        shadow_y = cursor_y + shadowSize;
        // 先绘制阴影
        graphics.setColor(new Color(0, 0, 0));
        graphics.drawString(currentPrice, shadow_x, shadow_y);
        // 再绘制文本
        graphics.setColor(new Color(255, 255, 255));
        graphics.drawString(currentPrice, cursor_x, cursor_y);
        // 商品吊牌价
        if (product.getCurrentPrice().compareTo(product.getOriginalPrice()) < 0) {
            cursor_x = cursor_x + 5 * fontSize;
            shadow_x = cursor_x + shadowSize;
            fontSize = qrWidth / 5;
            HashMap<TextAttribute, Object> hm = new HashMap<TextAttribute, Object>();
            hm.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            hm.put(TextAttribute.SIZE, fontSize);
            hm.put(TextAttribute.FAMILY, "微软雅黑");
            graphics.setFont(new Font(hm));
            // 先绘制阴影
            graphics.setColor(new Color(0, 0, 0));
            graphics.drawString(originalPrice, shadow_x, shadow_y);
            // 再绘制文本
            graphics.setColor(new Color(255, 255, 255));
            graphics.drawString(originalPrice, cursor_x, cursor_y);
        }
        graphics.finalize();
        return background;
    }

    private BufferedImage drawPoster(BufferedImage background, BufferedImage qrCode, Partner partner) {
        Graphics graphics = background.getGraphics();
        // 二维码
        int qrWidth = background.getWidth() / 6;
        int qrHeight = qrWidth;
        graphics.drawImage(qrCode, background.getWidth() * 5 / 12 + 1, background.getWidth() * 19 / 36, qrWidth,
                qrHeight, null);
        // 昵称
        int fontSize = qrWidth / 8;
        graphics.setFont(new Font("宋体", Font.BOLD, fontSize));
        graphics.setColor(new Color(0, 0, 0));
        String name = partner.getName();
        if (name != null && name.length() > 5) {
            name = name.substring(0, 5);
        }
        graphics.drawString(partner.getName(), background.getWidth() * 19 / 72,
                background.getWidth() * 54 / 72 + fontSize);
        // 等级
        String lvStr = "买手";
        if (partner.getLevel() == 1) {
            lvStr = "DM";
        } else if (partner.getLevel() == 0) {
            lvStr = "AM";
        }
        graphics.drawString(lvStr, background.getWidth() * 36 / 72, background.getWidth() * 58 / 72 + fontSize - 2);
        // 日期
        Date now = partner.getCreateDate();
        String year = String.valueOf(DateUtil.getYearOfDate(now));
        int MM = DateUtil.getMonthOfYear(now);
        String month = (MM < 10 ? "0" : "") + MM;
        int dd = DateUtil.getDayOfMonth(now);
        String day = (dd < 10 ? "0" : "") + dd;
        graphics.drawString(year, background.getWidth() * 21 / 72, background.getWidth() * 72 / 72 + fontSize + 1);
        graphics.drawString(month, background.getWidth() * 28 / 72, background.getWidth() * 72 / 72 + fontSize + 1);
        graphics.drawString(day, background.getWidth() * 33 / 72, background.getWidth() * 72 / 72 + fontSize + 1);
        return background;
    }

}
