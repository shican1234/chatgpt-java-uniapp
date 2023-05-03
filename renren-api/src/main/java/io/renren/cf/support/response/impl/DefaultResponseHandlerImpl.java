package io.renren.cf.support.response.impl;


import io.renren.cf.support.response.ResponseHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DefaultResponseHandlerImpl implements ResponseHandler {
    /**
     * 处理响应
     *
     * @param response
     * @throws Exception
     * @author sunkl
     * @date 2020/3/18 17:45
     */
    @Override
    public void handlerResponse(HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write("操作太频繁了，请休息一会儿吧");
        writer.flush();
        writer.close();
    }
}
