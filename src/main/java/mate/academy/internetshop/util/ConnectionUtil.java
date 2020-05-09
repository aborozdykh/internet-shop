package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.internetshop.web.filters.AuthorizationFilter;
import org.apache.log4j.Logger;

public class ConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String DBMS = "mysql";
    private static final String SERVER_NAME = "localhost";
    private static final String PORT_NUMBER = "3306";
    private static final String DATABASE = "internetshop";
    private static final String USER = "internetshop";
    private static final String PASSWORD = "Qwerty1@";
    private static final String TIME_ZONE = "UTC";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Can't find MySQL Driver", e);
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", USER);
        dbProperties.put("password", PASSWORD);
        String url = "jdbc:" + DBMS + "://" + SERVER_NAME + ":" + PORT_NUMBER + "/" + DATABASE
                + "?serverTimezone=" + TIME_ZONE;

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, dbProperties);
            return connection;
        } catch (SQLException e) {
            LOGGER.error("Can't establish the connection to DB", e);
            throw new RuntimeException("Can't establish the connection to DB", e);
        }
    }
}
