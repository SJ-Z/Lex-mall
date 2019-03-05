package cose.lexian.type.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cose.lexian.type.domain.SubType;
import cose.lexian.type.domain.Type;
import cose.lexian.type.service.TypeService;
import cose.lexian.util.TypeException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminTypeServlet", urlPatterns = "/admin/AdminTypeServlet")
public class AdminTypeServlet extends BaseServlet {
    private TypeService typeService = new TypeService();

    /**
     * 查询所有一级分类
     */
    public String findAllType(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("typeList", typeService.findAllType());
        return "f:/adminjsps/admin/type/typeList.jsp";
    }

    /**
     * 添加二级分类前的准备工作，加载所有一级分类
     */
    public String addSubTypePre(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("typeList", typeService.findAllType());
        return "f:/adminjsps/admin/type/addSubType.jsp";
    }

    /**
     * 添加二级分类
     */
    public String addSubType(HttpServletRequest request, HttpServletResponse response) {
        SubType form = new SubType();
        try {
            /** 封装表单数据 */
            if(request.getParameter("sub_name") == null || request.getParameter("sub_name").trim() == "") {
                if (request.getParameter("sub_type").equals("===选择一级分类===")) { //用作回显数据
                    throw new TypeException("请选择一级分类");
                }
                Type type = new Type();
                type.setT_id(request.getParameter("sub_type"));
                form.setSub_type(type);

                throw new TypeException("请设置二级分类的名字");
            }
            form.setSub_name(request.getParameter("sub_name").trim());
            if (request.getParameter("sub_type").equals("===选择一级分类===")) {
                throw new TypeException("请选择一级分类");
            }
            Type type = new Type();
            type.setT_id(request.getParameter("sub_type"));
            form.setSub_type(type);
            form.setSub_id(CommonUtils.uuid());

            typeService.addSubType(form);
            request.setAttribute("t_id", form.getSub_type().getT_id());
            return findAllSubType(request, response);
        } catch (TypeException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("typeList", typeService.findAllType());
            request.setAttribute("form", form);
            return "f:/adminjsps/admin/type/addSubType.jsp";
        }
    }

    /**
     * 查询某一级分类下所有二级分类
     */
    public String findAllSubType(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("t_id") != null) { //用于本类中其他函数调用时的界面回显
            String t_id = (String) request.getAttribute("t_id");
            request.setAttribute("subTypeList", typeService.findAllSubType(t_id));
        } else {
            String t_id = request.getParameter("t_id");
            request.setAttribute("subTypeList", typeService.findAllSubType(t_id));
        }
        return "f:/adminjsps/admin/type/subTypeList.jsp";
    }

    /**
     * 修改二级分类前的准备工作，加载该分类的信息
     */
    public String modifySubTypePre(HttpServletRequest request, HttpServletResponse response) {
        String sub_id = request.getParameter("sub_id");
        request.setAttribute("subType", typeService.findSubTypeById(sub_id));
        return "f:/adminjsps/admin/type/modifySubType.jsp";
    }

    /**
     * 修改二级分类
     */
    public String modifySubType(HttpServletRequest request, HttpServletResponse response) {
        SubType form = CommonUtils.toBean(request.getParameterMap(), SubType.class);
        typeService.modifySubType(form);
        SubType subType = typeService.findSubTypeById(form.getSub_id());
        request.setAttribute("t_id", subType.getSub_type().getT_id());
        return findAllSubType(request, response);
    }

    /**
     * 删除二级分类
     */
    public String deleteSubType(HttpServletRequest request, HttpServletResponse response) {
        String sub_id = request.getParameter("sub_id");
        try {
            SubType subType = typeService.findSubTypeById(sub_id);
            request.setAttribute("t_id", subType.getSub_type().getT_id());
            typeService.deleteSubType(sub_id);
            return findAllSubType(request, response);
        } catch (TypeException e) {
            request.setAttribute("msg", e.getMessage());
            return findAllSubType(request, response);
        }
    }

    /**
     * 修改一级分类前的准备工作，加载该分类的信息
     */
    public String modifyTypePre(HttpServletRequest request, HttpServletResponse response) {
        String t_id = request.getParameter("t_id");
        request.setAttribute("type", typeService.findTypeById(t_id));
        return "f:/adminjsps/admin/type/modifyType.jsp";
    }

    /**
     * 删除一级分类
     */
    public String deleteType(HttpServletRequest request, HttpServletResponse response) {
        String t_id = request.getParameter("t_id");
        try {
            typeService.deleteType(t_id);
            return findAllType(request, response);
        } catch (TypeException e) {
            request.setAttribute("msg", e.getMessage());
            return findAllType(request, response);
        }
    }
}
