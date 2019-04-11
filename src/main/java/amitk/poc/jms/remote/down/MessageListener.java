package amitk.poc.jms.remote.down;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author amitkapps
 */
public class MessageListener {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void onMessage(String message){

        logger.info("Received message: {}", message);

    }
}
