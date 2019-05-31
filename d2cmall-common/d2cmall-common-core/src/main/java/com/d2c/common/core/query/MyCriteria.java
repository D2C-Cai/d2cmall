package com.d2c.common.core.query;

import com.d2c.common.api.json.Json;
import com.d2c.common.api.json.JsonBean;
import com.d2c.common.api.json.JsonList;
import com.d2c.common.base.exception.BaseException;
import com.d2c.common.core.model.geo.GeoCommand;
import com.d2c.common.core.model.geo.GeoJson;
import com.d2c.common.core.model.geo.Sphere;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Shape;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import static org.springframework.util.ObjectUtils.nullSafeHashCode;

/**
 * 查询条件
 *
 * @author wull
 */
public class MyCriteria {

    private static final Object NOT_SET = new Object();
    /**
     * 条件列表
     */
    private List<MyCriteria> list;
    private String key;
    private LinkedHashMap<String, Object> criteria = new LinkedHashMap<String, Object>();
    private Object isValue = NOT_SET;

    public MyCriteria() {
        this.list = new ArrayList<MyCriteria>();
    }

    public MyCriteria(String key) {
        this.list = new ArrayList<MyCriteria>();
        this.list.add(this);
        this.key = key;
    }

    protected MyCriteria(List<MyCriteria> list, String key) {
        this.list = list;
        this.list.add(this);
        this.key = key;
    }

    /**
     * 创建查询
     */
    public static MyCriteria build() {
        return new MyCriteria();
    }

    /**
     * 查询条件where
     */
    public static MyCriteria where(String key) {
        return new MyCriteria(key);
    }

    private static boolean requiresGeoJsonFormat(Object value) {
        return value instanceof GeoJson
                || (value instanceof GeoCommand && ((GeoCommand) value).getShape() instanceof GeoJson);
    }

    /**
     * 查询条件 and
     */
    public MyCriteria and(String key) {
        return new MyCriteria(this.list, key);
    }

    /**
     * 等于，可为null
     */
    public MyCriteria is(Object o) {
        if (!isValue.equals(NOT_SET)) {
            throw new BaseException("不能连续的使用is查询, 需要使用and连接");
        }
        if (lastOperatorWasNot()) {
            throw new BaseException("无效查询：not不能用于is, 使用ne查询");
        }
        this.isValue = o;
        return this;
    }

    private boolean lastOperatorWasNot() {
        return !this.criteria.isEmpty() && "$not".equals(this.criteria.keySet().toArray()[this.criteria.size() - 1]);
    }

    /**
     * 不等于
     */
    public MyCriteria ne(Object o) {
        criteria.put("$ne", o);
        return this;
    }

    /**
     * 小于
     */
    public MyCriteria lt(Object o) {
        criteria.put("$lt", o);
        return this;
    }

    /**
     * 小于等于
     */
    public MyCriteria lte(Object o) {
        criteria.put("$lte", o);
        return this;
    }

    /**
     * 大于
     */
    public MyCriteria gt(Object o) {
        criteria.put("$gt", o);
        return this;
    }

    /**
     * 大于等于
     */
    public MyCriteria gte(Object o) {
        criteria.put("$gte", o);
        return this;
    }

    /**
     * in包括列表
     */
    public MyCriteria in(Object... o) {
        if (o.length > 1 && o[1] instanceof Collection) {
            throw new BaseException("你只能传递一个类型的参数 " + o[1].getClass().getName());
        }
        criteria.put("$in", Arrays.asList(o));
        return this;
    }

    public MyCriteria in(Collection<?> c) {
        criteria.put("$in", c);
        return this;
    }

    /**
     * nin不包括列表
     */
    public MyCriteria nin(Object... o) {
        return nin(Arrays.asList(o));
    }

    public MyCriteria nin(Collection<?> o) {
        criteria.put("$nin", o);
        return this;
    }

    /**
     * 包括全部条件
     */
    public MyCriteria all(Object... o) {
        return all(Arrays.asList(o));
    }

    public MyCriteria all(Collection<?> o) {
        criteria.put("$all", o);
        return this;
    }

    /**
     * 集合大小
     */
    public MyCriteria size(int s) {
        criteria.put("$size", s);
        return this;
    }

    /**
     * 是否存在
     */
    public MyCriteria exists(boolean b) {
        criteria.put("$exists", b);
        return this;
    }

    /**
     * 数据类型
     */
    public MyCriteria type(int t) {
        criteria.put("$type", t);
        return this;
    }

