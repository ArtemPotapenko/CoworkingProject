package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JBDCConnection {
    public Connection getConnection() {
        return connection;
    }

    private Connection connection;
    public JBDCConnection() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("C:\\Users\\potap\\IdeaProjects\\CoworkingProject\\src\\main\\resources\\jbdc.config"));
            String URL = prop.getProperty("url");
            System.out.println(prop.getProperty("user"));
            connection = DriverManager.getConnection(URL,prop);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
