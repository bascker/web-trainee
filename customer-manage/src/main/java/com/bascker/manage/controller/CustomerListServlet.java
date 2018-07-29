package com.bascker.manage.controller;

import com.bascker.manage.model.Customer;
import com.bascker.manage.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 进入客户列表界面
 */
@WebServlet("/customers")
public class CustomerListServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerListServlet.class);

    private CustomerService customerService;

    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final List<Customer> customers = customerService.list();
        LOGGER.debug("get customers list {}", customers);
        req.setAttribute("customers", customers);
        req.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(req, resp);
    }

}
