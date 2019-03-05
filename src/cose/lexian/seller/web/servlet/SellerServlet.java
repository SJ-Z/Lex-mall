package cose.lexian.seller.web.servlet;

import cn.itcast.servlet.BaseServlet;
import cose.lexian.seller.domain.Seller;
import cose.lexian.seller.service.SellerService;
import cose.lexian.util.UserException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SellerServlet", urlPatterns = "/seller/SellerServlet")
public class SellerServlet extends BaseServlet {
    private SellerService sellerService = new SellerService();

    /**
     * 退出功能
     */
    public String quit(HttpServletRequest request, HttpServletResponse response) {
        if (null != request.getSession(false).getAttribute("session_seller")) {
            request.getSession().invalidate();
            /** 删除自动登录 cookie */
            Cookie autologinC = new Cookie("autoLogin", "");
            autologinC.setPath(request.getContextPath());
            autologinC.setMaxAge(0);
            response.addCookie(autologinC);
        }
        return "r:/sellerjsps/index.jsp";
    }

    /**
     * 显示门店信息
     */
    public String showInfo(HttpServletRequest request, HttpServletResponse response) {
        Seller seller = sellerService.getSellerById(request.getParameter("s_id"));
        request.setAttribute("seller", seller);
        return "f:/sellerjsps/seller/information-browser.jsp";
    }

    /**
     * 加载门店信息，设置门店信息前的准备工作
     */
    public String modifyPre(HttpServletRequest request, HttpServletResponse response) {
        Seller seller = sellerService.getSellerById(request.getParameter("s_id"));
        request.setAttribute("seller", seller);
        return "f:/sellerjsps/seller/information-modify.jsp";
    }

    /**
     * 设置门店信息
     */
    public String modifyInfo(HttpServletRequest request, HttpServletResponse response) {
        //Seller form = CommonUtils.toBean(request.getParameterMap(), Seller.class); //不用该方法，Date类型会报错，此处只能手动装配
        Seller form = new Seller();
        form.setS_id(request.getParameter("s_id").trim());
        form.setS_storeName(request.getParameter("s_storeName").trim());
        form.setS_phone(request.getParameter("s_phone").trim());
        form.setS_addr(request.getParameter("s_addr").trim());
        form.setS_email(request.getParameter("s_email").trim());

        if (request.getParameter("s_openTime").trim().length() != 0) {
            form.setS_openTime(request.getParameter("s_openTime").trim());
        }
        if (request.getParameter("s_closeTime").trim().length() != 0) {
            form.setS_closeTime(request.getParameter("s_closeTime").trim());
        }

        /** 输入校验，表单信息不能为空 */
        try {
            if (form.getS_storeName().length() == 0) {
                throw new UserException("门店名称不能为空！");
            }
            if (form.getS_phone().length() == 0) {
                throw new UserException("门店电话不能为空！");
            }
            if (form.getS_addr().length() == 0) {
                throw new UserException("门店地址不能为空！");
            }
            if (form.getS_email().length() == 0) {
                throw new UserException("邮箱不能为空！");
            }
            if (request.getParameter("s_openTime").trim().length() == 0) {
                throw new UserException("营业开始时间不能为空！");
            }
            if (request.getParameter("s_closeTime").trim().length() == 0) {
                throw new UserException("营业结束时间不能为空！");
            }
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("seller", form);
            request.setAttribute("s_openTime", request.getParameter("s_openTime"));
            request.setAttribute("s_closeTime", request.getParameter("s_closeTime"));
            return "f:/sellerjsps/seller/information-modify.jsp";
        }

        sellerService.modifyInfo(form);
        request.setAttribute("msg", "设置商店信息成功！");
        request.setAttribute("seller", sellerService.getSellerById(form.getS_id()));
        return "f:/sellerjsps/seller/information-modify.jsp";
    }
}
