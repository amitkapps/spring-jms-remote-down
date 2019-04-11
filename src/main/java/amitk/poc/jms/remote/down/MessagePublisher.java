package amitk.poc.jms.remote.down;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * @author amitkapps
 */
public class MessagePublisher {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JmsTemplate jmsTemplate;
    private String defaultDestinationName;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void postMessage(String message){
        logger.info("posting message: {}", message);

        try {
            jmsTemplate.convertAndSend(message, defaultDestinationName);
        } catch (Exception e) {
            logger.error("error sending message: " + message, e);
            throw e;
        }
    }

    public void postMessage(String message, String destinationName){
        logger.info("posting message: {}, to destination: {}", message, destinationName);

        try {
            jmsTemplate.convertAndSend(destinationName, message);
        } catch (Exception e) {
            logger.error("error sending message: " + message, e);
            throw e;
        }
    }

    public void setDefaultDestinationName(String defaultDestinationName) {
        this.defaultDestinationName = defaultDestinationName;
    }

    public String getDefaultDestinationName() {
        return defaultDestinationName;
    }
}
