package dao;

import entity.Locatable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 23:52
 * To change this template use File | Settings | File Templates.
 */
@Component("fileSystemDAO")
public class FileSystemDAOImpl implements FileSystemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Locatable getById(Long id) {
        return entityManager.find(Locatable.class, id);
    }

    @Override
    public List<Locatable> getChildrenById(Long id) {
        return getChildrenByPath(entityManager.find(Locatable.class, id).getPath());
    }

    @Override
    public List<Locatable> getChildrenByPath(String path) {
        return entityManager.createNamedQuery("Locatable.getChildrenByPath", Locatable.class)
                .setParameter("path", path+"/%")
                .getResultList();
    }

    @Override
    public List getNearestChildrenByPath(String path) {
        return  entityManager.createNamedQuery("Locatable.getNearestChildrenByPath", Locatable.class)
                .setParameter("path", path)
                .getResultList();
    }

    @Override
    public Locatable getByPath(String path) {
        List<Locatable> results = entityManager.createNamedQuery("Locatable.getByPath", Locatable.class)
                                    .setParameter("path", path)
                                    .getResultList();
        if(!results.isEmpty()) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public void create(Locatable item) {
        entityManager.persist(item);
    }

    @Override
    public void delete(Locatable item) {
        entityManager.createNamedQuery("Locatable.deleteItem")
                .setParameter("path", item.getPath()+"%")
                .executeUpdate();
    }

    @Override
    public void move(Locatable item, Locatable parent) {
        int count = entityManager.createNamedQuery("Locatable.moveItem")
                .setParameter("oldPath", item.getPath())
                .setParameter("newPath", parent.getPath() + "/" + item.getName())
                .setParameter("path", item.getPath()+"%")
                .executeUpdate();
    }
}
