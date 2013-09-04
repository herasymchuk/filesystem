package filesystem.app.model.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 05.08.13
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class Path {

    private static final String PATH_DELIMETER = "/";

    public Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String path;

    public Boolean hasParent() {
        return path.lastIndexOf(PATH_DELIMETER) > -1;
    }

    public String getParentPath() {
        return hasParent() ? path.substring(0, path.lastIndexOf(PATH_DELIMETER)) : "";
    }
}
