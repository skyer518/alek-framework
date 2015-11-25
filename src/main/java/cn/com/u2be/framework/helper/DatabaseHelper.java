package cn.com.u2be.framework.helper;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by �� on 2015/11/23.
 */
public final class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);


    private static final QueryRunner QUERY_RUNNER;

    private static final BasicDataSource DATA_SOURCE;


    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;

    private static final String PASSWORD;

    static {
        QUERY_RUNNER = new QueryRunner();

        DRIVER = ConfigHelper.getJdbcDriver();
        URL = ConfigHelper.getJdbcUrl();
        USERNAME = ConfigHelper.getJdbcUsername();
        PASSWORD = ConfigHelper.getJdbcPassword();

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            return null;
        }
    };

    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }

    }


    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }


    public static Connection getConnection() {
        Connection connection = CONNECTION_HOLDER.get();
        try {
            if (connection == null) {
                connection = DATA_SOURCE.getConnection();
            }
        } catch (Exception e) {
            LOGGER.error("get Connection failure", e);
        } finally {
            CONNECTION_HOLDER.set(connection);
        }
        return connection;
    }

    /**
     * ��ѯ ʵ���б�
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {

        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * ��ѯ ��һʵ��
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * ִ�в�ѯ���
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (Exception e) {
            LOGGER.error("query entity list failure", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * ����ʵ�壬 insert update delete
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdata(String sql, Object... params) {
        int rows = 0;
        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            LOGGER.error("execute update failure", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * ����ʵ��
     *
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {

        if (MapUtils.isEmpty(fieldMap)) {
            LOGGER.error("Can not insert entity : fieldMap is empty ");
            return false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);

        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");

        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(" , ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), values.length(), ")");
        sql += columns + "VALUES" + values;

        Object[] params = fieldMap.values().toArray();

        return executeUpdata(sql, params) == 1;
    }

    /**
     * ���� ʵ��
     *
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {

        if (MapUtils.isEmpty(fieldMap)) {
            LOGGER.error("Can not update entity : fieldMap is empty ");
            return false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";

        StringBuilder columns = new StringBuilder("(");

        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?  , ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        sql += columns + " WHERE id= ?";

        List<Object> params = new ArrayList<Object>(0);
        params.addAll(fieldMap.values());
        params.add(id);

        return executeUpdata(sql, params) == 1;
    }


    /**
     * ɾ��ʵ��
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdata(sql, id) == 1;
    }

    /**
     * ȡ����  entityClass.getSimpleName();
     *
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }


}
