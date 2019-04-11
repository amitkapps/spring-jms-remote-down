package amitk.poc.jms.remote.down;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author amitkapps
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/jms-context.xml")
public class MessageListenerTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testContextLoads() throws InterruptedException {
        logger.info("Context loaded");

        Thread.currentThread().sleep(100000L);
    }
}
