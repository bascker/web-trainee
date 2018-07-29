package com.bascker.manage.controller;

import com.bascker.manage.service.CustomerService;
import com.bascker.manage.util.Constant;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建用户
 *
 * @author bascker
 */
@WebServlet("/customers/create")
public class CustomerCreateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreateServlet.class);

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    /**
     * 进入创建用户界面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/customerCreate.jsp").forward(req, resp);
    }

    /**
     * 处理创建用户请求
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final Map<String, String[]> parameterMap = req.getParameterMap();
        final Map<String, Object> fieldMap = new HashMap<>();
        parameterMap.forEach((k, v) -> {
            if (ArrayUtils.isEmpty(v)) {
                fieldMap.put(k, Constant.EMPTY_STR);
            } else if (v.length == 1){
                fieldMap.put(k, v[0]);
            }
        });

        final boolean success = customerService.create(fieldMap);
        LOGGER.debug("create customer {}", success);

        resp.sendRedirect("/customers");
    }

}
