package filesystem.app.model.dao;

import filesystem.app.model.entity.Locatable;
import org.springframework.stereotype.Repository;

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
@Repository("fileSystemDAO")
public class FileSystemDAOImpl implements filesystem.app.model.dao.FileSystemDAO {

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
        return getChildrenByPath(entityManager.find(Locatable.class, id).getFullPath());
    }

    /**
     * Return all children by full item path
     * @param path - full item path (item.getFullPath())
     * @return
     */
    @Override
    public List<Locatable> getChildrenByPath(String path) {
        return entityManager.createNamedQuery("Locatable.getChildrenByPath", Locatable.class)
                .setParameter("path", path+"%")
                .getResultList();
    }

    /**
     * Return only nearest children by full item path
     * @param path - full item path (item.getFullPath())
     * @return
     */
    @Override
    public List getNearestChildrenByPath(String path) {
        return  entityManager.createNamedQuery("Locatable.getNearestChildrenByPath", Locatable.class)
                .setParameter("path", path)
                .getResultList();
    }

    @Override
    public Locatable getByNameAndPath(String name, String path) {
        List<Locatable> results = entityManager.createNamedQuery("Locatable.getByNameAndPath", Locatable.class)
                                    .setParameter("name", name)
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
                .setParameter("id", item.getId())
                .setParameter("path", item.getFullPath()+"%")
                .executeUpdate();
    }

    @Override
    public void move(Locatable item, String newPath) throws IllegalArgumentException {
        entityManager.createNamedQuery("Locatable.moveItem")
                .setParameter("oldPath", item.getPath())
                .setParameter("newPath", newPath)
                .setParameter("path", item.getFullPath()+"%")
                .setParameter("id", item.getId())
                .executeUpdate();
    }

    @Override
    public void rename(Locatable item, String newName) {
        String oldPath = item.getFullPath();
        StringBuilder newPath = new StringBuilder(item.getPath());
        if(!item.getPath().isEmpty()){
            newPath.append("/");
        }
        newPath.append(newName).toString();
        entityManager.createNamedQuery("Locatable.renameItem")
                .setParameter("oldPath", oldPath)
                .setParameter("newPath", newPath.toString())
                .setParameter("path", oldPath+"%")
                .executeUpdate();
        item.setName(newName);
        entityManager.merge(item);
    }
}
