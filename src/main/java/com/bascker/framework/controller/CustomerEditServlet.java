package com.bascker.framework.controller;

import com.bascker.framework.model.Customer;
import com.bascker.framework.service.CustomerService;
import com.bascker.framework.util.CastUtil;
import com.bascker.framework.util.Constant;
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
 * 编辑用户
 *
 * @author bascker
 */
@WebServlet("/customers/edit")
public class CustomerEditServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEditServlet.class);

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final long id = CastUtil.castLong(req.getParameter("id"));
        LOGGER.debug("edit customer, id = {}", id);

        final Customer customer = customerService.get(id);
        req.setAttribute("customer", customer);
        req.getRequestDispatcher("/WEB-INF/view/customerEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {


        // value 为 String[] 是因为表单可能存在 checkbox 组件
        final Map<String, String[]> parameterMap = req.getParameterMap();
        final Map<String, Object> fieldMap = new HashMap<>();
        parameterMap.forEach((k, v) -> {
            if (ArrayUtils.isEmpty(v)) {
                fieldMap.put(k, Constant.EMPTY_STR);
            } else if (v.length == 1){
                fieldMap.put(k, v[0]);
            }
        });

        final long id = CastUtil.castLong(fieldMap.get("id"));
        customerService.update(id, fieldMap);
        resp.sendRedirect("/customers");
    }
}
