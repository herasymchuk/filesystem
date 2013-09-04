package filesystem.app.service.test;

import filesystem.app.model.entity.File;
import filesystem.app.model.entity.Folder;
import filesystem.app.model.entity.Locatable;
import filesystem.app.service.FileSystemService;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class FileSystemServiceTest extends BaseTestCase {

    @Autowired
    private FileSystemService fileSystemService;

    @Test
    public void simpleTest() throws Exception {
        Folder rootFolder = new Folder("root", null);
        fileSystemService.create(rootFolder);

        Folder folder = new Folder("firstFolder", rootFolder);
        fileSystemService.create(folder);

        File file = new File("firstFile", folder);
        file.setData(IOUtils.toByteArray((getClass().getResourceAsStream("test.txt"))));
        fileSystemService.create(file);
        fileSystemService.rename(rootFolder, "toor");
        folder = (Folder)fileSystemService.getById(folder.getId());
        fileSystemService.rename(folder, "folder_renamed");
        rootFolder = (Folder)fileSystemService.getById(rootFolder.getId());
        folder = (Folder)fileSystemService.getById(folder.getId());
        file = (File)fileSystemService.getById(file.getId());
        fileSystemService.rename(file, "file_renamed");
        rootFolder = (Folder)fileSystemService.getById(rootFolder.getId());
        folder = (Folder)fileSystemService.getById(folder.getId());
        file = (File)fileSystemService.getById(file.getId());
        rootFolder = (Folder)fileSystemService.getById(rootFolder.getId());
        folder = (Folder)fileSystemService.getById(folder.getId());
        file = (File)fileSystemService.getById(file.getId());
        TestCase.assertEquals(folder, fileSystemService.getByPath(createPath(rootFolder, folder)));

        List children = fileSystemService.getChildrenById(folder.getId());
        TestCase.assertNotNull(children);
        TestCase.assertEquals(1, children.size());
        assertContains(file, children);
        children = fileSystemService.getChildrenByPath(rootFolder.getPath());
        TestCase.assertNotNull(children);
        TestCase.assertEquals(2, children.size());
        assertContains(folder, children);
        assertContains(file, children);

        children = fileSystemService.getNearestChildrenByPath(rootFolder.getPath());
        TestCase.assertNotNull(children);
        TestCase.assertEquals(1, children.size());
        assertContains(folder, children);
        children = fileSystemService.getNearestChildrenByPath(folder.getPath());
        TestCase.assertNotNull(children);
        TestCase.assertEquals(1, children.size());
        assertContains(file, children);


        File file2 = new File("secondFile", rootFolder);
        fileSystemService.create(file2);

        Folder folder2 = new Folder("secondFolder", rootFolder);
        fileSystemService.create(folder2);

        String pathToFile2 = createPath(rootFolder, file2);
        TestCase.assertEquals(file2, fileSystemService.getByPath(pathToFile2));
        fileSystemService.delete(file2);
        TestCase.assertEquals(null, fileSystemService.getByPath(pathToFile2));

        fileSystemService.move(folder, folder2);

        //how update file persistance filesystem.app.model.entity properly
        assertContains(file, fileSystemService.getChildrenByPath(folder2.getPath()));
        assertContains(folder, fileSystemService.getChildrenByPath(folder2.getPath()));
        children = fileSystemService.getChildrenById(folder.getId());
        assertContains(file, children);

        File f = (File)fileSystemService.getByPath(createPath(rootFolder, folder2, folder, file));
        Assert.assertArrayEquals(IOUtils.toByteArray((getClass().getResourceAsStream("test.txt"))), f.getData());

        //fileSystemService.delete(folder2);
        //TestCase.assertEquals(0, fileSystemService.getChildrenById(rootFolder.getId()).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameTest() throws Exception {
        Folder rootFolder = new Folder("root/", null);
        fileSystemService.create(rootFolder);
    }

    @Test(expected = IllegalArgumentException.class)
    public void creationConflictTest() throws Exception {
        Folder rootFolder = new Folder("root3", null);
        fileSystemService.create(rootFolder);
        Folder rootFolder2 = new Folder("root3", null);
        fileSystemService.create(rootFolder2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void renameTest() throws Exception {
        Folder rootFolder = new Folder("root2", null);
        fileSystemService.create(rootFolder);
        fileSystemService.rename(rootFolder, "ro/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void renamingConflictTest() throws Exception {
        Folder rootFolder = new Folder("root20", null);
        fileSystemService.create(rootFolder);
        Folder rootFolder2 = new Folder("root21", null);
        fileSystemService.create(rootFolder2);
        fileSystemService.rename(rootFolder2, "root20");
    }

    @Test(expected = IllegalArgumentException.class)
    public void movingConflictTest() throws Exception {
        Folder rootFolder = new Folder("root2", null);
        fileSystemService.create(rootFolder);

        Folder folder = new Folder("firstFolder2", rootFolder);
        fileSystemService.create(folder);

        File file = new File("firstFile2", folder);
        file.setData(IOUtils.toByteArray((getClass().getResourceAsStream("filesystem/app/service/test/test.txt"))));
        fileSystemService.create(file);

        Folder folder2 = new Folder("firstFolder2", folder);
        fileSystemService.create(folder2);
        fileSystemService.move(folder2, rootFolder);
    }

    private String createPath(Locatable ...items) {
        StringBuffer path = new StringBuffer("");
        for (Locatable item : items) {
            path.append(item.getName()).append("/");
        }
        return path.deleteCharAt(path.length()-1).toString();
    }
}
