package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Пользователь
 * @version 1.0
 * @author potap
 */
@Data
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String username;
    private String encodedPassword;

    /**
     *
     * @param username имя пользователя
     * @param encodedPassword закодированный пароль
     *
     */
    public User(String username, String encodedPassword) {
        this.username = username;
        this.encodedPassword = encodedPassword;
    }
}
