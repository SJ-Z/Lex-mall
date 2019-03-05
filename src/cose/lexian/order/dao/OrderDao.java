package cose.lexian.order.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cose.lexian.goods.domain.Goods;
import cose.lexian.order.domain.Order;
import cose.lexian.order.domain.OrderItem;
import cose.lexian.seller.domain.Seller;
import cose.lexian.user.domain.User;
import cose.lexian.util.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDao {
    QueryRunner qr = new TxQueryRunner();

    /** 分页，查询商家所有订单 */
    public PageBean<Order> findAll(String s_id, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Order> pageBean = new PageBean<Order>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from orders where o_s_id=?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), s_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from orders o, users u, seller s where o.o_u_id=u.u_id and o.o_s_id=s.s_id and o.o_s_id=?" +
                    "order by o_orderTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), s_id, (pageCode-1)*pageSize, pageSize);
            List<Order> beanList = toOrderList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页查询所有订单 */
    public PageBean<Order> findAll(int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Order> pageBean = new PageBean<Order>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from orders";
            Number num = (Number) qr.query(sql, new ScalarHandler()); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from orders o, users u, seller s where o.o_u_id=u.u_id and o.o_s_id=s.s_id " +
                    "order by o_orderTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), (pageCode-1)*pageSize, pageSize);
            List<Order> beanList = toOrderList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 用户查询最近五条已完成订单 **/
    public List<Order> findFiveFinished(String u_id) {
        try {
            List<Order> orderList;
            List<Map<String, Object>> mapList;
            String sql = "select count(*) from orders where o_u_id=? and o_state=2";
            Number cnt = (Number) qr.query(sql, new ScalarHandler(), u_id);
            if (cnt.intValue() < 5) {
                sql = "select * from orders o, seller s where o.o_u_id=? and o.o_s_id=s.s_id and o.o_state=2 " +
                        "order by o.o_orderTime desc";
                mapList = qr.query(sql, new MapListHandler(), u_id);
                orderList = toOrderList(mapList);
            } else {
                sql = "select * from orders o, seller s where o.o_u_id=? and o.o_s_id=s.s_id and o.o_state=2 " +
                        "order by o.o_orderTime desc limit 0, 5";
                mapList = qr.query(sql, new MapListHandler(), u_id);
                orderList = toOrderList(mapList);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 用户查询所有未完成订单*/
    public List<Order> findUnFinished(String u_id) {
        try {
            String sql = "select * from orders o, seller s where o.o_u_id=? and o.o_s_id=s.s_id and o.o_state=1 " +
                    "order by o.o_orderTime desc";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), u_id);
            List<Order> orderList = toOrderList(mapList);
            sql = "select * from orderitem oi, goods g where oi.oi_o_id=? and oi.oi_g_id=g.g_id";
            for (Order order : orderList) {
                String o_id = order.getO_id();
                mapList = qr.query(sql, new MapListHandler(), o_id);
                List<OrderItem> orderItemList = toOrderItemList(mapList);
                order.setO_orderItemList(orderItemList);
            }
            System.out.print(orderList);
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 把mapList中每个Map转换成多个对象，并建立关系 */
    private List<Order> toOrderList(List<Map<String, Object>> mapList) {
        List<Order> orderList = new ArrayList<Order>();
        for(Map<String, Object> map : mapList) {
            Order order = toOrder(map);
            orderList.add(order);
        }
        return orderList;
    }

    /** 把一个Map转换成一个Order对象 */
    private Order toOrder(Map<String, Object> map) {
        Order order = CommonUtils.toBean(map, Order.class);
        User user = CommonUtils.toBean(map, User.class);
        Seller seller = CommonUtils.toBean(map, Seller.class);
        order.setO_user(user);
        order.setO_seller(seller);
        return order;
    }

    /** 分页，商家，按订单状态查询订单 */
    public PageBean<Order> findByState(String s_id, String state, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Order> pageBean = new PageBean<Order>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from orders where o_state=? and o_s_id=?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), state, s_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from orders o, users u, seller s where o.o_u_id=u.u_id and o.o_s_id=s.s_id and " +
                    "o.o_state=? and o.o_s_id=? order by o_orderTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), state, s_id, (pageCode-1)*pageSize, pageSize);
            List<Order> beanList = toOrderList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页，按订单状态查询订单 */
    public PageBean<Order> findByState(String state, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Order> pageBean = new PageBean<Order>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from orders where o_state=?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), state); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from orders o, users u, seller s where o.o_u_id=u.u_id and o.o_s_id=s.s_id and " +
                    "o.o_state=? order by o_orderTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), state, (pageCode-1)*pageSize, pageSize);
            List<Order> beanList = toOrderList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页，商家按订单时间查询订单 */
    public PageBean<Order> findByOrderTime(String s_id, Date orderTime, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            Timestamp o_orderTime = new Timestamp(orderTime.getTime());
            PageBean<Order> pageBean = new PageBean<Order>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from orders where day(o_orderTime)=day(?) and o_s_id=?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), o_orderTime.toString().split(" ")[0], s_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from orders o, users u, seller s where day(o.o_orderTime)=day(?) and o.o_u_id=u.u_id " +
                    "and o.o_s_id=s.s_id and o.o_s_id=? order by o_orderTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), o_orderTime.toString().split(" ")[0], s_id, (pageCode-1)*pageSize, pageSize);
            List<Order> beanList = toOrderList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页，按订单时间查询订单 */
    public PageBean<Order> findByOrderTime(Date orderTime, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            Timestamp o_orderTime = new Timestamp(orderTime.getTime());
            PageBean<Order> pageBean = new PageBean<Order>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from orders where day(o_orderTime)=day(?)";
            Number num = (Number) qr.query(sql, new ScalarHandler(), o_orderTime.toString().split(" ")[0]); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from orders o, users u, seller s where day(o.o_orderTime)=day(?) and o.o_u_id=u.u_id " +
                    "and o.o_s_id=s.s_id order by o_orderTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), o_orderTime.toString().split(" ")[0],
                    (pageCode-1)*pageSize, pageSize);
            List<Order> beanList = toOrderList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按订单id加载订单 */
    public Order load(String o_id) {
        try {
            String sql = "select * from orders where o_id=?";
            Order order = qr.query(sql, new BeanHandler<Order>(Order.class), o_id);
            loadOrderItems(order); /** 为order对象加载它的订单条目 */

            /** 返回订单列表 */
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 加载指定的订单所有的订单条目 */
    private void loadOrderItems(Order order) {
        try {
            /** 查询两张表：orderitem、goods */
            String sql = "select * from orderitem i, goods g where i.oi_g_id=g.g_id and i.oi_o_id=?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), order.getO_id());
            /** mapList是多个map，每个map对应一行结果 */
            /** 循环遍历每个Map，使用map生成两个对象，然后建立关系（最终结果一个OrderItem） */
            List<OrderItem> orderItemList = toOrderItemList(mapList);
            order.setO_orderItemList(orderItemList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 把mapList中每个Map转换成两个对象，并建立关系 */
    private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (Map<String, Object> map : mapList) {
            OrderItem item = toOrderItem(map);
            orderItemList.add(item);
        }
        return orderItemList;
    }

    /** 把一个Map转换成一个OrderItem对象 */
    private OrderItem toOrderItem(Map<String, Object> map) {
        OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
        Goods goods = CommonUtils.toBean(map, Goods.class);
        orderItem.setOi_goods(goods);
        return orderItem;
    }

    /** 通过订单id获取用户id */
    public String getUid(String o_id) {
        try {
            String sql = "select o_u_id from orders where o_id=?";
            return (String) qr.query(sql, new ScalarHandler(), o_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 通过订单id获取商家id */
    public String getSid(String o_id) {
        try {
            String sql = "select o_s_id from orders where o_id=?";
            return (String) qr.query(sql, new ScalarHandler(), o_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加订单 */
    public void addOrder(Order order) {
        try {
            double totalPrice = 0.0;
            String sql = "insert into orders values(?,?,?,?,?,?,?)";
            Timestamp orderTime = new Timestamp(order.getO_orderTime().getTime());
            qr.update(sql, order.getO_id(), orderTime, 0.0, order.getO_state(), order.getO_code(),
                    order.getO_user().getU_id(), order.getO_seller().getS_id());

            String str = "select * from goods where g_id=?";
            sql = "insert into orderitem values(?,?,?,?,?,?)";
            for (OrderItem orderItem : order.getO_orderItemList()) {
                Goods goods = qr.query(str, new BeanHandler<Goods>(Goods.class), orderItem.getOi_goods().getG_id());
                if (goods.getG_discount() == null || goods.getG_discount() == 0.0) {
                    orderItem.setOi_singlePrice(orderItem.getOi_count() * goods.getG_price());
                } else {
                    orderItem.setOi_singlePrice(orderItem.getOi_count() * goods.getG_discount());
                }
                qr.update(sql, orderItem.getOi_id(), orderItem.getOi_singlePrice(), orderItem.getOi_count(),
                        orderItem.getOi_count() * orderItem.getOi_singlePrice(), orderItem.getOi_order().getO_id(),
                        orderItem.getOi_goods().getG_id());
                totalPrice += orderItem.getOi_count() * orderItem.getOi_singlePrice();
            }

            sql = "update orders set o_totalPrice=? where o_id=?";
            qr.update(sql, totalPrice, order.getO_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 用户确认订单完成 */
    public void confirmOrder(String o_id) {
        try {
            String sql = "update orders set o_state=2 where o_id=?";
            qr.update(sql, o_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按商家id和订单id查询 */
    public Order findByIdAndS_id(String o_id, String s_id) {
        try {
            String sql = "select * from orders o, users u where o.o_id=? and o.o_s_id=? and o.o_u_id=u.u_id";
            Map<String, Object> map = qr.query(sql, new MapHandler(), o_id, s_id);
            Order order = toOrder(map);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
