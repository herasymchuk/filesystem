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

    List getChildrenById(Long id);

    List getChildrenByPath(String path);

    Locatable getByPath(String path);

    void create(Locatable file);

    void delete(Locatable file);

    void move(Locatable file, Locatable parent);
}
