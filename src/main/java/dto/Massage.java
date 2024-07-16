package dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сообщение для отправки клиенту
 */
@Data
@AllArgsConstructor
@JsonSerialize
public class Massage {
    private String massage;
}
