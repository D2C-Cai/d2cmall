package com.d2c.util.file;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FileTypeUtil {

    public final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {
        FILE_TYPE_MAP.put("mage/jpeg", "FFD8FF"); // JPEG (jpg)
        FILE_TYPE_MAP.put("image/x-png", "89504E47"); // PNG (png)
        FILE_TYPE_MAP.put("image/gif", "47494638"); // GIF (gif)
        FILE_TYPE_MAP.put("image/tiff", "49492A00"); // TIFF (tif)
        FILE_TYPE_MAP.put("image/x-ms-bmp", "424D"); // Windows Bitmap (bmp)
        FILE_TYPE_MAP.put("image/vnd.dwg", "41433130"); // CAD (dwg)
        FILE_TYPE_MAP.put("text/html", "68746D6C3E"); // HTML (html)
        FILE_TYPE_MAP.put("application/rtf", "7B5C727466"); // Rich Text Format
        // (rtf)
        FILE_TYPE_MAP.put("xml", "3C3F786D6C");
        FILE_TYPE_MAP.put("application/zip", "504B0304");
        FILE_TYPE_MAP.put("rar", "52617221");
        FILE_TYPE_MAP.put("psd", "38425053"); // Photoshop (psd)
        FILE_TYPE_MAP.put("eml", "44656C69766572792D646174653A"); // Email
        // [thorough
        // only]
        // (eml)
        FILE_TYPE_MAP.put("dbx", "CFAD12FEC5FD746F"); // Outlook Express (dbx)
        FILE_TYPE_MAP.put("pst", "2142444E"); // Outlook (pst)
        FILE_TYPE_MAP.put("xls", "D0CF11E0"); // MS Word
        FILE_TYPE_MAP.put("doc", "D0CF11E0"); // MS Excel 注意：word 和 excel的文件头一样
        FILE_TYPE_MAP.put("mdb", "5374616E64617264204A"); // MS Access (mdb)
        FILE_TYPE_MAP.put("wpd", "FF575043"); // WordPerfect (wpd)
        FILE_TYPE_MAP.put("eps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("ps", "252150532D41646F6265");
        FILE_TYPE_MAP.put("pdf", "255044462D312E"); // Adobe Acrobat (pdf)
        FILE_TYPE_MAP.put("qdf", "AC9EBD8F"); // Quicken (qdf)
        FILE_TYPE_MAP.put("pwl", "E3828596"); // Windows Password (pwl)
        FILE_TYPE_MAP.put("wav", "57415645"); // Wave (wav)
        FILE_TYPE_MAP.put("avi", "41564920");
        FILE_TYPE_MAP.put("ram", "2E7261FD"); // Real Audio (ram)
        FILE_TYPE_MAP.put("rm", "2E524D46"); // Real Media (rm)
        FILE_TYPE_MAP.put("video/mpeg", "000001BA"); //
        FILE_TYPE_MAP.put("video/quicktime", "6D6F6F76"); // Quicktime (mov)
        FILE_TYPE_MAP.put("asf", "3026B2758E66CF11"); // Windows Media (asf)
        FILE_TYPE_MAP.put("x-music/x-midi", "4D546864"); // MIDI (mid)
    }

    public final static String getImageFileType(File f) {
        if (isImage(f)) {
            try {
                ImageInputStream iis = ImageIO.createImageInputStream(f);
                Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                if (!iter.hasNext()) {
                    return null;
                }
                ImageReader reader = iter.next();
                iis.close();
                return reader.getFormatName();
            } catch (IOException e) {
                return null;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Created on 2010-7-2
     * <p>
     * Discription:[isImage,判断文件是否为图片]
     * </p>
     *
     * @param file
     * @return true 是 | false 否
     * @author:[shixing_11@sina.com]
     */
    public static final boolean isImage(File file) {
        boolean flag = false;
        try {
            BufferedImage bufreader = ImageIO.read(file);
            int width = bufreader.getWidth();
            int height = bufreader.getHeight();
            if (width == 0 || height == 0) {
                flag = false;
            } else {
                flag = true;
            }
        } catch (IOException e) {
            flag = false;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Created on 2010-7-1
     * <p>
     * Discription:[getFileHexString]
     * </p>
     *
     * @param b
     * @return fileTypeHex
     * @author:[shixing_11@sina.com]
     */
    public final static String getFileHexString(byte[] b) {
        StringBuilder stringBuilder = new StringBuilder();
        if (b == null || b.length <= 0) {
            return null;
        }
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Created on 2010-7-1
     * <p>
     * Discription:[getFileTypeByStream]
     * </p>
     *
     * @param b
     * @return fileType
     * @author:[shixing_11@sina.com]
     */
    public final static String getFileTypeByStream(byte[] b) {
        String filetypeHex = String.valueOf(getFileHexString(b));
        Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
        while (entryiterator.hasNext()) {
            Entry<String, String> entry = entryiterator.next();
            String fileTypeHexValue = entry.getValue();
            if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param filePath
     * @return
     */
    public static byte[] getFileBytesByPath(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
