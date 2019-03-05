package cose.lexian.goods.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cose.lexian.goods.domain.Goods;
import cose.lexian.seller.domain.Seller;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.domain.Type;
import cose.lexian.util.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GoodsDao {
    private QueryRunner qr = new TxQueryRunner();

    /** 通过二级分类id查询该分类下商品数量 */
    public int getCountBySub_id(String sub_id) {
        try {
            String sql = "select count(*) from goods where g_sub_id=? and g_del=0";
            Number cnt = (Number) qr.query(sql, new ScalarHandler(), sub_id);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按商品名称搜索 */
    public List<Goods> findByName(String keyword) {
        try {
            String sql = "select * from goods g, seller s where g.g_name like ? and g.g_s_id=s.s_id and g.g_del=0";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), "%" + keyword + "%");
            List<Goods> goodsList = toGoodsList(mapList);
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按一级分类id查询所有商品 */
    public List<Goods> findGoodsByType(String t_id) {
        try {
            String sql = "select * from goods g, seller s, subtype sub where g.g_s_id=s.s_id and g.g_t_id=? and " +
                    "g.g_sub_id=sub.sub_id and g.g_del=0 and g.g_count>0";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), t_id);
            List<Goods> goodsList = toGoodsList(mapList);
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 把mapList中每个Map转换成多个对象，并建立关系 */
    private List<Goods> toGoodsList(List<Map<String, Object>> mapList) {
        List<Goods> goodsList = new ArrayList<Goods>();
        for(Map<String, Object> map : mapList) {
            Goods goods = toGoods(map);
            goodsList.add(goods);
        }
        return goodsList;
    }

    /** 把一个Map转换成一个Goods对象 */
    private Goods toGoods(Map<String, Object> map) {
        Goods goods = CommonUtils.toBean(map, Goods.class);
        Seller seller = CommonUtils.toBean(map, Seller.class);
        Type type = CommonUtils.toBean(map, Type.class);
        SubType subType = CommonUtils.toBean(map, SubType.class);
        goods.setG_seller(seller);
        goods.setG_type(type);
        goods.setG_subType(subType);
        return goods;
    }

    /** 加载商品详情 */
    public Goods load(String g_id) {
        try {
            String sql = "select * from goods g, seller s where g.g_s_id=s.s_id and g.g_id=?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), g_id);
            Goods goods = toGoods(map);
            return goods;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 收藏商品，更新likegoods表 */
    public void likeGoods(String u_id, String g_id) {
        try {
            String sql = "insert into likegoods (lg_u_id, lg_g_id) values(?,?)";
            qr.update(sql, u_id, g_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 收藏商品，更新goods表 */
    public void addGoodsLike(String g_id) {
        try {
            String sql = "update goods set g_likeCount=g_likeCount+1 where g_id=?";
            qr.update(sql, g_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 获取用户收藏的商品二级分类id */
    public List<String> findLikeGoodsSubTypeIdByUid(String u_id) {
        try {
            String sql = "select g_sub_id from likegoods lg, goods g, subtype sub where lg.lg_g_id=g.g_id and lg.lg_u_id=?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), u_id);
            List<String> stringList = new ArrayList<String>();
            for (Map<String, Object> map : mapList) {
                stringList.add((String) map.get("g_sub_id"));
            }
            return stringList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 返回需要数量的最热门商品 */
    public List<Goods> findMostLikeGoodsByNum(int len) {
        try {
            String sql = "select * from (select * from goods order by g_likeCount desc) t, seller s where " +
                    "t.g_s_id=s.s_id and t.g_del=0 limit 0, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), len);
            List<Goods> goodsList = toGoodsList(mapList);
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 通过二级分类id查询该分类下最热门商品，每个分类查询两个 */
    public List<Goods> findRecommendGoods(List<String> g_sub_idList) {
        List<Goods> goodsList = new ArrayList<Goods>();
        try {
            String sql = "select * from (select * from goods where g_sub_id=? order by g_likeCount desc) t, seller s" +
                    " where t.g_s_id=s.s_id and t.g_del=0 limit 0, 2";
            for (String g_sub_id : g_sub_idList) {
                List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), g_sub_id);
                List<Goods> g = toGoodsList(mapList);
                goodsList.addAll(g);
            }
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页，按商家和一级分类查询商品 */
    public PageBean<Goods> findGoodsBySellerAndType(String s_id, String t_id, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods where g_s_id=? and g_t_id=? and g_del=0";
            Number num = (Number) qr.query(sql, new ScalarHandler(), s_id, t_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, type t, subtype sub where g.g_s_id=? and g.g_t_id=? and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id and g_del=0 limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), s_id, t_id, (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 按商家和二级分类查询商品 */
    public PageBean<Goods> findGoodsBySellerAndSubType(String s_id, String sub_id, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods where g_s_id=? and g_sub_id=?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), s_id, sub_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, type t, subtype sub where g.g_s_id=? and g.g_sub_id=? and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), s_id, sub_id, (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 添加商品 */
    public void add(Goods goods) {
        try {
            String sql = "insert into goods (g_id, g_name, g_desc, g_count, g_price, g_discount, g_image, " +
                    "g_updateTime, g_t_id, g_sub_id, g_s_id) values (?,?,?,?,?,?,?,?,?,?,?)";
            qr.update(sql, goods.getG_id(), goods.getG_name(), goods.getG_desc(), goods.getG_count(), goods.getG_price(),
                    goods.getG_discount(), goods.getG_image(), new Timestamp(goods.getG_updateTime().getTime()),
                    goods.getG_type().getT_id(), goods.getG_subType().getSub_id(), goods.getG_seller().getS_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页查询所有商品 */
    public PageBean<Goods> findAllGoods(int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods";
            Number num = (Number) qr.query(sql, new ScalarHandler()); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, seller s, type t, subtype sub where g.g_s_id=s.s_id and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id order by g.g_likeCount desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页，商家查询所有商品 */
    public PageBean<Goods> findAllGoods(String s_id, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods where g_s_id=? and g_del=0";
            Number num = (Number) qr.query(sql, new ScalarHandler(), s_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, seller s, type t, subtype sub where g.g_s_id=s.s_id and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id and g_s_id=? and g_del=0 order by g.g_likeCount desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), s_id, (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 查询某商家所有商品 */
    public List<Goods> findAllGoods(String s_id) {
        try {
            String sql = "select * from goods g, seller s where g_s_id=? and g.g_s_id=s.s_id and g.g_del=0" +
                    " order by g_likeCount desc";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), s_id);
            List<Goods> goodsList = toGoodsList(mapList);
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 修改商品状态 */
    public void changeGoodsState(String g_id, boolean del) {
        try {
            String sql = "update goods set g_del=?, g_updateTime=? where g_id=?";
            qr.update(sql, del, new Timestamp(new Date().getTime()), g_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页按商品状态查询 */
    public PageBean<Goods> findGoodsByDel(boolean g_del, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods where g_del=?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), g_del); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, seller s, type t, subtype sub where g.g_s_id=s.s_id and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id and g.g_del=? order by g.g_updateTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), g_del, (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 通过商品id搜索商品 */
    public Goods findGoodsById(String g_id) {
        try {
            String sql = "select * from goods g, seller s, type t, subtype sub where g.g_s_id=s.s_id and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id and g.g_id=?";
            Map<String, Object> map = qr.query(sql, new MapHandler(), g_id);
            Goods goods = toGoods(map);
            return goods;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 通过一级分类id查询该分类下商品件数 */
    public int getCountByT_id(String t_id) {
        try {
            String sql = "select count(*) from goods where g_t_id=? and g_del=0";
            Number cnt = (Number) qr.query(sql, new ScalarHandler(), t_id);
            return cnt.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 分页按商品名称查询 */
    public PageBean<Goods> findGoodsByName(String g_name, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods where g_name like ?";
            Number num = (Number) qr.query(sql, new ScalarHandler(), "%" + g_name + "%"); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, seller s, type t, subtype sub where g.g_s_id=s.s_id and " +
                    "g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id and g.g_name like ? order by g.g_updateTime desc limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), "%" + g_name + "%",
                    (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 查询用户收藏的商品 */
    public List<Goods> findLikeGoods(String u_id) {
        try {
            String sql = "select * from likegoods lg, goods g, seller s where lg.lg_u_id=? and " +
                    "lg.lg_g_id=g.g_id and g.g_s_id=s.s_id";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), u_id);
            List<Goods> goodsList = toGoodsList(mapList);
            return goodsList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 商家搜索商品，按商品名称搜索(模糊查询) */
    public PageBean<Goods> findGoodsBySellerAndName(String s_id, String keyword, int pageCode, int pageSize) {
        try {
            /*
            1.创建PageBean对象
            2.设置pageBean的pageCode和pageSize
            3.得到totalRecord，设置给pageBean
            4.得到beanList，设置给pageBean
            5.返回pageBean
             */
            PageBean<Goods> pageBean = new PageBean<Goods>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            //得到totalRecord，并设置到pageBean
            String sql = "select count(*) from goods where g_name like ? and g_s_id=? and g_del=0";
            Number num = (Number) qr.query(sql, new ScalarHandler(), "%" + keyword + "%", s_id); //结果是单行单列
            int totalRecord = num.intValue();
            pageBean.setTotalRecord(totalRecord);
            //得到beanList
            sql = "select * from goods g, type t, subtype sub where g.g_name like ? and g.g_s_id=? " +
                    "and g.g_t_id=t.t_id and g.g_sub_id=sub.sub_id and g.g_del=0 limit ?, ?";
            List<Map<String, Object>> mapList = qr.query(sql, new MapListHandler(), "%" + keyword + "%", s_id,
                    (pageCode-1)*pageSize, pageSize);
            List<Goods> beanList = toGoodsList(mapList);
            pageBean.setBeanList(beanList);
            return pageBean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 商家修改图片信息 */
    public void modifyGoods(Goods goods) {
        try {
            Timestamp updateTime = new Timestamp(goods.getG_updateTime().getTime());
            String sql = "update goods set g_name=?, g_desc=?, g_count=?, g_price=?, g_discount=?," +
                    " g_image=?, g_updateTime=? where g_id=?";
            qr.update(sql, goods.getG_name(), goods.getG_desc(), goods.getG_count(), goods.getG_price(),
                    goods.getG_discount(), goods.getG_image(), updateTime, goods.getG_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 用户取消收藏商品 */
    public void removeLikeGoods(String u_id, String g_id) {
        try {
            String sql = "delete from likegoods where lg_u_id=? and lg_g_id=?";
            qr.update(sql, u_id, g_id);
            sql = "update goods set g_likeCount=g_likeCount-1 where g_id=?";
            qr.update(sql, g_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /** 用户取消收藏商家 */
    public void removeLikeSeller(String u_id, String s_id) {
        try {
            String sql = "delete from likeseller where ls_u_id=? and ls_s_id=?";
            qr.update(sql, u_id, s_id);
            sql = "update seller set s_likeCount=s_likeCount-1 where s_id=?";
            qr.update(sql, s_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
