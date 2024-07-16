package dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс для запроса на создание места
 */
@Data
@AllArgsConstructor
@JsonSerialize
public class SpaceRequest {
    private String spaceName;
}
