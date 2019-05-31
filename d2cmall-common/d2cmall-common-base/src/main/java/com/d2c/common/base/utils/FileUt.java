package com.d2c.common.base.utils;

import com.d2c.common.base.exception.FileException;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.commons.compress.archivers.arj.ArjArchiveEntry;
import org.apache.commons.compress.archivers.cpio.CpioArchiveEntry;
import org.apache.commons.compress.archivers.jar.JarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 文件工具类
 *
 * @author wull
 */
public class FileUt {

    //	private static final Logger logger = LoggerFactory.getLogger(FileUt.class);
    private static final Charset charset = Charsets.toCharset("UTF-8");

    /**
     * 创建文件
     */
    public static File createFile(String path, String data) {
        return write(new File(path), data);
    }

    public static File createFile(File file, String data) {
        return write(file, data);
    }

    public static File getFileNoExists(String path) {
        int i = 0;
        File file = new File(path);
        while (file.exists()) {
            i++;
            int index = path.lastIndexOf(".");
            file = new File(path.substring(0, index) + "(" + i + ")" + path.substring(index));
        }
        return file;
    }

    /**
     * 写入文件
     */
    public static File write(File file, String data) {
        try {
            AssertUt.notNull(file);
            FileUtils.write(file, data, charset);
            return file;
        } catch (IOException e) {
            throw new FileException("写入文件失败...", e);
        }
    }

    public static FileOutputStream openOutputStream(File file) {
        try {
            AssertUt.notNull(file);
            return FileUtils.openOutputStream(file);
        } catch (IOException e) {
            throw new FileException("打开文件流...", e);
        }
    }

    /**
     * 读取文件
     */
    public static String read(File file) {
        try {
            AssertUt.notNull(file);
            return FileUtils.readFileToString(file, charset);
        } catch (IOException e) {
            throw new FileException("读取文件 " + file.getAbsolutePath() + " 失败...", e);
        }
    }

    /**
     * 复制数据
     */
    public static long copy(File inputFile, HttpServletResponse response) {
        InputStream input = null;
        try {
            input = new FileInputStream(inputFile);
            return copy(input, response.getOutputStream());
        } catch (Exception e) {
            throw new FileException("数据复制失败...", e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    public static long copy(final InputStream input, final OutputStream output) {
        try {
            return IOUtils.copy(input, output);
        } catch (IOException e) {
            throw new FileException("数据复制失败...", e);
        }
    }

    /**
     * 删除文件
     */
    public static boolean delete(File file) {
        AssertUt.notNull(file);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void delete(List<File> files) {
        files.forEach((file) -> {
            delete(file);
        });
    }

    /**
     * 创建文件夹
     */
    public static void mkdir(File directory) {
        try {
            FileUtils.forceMkdir(directory);
        } catch (IOException e) {
            throw new FileException("创建文件夹失败...", e);
        }
    }
    // ********************** 压缩解压 ************************

    /**
     * 压缩文件
     */
    public static File tarGz(String outPath) {
        File tar = tar(outPath, new File(outPath));
        File gz = gzip(tar.getPath(), tar);
        FileUt.delete(tar);
        return gz;
    }

    public static File zip(String outPath, File... files) {
        return archive("zip", outPath, files);
    }

    public static File tar(String outPath, File... files) {
        return archive("tar", outPath, files);
    }

    public static File gzip(String outPath, File file) {
        return compress("gz", outPath, file);
    }

    private static File compress(String suffix, String outPath, File file) {
        return compress(suffix, getOutFile(suffix, outPath), file);
    }

    private static File compress(String suffix, File outFile, File file) {
        CompressorOutputStream out = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            out = new CompressorStreamFactory().createCompressorOutputStream(suffix, new FileOutputStream(outFile));
            IOUtils.copy(in, out);
        } catch (Exception e) {
            throw new FileException("压缩文件 " + outFile + " 失败...", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
        return outFile;
    }

    @SuppressWarnings("unused")
    private static File archive(String suffix, String outPath, String... filePaths) {
        List<File> files = new ArrayList<>();
        for (String path : filePaths) {
            files.add(new File(path));
        }
        return archive(suffix, outPath, files.toArray(new File[]{}));
    }

    private static File archive(String suffix, String outPath, File... files) {
        if (files == null || files.length == 0) {
            files = new File[]{new File(outPath)};
        }
        return archive(suffix, getOutFile(suffix, outPath), files);
    }

    private static File archive(String suffix, File outFile, File... files) {
        ArchiveOutputStream out = null;
        try {
            out = new ArchiveStreamFactory().createArchiveOutputStream(suffix, new FileOutputStream(outFile));
            for (File file : files) {
                if (file.isFile()) {
                    addArchiveEntry(suffix, out, file.getName(), file);
                } else {
                    Map<String, File> map = getArchiveMap(file);
                    for (Entry<String, File> entry : map.entrySet()) {
                        addArchiveEntry(suffix, out, entry.getKey(), entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            throw new FileException("压缩文件 " + outFile + " 失败...", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return outFile;
    }

    private static File getOutFile(String suffix, String outPath) {
        String suff = "." + suffix;
        if (!StringUtils.endsWith(outPath, suff)) {
            outPath = outPath + suff;
        }
        return new File(outPath);
    }

    private static void addArchiveEntry(String suffix, final ArchiveOutputStream out, String name, File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            out.putArchiveEntry(getArchiveEntry(suffix, name, file));
            IOUtils.copy(in, out);
            out.closeArchiveEntry();
        } catch (Exception e) {
            throw new FileException("添加压缩文件 " + name + " 失败...", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    private static ArchiveEntry getArchiveEntry(String suffix, String name, File file) throws Exception {
        switch (suffix) {
            case "zip":
                return new ZipArchiveEntry(file, name);
            case "tar":
                return new TarArchiveEntry(file, name);
            case "jar":
                return new JarArchiveEntry(new ZipArchiveEntry(file, name));
            case "ar":
                return new ArArchiveEntry(file, name);
            case "arj":
                return new ArjArchiveEntry();
            case "cpio":
                return new CpioArchiveEntry(file, name);
        }
        return null;
    }

    private static Map<String, File> getArchiveMap(File file) {
        return getArchiveMap(null, file, null);
    }

    private static Map<String, File> getArchiveMap(Map<String, File> map, File file, String pare) {
        if (map == null) map = new HashMap<>();
        String name = file.getName();
        if (pare != null) {
            name = pare + "/" + name;
        }
        if (file.isFile()) {
            map.put(name, file);
        } else if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                getArchiveMap(map, f, name);
            }
        }
        return map;
    }

}