    /**
     * 不为空
     */
    public MyCriteria not() {
        return not(null);
    }

    /**
     * 操作取反
     */
    private MyCriteria not(Object value) {
        criteria.put("$not", value);
        return this;
    }

    /**
     * 正则表达式查询
     */
    public MyCriteria regex(String re) {
        return regex(re, null);
    }

    public MyCriteria regex(String re, String options) {
        return regex(toPattern(re, options));
    }

    public MyCriteria regex(Pattern pattern) {
        Assert.notNull(pattern, "正则表达式不能为空!");
        if (lastOperatorWasNot()) {
            return not(pattern);
        }
        this.isValue = pattern;
        return this;
    }

    private Pattern toPattern(String regex, String options) {
        Assert.notNull(regex, "正则表达式不能为空!");
        return Pattern.compile(regex, regexFlags(options));
    }

    private int regexFlags(String options) {
        if (options == null) return 0;
        switch (key) {
            case "c":
                return Pattern.CANON_EQ;
            case "d":
                return Pattern.UNIX_LINES;
            case "i":
                return Pattern.CASE_INSENSITIVE;
            case "m":
                return Pattern.MULTILINE;
            case "s":
                return Pattern.DOTALL;
            case "t":
                return Pattern.LITERAL;
            case "u":
                return Pattern.UNICODE_CASE;
            case "x":
                return Pattern.COMMENTS;
            case "g":
                return 256;
            default:
                return 0;
        }
    }

    /**
     * GeoJson 几何对象是否包括数据
     * {@link $geoWithin}
     */
    public MyCriteria withinSphere(Circle circle) {
        Assert.notNull(circle, "circle不能为空!");
        criteria.put("$geoWithin", new GeoCommand(new Sphere(circle)));
        return this;
    }

    public MyCriteria within(Shape shape) {
        Assert.notNull(shape, "Shape不能为空!");
        criteria.put("$geoWithin", new GeoCommand(shape));
        return this;
    }

    /**
     * 是否Point(x, y)附近
     * {@link $near}
     */
    public MyCriteria near(Point point) {
        Assert.notNull(point, "Point不能为空!");
        criteria.put("$near", point);
        return this;
    }

    /**
     * 是否Point(x, y)附近范围
     * {@link $nearSphere}
     */
    public MyCriteria nearSphere(Point point) {
        Assert.notNull(point, "Point不能为空!");
        criteria.put("$nearSphere", point);
        return this;
    }

    /**
     * GeoJson 相交
     */
    public MyCriteria intersects(JsonBean geoJson) {
        Assert.notNull(geoJson, "GeoJson不能为空!");
        criteria.put("$geoIntersects", geoJson);
        return this;
    }

    /**
     * 最大距离
     */
    public MyCriteria maxDistance(double maxDistance) {
        if (createNearCriteriaForCommand("$near", "$maxDistance", maxDistance)
                || createNearCriteriaForCommand("$nearSphere", "$maxDistance", maxDistance)) {
            return this;
        }
        criteria.put("$maxDistance", maxDistance);
        return this;
    }

    /**
     * 最小距离
     */
    public MyCriteria minDistance(double minDistance) {
        if (createNearCriteriaForCommand("$near", "$minDistance", minDistance)
                || createNearCriteriaForCommand("$nearSphere", "$minDistance", minDistance)) {
            return this;
        }
        criteria.put("$minDistance", minDistance);
        return this;
    }

    /**
     * 集合至少一个符合所有指定查询条件
     */
    public MyCriteria elemMatch(MyCriteria c) {
        criteria.put("$elemMatch", c.getJsonBean());
        return this;
    }

    /**
     * 通过Example（QBE）查询
     *
     * @see ExampleMatcher
     */
    public MyCriteria alike(Example<?> sample) {
        criteria.put("$sample", sample);
        this.list.add(this);
        return this;
    }

    /**
     * or 或查询
     */
    public MyCriteria orOperator(MyCriteria... criteria) {
        JsonList jsonList = getJsonList(criteria);
        return registerChain(new MyCriteria("$or").is(jsonList));
    }

    /**
     * not or 非或查询
     */
    public MyCriteria norOperator(MyCriteria... criteria) {
        JsonList jsonList = getJsonList(criteria);
        return registerChain(new MyCriteria("$nor").is(jsonList));
    }

    /**
     * and 查询
     */
    public MyCriteria andOperator(MyCriteria... criteria) {
        JsonList jsonList = getJsonList(criteria);
        return registerChain(new MyCriteria("$and").is(jsonList));
    }

