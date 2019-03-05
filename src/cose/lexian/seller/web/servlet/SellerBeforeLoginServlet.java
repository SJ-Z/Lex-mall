package cose.lexian.seller.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cose.lexian.seller.domain.Seller;
import cose.lexian.seller.service.SellerService;
import cose.lexian.util.UserException;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

@WebServlet(name = "SellerBeforeLoginServlet", urlPatterns = "/SellerBeforeLoginServlet")
public class SellerBeforeLoginServlet extends BaseServlet {
    private SellerService sellerService = new SellerService();

    /**
     * 注册功能
     */
    public String regist(HttpServletRequest request, HttpServletResponse response) {
        /**
         *  1.封装表单数据到form对象中
         *  2.补全：sid、code
         *  3.输入校验
         *   > 保存错误信息、form到request域，转发到regist.jsp
         *  4.调用service方法完成注册
         *   > 保存错误信息、form到request域，转发到regist.jsp
         *  5.发邮件
         *  6.保存成功信息转发到msg.jsp
         */
        /** 封装表单数据 */
        Seller form = CommonUtils.toBean(request.getParameterMap(), Seller.class);
        /** 补全sid、code */
        form.setS_id(CommonUtils.uuid());
        form.setS_code(CommonUtils.uuid() + CommonUtils.uuid());

        /**
         * 输入校验
         *  1.创建一个Map，用来封装错误信息，其中key为表单字段名称，值为错误信息
         */
        Map<String, String> errors = new HashMap<String, String>();
        /**
         *  2.获取form中的username、password、email进行校验
         */
        String username = form.getS_name();
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        if (username == null || username.trim().isEmpty()) {
            errors.put("s_name", "用户名不能为空！");
        } else if (username.length() < 3 || username.length() > 15) {
            errors.put("s_name", "用户名长度必须在3~15之间！");
        } else if (!pattern.matcher(username.charAt(0) + "").matches()) {
            errors.put("s_name", "用户名必须以字母开头！");
        } else if (!username.matches("^[A-Za-z]+\\w*")) {
            errors.put("s_name", "只能包含字母、数字、下划线！");
        }
        String password = form.getS_pwd();
        if (password == null || password.trim().isEmpty()) {
            errors.put("s_pwd", "密码不能为空！");
        } else if (password.length() < 3 || password.length() > 15) {
            errors.put("s_pwd", "密码长度必须在3~15之间！");
        } else if (!pattern.matcher(password.charAt(0) + "").matches()) {
            errors.put("s_pwd", "密码必须以字母开头！");
        } else if (!password.matches("^[A-Za-z]+\\w*")) {
            errors.put("s_pwd", "只能包含字母、数字、下划线！");
        }
        String _password = request.getParameter("_pwd");
        if (!_password.equals(password)) {
            errors.put("_pwd", "两次密码输入不一致！");
        }
        String email = form.getS_email();
        if (email == null || email.trim().isEmpty()) {
            errors.put("s_email", "Email不能为空！");
        } else if (!email.matches("\\w+@\\w+\\.\\w+")) {
            errors.put("s_email", "Email格式错误！");
        }
        if (form.getS_storeName().trim().equals("") || form.getS_storeName() == null) {
            errors.put("s_storeName", "门店名不能为空！");
        }
        if (form.getS_phone().trim().equals("") || form.getS_phone() == null) {
            errors.put("s_phone", "电话不能为空！");
        }
        if (form.getS_addr().trim().equals("")) {
            errors.put("s_addr", "地址不能为空！");
        }
        //对验证码进行校验
        String sessionVerifyCode = (String) request.getSession().getAttribute("session_vcode");
        String verifyCode = form.getS_verifyCode();
        if (verifyCode == null || verifyCode.trim().isEmpty()) {
            errors.put("s_verifyCode", "验证码不能为空！");
        } else if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
            errors.put("s_verifyCode", "验证码错误");
        }
        /**
         * 判断是否存在错误信息
         */
        if (errors.size() > 0) {
            /**
             * 1.保存错误信息
             * 2.保存表单数据
             * 3.转发到regist.jsp
             */
            request.setAttribute("errors", errors);
            request.setAttribute("form", form);
            return "f:/sellerjsps/seller-register.jsp";
        }
        /**
         * 调用service的regist()方法
         */
        try {
            sellerService.regist(form);
        } catch (UserException e) {
            /**
             * 1.保存异常信息
             * 2.保存form
             * 3.转发到regist.jsp
             */
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/sellerjsps/seller-register.jsp";
        }

        /** 提交注册申请给管理员审核，不用写，管理员可自己查看到状态为未审核的注册申请 */

