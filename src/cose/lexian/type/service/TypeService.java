package cose.lexian.type.service;

import cose.lexian.goods.dao.GoodsDao;
import cose.lexian.type.dao.TypeDao;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.domain.Type;
import cose.lexian.util.TypeException;

import java.util.List;

public class TypeService {
    private TypeDao typeDao = new TypeDao();
    private GoodsDao goodsDao = new GoodsDao();

    /** 查询所有一级分类 */
    public List<Type> findAllType() {
        return typeDao.findAllType();
    }

    /** 查询某一级分类下所有二级分类 */
    public List<SubType> findAllSubType(String t_id) {
        return typeDao.findAllSubType(t_id);
    }

    /** 修改二级分类 */
    public void modifySubType(SubType form) {
        typeDao.modifySubType(form);
    }

    /** 按二级分类id加载 */
    public SubType findSubTypeById(String sub_id) {
        return typeDao.findSubTypeById(sub_id);
    }

    /** 删除二级分类 */
    public void deleteSubType(String sub_id) throws TypeException {
        /** 获取该分类下商品的件数 */
        int count = goodsDao.getCountBySub_id(sub_id);
        if(count > 0) {
            throw new TypeException("该分类下还有商品，不能删除！");
        }
        typeDao.deleteSubType(sub_id);
    }

    /** 按一级分类id加载 */
    public Type findTypeById(String t_id) {
        return typeDao.findTypeById(t_id);
    }

    /** 添加二级分类 */
    public void addSubType(SubType form) throws TypeException {
        SubType subType = typeDao.findSubTypeBySubNameAndTypeId(form.getSub_name(), form.getSub_type().getT_id());
        if(subType != null) {
            throw new TypeException("该二级分类已存在，不可重复创建！");
        }
        typeDao.addSubType(form);
    }

    /** 添加一级分类 */
    public void addType(Type form) throws TypeException {
        Type type = typeDao.findTypeById(form.getT_id());
        if(type != null) {
            throw new TypeException("该一级分类已存在，不可重复创建！");
        }
        typeDao.addType(form);
    }

    /** 按一级分类名字查询 */
    public Type findTypeByName(String t_name) {
        return typeDao.findTypeByName(t_name);
    }

    /** 修改一级分类 */
    public void modifyType(Type form) {
        typeDao.modifyType(form);
    }

    /** 删除一级分类 */
    public void deleteType(String t_id) throws TypeException {
        /** 获取该分类下商品的件数 */
        int count = goodsDao.getCountByT_id(t_id);
        if(count > 0) {
            throw new TypeException("该分类下还有商品，不能删除！");
        }
        /** 删除一级分类和它的二级分类 */
        List<SubType> subTypeList = typeDao.findAllSubType(t_id);
        for (SubType subType : subTypeList) {
            typeDao.deleteSubType(subType.getSub_id());
        }
        typeDao.deleteType(t_id);
    }
}
