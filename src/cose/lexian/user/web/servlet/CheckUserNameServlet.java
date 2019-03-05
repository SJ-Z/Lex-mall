package cose.lexian.user.web.servlet;

import cose.lexian.user.service.UserService;
import cose.lexian.util.UserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebServlet(name = "CheckUserNameServlet", urlPatterns = "/CheckUserNameServlet")
public class CheckUserNameServlet extends HttpServlet {
    UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            String u_name = request.getParameter("u_name");

            /**
             * 输入校验
             */
            Pattern pattern = Pattern.compile("[A-Za-z]*");
            if (u_name == null || u_name.trim().isEmpty()) {
                throw new UserException("用户名不能为空！");
            } else if (u_name.length() < 3 || u_name.length() > 15) {
                throw new UserException("用户名长度必须在3~15之间！");
            } else if (!pattern.matcher(u_name.charAt(0) + "").matches()) {
                throw new UserException("用户名必须以字母开头！");
            } else if (!u_name.matches("^[A-Za-z]+\\w*")) {
                throw new UserException("用户名只能包含字母、数字、下划线！");
            }

            userService.checkUserName(u_name);
            String msg = "用户名可用！";
            String json = "{\"msg\":\"" + msg + "\"}";
            response.getWriter().print(json);
        } catch (UserException e) {
            String msg = e.getMessage();
            String json = "{\"msg\":\"" + msg + "\"}";
            response.getWriter().print(json);
        }
    }
}
