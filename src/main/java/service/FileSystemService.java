package service;

import entity.Locatable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public interface FileSystemService {

    @Transactional
    List getChildrenById(Long id);

    @Transactional
    List getNearestChildrenById(Long id);

    @Transactional
    List getChildrenByPath(String path);

    @Transactional
    List getNearestChildrenByPath(String path);

    @Transactional
    Locatable getByPath(String path);

    @Transactional
    void create(Locatable file);

    @Transactional
    void delete(Locatable file);

    @Transactional
    void move(Locatable item, Locatable parent);


}
