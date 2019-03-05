package cose.lexian.order.web.servlet;

import cn.itcast.servlet.BaseServlet;
import cose.lexian.order.domain.Order;
import cose.lexian.order.service.OrderService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "OrderServlet", urlPatterns = "/user/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /** 用户查询最近五条已完成订单，以及所有未完成订单 **/
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        String u_id;
        if (request.getAttribute("u_id") != null) {
            u_id = (String) request.getAttribute("u_id");
        } else {
            u_id = request.getParameter("u_id");
            if (u_id.contains("\"")) {
                u_id = u_id.split("\"")[1];
            }
        }
        List<Order> orderListFinished = orderService.findFiveFinished(u_id);
        List<Order> orderListUnfinished = orderService.findUnFinished(u_id);
        request.setAttribute("orderListFinished", orderListFinished);
        request.setAttribute("orderListUnfinished", orderListUnfinished);
        request.setAttribute("u_id", u_id);
        return "f:/user/pages/bill.jsp";
    }

    /** 用户确认订单完成 */
    public String confirmOrder(HttpServletRequest request, HttpServletResponse response) {
        String o_id = request.getParameter("o_id");
        String u_id = request.getParameter("u_id");
        orderService.confirmOrder(o_id);
        request.setAttribute("u_id", u_id);
        return findAll(request, response);
    }
}
