package io.renren.cf.strategy;

/**
 * 重复提交策略枚举定义
 *
 * @author sunkl
 * @date 2020/3/18 16:42
 */
public enum PreventStrategy {
    ALL,
    POST,
    GET,
    NONE;
}
