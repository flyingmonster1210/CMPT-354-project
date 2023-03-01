package ca.cmpt354.easyNote.dao;

import ca.cmpt354.easyNote.model.Post;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class BasicDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        Properties properties = new Properties();
        InputStream inputStream = BasicDao.class.getClassLoader().getResourceAsStream("properties/db.properties");

        try {
            properties.load(inputStream);
        } catch (IOException e) {
            BasicDao.printErrorMessage("BasicDao", "initial_static_block");
            throw new RuntimeException();
        }

        driver = properties.getProperty("driver");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        url = properties.getProperty("url");
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // if you fail to load the driver, make sure you have added MySQL connector to gradle
            BasicDao.printErrorMessage("BasicDao", "getConnection");
            e.printStackTrace();
            throw new RuntimeException();
        }
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            BasicDao.printErrorMessage("BasicDao", "getConnection");
            throwables.printStackTrace();
            throw new RuntimeException();
        }

        return connection;
    }

    @Deprecated
    public static ResultSet search(Connection connection, String sql, Object[] args) throws SQLException {
        ResultSet resultSet = null;

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1, args[i]);
        }
        resultSet = preparedStatement.executeQuery();

        return resultSet;
    }

    /**
     * 這裡可以直接拋出異常
     * @param connection
     * @param preparedStatement
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    public static ResultSet search(Connection connection, PreparedStatement preparedStatement, String sql, Object[] args) throws SQLException {

        ResultSet resultSet = null;

        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i+1, args[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    public static int update(Connection connection, String sql, Object[] args) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }

        int affectedRows = preparedStatement.executeUpdate();
        preparedStatement.close();
        return affectedRows;
    }

    public static boolean closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean successful = true;

        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException throwables) {
                successful = false;
                throwables.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException throwables) {
                successful = false;
                throwables.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException throwables) {
                successful = false;
                throwables.printStackTrace();
            }
        }

        return successful;
    }

    public static void printErrorMessage(String className, String functionName) {
        System.out.println("ERROR IN "+className+":");
        System.out.println("\t\t-> "+functionName);
    }
}
