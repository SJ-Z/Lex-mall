package cose.lexian.seller.service;

import cose.lexian.seller.dao.SellerDao;
import cose.lexian.seller.domain.Seller;
import cose.lexian.util.UserException;

import java.util.List;

public class SellerService {
    private SellerDao sellerDao = new SellerDao();

    public void regist(Seller form) throws UserException {
        /** 校验用户名 */
        Seller seller = sellerDao.findByUsername(form.getS_name());
        if(seller != null) {
            throw new UserException("该用户名已被注册！");
        }
        /** 校验邮箱 */
        seller = sellerDao.findByEmail(form.getS_email());
        if(seller != null) {
            throw new UserException("该邮箱已被注册！");
        }

        /** 注册成功 */
        sellerDao.add(form);
    }

    /** 激活功能 */
    public void active(String code) throws UserException {
        /** 1.使用code查询数据库得到Seller */
        Seller seller = sellerDao.findByCode(code);
        /** 2.如果seller不存在，说明激活码错误 */
        if(seller == null) {
            throw new UserException("激活码无效！");
        }
        /** 3.校验商家的状态是否为未激活状态，如果已激活，说明是二次激活，抛出异常 */
        if(seller.getS_state() == 5) {
            throw new UserException("您已经激活过了，请不要重复激活！");
        }
        /** 4.修改商家的状态，激活成功 */
        sellerDao.updateState(seller.getS_id(), 5);
    }

    public Seller login(Seller form) throws UserException {
        /**
         * 1.使用username查询，得到Seller
         * 2.如果seller为null，抛出异常（用户名不存在）
         * 3.比较form和seller的密码，若不同，抛出异常（密码错误）
         * 4.查看用户的状态，若为false，抛出异常（尚未激活）
         * 5.返回seller
         */
        Seller seller = sellerDao.findByUsername(form.getS_name());
        if(seller == null || !seller.getS_pwd().equals(form.getS_pwd())) {
            throw new UserException("用户名或密码错误！");
        }
        if(seller.getS_state() == 0) {
            throw new UserException("该账号尚未审核，请等待管理员审核！");
        } else if(seller.getS_state() == 1) {
            throw new UserException("该账号尚未激活！");
        } else if(seller.getS_state() == 2) {
            throw new UserException("该账号审核未通过！");
        } else if(seller.getS_state() == 3) {
            throw new UserException("该账号已被停用！");
        } else if(seller.getS_state() == 5) {
            return seller;
        } else {
            throw new UserException("程序不该执行到此处！");
        }
    }

    /** 按商家id查询商家 */
    public Seller getSellerById(String s_id) {
        return sellerDao.findBySid(s_id);
    }

    /** 设置门店信息 */
    public void modifyInfo(Seller form) {
        sellerDao.modifyInfo(form);
    }

    /** 收藏商家 */
    public void addLikeSeller(String u_id, String s_id) {
        sellerDao.likeSeller(u_id, s_id); //更新likeseller表
        sellerDao.addSellerLike(s_id); //更新seller表
    }

    /** 修改商家状态 */
    public void modifySellerState(String s_id, int s_state) {
        sellerDao.setSellerState(s_id, s_state);
    }

    /** 按邮箱查询商家 */
    public Seller findByEmail(String s_email) {
        return sellerDao.findByEmail(s_email);
    }

    /** 重设密码 */
    public void resetPwd(String s_id, String s_pwd) {
        sellerDao.resetPwd(s_id, s_pwd);
    }

    /** 查询用户收藏的商家 */
    public List<Seller> findLikeSeller(String u_id) {
        return sellerDao.findLikeSeller(u_id);
    }
}
