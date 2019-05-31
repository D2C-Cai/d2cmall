package com.d2c.common.base.utils;

import org.apache.commons.lang3.RandomUtils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Random随机数工具类
 *
 * @author wull
 */
public class RandomUt {

    private static final Random RANDOM = new Random();

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

    public static String nextStr(int bound) {
        return String.valueOf(RANDOM.nextInt(bound));
    }

    /**
     * 在列表中随机获取{limit}个对象放入列表
     *
     * @param list
     * @param limit
     * @return
     */
    public static <T> List<T> randomList(List<T> list, int limit) {
        Collections.shuffle(list);
        if (limit < list.size()) {
            list = list.subList(0, limit);
        }
        return list;
    }

    public static <T> List<T> randomList(List<T> list) {
        Collections.shuffle(list);
        return list;
    }

    /**
     * 在列表中随机获取一个对象
     *
     * @param list
     * @return
     */
    public static <T> T randomOne(List<T> list) {
        if (list.isEmpty()) return null;
        return list.get(RandomUtils.nextInt(0, list.size()));
    }

}
