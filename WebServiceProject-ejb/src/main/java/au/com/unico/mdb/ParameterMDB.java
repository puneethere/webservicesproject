package au.com.unico.mdb;


import java.util.Date;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.unico.model.Pair;
import au.com.unico.model.Parameter;
import au.com.unico.service.ParameterPersistenceBeanLocal;

/**
 * Message-Driven Bean implementation class for: ParameterMDB
 */
/*@MessageDriven(
		activationConfig = { @ActivationConfigProperty(
				propertyName = "destination", propertyValue = "java:/unico/parameterQueue"), @ActivationConfigProperty(
				propertyName = "destinationType", propertyValue = "javax.jms.Queue")
		}, 
		mappedName = "parameterQueue")*/
public class ParameterMDB implements MessageListener {

    @EJB
    private ParameterPersistenceBeanLocal paramPersistenceService;
    
    private static final Logger logger = LoggerFactory
			.getLogger(ParameterMDB.class);
	
	/**
     * Default constructor. 
     */
    public ParameterMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	ObjectMessage objMsg = (ObjectMessage) message;
    	try {
			Pair pair = (Pair) objMsg.getObject();
			Parameter param1 = new Parameter();
			param1.setValue(new Long(pair.getParam1()));
			Date dt = new Date();
			param1.setEnteredDate(dt);
			paramPersistenceService.saveParameter(param1);
			Parameter param2 = new Parameter();
			param2.setValue(new Long(pair.getParam2()));
			param2.setEnteredDate(dt);
			paramPersistenceService.saveParameter(param2);
		} catch (JMSException e) {
			logger.error("Exception in the consuming the message ", e);
		}
    }

}
