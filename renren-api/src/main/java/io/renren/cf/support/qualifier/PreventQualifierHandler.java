package io.renren.cf.support.qualifier;


import io.renren.cf.annotation.ExcludePreventRepeat;
import io.renren.cf.annotation.PreventRepeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 定义阻止限定
 */
public class PreventQualifierHandler {

    @Autowired
    @Qualifier(value = "nonePreventQualifier")
    private PreventQualifier preventQualifier;

    /**
     * 是否需要拦截
     *
     * @return
     * @author sunkl
     * @date 2020/3/19 18:34
     */
    public boolean globalIsPrevent(HttpServletRequest request, HandlerMethod handlerMethod) {
        if (handlerMethod.hasMethodAnnotation(ExcludePreventRepeat.class)) {
            return false;
        }

        if (handlerMethod.hasMethodAnnotation(PreventRepeat.class)) {
            return true;
        }
        return preventQualifier.isPrevent(request, handlerMethod);
    }
}
