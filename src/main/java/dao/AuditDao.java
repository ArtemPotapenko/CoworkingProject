package dao;

import dto.Audit;
import exceptions.JDBCException;
import org.springframework.stereotype.Component;

import java.sql.*;

/**
 * Класс добавления
 */
@Component
public class AuditDao {
    private final Connection connection = new JBDCConnection().getConnection();

    /**
     * Создание аудита
     * @param audit
     * @return
     */
    public Audit createAudit(Audit audit){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  audits(command, user_id, date) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, audit.getCommand());
            preparedStatement.setLong(2,audit.getUser().getId());
            preparedStatement.setDate(3, new Date(audit.getDate().getTime()));
            preparedStatement.executeUpdate();
            preparedStatement.getGeneratedKeys().next();
            audit.setId(preparedStatement.getGeneratedKeys().getLong(1));
            return audit;
        } catch (SQLException e) {
            throw new JDBCException(e.getMessage());
        }

    }
}
