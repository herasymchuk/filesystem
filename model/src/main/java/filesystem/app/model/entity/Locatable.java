package filesystem.app.model.entity;

import filesystem.app.model.utils.Path;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 22:12
 * To change this template use File | Settings | File Templates.
 */

@NamedQueries( {

    @NamedQuery(
        name="Locatable.getChildrenByPath",
        query="SELECT l FROM Locatable l WHERE l.path LIKE :path"
    ),

    @NamedQuery(
        name="Locatable.getNearestChildrenByPath",
        query="SELECT l FROM Locatable l WHERE l.path = :path"
    ),

    @NamedQuery(
        name="Locatable.getByNameAndPath",
        query="SELECT l FROM Locatable l WHERE l.path = :path AND l.name = :name"
    ),

    @NamedQuery(
        name="Locatable.deleteItem",
        query="DELETE FROM Locatable l WHERE l.path LIKE :path OR l.id = :id"
    ),
    @NamedQuery(
        name="Locatable.moveItem",
        query="UPDATE Locatable l SET l.path = CONCAT(:newPath, SUBSTRING(l.path, LENGTH(:oldPath)+1))  WHERE l.path LIKE :path OR l.id = :id"
    ),
    @NamedQuery(
        name="Locatable.renameItem",
        query="UPDATE Locatable l SET l.path = CONCAT(:newPath, SUBSTRING(l.path, LENGTH(:oldPath)+1))  WHERE l.path LIKE :path"
    ),
    @NamedQuery(
        name="Locatable.updateItemName",
        query="UPDATE Locatable l SET l.name = :name  WHERE l.id = :id"
    )
})


@Entity
@Inheritance
@Table(name="filesystem")
public class Locatable extends BaseEntity {

    public Locatable() {
        setDate(new Date());
        setPath("");
    }

    public Locatable(Locatable parent) {
        this();
        setParent(parent);
    }

    public Locatable(String name, Locatable parent) {
        this(parent);
        setName(name);
    }

    @Id
    @TableGenerator(name = "IdTable", table = "id_gen",
            pkColumnName = "NAME",
            valueColumnName = "VALUE",
            allocationSize = 1,
            pkColumnValue = "Locatable")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "IdTable")
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date date;

    public void setParent(Locatable parent) {
        if(parent != null) {
            setPath(parent.getFullPath());
        } else {
            setPath("");
        }
    }

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //@Transient
    //private String fullPath;

    public String getFullPath() {
        if(path.isEmpty()) {
           return getName();
        }
        return getPath() + "/" + getName();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Locatable locatable = (Locatable) o;

        if (!id.equals(locatable.id)) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
}