    private MyCriteria registerChain(MyCriteria criteria) {
        if (lastOperatorWasNot()) {
            throw new IllegalArgumentException("$not不允许为最后一个操作: " + criteria.getJsonBean());
        } else {
            list.add(criteria);
        }
        return this;
    }

    public String getKey() {
        return this.key;
    }

    /**
     * 获得 Json
     */
    public String toJson() {
        return getJsonBean().toJson();
    }

    /**
     * 获得JSON对象
     */
    public JsonBean getJsonBean() {
        if (this.list.size() == 1) {
            return list.get(0).getSingleJson();
        } else if (CollectionUtils.isEmpty(this.list) && !CollectionUtils.isEmpty(this.criteria)) {
            return getSingleJson();
        } else {
            JsonBean json = new JsonBean();
            for (MyCriteria c : this.list) {
                JsonBean bean = c.getSingleJson();
                for (String k : bean.keySet()) {
                    setValue(json, k, bean.get(k));
                }
            }
            return json;
        }
    }

    protected JsonBean getSingleJson() {
        JsonBean json = new JsonBean();
        boolean not = false;
        for (Entry<String, Object> entry : criteria.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (requiresGeoJsonFormat(value)) {
                value = new JsonBean("$geometry", value);
            }
            if (not) {
                JsonBean notBean = new JsonBean();
                notBean.put(key, value);
                json.put("$not", notBean);
                not = false;
            } else {
                if ("$not".equals(key) && value == null) {
                    not = true;
                } else {
                    json.put(key, value);
                }
            }
        }
        if (!StringUtils.hasText(this.key)) {
            if (not) {
                return new JsonBean("$not", json);
            }
            return json;
        }
        JsonBean bean = new JsonBean();
        if (!NOT_SET.equals(isValue)) {
            bean.put(this.key, this.isValue);
            bean.putAll(json);
        } else {
            bean.put(this.key, json);
        }
        return bean;
    }

    private JsonList getJsonList(MyCriteria[] criteria) {
        JsonList jsonList = new JsonList();
        for (MyCriteria c : criteria) {
            jsonList.add(c.getJsonBean());
        }
        return jsonList;
    }

    private void setValue(JsonBean dbo, String key, Object value) {
        Object existing = dbo.get(key);
        if (existing == null) {
            dbo.put(key, value);
        } else {
            throw new BaseException("Due to limitations of the com.mongodb.BasicDBObject, "
                    + "you can't add a second '" + key + "' expression specified as '" + key + " : " + value + "'. "
                    + "MyCriteria already contains '" + key + " : " + existing + "'.");
        }
    }

    private boolean createNearCriteriaForCommand(String command, String operation, double maxDistance) {
        if (!criteria.containsKey(command)) {
            return false;
        }
        Object existingNearOperationValue = criteria.get(command);
        if (existingNearOperationValue instanceof Json) {
            ((Json) existingNearOperationValue).put(operation, maxDistance);
            return true;
        } else if (existingNearOperationValue instanceof GeoJson) {
            JsonBean dbo = new JsonBean("$geometry", existingNearOperationValue).append(operation, maxDistance);
            criteria.put(command, dbo);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        MyCriteria that = (MyCriteria) obj;
        if (this.list.size() != that.list.size()) {
            return false;
        }
        for (int i = 0; i < this.list.size(); i++) {
            MyCriteria left = this.list.get(i);
            MyCriteria right = that.list.get(i);
            if (!simpleCriteriaEquals(left, right)) {
                return false;
            }
        }
        return true;
    }

    private boolean simpleCriteriaEquals(MyCriteria left, MyCriteria right) {
        boolean keyEqual = left.key == null ? right.key == null : left.key.equals(right.key);
        boolean criteriaEqual = left.criteria.equals(right.criteria);
        boolean valueEqual = isEqual(left.isValue, right.isValue);
        return keyEqual && criteriaEqual && valueEqual;
    }

    private boolean isEqual(Object left, Object right) {
        if (left == null) {
            return right == null;
        }
        if (left instanceof Pattern) {
            return right instanceof Pattern ? ((Pattern) left).pattern().equals(((Pattern) right).pattern()) : false;
        }
        return ObjectUtils.nullSafeEquals(left, right);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result += nullSafeHashCode(key);
        result += criteria.hashCode();
        result += nullSafeHashCode(isValue);
        return result;
    }

}
