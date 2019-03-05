package cose.lexian.order.domain;

import cose.lexian.seller.domain.Seller;
import cose.lexian.user.domain.User;

import java.util.Date;
import java.util.List;

public class Order {
    private String o_id; //订单id
    private Date o_orderTime; //订单时间
    private double o_totalPrice; //订单总价
    private int o_state; //订单状态
    private String o_code; //订单取货码
    private User o_user; //订单所属用户
    private Seller o_seller; //订单所属商家
    private List<OrderItem> o_orderItemList; //当前订单下所有条目

    @Override
    public String toString() {
        return "Order{" +
                "o_id='" + o_id + '\'' +
                ", o_orderTime=" + o_orderTime +
                ", o_totalPrice=" + o_totalPrice +
                ", o_state=" + o_state +
                ", o_code='" + o_code + '\'' +
                ", o_user=" + o_user +
                ", o_seller=" + o_seller +
                ", o_orderItemList=" + o_orderItemList +
                '}';
    }

    public List<OrderItem> getO_orderItemList() {
        return o_orderItemList;
    }

    public void setO_orderItemList(List<OrderItem> o_orderItemList) {
        this.o_orderItemList = o_orderItemList;
    }

    public String getO_code() {
        return o_code;
    }

    public void setO_code(String o_code) {
        this.o_code = o_code;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public Date getO_orderTime() {
        return o_orderTime;
    }

    public void setO_orderTime(Date o_orderTime) {
        this.o_orderTime = o_orderTime;
    }

    public double getO_totalPrice() {
        return o_totalPrice;
    }

    public void setO_totalPrice(double o_totalPrice) {
        this.o_totalPrice = o_totalPrice;
    }

    public int getO_state() {
        return o_state;
    }

    public void setO_state(int o_state) {
        this.o_state = o_state;
    }

    public User getO_user() {
        return o_user;
    }

    public void setO_user(User o_user) {
        this.o_user = o_user;
    }

    public Seller getO_seller() {
        return o_seller;
    }

    public void setO_seller(Seller o_seller) {
        this.o_seller = o_seller;
    }
}
