package service;

import dao.FileSystemDAO;
import entity.Locatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 23:55
 * To change this template use File | Settings | File Templates.
 */
@Component("fileSystemService")
public class FileSystemServiceImpl implements FileSystemService {

    @Autowired
    private FileSystemDAO fileSystemDAO;

    @Override
    public Locatable getById(Long id) {
        return fileSystemDAO.getById(id);
    }

    @Override
    public List getChildrenById(Long id) {
        return fileSystemDAO.getChildrenById(id);
    }

    @Override
    public List getChildrenByPath(String path) {
        return fileSystemDAO.getChildrenByPath(path);
    }

    @Override
    public List getNearestChildrenByPath(String path) {
        return fileSystemDAO.getNearestChildrenByPath(path);
    }

    @Override
    public Locatable getByPath(String path) {
        return fileSystemDAO.getByPath(path);
    }

    @Override
    public void create(Locatable item) {
        fileSystemDAO.create(item);
    }

    @Override
    public void delete(Locatable item) {
        fileSystemDAO.delete(item);
    }

    @Override
    public void move(Locatable item, Locatable parent) {
       fileSystemDAO.move(item, parent);
    }
}
