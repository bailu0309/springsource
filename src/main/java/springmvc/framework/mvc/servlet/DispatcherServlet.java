package springmvc.framework.mvc.servlet;


import com.sun.org.apache.xerces.internal.jaxp.validation.ErrorHandlerAdaptor;
import springmvc.framework.context.ApplicationContext;
import springmvc.framework.mvc.HandlerMapping;
import springmvc.framework.mvc.ViewResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author mabailu
 * @Date 2018/4/28 10:49
 * @Description MVC的入口类，是Http请求的分发中心
 */
public class DispatcherServlet extends HttpServlet {

    private final String LOCATION = "contextConfigLocation";

    private List handlerMappings = new ArrayList<HandlerMapping>();

    private Map<HandlerMapping, ErrorHandlerAdaptor> handlerAdapters = new HashMap<HandlerMapping, ErrorHandlerAdaptor>();

    private List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        ApplicationContext context = new ApplicationContext(config.getInitParameter(LOCATION));

        initStrategies(context);
    }


    /**
     * 初始化servlet需要的策略对象
     * Initialize the strategy objects that this servlet uses.
     * <p>May be overridden in subclasses in order to initialize further strategy objects.
     */
    protected void initStrategies(ApplicationContext context) {
        initMultipartResolver(context); //文件上传初始化解析
        initLocaleResolver(context);//本地化解析
        initThemeResolver(context);//主题解析

        //自己实现
        initHandlerMappings(context);//初始化http请求和Controller-method的对应关系
        //自己实现
        initHandlerAdapters(context);//通过HandlerAdapter进行多类型参数动态匹配

        initHandlerExceptionResolvers(context);//异常处理
        initRequestToViewNameTranslator(context);//直接解析请求到视图
        //自己实现
        initViewResolvers(context);//解析逻辑视图到具体视图

        initFlashMapManager(context);//flash映射管理
    }

    private void initFlashMapManager(ApplicationContext context) {
    }

    private void initViewResolvers(ApplicationContext context) {
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {
    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {
    }

    private void initHandlerAdapters(ApplicationContext context) {
    }

    /**
     * 将请求地址与Controller-Method进行对象
     *
     * @param context
     */
    private void initHandlerMappings(ApplicationContext context) {

    }

    private void initThemeResolver(ApplicationContext context) {
    }

    private void initLocaleResolver(ApplicationContext context) {
    }

    private void initMultipartResolver(ApplicationContext context) {
    }
}
