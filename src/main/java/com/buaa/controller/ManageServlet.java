package com.buaa.controller;

import com.buaa.Service.BusinessService;
import com.buaa.Service.BusinessServiceImpl;
import com.buaa.Utils.WebUtil;
import com.buaa.domain.Book;
import com.buaa.domain.Category;
import com.buaa.commons.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ManageServlet extends HttpServlet {
    private BusinessService bs = new BusinessServiceImpl();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        if("checkCategory".equals(op))
        {
            checkCatogory(request, response);
        }
        else if("addCategory".equals(op))
        {
            addCategory(request, response);
        }
        else if("listCategories".equals(op))
        {
            listCategories(request, response);
        }
        else if("addBookUI".equals(op))
        {
            addBookUI(request, response);
        }
        else if("delCategory".equals(op))
        {
            delCategory(request, response);
        }
        else if("addBook".equals(op))
        {
            addBook(request, response);
        }
        else if("listBooks".equals(op))
        {
            listBooks(request, response);
        }
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void listBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String num = request.getParameter("num");
        Page page = bs.findPage(num);
        page.setUrl("/servlet/ManageServlet?op=listBooks");
        request.setAttribute("commons", page);
        request.getRequestDispatcher("/manage/listBooks.jsp").forward(request, response);
    }

    /**
     * 保存书籍
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     */
    private void addBook(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if(!isMultipart){
            throw new RuntimeException();
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        List<FileItem> items = new ArrayList<>();

        try {
            items = sfu.parseRequest(request);
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        }
        Book book = new Book();
        for(FileItem item : items){
            if(item.isFormField()){
                String fieldName = item.getFieldName();
                String fieldValue = item.getString("UTF-8");

                try {
                    BeanUtils.setProperty(book, fieldName, fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                if("categoryId".equals(fieldName)){
                    Category category = bs.findCategoryById(fieldValue);
                    book.setCategory(category);
                }
            }else {
                String filename = item.getName();
                String extensionName = FilenameUtils.getExtension(filename);

                filename = UUID.randomUUID() + "." + extensionName;
                book.setFilename(filename);

                String rootDirectory = getServletContext().getRealPath("../images");
                String childPath = getStoreDirecotry(rootDirectory);
                book.setPath(childPath);

                try {
                    item.write(new File((rootDirectory+File.separator+childPath+File.separator+filename)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        bs.addBook(book);
        request.setAttribute("message", "添加书籍成功");
        try {
            request.getRequestDispatcher("/manage/message.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStoreDirecotry(String rootDirectory) {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String path = df.format(now);
        File file = new File(rootDirectory,path);
        if(!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    /**
     * 根据categoryName删除书籍
     * @param request
     * @param response
     */
    private void delCategory(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        bs.deleteCategoryByName(name);

        request.setAttribute("message", "删除成功");
        try {
            request.getRequestDispatcher("/manage/message.jsp?d="+new Date().getTime()).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加书籍
     * @param request
     * @param response
     */
    private void addBookUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> cs = bs.findAllCategories();
        request.setAttribute("cs", cs);
        request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
    }

    /**
     * 查询所有分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void listCategories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> cs = bs.findAllCategories();
        request.setAttribute("cs", cs);
        request.getRequestDispatcher("/manage/addBook.jsp").forward(request, response);
    }

    /**
     * 添加分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = WebUtil.fillBean(request, Category.class);
        bs.addCategory(category);

        request.setAttribute("message", "添加成功");
        request.getRequestDispatcher("/manage.message.jsp").forward(request, response);
    }

    /**
     * 检查分类名是否可用
     * @param request
     * @param response
     * @throws IOException
     */
    private void checkCatogory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        boolean exists = bs.isCategoryExists(name);
        if(exists){
            out.write("<font color = 'red'>该分类已经存在</font>");
        }else {
            out.write("<font color = 'green'>可是使用</font>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
