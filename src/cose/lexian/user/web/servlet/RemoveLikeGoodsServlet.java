package cose.lexian.user.web.servlet;

import cose.lexian.goods.service.GoodsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RemoveLikeGoodsServlet", urlPatterns = "/RemoveLikeGoodsServlet")
public class RemoveLikeGoodsServlet extends HttpServlet {
    GoodsService goodsService = new GoodsService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String u_id = request.getParameter("u_id");
        String g_id = request.getParameter("g_id");
        goodsService.removeLikeGoods(u_id.split("\"")[1], g_id);

    }
}
