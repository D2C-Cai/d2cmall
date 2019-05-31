package com.d2c.frame.provider.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class ElasticSearchConfig {

    /**
     * index like mysql database,type like mysql table,document like mysql row,
     * field like mysql field
     */
    public static final String INDEX_NAME = "d2cindex2";
    private static final Log logger = LogFactory.getLog(ElasticSearchConfig.class);
    private static TransportClient client;

    public ElasticSearchConfig(String ES_HOST1, int ES_PORT1, String ES_HOST2, int ES_PORT2, String ES_HOST3,
                               int ES_PORT3) {
        ImmutableSettings.Builder settings = ImmutableSettings.settingsBuilder();
        settings.put("cluster.name", "elasticsearch");
        settings.put("threadpool.bulk.type", "fixed");
        settings.put("threadpool.bulk.size", 100);
        settings.put("threadpool.bulk.queue_size", 1000);
        settings.put("threadpool.index.type", "fixed");
        settings.put("threadpool.index.size", 3000);
        settings.put("threadpool.index.queue_size", 5000);
        settings.put("threadpool.search.type", "fixed");
        settings.put("threadpool.search.size", 3000);
        settings.put("threadpool.search.queue_size", 5000);
        settings.put("client.transport.sniff", true);
        settings.build();
        client = new TransportClient(settings).addTransportAddresses(new InetSocketTransportAddress(ES_HOST1, ES_PORT1),
                new InetSocketTransportAddress(ES_HOST2, ES_PORT2), new InetSocketTransportAddress(ES_HOST3, ES_PORT3));
    }

    /**
     * 日期类型转换成long数值
     *
     * @param date
     * @return
     */
    public static long transDate2Long(Date date) {
        if (date == null) {
            return 0L;
        }
        return date.getTime();
    }

    /**
     * 构建索引文档内容
     *
     * @param builder
     * @param obj
     */
    public static void buildContent(XContentBuilder builder, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                try {
                    // 跳过某些特殊get方法
                    property.getWriteMethod();
                } catch (Exception e) {
                    continue;
                }
                Method setter = property.getWriteMethod();
                try {
                    // 跳过某些特殊get方法
                    setter.getParameterTypes();
                } catch (Exception e) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Class<?> returnType = getter.getReturnType();
                if (!"class".equalsIgnoreCase(property.getName())) {
                    if (getter.invoke(obj) == null) {
                        if (returnType.isAssignableFrom(Integer.class)) {
                            builder.field(property.getName(), 0);
                        } else if (returnType.isAssignableFrom(Long.class)) {
                            builder.field(property.getName(), 0L);
                        } else if (returnType.isAssignableFrom(Double.class)) {
                            builder.field(property.getName(), 0.00);
                        } else if (returnType.isAssignableFrom(BigDecimal.class)) {
                            builder.field(property.getName(), 0.00);
                        } else if (returnType.isAssignableFrom(Date.class)) {
                            builder.field(property.getName(), 0L);
                        }
                    } else {
                        if (returnType.isAssignableFrom(Date.class)) {
                            builder.field(property.getName(), transDate2Long((Date) getter.invoke(obj)));
                        } else if (returnType.isAssignableFrom(BigDecimal.class)) {
                            builder.field(property.getName(), ((BigDecimal) getter.invoke(obj)).doubleValue());
                        } else {
                            builder.field(property.getName(), getter.invoke(obj));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 文档map转bean
     *
     * @param map
     * @param obj
     */
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key) && !"id".equals(key) && !"class".equalsIgnoreCase(key)) {
                    Object value = map.get(key);
                    try {
                        // 跳过某些特殊get方法
                        property.getWriteMethod();
                    } catch (Exception e) {
                        continue;
                    }
                    Method setter = property.getWriteMethod();
                    try {
                        // 跳过某些特殊get方法
                        setter.getParameterTypes();
                    } catch (Exception e) {
                        continue;
                    }
                    Class<?> parameterType = setter.getParameterTypes()[0];
                    if (parameterType.isAssignableFrom(Integer.class) && value instanceof Number) {
                        setter.invoke(obj, ((Number) value).intValue());
                    } else if (parameterType.isAssignableFrom(Long.class) && value instanceof Number) {
                        setter.invoke(obj, ((Number) value).longValue());
                    } else if (parameterType.isAssignableFrom(Double.class) && value instanceof Number) {
                        setter.invoke(obj, ((Number) value).doubleValue());
                    } else if (parameterType.isAssignableFrom(BigDecimal.class)) {
                        value = new BigDecimal(value.toString());
                        setter.invoke(obj, value);
                    } else if (parameterType.isAssignableFrom(Date.class)) {
                        value = new Date(Long.valueOf(value.toString()));
                        setter.invoke(obj, value);
                    } else if (parameterType.isAssignableFrom(String.class)) {
                        setter.invoke(obj, value);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public TransportClient getClient() {
        return client;
    }

}
