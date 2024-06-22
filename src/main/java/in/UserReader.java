package in;

import out.UserWriter;

import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Класс для ввода пользователя
 */
public class UserReader {
    private final Scanner in = new Scanner(System.in);

    /**
     * Прочитать строку
     */
    public String readString(){
        return in.next();
    }

    /**
     *
     * Прочитать число
     */
    public int readInt(){
        return in.nextInt();
    }

    /**
     *
     * Прочитать число типа long
     */
    public long readLong(){
        return in.nextLong();
    }

    /**
     * Прочитать дату
     */
    public Date readDate() {
        UserWriter.print("Введите год:");
        int year = UserReader.getInstance().readInt();
        UserWriter.print("Введите номер месяца:");
        int month = UserReader.getInstance().readInt();
        UserWriter.print("Введите день:");
        int day = UserReader.getInstance().readInt();
        UserWriter.print("Введите час:");
        int hour = UserReader.getInstance().readInt();
        UserWriter.print("Введите минуты:");
        int minute = UserReader.getInstance().readInt();
        Calendar instance = Calendar.getInstance();
        instance.set(year, month - 1, day, hour, minute, 0);
        return instance.getTime();
    }
    /**
     * Прочитать дату без времени
     */
    public Date readDay() {
        UserWriter.print("Введите год:");
        int year = UserReader.getInstance().readInt();
        UserWriter.print("Введите номер месяца:");
        int month = UserReader.getInstance().readInt();
        UserWriter.print("Введите день:");
        int day = UserReader.getInstance().readInt();
        Calendar instance = Calendar.getInstance();
        instance.set(year, month - 1, day);
        return instance.getTime();
    }

    private UserReader() {
    }
    private static UserReader reader= new UserReader();

    public static void setReader(UserReader reader) {
        UserReader.reader = reader;
    }

    public static UserReader getInstance(){
        return reader;
    }
}
