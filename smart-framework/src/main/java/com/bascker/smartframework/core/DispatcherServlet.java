package com.bascker.smartframework.core;

import com.bascker.smartframework.bean.Data;
import com.bascker.smartframework.bean.Handler;
import com.bascker.smartframework.bean.Param;
import com.bascker.smartframework.bean.View;
import com.bascker.smartframework.helper.BeanHelper;
import com.bascker.smartframework.helper.ConfigHelper;
import com.bascker.smartframework.helper.ControllerHelper;
import com.bascker.smartframework.util.CodecUtil;
import com.bascker.smartframework.util.Constant;
import com.bascker.smartframework.util.JsonUtil;
import com.bascker.smartframework.util.ReflectionUtil;
import com.bascker.smartframework.util.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 请求转发器
 *
 * @author bascker
 * @since 1.0.0
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(final ServletConfig config) throws ServletException {
        // 初始化相关 Helper
        HelperLoader.init();

        // 获取 ServletContext 对象，用于注册 Servlet
        final ServletContext servletCtx = config.getServletContext();

        // 注册处理 JSP 的 Servlet
        final ServletRegistration jspServlet = servletCtx.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的默认 Servlet
        final ServletRegistration defaultServlet = servletCtx.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求方法和请求路径
        final String reqMethod = req.getMethod().toLowerCase();
        final String reqPath = req.getPathInfo();

        // 获取 Action 处理器
        final Handler handler = ControllerHelper.getHandler(reqMethod, reqPath);
        if (Objects.nonNull(handler)) {
            // 获取 Controller 类及其 Bean 实例
            final Class<?> controllerClass = handler.getControllerClasss();
            final Object controllerBean = BeanHelper.getBean(controllerClass);

            // 创建请求参数对象
            final Map<String, Object> paramMap = new HashMap<>();
            final Enumeration<String> paramNames = req.getParameterNames();
            while (paramNames.hasMoreElements()) {
                final String paramName = paramNames.nextElement();
                final String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            final String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotEmpty(body)) {
                final String[] params = StringUtils.split(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    Arrays.stream(params).forEach(param -> {
                        final String[] array = StringUtils.split(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && 2 == array.length) {
                            final String paramName = array[0];
                            final String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    });
                }
            }
            final Param param = new Param(paramMap);

            // 调用 Action 方法
            final Method method = handler.getActionMethod();
            final Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);

            // 处理 Action 方法返回值
            if (result instanceof View) {
                // 返回 JSP 页面
                final View view = (View) result;
                final String path = view.getPath();
                if (StringUtils.isNotEmpty(path)) {
                    if (StringUtils.startsWith(path, "/")) {
                        resp.sendRedirect(req.getContextPath() + path);
                    } else {
                        final Map<String, Object> model = view.getModel();
                        model.forEach((k, v) -> req.setAttribute(k, v));
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                }
            } else if (result instanceof Data) {
                // 返回 JSON 数据
                final Data data = (Data) result;
                final Object model = data.getModel();
                if (Objects.nonNull(model)) {
                    resp.setContentType(Constant.JSON_TYPE);
                    resp.setCharacterEncoding(Constant.UTF8);
                    try (final PrintWriter writer = resp.getWriter()){
                        final String json = JsonUtil.toJson(model);
                        writer.write(json);
                        writer.flush();
                    }
                }
            }
        }
    }
}