        /** 保存注册申请提交信息，转发到 msg.jsp */
        request.setAttribute("msg", "您的商家注册申请已提交，请注意查收反馈邮件！");
        return "f:/sellerjsps/info.jsp";
    }

    /**
     * 激活功能
     */
    public String active(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 1.获取参数激活码
         * 2.调用service方法完成激活
         *   > 保存异常信息到request域，转发到msg.jsp
         * 3.保存成功信息到request域，转发到msg.jsp
         */
        String code = request.getParameter("code");
        try {
            sellerService.active(code);
            request.setAttribute("msg", "恭喜，您已成功激活账号！");
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/sellerjsps/info.jsp";
    }

    /**
     * 登录功能
     */
    public String login(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 1.封装表单数据到form中
         * 2.输入校验
         * 3.调用service完成激活
         *  > 保存错误信息、form到request，转发到login.jsp
         * 4.保存用户信息到session中，重定向到index.jsp
         */
        Seller form = CommonUtils.toBean(request.getParameterMap(), Seller.class);
        boolean remember = request.getParameter("remember") != null ? true : false;
        try {
            //校验验证码
            String sessionVerifyCode = (String) request.getSession().getAttribute("session_vcode");
            String verifyCode = form.getS_verifyCode();
            if (verifyCode == null || verifyCode.trim().isEmpty()) {
                throw new UserException("验证码不能为空！");
            } else if (!verifyCode.equalsIgnoreCase(sessionVerifyCode)) {
                throw new UserException("验证码错误！");
            }
            Seller seller = sellerService.login(form);
            request.getSession().invalidate(); /** 登录前销毁之前的session，增加安全性 */
            request.getSession().setAttribute("session_seller", seller);

            if (remember) {
                /** 设置自动登录的cookie */
                Cookie autologinC = new Cookie("autoLogin", seller.getS_name() + ":" + seller.getS_pwd());
                autologinC.setPath(request.getContextPath());
                autologinC.setMaxAge(3600 * 24 * 7); //7天自动登录
                response.addCookie(autologinC);
            }

            return "r:/sellerjsps/seller/seller-open.jsp";
        } catch (UserException e) {
            request.setAttribute("remember", remember);
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/sellerjsps/index.jsp";
        }
    }

    /**
     * 找回密码前的准备工作
     */
    public String findPwd(HttpServletRequest request, HttpServletResponse response) {
        String s_email = request.getParameter("s_email");
        try {
            Seller seller = sellerService.findByEmail(s_email);
            if (seller != null) {
                /**
                 * 发邮件
                 */
                try {
                    Properties props = new Properties();
                    props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
                    String host = props.getProperty("host"); //获取服务器主机
                    String uname = props.getProperty("uname"); //获取用户名
                    String pwd = props.getProperty("pwd"); //获取密码
                    String from = props.getProperty("from"); //获取发件人
                    String to = seller.getS_email(); //获取收件人
                    String subject = props.getProperty("subject_findPwd"); //获取主题

                    String content = null;
                    content = props.getProperty("findPwdContent"); //获取邮件内容
                    content = MessageFormat.format(content, seller.getS_id()); //替换占位符{0}

                    Session session = MailUtils.createSession(host, uname, pwd);
                    Mail mail = new Mail(from, to, subject, content); //创建邮件对象
                    try {
                        MailUtils.send(session, mail); //发邮件
                        request.setAttribute("msg", "找回密码邮件已发送，请注意查收！");
                        return "f:/sellerjsps/info.jsp";
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new UserException("该邮箱不存在，请重新输入！");
            }
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("s_email", s_email);
            return "f:/sellerjsps/findPwd.jsp";
        }
    }

    /**
     * 找回密码的重新设置密码
     */
    public String resetPwd(HttpServletRequest request, HttpServletResponse response) {
        String s_id = request.getParameter("s_id");
        String state = request.getParameter("state");
        if (state != null && state.equals("0")) {
            return "f:/sellerjsps/resetPwd.jsp";
        }
        String s_pwd = request.getParameter("s_pwd");
        String _pwd = request.getParameter("_pwd");
        Pattern pattern = Pattern.compile("[A-Za-z]*");
        try {
            if (s_pwd == null || s_pwd.trim().isEmpty()) {
                throw new UserException("密码不能为空！");
            } else if (s_pwd.length() < 3 || s_pwd.length() > 15) {
                throw new UserException("密码长度必须在3~15之间！");
            } else if (!pattern.matcher(s_pwd.charAt(0) + "").matches()) {
                throw new UserException("密码必须以字母开头！");
            } else if (!s_pwd.matches("^[A-Za-z]+\\w*")) {
                throw new UserException("密码只能包含字母、数字、下划线！");
            } else if (!s_pwd.equals(_pwd)) {
                throw new UserException("两次密码输入不一致！");
            }
            sellerService.resetPwd(s_id, s_pwd);
            request.setAttribute("msg", "恭喜，密码修改成功！");
            return "f:/sellerjsps/info.jsp";
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/sellerjsps/resetPwd.jsp";
        }
    }
}
