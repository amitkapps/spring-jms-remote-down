package amitk.poc.jms.remote.down;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author amitkapps
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("publisher")
@ContextConfiguration("classpath:/application-context.xml")
public class MessagePublisherTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessagePublisher messagePublisher;

    @Test
    public void publishMessageToDefaultDestination() throws InterruptedException {

//        logger.info("Context loaded");

        for (int i = 0; i < 100; i++) {

            messagePublisher.postMessage("Message " + i);
            Thread.currentThread().sleep(5000L);
        }

    }

    @Value("#{appProperties.ordersDestinationName}")
//    @Value("#{appProperties.deliveriesDestinationName}")
    String publisherDestinationName;
    @Test
    public void publishMessageToCustomDestination() throws InterruptedException {

//        logger.info("Context loaded");

//        String destinationName = "jms/queue/deliveries";
        for (int i = 1; i <= 50; i++) {

            messagePublisher.postMessage("Message " + i, publisherDestinationName);
            Thread.currentThread().sleep(100L);
        }

    }
}
