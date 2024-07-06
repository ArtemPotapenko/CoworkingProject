package dao;

import dto.Space;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SpaceDaoTest {
    private static SpaceDao dao;

    @Test
    @DisplayName("Добавить место")
    public void insertOne() {
        Space space = new Space("123");
        Assertions.assertEquals(dao.createSpace(space), space);
        Assertions.assertEquals(space, dao.getSpaceById(1L).get());
    }

    @Test
    @DisplayName("Добавить место")
    public void getAll() {
        Assertions.assertEquals(dao.getAllSpaces().size(), 1);

    }
    @Test
    @DisplayName("Удалить место")
    public void delete() {
        Assertions.assertTrue(dao.deleteSpace(1L));
        Assertions.assertEquals(dao.getAllSpaces().size(), 0);
    }


}
