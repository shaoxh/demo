package com.example.demo.service;

import com.example.demo.util.ConnectionFactory;
import com.example.demo.constant.Constants;
import com.example.demo.util.RequestAdaptor;
//import com.tracknix.jspmyadmin.framework.constants.Constants;
//import com.tracknix.jspmyadmin.framework.web.utils.RequestAdaptor;

import javax.servlet.http.HttpSession;
import java.sql.*;

/**
 * @author Yugandhar Gangu
 */
public class ApiConnectionImpl implements ApiConnection {

    // private static final String _DRIVER = "com.mysql.jdbc.Driver";
    private static final String _URL = "jdbc:mysql://";
    private static final String _YEARISDATETYPE = "?yearIsDateType=false";

    private final Connection _connection;

    /**
     * @throws SQLException e
     */
    public ApiConnectionImpl() throws SQLException {
        try {
            _connection = _openConnection(null);
            if (_connection != null) {
                _connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
//            HttpSession session = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
//            session.setAttribute(Constants.SESSION_CONNECT, true);
            throw e;
        }
    }

    /**
     * @param dbName database name
     * @throws SQLException e
     */
    public ApiConnectionImpl(String dbName) throws SQLException {
        try {
            _connection = _openConnection(dbName);
            if (_connection != null) {
                _connection.setAutoCommit(false);
            }
        } catch (SQLException e) {
//            HttpSession session = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
//            session.setAttribute(Constants.SESSION_CONNECT, true);
            throw e;
        }
    }

    /**
     * @param host host name
     * @param port port number
     * @param user username
     * @param pass password
     * @throws SQLException e
     */
   public ApiConnectionImpl(String host, String port, String user, String pass) throws SQLException {
        try {
            _connection = _openConnection(host, port, user, pass);
        } catch (SQLException e) {
//            HttpSession session = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
//            session.setAttribute(Constants.SESSION_CONNECT, true);
            throw e;
        }
    }

    public void close() {
        try {
            if (_connection != null && !_connection.isClosed()) {
                _connection.close();
            }
        } catch (SQLException ignored) {
        }
    }

    public PreparedStatement getStmtSelect(final String query) throws SQLException {
        return _connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
    }

    public PreparedStatement getStmt(final String query) throws SQLException {
        return _connection.prepareStatement(query);
    }

    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        return _connection.getMetaData();
    }

    public void commit() {
        if (_connection != null) {
            try {
                _connection.commit();
            } catch (SQLException ignored) {
            }
        }
    }

    public void rollback() {
        if (_connection != null) {
            try {
                _connection.rollback();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * @param host host name
     * @param port port number
     * @param user username
     * @param pass password
     * @return Connection
     * @throws SQLException e
     */
    private Connection _openConnection(String host, String port, String user, String pass) throws SQLException {

        /*try {
            Class.forName(_DRIVER);
        } catch (ClassNotFoundException e) {
            return null;
        }*/
        Connection connection;
        StringBuilder builder;
        builder = new StringBuilder(_URL);
        builder.append(host);
        builder.append(Constants.SYMBOL_COLON);
        builder.append(port);
        builder.append(Constants.SYMBOL_BACK_SLASH);
        connection = DriverManager.getConnection(builder.toString(), user, pass);
        return connection;
    }

    /**
     * @param dbName database name
     * @return Connection
     * @throws SQLException e
     */
    private Connection _openConnection(String dbName) throws SQLException {

        /*try {
            Class.forName(_DRIVER);
        } catch (ClassNotFoundException e) {
            return null;
        }*/
        Connection connection = null;
        StringBuilder builder = new StringBuilder(_URL);
        switch (ConnectionFactory.connectionType) {
            case LOGIN:
                HttpSession httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
                if (httpSession.getAttribute(Constants.SESSION_HOST) != null) {
                    builder.append(httpSession.getAttribute(Constants.SESSION_HOST).toString());
                    builder.append(Constants.SYMBOL_COLON);
                    builder.append(httpSession.getAttribute(Constants.SESSION_PORT).toString());
                    builder.append(Constants.SYMBOL_BACK_SLASH);
                }
                if (dbName != null) {
                    builder.append(dbName);
                }
                builder.append(_YEARISDATETYPE);
                String pass = httpSession.getAttribute(Constants.SESSION_PASS).toString();
                if (Constants.BLANK.equals(pass)) {
                    pass = null;
                }
                connection = DriverManager.getConnection(builder.toString(),
                        httpSession.getAttribute(Constants.SESSION_USER).toString(), pass);
                break;

            case HALF_CONFIG:
                if (ConnectionFactory.config.getHost() != null) {
                    builder.append(ConnectionFactory.config.getHost());
                    builder.append(Constants.SYMBOL_COLON);
                    builder.append(ConnectionFactory.config.getPort());
                    builder.append(Constants.SYMBOL_BACK_SLASH);
                }
                httpSession = RequestAdaptor.REQUEST_MAP.get(Thread.currentThread().getId()).getSession();
                if (dbName != null) {
                    builder.append(dbName);
                }
                builder.append(_YEARISDATETYPE);
                pass = httpSession.getAttribute(Constants.SESSION_PASS).toString();
                if (Constants.BLANK.equals(pass)) {
                    pass = null;
                }
                connection = DriverManager.getConnection(builder.toString(),
                        httpSession.getAttribute(Constants.SESSION_USER).toString(), pass);
                break;

            case CONFIG:
                if (ConnectionFactory.config.getHost() != null) {
                    builder.append(ConnectionFactory.config.getHost());
                    builder.append(Constants.SYMBOL_COLON);
                    builder.append(ConnectionFactory.config.getPort());
                    builder.append(Constants.SYMBOL_BACK_SLASH);
                }
                if (dbName != null) {
                    builder.append(dbName);
                }
                builder.append(_YEARISDATETYPE);
                connection = DriverManager.getConnection(builder.toString(), ConnectionFactory.config.getUser(),
                        ConnectionFactory.config.getPass());
                break;
        }

        return connection;
    }

}
