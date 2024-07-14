package out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Класс ддя записи объектов в тело ответа в формате json
 */
public class ResponseWriter {
    /**
     * Запись ответа
     * @param obj объект
     * @param response Объект ответа
     * @param <T> Тим объекта
     * @throws IOException
     */
    public static  <T> void write(T obj,HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(obj);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
