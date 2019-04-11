package amitk.poc.jms.remote.down;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author amitkapps
 */
public class MessagePublisher {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void postMessage(String message){
        logger.info("posting message: {}", message);

        try {
            jmsTemplate.convertAndSend(message);
        } catch (Exception e) {
            logger.error("error sending message: " + message, e);
            throw e;
        }
    }
}
