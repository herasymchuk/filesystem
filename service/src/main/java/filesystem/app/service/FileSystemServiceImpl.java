package filesystem.app.service;

import filesystem.app.model.dao.FileSystemDAO;
import filesystem.app.model.dao.FileSystemDAOImpl;
import filesystem.app.model.entity.Locatable;
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
    public Locatable getByPath(String fullPath) {
        int endPathInd = fullPath.lastIndexOf("/");
        String path = endPathInd > 0 ? fullPath.substring(0, endPathInd) : "";
        String name = fullPath.substring(endPathInd+1);
        return fileSystemDAO.getByNameAndPath(name, path);
    }

    @Override
    public void create(Locatable item) throws IllegalArgumentException {
        if(!isNameValid(item.getName())) {
            throw new IllegalArgumentException("Name '" + item.getName() + "' is not valid!");
        }
        if(findItem(item.getName(), item.getPath()) != null) {
            throw new IllegalArgumentException("Item with the same name '" +
                    item.getName() + "' is already exists!");
        }
        fileSystemDAO.create(item);
    }

    @Override
    public void delete(Locatable item) {
        fileSystemDAO.delete(item);
    }

    private Locatable findItem(String name, String path) {
        return  fileSystemDAO.getByNameAndPath(name, path);
    }

    @Override
    public void move(Locatable item, Locatable parent) throws IllegalArgumentException {
        String newPath =  parent.getFullPath();
        if(findItem(item.getName(), newPath) != null) {
            throw new IllegalArgumentException("Item with the same name '" +
                    item.getName() + "' is already exists in '" +
                    newPath +  "'!");
        }
       fileSystemDAO.move(item, newPath);
    }

    @Override
    public void rename(Locatable item, String newName) throws IllegalArgumentException {
        if(!isNameValid(newName)) {
            throw new IllegalArgumentException("Name '" + newName + "' is not valid!");
        }
        if(findItem(newName, item.getPath()) != null) {
            throw new IllegalArgumentException("Item with the same name '" +
                    newName + "' is already exists in '" +
                    item.getPath() +  "'!");
        }
        fileSystemDAO.rename(item, newName);
    }

    private boolean isNameValid(String name) {
        return !(name.contains("/") ||
                name.contains("\\") ||
                name.contains("?") ||
                name.contains(","));
    }
}
