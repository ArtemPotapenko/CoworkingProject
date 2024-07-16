package dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Сообщение с токеном
 */
@Data
@AllArgsConstructor
@JsonSerialize
public class TokenResponse {
    private String token;
}
