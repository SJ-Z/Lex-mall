package cose.lexian.admin.test;

import cn.itcast.commons.CommonUtils;
import cose.lexian.goods.dao.GoodsDao;
import cose.lexian.goods.domain.Goods;
import cose.lexian.order.dao.OrderDao;
import cose.lexian.order.domain.Order;
import cose.lexian.seller.domain.Seller;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.domain.Type;
import cose.lexian.user.domain.User;
import org.junit.Test;

import java.util.Date;

public class AdminTest {
    @Test
    public void addOrder() {
        long start=System.currentTimeMillis();   //获取开始时间
        OrderDao orderDao = new OrderDao();
        for(int i = 1; i <= 300; i++) {
            Order order = new Order();
            order.setO_id(CommonUtils.uuid());
            order.setO_orderTime(new Date());
            order.setO_totalPrice(100.11);
            order.setO_state(i%2==0?1:2);
            order.setO_code(order.getO_id().substring(0, 6));
            User user = new User();
            user.setU_id("11111111111111111111111111111111");
            order.setO_user(user);
            Seller seller = new Seller();
            seller.setS_id("25F4EEE1B3154764B443D763EC5EF3C5");
            order.setO_seller(seller);

            orderDao.addOrder(order);
        }

        long end=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(end-start)+"ms");
    }

    @Test
    public void addGoods() {
        long start=System.currentTimeMillis();   //获取开始时间
        GoodsDao goodsDao = new GoodsDao();
        for(int i = 1; i <= 300; i++) {
            Goods goods = new Goods();
            goods.setG_id(CommonUtils.uuid());
            goods.setG_name("测试商品" + i);
            goods.setG_desc("测试商品的描述" + i);
            goods.setG_count(i);
            goods.setG_price(22.22);
            goods.setG_image("/goods_img/B775FFBC5CB944499187E27B6D70F39B.png");
            goods.setG_updateTime(new Date());
            Seller seller = new Seller();
            seller.setS_id("25F4EEE1B3154764B443D763EC5EF3C5");
            goods.setG_seller(seller);
            Type type = new Type();
            type.setT_id("55555555555555555555555555555555");
            goods.setG_type(type);
            SubType subType = new SubType();
            subType.setSub_id("44444444444444444444444444444444");
            goods.setG_subType(subType);

            goodsDao.add(goods);
        }

        long end=System.currentTimeMillis(); //获取结束时间
        System.out.println("程序运行时间： "+(end-start)+"ms");
    }
}
