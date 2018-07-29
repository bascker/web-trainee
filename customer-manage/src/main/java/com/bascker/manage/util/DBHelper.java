package com.bascker.manage.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * DataBase 工具类
 *
 * @author bascker
 */
public class DBHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);

    private static final String CONF_FILENAME = "db.properties";
    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    /**
     * commons-dbutils 提供的可面向实体查询的对象
     */
    private static final QueryRunner RUNNER;

    /**
     * 使用 ThreadLocal 确保一个线程中只有一个 Connection，从而保证 Connection 的线程安全性
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    /**
     * commons-dbcp2 提供的连接池（池化技术，避免 Connection 的频繁创建，引起大量系统开销）
     */
    private static final BasicDataSource DATA_SOURCE;

    static {
        RUNNER = new QueryRunner();

        CONNECTION_HOLDER = new ThreadLocal<>();

        final Properties props = PropsUtil.loadProps(CONF_FILENAME);
        DRIVER = PropsUtil.getString(props, Constant.JDBC_DRIVER);
        URL = PropsUtil.getString(props, Constant.JDBC_URL);
        USERNAME = PropsUtil.getString(props, Constant.JDBC_USERNAME);
        PASSWORD = PropsUtil.getString(props, Constant.JDBC_PASSWORD);

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (Objects.isNull(conn)) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOGGER.error("get connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }

        return conn;
    }

    /**
     * 关闭数据库连接，屏蔽了 Connection 的创建
     */
    public static void closeConnection() {
        final Connection conn = CONNECTION_HOLDER.get();
        if (Objects.nonNull(conn)) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 关闭数据库连接
     * @param connection
     */
    @Deprecated
    public static void closeConnection(final Connection connection) {
        if (Objects.nonNull(connection)) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 查询实体列表, 屏蔽了 Connection 的创建 & 关闭
     * @param entityClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> list(final Class<T> entityClass, final String sql, final Object... args) {
        try {
            return RUNNER.query(getConnection(), sql, new BeanListHandler<>(entityClass), args);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            return Collections.emptyList();
        } finally {
            closeConnection();
        }
    }

    /**
     * 查询实体列表
     * @param entityClass   查询的实体类
     * @param conn          数据库连接对象
     * @param sql           SQL 查询语句
     * @param args          查询参数
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> List<T> list(final Class<T> entityClass, final Connection conn,
      final String sql, final Object... args) {
        try {
            return RUNNER.query(conn, sql, new BeanListHandler<>(entityClass), args);
        } catch (SQLException e) {
            LOGGER.error("query entity list failure", e);
            return Collections.emptyList();
        } finally {
            closeConnection(conn);
        }
    }

    /**
     * 查询实体对象
     * @param entityClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T query(final Class<T> entityClass, final String sql, final Object... args) {
        try {
            return RUNNER.query(getConnection(), sql, new BeanHandler<>(entityClass), args);
        } catch (SQLException e) {
            LOGGER.error("query entity failure", e);
            return null;
        } finally {
            closeConnection();
        }
    }

    /**
     * 执行查询语句，提供多表连接查询功能. Map<String, Object> 表示列名与列值之间的映射关系
     * @param sql
     * @param args
     * @return
     */
    public static List<Map<String, Object>> executeQuery(final String sql, final Object... args) {
        try {
            return RUNNER.query(getConnection(), sql, new MapListHandler(), args);
        } catch (SQLException e) {
            LOGGER.error("execute query failure", e);
            return Collections.emptyList();
        } finally {
            closeConnection();
        }
    }

    /**
     * 执行更新语句(update/insert/delete), 返回受影响的行数
     * @param sql
     * @param args
     * @return rows, 受影响的行数
     */
    public static int executeUpdate(final String sql, final Object... args) {
        try {
            return RUNNER.update(getConnection(), sql, args);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            return 0;
        } finally {
            closeConnection();
        }
    }

    /**
     * 插入实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insert(final Class<T> entityClass, final Map<String, Object> fieldMap) {
        if (CollectionHelper.isEmpty(fieldMap)) {
            LOGGER.error("insert entity failure, cause by fieldMap is null or empty");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass);

        // 列名
        final StringBuilder cols = new StringBuilder("(");
        // 占位符
        final StringBuilder vals = new StringBuilder("(");
        fieldMap.keySet().forEach(fieldName -> {
            cols.append(fieldName).append(", ");
            vals.append("?, ");
        });
        cols.replace(cols.lastIndexOf(", "), cols.length(), ")");
        vals.replace(vals.lastIndexOf(", "), vals.length(), ")");

        sql += (cols + " VALUES " + vals);

        final Object[] args = fieldMap.values().toArray();

        return executeUpdate(sql, args) == 1;
    }

    /**
     * 更新实体
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean update(final Class<T> entityClass, final long id, final Map<String, Object> fieldMap) {
        if (CollectionHelper.isEmpty(fieldMap)) {
            LOGGER.error("update entity failure, cause by fieldMap is null or empty");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";

        final StringBuilder cols = new StringBuilder();
        fieldMap.keySet().forEach(fieldName -> cols.append(fieldName).append("=?, "));

        sql += (cols.substring(0, cols.lastIndexOf(", ")) + " WHERE id = ?");

        final List<Object> argsList = new ArrayList<>();
        argsList.addAll(fieldMap.values());
        argsList.add(id);

        final Object[] args = argsList.toArray();

        return executeUpdate(sql, args) == 1;
    }

    /**
     * 删除实体
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean delete(final Class<T> entityClass, final long id) {
        final String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        LOGGER.debug("execute delete operation, sql = {}", sql);
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行 SQL 文件
     * @param sqlFilePath
     */
    public static void executeSqlFile(final String sqlFilePath) {
        LOGGER.debug("execute sql file, sql file path = {}", sqlFilePath);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(sqlFilePath)))) {
            String sql = null;
            while (Objects.nonNull(sql = reader.readLine())) {
                executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file failure", e);
        }
    }

    /**
     * 获取实体对象的表名
     * @param entityClass
     * @return
     */
    private static String getTableName(final Class<?> entityClass) {
        return entityClass.getSimpleName();
    }

    private DBHelper() {}

}
