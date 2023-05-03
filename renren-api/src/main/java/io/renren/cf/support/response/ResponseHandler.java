package io.renren.cf.support.response;


import javax.servlet.http.HttpServletResponse;

/**
 * 拦截响应处理
 *
 * @author sunkl
 * @date 2020/3/18 17:44
 */
public interface ResponseHandler {

    /**
     * 处理响应
     *
     * @param response
     * @throws Exception
     * @author sunkl
     * @date 2020/3/18 17:45
     */
    void handlerResponse(HttpServletResponse response) throws Exception;
}
