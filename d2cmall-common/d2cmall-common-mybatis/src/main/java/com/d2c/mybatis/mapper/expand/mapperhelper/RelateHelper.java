package com.d2c.mybatis.mapper.expand.mapperhelper;

import com.d2c.common.api.annotation.JoinClass;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityField;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.FieldHelper;
import tk.mybatis.mapper.util.SimpleTypeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelateHelper {

    /**
     * 关联表<JoinClass, String>
     */
    private static final Map<String, JoinBean> relateMap = new HashMap<String, JoinBean>();

    public static String getJoinColumn(Class<?> entityClass, Class<?> joinClass) {
        JoinBean jb = getJoinBean(entityClass, joinClass);
        if (jb == null) return "";
        return getJoinBean(entityClass, joinClass).columnName;
    }

    public static JoinBean getJoinBean(Class<?> entityClass, Class<?> joinClass) {
        return relateMap.get(getKey(entityClass, joinClass));
    }

    public static synchronized void initRelateMap(Class<?> entityClass, Config config) {
        EntityTable table = EntityHelper.getEntityTable(entityClass);
        //处理所有列
        List<EntityField> fields = null;
        if (config.isEnableMethodAnnotation()) {
            fields = FieldHelper.getAll(entityClass);
        } else {
            fields = FieldHelper.getFields(entityClass);
        }
        for (EntityField field : fields) {
            //如果启用了简单类型，就做简单类型校验，如果不是简单类型，直接跳过
            if (config.isUseSimpleType() && !SimpleTypeUtil.isSimpleType(field.getJavaType())) {
                continue;
            }
            processField(entityClass, table, field);
        }
    }

    /**
     * 处理一列
     */
    private static void processField(Class<?> entityClass, EntityTable entityTable, EntityField field) {
        //JoinClass
        if (field.isAnnotationPresent(JoinClass.class)) {
            JoinClass joinClass = field.getAnnotation(JoinClass.class);
            EntityColumn column = entityTable.getPropertyMap().get(field.getName());
            relateMap.put(getKey(entityClass, joinClass.type()), new JoinBean(column.getColumn(), joinClass));
        }
    }

    private static String getKey(Class<?> entityClass, Class<?> joinClass) {
        return entityClass.getSimpleName() + "-" + joinClass.getName();
    }

    public static class JoinBean {

        String columnName;
        Class<?> type;

        public JoinBean(String columnName, JoinClass joinClass) {
            this.columnName = columnName;
            this.type = joinClass.type();
        }

    }

}
