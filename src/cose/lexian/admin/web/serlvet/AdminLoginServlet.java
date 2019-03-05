package cose.lexian.admin.web.serlvet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cose.lexian.admin.domain.Admin;
import cose.lexian.admin.service.AdminService;
import cose.lexian.util.UserException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminLoginServlet", urlPatterns = "/AdminLoginServlet")
public class AdminLoginServlet extends BaseServlet {
    private AdminService adminService = new AdminService();

    /** 登录 */
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Admin form = CommonUtils.toBean(request.getParameterMap(), Admin.class);
        try {
            //校验验证码
            String sessionVerifyCode = (String) request.getSession().getAttribute("session_vcode");
            String verifyCode = form.getA_verifyCode();
            if (verifyCode == null || verifyCode.trim().isEmpty()) {
                throw new UserException("验证码不能为空！");
            } else if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
                throw new UserException("验证码错误！");
            }
            Admin admin = adminService.login(form);
            request.getSession().invalidate(); /** 登录前销毁之前的session，增加安全性 */
            request.getSession().setAttribute("session_admin", admin);
            return "f:/adminjsps/admin/index.jsp";
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/adminjsps/login.jsp";
        }
    }
}
