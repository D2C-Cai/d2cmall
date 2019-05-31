package com.d2c.analyze.test.ant;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordSortTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private ExecutorService pools = Executors.newFixedThreadPool(30);
    private Map<String, Integer> map = new ConcurrentHashMap<>();
    private TreeMap<Integer, String> treeMap = new TreeMap<>();

    @Test
    public void test() {
        List<String> filePaths = new ArrayList<String>();
        // 从数据库读取文件地址列表，数量过多可以分页读取，循环调用loadFiles
        // filePaths.add(dao.loadFilePath());
        loadFiles(filePaths);
        findMaxWord();
    }

    /**
     * 对出现频率最高的字符串进行排序
     */
    public String[] findMaxWord() {
        if (!map.isEmpty()) {
            logger.error("单词数量为空");
        }
        map.forEach((k, v) -> {
            treeMap.put(v, k);
        });
        int i = 0;
        String[] words = new String[100];
        for (Entry<Integer, String> ent : treeMap.entrySet()) {
            words[i] = ent.getValue();
            i++;
            logger.info("单词:" + ent.getValue() + "  次数: " + ent.getKey() + " 排名: " + i);
            if (i >= 100) break;
        }
        return words;
    }

    /**
     * 多线程读取文件并读取单词到Map中
     */
    public void loadFiles(List<String> filePaths) {
        if (filePaths == null) return;
        filePaths.forEach(path -> {
            pools.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String words = readFile(path);
                        loadWords(words);
                    } catch (Exception e) {
                        logger.error("文件计算字符数量异常, path：" + path, e);
                    }
                }
            });
        });
    }

    /**
     * 读取单词并增加到线程安全的Map
     */
    public void loadWords(String words) {
        if (StringUtils.isBlank(words)) return;
        for (String w : words.split(" ")) {
            Integer cnt = map.get(w);
            if (cnt == null) cnt = 0;
            map.put(w, cnt++);
        }
    }

    /**
     * 读取文件
     */
    public String readFile(String path) {
        try {
            File file = new File(path);
            return FileUtils.readFileToString(file, Charsets.toCharset("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException("读取文件 " + path + " 失败...", e);
        }
    }

}
