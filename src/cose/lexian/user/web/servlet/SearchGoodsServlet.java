package cose.lexian.user.web.servlet;

import cose.lexian.goods.domain.Goods;
import cose.lexian.user.service.UserService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchGoodsServlet", urlPatterns = "/SearchGoodsServlet")
public class SearchGoodsServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String keyword = request.getParameter("keyword"); //获取搜索词
        List<Goods> goodsList = userService.searchGoods(keyword);

        for (Goods goods : goodsList) { //去除不必要的数据
            goods.setG_updateTime(null);
            goods.getG_seller().setS_openTime(0);
            goods.getG_seller().setS_closeTime(0);
            goods.setG_image("/lexian-mall" + goods.getG_image());
        }

        String json = "{\"goodsList\":" + JSONArray.fromObject(goodsList).toString() + "}";
        response.getWriter().print(json);
    }
}
