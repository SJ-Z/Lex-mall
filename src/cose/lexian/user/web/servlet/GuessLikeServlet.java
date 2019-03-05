package cose.lexian.user.web.servlet;

import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 猜你喜欢
 */
@WebServlet(name = "GuessLikeServlet", urlPatterns = "/GuessLikeServlet")
public class GuessLikeServlet extends HttpServlet {
    private GoodsService goodsService = new GoodsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String u_id = request.getParameter("u_id").split("\"")[1];
        List<Goods> goodsList = goodsService.guessLikeGoods(u_id); //十条推荐商品记录

        for (Goods goods : goodsList) { //去除不必要的数据
            goods.setG_updateTime(null);
            goods.setG_type(null);
            goods.setG_subType(null);
            goods.getG_seller().setS_openTime(0);
            goods.getG_seller().setS_closeTime(0);
            goods.setG_image("/lexian-mall" + goods.getG_image());
        }

        String json = "{\"goodsList\":" + JSONArray.fromObject(goodsList).toString() + "}";

        //System.out.println(json);
        response.getWriter().print(json);
    }
}
