package cose.lexian.user.web.servlet;

import cose.lexian.type.domain.Type;
import cose.lexian.type.service.TypeService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 本类用于在用户商城主页被加载时，ajax响应主页对所有一级分类的加载请求
 */
@WebServlet(name = "LoadTypeServlet", urlPatterns = "/LoadTypeServlet")
public class LoadTypeServlet extends HttpServlet {
    private TypeService typeService = new TypeService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        List<Type> typeList = typeService.findAllType();
        for (Type type :typeList) {
            type.setT_image("/lexian-mall" + type.getT_image());
        }
        String json = "{\"code\":\"1\", \"msg\":\"success\", \"result\":{"
                + "\"api\":\"/lexian-mall/LoadSubTypeAndTypeGoodsServlet\", "
                + "\"items\":" + JSONArray.fromObject(typeList).toString() + ", "
                + "}}";

        int loc = json.lastIndexOf(",");
        String str = json.substring(0, loc) + json.substring(loc+1);
        //System.out.println(json);

        response.getWriter().print(str);
    }
}
