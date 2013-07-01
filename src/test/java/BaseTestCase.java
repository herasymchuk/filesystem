import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * @author Vitalii Siryi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-config-test.xml"})
@SuppressWarnings("deprecation")
public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests {

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
