package amitk.poc.jms.remote.down;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author amitkapps
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/jms-context.xml")
public class MessagePublisherTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessagePublisher messagePublisher;

    @Test
    public void publishMessage() throws InterruptedException {

//        logger.info("Context loaded");

        for (int i = 0; i < 100; i++) {

            messagePublisher.postMessage("Message " + i);
            Thread.currentThread().sleep(5000L);
        }

    }
}
