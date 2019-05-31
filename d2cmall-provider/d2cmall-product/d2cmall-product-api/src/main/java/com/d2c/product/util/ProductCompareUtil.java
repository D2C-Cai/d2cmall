package com.d2c.product.util;

import com.d2c.product.model.Product;
import com.d2c.util.date.DateUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.common.collect.ImmutableMap;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

public class ProductCompareUtil {

    private static Map<String, String> nameMap = ImmutableMap.<String, String>builder().put("name", "名称")
            .put("productSellType", "销售类型").put("estimateDate", "预计发货时间（日期）").put("estimateDay", "预计发货时间（天）")
            .put("coupon", "是否支持优惠券").put("store", "商品库存").put("popStore", "第三方库存").put("price", "销售价格")
            .put("aPrice", "一口价价格").put("flashPrice", "限时购价格").put("collagePrice", "拼团价格").build();

    public static <T> boolean compare(T original, T current, ProductCompareType type, StringBuilder sb) {
        boolean isEqual = true;
        try {
            Class<?> clazz = original.getClass();
            List<String> fields = new ArrayList<>();
            if (type.equals(ProductCompareType.PRODUCT)) {
                fields = type.getProductField();
            } else if (type.equals(ProductCompareType.SKU)) {
                fields = type.getSkuField();
            }
            for (String field : fields) {
                Method getMethod = null;
                if ("aPrice".equals(field)) {
                    getMethod = clazz.getMethod("getaPrice", null);
                } else {
                    PropertyDescriptor pd = new PropertyDescriptor(field, clazz);
                    getMethod = pd.getReadMethod();
                }
                Object o1 = getMethod.invoke(original);
                Object o2 = getMethod.invoke(current);
                if (o1 != null && o1 instanceof Date) {
                    o1 = DateUtil.second2str((Date) o1);
                }
                if (o2 != null && o2 instanceof Date) {
                    o2 = DateUtil.second2str((Date) o2);
                }
                String s1 = o1 == null ? "null" : o1.toString();// 避免空指针异常
                String s2 = o2 == null ? "null" : o2.toString();// 避免空指针异常
                if (NumberUtils.isNumber(s1) && NumberUtils.isNumber(s2)) {
                    if (new BigDecimal(s1).compareTo(new BigDecimal(s2)) != 0) {
                        isEqual = false;
                        sb.append("<br/>" + nameMap.get(field) + " ：[" + s1 + "->" + s2 + "]");
                    }
                } else if (!s1.equals(s2)) {
                    isEqual = false;
                    sb.append("<br/>" + nameMap.get(field) + " ：[" + s1 + "->" + s2 + "]");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return isEqual;
    }

    public static void main(String[] args) {
        Product p1 = new Product();
        p1.setName("AA");
        // p1.setEstimateDate(new Date());
        p1.setaPrice(new BigDecimal(1));
        Product p2 = new Product();
        p2.setaPrice(new BigDecimal(2));
        p2.setName("AABB");
        p2.setEstimateDate(new Date(new Date().getTime() + 10000000000L));
        StringBuilder sb = new StringBuilder();
        compare(p1, p2, ProductCompareType.PRODUCT, sb);
        System.out.println(sb.toString());
    }

    public enum ProductCompareType {
        PRODUCT, SKU;
        private List<String> productField = Arrays
                .asList(new String[]{"name", "productSellType", "estimateDate", "estimateDay", "coupon"});
        private List<String> skuField = Arrays
                .asList(new String[]{"store", "popStore", "price", "aPrice", "flashPrice", "collagePrice"});

        public List<String> getProductField() {
            return productField;
        }

        public List<String> getSkuField() {
            return skuField;
        }
    }

}
