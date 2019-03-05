package cose.lexian.user.web.servlet;

import cose.lexian.seller.domain.Seller;
import cose.lexian.user.service.UserService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchSellerServlet", urlPatterns = "/SearchSellerServlet")
public class SearchSellerServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String keyword = request.getParameter("keyword"); //获取搜索词
        List<Seller> sellerList = userService.searchSeller(keyword);

        for (Seller seller : sellerList) { //去除不必要的数据
            seller.setS_openTime(0);
            seller.setS_closeTime(0);
        }

        String json = "{\"sellerList\":" + JSONArray.fromObject(sellerList).toString() + "}";
        response.getWriter().print(json);
    }
}
