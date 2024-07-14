package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для запроса на создание места
 */
@Data
@AllArgsConstructor
public class SpaceRequest {
    private String spaceName;
}
