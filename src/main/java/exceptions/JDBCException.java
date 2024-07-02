package exceptions;

public class JDBCException extends RuntimeException{
    public JDBCException(String message) {
        super(message);
    }
}
