package io.renren.cf.support.identifier;

import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 幂等唯一标识
 *
 * @author sunkl
 * @date 2020/3/18 17:22
 */
public interface IdempotentUniquenessHandler {

    /**
     * 幂等唯一id
     *
     * @param request
     * @param method
     * @return
     * @author sunkl
     * @date 2020/3/18 17:23
     */
    String idempotentId(HttpServletRequest request, HandlerMethod method);
}
