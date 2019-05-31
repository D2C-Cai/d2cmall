package com.d2c.mybatis.datasource;

/**
 * <pre>
 * 读/写动态数据库 决策者
 * 根据DataSourceType是write/read 来决定是使用读/写数据库
 * 通过ThreadLocal绑定实现选择功能
 * </pre>
 *
 * @author Zhang Kaitao
 */
public class ReadWriteDataSourceDecision {

    private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<DataSourceType>();

    public static void markWrite() {
        holder.set(DataSourceType.write);
    }

    public static void markRead() {
        holder.set(DataSourceType.read);
    }

    public static void reset() {
        holder.set(null);
    }

    public static boolean isChoiceNone() {
        return null == holder.get();
    }

    public static boolean isChoiceWrite() {
        return DataSourceType.write == holder.get();
    }

    public static boolean isChoiceRead() {
        return DataSourceType.read == holder.get();
    }

    public enum DataSourceType {
        write, read;
    }

}
