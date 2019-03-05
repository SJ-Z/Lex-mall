package cose.lexian.goods.service;

import cose.lexian.goods.dao.GoodsDao;
import cose.lexian.goods.domain.Goods;
import cose.lexian.util.PageBean;

import java.util.*;

public class GoodsService {
    private GoodsDao goodsDao = new GoodsDao();

    /** 按一级分类id查询所有商品 */
    public List<Goods> findGoodsByType(String t_id) {
        return goodsDao.findGoodsByType(t_id);
    }

    /** 收藏商品 */
    public void addLikeGoods(String u_id, String g_id) {
        goodsDao.likeGoods(u_id, g_id); //更新likegoods表
        goodsDao.addGoodsLike(g_id); //更新goods表
    }

    /** 猜你喜欢，返回十条商品记录 */
    public List<Goods> guessLikeGoods(String u_id) {
        List<Goods> mostHotGoodsList = goodsDao.findMostLikeGoodsByNum(10); //当下10条最热门商品
        List<Goods> goodsList = new ArrayList<Goods>(); //存放给用户推荐的商品
        List<String> g_sub_idList = goodsDao.findLikeGoodsSubTypeIdByUid(u_id); //获取到该用户收藏的商品二级分类id

        //去除该用户收藏的商品二级分类id中重复的
        Map<String, String> g_sub_idMap = new HashMap<String, String>();
        for (String sub_id : g_sub_idList) {
            g_sub_idMap.put(sub_id, sub_id);
        }
        g_sub_idList = new ArrayList<String>();
        Set<String> subSet = g_sub_idMap.keySet();
        Iterator<String> iterator = subSet.iterator();
        while (iterator.hasNext()) {
            g_sub_idList.add(iterator.next());
        }

        if(g_sub_idList.size() == 0) { //用户有收藏过商品
            goodsList = goodsDao.findRecommendGoods(g_sub_idList); //通过二级分类id查询该分类下最热门的商品
            if(goodsList.size() > 10) {
                return goodsList.subList(0, 10); //最多只返回10条最热门商品
            } else {
                int len = 10 - goodsList.size(); //需要补充的热门商品数量
                Map<String, Goods> goodsMap = new HashMap<String, Goods>(); //将当下10条最热门商品转为Map
                for (Goods goods : mostHotGoodsList) {
                    goodsMap.put(goods.getG_id(), goods);
                }
                for (Goods goods : goodsList) { //去除当下10条最热门商品中和给用户推荐的商品中重复的商品
                    if (goodsMap.containsKey(goods.getG_id())) {
                        goodsMap.put(goods.getG_id(), null);
                    }
                }
                //将给用户推荐的商品补足10条
                Set<String> keySet = goodsMap.keySet();
                iterator = keySet.iterator();
                for (int i=0; i<len; i++) {
                    while (true) {
                        String key = iterator.next();
                        if (goodsMap.get(key) != null) {
                            goodsList.add(goodsMap.get(key));
                            break;
                        }
                    }
                }

                return goodsList;
            }
        } else { //用户未收藏过商品，直接返回当下最热门商品
            return mostHotGoodsList;
        }
    }

    /** 分页，按商家和一级分类查询商品 */
    public PageBean<Goods> findGoodsBySellerAndType(String s_id, String t_id, int pageCode, int pageSize) {
        return goodsDao.findGoodsBySellerAndType(s_id, t_id, pageCode, pageSize);
    }

    /** 分页，按商家和二级分类查询商品 */
    public PageBean<Goods> findGoodsBySellerAndSubType(String s_id, String sub_id, int pageCode, int pageSize) {
        return goodsDao.findGoodsBySellerAndSubType(s_id, sub_id, pageCode, pageSize);
    }

    /** 添加商品 */
    public void add(Goods goods) {
        goodsDao.add(goods);
    }

    /** 分页查询所有商品 */
    public PageBean<Goods> findAllGoods(int pageCode, int pageSize) {
        return goodsDao.findAllGoods(pageCode, pageSize);
    }

    /** 分页，商家查询所有商品 */
    public PageBean<Goods> findAllGoods(String s_id, int pageCode, int pageSize) {
        return goodsDao.findAllGoods(s_id, pageCode, pageSize);
    }

    /** 查询某商家所有商品 */
    public List<Goods> findAllGoods(String s_id) {
        return goodsDao.findAllGoods(s_id);
    }

    /** 下架商品 */
    public void outGoods(String g_id) {
        goodsDao.changeGoodsState(g_id, true);
    }

    /** 分页按商品状态查询 */
    public PageBean<Goods> findGoodsByDel(boolean g_del, int pageCode, int pageSize) {
        return goodsDao.findGoodsByDel(g_del, pageCode, pageSize);
    }

    /** 通过商品id搜索商品 */
    public Goods findGoodsById(String g_id) {
        return goodsDao.findGoodsById(g_id);
    }

    /** 分页按商品名称查询 */
    public PageBean<Goods> findGoodsByName(String g_name, int pageCode, int pageSize) {
        return goodsDao.findGoodsByName(g_name, pageCode, pageSize);
    }

    /** 查询用户收藏的商品 */
    public List<Goods> findLikeGoods(String u_id) {
        return goodsDao.findLikeGoods(u_id);
    }

    /** 商家搜索商品，按商品名称搜索(模糊查询) */
    public PageBean<Goods> findGoodsBySellerAndName(String s_id, String keyword, int pageCode, int pageSize) {
        return goodsDao.findGoodsBySellerAndName(s_id, keyword, pageCode, pageSize);
    }

    /** 商家修改商品前的准备工作，加载商品信息 */
    public Goods modifyGoodsPre(String g_id) {
        return goodsDao.load(g_id);
    }

    /** 商家修改图片信息 */
    public void modifyGoods(Goods goods) {
        goodsDao.modifyGoods(goods);
    }

    /** 用户取消收藏商品 */
    public void removeLikeGoods(String u_id, String g_id) {
        goodsDao.removeLikeGoods(u_id, g_id);
    }

    /** 用户取消收藏商家 */
    public void removeLikeSeller(String u_id, String s_id) {
        goodsDao.removeLikeSeller(u_id, s_id);
    }

}
