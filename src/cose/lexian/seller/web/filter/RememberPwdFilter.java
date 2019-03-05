package cose.lexian.seller.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "RememberPwdFilter", urlPatterns = "/sellerjsps/index.jsp")
public class RememberPwdFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        Cookie[] cookies = request.getCookies();
        Cookie findC = null;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("autoLogin".equals(c.getName())) {
                    findC = c;
                    break;
                }
            }
        }

        if (findC != null) {
            String s_name = findC.getValue().split(":")[0];
            String s_pwd = findC.getValue().split(":")[1];
            request.setAttribute("s_name", s_name);
            request.setAttribute("s_pwd", s_pwd);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
