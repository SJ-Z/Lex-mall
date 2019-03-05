package cose.lexian.admin.service;

import cose.lexian.admin.dao.AdminDao;
import cose.lexian.admin.domain.Admin;
import cose.lexian.seller.dao.SellerDao;
import cose.lexian.seller.domain.Seller;
import cose.lexian.util.UserException;

import java.util.List;

public class AdminService {
    private AdminDao adminDao = new AdminDao();
    private SellerDao sellerDao = new SellerDao();

    /** 按商家状态查询 */
    public List<Seller> findSellerByState(int state) {
        return sellerDao.findByState(state);
    }

    /** 审核商家注册信息 */
    public void checkRegist(String s_id, int s_state) {
        sellerDao.setSellerState(s_id, s_state);
    }

    /** 登录 */
    public Admin login(Admin form) throws UserException {
        Admin admin = adminDao.findByUsername(form.getA_name());
        if(admin == null || !admin.getA_pwd().equals(form.getA_pwd())) {
            System.out.println(admin);
            System.out.println(form);
            throw new UserException("用户名或密码错误！");
        }
        return admin;
    }
}
