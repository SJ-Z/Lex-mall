package cose.lexian.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //处理post请求编码问题
        req.setCharacterEncoding("utf-8");
        HttpServletRequest request = (HttpServletRequest) req;

        //处理get请求的编码问题
        /*
        调包request
        1.写一个request的装饰类
        2.在放行时，使用我们自己的request
         */
        if(request.getMethod().equalsIgnoreCase("get")){
            EncodingRequest er = new EncodingRequest(request);
            chain.doFilter(er, resp);
        } else if (request.getMethod().equalsIgnoreCase("post")) {
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
