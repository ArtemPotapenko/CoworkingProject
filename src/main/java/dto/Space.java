package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Рабочее место
 * @author potap
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Space {

    @EqualsAndHashCode.Exclude
    private Long id;
    private String name;
    @ToString.Exclude
    private List<Booking> slots = new ArrayList<>();

    public Space(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + " - место " + getName();
    }

    /**
     * Добавить слот
     * @param booking добавить слот
     */
    public void addSlot(Booking booking){
        booking.setSpace(this);
        slots.add(booking);
    }
}
