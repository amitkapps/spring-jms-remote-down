package amitk.poc.jms.remote.down;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author amitkapps
 */
public class OrderMessageListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void onMessage(String message){

        logger.info("Received Order: {}", message);
        try {
            Thread.currentThread().sleep(3000L);
        } catch (InterruptedException e) {
            logger.warn("Interrupted exception", e);
        }

    }
}
