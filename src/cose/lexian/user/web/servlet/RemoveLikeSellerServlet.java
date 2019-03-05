package cose.lexian.user.web.servlet;

import cose.lexian.goods.service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RemoveLikeSellerServlet", urlPatterns = "/RemoveLikeSellerServlet")
public class RemoveLikeSellerServlet extends HttpServlet {
    GoodsService goodsService = new GoodsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String u_id = request.getParameter("u_id").split("\"")[1];
        String s_id = request.getParameter("s_id").split("\"")[1];
        goodsService.removeLikeSeller(u_id, s_id);

    }
}
