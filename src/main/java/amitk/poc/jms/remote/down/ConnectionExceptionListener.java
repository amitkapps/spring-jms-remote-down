package amitk.poc.jms.remote.down;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

/**
 * @author amitkapps
 */
public class ConnectionExceptionListener implements ExceptionListener {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onException(JMSException e) {
        logger.error("JMS Exception", e);
    }
}
