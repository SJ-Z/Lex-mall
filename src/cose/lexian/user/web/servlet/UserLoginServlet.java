package cose.lexian.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.seller.domain.Seller;
import cose.lexian.seller.service.SellerService;
import cose.lexian.user.domain.User;
import cose.lexian.user.service.UserService;
import cose.lexian.util.UserException;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserLoginServlet", urlPatterns = "/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    UserService userService = new UserService();
    GoodsService goodsService = new GoodsService();
    SellerService sellerService = new SellerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        User form = CommonUtils.toBean(req.getParameterMap(), User.class);
        try {
            String u_id = userService.login(form);
            List<Goods> likeGoodsList = goodsService.findLikeGoods(u_id);
            for (Goods goods : likeGoodsList) { //去除不必要的数据
                goods.setG_updateTime(null);
                goods.getG_seller().setS_openTime(0);
                goods.getG_seller().setS_closeTime(0);
                goods.setG_image("/lexian-mall" + goods.getG_image());
            }
            List<Seller> likeSellerList = sellerService.findLikeSeller(u_id);
            for (Seller seller : likeSellerList) { //去除不必要的数据
                seller.setS_openTime(0);
                seller.setS_closeTime(0);
            }

            String json = "{\"u_id\":\"" + u_id + "\", \"likegoodsList\":" + JSONArray.fromObject(likeGoodsList).toString()
                    + ", \"likesellerList\":" + JSONArray.fromObject(likeSellerList).toString() + "}";
            resp.getWriter().print(json);
        } catch (UserException e) {
            String msg = e.getMessage();
            String json = "{\"msg\":\"" + msg + "\"}";
            resp.getWriter().print(json);
        }
    }

//    @Test
//    public void test() {
//
//        String u_id = "11111111111111111111111111111111";
//        List<Goods> likeGoodsList = goodsService.findLikeGoods(u_id);
//        for (Goods goods : likeGoodsList) { //去除不必要的数据
//            goods.setG_updateTime(null);
//            goods.getG_seller().setS_openTime(0);
//            goods.getG_seller().setS_closeTime(0);
//            goods.setG_image("/lexian-mall" + goods.getG_image());
//        }
//        List<Seller> likeSellerList = sellerService.findLikeSeller(u_id);
//        for (Seller seller : likeSellerList) { //去除不必要的数据
//            seller.setS_openTime(0);
//            seller.setS_closeTime(0);
//        }
//
//        String json = "{\"u_id\":\"" + u_id + "\", \"likegoodsList\":" + JSONArray.fromObject(likeGoodsList).toString()
//                + ", \"likesellerList\":" + JSONArray.fromObject(likeSellerList).toString() + "}";
//
//        System.out.println(json);
//    }
}
