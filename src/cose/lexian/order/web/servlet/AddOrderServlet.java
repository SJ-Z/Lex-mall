package cose.lexian.order.web.servlet;

import cn.itcast.commons.CommonUtils;
import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.order.domain.Order;
import cose.lexian.order.domain.OrderItem;
import cose.lexian.order.service.OrderService;
import cose.lexian.seller.domain.Seller;
import cose.lexian.user.domain.User;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "AddOrderServlet", urlPatterns = "/AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
    private GoodsService goodsService = new GoodsService();
    private OrderService orderService = new OrderService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String u_id = request.getParameter("u_id").split("\"")[1]; //取出json中的u_id
        String s_id = request.getParameter("s_id"); //取出json中的s_id
        User user = new User();
        user.setU_id(u_id);
        Seller seller = new Seller();
        seller.setS_id(s_id);
        String goodsStr = request.getParameter("goods");
        JSONArray jsonArray = JSONArray.fromObject(goodsStr); //取出json中的goods

        /**
         * 生成订单
         */
        Order order = new Order();
        order.setO_id(CommonUtils.uuid());
        order.setO_orderTime(new Date());
        order.setO_user(user);
        order.setO_seller(seller);
        order.setO_code(order.getO_id().substring(0, 6));
        order.setO_state(1);

        /**
         * 利用goods生成orderitem
         */
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (int i=0; i<jsonArray.size(); i++) {
            Goods goods = goodsService.findGoodsById((String) jsonArray.getJSONObject(i).get("g_id"));
            OrderItem orderItem = new OrderItem();
            orderItem.setOi_id(CommonUtils.uuid());
            orderItem.setOi_goods(goods);
            orderItem.setOi_order(order);
            orderItem.setOi_count((Integer) jsonArray.getJSONObject(i).get("g_num"));
            orderItemList.add(orderItem);
        }
        order.setO_orderItemList(orderItemList);
        orderService.addOrder(order);
    }
}
