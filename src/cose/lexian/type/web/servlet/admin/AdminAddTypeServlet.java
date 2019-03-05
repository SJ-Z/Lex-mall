package cose.lexian.type.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cose.lexian.type.domain.Type;
import cose.lexian.type.service.TypeService;
import cose.lexian.util.TypeException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminAddTypeServlet", urlPatterns = "/admin/AdminAddTypeServlet")
public class AdminAddTypeServlet extends HttpServlet {
    private TypeService typeService = new TypeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        /** 1.把表单数据封装到Type对象中 */
        //创建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory(100 * 1024, new File("E:/temp"));//设置缓存大小和临时目录
        //得到解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置单个文件大小最大值为100KB
        sfu.setFileSizeMax(100 * 1024);
        //使用解析器去解析request对象，得到List<FileItem>
        Type form = new Type();
        try {
            List<FileItem> fileItemList = sfu.parseRequest(request);
            form.setT_name(fileItemList.get(0).getString("UTF-8")); //将一级分类名称存入Type

            /** 校验该一级分类是否已存在 */
            Type type = typeService.findTypeByName(form.getT_name());
            if (type != null) {
                throw new TypeException("该一级分类已存在!");
            }

            form.setT_id(CommonUtils.uuid()); //设置t_id

            /** 2.保存上传的文件：保存路径和文件名称 */
            String str = request.getSession().getServletContext().getRealPath("/");
            int loc = str.indexOf("out");
            String savepath = str.substring(0, loc) + "type_img";
            //得到文件名称：直接利用uuid命名，避免文件名冲突
            int begin = fileItemList.get(1).getName().indexOf(".");
            String suffix = fileItemList.get(1).getName().substring(begin); //截取图片后缀
            String filename = CommonUtils.uuid() + suffix;

            /** 校验文件的扩展名 */
            if(!filename.toLowerCase().endsWith("svg")) {
                request.setAttribute("msg", "您上传的图片必须是svg格式！");
                request.setAttribute("t_name", form.getT_name());
                request.getRequestDispatcher("/adminjsps/admin/type/addType.jsp").forward(request, response);
                return;
            }

            //使用目录和文件名称创建目标文件
            File destFile = new File(savepath, filename);
            //保存上传文件到目标文件位置
            fileItemList.get(1).write(destFile);

            /** 3.设置Type对象的image，即把图片的路径设置给Type的image */
            form.setT_image("/type_img/" + filename);
            /** 4.使用TypeService完成保存 */
            typeService.addType(form);

            /** 校验图片的尺寸 */
            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if(image.getWidth(null) > 200 || image.getHeight(null) > 200) {
                destFile.delete(); //删除这个文件
                request.setAttribute("msg", "您上传的图片尺寸超出了200 * 200！");
                request.setAttribute("t_name", form.getT_name());
                request.getRequestDispatcher("/adminjsps/admin/type/addType.jsp").forward(request, response);
                return;
            }

            /** 5.返回到一级分类列表 */
            request.getRequestDispatcher("/admin/AdminTypeServlet?method=findAllType").forward(request, response);
        } catch (TypeException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("t_name", form.getT_name());
            request.getRequestDispatcher("/adminjsps/admin/type/addType.jsp").forward(request, response);
        } catch (Exception e) {
            if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
                request.setAttribute("msg", "您上传的文件大小超出了100KB");
                request.setAttribute("t_name", form.getT_name());
                request.getRequestDispatcher("/adminjsps/admin/type/addType.jsp").forward(request, response);
            }
        }
    }
}
