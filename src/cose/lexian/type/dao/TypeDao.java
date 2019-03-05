package cose.lexian.type.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.domain.Type;
import cose.lexian.util.TypeException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TypeDao {
    private QueryRunner qr = new TxQueryRunner();

    /** 查询所有一级分类 */
    public List<Type> findAllType() {
        try {
            String sql = "select * from type";
            return qr.query(sql, new BeanListHandler<Type>(Type.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 查询某一级分类下所有二级分类 */
    public List<SubType> findAllSubType(String t_id) {
        try {
            String sql = "select * from subtype where sub_t_id=?";
            return qr.query(sql, new BeanListHandler<SubType>(SubType.class), t_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按二级分类的id查询分类 */
    public SubType findSubTypeById(String sub_id) {
        try {
            String sql = "select * from subtype sub, type t where sub.sub_t_id=t.t_id and sub.sub_id=?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), sub_id);
            SubType subType = toSubType(map);
            return subType;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 修改二级分类 */
    public void modifySubType(SubType form) {
        try {
            String sql = "update subtype set sub_name=? where sub_id=?";
            qr.update(sql, form.getSub_name(), form.getSub_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 将map转换为一个SubType对象 */
    private SubType toSubType(Map<String, Object> map) {
        SubType subType = CommonUtils.toBean(map, SubType.class);
        Type type = CommonUtils.toBean(map, Type.class);
        subType.setSub_type(type);
        return subType;
    }

    /** 删除二级分类 */
    public void deleteSubType(String sub_id) {
        try {
            String sql = "delete from subtype where sub_id=?";
            qr.update(sql, sub_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按一级分类id加载 */
    public Type findTypeById(String t_id) {
        try {
            String sql = "select * from type where t_id=?";
            return qr.query(sql, new BeanHandler<Type>(Type.class), t_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加二级分类 */
    public void addSubType(SubType form) throws TypeException {
        try {
            String sql = "insert into subtype values (?,?,?)";
            qr.update(sql, form.getSub_id(), form.getSub_name(), form.getSub_type().getT_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按二级分类名称和它所属一级分类id查询 */
    public SubType findSubTypeBySubNameAndTypeId(String sub_name, String t_id) {
        try {
            String sql = "select * from subtype where sub_t_id=? and sub_name=?";
            return qr.query(sql, new BeanHandler<SubType>(SubType.class), t_id, sub_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加一级分类 */
    public void addType(Type form) {
        try {
            String sql = "insert into type values (?,?,?)";
            qr.update(sql, form.getT_id(), form.getT_name(), form.getT_image());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按一级分类名字查询 */
    public Type findTypeByName(String t_name) {
        try {
            String sql = "select * from type where t_name=?";
            return qr.query(sql, new BeanHandler<Type>(Type.class), t_name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 修改一级分类 */
    public void modifyType(Type form) {
        try {
            String sql = "update type set t_name=?, t_image=? where t_id=?";
            qr.update(sql, form.getT_name(), form.getT_image(), form.getT_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 删除一级分类 */
    public void deleteType(String t_id) {
        try {
            String sql = "delete from type where t_id=?";
            qr.update(sql, t_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
