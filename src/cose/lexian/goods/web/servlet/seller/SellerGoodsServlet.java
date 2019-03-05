package cose.lexian.goods.web.servlet.seller;

import cn.itcast.servlet.BaseServlet;
import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.type.domain.Type;
import cose.lexian.type.service.TypeService;
import cose.lexian.util.GoodsException;
import cose.lexian.util.PageBean;
import cose.lexian.util.TypeException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "SellerGoodsServlet", urlPatterns = "/seller/SellerGoodsServlet")
public class SellerGoodsServlet extends BaseServlet {
    private GoodsService goodsService = new GoodsService();
    private TypeService typeService = new TypeService();

    /**
     * 分页，商家查询所有未下架的商品
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */
        String s_id;
        if (request.getAttribute("s_id") != null) {
            s_id = (String) request.getAttribute("s_id");
        } else {
            s_id = request.getParameter("s_id");
        }

        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 12; //给定pageSize的值，每页12行记录

        PageBean<Goods> pageBean = goodsService.findAllGoods(s_id, pageCode, pageSize);
        request.setAttribute("pageBean", pageBean);

        //设置url
        pageBean.setUrl(getUrl(request));

        return "f:/sellerjsps/seller/goods-browser.jsp";
    }

    /**
     * 上架商品前的准备工作，加载一级分类
     */
    public String addGoodsPre(HttpServletRequest request, HttpServletResponse response) {
        List<Type> typeList = typeService.findAllType();
        request.setAttribute("typeList", typeList);
        return "f:/sellerjsps/seller/goods-add.jsp";
    }

    /**
     * 上架搜索商品前的准备工作，加载一级分类
     */
    public String searchGoodsPre(HttpServletRequest request, HttpServletResponse response) {
        List<Type> typeList = typeService.findAllType();
        request.setAttribute("typeList", typeList);
        return "f:/sellerjsps/seller/goods-search.jsp";
    }

    /**
     * 商家搜索商品，按商品名称搜索(模糊查询)
     */
    public String searchGoodsByName(HttpServletRequest request, HttpServletResponse response) {
        String s_id = request.getParameter("s_id"); //获取商家id
        String keyword = request.getParameter("keyword");
        request.setAttribute("s_id", s_id);
        request.setAttribute("keyword", keyword);
        request.setAttribute("method", request.getParameter("method"));

        int pageCode = getPageCode(request);
        int pageSize = 12; //给定pageSize的值，每页12行记录
        PageBean<Goods> pageBean = null;

        try {
            if (keyword == null) {
                throw new GoodsException("请填写商品名称！");
            } else { //按一级分类和二级分类来查询
                pageBean = goodsService.findGoodsBySellerAndName(s_id, keyword, pageCode, pageSize);
            }

            if (pageBean.getBeanList().size() > 0) {
                request.setAttribute("pageBean", pageBean);
            }
            //设置url
            pageBean.setUrl(getGoodsByNameUrl(request));
            return "f:/sellerjsps/seller/goods-browser.jsp";
        } catch (GoodsException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/sellerjsps/seller/goods-search.jsp";
        }
    }

    /**
     * 商家搜索商品，一级分类、二级分类来搜索
     */
    public String searchGoodsByType(HttpServletRequest request, HttpServletResponse response) {
        String s_id = request.getParameter("s_id"); //获取商家id
        String t_id = request.getParameter("t_id"); //获取一级分类id
        String sub_id = request.getParameter("sub_id"); //获取二级分类id
        request.setAttribute("method", request.getParameter("method"));
        request.setAttribute("s_id", s_id);
        request.setAttribute("t_id", t_id);
        request.setAttribute("sub_id", sub_id);
        PageBean<Goods> pageBean = null;

        int pageCode = getPageCode(request);
        int pageSize = 12; //给定pageSize的值，每页12行记录
        try {
            if (t_id == null) {
                throw new TypeException("必须选择一级分类！");
            }
            if (sub_id.equals("===请选择二级分类===")) { //只按一级分类查询
                pageBean = goodsService.findGoodsBySellerAndType(s_id, t_id, pageCode, pageSize);
            } else { //按一级分类和二级分类来查询
                pageBean = goodsService.findGoodsBySellerAndSubType(s_id, sub_id, pageCode, pageSize);
            }

            if (pageBean.getBeanList().size() > 0) {
                request.setAttribute("pageBean", pageBean);
            }
            //设置url
            pageBean.setUrl(getGoodsByTypeUrl(request));
            return "f:/sellerjsps/seller/goods-browser.jsp";
        } catch (TypeException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/sellerjsps/seller/goods-search.jsp";
        }
    }

    /**
     * 商家修改商品信息前的准备工作，加载商品信息
     */
    public String modifyGoodsPre(HttpServletRequest request, HttpServletResponse response) {
        String g_id = request.getParameter("g_id");
        Goods goods = goodsService.modifyGoodsPre(g_id);

        request.setAttribute("goods", goods);
        return "f:/sellerjsps/seller/goods-modify.jsp";
    }

    /**
     * 商家下架商品
     */
    public String outGoods(HttpServletRequest request, HttpServletResponse response) {
        String g_id = request.getParameter("g_id");
        goodsService.outGoods(g_id);

        request.setAttribute("s_id", request.getParameter("s_id"));
        return findAll(request, response);
    }

    //获取pageCode
    private int getPageCode(HttpServletRequest request) {
        //得到pageCode：如果pageCode参数不存在，说明pageCode=1；如果pageCode参数存在，需要转换成int类型
        String value;
        try {
            value = request.getParameter("pageCode");
        } catch (Exception e) {
            value = null;
        }
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }

    //截取url :/项目名/Servlet路径?参数字符串
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString(); //获取?之后的参数部分

        //判断参数部分中是否包含pc这个参数，如果包含，需要截取掉，不要这一部分
        if (queryString.contains("&pageCode=")) {
            int index = queryString.lastIndexOf("&pageCode=");
            queryString = queryString.substring(0, index);
        }

        return contextPath + servletPath + "?" + queryString;
    }

    private String getGoodsByNameUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String paramString = "method=" + request.getAttribute("method") + "&s_id=" + request.getAttribute("s_id") +
                "&keyword=" + request.getAttribute("keyword");

        return contextPath + servletPath + "?" + paramString;
    }

    private String getGoodsByTypeUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String paramString = "method=" + request.getAttribute("method") + "&s_id=" + request.getAttribute("s_id") +
                "&t_id=" + request.getAttribute("t_id") + "&sub_id=" + request.getAttribute("sub_id");

        return contextPath + servletPath + "?" + paramString;
    }
}
