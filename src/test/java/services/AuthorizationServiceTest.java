package services;

import dao.UserDao;
import dto.User;
import exceptions.PasswordNotEqualsException;
import exceptions.UserAlreadyExistException;
import exceptions.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Base64;

public class AuthorizationServiceTest {
    static AuthorizationService authorizationService = new AuthorizationService(new UserDao());


    @Test
    public void oneUserTest() throws UserAlreadyExistException, UserNotFoundException, PasswordNotEqualsException {
        Assertions.assertEquals(authorizationService.register("User1", "111"), new User(1L, "User1", Base64.getEncoder().encodeToString("111".getBytes())));
        Assertions.assertEquals(authorizationService.login("User1", "111").getUsername(), "User1");
    }

    @Test
    public void notFoundUserTest() {
        Assertions.assertThrows(UserNotFoundException.class, () -> authorizationService.login("User", "1235"));
    }

    @Test
    public void incorrectPasswordTest() throws UserAlreadyExistException {
        authorizationService.register("User", "111");
        Assertions.assertThrows(PasswordNotEqualsException.class, () -> authorizationService.login("User", "12345"));
    }

    @Test
    public void twoUserTest() throws UserNotFoundException, PasswordNotEqualsException, UserAlreadyExistException {
        authorizationService.register("MyUser1", "111");
        authorizationService.register("MyUser2", "123");
        Assertions.assertThrows(UserAlreadyExistException.class, () -> authorizationService.register("MyUser1", "123"));
        Assertions.assertEquals(authorizationService.login("MyUser1", "111").getUsername(), "MyUser1" );
        Assertions.assertEquals(authorizationService.login("MyUser2", "123").getUsername(), "MyUser2");
    }
}
