package dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * Аудит
 */
@Data
@JsonSerialize
public class Audit {
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Long id;
    private User user;
    private String command;
    private Date date;
}
