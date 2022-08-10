package web.servlet;

import com.alibaba.fastjson.JSON;
import pojo.Brand;
import pojo.PageBean;
import service.BrandService;
import service.impl.BrandServiceImpl;
import web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class BrandServlet extends BaseServlet {
    private BrandService brandService = new BrandServiceImpl();

    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ids = request.getParameter("id");
        Integer id = Integer.parseInt(ids);

        brandService.deleteById(id);

        response.getWriter().write("success");
    }

    public void selectAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用Service查询
        List<Brand> brands = brandService.selectAll();

        //2.把数据转为JSON
        String jsonString = JSON.toJSONString(brands);

        //3.写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void selectById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String ids = request.getParameter("id");
        Integer id = Integer.parseInt(ids);

        Brand brand = brandService.selectById(id);

        String jsonString = JSON.toJSONString(brand);

        //3.写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收品牌数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //将JSON字符串转为brand对象
        Brand brand = JSON.parseObject(params, Brand.class);

        //2.调用service添加
        brandService.add(brand);

        //3.响应成功的标识
        response.getWriter().write("success");
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收品牌数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //将JSON字符串转为brand对象
        Brand brand = JSON.parseObject(params, Brand.class);

        //2.调用service添加
        brandService.update(brand);

        //3.响应成功的标识
        response.getWriter().write("success");
    }

    /**
     * 批量删除
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收品牌数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //将JSON字符串转为brand对象
        int[] ids = JSON.parseObject(params, int[].class);

        //2.调用service添加
        brandService.deleteByIds(ids);

        //3.响应成功的标识
        response.getWriter().write("success");
    }

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收 当前页码 和 每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        //转换数据
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //2.调用service进行查询
        PageBean<Brand> pageBean = brandService.selectByPage(currentPage, pageSize);

        //3.把数据转为JSON
        String jsonString = JSON.toJSONString(pageBean);

        //4.写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    /**
     * 分页条件查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收 当前页码 和 每页展示条数 url?currentPage=1&pageSize=5
        String _currentPage = request.getParameter("currentPage");
        String _pageSize = request.getParameter("pageSize");

        //转换数据
        int currentPage = Integer.parseInt(_currentPage);
        int pageSize = Integer.parseInt(_pageSize);

        //获取查询条件对象
        //接收数据
        BufferedReader br = request.getReader();
        String params = br.readLine();//json字符串

        //将JSON字符串转为brand对象
        Brand brand = JSON.parseObject(params, Brand.class);


        //2.调用service进行查询
        PageBean<Brand> pageBean = brandService.selectByPageAndCondition(currentPage, pageSize, brand);

        //3.把数据转为JSON
        String jsonString = JSON.toJSONString(pageBean);

        //4.写数据
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

}
