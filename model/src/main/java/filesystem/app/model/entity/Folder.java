package filesystem.app.model.entity;

import javax.persistence.Entity;
import java.security.InvalidParameterException;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 23:28
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Folder extends Locatable {
    public Folder() {
    }

    public Folder(String name, Locatable parent) {
        super(name, parent);
    }

    public Folder(Locatable parent) {
        super(parent);
    }

    @Override
    public void setParent(Locatable parent) {
        if(parent == null || parent instanceof Folder) {
            super.setParent(parent);
        } else {
            throw new InvalidParameterException("Parent must be folder");
        }
    }
}
