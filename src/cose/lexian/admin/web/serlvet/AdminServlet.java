package cose.lexian.admin.web.serlvet;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cose.lexian.admin.service.AdminService;
import cose.lexian.goods.domain.Goods;
import cose.lexian.goods.service.GoodsService;
import cose.lexian.order.domain.Order;
import cose.lexian.order.service.OrderService;
import cose.lexian.seller.domain.Seller;
import cose.lexian.seller.service.SellerService;
import cose.lexian.util.PageBean;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Properties;

@WebServlet(name = "AdminServlet", urlPatterns = "/admin/AdminServlet")
public class AdminServlet extends BaseServlet {
    private AdminService adminService = new AdminService();
    private SellerService sellerService = new SellerService();
    private OrderService orderService = new OrderService();
    private GoodsService goodsService = new GoodsService();

    /**
     * 审核商家注册信息前的准备工作，显示所有未审核的商家信息
     */
    public String checkRegistPre(HttpServletRequest request, HttpServletResponse response) {
        List<Seller> sellerList = adminService.findSellerByState(0);
        request.setAttribute("sellerList", sellerList);
        return "f:/adminjsps/admin/seller/checkRegistList.jsp";
    }

    /**
     * 审核商家注册信息
     */
    public String checkRegist(HttpServletRequest request, HttpServletResponse response) {
        String s_id = request.getParameter("s_id");
        int s_state = Integer.parseInt(request.getParameter("s_state"));
        sendEmail(s_id, s_state); //发邮件
        adminService.checkRegist(s_id, s_state); //修改商家状态
        return checkRegistPre(request, response);
    }

