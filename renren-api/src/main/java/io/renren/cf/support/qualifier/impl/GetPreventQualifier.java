package io.renren.cf.support.qualifier.impl;

import io.renren.cf.support.qualifier.PreventQualifier;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 获取类型阻止
 *
 * @author sunkl
 * @date 2020/3/18 16:52
 */
public class GetPreventQualifier implements PreventQualifier, InitializingBean {

    private Set<RequestMethod> methods;

    /**
     * 判定是否需要阻止提交
     *
     * @param request
     * @param handlerMethod
     * @return
     */
    @Override
    public boolean isPrevent(HttpServletRequest request, HandlerMethod handlerMethod) {
        return methods.contains(RequestMethod.valueOf(request.getMethod()));
    }

    /**
     * 初始化请求类型
     *
     * @author sunkl
     * @date 2020/3/18 17:01
     */
    @Override
    public void afterPropertiesSet() {
        methods = new HashSet<>(Arrays.asList(RequestMethod.GET,
                RequestMethod.HEAD, RequestMethod.OPTIONS));
    }
}
