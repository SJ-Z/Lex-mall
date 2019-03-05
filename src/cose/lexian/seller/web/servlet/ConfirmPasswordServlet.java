package cose.lexian.seller.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ConfirmPasswordServlet", urlPatterns = "/ConfirmPasswordServlet")
public class ConfirmPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try {
            String password = request.getParameter("s_pwd"); //获取请求参数
            Pattern pattern = Pattern.compile("[A-Za-z]*");
            Matcher isChar = pattern.matcher(password.charAt(0)+"");
            /** 验证密码是否符合要求 */
            if(password == null || password.trim().isEmpty()) {
                response.getWriter().print("密码不能为空！");
                return;
            } else if(password.length() < 3 || password.length() > 15) {
                response.getWriter().print("密码长度必须在3~15之间！");
                return;
            } else if(!isChar.matches()) {
                response.getWriter().print("密码必须以字母开头！");
                return;
            } else if(!password.matches("^[A-Za-z]+\\w*")) {
                response.getWriter().print("只能包含字母、数字、下划线！");
                return;
            }
            response.getWriter().print("恭喜，密码格式正确！");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
