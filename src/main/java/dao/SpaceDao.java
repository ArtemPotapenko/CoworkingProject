package dao;

import dto.Space;
import dto.User;
import exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CRUD для Space
 * @author potap
 * @version 1.0
 */
public class SpaceDao {
    private static final Map<Long, Space> map = new HashMap<>();
    private static long maxId = 0;

    /**
     *
     * @param space рабочее место
     * @return рабочее место с новым id
     *
     * Добавить рабочее место
     */
    public Space createSpace(Space space){
        map.put(++maxId, space);
        space.setId(maxId);
        return space;
    }

    /**
     *
     * @param id
     * @return найденное место
     *
     * Найти место по id
     */
    public Optional<Space> getSpaceById(Long id){
        return Optional.ofNullable(map.get(id));
    }

    /**
     *
     * @return Все места
     */
    public List<Space> getAllSpaces(){
        return map.values().stream().toList();
    }

    /**
     *
     * @param id Id
     * @return корректность удаления
     *
     * Удалить место
     */
    public boolean deleteSpace(Long id){
        if (!map.containsKey(id)) {
            return false;
        } else {
            map.remove(id);
            return true;
        }
    }


}
