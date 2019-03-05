package cose.lexian.seller.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cose.lexian.seller.domain.Seller;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SellerDao {
    QueryRunner qr = new TxQueryRunner();

    /**
     * 按用户名查询商家
     */
    public Seller findByUsername(String s_name) {
        try {
            String sql = "select * from seller where s_name=?";
            return qr.query(sql, new BeanHandler<Seller>(Seller.class), s_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按邮箱查询商家
     */
    public Seller findByEmail(String s_email) {
        try {
            String sql = "select * from seller where s_email=?";
            return qr.query(sql, new BeanHandler<Seller>(Seller.class), s_email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册商家信息
     */
    public void add(Seller form) {
        try {
            String sql = "insert into seller (s_id, s_name, s_pwd, s_email, s_code, s_storeName, s_phone, s_addr) values " +
                    "(?,?,?,?,?,?,?,?)";
            qr.update(sql, form.getS_id(), form.getS_name(), form.getS_pwd(),form.getS_email(), form.getS_code(),
                    form.getS_storeName(), form.getS_phone(), form.getS_addr());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按激活码查询商家
     */
    public Seller findByCode(String s_code) {
        try {
            String sql = "select * from seller where s_code=?";
            return qr.query(sql, new BeanHandler<Seller>(Seller.class), s_code);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新指定商家状态
     */
    public void updateState(String s_id, int s_state) {
        try {
            String sql = "update seller set s_state=? where s_id=?";
            qr.update(sql, s_state, s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按sid查询商家
     */
    public Seller findBySid(String s_id) {
        try {
            String sql = "select * from seller where s_id=?";
            return qr.query(sql, new BeanHandler<Seller>(Seller.class), s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置门店信息
     */
    public void modifyInfo(Seller form) {
        String sql = "update seller set s_storeName=?, s_phone=?, s_addr=?, s_openTime=?, s_closeTime=? where s_id=?";
        try {
            Time openTime = new Time(form.getS_openTime().getTime());
            Time closeTime = new Time(form.getS_closeTime().getTime());
            qr.update(sql, form.getS_storeName(), form.getS_phone(), form.getS_addr(),
                    openTime, closeTime, form.getS_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按门店名搜索 */
    public List<Seller> findByStoreName(String keyword) {
        try {
            String sql = "select * from seller where s_storeName like ?";
            return qr.query(sql, new BeanListHandler<Seller>(Seller.class), "%" + keyword + "%");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按商家状态查询 */
    public List<Seller> findByState(int i) {
        try {
            String sql = "select * from seller where s_state=?";
            return qr.query(sql, new BeanListHandler<Seller>(Seller.class), i);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 修改商家状态 */
    public void setSellerState(String s_id, int s_state) {
        try {
            String sql = "update seller set s_state=? where s_id=?";
            qr.update(sql, s_state, s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 收藏商品，更新likeseller表 */
    public void likeSeller(String u_id, String s_id) {
        try {
            String sql = "insert into likeseller (ls_u_id, ls_s_id) values(?,?)";
            qr.update(sql, u_id, s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 收藏商品，更新seller表 */
    public void addSellerLike(String s_id) {
        try {
            String sql = "update seller set s_likeCount=s_likeCount+1 where s_id=?";
            qr.update(sql, s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 重设密码 */
    public void resetPwd(String s_id, String s_pwd) {
        try {
            String sql = "update seller set s_pwd=? where s_id=?";
            qr.update(sql, s_pwd, s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 查询用户收藏的商家 */
    public List<Seller> findLikeSeller(String u_id) {
        try {
            String sql = "select * from likeseller l, seller s where l.ls_s_id=s.s_id and l.ls_u_id=?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), u_id);
            List<Seller> sellerList = toSellerList(mapList);
            return sellerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Seller> toSellerList(List<Map<String, Object>> mapList) {
        List<Seller> sellerList = new ArrayList<Seller>();
        for(Map<String, Object> map : mapList) {
            Seller seller = CommonUtils.toBean(map, Seller.class);
            sellerList.add(seller);
        }
        return sellerList;
    }
}
