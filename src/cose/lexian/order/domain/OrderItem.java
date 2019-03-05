package cose.lexian.order.domain;

import cose.lexian.goods.domain.Goods;

public class OrderItem {
    private String oi_id; //订单条目id
    private double oi_singlePrice; //购买时单价
    private int oi_count; //该商品数量
    private double oi_subTotal; //该订单条目总价
    private Order oi_order; //订单条目所属订单
    private Goods oi_goods; //订单条目对应的商品

    @Override
    public String toString() {
        return "OrderItem{" +
                "oi_id='" + oi_id + '\'' +
                ", oi_singlePrice=" + oi_singlePrice +
                ", oi_count=" + oi_count +
                ", oi_subTotal=" + oi_subTotal +
                ", oi_order=" + oi_order +
                ", oi_goods=" + oi_goods +
                '}';
    }

    public double getOi_singlePrice() {
        return oi_singlePrice;
    }

    public void setOi_singlePrice(double oi_singlePrice) {
        this.oi_singlePrice = oi_singlePrice;
    }

    public String getOi_id() {
        return oi_id;
    }

    public void setOi_id(String oi_id) {
        this.oi_id = oi_id;
    }

    public int getOi_count() {
        return oi_count;
    }

    public void setOi_count(int oi_count) {
        this.oi_count = oi_count;
    }

    public double getOi_subTotal() {
        return oi_subTotal;
    }

    public void setOi_subTotal(double oi_subTotal) {
        this.oi_subTotal = oi_subTotal;
    }

    public Order getOi_order() {
        return oi_order;
    }

    public void setOi_order(Order oi_order) {
        this.oi_order = oi_order;
    }

    public Goods getOi_goods() {
        return oi_goods;
    }

    public void setOi_goods(Goods oi_goods) {
        this.oi_goods = oi_goods;
    }
}
