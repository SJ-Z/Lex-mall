package cose.lexian.user.web.servlet;

import cose.lexian.seller.service.SellerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本类用于用户收藏商家，ajax。需要客户端传递用户u_id和商家s_id
 */
@WebServlet(name = "LikeSellerServlet", urlPatterns = "/LikeSellerServlet")
public class LikeSellerServlet extends HttpServlet {
    private SellerService sellerService = new SellerService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String u_id = request.getParameter("u_id").split("\"")[1];
        String s_id = request.getParameter("s_id").split("\"")[1];
        sellerService.addLikeSeller(u_id, s_id); //调用service完成收藏操作

    }
}
