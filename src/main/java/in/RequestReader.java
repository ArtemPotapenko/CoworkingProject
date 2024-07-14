package in;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Scanner;

/**
 * класс для чтения и парсинга данных из запроса
 */
public class RequestReader {
    public static <T> T readRequest(HttpServletRequest request, Class<T> clazz) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonData, clazz);
    }
}
