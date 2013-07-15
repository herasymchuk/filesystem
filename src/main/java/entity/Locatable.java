package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        query="SELECT l FROM Locatable l WHERE l.path = CONCAT(:path, '/', l.name)"
    ),

    @NamedQuery(
        name="Locatable.getByPath",
        query="SELECT l FROM Locatable l WHERE l.path = :path"
    ),

    @NamedQuery(
        name="Locatable.deleteItem",
        query="DELETE FROM Locatable l WHERE l.path LIKE :path"
    ),
    @NamedQuery(
        name="Locatable.moveItem",
        query="UPDATE Locatable l SET l.path = CONCAT(:newPath, SUBSTRING(l.path, LENGTH(:oldPath)+1))  WHERE l.path LIKE :path"
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

    public Locatable(String name) {
        this();
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

    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date date;

    public void setParent(Locatable parent) {
        if(parent != null) {
            if(parent.path != null) {
                setPath(parent.path + "/" + getName());
            }
        } else {
            setPath(getName());
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

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
