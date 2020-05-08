package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.internetshop.web.filters.AuthorizationFilter;
import org.apache.log4j.Logger;

public class ConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(AuthorizationFilter.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("Can't find MySQL Driver", e);
            throw new RuntimeException("Can't find MySQL Driver", e);
        }
    }

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", "internetshop");
        dbProperties.put("password", "Qwerty1@");
        String url = "jdbc:mysql://localhost:3306/internetshop?serverTimezone=UTC";

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, dbProperties);
            LOGGER.error("Connection ro DB established");
            return connection;
        } catch (SQLException e) {
            LOGGER.error("Can't establish the connection to DB", e);
            throw new RuntimeException("Can't establish the connection to DB", e);
        }
    }
}
