package cose.lexian.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cose.lexian.user.domain.User;
import cose.lexian.user.service.UserService;
import cose.lexian.util.UserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet(name = "UserRegistServlet", urlPatterns = "/UserRegistServlet")
public class UserRegistServlet extends HttpServlet {
    UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        User user = CommonUtils.toBean(request.getParameterMap(), User.class);

        /** 输入校验 */
        try {
            String username = user.getU_name();
            Pattern pattern = Pattern.compile("[A-Za-z]*");
            if (username == null || username.trim().isEmpty()) {
                throw new UserException("用户名不能为空！");
            } else if (username.length() < 3 || username.length() > 15) {
                throw new UserException("用户名长度必须在3~15之间！");
            } else if (!pattern.matcher(username.charAt(0) + "").matches()) {
                throw new UserException("用户名必须以字母开头！");
            } else if (!username.matches("^[A-Za-z]+\\w*")) {
                throw new UserException("用户名只能包含字母、数字、下划线！");
            }
            String password = user.getU_pwd();
            if (password == null || password.trim().isEmpty()) {
                throw new UserException("密码不能为空！");
            } else if (password.length() < 3 || password.length() > 15) {
                throw new UserException("密码长度必须在3~15之间！");
            } else if (!pattern.matcher(password.charAt(0) + "").matches()) {
                throw new UserException("密码必须以字母开头！");
            } else if (!password.matches("^[A-Za-z]+\\w*")) {
                throw new UserException("密码只能包含字母、数字、下划线！");
            }
        } catch (UserException e) {
            String json = "{\"msg\":\"" + e.getMessage() + "\"}";
            response.getWriter().print(json);
            return;
        }

        user.setU_id(CommonUtils.uuid());

        try {
            userService.regist(user);
        } catch (UserException e) {
            String json = "{\"msg\":\"" + e.getMessage() + "\"}";
            System.out.println(json);
            response.getWriter().print(json);
            return;
        }

        String json = "{\"msg\":\"1\"}";
        response.getWriter().print(json);
    }
}
