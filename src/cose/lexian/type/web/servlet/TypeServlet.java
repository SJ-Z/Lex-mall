package cose.lexian.type.web.servlet;

import cn.itcast.servlet.BaseServlet;
import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.service.TypeService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "TypeServlet", urlPatterns = "/TypeServlet")
public class TypeServlet extends BaseServlet {
    private TypeService typeService = new TypeService();
    private GoodsService goodsService = new GoodsService();

    /** 查询某一级分类下所有商品和二级分类 */
    public String findTypeAll(HttpServletRequest request, HttpServletResponse response) {
        String t_id = request.getParameter("t_id");
        List<Goods> goodsList = goodsService.findGoodsByType(t_id); //包含商品信息和店铺信息
        List<SubType> subTypeList = typeService.findAllSubType(t_id);
        request.setAttribute("goodsList", goodsList);
        request.setAttribute("subTypeList", subTypeList);
        return "";
    }


}
