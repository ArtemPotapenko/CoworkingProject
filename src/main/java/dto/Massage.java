package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сообщение для отправки клиенту
 */
@Data
@AllArgsConstructor
public class Massage {
    private String massage;
}
