package cose.lexian.admin.web.filter;

import cose.lexian.admin.domain.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*", "/adminjsps/admin/goods/*", "/adminjsps/admin/type/*"})
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 1.从session中获取管理员信息
         * 2.判断，如果session中存在管理员信息，放行
         * 3.否则，保存错误信息，转发到login.jsp显示
         */
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        Admin admin = (Admin) httpRequest.getSession().getAttribute("session_admin");
        if(admin != null) {
            chain.doFilter(req, resp);
        } else {
            httpRequest.setAttribute("msg", "您还没有登录！");
            httpRequest.getRequestDispatcher("/adminjsps/login.jsp").forward(httpRequest, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
