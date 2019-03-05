package cose.lexian.seller.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Seller {
    private String s_id; //商家id
    private String s_name; //用户名
    private String s_pwd; //密码
    private String s_email; //邮箱
    private String s_code; //邮箱激活码
    private String s_storeName; //门店名
    private String s_phone; //门店电话
    private String s_addr; //门店地址
    private Date s_openTime; //营业开始时间
    private Date s_closeTime; //营业结束时间
    private int s_state; //账号状态，是否激活
    private String s_verifyCode; //验证码
    private int s_likeCount; //商家收藏量

    public Seller() {
    }

    public Seller(String s_name, String s_pwd) {
        this.s_name = s_name;
        this.s_pwd = s_pwd;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "s_id='" + s_id + '\'' +
                ", s_name='" + s_name + '\'' +
                ", s_pwd='" + s_pwd + '\'' +
                ", s_email='" + s_email + '\'' +
                ", s_code='" + s_code + '\'' +
                ", s_storeName='" + s_storeName + '\'' +
                ", s_phone='" + s_phone + '\'' +
                ", s_addr='" + s_addr + '\'' +
                ", s_openTime=" + s_openTime +
                ", s_closeTime=" + s_closeTime +
                ", s_state=" + s_state +
                ", s_verifyCode='" + s_verifyCode + '\'' +
                ", s_likeCount=" + s_likeCount +
                '}';
    }

    public int getS_likeCount() {
        return s_likeCount;
    }

    public void setS_likeCount(int s_likeCount) {
        this.s_likeCount = s_likeCount;
    }

    public String getS_storeName() {
        return s_storeName;
    }

    public void setS_storeName(String s_storeName) {
        this.s_storeName = s_storeName;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getS_pwd() {
        return s_pwd;
    }

    public void setS_pwd(String s_pwd) {
        this.s_pwd = s_pwd;
    }

    public String getS_email() {
        return s_email;
    }

    public void setS_email(String s_email) {
        this.s_email = s_email;
    }

    public String getS_code() {
        return s_code;
    }

    public void setS_code(String s_code) {
        this.s_code = s_code;
    }

    public String getS_phone() {
        return s_phone;
    }

    public void setS_phone(String s_phone) {
        this.s_phone = s_phone;
    }

    public String getS_addr() {
        return s_addr;
    }

    public void setS_addr(String s_addr) {
        this.s_addr = s_addr;
    }

    public Date getS_openTime() {
        return s_openTime;
    }

    public void setS_openTime(Date s_openTime) {
        this.s_openTime = s_openTime;
    }

    public void setS_openTime(int num) {
        if (num==0) {
            this.s_openTime = null;
        }
    }

    public void setS_closeTime(int num) {
        if (num==0) {
            this.s_closeTime = null;
        }
    }

    public void setS_openTime(String s_openTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            setS_openTime(df.parse(s_openTime + ":00"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Date getS_closeTime() {
        return s_closeTime;
    }

    public void setS_closeTime(Date s_closeTime) {
        this.s_closeTime = s_closeTime;
    }

    public void setS_closeTime(String s_closeTime) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        try {
            setS_closeTime(df.parse(s_closeTime + ":00"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public int getS_state() {
        return s_state;
    }

    public void setS_state(int s_state) {
        this.s_state = s_state;
    }

    public String getS_verifyCode() {
        return s_verifyCode;
    }

    public void setS_verifyCode(String s_verifyCode) {
        this.s_verifyCode = s_verifyCode;
    }
}