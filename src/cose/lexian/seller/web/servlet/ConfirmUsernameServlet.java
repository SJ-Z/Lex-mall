package cose.lexian.seller.web.servlet;

import cose.lexian.seller.dao.SellerDao;
import cose.lexian.seller.domain.Seller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ConfirmUsernameServlet", urlPatterns = "/ConfirmUsernameServlet")
public class ConfirmUsernameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            String username = request.getParameter("s_name"); //获取请求参数
            Pattern pattern = Pattern.compile("[A-Za-z]*");
            Matcher isChar = pattern.matcher(username.charAt(0)+"");
            /** 验证用户名是否符合要求 */
            if(username == null || username.trim().isEmpty()) {
                response.getWriter().print("用户名不能为空！");
                return;
            } else if(username.length() < 3 || username.length() > 15) {
                response.getWriter().print("用户名长度必须在3~15之间！");
                return;
            } else if(!isChar.matches()) {
                response.getWriter().print("用户名必须以字母开头！");
                return;
            } else if(!username.matches("^[A-Za-z]+\\w*")) {
                response.getWriter().print("只能包含字母、数字、下划线！");
                return;
            }
            /** 验证用户名是否已存在 */
            SellerDao sellerDao = new SellerDao();
            Seller seller = sellerDao.findByUsername(username);
            if(seller != null) {
                response.getWriter().print("该用户名已被注册");
            } else {
                response.getWriter().print("恭喜，该用户名可用");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