    /**
     * 发送邮件
     */
    private void sendEmail(String s_id, int s_state) {
        /** 发邮件
         * 准备配置文件
         */
        try {
            Seller form = sellerService.getSellerById(s_id); //获取商家信息
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
            String host = props.getProperty("host"); //获取服务器主机
            String uname = props.getProperty("uname"); //获取用户名
            String pwd = props.getProperty("pwd"); //获取密码
            String from = props.getProperty("from"); //获取发件人
            String to = form.getS_email(); //获取收件人
            String subject = props.getProperty("subject_regist"); //获取主题

            String content = null;
            /** 根据s_state选择发送成功或失败邮件 */
            if (s_state == 1) { //审核通过，发送成功邮件
                content = props.getProperty("sucContent"); //获取邮件内容
                content = MessageFormat.format(content, form.getS_code()); //替换占位符{0}
            } else if (s_state == 2) { //审核未通过，发送失败邮件
                content = props.getProperty("failContent"); //获取邮件内容
            } else {
                throw new RuntimeException("程序不该执行到此处！");
            }

            Session session = MailUtils.createSession(host, uname, pwd);
            Mail mail = new Mail(from, to, subject, content); //创建邮件对象
            try {
                MailUtils.send(session, mail); //发邮件
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 退出功能
     */
    public String quit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "r:/adminjsps/admin/index.jsp";
    }

    /**
     * 显示所有运营中的商家
     */
    public String showOpenSeller(HttpServletRequest request, HttpServletResponse response) {
        List<Seller> sellerList = adminService.findSellerByState(5);
        request.setAttribute("sellerList", sellerList);
        return "f:/adminjsps/admin/seller/openSellerList.jsp";
    }

    /**
     * 停用商家
     */
    public String closeSeller(HttpServletRequest request, HttpServletResponse response) {
        String s_id = request.getParameter("s_id");
        sellerService.modifySellerState(s_id, 3);
        return showOpenSeller(request, response);
    }

    /**
     * 查看所有订单
     */
    public String findAllOrder(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */
        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 15; //给定pageSize的值，每页15行记录
        //传递pageCode、pageSize给service，得到PageBean
        PageBean<Order> pageBean = orderService.findAll(pageCode, pageSize);

        //设置url
        pageBean.setUrl(getUrl(request));

        request.setAttribute("pageBean", pageBean); //保存到request域中
        request.setAttribute("state", "0");
        return "f:/adminjsps/admin/order/list.jsp"; //转发
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

    /**
     * 按订单状态查询订单
     */
    public String findOrderByState(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */

        String state = request.getParameter("o_state");
        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 15; //给定pageSize的值，每页15行记录
        //传递pageCode、pageSize给service，得到PageBean
        PageBean<Order> pageBean = orderService.findByState(state, pageCode, pageSize);

        //设置url
        pageBean.setUrl(getUrl(request));

        request.setAttribute("pageBean", pageBean); //保存到request域中
        request.setAttribute("state", state);
        return "f:/adminjsps/admin/order/list.jsp"; //转发
    }

    /**
     * 查看订单详情
     */
    public String orderDetail(HttpServletRequest request, HttpServletResponse response) {
        String o_id = request.getParameter("o_id");
        Order order = orderService.viewDetail(o_id);
        request.setAttribute("order", order);
        return "f:/adminjsps/admin/order/desc.jsp";
    }

    /**
     * 查看所有商品
     */
    public String findAllGoods(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */
        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 10; //给定pageSize的值，每页10行记录
        //传递pageCode、pageSize给service，得到PageBean
        PageBean<Goods> pageBean = goodsService.findAllGoods(pageCode, pageSize);

        //设置url
        pageBean.setUrl(getUrl(request));

        request.setAttribute("pageBean", pageBean); //保存到request域中
        return "f:/adminjsps/admin/goods/goodsList.jsp"; //转发
    }

    /**
     * 下架商品
     */
    public String outGoods(HttpServletRequest request, HttpServletResponse response) {
        String g_id = request.getParameter("g_id");
        goodsService.outGoods(g_id);
        request.setAttribute("del", '1');
        return findGoodsByDel(request, response);
    }

    /**
     * 按商品状态查询
     */
    public String findGoodsByDel(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取页面传递的pageCode
        2.给定pageSize的值
        3.使用pageCode和pageSize调用service方法，得到pageBean，保存到request域
        4.转发
         */
        boolean g_del;
        if (request.getAttribute("del") == (Object) '1') {
            g_del = true;
        } else {
            String del = request.getParameter("del");
            if (del.equals("0")) {
                g_del = false;
            } else {
                g_del = true;
            }
        }

        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 10; //给定pageSize的值，每页10行记录
        //传递pageCode、pageSize给service，得到PageBean
        PageBean<Goods> pageBean = goodsService.findGoodsByDel(g_del, pageCode, pageSize);

        //设置url
        pageBean.setUrl(getUrl(request));

        request.setAttribute("pageBean", pageBean); //保存到request域中
        request.setAttribute("del", g_del);
        return "f:/adminjsps/admin/goods/goodsList.jsp"; //转发
    }

    /**
     * 通过商品id搜索商品
     */
    public String findGoodsById(HttpServletRequest request, HttpServletResponse response) {
        String g_id = request.getParameter("g_id");
        if (g_id.trim().equals("")) {
            request.setAttribute("msg", "请输入商品id！");
            return "f:/adminjsps/admin/goods/searchGoods.jsp";
        }
        Goods goods = goodsService.findGoodsById(g_id);
        if (goods.getG_id() == null) {
            request.setAttribute("msg", "未搜索到该商品！");
            request.setAttribute("g_id", g_id);
            return "f:/adminjsps/admin/goods/searchGoods.jsp";
        } else {
            request.setAttribute("goods", goods);
            return "f:/adminjsps/admin/goods/showGoods.jsp";
        }
    }

    /**
     * 通过商品名称搜索商品
     */
    public String findGoodsByName(HttpServletRequest request, HttpServletResponse response) {
        String g_name = request.getParameter("g_name");
        request.setAttribute("g_name", g_name);
        request.setAttribute("method", request.getParameter("method"));
        if (g_name.trim().equals("")) {
            request.setAttribute("msg", "请输入商品名称！");
            return "f:/adminjsps/admin/goods/searchGoods.jsp";
        }

        //1.得到pageCode
        int pageCode = getPageCode(request);
        int pageSize = 10; //给定pageSize的值，每页10行记录
        //传递pageCode、pageSize给service，得到PageBean
        PageBean<Goods> pageBean = goodsService.findGoodsByName(g_name, pageCode, pageSize);

        if (pageBean.getBeanList().size() == 0) {
            request.setAttribute("msg", "未搜索到该商品！");
            request.setAttribute("g_name", g_name);
            return "f:/adminjsps/admin/goods/searchGoods.jsp";
        }

        //设置url
        pageBean.setUrl(getGoodsByNameUrl(request));

        request.setAttribute("pageBean", pageBean); //保存到request域中
        return "f:/adminjsps/admin/goods/goodsList.jsp"; //转发
    }

    private String getGoodsByNameUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String paramString = "method=" + request.getAttribute("method") + "&g_name=" + request.getAttribute("g_name");

        return contextPath + servletPath + "?" + paramString;
    }
}
