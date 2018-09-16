package com.bascker.manage.controller;

import com.bascker.manage.model.Customer;
import com.bascker.manage.service.CustomerService;
import com.bascker.smartframework.annotation.Action;
import com.bascker.smartframework.annotation.Controller;
import com.bascker.smartframework.annotation.Inject;
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

    @Inject
    private CustomerService customerService;

    @Action("GET:/customers")
    public View index() {
        final List<Customer> customers = customerService.list();
        LOGGER.debug("customers = {}", customers);
        return new View("customer.jsp").addAttribute("customers", customers);
    }

    @Action("GET:/customers/show")
    public View show(final Param param) {
        final long id = param.getLong("id");
        final Customer customer = customerService.get(id);
        return new View("customerShow.jsp").addAttribute("customer", customer);
    }

    @Action("GET:/customers/create")
    public View create() {
        return new View("customerCreate.jsp");
    }

    /**
     * 直接使用 index() 返回 customers，页面的 url 未改变（类似转发）
     * TODO: 支持重定向
     * @param param
     * @return
     */
    @Action("POST:/customers/create")
    public View doCreate(final Param param) {
        final Map<String, Object> paramMap = param.getParamMap();
        final boolean rs = customerService.create(paramMap);
        LOGGER.debug("create customer{}, rs = {}", paramMap, rs);
        return index();
    }

    @Action("GET:/customers/edit")
    public View edit(final Param param) {
        final long id = param.getLong("id");
        final Customer customer = customerService.get(id);
        return new View("customerEdit.jsp").addAttribute("customer", customer);
    }

    /**
     * TODO: 支持 PUT 请求
     * @param param
     * @return
     */
    @Action("POST:/customers/edit")
    public View doEdit(final Param param) {
        final long id = param.getLong("id");
        final Map<String, Object> paramMap = param.getParamMap();
        final boolean rs = customerService.update(id, paramMap);
        LOGGER.debug("update customer{}, new data is {}, rs = {}", id, paramMap, rs);
        return index();
    }

    /**
     * TODO: 支持 DELETE 请求
     * @param param
     * @return
     */
    @Action("GET:/customers/delete")
    public View delete(final Param param) {
        final long id = param.getLong("id");
        final boolean rs = customerService.delete(id);
        LOGGER.debug("delete customer who id is {}, rs = {}", id, rs);
        return index();
    }

}