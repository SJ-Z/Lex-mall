package cose.lexian.order.service;

import cose.lexian.order.dao.OrderDao;
import cose.lexian.order.domain.Order;
import cose.lexian.seller.dao.SellerDao;
import cose.lexian.user.dao.UserDao;
import cose.lexian.util.PageBean;

import java.util.Date;
import java.util.List;

public class OrderService {
    OrderDao orderDao = new OrderDao();
    UserDao userDao = new UserDao();
    SellerDao sellerDao = new SellerDao();

    /** 分页，查询商家所有订单 */
    public PageBean<Order> findAll(String s_id, int pageCode, int pageSize) {
        return orderDao.findAll(s_id, pageCode, pageSize);
    }

    /** 分页查询所有订单 */
    public PageBean<Order> findAll(int pageCode, int pageSize) {
        return orderDao.findAll(pageCode, pageSize);
    }

    /** 用户查询最近五条已完成订单 **/
    public List<Order> findFiveFinished(String u_id) {
        return orderDao.findFiveFinished(u_id);
    }

    /** 用户查询所有未完成订单*/
    public List<Order> findUnFinished(String u_id) {
        return orderDao.findUnFinished(u_id);
    }

    /** 分页，商家，按订单状态查询订单 */
    public PageBean<Order> findByState(String s_id, String state, int pageCode, int pageSize) {
        return orderDao.findByState(s_id, state, pageCode, pageSize);
    }

    /** 分页，按订单状态查询订单 */
    public PageBean<Order> findByState(String state, int pageCode, int pageSize) {
        return orderDao.findByState(state, pageCode, pageSize);
    }

    /** 分页，商家按订单时间查询订单 */
    public PageBean<Order> findByOrderTime(String s_id, Date orderTime, int pageCode, int pageSize) {
        return orderDao.findByOrderTime(s_id, orderTime, pageCode, pageSize);
    }

    /** 分页，按订单时间查询订单 */
    public PageBean<Order> findByOrderTime(Date orderTime, int pageCode, int pageSize) {
        return orderDao.findByOrderTime(orderTime, pageCode, pageSize);
    }

    /** 加载订单 */
    public Order load(String o_id) {
        return orderDao.load(o_id);
    }

    /** 查看订单详情 */
    public Order viewDetail(String o_id) {
        Order order = orderDao.load(o_id);
        order.setO_user(userDao.findByUid(orderDao.getUid(o_id)));
        order.setO_seller(sellerDao.findBySid(orderDao.getSid(o_id)));
        return order;
    }

    /** 用户确认订单完成 */
    public void confirmOrder(String o_id) {
        orderDao.confirmOrder(o_id);
    }

    /** 添加订单 */
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    /** 按商家id和订单id查询 */
    public Order findByIdAndS_id(String o_id, String s_id) {
        return orderDao.findByIdAndS_id(o_id, s_id);
    }
}
