package web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
* 替换HttpServlet，根据请求的最后一段路径来进行方法的分发
* */
public class BaseServlet extends HttpServlet {

    //根据请求的最后一段路径来进行方法的分发
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的路径
        String uri = req.getRequestURI();  //uri示例： /brand-case/brand/selectAll

        //2.获取最后一段路径，方法名
        int index = uri.lastIndexOf('/');  //从后往前截取uri到第一个'/'
        String methodName = uri.substring(index + 1); //获取的方法名会包含/  如：/selectAll

        //3.执行方法
        //3.1 获取BrandServlet 字节码对象 Class
        //谁调用我(this 所在的方法)，我代表谁(this)

        Class<? extends BaseServlet> cls = this.getClass();

        //3.2 获取方法Method对象
        try {
            Method method = cls.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //3.3 执行方法
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
