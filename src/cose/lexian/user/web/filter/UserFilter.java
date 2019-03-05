package cose.lexian.user.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "UserFilter", urlPatterns = "/user/*")
public class UserFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
//        /** 1.只有未登录的商家才能自动登录 */
//        if(null == request.getSession().getAttribute("session_u_id")) {
//            Cookie[] cookies = request.getCookies();
//            Cookie findC = null;
//
//            if(cookies != null){
//                for(Cookie c : cookies){
//                    if("autoLogin".equals(c.getName())){
//                        findC = c;
//                        break;
//                    }
//                }
//            }
//
//            if(findC != null){
//                /** 2.自动登录Cookie中保存的用户名密码都需要是正确的才能自动登陆 */
//                String s_name = findC.getValue().split(":")[0];
//                String s_pwd = findC.getValue().split(":")[1];
//                SellerService sellerService = new SellerService();
//                try {
//                    Seller seller = sellerService.login(new Seller(s_name, s_pwd));
//                    /** 3.登录成功 */
//                    request.getSession().setAttribute("session_seller", seller);
//                    /** 自动登录成功，放行 */
//                    chain.doFilter(request, response);
//                } catch (UserException e) {
//                    request.setAttribute("msg", "用户名或密码错误，请重新登录");
//                    request.getRequestDispatcher("/sellerjsps/seller-register.jsp").forward(request, response);
//                }
//            } else {
//                request.setAttribute("msg", "您尚未登录");
//                request.getRequestDispatcher("/sellerjsps/seller-register.jsp").forward(request, response);
//            }
//        }
        /** 4.已登录的直接放行 */
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
