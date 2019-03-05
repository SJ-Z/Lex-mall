package cose.lexian.user.web.servlet;

import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.seller.domain.Seller;
import cose.lexian.seller.service.SellerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "LoadSellerServlet", urlPatterns = "/LoadSellerServlet")
public class LoadSellerServlet extends HttpServlet {
    private SellerService sellerService = new SellerService();
    private GoodsService goodsService = new GoodsService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String s_id = request.getParameter("s_id").split("\"")[1];
        Seller seller = sellerService.getSellerById(s_id);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String s_openTime = format.format(seller.getS_openTime());
        String s_closeTime = format.format(seller.getS_closeTime());
        seller.setS_openTime(0);
        seller.setS_closeTime(0);

        List<Goods> goodsList = goodsService.findAllGoods(s_id);
        for (Goods goods : goodsList) {
            goods.setG_updateTime(null);
            goods.setG_image("/lexian-mall" + goods.getG_image());
            goods.getG_seller().setS_closeTime(0);
            goods.getG_seller().setS_openTime(0);
        }

        String json = "{\"seller\":" + JSONObject.fromObject(seller) + ", \"s_openTime\":\"" + s_openTime +
                "\", \"s_closeTime\":\"" + s_closeTime + "\", " +
                "\"goodsList\":" + JSONArray.fromObject(goodsList).toString() + "}";

        response.getWriter().print(json);
    }

//    @Test
//    public void test() {
//        String s_id = "25F4EEE1B3154764B443D763EC5EF3C5";
//        Seller seller = sellerService.getSellerById(s_id);
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
//        String s_openTime = format.format(seller.getS_openTime());
//        String s_closeTime = format.format(seller.getS_closeTime());
//        seller.setS_openTime(0);
//        seller.setS_closeTime(0);
//
//        List<Goods> goodsList = goodsService.findAllGoods(s_id);
//        for (Goods goods : goodsList) {
//            goods.setG_updateTime(null);
//            goods.setG_image("/lexian-mall" + goods.getG_image());
//        }
//
//        String json = "{\"seller\":" + JSONObject.fromObject(seller) + ", \"s_openTime\":\"" + s_openTime +
//                "\", \"s_closeTime\":\"" + s_closeTime + "\", " +
//                "\"goodsList\":" + JSONArray.fromObject(goodsList).toString() + "}";
//
//        System.out.println(json);
//    }
}
