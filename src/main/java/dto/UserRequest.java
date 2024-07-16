package dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * Запрос с именем и паролем пользователя
 */
@Data
@JsonSerialize
public class UserRequest {
    private String username;
    private String password;
}
