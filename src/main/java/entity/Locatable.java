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
/*    @NamedQuery(
        name="Locatable.getChildrenById",
        query="SELECT locatable FROM Locatable locatable WHERE locatable.parentId = :parentId"
    ),*/

    @NamedQuery(
        name="Locatable.getChildrenByPath",
        query="SELECT l FROM Locatable l WHERE l.path LIKE :path"
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

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Locatable parent;

    public void setParent(Locatable parent) {
        if(parent != null) {
            this.parent = parent;
            if(parent.path != null) {
                setPath(parent.path + "/" + getId().toString());
            }
        } else {
            setPath(getId().toString());
        }
    }

    @OneToMany(mappedBy = "parent")
    private List<Locatable> nearestChildren  = new ArrayList<Locatable>();

    public List<Locatable> getNearestChildren() {
        return nearestChildren;
    }

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
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


}
