package dao;

import dto.Space;
import dto.User;
import exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDaoTest {
    private static UserDao dao = new UserDao();

    @Test
    @DisplayName("Добавить пользователя")
    public void insertOne() throws UserAlreadyExistException {
        User user = new User("qqq","123");
        Assertions.assertEquals(dao.createUser(user),user);
        Assertions.assertEquals(dao.getUserById(user.getId()).get(), user);
    }

    @Test
    @DisplayName("Добавить пользователя")
    public void getAll() {
        Assertions.assertEquals(dao.getUsers().size(), 1);

    }
    @Test
    @DisplayName("Удалить пользователя")
    public void delete() {
        Assertions.assertTrue(dao.deleteUser(1L));
        Assertions.assertEquals(dao.getUsers().size(), 0);
    }


}

