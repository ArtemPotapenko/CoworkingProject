package dto;

import lombok.Data;

/**
 * Запрос с именем и паролем пользователя
 */
@Data
public class UserRequest {
    private String username;
    private String password;
}
