package services;

import dao.SpaceDao;
import dto.Space;
import exceptions.SpaceNotFoundException;

import java.util.List;

/**
 * Сервис для управления рабочими местами
 */
public class SpaceService {
    private final SpaceDao spaceDao = new SpaceDao();
    public Space createSpace(String spacename){
        return spaceDao.createSpace(new Space(spacename));
    }
    public List<Space> getAllSpaces(){
        return spaceDao.getAllSpaces();
    }
    public Space getSpace(Long id) throws SpaceNotFoundException {
        return spaceDao.getSpaceById(id).orElseThrow(SpaceNotFoundException::new);
    }


}
