package cose.lexian.user.dao;

import cn.itcast.jdbc.TxQueryRunner;
import cose.lexian.user.domain.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    /** 通过用户id查询用户 */
    public User findByUid(String u_id) {
        try {
            String sql = "select * from users where u_id=?";
            return qr.query(sql, new BeanHandler<User>(User.class), u_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 用户登录 */
    public User login(User form) {
        try {
            String sql = "select * from users where u_name=? and u_pwd=?";
            User user = qr.query(sql, new BeanHandler<User>(User.class), form.getU_name(), form.getU_pwd());
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 通过用户名查询用户id */
    public String findByName(String u_name) {
        try {
            String sql = "select u_id from users where u_name=?";
            return qr.query(sql, new BeanHandler<String>(String.class), u_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void regist(User user) {
        try {
            String sql = "insert into users values (?,?,?)";
            qr.update(sql, user.getU_id(), user.getU_name(), user.getU_pwd());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
