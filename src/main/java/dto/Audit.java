package dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Аудит
 */
@Data
public class Audit {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private User user;
    private String command;
    private Date date;
}
