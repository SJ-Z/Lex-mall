package cose.lexian.type.web.servlet;

import cose.lexian.type.domain.SubType;
import cose.lexian.type.service.TypeService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** ajax根据一级分类id，加载该一级分类下的二级分类 */
@WebServlet(name = "LoadSubTypeServlet", urlPatterns = "/LoadSubTypeServlet")
public class LoadSubTypeServlet extends HttpServlet {
    private TypeService typeService = new TypeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String t_id = request.getParameter("t_id");
        List<SubType> subTypeList = typeService.findAllSubType(t_id);

        String json = JSONArray.fromObject(subTypeList).toString();
        response.getWriter().print(json);
    }
}
