package filesystem.app.service.test;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * @author Vitalii Siryi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/filesystem/app/model/entity/applicationContext-model.xml", "/filesystem/app/service/applicationContext-service.xml"})
@SuppressWarnings("deprecation")
public abstract class BaseTestCase extends AbstractJUnit4SpringContextTests {

    public static void assertContains(Object source, Collection collection){
        assertContains("", source, collection);
    }

    public static void assertContains( String message, Object source, Collection collection){
        if(collection == null){
            Assert.fail("Source collection is Null");
        }
        for(Object object: collection){
            if(source.equals(object)){
                return;
            }
        }
        Assert.fail(message);
    }

}
