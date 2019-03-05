package cose.lexian.goods.domain;

import cose.lexian.seller.domain.Seller;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.domain.Type;

import java.util.Date;

public class Goods {
    private String g_id; //商品id
    private String g_name; //商品名称
    private String g_desc; //商品描述
    private int g_count; //商品库存量
    private Double g_price; //商品价格
    private Double g_discount; //促销价格
    private String g_image; //商品图片（地址）
    private Date g_updateTime; //最后修改时间
    private Type g_type; //商品所属主类别
    private SubType g_subType; //商品所属详细类别
    private Seller g_seller; //商品所属商家
    private boolean g_del; //商品是否已下架
    private int g_likeCount; //商品收藏量

    @Override
    public String toString() {
        return "Goods{" +
                "g_id='" + g_id + '\'' +
                ", g_name='" + g_name + '\'' +
                ", g_desc='" + g_desc + '\'' +
                ", g_count='" + g_count + '\'' +
                ", g_price='" + g_price + '\'' +
                ", g_discount='" + g_discount + '\'' +
                ", g_image='" + g_image + '\'' +
                ", g_updateTime=" + g_updateTime +
                ", g_type=" + g_type +
                ", g_subType=" + g_subType +
                ", g_seller=" + g_seller +
                ", g_del=" + g_del +
                ", g_likeCount=" + g_likeCount +
                '}';
    }

    public int getG_count() {
        return g_count;
    }

    public void setG_count(int g_count) {
        this.g_count = g_count;
    }

    public int getG_likeCount() {
        return g_likeCount;
    }

    public void setG_likeCount(int g_likeCount) {
        this.g_likeCount = g_likeCount;
    }

    public String getG_id() {
        return g_id;
    }

    public void setG_id(String g_id) {
        this.g_id = g_id;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    public String getG_desc() {
        return g_desc;
    }

    public void setG_desc(String g_desc) {
        this.g_desc = g_desc;
    }

    public Double getG_discount() {
        return g_discount;
    }

    public void setG_discount(Double g_discount) {
        this.g_discount = g_discount;
    }

    public Double getG_price() {
        return g_price;
    }

    public void setG_price(Double g_price) {
        this.g_price = g_price;
    }

    public String getG_image() {
        return g_image;
    }

    public void setG_image(String g_image) {
        this.g_image = g_image;
    }

    public Date getG_updateTime() {
        return g_updateTime;
    }

    public void setG_updateTime(Date g_updateTime) {
        this.g_updateTime = g_updateTime;
    }


    public Type getG_type() {
        return g_type;
    }

    public void setG_type(Type g_type) {
        this.g_type = g_type;
    }

    public SubType getG_subType() {
        return g_subType;
    }

    public void setG_subType(SubType g_subType) {
        this.g_subType = g_subType;
    }

    public Seller getG_seller() {
        return g_seller;
    }

    public void setG_seller(Seller g_seller) {
        this.g_seller = g_seller;
    }

    public boolean isG_del() {
        return g_del;
    }

    public void setG_del(boolean g_del) {
        this.g_del = g_del;
    }
}
