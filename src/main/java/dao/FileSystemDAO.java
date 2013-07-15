package dao;

import entity.Locatable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 23:49
 * To change this template use File | Settings | File Templates.
 */

public interface FileSystemDAO {;

    Locatable getById(Long id);

    List getChildrenById(Long id);

    List getChildrenByPath(String path);

    List getNearestChildrenByPath(String path);

    Locatable getByPath(String path);

    void create(Locatable item);

    void delete(Locatable item);

    void move(Locatable item, Locatable parent);

    void rename(Locatable item, String newName);
}
