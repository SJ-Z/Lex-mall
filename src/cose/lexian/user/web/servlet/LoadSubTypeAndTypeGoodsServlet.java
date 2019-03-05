package cose.lexian.user.web.servlet;

import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.service.TypeService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoadSubTypeAndTypeGoodsServlet", urlPatterns = "/LoadSubTypeAndTypeGoodsServlet")
public class LoadSubTypeAndTypeGoodsServlet extends HttpServlet {
    private TypeService typeService = new TypeService();
    private GoodsService goodsService = new GoodsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String t_id = request.getParameter("t_id");
        //String t_id = "55555555555555555555555555555555";
        List<SubType> subTypeList = typeService.findAllSubType(t_id); //获取到该一级分类下所有二级分类
        List<Goods> goodsList = goodsService.findGoodsByType(t_id); //获取一级分类下所有商品

        for (Goods goods : goodsList) { //去除不必要的数据
            goods.setG_updateTime(null);
            goods.getG_seller().setS_openTime(0);
            goods.getG_seller().setS_closeTime(0);
            goods.setG_image("/lexian-mall" + goods.getG_image());
        }
        String json = "{\"code\":\"1\", \"msg\":\"success\", "
                + "\"subTypeList\":" + JSONArray.fromObject(subTypeList).toString() + ", "
                + "\"goodsList\":" + JSONArray.fromObject(goodsList).toString()
                + "}";

        //System.out.println(json);
        response.getWriter().print(json);
    }
}
