package com.d2c.common.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * SpEL 表达式语言工具类
 *
 * @author wull
 */
public class SpelUt {

    protected static final Logger logger = LoggerFactory.getLogger(SpelUt.class);
    protected static final SpelExpressionParser parser = new SpelExpressionParser();

    public static <T> T parse(String expr, Class<T> resClz) {
        return parser.parseExpression(expr).getValue(resClz);
    }

    public static Object parse(String expr, Object bean) {
        return parser.parseExpression(expr).getValue(bean);
    }

    public static <T> T parse(String expr, Object bean, Class<T> resClz) {
        return parser.parseExpression(expr).getValue(bean, resClz);
    }

    public static <T> T parse(String expr, Map<String, Object> map, Class<T> resClz) {
        return parse(expr, null, map, resClz);
    }

    public static <T> T parse(String expr, Object root, Map<String, Object> map, Class<T> resClz) {
        StandardEvaluationContext ctx = new StandardEvaluationContext();
        ctx.setVariables(map);
        return parser.parseExpression(expr).getValue(ctx, root, resClz);
    }

    public static String parseStr(String expr, Object bean) {
        return parser.parseExpression(expr).getValue(bean, String.class);
    }

    /**
     * 根据表达式对Bean对象进行解析
     *
     * @param expr     表达式 #{userName}
     * @param bean     对象
     * @param [resClz] 返回类型, 不填返回Object
     * @return 对象表达式解析  bean.userName
     * @see org.springframework.expression.spel.standard.SpelExpression
     */
    public static <T> T parseBean(String expr, Object bean, Class<T> resClz) {
        Expression exp = parser.parseExpression(expr, new TemplateParserContext());
        return exp.getValue(bean, resClz);
    }

    public static Object parseBean(String expr, Object bean) {
        Expression exp = parser.parseExpression(expr, new TemplateParserContext());
        return exp.getValue(bean);
    }

}
