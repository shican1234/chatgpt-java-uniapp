package io.renren.cf.support.qualifier.impl;

import io.renren.cf.support.qualifier.PreventQualifier;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * 所有均不阻止
 *
 * @author sunkl
 * @date 2020/3/18 16:52
 */
public class NonePreventQualifier implements PreventQualifier {
    /**
     * 判定是否需要阻止提交
     *
     * @param request
     * @param handlerMethod
     * @return
     */
    @Override
    public boolean isPrevent(HttpServletRequest request, HandlerMethod handlerMethod) {
        return false;
    }
}
