package services;

import dto.User;
import exceptions.ExitException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Date;

public class ExecuteServiceTest {
    private final ExecuteService service = new ExecuteService();
    private User user = new User(1L, "User", "12345");

    static UserReader userReader = Mockito.mock(UserReader.class);

    @BeforeAll
    public static void mock() {
        Mockito.when(userReader.readDay()).thenReturn(new Date());
        Mockito.when(userReader.readDate()).thenReturn(new Date());
        Mockito.when(userReader.readLong()).thenReturn(1L);
        Mockito.when(userReader.readInt()).thenReturn(1);
        Mockito.when(userReader.readString()).thenReturn("User");
        UserReader.setReader(userReader);
    }

    @Test
    public void commandsTest() throws ExitException {
        for (int i = 0; i < 9; i++) {
            service.execute(user, i);
        }
    }
}


