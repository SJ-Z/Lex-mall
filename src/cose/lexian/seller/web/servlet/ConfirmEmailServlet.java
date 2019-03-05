package cose.lexian.seller.web.servlet;

import cose.lexian.seller.dao.SellerDao;
import cose.lexian.seller.domain.Seller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ConfirmEmailServlet", urlPatterns = "/ConfirmEmailServlet")
public class ConfirmEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");

            String email = request.getParameter("s_email"); //获取请求参数
            /** 验证邮箱是否符合要求 */
            if(email == null || email.trim().isEmpty()) {
                response.getWriter().print("Email不能为空！");
                return;
            }
            SellerDao sellerDao = new SellerDao();
            Seller seller = sellerDao.findByEmail(email);
            if(seller != null) {
                response.getWriter().print("该邮箱已被注册");
                return;
            }
            response.getWriter().print("恭喜，邮箱格式正确！");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
