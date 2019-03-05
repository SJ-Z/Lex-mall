package cose.lexian.seller.web.filter;

import cose.lexian.seller.domain.Seller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "BeforeLoginFilter", urlPatterns = "/sellerjsps/seller/*")
public class BeforeLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        /** 未登录的商家会被打回到登录界面 */
        Seller sell = (Seller) request.getSession().getAttribute("session_seller");
        if (sell == null) {
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

            request.setAttribute("msg", "您尚未登录");
            request.getRequestDispatcher("/sellerjsps/index.jsp").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
