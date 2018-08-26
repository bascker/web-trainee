package com.bascker.manage.controller;

import com.bascker.manage.model.Customer;
import com.bascker.manage.service.CustomerService;
import com.bascker.smartframework.annotation.Action;
import com.bascker.smartframework.annotation.Controller;
import com.bascker.smartframework.bean.Data;
import com.bascker.smartframework.bean.Param;
import com.bascker.smartframework.bean.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 处理客户管理相关请求
 *
 * @author bascker
 */
@Controller
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService = new CustomerService();

    @Action("GET:/customer")
    public View index() {
        final List<Customer> customers = customerService.list();
        return new View("customer.jsp").addAttribute("customers", customers);
    }

    @Action("GET:/customer/show")
    public View show(final Param param) {
        final long id = param.getLong("id");
        final Customer customer = customerService.get(id);
        return new View("customerShow.jsp").addAttribute("customer", customer);
    }

    @Action("GET:/customer/create")
    public View create() {
        return new View("customerCreate.jsp");
    }

    @Action("POST:/customer/create")
    public Data doCreate(final Param param) {
        final Map<String, Object> paramMap = param.getParamMap();
        final boolean rs = customerService.create(paramMap);
        return new Data(rs);
    }

    @Action("GET:/customer/edit")
    public View edit(final Param param) {
        final long id = param.getLong("id");
        final Customer customer = customerService.get(id);
        return new View("customerEdit.jsp").addAttribute("customer", customer);
    }

    @Action("PUT:/customer/edit")
    public Data doEdit(final Param param) {
        final long id = param.getLong("id");
        final Map<String, Object> paramMap = param.getParamMap();
        final boolean rs = customerService.update(id, paramMap);
        return new Data(rs);
    }

    @Action("DELETE:/customer")
    public View delete(final Param param) {
        final long id = param.getLong("id");
        final boolean rs = customerService.delete(id);
        return new View("customer.jsp");
    }

}