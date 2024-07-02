package exceptions;

/**
 * Ошибка работы с бд
 */
public class JDBCException extends RuntimeException{
    public JDBCException(String message) {
        super(message);
    }
}
