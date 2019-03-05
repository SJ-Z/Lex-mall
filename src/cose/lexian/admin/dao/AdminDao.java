package cose.lexian.admin.dao;

import cn.itcast.jdbc.TxQueryRunner;
import cose.lexian.admin.domain.Admin;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class AdminDao {
    private QueryRunner qr = new TxQueryRunner();

    /** 按用户名查找 */
    public Admin findByUsername(String a_name) {
        try {
            String sql = "select * from admin where a_name=?";
            return qr.query(sql, new BeanHandler<Admin>(Admin.class), a_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
