package dao;

import exceptions.JDBCException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JBDCConnection {

    @Getter
    private final Connection connection;
    @Value("${spring.data.url}")
    private String url;
    @Value("${spring.data.password}")
    private String password;
    @Value("${spring.data.user}")
    private String user;

    public JBDCConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new JDBCException("Could not connect to database");
        }
    }
}
