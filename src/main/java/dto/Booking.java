package dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * бронирование
 *
 * @author potap
 * @version 1.0
 */
@Data
public class Booking {

    @EqualsAndHashCode.Exclude
    private Long id;
    private Date startTime;
    private Date endTime;
    private boolean free;
    private Space space;
    private User user;

    public Booking(Date startTime, Date endTime, Space space) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.space = space;
        this.free = true;
    }

    /**
     * Бронирование
     *
     * @param user
     */
    public void reserve(User user) {
        free = false;
        this.user = user;
    }

    /**
     * Отмена бронирования
     */
    public void cancel() {
        free = true;
        user = null;
    }

    @Override
    public String toString() {
        return id + " дата начала " + startTime + ", дата конца " + endTime + ", id места " + getSpace().getId() + (free ? " свободно" : " занято");
    }
}
