package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Объект запроса на создание бронирования
 */
@Data
@AllArgsConstructor
public class BookRequest {
    private Long spaceId;
    private Date startDate;
    private Date endDate;
}
