package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.security.InvalidParameterException;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 26.06.13
 * Time: 23:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class File extends Locatable {

    @Transient
    private Long size;

    public File() {
    }

    public File(String name) {
        super(name);
    }

    @Override
    public void setParent(Locatable parent) {
        if(parent == null || parent instanceof Folder) {
            super.setParent(parent);
        } else {
            throw new InvalidParameterException("Parent must be folder");
        }
    }

    @Lob
    @Column(length=100000)
    private byte[] data;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
