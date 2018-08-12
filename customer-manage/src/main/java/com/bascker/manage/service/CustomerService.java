package com.bascker.manage.service;

import com.bascker.manage.model.Customer;
import com.bascker.smartframework.helper.DBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 *
 * @author bascker
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    /**
     * 获取客户列表
     * @return
     */
    public List<Customer> list() {
        final String sql = "SELECT * FROM customer";
        return DBHelper.list(Customer.class, sql);
    }

    /**
     * 获取客户信息
     * @param id
     * @return
     */
    public Customer get(final long id) {
        final String sql = "SELECT * FROM customer WHERE id = ?";
        return DBHelper.query(Customer.class, sql, id);
    }

    /**
     * 创建客户
     * @param fieldMap
     * @return
     */
    public boolean create(Map<String, Object> fieldMap) {
        return DBHelper.insert(Customer.class, fieldMap);
    }

    /**
     * 更新客户数据
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean update(final long id, final Map<String, Object> fieldMap) {
        return DBHelper.update(Customer.class, id, fieldMap);
    }

    /**
     * 删除客户
     * @param id
     * @return
     */
    public boolean delete(final long id) {
        return DBHelper.delete(Customer.class, id);
    }

}
