package cose.lexian.order.web.servlet.seller;

import cn.itcast.servlet.BaseServlet;
import cose.lexian.order.domain.Order;
import cose.lexian.order.service.OrderService;
import cose.lexian.util.OrderException;
import cose.lexian.util.PageBean;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "SellerOrderServlet", urlPatterns = "/seller/SellerOrderServlet")
public class SellerOrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    /**
     * 分页，商家查询所有订单
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */
        String s_id = request.getParameter("s_id");

        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 15; //给定pageSize的值，每页15行记录

        PageBean<Order> pageBean = orderService.findAll(s_id, pageCode, pageSize);
        request.setAttribute("pageBean", pageBean);

        if (pageBean.getBeanList().size() == 0) {
            request.setAttribute("msg", "没有找到该订单信息！");
        }

        //设置url
        pageBean.setUrl(getUrl(request));

        return "f:/sellerjsps/seller/order/list.jsp";
    }

    /**
     * 分页，按订单状态查询订单
     */
    public String findByState(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */
        String s_id = request.getParameter("s_id");

        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 15; //给定pageSize的值，每页15行记录

        String state = request.getParameter("state");
        PageBean<Order> pageBean = orderService.findByState(s_id, state, pageCode, pageSize);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("state", request.getParameter("state"));

        if (pageBean.getBeanList().size() == 0) {
            request.setAttribute("msg", "没有找到该订单信息！");
        }
        //设置url
        pageBean.setUrl(getUrl(request));

        return "f:/sellerjsps/seller/order/list.jsp";
    }

    /**
     * 分页，商家，按订单时间查询订单
     */
    public String findByOrderTime(HttpServletRequest request, HttpServletResponse response) {
        String orderTime = request.getParameter("orderTime");
        try {
            if (orderTime.trim() == "" || orderTime == null) {
                throw new OrderException("没有找到该订单！");
            }
        } catch(OrderException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/sellerjsps/seller/order/list.jsp";
        }
        try {
            /*
            1.获取页面传递的pageCode
            2.给定pageSize的值
            3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
            4.转发
             */
            String s_id = request.getParameter("s_id");

            //1.得到pageCode
            int pageCode = getPageCode(request);
            int pageSize = 15; //给定pageSize的值，每页15行记录

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date o_orderTime = sdf.parse(orderTime);
            PageBean<Order> pageBean = orderService.findByOrderTime(s_id, o_orderTime, pageCode, pageSize);
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("s_id", s_id);
            request.setAttribute("orderTime", orderTime);

            if (pageBean.getBeanList().size() == 0) {
                request.setAttribute("msg", "没有找到该订单信息！");
            }
            //设置url
            pageBean.setUrl(getOrderTimeUrl(request));

            return "f:/sellerjsps/seller/order/list.jsp";
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 商家，按订单id查询订单
     */
    public String findByOrderId(HttpServletRequest request, HttpServletResponse response) {
        String s_id = request.getParameter("s_id");
        String o_id = request.getParameter("o_id").trim();
        Order order = orderService.findByIdAndS_id(o_id, s_id);

        if (order.getO_id() == null) {
            request.setAttribute("msg", "出错了！没有找到该订单的信息！");
            request.setAttribute("o_id", o_id);
        }

        request.setAttribute("order", order);
        return "f:/sellerjsps/seller/order/list.jsp";
    }

    /**
     * 查看订单详情
     */
    public String viewDetail(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("order", orderService.viewDetail(request.getParameter("o_id")));
        return "f:/sellerjsps/seller/order/detail.jsp";
    }

    //获取pageCode
    private int getPageCode(HttpServletRequest request) {
        //得到pageCode：如果pageCode参数不存在，说明pageCode=1；如果pageCode参数存在，需要转换成int类型
        String value;
        try {
            value = request.getParameter("pageCode");
        } catch (Exception e) {
            value = null;
        }
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }

    //截取url :/项目名/Servlet路径?参数字符串
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString(); //获取?之后的参数部分

        //判断参数部分中是否包含pc这个参数，如果包含，需要截取掉，不要这一部分
        if (queryString.contains("&pageCode=")) {
            int index = queryString.lastIndexOf("&pageCode=");
            queryString = queryString.substring(0, index);
        }

        return contextPath + servletPath + "?" + queryString;
    }

    /** 专为按时间查询订单设计的截取url函数 */
    private String getOrderTimeUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString(); //获取?之后的参数部分

        //判断参数部分中是否包含pc这个参数，如果包含，需要截取掉，不要这一部分
        if (queryString.contains("&pageCode=")) {
            int index = queryString.lastIndexOf("&pageCode=");
            queryString = queryString.substring(0, index);
        }
        String paramString = "&s_id=" + request.getAttribute("s_id") + "&orderTime=" + request.getAttribute("orderTime");

        return contextPath + servletPath + "?" + queryString + paramString;
    }
}
