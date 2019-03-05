package cose.lexian.user.service;

import cose.lexian.goods.dao.GoodsDao;
import cose.lexian.goods.domain.Goods;
import cose.lexian.seller.dao.SellerDao;
import cose.lexian.seller.domain.Seller;
import cose.lexian.user.dao.UserDao;
import cose.lexian.user.domain.User;
import cose.lexian.util.UserException;

import java.util.List;

public class UserService {
    private UserDao userDao = new UserDao();
    private SellerDao sellerDao = new SellerDao();
    private GoodsDao goodsDao = new GoodsDao();

    /**
     * 搜索店铺
     */
    public List<Seller> searchSeller(String keyword) {
        return sellerDao.findByStoreName(keyword);
    }

    /**
     * 搜索商品
     */
    public List<Goods> searchGoods(String keyword) {
        return goodsDao.findByName(keyword);
    }

    /**
     * 用户登录
     */
    public String login(User form) throws UserException {
        User user = userDao.login(form);
        if (user == null) {
            throw new UserException("用户名或密码错误！");
        } else {
            return user.getU_id();
        }
    }

    /**
     * 检测用户名是否已存在
     */
    public void checkUserName(String u_name) throws UserException {
        String u_id = userDao.findByName(u_name);
        if (u_id != null) {
            System.out.println(u_id);
            throw new UserException("用户名已存在！");
        }
    }

    public void regist(User user) throws UserException {
        checkUserName(user.getU_name());
        userDao.regist(user);
    }
}
