package com.bascker.framework.controller;

import com.bascker.framework.service.CustomerService;
import com.bascker.framework.util.CastUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 删除用户
 *
 * @author bascker
 */
@WebServlet("/customers/delete")
public class CustomerDeleteServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDeleteServlet.class);

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final long id = CastUtil.castLong(req.getParameter("id"));
        LOGGER.debug("delete customer, id = {}", id);
        final boolean success = customerService.delete(id);
        LOGGER.debug("delete customer {}", success);

        resp.sendRedirect("/customers");
    }

}
